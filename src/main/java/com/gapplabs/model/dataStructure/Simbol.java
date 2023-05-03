package com.gapplabs.model.dataStructure;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

public class Simbol extends GenericLexicalComponent{

    private String description;
    private static HashSet<Simbol> simbolHashSet;
    static{
        Simbol.simbolHashSet = new HashSet<>();
    }

    public Simbol(String token, String lexema, int lineNumber, String description) {
        super(token, lexema);
        this.description = description;
        Simbol.simbolHashSet.add(this);
    }
    public static HashSet<Simbol> getSimbolHashSet(){
        return (HashSet) Simbol.simbolHashSet.clone();
    }
}
