package com.buildhub.javafx;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LandingController {

    @FXML
    private Button loginBtn;

    @FXML
    private Button registerBtn;

    @FXML
    private Button getStartedBtn;

    @FXML
    private void onLogin() {
        try {
            Stage stage = (Stage) loginBtn.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/com/buildhub/views/login.fxml"));
            Scene scene = new Scene(root, 1000, 700);
            stage.setTitle("Login - BuildHub");
            transitionToScene(stage, scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onRegister() {
        try {
            Stage stage = (Stage) registerBtn.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/com/buildhub/views/register.fxml"));
            Scene scene = new Scene(root, 1000, 700);
            stage.setTitle("Register - BuildHub");
            transitionToScene(stage, scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onGetStarted() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("🏗️ Welcome to BuildHub");
        alert.setHeaderText("Your One-Stop Platform for Construction Projects");
        
        String content = "BuildHub connects Customers, Contractors, and Labours for efficient construction project management.\n\n" +
                        "✨ Key Features:\n\n" +
                        "👤 For Customers:\n" +
                        "  • Create and manage construction projects\n" +
                        "  • Track budgets and purchased items\n" +
                        "  • Hire contractors and labours\n" +
                        "  • Rate and review completed work\n\n" +
                        "🏗️ For Contractors:\n" +
                        "  • Bid on construction projects\n" +
                        "  • Showcase your portfolio\n" +
                        "  • Build your reputation through ratings\n" +
                        "  • Manage teams and project progress\n\n" +
                        "🛠️ For Labours:\n" +
                        "  • Find job opportunities\n" +
                        "  • Set your hourly rate\n" +
                        "  • Track work history\n" +
                        "  • Build your professional profile\n\n" +
                        "Ready to get started? Click 'Register' to create your account!";
        
        alert.setContentText(content);
        alert.getDialogPane().setPrefWidth(600);
        alert.showAndWait();
    }

    @FXML
    private void onJoinCustomer() {
        try {
            Stage stage = (Stage) registerBtn.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/com/buildhub/views/register.fxml"));
            Scene scene = new Scene(root, 1000, 700);
            stage.setTitle("Register as Customer - BuildHub");
            transitionToScene(stage, scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onJoinContractor() {
        try {
            Stage stage = (Stage) registerBtn.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/com/buildhub/views/register.fxml"));
            Scene scene = new Scene(root, 1000, 700);
            stage.setTitle("Register as Contractor - BuildHub");
            transitionToScene(stage, scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onJoinLabour() {
        try {
            Stage stage = (Stage) registerBtn.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/com/buildhub/views/register.fxml"));
            Scene scene = new Scene(root, 1000, 700);
            stage.setTitle("Register as Labour - BuildHub");
            transitionToScene(stage, scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void transitionToScene(Stage stage, Scene newScene) {
        // Fade out current scene
        FadeTransition fadeOut = new FadeTransition(Duration.millis(200), stage.getScene().getRoot());
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(e -> {
            // Set new scene
            stage.setScene(newScene);
            // Fade in new scene
            FadeTransition fadeIn = new FadeTransition(Duration.millis(300), newScene.getRoot());
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });
        fadeOut.play();
    }
}

