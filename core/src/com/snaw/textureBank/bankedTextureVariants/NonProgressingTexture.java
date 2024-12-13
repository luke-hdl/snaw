package com.snaw.textureBank.bankedTextureVariants;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.snaw.buttons.ClickArea;
import com.snaw.generalBehaviors.genericBehaviors.GenericBehavior;
import com.snaw.textureBank.BankedTexture;

import java.util.List;

public class NonProgressingTexture extends BankedTexture {
    protected Texture texture;

    public NonProgressingTexture(Texture clickZone, List<GenericBehavior> clickBehaviors, Texture placementTexture, Texture texture, List<GenericBehavior> behaviorList) {
        super(clickZone, clickBehaviors, placementTexture, behaviorList);
        this.texture = texture;
        cacheOffsetsForPlacementTexture();
    }
    
    @Override
    public void act() {
    }

    @Override
    protected Texture getCurrentTexture() {
        return texture;
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
