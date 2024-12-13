package com.snaw.characters.characterBehavior;

import com.snaw.characters.SnawCharacter;

public class MoveIfBlockedBehavior extends CharacterBehavior {
    String roomId;

    public MoveIfBlockedBehavior(SnawCharacter character, String roomId) {
        super(character);
        this.roomId = roomId;
    }

    @Override
    public boolean actAndFilter() {
        if (transientCharacterIs("moving blocked")) {
            character.setRoom(roomId);
            character.getTransientState().setValue("blocked successfully", true);
        }

        return true;
    }
}
