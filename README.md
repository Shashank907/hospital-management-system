# Hospital Management System

A backend **Hospital Management System** built using Java and Spring Boot. The project provides REST APIs for managing patients, doctors, appointments, departments, insurance, and user authentication.

## Tech Stack

* Java
* Spring Boot
* Spring Security
* JWT
* Spring Data JPA
* Hibernate
* MySQL
* Maven
* Lombok
* ModelMapper
* Postman

## Features

* User signup and login
* JWT-based authentication
* Patient management
* Doctor management
* Appointment management
* Department management
* Insurance management
* DTO-based API responses
* ModelMapper for DTO mapping
* Pagination
* Custom JPQL and native SQL queries
* RESTful APIs

## Authentication

The application uses **Spring Security and JWT** for authentication.

After login, the server returns a JWT token. The token must be included when accessing protected endpoints.

```http
Authorization: Bearer <your-jwt-token>
```

## API Endpoints

### Authentication

| Method | Endpoint       | Description           |
| ------ | -------------- | --------------------- |
| POST   | `/auth/signup` | Register a new user   |
| POST   | `/auth/login`  | Login and receive JWT |

### Public

| Method | Endpoint          | Description     |
| ------ | ----------------- | --------------- |
| GET    | `/public/doctors` | Get all doctors |

### Patients

| Method | Endpoint                 | Description         |
| ------ | ------------------------ | ------------------- |
| GET    | `/patients/profile`      | Get patient profile |
| POST   | `/patients/appointments` | Create appointment  |

### Doctors

| Method | Endpoint                           | Description               |
| ------ | ---------------------------------- | ------------------------- |
| GET    | `/doctors/{doctorId}/appointments` | Get doctor's appointments |

### Admin

| Method | Endpoint          | Description  |
| ------ | ----------------- | ------------ |
| GET    | `/admin/patients` | Get patients |
| GET    | `/admin/doctors`  | Get doctors  |

## Project Structure

```text
hospitalSystem/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/shashankcdr/hospitalSystem/
│   │   │       ├── config/
│   │   │       ├── controller/
│   │   │       ├── dto/
│   │   │       ├── entity/
│   │   │       ├── repository/
│   │   │       ├── security/
│   │   │       └── service/
│   │   └── resources/
│   │       ├── application.properties
│   │       └── data.sql
│   └── test/
├── pom.xml
└── README.md
```

## Setup

### 1. Clone the repository

```bash
git clone https://github.com/Shashank907/hospital-management-system.git
```

### 2. Create the database

```sql
CREATE DATABASE hospital_db;
```

### 3. Configure the application

Update your local database credentials and JWT secret in:

```text
src/main/resources/application.properties
```

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/hospital_db
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD

jwt.secretKey=YOUR_SECRET_KEY
```

> Do not commit real passwords or secret keys to GitHub.

### 4. Run the application

Using Maven:

```bash
mvn spring-boot:run
```

Or run `HospitalSystemApplication.java` from IntelliJ IDEA.

The application runs on:

```text
http://localhost:8080
```

## Testing

The APIs can be tested using **Postman**.

Typical authentication flow:

```text
Signup → Login → Get JWT → Send JWT → Access Protected APIs
```

## Future Improvements

* Role-based authorization
* Refresh token support
* Bean validation
* Global exception handling
* Swagger/OpenAPI documentation
* Docker support
* Improved test coverage
* Cloud deployment
* React frontend

## Author

**Shashank Shekhar Pandey**

Java Backend Developer
Java • Spring Boot • Spring Security • REST APIs • SQL
