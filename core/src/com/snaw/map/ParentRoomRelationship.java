package com.snaw.map;

import com.snaw.coreState.GameState;
import com.snaw.textureBank.PlacementTexture;

public class ParentRoomRelationship {
    protected Room parent;
    protected Room child;
    protected PlacementTexture placementTexture;

    protected String parentId;
    protected String childId;

    public ParentRoomRelationship(PlacementTexture placementTexture, String parentId, String childId) {
        this.placementTexture = placementTexture;
        this.parentId = parentId;
        this.childId = childId;
    }

    public Room getParent() {
        if (parent == null) {
            parent = GameState.getMap().getRooms().get(parentId);
        }
        return parent;
    }

    public Room getChild() {
        if (child == null) {
            child = GameState.getMap().getRooms().get(childId);
        }
        return child;
    }

    public int getStartX() {
        return placementTexture.getCalculatedStartPoint()[0];
    }

    public int getStartY() {
        return placementTexture.getCalculatedStartPoint()[1];
    }

    public String getChildId() {
        //Mostly used in initialization, before the map is created.
        return childId;
    }

    public String getParentId() {
        //Mostly used in initialization, before the map is created.
        return parentId;
    }
}
