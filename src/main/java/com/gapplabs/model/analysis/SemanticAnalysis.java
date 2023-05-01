package com.gapplabs.model.analysis;

import com.gapplabs.model.dataStructure.Common;
import com.gapplabs.model.dataStructure.Errors;
import com.gapplabs.model.dataStructure.Simbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SemanticAnalysis {
  
  private Simbols simbols;
  private Errors errors;
  private Common compatible = new Common();
  private Common operations = new Common();
  private final static String [] nameData = {"ent_", "dec_", "car_"};
  
  private boolean initType;
  private boolean setType;
  private String typeNow;
  
  private int numberPassOperation;
  private String typeData;
  
  private int numberPassFuntion;
  private String nameFuntion;
  private String typeFuntion;
  private boolean assingData;
  private String assingType;
  private List<String> paramsFuntion;
  private Map<String, List<String>> funtions;
  private Map<String, String> returnFuntions;
  private Map<String, String> typeFuntions;
  
  private int numberPassCall;
  private String rootType;
  private String callName;
  private int paramsSize;
  
  private String returnFuntion;
  private int numberPassReturn;

  public SemanticAnalysis(Simbols simbols, Errors errors) {
    this.simbols = simbols;
    this.errors = errors;
    compatible.createData(nameData, Arrays.asList("ent_"), Arrays.asList("ent_", "dec_"), Arrays.asList("car_"));
    operations.createData(nameData, Arrays.asList("+", "-", "*", "/", "%"), Arrays.asList("+", "-", "*", "/", "%"), Arrays.asList("+"));
    
    funtions = new HashMap<>();
    returnFuntions = new HashMap<>();
    typeFuntions = new HashMap<>();
    returnFuntion = null;
    
    initData();
  }
  
  public final void initData() {
    initType = true;
    setType = false;
    typeNow = null;
    
    numberPassOperation = 0;
    typeData = null;
    
    numberPassFuntion = 0;
    nameFuntion = null;
    typeFuntion = null;
    assingData = true;
    assingType = null;
    paramsFuntion = new ArrayList<>();
    
    numberPassCall = 0;
    rootType = null;
    callName = null;
    paramsSize = 0;
    
    numberPassReturn = 0;
  }
  
  public void analysisSemantic(String lexeme, String token, int line) {
    int index = simbols.getIndexData(lexeme, "lexema");
    assingTypeData(token);
    assingTypeIdentificator(lexeme, token);
    checkErrorsSemantic(lexeme, token, line, index);
    checkFuntionsSemantic(lexeme, token, index);
    checkReturnFuntion(lexeme, token, line);
    checkFuntionCall(lexeme, token, line, index);
  }
  
  
  private String getTypeData(String token) {
    if (token.contains("ENT")) return "ent_";
    if (token.contains("DEC")) return "dec_";
    if (token.contains("STR")) return "car_";
    return null;
  }
  
  private String getTypeLexeme(String token) {
    if (token.contains("ent")) return "ent_";
    if (token.contains("dec")) return "dec_";
    if (token.contains("car")) return "car_";
    return null;
  }
  
  private void setTypeData(String type, int index) {
    simbols.updateData(simbols.createMapData(new String [] {"tipo"}, type), index);
  }
  
  private void assingTypeData (String token) {
    int index = simbols.getIndexData(token, "token");
    String type = getTypeData(token);
    if (type != null) setTypeData(type, index);
  }
  
  private void assingTypeIdentificator(String lexeme, String token) {
    if (initType) {
      String type = getTypeLexeme(lexeme);
      if (type != null) {
        typeNow = type;
        setType = !setType;
        initType = !initType;
      }
    } else {
      if (token.contains("IDE") && setType) {
        int index = simbols.getIndexData(lexeme, "lexema");
        setTypeData(typeNow, index);
        setType = !setType;
      } else {
        if (lexeme.equals(",")) setType = !setType;
        else {
          typeNow = null;
          initType = true;
          setType = false;
        }
      }
    }
  }
  
  private int checkVariableIndefine(String lexeme, String token, int index, int line) {
    if (!token.contains("IDE")) return 0;
    else {
      if (!simbols.getData(index, "tipo").isEmpty()) {
        return 1;
      } else {
        errors.registerError(lexeme, errors.createTokenSemantic(),
                String.valueOf(line + 1), "Variable indefinida");
        this.numberPassOperation = 0;
        this.typeData = null;
        return 2;
      }
    }
  }
  
  private int checkTypeData(String lexeme, int index, int line) {
    String type = this.simbols.getData(index, "tipo");
    if(Arrays.asList("ent_", "dec_", "car_").contains(type)) {
      if (compatible.checkData(type, this.typeData)) return 1;
      else {
        errors.registerError(lexeme, errors.createTokenSemantic(),
                  String.valueOf(line + 1), "Incompatibilidad de tipo " + this.typeData);
        this.numberPassOperation = 0;
        this.typeData = null;
        return 2;
      }
    } else return 0;
  }
  
  private int checkOperations(String lexeme, String token, int line) {
    if (token.contains("AMR")) {
      if (operations.checkData(lexeme, this.typeData)) return 1;
      else {
        errors.registerError(lexeme, errors.createTokenSemantic(),
                String.valueOf(line + 1), "Operación no permitida de " + this.typeData);
        this.numberPassOperation = 0;
        this.typeData = null;
        return 2;
      }
    } return 0;
  }
  
  private void checkErrorsSemantic(String lexeme, String token, int line, int index) {
    switch (numberPassOperation) {
      case 0:
        switch (this.checkVariableIndefine(lexeme, token, index, line)) {
          case 0:
            this.numberPassOperation = 0;
            this.typeData = null;
            break;
          case 1:
            this.numberPassOperation ++;
            this.typeData = this.simbols.getData(index, "tipo");
            break;
        }
        break;
      case 1:
        if (lexeme.equals("=")) this.numberPassOperation ++;
        else {
          this.typeData = null;
          this.numberPassOperation = 0;
        }
        break;
      case 2:
        switch (this.checkVariableIndefine(lexeme, token, index, line)) {
          case 0:
          case 1:
            switch (this.checkTypeData(lexeme, index, line)) {
              case 0:
                this.typeData = null;
                this.numberPassOperation = 0;
                break;
              case 1:
                this.numberPassOperation ++;
                break;
            }
            break;
        }
        if (lexeme.equals(";")) {
          this.numberPassOperation = 0;
          this.typeData = null;
        }
        break;
      case 3:
        switch (this.checkOperations(lexeme, token, line)) {
          case 0:
            this.numberPassOperation = 0;
            this.typeData = null;
          case 1: 
            this.numberPassOperation --;
            break;
        }
        break;
    }
  }
  
  private void assingIdentificator(String lexeme, String token, int index) {
    if (assingData) {
      String type = getTypeLexeme(lexeme);
      if (type != null) {
        assingType = type;
        assingData = !assingData;
      } else resetFuntion();
    } else {
      if (token.contains("IDE")) {
        setTypeData(assingType, index);
        assingData = !assingData;        
        this.paramsFuntion.add(assingType);
        assingType = null;
        numberPassFuntion ++;
      } else {
        resetFuntion();
      }
    }
  }
  
  private void resetFuntion() {
    this.numberPassFuntion = 0;
    this.nameFuntion = null;
    this.assingData = true;
    this.typeFuntion = null;
    this.assingType = null;
    this.paramsFuntion = new ArrayList<>();
  }
  
  private void checkFuntionsSemantic(String lexeme, String token, int index) {
    switch (this.numberPassFuntion) {
      case 0:
        String type = getTypeLexeme(lexeme);
        if (type != null) {
          this.typeFuntion = type;
          this.numberPassFuntion ++;
        } else {
          resetFuntion();
        }
        break;
      case 1:
        numberPassFuntion ++;
        this.nameFuntion = lexeme;
        break;
      case 2:
        if (lexeme.equals("(")) numberPassFuntion ++;
        else resetFuntion();
        break;
      case 3:
        assingIdentificator(lexeme, token, index);
        break;
      case 4:
        if (lexeme.equals(",") || lexeme.equals(")")) {
          if (lexeme.equals(",")) numberPassFuntion --;
          if (lexeme.equals(")")) {
            this.returnFuntion = this.nameFuntion;
            returnFuntions.put( this.nameFuntion, this.typeFuntion);
            funtions.put(nameFuntion, paramsFuntion);
            System.err.println("Guarado de : " + this.nameFuntion);
            resetFuntion();
          }
        } else {
          resetFuntion();
        }
        break;
    }
  }
  
  private void checkReturnFuntion(String lexeme, String token, int line) {
    switch (this.numberPassReturn) {
      case 0:
        if (lexeme.endsWith("return")) {
          this.numberPassReturn ++;
        } else {
          this.numberPassReturn = 0;
        }
        break;
      case 1:
        int index = this.simbols.getIndexData(lexeme, "lexema");
        String type = this.simbols.getData(index, "tipo");
        System.out.println(this.returnFuntion + type + token);
        System.out.println(Arrays.toString(this.returnFuntions.keySet().toArray()));
        String typeFuntion = this.returnFuntions.get(this.returnFuntion);
        if (!type.isEmpty()) {
          if (compatible.checkData(type, typeFuntion)) {
            this.typeFuntions.put(this.returnFuntion, lexeme);
            this.numberPassReturn ++;
          } else {
            errors.registerError(lexeme, errors.createTokenSemantic(),
              String.valueOf(line + 1), "Incompatibilidad de tipo " + typeFuntion);
            this.numberPassReturn = 0;
          }
        } else {
          this.numberPassReturn = 0;
        }
        break;
      case 2:
        if (lexeme.equals(";")) {
          this.numberPassReturn = 0;
          this.returnFuntion = null;
        } else {
          this.numberPassReturn = 0;
        }
        break;
    }
  }
  
  private void resetFunctionCall() {
    this.numberPassCall = 0;
    this.rootType = null;
    this.callName = null;
    this.paramsSize = 0;
  }
  
  private void checkFuntionCall(String lexeme, String token, int line, int index) {
    switch (numberPassCall) {
      case 0:
        switch (this.checkVariableIndefine(lexeme, token, index, line)) {
          case 0:
            resetFunctionCall();
            break;
          case 1:
            this.numberPassCall ++;
            this.rootType = this.simbols.getData(index, "tipo");
            break;
        }
        break;
      case 1:
        if (lexeme.equals("=")) this.numberPassCall ++;
        else resetFunctionCall();
        break;
      case 2:
        if (returnFuntions.containsKey(lexeme)) {
          String type = returnFuntions.get(lexeme);
          String returnLexeme = this.typeFuntions.get(lexeme);
          if (compatible.checkData(type, this.rootType)) {
            this.numberPassCall ++;
            this.callName = lexeme;
          }
          else {
            errors.registerError(returnLexeme, errors.createTokenSemantic(),
              String.valueOf(line + 1), "Incompatibilidad de tipo " + this.rootType);
            resetFunctionCall();
          }
        } else resetFunctionCall();
        break;
      case 3:
        if (lexeme.equals("(")) {
          numberPassCall ++;
        } else resetFunctionCall();
        break;
      case 4:
        String type = simbols.getData(index, "tipo");
        String typeFuntionReturn = funtions.get(this.callName).get(this.paramsSize);
        if (lexeme.equals(")") && funtions.get(this.callName).isEmpty()) {
          this.numberPassCall += 2;
        }
        if (!type.isEmpty()) {
          if (compatible.checkData(type, typeFuntionReturn)) {
            this.numberPassCall ++;
            this.paramsSize ++;
          }
          else {
            errors.registerError(lexeme, errors.createTokenSemantic(),
                      String.valueOf(line + 1), "Incompatibilidad de tipo " + typeFuntionReturn);
            resetFunctionCall();
          }
        } else resetFunctionCall();
        break;
      case 5:
        if (lexeme.equals(",") || lexeme.equals(")")) {
          if (lexeme.equals(",")) numberPassCall --;
          if (lexeme.equals(")")) numberPassCall ++;
        } else resetFunctionCall();
        break;
      case 6:
        if (lexeme.equals(";")) {
         resetFunctionCall();
        }
        break;
    }
  }
}