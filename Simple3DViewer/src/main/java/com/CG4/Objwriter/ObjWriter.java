package com.CG4.Objwriter;

import com.CG4.math.Vector2f;
import com.CG4.math.Vector3f;
import com.CG4.model.Model;
import com.CG4.model.Polygon;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ObjWriter {

    // Метод для записи модели в файл формата .obj
    public static void write(Model model, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Запись вершин
            for (Vector3f vertex : model.getVertices()) {
                writer.write(String.format("v %f %f %f\n", vertex.x, vertex.y, vertex.z));
            }

            // Запись нормалей
            for (Vector3f normal : model.getNormals()) {
                writer.write(String.format("vn %f %f %f\n", normal.x, normal.y, normal.z));
            }

            // Запись текстурных координат (изменение типа на Vector2f)
            for (Vector2f textureCoordinate : model.getTextureCoordinates()) { // Используем Vector2f
                writer.write(String.format("vt %f %f\n", textureCoordinate.x, textureCoordinate.y));
            }

            // Запись полигонов
            for (Polygon polygon : model.getPolygons()) {
                writer.write("f");
                for (int i = 0; i < polygon.vertexIndices.size(); i++) {
                    int vertexIndex = polygon.vertexIndices.get(i);
                    // Формат записи: вершина/текстурная_координата/нормаль
                    writer.write(" " + (vertexIndex + 1));

                    // Запись текстурной координаты (если есть)
                    if (!model.getTextureCoordinates().isEmpty()) {
                        writer.write("/" + (i + 1));  // Текстурные координаты: (i + 1) для каждой вершины
                    } else {
                        writer.write("/");  // Если текстурные координаты нет, ставим "/"
                    }

                    // Запись нормали (если есть)
                    if (!model.getNormals().isEmpty()) {
                        writer.write("/" + (i + 1));  // Нормали: (i + 1)
                    }
                }
                writer.write("\n");
            }
        }
    }

    // Метод для записи модели в файл с выбором расположения через FileChooser
    public static void writeWithFileChooser(Model model, Stage stage) throws IOException {
        // Создаем новый FileChooser
        FileChooser fileChooser = new FileChooser();

        // Устанавливаем фильтр для выбора только .obj файлов
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("OBJ Files", "*.obj"));

        // Открываем диалог выбора файла
        Path selectedFile = fileChooser.showSaveDialog(stage).toPath();

        if (selectedFile != null) {
            // Записываем модель в выбранный файл
            write(model, selectedFile.toString());
        }
    }
}
