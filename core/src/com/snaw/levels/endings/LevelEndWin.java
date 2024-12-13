package com.snaw.levels.endings;

import com.snaw.exceptions.LevelHasEndedException;
import com.snaw.logging.LogLevel;
import com.snaw.logging.Logger;

public class LevelEndWin implements LevelEnd {

    public void activate() {
        Logger.log("Game ended: you won!", LogLevel.DEBUG_GAME);
        throw new LevelHasEndedException("Won");
    }
}
