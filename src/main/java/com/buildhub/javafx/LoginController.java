package com.buildhub.javafx;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;
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
            Scene scene = new Scene(root, 1000, 700);
            stage.setTitle("Register - BuildHub");
            transitionToScene(stage, scene);
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
        Scene scene = new Scene(root, 1400, 800);
        stage.setTitle(role.substring(0, 1).toUpperCase() + role.substring(1) + " Dashboard - BuildHub");
        transitionToScene(stage, scene);
    }

    @FXML
    private void onBack() {
        try {
            Stage stage = (Stage) loginBtn.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/com/buildhub/views/landing.fxml"));
            Scene scene = new Scene(root, 1000, 700);
            stage.setTitle("BuildHub - Construction Platform");
            transitionToScene(stage, scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void transitionToScene(Stage stage, Scene newScene) {
        // Quick fade out for smooth transition
        FadeTransition fadeOut = new FadeTransition(Duration.millis(250), stage.getScene().getRoot());
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(e -> {
            // Set new scene
            stage.setScene(newScene);
            // Quick fade in for smooth transition
            FadeTransition fadeIn = new FadeTransition(Duration.millis(300), newScene.getRoot());
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });
        fadeOut.play();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

