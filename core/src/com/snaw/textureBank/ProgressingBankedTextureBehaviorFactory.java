package com.snaw.textureBank;

import com.snaw.generalBehaviors.SharedBehaviorFactoryHelpers;
import com.snaw.generalBehaviors.SupplierFactory;
import com.snaw.generalBehaviors.genericBehaviors.GenericBehavior;
import com.snaw.textureBank.progressingBehaviors.BlankStateBehaviorProgressing;
import com.snaw.textureBank.progressingBehaviors.ProgressChainedProgressingTextureBankBehavior;
import com.snaw.textureBank.progressingBehaviors.ProgressProgressingTextureBankBehavior;
import com.snaw.textureBank.progressingBehaviors.ResetStateBehaviorProgressing;

public class ProgressingBankedTextureBehaviorFactory extends SharedBehaviorFactoryHelpers {

    private static GenericBehavior getProgressingChainedBankedTextureBehaviorFromTag(String tagValue, GenericBehavior childBehavior, ProgressingTextureBank bank) {
        String cleanedTagValue = tagValue;
        if (tagValue.contains("{")) {
            cleanedTagValue = tagValue.substring(0, tagValue.indexOf('{'));
        }

        switch (cleanedTagValue) {
            case "PROGRESS AND CHAIN":
                return new ProgressChainedProgressingTextureBankBehavior(childBehavior, bank, getSingleArgumentValue(tagValue).get().getSupplier());
            default:
                return getChainedBehaviorFromTag(tagValue, childBehavior);
        }

    }

    public static GenericBehavior getProgressingBankedTextureBehaviorFromTag(ProgressingTextureBank bank, String tagValue) {
        if (tagValue.contains("->")) {
            String[] chainedValues = tagValue.split("->");
            GenericBehavior currentBehavior = getProgressingBankedTextureBehaviorFromTag(bank, chainedValues[chainedValues.length - 1]);
            for (int i = chainedValues.length - 2; i >= 0; i--) {
                currentBehavior = getProgressingChainedBankedTextureBehaviorFromTag(chainedValues[i], currentBehavior, bank);
            }
            return currentBehavior;
        }
        String cleanedTagValue = tagValue;
        if (tagValue.contains("{")) {
            cleanedTagValue = tagValue.substring(0, tagValue.indexOf('{'));
        }
        switch (cleanedTagValue) {
            case "SET STATE":
                return getSupplierBasedStateSetter(tagValue, SupplierFactory.SupportedType.STRING, null, true);
            case "SET INT STATE":
            case "SUBTRACT FROM STATE":
            case "ADD TO STATE":
            case "CALC AND SET":
                return getSupplierBasedStateSetter(tagValue, SupplierFactory.SupportedType.NUMBER, null, true);
            case "SET BOOL STATE":
                return getSupplierBasedStateSetter(tagValue, SupplierFactory.SupportedType.BOOLEAN, null, true);
            case "CLEAR STATE":
                return new BlankStateBehaviorProgressing(bank);
            case "RESET STATE":
                return new ResetStateBehaviorProgressing(bank);
            case "PROGRESS":
                return new ProgressProgressingTextureBankBehavior(bank, getSingleArgumentValue(tagValue).get().getSupplier());
            default:
                return null;
        }
    }


}
