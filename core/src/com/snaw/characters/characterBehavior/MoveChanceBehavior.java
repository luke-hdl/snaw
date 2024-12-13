package com.snaw.characters.characterBehavior;

import com.snaw.characters.SnawCharacter;
import com.snaw.coreState.GameState;
import com.snaw.state.Condition;
import com.snaw.state.stateValues.NumberStateValue;

import java.util.function.Supplier;

public class MoveChanceBehavior extends CharacterBehavior {
    private Supplier<Condition<NumberStateValue>> chance;

    public MoveChanceBehavior(SnawCharacter character, Supplier<Condition<NumberStateValue>> chance) {
        super(character);
        this.chance = chance;
    }

    public MoveChanceBehavior(SnawCharacter character, int chance) {
        super(character);
        this.chance = () -> {
            // TODO - do this less stupid.
            return NumberStateValue.parse("NOT INITIALIZED", "" + chance);
        };
    }

    public MoveChanceBehavior(SnawCharacter character, String pointerToStage) {
        super(character);
        this.chance = new Supplier<Condition<NumberStateValue>>() {
            @Override
            public Condition<NumberStateValue> get() {
                return character.getCurrentState().getNumberStateValueValue(pointerToStage);
            }
        };
    }

    @Override
    public boolean actAndFilter() {
        // TODO - should treat doubles as doubles.
        int test = (int) (100d * GameState.randomChance());
        if (test > chance.get().getConditionValue().getInt()) {
            character.getTransientState().setValue("moving", false);
        }
        return true;
    }
}
