package com.snaw.buttons.behaviors;

import com.snaw.buttons.ClickArea;
import com.snaw.coreState.GameState;
import com.snaw.map.Room;
import com.snaw.state.Condition;

import java.util.function.Supplier;

public class SetCamBehavior extends ClickBehavior {
    // TODO - This could use a RoomSupplier instead of a condition for a string for a room ID...
    Supplier<Condition<String>> camID;

    public SetCamBehavior(ClickArea clickArea, Supplier<Condition<String>> camID) {
        super(clickArea);
        this.camID = camID;
    }

    public Room getRoom() {
        return GameState.getMap().getRooms().get(camID.get().getConditionValue());
    }

    @Override
    public void act() {
        GameState.changeRooms(getRoom());
    }
}
