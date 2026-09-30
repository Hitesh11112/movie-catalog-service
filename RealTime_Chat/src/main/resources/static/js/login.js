function switchTab(tab) {
    document.getElementById('tab-login').classList.toggle('active', tab === 'login');
    document.getElementById('tab-register').classList.toggle('active', tab === 'register');
    document.getElementById('login-form').classList.toggle('hidden', tab !== 'login');
    document.getElementById('register-form').classList.toggle('hidden', tab !== 'register');
}

function login() {
    const username = document.getElementById('login-username').value.trim();
    const password = document.getElementById('login-password').value.trim();
    const errorEl = document.getElementById('login-error');
    errorEl.textContent = '';

    if (!username || !password) {
        errorEl.textContent = 'Enter username and password';
        return;
    }

    fetch('/api/auth/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, password })
    })
    .then(res => {
        if (!res.ok) throw new Error('Invalid credentials');
        return res.json();
    })
    .then(data => {
        localStorage.setItem('token', data.token);
        localStorage.setItem('username', username);
        window.location.href = 'test.html';
    })
    .catch(() => {
        errorEl.textContent = 'Invalid username or password';
    });
}

function register() {
    const username = document.getElementById('register-username').value.trim();
    const password = document.getElementById('register-password').value.trim();
    const errorEl = document.getElementById('register-error');
    errorEl.textContent = '';

    if (!username || !password) {
        errorEl.textContent = 'Enter username and password';
        return;
    }

    fetch('/api/auth/register', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, password })
    })
    .then(res => {
        if (!res.ok) return res.text().then(msg => { throw new Error(msg); });
        return res.text();
    })
    .then(() => {
        switchTab('login');
        document.getElementById('login-username').value = username;
    })
    .catch(err => {
        errorEl.textContent = err.message || 'Registration failed';
    });
}