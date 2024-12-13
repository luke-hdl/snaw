package com.snaw.characters.characterBehavior;

import com.snaw.characters.SnawCharacter;
import com.snaw.coreState.GameState;
import com.snaw.state.stateValues.NumberStateValue;

import java.util.function.BiPredicate;


public class StateCheckerChainedBehavior extends ChainedCharacterBehavior {

    String idType;
    String id;
    String key;

    String stringValue;
    NumberStateValue numberValue;
    Boolean boolValue;

    BiPredicate<String, String> stringComparator;
    BiPredicate<NumberStateValue, NumberStateValue> numberComparator;
    BiPredicate<Boolean, Boolean> booleanComparator;

    public StateCheckerChainedBehavior(SnawCharacter character, CharacterBehavior childBehavior, String key, String value, String idType, String id) {
        super(character, childBehavior);
        this.key = key;
        this.stringValue = value;
        this.idType = idType;
        this.stringComparator = (s, s2) -> {
            if (s == null) {
                return s2 == null;
            }
            return s.equals(s2);
        };
        this.id = id;
    }

    public StateCheckerChainedBehavior(SnawCharacter character, CharacterBehavior childBehavior, String key, NumberStateValue value, String idType, String id) {
        super(character, childBehavior);
        this.key = key;
        this.numberValue = value;
        this.idType = idType;
        this.numberComparator = (i, i2) -> {
            if (i == null) {
                return i2 == null;
            }
            return i == i2;
        };
        this.id = id;
    }

    public StateCheckerChainedBehavior(SnawCharacter character, CharacterBehavior childBehavior, String key, boolean value, String idType, String id) {
        super(character, childBehavior);
        this.key = key;
        this.boolValue = value;
        this.idType = idType;
        this.booleanComparator = (b, b2) -> {
            if (b == null) {
                return b2 == null;
            }
            return b == b2;
        };
        this.id = id;
    }

    public StateCheckerChainedBehavior(SnawCharacter character, CharacterBehavior childBehavior, String key, String value, BiPredicate<String, String> comparator, String idType, String id) {
        super(character, childBehavior);
        this.key = key;
        this.stringValue = value;
        this.idType = idType;
        this.stringComparator = comparator;
        this.id = id;
    }

    public StateCheckerChainedBehavior(SnawCharacter character, CharacterBehavior childBehavior, String key, NumberStateValue value, BiPredicate<NumberStateValue, NumberStateValue> comparator, String idType, String id) {
        super(character, childBehavior);
        this.key = key;
        this.numberValue = value;
        this.idType = idType;
        this.numberComparator = comparator;
        this.id = id;
    }

    public StateCheckerChainedBehavior(SnawCharacter character, CharacterBehavior childBehavior, String key, boolean value, BiPredicate<Boolean, Boolean> comparator, String idType, String id) {
        super(character, childBehavior);
        this.key = key;
        this.boolValue = value;
        this.idType = idType;
        this.booleanComparator = comparator;
        this.id = id;
    }

    @Override
    public boolean shouldChain() {
        if (stringValue != null) {
            String value = GameState.getMap().getStringChildState(idType, id, key, true, false).getConditionValue();
            return stringComparator.test(stringValue, value);
        } else if (numberValue != null) {
            NumberStateValue value = GameState.getMap().getNumericChildState(idType, id, key, true, false).getConditionValue();
            return numberComparator.test(numberValue, value);
        } else {
            boolean value = GameState.getMap().getBoolChildState(idType, id, key, true, false).getConditionValue();
            return booleanComparator.test(boolValue, value);
        }
    }
}
