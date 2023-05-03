package com.gapplabs.model.dataStructure;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Objects;

public class GenericLexicalComponent {
    private String token;
    private String lexema;
//    private static ArrayList<GenericLexicalComponent> genericLexicalComponentArrayList;
    private static LinkedHashMap<String, GenericLexicalComponent> genericLexicalComponentLinkedHashMap;
    static{
        GenericLexicalComponent.genericLexicalComponentLinkedHashMap = new LinkedHashMap<>();
    }

    public String getToken() {
        return token;
    }

    public String getLexema() {
        return lexema;
    }

    public GenericLexicalComponent(String token, String lexema) {
        this.token = token;
        this.lexema = lexema;
        GenericLexicalComponent.genericLexicalComponentLinkedHashMap.put(this.lexema, this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenericLexicalComponent that = (GenericLexicalComponent) o;
        return Objects.equals(lexema, that.lexema);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lexema);
    }

    public static LinkedHashMap<String, GenericLexicalComponent> getGenericLexicalComponentArrayList(){
        return (LinkedHashMap) GenericLexicalComponent.genericLexicalComponentLinkedHashMap.clone();
    }
}
