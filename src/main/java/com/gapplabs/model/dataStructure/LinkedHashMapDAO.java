package com.gapplabs.model.dataStructure;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

public class LinkedHashMapDAO  <T> {
    private LinkedHashMap<String, T> linkedHashMap;

    public LinkedHashMapDAO() {
        this.linkedHashMap = new LinkedHashMap<>();
    }

    public boolean agregar(String clave, T instance) {
        if (this.linkedHashMap.containsKey(clave)) {
            return false;
        }
        this.linkedHashMap.put(clave, instance);
        return true;
    }

    public T obtener(String clave) {
        return this.linkedHashMap.get(clave);
    }

}
