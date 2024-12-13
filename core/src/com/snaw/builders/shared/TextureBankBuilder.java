package com.snaw.builders.shared;

import com.snaw.builders.Builder;
import com.snaw.builders.managers.BuilderManager;
import com.snaw.generalBehaviors.genericBehaviors.GenericBehavior;
import com.snaw.snawfile.SnawTagElement;
import com.snaw.snawfile.SnawTagset;
import com.snaw.textureBank.ProgressingBankedTextureBehaviorFactory;
import com.snaw.textureBank.ProgressingTextureBank;
import com.snaw.textureBank.TextureBank;

import java.util.LinkedList;
import java.util.List;

public class TextureBankBuilder extends Builder<TextureBank> {
    public TextureBankBuilder(BuilderManager manager) {
        super(manager);
    }

    @Override
    public TextureBank build(SnawTagset tagset) {
        // TODO - for SOME reason, textures are just randomly anywhere in the Texture Bank Builder.
        // this is dumb as hell and I don't know why I did it... fix it. -_-
        String renderingLevel = tagset.getOneOffTagValue("RENDERING LEVEL");
        if (renderingLevel == null) {
            renderingLevel = manager.defaultRenderingLevel;
        }
        TextureBank bank;
        if (tagset.getOneOffTagValue("IS PROGRESSING") != null && tagset.getOneOffTagValue("IS PROGRESSING").equals("yes")) {
            ProgressingTextureBank progBank = new ProgressingTextureBank(renderingLevel);
            SnawTagset behaviors = tagset.getOneOffTagset("PROGRESSING BEHAVIORS");
            if (behaviors != null) {
                List<GenericBehavior> progressingBehaviors = new LinkedList<>();
                for (SnawTagElement element : behaviors.getElements("BEHAVIOR")) {
                    progressingBehaviors.add(ProgressingBankedTextureBehaviorFactory.getProgressingBankedTextureBehaviorFromTag(progBank, element.getValue()));
                }
                progBank.setProgressionBehavior(progressingBehaviors);
            }
            SnawTagset progressingTextures = tagset.getOneOffTagset("PROGRESSING TEXTURES");
            if ( progressingTextures != null ) {
                progBank.setProgressingTextures(new AutoProgressingBankedTextureBuilder(manager).build(progressingTextures));
            }
            bank = progBank;
        } else {
            bank = new TextureBank(renderingLevel);
        }

        for (SnawTagset childSet : tagset.getChildren("TEXTURE")) {
            bank.addBankedTexture(new BankedTextureBuilder(manager).build(childSet));
        }
        return bank;
    }
}
