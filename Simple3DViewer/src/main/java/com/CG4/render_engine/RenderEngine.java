package com.CG4.render_engine;

import com.CG4.math.Matrix4x4;
import com.CG4.math.Vector3f;
import com.CG4.model.Model;
import com.CG4.model.Polygon;
import com.CG4.model.Texture;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import com.CG4.math.MathUtils;


public class RenderEngine {

    private static final float AMBIENT_COEFFICIENT = 0.1f;

    public static void render(GraphicsContext gc, Model model, Camera camera, int width, int height, Matrix4x4 modelMatrix,
                              boolean drawPolygons, boolean useTexture, boolean useLighting, Color staticColor, Texture texture) {
        Matrix4x4 viewMatrix = camera.getViewMatrix();
        Matrix4x4 projectionMatrix = camera.getProjectionMatrix();
        Matrix4x4 combinedMatrix = projectionMatrix.multiply(viewMatrix).multiply(modelMatrix);
        gc.clearRect(0, 0, width, height);
        if (model == null)
            return;
        ZBuffer zBuffer = new ZBuffer(width, height);

        if (useTexture && texture == null)
        {
            useTexture = false;
        }

        for (Polygon polygon : model.polygons) {
            if(polygon.vertexIndices.size() == 3){
                Vector3f v1 = model.vertices.get(polygon.vertexIndices.get(0));
                Vector3f v2 = model.vertices.get(polygon.vertexIndices.get(1));
                Vector3f v3 = model.vertices.get(polygon.vertexIndices.get(2));
                drawTriangle(gc, v1, v2, v3, combinedMatrix, width, height, zBuffer, drawPolygons, useTexture, useLighting, staticColor, texture, camera.getPosition(), model);
            } else if(polygon.vertexIndices.size() == 4){
                Vector3f v1 = model.vertices.get(polygon.vertexIndices.get(0));
                Vector3f v2 = model.vertices.get(polygon.vertexIndices.get(1));
                Vector3f v3 = model.vertices.get(polygon.vertexIndices.get(2));
                Vector3f v4 = model.vertices.get(polygon.vertexIndices.get(3));
                drawTriangle(gc, v1, v2, v3, combinedMatrix, width, height, zBuffer, drawPolygons, useTexture, useLighting, staticColor, texture, camera.getPosition(), model);
                drawTriangle(gc, v1, v3,v4, combinedMatrix,width,height, zBuffer, drawPolygons,useTexture,useLighting, staticColor, texture, camera.getPosition(), model);
            }
        }
    }

    private static void drawTriangle(GraphicsContext gc, Vector3f v1, Vector3f v2, Vector3f v3, Matrix4x4 combinedMatrix, int width, int height, ZBuffer zBuffer, boolean drawPolygons, boolean useTexture, boolean useLighting, Color staticColor, Texture texture, Vector3f lightSourcePosition, Model model) {
        Vector3f v1Projected = transformAndProject(v1, combinedMatrix);
        Vector3f v2Projected = transformAndProject(v2, combinedMatrix);
        Vector3f v3Projected = transformAndProject(v3, combinedMatrix);

        if(v1Projected == null || v2Projected == null || v3Projected == null)
            return;

        int x1 = (int) Math.round((v1Projected.x + 1) * width / 2.0);
        int y1 = (int) Math.round((1 - v1Projected.y) * height / 2.0);
        int x2 = (int) Math.round((v2Projected.x + 1) * width / 2.0);
        int y2 = (int) Math.round((1 - v2Projected.y) * height / 2.0);
        int x3 = (int) Math.round((v3Projected.x + 1) * width / 2.0);
        int y3 = (int) Math.round((1 - v3Projected.y) * height / 2.0);

        if (drawPolygons) {
            gc.setStroke(Color.BLACK);
            gc.strokeLine(x1,y1,x2,y2);
            gc.strokeLine(x2, y2, x3,y3);
            gc.strokeLine(x3, y3, x1, y1);
        }
        if(!drawPolygons){
            fillTriangle(gc, v1Projected, v2Projected, v3Projected, width, height, zBuffer, useTexture, useLighting, staticColor, texture, lightSourcePosition, model);
        }
    }
    private static void fillTriangle(GraphicsContext gc, Vector3f v1, Vector3f v2, Vector3f v3, int width, int height, ZBuffer zBuffer, boolean useTexture, boolean useLighting, Color staticColor, Texture texture, Vector3f lightSourcePosition, Model model) {
        float minX = Math.min(Math.min(v1.x, v2.x), v3.x);
        float minY = Math.min(Math.min(v1.y, v2.y), v3.y);
        float maxX = Math.max(Math.max(v1.x, v2.x), v3.x);
        float maxY = Math.max(Math.max(v1.y, v2.y), v3.y);

        for (int y = (int) Math.round(minY * height / 2.0); y <= (int)Math.round(maxY * height / 2.0); y++) {
            for (int x = (int) Math.round(minX * width/2.0); x <= (int)Math.round(maxX * width/ 2.0); x++) {
                float screenX = x / (float)width * 2 - 1;
                float screenY = -y / (float)height * 2 + 1;
                if (MathUtils.isPointInsideTriangle(screenX, screenY, v1, v2, v3)) {
                    Vector3f barycentricCoords = MathUtils.calculateBarycentricCoordinates(screenX, screenY, v1, v2, v3);
                    float z =  interpolateZ(barycentricCoords, v1,v2,v3);

                    if (zBuffer.checkZ(x, y, z)) {
                        if (useTexture && texture != null) {
                            float u = interpolateU(barycentricCoords, v1,v2,v3);
                            float v = interpolateV(barycentricCoords, v1,v2,v3);
                            Color pixelColor = texture.getPixelColor(u,v);
                            if (useLighting) {
                                Vector3f normal = getNormal(model, screenX, screenY, v1,v2,v3);
                                float lightIntensity =  calculateLighting(normal, lightSourcePosition, screenX, screenY, v1, v2, v3);
                                Color litColor = litColor(pixelColor, lightIntensity);
                                gc.setFill(litColor);

                            }
                            else
                            {
                                gc.setFill(pixelColor);
                            }

                        } else if(useLighting) {
                            Vector3f normal = getNormal(model, screenX, screenY, v1,v2,v3);
                            float lightIntensity =  calculateLighting(normal, lightSourcePosition, screenX, screenY, v1, v2, v3);
                            Color litColor = litColor(staticColor, lightIntensity);
                            gc.setFill(litColor);
                        }
                        else{
                            gc.setFill(staticColor);
                        }
                        gc.fillRect(x, y, 1, 1);
                    }
                }
            }
        }
    }
    private static Vector3f getNormal(Model model, float x, float y, Vector3f v1, Vector3f v2, Vector3f v3){
        Vector3f barycentricCoords = MathUtils.calculateBarycentricCoordinates(x, y, v1, v2, v3);
        Vector3f normal = new Vector3f(0,0,0);
        if(model.normals.size() == model.vertices.size()){
            normal = new Vector3f(
                    model.normals.get(model.vertices.indexOf(v1)).multiply(barycentricCoords.x).plus(
                            model.normals.get(model.vertices.indexOf(v2)).multiply(barycentricCoords.y).plus(
                                    model.normals.get(model.vertices.indexOf(v3)).multiply(barycentricCoords.z)
                            )));
        } else{
            normal = v1.calculateNormal(v2,v3);
        }
        return normal.normalize();
    }
    private static float interpolateZ(Vector3f barycentricCoords, Vector3f v1, Vector3f v2, Vector3f v3){
        return v1.z * barycentricCoords.x + v2.z * barycentricCoords.y + v3.z * barycentricCoords.z;
    }
    private static float interpolateU(Vector3f barycentricCoords, Vector3f v1, Vector3f v2, Vector3f v3){
        return   barycentricCoords.y;
    }
    private static float interpolateV(Vector3f barycentricCoords, Vector3f v1, Vector3f v2, Vector3f v3){
        return barycentricCoords.z;
    }
    private static float calculateLighting(Vector3f normal, Vector3f lightSourcePosition, float x, float y, Vector3f v1, Vector3f v2, Vector3f v3){
        Vector3f vertex = new Vector3f(x,y,interpolateZ(MathUtils.calculateBarycentricCoordinates(x,y,v1,v2,v3),v1,v2,v3));
        Vector3f lightDirection = lightSourcePosition.minus(vertex).normalize();
        float intensity = Math.max(0, Math.min(1, normal.dotProduct(lightDirection)));
        return AMBIENT_COEFFICIENT + (1 - AMBIENT_COEFFICIENT) * intensity;
    }
    private static Color litColor(Color color, float intensity){
        float red = (float) color.getRed();
        float green = (float) color.getGreen();
        float blue = (float) color.getBlue();
        return new Color(red * intensity, green * intensity, blue * intensity, 1);
    }


    private static Vector3f transformAndProject(Vector3f vertex, Matrix4x4 combinedMatrix) {
        Vector3f transformedVertex = combinedMatrix.multiply(vertex);
        float w = transformedVertex.x * combinedMatrix.m03 + transformedVertex.y * combinedMatrix.m13 + transformedVertex.z * combinedMatrix.m23 + 1 * combinedMatrix.m33;
        float x = transformedVertex.x;
        float y = transformedVertex.y;
        float z = transformedVertex.z;

        if(Math.abs(w) < 1e-5)
            return null;
        return new Vector3f(x/w,y/w,z/w);
    }
}