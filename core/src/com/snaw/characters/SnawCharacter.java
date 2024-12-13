package com.snaw.characters;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.snaw.coreState.GameState;
import com.snaw.gameObjects.StateHoldingObject;
import com.snaw.generalBehaviors.genericBehaviors.GenericBehavior;
import com.snaw.map.Room;
import com.snaw.state.State;
import com.snaw.textureBank.TextureBank;

import java.util.List;
import java.util.Map;

public class SnawCharacter implements StateHoldingObject {
    //it's called SnawCharacter because Character is a primitive wrapper name...
    protected String id;
    protected State initialState;
    protected State currentState;
    protected List<GenericBehavior> behaviorList;
    protected Map<String, TextureBank> textureBanks;
    protected State transientState; //Used to store State values that don't persist through multiple moves.

    public SnawCharacter(String id, State state, Map<String, TextureBank> textureBanks) {
        //We don't initialize with CharacterBehaviors since they need the Character to be initialized to instantiate them.
        this.id = id;
        this.initialState = state;
        if (this.initialState == null) {
            this.initialState = new State();
        }
        this.transientState = new State();
        this.textureBanks = textureBanks;
    }

    @Override
    public State getState(){
        return currentState;
    }

    public void initialize() {
        this.currentState = initialState.copy();
        transientState = new State();
        currentState.setOwner(this);
        transientState.setOwner(this);
    }

    public void act() {
        for (GenericBehavior behavior : behaviorList) {
            if (!behavior.actAndFilter()) {
                break;
            }
        }

        for (TextureBank textureBank : textureBanks.values()) {
            textureBank.performAnyActions();
        }
    }

    public void startTurn() {
        this.transientState = new State();
    }

    public Room getRoom() {
        //Provided for convienence; assumes that you use the State condition In Room.
        String roomId = currentState.getStringValue("In Room").getConditionValue();
        if (roomId != null) {
            return GameState.getMap().getRooms().get(roomId);
        }
        return null;
    }

    public void setRoom(String roomId) {
        //Provided for convienence; assumes that you use the State condition In Room.
        currentState.setValue("In Room", roomId);
    }

    public void render(SpriteBatch batch, int x, int y, String renderingLevel) {
        if (textureBanks.containsKey(renderingLevel)) {
            textureBanks.get(renderingLevel).render(currentState, batch, x, y);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State state) {
        this.currentState = state;
    }

    public void finishTurn() {
        this.transientState = new State();
    }

    public State getTransientState() {
        return this.transientState;
    }

    public List<GenericBehavior> getBehaviorList() {
        return behaviorList;
    }

    public void setBehaviorList(List<GenericBehavior> behaviorList) {
        this.behaviorList = behaviorList;
    }

    public Map<String, TextureBank> getTextureBanks() {
        return textureBanks;
    }

    public void setTextureBank(Map<String, TextureBank> textureBank) {
        this.textureBanks = textureBank;
    }

}
