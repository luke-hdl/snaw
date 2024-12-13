package com.snaw.pixMapUtils;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.snaw.buttons.SnawClickButton;

import java.util.Arrays;
import java.util.List;

public class GetSnawClickButtonFromTexture {
    private static List<Integer> backgroundColors = Arrays.asList(0, 1, -256); //imagemagick bullshit.

    public static SnawClickButton getButton(Texture texture) {
        if (!texture.getTextureData().isPrepared()) {
            texture.getTextureData().prepare();
        }
        Pixmap pixmap = texture.getTextureData().consumePixmap();
        int[] start = null;
        int[] end = null;
        for (int x = 0; x < texture.getWidth(); x++) {
            for (int y = 0; y < texture.getHeight(); y++) {
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
            pixmap.dispose();
            return new SnawClickButton(new int[]{0, 0}, 0, 0, texture.getHeight(), texture.getWidth());
        }

        int newWidth = end[0] - start[0] + 1;
        int newHeight = end[1] - start[1] + 1;

        //Because images are indexed from the bottom, but the map is indexed from the top
        //We need to "flip" y over the center.
        int[] dimensions = {texture.getWidth(), texture.getHeight()};
        int flipValue = CoordinateFlipper.flipOverSignedMagnitude(start[1], 0, dimensions[1]);
        int flippedSpriteHeight = end[1] - start[1];
        int flipped = start[1] + flipValue - flippedSpriteHeight - 1;

        start[1] = flipped;

        return new SnawClickButton(start, newWidth, newHeight, texture.getHeight(), texture.getWidth());

    }

}
