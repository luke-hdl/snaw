package com.snaw.textureBank.bankedTextureVariants;

import com.badlogic.gdx.graphics.Texture;
import com.snaw.exceptions.DebugException;
import com.snaw.generalBehaviors.genericBehaviors.GenericBehavior;
import com.snaw.textureBank.BankedTexture;

import java.util.List;

public class ManuallyProgressingTexture extends BankedTexture {
    // TODO - This needs to be updated to do anything at all.
    public ManuallyProgressingTexture(Texture clickZone, List<GenericBehavior> clickBehaviors, Texture placementTexture, List<GenericBehavior> behaviorList) {
        super(clickZone, clickBehaviors, placementTexture, behaviorList);
        cacheOffsetsForPlacementTexture();
    }

    @Override
    public void act() {
        throw new DebugException("Cannot manually progress textures yet. Because. You didn't implement it???");
    }

    @Override
    protected Texture getCurrentTexture() {
        return null;
    }

    @Override
    public void dispose() {

    }
}
