package com.snaw.builders.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.snaw.builders.Builder;
import com.snaw.builders.managers.BuilderManager;
import com.snaw.map.ParentRoomRelationship;
import com.snaw.snawfile.SnawTagset;
import com.snaw.textureBank.BankedTexture;
import com.snaw.textureBank.PlacementTexture;
import com.snaw.textureBank.bankedTextureVariants.NonProgressingTexture;

public class ParentRoomRelationshipBuilder extends Builder<ParentRoomRelationship> {
    public ParentRoomRelationshipBuilder(BuilderManager manager) {
        super(manager);
    }

    @Override
    public ParentRoomRelationship build(SnawTagset tagset) {
        String parentRoomId = tagset.getOneOffTagValue("PARENT ROOM");
        String childRoomId = tagset.getOneOffTagValue("CHILD ROOM");
        String placementTextureValue = tagset.getOneOffTagValue("PLACEMENT TEXTURE");
        BankedTexture texture = new NonProgressingTexture(null, null, null, new Texture(Gdx.files.internal(placementTextureValue)), null);
        PlacementTexture placementTexture = new PlacementTexture(manager.defaultRenderingLevel);
        placementTexture.addBankedTexture(texture);

        return new ParentRoomRelationship(placementTexture, parentRoomId, childRoomId);
    }
}
