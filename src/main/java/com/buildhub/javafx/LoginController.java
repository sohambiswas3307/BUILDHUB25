package com.buildhub.javafx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginController {

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

        // TODO: Implement actual login logic with backend
        showAlert("Login", "Login functionality will connect to backend API");
        
        // For now, navigate to customer dashboard as demo
        try {
            navigateToCustomerDashboard();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onRegisterClick() {
        try {
            Stage stage = (Stage) loginBtn.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/com/buildhub/views/register.fxml"));
            stage.setScene(new Scene(root, 800, 600));
            stage.setTitle("Register - BuildHub");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void navigateToCustomerDashboard() throws Exception {
        Stage stage = (Stage) loginBtn.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/com/buildhub/views/customer_dashboard.fxml"));
        stage.setScene(new Scene(root, 1200, 800));
        stage.setTitle("Customer Dashboard - BuildHub");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

