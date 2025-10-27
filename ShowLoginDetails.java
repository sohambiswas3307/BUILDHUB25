import java.sql.*;

public class ShowLoginDetails {
    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found");
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        String url = "jdbc:sqlite:buildhub.db";
        
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            
            System.out.println("\n╔════════════════════════════════════════════════════════════╗");
            System.out.println("║           BUILDHUB - STORED LOGIN DETAILS                 ║");
            System.out.println("╚════════════════════════════════════════════════════════════╝\n");
            
            // Get all users
            ResultSet rs = stmt.executeQuery("SELECT id, name, email, role, phone, address FROM users");
            
            int count = 0;
            while (rs.next()) {
                count++;
                System.out.println("┌────────────────────────────────────────────────────────┐");
                System.out.println("│ User #" + rs.getInt("id"));
                System.out.println("├────────────────────────────────────────────────────────┤");
                System.out.println("│ Name:    " + rs.getString("name"));
                System.out.println("│ Email:   " + rs.getString("email") + " (This is your login email)");
                System.out.println("│ Role:    " + rs.getString("role").toUpperCase());
                System.out.println("│ Phone:   " + rs.getString("phone"));
                System.out.println("│ Address: " + rs.getString("address"));
                System.out.println("└────────────────────────────────────────────────────────┘\n");
            }
            
            // Count by role
            System.out.println("\n╔════════════════════════════════════════════════════════════╗");
            System.out.println("║                    SUMMARY BY ROLE                       ║");
            System.out.println("╚════════════════════════════════════════════════════════════╝\n");
            
            ResultSet roles = stmt.executeQuery("SELECT role, COUNT(*) as count FROM users GROUP BY role");
            while (roles.next()) {
                System.out.println("  " + roles.getString("role").toUpperCase() + ": " + roles.getInt("count") + " user(s)");
            }
            
            System.out.println("\n  TOTAL USERS: " + count);
            System.out.println("\n╔════════════════════════════════════════════════════════════╗");
            System.out.println("║  IMPORTANT: Passwords are encrypted (BCrypt)            ║");
            System.out.println("║  Use the email addresses shown above to login          ║");
            System.out.println("║  Each user can only login with their registered email  ║");
            System.out.println("╚════════════════════════════════════════════════════════════╝\n");
            
        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

