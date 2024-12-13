package com.snaw.screens;

import com.snaw.NightGameTracker;
import com.snaw.coreState.GameState;
import com.snaw.exceptions.LevelHasEndedException;

public class RoomScreen extends NightGameScreen {
    public RoomScreen(NightGameTracker game) {
        super(game);
    }

    long timeSinceLastAttemptedRender = 0l;

    @Override
    public void render() {
        game.batch.setProjectionMatrix(camera.combined);
        if (GameState.getCurrentLevel() != null) {
            try {
                GameState.getCurrentLevel().runLevel();
                GameState.getCurrentLevel().renderLevel(game.batch);
            } catch (LevelHasEndedException e) {
                GameState.setCurrentLevel(null);
                GameState.changeMenus(e.getLevelEndScreenId());
            }
        } else if (GameState.getCurrentMenu() != null) {
            GameState.getCurrentMenu().process(game.batch, 0, 0);
        }
    }

    protected boolean shouldRender( ){
        long timePerRender = 1000l/GameState.getTargetFps();
        if ( timeSinceLastAttemptedRender >= timePerRender ) {
            timeSinceLastAttemptedRender = timeSinceLastAttemptedRender - timePerRender;
            return true;
        } else {
            timeSinceLastAttemptedRender += GameState.getTimeSinceLastMove();
        }
        return false;
    }

    @Override
    public void processInputs() {
        //Not generally used.
        //We use listeners instead.
    }
}
