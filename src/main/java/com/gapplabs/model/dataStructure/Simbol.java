package com.gapplabs.model.dataStructure;


import java.util.HashMap;

public class Simbol extends GenericLexicalComponent{

    private String description;

    public Simbol(String token, String lexema, int lineNumber, String description) {
        super(token, lexema);
        this.description = description;
    }

}
