package com.CG4.render_engine;

import com.CG4.math.Matrix4x4;
import com.CG4.math.TransformUtils;
import com.CG4.math.Vector3f;
import com.CG4.model.Model;
import com.CG4.model.Polygon;
import com.CG4.model.Texture;
import com.CG4.transformations.AffineTransformations;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class RenderEngine {

    private static final float AMBIENT_COEFFICIENT = 0.1f;  // Коэффициент фонового освещения

    private static boolean useTexture = true;  // Флаг, указывающий, использовать ли текстуры
    private static Texture texture = null;  // Объект текстуры
    private static boolean drawPolygons = true;  // Флаг для отрисовки контуров полигона
    private static boolean useLighting = true;  // Флаг для использования освещения
    private static Color staticColor = Color.WHITE;  // Статический цвет для примера без текстуры

    // Основной метод рендеринга модели
    public static void render(GraphicsContext gc, Model model, Camera camera, int width, int height, Matrix4x4 modelMatrix) {
        Matrix4x4 viewMatrix = camera.getViewMatrix();  // Получаем матрицу вида
        Matrix4x4 projectionMatrix = camera.getProjectionMatrix();  // Получаем матрицу проекции
        gc.clearRect(0, 0, width, height);  // Очищаем экран
        if (model == null) return;  // Если модель пуста, выходим

        ZBuffer zBuffer = new ZBuffer(width, height);  // Буфер глубины для отслеживания переднего плана

        // Применяем преобразования и перемещаем модель в центр
        Model transformedModel = AffineTransformations.moveModelToCenter(model, modelMatrix);
        if (useTexture && texture == null) {
            useTexture = false;  // Если текстуры нет, отключаем использование текстуры
        }

        // Проходим по всем полигонам модели
        for (Polygon polygon : transformedModel.polygons) {
            if(polygon.vertexIndices.size() == 3){
                // Рисуем треугольники
                Vector3f v1 = transformedModel.vertices.get(polygon.vertexIndices.get(0));
                Vector3f v2 = transformedModel.vertices.get(polygon.vertexIndices.get(1));
                Vector3f v3 = transformedModel.vertices.get(polygon.vertexIndices.get(2));
                drawTriangle(gc, v1, v2, v3, viewMatrix, projectionMatrix, width, height, zBuffer, drawPolygons, useTexture, useLighting, staticColor, texture, camera.getPosition());
            } else if(polygon.vertexIndices.size() == 4){
                // Рисуем четырехугольники, делим на два треугольника
                Vector3f v1 = transformedModel.vertices.get(polygon.vertexIndices.get(0));
                Vector3f v2 = transformedModel.vertices.get(polygon.vertexIndices.get(1));
                Vector3f v3 = transformedModel.vertices.get(polygon.vertexIndices.get(2));
                Vector3f v4 = transformedModel.vertices.get(polygon.vertexIndices.get(3));
                drawTriangle(gc, v1, v2, v3, viewMatrix, projectionMatrix, width, height, zBuffer, drawPolygons, useTexture, useLighting, staticColor, texture, camera.getPosition());
                drawTriangle(gc, v1, v3, v4, viewMatrix, projectionMatrix, width, height, zBuffer, drawPolygons, useTexture, useLighting, staticColor, texture, camera.getPosition());
            }
        }
    }

    // Метод для отрисовки треугольника
    private static void drawTriangle(GraphicsContext gc, Vector3f v1, Vector3f v2, Vector3f v3, Matrix4x4 viewMatrix, Matrix4x4 projectionMatrix, int width, int height, ZBuffer zBuffer, boolean drawPolygons, boolean useTexture, boolean useLighting, Color staticColor, Texture texture, Vector3f lightSourcePosition) {
        // Преобразуем вершины в экранные координаты
        Vector3f v1Projected = transformAndProject(v1, viewMatrix, projectionMatrix);
        Vector3f v2Projected = transformAndProject(v2, viewMatrix, projectionMatrix);
        Vector3f v3Projected = transformAndProject(v3, viewMatrix, projectionMatrix);

        // Если хотя бы одна вершина не была преобразована, пропускаем этот треугольник
        if(v1Projected == null || v2Projected == null || v3Projected == null)
            return;

        // Преобразуем координаты в пиксельные
        int x1 = (int) Math.round((v1Projected.x + 1) * width / 2.0);
        int y1 = (int) Math.round((1 - v1Projected.y) * height / 2.0);
        int x2 = (int) Math.round((v2Projected.x + 1) * width / 2.0);
        int y2 = (int) Math.round((1 - v2Projected.y) * height / 2.0);
        int x3 = (int) Math.round((v3Projected.x + 1) * width / 2.0);
        int y3 = (int) Math.round((1 - v3Projected.y) * height / 2.0);

        // Если флаг drawPolygons активирован, рисуем контуры треугольника
        if (drawPolygons) {
            gc.setStroke(Color.BLACK);
            gc.strokeLine(x1, y1, x2, y2);
            gc.strokeLine(x2, y2, x3, y3);
            gc.strokeLine(x3, y3, x1, y1);
        }
        // Если флаг drawPolygons не активен, заполняем треугольник
        if(!drawPolygons){
            fillTriangle(gc, v1Projected, v2Projected, v3Projected, width, height, zBuffer, useTexture, useLighting, staticColor, texture, lightSourcePosition );
        }
    }

    // Метод для заливки треугольника
    private static void fillTriangle(GraphicsContext gc, Vector3f v1, Vector3f v2, Vector3f v3, int width, int height, ZBuffer zBuffer, boolean useTexture, boolean useLighting, Color staticColor, Texture texture, Vector3f lightSourcePosition) {
        // Находим минимальные и максимальные координаты треугольника для обхода всех пикселей
        float minX = Math.min(Math.min(v1.x, v2.x), v3.x);
        float minY = Math.min(Math.min(v1.y, v2.y), v3.y);
        float maxX = Math.max(Math.max(v1.x, v2.x), v3.x);
        float maxY = Math.max(Math.max(v1.y, v2.y), v3.y);

        // Проходим по всем пикселям, находящимся в пределах треугольника
        for (int y = (int) Math.round(minY * height / 2.0); y <= (int) Math.round(maxY * height / 2.0); y++) {
            for (int x = (int) Math.round(minX * width / 2.0); x <= (int) Math.round(maxX * width / 2.0); x++) {
                // Проверяем, находится ли точка внутри треугольника
                float screenX = x / (float) width * 2 - 1;
                float screenY = -y / (float) height * 2 + 1;
                if (isPointInsideTriangle(screenX, screenY, v1, v2, v3)) {
                    // Вычисляем глубину точки в треугольнике
                    float z = calculateZ(screenX, screenY, v1, v2, v3);

                    // Проверяем, если точка проходит по глубине (не перекрывает другие пиксели)
                    if (zBuffer.checkZ(x, y, z)) {
                        // Если используется текстура, получаем цвет пикселя из текстуры
                        if (useTexture && texture != null) {
                            float u = calculateU(screenX, screenY, v1, v2, v3);
                            float v = calculateV(screenX, screenY, v1, v2, v3);
                            Color pixelColor = texture.getPixelColor(u, v);

                            // Если используется освещение, рассчитываем интенсивность освещения и применяем его
                            if (useLighting) {
                                Vector3f normal = calculateNormal(v1, v2, v3);
                                float lightIntensity = calculateLighting(normal, lightSourcePosition, screenX, screenY, v1, v2, v3);
                                Color litColor = litColor(pixelColor, lightIntensity);
                                gc.setFill(litColor);
                            } else {
                                gc.setFill(pixelColor);
                            }
                        } else if (useLighting) {
                            // Если текстуры нет, но используется освещение, вычисляем освещенность цвета
                            Vector3f normal = calculateNormal(v1, v2, v3);
                            float lightIntensity = calculateLighting(normal, lightSourcePosition, screenX, screenY, v1, v2, v3);
                            Color litColor = litColor(staticColor, lightIntensity);
                            gc.setFill(litColor);
                        } else {
                            gc.setFill(staticColor);  // Без текстуры и освещения, просто статический цвет
                        }

                        // Рисуем пиксель
                        gc.fillRect(x, y, 1, 1);
                    }
                }
            }
        }
    }

    // Метод для преобразования вершины в экранные координаты
    private static Vector3f transformAndProject(Vector3f vertex, Matrix4x4 viewMatrix, Matrix4x4 projectionMatrix) {
        Vector3f transformedVertex = TransformUtils.transform(vertex, viewMatrix);
        float w = transformedVertex.x * viewMatrix.m03 + transformedVertex.y * viewMatrix.m13 + transformedVertex.z * viewMatrix.m23 + 1 * viewMatrix.m33;

        transformedVertex = TransformUtils.transform(transformedVertex, projectionMatrix);

        float x = transformedVertex.x;
        float y = transformedVertex.y;
        float z = transformedVertex.z;

        if (w == 0)
            return null;
        return new Vector3f(x / w, y / w, z / w);  // Преобразуем в нормализованные координаты
    }

    // Метод для проверки, находится ли точка внутри треугольника с использованием барицентрических координат
    private static boolean isPointInsideTriangle(float x, float y, Vector3f v1, Vector3f v2, Vector3f v3) {
        float area = 0.5f * (-v2.y * v3.x + v1.y * (-v2.x + v3.x) + v1.x * (v2.y - v3.y) + v2.x * v3.y);
        float s = 1 / (2 * area) * (v1.y * v3.x - v1.x * v3.y + (v3.y - v1.y) * x + (v1.x - v3.x) * y);
        float t = 1 / (2 * area) * (v1.x * v2.y - v1.y * v2.x + (v1.y - v2.y) * x + (v2.x - v1.x) * y);
        return s > 0 && t > 0 && 1 - s - t > 0;  // Проверяем, что все барицентрические координаты положительные
    }

    // Метод для вычисления глубины Z для точки в треугольнике
    private static float calculateZ(float x, float y, Vector3f v1, Vector3f v2, Vector3f v3) {
        float area = 0.5f * (-v2.y * v3.x + v1.y * (-v2.x + v3.x) + v1.x * (v2.y - v3.y) + v2.x * v3.y);
        float s = 1 / (2 * area) * (v1.y * v3.x - v1.x * v3.y + (v3.y - v1.y) * x + (v1.x - v3.x) * y);
        float t = 1 / (2 * area) * (v1.x * v2.y - v1.y * v2.x + (v1.y - v2.y) * x + (v2.x - v1.x) * y);
        return v1.z * (1 - s - t) + v2.z * s + v3.z * t;  // Интерполяция по Z
    }

    // Метод для вычисления освещенности
    private static float calculateLighting(Vector3f normal, Vector3f lightSourcePosition, float x, float y, Vector3f v1, Vector3f v2, Vector3f v3) {
        Vector3f point = new Vector3f(x, y, calculateZ(x, y, v1, v2, v3));  // Точка на поверхности
        Vector3f lightDirection = lightSourcePosition.subtract(point).normalize();  // Вектор от точки до источника света
        float dotProduct = normal.dot(lightDirection);  // Скалярное произведение нормали и направления света
        return Math.max(AMBIENT_COEFFICIENT + dotProduct, 0);  // Возвращаем освещенность с минимальным значением 0
    }

    // Метод для вычисления цвета с учетом освещения
    private static Color litColor(Color color, float intensity) {
        float red = (float) color.getRed();
        float green = (float) color.getGreen();
        float blue = (float) color.getBlue();
        return new Color(red * intensity, green * intensity, blue * intensity, 1);  // Применяем интенсивность к цвету
    }
}
