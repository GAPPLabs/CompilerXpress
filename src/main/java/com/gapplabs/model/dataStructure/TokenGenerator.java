package com.gapplabs.model.dataStructure;

import java.util.HashMap;

public class TokenGenerator {

    private static HashMap<RegexEnum, Integer> token;

    public static String generateToken(RegexEnum regexEnum) {
        int id = token.get(regexEnum) + 1;
        token.put(regexEnum, id);
        return String.valueOf(regexEnum) + id;
    }
}
