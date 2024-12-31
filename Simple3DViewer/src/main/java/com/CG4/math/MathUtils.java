package com.CG4.math;

public class MathUtils {
    public static Vector3f calculateBarycentricCoordinates(float x, float y, Vector3f v1, Vector3f v2, Vector3f v3){
        float area = 0.5f * (-v2.y * v3.x + v1.y * (-v2.x + v3.x) + v1.x * (v2.y - v3.y) + v2.x * v3.y);
        float s = 1 / (2* area) * (v1.y * v3.x - v1.x * v3.y + (v3.y - v1.y) * x + (v1.x - v3.x) * y);
        float t = 1 / (2*area) * (v1.x * v2.y - v1.y * v2.x + (v1.y - v2.y) * x + (v2.x - v1.x) * y);
        return new Vector3f(1-s-t, s, t);
    }
    public static boolean isPointInsideTriangle(float x, float y, Vector3f v1, Vector3f v2, Vector3f v3){
        float area = 0.5f * (-v2.y * v3.x + v1.y * (-v2.x + v3.x) + v1.x * (v2.y - v3.y) + v2.x * v3.y);
        float s = 1 / (2* area) * (v1.y * v3.x - v1.x * v3.y + (v3.y - v1.y) * x + (v1.x - v3.x) * y);
        float t = 1 / (2*area) * (v1.x * v2.y - v1.y * v2.x + (v1.y - v2.y) * x + (v2.x - v1.x) * y);
        return s > 0 && t > 0 && 1 - s - t > 0;
    }

}