package com.gapplabs.model.dataStructure;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

public class Simbol extends GenericLexicalComponent{
    private String description;

    public Simbol(String token, String lexema, int lineNumber, String description) {
        super(token, lexema, lineNumber);
        this.description = description;
    }
}
