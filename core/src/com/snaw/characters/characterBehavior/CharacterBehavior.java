package com.snaw.characters.characterBehavior;

import com.snaw.characters.SnawCharacter;
import com.snaw.generalBehaviors.genericBehaviors.GenericBehavior;

public abstract class CharacterBehavior implements GenericBehavior {
    protected final SnawCharacter character;

    public CharacterBehavior(SnawCharacter character) {
        this.character = character;
    }

    @Override
    public abstract boolean actAndFilter(); //Returns whether the character should continue to act this round.

    protected boolean characterIs(String tag) {
        return transientCharacterIs(tag) || nontransientCharacterIs(tag);
    }

    protected boolean transientCharacterIs(String tag) {
        return character.getTransientState().checkValue(tag, true);
    }

    protected boolean nontransientCharacterIs(String tag) {
        return character.getCurrentState().checkValue(tag, true);
    }
}
