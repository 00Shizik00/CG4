package com.CG4.objreader;

import com.CG4.math.Vector2f;
import com.CG4.math.Vector3f;
import com.CG4.model.Model;
import com.CG4.model.Polygon;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class ObjReader {

	// Метод для чтения модели из файла .obj
	public static Model read(Path fileName) throws IOException, ObjReaderException {
		Model result = new Model();
		try (Scanner scanner = new Scanner(Files.newBufferedReader(fileName))) {
			int lineCounter = 0;
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				lineCounter++;

				if (line.isBlank()) {
					continue; // Пропуск пустых строк
				}

				try (Scanner lineScanner = new Scanner(line)) {
					if (!lineScanner.hasNext()) {
						continue;
					}

					String token = lineScanner.next();
					switch (token) {
						case "v": // Обработка вершины
							ArrayList<String> wordsInLineWithoutToken = new ArrayList<>();
							while (lineScanner.hasNext()) {
								wordsInLineWithoutToken.add(lineScanner.next());
							}
							result.vertices.add(parseVertex(wordsInLineWithoutToken, lineCounter));
							break;

						case "vn": // Обработка нормали
							ArrayList<String> normalWords = new ArrayList<>();
							while (lineScanner.hasNext()) {
								normalWords.add(lineScanner.next());
							}
							result.normals.add(parseNormal(normalWords, lineCounter));
							break;

						case "vt": // Обработка текстурных координат
							ArrayList<String> textureWords = new ArrayList<>();
							while (lineScanner.hasNext()) {
								textureWords.add(lineScanner.next());
							}
							result.textureCoordinates.add(parseTextureCoordinate(textureWords, lineCounter)); // Изменение на Vector2f
							break;

						case "f": // Обработка полигона
							Polygon polygon = new Polygon();
							while (lineScanner.hasNext()) {
								String vertex = lineScanner.next();
								try {
									if (vertex.contains("/")) {
										vertex = vertex.substring(0, vertex.indexOf("/"));
									}
									polygon.vertexIndices.add(Integer.parseInt(vertex) - 1);
								} catch (NumberFormatException e) {
									throw new ObjReaderException("Неверный формат индекса вершины в строке: " + lineCounter, e);
								}
							}
							result.polygons.add(polygon);
							break;

						default: // Игнорирование неизвестных токенов
							System.out.println("Предупреждение: Неизвестный токен '" + token + "' в строке " + lineCounter);
					}
				} catch (Exception e) {
					throw new ObjReaderException("Ошибка при разборе строки " + lineCounter + ": " + line, e);
				}
			}
		} catch (IOException e) {
			throw new ObjReaderException("Ошибка при чтении файла .obj: " + fileName, e);
		}
		return result;
	}

	// Метод для разбора вершины
	public static Vector3f parseVertex(ArrayList<String> wordsInLineWithoutToken, int lineCounter) throws ObjReaderException {
		if (wordsInLineWithoutToken.size() < 3) {
			throw new ObjReaderException("Ошибка при разборе файла OBJ в строке: " + lineCounter + ". Слишком мало аргументов для вершины.");
		}
		if (wordsInLineWithoutToken.size() > 3) {
			throw new ObjReaderException("Ошибка при разборе файла OBJ в строке: " + lineCounter + ". Слишком много аргументов для вершины.");
		}
		try {
			return new Vector3f(
					Float.parseFloat(wordsInLineWithoutToken.get(0)),
					Float.parseFloat(wordsInLineWithoutToken.get(1)),
					Float.parseFloat(wordsInLineWithoutToken.get(2))
			);
		} catch (NumberFormatException e) {
			throw new ObjReaderException("Ошибка при разборе файла OBJ в строке: " + lineCounter + ". Не удалось разобрать значение типа float.", e);
		}
	}

	// Метод для разбора нормали
	public static Vector3f parseNormal(ArrayList<String> normalWords, int lineCounter) throws ObjReaderException {
		if (normalWords.size() < 3) {
			throw new ObjReaderException("Ошибка при разборе файла OBJ в строке: " + lineCounter + ". Слишком мало аргументов для нормали.");
		}
		if (normalWords.size() > 3) {
			throw new ObjReaderException("Ошибка при разборе файла OBJ в строке: " + lineCounter + ". Слишком много аргументов для нормали.");
		}
		try {
			return new Vector3f(
					Float.parseFloat(normalWords.get(0)),
					Float.parseFloat(normalWords.get(1)),
					Float.parseFloat(normalWords.get(2))
			);
		} catch (NumberFormatException e) {
			throw new ObjReaderException("Ошибка при разборе файла OBJ в строке: " + lineCounter + ". Не удалось разобрать значение типа float для нормали.", e);
		}
	}

	// Метод для разбора текстурных координат (изменен тип возвращаемого значения на Vector2f)
	public static Vector2f parseTextureCoordinate(ArrayList<String> textureWords, int lineCounter) throws ObjReaderException {
		if (textureWords.size() < 2) {
			throw new ObjReaderException("Ошибка при разборе файла OBJ в строке: " + lineCounter + ". Слишком мало аргументов для текстурных координат.");
		}
		if (textureWords.size() > 2) {
			throw new ObjReaderException("Ошибка при разборе файла OBJ в строке: " + lineCounter + ". Слишком много аргументов для текстурных координат.");
		}
		try {
			return new Vector2f(
					Float.parseFloat(textureWords.get(0)),
					Float.parseFloat(textureWords.get(1))
			);
		} catch (NumberFormatException e) {
			throw new ObjReaderException("Ошибка при разборе файла OBJ в строке: " + lineCounter + ". Не удалось разобрать значение типа float для текстурных координат.", e);
		}
	}
}
