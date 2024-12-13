package com.snaw.textureBank.behaviors;

import com.snaw.coreState.GameState;

public class CheckCharacterStateStringValueTextureBehavior extends BankedTextureBehavior {
    String characterId;
    String key;
    String value;

    public CheckCharacterStateStringValueTextureBehavior(String characterId, String key, String value) {
        this.characterId = characterId;
        this.key = key;
        this.value = value;
    }

    @Override
    public boolean actAndFilter() {
        return !GameState.getMap().getCharacters().get(characterId).getCurrentState().checkValue(key, value);
    }
}
