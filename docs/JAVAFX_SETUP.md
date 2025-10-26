# BuildHub - JavaFX Desktop Application

BuildHub now includes a **JavaFX desktop application** for a native desktop experience.

## Features

- Native desktop UI with JavaFX
- Beautiful, modern interface
- Three user role interfaces
- Responsive design
- Cross-platform support (Windows, macOS, Linux)

## Setup

### 1. Build the Project

```bash
mvn clean package
```

This will create the JAR file at `target/buildhub-1.0.0.jar`

### 2. Run the JavaFX Application

#### Option 1: Using Maven (Recommended)

First, download JavaFX dependencies:

```bash
mvn dependency:copy-dependencies
```

Then run:

```bash
java --module-path target\dependency --add-modules javafx.controls,javafx.fxml -cp "target\classes;target\dependency\*" com.buildhub.javafx.BuildHubFxApp
```

#### Option 2: Using the Batch Script (Windows)

```bash
.\run-javafx.bat
```

#### Option 3: Direct Java Command

```bash
java -cp target\buildhub-1.0.0.jar com.buildhub.javafx.BuildHubFxApp
```

## Application Structure

```
src/main/java/com/buildhub/javafx/
├── BuildHubFxApp.java          # Main JavaFX application
└── LandingController.java      # Landing page controller

src/main/resources/com/buildhub/
├── views/
│   └── landing.fxml            # Landing page UI
└── styles/
    └── styles.css              # CSS styles
```

## Running the Desktop App

1. The JavaFX app opens a native window
2. Land on the landing page with three role cards
3. Click "Login" or "Register" to proceed
4. Select your role (Customer, Contractor, Labour)

## Integration with Backend

The JavaFX app can connect to either:
- **Node.js Backend**: http://localhost:5000
- **Java Spring Boot Backend**: http://localhost:8080

## Development

### Running in Development Mode

```bash
mvn javafx:run
```

### Building for Distribution

```bash
mvn jlink:jlink
```

This creates a native executable.

## Screenshots

The application features:
- **Landing Page**: Hero section with role selection cards
- **Modern UI**: Clean, professional interface
- **Responsive**: Adapts to window size changes
- **Interactive**: Smooth button hover effects

## System Requirements

- Java 17 or higher
- JavaFX 21 or higher
- Maven (for building)

## Notes

- The JavaFX app runs independently of the web application
- It can connect to the same SQLite database
- Cross-platform support for Windows, macOS, and Linux

