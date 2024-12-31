package com.CG4.math;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Matrix4x4Test {

    @Test
    void testMultiply() {
        Matrix4x4 matrix1 = new Matrix4x4(
                1, 2, 3, 4,
                5, 6, 7, 8,
                9, 10, 11, 12,
                13, 14, 15, 16
        );
        Matrix4x4 matrix2 = new Matrix4x4(
                16, 15, 14, 13,
                12, 11, 10, 9,
                8, 7, 6, 5,
                4, 3, 2, 1
        );
        Matrix4x4 expectedResult = new Matrix4x4(
                100, 88, 76, 64,
                284, 256, 228, 200,
                468, 424, 380, 336,
                652, 592, 532, 472
        );
        Matrix4x4 result = matrix1.multiply(matrix2);
        assertEquals(expectedResult.m00, result.m00, 1e-5);
        assertEquals(expectedResult.m01, result.m01, 1e-5);
        assertEquals(expectedResult.m02, result.m02, 1e-5);
        assertEquals(expectedResult.m03, result.m03, 1e-5);
        assertEquals(expectedResult.m10, result.m10, 1e-5);
        assertEquals(expectedResult.m11, result.m11, 1e-5);
        assertEquals(expectedResult.m12, result.m12, 1e-5);
        assertEquals(expectedResult.m13, result.m13, 1e-5);
        assertEquals(expectedResult.m20, result.m20, 1e-5);
        assertEquals(expectedResult.m21, result.m21, 1e-5);
        assertEquals(expectedResult.m22, result.m22, 1e-5);
        assertEquals(expectedResult.m23, result.m23, 1e-5);
        assertEquals(expectedResult.m30, result.m30, 1e-5);
        assertEquals(expectedResult.m31, result.m31, 1e-5);
        assertEquals(expectedResult.m32, result.m32, 1e-5);
        assertEquals(expectedResult.m33, result.m33, 1e-5);

    }

    @Test
    void testMultiplyVector() {
        Matrix4x4 matrix = new Matrix4x4(
                1, 2, 3, 4,
                5, 6, 7, 8,
                9, 10, 11, 12,
                13, 14, 15, 16
        );
        Vector3f vector = new Vector3f(1,2,3);
        Vector3f expectedResult = new Vector3f(18, 46, 74);
        Vector3f result = matrix.multiply(vector);
        assertEquals(expectedResult.x, result.x, 1e-5);
        assertEquals(expectedResult.y, result.y, 1e-5);
        assertEquals(expectedResult.z, result.z, 1e-5);
    }

    @Test
    void testCreateTranslationMatrix(){
        Matrix4x4 translation = Matrix4x4.createTranslationMatrix(1,2,3);
        Matrix4x4 expectedResult = new Matrix4x4(
                1, 0, 0, 1,
                0, 1, 0, 2,
                0, 0, 1, 3,
                0, 0, 0, 1
        );
        assertEquals(expectedResult.m00, translation.m00, 1e-5);
        assertEquals(expectedResult.m01, translation.m01, 1e-5);
        assertEquals(expectedResult.m02, translation.m02, 1e-5);
        assertEquals(expectedResult.m03, translation.m03, 1e-5);
        assertEquals(expectedResult.m10, translation.m10, 1e-5);
        assertEquals(expectedResult.m11, translation.m11, 1e-5);
        assertEquals(expectedResult.m12, translation.m12, 1e-5);
        assertEquals(expectedResult.m13, translation.m13, 1e-5);
        assertEquals(expectedResult.m20, translation.m20, 1e-5);
        assertEquals(expectedResult.m21, translation.m21, 1e-5);
        assertEquals(expectedResult.m22, translation.m22, 1e-5);
        assertEquals(expectedResult.m23, translation.m23, 1e-5);
        assertEquals(expectedResult.m30, translation.m30, 1e-5);
        assertEquals(expectedResult.m31, translation.m31, 1e-5);
        assertEquals(expectedResult.m32, translation.m32, 1e-5);
        assertEquals(expectedResult.m33, translation.m33, 1e-5);
    }
    @Test
    void testCreateRotationXMatrix(){
        Matrix4x4 rotation = Matrix4x4.createRotationMatrixX((float) Math.PI/2);
        Matrix4x4 expectedResult = new Matrix4x4(
                1, 0, 0, 0,
                0, 0, -1, 0,
                0, 1, 0, 0,
                0, 0, 0, 1
        );
        assertEquals(expectedResult.m00, rotation.m00, 1e-5);
        assertEquals(expectedResult.m01, rotation.m01, 1e-5);
        assertEquals(expectedResult.m02, rotation.m02, 1e-5);
        assertEquals(expectedResult.m03, rotation.m03, 1e-5);
        assertEquals(expectedResult.m10, rotation.m10, 1e-5);
        assertEquals(expectedResult.m11, rotation.m11, 1e-5);
        assertEquals(expectedResult.m12, rotation.m12, 1e-5);
        assertEquals(expectedResult.m13, rotation.m13, 1e-5);
        assertEquals(expectedResult.m20, rotation.m20, 1e-5);
        assertEquals(expectedResult.m21, rotation.m21, 1e-5);
        assertEquals(expectedResult.m22, rotation.m22, 1e-5);
        assertEquals(expectedResult.m23, rotation.m23, 1e-5);
        assertEquals(expectedResult.m30, rotation.m30, 1e-5);
        assertEquals(expectedResult.m31, rotation.m31, 1e-5);
        assertEquals(expectedResult.m32, rotation.m32, 1e-5);
        assertEquals(expectedResult.m33, rotation.m33, 1e-5);
    }
    @Test
    void testCreateRotationYMatrix(){
        Matrix4x4 rotation = Matrix4x4.createRotationMatrixY((float) Math.PI/2);
        Matrix4x4 expectedResult = new Matrix4x4(
                0, 0, 1, 0,
                0, 1, 0, 0,
                -1, 0, 0, 0,
                0, 0, 0, 1
        );
        assertEquals(expectedResult.m00, rotation.m00, 1e-5);
        assertEquals(expectedResult.m01, rotation.m01, 1e-5);
        assertEquals(expectedResult.m02, rotation.m02, 1e-5);
        assertEquals(expectedResult.m03, rotation.m03, 1e-5);
        assertEquals(expectedResult.m10, rotation.m10, 1e-5);
        assertEquals(expectedResult.m11, rotation.m11, 1e-5);
        assertEquals(expectedResult.m12, rotation.m12, 1e-5);
        assertEquals(expectedResult.m13, rotation.m13, 1e-5);
        assertEquals(expectedResult.m20, rotation.m20, 1e-5);
        assertEquals(expectedResult.m21, rotation.m21, 1e-5);
        assertEquals(expectedResult.m22, rotation.m22, 1e-5);
        assertEquals(expectedResult.m23, rotation.m23, 1e-5);
        assertEquals(expectedResult.m30, rotation.m30, 1e-5);
        assertEquals(expectedResult.m31, rotation.m31, 1e-5);
        assertEquals(expectedResult.m32, rotation.m32, 1e-5);
        assertEquals(expectedResult.m33, rotation.m33, 1e-5);
    }
    @Test
    void testCreateRotationZMatrix(){
        Matrix4x4 rotation = Matrix4x4.createRotationMatrixZ((float) Math.PI/2);
        Matrix4x4 expectedResult = new Matrix4x4(
                0, -1, 0, 0,
                1, 0, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1
        );
        assertEquals(expectedResult.m00, rotation.m00, 1e-5);
        assertEquals(expectedResult.m01, rotation.m01, 1e-5);
        assertEquals(expectedResult.m02, rotation.m02, 1e-5);
        assertEquals(expectedResult.m03, rotation.m03, 1e-5);
        assertEquals(expectedResult.m10, rotation.m10, 1e-5);
        assertEquals(expectedResult.m11, rotation.m11, 1e-5);
        assertEquals(expectedResult.m12, rotation.m12, 1e-5);
        assertEquals(expectedResult.m13, rotation.m13, 1e-5);
        assertEquals(expectedResult.m20, rotation.m20, 1e-5);
        assertEquals(expectedResult.m21, rotation.m21, 1e-5);
        assertEquals(expectedResult.m22, rotation.m22, 1e-5);
        assertEquals(expectedResult.m23, rotation.m23, 1e-5);
        assertEquals(expectedResult.m30, rotation.m30, 1e-5);
        assertEquals(expectedResult.m31, rotation.m31, 1e-5);
        assertEquals(expectedResult.m32, rotation.m32, 1e-5);
        assertEquals(expectedResult.m33, rotation.m33, 1e-5);
    }

    @Test
    void testCreateScaleMatrix(){
        Matrix4x4 scale = Matrix4x4.createScaleMatrix(1,2,3);
        Matrix4x4 expectedResult = new Matrix4x4(
                1, 0, 0, 0,
                0, 2, 0, 0,
                0, 0, 3, 0,
                0, 0, 0, 1
        );
        assertEquals(expectedResult.m00, scale.m00, 1e-5);
        assertEquals(expectedResult.m01, scale.m01, 1e-5);
        assertEquals(expectedResult.m02, scale.m02, 1e-5);
        assertEquals(expectedResult.m03, scale.m03, 1e-5);
        assertEquals(expectedResult.m10, scale.m10, 1e-5);
        assertEquals(expectedResult.m11, scale.m11, 1e-5);
        assertEquals(expectedResult.m12, scale.m12, 1e-5);
        assertEquals(expectedResult.m13, scale.m13, 1e-5);
        assertEquals(expectedResult.m20, scale.m20, 1e-5);
        assertEquals(expectedResult.m21, scale.m21, 1e-5);
        assertEquals(expectedResult.m22, scale.m22, 1e-5);
        assertEquals(expectedResult.m23, scale.m23, 1e-5);
        assertEquals(expectedResult.m30, scale.m30, 1e-5);
        assertEquals(expectedResult.m31, scale.m31, 1e-5);
        assertEquals(expectedResult.m32, scale.m32, 1e-5);
        assertEquals(expectedResult.m33, scale.m33, 1e-5);
    }
}