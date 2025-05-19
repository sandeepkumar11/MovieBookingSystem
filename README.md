# Movie Booking System

A distributed, microservices-based Movie Booking System built using **Spring Boot** and **gRPC**.

## Architecture Overview

This system is modularized into several services, each handling a specific domain. It uses **REST** for public APIs and **gRPC** for internal service-to-service communication.

### Core Microservices

| Service           | Protocol | Description |
|-------------------|----------|-------------|
| **User Service**  | REST     | Handles user registration, login, and profile management |
| **Movie Service** | REST     | Stores and serves movie metadata |
| **Theatre Service** | REST   | Manages theatres, screens, and showtimes |
| **Booking Service** | REST + gRPC | Manages seat selection, booking confirmation, and availability |
| **Payment Service** | gRPC + Webhook | Handles payment initiation, confirmation, and transaction status |
| **Notification Service** | REST / Async | Sends email/SMS confirmations (optional) |

---

## Tech Stack

- Java 17+
- Spring Boot 3.x
- Spring Data JPA
- Spring Security (JWT based)
- gRPC with Protobuf
- MySQL/PostgreSQL (for persistence)
- Redis (for caching, optional)
- RabbitMQ/Kafka (for async events, optional)
- Maven

---

## Project Structure

```plaintext
movie-booking-system/
├── user-service/
├── movie-service/
├── theatre-service/
├── booking-service/
├── payment-service/
├── common-lib/          # Shared DTOs and utils
├── gateway-service/     # Optional API Gateway (Spring Cloud Gateway)
└── README.md

````

---

## Getting Started

### Prerequisites

- Java 17+
- Docker (for DBs and services)
- Maven
- Protobuf compiler (`protoc`)

### Build & Run

#### 1. Clone the repository
```bash
git clone https://github.com/sandeepkumar11/MovieBookingSystem.git
cd MovieBookingSystem
````

#### 2. Generate gRPC code

```bash
mvn clean compile
```

#### 3. Start services

Each service is a Spring Boot app. You can run them using:

```bash
cd user-service
mvn spring-boot:run
```

Or run everything using Docker Compose (if configured).

---

## gRPC Example - Payment Service

Protobuf file: `payment-service/src/main/proto/payment.proto`

```proto
syntax = "proto3";

service PaymentService {
  rpc InitiatePayment(PaymentRequest) returns (PaymentResponse);
  rpc GetPaymentStatus(PaymentStatusRequest) returns (PaymentStatusResponse);
}
```

Run the gRPC server in `payment-service`, and use a gRPC client or another microservice to consume it.

---

## Sample REST APIs

| Endpoint                       | Method | Description                  |
| ------------------------------ | ------ | ---------------------------- |
| `/api/users/register`          | POST   | Register a new user          |
| `/api/movies`                  | GET    | Get list of movies           |
| `/api/theatres/{movieId}`      | GET    | Get theatres showing a movie |
| `/api/bookings/confirm`        | POST   | Confirm a booking            |
| `/api/payments/status/{txnId}` | GET    | Check payment status         |

---

## To Do

* [ ] Add Docker Compose for local orchestration
* [ ] Integrate external payment gateways (e.g., Razorpay/Stripe)
* [ ] Implement rate limiting and retries
* [ ] Add tests (JUnit + Testcontainers)

---

## License

MIT License - See [LICENSE](LICENSE)