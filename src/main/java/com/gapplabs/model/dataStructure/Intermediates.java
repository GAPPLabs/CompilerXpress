package com.gapplabs.model.dataStructure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Intermediates extends Common {

  private final static String[] nameData = {"renglon", "objeto", "fuente", "operador", "etiqueta"};
  public final static String[] nameTable = {"renglon", "objeto", "fuente", "operador"};

  public Intermediates() {
    this.createData(nameData);
  }

  public void registerIntermediate(String objeto, String fuente, String operado, String etiqueta) {
    this.addData(this.createMapData(nameData, String.valueOf(this.getSize() + 1),
            objeto, fuente, operado, etiqueta));
  }
  
  public Map<String, List<Map<String, String>>> transformBlockCode(){
    Map<String, List<Map<String, String>>> mapData = new LinkedHashMap<>() {
      {
        for (int i = 0; i < getSize(); i ++) {
          List<Map<String, String>> listData = new ArrayList<>(Arrays.asList(getData(i, nameData)));
          if (i + 1 == getSize()) {
            put(String.valueOf(i), listData);
            break;
          }
          
          for (int a = i + 1; a < getSize(); a ++) {
            Map<String, String> data = getData(a, nameData);
            if (data.get(nameData[4]).equals("")) {
              listData.add(data);
            } else {
              put(String.valueOf(i), listData);
              i = a - 1;
              break;
            }
          }
        }
      }
    };
    return mapData;
  }

  public String transforIntermediate() {
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
