const token = localStorage.getItem('token');

function logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('username');
    window.location.href = 'login.html';
}

function isTokenExpired(t) {
    try {
        const payload = JSON.parse(atob(t.split('.')[1].replace(/-/g, '+').replace(/_/g, '/')));
        return payload.exp * 1000 < Date.now();
    } catch (e) {
        return true;
    }
}

let stompClient = null;

function loadHistory() {
    fetch('/api/messages', {
        headers: { 'Authorization': 'Bearer ' + token }
    })
        .then(res => {
            if (res.status === 401 || res.status === 403) {
                logout();
                throw new Error('Unauthorized');
            }
            if (!res.ok) throw new Error('Failed to load messages: ' + res.status);
            return res.json();
        })
        .then(messages => messages.forEach(renderMessage))
        .catch(err => console.error(err));
}

function connect() {
    stompClient = new StompJs.Client({
        // New SockJS object on every (re)connect, so reconnects don't reuse a dead socket
        webSocketFactory: () => new SockJS('/ws'),
        connectHeaders: { Authorization: 'Bearer ' + token },
        reconnectDelay: 3000,
        beforeConnect: () => {
            if (isTokenExpired(token)) {
                stompClient.deactivate();
                logout();
            }
        },
        onConnect: () => {
            stompClient.subscribe('/topic/public', (msg) => {
                renderMessage(JSON.parse(msg.body));
            });
        },
        onStompError: (frame) => console.error('STOMP error:', frame.headers['message']),
        onWebSocketError: (e) => console.error('WebSocket error:', e)
    });
    stompClient.activate();
}

function renderMessage(body) {
    const myName = localStorage.getItem('username');
    const div = document.createElement('div');
    div.className = 'message ' + (body.sender === myName ? 'mine' : 'theirs');

    const senderEl = document.createElement('span');
    senderEl.className = 'sender';
    senderEl.textContent = body.sender;          // textContent => no XSS

    div.appendChild(senderEl);
    div.appendChild(document.createTextNode(body.content));

    const messagesDiv = document.getElementById('messages');
    messagesDiv.appendChild(div);
    messagesDiv.scrollTop = messagesDiv.scrollHeight;
}

function sendMessage() {
    const input = document.getElementById('content');
    const content = input.value.trim();
    if (!content) return;

    if (!stompClient || !stompClient.connected) {
        alert('Not connected yet, try again in a moment');
        return;
    }

    // The server sets the sender from your JWT, so only content is sent
    stompClient.publish({
        destination: '/app/chat.send',
        headers: { 'content-type': 'application/json' },
        body: JSON.stringify({ content })
    });
    input.value = '';
}

function clearHistory() {
    fetch('/api/messages', {
        method: 'DELETE',
        headers: { 'Authorization': 'Bearer ' + token }
    })
        .then(res => {
            if (res.ok) document.getElementById('messages').innerHTML = '';
        });
}

if (!token || isTokenExpired(token)) {
    logout();
} else {
    loadHistory();
    connect();
}