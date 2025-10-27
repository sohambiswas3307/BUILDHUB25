package com.buildhub.javafx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LabourDashboardController {

    @FXML
    private Button myJobsBtn;
    @FXML
    private Button logoutBtn;
    @FXML
    private Label profileName;
    @FXML
    private Label hourlyRate;
    @FXML
    private Label workHistoryCount;
    @FXML
    private VBox jobsContainer;

    @FXML
    private void onViewJobs() {
        showAlert("My Jobs", "View your work history and active jobs");
    }

    @FXML
    private void onBrowseJobs() {
        showAlert("Browse Jobs", "Search and apply for available positions");
    }

    @FXML
    private void onUpdateAvailability() {
        showAlert("Update Availability", "Set your work hours and availability");
    }

    @FXML
    private void onBack() {
        try {
            Stage stage = (Stage) logoutBtn.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/com/buildhub/views/landing.fxml"));
            stage.setScene(new Scene(root, 1000, 700));
            stage.setTitle("BuildNET - Construction Platform");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onLogout() {
        onBack();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

