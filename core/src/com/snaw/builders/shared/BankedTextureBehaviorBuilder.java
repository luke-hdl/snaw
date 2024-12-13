package com.snaw.builders.shared;

import com.snaw.builders.Builder;
import com.snaw.builders.managers.BuilderManager;
import com.snaw.generalBehaviors.genericBehaviors.GenericBehavior;
import com.snaw.snawfile.SnawTagElement;
import com.snaw.snawfile.SnawTagset;
import com.snaw.textureBank.BankedTextureBehaviorFactory;
import com.snaw.textureBank.behaviors.BankedTextureBehavior;

import java.util.LinkedList;
import java.util.List;

public class BankedTextureBehaviorBuilder extends Builder<List<GenericBehavior>> {

    public BankedTextureBehaviorBuilder(BuilderManager manager) {
        super(manager);
    }

    @Override
    public List<GenericBehavior> build(SnawTagset tagset) {
        List<GenericBehavior> behaviors = new LinkedList<>();
        for (SnawTagElement element : tagset.getElements("BEHAVIOR")) {
            behaviors.add(BankedTextureBehaviorFactory.getBankedTextureBehaviorFromTag(element.getValue()));
        }
        return behaviors;
    }
}
