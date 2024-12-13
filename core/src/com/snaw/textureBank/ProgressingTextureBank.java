package com.snaw.textureBank;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.snaw.exceptions.TextureIsInvalidatedException;
import com.snaw.gameObjects.StateHoldingObject;
import com.snaw.generalBehaviors.genericBehaviors.GenericBehavior;
import com.snaw.state.State;
import com.snaw.textureBank.bankedTextureVariants.AutoProgressingTexture;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ProgressingTextureBank extends TextureBank implements StateHoldingObject {
    State initialState;
    State currentState;

    List<GenericBehavior> progressionBehavior;
    protected List<BankedTexture> invalidatedTextures;
    Map<String, AutoProgressingTexture> autoProgressingTextures = new HashMap<>();

    public ProgressingTextureBank(String renderingLevel) {
        super(renderingLevel);
        this.progressionBehavior = new LinkedList<>();
        this.invalidatedTextures = new LinkedList<>();
    }

    public ProgressingTextureBank(String renderingLevel, State state) {
        super(renderingLevel);
        this.initialState = state;
        this.progressionBehavior = new LinkedList<>();
    }

    public void setProgressionBehavior(List<GenericBehavior> progressionBehavior) {
        this.progressionBehavior = progressionBehavior;
    }

    public void setProgressingTextures(List<AutoProgressingTexture> textures) {
        for (AutoProgressingTexture texture : textures) {
            autoProgressingTextures.put(texture.getId(), texture);
        }
    }

    @Override
    public void render(State state, SpriteBatch batch, int x, int y) {
        int[] offsetsSelf = currentState == null ? new int[]{0, 0} : currentState.getStateBasedOffsets();
        int[] offsetsOther = state == null ? new int[]{0, 0} : state.getStateBasedOffsets();
        textureList.forEach(e -> e.render(batch, x + offsetsSelf[0] + offsetsOther[0], y + offsetsSelf[1] + offsetsOther[1]));
        autoProgressingTextures.values().forEach(e -> e.render(batch, x, y));
        disposeInvalidatedTextures();
    }

    private void disposeInvalidatedTextures() {
        if (!invalidatedTextures.isEmpty()) {
            invalidatedTextures.forEach((e) -> e.dispose());
            invalidatedTextures = new LinkedList<>();
        }
    }

    @Override
    public void performAnyActions() {
        for (GenericBehavior behavior : progressionBehavior) {
            if (!behavior.actAndFilter()) {
                break;
            }
        }
    }

    public boolean progress(String textureId) {
        try {
            if (autoProgressingTextures.containsKey(textureId)) {
                return autoProgressingTextures.get(textureId).progress();
            }
        } catch (TextureIsInvalidatedException exception) {
            invalidatedTextures.add(autoProgressingTextures.get(textureId));
            autoProgressingTextures.remove(textureId);
            return exception.shouldChain();
        }
        return false;
    }

    public void initialize() {
        this.currentState = initialState.copy();
        currentState.setOwner(this);
    }

    @Override
    public State getState() {
        return currentState;
    }

    public void setState(State state) {
        this.currentState = state;
    }
}
