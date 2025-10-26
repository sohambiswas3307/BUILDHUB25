# 🏗️ BuildHub - Construction Management Platform

A modern, desktop-based platform connecting Customers, Contractors, and Labours for efficient construction project management.

## 📋 Overview

BuildHub is a JavaFX desktop application that simplifies construction project management, hiring, and collaboration, ensuring transparency, trust, and efficiency in the construction ecosystem.

## ✨ Features

- **Multi-Role System**: Separate dashboards for Customers, Contractors, and Labours
- **Project Management**: Create, track, edit, and manage construction projects
- **Trust & Transparency**: Ratings, reviews, and approval tracking
- **Cost Tracking**: Track purchased items and project budgets
- **Modern UI**: Purple gradient theme with dark sidebar navigation
- **SQLite Database**: Lightweight, local database with automatic schema initialization

## 🚀 Quick Start

### Prerequisites

- Java 17 or higher
- Maven 3.8+

### Run the Application

```bash
# Clone the repository
git clone https://github.com/sohambiswas3307/BUILDHUB25.git
cd BUILDHUB25

# Run the JavaFX application
mvn javafx:run
```

## 🎨 UI Theme

- **Purple Gradient Background**: Soft purple (#E0E7FF to #C7D2FE)
- **Dark Sidebar**: #1E1B4B navigation bar with BuildHub branding
- **White Content Area**: Clean, modern card-based design
- **Indian Currency**: All monetary values displayed in ₹ (INR)

## 📁 Project Structure

```
BUILDHUB25/
├── src/
│   └── main/
│       ├── java/com/buildhub/
│       │   ├── javafx/          # JavaFX controllers
│       │   ├── controller/      # Spring Boot controllers
│       │   ├── service/         # Business logic
│       │   ├── model/           # Data models
│       │   └── config/          # Configuration
│       └── resources/
│           ├── com/buildhub/
│           │   ├── views/       # FXML layouts
│           │   └── styles/      # CSS files
│           └── application.properties
├── database/
│   ├── schema.sql               # Database schema
│   └── README.md                # Database documentation
├── pom.xml                       # Maven configuration
└── README.md                     # This file
```

## 🔧 Configuration

Main configuration: `src/main/resources/application.properties`
Database path: `./buildhub.db` (auto-created on first run)

## 📊 Database Schema

The application uses SQLite with the following main tables:

- `users` - User accounts with role-specific fields
- `projects` - Construction projects
- `labour_work_history` - Labour work tracking
- `disputes` - Dispute management
- `purchased_items` - Cost tracking
- `consultations` - Customer-Contractor consultations

See `database/schema.sql` for complete schema details.

## 👥 User Roles

### Customer
- Create and manage construction projects
- Track project budgets and expenses
- Hire contractors and labours
- View project progress

### Contractor
- Browse and bid on projects
- Manage portfolio and ratings
- Track completed work
- Showcase specialization

### Labour
- Find job opportunities
- Track work hours and wages
- Build reputation through ratings
- Manage availability

## 💻 Running Options

### Desktop Application (Recommended)
```bash
mvn javafx:run
```

### Spring Boot Server
```bash
mvn spring-boot:run
```

## 🔐 Authentication

- Passwords are hashed using BCrypt
- Email addresses must be unique
- Role-based access control

## 📝 Tech Stack

- **Frontend**: JavaFX with FXML
- **Backend**: Java Spring Boot
- **Database**: SQLite with JDBC
- **Authentication**: BCrypt password hashing
- **Build Tool**: Maven

## 📄 License

ISC License

---

Built with ❤️ for the construction industry
