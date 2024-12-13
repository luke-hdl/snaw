package com.snaw.levels.levelBehavior;

import com.snaw.generalBehaviors.genericBehaviors.GenericBehavior;
import com.snaw.generalBehaviors.genericBehaviors.GenericChainedBehavior;
import com.snaw.levels.Level;

public abstract class ChainedLevelBehavior extends GenericChainedBehavior {
    Level level;

    public ChainedLevelBehavior(Level level, GenericBehavior chainedBehavior) {
        super(chainedBehavior);
        this.level = level;
    }
}
