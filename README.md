# 🏥 Hospital Management System

A backend REST API for managing hospital operations such as patients, doctors, appointments, authentication, and role-based access control.

Built with **Java, Spring Boot, Spring Security, JWT, Spring Data JPA, Hibernate, and MySQL**, following a layered architecture and production-oriented backend practices.

---

## 🚀 Features

### 🔐 Authentication & Authorization

* User signup and login
* JWT-based authentication
* Stateless authentication
* Spring Security integration
* Method-level Role-Based Access Control (RBAC)
* Role-based authorization using `@PreAuthorize`
* Supported roles:

    * `ADMIN`
    * `DOCTOR`
    * `PATIENT`
    * `RECEPTIONIST`

Example:

```java
@PreAuthorize("hasRole('PATIENT')")
```

Multiple roles:

```java
@PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'RECEPTIONIST')")
```

Authentication is handled through a custom JWT filter.

---

### 👨‍⚕️ Doctor Management

* Doctor management
* Doctor specialization
* Doctor information
* Doctor appointment management
* Reassign appointments to doctors

---

### 🧑‍⚕️ Patient Management

* Create patient profile
* Get logged-in patient's profile
* Update patient profile
* Get patient's appointments
* Patient request validation
* Patient-specific authorization

---

### 📅 Appointment Management

* Create appointments
* Get appointment by ID
* Get all appointments
* Update appointments
* Update appointment status
* Get doctor's appointments
* Get logged-in patient's appointments
* Reassign appointments to another doctor
* Appointment status transition validation

Supported appointment statuses include:

```text
SCHEDULED
CONFIRMED
COMPLETED
CANCELLED
```

Invalid appointment status transitions are rejected by the backend.

---

## 📊 Pagination & Sorting

Appointment APIs support pagination and sorting using Spring Data JPA.

### Pagination

```http
GET /appointments?page=0&size=10
```

### Ascending sorting

```http
GET /appointments?page=0&size=10&sortBy=appointmentTime&direction=asc
```

### Descending sorting

```http
GET /appointments?page=0&size=10&sortBy=appointmentTime&direction=desc
```

The API returns Spring Data's `Page` response containing information such as:

* Current page
* Page size
* Total elements
* Total pages
* Content

---

## ✅ Request Validation

The application uses **Jakarta Bean Validation** for validating incoming request data.

Validation annotations currently used include:

* `@NotBlank`
* `@NotNull`
* `@Email`
* `@Size`
* `@Past`
* `@Future`

Example:

```java
@NotBlank(message = "Name is required")
private String name;
```

Invalid requests return a structured `400 Bad Request` response.

---

## ⚠️ Global Exception Handling

Centralized exception handling is implemented using:

```java
@RestControllerAdvice
```

Currently handled exceptions include:

* Validation errors
* Resource not found
* Invalid appointment state transitions

Example error response:

```json
{
  "status": 404,
  "message": "Appointment not found with ID: 999",
  "timestamp": "2026-09-11T01:27:00",
  "errors": null
}
```

---

## 🏗️ Project Architecture

The project follows a layered architecture:

```text
com.shashankcdr.hospitalSystem
│
├── controller
│   ├── AppointmentController
│   ├── PatientController
│   ├── DoctorController
│   └── ...
│
├── service
│   ├── AppointmentService
│   ├── PatientService
│   ├── DoctorService
│   └── ...
│
├── repository
│   ├── AppointmentRepository
│   ├── PatientRepository
│   ├── DoctorRepository
│   └── ...
│
├── entity
│   ├── User
│   ├── Patient
│   ├── Doctor
│   ├── Appointment
│   ├── Department
│   └── Insurance
│
├── dto
│   ├── CreateAppointmentRequestDto
│   ├── UpdateAppointmentRequestDto
│   ├── AppointmentResponseDto
│   ├── CreatePatientRequestDto
│   └── ...
│
├── security
│   ├── JwtAuthFilter
│   ├── WebSecurityConfig
│   └── ...
│
├── exception
│   ├── GlobalExceptionHandler
│   ├── ErrorResponse
│   └── ResourceNotFoundException
│
└── HospitalSystemApplication
```

---

## 🔐 Security Flow

The application uses stateless JWT authentication.

```text
Client
   │
   ▼
POST /auth/login
   │
   ▼
Username + Password
   │
   ▼
Spring Security Authentication
   │
   ▼
JWT Token
   │
   ▼
Client sends:
Authorization: Bearer <JWT>
   │
   ▼
JwtAuthFilter
   │
   ▼
JWT validation
   │
   ▼
Authentication
   │
   ▼
Method-level authorization
   │
   ▼
@PreAuthorize
   │
   ▼
Controller
```

Public endpoints are configured using:

```java
.requestMatchers("/public/**", "/auth/**").permitAll()
```

All other requests require authentication:

```java
.anyRequest().authenticated()
```

The application uses:

```java
SessionCreationPolicy.STATELESS
```

and CSRF protection is disabled because the API uses stateless JWT authentication.

---

## 🗄️ Database

The application uses **MySQL** with **Spring Data JPA**, **Hibernate**, and JPA entity relationships.

Main entities include:

```text
User
 │
 ├── Patient
 │
 └── Role

Doctor
 │
 └── Appointment

Patient
 │
 └── Appointment

Department
Insurance
Appointment
```

---

## 🛠️ Tech Stack

| Technology         | Usage                          |
| ------------------ | ------------------------------ |
| Java 24            | Programming Language           |
| Spring Boot 4.1.1  | Backend Framework              |
| Spring Security    | Authentication & Authorization |
| JWT                | Authentication                 |
| Spring Data JPA    | Data Access                    |
| Hibernate          | ORM                            |
| MySQL              | Database                       |
| Jakarta Validation | Request Validation             |
| Lombok             | Boilerplate Reduction          |
| ModelMapper        | DTO ↔ Entity Mapping           |
| Maven              | Build & Dependency Management  |
| Git & GitHub       | Version Control                |
| Postman            | API Testing                    |

---

## 📡 API Endpoints

### Authentication

| Method | Endpoint       | Description                        |
| ------ | -------------- | ---------------------------------- |
| POST   | `/auth/signup` | Register a user                    |
| POST   | `/auth/login`  | Authenticate user and generate JWT |

---

### Patient

| Method | Endpoint                    | Description                        |
| ------ | --------------------------- | ---------------------------------- |
| POST   | `/patients/profile`         | Create patient profile             |
| GET    | `/patients/me`              | Get logged-in patient's profile    |
| PUT    | `/patients/me`              | Update logged-in patient's profile |
| GET    | `/patients/me/appointments` | Get patient's appointments         |

---

### Appointments

| Method | Endpoint                    | Description                              |
| ------ | --------------------------- | ---------------------------------------- |
| POST   | `/appointments`             | Create appointment                       |
| GET    | `/appointments/{id}`        | Get appointment by ID                    |
| GET    | `/appointments`             | Get appointments with pagination/sorting |
| PUT    | `/appointments/{id}`        | Update appointment                       |
| PATCH  | `/appointments/{id}/status` | Update appointment status                |

---

## 🧪 API Testing

The APIs are tested using **Postman**.

Recommended authentication flow:

```text
1. Signup
      ↓
2. Login
      ↓
3. Receive JWT
      ↓
4. Add JWT as Bearer Token
      ↓
5. Access protected APIs
      ↓
6. Test role-based authorization
```

Example authorization header:

```http
Authorization: Bearer <JWT_TOKEN>
```

---

## ⚙️ Configuration

Create the MySQL database:

```sql
CREATE DATABASE hospital_db;
```

Configure your database credentials in:

```text
src/main/resources/application.properties
```

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/hospital_db
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

> Replace `YOUR_PASSWORD` with your local MySQL password.

---

## ▶️ Running the Application

### 1. Clone the repository

```bash
git clone <YOUR_GITHUB_REPOSITORY_URL>
```

### 2. Open the project

Open the project in IntelliJ IDEA or another Java IDE.

### 3. Configure MySQL

Create the `hospital_db` database and configure your MySQL credentials.

### 4. Build the project

```bash
mvn clean install
```

### 5. Run the application

```bash
mvn spring-boot:run
```

The application runs on:

```text
http://localhost:8080
```

---

## 📌 Production-Oriented Development

This project is being developed with real-world backend development practices, including:

* RESTful API design
* Layered architecture
* DTO-based API design
* JWT authentication
* Role-Based Access Control
* Request validation
* Global exception handling
* Pagination
* Sorting
* Transaction management
* JPA/Hibernate relationships
* Centralized error responses
* Git version control

---

## 🔮 Future Improvements

The following features are planned as the project continues to evolve:

* [ ] Appointment conflict checking
* [ ] Safe pagination and sorting validation
* [ ] Standard API response wrapper
* [ ] Audit fields (`createdAt`, `updatedAt`)
* [ ] Logging
* [ ] Swagger/OpenAPI documentation
* [ ] Password reset
* [ ] Email notifications
* [ ] Appointment reminders
* [ ] Payment integration
* [ ] Automated testing
* [ ] React frontend
* [ ] Docker containerization
* [ ] CI/CD pipeline
* [ ] Cloud deployment

---

## 🎯 Project Goal

The goal of this project is to build a **production-oriented Hospital Management System** while gaining practical experience in Java backend development and modern Spring Boot technologies.

Development roadmap:

```text
Java
  ↓
Spring Boot
  ↓
REST APIs
  ↓
Spring Security
  ↓
JWT Authentication
  ↓
RBAC
  ↓
Validation
  ↓
Exception Handling
  ↓
Pagination & Sorting
  ↓
Production Features
  ↓
Testing
  ↓
Swagger/OpenAPI
  ↓
React Frontend
  ↓
Docker
  ↓
Cloud Deployment
```

---

## 👨‍💻 Author

**Shashank Pandey**

B.Tech Computer Science & Engineering — 2026

### Interests

* Java Backend Development
* Spring Boot
* REST APIs
* Spring Security
* Full-Stack Development
* Data Structures & Algorithms

---

⭐ If you find this project useful, consider giving the repository a star.
