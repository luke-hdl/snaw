package com.snaw.generalBehaviors.genericBehaviors;

public abstract class GenericChainedBehavior implements GenericBehavior {
    GenericBehavior chainedBehavior;

    public  GenericChainedBehavior( GenericBehavior chainedBehavior ){
        this.chainedBehavior = chainedBehavior;
    }

    @Override
    public boolean actAndFilter() {
        if (shouldChain()) {
            return chainedBehavior.actAndFilter();
        }
        return true;
    }

    public abstract boolean shouldChain();
}
