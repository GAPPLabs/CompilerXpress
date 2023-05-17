package com.gapplabs.model.analysis;

import com.gapplabs.model.analysis.util.Functions;
import java.util.Arrays;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OptimisationAnalysis {
    
    private final String regexFunction;
    private final String regexCallFunction;
    
    private final String test = """
                                ent_ ISC111 , ISC112 , ISC113 ;
                                dec_ ISC211 ;
                                car_ ISC311 ;
                                
                                ISC111 = ISC112 * ISC113 ; 
                                ISC211 = ISC112 ;
                                
                                ent_ ISC333 ( ent_ ISC914 )
                                {
                                ISC914 = 5235 * ISC113 ;
                                return ISC914 ;
                                }
                                
                                ent_ ISC334 ( ent_ ISC914 )
                                {
                                ISC914 = ISC333 ( ISC914 , ISC913 , ISC916 ) ;
                                return ISC914 ;
                                }
                                
                                ent_ ISC335 ( ent_ ISC914 )
                                {
                                ISC914 = 5235 * ISC113 ;
                                return ISC914 ;
                                }
                                
                                ent_ ISC336 ( ent_ ISC914 )
                                {
                                ISC914 = ISC335 ( ISC914 , ISC913 , ISC916 ) ;
                                return ISC914 ;
                                }
                                
                                ISC111 = ISC333 ( 555 ) ;
                                """;

    public OptimisationAnalysis() {
        this.regexFunction = "(ent_|dec_|car_)\\s*(ISC\\d{3})\\s*\\((.*?)\\)\\s*\\{((?s).*?)\\}";
        this.regexCallFunction = "\\s*(ISC\\d{3})\\s*\\((.*?)\\)\\s*;";
    }
    
    private Functions [] chechFunctionOptimizer (String text) {
        Pattern patternFunction = Pattern.compile(regexFunction);
        Matcher matcherFunction = patternFunction.matcher(text);
        
        while (matcherFunction.find()) {
            Pattern patternCall = Pattern.compile(regexCallFunction);
            Matcher matcherCall = patternCall.matcher(matcherFunction.group());
            while(matcherCall.find()) {
                Functions fuctionCall = decomposingFuction(matcherCall, false);
                Functions functionRemove = searchFunction(text, fuctionCall.getIdentify());
                return new Functions[] {fuctionCall, functionRemove};
            }
        }
        return null;
    }
    
    private Functions searchFunction (String text, String identify) {
        Pattern patternFunction = Pattern.compile(regexFunction);
        Matcher matcherFunction = patternFunction.matcher(text);
        
        while (matcherFunction.find()) {
            if (matcherFunction.group(2).equals(identify)) {
                return decomposingFuction(matcherFunction, true);
            }
        }
        return null;
    }
    
    private Functions decomposingFuction(Matcher matcher, boolean function) {
        String identify = matcher.group(function ? 2 : 1);
        String [] params = matcher.group(function ? 3 : 2)
                .replaceAll("(ent_|dec_|car_)", "")
                .split("\\s+,\\s+|\\s+");
        params = Arrays.stream(params).filter(str -> !str.isEmpty()).toArray(String[]::new);
        if (function) {
            String body = matcher.group(4);
            int start = matcher.start();
            int end = matcher.end();
            System.out.println("Identify: " + identify);
            System.out.println("Params: " + Arrays.toString(params));
            System.out.println("Body: " + body);
            System.out.println("Start position: " + start);
            System.out.println("End position: " + end);
            return new Functions(identify, params, body, start, end, function, matcher);
        } else {
            System.out.println("Identify: " + identify);
            System.out.println("Params: " + Arrays.toString(params));
            return new Functions(identify, params, null, -1, -1, function, matcher);
        }
    }
    
    public static void main(String[] args) {
        OptimisationAnalysis op = new OptimisationAnalysis();
        op.chechFunctionOptimizer(op.test);
    }
}