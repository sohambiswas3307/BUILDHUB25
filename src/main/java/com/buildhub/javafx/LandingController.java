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
        
        // Create floating circles
        for (int i = 0; i < 5; i++) {
            Circle circle = new Circle();
            circle.setRadius(Math.random() * 50 + 20);
            circle.setFill(javafx.scene.paint.Color.rgb(139, 92, 246, 0.1));
            circle.setStroke(javafx.scene.paint.Color.rgb(139, 92, 246, 0.3));
            circle.setStrokeWidth(2);
            
            // Random starting position
            circle.setCenterX(Math.random() * 1000);
            circle.setCenterY(Math.random() * 700);
            
            rootPane.getChildren().add(0, circle); // Add to back
            animatedCircles.add(circle);
            
            // Create floating animation
            animateFloatingCircle(circle);
        }
    }
    
    private void animateFloatingCircle(Circle circle) {
        double startX = circle.getCenterX();
        double startY = circle.getCenterY();
        
        // Create parallel animations for X and Y
        Timeline timeline = new Timeline();
        
        KeyValue kv1 = new KeyValue(circle.centerXProperty(), 
            startX + (Math.random() - 0.5) * 200, Interpolator.EASE_BOTH);
        KeyValue kv2 = new KeyValue(circle.centerYProperty(), 
            startY + (Math.random() - 0.5) * 200, Interpolator.EASE_BOTH);
        
        KeyFrame kf = new KeyFrame(Duration.seconds(8 + Math.random() * 4), kv1, kv2);
        timeline.getKeyFrames().add(kf);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        
        // Opacity animation for subtle fade
        FadeTransition fade = new FadeTransition(Duration.seconds(4 + Math.random() * 2), circle);
        fade.setFromValue(0.3);
        fade.setToValue(0.6);
        fade.setCycleCount(Animation.INDEFINITE);
        fade.setAutoReverse(true);
        
        timeline.play();
        fade.play();
    }
}

