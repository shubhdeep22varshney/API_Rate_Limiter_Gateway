## 📬 Postman Collection

You can test APIs using the Postman collection:

- Import `postman_collection.json` into Postman
- Set variables:
    - base_url = http://localhost:8082
    - token = Bearer <your_jwt_token>

## 🔑 Sample Requests

### Get Token (Keycloak)
POST http://localhost:8180/realms/api-gateway-realm/protocol/openid-connect/token

### Access API
GET http://localhost:8082/demo/hello

Header:
Authorization: Bearer <token>