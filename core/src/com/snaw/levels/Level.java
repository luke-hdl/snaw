package com.snaw.levels;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.snaw.coreState.GameState;
import com.snaw.exceptions.CrashGameException;
import com.snaw.exceptions.LevelHasEndedException;
import com.snaw.generalBehaviors.genericBehaviors.GenericBehavior;
import com.snaw.logging.LogLevel;
import com.snaw.logging.Logger;
import com.snaw.map.GameMap;

import java.util.List;

public class Level {
    protected String id;
    protected String mapId;
    protected GameMap map;
    //A distinct Level.
    //Levels hold a pointer to a Map; depending on your game, it may be that every level is in the same map (but with different states set).
    //Or it might be that some have their own maps.
    List<GenericBehavior> ongoingBehaviorList;
    List<GenericBehavior> setupBehaviorList;

    public Level(String id, String mapId) {
        this.id = id;
        this.mapId = mapId;
    }

    public String getId() {
        return id;
    }

    public GameMap getMap() {
        if (map == null) {
            map = GameState.getMap(mapId);
        }
        return map;
    }

    public void setup() {
        getMap().initialize();
        for (GenericBehavior behavior : setupBehaviorList) {
            if (!behavior.actAndFilter()) {
                break;
            }
        }
    }

    public void renderLevel(SpriteBatch batch){
        getMap().render(batch);
    }

    public void runLevel() {
        try {
            for (GenericBehavior behavior : ongoingBehaviorList) {
                if (!behavior.actAndFilter()) {
                    break;
                }
            }

            getMap().process();
        } catch ( RuntimeException e ) {
            if ( e.getClass().equals(CrashGameException.class) || e.getClass().equals(LevelHasEndedException.class)) {
                throw e;
            }
            else {
                Logger.log(e, LogLevel.UNEXPECTED_EXCEPTION);
            }
        }
    }

    public List<GenericBehavior> getOngoingBehaviorList() {
        return ongoingBehaviorList;
    }

    public void setOngoingBehaviorList(List<GenericBehavior> ongoingBehaviorList) {
        this.ongoingBehaviorList = ongoingBehaviorList;
    }

    public List<GenericBehavior> getSetupBehaviorList() {
        return setupBehaviorList;
    }

    public void setSetupBehaviorList(List<GenericBehavior> setupBehaviorList) {
        this.setupBehaviorList = setupBehaviorList;
    }

}
