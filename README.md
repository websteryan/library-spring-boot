# Library Management System

A comprehensive library management system built with Spring Boot, featuring user authentication, book management, and loan tracking.

## Features

### Authentication & Authorization
- JWT-based authentication
- Role-based access control (ADMIN, LIBRARIAN, USER)
- Secure password encryption with BCrypt

### Book Management
- CRUD operations for books
- Category-based organization
- Search functionality (title, author, ISBN, category)
- Availability tracking

### Loan Management
- Book borrowing and returning
- Loan status tracking (ACTIVE, RETURNED, OVERDUE, CANCELLED)
- Automatic overdue detection
- User loan limits

### User Management
- User registration and profile management
- Role-based permissions
- User activity tracking

## Technology Stack

- **Framework**: Spring Boot 3.2.0
- **Security**: Spring Security with JWT
- **Database**: H2 (in-memory for development)
- **ORM**: Spring Data JPA with Hibernate
- **Documentation**: OpenAPI 3 (Swagger)
- **Build Tool**: Maven
- **Java Version**: 17

## Architecture

### Design Patterns Used
- **Repository Pattern**: Data access abstraction
- **DTO Pattern**: Data transfer between layers
- **Builder Pattern**: Object construction (Lombok)
- **Singleton Pattern**: JWT utilities and configurations

### Key Components
- **Controllers**: REST API endpoints
- **Services**: Business logic layer
- **Repositories**: Data access layer
- **Security**: JWT authentication and authorization
- **Exception Handling**: Global exception handler

## API Endpoints

### Authentication
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - User login

### Books
- `GET /api/books` - Get all books (paginated)
- `GET /api/books/search` - Search books
- `GET /api/books/available` - Get available books
- `GET /api/books/{id}` - Get book by ID
- `POST /api/books` - Create book (ADMIN/LIBRARIAN)
- `PUT /api/books/{id}` - Update book (ADMIN/LIBRARIAN)
- `DELETE /api/books/{id}` - Delete book (ADMIN)

### Loans
- `GET /api/loans` - Get all loans (ADMIN/LIBRARIAN)
- `GET /api/loans/user/{userId}` - Get user loans
- `GET /api/loans/status/{status}` - Get loans by status
- `POST /api/loans/user/{userId}/book/{bookId}` - Create loan
- `PUT /api/loans/{loanId}/return` - Return book

## Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

### Installation

1. Clone the repository:
\`\`\`bash
git clone <repository-url>
cd library-management
\`\`\`

2. Build the project:
\`\`\`bash
mvn clean install
\`\`\`

3. Run the application:
\`\`\`bash
mvn spring-boot:run
\`\`\`

The application will start on `http://localhost:8080`

### Default Users

The application comes with pre-configured users:

| Email | Password | Role |
|-------|----------|------|
| admin@library.com | password | ADMIN |
| librarian@library.com | password | LIBRARIAN |
| user@library.com | password | USER |

### API Documentation

Once the application is running, you can access:
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Docs**: http://localhost:8080/v3/api-docs
- **H2 Console**: http://localhost:8080/h2-console

## Configuration

### Database Configuration
The application uses H2 in-memory database by default. To use a different database, update `application.properties`:

```properties
# For MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/library_db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
