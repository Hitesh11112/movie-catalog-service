# Portfolio Projects — Hitesh Patwal

## Project 1: LearnHub — Learning Platform (Microservices)

A microservices-based learning platform with two independent Spring Boot services communicating over REST via OpenFeign, with JWT-secured endpoints on the Student Service.

### Tech Stack
- Java 17
- Spring Boot
- Spring Data JPA
- MySQL
- OpenFeign (service-to-service communication)
- Spring Security + JWT
- Postman (testing)

### Architecture

Single Spring Boot service — one controller handles WebSocket messages, another handles REST auth and chat history.

Client (login.html / test.html)
│
├── REST: /api/auth/register, /api/auth/login
├── REST: /api/messages (GET history, DELETE clear)
└── WebSocket: /ws (STOMP) → /app/chat.send → broadcasts to /topic/public
│
Spring Boot App → MySQL (users, messages)



## Features
- User registration and login with JWT-based authentication
- Real-time messaging via WebSocket — no polling, no refresh needed
- Persistent chat history (loaded from MySQL on page load)
- Clear chat history button
- Passwords hashed with BCrypt before storage

## API Endpoints

| Method | Endpoint | Auth required | Description |
|--------|----------|---------------|--------------|
| POST | `/api/auth/register` | No | Register a new user |
| POST | `/api/auth/login` | No | Returns a JWT token |
| GET | `/api/messages` | Yes | Fetch chat history |
| DELETE | `/api/messages` | Yes | Clear all chat history |
| WS | `/ws` (STOMP) | — | WebSocket handshake endpoint |
| STOMP SEND | `/app/chat.send` | — | Send a message |
| STOMP SUBSCRIBE | `/topic/public` | — | Receive broadcasted messages |

## How It Works
1. User registers and logs in via REST → receives a JWT token, stored in `localStorage`
2. Client connects to `/ws` via SockJS, subscribes to `/topic/public`
3. On page load, `GET /api/messages` (with the JWT) loads existing chat history into the UI
4. Sending a message publishes to `/app/chat.send` → the server saves it to MySQL and broadcasts it to every subscribed client instantly
5. Logout clears the stored token and redirects back to the login page

## Project Structure
src/main/java/.../
├── entity/ (ChatMessage, User)
├── repository/ (ChatMessageRepository, UserRepository)
├── controller/ (ChatController, ChatRestController, AuthController)
├── config/ (WebSocketConfig)
├── security/ (SecurityConfig, JwtFilter)
└── util/ (JwtUtil)

src/main/resources/static/
├── login.html
├── test.html
├── css/
│ ├── style.css
│ └── login.css
└── js/
├── script.js
└── login.js



## Running Locally
1. Start MySQL and create the `chatapp` database
2. Run the Spring Boot application (default port 8098)
3. Open `http://localhost:8098/login.html`
4. Register a new user, then log in
5. Open a second browser (or incognito window), register/login as a different user, and send messages back and forth to confirm real-time delivery

## Known Limitations
- The WebSocket endpoint (`/ws`) itself is not JWT-protected — STOMP's handshake doesn't carry standard `Authorization` headers cleanly, so only REST endpoints (`/api/messages`) currently enforce authentication
- Runs on `localhost` only — to chat with someone outside your local network, expose the app via ngrok or deploy it to a cloud host
