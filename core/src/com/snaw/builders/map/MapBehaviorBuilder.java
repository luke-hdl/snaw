package com.snaw.builders.map;

import com.snaw.builders.Builder;
import com.snaw.builders.managers.BuilderManager;
import com.snaw.generalBehaviors.genericBehaviors.GenericBehavior;
import com.snaw.mapBehaviors.MapBehaviorFactory;
import com.snaw.snawfile.SnawTagElement;
import com.snaw.snawfile.SnawTagset;

import java.util.LinkedList;
import java.util.List;

public class MapBehaviorBuilder extends Builder<List<GenericBehavior>> {
    public MapBehaviorBuilder(BuilderManager manager) {
        super(manager);
    }

    @Override
    public List<GenericBehavior> build(SnawTagset tagset) {
        List<GenericBehavior> behaviors = new LinkedList<>();
        for (SnawTagElement element : tagset.getElements("BEHAVIOR")) {
            behaviors.add(MapBehaviorFactory.getMapBehaviorFromTag(element.getValue()));
        }
        return behaviors;
    }
}
