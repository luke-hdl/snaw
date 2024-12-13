package com.snaw.buttons;

import com.snaw.buttons.behaviors.ChangeLevelBehavior;
import com.snaw.buttons.behaviors.ClickBehavior;
import com.snaw.buttons.behaviors.SetCamBehavior;
import com.snaw.buttons.behaviors.SetMenuBehavior;
import com.snaw.buttons.behaviors.ToggleRoomStateBehavior;
import com.snaw.generalBehaviors.SharedBehaviorFactoryHelpers;

import java.util.regex.Matcher;

public class ClickBehaviorFactory extends SharedBehaviorFactoryHelpers {
    public static ClickBehavior getClickBehaviorFromTag(String tagValue) {
        String tagValueNoArguments = !tagValue.contains("{") ? tagValue : tagValue.substring(0, tagValue.indexOf('{'));
        switch (tagValueNoArguments) {
            case "DO NOTHING":
                return new ClickBehavior(null) {
                    @Override
                    public void act() {
                        //no op
                    }
                };
            case "SET CAM":
                //Doesn't CURRENTLY support advanced behavior.
                //Probably won't until the Room Behavior State Logic is in place.
                return new SetCamBehavior(null, getSingleArgumentValue(tagValue));
            case "SET MENU":
                //Doesn't CURRENTLY support advanced behavior.
                //Probably won't until the Room Behavior State Logic is in place.
                return new SetMenuBehavior(null, getSingleArgumentValue(tagValue));
            case "START LEVEL":
                //Doesn't CURRENTLY support advanced behavior.
                //Probably won't until the Room Behavior State Logic is in place.
                Matcher matcher = getSoleArgument.matcher(tagValue);
                matcher.find();
                String levelId = matcher.group(1);
                return new ChangeLevelBehavior(null, levelId);
            case "TOGGLE ROOM STATE":
                //Doesn't CURRENTLY support advanced behavior.
                //Probably won't until the Room Behavior State Logic is in place.
                return new ToggleRoomStateBehavior(null, getSingleArgumentValue(tagValue).get().getConditionValue().split(":")[0], getSingleArgumentValue(tagValue).get().getConditionValue().split(":")[1]);
            case "QUIT":
                return new ClickBehavior(null) {
                    @Override
                    public void act() {
                        //throw new RuntimeException("Note to self: remember how to quit correctly");
                    }
                };
            default:
                return null;
        }
    }
}
