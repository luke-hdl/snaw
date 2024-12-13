package com.snaw.builders.menu;

import com.snaw.builders.Builder;
import com.snaw.builders.managers.BuilderManager;
import com.snaw.builders.map.ButtonOverlayBuilder;
import com.snaw.builders.shared.TextureBankBuilder;
import com.snaw.buttons.ButtonOverlay;
import com.snaw.menu.Menu;
import com.snaw.snawfile.SnawTagset;
import com.snaw.textureBank.TextureBank;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MenuBuilder extends Builder<Menu> {

    public MenuBuilder(BuilderManager manager) {
        super(manager);
    }

    @Override
    public Menu build(SnawTagset tagset) {
        String id = tagset.getOneOffTagValue("ID");

        Map<String, TextureBank> textureBankMap = new HashMap<>();
        String main = tagset.getOneOffTagValue("IS START MENU");

        SnawTagset textureBanksSet = tagset.getOneOffTagset("TEXTURE BANKS");
        if (textureBanksSet != null) {
            for (SnawTagset textureBankSet : textureBanksSet.getChildren("TEXTURE BANK")) {
                TextureBank bank = new TextureBankBuilder(manager).build(textureBankSet);

                textureBankMap.put(bank.getRenderingLevel(), bank);
            }
        }

        List<ButtonOverlay> overlayList = new LinkedList<>();
        SnawTagset overlaysSet = tagset.getOneOffTagset("BUTTON OVERLAYS");
        if (overlaysSet != null) {
            for (SnawTagset overlaySet : overlaysSet.getChildren("BUTTON OVERLAY")) {
                overlayList.add(new ButtonOverlayBuilder(manager).build(overlaySet));
            }
        }

        Menu menu = new Menu(id, textureBankMap, overlayList);
        if (main != null && main.equals("true")) {
            manager.mainMenu = menu;
        }

        return menu;
    }
}
