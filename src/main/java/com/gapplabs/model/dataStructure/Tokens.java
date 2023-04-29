package com.gapplabs.model.dataStructure;

public class Tokens extends Common {
  
  private final static String [] nameData = {"linea"};
  private String contentToken;
  private boolean initLine;

  public Tokens() {
    this.createData(nameData);
    contentToken = "";
    initLine = true;
  }
  
  public void writeToken (String token) {
    contentToken += initLine ? token : " " + token;
    if (initLine) initLine = false;
  }
  
  public void saveWriteToken() {
    this.addData(this.createMapData(new String[] {"linea"}, contentToken));
    contentToken = "";
    initLine = true;
  }
  
  public String getTokesLine() {
    return String.join("\n", this.getList("linea"));
  }
  
  public void clearData() {
    this.resetData();
  }
}
