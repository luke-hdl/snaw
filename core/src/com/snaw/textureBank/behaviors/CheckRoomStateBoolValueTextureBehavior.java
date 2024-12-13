package com.snaw.textureBank.behaviors;

import com.snaw.coreState.GameState;
import com.snaw.map.Room;

public class CheckRoomStateBoolValueTextureBehavior extends BankedTextureBehavior {
    private String roomId;
    private Room room;
    private String checkKey;

    public CheckRoomStateBoolValueTextureBehavior(String roomId, String checkKey) {
        this.roomId = roomId;
        this.checkKey = checkKey;
    }

    @Override
    public boolean actAndFilter() {
        return !getRoom().getStateBoolValue(checkKey).getConditionValue();
    }

    private Room getRoom() {
        if (room == null) {
            room = GameState.getMap().getRooms().get(roomId);
        }

        return room;
    }


}
