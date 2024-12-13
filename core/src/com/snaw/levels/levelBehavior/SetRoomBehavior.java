package com.snaw.levels.levelBehavior;

import com.snaw.coreState.GameState;
import com.snaw.levels.Level;
import com.snaw.map.Room;
import com.snaw.state.Condition;

import java.util.function.Supplier;

public class SetRoomBehavior extends LevelBehavior {
    protected Supplier<String> camID;
    private Room cam;

    public SetRoomBehavior(Level level, Supplier<Condition<String>> camIdSupplier) {
        super(level);
        this.camID = camIdSupplier.get().getSupplier();
    }

    public Room getRoom() {
        if (cam == null) {
            String camIDString = camID.get();
            cam = GameState.getMap().getRooms().get(camIDString);
        }
        return cam;
    }

    @Override
    public boolean actAndFilter() {
        GameState.changeRooms(getRoom());
        return true;
    }
}
