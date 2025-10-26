package com.buildhub.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class BuildHubFxApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            // Load the Login FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/buildhub/views/login.fxml"));
            Scene scene = new Scene(loader.load(), 800, 600);
            
            // Set the scene
            primaryStage.setTitle("BuildHub - Login");
            primaryStage.setScene(scene);
            primaryStage.setMinWidth(800);
            primaryStage.setMinHeight(600);
            
            // Show the stage
            primaryStage.show();
            
            System.out.println("🚀 BuildHub JavaFX Desktop App Started");
            System.out.println("📱 Login window opened successfully");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

