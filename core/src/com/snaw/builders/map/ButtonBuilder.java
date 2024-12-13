package com.snaw.builders.map;

import com.snaw.builders.Builder;
import com.snaw.builders.managers.BuilderManager;
import com.snaw.builders.shared.TextureBankBuilder;
import com.snaw.buttons.ButtonTemplate;
import com.snaw.buttons.ClickAreaTemplate;
import com.snaw.snawfile.SnawTagset;
import com.snaw.textureBank.TextureBank;

import java.util.LinkedList;
import java.util.List;

public class ButtonBuilder extends Builder<ButtonTemplate> {
    public ButtonBuilder(BuilderManager manager) {
        super(manager);
    }

    @Override
    public ButtonTemplate build(SnawTagset tagset) {
        String id = tagset.getOneOffTagValue("ID");
        TextureBank buttonTextureBank = null;
        SnawTagset textureBankTagset = tagset.getOneOffTagset("TEXTURE BANK");
        if (textureBankTagset != null) {
            buttonTextureBank = new TextureBankBuilder(manager).build(textureBankTagset);
        }

        SnawTagset clickAreaTagset = tagset.getOneOffTagset("CLICK AREAS");
        List<ClickAreaTemplate> clickAreaList = new LinkedList<>();
        if (clickAreaTagset != null) {
            clickAreaList = new ClickAreaBuilder(manager).build(clickAreaTagset);
        }

        return new ButtonTemplate(id, buttonTextureBank, clickAreaList);
    }
}
