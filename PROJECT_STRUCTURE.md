# 📂 BuildHub Project Structure

This document outlines the simplified and organized project structure.

## 🎯 Root Directory

```
BUILDHUB25/
├── 📁 src/                          # Source code
├── 📁 database/                     # Database schema and docs
├── 📄 pom.xml                       # Maven configuration
├── 📄 README.md                     # Project documentation
├── 📄 PROJECT_STRUCTURE.md          # This file
└── 🔒 .gitignore                    # Git ignore rules
```

## 📦 Source Code (`src/`)

```
src/
└── main/
    ├── java/com/buildhub/
    │   ├── 🎨 javafx/                    # Desktop UI Controllers
    │   │   ├── BuildHubFxApp.java        # Main JavaFX application
    │   │   ├── LandingController.java    # Landing page
    │   │   ├── LoginController.java      # Login functionality
    │   │   ├── RegisterController.java   # Registration
    │   │   ├── CustomerDashboardController.java
    │   │   ├── ContractorDashboardController.java
    │   │   ├── LabourDashboardController.java
    │   │   └── DatabaseService.java      # Database operations
    │   │
    │   ├── 🌐 controller/                 # REST API Controllers
    │   │   ├── AuthController.java       # Authentication endpoints
    │   │   ├── ProjectController.java    # Project CRUD endpoints
    │   │   └── HealthController.java      # Health check
    │   │
    │   ├── ⚙️ service/                    # Business Logic
    │   │   └── AuthService.java           # Authentication service
    │   │
    │   ├── 📋 model/                      # Data Models
    │   │   ├── User.java                  # User model
    │   │   └── Project.java                # Project model
    │   │
    │   └── ⚙️ config/                     # Configuration
    │       ├── DatabaseConfig.java        # DB setup
    │       └── CorsConfig.java            # CORS configuration
    │
    └── resources/
        ├── application.properties          # Spring Boot config
        └── com/buildhub/
            ├── 🎨 views/                   # FXML UI layouts
            │   ├── landing.fxml
            │   ├── login.fxml
            │   ├── register.fxml
            │   ├── customer_dashboard.fxml
            │   ├── contractor_dashboard.fxml
            │   └── labour_dashboard.fxml
            │
            └── 🎨 styles/                  # CSS styling
                └── styles.css
```

## 🗄️ Database (`database/`)

```
database/
├── schema.sql         # Complete SQLite database schema
└── README.md          # Database documentation and usage guide
```

## 🚫 Hidden from Git

These files/directories are automatically ignored by `.gitignore`:

- `target/` - Compiled classes and build artifacts
- `buildhub.db` - SQLite database file (auto-generated)
- `node_modules/` - Node.js dependencies (if any)
- `*.bat` - Batch files
- `_archive/` - Archived documentation and old files
- IDE-specific files (`.idea/`, `.vscode/`, etc.)
- OS-specific files (`.DS_Store`, `Thumbs.db`, etc.)

## 🚀 Key Components

### JavaFX Desktop App
- **Entry Point**: `BuildHubFxApp.java`
- **Main Scene**: Landing page → Login → Dashboard
- **Theming**: Purple gradient with dark sidebar

### Database
- **Type**: SQLite
- **Location**: `./buildhub.db` (auto-created)
- **Schema**: See `database/schema.sql`

### Spring Boot API (Optional)
- **Port**: 8080 (configurable)
- **Controllers**: REST endpoints for web integration

## 📝 File Organization

✅ **Visible Files** (Essential for development):
- Source code in `src/`
- Configuration files (`pom.xml`, `application.properties`)
- Documentation (`README.md`, `PROJECT_STRUCTURE.md`)
- Database schema (`database/schema.sql`)

🚫 **Hidden/Archived Files** (Not needed for development):
- Build artifacts (`target/`)
- Database file (`*.db`)
- Old documentation (`docs/` → moved to `_archive/`)
- IDE and OS files

## 🎯 Running the Project

```bash
# Clean and compile
mvn clean compile

# Run JavaFX desktop app
mvn javafx:run

# Run Spring Boot server
mvn spring-boot:run
```

## 📊 Simplified Structure Benefits

1. **Clear Organization** - All code in `src/`
2. **Separated Concerns** - Database schema in `database/`
3. **Clean Repository** - Only essential files visible
4. **Easy Navigation** - Logical folder structure
5. **Professional Look** - Industry-standard layout

