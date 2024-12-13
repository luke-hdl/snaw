package com.snaw.buttons;

import com.snaw.generalBehaviors.genericBehaviors.GenericBehavior;
import com.snaw.textureBank.TextureBank;

import java.util.List;

public class ClickAreaTemplate {
    TextureBank coverageBank;
    List<GenericBehavior> clickBehaviors;

    public ClickAreaTemplate(TextureBank coverageBank, List<GenericBehavior> clickBehaviors) {
        this.coverageBank = coverageBank;
        this.clickBehaviors = clickBehaviors;
    }

    public ClickArea getClickArea() {
        return new ClickArea(coverageBank.getTopTexture(), clickBehaviors);
    }
}
