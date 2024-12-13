package com.snaw.builders.levels;

import com.snaw.builders.Builder;
import com.snaw.builders.managers.BuilderManager;
import com.snaw.generalBehaviors.genericBehaviors.GenericBehavior;
import com.snaw.levels.Level;
import com.snaw.levels.LevelBehaviorFactory;
import com.snaw.snawfile.SnawTagElement;
import com.snaw.snawfile.SnawTagset;

import java.util.LinkedList;
import java.util.List;

public class LevelBuilder extends Builder<Level> {
    public LevelBuilder(BuilderManager manager) {
        super(manager);
    }

    @Override
    public Level build(SnawTagset tagset) {
        String id = tagset.getOneOffTagValue("ID");
        String mapId = tagset.getOneOffTagValue("MAP");

        Level level = new Level(id, mapId);

        SnawTagset setupTagset = tagset.getOneOffTagset("SETUP BEHAVIORS");
        if (setupTagset != null) {
            List<GenericBehavior> behaviorList = new LinkedList<>();
            for (SnawTagElement element : setupTagset.getElements("BEHAVIOR")) {
                behaviorList.add(LevelBehaviorFactory.getLevelBehaviorFromTag(level, element.getValue()));
            }
            level.setSetupBehaviorList(behaviorList);
        }

        SnawTagset ongoingTagset = tagset.getOneOffTagset("ONGOING BEHAVIORS");
        if (ongoingTagset != null) {
            List<GenericBehavior> behaviorList = new LinkedList<>();
            for (SnawTagElement element : ongoingTagset.getElements("BEHAVIOR")) {
                behaviorList.add(LevelBehaviorFactory.getLevelBehaviorFromTag(level, element.getValue()));
            }
            level.setOngoingBehaviorList(behaviorList);
        }

        return level;
    }
}
