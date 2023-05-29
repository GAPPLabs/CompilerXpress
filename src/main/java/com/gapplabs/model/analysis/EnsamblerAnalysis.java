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

  public EnsamblerAnalysis(Intermediates intermediates, Ensambler ensambler) {
    this.intermediates = intermediates;
    this.ensambler = ensambler;
  }

  public void compileEnsamblerCode() {
    Map<String, List<Map<String, String>>> codeData = this.intermediates.transformBlockCode();

    for (String key : codeData.keySet()) {
      List<Map<String, String>> data = codeData.get(key);

      switch (data.get(0).get("etiqueta")) {
        case "Asignacion", "Asignacion funcion" -> {
          createEnsamblerAssignation(data, Integer.parseInt(key) + 1);
        }
        case "Aritmetica" -> {
          createEnsamblerAritmetic(data, Integer.parseInt(key) + 1);
        }
        case "Salto inicio funcion", "Salto fin funcion", "Salto hacia la funcion" -> {
          this.ensambler.registerEnsambler("ETI-" + (Integer.parseInt(key) + 1), "JMP", "ETI-" + data.get(0).get("fuente"), "");
        }
        case "Fin de triplo" -> {
          this.ensambler.registerEnsambler("ETI-" + (Integer.parseInt(key) + 1), "", "", "");
        }
      }
    }

   removeUnusedLabesl();
  }

  private void createEnsamblerAssignation(List<Map<String, String>> data, int ID) {
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
        this.ensambler.registerEnsambler("ETI-" + ID, "MOV", registerLeft, registerRigth);
      } else {
        this.ensambler.registerEnsambler("", "MOV", registerLeft, registerRigth);
      }
    }
  }

  private void createEnsamblerAritmetic(List<Map<String, String>> data, int ID) {
    List<String> orderFirst = Arrays.asList("-", "+");
    List<String> orderSecond = Arrays.asList("*", "/", "%");
    boolean aritmeticOrderFirst = data.stream().flatMap(map -> map.values().stream()).anyMatch(value -> orderFirst.stream().anyMatch(value::contains));
    boolean aritmeticOrderSecond = data.stream().flatMap(map -> map.values().stream()).anyMatch(value -> orderSecond.stream().anyMatch(value::contains));

    // Solo suma y resta
    if (aritmeticOrderFirst == true && aritmeticOrderSecond == false) {
      systemAritmeticOrderFirst(data, ID);
    }

    // Solo multiplicacion, division y modulo
    if (aritmeticOrderFirst == false && aritmeticOrderSecond == true) {
      systemAritmeticOrderSecond(data, ID);
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

      systemAritmeticCompound(data, ID, temporalData, startData, endData, count);
    }
  }

  private void systemAritmeticOrderFirst(List<Map<String, String>> data, int ID) {
    boolean init = true;
    for (int i = 0; i < data.size(); i++) {
      String label = init ? "ETI-" + ID : "";

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

  private void systemAritmeticOrderSecond(List<Map<String, String>> data, int ID) {
    boolean init = true;
    for (int i = 0; i < data.size(); i++) {
      String label = init ? "ETI-" + ID : "";

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

  private void systemAritmeticCompound(List<Map<String, String>> data, int ID, List<String> MEM, List<Integer> startMEN, List<Integer> endMEN, int count) {
    boolean init = true;
    for (int i = 0; i < data.size(); i++) {
      if (data.get(i).get("operador").equals("-") || data.get(i).get("operador").equals("+")) {
        boolean find = false;

        // Encuentra la operación de segundo orden y lo procesa parcialmente.
        for (int a = 0; a < count; a++) {
          if (data.get(i - 1).get("fuente").equals(MEM.get(a)) && data.get(i - 1).get("operador").equals("=")) {
            subSystemAritmeticCompound(data, ID, init, startMEN.get(a), endMEN.get(a));
            find = true;
            init = false;
          }
        }

        if (find == true) {
          boolean match = false;

          // Finaliza la parcialidad mediante las operaciones de primer orden.
          for (int a = 0; a < count; a++) {
            if (data.get(i).get("fuente").equals(MEM.get(a)) && data.get(i).get("operador").matches("\\+|-")) {
              subSystemAritmeticCompound(data, ID, init, startMEN.get(a), endMEN.get(a));
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

          if (init) {
            this.ensambler.registerEnsambler(label, "MOV", "AX", data.get(i - 1).get("fuente"));
            init = false;
          }

          // Finaliza la parcialidad mediante las operaciones de primer orden.
          for (int a = 0; a < count; a++) {
            if (data.get(i).get("fuente").equals(MEM.get(a)) && data.get(i).get("operador").matches("\\+|-")) {
              subSystemAritmeticCompound(data, ID, init, startMEN.get(a), endMEN.get(a));
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

  private void subSystemAritmeticCompound(List<Map<String, String>> data, int ID, boolean init, int startMEN, int endMEN) {
    boolean skip = false;

    for (int i = startMEN; i <= endMEN; i++) {
      String label = init ? "ETI-" + ID : "";

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
