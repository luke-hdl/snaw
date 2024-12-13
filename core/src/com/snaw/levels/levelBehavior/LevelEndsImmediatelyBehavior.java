package com.snaw.levels.levelBehavior;

import com.snaw.levels.Level;
import com.snaw.levels.endings.LevelEnd;

public class LevelEndsImmediatelyBehavior extends LevelEndBehavior {
    //Primarily for chaining.

    public LevelEndsImmediatelyBehavior(Level level, LevelEnd levelEnd) {
        super(level, levelEnd);
    }

    @Override
    public boolean levelHasEnded() {
        return true;
    }
}
