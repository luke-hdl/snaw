package com.snaw.characters.characterBehavior;

import com.snaw.characters.SnawCharacter;

public class NothingHappensBehavior extends CharacterBehavior {
    public NothingHappensBehavior(SnawCharacter character) {
        super(character);
    }

    @Override
    public boolean actAndFilter() {
        return true;
    }
}
