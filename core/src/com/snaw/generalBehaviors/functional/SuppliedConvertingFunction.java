package com.snaw.generalBehaviors.functional;

import java.util.function.Supplier;

public abstract class SuppliedConvertingFunction<T, K> implements Supplier<K> {
    public Supplier<T> firstValue;
    public Supplier<T> secondValue;

    public SuppliedConvertingFunction(Supplier<T> supplierOne, Supplier<T> supplierTwo){
        this.firstValue = supplierOne;
        this.secondValue = supplierTwo;
    }
}
