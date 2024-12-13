package com.snaw.exceptions;

public class LevelHasEndedException extends RuntimeException {
    private final String levelEndScreenId;

    public LevelHasEndedException(String levelEndScreenId) {
        this.levelEndScreenId = levelEndScreenId;
    }

    public String getLevelEndScreenId() {
        return levelEndScreenId;
    }

    //Occurs when a level has ended...
    //obviously. Mostly used for try-catch purposes.
    //Probably what I do with this is a bad idea. lol.
}
