package com.buildhub.javafx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Pair;
import java.util.List;
import java.util.Map;

public class CustomerDashboardController {

    private DatabaseService dbService = new DatabaseService();
    private int currentUserId = 1; // TODO: Get from login

    @FXML
    private Button newProjectBtn;
    @FXML
    private Button logoutBtn;
    @FXML
    private Label profileName;
    @FXML
    private Label profileEmail;
    @FXML
    private TableView<Map> projectsTable;

    @FXML
    private void onNewProject() {
        // Create dialog for new project
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Create New Project");
        dialog.setHeaderText("Enter project details");

        // Set the button types
        ButtonType createButtonType = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);

        // Create the dialog fields
        TextField titleField = new TextField();
        titleField.setPromptText("Project Title");
        
        TextArea descriptionField = new TextArea();
        descriptionField.setPromptText("Description");
        descriptionField.setPrefRowCount(3);
        
        TextField locationField = new TextField();
        locationField.setPromptText("Location");
        
        TextField budgetField = new TextField();
        budgetField.setPromptText("Budget (e.g., 50000)");

        javafx.scene.layout.GridPane grid = new javafx.scene.layout.GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(20, 150, 10, 10));

        grid.add(new javafx.scene.control.Label("Title:"), 0, 0);
        grid.add(titleField, 1, 0);
        grid.add(new javafx.scene.control.Label("Description:"), 0, 1);
        grid.add(descriptionField, 1, 1);
        grid.add(new javafx.scene.control.Label("Location:"), 0, 2);
        grid.add(locationField, 1, 2);
        grid.add(new javafx.scene.control.Label("Budget:"), 0, 3);
        grid.add(budgetField, 1, 3);

        dialog.getDialogPane().setContent(grid);

        // Enable/Disable create button depending on whether title is entered
        Button createButton = (Button) dialog.getDialogPane().lookupButton(createButtonType);
        createButton.setDisable(true);

        titleField.textProperty().addListener((observable, oldValue, newValue) -> {
            createButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == createButtonType) {
                return new Pair<>(titleField.getText(), locationField.getText());
            }
            return null;
        });

        dialog.showAndWait().ifPresent(result -> {
            String title = titleField.getText();
            String description = descriptionField.getText();
            String location = locationField.getText();
            Double budget = 0.0;
            
            try {
                budget = Double.parseDouble(budgetField.getText());
            } catch (NumberFormatException e) {
                showAlert("Error", "Invalid budget amount");
                return;
            }

            if (dbService.createProject(currentUserId, title, description, location, budget)) {
                showAlert("Success", "Project created successfully!");
                loadProjects();
            } else {
                showAlert("Error", "Failed to create project");
            }
        });
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
    private void onDeleteProject() {
        TableView.TableViewSelectionModel<Map> selectionModel = projectsTable.getSelectionModel();
        if (selectionModel.getSelectedItem() != null) {
            Map<String, Object> project = selectionModel.getSelectedItem();
            int projectId = (Integer) project.get("id");
            String projectTitle = (String) project.get("title");

            // Confirm deletion
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Delete Project");
            confirmAlert.setHeaderText("Are you sure?");
            confirmAlert.setContentText("Do you want to delete project: " + projectTitle + "?");

            confirmAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    if (dbService.deleteProject(projectId)) {
                        showAlert("Success", "Project deleted successfully!");
                        loadProjects();
                    } else {
                        showAlert("Error", "Failed to delete project");
                    }
                }
            });
        } else {
            showAlert("No Selection", "Please select a project to delete");
        }
    }

    private void loadProjects() {
        List<Map<String, Object>> projects = dbService.getProjectsByCustomer(currentUserId);
        projectsTable.getItems().clear();
        for (Map<String, Object> project : projects) {
            projectsTable.getItems().add(project);
        }
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

