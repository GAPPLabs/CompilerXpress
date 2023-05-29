package com.gapplabs.model.dataStructure;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Ensambler extends Common {

  private final static String[] nameData = {"etiqueta", "menonico", "objeto", "fuente"};
  public final static String[] nameTable = {"etiqueta", "menonico", "objeto", "fuente"};

  public Ensambler() {
    this.createData(nameData);
  }

  public void registerEnsambler(String etiqueta, String menonico, String objeto, String fuente) {
    this.addData(this.createMapData(nameData, etiqueta, menonico, objeto, fuente));
  }

  public String transforEnsambler() {
    String result = "";
    Map<String, Integer> maxLength = new HashMap<String, Integer>() {
      {
        for (String key : nameTable) {
          int max = Collections.max(getList(key), Comparator.comparing(String::length)).length();
          put(key, key.length() > max ? key.length() + 3 : max + 3);
        }
      }
    };
    for (int i = -1; i < this.getSize(); i++) {
      for (String key : nameTable) {
        if (i == -1) result += complement(key, " ", maxLength.get(key)) + "   ";
        else result += complement(this.getData(i, key), " ", maxLength.get(key)) + "   ";
      }
      result += "\n";
    }
    return result;
  }

  private String complement(String word, String complement, int maxComplement) {
    if (word.length() == maxComplement) return word;
    else  return word + complement.repeat(maxComplement - word.length());
  }

  public void clearData() {
    this.resetData();
  }
}
