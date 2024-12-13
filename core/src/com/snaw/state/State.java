package com.snaw.state;

import com.snaw.gameObjects.StateHoldingObject;
import com.snaw.state.stateValues.NumberStateValue;

import java.util.HashMap;
import java.util.Map;

public class State {
    //A State holds a number of arbitrary Conditions, representing the condition of whatever it is.
    //Currently, conditions support strings, NumberStateValues, and bools.
    //These are set in the RAWs and checked by them.

    protected StateHoldingObject parent;

    protected Map<String, Condition<Boolean>> boolConditions;
    protected Map<String, Condition<String>> stringConditions;
    protected Map<String, Condition<NumberStateValue>> numberStateValueConditions;

    public State(Map<String, Condition<Boolean>> boolConditions, Map<String, Condition<String>> stringConditions, Map<String, Condition<NumberStateValue>> numberStateValueConditions) {
        this.boolConditions = boolConditions;
        this.stringConditions = stringConditions;
        this.numberStateValueConditions = numberStateValueConditions;
    }

    public State() {
        this.boolConditions = new HashMap<>();
        this.stringConditions = new HashMap<>();
        this.numberStateValueConditions = new HashMap<>();
    }

    public State copy() {
        //Owner is NOT copied, since template states don't have owners.
        Map<String, Condition<Boolean>> copiedBoolConditions = new HashMap<>();
        Map<String, Condition<String>> copiedStringConditions = new HashMap<>();
        Map<String, Condition<NumberStateValue>> copiedNumberStateValueConditions = new HashMap<>();

        for (Map.Entry<String, Condition<Boolean>> entry : boolConditions.entrySet()) {
            copiedBoolConditions.put(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<String, Condition<String>> entry : stringConditions.entrySet()) {
            copiedStringConditions.put(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<String, Condition<NumberStateValue>> entry : numberStateValueConditions.entrySet()) {
            copiedNumberStateValueConditions.put(entry.getKey(), new Condition<>(entry.getKey(), entry.getValue().getConditionValue().copy()));
        }

        return new State(copiedBoolConditions, copiedStringConditions, copiedNumberStateValueConditions);
    }

    public void setOwner(StateHoldingObject stateHoldingObject) {
        this.parent = stateHoldingObject;
    }

    //HARDCODED specially-supported state names.

    public int[] getStateBasedOffsets() {
        int x = this.getNumberStateValueValue("x offset").getConditionValue().getInt();
        int y = this.getNumberStateValueValue("y offset").getConditionValue().getInt();
        return new int[]{x, y};
    }

    public void toggle(String key) {
        if (!boolConditions.containsKey(key)) {
            boolConditions.put(key, new Condition<>(key, true));
        } else {
            boolConditions.put(key, new Condition<>(key, !boolConditions.get(key).getConditionValue()));
        }
    }

    public void setValue(String key, boolean bool) {
        boolConditions.put(key, new Condition<>(key, bool));
    }

    public void setValue(String key, String string) {
        stringConditions.put(key, new Condition<>(key, string));
    }

    public void setValue(String key, NumberStateValue numberStateValue) {
        numberStateValueConditions.put(key, new Condition<>(key, numberStateValue));
    }

    public Condition<Boolean> check(String key) {
        if (!boolConditions.containsKey(key)) {
            boolConditions.put(key, new Condition<>(key, false));
        }

        return boolConditions.get(key);
    }

    public Condition<NumberStateValue> getNumberStateValueValue(String key) {
        if (!numberStateValueConditions.containsKey(key)) {
            numberStateValueConditions.put(key, new Condition<>(key, new NumberStateValue()));
        }
        return numberStateValueConditions.get(key);
    }

    public Condition<String> getStringValue(String key) {
        if (!stringConditions.containsKey(key)) {
            stringConditions.put(key, new Condition<>(key, ""));
        }

        return stringConditions.get(key);
    }

    public boolean checkValue(String key, String value) {
        return stringConditions.containsKey(key) && stringConditions.get(key).getConditionValue().equals(value);
    }

    public boolean checkValue(String key, NumberStateValue value) {
        return numberStateValueConditions.containsKey(key) && numberStateValueConditions.get(key).getConditionValue().equals(value);
    }

    public boolean checkValue(String key, boolean value) {
        return boolConditions.containsKey(key) && boolConditions.get(key).getConditionValue().equals(value);
    }
}
