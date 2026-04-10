# 🚀 API Rate Limiter + Gateway Backend

## 📌 Overview

This project is a **backend API Gateway** built using **Spring Boot** and **Spring Cloud Gateway**.
It routes incoming client requests to backend services and enforces **rate limiting using Redis** to prevent API abuse.

---

## ⚙️ Features

* 🔁 API routing using **Spring Cloud Gateway**
* 🚦 Redis-based **rate limiting (token bucket algorithm)**
* 👤 **Client-based throttling** using custom `KeyResolver` (`client-id` header)
* ❌ Automatic **HTTP 429 (Too Many Requests)** for exceeded limits
* 📊 **Actuator endpoints** for monitoring and route inspection
* 📝 **Request logging filter** for debugging and tracking
* 🐳 Redis setup using **Docker**

---

## 🧰 Tech Stack

* Java 21
* Spring Boot
* Spring Cloud Gateway
* Redis
* Maven
* Docker
* IntelliJ IDEA

---

## 📁 Project Structure

```
api-rate-limiter-gateway-starter/
│
├── api-gateway-service/        # API Gateway (rate limiting + routing)
├── demo-backend-service/       # Sample backend service
├── docker-compose.yml          # Redis container setup
```

---

## ▶️ How to Run

### 1️⃣ Start Redis (Docker)

```bash
docker compose up -d
```

### 2️⃣ Run Backend Service

Run in IntelliJ:

```
DemoBackendApplication
```

### 3️⃣ Run API Gateway

Run in IntelliJ:

```
ApiGatewayApplication
```

---

## 🌐 API Endpoints

### Backend (Direct)

```
http://localhost:8081/hello
```

### Gateway (via routing)

```
http://localhost:8082/demo/hello
```

---

## 🚦 Rate Limiting

* Implemented using **Spring Cloud Gateway + Redis**
* Token Bucket Configuration:

    * `replenishRate`: 1 request/sec
    * `burstCapacity`: 2 requests

### 👤 Client-based Rate Limiting

Use header:

```
client-id: user1
```

Each client gets a **separate rate limit bucket**.

---

## 🧪 Testing

### Browser

```
http://localhost:8082/demo/hello
```

### Postman

Add header:

```
client-id: user1
```

---

## 📊 Actuator Monitoring

### Health Check

```
http://localhost:8082/actuator/health
```

### Gateway Routes

```
http://localhost:8082/actuator/gateway/routes
```

---

## 📝 Logging

The gateway logs:

* request method
* request path
* client-id
* response status

Example:

```
Incoming request -> method: GET, path: /demo/hello, client-id: user1
Outgoing response -> status: 200, path: /demo/hello, client-id: user1
```

---

## 🎯 Key Learnings

* API Gateway architecture
* Redis-based rate limiting
* Request filtering in Spring Cloud Gateway
* Client-aware throttling using headers
* Monitoring using Actuator

---

## 🔮 Future Improvements

* JWT Authentication
* Role-based access control
* Multiple backend services
* API usage analytics
* Distributed tracing (Zipkin)

---

## 👨‍💻 Author

**Shubhdeep Varshney**
Email -shubhdeepvarshney02@gmail.com