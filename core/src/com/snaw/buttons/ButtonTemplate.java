package com.snaw.buttons;

import com.snaw.textureBank.TextureBank;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.LinkedList;
import java.util.List;

public class ButtonTemplate {
    String id;
    TextureBank buttonAppearance;
    List<ClickAreaTemplate> clickAreaTemplates;

    public ButtonTemplate(String id, TextureBank buttonAppearance, List<ClickAreaTemplate> clickAreaTemplates) {
        this.id = id;
        this.buttonAppearance = buttonAppearance;
        this.clickAreaTemplates = clickAreaTemplates;
    }

    public String getId() {
        return id;
    }

    public void runEvents() {
        throw new NotImplementedException();
    }

    public List<ClickArea> getClickAreas() {
        List<ClickArea> clickAreas = new LinkedList<>();
        clickAreaTemplates.forEach(e -> clickAreas.add(e.getClickArea()));
        return clickAreas;
    }

    public Button buildButton() {
        return new Button(this);
    }
}
