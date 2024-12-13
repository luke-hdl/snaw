package com.snaw.builders.builtFile;

import com.snaw.levels.Level;
import com.snaw.map.GameMap;

import java.util.Map;

public class BuiltFile {
    public MenuData menuData;
    //Holds a built file.
    //Not directly used once the game is generated -- this is just where built data is stored and used to generate the GameState object.
    protected Map<String, GameMap> maps;
    protected Map<String, Level> levels;
    protected int targetFps;

    public BuiltFile(Map<String, GameMap> maps, Map<String, Level> levels, MenuData menuData, int targetFps) {
        this.maps = maps;
        this.levels = levels;
        this.menuData = menuData;
        this.targetFps = targetFps;
    }

    public int getTargetFps( ){
        return targetFps;
    }

    public Map<String, GameMap> getMaps() {
        return maps;
    }

    public Map<String, Level> getLevels() {
        return levels;
    }
}
