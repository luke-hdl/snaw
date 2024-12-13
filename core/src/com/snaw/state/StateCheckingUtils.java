package com.snaw.state;

import com.snaw.coreState.GameState;
import com.snaw.state.stateValues.NumberStateValue;

public class StateCheckingUtils {
    public static void setStateHelper(String checkString, String value) {
        String[] split = checkString.split(":");
        GameState.getMap().setChildState(split[0], split[1], split[2], value);
    }

    public static void setStateHelper(String checkString, NumberStateValue value) {
        String[] split = checkString.split(":");
        GameState.getMap().setChildState(split[0], split[1], split[2], value);

    }

    public static void setStateHelper(String checkString, boolean value) {
        String[] split = checkString.split(":");
        GameState.getMap().setChildState(split[0], split[1], split[2], value);
    }

    public static Condition<String> calculateStringStateCheck(String checkString, boolean checkTransient, boolean checkNontransient) {
        String[] split = checkString.split(":");
        return GameState.getMap().getStringChildState(split[0], split[1], split[2], checkTransient, checkNontransient);
    }

    public static Condition<NumberStateValue> calculateNumericStateCheck(String checkString, boolean checkTransient, boolean checkNontransient) {
        String[] split = checkString.split(":");
        return GameState.getMap().getNumericChildState(split[0], split[1], split[2], checkTransient, checkNontransient);

    }

    public static Condition<Boolean> calculateBooleanStateCheck(String checkString, boolean checkTransient, boolean checkNontransient) {
        String[] split = checkString.split(":");
        return GameState.getMap().getBoolChildState(split[0], split[1], split[2], checkTransient, checkNontransient);
    }
}
