package com.snaw.textureBank.bankedTextureVariants;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.snaw.exceptions.TextureIsInvalidatedException;
import com.snaw.generalBehaviors.genericBehaviors.GenericBehavior;
import com.snaw.textureBank.BankedTexture;

import java.util.LinkedList;
import java.util.List;

public class AutoProgressingTexture extends BankedTexture {
    // Note - autoprogressing textures don't support Behavior objects.
    // You'll need to use manually progressing textures.

    public enum WhenDone {
        LOOPS, //Returns to start.
        STOPS, //Continues to draw the final frame forever.
        ENDS,  //Stops drawing the texture.

        LOOPS_AND_CHAINS, //Returns to start. Chained behaviors that progress LOOPS AND CHAINS will chain each loop.
        STOPS_AND_CHAINS, //Continues to draw the final frame forever. Chained behaviors that progress ENDS AND CHAINS will chain once and once only, when the last frame is first progressed.
        ENDS_AND_CHAINS,  //Stops drawing the texture. Chained behaviors that progress ENDS AND CHAINS will chain once and once only, when the last frame is first progressed.
    }

    protected List<FileHandle> handles;
    protected String id; //This is the path to the folders.
    protected Texture currentTexture;
    protected List<Texture> previousTextures; //Removed once we re-render.

    protected int currentIndex;

    protected WhenDone whenDone;

    protected boolean stopProgressingIndefinitely = false;
    public AutoProgressingTexture(Texture clickZone, List<GenericBehavior> clickBehaviors, Texture placementTexture, List<FileHandle> handles, String id ) {
        this(clickZone, clickBehaviors, placementTexture, handles, id, WhenDone.ENDS_AND_CHAINS);
    }

    public AutoProgressingTexture(Texture clickZone, List<GenericBehavior> clickBehaviors, Texture placementTexture, List<FileHandle> handles, String id, WhenDone whenDone ) {
        super(clickZone, clickBehaviors, placementTexture, new LinkedList<>());
        this.handles = handles;
        this.id = id;
        currentIndex = 0;
        currentTexture = new Texture(handles.get(currentIndex));
        previousTextures = new LinkedList<>();
        this.whenDone = whenDone;
        cacheOffsetsForPlacementTexture();
    }

    public void act(){
        progress();
    }

    public boolean progress() {
        if ( stopProgressingIndefinitely ) {
            return whenDone == WhenDone.LOOPS || whenDone == WhenDone.STOPS || whenDone == WhenDone.ENDS; //chained behaviors stop chaining.
        }
        previousTextures.add(currentTexture);
        currentIndex++;
        if ( currentIndex == handles.size()){
            switch ( whenDone ) {
                case LOOPS_AND_CHAINS:
                case LOOPS:
                    currentIndex = 0;
                    break;
                case ENDS:
                case ENDS_AND_CHAINS:
                    throw new TextureIsInvalidatedException(true);
                case STOPS:
                case STOPS_AND_CHAINS:
                    stopProgressingIndefinitely = true;
                    break;
            }
            return true;
        }
        currentTexture = new Texture(handles.get(currentIndex));
        return whenDone == WhenDone.LOOPS || whenDone == WhenDone.STOPS || whenDone == WhenDone.ENDS;
    }

    public String getId() {
        return id;
    }

    @Override
    protected Texture getCurrentTexture() {
        return currentTexture;
    }

    public void render(SpriteBatch batch, int x, int y) {
        lastOffsets[0] = x;
        lastOffsets[1] = y;
        checkInputs();
        batch.draw(currentTexture, x, y);
        previousTextures.forEach( (e) -> e.dispose());
        previousTextures = new LinkedList<>();
    }

    @Override
    public void dispose() {
        currentTexture.dispose();
        previousTextures.forEach( (e) -> e.dispose());
        previousTextures = new LinkedList<>();
    }
}
