package com.snaw.builders;

import java.util.Map;
import java.util.function.Consumer;

@Deprecated
//eventually I'll remove/refactor this lol
public class UnresolvedReference<T> {
    String id;
    Consumer<T> parentSetter;
    Map<String, T> mapToCheck; //pointer -- maintained elsewhere.

    public UnresolvedReference(String id, Consumer<T> parentSetter, Map<String, T> mapToCheck) {
        this.id = id;
        this.parentSetter = parentSetter;
        this.mapToCheck = mapToCheck;
    }

    public T resolveReference() {
        T reference = mapToCheck.get(id);
        if (reference != null) {
            parentSetter.accept(reference);
        }
        return reference;
    }
}
