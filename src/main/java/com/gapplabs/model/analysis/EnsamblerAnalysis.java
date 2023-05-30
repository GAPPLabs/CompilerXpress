package com.gapplabs.model.analysis;

import com.gapplabs.model.dataStructure.Ensambler;
import com.gapplabs.model.dataStructure.Intermediates;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class EnsamblerAnalysis {

  private final Intermediates intermediates;
  private final Ensambler ensambler;
  String memory = null;

  public EnsamblerAnalysis(Intermediates intermediates, Ensambler ensambler) {
    this.intermediates = intermediates;
    this.ensambler = ensambler;
    initData();
  }
  
  public final void initData() {
    this.memory = null;
  }

  public void compileEnsamblerCode() {
    Map<String, List<Map<String, String>>> codeData = this.intermediates.transformBlockCode();

    for (String key : codeData.keySet()) {
      List<Map<String, String>> data = codeData.get(key);
      String label = data.get(0).get("etiqueta");
      String nameFunction = label.split(" ")[label.split(" ").length - 1];

      if (label.equals("Asignacion")) {
        createEnsamblerAssignation(data, Integer.parseInt(key) + 1, null, false, false);
      }
      
      if (label.contains("Asignacion inicio")) {
        createEnsamblerAssignation(data, Integer.parseInt(key) + 1, nameFunction, true, false);
      }
      
      if (label.contains("Asignacion key")) {
        createEnsamblerAssignation(data, Integer.parseInt(key) + 1, nameFunction, true, true);
      }
      
      if (label.contains("Asignacion funcion")) {
        createEnsamblerAssignation(data, Integer.parseInt(key) + 1, nameFunction, false, false);
      }
      
      if (label.equals("Aritmetica")) {
        createEnsamblerAritmetic(data, Integer.parseInt(key) + 1, null);
      }
      
      if (label.contains("Aritmetica key")) {
        createEnsamblerAritmetic(data, Integer.parseInt(key) + 1, nameFunction);
      }
    
      if (label.contains("Salto inicio funcion")) {
        String index = String.valueOf(Integer.parseInt(data.get(0).get("fuente")) - 1);
        if (codeData.containsKey(index)) {
          boolean condition = codeData.get(index).get(0).get("etiqueta").contains("Salto");
          if (memory == null && !condition) memory = nameFunction;
          String jump = condition ? "ETI-" + (Integer.parseInt(index) + 1) : "ASIGNA_PARAMETROS_" + memory;
          memory = memory != null ? memory : condition ? nameFunction : null;
          this.ensambler.registerEnsambler("ETI-" + (Integer.parseInt(key) + 1), "JMP", jump, "");
        } else {
          this.ensambler.registerEnsambler("ETI-" + (Integer.parseInt(key) + 1), "JMP", "ETI-" + (Integer.parseInt(index) + 1), "");
        }
      }
      
      if (label.contains("Salto fin funcion")) {
        this.ensambler.registerEnsambler("ETI-" + (Integer.parseInt(key) + 1), "JMP", "VALOR_A_RETORNAR_" + nameFunction, "");
      }
      
      if (label.contains("Salto hacia la funcion")) {
        this.ensambler.registerEnsambler("ETI-" + (Integer.parseInt(key) + 1), "JMP", "INICIO_" + nameFunction, "");
      }
      
      if (label.equals("Fin de triplo")) {
        this.ensambler.registerEnsambler("ETI-" + (Integer.parseInt(key) + 1), "", "", "");
      }
    }

    removeUnusedLabesl();
  }

  private void createEnsamblerAssignation(List<Map<String, String>> data, int ID, String function, boolean assing, boolean key) {
    System.out.println("tamanio: " + data.size());
    String expression = "(T)([0-9]+)";

    for (int i = 0; i < data.size(); i++) {
      String registerLeft, registerRigth = null;

      if (data.get(i).get("objeto").matches(expression)) {
        registerLeft = "AX";
        registerRigth = data.get(i).get("fuente");
      } else {
        registerRigth = "AX";
        registerLeft = data.get(i).get("objeto");
      }

      if (i == 0) {
        if (function != null) {
          if (assing) {
            String assingFunction = "ASIGNA_PARAMETROS_" + function;
            if (this.ensambler.getIndexData(assingFunction, "etiqueta") == -1) {
              this.ensambler.registerEnsambler(assingFunction, "MOV", registerLeft, registerRigth);
            } else {
              this.ensambler.registerEnsambler("", "MOV", registerLeft, registerRigth);
            }
          } else {
            String label = key ? "INICIO_" : "VALOR_A_RETORNAR_";
            this.ensambler.registerEnsambler(label + function, "MOV", registerLeft, registerRigth);
          }
        } else {
          this.ensambler.registerEnsambler("ETI-" + ID, "MOV", registerLeft, registerRigth);
        }
      } else {
        this.ensambler.registerEnsambler("", "MOV", registerLeft, registerRigth);
      }
    }
  }

  private void createEnsamblerAritmetic(List<Map<String, String>> data, int ID, String function) {
    List<String> orderFirst = Arrays.asList("-", "+");
    List<String> orderSecond = Arrays.asList("*", "/", "%");
    boolean aritmeticOrderFirst = data.stream().flatMap(map -> map.values().stream()).anyMatch(value -> orderFirst.stream().anyMatch(value::contains));
    boolean aritmeticOrderSecond = data.stream().flatMap(map -> map.values().stream()).anyMatch(value -> orderSecond.stream().anyMatch(value::contains));

    // Solo suma y resta
    if (aritmeticOrderFirst == true && aritmeticOrderSecond == false) {
      systemAritmeticOrderFirst(data, ID, function);
    }

    // Solo multiplicacion, division y modulo
    if (aritmeticOrderFirst == false && aritmeticOrderSecond == true) {
      systemAritmeticOrderSecond(data, ID, function);
    }

    // Aritmetica compuesta
    if (aritmeticOrderFirst == true && aritmeticOrderSecond == true) {
      int count = 0;
      String memory = "";

      List<String> temporalData = new ArrayList<>();
      List<Integer> startData = new ArrayList<>();
      List<Integer> endData = new ArrayList<>();

      for (int i = 0; i < data.size(); i++) {
        if (data.get(i).get("operador").matches("/|\\*|%")) {
          if (memory.isEmpty()) {
            memory = data.get(i - 1).get("objeto");
            startData.add(i - 1);
          } else {
            if (data.get(i - 1).get("fuente").equals(memory)) {
              memory = data.get(i - 1).get("objeto");
            } else {
              count++;
              temporalData.add(memory);
              memory = "";
              endData.add(i - 2);
              i -= 2;
            }
          }
        }

        if (data.get(i).get("operador").matches("\\+|-")) {
          count++;
          temporalData.add(memory);
          endData.add(i - 2);
          break;
        }
      }

      System.out.println("CONTROL: " + temporalData.size() + " --> " + startData.size() + " --> " + endData.size());

      for (int i = 0; i < count; i++) {
        System.out.println("MEM: " + temporalData.get(i) + " --> " + startData.get(i) + " - " + endData.get(i));
      }

      systemAritmeticCompound(data, ID, function, temporalData, startData, endData, count);
    }
  }

  private void systemAritmeticOrderFirst(List<Map<String, String>> data, int ID, String function) {
    boolean init = true;
    for (int i = 0; i < data.size(); i++) {
      String label = init ? "ETI-" + ID : "";
      label = function != null ? "INICIO_" + function : label;

      switch (data.get(i).get("operador")) {
        case "-" -> {
          if (init) {
            this.ensambler.registerEnsambler(label, "MOV", "AX", data.get(i - 1).get("fuente"));
            init = false;
          }
          this.ensambler.registerEnsambler("", "MOV", "BX", data.get(i).get("fuente"));
          this.ensambler.registerEnsambler("", "SUB", "AX", "BX");
        }
        case "+" -> {
          if (init) {
            this.ensambler.registerEnsambler(label, "MOV", "AX", data.get(i - 1).get("fuente"));;
            init = false;
          }
          this.ensambler.registerEnsambler("", "MOV", "BX", data.get(i).get("fuente"));
          this.ensambler.registerEnsambler("", "ADD", "AX", "BX");
        }
      }

      if (data.get(i).get("operador").equals("=") && (i + 1) == data.size()) {
        this.ensambler.registerEnsambler("", "MOV", data.get(i).get("objeto"), "AX");
      }
    }
  }

  private void systemAritmeticOrderSecond(List<Map<String, String>> data, int ID, String function) {
    boolean init = true;
    for (int i = 0; i < data.size(); i++) {
      String label = init ? "ETI-" + ID : "";
      label = function != null ? "INICIO_" + function : label;

      switch (data.get(i).get("operador")) {
        case "*" -> {
          if (init) {
            this.ensambler.registerEnsambler(label, "MOV", "AX", data.get(i - 1).get("fuente"));
            init = false;
          }
          this.ensambler.registerEnsambler("", "MOV", "BL", data.get(i).get("fuente"));
          this.ensambler.registerEnsambler("", "MUL", "BL", "");
        }
        case "/" -> {
          if (init) {
            this.ensambler.registerEnsambler(label, "MOV", "AX", data.get(i - 1).get("fuente"));
            init = false;
          }
          this.ensambler.registerEnsambler("", "MOV", "BL", data.get(i).get("fuente"));
          this.ensambler.registerEnsambler("", "DIV", "BL", "");
          this.ensambler.registerEnsambler("", "MOV", "AH", "0");
        }
        case "%" -> {
          if (init) {
            this.ensambler.registerEnsambler(label, "MOV", "AX", data.get(i - 1).get("fuente"));
            init = false;
          }
          this.ensambler.registerEnsambler("", "MOV", "BL", data.get(i).get("fuente"));
          this.ensambler.registerEnsambler("", "DIV", "BL", "");
          this.ensambler.registerEnsambler("", "MOV", "AL", "AH");
          this.ensambler.registerEnsambler("", "MOV", "AH", "0");
        }
      }

      if (data.get(i).get("operador").equals("=") && (i + 1) == data.size()) {
        this.ensambler.registerEnsambler("", "MOV", data.get(i).get("objeto"), "AX");
      }
    }
  }

  private void systemAritmeticCompound(List<Map<String, String>> data, int ID, String function, List<String> MEM, List<Integer> startMEN, List<Integer> endMEN, int count) {
    boolean init = true;
    for (int i = 0; i < data.size(); i++) {
      if (data.get(i).get("operador").equals("-") || data.get(i).get("operador").equals("+")) {
        boolean find = false;

        // Encuentra la operación de segundo orden y lo procesa parcialmente.
        for (int a = 0; a < count; a++) {
          if (data.get(i - 1).get("fuente").equals(MEM.get(a)) && data.get(i - 1).get("operador").equals("=")) {
            subSystemAritmeticCompound(data, ID, function, init, startMEN.get(a), endMEN.get(a));
            find = true;
            init = false;
          }
        }

        if (find == true) {
          boolean match = false;

          // Finaliza la parcialidad mediante las operaciones de primer orden.
          for (int a = 0; a < count; a++) {
            if (data.get(i).get("fuente").equals(MEM.get(a)) && data.get(i).get("operador").matches("\\+|-")) {
              subSystemAritmeticCompound(data, ID, function, init, startMEN.get(a), endMEN.get(a));
              match = true;
            }
          }

          String operator = data.get(i).get("operador").equals("+") ? "ADD" : "SUB";
          if (match) {
            this.ensambler.registerEnsambler("", operator, "CX", "AX");
            this.ensambler.registerEnsambler("", "MOV", "AX", "CX");
          } else {
            this.ensambler.registerEnsambler("", "MOV", "BX", data.get(i).get("fuente"));
            this.ensambler.registerEnsambler("", operator, "AX", "BX");
          }
        } else {
          boolean match = false;
          String label = init ? "ETI-" + ID : "";
          label = function != null ? "INICIO_" + function : label;

          if (init) {
            this.ensambler.registerEnsambler(label, "MOV", "AX", data.get(i - 1).get("fuente"));
            init = false;
          }

          // Finaliza la parcialidad mediante las operaciones de primer orden.
          for (int a = 0; a < count; a++) {
            if (data.get(i).get("fuente").equals(MEM.get(a)) && data.get(i).get("operador").matches("\\+|-")) {
              subSystemAritmeticCompound(data, ID, function, init, startMEN.get(a), endMEN.get(a));
              match = true;
            }
          }

          String operator = data.get(i).get("operador").equals("+") ? "ADD" : "SUB";
          if (match) {
            this.ensambler.registerEnsambler("", operator, "CX", "AX");
            this.ensambler.registerEnsambler("", "MOV", "AX", "CX");
          } else {
            this.ensambler.registerEnsambler("", "MOV", "BX", data.get(i).get("fuente"));
            this.ensambler.registerEnsambler("", operator, "AX", "BX");
          }
        }
      }
      
      if (data.get(i).get("operador").equals("=") && (i + 1) == data.size()) {
        this.ensambler.registerEnsambler("", "MOV", data.get(i).get("objeto"), "AX");
      }
    }
  }

  private void subSystemAritmeticCompound(List<Map<String, String>> data, int ID, String function, boolean init, int startMEN, int endMEN) {
    boolean skip = false;

    for (int i = startMEN; i <= endMEN; i++) {
      String label = init ? "ETI-" + ID : "";
      label = function != null ? "INICIO_" + function : label;

      switch (data.get(i).get("operador")) {
        case "*" -> {
          if (init) {
            this.ensambler.registerEnsambler(label, "MOV", "AX", data.get(i - 1).get("fuente"));
            init = false;
            skip = true;
          } else {
            if (data.get(i - 1).get("operador").equals("=") && skip == false) {
              this.ensambler.registerEnsambler("", "MOV", "CX", "AX");
              this.ensambler.registerEnsambler("", "MOV", "AX", data.get(i - 1).get("fuente"));
              skip = true;
            }
          }
          this.ensambler.registerEnsambler("", "MOV", "BL", data.get(i).get("fuente"));
          this.ensambler.registerEnsambler("", "MUL", "BL", "");
        }
        case "/" -> {
          if (init) {
            this.ensambler.registerEnsambler(label, "MOV", "AX", data.get(i - 1).get("fuente"));
            init = false;
            skip = true;
          } else {
            if (data.get(i - 1).get("operador").equals("=") && skip == false) {
              this.ensambler.registerEnsambler("", "MOV", "CX", "AX");
              this.ensambler.registerEnsambler("", "MOV", "AX", data.get(i - 1).get("fuente"));
              skip = true;
            }
          }
          this.ensambler.registerEnsambler("", "MOV", "BL", data.get(i).get("fuente"));
          this.ensambler.registerEnsambler("", "DIV", "BL", "");
          this.ensambler.registerEnsambler("", "MOV", "AH", "0");
        }
        case "%" -> {
          if (init) {
            this.ensambler.registerEnsambler(label, "MOV", "AX", data.get(i - 1).get("fuente"));
            init = false;
            skip = true;
          } else {
            if (data.get(i - 1).get("operador").equals("=") && skip == false) {
              this.ensambler.registerEnsambler("", "MOV", "CX", "AX");
              this.ensambler.registerEnsambler("", "MOV", "AX", data.get(i - 1).get("fuente"));
              skip = true;
            }
          }
          this.ensambler.registerEnsambler("", "MOV", "BL", data.get(i).get("fuente"));
          this.ensambler.registerEnsambler("", "DIV", "BL", "");
          this.ensambler.registerEnsambler("", "MOV", "AL", "AH");
          this.ensambler.registerEnsambler("", "MOV", "AH", "0");
        }
      }
    }
  }

  private void removeUnusedLabesl() {
    for (int i = 0; i < this.ensambler.getSize(); i++) {
      boolean found = false;
      for (int a = 0; a < this.ensambler.getSize(); a++) {
        if (this.ensambler.getData(a, "objeto").equals(this.ensambler.getData(i, "etiqueta"))) {
          found = true;
        }
      }
      if (found == false) {
        this.ensambler.updateData(this.ensambler.createMapData(new String[]{"etiqueta"}, ""), i);
      }
    }
  }
}
