package com.snaw.buttons.behaviors;

import com.snaw.generalBehaviors.genericBehaviors.GenericBehavior;

public class ThrowExceptionBehavior implements GenericBehavior {
    protected RuntimeException exception;

    public ThrowExceptionBehavior(RuntimeException exception) {
        this.exception = exception;
    }

    @Override
    public void act() {
        throw exception;
    }

    @Override
    public boolean actAndFilter() {
        throw exception;
    }
}
