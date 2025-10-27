# How to Check Login Details in BuildHub Database

## Quick Method: View from Terminal Logs

From your terminal logs, you can see these users are registered:

**Registered Users (from logs):**
1. **Email:** chinmay@gmail.com
2. **Email:** biswassoham49@gmail.com

---

## Method 1: Run the Database Viewer Tool

I've created a simple Java tool to view the database. Run it:

```powershell
javac -cp "target/classes;target/dependency/*" src/main/java/com/buildhub/util/DatabaseViewer.java
java -cp "target/classes;target/dependency/*" com.buildhub.util.DatabaseViewer
```

---

## Method 2: Use DB Browser for SQLite (Recommended for GUI)

### Steps:
1. **Download DB Browser for SQLite**
   - Visit: https://sqlitebrowser.org/
   - Download and install

2. **Open the Database**
   - Open DB Browser for SQLite
   - Click "Open Database"
   - Navigate to: `d:\app labour\buildhub.db`
   - Click Open

3. **View Users**
   - Click on "Browse Data" tab
   - Select "users" from the table dropdown
   - View all registered users and their details

4. **Execute SQL Queries**
   - Click on "Execute SQL" tab
   - Run this query:
   ```sql
   SELECT id, name, email, role, phone, address FROM users;
   ```

---

## Method 3: View Database File Location

The database file is located at:
```
d:\app labour\buildhub.db
```

Size: ~40KB (based on last file check)

---

## Method 4: From Application UI

### To Create New Test Users:

1. **Run the application:**
   ```powershell
   mvn javafx:run
   ```

2. **Click "Sign Up"** on the landing page

3. **Fill in the details:**
   - Name
   - Email
   - Phone
   - Address
   - Password
   - Select Role (Customer, Contractor, or Labour)

4. **Click Register**

5. **Login with the credentials you just created**

---

## Database Schema

The users table contains:
- `id`: Unique identifier
- `name`: User's full name
- `email`: Login email (unique)
- `password`: BCrypt hashed password
- `role`: customer, contractor, or labour
- `phone`: Contact number
- `address`: User address

---

## Security Notes

⚠️ **Important:**
- Passwords are encrypted using BCrypt
- You cannot view actual passwords, only hashed versions
- To test login, use credentials you registered

---

## Quick Test

To test the application:
1. Open the app: `mvn javafx:run`
2. Click "Sign Up"
3. Create a test account
4. Click "Login" and use those credentials

---

## View from Code

If you want to add database viewing to the app, the `DatabaseService` class has methods like:
- `registerUser()` - Creates new users
- `loginUser()` - Validates login
- You can add a view method to the service

The database is automatically created in the project root as `buildhub.db` when you first run the application.

