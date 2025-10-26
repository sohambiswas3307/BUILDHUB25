package com.buildhub.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;

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
        showAlert("Login", "Opening login page...");
    }

    @FXML
    private void onRegister() {
        showAlert("Register", "Opening registration page...");
    }

    @FXML
    private void onGetStarted() {
        showAlert("Get Started", "Welcome to BuildHub! Let's build something amazing together.");
    }

    @FXML
    private void onLearnMore() {
        showAlert("Learn More", "BuildHub connects Customers, Contractors, and Labours for efficient project management.");
    }

    @FXML
    private void onJoinCustomer() {
        showAlert("Join as Customer", "Register as a Customer to post projects and hire skilled professionals.");
    }

    @FXML
    private void onJoinContractor() {
        showAlert("Join as Contractor", "Register as a Contractor to bid on projects and manage your portfolio.");
    }

    @FXML
    private void onJoinLabour() {
        showAlert("Join as Labour", "Register as Labour to find job opportunities and build your reputation.");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

