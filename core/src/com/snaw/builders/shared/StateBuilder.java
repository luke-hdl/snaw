package com.snaw.builders.shared;

import com.snaw.builders.Builder;
import com.snaw.builders.managers.BuilderManager;
import com.snaw.state.stateValues.NumberStateValue;
import com.snaw.snawfile.SnawTagElement;
import com.snaw.snawfile.SnawTagset;
import com.snaw.state.Condition;
import com.snaw.state.State;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StateBuilder extends Builder<State> {
    private static final Pattern getSoleArgument = Pattern.compile("(.*)\\{([0-9.A-Za-z,;: ]*)\\}");

    public StateBuilder(BuilderManager manager) {
        super(manager);
    }

    @Override
    public State build(SnawTagset tagset) {
        //Conditions are built here because creating a builder for each type they support is too annoying, lol (and so is using generics.)
        //This might get changed later.
        Map<String, Condition<Boolean>> boolConditions = new HashMap<>();
        Map<String, Condition<String>> stringConditions = new HashMap<>();
        Map<String, Condition<NumberStateValue>> numberConditions = new HashMap<>();

        for (SnawTagElement conditionTag : tagset.getElements("CONDITION BOOL")) {
            if (conditionTag.getValue().contains("{")) {
                String[] args = getSplitOutArguments(conditionTag.getValue());
                boolConditions.put(args[0], new Condition<>(args[0], args[1].toLowerCase().equals("true")));
            } else {
                boolConditions.put(conditionTag.getValue(), new Condition<>(conditionTag.getValue(), true));
            }
        }
        for (SnawTagElement conditionTag : tagset.getElements("CONDITION STRING")) {
            if (conditionTag.getValue().contains("{")) {
                String[] args = getSplitOutArguments(conditionTag.getValue());
                stringConditions.put(args[0], new Condition<>(args[0], args[1]));
            } else {
                stringConditions.put(conditionTag.getValue(), new Condition<>(conditionTag.getValue(), ""));
            }
        }
        for (SnawTagElement conditionTag : tagset.getElements("CONDITION NUMBER")) {
            if (conditionTag.getValue().contains("{")) {
                String[] args = getSplitOutArguments(conditionTag.getValue());
                numberConditions.put(args[0], new Condition<>(args[0], NumberStateValue.parse(args[1])));
            } else {
                numberConditions.put(conditionTag.getValue(), new Condition<>(conditionTag.getValue(), new NumberStateValue()));
            }
        }
        for (SnawTagElement conditionTag : tagset.getElements("CONDITION")) {
            //Default to String.
            if (conditionTag.getValue().contains("{")) {
                String[] args = getSplitOutArguments(conditionTag.getValue());
                stringConditions.put(args[0], new Condition<>(args[0], args[1]));
            } else {
                stringConditions.put(conditionTag.getValue(), new Condition<>(conditionTag.getValue(), ""));
            }
        }

        return new State(boolConditions, stringConditions, numberConditions);
    }

    private String[] getSplitOutArguments(String value) {
        Matcher matcher = getSoleArgument.matcher(value);
        matcher.find();
        return matcher.groupCount() >= 2 ? new String[]{matcher.group(1), matcher.group(2)} : new String[0];
    }
}
