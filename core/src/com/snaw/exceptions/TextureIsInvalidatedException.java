package com.snaw.exceptions;

public class TextureIsInvalidatedException extends RuntimeException {
    protected boolean shouldChainIfNecessary;

    public TextureIsInvalidatedException( ){
        this.shouldChainIfNecessary = false;
    }
    public TextureIsInvalidatedException( boolean shouldChainIfNecessary ) {
        this.shouldChainIfNecessary = shouldChainIfNecessary;
    }

    public boolean shouldChain(){
        return shouldChainIfNecessary;
    }
}
