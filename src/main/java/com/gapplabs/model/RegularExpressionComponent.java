package com.gapplabs.model;

public enum RegularExpressionComponent {

    SEP("(\\()|(\\))|(\\})|(\\{)|(,)|(;)"),
    AMR("(\\+)|(\\-)|(\\*)|(\\/)|(\\%)"),
    REL("(<=)|(>=)|(==)|(!=)|(<)|(>)"),
    ASG("="),
    RET("return"),
    IDE("^ISC.*R$"),
    ENT("^(-)?5\\d*5$"),
    DEC("^-?\\d+\\.5\\d*5$"),
    TDS("^(ent|dec|car)_$");
    private String regex;

    RegularExpressionComponent(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }
}
