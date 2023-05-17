package com.gapplabs.model.analysis;

import com.gapplabs.model.dataStructure.Errors;
import com.gapplabs.model.dataStructure.Intermediates;
import com.gapplabs.model.dataStructure.Simbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IntermediateAnalysis {
  
  // Esta clase funciona en dos partes:
  // Parte 1: recorre cada linea del codigo y genera un pequeño pseudocodigo para facilitar el registro en el triplo
  // Parte 2: toma el pseudocodigo y lo registra en el triplo

  private Simbols simbols;
  private Errors errors;
  private Intermediates intermediates;

  // Estructura de instrucciones de pseudocodigo:
  private ArrayList<ArrayList<String>> control;
  
  // Estructura de funciones:
  private Map<String, List<String>> function;
  private Map<String, Map<String, Integer>> position;
  private String nameFunction;
  // private ArrayList<Integer> position;

  // Almacen de jerarquia auxiliares:
  private int temporaryCounter;
  private boolean init;

  public IntermediateAnalysis(Simbols simbols, Errors errors, Intermediates intermediates) {
    this.simbols = simbols;
    this.errors = errors;
    this.intermediates = intermediates;
    initData();
  }
  
  public final void initData() {
    this.temporaryCounter = 0;
    this.init = false;
    this.nameFunction = null;
    this.control = new ArrayList<>();
    this.function = new HashMap<>();
    this.position = new HashMap<>();
  }

  public void compile(ArrayList<String[]> extractedWords) {
    for (String[] words : extractedWords) {
      initializationCode(words);
      createFuntion(words);
      keyFuntion(words);
      returnFuntion(words);
      callFuntion(words);
    }

    System.out.println("Intermediate");
    for (ArrayList<String> code : this.control) {
      System.out.println(Arrays.toString(code.toArray()));
      initializationIntermediate(code);
      createFunctionIntermediate(code);
      keyIntermediate(code);
      returnFunctionIntermediate(code);
      callFunctionIntermediate(code);
    }
    intermediates.registerIntermediate("", "", "", "fin de triplo");
  }

  // Método para pseudocodigo de asignaciones:
  private void initializationCode(String[] words) {
    String inicialization = "";
    ArrayList<String> operations = new ArrayList<>();
    int process = 0;

    for (String word : words) {
      if (!word.isEmpty()) {
        String token = "";
        if (simbols.checkData(word, "lexema")) {
          token = simbols.getData(simbols.getIndexData(word, "lexema"), "token");
        }

        switch (process) {
          case 0:
            operations.add("Asignacion");
            inicialization = word;
            process++;
            break;
          case 1:
            if (word.equals("=")) {
              process++;
            } else {
              return;
            }
            break;
          case 2:
            operations.add(word);
            process++;
            break;
          case 3:
            if (token.contains("AMR") || word.equals(";")) {
              if (token.contains("AMR")) {
                operations.add(word);
                process--;
              }
              if (word.equals(";")) {
                operations.add(inicialization);
                control.add(operations);
                return;
              }
            } else {
              return;
            }
            break;
        }
      }
    }
  }
  
  // Método que genera la estructura de funciones.
  private void createFuntion(String[] words) {
    String nameFunction = "";
    String typeFunction = "";
    List<String> parameters = new ArrayList<>();
    int process = 0;

    for (String word : words) {
      if (!word.isEmpty()) {
        String token = "";
        if (simbols.checkData(word, "lexema")) {
          token = simbols.getData(simbols.getIndexData(word, "lexema"), "token");
        }

        switch (process) {
          case 0:
            typeFunction = word;
            process++;
            break;
          case 1:
            nameFunction = word;
            process++;
            break;
          case 2:
            if (word.equals("(")) {
              process++;
            } else {
              return;
            }
            break;
          case 3:
            if (token.contains("TDS") || word.equals(")")) {
              if (token.contains("TDS")) {
                process++;
              }
              if (word.equals(")")) {
                parameters.add("return");
                this.function.put(nameFunction, parameters);
                this.control.add(new ArrayList<>(Arrays.asList("Funcion", nameFunction)));
                return;
              }
            } else {
              return;
            }
            break;
          case 4:
            if (token.contains("IDE")) {
              parameters.add(word);
              process++;
            } else {
              return;
            }
            break;
          case 5:
            if (word.equals(",") || word.equals(")")) {
              if (word.equals(",")) {
                process -= 2;
              }
              if (word.equals(")")) {
                parameters.add("return");
                this.function.put(nameFunction, parameters);
                this.control.add(new ArrayList<>(Arrays.asList("Funcion", nameFunction)));
                return;
              }
            } else {
              return;
            }
            break;
        }
      }
    }
  }
  
  // Método que genera el pseudocodigo de limites de funciones.
  private void keyFuntion(String[] words) {
    ArrayList<String> keys = new ArrayList<>();

    for (String word : words) {
      if (!word.isEmpty()) {
        if (word.equals("{") || word.equals("}")) {
          keys.add("Limite");
          if (word.equals("{")) {
            keys.add("inicio");
          }
          if (word.equals("}")) {
            keys.add("fin");
          }
          this.control.add(keys);
          return;
        } else {
          return;
        }
      }
    }
  }
  
  // Método que genera el pseudocodigo de regreso de funciones.
  private void returnFuntion(String[] words) {
    ArrayList<String> parameters = new ArrayList<>();
    int process = 0;

    for (String word : words) {
      if (!word.isEmpty()) {
        String token = "";
        if (simbols.checkData(word, "lexema")) {
          token = simbols.getData(simbols.getIndexData(word, "lexema"), "token");
        }
        
        switch (process) {
          case 0:
            if (word.equals("return")) {
            parameters.add("Regreso");
            process ++;
            } else return;
            break;
          case 1:
            if (token.contains("IDE") || token.contains("TDS")) {
              parameters.add(word);
              process ++;
            } else return;
            break;
          case 2:
            if (word.equals(";")) {
              this.control.add(parameters);
              return;
            } else return;
        }
      }
    }
  }
  
  // Método para pseudocodigo de llamada de funcion:
  private void callFuntion(String[] words) {
    String inicialization = "";
    String nameFuntion = "";
    ArrayList<String> parameters = new ArrayList<>();
    int process = 0;
    int index = 0;

    for (String word : words) {
      if (!word.isEmpty()) {
        String token = "";
        if (simbols.checkData(word, "lexema")) {
          token = simbols.getData(simbols.getIndexData(word, "lexema"), "token");
        }
        
        if(function.containsKey(word) && process == 0) {
          parameters.add("Llamada");
          process+=2;
        }

        switch (process) {
          case 0:
            parameters.add("Llamada");
            inicialization = word;
            process++;
            break;
          case 1:
            if (word.equals("=")) process++;
            else return;
            break;
          case 2:
            if(function.containsKey(word)) {
              parameters.add(word);
              nameFuntion = word;
              process++;
            }
            else return;
            break;
          case 3:
            if (word.equals("(")) process++;
            else return;
            break;
          case 4:
            if (word.equals(")")) {
                parameters.add("return");
                parameters.add(inicialization);
                this.control.add(parameters);
                return;
            } else {
              parameters.add("Asignacion");
              parameters.add(word);
              parameters.add(function.get(nameFuntion).get(index));
              process++;
              index++;
            }
            break;
          case 5:
            if (word.equals(",") || word.equals(")")) {
              if (word.equals(",")) {
                process --;
              }
              if (word.equals(")")) {
                parameters.add("return");
                parameters.add(inicialization);
                this.control.add(parameters);
                return;
              }
            } else return;
            break;
        }
      }
    }
  }

  // Registra las operaciones en el triplo
  private void initializationIntermediate(ArrayList<String> codes) {
    boolean correct = codes.get(0).equals("Asignacion");
    String assignment = codes.get(codes.size() - 1);
    boolean asignation = true;
    
    if ((codes.contains("+") || codes.contains("-")) || (codes.contains("*") || codes.contains("/") || codes.contains("%"))) {
      asignation = false;
    }

    if (correct) {
      if (asignation) {
        intermediates.registerIntermediate("T1", codes.get(1), "=", "Asignacion");
        intermediates.registerIntermediate(assignment, "T1", "=", "");
      } else {
        List<String> subCode = codes.subList(1, codes.size() - 1);
        systemAritmetic(subCode, assignment);
      }
    }
  }
  
  // Método auxiliar para la creación de funciones.
  private void createFunctionIntermediate(ArrayList<String> codes) {
    boolean correct = codes.get(0).equals("Funcion");
    String nameFunction = codes.get(1);
    
    if (correct) {
      this.position.put(nameFunction, new HashMap<>());
      this.nameFunction = nameFunction;
    }
  }
  
    // Método auxiliar para el return de las funciones.
  private void returnFunctionIntermediate(ArrayList<String> codes) {
    boolean correct = codes.get(0).equals("Regreso");
    
    if (correct) {
      this.function.get(this.nameFunction).add(codes.get(1));
    }
  }
  
  // Registra las delimitaciones de las funciones en el tiplo
  private void keyIntermediate(ArrayList<String> codes) {
    boolean correct = codes.get(0).equals("Limite");
    if (correct) {
      if (codes.get(1).equals("inicio")) {
        intermediates.registerIntermediate("", "", "JMP", "Salto inicio funcion");
        position.get(this.nameFunction).put("inicio", intermediates.getSize());
      } else {
        int index = intermediates.getLastIndexData("Salto inicio funcion", "etiqueta");
        intermediates.updateData(intermediates.createMapData(new String[] {"fuente"}, String.valueOf(intermediates.getSize() + 2)), index);
        intermediates.registerIntermediate("", "", "JMP", "Salto fin funcion");
        position.get(this.nameFunction).put("fin", intermediates.getSize() - 1);
      }
    }
  }
  
  // Registra las llamadas de funcion en el tiplo
  private void callFunctionIntermediate(ArrayList<String> codes) {
    boolean correct = codes.get(0).equals("Llamada");
    String nameFunction = codes.get(1);
  
    if (correct) {
      for (int i = 2; i < codes.size(); i++) {
        if (codes.get(i).equals("Asignacion")) {
          List<String> subList = codes.subList(i, i + 3);
          initializationIntermediate(new ArrayList<>(subList));
          i += 2;
        }
        if (codes.get(i).equals("return")) {
          intermediates.registerIntermediate("", intermediates.getData(position.get(nameFunction).get("inicio"), 
                  "renglon"), "JMP", "Salto hacia la funcion");
          intermediates.updateData(intermediates.createMapData(new String[] {"fuente"}, String.valueOf(intermediates.getSize() + 1)),
                  position.get(nameFunction).get("fin"));
          ArrayList<String> subList = new ArrayList<>(Arrays.asList("Asignacion",
                  function.get(nameFunction).get(function.get(nameFunction).size() - 1),
                  codes.get(codes.size() - 1)));
          initializationIntermediate(subList);
          return;
        }
      }
    }
  }

  // Proceso aritmetico:
  private void processAritmetic(List<String> sentence) {
    boolean first = false;

    for (int i = 1; i < sentence.size() - 1; i++) {
      if (sentence.get(i).equals("%") || sentence.get(i).equals("/") || sentence.get(i).equals("*")) {
        this.temporaryCounter++;

        if (this.init == true) {
          intermediates.registerIntermediate("T" + this.temporaryCounter, sentence.get(i - 1), "=", "Aritmetica");
          this.init = false;
        } else {
          intermediates.registerIntermediate("T" + this.temporaryCounter, sentence.get(i - 1), "=", "");
        }

        intermediates.registerIntermediate("T" + this.temporaryCounter, sentence.get(i + 1), sentence.get(i), "");
        sentence.set(i - 1, "T" + temporaryCounter);
        sentence.remove(i + 1);
        sentence.remove(i);
        i = 0;
      }
    }

    for (int i = 1; i < sentence.size() - 1; i++) {
      if (sentence.get(i).equals("+") || sentence.get(i).equals("-")) {
        if (first == false) {
          this.temporaryCounter++;
          if (this.init == true) {
            intermediates.registerIntermediate("T" + this.temporaryCounter, sentence.get(i - 1), "=", "Aritmetica");
            this.init = false;
          } else {
            intermediates.registerIntermediate("T" + this.temporaryCounter, sentence.get(i - 1), "=", "");
          }
          first = true;
        }

        intermediates.registerIntermediate("T" + this.temporaryCounter, sentence.get(i + 1), sentence.get(i), "");
        sentence.set(i - 1, "T" + this.temporaryCounter);
        sentence.remove(i + 1);
        sentence.remove(i);
        i--;
      }
    }

    sentence.clear();
  }

  // Sistema aritmetico, funciona mas como un intermediaro.
  private void systemAritmetic(List<String> codes, String assignment) {
    this.processAritmetic(codes);
    intermediates.registerIntermediate(assignment, "T" + this.temporaryCounter, "=", "");
    this.temporaryCounter = 0;
  }
}
