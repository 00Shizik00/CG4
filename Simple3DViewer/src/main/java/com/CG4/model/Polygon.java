package com.CG4.model;

import java.util.ArrayList;
import java.util.List;

public class Polygon {
    public List<Integer> vertexIndices = new ArrayList<>();
    public List<Integer> textureIndices = new ArrayList<>();
    public List<Integer> normalIndices = new ArrayList<>();
    public int numVertices;

    public Polygon(){
        numVertices = 0;
    }

    public void setVertexIndices(List<Integer> vertexIndices) {
        if(vertexIndices != null)
        {
            this.vertexIndices = vertexIndices;
            numVertices = vertexIndices.size();
        }
    }


    public void setTextureIndices(List<Integer> textureIndices) {
        if (textureIndices != null)
            this.textureIndices = textureIndices;
    }


    public void setNormalIndices(List<Integer> normalIndices) {
        if(normalIndices != null)
            this.normalIndices = normalIndices;
    }


}