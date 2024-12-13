package com.snaw.builders;

import com.snaw.builders.managers.BuilderManager;
import com.snaw.snawfile.SnawTagset;

public abstract class Builder<T> {
    public BuilderManager manager;

    public Builder(BuilderManager manager) {
        this.manager = manager;
    }

    public abstract T build(SnawTagset tagset);
}
