package com.buildhub.javafx;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseService {
    private static final String DB_PATH = "jdbc:sqlite:./buildhub.db";
    private Connection connection;

    public DatabaseService() {
        try {
            connection = DriverManager.getConnection(DB_PATH);
            System.out.println("✅ Connected to SQLite database from JavaFX");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean registerUser(String name, String email, String password, String role, String phone, String address) {
        try {
            String sql = "INSERT INTO users (name, email, password, role, phone, address, license_number) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, password); // In production, hash this password
            stmt.setString(4, role);
            stmt.setString(5, phone);
            stmt.setString(6, address);
            stmt.setString(7, "");
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Map<String, Object> loginUser(String email, String password) {
        try {
            String sql = "SELECT * FROM users WHERE email = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // In production, verify hashed password
                Map<String, Object> user = new HashMap<>();
                user.put("id", rs.getInt("id"));
                user.put("name", rs.getString("name"));
                user.put("email", rs.getString("email"));
                user.put("role", rs.getString("role"));
                user.put("phone", rs.getString("phone"));
                user.put("address", rs.getString("address"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Map<String, Object>> getProjectsByCustomer(int customerId) {
        List<Map<String, Object>> projects = new ArrayList<>();
        try {
            String sql = "SELECT * FROM projects WHERE customer_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

