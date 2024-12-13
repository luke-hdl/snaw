package com.snaw.builders.characters;

import com.snaw.builders.Builder;
import com.snaw.builders.managers.BuilderManager;
import com.snaw.builders.shared.StateBuilder;
import com.snaw.builders.shared.TextureBankBuilder;
import com.snaw.characters.CharacterBehaviorFactory;
import com.snaw.characters.SnawCharacter;
import com.snaw.generalBehaviors.genericBehaviors.GenericBehavior;
import com.snaw.snawfile.SnawTagElement;
import com.snaw.snawfile.SnawTagset;
import com.snaw.state.State;
import com.snaw.textureBank.TextureBank;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CharacterBuilder extends Builder<SnawCharacter> {
    public CharacterBuilder(BuilderManager manager) {
        super(manager);
    }

    @Override
    public SnawCharacter build(SnawTagset tagset) {
        String id = tagset.getOneOffTagValue("ID");

        State state = new State();
        SnawTagset stateTagset = tagset.getOneOffTagset("STATE");
        if (stateTagset != null) {
            state = new StateBuilder(manager).build(stateTagset);
        }

        Map<String, TextureBank> characterTextureBanks = new HashMap<>();
        SnawTagset textureBanks = tagset.getOneOffTagset("TEXTURE BANKS");
        if (textureBanks != null) {
            for (SnawTagset textureBankSet : textureBanks.getChildren("TEXTURE BANK")) {
                TextureBank bank = new TextureBankBuilder(manager).build(textureBankSet);
                characterTextureBanks.put(bank.getRenderingLevel(), bank);
            }
        }

        SnawCharacter character = new SnawCharacter(id, state, characterTextureBanks);

        SnawTagset behaviorsTagset = tagset.getOneOffTagset("BEHAVIORS");
        if (behaviorsTagset != null) {
            List<GenericBehavior> behaviorList = new LinkedList<>();
            for (SnawTagElement element : behaviorsTagset.getElements("BEHAVIOR")) {
                behaviorList.add(CharacterBehaviorFactory.getCharacterBehaviorFromTag(character, element.getValue()));
            }
            character.setBehaviorList(behaviorList);
        }

        return character;
    }
}
