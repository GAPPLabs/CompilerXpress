package com.gapplabs.model.dataStructure;

public class Simbols extends Common {
  
  private final static String [] nameData = {"lexema", "token", "tipo"};

  public Simbols() {
    this.createData(nameData);
  }
  
  public boolean registerSimbol(String lexema, String token) {
    if (!this.checkData(lexema, "lexema")) {
      this.addData(this.createMapData(nameData, lexema, token, 
              ""));
      return true;
    } else {
      return false;
    }
  } 
}
