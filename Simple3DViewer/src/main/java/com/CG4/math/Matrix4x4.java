package com.CG4.math;


public class Matrix4x4 {

    public float m00, m01, m02, m03;
    public float m10, m11, m12, m13;
    public float m20, m21, m22, m23;
    public float m30, m31, m32, m33;


    public Matrix4x4(float m00, float m01, float m02, float m03,
                     float m10, float m11, float m12, float m13,
                     float m20, float m21, float m22, float m23,
                     float m30, float m31, float m32, float m33) {
        this.m00 = m00;
        this.m01 = m01;
        this.m02 = m02;
        this.m03 = m03;
        this.m10 = m10;
        this.m11 = m11;
        this.m12 = m12;
        this.m13 = m13;
        this.m20 = m20;
        this.m21 = m21;
        this.m22 = m22;
        this.m23 = m23;
        this.m30 = m30;
        this.m31 = m31;
        this.m32 = m32;
        this.m33 = m33;
    }

    public Matrix4x4() {
        this(1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1);
    }

    public Matrix4x4(int a) {
        this(0, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 0);
    }


    public Matrix4x4 multiply(Matrix4x4 other) {
        float newM00 = m00 * other.m00 + m01 * other.m10 + m02 * other.m20 + m03 * other.m30;
        float newM01 = m00 * other.m01 + m01 * other.m11 + m02 * other.m21 + m03 * other.m31;
        float newM02 = m00 * other.m02 + m01 * other.m12 + m02 * other.m22 + m03 * other.m32;
        float newM03 = m00 * other.m03 + m01 * other.m13 + m02 * other.m23 + m03 * other.m33;

        float newM10 = m10 * other.m00 + m11 * other.m10 + m12 * other.m20 + m13 * other.m30;
        float newM11 = m10 * other.m01 + m11 * other.m11 + m12 * other.m21 + m13 * other.m31;
        float newM12 = m10 * other.m02 + m11 * other.m12 + m12 * other.m22 + m13 * other.m32;
        float newM13 = m10 * other.m03 + m11 * other.m13 + m12 * other.m23 + m13 * other.m33;

        float newM20 = m20 * other.m00 + m21 * other.m10 + m22 * other.m20 + m23 * other.m30;
        float newM21 = m20 * other.m01 + m21 * other.m11 + m22 * other.m21 + m23 * other.m31;
        float newM22 = m20 * other.m02 + m21 * other.m12 + m22 * other.m22 + m23 * other.m32;
        float newM23 = m20 * other.m03 + m21 * other.m13 + m22 * other.m23 + m23 * other.m33;

        float newM30 = m30 * other.m00 + m31 * other.m10 + m32 * other.m20 + m33 * other.m30;
        float newM31 = m30 * other.m01 + m31 * other.m11 + m32 * other.m21 + m33 * other.m31;
        float newM32 = m30 * other.m02 + m31 * other.m12 + m32 * other.m22 + m33 * other.m32;
        float newM33 = m30 * other.m03 + m31 * other.m13 + m32 * other.m23 + m33 * other.m33;

        return new Matrix4x4(newM00, newM01, newM02, newM03,
                newM10, newM11, newM12, newM13,
                newM20, newM21, newM22, newM23,
                newM30, newM31, newM32, newM33);
    }


    public Vector3f multiply(Vector3f vector) {
        float newX = vector.x * m00 + vector.y * m01 + vector.z * m02 + 1 * m03;
        float newY = vector.x * m10 + vector.y * m11 + vector.z * m12 + 1 * m13;
        float newZ = vector.x * m20 + vector.y * m21 + vector.z * m22 + 1 * m23;
        return new Vector3f(newX, newY, newZ);
    }


    public static Matrix4x4 createTranslationMatrix(float x, float y, float z) {
        return new Matrix4x4(1, 0, 0, x,
                0, 1, 0, y,
                0, 0, 1, z,
                0, 0, 0, 1);
    }

    public static Matrix4x4 createRotationMatrixX(float angle) {
        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);
        return new Matrix4x4(1, 0, 0, 0,
                0, cos, -sin, 0,
                0, sin, cos, 0,
                0, 0, 0, 1);
    }

    public static Matrix4x4 createRotationMatrixY(float angle) {
        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);
        return new Matrix4x4(cos, 0, sin, 0,
                0, 1, 0, 0,
                -sin, 0, cos, 0,
                0, 0, 0, 1);
    }

    public static Matrix4x4 createRotationMatrixZ(float angle) {
        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);
        return new Matrix4x4(cos, -sin, 0, 0,
                sin, cos, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1);
    }

    public static Matrix4x4 createScaleMatrix(float x, float y, float z) {
        return new Matrix4x4(x, 0, 0, 0,
                0, y, 0, 0,
                0, 0, z, 0,
                0, 0, 0, 1);
    }
}