package com.gapplabs.controller;

import com.gapplabs.model.analysis.WordAnalysis;
import com.gapplabs.model.dataStructure.*;
import com.gapplabs.model.Compiler;
import com.gapplabs.view.ViewMain;
import com.gapplabs.view.plugin.ToolTable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

public class MainController implements ActionListener {
    private ViewMain mainView;
    private WordAnalysis wordAnalysis;
    private ToolTable tool;
    private Compiler compiler;

    public MainController(ViewMain mainView, WordAnalysis wordAnalysis) {
        System.out.println("arrranca el maincontroller");
        this.compiler = new Compiler();

        this.mainView = mainView;
        this.wordAnalysis = wordAnalysis;
        this.tool = new ToolTable(mainView);
        // Binding the abstract buttons to the controller
        for (AbstractButton abstractButton : mainView.getAllButtons()){
            abstractButton.addActionListener(this);
        }

        this.mainView.getTableSimbols().setModel(new DefaultTableModel(new String[]{"Lexema", "Tipo", "Descripción"}, 0));
        this.mainView.getTableErrors().setModel(new DefaultTableModel(new String[]{"Token", "Lexema", "Descripción"}, 0));
        this.mainView.getTableTriplo().setModel(new DefaultTableModel());
    }

    public void setModelData(DefaultTableModel model, Collection<?> colection){
        if (colection.getClass() == Simbol.class){
            colection.forEach(simbol -> {
                Simbol simbol2 = (Simbol) simbol;
                model.addRow(new Object[]{simbol2.getLexema()});
            });
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mainView.getAnalyze()) {
            System.out.println("Boton1 presionado");
            this.wordAnalysis.compile(this.mainView.textArea.getText());

            this.compiler.compile(this.mainView.textArea.getText());


//            this.mainView.getTableSimbols().setModel(tool.getModelStructure(Simbols.nameTable,
//                    wordAnalysis.getSimbols().getStructure()));
//            this.mainView.getTableErrors().setModel(tool.getModelStructure(Errors.nameTable,
//                    wordAnalysis.getErros().getStructure()));
//            this.mainView.getTableTriplo().setModel(tool.getModelStructure(Intermediates.nameTable,
//                    wordAnalysis.getIntermediates().getStructure()));
        }
//        if (e.getSource() == mainView.getExit()) System.exit(0);
//        if (e.getSource() == mainView.getReset()) {
//          this.mainView.getTableSimbols().setModel(new DefaultTableModel());
//          this.mainView.getTableErrors().setModel(new DefaultTableModel());
//          this.mainView.getTableTriplo().setModel(new DefaultTableModel());
//        }
//        if (e.getSource() == this.mainView.getOpen()) this.mainView.getTextArea().setText(tool.selectionFile());
//        if (e.getSource() == this.mainView.getSave()) tool.saveFile(this.wordAnalysis.getIntermediates().transforIntermediate());
//        if (e.getSource() == this.mainView.getToken()) tool.saveFile(this.wordAnalysis.getTokens().getTokesLine());
    }
}
