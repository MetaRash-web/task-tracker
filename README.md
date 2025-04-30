# Task Tracker

Task Tracker is an enterprise-grade, modular task management system designed with a microservice architecture. The platform leverages modern Java, Spring Boot, Kafka, WebSockets, and Docker technologies to provide robust, scalable, and real-time task tracking and notification capabilities.

---

## 🚀 Features

- 🔐 **JWT-based Authentication**: Secure stateless authentication and authorization.
- 📝 **Task CRUD Operations**: Endpoints for creating, updating, deleting, and retrieving tasks, with support for filtering and pagination.
- 📡 **Real-Time Notifications**: WebSocket and STOMP integration for instant UI updates on task changes and deadlines.
- ⏰ **Automated Scheduling**: Spring Scheduling and dedicated microservices for overdue task notifications and email reminders.
- 📨 **Event-Driven Architecture**: Kafka-based asynchronous message processing for decoupled service communication.
- 🛡️ **Role-Based Security**: Password hashing (BCrypt), CORS protection, rate limiting.
- 🔗 **REST API**: RESTful endpoints, DTO mapping, error handling, and API versioning.
- 🚦 **Rate Limiting**: Integrated with Redis and Bucket4j for API abuse prevention.
- 🐳 **Containerization**: Dockerized deployment, multi-module Maven build, and Docker Compose orchestration.

---

## 🛠️ Technology Stack

- **Backend**: Java 17, Spring Boot, Spring Security, Spring Data JPA, Hibernate, Kafka, WebSocket (STOMP), Lombok, Spring Scheduling
- **Frontend**: Next.js (React), TypeScript, STOMP.js, CSS
- **Microservices**: Spring Boot (Scheduler, Email Sender)
- **Database**: MySQL
- **Caching & Rate Limiting**: Redis, Bucket4j
- **DevOps**: Docker, Docker Compose, Maven (multi-module), npm

---

## 🏗️ Architecture Overview

- **Monorepo Structure**: Separate modules for backend, frontend, scheduler, email sender, and shared code (common)
- **Microservices**: Backend, scheduler, and email sender as independent Spring Boot services
- **Event-Driven**: Kafka for asynchronous communication between services
- **Real-Time**: WebSocket endpoints for authenticated user notifications
- **Security**: JWT, BCrypt, CORS, rate limiting

---

## 🖥️ Local Development

### Prerequisites
- Java 17+
- Node.js 18+
- Docker & Docker Compose
- MySQL (or use Docker)

### Environment Variables
Environment variables are required for each module. Example variables:
- `DB_HOST`, `DB_PORT`, `DB_USER`, `DB_PASS`, `JWT_SECRET`, `KAFKA_BROKER`, etc.

### Quick Start
```sh
make build
# or

docker-compose up --build
```

### Manual Start
- **Backend**: `cd task-tracker-backend && ./mvnw spring-boot:run`
- **Frontend**: `cd task-tracker-frontend && npm install && npm run dev`
- **Scheduler**: `cd task-tracker-scheduler && ./mvnw spring-boot:run`
- **Email Sender**: `cd task-tracker-email-sender && ./mvnw spring-boot:run`

---

## 🔗 API & WebSocket Usage

- **REST API**: See `TaskController`, `AuthController`, etc. for endpoint structure.
- **WebSocket**: Connect to `/ws`, subscribe to `/user/queue/notifications` for real-time updates.

---

## 🧑‍💻 Skills Demonstrated
- Java Core (OOP, collections, generics, concurrency, exception handling)
- Spring Boot (REST, Security, JPA, Scheduling, AOP, validation)
- DTO mapping and separation of concerns
- Kafka, event-driven and asynchronous message processing
- WebSocket, STOMP, real-time frontend integration
- SQL, MySQL, JPA/Hibernate
- Docker, Docker Compose, multi-module Maven
- Redis (caching, rate limiting)
- API versioning, error handling, validation
- Spring Scheduling (automated tasks, notifications)
- Authentication, authorization, rate limiting
- Modular architecture, logging
- Git, GitHub, CI/CD basics

---

## 📬 Contact
- [Telegram](https://t.me/metara5h)

---