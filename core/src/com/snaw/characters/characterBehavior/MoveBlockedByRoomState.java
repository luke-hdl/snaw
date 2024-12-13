package com.snaw.characters.characterBehavior;

import com.snaw.characters.SnawCharacter;

public class MoveBlockedByRoomState extends CharacterBehavior {
    String key;

    public MoveBlockedByRoomState(SnawCharacter character, String key) {
        super(character);
        this.key = key;
    }

    @Override
    public boolean actAndFilter() {
        if (transientCharacterIs("moving") && character.getRoom().getStateBoolValue(key).getConditionValue()) {
            character.getTransientState().setValue("moving", false);
            character.getTransientState().setValue("moving blocked", true);
        }

        return true;
    }
}
