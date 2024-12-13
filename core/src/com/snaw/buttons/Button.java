package com.snaw.buttons;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.snaw.state.State;
import com.snaw.textureBank.TextureBank;

import java.util.List;

public class Button {
    //Buttons are disposed and recreated; however, the ID and ButtonAppearance objects are not.
    String id;
    TextureBank buttonAppearance;
    List<ClickArea> clickAreas;
    int[] lastOffsets = new int[]{0, 0};
    //We process click events on any frame, but only know offsets on render frames.
    //So we treat the offsets on non-render frames as equal.

    public Button(ButtonTemplate template) {
        this.id = template.id;
        this.buttonAppearance = template.buttonAppearance;
        this.clickAreas = template.getClickAreas();
    }

    public String getId() {
        return id;
    }

    public void dispose() {
        clickAreas.forEach(e -> e.dispose());
    }

    public void runEvents() {
        for (ClickArea clickArea : clickAreas) {
            clickArea.processClickEvents(lastOffsets[0], lastOffsets[1]);
        }
    }

    public void render(SpriteBatch batch, int x, int y) {
        lastOffsets[0] = x;
        lastOffsets[1] = y;
        //buttonAppearance.render(null, batch, x, 500 - y);
    }

    public void setOffsets(int ox, int oy) {
        for (ClickArea clickArea : clickAreas) {
            clickArea.setOffsets(ox, oy);
        }
    }
}
