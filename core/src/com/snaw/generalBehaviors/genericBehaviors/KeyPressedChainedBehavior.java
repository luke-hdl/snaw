package com.snaw.generalBehaviors.genericBehaviors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class KeyPressedChainedBehavior extends GenericChainedBehavior {
    protected int key;
    protected boolean chainMultipleTimes;
    protected boolean hasChainedForCurrentStroke = false;

    public KeyPressedChainedBehavior(GenericBehavior chainedBehavior, String checkedKey, boolean chainMultipleTimesPerStroke) {
        super(chainedBehavior);
        key = Input.Keys.valueOf(checkedKey);
        chainMultipleTimes = chainMultipleTimesPerStroke;
    }

    @Override
    public boolean shouldChain() {
        if (!chainMultipleTimes) {
            if (hasChainedForCurrentStroke) {
                if (Gdx.input.isKeyPressed(key)) {
                    hasChainedForCurrentStroke = false;
                }
                return false;
            }
            if (Gdx.input.isKeyPressed(key)) {
                hasChainedForCurrentStroke = true;
                return true;
            }
        }
        return Gdx.input.isKeyPressed(key);
    }
}
