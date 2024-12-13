package com.snaw.menu;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.snaw.buttons.ButtonOverlay;
import com.snaw.coreState.GameState;
import com.snaw.textureBank.TextureBank;

import java.util.List;
import java.util.Map;

public class Menu {
    String id;
    Map<String, TextureBank> textureBanks;
    List<ButtonOverlay> buttons;

    public Menu(String id, Map<String, TextureBank> appearance, List<ButtonOverlay> overlayList) {
        this.id = id;
        this.textureBanks = appearance;
        this.buttons = overlayList;
    }

    public int[] getDimensions() {
        for (TextureBank textureBank : textureBanks.values()) {
            return textureBank.getDimensions();
        }
        return new int[]{0, 0};
    }

    public void process(SpriteBatch batch, int x, int y) {
        //Processes for the active room and its subrooms.
        for (String renderingLevel : GameState.getMenuRenderingLevels()) {
            render(batch, x, y, renderingLevel);
        }
        buttons.forEach(e -> e.runEvents());
    }

    public void renderButtonOverlay(ButtonOverlay overlay, SpriteBatch batch, int x, int y, String renderingLevel) {
        overlay.render(batch, x, getDimensions()[1] - y, renderingLevel);
        int ox = overlay.getRenderingTextureOffset()[0];
        int oy = overlay.getRenderingTextureOffset()[1];

        if (overlay.getRenderingTextureBank() != null) {
            if (overlay.getRenderingTextureBank().getRenderingLevel().equals(renderingLevel)) {
                overlay.getRenderingTextureBank().render(null, batch, x + ox, oy - y);
            }
        }

    }

    public void render(SpriteBatch batch, int x, int y, String currentRenderingLevel) {
        if (textureBanks.containsKey(currentRenderingLevel)) {
            textureBanks.get(currentRenderingLevel).render(null, batch, x, y);
        }
        buttons.forEach(e -> renderButtonOverlay(e, batch, x, y, currentRenderingLevel));
    }

    public String getId() {
        return id;
    }

    public void makeInactiveMenu() {
    }

    public void makeActiveMenu() {
        //No op
    }
}
