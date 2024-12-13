package com.snaw.logging;

public enum LogLevel {
    MAX_LOG, //logs everything of any note at all.
    DEBUG_LOAD, //logs anything of note for debugging purposes during the load and game.
    DEBUG_GAME, //logs anything of note for debugging purposes during the game.
    INVALID_STATE, //logs only states that should never occur, along with manually created Debug Behavior messages...
    DEBUG_BEHAVIOR, //logs only manually created debug behaviors (such as DebugBehavior in Character.)
    UNEXPECTED_EXCEPTION, //logs only exceptions that aren't considered "expected" (that is, LevelHasEnded in a level or CrashGame anywhere.)
    NO_LOG //no log...
}
