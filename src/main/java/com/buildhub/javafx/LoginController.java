package com.buildhub.javafx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.util.Map;

public class LoginController {
    private DatabaseService dbService = new DatabaseService();

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginBtn;

    @FXML
    private Hyperlink registerLink;

    @FXML
    private void onLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please enter both email and password");
            return;
        }

        // Login with database
        Map<String, Object> user = dbService.loginUser(email, password);
        
        if (user != null) {
            String role = (String) user.get("role");
            showAlert("Success", "Login successful! Welcome, " + user.get("name"));
            
            // Navigate to appropriate dashboard
            try {
                navigateToDashboard(role);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            showAlert("Error", "Invalid email or password");
        }
    }

    @FXML
    private void onRegisterClick() {
        try {
            Stage stage = (Stage) loginBtn.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/com/buildhub/views/register.fxml"));
            stage.setScene(new Scene(root, 1000, 700));
            stage.setTitle("Register - BuildHub");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void navigateToCustomerDashboard() throws Exception {
        navigateToDashboard("customer");
    }
    
    private void navigateToDashboard(String role) throws Exception {
        Stage stage = (Stage) loginBtn.getScene().getWindow();
        String dashboardPath = "/com/buildhub/views/" + role + "_dashboard.fxml";
        Parent root = FXMLLoader.load(getClass().getResource(dashboardPath));
        stage.setScene(new Scene(root, 1200, 800));
        stage.setTitle(role.substring(0, 1).toUpperCase() + role.substring(1) + " Dashboard - BuildHub");
    }

    @FXML
    private void onBack() {
        try {
            Stage stage = (Stage) loginBtn.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/com/buildhub/views/landing.fxml"));
            stage.setScene(new Scene(root, 1000, 700));
            stage.setTitle("BuildHub - Construction Platform");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

