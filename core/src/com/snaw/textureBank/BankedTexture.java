package com.snaw.textureBank;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.snaw.buttons.ClickArea;
import com.snaw.generalBehaviors.genericBehaviors.GenericBehavior;
import com.snaw.pixMapUtils.TextureCropper;

import java.util.List;

public abstract class BankedTexture {
    protected ClickArea clickArea;
    protected List<GenericBehavior> bankedTextureBehaviorList;
    protected int[] lastOffsets = new int[]{0, 0};

    public BankedTexture(Texture clickZone, List<GenericBehavior> clickBehaviors, Texture placementTexture, List<GenericBehavior> behaviorList) {
        this.placementTexture = placementTexture;
        this.bankedTextureBehaviorList = behaviorList;
        if (clickZone != null) {
            clickArea = new ClickArea(clickZone, clickBehaviors);
        }
    }

    protected abstract Texture getCurrentTexture();

    protected Texture placementTexture;

    protected int[] placementTextureOffsets;

    public abstract void dispose();

    public abstract void act();

    public boolean isClickable() {
        return clickArea != null;
    }

    public void checkInputs() {
        if (isClickable()) {
            clickArea.processClickEvents(lastOffsets[0], lastOffsets[1]);
        }
    }

    public void render(SpriteBatch batch, int x, int y) {
        lastOffsets[0] = x;
        lastOffsets[1] = y;
        for (GenericBehavior behavior : bankedTextureBehaviorList) {
            if (behavior.actAndFilter()) {
                return;
            }
        }
        checkInputs();
        batch.draw(getCurrentTexture(), x + placementTextureOffsets[0], y + placementTextureOffsets[1]);
    }

    public void cacheOffsetsForPlacementTexture() {
        if (placementTexture == null) {
            placementTextureOffsets = new int[]{0, 0};
        } else {
            placementTextureOffsets = TextureCropper.getCalculatedStartPoint(placementTexture);
            placementTextureOffsets[0] = placementTextureOffsets[0] - getCurrentTexture().getWidth()/2;
            clickArea.setOffsets(-clickArea.getAreaWidth()/2, 0);
        }
    }
}
