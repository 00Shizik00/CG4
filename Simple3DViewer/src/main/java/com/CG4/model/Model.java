package com.CG4.model;

import com.CG4.math.Vector2f;
import com.CG4.math.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class Model {
    public List<Vector3f> vertices = new ArrayList<>();
    public List<Polygon> polygons = new ArrayList<>();
    public List<Vector3f> normals = new ArrayList<>();
    public List<Vector2f> textureCoordinates = new ArrayList<>();  // Поддержка текстурных координат

    public void calculateNormals(){
        normals.clear();
        for (Polygon polygon : polygons){
            if (polygon.vertexIndices.size() == 3) {
                Vector3f v1 = vertices.get(polygon.vertexIndices.get(0));
                Vector3f v2 = vertices.get(polygon.vertexIndices.get(1));
                Vector3f v3 = vertices.get(polygon.vertexIndices.get(2));
                Vector3f normal = calculateNormal(v1, v2, v3);
                normals.add(normal);
                normals.add(normal);
                normals.add(normal);
            } else if (polygon.vertexIndices.size() == 4) {
                Vector3f v1 = vertices.get(polygon.vertexIndices.get(0));
                Vector3f v2 = vertices.get(polygon.vertexIndices.get(1));
                Vector3f v3 = vertices.get(polygon.vertexIndices.get(2));
                Vector3f v4 = vertices.get(polygon.vertexIndices.get(3));
                Vector3f normal1 = calculateNormal(v1, v2, v3);
                Vector3f normal2 = calculateNormal(v1, v3, v4);
                normals.add(normal1);
                normals.add(normal1);
                normals.add(normal1);
                normals.add(normal2);
                normals.add(normal2);
                normals.add(normal2);
            }
        }
    }

    private Vector3f calculateNormal(Vector3f v1, Vector3f v2, Vector3f v3) {
        Vector3f normal = new Vector3f(
                (v2.y - v1.y) * (v3.z - v1.z) - (v2.z - v1.z) * (v3.y - v1.y),
                (v2.z - v1.z) * (v3.x - v1.x) - (v2.x - v1.x) * (v3.z - v1.z),
                (v2.x - v1.x) * (v3.y - v1.y) - (v2.y - v1.y) * (v3.x - v1.x)
        );
        float length = (float) Math.sqrt(normal.x * normal.x + normal.y * normal.y + normal.z * normal.z);
        if (length != 0) {
            normal.x /= length;
            normal.y /= length;
            normal.z /= length;
        }
        return normal;
    }

    // Геттеры для полей
    public List<Vector3f> getVertices() {
        return vertices;
    }

    public List<Polygon> getPolygons() {
        return polygons;
    }

    public List<Vector3f> getNormals() {
        return normals;
    }

    public List<Vector2f> getTextureCoordinates() {
        return textureCoordinates;
    }

    public void setTextureCoordinates(List<Vector2f> textureCoordinates) {
        this.textureCoordinates = textureCoordinates;
    }
}
