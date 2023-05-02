package com.gapplabs.model.dataStructure;

public enum RegexEnum {
    SEP("(\\()|(\\))|(\\})|(\\{)|(,)|(;)"),
    AMR("(\\+)|(\\-)|(\\*)|(\\/)|(\\%)"),
    REL("(<=)|(>=)|(==)|(!=)|(<)|(>)"),
    ASG("="),
    RET("return"),
    IDE("^ISC.*R$"),
    ENT("^(-)?5\\d*5$"),
    DEC("^-?\\d+\\.5\\d*5$"),
    TDS("^(ent|dec|car)_$"),
    ERROR(""),

    ERRORSEMAN("");

    private String regex;

    RegexEnum(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }
}
