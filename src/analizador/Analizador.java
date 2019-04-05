/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizador;
import java.io.*;

/**
 *
 * @author Lenovo
 */
public class Analizador {
    FileInputStream entrada; 
    File archivo;
    
    public Analizador(){
        
    }
    
    public String AbrirTxt(File archivo){
        String contenido="";
        
        try {
            
            entrada = new FileInputStream(archivo);
            
            int assci;
                    
             while ((assci=entrada.read()) !=-1) {
                char caracter = (char)assci;
                contenido+=caracter;
            }
        } catch (Exception e) {
        }
        
        return contenido;
    }
    
    
    public String GuardarTxt(String ruta, String contenido){
    
        String respuesta = null;
        
        try {
            
            File file = new File(ruta);
            
            if (!file.exists()) {
                file.createNewFile();
            }
                        
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            
            String texto = contenido;
            String [] lineas = texto.split("\n");
            
            for (String linea : lineas) {
                bw.write(linea);
                bw.newLine();            
            } 
                    
            bw.close();
            respuesta ="se guardo con exito el archivo";
                    
        } catch (Exception e) {
            respuesta ="error al guardar el archivo";
        }

        return respuesta;
    }
    
    
}
