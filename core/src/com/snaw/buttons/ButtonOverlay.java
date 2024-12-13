package com.snaw.buttons;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.snaw.coreState.GameState;
import com.snaw.textureBank.PlacementTexture;
import com.snaw.textureBank.TextureBank;

public class ButtonOverlay {
    String buttonId;
    PlacementTexture placement;
    String renderingLevel;
    private Button button;

    public ButtonOverlay(String buttonId, PlacementTexture texture, String renderingLevel) {
        this.buttonId = buttonId;
        this.placement = texture;
        this.renderingLevel = renderingLevel;
    }

    public ButtonOverlay getCopy() {
        return new ButtonOverlay(buttonId, placement, renderingLevel);
    }

    public void runEvents() {
        getButton().runEvents();
    }

    public Button getButton() {
        //Gets button without adding it to the stage.
        if (button == null) {
            button = GameState.getMap().getButtons().get(buttonId).buildButton();
            int ox = getRenderingTextureOffset()[0];
            int oy = getRenderingTextureOffset()[1];
            button.setOffsets(ox, oy);
        }

        return button;
    }

    public void render(SpriteBatch batch, int x, int y, String renderingLevel) {
        if (renderingLevel.equals(this.renderingLevel) && getButton().buttonAppearance != null) {
            getButton().render(batch, x, y);
        }

    }

    public TextureBank getRenderingTextureBank() {
        return getButton().buttonAppearance;
    }

    public int[] getRenderingTextureOffset() {
        return placement.getCalculatedStartPoint();
    }
}
