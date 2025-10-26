# BuildHub Database Schema

This directory contains the database schema and related files for the BuildHub application.

## Files

- `schema.sql` - Complete SQLite database schema
- `README.md` - This file

## Database Structure

### Tables Overview

#### 1. **users**
Stores all user accounts (Customers, Contractors, and Labours)

**Key Fields:**
- `id` - Primary key
- `name`, `email`, `password`, `phone`, `address` - Basic user info
- `role` - Enum: 'customer', 'contractor', 'labour'
- Contractor-specific: `license_number`, `specialization`, `min_budget`, `max_budget`, `portfolio_url`
- Labour-specific: `experience`, `domain_of_expertise`, `skills`, `hourly_rate`, `availability`
- Metrics: `customer_approval_ratio`, `projects_completed`, `average_rating`, etc.

#### 2. **projects**
Stores construction projects created by customers

**Key Fields:**
- `id` - Primary key
- `customer_id` - Foreign key to users
- `title`, `description`, `budget`, `location`
- `status` - Enum: 'pending', 'in_progress', 'completed', 'cancelled'
- `assigned_contractor_id` - Foreign key to users (contractor)
- `required_skills`, `start_date`, `end_date`, `completion_date`

#### 3. **labour_work_history**
Tracks work history for labour users

**Key Fields:**
- `id` - Primary key
- `labour_id`, `project_id` - Foreign keys
- `role`, `start_date`, `end_date`, `hours_worked`, `pay_rate`
- `status`, `feedback`, `rating`

#### 4. **disputes**
Manages disputes between users

**Key Fields:**
- `id` - Primary key
- `project_id`, `customer_id`, `contractor_id`, `labour_id` - Foreign keys
- `reason`, `status`, `resolution_details`

#### 5. **purchased_items**
Tracks items purchased for projects (cost tracking)

**Key Fields:**
- `id` - Primary key
- `project_id` - Foreign key
- `item_name`, `quantity`, `unit_price`, `total_price`
- `category`, `supplier`, `purchase_date`, `notes`

#### 6. **consultations**
Tracks consultation requests between customers and contractors

**Key Fields:**
- `id` - Primary key
- `customer_id`, `contractor_id`, `project_id` - Foreign keys
- `status` - Enum: 'pending', 'accepted', 'rejected', 'completed'
- `consultation_date`, `response_date`, `notes`

## Usage

### Initialize Database

The database is automatically created and initialized when you run the BuildHub application. The `DatabaseService.java` class handles table creation on startup.

### Manual Database Creation

If you need to create the database manually:

```bash
sqlite3 buildhub.db < database/schema.sql
```

### View Database

```bash
sqlite3 buildhub.db
.tables          # List all tables
.schema users     # View table structure
SELECT * FROM users;  # Query data
```

## Database Path

The database file (`buildhub.db`) is created in the project root directory by default.

Change the path in `DatabaseService.java`:
```java
private static final String DB_PATH = "jdbc:sqlite:./buildhub.db";
```

## Security

- Passwords are hashed using BCrypt before storage
- Email addresses are unique and indexed
- Foreign key constraints ensure data integrity
- Cascade deletes prevent orphaned records

## Backup

To backup your database:

```bash
sqlite3 buildhub.db .dump > backup.sql
```

To restore:

```bash
sqlite3 buildhub.db < backup.sql
```

## License

ISC - See main project LICENSE file

