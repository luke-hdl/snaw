package com.snaw.textureBank.progressingBehaviors;

import com.snaw.textureBank.ProgressingTextureBank;

public class ResetStateBehaviorProgressing extends ProgressingTextureBankBehavior {
    public ResetStateBehaviorProgressing(ProgressingTextureBank bank) {
        super(bank);
    }

    @Override
    public boolean actAndFilter() {
        bank.initialize();
        return true;
    }
}
