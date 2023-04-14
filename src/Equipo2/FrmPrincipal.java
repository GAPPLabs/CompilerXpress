package Equipo2;

import java.io.*;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class FrmPrincipal extends javax.swing.JFrame {

    public FrmPrincipal() {
        initComponents();
    }

    public boolean isString(Object str) {
        return str.equals(str.toString());
    }

    public static boolean isNumeric(String cadena) {
        boolean resultado;
        try {
            Integer.parseInt(cadena);
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
        }
        return resultado;
    }

    private void analizarLexico() throws IOException {
        int a = 0;
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Token");
        modelo.addColumn("Lexema");
        modelo.addColumn("Linea");
        modelo.addColumn("Descripcion");
        TablaErrores.setModel(modelo);
        DefaultTableModel modeloTokens = new DefaultTableModel();
        modeloTokens.addColumn("Token");
        modeloTokens.addColumn("Lexema");
        modeloTokens.addColumn("Tipo");
        DefaultTableModel TablaAux = new DefaultTableModel();
        TablaAux.addColumn("TOKEN");
        DefaultTableModel ModeloPosfijo = new DefaultTableModel();
        ModeloPosfijo.addColumn("Paso");
        ModeloPosfijo.addColumn("Objeto");
        ModeloPosfijo.addColumn("Fuente");
        ModeloPosfijo.addColumn("Operador");
        Tablaorden.setModel(TablaAux);
        TablaTokens.setModel(modeloTokens);
        
        DefaultTableModel LexemaTable = new DefaultTableModel();
        LexemaTable.addColumn("LEXEMA");
        Lexemes.setModel(LexemaTable);

        TableModel Lexemas = Lexemes.getModel();
        TableModel tableModel = TablaErrores.getModel();
        TableModel tableModelT = TablaTokens.getModel();
        TableModel tablemodelA = Tablaorden.getModel();

        int cols = 0;
        int fils = 0;
        String AuxCAD2 = "";
        String LLEXEMA = "";
        String ETOKEN = "";
        String AERROR = "";
        String DocuAux = "";
        int CVAR = 0;
        boolean DocuBan = false;
        int CERROR = 0;
        int cont = 1;
        String AuxCAD = "";
        int CSEP = 0;
        int COA = 0, COR = 0;
        int CNE = 0;
        int CIDN = 0;
        int CTD = 0;
        int COAS = 0;
        int COL = 0;
        int CNPD = 0;
        int CDW = 0;
        String tokens = "";
        String expr = (String) txtResultado.getText();
        Lexer lexer = new Lexer(new StringReader(expr));
        String resultado = "LINEA " + cont + "\t\tSIMBOLO\n";
        while (true) {
            Tokens token = lexer.yylex();
            if (token == null) {
                txtAnalizarLex.setText(resultado);
                ConstruirTipo();
                
                return;
            }
            switch (token) {
                case Linea:
                    cont++;
                    resultado += "LINEA " + cont + "\n";
                    String TokenTemp = "Salto" + cont;
                    DocuAux = tokens.concat("\n");
                    tokens = DocuAux;
                    Documento.setText(tokens);
                    TablaAux.addRow(new Object[]{TokenTemp});
                    LexemaTable.addRow(new Object[]{TokenTemp});

                    break;
                case Comillas:
                    COA++;   //REVISARAAAAAAAAAAAAAA!!!
                    /* resultado += "OA"+COA+"\t" + lexer.lexeme + "\n";
                    AuxCAD = Integer.toString(COA);
                    AuxCAD = " OA"+COA;
                    tokens=tokens.concat(AuxCAD);
                    Documento.setText(tokens);
                    break;
                    //resultado += "OA"+C+"\n";*/
                    AuxCAD = Integer.toString(COA);
                    AuxCAD2 = " OA" + AuxCAD;
                    LLEXEMA = "";
                    ETOKEN = "";

                    cols = tableModelT.getColumnCount();
                    fils = tableModelT.getRowCount();
                    LexemaTable.addRow(new Object[]{lexer.lexeme});
                    a = 0;
                    System.out.println(cols);
                    System.out.println(fils);

                    for (int i = 0; i < fils; i++) {
                        LLEXEMA = String.valueOf(tableModelT.getValueAt(i, 1));
                        ETOKEN = String.valueOf(tableModelT.getValueAt(i, 0));
                        //   System.out.println(lexer.lexeme + "" + AERROR);  //REVISAR
                        String Lexe = lexer.lexeme;

                        if (Lexe.equals(LLEXEMA)) {
                            // modeloTokens.addRow(new Object[]{ETOKEN, lexer.lexeme});
                            i = fils;
                            COA--;
                            a++;
                            DocuAux = tokens.concat(ETOKEN);
                            TablaAux.addRow(new Object[]{ETOKEN});
                            tokens = DocuAux;
                            Documento.setText(tokens);
                        }
                    }
                    if (a == 0) {
                        modeloTokens.addRow(new Object[]{AuxCAD2, lexer.lexeme});
                        DocuAux = tokens.concat(AuxCAD2);
                        TablaAux.addRow(new Object[]{AuxCAD2});
                        tokens = DocuAux;
                        Documento.setText(tokens);
                    }//REVISAR

                    // if(lexer.lexeme == TempLex)
                    break;

                case Cadena:
                    resultado += "  <Tipo de dato>\t" + lexer.lexeme + "\n";
                    break;
                case T_dato:
                    CTD++;
                    AuxCAD = Integer.toString(CTD);
                    AuxCAD2 = " TD" + AuxCAD;
                    LLEXEMA = "";
                    ETOKEN = "";

                    cols = tableModelT.getColumnCount();
                    fils = tableModelT.getRowCount();
                    LexemaTable.addRow(new Object[]{lexer.lexeme});
                    a = 0;
                    System.out.println(cols);
                    System.out.println(fils);

                    for (int i = 0; i < fils; i++) {
                        LLEXEMA = String.valueOf(tableModelT.getValueAt(i, 1));
                        ETOKEN = String.valueOf(tableModelT.getValueAt(i, 0));
                        //   System.out.println(lexer.lexeme + "" + AERROR);  //REVISAR
                        String Lexe = lexer.lexeme;

                        if (Lexe.equals(LLEXEMA)) {
                            // modeloTokens.addRow(new Object[]{ETOKEN, lexer.lexeme});
                            i = fils;
                            CTD--;
                            DocuAux = tokens.concat(ETOKEN);
                            TablaAux.addRow(new Object[]{ETOKEN});
                            tokens = DocuAux;
                            Documento.setText(tokens);
                            a++;
                        }
                    }
                    if (a == 0) {
                        modeloTokens.addRow(new Object[]{AuxCAD2, lexer.lexeme});
                        TablaAux.addRow(new Object[]{AuxCAD2});
                        DocuAux = tokens.concat(AuxCAD2);
                        tokens = DocuAux;
                        Documento.setText(tokens);
                    }//REVISAR
                    break;
                case If:
                    resultado += "  <Reservada if>\t" + lexer.lexeme + "\n";
                    break;
                case Else:
                    resultado += "  <Reservada else>\t" + lexer.lexeme + "\n";
                    break;
                case Do:
                    resultado += "  <Reservada do>\t" + lexer.lexeme + "\n";
                    break;
                case While:
                    CDW++;
                    AuxCAD = Integer.toString(CDW);
                    AuxCAD2 = " DW" + AuxCAD;
                    LLEXEMA = "";
                    ETOKEN = "";

                    cols = tableModelT.getColumnCount();
                    fils = tableModelT.getRowCount();
                    LexemaTable.addRow(new Object[]{lexer.lexeme});
                    a = 0;
                    System.out.println(cols);
                    System.out.println(fils);

                    for (int i = 0; i < fils; i++) {
                        LLEXEMA = String.valueOf(tableModelT.getValueAt(i, 1));
                        ETOKEN = String.valueOf(tableModelT.getValueAt(i, 0));
                        //   System.out.println(lexer.lexeme + "" + AERROR);  //REVISAR
                        String Lexe = lexer.lexeme;

                        if (Lexe.equals(LLEXEMA)) {
                            // modeloTokens.addRow(new Object[]{ETOKEN, lexer.lexeme});
                            i = fils;
                            CDW--;
                            DocuAux = tokens.concat(ETOKEN);
                            TablaAux.addRow(new Object[]{ETOKEN});
                            tokens = DocuAux;
                            Documento.setText(tokens);
                            a++;
                        }
                    }
                    if (a == 0) {
                        modeloTokens.addRow(new Object[]{AuxCAD2, lexer.lexeme});
                        TablaAux.addRow(new Object[]{AuxCAD2});
                        DocuAux = tokens.concat(AuxCAD2);
                        tokens = DocuAux;
                        Documento.setText(tokens);
                    }//REVISAR
                    break;
                /*case For:COA++;
                    /*resultado += "OA"+COA+"\t" + lexer.lexeme + "\n";
                    AuxCAD = Integer.toString(COA);
                    AuxCAD = " OA"+COA;
                    tokens=tokens.concat(AuxCAD);
                    Documento.setText(tokens);
                    break;
                    //resultado += "OA"+C+"\n";
                    AuxCAD = Integer.toString(COA);
                    String AuxCAD2 = "OA"+AuxCAD;
                    String LLEXEMA = "";
                    String ETOKEN = "";
                   TableModel tableModel = TablaTokens.getModel();
                   int cols = tableModel.getColumnCount();
                   int fils = tableModel.getRowCount();
               int a=0;
                 for (int i = 0; i < fils; i++) {
                     LLEXEMA=String.valueOf(tableModel.getValueAt(i,1));
                     ETOKEN=String.valueOf(tableModel.getValueAt(i,0));
                  //   System.out.println(lexer.lexeme + "" + AERROR);  //REVISAR
                     String Lexe = lexer.lexeme;
                     if(Lexe.equals(LLEXEMA  )){  
                        modelo.addRow(new Object[]{ETOKEN, lexer.lexeme, String.valueOf(cont),"LEXICO"});
                        i=fils;
                       a++; 
                     }
                 }
               if(a==0){   String[] datos = {AuxCAD2, lexer.lexeme, String.valueOf(cont),"LEXICO"};
                    modelo.addRow(datos);}
                   // if(lexer.lexeme == TempLex)
                    break;*/

                case Igual:
                    COAS++;
                    AuxCAD = Integer.toString(COAS);
                    AuxCAD2 = " OAS" + AuxCAD;
                    LLEXEMA = "";
                    ETOKEN = "";

                    cols = tableModelT.getColumnCount();
                    fils = tableModelT.getRowCount();
                    LexemaTable.addRow(new Object[]{lexer.lexeme});
                    a = 0;
                    System.out.println(cols);
                    System.out.println(fils);

                    for (int i = 0; i < fils; i++) {
                        LLEXEMA = String.valueOf(tableModelT.getValueAt(i, 1));
                        ETOKEN = String.valueOf(tableModelT.getValueAt(i, 0));
                        //   System.out.println(lexer.lexeme + "" + AERROR);  //REVISAR
                        String Lexe = lexer.lexeme;

                        if (Lexe.equals(LLEXEMA)) {
                            // modeloTokens.addRow(new Object[]{ETOKEN, lexer.lexeme});
                            i = fils;
                            COAS--;
                            DocuAux = tokens.concat(ETOKEN);
                            TablaAux.addRow(new Object[]{ETOKEN});
                            tokens = DocuAux;
                            Documento.setText(tokens);
                            a++;
                        }
                    }
                    if (a == 0) {
                        modeloTokens.addRow(new Object[]{AuxCAD2, lexer.lexeme});
                        TablaAux.addRow(new Object[]{AuxCAD2});
                        DocuAux = tokens.concat(AuxCAD2);
                        tokens = DocuAux;
                        Documento.setText(tokens);
                    }//REVISAR
                    break;
                case Suma:
                    COA++;
                    AuxCAD = Integer.toString(COA);
                    AuxCAD2 = " OA" + AuxCAD;
                    LLEXEMA = "";
                    ETOKEN = "";

                    cols = tableModelT.getColumnCount();
                    fils = tableModelT.getRowCount();
                    LexemaTable.addRow(new Object[]{lexer.lexeme});
                    a = 0;
                    System.out.println(cols);
                    System.out.println(fils);

                    for (int i = 0; i < fils; i++) {
                        LLEXEMA = String.valueOf(tableModelT.getValueAt(i, 1));
                        ETOKEN = String.valueOf(tableModelT.getValueAt(i, 0));
                        //   System.out.println(lexer.lexeme + "" + AERROR);  //REVISAR
                        String Lexe = lexer.lexeme;
                        if (Lexe.equals(LLEXEMA)) {
                            // modeloTokens.addRow(new Object[]{ETOKEN, lexer.lexeme});
                            i = fils;
                            COA--;
                            DocuAux = tokens.concat(ETOKEN);
                            TablaAux.addRow(new Object[]{ETOKEN});

                            tokens = DocuAux;
                            Documento.setText(tokens);
                            a++;
                        }
                    }
                    if (a == 0) {
                        modeloTokens.addRow(new Object[]{AuxCAD2, lexer.lexeme});
                        TablaAux.addRow(new Object[]{AuxCAD2});
                        DocuAux = tokens.concat(AuxCAD2);
                        tokens = DocuAux;
                        Documento.setText(tokens);
                    }//REVISAR

                    break;
                case Resta:
                    COA++;
                    AuxCAD = Integer.toString(COA);
                    AuxCAD2 = " OA" + AuxCAD;
                    LLEXEMA = "";
                    ETOKEN = "";

                    cols = tableModelT.getColumnCount();
                    fils = tableModelT.getRowCount();
                    LexemaTable.addRow(new Object[]{lexer.lexeme});
                    a = 0;
                    System.out.println(cols);
                    System.out.println(fils);

                    for (int i = 0; i < fils; i++) {
                        LLEXEMA = String.valueOf(tableModelT.getValueAt(i, 1));
                        ETOKEN = String.valueOf(tableModelT.getValueAt(i, 0));
                        //   System.out.println(lexer.lexeme + "" + AERROR);  //REVISAR
                        String Lexe = lexer.lexeme;

                        if (Lexe.equals(LLEXEMA)) {
                            //modeloTokens.addRow(new Object[]{ETOKEN, lexer.lexeme});
                            i = fils;
                            COA--;
                            DocuAux = tokens.concat(ETOKEN);
                            TablaAux.addRow(new Object[]{ETOKEN});
                            tokens = DocuAux;
                            Documento.setText(tokens);
                            a++;
                        }
                    }
                    if (a == 0) {
                        modeloTokens.addRow(new Object[]{AuxCAD2, lexer.lexeme});
                        DocuAux = tokens.concat(AuxCAD2);
                        TablaAux.addRow(new Object[]{AuxCAD2});
                        tokens = DocuAux;
                        Documento.setText(tokens);
                    }//REVISAR

                    break;
                case Multiplicacion:
                    COA++;
                    AuxCAD = Integer.toString(COA);
                    AuxCAD2 = " OA" + AuxCAD;
                    LLEXEMA = "";
                    ETOKEN = "";

                    cols = tableModelT.getColumnCount();
                    fils = tableModelT.getRowCount();
                    LexemaTable.addRow(new Object[]{lexer.lexeme});
                    a = 0;
                    System.out.println(cols);
                    System.out.println(fils);

                    for (int i = 0; i < fils; i++) {
                        LLEXEMA = String.valueOf(tableModelT.getValueAt(i, 1));
                        ETOKEN = String.valueOf(tableModelT.getValueAt(i, 0));
                        //   System.out.println(lexer.lexeme + "" + AERROR);  //REVISAR
                        String Lexe = lexer.lexeme;

                        if (Lexe.equals(LLEXEMA)) {
                            // modeloTokens.addRow(new Object[]{ETOKEN, lexer.lexeme});
                            i = fils;
                            COA--;
                            DocuAux = tokens.concat(ETOKEN);
                            TablaAux.addRow(new Object[]{ETOKEN});
                            tokens = DocuAux;
                            Documento.setText(tokens);
                            a++;
                        }
                    }
                    if (a == 0) {
                        modeloTokens.addRow(new Object[]{AuxCAD2, lexer.lexeme});
                        TablaAux.addRow(new Object[]{AuxCAD2});
                        DocuAux = tokens.concat(AuxCAD2);
                        tokens = DocuAux;
                        Documento.setText(tokens);
                    }//REVISAR

                    break;
                case Division:
                    COA++;
                    AuxCAD = Integer.toString(COA);
                    AuxCAD2 = " OA" + AuxCAD;
                    LLEXEMA = "";
                    ETOKEN = "";

                    cols = tableModelT.getColumnCount();
                    fils = tableModelT.getRowCount();
                    LexemaTable.addRow(new Object[]{lexer.lexeme});
                    a = 0;
                    System.out.println(cols);
                    System.out.println(fils);

                    for (int i = 0; i < fils; i++) {
                        LLEXEMA = String.valueOf(tableModelT.getValueAt(i, 1));
                        ETOKEN = String.valueOf(tableModelT.getValueAt(i, 0));
                        //   System.out.println(lexer.lexeme + "" + AERROR);  //REVISAR
                        String Lexe = lexer.lexeme;

                        if (Lexe.equals(LLEXEMA)) {
                            // modeloTokens.addRow(new Object[]{ETOKEN, lexer.lexeme});
                            i = fils;
                            COA--;
                            DocuAux = tokens.concat(ETOKEN);
                            TablaAux.addRow(new Object[]{ETOKEN});
                            tokens = DocuAux;
                            Documento.setText(tokens);
                            a++;
                        }
                    }
                    if (a == 0) {
                        modeloTokens.addRow(new Object[]{AuxCAD2, lexer.lexeme});
                        DocuAux = tokens.concat(AuxCAD2);
                        TablaAux.addRow(new Object[]{AuxCAD2});
                        tokens = DocuAux;
                        Documento.setText(tokens);
                    }//REVISAR

                    break;
                case Op_logico:
                    COL++;
                    AuxCAD = Integer.toString(COL);
                    AuxCAD2 = " OL" + AuxCAD;
                    LLEXEMA = "";
                    ETOKEN = "";

                    cols = tableModelT.getColumnCount();
                    fils = tableModelT.getRowCount();
                    LexemaTable.addRow(new Object[]{lexer.lexeme});
                    a = 0;
                    System.out.println(cols);
                    System.out.println(fils);

                    for (int i = 0; i < fils; i++) {
                        LLEXEMA = String.valueOf(tableModelT.getValueAt(i, 1));
                        ETOKEN = String.valueOf(tableModelT.getValueAt(i, 0));
                        //   System.out.println(lexer.lexeme + "" + AERROR);  //REVISAR
                        String Lexe = lexer.lexeme;

                        if (Lexe.equals(LLEXEMA)) {
                            // modeloTokens.addRow(new Object[]{ETOKEN, lexer.lexeme});
                            i = fils;
                            COL--;
                            DocuAux = tokens.concat(ETOKEN);
                            TablaAux.addRow(new Object[]{ETOKEN});
                            tokens = DocuAux;
                            Documento.setText(tokens);
                            a++;
                        }
                    }
                    if (a == 0) {
                        modeloTokens.addRow(new Object[]{AuxCAD2, lexer.lexeme});
                        DocuAux = tokens.concat(AuxCAD2);
                        TablaAux.addRow(new Object[]{AuxCAD2});
                        tokens = DocuAux;
                        Documento.setText(tokens);
                    }//REVISAR

                    break;
                case Op_incremento:
                    resultado += "  <Operador incremento>\t" + lexer.lexeme + "\n";
                    break;
                case Op_relacional:
                    COR++;
                    AuxCAD = Integer.toString(COR);
                    AuxCAD2 = " OR" + AuxCAD;
                    LLEXEMA = "";
                    ETOKEN = "";

                    cols = tableModelT.getColumnCount();
                    fils = tableModelT.getRowCount();
                    LexemaTable.addRow(new Object[]{lexer.lexeme});
                    a = 0;
                    System.out.println(cols);
                    System.out.println(fils);

                    for (int i = 0; i < fils; i++) {
                        LLEXEMA = String.valueOf(tableModelT.getValueAt(i, 1));
                        ETOKEN = String.valueOf(tableModelT.getValueAt(i, 0));
                        //   System.out.println(lexer.lexeme + "" + AERROR);  //REVISAR
                        String Lexe = lexer.lexeme;

                        if (Lexe.equals(LLEXEMA)) {
                            // modeloTokens.addRow(new Object[]{ETOKEN, lexer.lexeme});
                            i = fils;
                            COR--;
                            DocuAux = tokens.concat(ETOKEN);
                            TablaAux.addRow(new Object[]{ETOKEN});
                            tokens = DocuAux;
                            Documento.setText(tokens);
                            a++;
                        }
                    }
                    if (a == 0) {
                        modeloTokens.addRow(new Object[]{AuxCAD2, lexer.lexeme});
                        TablaAux.addRow(new Object[]{AuxCAD2});
                        DocuAux = tokens.concat(AuxCAD2);
                        tokens = DocuAux;
                        Documento.setText(tokens);
                    }//REVISAR
                    break;
                case Op_atribucion:
                    resultado += "  <Operador atribucion>\t" + lexer.lexeme + "\n";
                    break;
                case Op_booleano:
                    resultado += "  <Operador booleano>\t" + lexer.lexeme + "\n";
                    break;
                case Parentesis_a:
                    CSEP++;
                    AuxCAD = Integer.toString(CSEP);
                    AuxCAD2 = " SEP" + AuxCAD;
                    LLEXEMA = "";
                    ETOKEN = "";

                    cols = tableModelT.getColumnCount();
                    fils = tableModelT.getRowCount();
                    LexemaTable.addRow(new Object[]{lexer.lexeme});
                    a = 0;
                    System.out.println(cols);
                    System.out.println(fils);

                    for (int i = 0; i < fils; i++) {
                        LLEXEMA = String.valueOf(tableModelT.getValueAt(i, 1));
                        ETOKEN = String.valueOf(tableModelT.getValueAt(i, 0));
                        //   System.out.println(lexer.lexeme + "" + AERROR);  //REVISAR
                        String Lexe = lexer.lexeme;

                        if (Lexe.equals(LLEXEMA)) {
                            // modeloTokens.addRow(new Object[]{ETOKEN, lexer.lexeme});
                            i = fils;
                            CSEP--;
                            DocuAux = tokens.concat(ETOKEN);
                            TablaAux.addRow(new Object[]{ETOKEN});
                            tokens = DocuAux;
                            Documento.setText(tokens);
                            a++;
                        }

                    }
                    if (a == 0) {
                        modeloTokens.addRow(new Object[]{AuxCAD2, lexer.lexeme});
                        DocuAux = tokens.concat(AuxCAD2);
                        TablaAux.addRow(new Object[]{AuxCAD2});
                        tokens = DocuAux;
                        Documento.setText(tokens);
                    }//REVISAR
                    break;
                case Parentesis_c:
                    CSEP++;
                    AuxCAD = Integer.toString(CSEP);
                    AuxCAD2 = " SEP" + AuxCAD;
                    LLEXEMA = "";
                    ETOKEN = "";

                    cols = tableModelT.getColumnCount();
                    fils = tableModelT.getRowCount();
                    LexemaTable.addRow(new Object[]{lexer.lexeme});
                    a = 0;
                    System.out.println(cols);
                    System.out.println(fils);

                    for (int i = 0; i < fils; i++) {
                        LLEXEMA = String.valueOf(tableModelT.getValueAt(i, 1));
                        ETOKEN = String.valueOf(tableModelT.getValueAt(i, 0));
                        //   System.out.println(lexer.lexeme + "" + AERROR);  //REVISAR
                        String Lexe = lexer.lexeme;

                        if (Lexe.equals(LLEXEMA)) {
                            // modeloTokens.addRow(new Object[]{ETOKEN, lexer.lexeme});
                            i = fils;
                            CSEP--;
                            DocuAux = tokens.concat(ETOKEN);
                            TablaAux.addRow(new Object[]{ETOKEN});
                            tokens = DocuAux;
                            Documento.setText(tokens);
                            a++;
                        }
                    }
                    if (a == 0) {
                        modeloTokens.addRow(new Object[]{AuxCAD2, lexer.lexeme});
                        TablaAux.addRow(new Object[]{AuxCAD2});
                        DocuAux = tokens.concat(AuxCAD2);
                        tokens = DocuAux;
                        Documento.setText(tokens);
                    }//REVISAR
                    break;
                case Llave_a:
                    CSEP++;
                    AuxCAD = Integer.toString(CSEP);
                    AuxCAD2 = " SEP" + AuxCAD;
                    LLEXEMA = "";
                    ETOKEN = "";

                    cols = tableModelT.getColumnCount();
                    fils = tableModelT.getRowCount();
                    LexemaTable.addRow(new Object[]{lexer.lexeme});
                    a = 0;
                    System.out.println(cols);
                    System.out.println(fils);

                    for (int i = 0; i < fils; i++) {
                        LLEXEMA = String.valueOf(tableModelT.getValueAt(i, 1));
                        ETOKEN = String.valueOf(tableModelT.getValueAt(i, 0));
                        //   System.out.println(lexer.lexeme + "" + AERROR);  //REVISAR
                        String Lexe = lexer.lexeme;

                        if (Lexe.equals(LLEXEMA)) {
                            // modeloTokens.addRow(new Object[]{ETOKEN, lexer.lexeme});
                            i = fils;
                            CSEP--;
                            DocuAux = tokens.concat(ETOKEN);
                            TablaAux.addRow(new Object[]{ETOKEN});
                            tokens = DocuAux;
                            Documento.setText(tokens);
                            a++;
                        }
                    }
                    if (a == 0) {
                        modeloTokens.addRow(new Object[]{AuxCAD2, lexer.lexeme});
                        DocuAux = tokens.concat(AuxCAD2);
                        TablaAux.addRow(new Object[]{AuxCAD2});
                        tokens = DocuAux;
                        Documento.setText(tokens);
                    }//REVISAR
                    break;
                case Llave_c:
                    CSEP++;
                    AuxCAD = Integer.toString(CSEP);
                    AuxCAD2 = " SEP" + AuxCAD;
                    LLEXEMA = "";
                    ETOKEN = "";

                    cols = tableModelT.getColumnCount();
                    fils = tableModelT.getRowCount();
                    LexemaTable.addRow(new Object[]{lexer.lexeme});
                    a = 0;
                    System.out.println(cols);
                    System.out.println(fils);

                    for (int i = 0; i < fils; i++) {
                        LLEXEMA = String.valueOf(tableModelT.getValueAt(i, 1));
                        ETOKEN = String.valueOf(tableModelT.getValueAt(i, 0));
                        //   System.out.println(lexer.lexeme + "" + AERROR);  //REVISAR
                        String Lexe = lexer.lexeme;

                        if (Lexe.equals(LLEXEMA)) {
                            // modeloTokens.addRow(new Object[]{ETOKEN, lexer.lexeme});
                            i = fils;
                            CSEP--;
                            DocuAux = tokens.concat(ETOKEN);
                            TablaAux.addRow(new Object[]{ETOKEN});
                            tokens = DocuAux;
                            Documento.setText(tokens);
                            a++;
                        }
                    }
                    if (a == 0) {
                        modeloTokens.addRow(new Object[]{AuxCAD2, lexer.lexeme});
                        DocuAux = tokens.concat(AuxCAD2);
                        tokens = DocuAux;
                        TablaAux.addRow(new Object[]{AuxCAD2});
                        Documento.setText(tokens);
                    }//REVISAR
                    break;
                case Corchete_a:
                    CSEP++;
                    resultado += "SEP" + CSEP + "\t" + lexer.lexeme + "\n";
                    AuxCAD = String.valueOf(CSEP);
                    tokens.concat(" SEP" + AuxCAD);
                    Documento.setText(tokens);
                    break;
                case Corchete_c:
                    CSEP++;
                    resultado += "SEP" + CSEP + "\t" + lexer.lexeme + "\n";
                    AuxCAD = String.valueOf(CSEP);
                    tokens.concat(" SEP" + AuxCAD);
                    Documento.setText(tokens);
                    break;
                case Main:
                    resultado += "  <Reservada main>\t" + lexer.lexeme + "\n";
                    break;
                case P_coma:
                    CSEP++;
                    AuxCAD = Integer.toString(CSEP);
                    AuxCAD2 = " SEP" + AuxCAD;
                    LLEXEMA = "";
                    ETOKEN = "";

                    cols = tableModelT.getColumnCount();
                    fils = tableModelT.getRowCount();
                    LexemaTable.addRow(new Object[]{lexer.lexeme});
                    a = 0;
                    System.out.println(cols);
                    System.out.println(fils);

                    for (int i = 0; i < fils; i++) {
                        LLEXEMA = String.valueOf(tableModelT.getValueAt(i, 1));
                        ETOKEN = String.valueOf(tableModelT.getValueAt(i, 0));
                        //   System.out.println(lexer.lexeme + "" + AERROR);  //REVISAR
                        String Lexe = lexer.lexeme;

                        if (Lexe.equals(LLEXEMA)) {
                            // modeloTokens.addRow(new Object[]{ETOKEN, lexer.lexeme});
                            i = fils;
                            CSEP--;
                            DocuAux = tokens.concat(ETOKEN);
                            TablaAux.addRow(new Object[]{ETOKEN});
                            tokens = DocuAux;
                            Documento.setText(tokens);
                            a++;
                        }
                    }
                    if (a == 0) {
                        modeloTokens.addRow(new Object[]{AuxCAD2, lexer.lexeme});
                        TablaAux.addRow(new Object[]{AuxCAD2});
                        DocuAux = tokens.concat(AuxCAD2);
                        tokens = DocuAux;
                        Documento.setText(tokens);
                    }//REVISAR

                    //   Documento.setText(tokens);
                    break;
                case Identificador:
                    CVAR++;
                    AuxCAD = Integer.toString(CVAR);
                    AuxCAD2 = " VAR" + AuxCAD;
                    LLEXEMA = "";
                    ETOKEN = "";

                    cols = tableModelT.getColumnCount();
                    fils = tableModelT.getRowCount();
                    LexemaTable.addRow(new Object[]{lexer.lexeme});
                    a = 0;
                    System.out.println(cols);
                    System.out.println(fils);

                    for (int i = 0; i < fils; i++) {
                        LLEXEMA = String.valueOf(tableModelT.getValueAt(i, 1));
                        ETOKEN = String.valueOf(tableModelT.getValueAt(i, 0));
                        //   System.out.println(lexer.lexeme + "" + AERROR);  //REVISAR
                        String Lexe = lexer.lexeme;

                        if (Lexe.equals(LLEXEMA)) {
                            // modeloTokens.addRow(new Object[]{ETOKEN, lexer.lexeme});
                            i = fils;
                            CVAR--;
                            DocuAux = tokens.concat(ETOKEN);
                            TablaAux.addRow(new Object[]{ETOKEN});
                            tokens = DocuAux;
                            Documento.setText(tokens);
                            a++;
                        }
                    }
                    if (a == 0) {
                        modeloTokens.addRow(new Object[]{AuxCAD2, lexer.lexeme});
                        TablaAux.addRow(new Object[]{AuxCAD2});
                        DocuAux = tokens.concat(AuxCAD2);
                        tokens = DocuAux;
                        Documento.setText(tokens);
                    }//REVISAR
                    break;
                case Numero:
                    CNE++;
                    AuxCAD = Integer.toString(CNE);
                    AuxCAD2 = " NE" + AuxCAD;
                    LLEXEMA = "";
                    ETOKEN = "";

                    cols = tableModelT.getColumnCount();
                    fils = tableModelT.getRowCount();
                    LexemaTable.addRow(new Object[]{lexer.lexeme});
                    a = 0;
                    System.out.println(cols);
                    System.out.println(fils);

                    for (int i = 0; i < fils; i++) {
                        LLEXEMA = String.valueOf(tableModelT.getValueAt(i, 1));
                        ETOKEN = String.valueOf(tableModelT.getValueAt(i, 0));
                        //   System.out.println(lexer.lexeme + "" + AERROR);  //REVISAR
                        String Lexe = lexer.lexeme;

                        if (Lexe.equals(LLEXEMA)) {
                            // modeloTokens.addRow(new Object[]{ETOKEN, lexer.lexeme});
                            i = fils;
                            CNE--;
                            DocuAux = tokens.concat(ETOKEN);
                            TablaAux.addRow(new Object[]{ETOKEN});
                            tokens = DocuAux;
                            Documento.setText(tokens);
                            a++;
                        }
                    }
                    if (a == 0) {
                        modeloTokens.addRow(new Object[]{AuxCAD2, lexer.lexeme});
                        TablaAux.addRow(new Object[]{AuxCAD2});
                        DocuAux = tokens.concat(AuxCAD2);
                        tokens = DocuAux;
                        Documento.setText(tokens);
                    }//REVISAR
                    break;
                case NumeroDecimal:
                    CNPD++;
                    AuxCAD = Integer.toString(CNPD);
                    AuxCAD2 = " NPD" + AuxCAD;
                    LLEXEMA = "";
                    ETOKEN = "";

                    cols = tableModelT.getColumnCount();
                    fils = tableModelT.getRowCount();

                    a = 0;
                    System.out.println(cols);
                    System.out.println(fils);

                    for (int i = 0; i < fils; i++) {
                        LLEXEMA = String.valueOf(tableModelT.getValueAt(i, 1));
                        ETOKEN = String.valueOf(tableModelT.getValueAt(i, 0));
                        //   System.out.println(lexer.lexeme + "" + AERROR);  //REVISAR
                        String Lexe = lexer.lexeme;
                        LexemaTable.addRow(new Object[]{Lexe});
                        if (Lexe.equals(LLEXEMA)) {
                            // modeloTokens.addRow(new Object[]{ETOKEN, lexer.lexeme});
                            i = fils;
                            CNPD--;
                            DocuAux = tokens.concat(ETOKEN);
                            TablaAux.addRow(new Object[]{ETOKEN});
                            tokens = DocuAux;
                            Documento.setText(tokens);
                            a++;
                        }
                    }
                    if (a == 0) {
                        modeloTokens.addRow(new Object[]{AuxCAD2, lexer.lexeme});
                        TablaAux.addRow(new Object[]{AuxCAD2});
                        DocuAux = tokens.concat(AuxCAD2);
                        tokens = DocuAux;
                        Documento.setText(tokens);
                    }//REVISAR

                    break;

                case ERROR:
                    CERROR++;
                    resultado += "ERROR" + CERROR + "\n";
                    AuxCAD = Integer.toString(CERROR);
                    AuxCAD2 = " ERROR" + AuxCAD;
                    AERROR = "";
                    ETOKEN = "";
                    tableModel = TablaErrores.getModel();
                    cols = tableModel.getColumnCount();
                    fils = tableModel.getRowCount();
                    LexemaTable.addRow(new Object[]{lexer.lexeme});
                    a = 0;
                    for (int i = 0; i < fils; i++) {
                        AERROR = String.valueOf(tableModel.getValueAt(i, 1));
                        ETOKEN = String.valueOf(tableModel.getValueAt(i, 0));
                        String LLINEA = String.valueOf(tableModel.getValueAt(i, 2));
                        //   System.out.println(lexer.lexeme + "" + AERROR);  //REVISAR
                        String Lexe = lexer.lexeme;

                        if (Lexe.equals(AERROR)) {
                            if (String.valueOf(LLINEA) == String.valueOf(cont)) {
                                modelo.addRow(new Object[]{ETOKEN, lexer.lexeme, String.valueOf(cont), "LEXICO"});
                                modelo.removeRow(i);
                            }

                            modelo.addRow(new Object[]{ETOKEN, lexer.lexeme, LLINEA + "," + String.valueOf(cont), "LEXICO"});
                            modelo.removeRow(i);
                            //modelo.removeRow(i);
                            i = fils;
                            a++;
                            CERROR--;
                            DocuAux = tokens.concat(ETOKEN);
                            TablaAux.addRow(new Object[]{ETOKEN});
                            tokens = DocuAux;
                            Documento.setText(tokens);
                        }
                    }
                    if (a == 0) {

                        String[] datos = {AuxCAD2, lexer.lexeme, String.valueOf(cont), "LEXICO"};
                        modelo.addRow(datos);
                        TablaAux.addRow(new Object[]{AuxCAD2});
                        DocuAux = tokens.concat(AuxCAD2);
                        tokens = DocuAux;
                        Documento.setText(tokens);

                        //   if(a==0){   String[] datos = {AuxCAD2, lexer.lexeme, String.valueOf(cont),"LEXICO"};
                    }

                    // if(lexer.lexeme == TempLex)
                    break;
                default:
                    resultado += "  < " + lexer.lexeme + " >\n";
                    break;
            }
        }
    }

    public void ConstruirTipo() {
        TableModel tablemodelA = Tablaorden.getModel();
        TableModel tableModelT = TablaTokens.getModel();
        DefaultTableModel modeloErroresSem = (DefaultTableModel) TablaErrores.getModel();
        int cols, fils, colsorden;
        String Token;
        cols = tableModelT.getColumnCount();
        fils = tableModelT.getRowCount();
        colsorden = tablemodelA.getRowCount();
        String TipoAsignador = "";
        int CERM = 0;
        int NumLineas = 1;
        String AuxToken = "";
        /*String  LLEXEMA=String.valueOf(tableModelT.getValueAt(0,1));
        String  ETOKEN=String.valueOf(tableModelT.getValueAt(0,0));
        String  ORDEN=String.valueOf(tablemodelA.getValueAt(0,0));
        JOptionPane.showConfirmDialog(null, ETOKEN+LLEXEMA+ORDEN);*/
        for (int i = 0; i < colsorden; i++) {
            Token = String.valueOf(tablemodelA.getValueAt(i, 0));
            if (i + 1 < colsorden) {
                AuxToken = String.valueOf(tablemodelA.getValueAt(i + 1, 0));
            }

            int aux = i + 1;
            //String TOKENSIG = String.valueOf(tablemodelA.getValueAt(aux,0));
            System.err.println(Token);
            for (int j = 0; j < fils; j++) {
                String LLEXEMA = String.valueOf(tableModelT.getValueAt(j, 1));
                String ETOKEN = String.valueOf(tableModelT.getValueAt(j, 0));
                String Tipo = String.valueOf(tableModelT.getValueAt(j, 2));
                String strS = Token;
                String TokenSALTO = strS.substring(0, strS.length() - 1);
                //   System.out.println(lexer.lexeme + "" + AERROR);  //REVISAR

                if (Token.equals(ETOKEN)) {
                    System.err.println("BIEN ");
                    String str = ETOKEN;
                    String TokenPuro = str.substring(0, str.length() - 1);

                    System.err.println(TokenPuro);

                    if (ETOKEN.contains("TD")) {
                        TipoAsignador = LLEXEMA;
                        System.err.println("UBICADO IGUAL");
                    }
                    if (ETOKEN.contains(" VAR")) { //PARA VARIABLES
                        if (Tipo == "null") { //&& String.valueOf(tablemodelA.getValueAt(i-1,0)).contains(" TD")
                            if (TipoAsignador != "") {
                                tableModelT.setValueAt(TipoAsignador, j, 2);
                            } else {
                                CERM++;
                                modeloErroresSem.addRow(new Object[]{"ErrmSem" + CERM, LLEXEMA, NumLineas, "Variable No Definida"});
                            }
                        }
                        if (Tipo != "null") {
                            if (TipoAsignador != "") {
                                CERM++;
                                modeloErroresSem.addRow(new Object[]{"ErrmSem" + CERM, LLEXEMA, NumLineas, "Definida Anteriormente"});
                            }
                        }
                    }
                    if (ETOKEN.contains(" VAR") && Tipo.contains("#Largo") && AuxToken.contains("OAS")) { //ARREGLANDO
                        // JOptionPane.showConfirmDialog(null, "Combinado");

                        for (int k = i; k < colsorden; k++) {

                            String TokenX = String.valueOf(tablemodelA.getValueAt(k, 0));     //TRAVBAJANDING

                            if (TokenX.contains(" VAR")) {

                                //              JOptionPane.showConfirmDialog(null, "ENCONTRE VAR");
                                for (int l = 0; l < fils; l++) {
                                    String ETOKENX = String.valueOf(tableModelT.getValueAt(l, 0));  //REVISAR PAAA

                                    if (TokenX.equals(ETOKENX)) {
                                        //                  JOptionPane.showConfirmDialog(null, "ES UBICADO");
                                        //                JOptionPane.showConfirmDialog(null, String.valueOf(tableModelT.getValueAt(l,2)));
                                        //              JOptionPane.showInternalConfirmDialog(null, Token);
                                        //            JOptionPane.showInternalConfirmDialog(null, ETOKENX);
                                        if (String.valueOf(tableModelT.getValueAt(l, 2)).contains("#Flotante")) {

                                            CERM++;
                                            modeloErroresSem.addRow(new Object[]{"ErrmSem" + CERM, ETOKENX, NumLineas, "Incompatibilidad de #FLOTANTE"});

                                        }
                                        if (String.valueOf(tableModelT.getValueAt(l, 2)).contains("#Si")) {
                                            CERM++;
                                            modeloErroresSem.addRow(new Object[]{"ErrmSem" + CERM, ETOKENX, NumLineas, "Incompatibilidad de #SI"});
                                        }
                                        l = fils;
                                    }
                                }
                            }
                            if (TokenX.contains("Salto")) {
                                k = colsorden;
                            }
                        }
                    }
                    if (ETOKEN.contains(" VAR") && Tipo.contains("#Flotante") && AuxToken.contains("OAS")) { //ARREGLANDO
                        //JOptionPane.showConfirmDialog(null, "Combinado");

                        for (int k = i; k < colsorden; k++) {

                            String TokenX = String.valueOf(tablemodelA.getValueAt(k, 0));     //TRAVBAJANDING

                            if (TokenX.contains(" VAR")) {

                                //              JOptionPane.showConfirmDialog(null, "ENCONTRE VAR");
                                for (int l = 0; l < fils; l++) {
                                    String ETOKENX = String.valueOf(tableModelT.getValueAt(l, 0));  //REVISAR PAAA

                                    if (TokenX.equals(ETOKENX)) {
                                        //                  JOptionPane.showConfirmDialog(null, "ES UBICADO");
                                        //                JOptionPane.showConfirmDialog(null, String.valueOf(tableModelT.getValueAt(l,2)));
                                        //              JOptionPane.showInternalConfirmDialog(null, Token);
                                        //            JOptionPane.showInternalConfirmDialog(null, ETOKENX);

                                        if (String.valueOf(tableModelT.getValueAt(l, 2)).contains("#Si")) {
                                            CERM++;
                                            modeloErroresSem.addRow(new Object[]{"ErrmSem" + CERM, ETOKENX, NumLineas, "Incompatibilidad de #SI"});
                                        }
                                        l = fils;
                                    }
                                }
                            }
                            if (TokenX.contains("Salto")) {
                                k = colsorden;
                            }
                        }
                    }
                    if (ETOKEN.contains(" VAR") && Tipo.contains("#Si") && AuxToken.contains("OAS")) { //ARREGLANDO
                        // JOptionPane.showConfirmDialog(null, "Combinado");

                        for (int k = i; k < colsorden; k++) {

                            String TokenX = String.valueOf(tablemodelA.getValueAt(k, 0));     //TRAVBAJANDING

                            if (TokenX.contains(" VAR")) {

                                //              JOptionPane.showConfirmDialog(null, "ENCONTRE VAR");
                                for (int l = 0; l < fils; l++) {
                                    String ETOKENX = String.valueOf(tableModelT.getValueAt(l, 0));  //REVISAR PAAA

                                    if (TokenX.equals(ETOKENX)) {
                                        //                  JOptionPane.showConfirmDialog(null, "ES UBICADO");
                                        //                JOptionPane.showConfirmDialog(null, String.valueOf(tableModelT.getValueAt(l,2)));
                                        //              JOptionPane.showInternalConfirmDialog(null, Token);
                                        //            JOptionPane.showInternalConfirmDialog(null, ETOKENX);
                                        if (String.valueOf(tableModelT.getValueAt(l, 2)).contains("#Flotante")) {

                                            CERM++;
                                            modeloErroresSem.addRow(new Object[]{"ErrmSem" + CERM, ETOKENX, NumLineas, "Incompatibilidad de #FLOTANTE"});
                                        }
                                        if (String.valueOf(tableModelT.getValueAt(l, 2)).contains("#Largo")) {
                                            CERM++;
                                            modeloErroresSem.addRow(new Object[]{"ErrmSem" + CERM, ETOKENX, NumLineas, "Incompatibilidad de #Largo"});
                                        }
                                        l = fils;
                                    }
                                }
                            }
                            if (TokenX.contains("Salto")) {
                                k = colsorden;
                            }
                        }
                    }
                    j = fils;
                }

                /*if(ETOKEN.contains(" VAR") && String.valueOf(tableModelT.getValueAt(j,2))=="#Largo" && TOKENSIG==" OAS"  ){
                                
                                for (int k = i+2; k < colsorden; k++) {
                                String AuxToken = String.valueOf(tablemodelA.getValueAt(k,0));
                                                 if(AuxToken.contains("#Flotante") && AuxToken.contains("#Si")){
                                                     CERM++;
                                                     modeloErroresSem.addRow(new Object[]{"ErrmSem"+CERM, LLEXEMA,NumLineas ,"Incompatibilidad de Tipos de datos"});
                                                 }
                                }
                             }*/
            }
            if (Token.contains("Salto")) {
                NumLineas++;
                TipoAsignador = "";
            }
        }
    }

    

    /*private void analizarLexico33() throws IOException{
        int cont1=0;
        int cont2=0;
        int cont3=0;
        int cont4=0;
        int cont5=0;
        int cont6=0;
        int cont7=0;
        int cont8=0;
        int cont9=0;
        int cont10=0;
        int cont11=0;
        int cont=1;
        String expr = (String) txtResultado.getText();
        Lexer lexer = new Lexer(new StringReader(expr));
        String resultado = "Token " + "\tLexema\n";
        String tokens = " ";
        String er = "Token de error\t" + "Lexema\t" + "Linea\t" + "Descripcion\n";
        while (true) {
            Tokens token = lexer.yylex();
            if (token == null) {
                txtAnalizarLex.setText(resultado);
                txtToken.setText(tokens);
                txtError.setText(er);
                return;
            }
            switch (token) {
                case Linea:
                    cont++;
                    resultado += "\n";
                    tokens += "\n";
                    break;
                case TD: cont1++;
                    resultado +=" TD"+ cont1 + "\t"+ lexer.lexeme + "\n";
                    tokens += "TD"+ cont1+" ";
                    break;
                
                case DOW:cont2++;
                    resultado += "  DOW"+cont2+"\t" + lexer.lexeme + "\n";
                    tokens += "DOW"+ cont2+" ";
                    break;
                case OAS:cont3++;
                    resultado += "  OAS"+cont3+"\t" + lexer.lexeme + "\n";
                    tokens += "OAS"+ cont3+" ";
                    break;
                case OA:cont4++;
                    resultado += "  OA"+cont4+"\t" + lexer.lexeme + "\n";
                    tokens += "OA"+ cont4+" ";
                    break;
                case OL:cont5++;
                    resultado += "  OL"+cont5+"\t" + lexer.lexeme + "\n";
                    tokens += "OL"+ cont5+" ";
                    break;
                case OR:cont6++;
                    resultado += "  OR"+cont6+"\t" + lexer.lexeme + "\n";
                    tokens += "OR"+ cont6+" ";
                    break;
                case SEP:cont7++;
                    resultado += "  SEP"+cont7+"\t" + lexer.lexeme + "\n";
                    tokens += "SEP"+ cont7+" ";
                    break;
                case Identificador:cont8++;
                    resultado += "  VAR"+cont8+"\t" + lexer.lexeme + "\n";
                    tokens += "VAR"+ cont8+" ";
                    break;
                case Numero:cont9++;
                    resultado += "  NE"+cont9+"\t" + lexer.lexeme + "\n";
                    tokens += "NE"+ cont9+" ";
                    break;
                case NPD:cont10++;
                    resultado += "  NPD"+cont10+"\t" + lexer.lexeme + "\n";
                    tokens += "NPD"+ cont10+" ";
                    break;
                case ERROR:cont11++;
                resultado += "  ER"+ cont11+"\t" + "\n";    
                er += "  ER"+ cont11+"\t"+ lexer.lexeme +"\t" + cont+ "\t" + "Error lexico" + "\n";
                    break;
                default:
                    resultado += "  < " + lexer.lexeme + " >\n";
                    break;
            }   
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnArchivo = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtResultado = new javax.swing.JTextArea();
        btnAnalizarLex = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        TablaErrores = new javax.swing.JTable();
        btnLimpiarLex = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        TablaTokens = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAnalizarLex = new javax.swing.JTextArea();
        jScrollPane6 = new javax.swing.JScrollPane();
        Tablaorden = new javax.swing.JTable();
        jScrollPane8 = new javax.swing.JScrollPane();
        Lexemes = new javax.swing.JTable();
        jScrollPane9 = new javax.swing.JScrollPane();
        Tablaorden1 = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        Documento = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ANALIZADOR LEXICO V2  EQUIPO  12");
        setBackground(new java.awt.Color(0, 51, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setForeground(new java.awt.Color(0, 51, 255));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Analizador Lexico", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Comic Sans MS", 1, 24))); // NOI18N

        btnArchivo.setText("Cargar .txt");
        btnArchivo.setBorder(new javax.swing.border.MatteBorder(null));
        btnArchivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnArchivoActionPerformed(evt);
            }
        });

        txtResultado.setColumns(20);
        txtResultado.setRows(5);
        jScrollPane1.setViewportView(txtResultado);

        btnAnalizarLex.setText("Analizar");
        btnAnalizarLex.setBorder(new javax.swing.border.MatteBorder(null));
        btnAnalizarLex.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnalizarLexActionPerformed(evt);
            }
        });

        jButton1.setText("Guardar Archivo.txt");
        jButton1.setBorder(new javax.swing.border.MatteBorder(null));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tabla de Errores", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Comic Sans MS", 1, 18))); // NOI18N

        TablaErrores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Token", "Lexema", "Linea", "Descripcion"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(TablaErrores);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 497, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 9, Short.MAX_VALUE))
        );

        btnLimpiarLex.setText("Limpiar");
        btnLimpiarLex.setBorder(new javax.swing.border.MatteBorder(null));
        btnLimpiarLex.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarLexActionPerformed(evt);
            }
        });

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tabla Simbolos", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Comic Sans MS", 1, 18))); // NOI18N

        TablaTokens.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "TOKEN", "LEXEMA", "TIPO"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(TablaTokens);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnAnalizarLex, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnArchivo, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(528, 528, 528)
                        .addComponent(btnLimpiarLex, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(177, Short.MAX_VALUE))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAnalizarLex, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnArchivo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLimpiarLex, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(59, Short.MAX_VALUE))
        );

        jPanel2.getAccessibleContext().setAccessibleName("TABLA DE ERRORES");

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1170, 550));

        txtAnalizarLex.setEditable(false);
        txtAnalizarLex.setColumns(20);
        txtAnalizarLex.setRows(5);
        jScrollPane2.setViewportView(txtAnalizarLex);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 460, 0, 10));

        Tablaorden.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane6.setViewportView(Tablaorden);

        getContentPane().add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 460, 0, 0));

        Lexemes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane8.setViewportView(Lexemes);

        getContentPane().add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 460, 0, 10));

        Tablaorden1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane9.setViewportView(Tablaorden1);

        getContentPane().add(jScrollPane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 460, 0, 0));

        Documento.setEditable(false);
        Documento.setColumns(20);
        Documento.setRows(5);
        jScrollPane4.setViewportView(Documento);

        getContentPane().add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 570, 10, 0));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLimpiarLexActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarLexActionPerformed
        // TODO add your handling code here:
        txtResultado.setText(null);
        txtResultado.setText(null);
        txtResultado.setText(null);
    }//GEN-LAST:event_btnLimpiarLexActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String ruta = "";
        try {
            // TODO add your handling code here:

            // SELECIONAMOS LA CARPETA DONDE SE GUARDARA
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FrmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(FrmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(FrmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(FrmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        JFileChooser f = new JFileChooser();
        f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        f.showSaveDialog(null);

        System.out.println(f.getCurrentDirectory());   // PUESTO PARA VERIFICAR
        System.out.println(f.getSelectedFile());
        //  String direct = String.valueOf(f);
        ruta = f.getSelectedFile() + "\\Archivo de Tokens.txt";

        try {

            String contenido = Documento.getText();
            File file = new File(ruta);
            // Si el archivo no existe es creado
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            try (BufferedWriter bw = new BufferedWriter(fw)) {
                bw.write(contenido);
            }
            JOptionPane.showMessageDialog(null, "El Archivo se ha Guardado Exitosamente!");
        } catch (Exception e) {

            System.err.println("No se ha podido crear el archivo :( = " + e);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnAnalizarLexActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnalizarLexActionPerformed

        try {

            analizarLexico();
        } catch (IOException ex) {
            Logger.getLogger(FrmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAnalizarLexActionPerformed

    private void btnArchivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnArchivoActionPerformed
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File archivo = new File(chooser.getSelectedFile().getAbsolutePath());

        try {
            String ST = new String(Files.readAllBytes(archivo.toPath()));
            txtResultado.setText(ST);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FrmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FrmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnArchivoActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea Documento;
    private javax.swing.JTable Lexemes;
    private javax.swing.JTable TablaErrores;
    private javax.swing.JTable TablaTokens;
    private javax.swing.JTable Tablaorden;
    private javax.swing.JTable Tablaorden1;
    private javax.swing.JButton btnAnalizarLex;
    private javax.swing.JButton btnArchivo;
    private javax.swing.JButton btnLimpiarLex;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTextArea txtAnalizarLex;
    private javax.swing.JTextArea txtResultado;
    // End of variables declaration//GEN-END:variables
}
