package com.snaw.levels.levelBehavior;

import com.snaw.levels.Level;
import com.snaw.levels.endings.LevelEnd;

public abstract class LevelEndBehavior extends LevelBehavior {
    //For winning and losing.
    protected LevelEnd endResult;

    public LevelEndBehavior(Level level, LevelEnd levelEnd) {
        super(level);
        this.endResult = levelEnd;
    }

    @Override
    public boolean actAndFilter() {
        if (levelHasEnded()) {
            endResult.activate();
            return false; //generally shouldn't activate, since endResult.activate should break out of the level loop.
        }
        return true;
    }

    public abstract boolean levelHasEnded();

}
