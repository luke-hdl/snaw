package com.snaw.map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.snaw.buttons.ButtonOverlay;
import com.snaw.coreState.GameState;
import com.snaw.gameObjects.StateHoldingObject;
import com.snaw.generalBehaviors.genericBehaviors.GenericBehavior;
import com.snaw.state.Condition;
import com.snaw.state.State;
import com.snaw.textureBank.TextureBank;
import com.snaw.uielements.UIElement;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Room implements StateHoldingObject {
    String roomId;
    State initialState;
    State currentState;
    List<GenericBehavior> behaviorStream;

    List<ButtonOverlay> buttons;
    Map<String, TextureBank> textureBanks;
    List<TextureBank> uiTextures; //drawn at the end, passed from the UI Elements
    ParentRoomRelationship relationshipWhereThisIsChild;
    List<ParentRoomRelationship> relationshipsWhereThisIsParent;
    private List<String> uiElementIds;

    public Room(String roomId, List<GenericBehavior> behaviors, List<ButtonOverlay> overlays, Map<String, TextureBank> textureBanks, State state, List<String> elementIds) {
        this.roomId = roomId;
        this.initialState = state;
        if (this.initialState == null) {
            this.initialState = new State();
        }
        behaviorStream = behaviors;
        buttons = overlays;
        this.textureBanks = textureBanks;
        relationshipsWhereThisIsParent = new LinkedList<>();
        uiElementIds = elementIds;
    }

    @Override
    public State getState() {
        return currentState;
    }

    public void processUiElements() {
        List<UIElement> elements = new LinkedList<>();
        uiTextures = new LinkedList<>();
        for (String id : uiElementIds) {
            elements.add(GameState.getMap().getUiElements().get(id));
        }

        for (UIElement element : elements) {
            for (ButtonOverlay overlay : element.getButtonOverlayList()) {
                buttons.add(overlay.getCopy());
            }
            uiTextures.add(element.getTextureBank());
        }
    }

    public List<TextureBank> getUiTextures(String requiredRenderingLevel) {
        if (uiTextures == null) {
            processUiElements();
        }
        LinkedList<TextureBank> renderingTextures = new LinkedList<>();
        for (TextureBank bank : uiTextures) {
            if (bank.getRenderingLevel().equals(requiredRenderingLevel)) {
                renderingTextures.add(bank);
            }
        }
        return renderingTextures;
    }

    public List<TextureBank> getUiTextures() {
        if (uiTextures == null) {
            processUiElements();
        }
        return uiTextures;
    }

    public void processInactive() {
        processBehaviors();
    }

    public void processActive(SpriteBatch batch, int x, int y) {
        //Processes for the active room and its subrooms.
        for (String renderingLevel : GameState.getMap().renderingLevels) {
            render(batch, x, y, renderingLevel);
        }
        processBehaviors();
        buttons.forEach(e -> e.runEvents());
    }

    public void renderButtonOverlay(ButtonOverlay overlay, SpriteBatch batch, int x, int y, String renderingLevel) {
        int ox = overlay.getRenderingTextureOffset()[0];
        int oy = overlay.getRenderingTextureOffset()[1];
        overlay.render(batch, x, y, renderingLevel);
        if (overlay.getRenderingTextureBank().getRenderingLevel().equals(renderingLevel)) {
            overlay.getRenderingTextureBank().render(null, batch, x + ox, oy + y);
        }

    }

    public void render(SpriteBatch batch, int x, int y, String currentRenderingLevel) {
        if (textureBanks.containsKey(currentRenderingLevel)) {
            textureBanks.get(currentRenderingLevel).render(currentState, batch, x, y);
        }
        int[] offsetsSelf = currentState == null ? new int[]{0, 0} : currentState.getStateBasedOffsets();
        GameState.getMap().getCharacters(this.roomId).forEach(e -> e.render(batch, x, y, currentRenderingLevel));
        for (ParentRoomRelationship relationship : relationshipsWhereThisIsParent) {
            //Children don't see parent states, so we manually add any offsets.
            relationship.getChild().render(batch, relationship.getStartX() + offsetsSelf[0], relationship.getStartY() - 1 + offsetsSelf[1], currentRenderingLevel);
        }
        buttons.forEach(e ->
                renderButtonOverlay(e, batch, x + offsetsSelf[0], y + offsetsSelf[1], currentRenderingLevel));
        getUiTextures(currentRenderingLevel).forEach(e -> e.render(currentState, batch, x, y));
    }

    public void processBehaviors() {
        behaviorStream.forEach(e -> e.act());

        for (TextureBank textureBank : textureBanks.values()) {
            textureBank.performAnyActions();
        }
    }

    public List<ButtonOverlay> getButtons() {
        return buttons;
    }

    public void initialize() {
        currentState = initialState.copy();
        currentState.setOwner(this);
    }

    public void makeActiveRoom() {
        //NoOp.
    }

    public void makeInactiveRoom() {
        //buttons.forEach( e -> e.clearCachedData() );
    }

    public int[] getDimensions() {
        for (TextureBank textureBank : textureBanks.values()) {
            return textureBank.getDimensions();
        }
        return new int[]{0, 0};
    }

    public ParentRoomRelationship getRelationshipWhereThisIsChild() {
        return relationshipWhereThisIsChild;
    }

    public void setRelationshipWhereThisIsChild(ParentRoomRelationship relationship) {
        if (this.relationshipWhereThisIsChild != null) {
            throw new RuntimeException("Parent relationships can't be changed.");
        }
        relationship.getParent().addChild(relationshipWhereThisIsChild);
        this.relationshipWhereThisIsChild = relationship;
    }

    public void setRelationshipWhereThisIsChild(ParentRoomRelationship relationship, Room parentRoom) {
        if (this.relationshipWhereThisIsChild != null) {
            throw new RuntimeException("Parent relationships can't be changed.");
        }
        if (!parentRoom.roomId.equals(relationship.parentId)) {
            throw new RuntimeException("Parent identified incorrectly.");
        }
        this.relationshipWhereThisIsChild = relationship;
        parentRoom.addChild(relationshipWhereThisIsChild);
    }

    public List<ParentRoomRelationship> getRelationshipsWhereThisIsParent() {
        return relationshipsWhereThisIsParent;
    }

    private void addChild(ParentRoomRelationship child) {
        if (child != null) {
            this.relationshipsWhereThisIsParent.add(child);
        }
    }

    public Condition<Boolean> getStateBoolValue(String checkKey) {
        return currentState.check(checkKey);
    }

    public void toggleStateBoolValue(String key) {
        currentState.toggle(key);
    }
}
