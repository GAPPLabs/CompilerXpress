package com.gapplabs.model.dataStructure;

import java.util.HashMap;

public class TokenGenerator {

    private static HashMap<String, Integer> token;
    static {
        TokenGenerator.token = new HashMap<>();
        System.out.println("SE EJECUTA BLOQUE ESTÁTICO");
        for (RegexEnum regexEnum:RegexEnum.values()) {
            TokenGenerator.token.put(String.valueOf(regexEnum), 0);
        }
    }

    public static String generateToken(RegexEnum regexEnum) {
        int id = token.get(String.valueOf(regexEnum)) + 1;
        token.put(String.valueOf(regexEnum), id);
        return String.valueOf(regexEnum) + id;
    }
}
