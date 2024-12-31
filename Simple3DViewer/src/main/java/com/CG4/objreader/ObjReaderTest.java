package com.CG4.objreader;

import com.CG4.math.Vector3f;
import com.CG4.model.Model;
import com.CG4.model.Polygon;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

public class ObjReaderTest {

    @Test
    public void testParseVertex01() throws ObjReaderException {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02", "1.03"));
        Vector3f result = ObjReader.parseVertex(wordsInLineWithoutToken, 5);
        Vector3f expectedResult = new Vector3f(1.01f, 1.02f, 1.03f);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    public void testParseVertex02() throws ObjReaderException {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02", "1.03"));
        Vector3f result = ObjReader.parseVertex(wordsInLineWithoutToken, 5);
        Vector3f expectedResult = new Vector3f(1.01f, 1.02f, 1.10f);
        Assertions.assertNotEquals(expectedResult, result);
    }

    @Test
    public void testParseVertex03() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("ab", "o", "ba"));
        ObjReaderException exception = Assertions.assertThrows(ObjReaderException.class, () -> ObjReader.parseVertex(wordsInLineWithoutToken, 10));
        String expectedError = "Error parsing OBJ file on line: 10. Failed to parse float value.";
        Assertions.assertEquals(expectedError, exception.getMessage());

    }

    @Test
    public void testParseVertex04() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.0", "2.0"));

        ObjReaderException exception = Assertions.assertThrows(ObjReaderException.class, () -> ObjReader.parseVertex(wordsInLineWithoutToken, 10));
        String expectedError = "Error parsing OBJ file on line: 10. Too few vertex arguments.";
        Assertions.assertEquals(expectedError, exception.getMessage());

    }

    @Test
    public void testParseVertex05() {

        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.0", "2.0", "3.0", "4.0"));
        ObjReaderException exception = Assertions.assertThrows(ObjReaderException.class, () -> ObjReader.parseVertex(wordsInLineWithoutToken, 10));
        String expectedError = "Error parsing OBJ file on line: 10. Too many vertex arguments.";
        Assertions.assertEquals(expectedError, exception.getMessage());

    }

    // Additional Tests

    @Test
    public void testReadValidFile() throws Exception {
        Path validObjFile = Path.of("src/test/resources/valid.obj"); // Replace with actual file path
        Model model = ObjReader.read(validObjFile);
        Assertions.assertNotNull(model);
        Assertions.assertFalse(model.getVertices().isEmpty());
        Assertions.assertFalse(model.getPolygons().isEmpty());
    }

    @Test
    public void testReadFileWithInvalidSyntax() {
        Path invalidObjFile = Path.of("src/test/resources/invalid_syntax.obj"); // Replace with actual file path
        ObjReaderException exception = Assertions.assertThrows(ObjReaderException.class, () -> ObjReader.read(invalidObjFile));
        Assertions.assertTrue(exception.getMessage().contains("Error parsing line"));
    }

    @Test
    public void testReadEmptyFile() {
        Path emptyObjFile = Path.of("src/test/resources/empty.obj"); // Replace with actual file path
        ObjReaderException exception = Assertions.assertThrows(ObjReaderException.class, () -> ObjReader.read(emptyObjFile));
        Assertions.assertTrue(exception.getMessage().contains("Error reading .obj file"));
    }

    @Test
    public void testParsePolygonWithInvalidIndex() {
        ArrayList<String> wordsInLine = new ArrayList<>(Arrays.asList("1", "2", "x"));
        ObjReaderException exception = Assertions.assertThrows(ObjReaderException.class, () -> {
            Polygon polygon = new Polygon();
            for (String word : wordsInLine) {
                if (word.contains("/")) {
                    word = word.substring(0, word.indexOf("/"));
                }
                polygon.vertexIndices.add(Integer.parseInt(word));
            }
        });
        Assertions.assertTrue(exception.getMessage().contains("For input string"));
    }

    @Test
    public void testParsePolygonWithNegativeIndex() {
        ArrayList<String> wordsInLine = new ArrayList<>(Arrays.asList("-1", "-2", "-3"));
        ObjReaderException exception = Assertions.assertThrows(ObjReaderException.class, () -> {
            Polygon polygon = new Polygon();
            for (String word : wordsInLine) {
                if (word.contains("/")) {
                    word = word.substring(0, word.indexOf("/"));
                }
                polygon.vertexIndices.add(Integer.parseInt(word));
            }
        });
        Assertions.assertTrue(exception.getMessage().contains("Error parsing line"));
    }
}
