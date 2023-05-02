package com.gapplabs.model.dataStructure;

import java.util.Objects;

public class GenericLexicalComponent {
    private String token;
    private String lexema;

    public String getToken() {
        return token;
    }

    public String getLexema() {
        return lexema;
    }

    public GenericLexicalComponent(String token, String lexema) {
        this.token = token;
        this.lexema = lexema;
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
}
