package com.CG4.math;


public class TransformUtils {
    public static Vector3f transform(Vector3f vector, Matrix4x4 matrix) {
        float x = vector.x * matrix.m00 + vector.y * matrix.m10 + vector.z * matrix.m20 + 1 * matrix.m30;
        float y = vector.x * matrix.m01 + vector.y * matrix.m11 + vector.z * matrix.m21 + 1 * matrix.m31;
        float z = vector.x * matrix.m02 + vector.y * matrix.m12 + vector.z * matrix.m22 + 1 * matrix.m32;
        return new Vector3f(x,y,z);
    }

    public static Vector3f calculateCentroid(java.util.List<Vector3f> vertices) {
        if (vertices == null || vertices.isEmpty()) {
            return new Vector3f(0,0,0);
        }
        float xSum = 0;
        float ySum = 0;
        float zSum = 0;

        for (Vector3f vertex : vertices) {
            xSum += vertex.x;
            ySum += vertex.y;
            zSum += vertex.z;
        }

        return new Vector3f(xSum/vertices.size(), ySum / vertices.size(), zSum/ vertices.size());
    }
}