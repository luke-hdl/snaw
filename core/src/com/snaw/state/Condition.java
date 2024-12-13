package com.snaw.state;

import java.util.function.Supplier;

public class Condition<T> {
    //Conditions, among other things, allow us to mutate immutable condition values.
    //If a condition needs built-in behavior to support and manage multiple types under "one roof", so to speak;
    //a StateValue class should be created.
    protected String conditionKey;
    protected T conditionValue;

    public Condition(String conditionKey, T conditionValue) {
        this.conditionKey = conditionKey;
        this.conditionValue = conditionValue;
    }

    public String getConditionKey() {
        return conditionKey;
    }

    public T getConditionValue() {
        return conditionValue;
    }

    public void setConditionValue(T conditionValue) {
        this.conditionValue = conditionValue;
    }

    public Supplier<T> getSupplier() {
        //Used when we ONLY need the supplier.
        return () -> conditionValue;
    }
}
