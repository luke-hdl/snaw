package com.snaw.builders.map;

import com.snaw.builders.Builder;
import com.snaw.builders.characters.CharacterBuilder;
import com.snaw.builders.managers.BuilderManager;
import com.snaw.builders.shared.StateBuilder;
import com.snaw.builders.shared.TextureBankBuilder;
import com.snaw.buttons.ButtonOverlay;
import com.snaw.buttons.ButtonTemplate;
import com.snaw.characters.SnawCharacter;
import com.snaw.generalBehaviors.genericBehaviors.GenericBehavior;
import com.snaw.map.GameMap;
import com.snaw.map.ParentRoomRelationship;
import com.snaw.map.Room;
import com.snaw.snawfile.SnawTagElement;
import com.snaw.snawfile.SnawTagset;
import com.snaw.state.State;
import com.snaw.textureBank.TextureBank;
import com.snaw.uielements.UIElement;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GameMapBuilder extends Builder<GameMap> {

    public GameMapBuilder(BuilderManager manager) {
        super(manager);
    }

    @Override
    public GameMap build(SnawTagset tagset) {
        String id = tagset.getOneOffTagValue("ID");
        // TODO - Events.

        State state = new State();
        SnawTagset mapStateTags = tagset.getOneOffTagset("STATE");
        if (mapStateTags != null) {
            state = new StateBuilder(manager).build(mapStateTags);
        }

        List<String> renderingLevels = new LinkedList<>();
        for (SnawTagElement renderingLevel : tagset.getOneOffTagset("RENDERING LEVELS").getElements("LEVEL")) {
            if (renderingLevel.getValue().contains("{")) {
                renderingLevels.add(renderingLevel.getValue().substring(0, renderingLevel.getValue().indexOf('{')));
                if (renderingLevel.getValue().endsWith("{Default}")) {
                    manager.defaultRenderingLevel = renderingLevel.getValue().substring(0, renderingLevel.getValue().indexOf('{'));
                }
            } else {
                renderingLevels.add(renderingLevel.getValue());
            }
        }
        SnawTagset roomsTagset = tagset.getOneOffTagset("ROOMS");
        Map<String, Room> rooms = new HashMap<>();
        Room office = null;
        for (SnawTagset room : roomsTagset.getChildren("ROOM")) {
            // TODO - move this to RoomBuilder.
            String roomId = room.getOneOffTagValue("ID");
            String isOffice = room.getOneOffTagValue("IS OFFICE");
            List<GenericBehavior> roomMapBehaviors = new LinkedList<>();
            SnawTagset mapBehaviors = tagset.getOneOffTagset("MAP BEHAVIORS");
            if (mapBehaviors != null) {
                roomMapBehaviors.addAll(new MapBehaviorBuilder(manager).build(mapBehaviors));
            }

            State roomState = null;
            SnawTagset stateTags = room.getOneOffTagset("STATE");
            if (stateTags != null) {
                roomState = new StateBuilder(manager).build(stateTags);
            }

            Map<String, TextureBank> roomTextureBanks = new HashMap<>();
            SnawTagset textureBanks = room.getOneOffTagset("TEXTURE BANKS");
            if (textureBanks != null) {
                for (SnawTagset textureBankSet : textureBanks.getChildren("TEXTURE BANK")) {
                    TextureBank bank = new TextureBankBuilder(manager).build(textureBankSet);
                    roomTextureBanks.put(bank.getRenderingLevel(), bank);
                }
            }

            List<ButtonOverlay> overlays = new LinkedList<>();
            SnawTagset overlaysSet = room.getOneOffTagset("BUTTON OVERLAYS");
            if (overlaysSet != null) {
                for (SnawTagset childTagset : overlaysSet.getChildren("BUTTON OVERLAY")) {
                    overlays.add(new ButtonOverlayBuilder(manager).build(childTagset));
                }
            }

            List<String> uiElementIds = new LinkedList<>();

            SnawTagset uiElementsSet = room.getOneOffTagset("UI ELEMENTS");
            if (uiElementsSet != null) {
                for (SnawTagElement childTagset : uiElementsSet.getElements("UI ELEMENT")) {
                    uiElementIds.add(childTagset.getValue());
                }
            }

            Room foundRoom = new Room(roomId, roomMapBehaviors, overlays, roomTextureBanks, roomState, uiElementIds);

            rooms.put(roomId, foundRoom);
            if (isOffice != null && isOffice.equals("yes")) {
                office = foundRoom;
            }
        }

        SnawTagset parentRoomRelationshipTagset = tagset.getOneOffTagset("PARENT ROOM RELATIONSHIPS");
        if (parentRoomRelationshipTagset != null) {
            for (SnawTagset relationshipTagset : parentRoomRelationshipTagset.getChildren("PARENT ROOM RELATIONSHIP")) {
                ParentRoomRelationship relationship = new ParentRoomRelationshipBuilder(manager).build(relationshipTagset);
                rooms.get(relationship.getChildId()).setRelationshipWhereThisIsChild(relationship, rooms.get(relationship.getParentId()));
            }
        }

        SnawTagset characterTagset = tagset.getOneOffTagset("CHARACTERS");
        Map<String, SnawCharacter> characters = new HashMap<>();
        for (SnawTagset character : characterTagset.getChildren("CHARACTER")) {
            SnawCharacter builtCharacter = new CharacterBuilder(manager).build(character);
            characters.put(builtCharacter.getId(), builtCharacter);
        }

        SnawTagset buttonTagset = tagset.getOneOffTagset("BUTTONS");
        Map<String, ButtonTemplate> buttons = new HashMap<>();
        for (SnawTagset button : buttonTagset.getChildren("BUTTON")) {
            ButtonTemplate builtButton = new ButtonBuilder(manager).build(button);
            buttons.put(builtButton.getId(), builtButton);
        }

        SnawTagset uiElementTagset = tagset.getOneOffTagset("UI ELEMENTS");
        Map<String, UIElement> elements = new HashMap<>();
        for (SnawTagset element : uiElementTagset.getChildren("UI ELEMENT")) {
            UIElement builtElement = new UIElementBuilder(manager).build(element);
            elements.put(builtElement.getId(), builtElement);
        }


        return new GameMap(id, renderingLevels, state, rooms, buttons, elements, characters, office);
    }
}
