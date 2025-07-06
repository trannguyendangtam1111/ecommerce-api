# 🛒 E-Commerce REST API

A simple yet complete **E-Commerce REST API** built with **Java Spring Boot**, supporting core features such as **user registration, login, role-based authorization, product management, cart and order placement**. Secured with **JWT** and integrated with **Swagger UI** for easy testing.

---

## 🚀 Tech Stack

- **Java 17+**
- **Spring Boot 3**
- **Spring Security + JWT**
- **Spring Data JPA + Hibernate**
- **PostgreSQL**
- **Swagger UI (OpenAPI)**
- **Lombok**
- **Maven**

---

## 📦 Main Features

| Feature                | Description |
|------------------------|-------------|
| ✅ Register / Login     | Create user accounts and receive JWT token |
| 🔐 Role-based access    | Roles: ROLE_ADMIN and ROLE_CUSTOMER |
| 🛍 Product Management   | CRUD products, only accessible by ADMIN |
| 🛒 Shopping Cart        | CUSTOMER can add/view/remove cart items |
| 📦 Order Placement      | CUSTOMER can place order from current cart |
| 📜 View Orders          | CUSTOMER can view their own orders |
| 🔍 Search + Pagination  | Search products by name, price, and paginate |
| 📄 Swagger UI           | Interactive API documentation & testing |

---

## 🔐 API Access Control

| API Endpoint                    | Required Role     |
|---------------------------------|-------------------|
| `POST /api/auth/register`       | Public            |
| `POST /api/auth/login`          | Public            |
| `GET /api/products`             | Public            |
| `POST/PUT/DELETE /api/products` | ROLE_ADMIN        |
| `GET /api/cart`                 | ROLE_CUSTOMER     |
| `POST /api/cart`                | ROLE_CUSTOMER     |
| `POST /api/orders`              | ROLE_CUSTOMER     |
| `GET /api/orders/me`            | ROLE_CUSTOMER     |

---

## 📂 Project Structure

```
src/main/java/com/cyan/shop/
│
├── config/               # Swagger & project config
├── controller/           # API endpoints
├── dto/                  # Request/Response objects
├── entity/               # JPA entities
├── repository/           # JpaRepository interfaces
├── security/             # JWT filter, security config
└── service/              # Business logic
```

---

## ▶️ How to Run

### 1. Configure the database
Create a PostgreSQL database named `ecommerce_db`, and update `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/ecommerce_db
spring.datasource.username=postgres
spring.datasource.password=your_password
```

### 2. Start the API
```bash
./mvnw spring-boot:run
```

### 3. Open Swagger UI
Access in your browser:
```
http://localhost:8080/swagger-ui/index.html
```

---

## 📌 Notes

- Default role when registering is `ROLE_CUSTOMER`
- Cart data will be cleared after placing an order
- All APIs are protected by JWT
- Exception handling is implemented for clean REST errors

---

## 👤 Author

- **Name:** Cyan Tran
- **Role:** Java Backend Developer (Fresher)
- **Email:** [cyan@gmail.com](mailto:cyan@gmail.com)

---

## 📝 License

MIT License. Free for learning, education, and portfolio use.
