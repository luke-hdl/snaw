package com.snaw.builders.map;

import com.snaw.builders.Builder;
import com.snaw.builders.managers.BuilderManager;
import com.snaw.buttons.ClickBehaviorFactory;
import com.snaw.generalBehaviors.genericBehaviors.GenericBehavior;
import com.snaw.snawfile.SnawTagElement;
import com.snaw.snawfile.SnawTagset;

import java.util.LinkedList;
import java.util.List;

public class ClickBehaviorBuilder extends Builder<List<GenericBehavior>> {
    public ClickBehaviorBuilder(BuilderManager manager) {
        super(manager);
    }

    @Override
    public List<GenericBehavior> build(SnawTagset tagset) {
        List<GenericBehavior> behaviors = new LinkedList<>();
        for (SnawTagElement element : tagset.getElements("BEHAVIOR")) {
            behaviors.add(ClickBehaviorFactory.getClickBehaviorFromTag(element.getValue()));
        }
        return behaviors;
    }
}
