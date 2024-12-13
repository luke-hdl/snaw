package com.snaw.characters.characterBehavior;

import com.snaw.characters.SnawCharacter;
import com.snaw.generalBehaviors.genericBehaviors.GenericChainedBehavior;

public abstract class ChainedCharacterBehavior extends GenericChainedBehavior {
    SnawCharacter character;

    public ChainedCharacterBehavior(SnawCharacter character, CharacterBehavior chainedBehavior) {
        super(chainedBehavior);
        this.character = character;
    }

    public abstract boolean shouldChain();
}
