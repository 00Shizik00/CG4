package com.CG4.render_engine;

import com.CG4.math.Matrix4x4;
import com.CG4.math.Vector3f;


public class Camera {
    private Vector3f position;
    private Vector3f lookAt;
    private Vector3f upDirection;
    private Matrix4x4 projectionMatrix;

    public Camera(Vector3f position, Vector3f lookAt, Vector3f upDirection, float fov, float aspectRatio, float near, float far)
    {
        this.position = position;
        this.lookAt = lookAt;
        this.upDirection = upDirection;
        this.projectionMatrix = GraphicConveyor.createPerspectiveProjectionMatrix(fov, aspectRatio, near, far);

    }
    public Camera(Vector3f vector3f, Vector3f f, int i, int i1)
    {
        this(new Vector3f(0,0,10), new Vector3f(0,0,0), new Vector3f(0,1,0), 60, 1,0.1f, 1000);
    }


    public Matrix4x4 getViewMatrix() {
        return GraphicConveyor.createViewMatrix(position, lookAt, upDirection);
    }


    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getLookAt() {
        return lookAt;
    }

    public void setLookAt(Vector3f lookAt) {
        this.lookAt = lookAt;
    }

    public Vector3f getUpDirection() {
        return upDirection;
    }

    public void setUpDirection(Vector3f upDirection) {
        this.upDirection = upDirection;
    }

    public Matrix4x4 getProjectionMatrix() {
        return projectionMatrix;
    }

    public void setProjectionMatrix(Matrix4x4 projectionMatrix) {
        this.projectionMatrix = projectionMatrix;
    }
}