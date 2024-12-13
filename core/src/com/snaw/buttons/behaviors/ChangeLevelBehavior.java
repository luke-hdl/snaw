package com.snaw.buttons.behaviors;

import com.snaw.buttons.ClickArea;
import com.snaw.coreState.GameState;
import com.snaw.levels.Level;

public class ChangeLevelBehavior extends ClickBehavior {
    protected String levelId;

    public ChangeLevelBehavior(ClickArea area, String levelId) {
        super(area);
        this.levelId = levelId;
    }

    protected Level getLevel() {
        return GameState.getLevel(levelId);
    }

    @Override
    public void act() {
        GameState.setCurrentLevel(levelId);
    }
}
