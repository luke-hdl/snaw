package com.snaw.characters.characterBehavior;

import com.snaw.characters.SnawCharacter;
import com.snaw.state.Condition;

import java.util.function.Supplier;

public class EndsTurnBehavior extends CharacterBehavior {
    Supplier<Condition<String>> key;

    public EndsTurnBehavior(SnawCharacter character, Supplier<Condition<String>> key) {
        super(character);
        this.key = key;
    }


    @Override
    public boolean actAndFilter() {
        return !characterIs(key.get().getConditionValue());
    }
}
