package com.snaw.textureBank;

import com.snaw.generalBehaviors.SharedBehaviorFactoryHelpers;
import com.snaw.generalBehaviors.SupplierFactory;
import com.snaw.generalBehaviors.genericBehaviors.GenericBehavior;
import com.snaw.textureBank.behaviors.AppearsRandomlyTextureBehavior;
import com.snaw.textureBank.behaviors.DoNothingBehavior;

public class BankedTextureBehaviorFactory extends SharedBehaviorFactoryHelpers {
    public static GenericBehavior getBankedTextureBehaviorFromTag(String tagValue) {
        String tagValueNoArguments = tagValue.substring(0, tagValue.indexOf('{'));
        switch (tagValueNoArguments) {
            case "APPEAR RANDOMLY":
                return new AppearsRandomlyTextureBehavior(getSingleArgumentValueNumber(tagValue).get().getSupplier());
            case "CHECK STATE":
                // TODO - should be more robust.
                if ( !getRawSingleArgumentValue(tagValue).contains("=")){
                    return getSupplierBasedStateChecker(getRawSingleArgumentValue(tagValue), SupplierFactory.SupportedType.BOOLEAN, new DoNothingBehavior());
                }
                return getSupplierBasedStateChecker(getRawSingleArgumentValue(tagValue), SupplierFactory.SupportedType.STRING, new DoNothingBehavior(), false);
            case "CHECK INT STATE":
                return getSupplierBasedStateChecker(getRawSingleArgumentValue(tagValue), SupplierFactory.SupportedType.NUMBER, new DoNothingBehavior());
            default:
                System.out.println("Doing nothing for value: " + tagValue);
                return new DoNothingBehavior();
        }
    }
}
