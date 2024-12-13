package com.snaw.buttons.behaviors;

import com.snaw.buttons.ClickArea;
import com.snaw.coreState.GameState;
import com.snaw.state.Condition;

import java.util.function.Supplier;

public class SetMenuBehavior extends ClickBehavior {
    // TODO - This could use a RoomSupplier instead of a condition for a string for a room ID...
    protected Supplier<Condition<String>> menuId;

    public SetMenuBehavior(ClickArea area, Supplier<Condition<String>> menuId) {
        super(area);
        this.menuId = menuId;
    }

    @Override
    public void act() {
        GameState.changeMenus(menuId.get().getConditionValue());
    }
}
