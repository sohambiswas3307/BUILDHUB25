package com.buildhub.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseViewer {
    
    public static void viewUsers() {
        String url = "jdbc:sqlite:buildhub.db";
        
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            
            System.out.println("\n============================================");
            System.out.println("BUILDHUB USER LOGIN DETAILS");
            System.out.println("============================================");
            
            // Get all users
            ResultSet rs = stmt.executeQuery("SELECT id, name, email, role, phone, address FROM users");
            
            int count = 0;
            while (rs.next()) {
                count++;
                System.out.println("\nUser #" + rs.getInt("id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Role: " + rs.getString("role"));
                System.out.println("Phone: " + rs.getString("phone"));
                System.out.println("Address: " + rs.getString("address"));
                System.out.println("-".repeat(50));
            }
            
            // Count by role
            System.out.println("\n\nUSER COUNT BY ROLE:");
            ResultSet roles = stmt.executeQuery("SELECT role, COUNT(*) as count FROM users GROUP BY role");
            while (roles.next()) {
                System.out.println(roles.getString("role") + ": " + roles.getInt("count"));
            }
            
            System.out.println("\nTotal Users: " + count);
            System.out.println("\n============================================");
            
            // View projects
            System.out.println("\n\n============================================");
            System.out.println("PROJECTS IN DATABASE");
            System.out.println("============================================");
            
            ResultSet projects = stmt.executeQuery("SELECT id, title, description, budget, status FROM projects");
            int projCount = 0;
            while (projects.next()) {
                projCount++;
                System.out.println("\nProject #" + projects.getInt("id"));
                System.out.println("Title: " + projects.getString("title"));
                System.out.println("Description: " + projects.getString("description"));
                System.out.println("Budget: " + projects.getString("budget"));
                System.out.println("Status: " + projects.getString("status"));
                System.out.println("-".repeat(50));
            }
            
            System.out.println("\nTotal Projects: " + projCount);
            System.out.println("\n============================================");
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        viewUsers();
    }
}

