package com.buildhub.javafx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ContractorDashboardController {

    @FXML
    private Button myBidsBtn;
    @FXML
    private Button logoutBtn;
    @FXML
    private Label profileName;
    @FXML
    private Label rating;
    @FXML
    private Label projectsCompleted;
    @FXML
    private VBox projectsContainer;

    @FXML
    private void onViewBids() {
        showAlert("My Bids", "View all your project bids and status");
    }

    @FXML
    private void onSubmitCompletion() {
        showAlert("Submit Completion", "Submit project completion details");
    }

    @FXML
    private void onUpdatePortfolio() {
        showAlert("Update Portfolio", "Showcase your completed projects");
    }

    @FXML
    private void onBack() {
        try {
            Stage stage = (Stage) logoutBtn.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/com/buildhub/views/landing.fxml"));
            stage.setScene(new Scene(root, 1000, 700));
            stage.setTitle("BuildHub - Construction Platform");
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

