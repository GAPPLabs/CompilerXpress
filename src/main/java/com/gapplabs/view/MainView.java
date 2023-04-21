package com.gapplabs.view;

import com.formdev.flatlaf.FlatDarkLaf;
import com.gapplabs.view.plugin.EnumerateText;
import java.awt.event.MouseEvent;
import javax.swing.*;

public  class MainView extends javax.swing.JFrame {

    public MainView() {
        initComponents();
        setSettings();
        setLookAndFell();
    }

    public void comenzar(){
        initComponents();
        setSettings();
        setLookAndFell();
    }

    private void setSettings() {
        scrollTextArea.setRowHeaderView(new EnumerateText(textArea));
    }

    private void setLookAndFell() {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (UnsupportedLookAndFeelException ex) {
            System.err.println("Failed to initialize LaF");
        }
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
        analyze = new javax.swing.JButton();
        reset = new javax.swing.JButton();
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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(scrollTextArea, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(scrollTextArea, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2);

        jLabel3.setText("Tabla de simbolos:");

        tableSimbols.setModel(new javax.swing.table.DefaultTableModel());
        jScrollPane2.setViewportView(tableSimbols);

        jLabel4.setText("Tabla de errores:");

        tableErrors.setModel(new javax.swing.table.DefaultTableModel());
        jScrollPane3.setViewportView(tableErrors);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                                .addGap(0, 0, 0))
        );

        jPanel1.add(jPanel3);

        analyze.setText("Analizar");

        reset.setText("Reiniciar");

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
                                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(analyze)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(reset)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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
                                        .addComponent(reset))
                                .addGap(8, 8, 8))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton analyze;
    public javax.swing.JMenuItem exit;
    public javax.swing.JMenuItem open;
    public javax.swing.JButton reset;
    private javax.swing.JScrollPane scrollTextArea;

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

    public JScrollPane getScrollTextArea() {
        return scrollTextArea;
    }

    public JTable getTableErrors() {
        return tableErrors;
    }

    public JTable getTableSimbols() {
        return tableSimbols;
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public AbstractButton[] getAllButtons() {
        AbstractButton componetsArray[] = {this.analyze, this.exit, this.open, this.reset};
        return componetsArray;
    }

    public javax.swing.JTable tableErrors;
    public javax.swing.JTable tableSimbols;
    public javax.swing.JTextArea textArea;
    // End of variables declaration//GEN-END:variables
}
