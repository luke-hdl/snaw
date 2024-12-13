package com.snaw.builders.managers;

import com.snaw.builders.builtFile.BuiltFile;
import com.snaw.builders.shared.FileBuilder;
import com.snaw.logging.LogLevel;
import com.snaw.logging.Logger;
import com.snaw.snawfile.SnawTagset;

public class FileBuilderManager extends BuilderManager<BuiltFile> {
    @Override
    public BuiltFile build(SnawTagset tagset) {
        result = new FileBuilder(this).build(tagset);
        Logger.log("Successfully built file.", LogLevel.DEBUG_LOAD);
        return result;
    }

}
