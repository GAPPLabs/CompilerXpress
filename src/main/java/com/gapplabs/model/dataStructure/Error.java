package com.gapplabs.model.dataStructure;

import java.util.LinkedHashSet;

public class Error extends GenericLexicalComponent{
    private String description;

    public Error(String token,  String lexema,int lineNumber, String description) {
        super(token, lexema, lineNumber);
        this.description = description;
    }
}
