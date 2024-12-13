package com.snaw.generalBehaviors.genericBehaviors;

import com.snaw.coreState.GameState;
import com.snaw.state.Condition;
import com.snaw.state.stateValues.NumberStateValue;

import java.util.function.Supplier;

public class PerTimeChainedBehavior extends GenericChainedBehavior {
    Supplier<Condition<NumberStateValue>> perTime;
    int sinceLastRun;


    public PerTimeChainedBehavior(GenericBehavior chainedBehavior, Supplier<Condition<NumberStateValue>> perTime) {
        super(chainedBehavior);
        this.perTime = perTime;
        sinceLastRun = 0;
    }

    @Override
    public boolean actAndFilter() {
        if (shouldChain()) {
            return chainedBehavior.actAndFilter();
        }

        return true;
    }

    @Override
    public boolean shouldChain() {
        sinceLastRun += GameState.getTimeSinceLastMove();
        if (sinceLastRun > perTime.get().getConditionValue().getInt()) {
            sinceLastRun = 0;
            return true;
        }
        return false;
    }
}
