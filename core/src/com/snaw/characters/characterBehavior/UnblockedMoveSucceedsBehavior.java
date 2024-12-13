package com.snaw.characters.characterBehavior;

import com.snaw.characters.SnawCharacter;

public class UnblockedMoveSucceedsBehavior extends CharacterBehavior {

    public UnblockedMoveSucceedsBehavior(SnawCharacter character) {
        super(character);
    }

    @Override
    public boolean actAndFilter() {
        if (transientCharacterIs("moving") && !transientCharacterIs("moving blocked")) {
            String roomId = character.getTransientState().getStringValue("moving to room").getConditionValue();
            character.setRoom(roomId);
            character.getTransientState().setValue("moved successfully", true);
        }

        return true;
    }
}
