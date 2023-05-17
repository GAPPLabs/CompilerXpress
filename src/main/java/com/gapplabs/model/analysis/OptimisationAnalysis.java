package com.gapplabs.model.analysis;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OptimisationAnalysis {
    
    private final String expressionFunction;
    private final String expressionSubFunction;
    
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
                                
                                ISC111 = ISC333 ( 555 ) ;
                                """;

    public OptimisationAnalysis() {
        this.expressionFunction = "ISC\\d{3}\\s*\\(.*?\\)\\s*\\{(?s).*\\}";
        this.expressionSubFunction = "ISC\\d{3}\\s*\\(.*\\)";
    }
    
    private void functionAnalysis () {
        Pattern pattern = Pattern.compile(expressionFunction);
        Matcher matcher = pattern.matcher(test);
        
        System.err.println("RUNNN");
        while (matcher.find()) {
            System.out.println("Función encontrada: " + matcher.group() +
                    " - Inicio: " + matcher.start() +
                    " ~ Fin: " + matcher.end());
        }
    }
    
    private void subFunctionAnalysis() {
        Pattern pattern = Pattern.compile(expressionSubFunction);
        Matcher matcher = pattern.matcher(test);
        
        System.err.println("RUNNN");
        while (matcher.find()) {
            System.out.println("Función encontrada: " + matcher.group() +
                    " - Inicio: " + matcher.start() +
                    " ~ Fin: " + matcher.end());
        }
    }
    
    public static void main(String[] args) {
        new OptimisationAnalysis().functionAnalysis();
        new OptimisationAnalysis().subFunctionAnalysis();
    }
}
