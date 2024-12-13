package com.snaw.characters.characterBehavior;

import com.snaw.characters.SnawCharacter;

public class MovesInALineBehavior extends CharacterBehavior {
    String[] roomIds;

    public MovesInALineBehavior(SnawCharacter character, String[] roomIds) {
        super(character);
        this.roomIds = roomIds;
    }

    @Override
    public boolean actAndFilter() {
        if (transientCharacterIs("moving")) {
            String roomId = character.getCurrentState().getStringValue("In Room").getConditionValue();
            for (int i = 0; i < roomIds.length - 1; i++) {
                if (roomIds[i].equals(roomId)) {
                    character.getTransientState().setValue("moving to room", roomIds[i + 1]);
                }
            }
        }
        if (character.getTransientState().getStringValue("moving to room").getConditionValue().isEmpty()) {
            character.getTransientState().setValue("moving", false);
        }
        return true;
    }
}
