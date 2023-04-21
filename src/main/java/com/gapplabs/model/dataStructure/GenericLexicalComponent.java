package com.gapplabs.model.dataStructure;

public class GenericLexicalComponent {

    private String token;
    private String lexema;
    private int lineNumber;

    public GenericLexicalComponent(String token, String lexema, int lineNumber) {
        this.token = token;
        this.lexema = lexema;
        this.lineNumber = lineNumber;
    }
}
