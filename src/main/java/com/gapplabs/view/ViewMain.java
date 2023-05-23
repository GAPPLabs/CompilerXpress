package com.gapplabs.view;

import com.formdev.flatlaf.FlatDarkLaf;
import com.gapplabs.view.plugin.EnumerateText;
import java.awt.event.MouseEvent;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class ViewMain extends javax.swing.JFrame {

  public ViewMain() {
    initComponents();
    setSettings();
    setLookAndFell();
  }

  private void setSettings() {
    scrollTextArea.setRowHeaderView(new EnumerateText(textArea));
    scrollTextAreaOptimizer.setRowHeaderView(new EnumerateText(textAreaOptimizer));
  }

  private void setLookAndFell() {
    try {
      UIManager.setLookAndFeel(new FlatDarkLaf());
      SwingUtilities.updateComponentTreeUI(this);
    } catch (UnsupportedLookAndFeelException ex) {
      System.err.println("Failed to initialize LaF");
    }
  }

  public JButton getAnalyze() {
    return analyze;
  }

  public JMenuItem getExit() {
    return exit;
  }

  public JMenuItem getOpen() {
    return open;
  }

  public JButton getReset() {
    return reset;
  }

  public JButton getSave() {
    return save;
  }
  
  public JButton getToken() {
    return token;
  }
  
  public JButton getAsm() {
    return asm;
  }
  
  public JButton getOptimisation() {
    return optimisation;
  }

  public JScrollPane getScrollTextArea() {
    return scrollTextArea;
  }

  public JTable getTableTriplo() {
    return tableTriplo;
  }

  public JTable getTableErrors() {
    return tableErrors;
  }

  public JTable getTableSimbols() {
    return tableSimbols;
  }
  
  public JTable getTableAsm() {
    return tableAsm;
  }

  public JTextArea getTextArea() {
    return textArea;
  }
  
  public JTextArea getTextAreaOptimizer() {
    return textAreaOptimizer;
  }

  public AbstractButton[] getAllButtons() {
    AbstractButton componetsArray[] = {this.analyze, this.exit, this.open, this.reset, this.save, this.token, this.asm, this.optimisation};
    return componetsArray;
  }

  @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
        javax.swing.JPanel jPanel1 = new javax.swing.JPanel();
        javax.swing.JPanel jPanel2 = new javax.swing.JPanel();
        javax.swing.JLabel jLabel2 = new javax.swing.JLabel();
        scrollTextArea = new javax.swing.JScrollPane();
        textArea = new javax.swing.JTextArea();
        scrollTextAreaOptimizer = new javax.swing.JScrollPane();
        textAreaOptimizer = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        javax.swing.JPanel jPanel3 = new javax.swing.JPanel();
        javax.swing.JLabel jLabel3 = new javax.swing.JLabel();
        javax.swing.JScrollPane jScrollPane2 = new javax.swing.JScrollPane();
        tableSimbols = new javax.swing.JTable() {
            public String getToolTipText(MouseEvent e) {
                String tip = null;
                java.awt.Point p = e.getPoint();
                int rowIndex = rowAtPoint(p);
                int colIndex = columnAtPoint(p);

                try {
                    tip = getValueAt(rowIndex, colIndex).toString();
                } catch (RuntimeException e1) {
                    System.out.println("Error");
                }

                return tip;
            }
        };
        javax.swing.JLabel jLabel4 = new javax.swing.JLabel();
        javax.swing.JScrollPane jScrollPane3 = new javax.swing.JScrollPane();
        tableErrors = new javax.swing.JTable() {
            public String getToolTipText(MouseEvent e) {
                String tip = null;
                java.awt.Point p = e.getPoint();
                int rowIndex = rowAtPoint(p);
                int colIndex = columnAtPoint(p);

                try {
                    tip = getValueAt(rowIndex, colIndex).toString();
                } catch (RuntimeException e1) {
                    System.out.println("Error");
                }

                return tip;
            }
        };
        javax.swing.JLabel jLabel5 = new javax.swing.JLabel();
        javax.swing.JScrollPane jScrollPane4 = new javax.swing.JScrollPane();
        tableTriplo = new javax.swing.JTable() {
            public String getToolTipText(MouseEvent e) {
                String tip = null;
                java.awt.Point p = e.getPoint();
                int rowIndex = rowAtPoint(p);
                int colIndex = columnAtPoint(p);

                try {
                    tip = getValueAt(rowIndex, colIndex).toString();
                } catch (RuntimeException e1) {
                    System.out.println("Error");
                }

                return tip;
            }
        };
        javax.swing.JLabel jLabel7 = new javax.swing.JLabel();
        javax.swing.JScrollPane jScrollPane5 = new javax.swing.JScrollPane();
        tableAsm = new javax.swing.JTable() {
            public String getToolTipText(MouseEvent e) {
                String tip = null;
                java.awt.Point p = e.getPoint();
                int rowIndex = rowAtPoint(p);
                int colIndex = columnAtPoint(p);

                try {
                    tip = getValueAt(rowIndex, colIndex).toString();
                } catch (RuntimeException e1) {
                    System.out.println("Error");
                }

                return tip;
            }
        };
        analyze = new javax.swing.JButton();
        reset = new javax.swing.JButton();
        save = new javax.swing.JButton();
        token = new javax.swing.JButton();
        asm = new javax.swing.JButton();
        optimisation = new javax.swing.JButton();
        javax.swing.JMenuBar jMenuBar1 = new javax.swing.JMenuBar();
        javax.swing.JMenu jMenu1 = new javax.swing.JMenu();
        open = new javax.swing.JMenuItem();
        javax.swing.JPopupMenu.Separator jSeparator1 = new javax.swing.JPopupMenu.Separator();
        exit = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CompilerXpress - GAPP LABS");

        jLabel1.setText("Analizador de expresiones regulares");

        jPanel1.setLayout(new java.awt.GridLayout(1, 2, 8, 0));

        jLabel2.setText("Editor de codigo:");

        textArea.setColumns(20);
        textArea.setRows(5);
        scrollTextArea.setViewportView(textArea);

        textAreaOptimizer.setEditable(false);
        textAreaOptimizer.setColumns(20);
        textAreaOptimizer.setRows(5);
        scrollTextAreaOptimizer.setViewportView(textAreaOptimizer);

        jLabel6.setText("Texto optimizado:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(scrollTextArea, javax.swing.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(scrollTextAreaOptimizer)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollTextArea, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addGap(8, 8, 8)
                .addComponent(scrollTextAreaOptimizer, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.add(jPanel2);

        jLabel3.setText("Tabla de simbolos:");

        tableSimbols.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tableSimbols);

        jLabel4.setText("Tabla de errores:");

        tableErrors.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(tableErrors);

        jLabel5.setText("Tabla de triplo:");

        tableTriplo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(tableTriplo);

        jLabel7.setText("Tabla de ensamblador:");

        tableAsm.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane5.setViewportView(tableAsm);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.add(jPanel3);

        analyze.setText("Analizar");

        reset.setText("Reiniciar");

        save.setText("Guardar triplo");

        token.setText("Guardar token");

        asm.setText("Guardar ensamblador");

        optimisation.setText("Guardar optimización");

        jMenu1.setText("Archivo");

        open.setText("Abrir archivo");
        jMenu1.add(open);
        open.getAccessibleContext().setAccessibleName("");

        jMenu1.add(jSeparator1);

        exit.setText("Salir");
        jMenu1.add(exit);
        exit.getAccessibleContext().setAccessibleName("");

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(analyze)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(reset)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(optimisation)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(token)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(save)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(asm))
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(8, 8, 8))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(analyze)
                    .addComponent(reset)
                    .addComponent(save)
                    .addComponent(token)
                    .addComponent(asm)
                    .addComponent(optimisation))
                .addGap(8, 8, 8))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton analyze;
    public javax.swing.JButton asm;
    public javax.swing.JMenuItem exit;
    private javax.swing.JLabel jLabel6;
    public javax.swing.JMenuItem open;
    public javax.swing.JButton optimisation;
    public javax.swing.JButton reset;
    public javax.swing.JButton save;
    private javax.swing.JScrollPane scrollTextArea;
    private javax.swing.JScrollPane scrollTextAreaOptimizer;
    public javax.swing.JTable tableAsm;
    public javax.swing.JTable tableErrors;
    public javax.swing.JTable tableSimbols;
    public javax.swing.JTable tableTriplo;
    public javax.swing.JTextArea textArea;
    public javax.swing.JTextArea textAreaOptimizer;
    public javax.swing.JButton token;
    // End of variables declaration//GEN-END:variables
}
