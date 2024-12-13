package com.snaw.map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.snaw.buttons.ButtonTemplate;
import com.snaw.characters.SnawCharacter;
import com.snaw.coreState.GameState;
import com.snaw.gameObjects.StateHoldingObject;
import com.snaw.state.Condition;
import com.snaw.state.stateValues.NumberStateValue;
import com.snaw.state.State;
import com.snaw.uielements.UIElement;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GameMap implements StateHoldingObject {
    String id;
    Map<String, Room> rooms;
    Map<String, ButtonTemplate> buttons;

    Map<String, UIElement> uiElements;

    Map<String, SnawCharacter> characters;
    List<String> renderingLevels;
    Room office;

    State initialState;
    State currentState;

    public GameMap(String id, List<String> renderingLevels, State state, Map<String, Room> rooms, Map<String, ButtonTemplate> buttons, Map<String, UIElement> elements, Map<String, SnawCharacter> characters, Room office) {
        this.id = id;
        this.renderingLevels = renderingLevels;
        this.initialState = state;
        this.rooms = rooms;
        this.buttons = buttons;
        this.office = office;
        this.uiElements = elements;
        this.characters = characters;
    }

    public void initialize() {
        this.currentState = initialState.copy();
        currentState.setOwner(this);
        for (SnawCharacter character : characters.values()) {
            character.initialize();
        }
        for (Room room : rooms.values()) {
            room.initialize();
        }
    }

    @Override
    public State getState(){
        return currentState;
    }

    public String getId() {
        return id;
    }

    public Map<String, SnawCharacter> getCharacters() {
        return characters;
    }

    public List<SnawCharacter> getCharacters(String roomId) {
        // TODO - This is inefficient. Shouldn't be a problem with a small number of characters; could get bad with many.
        List<SnawCharacter> charactersInRoom = new LinkedList<>();
        for (SnawCharacter character : characters.values()) {
            if (character.getCurrentState().getStringValue("In Room").getConditionValue().equals(roomId)) {
                charactersInRoom.add(character);
            }
        }
        return charactersInRoom;
    }

    public Room getOffice() {
        return office;
    }

    public Map<String, ButtonTemplate> getButtons() {
        return buttons;
    }

    public Map<String, Room> getRooms() {
        return rooms;
    }

    public Map<String, UIElement> getUiElements() {
        return uiElements;
    }

    public void render(SpriteBatch batch){
        for (Room room : rooms.values()) {
            if (room == GameState.getCurrentRoom()) {
                room.processActive(batch, 0, 0);
            } else {
                room.processInactive();
            }
        }

    }

    public void process() {
        for (SnawCharacter character : characters.values()) {
            character.startTurn();
        }
        for (SnawCharacter character : characters.values()) {
            character.act();
        }
    }

    public void setChildState(String idType, String id, String key, NumberStateValue value, boolean setNontransient, boolean setTransient) {
        switch (idType) {
            case "Room":
                this.getRooms().get(id).currentState.setValue(key, value);
                break;
            case "Character":
                if (setTransient) {
                    this.getCharacters().get(id).getTransientState().setValue(key, value);
                }
                if (setNontransient) {
                    this.getCharacters().get(id).getCurrentState().setValue(key, value);
                }
                break;
            case "Map":
                this.currentState.setValue(key, value);
                break;
            default:
                //No op.
                break;
        }
    }

    public void setChildState(String idType, String id, String key, String value) {
        setChildState(idType, id, key, value, true, false);
    }

    public void setChildState(String idType, String id, String key, NumberStateValue value) {
        setChildState(idType, id, key, value, true, false);
    }

    public void setChildState(String idType, String id, String key, boolean value) {
        setChildState(idType, id, key, value, true, false);
    }

    public void setChildState(String idType, String id, String key, String value, boolean setNontransient, boolean setTransient) {
        switch (idType) {
            case "Room":
                this.getRooms().get(id).currentState.setValue(key, value);
                break;
            case "Character":
                if (setTransient) {
                    this.getCharacters().get(id).getTransientState().setValue(key, value);
                }
                if (setNontransient) {
                    this.getCharacters().get(id).getCurrentState().setValue(key, value);
                }
                break;
            case "Map":
                this.currentState.setValue(key, value);
                break;
            default:
                //No op.
                break;
        }
    }

    public void setChildState(String idType, String id, String key, boolean value, boolean setNontransient, boolean setTransient) {
        switch (idType) {
            case "Room":
                this.getRooms().get(id).currentState.setValue(key, value);
                break;
            case "Character":
                if (setTransient) {
                    this.getCharacters().get(id).getTransientState().setValue(key, value);
                }
                if (setNontransient) {
                    this.getCharacters().get(id).getCurrentState().setValue(key, value);
                }
                break;
            case "Map":
                this.currentState.setValue(key, value);
                break;
            default:
                //No op.
                break;
        }
    }

    public boolean checkChildState(String idType, String id, String key, String value) {
        return checkChildState(idType, id, key, value, true, true);
    }

    public boolean checkChildState(String idType, String id, String key, NumberStateValue value) {
        return checkChildState(idType, id, key, value, true, true);
    }

    public boolean checkChildState(String idType, String id, String key, boolean value) {
        return checkChildState(idType, id, key, value, true, true);
    }

    public boolean checkChildState(String idType, String id, String key, NumberStateValue value, boolean checkNontransient, boolean checkTransient) {
        switch (idType) {
            case "Room":
                return this.getRooms().get(id).currentState.checkValue(key, value);
            case "Character":
                if (checkTransient) {
                    return this.getCharacters().get(id).getTransientState().checkValue(key, value);

                }
                if (checkNontransient) {
                    return this.getCharacters().get(id).getCurrentState().checkValue(key, value);
                }
                return true;
            case "Map":
                return this.currentState.checkValue(key, value);
            default:
                return false;
        }
    }

    public boolean checkChildState(String idType, String id, String key, boolean value, boolean checkNontransient, boolean checkTransient) {
        switch (idType) {
            case "Room":
                return this.getRooms().get(id).currentState.checkValue(key, value);
            case "Character":
                if (checkTransient) {
                    return this.getCharacters().get(id).getTransientState().checkValue(key, value);

                }
                if (checkNontransient) {
                    return this.getCharacters().get(id).getCurrentState().checkValue(key, value);
                }
                return true;
            case "Map":
                return this.currentState.checkValue(key, value);
            default:
                return false;
        }
    }

    public boolean checkChildState(String idType, String id, String key, String value, boolean checkNontransient, boolean checkTransient) {
        switch (idType) {
            case "Room":
                return this.getRooms().get(id).currentState.checkValue(key, value);
            case "Character":
                if (checkTransient) {
                    return this.getCharacters().get(id).getTransientState().checkValue(key, value);

                }
                if (checkNontransient) {
                    return this.getCharacters().get(id).getCurrentState().checkValue(key, value);
                }
                return true;
            case "Map":
                return this.currentState.checkValue(key, value);
            default:
                return false;
        }
    }

    public Condition<NumberStateValue> getNumericChildState(String idType, String id, String key, boolean checkTransient, boolean checkNontransient) {
        switch (idType) {
            case "Room":
                return this.getRooms().get(id).currentState.getNumberStateValueValue(key);
            case "Character":
                if (checkTransient) {
                    return this.getCharacters().get(id).getTransientState().getNumberStateValueValue(key);
                }
                if (checkNontransient) {
                    return this.getCharacters().get(id).getCurrentState().getNumberStateValueValue(key);
                }
                return null;
            case "Map":
                return this.currentState.getNumberStateValueValue(key);
            default:
                return null;
        }
    }

    public Condition<Boolean> getBoolChildState(String idType, String id, String key, boolean checkTransient, boolean checkNontransient) {
        switch (idType) {
            case "Room":
                return this.getRooms().get(id).currentState.check(key);
            case "Character":
                if (checkTransient) {
                    return this.getCharacters().get(id).getTransientState().check(key);
                }
                if (checkNontransient) {
                    return this.getCharacters().get(id).getCurrentState().check(key);
                }
                return null;
            case "Map":
                return this.currentState.check(key);
            default:
                return null;
        }
    }

    public Condition<String> getStringChildState(String idType, String id, String key, boolean checkTransient, boolean checkNontransient) {
        switch (idType) {
            case "Room":
                return this.getRooms().get(id).currentState.getStringValue(key);
            case "Character":
                if (checkTransient) {
                    return this.getCharacters().get(id).getTransientState().getStringValue(key);
                }
                if (checkNontransient) {
                    return this.getCharacters().get(id).getCurrentState().getStringValue(key);
                }
                return null;
            case "Map":
                return this.currentState.getStringValue(key);
            default:
                return null;
        }
    }
}
