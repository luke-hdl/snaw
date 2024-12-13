package com.snaw.builders.shared;

import com.snaw.builders.Builder;
import com.snaw.builders.builtFile.BuiltFile;
import com.snaw.builders.levels.LevelBuilder;
import com.snaw.builders.managers.BuilderManager;
import com.snaw.builders.managers.MenuDataBuilderManager;
import com.snaw.builders.map.GameMapBuilder;
import com.snaw.levels.Level;
import com.snaw.map.GameMap;
import com.snaw.snawfile.SnawTagset;

import java.util.HashMap;
import java.util.Map;

public class FileBuilder extends Builder<BuiltFile> {
    public FileBuilder(BuilderManager manager) {
        super(manager);
    }

    @Override
    public BuiltFile build(SnawTagset tagset) {
        Map<String, GameMap> maps = new HashMap<>();
        Map<String, Level> levels = new HashMap<>();

        int targetFps = Integer.parseInt(tagset.getOneOffTagValue("TARGET FPS"));
        for (SnawTagset mapTagset : tagset.getOneOffTagset("GAME MAPS").getChildren("GAME MAP")) {
            GameMap map = new GameMapBuilder(manager).build(mapTagset);
            maps.put(map.getId(), map);
        }
        for (SnawTagset levelTagset : tagset.getOneOffTagset("LEVELS").getChildren("LEVEL")) {
            Level level = new LevelBuilder(manager).build(levelTagset);
            levels.put(level.getId(), level);
        }

        return new BuiltFile(maps, levels, new MenuDataBuilderManager().build(tagset.getOneOffTagset("MENU DATA")), targetFps);
    }
}
