package com.snaw.levels.levelBehavior;

import com.snaw.coreState.GameState;
import com.snaw.levels.Level;
import com.snaw.state.stateValues.NumberStateValue;

public class StateSetterBehavior extends LevelBehavior {
    String idType;
    String id;
    String key;

    String stringValue;
    NumberStateValue numValue;

    public StateSetterBehavior(Level level, String key, String stringValue, String idType, String id) {
        super(level);
        this.key = key;
        this.numValue = new NumberStateValue();
        this.stringValue = stringValue;
        this.idType = idType;
        this.id = id;
    }

    public StateSetterBehavior(Level level, String key, NumberStateValue intValue, String idType, String id) {
        super(level);
        this.key = key;
        this.numValue = intValue;
        this.stringValue = null;
        this.idType = idType;
        this.id = id;
    }

    @Override
    public boolean actAndFilter() {
        if (stringValue != null) {
            GameState.getMap().setChildState(idType, id, key, stringValue, true, true);
        } else {
            GameState.getMap().setChildState(idType, id, key, numValue, true, true);
        }
        return true;
    }
}
