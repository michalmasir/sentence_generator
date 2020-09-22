package com.gooddata.sentencegenerator.engine.dto;

public enum WordCategory {
    NOUN("N"),
    VERB("V"),
    ADJECTIVE("A");

    private final String label;

    WordCategory(String label) {
        this.label = label;
    }

    public String getLabel(){
        return label;
    }
}
