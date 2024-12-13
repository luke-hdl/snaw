package com.snaw.textureBank.behaviors;

import com.snaw.coreState.GameState;
import com.snaw.state.stateValues.NumberStateValue;

import java.util.function.Supplier;

public class AppearsRandomlyTextureBehavior extends BankedTextureBehavior {
    Supplier<NumberStateValue> chance;

    public AppearsRandomlyTextureBehavior(Supplier<NumberStateValue> chance) {
        this.chance = chance;
    }

    @Override
    public boolean actAndFilter() {
        return 100d * GameState.randomChance() <= chance.get().getInt();
    }
}
