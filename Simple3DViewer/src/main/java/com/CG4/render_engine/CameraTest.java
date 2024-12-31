package com.CG4.render_engine;

import com.CG4.math.Matrix4x4;
import com.CG4.math.Vector3f;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CameraTest {
    @Test
    void testGetViewMatrix(){
        Camera camera = new Camera(new Vector3f(0,0,0), new Vector3f(0,0,-1), 1, 1);
        Matrix4x4 result = camera.getViewMatrix();
        Matrix4x4 expectedResult = new Matrix4x4(
                1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1
        );
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

        camera = new Camera(new Vector3f(1,2,3), new Vector3f(1,0,0), 1,1);
        result = camera.getViewMatrix();
        expectedResult = new Matrix4x4(
                0, 0, -1, 1,
                0, 1, 0, -2,
                1, 0, 0, -3,
                0, 0, 0, 1
        );
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
    void testGetProjectionMatrix(){
        Camera camera = new Camera(new Vector3f(0,0,0), new Vector3f(0,0,-1), 1, 1);
        Matrix4x4 result = camera.getProjectionMatrix();
        Matrix4x4 expectedResult = new Matrix4x4(
                1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, -1, -1,
                0, 0, -1, 0
        );
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

}