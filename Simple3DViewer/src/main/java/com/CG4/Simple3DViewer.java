package com.CG4;

import com.CG4.gui.GuiController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Simple3DViewer extends Application {

    @Override
    public void start(Stage stage) {
        try {
            BorderPane root = new BorderPane();
            Canvas canvas = new Canvas(1600, 900); // Initial size (you can make it dynamic)
            root.setCenter(canvas);

            Scene scene = new Scene(root, 1600, 900); // Set scene size

            GuiController controller = new GuiController(canvas);
            stage.setMinWidth(800);
            stage.setMinHeight(600);
            stage.setTitle("Simple 3D Viewer");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.err.println("Error initializing application: " + e.getMessage());
            e.printStackTrace();
            // Optionally, show an alert dialog to the user.
        }

    }


    public static void main(String[] args) {
        launch(args);
    }
}