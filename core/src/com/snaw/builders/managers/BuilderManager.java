package com.snaw.builders.managers;

import com.snaw.builders.UnresolvedReference;
import com.snaw.menu.Menu;
import com.snaw.snawfile.SnawTagset;

import java.util.LinkedList;
import java.util.List;

public abstract class BuilderManager<T> {
    public String defaultRenderingLevel;
    public Menu mainMenu;
    List<UnresolvedReference> unresolvedReferences;
    T result;

    public void setUpForBuild() {
        unresolvedReferences = new LinkedList<>();
    }

    public abstract T build(SnawTagset tagset);

    public void resolveReferences() {
        int lastSize = -1;
        while (unresolvedReferences.size() != lastSize) {
            int i = 0;
            while (i < unresolvedReferences.size()) {
                UnresolvedReference reference = unresolvedReferences.get(i);
                if (reference.resolveReference() != null) {
                    unresolvedReferences.remove(i);
                } else {
                    i++;
                }
            }
        }
        if (unresolvedReferences.size() > 0) {
            throw new RuntimeException("Unresolved References Identified.");
        }
    }

}
