# How to Check Login Details in SQLite Database

## Method 1: Using SQLite Command Line

### Step 1: Navigate to project directory
```bash
cd "d:\app labour"
```

### Step 2: Open SQLite database
```bash
sqlite3 buildhub.db
```

### Step 3: Query user login information
```sql
-- View all users with login credentials (names and emails)
SELECT id, name, email, role, phone FROM users;

-- View specific user details
SELECT * FROM users WHERE email = 'chinmay@gmail.com';

-- View password hashes (to verify they exist)
SELECT id, name, email, SUBSTR(password, 1, 20) as password_preview FROM users;

-- Count users by role
SELECT role, COUNT(*) as count FROM users GROUP BY role;
```

### Step 4: Exit SQLite
```sql
.quit
```

---

## Method 2: Using DB Browser for SQLite (GUI)

### Step 1: Download and Install
- Download from: https://sqlitebrowser.org/
- Install the application

### Step 2: Open the Database
- Open DB Browser for SQLite
- Click "Open Database"
- Navigate to: `d:\app labour\buildhub.db`

### Step 3: Browse Data
- Click on "Browse Data" tab
- Select "users" table from dropdown
- View all user login information

### Step 4: Execute SQL Queries
- Click on "Execute SQL" tab
- Run queries like:
```sql
SELECT id, name, email, role FROM users;
```

---

## Method 3: Using VS Code Extension

### Step 1: Install SQLite Extension
- Open VS Code
- Install "SQLite Viewer" or "SQLite" extension

### Step 2: Open Database File
- Right-click on `buildhub.db`
- Select "Open Database"
- View tables and query data

---

## Method 4: Using Python Script (Quick Check)

Create a file `check_database.py`:

```python
import sqlite3

# Connect to database
conn = sqlite3.connect('buildhub.db')
cursor = conn.cursor()

# Query all users
cursor.execute("SELECT id, name, email, role, phone, address FROM users")
users = cursor.fetchall()

print("=" * 80)
print("BUILDHUB USER LOGIN DETAILS")
print("=" * 80)

for user in users:
    print(f"ID: {user[0]}")
    print(f"Name: {user[1]}")
    print(f"Email: {user[2]}")
    print(f"Role: {user[3]}")
    print(f"Phone: {user[4]}")
    print(f"Address: {user[5]}")
    print("-" * 80)

# Count by role
cursor.execute("SELECT role, COUNT(*) FROM users GROUP BY role")
roles = cursor.fetchall()
print("\nUSER COUNT BY ROLE:")
for role in roles:
    print(f"{role[0]}: {role[1]}")

conn.close()
```

Run it:
```bash
python check_database.py
```

---

## Method 5: View Database Location

The database file is located at:
```
d:\app labour\buildhub.db
```

---

## Quick Test Credentials (Based on Logs)

From the terminal logs, these users exist:

**Customer:**
- Email: chinmay@gmail.com
- Email: biswassoham49@gmail.com

You can test login with any registered email. Passwords are hashed using BCrypt.

---

## Reset Password (If Needed)

If you need to reset a password, you can update the database:

```sql
-- Update password for a user
-- New password will be: "password123" (hashed)
UPDATE users 
SET password = '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy'
WHERE email = 'chinmay@gmail.com';
```

---

## Security Note

⚠️ **Important**: Passwords are stored as BCrypt hashes in the database. You cannot view actual passwords, only the hashed versions.

To test login:
1. Register a new user through the app
2. Use that email and password to login

---

## Sample Query Results

```sql
-- Get all login credentials
SELECT 
    id,
    name,
    email,
    role,
    password  -- This is the BCrypt hash
FROM users;
```

Output example:
```
id | name          | email                    | role      | password
1  | Chinmay       | chinmay@gmail.com       | customer  | $2a$10$...
2  | Soham Biswas  | biswassoham49@gmail.com | customer  | $2a$10$...
```

