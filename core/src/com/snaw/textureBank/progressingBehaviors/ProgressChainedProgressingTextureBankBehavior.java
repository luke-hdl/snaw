package com.snaw.textureBank.progressingBehaviors;

import com.snaw.generalBehaviors.genericBehaviors.GenericBehavior;
import com.snaw.generalBehaviors.genericBehaviors.GenericChainedBehavior;
import com.snaw.textureBank.ProgressingTextureBank;

import java.util.function.Supplier;

public class ProgressChainedProgressingTextureBankBehavior extends GenericChainedBehavior {
    ProgressingTextureBank bank;
    Supplier<String> textureId;

    public ProgressChainedProgressingTextureBankBehavior(GenericBehavior childBehavior, ProgressingTextureBank bank, Supplier<String> textureId) {
        super(childBehavior);
        this.bank = bank;
        this.textureId = textureId;
    }

    @Override
    public boolean shouldChain() {
        return bank.progress(textureId.get());
    }
}
