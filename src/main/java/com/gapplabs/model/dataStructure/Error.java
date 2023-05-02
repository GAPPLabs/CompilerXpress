package com.gapplabs.model.dataStructure;

import java.util.ArrayList;
import java.util.HashSet;

public class Error extends GenericLexicalComponent {

    private String description;
    private HashSet<Integer> numberLines;

    public Error(String token, String lexema, String description) {
        super(token, lexema);
        this.description = description;
    }

    public void addNumberLine(int numberLine) {
        this.numberLines.add(numberLine);
    }


}
