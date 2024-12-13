package com.snaw.characters.characterBehavior;

import com.snaw.characters.SnawCharacter;
import com.snaw.coreState.GameState;
import com.snaw.state.Condition;
import com.snaw.state.stateValues.NumberStateValue;

import java.util.function.Supplier;

public class TriesToMoveBehavior extends CharacterBehavior {
    Supplier<Condition<NumberStateValue>> clickSpeed;
    long timeSinceLastClick = 0l;

    public TriesToMoveBehavior(SnawCharacter character, Supplier<Condition<NumberStateValue>> clickSpeed) {
        super(character);
        this.clickSpeed = clickSpeed;
    }

    @Override
    public boolean actAndFilter() {
        timeSinceLastClick = timeSinceLastClick + GameState.getTimeSinceLastMove();
        if (timeSinceLastClick > clickSpeed.get().getConditionValue().getInt()) {
            timeSinceLastClick = 0l;
            character.getTransientState().setValue("moving", true);
        }
        return true;
    }
}
