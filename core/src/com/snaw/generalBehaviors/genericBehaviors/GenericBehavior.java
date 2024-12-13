package com.snaw.generalBehaviors.genericBehaviors;

import com.snaw.logging.LogLevel;
import com.snaw.logging.Logger;

public interface GenericBehavior {
    default void act() {
        Logger.log("ACT ERROR", LogLevel.INVALID_STATE);
        //No-Op.
    }

    default boolean actAndFilter() {
        Logger.log("ACT AND FILTER ERROR", LogLevel.INVALID_STATE);
        return true;
    }

}
