# BuildHub - Java Backend Setup

This project now supports **Java Spring Boot** backend with **JDBC** for SQLite database.

## Structure

```
BuildHub/
├── pom.xml                          # Maven configuration
├── server.js                        # Node.js server (original)
└── src/
    └── main/
        ├── java/com/buildhub/
        │   ├── BuildHubApplication.java    # Main Spring Boot app
        │   ├── config/                     # Configuration classes
        │   ├── controller/                 # REST controllers
        │   ├── model/                      # Data models
        │   └── service/                    # Business logic
        └── resources/
            └── application.properties      # App configuration
```

## Prerequisites

1. **Java 17+** (JDK 17 or higher)
2. **Maven** (build tool)
3. **SQLite** database (already created)

## Installation

### 1. Check Java Version
```bash
java -version
```
Should show Java 17 or higher.

### 2. Build the Project
```bash
mvn clean install
```

## Running the Java Backend

### Option 1: Using Maven
```bash
mvn spring-boot:run
```

### Option 2: Using the JAR
```bash
mvn package
java -jar target/buildhub-1.0.0.jar
```

## Java Backend Port

- **Server**: http://localhost:8080
- **API**: http://localhost:8080/api
- **Health Check**: http://localhost:8080/api/health

## Configuration

Edit `src/main/resources/application.properties`:

```properties
server.port=8080
spring.datasource.url=jdbc:sqlite:buildhub.db
```

## API Endpoints (Java Backend)

- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - Login user
- `GET /api/auth/health` - Auth service health check
- `GET /api/projects` - Get all projects
- `POST /api/projects` - Create new project
- `GET /api/projects/{id}` - Get project by ID
- `GET /api/health` - API health check

## Frontend Configuration

The React frontend can connect to either:

1. **Node.js Backend** (port 5000)
2. **Java Backend** (port 8080)

Update the frontend API base URL in `client/src/utils/api.js`:

```javascript
// For Node.js backend
const API_BASE_URL = 'http://localhost:5000/api';

// For Java backend
const API_BASE_URL = 'http://localhost:8080/api';
```

## Database

Both backends use the **same SQLite database** (`buildhub.db`), so data is shared between them.

## Comparison

| Feature | Node.js Backend | Java Backend |
|---------|----------------|--------------|
| Port | 5000 | 8080 |
| Technology | Express.js | Spring Boot |
| Database | SQLite (better-sqlite3) | SQLite (JDBC) |
| Framework | Express | Spring Boot |
| Languages | JavaScript | Java |

## Development

### Run Node.js Backend
```bash
npm start
```

### Run Java Backend
```bash
mvn spring-boot:run
```

## Notes

- Both backends can run simultaneously (different ports)
- Both use the same SQLite database
- Frontend needs to be configured to point to the desired backend
- Java backend is production-ready with Spring Boot
- JDBC provides robust database connection pooling

