package com.snaw.coreState;

import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.snaw.NightGameTracker;
import com.snaw.builders.builtFile.BuiltFile;
import com.snaw.builders.managers.FileBuilderManager;
import com.snaw.snawfile.SnawFileLoader;
import com.snaw.snawfile.SnawTagset;

import java.io.IOException;

public class GenerateGameState {
    public static void generateGameState(NightGameTracker tracker, String file) throws IOException {
        SnawTagset tagset = new SnawFileLoader().load(file);
        BuiltFile builtFile = new FileBuilderManager().build(tagset.getOneOffTagset("FILE"));
        GameState.maps = builtFile.getMaps();
        GameState.levels = builtFile.getLevels();
        GameState.tracker = tracker;
        ScalingViewport viewp = new FitViewport(820, 500, tracker.camera()); // change this to your needed viewport
        GameState.setViewport(viewp);
        GameState.menuRenderingLevels = builtFile.menuData.getRenderingLevels();
        GameState.menus = builtFile.menuData.getMenus();
        GameState.changeMenus(builtFile.menuData.getMainMenu());
        GameState.targetFps = builtFile.getTargetFps();
        GameState.completeInitialization();
    }
}
