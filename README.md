# BuildHub - Construction Platform

A one-stop platform connecting Customers, Contractors, and Labours. Designed to simplify project management, hiring, and collaboration.

## Features

- **Multi-User System**: Separate interfaces for Customers, Contractors, and Labours
- **Project Management**: Create, track, and manage construction projects
- **Transparent Hiring**: Browse and post job opportunities
- **Trust & Transparency**: Ratings, reviews, and verification system
- **Efficient Collaboration**: Real-time updates and communication

## Tech Stack

- **Desktop Application**: JavaFX (Native UI)
- **Backend**: Java Spring Boot
- **Database**: SQLite3 with JDBC
- **Authentication**: JWT with BCrypt password hashing

## Getting Started

### 🖥️ Running the JavaFX Desktop Application

BuildHub is a **native desktop application** built with JavaFX:

```bash
# Compile and run the application
mvn javafx:run
```

This opens a native desktop window with the full BuildHub interface.

### ☕ Java Spring Boot Backend (API Server)

To run the Spring Boot backend API server:

```bash
# Run the Spring Boot server
mvn spring-boot:run
```

Runs on port 8080. Provides REST API endpoints for user management, authentication, and project operations.

### 🔨 Building for Production

```bash
# Clean and compile
mvn clean compile

# Run JavaFX application
mvn javafx:run

# Or run Spring Boot server
mvn spring-boot:run
```

See [docs/JAVAFX_SETUP.md](docs/JAVAFX_SETUP.md) and [docs/JAVA_SETUP.md](docs/JAVA_SETUP.md) for detailed setup instructions.

**Why Java/JavaFX?**
- **Native Desktop UI**: Better performance and native look-and-feel
- **Production-Ready**: Enterprise-grade framework
- **Type-Safe**: Compile-time error checking
- **Database Access**: Direct JDBC connection to SQLite
- **Cross-Platform**: Works on Windows, macOS, and Linux
- **No Browser Required**: Run as a standalone desktop application

### Usage

1. Launch the application with `mvn javafx:run`
2. Register as Customer, Contractor, or Labour
3. Access your dedicated dashboard
4. Create projects, post jobs, or find opportunities
5. Manage your workflow efficiently

## User Roles

### Customer
- Post construction projects
- Hire contractors and labours
- Track project progress
- Manage budgets and payments
- Edit and delete projects

### Contractor
- Bid on projects
- Manage teams and labours
- Track work progress
- Get paid for completed work
- Build portfolio and ratings

### Labour
- Find job opportunities
- Apply for positions
- Track work hours
- Receive payments
- Build reputation through ratings

## Configuration

The application uses SQLite for database storage. The database file `buildhub.db` is created automatically in the project directory.

Main configuration file: `src/main/resources/application.properties`

## License

ISC

