package com.buildhub.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class BuildNETFxApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            // Load the Landing Page FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/buildhub/views/landing.fxml"));
            Scene scene = new Scene(loader.load(), 1000, 700);
            
            // Set the scene
            primaryStage.setTitle("BuildNET - Construction Platform");
            primaryStage.setScene(scene);
            primaryStage.setMinWidth(900);
            primaryStage.setMinHeight(600);
            
            // Show the stage
            primaryStage.show();
            
            System.out.println("🚀 BuildNET JavaFX Desktop App Started");
            System.out.println("📱 Landing page opened successfully");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

