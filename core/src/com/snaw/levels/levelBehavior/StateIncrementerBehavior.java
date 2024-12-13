package com.snaw.levels.levelBehavior;

import com.snaw.coreState.GameState;
import com.snaw.levels.Level;

public class StateIncrementerBehavior extends LevelBehavior {
    String idType;
    String id;
    String key;

    int incrementBy;

    public StateIncrementerBehavior(Level level, String key, int incrementBy, String idType, String id) {
        super(level);
        this.key = key;
        this.idType = idType;
        this.id = id;
        this.incrementBy = incrementBy;
    }

    @Override
    public boolean actAndFilter() {
        GameState.getMap().getNumericChildState(idType, id, key, false, true).getConditionValue().increment();
        return true;
    }
}
