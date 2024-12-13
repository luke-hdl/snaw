package com.snaw.generalBehaviors;

import com.snaw.generalBehaviors.functional.SuppliedConvertingFunction;
import com.snaw.generalBehaviors.functional.SuppliedFunction;
import com.snaw.state.Condition;
import com.snaw.state.stateValues.NumberStateValue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Supplier;

public enum CompareAnnotation {
    //Compare Annotation's order DOES MATTER.
    //If a comparer has a string that fully includes another (e.g. ">=" contains "=")
    //It must be AFTER that one.
    //This makes checking these easier.
    EQUAL(Arrays.asList("=", "==", "===", " EQUALS ")),
    GREATER(Arrays.asList(">", " GREATER THAN ")),
    LESSER(Arrays.asList("<", " LESS THAN ")),
    GREATER_EQUAL(Arrays.asList(">=", ">==", ">===", " GREATER OR EQUAL THAN ")),
    LESSER_EQUAL(Arrays.asList("<=", "<==", "<===", " GREATER OR EQUAL THAN ")),
    NOT_EQUAL(Arrays.asList("!=", "!==", "!===", " NOT EQUALS "));

    private static final Map<String, CompareAnnotation> reverseLookup;

    private static final List<String> reverseLookupOrderForLastPick;
    private static final List<String> reverseLookupOrderForEarlyExit;

    static {
        reverseLookupOrderForLastPick = new LinkedList<>();
        reverseLookupOrderForEarlyExit = new LinkedList<>();
        reverseLookup = new HashMap<>();
        for (int i = 0; i < values().length; i++) {
            CompareAnnotation value = values()[i];
            for (String stringValue : value.stringValues) {
                reverseLookupOrderForLastPick.add(stringValue);
                reverseLookupOrderForEarlyExit.add(0, stringValue);
                reverseLookup.put(stringValue, value);
            }
        }
    }
    private final List<String> stringValues;

    CompareAnnotation(List<String> stringValues) {
        this.stringValues = Collections.unmodifiableList(stringValues);
    }

    public static CompareAnnotation lookup(String string) {
        return reverseLookup.get(string.replace(" ", ""));
    }

    public static List<String> getAllCompareAnnotationStringValuesForwards() {
        return reverseLookupOrderForLastPick;
    }

    public static List<String> getAllCompareAnnotationStringValuesBackwards() {
        List<String> test = reverseLookupOrderForEarlyExit;
        return reverseLookupOrderForEarlyExit;
    }

    protected BiPredicate<Condition<String>, Condition<String>> getPredicateForStrings() {
        switch (this) {
            case EQUAL:
                return (s, s2) -> s.getConditionValue().equals(s2.getConditionValue());
            case NOT_EQUAL:
                return (s, s2) -> !s.getConditionValue().equals(s2.getConditionValue());
            default:
                return null;
        }
    }

    protected BiPredicate<Condition<Boolean>, Condition<Boolean>> getPredicateForBooleans() {
        switch (this) {
            case EQUAL:
                return (s, s2) -> s.getConditionValue().equals(s2.getConditionValue());
            case NOT_EQUAL:
                return (s, s2) -> !s.getConditionValue().equals(s2.getConditionValue());
            default:
                return null;
        }
    }


    public BiPredicate<Condition<NumberStateValue>, Condition<NumberStateValue>> getPredicateForNumbers() {
        switch (this) {
            case LESSER:
                return (e1, e2) -> e1.getConditionValue().isLessThan(e2.getConditionValue());
            case LESSER_EQUAL:
                return (e1, e2) -> e1.getConditionValue().isLessThanOrEqual(e2.getConditionValue());
            case GREATER:
                return (e1, e2) -> e1.getConditionValue().isGreaterThan(e2.getConditionValue());
            case GREATER_EQUAL:
                return (e1, e2) -> e1.getConditionValue().isGreaterThanOrEqual(e2.getConditionValue());
            case EQUAL:
                return (e1, e2) -> e1.getConditionValue().isEqual(e2.getConditionValue());
            case NOT_EQUAL:
                return (e1, e2) -> e1.getConditionValue().isNotEqual(e2.getConditionValue());
            default:
                return null;
        }
    }

    // TODO - this method name sucks.
    public SuppliedConvertingFunction<Condition<NumberStateValue>, Condition<Boolean>> getSettingComparingNumericConvertingFunction(Supplier<Condition<NumberStateValue>> conditionSupplier, Supplier<Condition<NumberStateValue>> conditionSupplier2) {
        switch (this) {
            case LESSER:
                return new SuppliedConvertingFunction<Condition<NumberStateValue>, Condition<Boolean>>(conditionSupplier, conditionSupplier2) {
                    @Override
                    public Condition<Boolean> get() {
                        return new Condition<>("", this.firstValue.get().getConditionValue().isLessThan(this.secondValue.get().getConditionValue()));
                    }
                };
            case LESSER_EQUAL:
                return new SuppliedConvertingFunction<Condition<NumberStateValue>, Condition<Boolean>>(conditionSupplier, conditionSupplier2) {
                    @Override
                    public Condition<Boolean> get() {
                        return new Condition<>("", this.firstValue.get().getConditionValue().isLessThanOrEqual(this.secondValue.get().getConditionValue()));
                    }
                };
            case GREATER:
                return new SuppliedConvertingFunction<Condition<NumberStateValue>, Condition<Boolean>>(conditionSupplier, conditionSupplier2) {
                    @Override
                    public Condition<Boolean> get() {
                        return new Condition<>("", this.firstValue.get().getConditionValue().isGreaterThan(this.secondValue.get().getConditionValue()));
                    }
                };
            case GREATER_EQUAL:
                return new SuppliedConvertingFunction<Condition<NumberStateValue>, Condition<Boolean>>(conditionSupplier, conditionSupplier2) {
                    @Override
                    public Condition<Boolean> get() {
                        return new Condition<>("", this.firstValue.get().getConditionValue().isGreaterThanOrEqual(this.secondValue.get().getConditionValue()));
                    }
                };
            case EQUAL:
                return new SuppliedConvertingFunction<Condition<NumberStateValue>, Condition<Boolean>>(conditionSupplier, conditionSupplier2) {
                    @Override
                    public Condition<Boolean> get() {
                        return new Condition<>("", this.firstValue.get().getConditionValue().isEqual(this.secondValue.get().getConditionValue()));
                    }
                };
            case NOT_EQUAL:
                return new SuppliedConvertingFunction<Condition<NumberStateValue>, Condition<Boolean>>(conditionSupplier, conditionSupplier2) {
                    @Override
                    public Condition<Boolean> get() {
                        return new Condition<>("", this.firstValue.get().getConditionValue().isNotEqual(this.secondValue.get().getConditionValue()));
                    }
                };
            default:
                return null;
        }
    }
    public SuppliedConvertingFunction<Condition<String>, Condition<Boolean>> getSettingComparingStringConvertingFunction(Supplier<Condition<String>> conditionSupplier, Supplier<Condition<String>> conditionSupplier2) {
        switch (this) {
            case EQUAL:
                return new SuppliedConvertingFunction<Condition<String>, Condition<Boolean>>(conditionSupplier, conditionSupplier2) {
                    @Override
                    public Condition<Boolean> get() {
                        return new Condition<>("", this.firstValue.get().getConditionValue().equals(this.secondValue.get().getConditionValue()));
                    }
                };
            case NOT_EQUAL:
                return new SuppliedConvertingFunction<Condition<String>, Condition<Boolean>>(conditionSupplier, conditionSupplier2) {
                    @Override
                    public Condition<Boolean> get() {
                        return new Condition<>("", !this.firstValue.get().getConditionValue().equals(this.secondValue.get().getConditionValue()));
                    }
                };
            default:
                return null;
        }
    }

    public SuppliedConvertingFunction<Condition<Boolean>, Condition<Boolean>> getSettingComparingBooleanConvertingFunction(Supplier<Condition<Boolean>> conditionSupplier, Supplier<Condition<Boolean>> conditionSupplier2) {
        switch (this) {
            case EQUAL:
                return new SuppliedConvertingFunction<Condition<Boolean>, Condition<Boolean>>(conditionSupplier, conditionSupplier2) {
                    @Override
                    public Condition<Boolean> get() {
                        return new Condition<>("", this.firstValue.get().getConditionValue().equals(this.secondValue.get().getConditionValue()));
                    }
                };
            case NOT_EQUAL:
                return new SuppliedConvertingFunction<Condition<Boolean>, Condition<Boolean>>(conditionSupplier, conditionSupplier2) {
                    @Override
                    public Condition<Boolean> get() {
                        return new Condition<>("", !this.firstValue.get().getConditionValue().equals(this.secondValue.get().getConditionValue()));
                    }
                };
            default:
                return null;
        }
    }



}
