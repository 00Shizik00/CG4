package com.CG4.math;

import java.util.Objects;

public class Vector3f {

    public float x;
    public float y;
    public float z;


    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3f minus(Vector3f other) {
        return new Vector3f(x - other.x, y - other.y, z - other.z);
    }

    public Vector3f plus(Vector3f other) {
        return new Vector3f(x + other.x, y + other.y, z + other.z);
    }

    public Vector3f multiply(float scalar) {
        return new Vector3f(x * scalar, y * scalar, z * scalar);
    }

    public Vector3f multiply(Matrix4x4 matrix) {
        float newX = x * matrix.m00 + y * matrix.m10 + z * matrix.m20 + 1 * matrix.m30;
        float newY = x * matrix.m01 + y * matrix.m11 + z * matrix.m21 + 1 * matrix.m31;
        float newZ = x * matrix.m02 + y * matrix.m12 + z * matrix.m22 + 1 * matrix.m32;
        return new Vector3f(newX, newY, newZ);
    }

    public float length(){
        return (float) Math.sqrt(x*x + y*y + z*z);
    }
    public Vector3f normalize(){
        float len = length();
        if(len == 0)
            return new Vector3f(0,0,0);
        return new Vector3f(x/len, y/len, z/len);
    }

    public float dotProduct(Vector3f other){
        return x* other.x + y*other.y + z*other.z;
    }
    public Vector3f calculateNormal(Vector3f v2, Vector3f v3) {
        Vector3f normal = new Vector3f(
                (v2.y - y) * (v3.z - z) - (v2.z - z) * (v3.y - y),
                (v2.z - z) * (v3.x - x) - (v2.x - x) * (v3.z - z),
                (v2.x - x) * (v3.y - y) - (v2.y - y) * (v3.x - x)
        );
        return normal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector3f vector3f = (Vector3f) o;
        return Float.compare(vector3f.x, x) == 0 && Float.compare(vector3f.y, y) == 0 && Float.compare(vector3f.z, z) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }
}