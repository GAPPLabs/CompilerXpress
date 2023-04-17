package com.gapplabs.model.dataStructure;

public class Errors extends Common {
  
  private final static String [] nameData = {"lexema", "token", "linea", "descripcion"};

  public Errors() {
    this.createData(nameData);
  }
  
  public boolean registerError(String lexema, String token, String linea, String descripcion) {
    if (!this.checkData(lexema, "lexema")) {
      this.addData(this.createMapData(nameData, lexema, token,
              linea, descripcion));
      return true;
    } else {
      int index = this.getIndexData(lexema, "lexema");
      String lineBefore = this.getData(index, "linea").get("linea");
      this.updateData(this.createMapData(new String [] {"linea"}, 
              checkLine(linea, lineBefore)), index);
      return false;
    }
  }
  
  private String checkLine (String line, String lineBefore) {        
      if (!lineBefore.contains(line)) {
          return lineBefore + ", " + line; 
      }
      return line;
  }
}
