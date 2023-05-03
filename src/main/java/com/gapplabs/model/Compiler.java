package com.gapplabs.model;

import com.gapplabs.model.dataStructure.*;
import com.gapplabs.model.dataStructure.Error;

import java.sql.SQLOutput;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Compiler<T> {

    private HashMap<String, Simbol> linkedHashMapSimbol;
    private HashMap<String, Error> linkedHashMapError;
    private LinkedHashMap<String, ? super GenericLexicalComponent> genericLexicalComponentLinkedHashMap;
    private HashSet<Simbol> hashSetSimbol;
    private HashSet<Error> hashSetError;
    //    private LinkedHashMapDAO<GenericLexicalComponent> genericLexicalComponentLinkedHashMapDAO;
    //    private LinkedHashMapDAO<LexerPattern> lexerPatternLinkedHashMapDAO;
    private ArrayList<String[]> lines;

    public Compiler() {
        this.linkedHashMapSimbol = new HashMap<>();
        this.linkedHashMapError = new HashMap<>();
//        this.genericLexicalComponentLinkedHashMapDAO = new LinkedHashMapDAO<>();
//        this.lexerPatternLinkedHashMapDAO = new LinkedHashMapDAO<>();
//        // It adds all the regular expressions components created
//        this.addRegularExpressionsHashMapDAO();
        this.lines = new ArrayList<>();
    }

    private void addRegularExpressionsHashMapDAO() {
//        for (RegexEnum regularExpressionComponent : RegexEnum.values()) {
//            LexerPattern lexerPattern = new LexerPattern(String.valueOf(regularExpressionComponent), regularExpressionComponent.getRegex());
//            this.lexerPatternLinkedHashMapDAO.agregar(lexerPattern.getPrefix(), lexerPattern);
//        }
    }

    public void extractLines(String codeString) {
        // Splitting the lines
        for (String algo : codeString.split("\n")) {
            this.lines.add(algo.split("\\s+"));
        }
    }

//    private void lexicalAnalysis(ArrayList<String[]> words) {
//
//        for (int line = 0; line < words.size(); line++) {
//            for (String lexeme : words.get(line)) {
//                if (!lexeme.isEmpty()) {
//                    for (RegexEnum regexEnum : RegexEnum.values()) {
//
//                    }
//                    String token = null;
//                    boolean matchExpression = false;
//                    for (int expression = 0; expression < expressions.getSize(); expression++) {
//                        Pattern pattern = Pattern.compile(expressions.getData(expression, "expresion"));
//                        Matcher matcher = pattern.matcher(lexeme);
//
//                        if (matcher.matches()) {
//                            token = expressions.createToken(expression);
//                            if (simbols.registerSimbol(lexeme, token)) {
//                                expressions.incrementCount(expression);
//                            } else {
//                                int index = simbols.getIndexData(lexeme, "lexema");
//                                token = simbols.getData(index, "token");
//                            }
//                            matchExpression = !matchExpression;
//                            this.semantic.analysisSemantic(lexeme, token, line);
//                            break;
//                        }
//                    }
//
//                    if (!matchExpression) {
//                        simbols.registerSimbol(lexeme, "-");
//                        this.semantic.analysisSemantic(lexeme, "-", line);
//                    }
//                }
//            }
//        }
//    }

//    public void processLexeme(String lexeme) {
//        String token = null;
//        boolean matchExpression = false;
//        for (int expression = 0; expression < expressions.getSize(); expression++) {
//            Pattern pattern = Pattern.compile(expressions.getData(expression, "expresion"));
//            Matcher matcher = pattern.matcher(lexeme);
//
//            if (matcher.matches()) {
//                token = expressions.createToken(expression);
//                if (simbols.registerSimbol(lexeme, token)) {
//                    expressions.incrementCount(expression);
//                } else {
//                    int index = simbols.getIndexData(lexeme, "lexema");
//                    token = simbols.getData(index, "token");
//                }
//                matchExpression = !matchExpression;
//                this.semantic.analysisSemantic(lexeme, token, line);
//                break;
//            }
//        }
//
//        if (!matchExpression) {
//            simbols.registerSimbol(lexeme, "-");
//            this.semantic.analysisSemantic(lexeme, "-", line);
//        }
//    }

    private void clearStructures() {
        this.lines.clear();
//        this.errorLinkedHasMapDAO.clear();
//        this.simbolLinkedHashMapDAO.clear();
    }

    private void lexicalAnalysis2() {
        for (int line = 0; line < this.lines.size(); line++) {
            for (String lexeme : lines.get(line)) {
                if (!lexeme.isEmpty()) {

                    GenericLexicalComponent genericLexicalComponent = (GenericLexicalComponent) GenericLexicalComponent.getGenericLexicalComponentArrayList().get(lexeme);
                    if (genericLexicalComponent instanceof Error) {
                        // Adding the current line number  to the list of that instance
                        ((Error) genericLexicalComponent).addNumberLine(line);
                    } else {
                        // Si no se encuentra, entonces hacemos uso de las expresiones regulares
                        // Tiene que ser agregada porque no lo permite  el compilador en la expresión lambda
                        RegexEnum tokenString = this.getRegexEnum(lexeme);
                        if (tokenString ==null ){
                            new Error(TokenGenerator.generateToken(RegexEnum.ERROR), lexeme,line, "");
                        }else{
                           new Simbol(TokenGenerator.generateToken(tokenString), lexeme, line, "");
                        }
                    }
                }
            }
        }
    }

    public RegexEnum getRegexEnum(String lexeme) {
        for (RegexEnum regexPattern: RegexEnum.values()) {
            if (Pattern.compile(regexPattern.getRegex()).matcher(lexeme).matches()){
                System.out.println("ENCONTRÓ EL REGEX PATTERN");
                return regexPattern;
            }
        }
        System.out.println("No ENCONTRÓ EL REGEX PATTERN");
        return null;
    }


    public void compile(String codeString) {
        // Cleaning all the structures
//        this.clearStructures();

        System.out.println("entra en método compile222");
        this.extractLines(codeString);
        this.lexicalAnalysis2();
        System.out.println("nueva estructura");
        for (Simbol simbol:Simbol.getSimbolHashSet()) {
            System.out.println(simbol);
        }
//        this.lexicalAnalysis(this.lines);

    }
}
