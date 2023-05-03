package com.gapplabs;

import com.gapplabs.controller.MainController;
import com.gapplabs.model.analysis.WordAnalysis;
import com.gapplabs.model.dataStructure.RegexEnum;
import com.gapplabs.view.ViewMain;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App {
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                WordAnalysis wordAnalysis = new WordAnalysis();
//                Compiler compiler = new Compiler();
                ViewMain view = new ViewMain();
                MainController mainController = new MainController(view, wordAnalysis);
                view.setVisible(true);
            }
        });
    }
}