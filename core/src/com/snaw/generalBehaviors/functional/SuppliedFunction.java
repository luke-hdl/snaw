package com.snaw.generalBehaviors.functional;

import java.util.function.Supplier;

//If you need the input and output classes to vary, see SuppliedConvertingFunction.
//This is a very common subcase of SuppliedConvertingFunction, where no conversion is applied.
public abstract class SuppliedFunction<T> extends SuppliedConvertingFunction<T, T> {
    public SuppliedFunction(Supplier<T> supplierOne, Supplier<T> supplierTwo){
        super(supplierOne, supplierTwo);
    }

}
