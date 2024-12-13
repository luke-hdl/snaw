package com.snaw.uielements;

import com.snaw.buttons.ButtonOverlay;
import com.snaw.textureBank.TextureBank;

import java.util.List;

public class UIElement {
    String id;
    List<ButtonOverlay> buttonOverlayList;
    TextureBank textureBank;

    public UIElement(String id, List<ButtonOverlay> buttonOverlayList, TextureBank textureBank) {
        this.id = id;
        this.buttonOverlayList = buttonOverlayList;
        this.textureBank = textureBank;
    }

    public String getId() {
        return id;
    }

    public List<ButtonOverlay> getButtonOverlayList() {
        return buttonOverlayList;
    }

    public void setButtonOverlayList(List<ButtonOverlay> buttonOverlayList) {
        this.buttonOverlayList = buttonOverlayList;
    }

    public TextureBank getTextureBank() {
        return textureBank;
    }

    public void setTextureBank(TextureBank textureBank) {
        this.textureBank = textureBank;
    }
}
