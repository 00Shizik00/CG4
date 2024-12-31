package com.CG4.render_engine;

public class ZBuffer {
    private float[] buffer;
    private int width;
    private int height;

    public ZBuffer(int width, int height) {
        this.width = width;
        this.height = height;
        this.buffer = new float[width * height];
        clear();
    }
    public boolean checkZ(int x, int y, float z){
        if(x < 0 || x >= width || y < 0 || y>= height)
            return false;
        int index = y*width + x;
        if(index >= buffer.length)
            return false;
        if(z > buffer[index]) {
            buffer[index] = z;
            return true;
        }
        return false;
    }

    public void clear() {
        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = Float.NEGATIVE_INFINITY;
        }
    }
}