package com.CG4.math;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Vector2fTest {

    @Test
    void testPlus() {
        Vector2f v1 = new Vector2f(1, 2);
        Vector2f v2 = new Vector2f(3, 4);
        Vector2f result = v1.plus(v2);
        assertEquals(4, result.x, 1e-5);
        assertEquals(6, result.y, 1e-5);
    }

    @Test
    void testMinus() {
        Vector2f v1 = new Vector2f(5, 6);
        Vector2f v2 = new Vector2f(2, 1);
        Vector2f result = v1.minus(v2);
        assertEquals(3, result.x, 1e-5);
        assertEquals(5, result.y, 1e-5);
    }

    @Test
    void testMultiply() {
        Vector2f v = new Vector2f(2, 3);
        Vector2f result = v.multiply(2);
        assertEquals(4, result.x, 1e-5);
        assertEquals(6, result.y, 1e-5);
    }

    @Test
    void testLength() {
        Vector2f v = new Vector2f(3, 4);
        float result = v.length();
        assertEquals(5, result, 1e-5);
    }

    @Test
    void testNormalize() {
        Vector2f v = new Vector2f(3, 4);
        Vector2f result = v.normalize();
        assertEquals(0.6, result.x, 1e-5);
        assertEquals(0.8, result.y, 1e-5);

        Vector2f v2 = new Vector2f(0,0);
        result = v2.normalize();
        assertEquals(0,result.x, 1e-5);
        assertEquals(0,result.y, 1e-5);
    }


    @Test
    void testDotProduct() {
        Vector2f v1 = new Vector2f(1, 2);
        Vector2f v2 = new Vector2f(3, 4);
        float result = v1.dotProduct(v2);
        assertEquals(11, result, 1e-5);
    }
}