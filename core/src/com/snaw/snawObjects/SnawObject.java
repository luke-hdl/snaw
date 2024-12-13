package com.snaw.snawObjects;

import com.snaw.characters.SnawCharacter;
import com.snaw.state.State;
import com.snaw.textureBank.TextureBank;

import java.util.Map;

public class SnawObject extends SnawCharacter {
    //Essentially a character, really.
    //Really, SnawCharacter should extend this?
    //I put it in its own zone because I think I'll need to refactor it later.
    public SnawObject(String id, State state, Map<String, TextureBank> textureBanks) {
        super(id, state, textureBanks);
    }
}
