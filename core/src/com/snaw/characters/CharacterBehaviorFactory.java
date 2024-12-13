package com.snaw.characters;

import com.snaw.buttons.behaviors.ThrowExceptionBehavior;
import com.snaw.characters.characterBehavior.AttacksPlayerInBehavior;
import com.snaw.characters.characterBehavior.DebugBehavior;
import com.snaw.characters.characterBehavior.DebugMovementKeysBehavior;
import com.snaw.characters.characterBehavior.EndsTurnBehavior;
import com.snaw.characters.characterBehavior.MoveBlockedByRoomState;
import com.snaw.characters.characterBehavior.MoveChanceBehavior;
import com.snaw.characters.characterBehavior.MoveIfBlockedBehavior;
import com.snaw.characters.characterBehavior.MovesInALineBehavior;
import com.snaw.characters.characterBehavior.NothingHappensBehavior;
import com.snaw.characters.characterBehavior.TriesToMoveBehavior;
import com.snaw.characters.characterBehavior.UnblockedMoveSucceedsBehavior;
import com.snaw.exceptions.CrashGameException;
import com.snaw.exceptions.DebugException;
import com.snaw.generalBehaviors.SharedBehaviorFactoryHelpers;
import com.snaw.generalBehaviors.SupplierFactory;
import com.snaw.generalBehaviors.genericBehaviors.GenericBehavior;

import java.util.Arrays;
import java.util.function.Supplier;

public class CharacterBehaviorFactory extends SharedBehaviorFactoryHelpers {

    public static GenericBehavior getCharacterBehaviorFromTag(SnawCharacter character, String tagValue) {
        if (tagValue.contains("->")) {
            String[] chainedValues = tagValue.split("->");
            GenericBehavior currentBehavior = getCharacterBehaviorFromTag(character, chainedValues[chainedValues.length - 1]);
            for (int i = chainedValues.length - 2; i >= 0; i--) {
                currentBehavior = getChainedBehaviorFromTag(chainedValues[i], currentBehavior);
            }
            return currentBehavior;
        }
        Supplier<String> arguments;
        String cleanedTagValue = tagValue;
        if (tagValue.contains("{")) {
            cleanedTagValue = tagValue.substring(0, tagValue.indexOf('{'));
        }
        switch (cleanedTagValue) {
            case "MOVEMENT DEBUG":
                return new DebugMovementKeysBehavior(character);
            case "SET STATE":
                return getSupplierBasedStateSetter(tagValue, SupplierFactory.SupportedType.STRING, null, true);
            case "SET INT STATE":
            case "SUBTRACT FROM STATE":
            case "ADD TO STATE":
            case "CALC AND SET":
                return getSupplierBasedStateSetter(tagValue, SupplierFactory.SupportedType.NUMBER, null, true);
            case "SET BOOL STATE":
                return getSupplierBasedStateSetter(tagValue, SupplierFactory.SupportedType.BOOLEAN, null, true);
            case "END TURN IF":
                return new EndsTurnBehavior(character, getSingleArgumentValue(tagValue));
            case "CRASH GAME":
                return new ThrowExceptionBehavior(new CrashGameException());
            case "DEBUG EXCEPTION":
                return new ThrowExceptionBehavior(new DebugException("Called by: " + character.getId()));
            case "DEBUG":
                return new DebugBehavior(character);
            case "TRIES TO MOVE EVERY":
                return new TriesToMoveBehavior(character, getSingleArgumentValueNumber(tagValue));
            case "MOVE CHANCE":
                return new MoveChanceBehavior(character, getSingleArgumentValueNumber(tagValue));
            case "MOVES IN A LINE":
                // No plans to support sub-argument logic, since it is part of the simplified suite.
                return new MovesInALineBehavior(character, getSingleArgumentValue(tagValue).get().getConditionValue().split(","));
            case "MOVE BLOCKED BY ROOM STATE":
                // No plans to support sub-argument logic, since it is part of the simplified suite.
                // Use CHECK STATE -> BLOCK MOVE instead.
                return new MoveBlockedByRoomState(character, getSingleArgumentValue(tagValue).get().getConditionValue());
            case "MOVE SUCCEEDS IF NOT BLOCKED":
                return new UnblockedMoveSucceedsBehavior(character);
            case "RETURNS TO ROOM WHEN BLOCKED":
                return new MoveIfBlockedBehavior(character, getSingleArgumentValue(tagValue).get().getConditionValue());
            case "ATTACKS PLAYER WHEN IN":
                // No plans to support sub-argument logic, since it is part of the simplified suite.
                // USE CHECK STATE -> ATTACK PLAYER instead.
                return new AttacksPlayerInBehavior(character, Arrays.asList(getSingleArgumentValue(tagValue).get().getConditionValue().split(",")));
            case "NOTHING HAPPENS":
                return new NothingHappensBehavior(character);
            default:
                return null;
        }
    }
}
