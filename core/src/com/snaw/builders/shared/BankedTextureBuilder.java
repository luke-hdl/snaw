package com.snaw.builders.shared;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.snaw.builders.Builder;
import com.snaw.builders.managers.BuilderManager;
import com.snaw.builders.map.ClickBehaviorBuilder;
import com.snaw.generalBehaviors.genericBehaviors.GenericBehavior;
import com.snaw.snawfile.SnawTagset;
import com.snaw.textureBank.BankedTexture;
import com.snaw.textureBank.TextureBank;
import com.snaw.textureBank.bankedTextureVariants.NonProgressingTexture;

import java.util.LinkedList;
import java.util.List;

public class BankedTextureBuilder extends Builder<BankedTexture> {

    public BankedTextureBuilder(BuilderManager manager) {
        super(manager);
    }

    @Override
    public BankedTexture build(SnawTagset tagset) {
        Texture texture = new Texture(Gdx.files.internal(tagset.getOneOffTagValue("TEXTURE IMAGE")));

        //Only in nonprogressing right now. needs to be copied over.
        String pathToPlacement = tagset.getOneOffTagValue("PLACEMENT TEXTURE");
        Texture placementTexture = null;
        if ( pathToPlacement != null ){
            placementTexture = new Texture(Gdx.files.internal(pathToPlacement));
        }

        String pathToClickable = tagset.getOneOffTagValue("CLICK TEXTURE");
        Texture clickableTexture = null;
        List<GenericBehavior> clickBehaviorList = new LinkedList<>();

        if ( pathToClickable != null ){
            clickableTexture = new Texture(Gdx.files.internal(pathToClickable));
        }

        if ( clickableTexture != null) {
            SnawTagset clickBehaviorTagset = tagset.getOneOffTagset("CLICK BEHAVIORS");
            if (clickBehaviorTagset != null) {
                clickBehaviorList.addAll(new ClickBehaviorBuilder(manager).build(clickBehaviorTagset));
            }
        }

        //normal
        SnawTagset textureBehaviorTagset = tagset.getOneOffTagset("BANKED TEXTURE BEHAVIORS");
        List<GenericBehavior> behaviorList = new LinkedList<>();
        if (textureBehaviorTagset != null) {
            behaviorList.addAll(new BankedTextureBehaviorBuilder(manager).build(textureBehaviorTagset));
        }
        return new NonProgressingTexture(clickableTexture, clickBehaviorList, placementTexture, texture, behaviorList);
    }
}
