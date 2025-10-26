package com.buildhub.javafx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
    private Label userNameLabel;
    @FXML
    private VBox projectsList;
    
    private Map<String, Object> currentUser;
    
    public void setCurrentUser(Map<String, Object> user) {
        this.currentUser = user;
        this.currentUserId = (int) user.get("id");
        if (userNameLabel != null) {
            userNameLabel.setText((String) user.get("name"));
        }
        loadProjects();
    }

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

    private void onDeleteProject(Integer projectId, String projectTitle) {
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
    }

    public void initialize() {
        loadProjects();
    }

    private void loadProjects() {
        projectsList.getChildren().clear();
        List<Map<String, Object>> projects = dbService.getProjectsByCustomer(currentUserId);
        
        if (projects.isEmpty()) {
            Label noProjectsLabel = new Label("No projects yet. Click '+ New Project' to create one!");
            noProjectsLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #6B7280;");
            projectsList.getChildren().add(noProjectsLabel);
            return;
        }

        for (Map<String, Object> project : projects) {
            String title = (String) project.get("title");
            String location = (String) project.get("location");
            Double budget = (Double) project.get("budget");
            String status = (String) project.get("status");
            Integer projectId = (Integer) project.get("id");

            // Create project card
            VBox projectCard = new VBox(10);
            projectCard.setStyle("-fx-background-color: white; -fx-background-radius: 8; -fx-padding: 15; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2);");
            projectCard.setPrefWidth(900);
            projectCard.setMinWidth(900);

            // Project title and status
            HBox titleBox = new HBox(10);
            Label titleLabel = new Label(title);
            titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #1E1B4B;");
            
            Label statusLabel = new Label(status.toUpperCase());
            statusLabel.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-padding: 5 10; -fx-background-radius: 15;");
            
            if ("pending".equals(status)) {
                statusLabel.setStyle(statusLabel.getStyle() + "-fx-background-color: #FEF3C7; -fx-text-fill: #92400E;");
            } else if ("in_progress".equals(status)) {
                statusLabel.setStyle(statusLabel.getStyle() + "-fx-background-color: #DBEAFE; -fx-text-fill: #1E40AF;");
            } else if ("completed".equals(status)) {
                statusLabel.setStyle(statusLabel.getStyle() + "-fx-background-color: #D1FAE5; -fx-text-fill: #065F46;");
            }
            
            titleBox.getChildren().addAll(titleLabel, statusLabel);
            
            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);
            
            // Action buttons
            HBox actionBox = new HBox(10);
            Button editBtn = new Button("✏️ Edit");
            editBtn.setStyle("-fx-background-color: #3B82F6; -fx-text-fill: white; -fx-cursor: hand; -fx-padding: 5 10;");
            editBtn.setOnAction(e -> onEditProject(project));
            
            Button deleteBtn = new Button("🗑️ Delete");
            deleteBtn.setStyle("-fx-background-color: #EF4444; -fx-text-fill: white; -fx-cursor: hand; -fx-padding: 5 10;");
            deleteBtn.setOnAction(e -> onDeleteProject(projectId, title));
            
            actionBox.getChildren().addAll(editBtn, deleteBtn);
            
            titleBox.getChildren().addAll(spacer, actionBox);
            
            // Project details
            Label locationLabel = new Label("📍 " + (location != null ? location : "N/A"));
            locationLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #4B5563;");
            
            Label budgetLabel = new Label("💰 Budget: ₹" + (budget != null ? String.format("%.2f", budget) : "0.00"));
            budgetLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #4B5563;");
            
            projectCard.getChildren().addAll(titleBox, locationLabel, budgetLabel);
            
            projectsList.getChildren().add(projectCard);
        }
    }
    
    @FXML
    private void onEditProject(Map<String, Object> project) {
        String currentTitle = (String) project.get("title");
        String currentDescription = (String) project.get("description");
        String currentLocation = (String) project.get("location");
        Double currentBudget = (Double) project.get("budget");
        Integer projectId = (Integer) project.get("id");
        
        // Create edit dialog
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Edit Project");
        dialog.setHeaderText("Update project details");

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        TextField titleField = new TextField(currentTitle);
        titleField.setPromptText("Project Title");
        
        TextArea descriptionField = new TextArea(currentDescription != null ? currentDescription : "");
        descriptionField.setPromptText("Description");
        descriptionField.setPrefRowCount(3);
        
        TextField locationField = new TextField(currentLocation);
        locationField.setPromptText("Location");
        
        TextField budgetField = new TextField(currentBudget != null ? currentBudget.toString() : "0");
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

        Button saveButton = (Button) dialog.getDialogPane().lookupButton(saveButtonType);
        saveButton.setDisable(false);
        
        titleField.textProperty().addListener((observable, oldValue, newValue) -> {
            saveButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
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

            if (dbService.updateProject(projectId, title, description, location, budget)) {
                showAlert("Success", "Project updated successfully!");
                loadProjects();
            } else {
                showAlert("Error", "Failed to update project");
            }
        });
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

