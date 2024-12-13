package com.snaw.textureBank;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.snaw.state.State;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class TextureBank {
    protected List<BankedTexture> textureList = new LinkedList<>();
    protected String renderingLevel;
    int[] calculatedStartPoint = null;
    private List<Integer> backgroundColors = Arrays.asList(0, -1, 1, -256, Color.WHITE.toIntBits()); //imagemagick bullshit.

    public TextureBank(String renderingLevel) {
        this.renderingLevel = renderingLevel;
    }

    public void addBankedTexture(BankedTexture bankedTexture) {
        textureList.add(bankedTexture);
    }


    public void performAnyActions() {
        //No-op.
    }

    public void  render(State state, SpriteBatch batch, int x, int y) {
        int[] offsets = state == null ? new int[]{0, 0} : state.getStateBasedOffsets();
        textureList.forEach(e -> e.render(batch, x + offsets[0], y + offsets[1]));
    }

    public int[] getDimensions() {
        return new int[]{textureList.get(0).getCurrentTexture().getWidth(), textureList.get(0).getCurrentTexture().getHeight()};
    }

    public int[][] getStartAndEndPointsDroppingColor(int color) {
        Texture checkTexture = textureList.get(0).getCurrentTexture();
        if (!checkTexture.getTextureData().isPrepared()) {
            checkTexture.getTextureData().prepare();
        }
        Pixmap pixmap = checkTexture.getTextureData().consumePixmap();
        int startX = -1;
        int endX = -1;
        int startY = -1;
        int endY = -1;
        for (int x = 0; x < pixmap.getWidth(); x++) {
            for (int y = checkTexture.getHeight() - 1; y >= 0; y--) {
                if (startX < 0 && pixmap.getPixel(x, y) != color) {
                    startX = x;
                    startY = y;
                }

                if (startX >= 0 && pixmap.getPixel(x, y) == color) {
                    endX = x;
                    endY = y;
                }
            }
        }

        return new int[][]{new int[]{startX, checkTexture.getHeight() - startY}, new int[]{endX, checkTexture.getHeight() - endY}};
    }

    public int[] getCalculatedStartPoint() {
        return getCalculatedStartPoint(backgroundColors);
    }

    public int[] getCalculatedStartPoint(List<Integer> colors) {
        if (calculatedStartPoint == null) {
            Texture checkTexture = textureList.get(0).getCurrentTexture();
            if (!checkTexture.getTextureData().isPrepared()) {
                checkTexture.getTextureData().prepare();
            }
            Pixmap pixmap = checkTexture.getTextureData().consumePixmap();
            for (int x = 0; x < pixmap.getWidth(); x++) {
                for (int y = checkTexture.getHeight() - 1; y >= 0; y--) {
                    int pointColor = pixmap.getPixel(x, y);
                    if (!colors.contains(pointColor)) {
                        calculatedStartPoint = new int[]{x, y};
                        break;
                    }
                }
                if (calculatedStartPoint != null) {
                    break;
                }
            }

            calculatedStartPoint[1] = checkTexture.getHeight() - calculatedStartPoint[1]; //fixes the cameras being upside down...
        }
        return calculatedStartPoint;
    }

    public Texture getTopTexture() {
        return textureList.get(0).getCurrentTexture();
    }

    public String getRenderingLevel() {
        return renderingLevel;
    }
}
