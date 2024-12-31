package com.CG4.math;

import java.util.Objects;

public class Vector2f {

    public float x;
    public float y;

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2f plus(Vector2f other){
        return new Vector2f(x+other.x, y+other.y);
    }

    public Vector2f minus(Vector2f other){
        return new Vector2f(x-other.x, y-other.y);
    }

    public Vector2f multiply(float scalar){
        return new Vector2f(x*scalar, y*scalar);
    }
    public float length(){
        return (float) Math.sqrt(x*x + y*y);
    }

    public Vector2f normalize(){
        float len = length();
        if(len == 0)
            return new Vector2f(0,0);
        return new Vector2f(x/len, y/len);
    }

    public float dotProduct(Vector2f other){
        return x* other.x + y*other.y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2f vector2f = (Vector2f) o;
        return Float.compare(vector2f.x, x) == 0 && Float.compare(vector2f.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}