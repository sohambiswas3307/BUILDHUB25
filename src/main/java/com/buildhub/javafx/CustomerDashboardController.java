package com.buildhub.javafx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class CustomerDashboardController {

    @FXML
    private Button newProjectBtn;
    @FXML
    private Button logoutBtn;
    @FXML
    private Label profileName;
    @FXML
    private Label profileEmail;
    @FXML
    private TableView projectsTable;

    @FXML
    private void onNewProject() {
        showAlert("New Project", "Opening project creation form...");
    }

    @FXML
    private void onAddItem() {
        showAlert("Add Item", "Add purchased items to track costs");
    }

    @FXML
    private void onViewReports() {
        showAlert("Reports", "View detailed project reports and analytics");
    }

    @FXML
    private void onLogout() {
        try {
            Stage stage = (Stage) logoutBtn.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/com/buildhub/views/landing.fxml"));
            stage.setScene(new Scene(root, 900, 600));
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

