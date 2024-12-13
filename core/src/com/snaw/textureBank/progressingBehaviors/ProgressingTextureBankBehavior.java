package com.snaw.textureBank.progressingBehaviors;

import com.snaw.generalBehaviors.genericBehaviors.GenericBehavior;
import com.snaw.textureBank.ProgressingTextureBank;

public abstract class ProgressingTextureBankBehavior implements GenericBehavior {
    protected final ProgressingTextureBank bank;

    public ProgressingTextureBankBehavior(ProgressingTextureBank bank) {
        this.bank = bank;
    }

    @Override
    public abstract boolean actAndFilter(); //Returns whether the character should continue to act this round.

    protected boolean thisIs(String tag) {
        return bank.getState().checkValue(tag, true);
    }
}
