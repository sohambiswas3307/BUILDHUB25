# Setup Instructions

## Prerequisites

- Node.js (v14 or higher)
- SQLite3 (comes bundled with better-sqlite3)

## Installation Steps

### 1. Install Dependencies

```bash
# Install backend dependencies
npm install

# Install frontend dependencies
cd client
npm install
cd ..
```

### 2. Configure Environment Variables

Copy the example environment file:
```bash
cp .env.example .env
```

Edit `.env` and update:
- `DB_PATH`: Path to SQLite database file (default: ./buildhub.db)
- `JWT_SECRET`: A secure random string for JWT tokens
- `PORT`: Backend server port (default: 5000)

### 3. Start the Application

#### Development Mode (Both servers)
```bash
npm run dev
```

This will start:
- Backend server on http://localhost:5000
- Frontend server on http://localhost:3000

#### Production Mode
```bash
# Build the frontend
npm run build

# Start backend only (database will be created automatically)
npm run server
```

## Usage

1. Open http://localhost:3000 in your browser
2. Register as:
   - **Customer**: Post construction projects
   - **Contractor**: Bid on projects and manage teams
   - **Labour**: Find and apply for job opportunities
3. Each user type has a dedicated dashboard

## Features

### Customers
- Create and manage construction projects
- View and accept contractor bids
- Track project progress
- Hire contractors and labours

### Contractors
- Browse available projects
- Place bids on projects
- Manage accepted projects
- Track earnings

### Labours
- Browse job postings
- Apply for positions
- Track work hours
- Manage profile and skills

## Database Schema

The SQLite database is automatically created on first run with the following tables:
- **Users**: Stores user information with role-based fields
- **Projects**: Construction projects with budget, timeline, and progress
- **Bids**: Contractor bids on projects
- **Labour Requirements**: Labour needs for each project
- **Hired Labours**: Labours assigned to projects
- **Labour Postings**: Job openings for construction workers
- **Applicants**: Applications for labour postings
- **Ratings**: User ratings and reviews

## API Endpoints

### Authentication
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - Login user
- `GET /api/auth/me` - Get current user

### Projects
- `GET /api/projects` - Get all projects
- `POST /api/projects` - Create new project
- `GET /api/projects/:id` - Get project details
- `PUT /api/projects/:id` - Update project
- `POST /api/projects/:id/bid` - Place a bid
- `POST /api/projects/:id/accept-bid/:bidId` - Accept a bid

### Labours
- `GET /api/labours` - Get all labours
- `GET /api/labours/postings` - Get job postings
- `POST /api/labours/postings` - Create job posting
- `POST /api/labours/postings/:id/apply` - Apply for a job

