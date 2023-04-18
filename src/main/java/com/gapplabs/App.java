package com.gapplabs;

import com.gapplabs.view.ControllerView;
import com.gapplabs.view.ViewMain;

public class App {
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
              ViewMain view = new ViewMain();
              ControllerView controller = new ControllerView(view);
              view.setVisible(true);
            }
        });
    }
}