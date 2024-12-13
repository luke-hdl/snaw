package com.snaw.builders.shared;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.snaw.builders.Builder;
import com.snaw.builders.managers.BuilderManager;
import com.snaw.builders.map.ClickBehaviorBuilder;
import com.snaw.generalBehaviors.genericBehaviors.GenericBehavior;
import com.snaw.logging.LogLevel;
import com.snaw.logging.Logger;
import com.snaw.snawfile.SnawTagElement;
import com.snaw.snawfile.SnawTagset;
import com.snaw.textureBank.bankedTextureVariants.AutoProgressingTexture;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class AutoProgressingBankedTextureBuilder extends Builder<List<AutoProgressingTexture>> {
    public AutoProgressingBankedTextureBuilder(BuilderManager manager) {
        super(manager);
    }

    @Override
    public List<AutoProgressingTexture> build(SnawTagset tagset) {
        List<AutoProgressingTexture> textures = new LinkedList<>();
        for (SnawTagElement element : tagset.getElements("AUTOPROGRESSING TEXTURE")) {
            String folderPath = element.getValue();
            FileHandle folder = Gdx.files.local("assets/" + folderPath);
            if ( !folder.isDirectory() ) {
                Logger.log("File " + folderPath + " is not a directory!", LogLevel.DEBUG_LOAD);
            }
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
            List<FileHandle> handles = Arrays.asList(folder.list());
            textures.add(new AutoProgressingTexture(clickableTexture, clickBehaviorList, placementTexture, handles, folderPath));
        }
        return textures;
    }
}
