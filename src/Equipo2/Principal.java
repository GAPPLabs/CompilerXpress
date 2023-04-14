/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Equipo2;

import java.io.File;
import java.io.IOException;


/**
 *
 * 
 */ 
public class Principal {
    public static void main(String[] args) throws Exception {
        String ruta1 = "C:\\Users\\iosal\\OneDrive\\Documentos\\10 SEMESTRE\\LENGUAJES Y AUTÓMATAS II\\UNIDAD 2\\eror\\Lexer.flex";
        generar(ruta1);
    }
    public static void generar(String ruta1) throws IOException, Exception{
        File archivo;
        archivo = new File(ruta1);
        JFlex.Main.generate(archivo);
    }
}
