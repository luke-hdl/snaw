package com.snaw.generalBehaviors;

import com.snaw.coreState.GameState;
import com.snaw.generalBehaviors.functional.SuppliedConvertingFunction;
import com.snaw.generalBehaviors.genericBehaviors.GenericBehavior;
import com.snaw.generalBehaviors.genericBehaviors.KeyPressedChainedBehavior;
import com.snaw.generalBehaviors.genericBehaviors.PerTimeChainedBehavior;
import com.snaw.generalBehaviors.genericBehaviors.SupplierBehavior;
import com.snaw.state.Condition;
import com.snaw.state.StateCheckingUtils;
import com.snaw.state.stateValues.NumberStateValue;
import com.snaw.textureBank.behaviors.DoNothingBehavior;

import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SharedBehaviorFactoryHelpers {
    protected static final Pattern getSoleArgument = Pattern.compile(".*\\{(.*)\\}");

    protected static GenericBehavior getChainedBehaviorFromTag(String tagValue, GenericBehavior childBehavior) {
        String cleanedTagValue = tagValue;
        if (tagValue.contains("{")) {
            cleanedTagValue = tagValue.substring(0, tagValue.indexOf('{'));
        }

        switch (cleanedTagValue) {
            case "WHILE KEY PRESSED":
                return new KeyPressedChainedBehavior(childBehavior, getRawSingleArgumentValue(tagValue), true);
            case "WHEN KEY PRESSED":
                return new KeyPressedChainedBehavior(childBehavior, getRawSingleArgumentValue(tagValue), false);
            case "PER TIME":
                return new PerTimeChainedBehavior(childBehavior, getSingleArgumentValueNumber(tagValue));
            case "CHECK INT STATE":
                return getSupplierBasedStateChecker(getRawSingleArgumentValue(tagValue), SupplierFactory.SupportedType.NUMBER, childBehavior);
            case "CHECK STATE":
                return getSupplierBasedStateChecker(getRawSingleArgumentValue(tagValue), cleanedTagValue.contains("=") ? SupplierFactory.SupportedType.STRING : SupplierFactory.SupportedType.BOOLEAN, childBehavior);
            default:
                return null;
        }
    }

    protected static GenericBehavior getSupplierBasedStateChecker(String value, SupplierFactory.SupportedType type, GenericBehavior chainedBehavior) {
        return getSupplierBasedStateChecker(value, type, chainedBehavior, false);
    }

    protected static GenericBehavior getSupplierBasedStateSetter(String value, SupplierFactory.SupportedType type, GenericBehavior chainedBehavior, boolean setting) {
        return new SupplierBehavior(chainedBehavior != null ? chainedBehavior : new DoNothingBehavior(), SupplierFactory.getSupplierForOperatingSupplierBehavior(getRawSingleArgumentValue(value), type, setting));
    }


    protected static GenericBehavior getSupplierBasedStateChecker(String value, SupplierFactory.SupportedType type, GenericBehavior chainedBehavior, boolean invertResult) {
        if (type == SupplierFactory.SupportedType.NUMBER) {
            SuppliedConvertingFunction<Condition<NumberStateValue>, Condition<Boolean>> supplier = (SuppliedConvertingFunction<Condition<NumberStateValue>, Condition<Boolean>>) SupplierFactory.getSupplierForComparingSupplierBehavior(value, type);
            SupplierBehavior supplierBehavior = new SupplierBehavior(chainedBehavior, () -> new Condition<>("", invertResult ? supplier.get().getConditionValue() : !supplier.get().getConditionValue()));
            return supplierBehavior;
        } else if (type == SupplierFactory.SupportedType.BOOLEAN) {
            Supplier<Condition<Boolean>> supplier = (Supplier<Condition<Boolean>>) SupplierFactory.getSupplierForComparingSupplierBehavior(value, type);
            SupplierBehavior supplierBehavior = new SupplierBehavior(chainedBehavior, () -> new Condition<>("", invertResult ? supplier.get().getConditionValue() : !supplier.get().getConditionValue()));
            return supplierBehavior;
        } else {
            SuppliedConvertingFunction<Condition<String>, Condition<Boolean>> supplier = (SuppliedConvertingFunction<Condition<String>, Condition<Boolean>>) SupplierFactory.getSupplierForComparingSupplierBehavior(value, type);
            SupplierBehavior supplierBehavior = new SupplierBehavior(chainedBehavior, () -> new Condition<>("", invertResult ? supplier.get().getConditionValue() : !supplier.get().getConditionValue()));
            return supplierBehavior;
        }
    }

    protected static String getRawSingleArgumentValue(String value) {
        Matcher matcher = getSoleArgument.matcher(value);
        return matcher.find() ? matcher.group(1) : "";
    }

    protected static Supplier<Condition<String>> getSingleArgumentValue(String value) {
        Matcher matcher = getSoleArgument.matcher(value);
        String argument = matcher.find() ? matcher.group(1) : "";
        if (argument.contains("(") && argument.contains(")")) {
            // TODO - clean this up.
            return () -> GameState.hasBeenInitialized() ? StateCheckingUtils.calculateStringStateCheck(argument.substring(1, argument.length() - 1), false, true) : new Condition<>("NOT INITIALIZED", argument);
        }

        return () -> new Condition<>("NOT INITIALIZED", argument);
    }

    protected static Supplier<Condition<NumberStateValue>> getSingleArgumentValueNumber(String value) {
        Matcher matcher = getSoleArgument.matcher(value);
        String argument = matcher.find() ? matcher.group(1) : "";
        if (argument.contains("(") && argument.contains(")")) {
            return () -> GameState.hasBeenInitialized() ? StateCheckingUtils.calculateNumericStateCheck(argument.substring(1, argument.length() - 1), false, true) : NumberStateValue.parse("NOT INITIALIZED", "0");
        }

        return (() -> NumberStateValue.parse("NOT INITIALIZED", argument));
    }
}
