package com.snaw.builders.map;

import com.snaw.builders.Builder;
import com.snaw.builders.managers.BuilderManager;
import com.snaw.builders.shared.TextureBankBuilder;
import com.snaw.buttons.ClickAreaTemplate;
import com.snaw.generalBehaviors.genericBehaviors.GenericBehavior;
import com.snaw.snawfile.SnawTagset;
import com.snaw.textureBank.TextureBank;

import java.util.LinkedList;
import java.util.List;

public class ClickAreaBuilder extends Builder<List<ClickAreaTemplate>> {
    public ClickAreaBuilder(BuilderManager manager) {
        super(manager);
    }

    @Override
    public List<ClickAreaTemplate> build(SnawTagset tagset) {
        List<ClickAreaTemplate> clickAreas = new LinkedList<>();

        for (SnawTagset clickAreaTagset : tagset.getChildren("CLICK AREA")) {
            TextureBank textureBank = null;
            SnawTagset textureBankTagset = clickAreaTagset.getOneOffTagset("TEXTURE BANK");
            if (textureBankTagset != null) {
                textureBank = new TextureBankBuilder(manager).build(textureBankTagset);
            }

            List<GenericBehavior> behaviors = new LinkedList<>();
            SnawTagset clickTagset = clickAreaTagset.getOneOffTagset("CLICK BEHAVIORS");
            if (clickTagset != null) {
                behaviors = new ClickBehaviorBuilder(manager).build(clickTagset);
            }

            clickAreas.add(new ClickAreaTemplate(textureBank, behaviors));
        }
        return clickAreas;
    }
}
