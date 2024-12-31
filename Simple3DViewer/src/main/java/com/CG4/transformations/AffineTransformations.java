package com.CG4.transformations;

import com.CG4.math.Matrix4x4;
import com.CG4.math.TransformUtils;
import com.CG4.math.Vector3f;
import com.CG4.model.Model;


public class AffineTransformations {

    public static Model transformModel(Model model, Matrix4x4 matrix) {
        Model transformedModel = new Model();

        for (Vector3f vertex : model.vertices) {
            Vector3f transformedVertex = TransformUtils.transform(vertex, matrix);
            transformedModel.vertices.add(transformedVertex);
        }
        transformedModel.polygons.addAll(model.polygons);
        return transformedModel;
    }

    public static Model moveModelToCenter(Model model, Matrix4x4 matrix){
        Vector3f center = TransformUtils.calculateCentroid(model.vertices);
        Matrix4x4 moveToOrigin = Matrix4x4.createTranslationMatrix(-center.x, -center.y, -center.z);
        Matrix4x4 moveToNewPlace = new Matrix4x4();
        if(matrix != null)
        {
            moveToNewPlace = matrix;
        }

        return transformModel(model, moveToOrigin.multiply(moveToNewPlace));
    }

    public static Model scale(Model model, float x, float y, float z){
        Matrix4x4 scaleMatrix = Matrix4x4.createScaleMatrix(x,y,z);
        return transformModel(model, scaleMatrix);
    }
    public static Model rotateX(Model model, float angle){
        Matrix4x4 rotationMatrix = Matrix4x4.createRotationMatrixX(angle);
        return transformModel(model, rotationMatrix);
    }
    public static Model rotateY(Model model, float angle){
        Matrix4x4 rotationMatrix = Matrix4x4.createRotationMatrixY(angle);
        return transformModel(model, rotationMatrix);
    }
    public static Model rotateZ(Model model, float angle){
        Matrix4x4 rotationMatrix = Matrix4x4.createRotationMatrixZ(angle);
        return transformModel(model, rotationMatrix);
    }

    public static Model translate(Model model, float x, float y, float z){
        Matrix4x4 translationMatrix = Matrix4x4.createTranslationMatrix(x,y,z);
        return transformModel(model, translationMatrix);
    }
}