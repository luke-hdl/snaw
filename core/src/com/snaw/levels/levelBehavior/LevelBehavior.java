package com.snaw.levels.levelBehavior;

import com.snaw.generalBehaviors.genericBehaviors.GenericBehavior;
import com.snaw.levels.Level;

public abstract class LevelBehavior implements GenericBehavior {
    //Returns true if processing should continue for the event.
    protected Level level;

    public LevelBehavior(Level level) {
        this.level = level;
    }

    public abstract boolean actAndFilter();

}
