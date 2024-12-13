package com.snaw.textureBank.behaviors;

public class DoNothingBehavior extends BankedTextureBehavior {
    @Override
    public boolean actAndFilter() {
        return false;
    }
}
