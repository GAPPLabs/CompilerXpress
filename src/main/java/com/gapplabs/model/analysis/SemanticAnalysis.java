package com.gapplabs.model.analysis;

import com.gapplabs.model.dataStructure.Simbols;

public class SemanticAnalysis {
  
  private Simbols simbols;
  
  private int numberPassFuntion;
  private boolean initType;
  private boolean setType;
  private String typeNow;
  
  private boolean asignacionTipoDatos = false;
  private boolean continuarComa = false;
  private String tipoDatoActual = "";
  private String tipoDatoSentencia = "";
  private int numeroPasoSemantico = 0;
  private int contadorErrorSemantico = 0;

  public SemanticAnalysis(Simbols simbols) {
    this.simbols = simbols;
    numberPassFuntion = 0;
    initType = true;
    setType = false;
    typeNow = null;
  }
  
  public void analysisSemantic(String lexeme, String token, int line) {
    assingTypeData(token);
    assingTypeIdentificator(lexeme, token);
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
}
