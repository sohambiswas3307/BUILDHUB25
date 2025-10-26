# BuildHub - Construction Platform

A one-stop platform connecting Customers, Contractors, and Labours. Designed to simplify project management, hiring, and collaboration.

## Features

- **Multi-User System**: Separate interfaces for Customers, Contractors, and Labours
- **Project Management**: Create, track, and manage construction projects
- **Transparent Hiring**: Browse and post job opportunities
- **Trust & Transparency**: Ratings, reviews, and verification system
- **Efficient Collaboration**: Real-time updates and communication

## Tech Stack

- **Backend**: Node.js, Express, SQLite3
- **Frontend**: React, Tailwind CSS
- **Authentication**: JWT

## Getting Started

### Installation

```bash
npm run install-all
```

### Development

```bash
npm run dev
```

This will start both the backend server (on port 5000) and frontend (on port 3000).

### Usage

1. Register as Customer, Contractor, or Labour
2. Access your dedicated dashboard
3. Create projects, post jobs, or find opportunities
4. Manage your workflow efficiently

## User Roles

### Customer
- Post construction projects
- Hire contractors and labours
- Track project progress
- Manage budgets and payments

### Contractor
- Bid on projects
- Manage teams and labours
- Track work progress
- Get paid for completed work

### Labour
- Find job opportunities
- Apply for positions
- Track work hours
- Receive payments

## Environment Variables

Create a `.env` file in the root directory:

```
DB_PATH=./construction-connect.db
JWT_SECRET=your-secret-key
PORT=5000
```

## License

ISC

