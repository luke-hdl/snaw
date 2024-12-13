package com.snaw.characters.characterBehavior;

import com.snaw.characters.SnawCharacter;
import com.snaw.logging.LogLevel;
import com.snaw.logging.Logger;

public class DebugBehavior extends CharacterBehavior {
    public DebugBehavior(SnawCharacter character) {
        super(character);
    }

    @Override
    public boolean actAndFilter() {
        Logger.log("Character " + character.getId() + " debugs.", LogLevel.DEBUG_BEHAVIOR);
        return true;
    }
}
