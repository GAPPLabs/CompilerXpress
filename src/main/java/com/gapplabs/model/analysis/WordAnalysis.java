package com.gapplabs.model.analysis;

import com.gapplabs.model.dataStructure.Errors;
import com.gapplabs.model.dataStructure.Expressions;
import com.gapplabs.model.dataStructure.Simbols;
import com.gapplabs.model.dataStructure.Tokens;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// TODO This class has to be renamed to Compiler (maybe)
// TODO Create a method which can getText, words, analyze and others internals methods
public class WordAnalysis {

    private Simbols simbols;
    private Errors erros;
    private Expressions expressions;
    private Tokens tokens;
    private ArrayList<String[]> extractedWords = new ArrayList<>();

    public WordAnalysis() {
        simbols = new Simbols();
        erros = new Errors();
        expressions = new Expressions();
        tokens = new Tokens();
        this.extractedWords = new ArrayList<>();
    }

    public void extractWords(String codeString) {
        String[] lines = codeString.split("\n");
        for (String line : lines) this.extractedWords.add(line.split("\\s+"));
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
                            break;
                        }
                    }

                    if (!matchExpression) {
                        token = erros.createToken();
                        if (!erros.registerError(lexeme, token, String.valueOf(line + 1), "Error lexico")) {
                            int index = erros.getIndexData(lexeme, "lexema");
                            token = erros.getData(index, "token");
                        }
                    }

                    tokens.writeToken(token);
                }
            }
            tokens.saveWriteToken();
        }
    }

    // This can be separated  in precompile and compile
    public void compile(String codeString) {
        System.out.println("entra en método compile");
        // Removing all the words from a previous analysis
        this.extractedWords.clear();
        this.extractWords(codeString);
        this.lexicalAnalysis(this.extractedWords);
    }

    public static void main(String[] args) {
      String text = "Hola como estas = \t como = + car_ads - ISC123R";
      WordAnalysis a = new WordAnalysis();
      a.compile(text);

      a.simbols.printData();
      a.erros.printData();
      a.expressions.printData();
      a.tokens.printData();
  }
}
