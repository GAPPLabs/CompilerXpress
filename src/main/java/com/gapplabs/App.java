package com.gapplabs;

import com.gapplabs.controller.MainController;
import com.gapplabs.model.Compiler;
import com.gapplabs.model.analysis.WordAnalysis;
import com.gapplabs.view.MainView;

public class App {
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                WordAnalysis wordAnalysis = new WordAnalysis();
                Compiler compiler = new Compiler();
                MainView view = new MainView();
                MainController mainController = new MainController(view, wordAnalysis, compiler);
                view.setVisible(true);
            }
        });
    }
}