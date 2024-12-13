package com.snaw.buttons.behaviors;

import com.snaw.buttons.ClickArea;
import com.snaw.coreState.GameState;
import com.snaw.map.Room;

public class ToggleRoomStateBehavior extends ClickBehavior {
    String camID;
    String key;
    private Room cam;

    public ToggleRoomStateBehavior(ClickArea clickArea, String camID, String key) {
        super(clickArea);
        this.camID = camID;
        this.key = key;
    }

    public ToggleRoomStateBehavior(ClickArea clickArea, Room cam, String key) {
        super(clickArea);
        this.cam = cam;
        this.key = key;
    }

    public Room getRoom() {
        if (cam == null) {
            cam = GameState.getMap().getRooms().get(camID);
        }
        return cam;
    }

    @Override
    public void act() {
        getRoom().toggleStateBoolValue(key);
    }

}
