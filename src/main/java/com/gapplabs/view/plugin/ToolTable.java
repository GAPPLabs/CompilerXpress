package com.gapplabs.view.plugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class ToolTable {

  private JFrame frame;

  public ToolTable(JFrame frame) {
    this.frame = frame;
  }

  public DefaultTableModel getModelStructure(String[] nameData, Map<String, List<String>> structure) {
    DefaultTableModel model = new DefaultTableModel();
    for (String name : nameData) {
      model.addColumn(name, structure.get(name).toArray());

    }
    return model;
  }

  public String selectionFile() {
    JFileChooser fileChooser = new JFileChooser();
    FileNameExtensionFilter filterFile = new FileNameExtensionFilter("Archivo fuente (.txt)", "txt");
    fileChooser.setDialogTitle("Abrir archivo:");
    fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
    fileChooser.setApproveButtonText("Seleccionar");
    fileChooser.setApproveButtonToolTipText("Selecciona el archivo");
    fileChooser.setMultiSelectionEnabled(false);
    fileChooser.setFileFilter(filterFile);
    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

    String fileContent = "";
    if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
      BufferedReader readFile = null;
      String fileSelected = fileChooser.getSelectedFile().getAbsolutePath();

      try {
        FileReader storage = new FileReader(fileSelected);
        readFile = new BufferedReader(storage);
        String readLine = "";

        while ((readLine = readFile.readLine()) != null) {
          fileContent += fileContent.isEmpty() ? readLine : "\n" + readLine;
        }

        readFile.close();
        return fileContent;
      } catch (IOException error) {
        JOptionPane.showMessageDialog(frame, "Error al leer el archivo", "Error:", JOptionPane.ERROR_MESSAGE);
      }
    }
    return fileContent;
  }

  public void saveFile(String result) {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Guardar archivo:");
    fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
    fileChooser.setApproveButtonText("Guardar");
    fileChooser.setApproveButtonToolTipText("Guardar el archivo");
    fileChooser.setMultiSelectionEnabled(false);
    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

    if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
      PrintWriter writeFile = null;
      String fileSelected = fileChooser.getSelectedFile().getAbsolutePath();

      try {
        FileWriter storage = new FileWriter(fileSelected);
        writeFile = new PrintWriter(storage);
        writeFile.print(result);
        writeFile.close();
      } catch (IOException error) {
        JOptionPane.showMessageDialog(frame, "Error al escribir el archivo", "Error:", JOptionPane.ERROR_MESSAGE);
      }
    }
  }
}
