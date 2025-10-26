package com.buildhub.javafx;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class LandingController implements Initializable {

    @FXML
    private Button loginBtn;

    @FXML
    private Button registerBtn;

    @FXML
    private Button getStartedBtn;
    
    private Pane rootPane;
    private List<Circle> animatedCircles = new ArrayList<>();

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
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Find the root pane and add animated background
        try {
            Parent root = loginBtn.getScene().getRoot();
            if (root instanceof Pane) {
                rootPane = (Pane) root;
                createAnimatedBackground();
            }
        } catch (Exception e) {
            // Scene not yet ready, will be called later
        }
    }
    
    private void createAnimatedBackground() {
        if (rootPane == null) return;
        
        // Create fewer floating circles for better performance
        for (int i = 0; i < 3; i++) {
            Circle circle = new Circle();
            circle.setRadius(Math.random() * 40 + 20);
            circle.setFill(javafx.scene.paint.Color.rgb(139, 92, 246, 0.08));
            circle.setStroke(javafx.scene.paint.Color.rgb(139, 92, 246, 0.2));
            circle.setStrokeWidth(1);
            
            // Random starting position
            circle.setCenterX(Math.random() * 1000);
            circle.setCenterY(Math.random() * 700);
            
            rootPane.getChildren().add(0, circle); // Add to back
            animatedCircles.add(circle);
            
            // Create simplified floating animation
            animateFloatingCircle(circle);
        }
    }
    
    private void animateFloatingCircle(Circle circle) {
        double startX = circle.getCenterX();
        double startY = circle.getCenterY();
        
        // Simplified animation with less computation
        Timeline timeline = new Timeline();
        
        KeyValue kv1 = new KeyValue(circle.centerXProperty(), 
            startX + (Math.random() - 0.5) * 150);
        KeyValue kv2 = new KeyValue(circle.centerYProperty(), 
            startY + (Math.random() - 0.5) * 150);
        
        KeyFrame kf = new KeyFrame(Duration.seconds(6), kv1, kv2);
        timeline.getKeyFrames().add(kf);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        
        timeline.play();
    }
}

