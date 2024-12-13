package com.snaw.characters.characterBehavior;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.snaw.characters.SnawCharacter;
import com.snaw.state.stateValues.NumberStateValue;

public class DebugMovementKeysBehavior extends CharacterBehavior {
    public DebugMovementKeysBehavior(SnawCharacter character) {
        super(character);
    }

    @Override
    public boolean actAndFilter() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            character.getState().getNumberStateValueValue("x offset").getConditionValue().subtract(new NumberStateValue(1));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            character.getState().getNumberStateValueValue("x offset").getConditionValue().add(new NumberStateValue(1));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            character.getState().getNumberStateValueValue("y offset").getConditionValue().subtract(new NumberStateValue(1));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)){
            character.getState().getNumberStateValueValue("y offset").getConditionValue().add(new NumberStateValue(1));
        }

        return true;
    }
}
