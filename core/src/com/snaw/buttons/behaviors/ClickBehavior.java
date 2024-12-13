package com.snaw.buttons.behaviors;

import com.snaw.buttons.ClickArea;
import com.snaw.generalBehaviors.genericBehaviors.GenericBehavior;

public abstract class ClickBehavior implements GenericBehavior {
    protected ClickArea clickArea;

    public ClickBehavior(ClickArea clickArea) {
        this.clickArea = clickArea;
    }

    @Override
    public abstract void act();
}
