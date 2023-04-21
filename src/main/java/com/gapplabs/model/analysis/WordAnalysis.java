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
    private SemanticAnalysis semantic;
    private ArrayList<String[]> extractedWords = new ArrayList<>();

    public WordAnalysis() {
        simbols = new Simbols();
        erros = new Errors();
        expressions = new Expressions();
        this.extractedWords = new ArrayList<>();
        semantic = new SemanticAnalysis(simbols, erros);
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

  public Simbols getSimbols() {
    return simbols;
  }

  public Errors getErros() {
    return erros;
  }

  public Expressions getExpressions() {
    return expressions;
  }

  public SemanticAnalysis getSemantic() {
    return semantic;
  }

  public ArrayList<String[]> getExtractedWords() {
    return extractedWords;
  }

    // This can be separated  in precompile and compile
    public void compile(String codeString) {
        System.out.println("entra en método compile");
        // Removing all the words from a previous analysis
        this.extractedWords.clear();
        this.extractWords(codeString);
        this.lexicalAnalysis(this.extractedWords);
    }

//    public static void main(String[] args) {
//      String text = //"ent_ ISC1235R ; \n" +
//                    //"ISC1235R = 666.5555 \n" + 
//                    "ent_ ISC1235R , ISC12345R ; \n" + 
//                    "dec_ ISC1235555R , ISC12444345R ; \n" + 
//                    "ent_ gass ( ent_ ISC34432R ) ; \n" +
//                    "ISC1235R = gass ( ISC1235555R ) ;";
//      WordAnalysis a = new WordAnalysis();
//      a.compile(text);
//
//      a.simbols.printData();
//      a.erros.printData();
//      a.expressions.printData();
//  }
}
