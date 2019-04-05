package analizador;

import java.io.File;

/**
 *
 * @author Lenovo
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        String path = "C:/Users/Lenovo/Documents/NetBeansProjects/AnalizadorLexico/src/analizador/Lexer.flex";
        generarLexer(path);
        new Inteface().setVisible(true);
    }

    public static void generarLexer(String path) {
        File file = new File(path);
        jflex.Main.generate(file);
    }

}
