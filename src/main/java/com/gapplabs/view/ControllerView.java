package com.gapplabs.view;

import java.awt.event.ActionEvent;

public class ControllerView {
  
  private final MainView view;

  public ControllerView(MainView view) {
    this.view = view;
    setEvents();
  }
  
  private void setEvents() {
    view.exit.addActionListener((ActionEvent e) -> {exit();});
  }
  
  private void exit() {
    System.exit(0);
  }
}
