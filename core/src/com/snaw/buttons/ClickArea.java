package com.snaw.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.snaw.generalBehaviors.genericBehaviors.GenericBehavior;
import com.snaw.pixMapUtils.GetSnawClickButtonFromTexture;
import com.snaw.pixMapUtils.TextureCropper;
import com.snaw.textureBank.TextureBank;

import java.util.List;

public class ClickArea {
    SnawClickButton updatedCoverageArea;

    boolean isClicked; //Whether we should act.
    boolean hasBeenClicked; //Whether we've acted on a click.
    List<GenericBehavior> clickBehaviors;

    int[] offsets = new int[]{0, 0};

    public ClickArea(Texture texture, List<GenericBehavior> clickBehaviors) {
        updatedCoverageArea = GetSnawClickButtonFromTexture.getButton(texture);
        this.clickBehaviors = clickBehaviors;
    }

    public ClickArea(Texture texture, List<GenericBehavior> clickBehaviors, boolean adjustForCentering) {
        //Click areas that are held in a texture need to be adjusted for their own width.
        updatedCoverageArea = GetSnawClickButtonFromTexture.getButton(texture);
        if ( adjustForCentering ) {
            offsets[0] = -updatedCoverageArea.width/2;
        }
        this.clickBehaviors = clickBehaviors;
    }

    protected static float[] convertMouseToLocalFrame(int[] offsets, SnawClickButton button) {
        // TODO - This calculation depends on the frame size. It should be updated.
        // TODO - should also probably unproject.
        float baseX = Gdx.input.getX();
        float baseY = Gdx.input.getY();
        float x = baseX - offsets[0] - button.clickAreaOffset[0];
        float y = 500 - baseY - offsets[1] - (button.clickAreaOffset[1]);
        //System.out.println(baseX + " " + baseY + " " + x + " " + y);
        return new float[]{x, y};
    }

    public void isClicked(int xOff, int yOff) {
        float[] position = convertMouseToLocalFrame(offsets, updatedCoverageArea);
        boolean over = Gdx.input.isTouched() && updatedCoverageArea.isOver(position[0] - xOff, position[1] - yOff);
        if ( over) {
            System.out.println("OVER: " + this.toString());
        } if (!hasBeenClicked && over) {
            hasBeenClicked = true;
            isClicked = true;
        } else if (!over) {
            hasBeenClicked = false;
        }
    }

    public void processClickEvents(int xOff, int yOff) {
        isClicked(xOff, yOff);
        if (isClicked) {
            System.out.println("Clicked " + this);
            clickBehaviors.forEach(e -> e.act());
            isClicked = false;
        }
    }

    public void dispose() {
        //We don't need to dispose the texture bank itself since it's shared between ClickAreas; we only dispose the smart-cropped texture.
        //This is kind of insanely inefficient, also.
    }

    public void setOffsets(int ox, int oy) {
        this.offsets = new int[]{ox, oy};
    }

    public void updateOffsets(int byX, int byY) {
        offsets[0] += byX;
        offsets[1] += byY;
    }

    public int getAreaWidth() {
        return updatedCoverageArea.width;
    }
}
