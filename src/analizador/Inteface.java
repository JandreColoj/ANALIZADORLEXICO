package analizador;

import java.io.*;
import java.util.ArrayList;
import javax.swing.*;

public class Inteface extends javax.swing.JFrame {
    
    JFileChooser seleccionar  = new JFileChooser();
    File archivo = null; 
    Analizador analizador  = new Analizador();
    
    //variables
    String resultado="";
    int linea_actual=0;
    boolean universo = false;        
    
    public Inteface() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btn_abrir = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        txt_texto = new javax.swing.JTextArea();
        btn_guardar = new javax.swing.JButton();
        btn_analizar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 153, 204));

        btn_abrir.setText("ABRIR ARCHIVO");
        btn_abrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_abrirActionPerformed(evt);
            }
        });

        txt_texto.setColumns(20);
        txt_texto.setRows(5);
        jScrollPane2.setViewportView(txt_texto);

        btn_guardar.setText("GUARDAR");
        btn_guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_guardarActionPerformed(evt);
            }
        });

        btn_analizar.setText("ANALIZAR");
        btn_analizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_analizarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(227, 227, 227)
                        .addComponent(btn_abrir))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 552, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(18, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btn_analizar)
                .addGap(109, 109, 109)
                .addComponent(btn_guardar)
                .addGap(150, 150, 150))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(btn_abrir)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_guardar)
                    .addComponent(btn_analizar))
                .addGap(18, 18, 18))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_abrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_abrirActionPerformed
       
        if (seleccionar.showDialog(this, "Abrir archivo")==JFileChooser.APPROVE_OPTION) {
            this.archivo = seleccionar.getSelectedFile();
            
            if (archivo.canRead()) {
                if (archivo.getName().endsWith("txt")) {                    
                     
                    String contenido = analizador.AbrirTxt(archivo);
                    txt_texto.setText(contenido);
               }
            }
        }
        
    }//GEN-LAST:event_btn_abrirActionPerformed

    private void btn_guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_guardarActionPerformed
        
        if (seleccionar.showDialog(null, "Guardar texto")==JFileChooser.APPROVE_OPTION) {
            archivo = seleccionar.getSelectedFile();
            
            if (archivo.getName().endsWith("txt")) {
                
                String ruta = archivo.getAbsolutePath();
                String contenido = txt_texto.getText();
                String respuesta = analizador.GuardarTxt(ruta, contenido);
                     
                if (respuesta!=null) {
                    JOptionPane.showMessageDialog(null, respuesta);
                }else{
                    JOptionPane.showMessageDialog(null, respuesta);
                }
                
            }else{
                JOptionPane.showMessageDialog(null, "El archivo debe guardarse en formato .txt");
            }
        }
    }//GEN-LAST:event_btn_guardarActionPerformed

    private void btn_analizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_analizarActionPerformed
        try{
             LexerFile();
        }
        catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }//GEN-LAST:event_btn_analizarActionPerformed
    
    public void LexerFile() throws IOException{
        resultado ="    SALIDA                                     ENTRADA \n";
        resultado =resultado+"   ------------------------------------------------------------------------------------------------------ \n";
        File ruta = archivo.getAbsoluteFile();
        
        Reader reader = new BufferedReader(new FileReader(ruta));
        Lexer lexer = new Lexer (reader);
        

        int linea = 1;
        int token_contador = 1;
        ArrayList<String> errorLine = new ArrayList<String>();
        boolean definicion = false;
        boolean operacion = false;
        boolean universo = false;
        
        while (true){
            Token token =lexer.yylex();
            
            if (token == null){
                //resultado=resultado+"EOF";;
                resultado =resultado+"\n   ------------------------------------------------------------------------------------------------------ ";
                txt_texto.setText(resultado);
                return;
            }
            System.err.println(token);
            String espacio="";
            if (linea<10) {
                 espacio=" ";
            }
            
            switch (token){
                 
                case DEFINICION:
                    if (!definicion) {
                        resultado = resultado +"   | Linea "+linea+" Correcta.                       "+espacio+" | "+lexer.yytext();
                        definicion=true;
                    }else{
                        resultado = resultado +"\n   | Linea "+linea+" ERROR.                          "+espacio+" | "+lexer.yytext()+" YA ESTA DECLARADA";
                    }

                    break;
                case UNIVERSO:
                    if (!universo) {
                        resultado = resultado +"\n   | Linea "+linea+" Correcta.                       "+espacio+" | "+lexer.yytext();
                        universo=true;
                    }else{
                        resultado = resultado +"\n   | Linea "+linea+" ERROR.                          "+espacio+" | "+lexer.yytext()+" YA ESTA DECLARADA";
                    }
                    break;
                case CONJUNTO:
                    resultado = resultado +"\n   | Linea "+linea+" Correcta.                       "+espacio+" | "+lexer.yytext();
                    break;
                case CONJUNTO_VACIO:
                    resultado = resultado +"\n   | Linea "+linea+" Correcta.                       "+espacio+" | "+lexer.yytext();
                    linea++;
                    break;
                case OPERACION:
                    if (!operacion) {
                         resultado = resultado +"\n   | Linea "+linea+ " Correcta.                       "+espacio+" | "+lexer.yytext();
                         operacion=true;
                    }else{
                        resultado = resultado +"\n   | Linea "+linea+" ERROR.                          "+espacio+" | "+lexer.yytext()+" YA ESTA DECLARADA";
                    }
                    token_contador++;
                    break;
                case OPERACIONES:   
                    resultado = resultado +"\n   | Linea "+linea+" Correcta.                       "+espacio+" | "+lexer.yytext();
                    token_contador++;
                    break;
                case LINEA:
                    linea++;
                    break;
                case ERROR:
                     
                    //busca si ya la linea donde esta el error ya esta agregado
                    boolean blnFound = errorLine.contains(Integer.toString(linea));
                    
                    //si no esta agregado a la lista guarda en que linea esta el error
                    if (!blnFound) {
                        linea_actual =linea;
                        resultado=resultado+"\n   | Linea "+linea+" Error, Sintaxis incorrecto.      "+espacio+"| ";
                        errorLine.add(Integer.toString(linea));
                    }
                    
                    //agrega los caracteres incorrectos
                    if(linea_actual==linea){
                        resultado=resultado+lexer.yytext();
                    }
                   
                    token_contador++;
                    break;
            }
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_abrir;
    private javax.swing.JButton btn_analizar;
    private javax.swing.JButton btn_guardar;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea txt_texto;
    // End of variables declaration//GEN-END:variables
}
