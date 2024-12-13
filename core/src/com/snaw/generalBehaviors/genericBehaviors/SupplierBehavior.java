package com.snaw.generalBehaviors.genericBehaviors;

import com.snaw.state.Condition;
import com.snaw.textureBank.behaviors.DoNothingBehavior;

import java.util.function.Supplier;

public class SupplierBehavior extends GenericChainedBehavior {
    //Optionally chains.

    protected Supplier supplier;

    public SupplierBehavior(GenericBehavior behavior, Supplier supplier) {
        super(behavior);
        this.supplier = supplier;
    }

    @Override
    public void act() {
        supplier.get();
    }

    @Override
    public boolean actAndFilter() {
        boolean result = shouldChain();
        //Chaining follows inversed logic, so...
        if (result == true || chainedBehavior.getClass().equals(DoNothingBehavior.class)) {
            return result;
        }
        return chainedBehavior.actAndFilter();
    }

    @Override
    public boolean shouldChain() {
        Object something = supplier.get();
        if (something.getClass().equals(Boolean.class)) {
            return (boolean) something;
        }
        if (something.getClass().equals(Condition.class)) {
            if (((Condition<?>) something).getConditionValue().getClass().equals(Boolean.class)) {
                return (boolean) ((Condition<?>) something).getConditionValue();
            }
        }
        return true;
    }
}
