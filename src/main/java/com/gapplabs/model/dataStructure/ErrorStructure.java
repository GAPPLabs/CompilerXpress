package com.gapplabs.model.dataStructure;

public class ErrorStructure extends Common2{


    public ErrorStructure() {
        super(new String[]{
                String.valueOf(StructureConstant.LEXEMA),
                String.valueOf(StructureConstant.TOKEN),
                String.valueOf(StructureConstant.LINE),
                String.valueOf(StructureConstant.DESCRIPTION),
        });
    }
}
