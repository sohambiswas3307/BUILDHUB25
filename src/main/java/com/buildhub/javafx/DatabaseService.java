package com.buildhub.javafx;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseService {
    private static final String DB_PATH = "jdbc:sqlite:./buildhub.db";
    private static Connection connection;
    private static boolean initialized = false;

    public DatabaseService() {
        synchronized (DatabaseService.class) {
            try {
                if (connection == null || connection.isClosed()) {
                    connection = DriverManager.getConnection(DB_PATH);
                    System.out.println("✅ Connected to SQLite database from JavaFX");
                }
                if (!initialized) {
                    initializeTables();
                    initialized = true;
                }
            } catch (SQLException e) {
                System.err.println("❌ Database connection error: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private Connection getConnection() {
        return connection;
    }

    private void initializeTables() {
        try (Statement stmt = connection.createStatement()) {
            // Create users table if it doesn't exist
            String createUsersTable = """
                CREATE TABLE IF NOT EXISTS users (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    email TEXT UNIQUE NOT NULL,
                    password TEXT NOT NULL,
                    role TEXT NOT NULL CHECK(role IN ('customer', 'contractor', 'labour')),
                    phone TEXT NOT NULL,
                    address TEXT NOT NULL,
                    license_number TEXT DEFAULT '',
                    experience TEXT DEFAULT '',
                    specialization TEXT DEFAULT '',
                    domain_of_expertise TEXT DEFAULT '',
                    skills TEXT DEFAULT '[]',
                    hourly_rate REAL DEFAULT 0,
                    min_budget REAL DEFAULT 0,
                    max_budget REAL DEFAULT 0,
                    availability TEXT DEFAULT 'available',
                    portfolio_url TEXT DEFAULT '',
                    customer_approval_ratio REAL DEFAULT 1.0,
                    projects_completed INTEGER DEFAULT 0,
                    average_rating REAL DEFAULT 0,
                    acceptance_per_consultation_ratio REAL DEFAULT 1.0,
                    is_verified INTEGER DEFAULT 0,
                    avatar TEXT DEFAULT '',
                    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
                )
                """;
            stmt.execute(createUsersTable);

            // Create projects table if it doesn't exist
            String createProjectsTable = """
                CREATE TABLE IF NOT EXISTS projects (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    customer_id INTEGER NOT NULL,
                    title TEXT NOT NULL,
                    description TEXT,
                    budget REAL,
                    status TEXT DEFAULT 'pending' CHECK(status IN ('pending', 'in_progress', 'completed', 'cancelled')),
                    start_date TEXT,
                    end_date TEXT,
                    location TEXT,
                    required_skills TEXT DEFAULT '[]',
                    assigned_contractor_id INTEGER,
                    completed_details TEXT DEFAULT '',
                    completion_date TEXT,
                    customer_approved INTEGER DEFAULT 0,
                    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (customer_id) REFERENCES users(id) ON DELETE CASCADE,
                    FOREIGN KEY (assigned_contractor_id) REFERENCES users(id)
                )
                """;
            stmt.execute(createProjectsTable);

            // Create labour_work_history table
            String createLabourWorkHistoryTable = """
                CREATE TABLE IF NOT EXISTS labour_work_history (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    labour_id INTEGER NOT NULL,
                    project_id INTEGER NOT NULL,
                    role TEXT,
                    start_date TEXT,
                    end_date TEXT,
                    hours_worked REAL,
                    pay_rate REAL,
                    status TEXT DEFAULT 'completed',
                    feedback TEXT,
                    rating INTEGER,
                    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (labour_id) REFERENCES users(id) ON DELETE CASCADE,
                    FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE
                )
                """;
            stmt.execute(createLabourWorkHistoryTable);

            // Create disputes table
            String createDisputesTable = """
                CREATE TABLE IF NOT EXISTS disputes (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    project_id INTEGER NOT NULL,
                    customer_id INTEGER NOT NULL,
                    contractor_id INTEGER,
                    labour_id INTEGER,
                    reason TEXT NOT NULL,
                    status TEXT DEFAULT 'open' CHECK(status IN ('open', 'resolved', 'closed')),
                    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                    resolved_at TEXT,
                    resolution_details TEXT,
                    FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE,
                    FOREIGN KEY (customer_id) REFERENCES users(id) ON DELETE CASCADE,
                    FOREIGN KEY (contractor_id) REFERENCES users(id),
                    FOREIGN KEY (labour_id) REFERENCES users(id)
                )
                """;
            stmt.execute(createDisputesTable);

            // Create purchased_items table
            String createPurchasedItemsTable = """
                CREATE TABLE IF NOT EXISTS purchased_items (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    project_id INTEGER NOT NULL,
                    item_name TEXT NOT NULL,
                    quantity INTEGER NOT NULL DEFAULT 1,
                    unit_price REAL NOT NULL,
                    total_price REAL NOT NULL,
                    category TEXT,
                    supplier TEXT,
                    purchase_date TEXT,
                    notes TEXT,
                    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE
                )
                """;
            stmt.execute(createPurchasedItemsTable);

            // Create consultations table
            String createConsultationsTable = """
                CREATE TABLE IF NOT EXISTS consultations (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    project_id INTEGER NOT NULL,
                    type TEXT NOT NULL CHECK(type IN ('bid', 'proposal', 'meeting', 'review')),
                    contractor_id INTEGER,
                    notes TEXT,
                    status TEXT DEFAULT 'pending' CHECK(status IN ('pending', 'accepted', 'rejected')),
                    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE,
                    FOREIGN KEY (contractor_id) REFERENCES users(id)
                )
                """;
            stmt.execute(createConsultationsTable);

            System.out.println("✅ Database tables initialized");
        } catch (SQLException e) {
            System.err.println("❌ Error initializing tables: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean registerUser(String name, String email, String password, String role, String phone, String address) {
        synchronized (DatabaseService.class) {
            try {
                String sql = "INSERT INTO users (name, email, password, role, phone, address, license_number) VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = getConnection().prepareStatement(sql);
                stmt.setString(1, name);
                stmt.setString(2, email);
                stmt.setString(3, password); // In production, hash this password
                stmt.setString(4, role);
                stmt.setString(5, phone);
                stmt.setString(6, address);
                stmt.setString(7, "");
                stmt.executeUpdate();
                stmt.close();
                System.out.println("✅ User registered: " + email);
                return true;
            } catch (SQLException e) {
                System.err.println("❌ Registration error: " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
    }

    public Map<String, Object> loginUser(String email, String password) {
        synchronized (DatabaseService.class) {
            try {
                String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
                PreparedStatement stmt = getConnection().prepareStatement(sql);
                stmt.setString(1, email);
                stmt.setString(2, password);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    Map<String, Object> user = new HashMap<>();
                    user.put("id", rs.getInt("id"));
                    user.put("name", rs.getString("name"));
                    user.put("email", rs.getString("email"));
                    user.put("role", rs.getString("role"));
                    user.put("phone", rs.getString("phone"));
                    user.put("address", rs.getString("address"));
                    rs.close();
                    stmt.close();
                    System.out.println("✅ User logged in: " + email);
                    return user;
                }
                rs.close();
                stmt.close();
            } catch (SQLException e) {
                System.err.println("❌ Login error: " + e.getMessage());
                e.printStackTrace();
            }
            return null;
        }
    }

    public List<Map<String, Object>> getProjectsByCustomer(int customerId) {
        List<Map<String, Object>> projects = new ArrayList<>();
        synchronized (DatabaseService.class) {
            try {
                String sql = "SELECT * FROM projects WHERE customer_id = ?";
                PreparedStatement stmt = getConnection().prepareStatement(sql);
                stmt.setInt(1, customerId);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    Map<String, Object> project = new HashMap<>();
                    project.put("id", rs.getInt("id"));
                    project.put("title", rs.getString("title"));
                    project.put("description", rs.getString("description"));
                    project.put("budget", rs.getDouble("budget"));
                    project.put("status", rs.getString("status"));
                    project.put("location", rs.getString("location"));
                    projects.add(project);
                }
                rs.close();
                stmt.close();
            } catch (SQLException e) {
                System.err.println("❌ Error fetching projects: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return projects;
    }

    public boolean createProject(int customerId, String title, String description, String location, Double budget) {
        synchronized (DatabaseService.class) {
            try {
                String sql = "INSERT INTO projects (customer_id, title, description, location, budget, status) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = getConnection().prepareStatement(sql);
                stmt.setInt(1, customerId);
                stmt.setString(2, title);
                stmt.setString(3, description);
                stmt.setString(4, location);
                stmt.setDouble(5, budget);
                stmt.setString(6, "pending");
                stmt.executeUpdate();
                stmt.close();
                System.out.println("✅ Project created: " + title);
                return true;
            } catch (SQLException e) {
                System.err.println("❌ Error creating project: " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
    }

    public boolean updateProject(int projectId, String title, String description, String location, Double budget) {
        synchronized (DatabaseService.class) {
            try {
                String sql = "UPDATE projects SET title = ?, description = ?, location = ?, budget = ? WHERE id = ?";
                PreparedStatement stmt = getConnection().prepareStatement(sql);
                stmt.setString(1, title);
                stmt.setString(2, description);
                stmt.setString(3, location);
                stmt.setDouble(4, budget);
                stmt.setInt(5, projectId);
                int rowsAffected = stmt.executeUpdate();
                stmt.close();
                if (rowsAffected > 0) {
                    System.out.println("✅ Project updated: " + projectId);
                    return true;
                }
                return false;
            } catch (SQLException e) {
                System.err.println("❌ Error updating project: " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
    }

    public boolean deleteProject(int projectId) {
        synchronized (DatabaseService.class) {
            try {
                String sql = "DELETE FROM projects WHERE id = ?";
                PreparedStatement stmt = getConnection().prepareStatement(sql);
                stmt.setInt(1, projectId);
                int rowsAffected = stmt.executeUpdate();
                stmt.close();
                if (rowsAffected > 0) {
                    System.out.println("✅ Project deleted: " + projectId);
                    return true;
                }
                return false;
            } catch (SQLException e) {
                System.err.println("❌ Error deleting project: " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
    }

    public List<Map<String, Object>> getAllProjects() {
        List<Map<String, Object>> projects = new ArrayList<>();
        synchronized (DatabaseService.class) {
            try {
                String sql = "SELECT p.*, u.name as customer_name FROM projects p LEFT JOIN users u ON p.customer_id = u.id";
                PreparedStatement stmt = getConnection().prepareStatement(sql);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    Map<String, Object> project = new HashMap<>();
                    project.put("id", rs.getInt("id"));
                    project.put("customer_id", rs.getInt("customer_id"));
                    project.put("title", rs.getString("title"));
                    project.put("description", rs.getString("description"));
                    project.put("location", rs.getString("location"));
                    project.put("budget", rs.getDouble("budget"));
                    project.put("status", rs.getString("status"));
                    project.put("customer_name", rs.getString("customer_name"));
                    project.put("created_at", rs.getString("created_at"));
                    projects.add(project);
                }
                rs.close();
                stmt.close();
            } catch (SQLException e) {
                System.err.println("❌ Error fetching projects: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return projects;
    }

    public static void close() {
        synchronized (DatabaseService.class) {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                    System.out.println("✅ Database connection closed");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

