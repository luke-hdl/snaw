package com.snaw.textureBank.progressingBehaviors;

import com.snaw.state.State;
import com.snaw.textureBank.ProgressingTextureBank;

public class BlankStateBehaviorProgressing extends ProgressingTextureBankBehavior {
    public BlankStateBehaviorProgressing(ProgressingTextureBank bank) {
        super(bank);
    }

    @Override
    public boolean actAndFilter() {
        bank.setState(new State());
        return true;
    }
}
