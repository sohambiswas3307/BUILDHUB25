package com.buildhub.javafx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class RegisterController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextArea addressField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private RadioButton customerRadio;
    @FXML
    private RadioButton contractorRadio;
    @FXML
    private RadioButton labourRadio;
    @FXML
    private ToggleGroup roleGroup;
    @FXML
    private Button registerBtn;
    @FXML
    private Hyperlink loginLink;

    @FXML
    private void onRegister() {
        String name = nameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String address = addressField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please fill all fields");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showAlert("Error", "Passwords do not match");
            return;
        }

        // Determine role
        String role = "customer";
        if (contractorRadio.isSelected()) {
            role = "contractor";
        } else if (labourRadio.isSelected()) {
            role = "labour";
        }

        // TODO: Implement actual registration with backend
        showAlert("Registration", "Registration will connect to backend API. Role: " + role);
        
        // Navigate to appropriate dashboard
        try {
            navigateToDashboard(role);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onLoginClick() throws Exception {
        Stage stage = (Stage) registerBtn.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/com/buildhub/views/login.fxml"));
        stage.setScene(new Scene(root, 800, 600));
        stage.setTitle("Login - BuildHub");
    }

    private void navigateToDashboard(String role) throws Exception {
        Stage stage = (Stage) registerBtn.getScene().getWindow();
        String dashboardPath = "/com/buildhub/views/" + role + "_dashboard.fxml";
        Parent root = FXMLLoader.load(getClass().getResource(dashboardPath));
        stage.setScene(new Scene(root, 1200, 800));
        stage.setTitle(role.substring(0, 1).toUpperCase() + role.substring(1) + " Dashboard - BuildHub");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

