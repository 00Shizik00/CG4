package com.CG4.render_engine;

import com.CG4.math.Matrix4x4;
import com.CG4.math.Vector3f;

public class GraphicConveyor {
    public static Matrix4x4 createViewMatrix(Vector3f cameraPos, Vector3f lookAtPos, Vector3f upDirection){

        Vector3f zAxis = new Vector3f(
                cameraPos.x - lookAtPos.x,
                cameraPos.y - lookAtPos.y,
                cameraPos.z - lookAtPos.z
        );
        normalize(zAxis);
        Vector3f xAxis = crossProduct(upDirection, zAxis);
        normalize(xAxis);
        Vector3f yAxis = crossProduct(zAxis, xAxis);
        normalize(yAxis);
        return  new Matrix4x4(
                xAxis.x, yAxis.x, zAxis.x, 0,
                xAxis.y, yAxis.y, zAxis.y, 0,
                xAxis.z, yAxis.z, zAxis.z, 0,
                -dotProduct(xAxis, cameraPos), -dotProduct(yAxis, cameraPos),-dotProduct(zAxis,cameraPos), 1
        );
    }
    public static Matrix4x4 createPerspectiveProjectionMatrix(float fov, float aspectRatio, float near, float far){

        float tanHalfFov = (float) Math.tan(Math.toRadians(fov / 2));

        return new Matrix4x4(1 / (aspectRatio * tanHalfFov), 0, 0, 0,
                0, 1 / tanHalfFov, 0, 0,
                0, 0, -(far + near) / (far - near), -(2 * far * near) / (far - near),
                0, 0, -1, 0);
    }

    private static float dotProduct(Vector3f a, Vector3f b){
        return a.x * b.x + a.y * b.y + a.z * b.z;
    }
    private static Vector3f crossProduct(Vector3f a, Vector3f b) {
        return new Vector3f(
                a.y * b.z - a.z * b.y,
                a.z * b.x - a.x * b.z,
                a.x * b.y - a.y * b.x
        );
    }
    private static void normalize(Vector3f vector) {
        float length = (float) Math.sqrt(vector.x * vector.x + vector.y * vector.y + vector.z * vector.z);
        if(length == 0)
            return;
        vector.x /= length;
        vector.y /= length;
        vector.z /= length;
    }
}
