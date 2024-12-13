package com.snaw.levels.levelBehavior;

import com.snaw.characters.SnawCharacter;
import com.snaw.coreState.GameState;
import com.snaw.generalBehaviors.genericBehaviors.GenericBehavior;
import com.snaw.levels.Level;

public class BeingAttackedChainedBehavior extends ChainedLevelBehavior {
    public BeingAttackedChainedBehavior(Level level, GenericBehavior chainedBehavior) {
        super(level, chainedBehavior);
    }

    @Override
    public boolean shouldChain() {
        for (SnawCharacter character : GameState.getCurrentLevel().getMap().getCharacters().values()) {
            if (character.getTransientState().checkValue("attacking", true)) {
                return true;
            }
        }
        return false;
    }
}
