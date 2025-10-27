package com.buildhub.javafx;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class RegisterController {
    private DatabaseService dbService = new DatabaseService();

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

        // Register with database
        boolean success = dbService.registerUser(name, email, password, role, phone, address);
        
        if (success) {
            showAlert("Success", "Registration successful! Role: " + role);
            // Navigate to appropriate dashboard
            try {
                navigateToDashboard(role);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Registration Failed");
            alert.setHeaderText("Email Already Exists");
            alert.setContentText("An account with this email already exists. Please try logging in instead.");
            ButtonType loginButton = new ButtonType("Go to Login", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(loginButton, cancelButton);
            
            alert.showAndWait().ifPresent(response -> {
                if (response == loginButton) {
                    try {
                        Stage stage = (Stage) registerBtn.getScene().getWindow();
                        Parent root = FXMLLoader.load(getClass().getResource("/com/buildhub/views/login.fxml"));
                        stage.setScene(new Scene(root, 800, 600));
                        stage.setTitle("Login - BuildNET");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @FXML
    private void onLoginClick() throws Exception {
        Stage stage = (Stage) registerBtn.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/com/buildhub/views/login.fxml"));
        Scene scene = new Scene(root, 1000, 700);
        stage.setTitle("Login - BuildNET");
        transitionToScene(stage, scene);
    }

    @FXML
    private void onBack() {
        try {
            Stage stage = (Stage) registerBtn.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/com/buildhub/views/landing.fxml"));
            Scene scene = new Scene(root, 1000, 700);
            stage.setTitle("BuildNET - Construction Platform");
            transitionToScene(stage, scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void navigateToDashboard(String role) throws Exception {
        Stage stage = (Stage) registerBtn.getScene().getWindow();
        String dashboardPath = "/com/buildhub/views/" + role + "_dashboard.fxml";
        Parent root = FXMLLoader.load(getClass().getResource(dashboardPath));
        Scene scene = new Scene(root, 1400, 800);
        stage.setTitle(role.substring(0, 1).toUpperCase() + role.substring(1) + " Dashboard - BuildNET");
        transitionToScene(stage, scene);
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

