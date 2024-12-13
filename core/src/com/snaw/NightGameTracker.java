package com.snaw;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.snaw.screens.NightGameScreen;
import com.snaw.screens.RoomScreen;

public class NightGameTracker extends Game {
    public SpriteBatch batch;
    public BitmapFont font;

    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        this.setScreen(new RoomScreen(this));
    }

    public void render() {
        super.render();
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    public void updateCamera() {
        if (screen.getClass().equals(NightGameScreen.class)) {
            ((NightGameScreen) screen).updateCamera();
        }
    }

    public Camera camera() {
        return ((NightGameScreen) screen).camera();
    }
}
