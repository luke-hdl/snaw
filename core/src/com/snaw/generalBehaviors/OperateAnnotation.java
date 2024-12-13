package com.snaw.generalBehaviors;

import com.snaw.generalBehaviors.functional.SuppliedConvertingFunction;
import com.snaw.generalBehaviors.functional.SuppliedFunction;
import com.snaw.state.Condition;
import com.snaw.state.stateValues.NumberStateValue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public enum OperateAnnotation {
    //CURRENTLY SUPPORTED
    //Multi-functional
    ASSIGN(Arrays.asList("=")), //assignment operator, equivalent to a=b for all typies.

    //Numerics only
    PLUS(Arrays.asList("+", " PLUS ", " ADD ")), //adds ints, that's it
    INCREMENT(Arrays.asList("++")), //or succession. adds 1.
    MINUS(Arrays.asList("-", " MINUS ", " SUBTRACT ")), //subtracts ints, that's it
    MULTIPLY(Arrays.asList("*", " TIMES ", " MULTIPLY ")), //multiples ints, that's it
    DIVIDE(Arrays.asList(" DIVIDE ", " DIVIDED BY ")), //divides ints, that's it. no support for / since we use it a lot.
    EXPONENTIATE(Arrays.asList("^", " TO THE POWER OF ", " POWER ")), //exponentiates ints, that's it
    MODULO(Arrays.asList("%", " MOD ")), //remainder on ints, that's it
    FLOOR(Arrays.asList(" FLOOR ")),
    CEILING(Arrays.asList(" ROOT ")),
    LOWER_LIMIT(Arrays.asList(" LOWER LIMIT ")), //sets A to B if A is lower than B. Intended for difficulty levels that progress to a certain points, etc.
    UPPER_LIMIT(Arrays.asList(" UPPER LIMIT ")), //sets A to B if A is higher than B. As above.

    //Bools only
    NOT(Arrays.asList(" NOT ", "!")), //logical inversion.
    AND(Arrays.asList(" AND ", "&", "&&")), //logical AND; boolean only. no bitwise and for you!
    OR(Arrays.asList(" OR ", "|", "||")), //logical OR; boolean only
    IMPLIES(Arrays.asList(" IMPLIES ")),
    //no -> option because that's used for chaining.
    ; //logical implication. you. probably don't need this. A IMPLIES B is equivalent to (NOT A) OR (A AND B).

    //Strings only
    //None. :)

    //NOTES ON FUTURE SUPPORT
    //If the text-window update ever happens, there will potentally be operators to help with text manipulation.
    //

    private static final Map<String, OperateAnnotation> reverseLookup;

    static {
        reverseLookup = new HashMap<>();
        for (int i = 0; i < values().length; i++) {
            OperateAnnotation value = values()[i];
            for (String stringValue : value.stringValues) {
                reverseLookup.put(stringValue, value);
            }
        }
    }

    private final List<String> stringValues;

    OperateAnnotation(List<String> stringValues) {
        this.stringValues = Collections.unmodifiableList(stringValues);
    }

    public static OperateAnnotation lookup(String string) {
        return reverseLookup.get(string);
    }

    public static Set<String> getAllCompareAnnotationStringValues() {
        return reverseLookup.keySet();
    }

    public SuppliedConvertingFunction<Condition<Boolean>, Condition<Boolean>> getNonSettingOperatingBooleanFunction(Supplier<Condition<Boolean>> conditionSupplier, Supplier<Condition<Boolean>> conditionSupplier2) {
        switch (this) {
            case NOT:
                return new SuppliedFunction<Condition<Boolean>>(conditionSupplier, conditionSupplier2) {
                    @Override
                    public Condition<Boolean> get() {
                        Boolean value = this.firstValue.get().getConditionValue();
                        return new Condition<>("", !value);
                    }
                };
            case AND:
                return new SuppliedFunction<Condition<Boolean>>(conditionSupplier, conditionSupplier2) {
                    @Override
                    public Condition<Boolean> get() {
                        Boolean value = this.firstValue.get().getConditionValue() && this.secondValue.get().getConditionValue();
                        return new Condition<>("", value);
                    }
                };
            case OR:
                return new SuppliedFunction<Condition<Boolean>>(conditionSupplier, conditionSupplier2) {
                    @Override
                    public Condition<Boolean> get() {
                        Boolean value = this.firstValue.get().getConditionValue() || this.secondValue.get().getConditionValue();
                        return new Condition<>("", value);
                    }
                };
            case IMPLIES:
                return new SuppliedFunction<Condition<Boolean>>(conditionSupplier, conditionSupplier2) {
                    @Override
                    public Condition<Boolean> get() {
                        Boolean value = this.firstValue.get().getConditionValue();
                        Boolean value2 = this.secondValue.get().getConditionValue();
                        return new Condition<>("", !value || (value && value2));
                    }
                };
            default:
                return null;
        }
    }

    public SuppliedFunction<Condition<Boolean>> getSettingOperatingBooleanFunction(Supplier<Condition<Boolean>> conditionSupplier, Supplier<Condition<Boolean>> conditionSupplier2) {
        switch (this) {
            case NOT:
                return new SuppliedFunction<Condition<Boolean>>(conditionSupplier, conditionSupplier2) {
                    @Override
                    public Condition<Boolean> get() {
                        this.firstValue.get().setConditionValue(!this.firstValue.get().getConditionValue());
                        return firstValue.get();
                    }
                };
            case AND:
                return new SuppliedFunction<Condition<Boolean>>(conditionSupplier, conditionSupplier2) {
                    @Override
                    public Condition<Boolean> get() {
                        Boolean value = this.firstValue.get().getConditionValue() && this.secondValue.get().getConditionValue();
                        this.firstValue.get().setConditionValue(value);
                        return firstValue.get();
                    }
                };
            case OR:
                return new SuppliedFunction<Condition<Boolean>>(conditionSupplier, conditionSupplier2) {
                    @Override
                    public Condition<Boolean> get() {
                        Boolean value = this.firstValue.get().getConditionValue() || this.secondValue.get().getConditionValue();
                        this.firstValue.get().setConditionValue(value);
                        return firstValue.get();
                    }
                };
            case IMPLIES:
                return new SuppliedFunction<Condition<Boolean>>(conditionSupplier, conditionSupplier2) {
                    @Override
                    public Condition<Boolean> get() {
                        Boolean value = this.firstValue.get().getConditionValue();
                        Boolean value2 = this.secondValue.get().getConditionValue();
                        this.firstValue.get().setConditionValue(!value || (value && value2));
                        return firstValue.get();
                    }
                };
            default:
                return null;
        }
    }

    //String and bool support will come. LOL.
    //Possibly they'll get the StateValue treatment too.
    public SuppliedFunction<Condition<String>> getNonSettingOperatingStringFunction(Supplier<Condition<String>> conditionSupplier, Supplier<Condition<String>> conditionSupplier2) {
        //Not supported.
        return null;
    }

    public SuppliedFunction<Condition<String>> getSettingOperatingStringFunction(Supplier<Condition<String>> conditionSupplier, Supplier<Condition<String>> conditionSupplier2) {
        switch (this) {
            case ASSIGN:
                return new SuppliedFunction<Condition<String>>(conditionSupplier, conditionSupplier2) {
                    @Override
                    public Condition<String> get() {
                        Condition<String> value = this.firstValue.get();
                        value.setConditionValue(this.secondValue.get().getConditionValue());
                        return value;
                    }
                };
            default:
                return null;
        }
    }

    public SuppliedFunction<Condition<NumberStateValue>> getNonSettingOperatingNumericFunction(Supplier<Condition<NumberStateValue>> conditionSupplier, Supplier<Condition<NumberStateValue>> conditionSupplier2) {
        //Returns a fresh new NumberStateValue in a temporary condition.
        switch (this) {
            case PLUS:
                return new SuppliedFunction<Condition<NumberStateValue>>(conditionSupplier, conditionSupplier2) {
                    @Override
                    public Condition<NumberStateValue> get() {
                        NumberStateValue temp = firstValue.get().getConditionValue().copy();
                        temp.add(this.secondValue.get().getConditionValue());
                        return new Condition<>("", temp);
                    }
                };
            case MINUS:
                return new SuppliedFunction<Condition<NumberStateValue>>(conditionSupplier, conditionSupplier2) {
                    @Override
                    public Condition<NumberStateValue> get() {
                        NumberStateValue temp = firstValue.get().getConditionValue().copy();
                        temp.subtract(this.secondValue.get().getConditionValue());
                        return new Condition<>("", temp);
                    }
                };
            case INCREMENT:
                return new SuppliedFunction<Condition<NumberStateValue>>(conditionSupplier, conditionSupplier2) {
                    @Override
                    public Condition<NumberStateValue> get() {
                        NumberStateValue temp = firstValue.get().getConditionValue().copy();
                        temp.increment();
                        return new Condition<>("", temp);
                    }
                };
            case MULTIPLY:
                return new SuppliedFunction<Condition<NumberStateValue>>(conditionSupplier, conditionSupplier2) {
                    @Override
                    public Condition<NumberStateValue> get() {
                        NumberStateValue temp = firstValue.get().getConditionValue().copy();
                        temp.multiply(this.secondValue.get().getConditionValue());
                        return new Condition<>("", temp);
                    }
                };
            case DIVIDE:
                return new SuppliedFunction<Condition<NumberStateValue>>(conditionSupplier, conditionSupplier2) {
                    @Override
                    public Condition<NumberStateValue> get() {
                        NumberStateValue temp = firstValue.get().getConditionValue().copy();
                        temp.divide(this.secondValue.get().getConditionValue());
                        return new Condition<>("", temp);
                    }
                };
            case EXPONENTIATE:
                return new SuppliedFunction<Condition<NumberStateValue>>(conditionSupplier, conditionSupplier2) {
                    @Override
                    public Condition<NumberStateValue> get() {
                        NumberStateValue temp = firstValue.get().getConditionValue().copy();
                        temp.exponentiate(this.secondValue.get().getConditionValue());
                        return new Condition<>("", temp);
                    }
                };
            case MODULO:
                return new SuppliedFunction<Condition<NumberStateValue>>(conditionSupplier, conditionSupplier2) {
                    @Override
                    public Condition<NumberStateValue> get() {
                        NumberStateValue temp = firstValue.get().getConditionValue().copy();
                        temp.mod(this.secondValue.get().getConditionValue());
                        return new Condition<>("", temp);
                    }
                };
            case FLOOR:
                return new SuppliedFunction<Condition<NumberStateValue>>(conditionSupplier, conditionSupplier2) {
                    @Override
                    public Condition<NumberStateValue> get() {
                        NumberStateValue temp = firstValue.get().getConditionValue().copy();
                        temp.floor();
                        return new Condition<>("", temp);
                    }
                };
            case CEILING:
                return new SuppliedFunction<Condition<NumberStateValue>>(conditionSupplier, conditionSupplier2) {
                    @Override
                    public Condition<NumberStateValue> get() {
                        NumberStateValue temp = firstValue.get().getConditionValue().copy();
                        temp.ceiling();
                        return new Condition<>("", temp);
                    }
                };
            case LOWER_LIMIT:
                return new SuppliedFunction<Condition<NumberStateValue>>(conditionSupplier, conditionSupplier2) {
                    @Override
                    public Condition<NumberStateValue> get() {
                        NumberStateValue temp = firstValue.get().getConditionValue().copy();
                        temp.lowerLimit(this.secondValue.get().getConditionValue());
                        return new Condition<>("", temp);
                    }
                };
            case UPPER_LIMIT:
                return new SuppliedFunction<Condition<NumberStateValue>>(conditionSupplier, conditionSupplier2) {
                    @Override
                    public Condition<NumberStateValue> get() {
                        NumberStateValue temp = firstValue.get().getConditionValue().copy();
                        temp.upperLimit(this.secondValue.get().getConditionValue());
                        return new Condition<>("", temp);
                    }
                };
            default:
                return null;
        }
    }

    public SuppliedFunction<Condition<NumberStateValue>> getSettingOperatingNumericFunction(Supplier<Condition<NumberStateValue>> conditionSupplier, Supplier<Condition<NumberStateValue>> conditionSupplier2) {
        switch (this) {
            case ASSIGN:
                return new SuppliedFunction<Condition<NumberStateValue>>(conditionSupplier, conditionSupplier2) {
                    @Override
                    public Condition<NumberStateValue> get() {
                        this.firstValue.get().getConditionValue().set(this.secondValue.get().getConditionValue());
                        return this.firstValue.get();
                    }
                };
            case PLUS:
                return new SuppliedFunction<Condition<NumberStateValue>>(conditionSupplier, conditionSupplier2) {
                    @Override
                    public Condition<NumberStateValue> get() {
                        this.firstValue.get().getConditionValue().add(this.secondValue.get().getConditionValue());
                        return this.firstValue.get();
                    }
                };
            case MINUS:
                return new SuppliedFunction<Condition<NumberStateValue>>(conditionSupplier, conditionSupplier2) {
                    @Override
                    public Condition<NumberStateValue> get() {
                        this.firstValue.get().getConditionValue().subtract(this.secondValue.get().getConditionValue());
                        return this.firstValue.get();
                    }
                };
            case INCREMENT:
                return new SuppliedFunction<Condition<NumberStateValue>>(conditionSupplier, conditionSupplier2) {
                    @Override
                    public Condition<NumberStateValue> get() {
                        this.firstValue.get().getConditionValue().increment();
                        return this.firstValue.get();
                    }
                };
            case MULTIPLY:
                return new SuppliedFunction<Condition<NumberStateValue>>(conditionSupplier, conditionSupplier2) {
                    @Override
                    public Condition<NumberStateValue> get() {
                        this.firstValue.get().getConditionValue().multiply(this.secondValue.get().getConditionValue());
                        return this.firstValue.get();
                    }
                };
            case DIVIDE:
                return new SuppliedFunction<Condition<NumberStateValue>>(conditionSupplier, conditionSupplier2) {
                    @Override
                    public Condition<NumberStateValue> get() {
                        this.firstValue.get().getConditionValue().divide(this.secondValue.get().getConditionValue());
                        return this.firstValue.get();
                    }
                };
            case EXPONENTIATE:
                return new SuppliedFunction<Condition<NumberStateValue>>(conditionSupplier, conditionSupplier2) {
                    @Override
                    public Condition<NumberStateValue> get() {
                        this.firstValue.get().getConditionValue().exponentiate(this.secondValue.get().getConditionValue());
                        return this.firstValue.get();
                    }
                };
            case MODULO:
                return new SuppliedFunction<Condition<NumberStateValue>>(conditionSupplier, conditionSupplier2) {
                    @Override
                    public Condition<NumberStateValue> get() {
                        this.firstValue.get().getConditionValue().mod(this.secondValue.get().getConditionValue());
                        return this.firstValue.get();
                    }
                };
            case FLOOR:
                return new SuppliedFunction<Condition<NumberStateValue>>(conditionSupplier, conditionSupplier2) {
                    @Override
                    public Condition<NumberStateValue> get() {
                        this.firstValue.get().getConditionValue().floor();
                        return this.firstValue.get();
                    }
                };
            case CEILING:
                return new SuppliedFunction<Condition<NumberStateValue>>(conditionSupplier, conditionSupplier2) {
                    @Override
                    public Condition<NumberStateValue> get() {
                        this.firstValue.get().getConditionValue().ceiling();
                        return this.firstValue.get();
                    }
                };
            case LOWER_LIMIT:
                return new SuppliedFunction<Condition<NumberStateValue>>(conditionSupplier, conditionSupplier2) {
                    @Override
                    public Condition<NumberStateValue> get() {
                        this.firstValue.get().getConditionValue().lowerLimit(this.secondValue.get().getConditionValue());
                        return this.firstValue.get();
                    }
                };
            case UPPER_LIMIT:
                return new SuppliedFunction<Condition<NumberStateValue>>(conditionSupplier, conditionSupplier2) {
                    @Override
                    public Condition<NumberStateValue> get() {
                        this.firstValue.get().getConditionValue().upperLimit(this.secondValue.get().getConditionValue());
                        return this.firstValue.get();
                    }
                };
            default:
                return null;
        }
    }

        /*

        public BiFunction<Supplier<Condition<NumberStateValue>>, Supplier<Condition<NumberStateValue>>, Supplier<Condition<NumberStateValue>>> getNonSettingOperatorAnnotationsForNumerics
        () {
            switch (this) {
                case PLUS:
                    return (numberStateValueSupplier, numberStateValueSupplier2) -> () -> {
                        NumberStateValue newValue = numberStateValueSupplier.get().getConditionValue().copy();
                        newValue.add(numberStateValueSupplier2.get().getConditionValue());
                        return new Condition<>("CREATED AT RUNTIME", newValue);
                    };
                case INCREMENT:
                    return (numberStateValueSupplier, numberStateValueSupplier2) -> () -> {
                        NumberStateValue newValue = numberStateValueSupplier.get().getConditionValue().copy();
                        newValue.increment();
                        return new Condition<>("CREATED AT RUNTIME", newValue);
                    };
                case MINUS:
                    return (numberStateValueSupplier, numberStateValueSupplier2) -> () -> {
                        NumberStateValue newValue = numberStateValueSupplier.get().getConditionValue().copy();
                        newValue.subtract(numberStateValueSupplier2.get().getConditionValue());
                        return new Condition<>("CREATED AT RUNTIME", newValue);
                    };
                case MULTIPLY:
                    return (numberStateValueSupplier, numberStateValueSupplier2) -> () -> {
                        NumberStateValue newValue = numberStateValueSupplier.get().getConditionValue().copy();
                        newValue.multiply(numberStateValueSupplier2.get().getConditionValue());
                        return new Condition<>("CREATED AT RUNTIME", newValue);
                    };
                case DIVIDE:
                    return (numberStateValueSupplier, numberStateValueSupplier2) -> () -> {
                        NumberStateValue newValue = numberStateValueSupplier.get().getConditionValue().copy();
                        newValue.divide(numberStateValueSupplier2.get().getConditionValue());
                        return new Condition<>("CREATED AT RUNTIME", newValue);
                    };
                case EXPONENTIATE:
                    return (numberStateValueSupplier, numberStateValueSupplier2) -> () -> {
                        NumberStateValue newValue = numberStateValueSupplier.get().getConditionValue().copy();
                        newValue.exponentiate(numberStateValueSupplier2.get().getConditionValue());
                        return new Condition<>("CREATED AT RUNTIME", newValue);
                    };
                case MODULO:
                    return (numberStateValueSupplier, numberStateValueSupplier2) -> () -> {
                        NumberStateValue newValue = numberStateValueSupplier.get().getConditionValue().copy();
                        newValue.mod(numberStateValueSupplier2.get().getConditionValue());
                        return new Condition<>("CREATED AT RUNTIME", newValue);
                    };
                case FLOOR:
                    return (numberStateValueSupplier, numberStateValueSupplier2) -> () -> {
                        NumberStateValue newValue = numberStateValueSupplier.get().getConditionValue().copy();
                        newValue.floor();
                        return new Condition<>("CREATED AT RUNTIME", newValue);
                    };
                case CEILING:
                    return (numberStateValueSupplier, numberStateValueSupplier2) -> () -> {
                        NumberStateValue newValue = numberStateValueSupplier.get().getConditionValue().copy();
                        newValue.ceiling();
                        return new Condition<>("CREATED AT RUNTIME", newValue);
                    };
                case LOWER_LIMIT:
                    return (numberStateValueSupplier, numberStateValueSupplier2) -> () -> {
                        NumberStateValue newValue = numberStateValueSupplier.get().getConditionValue().copy();
                        newValue.lowerLimit(numberStateValueSupplier2.get().getConditionValue());
                        return new Condition<>("CREATED AT RUNTIME", newValue);
                    };
                case UPPER_LIMIT:
                    return (numberStateValueSupplier, numberStateValueSupplier2) -> () -> {
                        NumberStateValue newValue = numberStateValueSupplier.get().getConditionValue().copy();
                        newValue.upperLimit(numberStateValueSupplier2.get().getConditionValue());
                        return new Condition<>("CREATED AT RUNTIME", newValue);
                    };
                default:
                    return null;
            }
        }
         */
}