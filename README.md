# 🚀 API Rate Limiter + Gateway Backend (JWT + Keycloak)

## 📌 Overview

This project is a **production-style API Gateway backend** built using **Spring Boot + Spring Cloud Gateway**.
It routes client requests, enforces **rate limiting using Redis**, and secures APIs using **JWT authentication via Keycloak**.

---

## 🏗️ Architecture

```text
Client → API Gateway → Backend Service
           ↓
     JWT Validation
           ↓
        Keycloak
           ↓
         Redis (Rate Limiting)
```

---

## ⚙️ Features

* 🔁 API routing using Spring Cloud Gateway
* 🚦 Redis-based rate limiting (Token Bucket Algorithm)
* 👤 Client-based throttling using `client-id` header
* 🔐 JWT Authentication using Keycloak
* 🛡️ Role-based access control (USER / ADMIN)
* 📊 Actuator endpoints for monitoring
* 📝 Request logging filter
* 🐳 Docker-based Redis & Keycloak setup

---

## 🧰 Tech Stack

* Java 21
* Spring Boot
* Spring Cloud Gateway
* Spring Security (WebFlux)
* Keycloak (Auth Server)
* Redis
* Docker
* Maven
* IntelliJ IDEA

---

## 📁 Project Structure

```text
api-rate-limiter-gateway/
│
├── api-gateway-service/        # Gateway (JWT + Rate Limiting)
├── demo-backend-service/       # Sample backend
├── docker-compose.yml          # Redis
├── postman_collection.json     # API testing
```

---

## ▶️ How to Run

### 1️⃣ Start Redis

```bash
docker compose up -d
```

---

### 2️⃣ Start Keycloak

```bash
docker run -d --name keycloak -p 8180:8080 \
-e KEYCLOAK_ADMIN=admin \
-e KEYCLOAK_ADMIN_PASSWORD=admin \
quay.io/keycloak/keycloak:latest start-dev
```

Open:

```
http://localhost:8180
```

---

### 3️⃣ Setup Keycloak

* Create Realm → `api-gateway-realm`
* Create Client → `api-gateway-client`
* Enable:

  * Client Authentication = ON
  * Direct Access Grants = ON
* Copy **Client Secret**
* Create User:

  * username: `testuser`
  * password: `test123`
* Create Roles:

  * USER
  * ADMIN

---

### 4️⃣ Run Services

* Run `DemoBackendApplication`
* Run `ApiGatewayApplication`

---

## 🔐 JWT Authentication

### Get Token

POST:

```text
http://localhost:8180/realms/api-gateway-realm/protocol/openid-connect/token
```

Body:

```text
grant_type=password
client_id=api-gateway-client
client_secret=<CLIENT_SECRET>
username=testuser
password=test123
```

---

### Use Token

```text
Authorization: Bearer <ACCESS_TOKEN>
```

---

## 🌐 API Endpoints

### Public (Health)

```
GET /actuator/health
```

---

### User API

```
GET /demo/hello
```

---

### Admin API

```
GET /demo/admin-only
```

---

## 🚦 Rate Limiting

* Redis-based
* Config:

  * replenishRate: 1 request/sec
  * burstCapacity: 2

Example:

* 2 requests → allowed
* 3rd → **429 Too Many Requests**

---

## 👤 Role-Based Access

Endpoint         | Access      |
| /demo/hello     | USER, ADMIN |
| /demo/admin-only | ADMIN only  |

---

## 📊 Monitoring

### Health

```
http://localhost:8082/actuator/health
```

### Routes

```
http://localhost:8082/actuator/gateway/routes
```

---

## 📝 Logging

Example:

```
Incoming request → method: GET, path: /demo/hello, client-id: user1
Outgoing response → status: 200
```

---

## 📬 Postman

* Import `postman_collection.json`
* Set:

```text
base_url = http://localhost:8082
token = Bearer <JWT>
```

---

## 🎯 Key Learnings

* API Gateway architecture
* JWT authentication (OAuth2 Resource Server)
* Redis rate limiting
* Role-based authorization
* Microservices security design

---

## 🔮 Future Improvements

* OAuth2 login flow
* API analytics dashboard
* Distributed tracing (Zipkin)
* Circuit breaker (Resilience4j)

---

## 👨‍💻 Author

**Shubhdeep Varshney**
Email - shubhdeepvarshney02@gmail.com
