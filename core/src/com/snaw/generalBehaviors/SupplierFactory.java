package com.snaw.generalBehaviors;

import com.snaw.coreState.GameState;
import com.snaw.state.Condition;
import com.snaw.state.StateCheckingUtils;
import com.snaw.state.stateValues.NumberStateValue;

import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.regex.Pattern;

public class SupplierFactory {
    public enum SupportedType {
        NUMBER,
        STRING,
        BOOLEAN
    }

    protected static final Pattern getSoleArgument = Pattern.compile(".*\\{(.*)\\}");

    //This creates a supplier containing a mix of functions containing more suppliers.
    //Running it will fully resolve.

    public static Supplier getSupplierForOperatingSupplierBehavior(String value, SupportedType type, boolean setting) {
        return getOperateAnnotationsInSubsection(value, type, setting);
    }

    public static Supplier getSupplierForComparingSupplierBehavior(String value, SupportedType type) {
        return getComparingAnnotationsInSubsection(value, type);
    }

    protected static Supplier getComparingAnnotationsInSubsection(String argument, SupportedType type) {
        //Unlike setting, you can change type in comparing (generally something like 3>2|2>1).
        //This will. currently break if you do that!
        //I'd also like to include the ability to check an operating option in a non-setting way:
        //e.g. 2+2<3+1
        //This should work pretty easily for most operators, but I need to consider how to handle operators that could be either
        //e.g. = ...
        //can always force the use of letternames. but I don't recommend that.
        // TODO - fix that. anything that has a child should always be a bool, since compare annotations are T -> Bool.
        String annotationCode = null;
        for (String possibleCode : CompareAnnotation.getAllCompareAnnotationStringValuesBackwards()) {
            if (argument.contains(possibleCode)) {
                annotationCode = possibleCode;
                break;
            }
        }

        if (annotationCode == null) {
            //All done!
            //An empty argument is considered TRUE for bools.
            if (argument.isEmpty() && type == SupportedType.BOOLEAN) {
                return new Supplier<Condition<Boolean>>() {
                    @Override
                    public Condition<Boolean> get() {
                        return new Condition<>("", true);
                    }
                };
            }
            Supplier supplierForCode = getSingleArgumentValue(argument, type);
            return supplierForCode;
        } else {
            String[] subArguments = argument.split(Pattern.quote(annotationCode), 2);
            Supplier supplierOne = getComparingAnnotationsInSubsection(subArguments[0], type);
            Supplier supplierTwo;
            if (subArguments.length == 1 && type == SupportedType.BOOLEAN) {
                supplierTwo = (Supplier<Condition<Boolean>>) () -> new Condition<>("", true);
            } else {
                supplierTwo = getComparingAnnotationsInSubsection(subArguments[1], type);
            }
            CompareAnnotation ca = CompareAnnotation.lookup(annotationCode);
            switch (type) {
                case STRING:
                    return ca.getSettingComparingStringConvertingFunction(supplierOne, supplierTwo);
                case BOOLEAN:
                    return ca.getSettingComparingBooleanConvertingFunction(supplierOne, supplierTwo);
                default:
                    return ca.getSettingComparingNumericConvertingFunction(supplierOne, supplierTwo);
            }
        }
    }

    protected static Supplier getOperateAnnotationsInSubsection(String argument, SupportedType type, boolean setting) {
        String annotationCode = null;
        for (String possibleCode : OperateAnnotation.getAllCompareAnnotationStringValues()) {
            if (argument.contains(possibleCode)) {
                annotationCode = possibleCode;
                break;
            }
        }

        if (annotationCode == null) {
            //all done!
            Supplier supplierForCode = getSingleArgumentValue(argument, type);
            return supplierForCode;
        } else {
            String[] subArguments = argument.split(Pattern.quote(annotationCode), 2);
            Supplier supplierOne = getOperateAnnotationsInSubsection(subArguments[0], type, setting);
            Supplier supplierTwo = getOperateAnnotationsInSubsection(subArguments[1], type, setting);
            OperateAnnotation op = OperateAnnotation.lookup(annotationCode);
            switch (type) {
                case NUMBER:
                    if (setting) {
                        return op.getSettingOperatingNumericFunction(supplierOne, supplierTwo);
                    } else {
                        return op.getNonSettingOperatingNumericFunction(supplierOne, supplierTwo);
                    }

                case STRING:
                    if (setting) {
                        return op.getSettingOperatingStringFunction(supplierOne, supplierTwo);
                    } else {
                        return op.getNonSettingOperatingStringFunction(supplierOne, supplierTwo);
                    }

                default:
                    if (setting) {
                        return op.getSettingOperatingBooleanFunction(supplierOne, supplierTwo);
                    } else {
                        return op.getNonSettingOperatingStringFunction(supplierOne, supplierTwo);
                    }
            }
        }
    }

    private static BiFunction getNonsettingFunctionsForOperator(OperateAnnotation annotation, SupportedType type) {
        switch (type) {
            case NUMBER:
                // TODO implement
            case STRING:
                // TODO implement
            case BOOLEAN:
                // TODO implement
            default:
                return null;
        }
    }

    protected static Supplier getSingleArgumentValue(String argument, SupportedType type) {
        switch (type) {
            case NUMBER:
                return getSingleArgumentValueNumber(argument);
            case STRING:
                return getSingleArgumentValueString(argument);
            case BOOLEAN:
                return getSingleArgumentValueBool(argument);
        }
        return null;
    }

    protected static Supplier<Condition<Boolean>> getSingleArgumentValueBool(String argument) {
        if (argument.contains("(") && argument.contains(")")) {
            // TODO - clean this up.
            return () -> GameState.hasBeenInitialized() ? StateCheckingUtils.calculateBooleanStateCheck(argument.substring(1, argument.length() - 1), false, true) : new Condition<>("NOT INITIALIZED", "true".equals(argument));
        }

        return () -> new Condition<>("NOT INITIALIZED", "true".equals(argument));
    }

    protected static Supplier<Condition<String>> getSingleArgumentValueString(String argument) {
        if (argument.contains("(") && argument.contains(")")) {
            // TODO - clean this up.
            return () -> GameState.hasBeenInitialized() ? StateCheckingUtils.calculateStringStateCheck(argument.substring(1, argument.length() - 1), false, true) : new Condition<>("NOT INITIALIZED", "");
        }

        return () -> new Condition<>("NOT INITIALIZED", argument);
    }

    protected static Supplier<Condition<NumberStateValue>> getSingleArgumentValueNumber(String argument) {
        if (argument.contains("(") && argument.contains(")")) {
            return () -> GameState.hasBeenInitialized() ? StateCheckingUtils.calculateNumericStateCheck(argument.substring(1, argument.length() - 1), false, true) : NumberStateValue.parse("NOT INITIALIZED", "0");
        }

        return (() -> NumberStateValue.parse("NOT INITIALIZED", argument));
    }

}
