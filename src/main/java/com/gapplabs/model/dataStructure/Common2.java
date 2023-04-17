package com.gapplabs.model.dataStructure;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Common2 {
    protected ArrayList<String[]> structure;
    protected final String[] header;
    protected final int COLUMN_SIZE;

    public Common2(String[] header) {

        this.structure = new ArrayList<>();
        this.header = header;
        COLUMN_SIZE = header.length;
    }

    protected void addRegister(String[] row) throws Exception {
        if (row.length != this.COLUMN_SIZE) {
            throw new Exception("Columns size of the row does not match to the structure " + this.getClass());
        } else {
            this.structure.add(row);
        }
    }

    public ArrayList<String[]> getStructure() {
        return (ArrayList<String[]>) this.structure.clone();
    }

    public String[] getHeader() {
        return this.header.clone();
    }

    public void clearStructure() {
        this.structure.clear();
    }

    public String[] getColum(int position) {
        //Extracting a value from each row and storing them in a static array
        return (String[]) this.structure.stream().map(row -> row[position]).toArray();
    }

    public String[] getRow(int position) {
        //Returning a clone of the row of the structure
           return  this.structure.get(position).clone();
    }

}
