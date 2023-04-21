package com.gapplabs.model.dataStructure;

public class Errors extends Common {
  
  private final static String [] nameData = {"lexema", "token", "linea", "descripcion"};
  public final static String [] nameTable = {"lexema", "token", "linea", "descripcion"};
  private int errorSemantic;

  public Errors() {
    this.createData(nameData);
    this.errorSemantic = 0;
  }
  
  public boolean registerError(String lexema, String token, String linea, String descripcion) {
    if (!this.checkData(lexema, "lexema")) {
      this.addData(this.createMapData(nameData, lexema, token,
              linea, descripcion));
      return true;
    } else {
      int index = this.getIndexData(lexema, "lexema");
      String lineBefore = this.getData(index, "linea");
      this.updateData(this.createMapData(new String [] {"linea"}, 
              checkLine(linea, lineBefore)), index);
      return false;
    }
  }
  
  public String createToken() {
    return "ERL" + (this.getSize() + 1);
  }
  
  public String createTokenSemantic() {
    this.errorSemantic ++;
    return "ERsem" + (this.errorSemantic);
  }
  
  private String checkLine (String line, String lineBefore) {        
      if (!lineBefore.contains(line)) {
          return lineBefore + ", " + line; 
      }
      return line;
  }
  
  public void clearData() {
    this.resetData();
    this.errorSemantic = 0;
  }
}
