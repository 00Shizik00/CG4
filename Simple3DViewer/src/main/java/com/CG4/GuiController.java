package com.CG4;

import com.CG4.math.Matrix4x4;
import com.CG4.math.Vector3f;
import com.CG4.model.Model;
import com.CG4.objreader.ObjReader;
import com.CG4.objreader.ObjReaderException;
import com.CG4.render_engine.Camera;
import com.CG4.render_engine.GraphicConveyor;
import com.CG4.render_engine.renderEngine;
import com.CG4.scene.Scene;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

public class GuiController {
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Canvas canvas;

    @FXML
    private Button loadModelButton;
    @FXML
    private Label cameraPositionLabel;


    @FXML
    private TextField scaleXTextField;
    @FXML
    private TextField scaleYTextField;
    @FXML
    private TextField scaleZTextField;

    @FXML
    private TextField rotateXTextField;
    @FXML
    private TextField rotateYTextField;
    @FXML
    private TextField rotateZTextField;

    @FXML
    private TextField translateXTextField;
    @FXML
    private TextField translateYTextField;
    @FXML
    private TextField translateZTextField;


    private Scene scene;
    private float cameraSpeed = 0.5f;
    private float mouseX, mouseY;
    private float mouseDX, mouseDY;
    private float rotationAngleX = 0, rotationAngleY = 0;
    private boolean isDragging = false;



    @FXML
    public void initialize() {
        scene = new Scene();
        scene.addCamera(new Camera(new Vector3f(0, 0, 0), new Vector3f(0, 0, -1), 1, 1));


        canvas.widthProperty().addListener(observable -> drawFrame());
        canvas.heightProperty().addListener(observable -> drawFrame());
        canvas.setOnMousePressed(this::handleMousePressed);
        canvas.setOnMouseDragged(this::handleMouseDragged);
        canvas.setOnMouseReleased(this::handleMouseReleased);
        canvas.setFocusTraversable(true);
        canvas.setOnKeyPressed(this::handleKeyPressed);
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                drawFrame();
            }
        };
        timer.start();
        updateCameraLabels();

    }


    @FXML
    private void handleLoadModel() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model Files", "*.obj"));
        File file = fileChooser.showOpenDialog(canvas.getScene().getWindow());
        if (file == null) {
            return;
        }
        Path filePath = file.toPath();
        try {
            Model model = ObjReader.read(filePath);
            scene.setModel(model);
        } catch (IOException exception) {
            showAlert("File Error", "Error loading model from .obj file: " + exception.getMessage());
        }
        catch (ObjReaderException exception) {
            showAlert("Parse Error", "Error parsing the .obj file: " + exception.getMessage());
        }
    }
    private void drawFrame() {
        int width = (int) canvas.getWidth();
        int height = (int) canvas.getHeight();
        if(Objects.nonNull(scene.getModel()))
        {

            renderEngine.render(canvas.getGraphicsContext2D(), scene.getModel(), scene.getCurrentCamera(), width, height, scene.getModelMatrix()); // Corrected call
        }

        updateCameraLabels();
    }


    @FXML
    private void handleKeyPressed(javafx.scene.input.KeyEvent event){
        if(Objects.isNull(scene.getCurrentCamera()))
            return;

        float moveAmount = cameraSpeed;
        Vector3f moveVector = new Vector3f(0,0,0);
        switch(event.getCode()){
            case W:
                moveVector = new Vector3f(0,0,-moveAmount);
                break;
            case S:
                moveVector = new Vector3f(0,0,moveAmount);
                break;
            case A:
                moveVector = new Vector3f(-moveAmount,0,0);
                break;
            case D:
                moveVector = new Vector3f(moveAmount,0,0);
                break;
            case SHIFT:
                moveVector = new Vector3f(0,moveAmount,0);
                break;
            case CONTROL:
                moveVector = new Vector3f(0,-moveAmount,0);
                break;
        }
        if (!moveVector.equals(new Vector3f(0,0,0))){
            Matrix4x4 cameraRotation = GraphicConveyor.createViewMatrix(scene.getCurrentCamera().getPosition(), scene.getCurrentCamera().getLookAt(), scene.getCurrentCamera().getUpDirection());
            Vector3f move = transform(moveVector, cameraRotation);
            scene.getCurrentCamera().setPosition(new Vector3f(scene.getCurrentCamera().getPosition().x+move.x, scene.getCurrentCamera().getPosition().y + move.y, scene.getCurrentCamera().getPosition().z + move.z));
            scene.getCurrentCamera().setLookAt(new Vector3f(scene.getCurrentCamera().getLookAt().x+move.x, scene.getCurrentCamera().getLookAt().y + move.y, scene.getCurrentCamera().getLookAt().z + move.z));
        }

    }

    private void handleMousePressed(MouseEvent event) {
        if(event.getButton() != MouseButton.PRIMARY)
            return;
        isDragging = true;
        mouseX = (float) event.getX();
        mouseY = (float) event.getY();
    }

    private void handleMouseDragged(MouseEvent event) {
        if(!isDragging)
            return;
        if(event.getButton() != MouseButton.PRIMARY)
            return;
        mouseDX = (float) event.getX() - mouseX;
        mouseDY = (float) event.getY() - mouseY;

        rotationAngleY += mouseDX * 0.01;
        rotationAngleX += mouseDY * 0.01;

        mouseX = (float) event.getX();
        mouseY = (float) event.getY();
        Matrix4x4 rotationMatrix = Matrix4x4.createRotationMatrixX(rotationAngleX).multiply(Matrix4x4.createRotationMatrixY(rotationAngleY));

        scene.getCurrentCamera().setLookAt(transform(new Vector3f(0,0,0),rotationMatrix).add(scene.getCurrentCamera().getPosition()));

    }

    private void handleMouseReleased(MouseEvent event) {
        isDragging = false;
    }
    @FXML
    private void handleModelScale() {
        try{
            float modelScaleX = Float.parseFloat(scaleXTextField.getText());
            float modelScaleY = Float.parseFloat(scaleYTextField.getText());
            float modelScaleZ = Float.parseFloat(scaleZTextField.getText());
            Matrix4x4 scaleMatrix = Matrix4x4.createScaleMatrix(modelScaleX,modelScaleY,modelScaleZ);
            scene.setModelMatrix(scaleMatrix);

        } catch (NumberFormatException e)
        {
            showAlert("Error", "Invalid scale input");
        }
    }
    @FXML
    private void handleModelRotation() {
        try{
            float modelRotationX = (float) Math.toRadians(Float.parseFloat(rotateXTextField.getText()));
            float modelRotationY = (float) Math.toRadians(Float.parseFloat(rotateYTextField.getText()));
            float modelRotationZ = (float) Math.toRadians(Float.parseFloat(rotateZTextField.getText()));
            Matrix4x4 rotationMatrix = Matrix4x4.createRotationMatrixX(modelRotationX).multiply(Matrix4x4.createRotationMatrixY(modelRotationY)).multiply(Matrix4x4.createRotationMatrixZ(modelRotationZ));
            scene.setModelMatrix(rotationMatrix);
        } catch (NumberFormatException e)
        {
            showAlert("Error", "Invalid rotation input");
        }
    }

    @FXML
    private void handleModelTranslation() {
        try{
            float modelTranslateX = Float.parseFloat(translateXTextField.getText());
            float modelTranslateY = Float.parseFloat(translateYTextField.getText());
            float modelTranslateZ = Float.parseFloat(translateZTextField.getText());
            Matrix4x4 translationMatrix = Matrix4x4.createTranslationMatrix(modelTranslateX, modelTranslateY,modelTranslateZ);
            scene.setModelMatrix(translationMatrix);


        } catch (NumberFormatException e)
        {
            showAlert("Error", "Invalid translation input");
        }
    }



    private void updateCameraLabels(){
        if(Objects.nonNull(scene.getCurrentCamera()))
            cameraPositionLabel.setText("Position: " +String.format("%.2f, %.2f, %.2f", scene.getCurrentCamera().getPosition().x, scene.getCurrentCamera().getPosition().y, scene.getCurrentCamera().getPosition().z));
    }
    private Vector3f transform(Vector3f vector, Matrix4x4 matrix) {
        float x = vector.x * matrix.m00 + vector.y * matrix.m10 + vector.z * matrix.m20 + 1 * matrix.m30;
        float y = vector.x * matrix.m01 + vector.y * matrix.m11 + vector.z * matrix.m21 + 1 * matrix.m31;
        float z = vector.x * matrix.m02 + vector.y * matrix.m12 + vector.z * matrix.m22 + 1 * matrix.m32;
        return new Vector3f(x,y,z);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

}