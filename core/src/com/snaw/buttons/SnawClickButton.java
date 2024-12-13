package com.snaw.buttons;

public class SnawClickButton {
    //Meant to replace TransparentButton. :)
    int[] clickAreaOffset;
    int[] lastDrawOffset;
    int width;
    int height;

    int heightOfSubregion;

    int widthOfSubregion;

    public SnawClickButton(int[] clickAreaOffset, int width, int height, int heightOfSubregion, int widthOfSubregion) {
        this.clickAreaOffset = clickAreaOffset;
        this.width = width;
        this.height = height;
        this.lastDrawOffset = new int[]{0, 0};
        this.heightOfSubregion = heightOfSubregion;
        this.widthOfSubregion = widthOfSubregion;
    }

    //These positions are localized by the calling class; there is no need to calculate startX and startY.
    public boolean isOver(float x, float y) {
        int startX = 0;
        int startY = 0;
        boolean overX = startX <= x && startX + width - 1 >= x;
        boolean overY = startY <= y && startY + height - 1 >= y;
        return overX && overY;
    }
}
