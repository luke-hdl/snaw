package com.snaw.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.snaw.NightGameTracker;
import com.snaw.coreState.GameState;

public abstract class NightGameScreen implements Screen {
    protected final NightGameTracker game;
    protected OrthographicCamera camera;

    long lastTime;

    public NightGameScreen(final NightGameTracker game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        lastTime = System.currentTimeMillis();
    }

    public void updateCamera() {
        camera.setToOrtho(false, 820, 500); // TODO - Update these to the loaded file size.
    }

    @Override
    public void render(float delta) {
        long currentTime = System.currentTimeMillis();
        GameState.setTimeSinceLastMove(currentTime - lastTime);
        lastTime = currentTime;

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.begin();
        game.batch.setProjectionMatrix(camera.combined);
        render();
        processInputs();
        game.batch.end();
    }

    public abstract void render();

    public abstract void processInputs();

    @Override
    public void show() {
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }


    public Camera camera() {
        return camera;
    }
}
