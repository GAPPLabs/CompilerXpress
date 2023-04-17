package com.gapplabs.model.dataStructure;

public enum StructureConstant {

    // For SimbolStructure class and some of Errorstructure class
    LEXEMA(1), TOKEN(2), TYPE(3),
    // For the rest of ErrorStructure class
    LINE(3), DESCRIPTION(4);

    private int position;

    private StructureConstant(int position) {
    }

    public int getPosition() {
        return position;
    }
}
