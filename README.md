# API Rate Limiter + Gateway Backend Starter

This starter project gives you a clean base for a resume-ready **API Gateway + Rate Limiter** backend.

## Project structure

- `api-gateway-service` -> Spring Cloud Gateway app
- `demo-backend-service` -> small backend service to test routing
- `docker-compose.yml` -> runs Redis locally

## What this project already does

- routes `/demo/**` from gateway to the backend service
- strips `/demo` before forwarding the request
- applies Redis-backed rate limiting at the gateway level
- returns **429 Too Many Requests** when the request limit is exceeded

## Current flow

Client -> `http://localhost:8080/demo/hello`

Gateway -> checks rate limit using Redis

Gateway -> forwards to `http://localhost:8081/hello`

Backend -> returns JSON response

## IntelliJ + Windows setup

### 1. Extract the ZIP
Extract this ZIP anywhere, for example:

`C:\Users\YourName\Desktop\api-rate-limiter-gateway-starter`

### 2. Open in IntelliJ IDEA
- Open IntelliJ IDEA
- Click **Open**
- Select the extracted folder
- Wait for Maven import to finish

### 3. Make sure you have
- JDK 21 configured in IntelliJ
- Docker Desktop running
- Internet connected for first Maven dependency download

## How to run

### Start Redis
Open terminal in the project root and run:

```bash
docker compose up -d
```

### Run demo backend service
Open the class:

`demo-backend-service/src/main/java/com/example/demobackend/DemoBackendApplication.java`

Run `DemoBackendApplication`

### Run gateway service
Open the class:

`api-gateway-service/src/main/java/com/example/apigateway/ApiGatewayApplication.java`

Run `ApiGatewayApplication`

## Test URLs

### Direct backend
```http
GET http://localhost:8081/hello
```

### Through gateway
```http
GET http://localhost:8080/demo/hello
```

### Another backend endpoint through gateway
```http
GET http://localhost:8080/demo/health-check
```

## Rate limit behavior

Current config:
- `replenishRate: 3`
- `burstCapacity: 5`
- `requestedTokens: 1`

This means the gateway allows a short burst and then starts returning **429** if too many requests arrive quickly.

## Easy Postman test
Send `GET http://localhost:8080/demo/hello` many times quickly.

You should first get `200 OK` and then some `429 Too Many Requests` responses.

## Next build steps

We will implement these one by one later:
1. custom error body for 429
2. per-user/API-key rate limiting
3. JWT auth integration
4. route config cleanup
5. logging and monitoring
6. Dockerfiles
7. README polishing
8. resume bullet points
