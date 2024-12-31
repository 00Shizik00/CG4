package com.CG4.model;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Texture {
    private com.CG4.model.Image image;
    private com.CG4.model.PixelReader pixelReader;

    public Texture(Image image) {
        this.image = image;
        if (image != null) {
            this.pixelReader = image.getPixelReader();
        } else {
            this.pixelReader = null;
        }
    }


    public Color getPixelColor(double u, double v) {
        if (pixelReader == null || image == null) {
            return Color.WHITE; // Or some default color
        }

        int x = (int) (u * image.getWidth());
        int y = (int) (v * image.getHeight());


        if (x < 0) {
            x = 0;
        }
        if(x >= image.getWidth()){
            x = (int) image.getWidth() - 1;
        }
        if (y < 0) {
            y = 0;
        }
        if(y >= image.getHeight()){
            y = (int) image.getHeight() - 1;
        }

        return pixelReader.getColor(x, y);
    }

    public double getWidth() {
        if(image == null)
            return 0;
        return image.getWidth();
    }

    public double getHeight() {
        if(image == null)
            return 0;
        return image.getHeight();
    }
}