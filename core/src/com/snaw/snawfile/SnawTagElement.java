package com.snaw.snawfile;

public class SnawTagElement {
    String tag;
    private String value;

    public SnawTagElement(String tag, String value) {
        this.tag = tag;
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
