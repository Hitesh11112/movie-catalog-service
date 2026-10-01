const token = localStorage.getItem('token');
if (!token) {
    window.location.href = 'login.html';
}

fetch('/api/messages', {
    headers: { 'Authorization': 'Bearer ' + token }
})
    .then(res => res.json())
    .then(messages => messages.forEach(renderMessage));

const socket = new SockJS('http://movie-catalog-service-production.up.railway.app/ws');
const stompClient = new StompJs.Client({
    webSocketFactory: () => socket,
    onConnect: () => {
        stompClient.subscribe('/topic/public', (msg) => {
            const body = JSON.parse(msg.body);
            renderMessage(body);
        });
    }
});
stompClient.activate();

function renderMessage(body) {
    const myName = localStorage.getItem('username');
    const div = document.createElement('div');
    div.className = 'message ' + (body.sender === myName ? 'mine' : 'theirs');
    div.innerHTML = `<span class="sender">${body.sender}</span>${body.content}`;
    const messagesDiv = document.getElementById('messages');
    messagesDiv.appendChild(div);
    messagesDiv.scrollTop = messagesDiv.scrollHeight;
}

function sendMessage() {
    const sender = localStorage.getItem('username');
    const content = document.getElementById('content').value.trim();
    if (!sender || !content) return;

    stompClient.publish({
        destination: '/app/chat.send',
        headers: { 'content-type': 'application/json' },
        body: JSON.stringify({ sender, content })
    });
    document.getElementById('content').value = '';
}

function clearHistory() {
    fetch('/api/messages', {
        method: 'DELETE',
        headers: { 'Authorization': 'Bearer ' + token }
    })
    .then(() => {
        document.getElementById('messages').innerHTML = '';
    });
}

function logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('username');
    window.location.href = 'login.html';
}