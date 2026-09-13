# 🏥 Hospital Management System

A production-oriented **Hospital Management System backend** built using **Java, Spring Boot, Spring Security, JWT, MySQL, Swagger/OpenAPI, and Docker**.

The system provides REST APIs for managing patients, doctors, appointments, departments, insurance, prescriptions, billing, payments, and user authentication with role-based access control.

---

## 🚀 Features

### 🔐 Authentication & Security

* User registration and login
* JWT-based authentication
* Password encryption using BCrypt
* Role-Based Access Control (RBAC)
* Protected REST endpoints
* Stateless Spring Security configuration
* Custom JWT authentication filter
* Environment-based configuration for sensitive values

### 👥 User & Role Management

* User management
* Role-based authorization
* Admin-controlled role updates
* Supported roles:

    * `ADMIN`
    * `DOCTOR`
    * `PATIENT`

### 🧑‍⚕️ Doctor Management

* Create and manage doctors
* Doctor information management
* Department association
* Doctor-specific operations

### 🧑‍🦽 Patient Management

* Patient registration
* Patient profile management
* Patient appointment management
* Insurance association
* User-to-patient mapping

### 📅 Appointment Management

* Create appointments
* Update appointments
* Appointment status management
* Doctor-patient appointment relationship
* Role-based appointment access

### 🏥 Department Management

* Department management
* Doctor-department relationships
* Hospital department operations

### 💊 Prescription Management

* Create prescriptions
* Prescription medicine management
* Prescription retrieval
* Doctor-patient prescription workflow

### 🧾 Billing

* Create patient bills
* Bill management
* Bill payment status tracking
* Patient billing workflow

### 💳 Payment Management

* Payment creation
* Payment status tracking
* Multiple payment methods
* Bill-payment relationship

### 🛡️ Insurance

* Patient insurance management
* Insurance information retrieval
* Patient-insurance relationship

### ⚠️ Production-Oriented Features

* Global exception handling
* Custom exceptions
* DTO-based API design
* Request validation
* Centralized error responses
* Swagger/OpenAPI documentation
* Docker containerization
* Docker Compose
* MySQL persistent volume
* Environment variable configuration

---

## 🛠️ Tech Stack

| Technology        | Purpose                        |
| ----------------- | ------------------------------ |
| Java 21           | Programming language           |
| Spring Boot       | Backend framework              |
| Spring Security   | Authentication & authorization |
| JWT               | Stateless authentication       |
| Spring Data JPA   | Database access                |
| Hibernate         | ORM                            |
| MySQL 8           | Relational database            |
| Maven             | Dependency management & build  |
| Lombok            | Boilerplate reduction          |
| Swagger / OpenAPI | API documentation              |
| Docker            | Containerization               |
| Docker Compose    | Multi-container orchestration  |
| Git & GitHub      | Version control                |

---

## 🏗️ Architecture

```text
                    ┌──────────────────────┐
                    │      Client          │
                    │ Postman / Swagger /  │
                    │    React Frontend    │
                    └──────────┬───────────┘
                               │
                               ▼
                    ┌──────────────────────┐
                    │   Spring Boot API    │
                    │                      │
                    │ Controllers          │
                    │ Services             │
                    │ Repositories          │
                    │ DTOs                  │
                    └──────────┬───────────┘
                               │
                  ┌────────────┴────────────┐
                  │                         │
                  ▼                         ▼
        ┌─────────────────┐       ┌─────────────────┐
        │ Spring Security │       │ Global Exception│
        │ JWT + RBAC      │       │ Handler         │
        └─────────────────┘       └─────────────────┘
                               │
                               ▼
                    ┌──────────────────────┐
                    │       MySQL 8        │
                    │    hospital_db       │
                    └──────────────────────┘
```

---

## 📂 Project Structure

```text
hospital-management-system/
│
├── hospitalSystem/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/shashankcdr/hospitalSystem/
│   │   │   │
│   │   │   │       ├── config/
│   │   │   │       ├── controller/
│   │   │   │       ├── dto/
│   │   │   │       ├── entity/
│   │   │   │       ├── exception/
│   │   │   │       ├── repository/
│   │   │   │       ├── security/
│   │   │   │       └── service/
│   │   │   │
│   │   │   └── resources/
│   │   │       ├── application.properties
│   │   │       └── data.sql
│   │   │
│   │   ├── Dockerfile
│   │   ├── docker-compose.yml
│   │   └── pom.xml
│   │
│   ├── .gitignore
│   └── README.md
```

---

## 📡 API Modules

The application currently provides approximately **28 REST APIs** across the following modules:

```text
Authentication
├── Signup
└── Login

Users / Admin
├── User management
└── Role management

Patients
├── Patient management
├── Profile
└── Patient appointments

Doctors
├── Doctor management
└── Doctor operations

Departments
└── Department management

Appointments
├── Create
├── Update
├── Status management
└── Retrieval

Prescriptions
├── Create prescription
├── Medicines
└── Retrieval

Billing
├── Create bill
└── Bill management

Payments
├── Create payment
└── Payment management

Insurance
└── Insurance management
```

---

## 🔐 Authentication Flow

The application uses **JWT-based stateless authentication**.

```text
User
 │
 │ Login
 ▼
/auth/login
 │
 ▼
Spring Security
 │
 ▼
AuthenticationManager
 │
 ▼
JWT Generated
 │
 ▼
Client
 │
 │ Authorization: Bearer <JWT>
 ▼
JwtAuthFilter
 │
 ▼
Validate Token
 │
 ▼
Load User & Role
 │
 ▼
RBAC Authorization
 │
 ▼
Controller
```

Example:

```http
Authorization: Bearer <your-jwt-token>
```

---

## 👮 Role-Based Access Control

Access to protected resources is controlled using Spring Security and roles.

Example:

```java
@PreAuthorize("hasRole('ADMIN')")
```

This ensures that sensitive administrative operations cannot be accessed by unauthorized users.

---

## 📖 API Documentation

Swagger/OpenAPI is integrated into the project for interactive API documentation and testing.

After starting the application, open:

```text
http://localhost:8080/api/swagger-ui.html
```

OpenAPI specification:

```text
http://localhost:8080/api/v3/api-docs
```

You can use Swagger to:

* Explore APIs
* View request/response models
* Test endpoints
* Send authenticated requests
* Understand API contracts

---

## 🐳 Running with Docker

The project is containerized using Docker and Docker Compose.

### Prerequisites

Install:

* Docker Desktop
* Git

No local MySQL installation is required when using Docker Compose.

---

### 1. Clone the repository

```bash
git clone https://github.com/Shashank907/hospital-management-system.git
```

```bash
cd hospital-management-system
```

---

### 2. Configure environment variables

Create a `.env` file inside the project root.

```env
MYSQL_ROOT_PASSWORD=your_mysql_password
JWT_SECRETKEY=your_jwt_secret_key
```

> Never commit `.env` to GitHub.

---

### 3. Start the application

```bash
docker compose up -d
```

Docker Compose starts:

```text
hospital-mysql
hospital-system
```

---

### 4. Check containers

```bash
docker compose ps
```

Expected services:

```text
hospital-mysql
hospital-system
```

---

### 5. View application logs

```bash
docker compose logs -f app
```

---

### 6. Stop the application

```bash
docker compose down
```

The MySQL volume is preserved, so database data is not removed.

> Do not use `docker compose down -v` unless you intentionally want to delete the database volume.

---

## 🐳 Docker Architecture

```text
                 Docker Compose
                       │
          ┌────────────┴────────────┐
          │                         │
          ▼                         ▼
 ┌─────────────────┐       ┌──────────────────┐
 │ Spring Boot App │       │     MySQL 8      │
 │                 │       │                  │
 │ Port: 8080      │──────▶│ Port: 3306       │
 │                 │  db   │                  │
 └─────────────────┘       └──────────────────┘
          │                         │
          └────────── Docker ───────┘
                    Network

Host:
8080 → Spring Boot
3307 → MySQL
```

The Spring Boot application communicates with MySQL using the Docker Compose service name:

```text
jdbc:mysql://db:3306/hospital_db
```

---

## ⚙️ Local Development Without Docker

If you want to run the application directly with Java and a local MySQL installation:

### Configure MySQL

Create:

```text
hospital_db
```

Then configure your database credentials in `application.properties` or environment variables.

### Build the project

Windows:

```powershell
.\mvnw.cmd clean package "-Dmaven.test.skip=true"
```

### Run the application

```powershell
.\mvnw.cmd spring-boot:run
```

The application runs on:

```text
http://localhost:8080/api
```

---

## 🧪 Testing APIs

The APIs can be tested using:

* Swagger UI
* Postman
* REST clients

Recommended authentication workflow:

```text
1. Signup
      ↓
2. Login
      ↓
3. Copy JWT
      ↓
4. Add Bearer Token
      ↓
5. Access protected APIs
```

---

## 🗄️ Database

Database:

```text
MySQL 8
```

Database name:

```text
hospital_db
```

The application uses:

* Spring Data JPA
* Hibernate
* Entity relationships
* Repository pattern
* Transactional service operations

Docker uses a persistent volume:

```text
hospital-mysql-data
```

This allows database data to survive container recreation.

---

## 🛡️ Error Handling

The application uses centralized exception handling with a global exception handler.

Examples include:

```text
ResourceNotFoundException
ResourceAlreadyExistsException
Validation errors
Authentication errors
Authorization errors
```

API errors are returned using a consistent response structure.

---

## 📦 Main Domain Entities

```text
User
 │
 ├── Patient
 │     └── Insurance
 │
 └── Role

Doctor
 │
 ├── Department
 └── Appointment

Patient
 │
 ├── Appointment
 ├── Prescription
 ├── Bill
 ├── Payment
 └── Insurance
```

---

## 🔄 Development Workflow

The project uses Git for version control.

Main development branch:

```text
main
```

Production-focused development was performed on:

```text
production-level
```

Typical workflow:

```bash
git switch production-level

# Make changes

git add .

git commit -m "Your commit message"

git push origin production-level
```

After completing and testing production-level changes, they can be merged into `main`.

---

## 📌 Production-Oriented Improvements

The project was designed with production concepts rather than only basic CRUD operations.

Implemented:

* JWT authentication
* Role-based authorization
* DTO-based API layer
* Request validation
* Global exception handling
* Custom exceptions
* Swagger/OpenAPI
* Environment variables
* Docker
* Docker Compose
* MySQL persistent storage
* Modular service/repository architecture
* Billing and payment workflow
* Prescription management

---

## 🚧 Future Improvements

Planned improvements include:

* React frontend
* Cloud deployment
* CI/CD pipeline
* Automated tests
* Redis caching
* Monitoring and logging
* Email/notification service
* Pagination and sorting
* Rate limiting
* Production database migration strategy

---

## 🎯 Learning Outcomes

Through this project, I practiced:

* Java backend development
* Spring Boot REST API development
* Spring Security
* JWT authentication
* RBAC
* Spring Data JPA
* Hibernate
* MySQL
* DTO design
* Exception handling
* API validation
* Swagger/OpenAPI
* Docker
* Docker Compose
* Git/GitHub
* Production-oriented backend architecture

---

## 👨‍💻 Author

**Shashank Pandey**

Java Backend Developer | Spring Boot | MySQL | Docker

### Profiles

* GitHub: `https://github.com/Shashank907`
* LeetCode: `https://leetcode.com/u/Shekharr21/`

---

## ⭐ Project

If you find this project useful, consider giving the repository a ⭐ on GitHub.

---

## 📄 License

This project is created for learning, portfolio, and educational purposes.
