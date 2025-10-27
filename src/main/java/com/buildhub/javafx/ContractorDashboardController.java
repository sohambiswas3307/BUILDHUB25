package com.buildhub.javafx;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.List;
import java.util.Map;

public class ContractorDashboardController {

    private DatabaseService dbService = new DatabaseService();
    private int currentUserId = 2; // TODO: Get from login
    
    @FXML
    private Button logoutBtn;
    @FXML
    private Label userNameLabel;
    @FXML
    private Label profileName;
    @FXML
    private Label rating;
    @FXML
    private Label projectsCompleted;
    @FXML
    private Label approvalRatio;
    @FXML
    private Label specialtyLabel;
    @FXML
    private Label budgetRangeLabel;
    @FXML
    private VBox portfolioList;

    @FXML
    private void initialize() {
        // Load contractor profile data
        loadProfileData();
        loadPortfolio();
    }
    
    private void loadProfileData() {
        // TODO: Load from database
        profileName.setText("Expert Contractor");
        rating.setText("⭐ Rating: 4.8/5");
        projectsCompleted.setText("📊 Projects: 12");
        approvalRatio.setText("✅ Approval: 95%");
        specialtyLabel.setText("Commercial Construction");
        budgetRangeLabel.setText("₹5L - ₹50L");
    }
    
    private void loadPortfolio() {
        portfolioList.getChildren().clear();
        
        // Sample portfolio items with detailed information
        String[][] projects = {
            {"🏢 Tech Tower - 2023", "Commercial building construction with modern architecture", "₹45L", "2023-06-15", "Excellent - 5.0/5", "Timely completion with premium quality"},
            {"🏗️ Bridge Construction - 2022", "Large-scale infrastructure project", "₹80L", "2022-11-20", "Excellent - 4.9/5", "Met all safety standards and deadlines"},
            {"🏠 Residential Complex - 2021", "Multi-unit residential development", "₹25L", "2021-08-10", "Excellent - 4.8/5", "Customer satisfaction exceeded expectations"}
        };
        
        for (String[] project : projects) {
            VBox projectCard = new VBox(10);
            projectCard.setStyle("-fx-background-color: white; -fx-padding: 20; -fx-background-radius: 10; -fx-border-color: #E5E7EB; -fx-border-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.05), 8, 0, 0, 2);");
            
            Label projectLabel = new Label(project[0]);
            projectLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #1E1B4B;");
            
            Label description = new Label(project[1]);
            description.setStyle("-fx-font-size: 13px; -fx-text-fill: #6B7280;");
            description.setWrapText(true);
            
            HBox info = new HBox(15);
            Label budgetLabel = new Label("💰 " + project[2]);
            budgetLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #10B981;");
            
            Label ratingLabel = new Label(project[4]);
            ratingLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #8B5CF6;");
            
            info.getChildren().addAll(budgetLabel, ratingLabel);
            
            HBox actions = new HBox(10);
            Button viewBtn = new Button("View Details");
            viewBtn.setStyle("-fx-background-color: #8B5CF6; -fx-text-fill: white; -fx-cursor: hand; -fx-padding: 8 16; -fx-background-radius: 6;");
            viewBtn.setOnAction(e -> showProjectDetails(project));
            
            actions.getChildren().addAll(viewBtn);
            
            projectCard.getChildren().addAll(projectLabel, description, info, actions);
            portfolioList.getChildren().add(projectCard);
        }
    }
    
    private void showProjectDetails(String[] project) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("📋 Project Portfolio Details");
        alert.setHeaderText(project[0]);
        
        String content = "📋 Description:\n" + project[1] + "\n\n" +
                         "💰 Project Budget: " + project[2] + "\n" +
                         "📅 Completion Date: " + project[3] + "\n" +
                         project[4] + "\n\n" +
                         "📊 Notes:\n" + project[5] + "\n\n" +
                         "✅ Status: Successfully Completed";
        
        alert.setContentText(content);
        alert.getDialogPane().setPrefWidth(500);
        alert.showAndWait();
    }

    @FXML
    private void onSubmitCompletion() {
        Dialog<Map<String, String>> dialog = new Dialog<>();
        dialog.setTitle("✅ Submit Project Completion");
        dialog.setHeaderText("Enter completed project details");
        
        ButtonType submitButtonType = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(submitButtonType, ButtonType.CANCEL);
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        
        TextField projectTitleField = new TextField();
        projectTitleField.setPromptText("Project Title");
        TextArea projectDescriptionArea = new TextArea();
        projectDescriptionArea.setPromptText("Project Description");
        projectDescriptionArea.setPrefRowCount(3);
        DatePicker completionDatePicker = new DatePicker();
        completionDatePicker.setPromptText("Completion Date");
        
        grid.add(new Label("Project Title:"), 0, 0);
        grid.add(projectTitleField, 1, 0);
        grid.add(new Label("Description:"), 0, 1);
        grid.add(projectDescriptionArea, 1, 1);
        grid.add(new Label("Completion Date:"), 0, 2);
        grid.add(completionDatePicker, 1, 2);
        
        dialog.getDialogPane().setContent(grid);
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == submitButtonType) {
                Map<String, String> result = new java.util.HashMap<>();
                result.put("title", projectTitleField.getText());
                result.put("description", projectDescriptionArea.getText());
                result.put("date", completionDatePicker.getValue() != null ? completionDatePicker.getValue().toString() : "");
                return result;
            }
            return null;
        });
        
        dialog.showAndWait().ifPresent(result -> {
            if (!result.get("title").isEmpty()) {
                showAlert("Success", "Project completion submitted successfully!");
                loadPortfolio(); // Refresh portfolio
            }
        });
    }
    
    @FXML
    private void onUpdatePortfolio() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("📸 Update Portfolio");
        dialog.setHeaderText("Add a new portfolio item");
        
        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        
        TextField projectNameField = new TextField();
        projectNameField.setPromptText("Project Name");
        TextArea descriptionArea = new TextArea();
        descriptionArea.setPromptText("Project Description");
        descriptionArea.setPrefRowCount(3);
        
        grid.add(new Label("Project Name:"), 0, 0);
        grid.add(projectNameField, 1, 0);
        grid.add(new Label("Description:"), 0, 1);
        grid.add(descriptionArea, 1, 1);
        
        dialog.getDialogPane().setContent(grid);
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                return projectNameField.getText();
            }
            return null;
        });
        
        dialog.showAndWait().ifPresent(result -> {
            if (result != null && !result.isEmpty()) {
                showAlert("Success", "Portfolio item added: " + result);
                loadPortfolio(); // Refresh portfolio
            }
        });
    }
    
    @FXML
    private void onUpdateProfile() {
        Dialog<Map<String, String>> dialog = new Dialog<>();
        dialog.setTitle("💼 Update Profile");
        dialog.setHeaderText("Update your contractor profile information");
        
        ButtonType updateButtonType = new ButtonType("Update", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(updateButtonType, ButtonType.CANCEL);
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        
        TextField specialtyField = new TextField();
        specialtyField.setText(specialtyLabel.getText());
        specialtyField.setPromptText("Specialty/Domain");
        
        TextField budgetMinField = new TextField();
        budgetMinField.setPromptText("Min Budget (₹)");
        
        TextField budgetMaxField = new TextField();
        budgetMaxField.setPromptText("Max Budget (₹)");
        
        grid.add(new Label("Specialty:"), 0, 0);
        grid.add(specialtyField, 1, 0);
        grid.add(new Label("Budget Min:"), 0, 1);
        grid.add(budgetMinField, 1, 1);
        grid.add(new Label("Budget Max:"), 0, 2);
        grid.add(budgetMaxField, 1, 2);
        
        dialog.getDialogPane().setContent(grid);
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == updateButtonType) {
                Map<String, String> result = new java.util.HashMap<>();
                result.put("specialty", specialtyField.getText());
                result.put("budgetMin", budgetMinField.getText());
                result.put("budgetMax", budgetMaxField.getText());
                return result;
            }
            return null;
        });
        
        dialog.showAndWait().ifPresent(result -> {
            if (result != null && !result.get("specialty").isEmpty()) {
                specialtyLabel.setText(result.get("specialty"));
                budgetRangeLabel.setText("₹" + result.get("budgetMin") + " - ₹" + result.get("budgetMax"));
                showAlert("Success", "Profile updated successfully!");
            }
        });
    }
    
    @FXML
    private void navigateToDashboard() {
        showAlert("Dashboard", "You are on the Dashboard");
    }
    
    @FXML
    private void navigateToSettings() {
        showAlert("Settings", "Settings page coming soon!");
    }

    @FXML
    private void onBack() {
        try {
            Stage stage = (Stage) logoutBtn.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/com/buildhub/views/landing.fxml"));
            Scene scene = new Scene(root, 1000, 700);
            stage.setTitle("BuildNET - Construction Platform");
            transitionToScene(stage, scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
