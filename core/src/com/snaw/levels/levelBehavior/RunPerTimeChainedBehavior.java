package com.snaw.levels.levelBehavior;

import com.snaw.coreState.GameState;
import com.snaw.generalBehaviors.genericBehaviors.GenericBehavior;
import com.snaw.levels.Level;

public class RunPerTimeChainedBehavior extends ChainedLevelBehavior {
    int perTime;
    int sinceLastRun;

    public RunPerTimeChainedBehavior(Level level, GenericBehavior chainedBehavior, int perTime) {
        super(level, chainedBehavior);
        this.perTime = perTime;
        sinceLastRun = 0;
    }

    @Override
    public boolean shouldChain() {
        sinceLastRun += GameState.getTimeSinceLastMove();
        if (sinceLastRun > perTime) {
            sinceLastRun = 0;
            return true;
        }
        return false;
    }
}
