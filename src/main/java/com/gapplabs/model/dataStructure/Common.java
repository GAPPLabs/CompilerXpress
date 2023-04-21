package com.gapplabs.model.dataStructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class Common {
  private final Map<String, List<String>> structure;
  private int size;  

  public Common() {
    structure = new HashMap<>();
    size = 0;
  }
  
  public void createData(String... nameData) {
    for (String name : nameData) {
      structure.put(name, new ArrayList<>());
    }
  }
  
  public void createData(String [] nameData, List<String>... data) {
    IntStream.range(0, nameData.length).forEach(i -> {
      structure.put(nameData[i], data[i]);
    });
  }
  
  public void addData(Map<String, String> data) {
    for (String key : data.keySet()) {
      structure.get(key).add(data.get(key));
    }
    size ++;
  }
  
  public String getData(int index, String nameData) {
    return structure.get(nameData).get(index);
  }
  
  public Map<String, String> getData(int index, String... nameData) {
    Map<String, String> data = new HashMap<>();
    for (String key : nameData) {
      data.put(key, structure.get(key).get(index));
    }
    return data;
  }
  
  public void updateData(Map<String, String> data, int index) {
    for (String key : data.keySet()) {
      structure.get(key).set(index, data.get(key));
    }
  }
  
  public boolean checkData(String data, String nameData) {   
    return structure.get(nameData).contains(data);
  }
  
  public void deleteData(int index) {
    for (String key : structure.keySet()) {
      structure.get(key).remove(index);
    }
    size --;
  }
  
  public void resetData(){
    for (String key : structure.keySet()) {
      structure.get(key).clear();
    }
    size = 0;
  }
  
  public Map<String, String> createMapData(String [] nameData, String... data) {
    Map<String, String> map = new HashMap<>();
    IntStream.range(0, nameData.length).forEach(i -> {
      map.put(nameData[i], data[i]);
    });
    return map;
  }
  
  public void printData() {
    int numRows = structure.values().stream().mapToInt(List::size).max().orElse(0);
    for (String key : structure.keySet()) {
    System.out.print(key + ": ");
    List<String> column = structure.get(key);
    // Iterar por cada fila de la columna y obtener el valor o una cadena vacía si la fila no tiene valores
    for (int row = 0; row < numRows; row++) {
        String value = "";
        if (row < column.size()) {
            value = column.get(row);
        }
        System.out.print(value + "\t");
    }
    System.out.println();
}
  }
  
  public int getIndexData(String data, String nameData) {
    return structure.get(nameData).indexOf(data);
  }
  
  public List<String> getList(String nameData) {
    return structure.get(nameData);
  }

  public Map<String, List<String>> getStructure() {
    return structure;
  }

  public int getSize() {
    return size;
  }
}
