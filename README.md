# Config Server

[![Java Version](https://img.shields.io/badge/Java-21-blue.svg)](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Angular](https://img.shields.io/badge/Angular-16+-red.svg)](https://angular.io/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

A robust and centralized solution for managing your application's properties and secrets efficiently and securely.

## Table of Contents

1.  [Description](#description)
2.  [How It Works](#how-it-works)
3.  [Main Features](#main-features)
4.  [Architecture](#architecture)
5.  [Technical Specifications](#technical-specifications)
6.  [Installation and Setup](#installation-and-setup)
7.  [Security](#security)
8.  [REST API](#rest-api)
9.  [Monitoring and Logs](#monitoring-and-logs)
10. [Contributing](#contributing)
11. [License](#license)

## Description

**Config Server** is a robust and centralized solution for application configuration management, designed to provide efficient and secure handling of properties and secrets in an enterprise environment. It enables configuration management through an intuitive user interface and provides secure access through APIs.

## How It Works

1.  **User Authentication & Scope Creation**
    *   Users log into the Config Server application.
    *   They create a new scope, which generates an access key.
    *   The access key, URL, and scope are configured in the client application's `bootstrap.properties`.
    *   Users can add, modify, and delete properties within the created scope.
    *   All activities are logged for auditing purposes.

2.  **Client API Integration**
    *   Client applications include the Config Server driver as a dependency.
    *   During startup, the client reads connection details from `bootstrap.properties`.
    *   The driver connects to the Config Server backend using these credentials.
    *   The client maintains a local cache for improved performance and resilience.
    *   It implements automatic retry and circuit breaker patterns for reliability.

3.  **Configuration Retrieval**
    *   Config Server authenticates the client's request.
    *   It returns all properties associated with the specified scope.
    *   The client API stores the retrieved properties in its environment.
    *   Sensitive properties are automatically decrypted before delivery.
    *   Supports property refresh without an application restart.

## Main Features

-   ✨ Centralized configuration management.
-   🔐 Secure secrets handling with encryption (Jasypt).
-   🔄 Dynamic properties update without service restarts.
-   📊 Complete audit system for all changes.
-   🎯 Scope management for different environments/applications.
-   📝 Detailed change history for properties.
-   🚀 REST API for service integration.
-   👥 JWT-based authentication and authorization system.
-   📱 Modern and responsive user interface (Angular).

## Architecture

The project is structured into three main components:

## Technical Specifications

### Backend (`SB-ConfigServer-Application`)

-   **Framework**: Spring Boot 3.x
-   **Language**: Java 21
-   **Security**: Spring Security with JWT
-   **Database**: Spring Data JPA with PostgreSQL
-   **Build**: Maven

### Frontend (`NG-ConfigServer-UI`)

-   **Framework**: Angular 16+
-   **Language**: TypeScript
-   **Package Manager**: npm

### Database

-   **Engine**: PostgreSQL 15+

## Installation and Setup

### Prerequisites

-   JDK 21
-   Node.js 18+
-   PostgreSQL 15+
-   Maven 3.8+
-   Docker (for easy DB setup)

### Parent Dependencies

Before running the application, you must install the parent dependencies. Clone the repositories and run `mvn clean install` in each to install them in your local Maven repository.

1.  **Java Parent**: `parents` repository.
2.  **Common Libs**: `common-libs` repository.

### Database Setup

Run the following Docker command to start a PostgreSQL database instance:

```bash
bash docker run -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=admin123 -e POSTGRES_DB=configserver -p 5432:5432 -d postgres:latest
```

### Backend Setup

1.  Navigate to the `SB-ConfigServer-Application` directory.
2.  Configure your database connection in `src/main/resources/application.properties`.
3.  Run the application with the command: `mvn spring-boot:run`.
4.  The server will start on port `8080` by default.

### Frontend Setup

1.  Navigate to the `NG-ConfigServer-UI` directory.
2.  Install dependencies: `npm install`.
3.  Start the development server: `ng serve`.
4.  Open your browser and go to `http://localhost:4200`.

## Security

-   **Authentication**: The API uses JSON Web Tokens (JWT) to secure endpoints. Users must authenticate to obtain a token.
-   **Authorization**: Spring's method-level security is used to control access to different features.
-   **Driver Access**: Client services (drivers) authenticate using an `X-Scope-Access-Key` header to fetch properties for a specific scope.
-   **Encryption**: Sensitive properties are stored encrypted in the database using Jasypt.

## REST API

The application exposes the following main endpoints:

### Scopes (`/api/scopes`)

-   `GET /`: Get all scopes.
-   `GET /{id}`: Get a scope by its ID.
-   `GET /{id}/access-key`: Get the access key for a scope.
-   `POST /`: Create a new scope.
-   `PUT /{scopeId}/users`: Assign a set of users (by email) to a scope.

### Driver (`/api/driver`)

-   `POST /properties`: Get all properties (and decrypted secrets) for a scope. Requires `X-Scope-Name` and `X-Scope-Access-Key` headers.

## Monitoring and Logs

The application uses a custom logging starter (`spring-boot-logging-starter`) to provide detailed and consistent event logging, facilitating monitoring and debugging.

## Contributing

Contributions are welcome. Please open an issue or submit a pull request to discuss any changes.

## License

This project is licensed under the MIT License. See the `LICENSE` file for details.