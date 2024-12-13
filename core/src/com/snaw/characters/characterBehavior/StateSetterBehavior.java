package com.snaw.characters.characterBehavior;

import com.snaw.characters.SnawCharacter;
import com.snaw.coreState.GameState;

public class StateSetterBehavior extends CharacterBehavior {
    String idType;
    String id;
    String key;

    String value;

    public StateSetterBehavior(SnawCharacter character, String key, String value, String idType, String id) {
        super(character);
        this.key = key;
        this.value = value;
        this.idType = idType;
        this.id = id;
    }

    @Override
    public boolean actAndFilter() {
        GameState.getMap().setChildState(idType, id, key, value, true, true);
        return true;
    }
}
