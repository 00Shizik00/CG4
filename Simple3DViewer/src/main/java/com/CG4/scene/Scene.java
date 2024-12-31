package com.CG4.scene;

import com.CG4.math.Matrix4x4;
import com.CG4.model.Model;
import com.CG4.render_engine.Camera;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scene {
    private  List<Camera> cameras;
    private int currentCameraIndex;

    private Map<String, Model> models;
    private Map<String, Matrix4x4> modelMatrices;
    private String currentModel;


    public Scene(){
        cameras = new ArrayList<>();
        currentCameraIndex = 0;
        models = new HashMap<>();
        modelMatrices = new HashMap<>();
        currentModel = null;
    }
    public List<Camera> getCameras(){
        return cameras;
    }
    public void addCamera(Camera camera) {
        this.cameras.add(camera);
    }
    public Camera getCurrentCamera(){
        if(cameras.isEmpty())
            return new Camera(new Vector3f(0, 0, 0), new Vector3f(0, 0, -1), 1, 1);
        return cameras.get(currentCameraIndex);
    }

    public void setCurrentCamera(int index){
        if(index >= 0 && index < cameras.size())
            currentCameraIndex = index;
    }

    public void addModel(String modelName, Model model){
        models.put(modelName, model);
        modelMatrices.put(modelName, new Matrix4x4());
        if(currentModel == null)
            currentModel = modelName;
    }

    public void setCurrentModel(String modelName){
        if(models.containsKey(modelName))
            currentModel = modelName;
    }

    public Model getCurrentModel(){
        if(currentModel == null)
            return null;
        return models.get(currentModel);
    }
    public Matrix4x4 getCurrentModelMatrix(){
        if(currentModel == null)
            return null;
        return modelMatrices.get(currentModel);
    }
    public void setModelMatrix(String modelName, Matrix4x4 matrix){
        if(modelMatrices.containsKey(modelName))
            modelMatrices.put(modelName, matrix);
    }


    public Map<String, Model> getModels() {
        return models;
    }
    public Map<String, Matrix4x4> getModelMatrices(){
        return modelMatrices;
    }
}