package com.gapplabs.model.dataStructure;

public class Expressions extends Common {
  
  private final static String [] nameData = {"expresion", "prefijo", "contador"};

  public Expressions() {
    this.createData(nameData);
    initData();
  }
  
  private void initData() {
    this.addData(this.createMapData(nameData, "(\\()|(\\))|(\\})|(\\{)|(,)|(;)", "SEP", "0"));
    this.addData(this.createMapData(nameData, "(\\+)|(\\-)|(\\*)|(\\/)|(\\%)", "AMR", "0"));
    this.addData(this.createMapData(nameData, "(<=)|(>=)|(==)|(!=)|(<)|(>)", "REL", "0"));
    this.addData(this.createMapData(nameData, "=", "ASG", "0"));
    this.addData(this.createMapData(nameData, "return", "RET", "0"));
    this.addData(this.createMapData(nameData, "^ISC.*R$", "IDE", "0"));
    this.addData(this.createMapData(nameData, "^(-)?5\\d*5$", "ENT", "0"));
      this.addData(this.createMapData(nameData, "^-?\\d+\\.5\\d*5$", "DEC", "0"));
    this.addData(this.createMapData(nameData, "^(ent|dec|car)_$", "TDS", "0"));
  }
  
  public String createToken(int index) {
    return this.getData(index, "prefijo") + (Integer.parseInt(this.getData(index, "contador")) + 1);
  } 
  
  public void incrementCount(int index) {
    int count = Integer.parseInt(this.getData(index, "contador"));
    this.updateData(this.createMapData(new String [] {"contador"}, String.valueOf(count + 1)), index);
  }
}
