package com.gapplabs.model;

import com.gapplabs.model.dataStructure.GenericLexicalComponent;
import com.gapplabs.model.dataStructure.LinkedHashMapDAO;
import com.gapplabs.model.dataStructure.Simbol;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Compiler {
    private LinkedHashMapDAO<Simbol> simbolLinkedHashMapDAO;
    private LinkedHashMapDAO<Error> errorLinkedHasMapDAO;
    private LinkedHashMapDAO<GenericLexicalComponent> genericLexicalComponentLinkedHashMapDAO;
    private LinkedHashMapDAO<LexerPattern> lexerPatternLinkedHashMapDAO;
    private ArrayList<String[]> lines;

    public Compiler() {
        this.simbolLinkedHashMapDAO = new LinkedHashMapDAO<>();
        this.errorLinkedHasMapDAO = new LinkedHashMapDAO<>();
        this.genericLexicalComponentLinkedHashMapDAO = new LinkedHashMapDAO<>();
        this.lexerPatternLinkedHashMapDAO = new LinkedHashMapDAO<>();
        // It adds all the regular expressions components created
        this.addRegularExpressionsHashMapDAO();
        this.lines = new ArrayList<>();
    }

    private void addRegularExpressionsHashMapDAO() {
        for (RegularExpressionComponent regularExpressionComponent : RegularExpressionComponent.values()) {
            LexerPattern lexerPattern = new LexerPattern(String.valueOf(regularExpressionComponent), regularExpressionComponent.getRegex());
            this.lexerPatternLinkedHashMapDAO.agregar(lexerPattern.getPrefix(), lexerPattern);
        }
    }

    public void extractLines(String codeString) {
        // Splitting the lines
        for (String algo : codeString.split("\n")) {
            this.lines.add(algo.split("\\s+"));
        }
    }

    private void lexicalAnalysis(ArrayList<String[]> words) {

        for (int line = 0; line < words.size(); line++) {
            for (String lexeme : words.get(line)) {
                if (!lexeme.isEmpty()) {
                    String token = null;
                    boolean matchExpression = false;
                    for (int expression = 0; expression < expressions.getSize(); expression++) {
                        Pattern pattern = Pattern.compile(expressions.getData(expression, "expresion"));
                        Matcher matcher = pattern.matcher(lexeme);

                        if (matcher.matches()) {
                            token = expressions.createToken(expression);
                            if (simbols.registerSimbol(lexeme, token)) {
                                expressions.incrementCount(expression);
                            } else {
                                int index = simbols.getIndexData(lexeme, "lexema");
                                token = simbols.getData(index, "token");
                            }
                            matchExpression = !matchExpression;
                            this.semantic.analysisSemantic(lexeme, token, line);
                            break;
                        }
                    }

                    if (!matchExpression) {
                        simbols.registerSimbol(lexeme, "-");
                        this.semantic.analysisSemantic(lexeme, "-", line);
                    }
                }
            }
        }
    }

    private void clearStructures() {
        this.lines.clear();
        this.errorLinkedHasMapDAO.clear();
        this.simbolLinkedHashMapDAO.clear();
    }

    private void lexicalAnalysis2() {


    }

    public void compile(String codeString) {
        // Cleaning all the structures
        this.clearStructures();

        System.out.println("entra en método compile");
        this.extractLines(codeString);
        this.lexicalAnalysis2();
        this.lexicalAnalysis(this.lines);

    }
}
