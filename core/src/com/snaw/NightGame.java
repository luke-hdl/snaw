package com.snaw;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.utils.ScreenUtils;
import com.snaw.coreState.GenerateGameState;

import java.io.IOException;

public class NightGame extends ApplicationAdapter {
    NightGameTracker tracker = new NightGameTracker();

    @Override
    public void create() {
        try {
            tracker.create();
            GenerateGameState.generateGameState(tracker, "sampleFile.snaw");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render() {
        ScreenUtils.clear(1, 0, 0, 1);
        tracker.render();
    }

    @Override
    public void dispose() {
        tracker.dispose();
    }
}
