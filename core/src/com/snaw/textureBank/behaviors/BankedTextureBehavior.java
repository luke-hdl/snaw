package com.snaw.textureBank.behaviors;

import com.snaw.generalBehaviors.genericBehaviors.GenericBehavior;

public abstract class BankedTextureBehavior implements GenericBehavior {
    //note that true blocks drawing instead of allowing it to continue...
    @Override
    public abstract boolean actAndFilter();
}
