package com.gapplabs.controller;

import com.gapplabs.model.analysis.WordAnalysis;
import com.gapplabs.view.MainView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainController implements ActionListener {
    private MainView mainView;
    private  WordAnalysis wordAnalysis;

    public MainController(MainView mainView, WordAnalysis wordAnalysis) {
        System.out.println("arrranca el maincontroller");
        this.mainView = mainView;
        this.wordAnalysis = wordAnalysis;
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
        }
    }
}
