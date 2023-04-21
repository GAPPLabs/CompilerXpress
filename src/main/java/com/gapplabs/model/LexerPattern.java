package com.gapplabs.model;

public class LexerPattern {
    private String prefix;
    private String regularExpression;
    private int counter;

    public String getPrefix() {
        return prefix;
    }

    public String getRegularExpression() {
        return regularExpression;
    }

    public int getCounter() {
        return counter;
    }

    public LexerPattern(String prefix, String regularExpression) {
        this.prefix = prefix;
        this.regularExpression = regularExpression;
        this.counter = 0;
    }
}
