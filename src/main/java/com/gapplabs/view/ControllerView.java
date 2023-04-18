package com.gapplabs.view;

import java.awt.event.ActionEvent;

public class ControllerView {
  
  private final ViewMain view;

  public ControllerView(ViewMain view) {
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
