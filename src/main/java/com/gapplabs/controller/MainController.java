package com.gapplabs.controller;

import com.gapplabs.model.analysis.WordAnalysis;
import com.gapplabs.model.dataStructure.Simbols;
import com.gapplabs.model.dataStructure.Errors;
import com.gapplabs.view.MainView;
import com.gapplabs.view.plugin.ToolTable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;

public class MainController implements ActionListener {
    private MainView mainView;
    private WordAnalysis wordAnalysis;
    private ToolTable tool;

    public MainController(MainView mainView, WordAnalysis wordAnalysis) {
        System.out.println("arrranca el maincontroller");
        this.mainView = mainView;
        this.wordAnalysis = wordAnalysis;
        this.tool = new ToolTable(mainView);
        // Binding the abstract buttons to the controller
        for (AbstractButton abstractButton : mainView.getAllButtons()){
            abstractButton.addActionListener(this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mainView.getAnalyze()) {
            System.out.println("Boton1 presionado");
            this.wordAnalysis.compile(this.mainView.textArea.getText());
            this.mainView.getTableSimbols().setModel(tool.getModelStructure(Simbols.nameTable, 
                    wordAnalysis.getSimbols().getStructure()));
            this.mainView.getTableErrors().setModel(tool.getModelStructure(Errors.nameTable, 
                    wordAnalysis.getErros().getStructure()));
        }
        if (e.getSource() == mainView.getExit()) System.exit(0);
        if (e.getSource() == mainView.getReset()) {
          this.mainView.getTableSimbols().setModel(new DefaultTableModel());
          this.mainView.getTableErrors().setModel(new DefaultTableModel());
        }
        if (e.getSource() == this.mainView.getOpen()) this.mainView.getTextArea().setText(tool.selectionFile());
    }
}
