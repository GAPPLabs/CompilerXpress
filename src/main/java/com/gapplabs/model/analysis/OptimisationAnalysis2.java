package com.gapplabs.model.analysis;

import com.gapplabs.model.analysis.util.Functions2;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OptimisationAnalysis2 {

    private final String regexFunction;
    private final String regexCallFunction;
    private final String regexReturnFunction;
    private final String regexFirstCode;

    private String test = """
           
            ent_ ISC111 , ISC112 , ISC113 ;
            ent_ ISC211 , ISC212 ;
                                            
            ISC211 = 0 ; 
            ISC212 = 5 ;
                                            
            ent_ ISC400 ( ent_ ISC914 , ent_ ISC915 )
            {
            dec_ ISC311 ;
            ISC311 = ISC211 % ISC212 ;
            ISC111 = ISC914 + ISC915 ;
            ISC311 = ISC111 + ISC311 ;
            return ISC111 ;
            }
                                            
            ent_ ISC500 ( ent_ ISC213 , ent_ ISC214 )
            {
            ISC112 = ISC400 ( ISC213 , ISC214 ) ;                                
            ISC110
            return ISC110 ;
            }       
                          
            """;

    public OptimisationAnalysis2() {
        this.regexFunction = "(ent_|dec_|car_)\\s*(ISC\\d{3})\\s*\\((.*?)\\)\\s*\\{((?s).*?)\\}";
        this.regexCallFunction = "\\s*(ISC\\d{3})\\s*\\((.*?)\\)\\s*;";
        this.regexReturnFunction = "(.*)\n\\s*(return ISC\\d{3} ;)";
        this.regexFirstCode = "(=\\s*)(.* ;)";
    }
    
    public String compileOptimizer(String text) {
        Functions2[] functions = chechFunctionOptimizer(text);
        
        while(functions != null) {
            text = replaceText(functions, text, replaceBody(functions));
            functions = chechFunctionOptimizer(text);
        }
        
        return text;
    }

    private Functions2[] chechFunctionOptimizer(String text) {
        Pattern patternFunction = Pattern.compile(regexFunction);
        Matcher matcherFunction = patternFunction.matcher(text);

        while (matcherFunction.find()) {
            Pattern patternCall = Pattern.compile(regexCallFunction);
            Matcher matcherCall = patternCall.matcher(matcherFunction.group());
            while (matcherCall.find()) {
                Functions2 functionBase = decomposingFuction(matcherFunction, true);
                Functions2 fuctionCall = decomposingFuction(matcherCall, false);
                Functions2 functionRemove = searchFunction(text, fuctionCall.getIdentify());
                return new Functions2[]{functionBase, fuctionCall, functionRemove};
            }
        }
        return null;
    }

    private Functions2 searchFunction(String text, String identify) {
        Pattern patternFunction = Pattern.compile(regexFunction);
        Matcher matcherFunction = patternFunction.matcher(text);

        while (matcherFunction.find()) {
            if (matcherFunction.group(2).equals(identify)) {
                return decomposingFuction(matcherFunction, true);
            }
        }
        return null;
    }

    private Functions2 decomposingFuction(Matcher matcher, boolean function) {
        String [] toolsRegex = {"(ent_|dec_|car_)", "\\s+,\\s+|\\s+"};
        int init = !function ? 0 : 1;
        
        String identify = matcher.group(init + 1);
        String[] params = matcher.group(init + 2).replaceAll(toolsRegex[0], "").split(toolsRegex[1]);
        params = Arrays.stream(params).filter(str -> !str.isEmpty()).toArray(String[]::new);
        int start = matcher.start();
        int end = matcher.end();
        
        if (function) {
            String body = matcher.group(4).replaceFirst("^\\s+", "").replaceAll("\\s+$", "");
            Matcher matcherBody = Pattern.compile(this.regexReturnFunction).matcher(body);
            
            if (matcherBody.matches()) {
                String code = matcherBody.group(1);
                if (code.split("\n").length == 1) {
                    matcherBody = Pattern.compile(this.regexFirstCode).matcher(code);
                    if (matcherBody.find()) {
                        code = matcherBody.group(2);
                        return new Functions2(identify, params, body, start, end, matcher, code,
                            matcherBody.start(2), matcherBody.end(2), matcherBody);
                    }
                } else {
                    return new Functions2(identify, params, body, start, end, matcher, code,
                            matcherBody.start(1), matcherBody.end(1), matcherBody);
                }
            } else {
                return new Functions2(identify, params, body, start, end, matcher);
            }
        } else {
            return new Functions2(identify, params, start, end, matcher);
        }
        
        return null;
    }
    
    private String replaceBody(Functions2[] functions) {                
        Functions2 remove = functions[2];
        String code = remove.getCode();
        String body = functions[0].getBody();
        
        if (code != null && code.split("\n").length == 1) {
            for (int i = 0; i < functions[1].getParams().length; i ++) {
                code = code.replaceAll(functions[2].getParams()[i], functions[1].getParams()[i]);
            }
            body = body.replace(functions[1].getMatcher().group(), " " + code);
            return body;
        } else {
            code = remove.getBody();
            for (int i = 0; i < functions[1].getParams().length; i ++) {
                code = code.replaceAll(functions[2].getParams()[i], functions[1].getParams()[i]);
            }
            body = body.replace(body, code);
        }

        return body;
    }
    
    private String replaceText(Functions2 [] functions, String text, String replace) {
        text = text.replace(functions[2].getMatcher().group() + "\n\n", "");
        text = text.replace(functions[0].getBody(), replace);
        System.out.println(text);
        System.out.println("");
        return text;
    }
    
    public static void main(String[] args) {
        OptimisationAnalysis2 op = new OptimisationAnalysis2();
        Functions2[] functions = op.chechFunctionOptimizer(op.test);

        while(functions != null) {
            op.test = op.replaceText(functions, op.test, op.replaceBody(functions));
            functions = op.chechFunctionOptimizer(op.test);
        }
    }
}


//ent_ ISC111 , ISC112 , ISC113 ;
//ent_ ISC211 , ISC212 ;
//
//ISC211 = 0 ; 
//ISC212 = 5 ;
//
//ent_ ISC400 ( ent_ ISC914 , ent_ ISC915 )
//{
//dec_ ISC311 ;
//ISC311 = ISC211 % ISC212 ;
//ISC111 = ISC914 + ISC915 ;
//ISC311 = ISC111 + ISC311 ;
//return ISC111 ;
//}
//
//ent_ ISC500 ( ent_ ISC213 , ent_ ISC214 )
//{
//ISC112 = ISC400 ( ISC213 , ISC214 ) ;                                
//return ISC112 ;
//}
//
//ent_ ISC600 ( ent_ ISC914 , ent_ ISC915 )
//{
//ISC111 = ISC914 + ISC915 ;
//return ISC111 ;
//}
//
//ent_ ISC700 ( ent_ ISC213 , ent_ ISC214 )
//{
//ISC112 = ISC600 ( ISC213 , ISC214 ) ;                                
//return ISC112 ;
//}
//
//ent_ ISC800 ( ent_ ISC914 , ent_ ISC915 )
//{
//ISC111 = ISC914 + ISC915 ;
//return ISC111 ;
//}
//
//ent_ ISC900 ( ent_ ISC213 , ent_ ISC214 )
//{
//dec_ ISC311 ;
//ISC311 = ISC211 % ISC212 ;
//ISC112 = ISC800 ( ISC213 , ISC214 ) ;
//ISC112 = ISC311 + ISC112;
//return ISC112 ;
//}
//
//ISC113 = ISC500 ( ISC211 , ISC212 ) ;
//ISC113 = ISC700 ( ISC211 , ISC212 ) ;
//ISC113 = ISC900 ( ISC211 , ISC212 ) ;
