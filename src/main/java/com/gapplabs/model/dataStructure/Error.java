package com.gapplabs.model.dataStructure;

import java.util.ArrayList;
import java.util.HashSet;

public class Error extends GenericLexicalComponent {

    private String description;
    private HashSet<Integer> numberLines;

    private static HashSet<Error> errorHashSet;

    static{
        Error.errorHashSet = new HashSet<>();
    }

    public Error(String token, String lexema,int line, String description) {
        super(token, lexema);
        this.description = description;
        this.numberLines = new HashSet<>();
        this.numberLines.add(line);
    }

    public void addNumberLine(int numberLine) {
        this.numberLines.add(numberLine);
    }

    public static HashSet<Error> getErrorHashSet() {
        return (HashSet) Error.errorHashSet.clone();
    }
}
