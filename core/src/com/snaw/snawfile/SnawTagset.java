package com.snaw.snawfile;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SnawTagset {
    private SnawTagset parentTagset;
    private String setTag;
    private Map<String, List<SnawTagset>> childTagsets = new HashMap<>();
    private Map<String, List<SnawTagElement>> elements = new HashMap<>();

    public SnawTagset(SnawTagset parentTagset, String setTag) {
        this.parentTagset = parentTagset;
        this.setTag = setTag;
        if (parentTagset != null) {
            parentTagset.addChild(setTag, this);
        }
    }

    private void addChild(String childTag, SnawTagset child) {
        if (!childTagsets.containsKey(childTag)) {
            childTagsets.put(childTag, new LinkedList<>());
        }
        childTagsets.get(childTag).add(child);
    }

    public void addElements(SnawTagElement element) {
        if (!elements.containsKey(element.tag)) {
            elements.put(element.tag, new LinkedList<>());
        }
        elements.get(element.tag).add(element);
    }

    public List<SnawTagset> getChildren(String tag) {
        return childTagsets.containsKey(tag) ? childTagsets.get(tag) : new LinkedList<>();
    }

    public List<SnawTagElement> getElements(String tag) {
        return elements.get(tag) != null ? elements.get(tag) : new LinkedList<>();
    }

    public String getOneOffTagValue(String tag) {
        return this.getElements(tag) != null && this.getElements(tag).size() == 1 ? this.getElements(tag).get(0).getValue() : null;
    }

    public SnawTagset getOneOffTagset(String tag) {
        return this.getChildren(tag) != null && this.getChildren(tag).size() == 1 ? this.getChildren(tag).get(0) : null;
    }
}
