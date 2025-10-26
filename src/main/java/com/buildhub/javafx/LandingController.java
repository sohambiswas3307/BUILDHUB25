package com.buildhub.javafx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class LandingController {

    @FXML
    private Button loginBtn;

    @FXML
    private Button registerBtn;

    @FXML
    private Button getStartedBtn;

    @FXML
    private Button learnMoreBtn;

    @FXML
    private void onLogin() {
        try {
            Stage stage = (Stage) loginBtn.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/com/buildhub/views/login.fxml"));
            stage.setScene(new Scene(root, 800, 600));
            stage.setTitle("Login - BuildHub");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onRegister() {
        try {
            Stage stage = (Stage) registerBtn.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/com/buildhub/views/register.fxml"));
            stage.setScene(new Scene(root, 800, 600));
            stage.setTitle("Register - BuildHub");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onGetStarted() {
        onRegister();
    }

    @FXML
    private void onLearnMore() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About BuildHub");
        alert.setHeaderText(null);
        alert.setContentText("BuildHub connects Customers, Contractors, and Labours for efficient project management. Create projects, find professionals, and build with confidence.");
        alert.showAndWait();
    }

    @FXML
    private void onJoinCustomer() {
        try {
            Stage stage = (Stage) learnMoreBtn.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/com/buildhub/views/register.fxml"));
            stage.setScene(new Scene(root, 800, 600));
            stage.setTitle("Register as Customer - BuildHub");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onJoinContractor() {
        try {
            Stage stage = (Stage) learnMoreBtn.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/com/buildhub/views/register.fxml"));
            stage.setScene(new Scene(root, 800, 600));
            stage.setTitle("Register as Contractor - BuildHub");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onJoinLabour() {
        try {
            Stage stage = (Stage) learnMoreBtn.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/com/buildhub/views/register.fxml"));
            stage.setScene(new Scene(root, 800, 600));
            stage.setTitle("Register as Labour - BuildHub");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

