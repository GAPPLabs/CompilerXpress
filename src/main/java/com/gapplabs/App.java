package com.gapplabs;

import com.gapplabs.controller.MainController;
import com.gapplabs.model.analysis.WordAnalysis;
import com.gapplabs.view.ViewMain;

public class App {
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                WordAnalysis wordAnalysis = new WordAnalysis();
                ViewMain view = new ViewMain();
                // ControllerView controller = new ControllerView(view);
                MainController mainController = new MainController(view, wordAnalysis );
                view.setVisible(true);
            }
        });
    }
}