package com.snaw.builders.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.snaw.builders.Builder;
import com.snaw.builders.managers.BuilderManager;
import com.snaw.buttons.ButtonOverlay;
import com.snaw.snawfile.SnawTagset;
import com.snaw.textureBank.BankedTexture;
import com.snaw.textureBank.PlacementTexture;
import com.snaw.textureBank.bankedTextureVariants.NonProgressingTexture;

public class ButtonOverlayBuilder extends Builder<ButtonOverlay> {
    public ButtonOverlayBuilder(BuilderManager manager) {
        super(manager);
    }

    @Override
    public ButtonOverlay build(SnawTagset tagset) {
        String buttonId = tagset.getOneOffTagValue("BUTTON");
        String renderingLevel = tagset.getOneOffTagValue("RENDERING LEVEL");
        if (renderingLevel == null || renderingLevel.isEmpty()) {
            renderingLevel = manager.defaultRenderingLevel;
        }
        String placementTextureValue = tagset.getOneOffTagValue("PLACEMENT TEXTURE");
        BankedTexture texture = new NonProgressingTexture(null, null, null, new Texture(Gdx.files.internal(placementTextureValue)), null);
        PlacementTexture placementTexture = new PlacementTexture(manager.defaultRenderingLevel);
        placementTexture.addBankedTexture(texture);

        return new ButtonOverlay(buttonId, placementTexture, renderingLevel);
    }
}
