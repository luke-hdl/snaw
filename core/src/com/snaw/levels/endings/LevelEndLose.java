package com.snaw.levels.endings;

import com.snaw.exceptions.LevelHasEndedException;
import com.snaw.logging.LogLevel;
import com.snaw.logging.Logger;

public class LevelEndLose implements LevelEnd {

    public void activate() {
        Logger.log("Game ended: you lost!", LogLevel.DEBUG_GAME);
        throw new LevelHasEndedException("Lost");
    }
}
