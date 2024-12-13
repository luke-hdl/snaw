package com.snaw.coreState;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.snaw.NightGameTracker;
import com.snaw.levels.Level;
import com.snaw.map.GameMap;
import com.snaw.map.Room;
import com.snaw.menu.Menu;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class GameState {
    public static Menu currentMenu;
    static NightGameTracker tracker;
    static Level currentLevel;

    static List<String> menuRenderingLevels;

    static Map<String, Menu> menus;
    static Map<String, Level> levels;
    static Map<String, GameMap> maps;

    static Viewport viewport; //used to manage Room stages. Should probably be somewhere else.
    static Random random = new Random();

    static long timeSinceLastMove = 0l;

    static int targetFps;

    static Room currentRoom; // TODO - move this to Map/Level???

    static boolean initialized = false;

    public static boolean hasBeenInitialized() {
        return initialized;
    }

    public static void completeInitialization() {
        initialized = true;
    }

    public static GameMap getMap(String mapId) {
        return maps.get(mapId);
    }

    public static GameMap getMap() {
        if (currentLevel == null) {
            //Buttons for maps are currently pulled from a random map?
            //Until I refactor, anyways...
            // TODO do that
            for (GameMap map : maps.values()) {
                return map;
            }
        }
        return currentLevel.getMap();
    }

    public static double randomChance() {
        return random.nextDouble();
    }

    public static Room getCurrentRoom() {
        return currentRoom;
    }

    public static Level getCurrentLevel() {
        return currentLevel;
    }

    public static void setCurrentLevel(String levelId) {
        currentLevel = getLevel(levelId);
        if (currentLevel != null) {
            currentLevel.setup();
        }
    }

    public static void changeMenus(String menuId) {
        changeMenus(menus.get(menuId));
    }

    public static void changeMenus(Menu menu) {
        if (currentMenu != null) {
            currentMenu.makeInactiveMenu();
        }
        currentMenu = menu;
        currentMenu.makeActiveMenu();
        tracker.updateCamera();
    }

    public static void changeRooms(Room room) {
        if (currentRoom != null) {
            currentRoom.makeInactiveRoom();
        }
        currentRoom = room;
        currentRoom.makeActiveRoom();
        tracker.updateCamera();
    }

    public static long getTimeSinceLastMove() {
        //The Stage holds buttons.
        return timeSinceLastMove;
    }

    public static void setTimeSinceLastMove(long newTime) {
        //The Stage holds buttons.
        timeSinceLastMove = newTime;
    }

    public static void setViewport(ScalingViewport viewp) {
        viewport = viewp;
    }

    public static Level getLevel(String levelId) {
        return levels.get(levelId);
    }

    public static List<String> getMenuRenderingLevels() {
        return menuRenderingLevels;
    }

    public static Menu getCurrentMenu() {
        return currentMenu;
    }

    public static int getTargetFps() {
        return targetFps;
    }
}
