package com.snaw.screens;

import com.snaw.NightGameTracker;

public class MainMenuScreen extends NightGameScreen {
    public MainMenuScreen(NightGameTracker game) {
        super(game);
    }

    @Override
    public void render() {
        game.batch.setProjectionMatrix(camera.combined);
        game.font.draw(game.batch, "-- AWW YEAH ITS SNAW TIME --", 100, 150);
        game.font.draw(game.batch, "-- click to begin --", 100, 100);
    }

    @Override
    public void processInputs() {

    }
}
