package com.snaw.pixMapUtils;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

import java.util.Arrays;
import java.util.List;

public class TextureCropper {
    public int[] croppedStartPoint;
    public int[] croppedEndPoint;

    private static List<Integer> backgroundColors = Arrays.asList(0, 1, -256); //imagemagick bullshit.

    public static int[] getCalculatedStartPoint(Texture checkTexture) {
        int[] calculatedStartPoint = null;
        if (!checkTexture.getTextureData().isPrepared()) {
            checkTexture.getTextureData().prepare();
        }
        Pixmap pixmap = checkTexture.getTextureData().consumePixmap();
        for (int x = 0; x < pixmap.getWidth(); x++) {
            for (int y = checkTexture.getHeight() - 1; y >= 0; y--) {
                int pointColor = pixmap.getPixel(x, y);
                if (!backgroundColors.contains(pointColor)) {
                    calculatedStartPoint = new int[]{x, y};
                    break;
                }
            }
            if (calculatedStartPoint != null) {
                break;
            }
        }

        calculatedStartPoint[1] = checkTexture.getHeight() - calculatedStartPoint[1]; //fixes the cameras being upside down...
        return calculatedStartPoint;
    }

    public Texture autoCrop(Texture texture) {
        if (!texture.getTextureData().isPrepared()) {
            texture.getTextureData().prepare();
        }
        Pixmap pixmap = texture.getTextureData().consumePixmap();
        int[] start = null;
        int[] end = null;
        for (int x = 0; x < pixmap.getWidth(); x++) {
            for (int y = 0; y < pixmap.getHeight(); y++) {
                int pixel = pixmap.getPixel(x, y);
                if (start == null && !backgroundColors.contains(pixel)) {
                    start = new int[]{x, y};
                    end = new int[]{x, y};
                }
                if (start != null && !backgroundColors.contains(pixel)) {
                    if (x > end[0]) {
                        end[0] = x;
                    }
                    if (x < start[0]) {
                        start[0] = x;
                    }
                    if (y > end[1]) {
                        end[1] = y;
                    }
                    if (y < start[1]) {
                        start[1] = y;
                    }
                }
            }
        }

        if (start == null) {
            return new Texture(new Pixmap(0, 0, Pixmap.Format.RGBA8888));
        }

        int newWidth = end[0] - start[0] + 1;
        int newHeight = end[1] - start[1] + 1;

        Pixmap newPixMap = new Pixmap(newWidth, newHeight, Pixmap.Format.RGBA8888);
        for (int x = 0; x < newWidth; x++) {
            for (int y = 0; y < newHeight; y++) {
                newPixMap.setColor(pixmap.getPixel(start[0] + x, start[1] + y));
                newPixMap.drawRectangle(x, y, 1, 1);
            }
        }

        Texture newTexture = new Texture(newPixMap);
        newPixMap.dispose();
        croppedStartPoint = start;
        croppedEndPoint = end;
        return newTexture;

    }
}
