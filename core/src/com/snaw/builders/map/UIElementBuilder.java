package com.snaw.builders.map;

import com.snaw.builders.Builder;
import com.snaw.builders.managers.BuilderManager;
import com.snaw.builders.shared.TextureBankBuilder;
import com.snaw.buttons.ButtonOverlay;
import com.snaw.snawfile.SnawTagset;
import com.snaw.textureBank.TextureBank;
import com.snaw.uielements.UIElement;

import java.util.LinkedList;
import java.util.List;

public class UIElementBuilder extends Builder<UIElement> {
    public UIElementBuilder(BuilderManager manager) {
        super(manager);
    }

    @Override
    public UIElement build(SnawTagset tagset) {
        String id = tagset.getOneOffTagValue("ID");
        List<ButtonOverlay> buttonOverlayList = new LinkedList<>();
        SnawTagset overlays = tagset.getOneOffTagset("BUTTON OVERLAYS");
        if (overlays != null) {
            overlays.getChildren("BUTTON OVERLAY").forEach(e -> buttonOverlayList.add(new ButtonOverlayBuilder(manager).build(e)));
        }
        TextureBank textureBank = null;
        SnawTagset textureBankTagset = tagset.getOneOffTagset("TEXTURE BANK");
        if (textureBankTagset != null) {
            textureBank = new TextureBankBuilder(manager).build(textureBankTagset);
        } else {
            textureBank = new TextureBank(manager.defaultRenderingLevel);
        }
        return new UIElement(id, buttonOverlayList, textureBank);
    }
}
