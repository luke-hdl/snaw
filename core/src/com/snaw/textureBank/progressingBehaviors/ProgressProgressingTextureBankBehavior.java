package com.snaw.textureBank.progressingBehaviors;

import com.snaw.textureBank.ProgressingTextureBank;

import java.util.function.Supplier;

public class ProgressProgressingTextureBankBehavior extends ProgressingTextureBankBehavior {
    protected Supplier<String> textureId;

    public ProgressProgressingTextureBankBehavior(ProgressingTextureBank bank, Supplier<String> textureId) {
        super(bank);
        this.textureId = textureId;
    }

    @Override
    public boolean actAndFilter() {
        bank.progress(textureId.get());
        return true;
    }
}
