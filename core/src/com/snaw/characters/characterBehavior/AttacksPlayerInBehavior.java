package com.snaw.characters.characterBehavior;

import com.snaw.characters.SnawCharacter;

import java.util.List;

public class AttacksPlayerInBehavior extends CharacterBehavior {
    private List<String> roomIds;

    public AttacksPlayerInBehavior(SnawCharacter character, List<String> roomIds) {
        super(character);
        this.roomIds = roomIds;
    }

    @Override
    public boolean actAndFilter() {
        String roomId = character.getCurrentState().getStringValue("In Room").getConditionValue();
        if (roomIds.contains(roomId)) {
            character.getTransientState().setValue("attacking", true);
        }
        return false;
    }
}
