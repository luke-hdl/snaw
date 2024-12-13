package com.snaw.levels;

import com.snaw.generalBehaviors.SharedBehaviorFactoryHelpers;
import com.snaw.generalBehaviors.SupplierFactory;
import com.snaw.generalBehaviors.genericBehaviors.GenericBehavior;
import com.snaw.generalBehaviors.genericBehaviors.SupplierBehavior;
import com.snaw.levels.endings.LevelEndLose;
import com.snaw.levels.endings.LevelEndWin;
import com.snaw.levels.levelBehavior.BeingAttackedChainedBehavior;
import com.snaw.levels.levelBehavior.LevelEndsImmediatelyBehavior;
import com.snaw.levels.levelBehavior.RunPerTimeChainedBehavior;
import com.snaw.levels.levelBehavior.SetRoomBehavior;
import com.snaw.levels.levelBehavior.StateIncrementerBehavior;
import com.snaw.state.Condition;
import com.snaw.textureBank.behaviors.DoNothingBehavior;

import java.util.function.Supplier;

public class LevelBehaviorFactory extends SharedBehaviorFactoryHelpers {

    private static GenericBehavior getChainedLevelBehaviorFromTag(Level level, String tagValue, GenericBehavior childBehavior) {
        String cleanedTagValue = tagValue;
        Supplier<String> arguments = null;
        if (tagValue.contains("{")) {
            cleanedTagValue = tagValue.substring(0, tagValue.indexOf('{'));
        }
        String[] argumentsSplit;
        switch (cleanedTagValue) {
            case "CHECK STATE":
            case "CHECK INT STATE":
                return getChainedBehaviorFromTag(tagValue, childBehavior);
            case "ANY CHARACTERS ATTACKING":
                return new BeingAttackedChainedBehavior(level, childBehavior);
            case "PER TIME":
                // TODO - add support for provided checkers.
                return new RunPerTimeChainedBehavior(level, childBehavior, Integer.parseInt(getSingleArgumentValue(tagValue).get().getConditionValue()));
            default:
                return null;
        }
    }

    public static GenericBehavior getLevelBehaviorFromTag(Level level, String tagValue) {
        if (tagValue.contains("->")) {
            String[] chainedValues = tagValue.split("->");
            GenericBehavior currentBehavior = getLevelBehaviorFromTag(level, chainedValues[chainedValues.length - 1]);
            for (int i = chainedValues.length - 2; i >= 0; i--) {
                currentBehavior = getChainedLevelBehaviorFromTag(level, chainedValues[i], currentBehavior);
            }
            return currentBehavior;
        }
        String cleanedTagValue = tagValue;
        Supplier<Condition<String>> arguments = null;
        if (tagValue.contains("{")) {
            cleanedTagValue = tagValue.substring(0, tagValue.indexOf('{'));
            arguments = getSingleArgumentValue(tagValue);
        }
        String[] argumentsSplit = getSingleArgumentValue(tagValue).get().getConditionValue().split(":");
        switch (cleanedTagValue) {
            case "IMMEDIATELY LOSE":
                return new LevelEndsImmediatelyBehavior(level, new LevelEndLose());
            case "IMMEDIATELY WIN":
                return new LevelEndsImmediatelyBehavior(level, new LevelEndWin());
            case "SET ROOM":
                // todo cleanup
                Supplier<Condition<String>> finalArguments = arguments;
                return new SetRoomBehavior(level, () -> finalArguments.get());
            case "SET STATE":
                return getSupplierBasedStateSetter(tagValue, SupplierFactory.SupportedType.STRING, null, true);
            case "SET INT STATE":
            case "SUBTRACT FROM STATE":
            case "ADD TO STATE":
            case "CALC AND SET":
                return getSupplierBasedStateSetter(tagValue, SupplierFactory.SupportedType.NUMBER, null, true);
            case "SET BOOL STATE":
                return getSupplierBasedStateSetter(tagValue, SupplierFactory.SupportedType.BOOLEAN, null, true);
            //return new StateIncrementerBehavior(level, argumentsSplit[2].split("=")[0], -1 * Integer.parseInt(argumentsSplit[2].split("=")[1]), argumentsSplit[0], argumentsSplit[1]);
            default:
                return null;
        }
    }

}
