/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Imprimir;

import Methods.Utilities;
import Paradox.ParadoxController;

/**
 *
 * @author rdiazarr
 */
public class ParadoxPrint {
    
    private final ParadoxController scene;
    
    private final Utilities utilidades;
    
    /**
     * Constructor de la clase
     * @param paradoxScene 
     */
    public ParadoxPrint (ParadoxController paradoxScene){
        this.scene = paradoxScene;
        this.utilidades = new Utilities();
    }
   
    
    public void numbers(final String modulus, final String exponent, final String message, final int radix) {
        this.scene.getModulus().setText(this.utilidades.putPoints(modulus, radix));
        this.scene.getExponent().setText(this.utilidades.putPoints(exponent, radix));
        this.scene.getMessage().setText(this.utilidades.putPoints(message, radix));        
    }
    
    public void partialClear() {
        this.scene.getEstimationCiphers().clear();
        this.scene.getNumCyphers().clear();
        this.scene.getAvgCiphersStats().clear();       
        this.scene.getResults().clear();
        this.scene.getPrivateKey().clear();
        this.scene.getTime().clear();
    }
    
    public void enableStop() {
        this.scene.getStartBttn().setText("   Parar   ");
        this.scene.getClearBttn().setDisable(true);
    }
    
    public void enablePause() {
        this.scene.getPauseBttn().setText("Pausar");
        this.scene.getPauseBttn().setDisable(false);
        this.scene.getClearBttn().setDisable(true);
    }
    
    public void enableStart() {
        this.scene.getStartBttn().setText("Comenzar");
        this.scene.getPauseBttn().setDisable(true);
        this.scene.getClearBttn().setDisable(false);
    }
    
     public void enableContinue() {
        this.scene.getPauseBttn().setText("Continuar");        
        this.scene.getPauseBttn().setDisable(false);
        this.scene.getClearBttn().setDisable(true);
    }
     
    public void editableModExp(boolean editable) {
        this.scene.getExponent().setEditable(editable);
        this.scene.getModulus().setEditable(editable);
        this.scene.getMessage().setEditable(editable);
    }

    //IMPRESION DE RESULTADOS
    public void initialResults(String message, String cipherI, String cipherJ, String j, String modulus, int radix) {
        //el mensaje/número elegido es igual que el cipherI por estar elevado a 1
        String lineas = "Columna I \n" +
                        "---------------------------\n" + 
                        "mensaje^0 mod Módulo\n";        
        
        
        lineas = lineas + this.utilidades.putPoints(message, radix) + "^0 mod " + this.utilidades.putPoints(modulus, radix) + " = " + this.utilidades.putPoints(cipherI, radix)+ "\n";
        
        lineas = lineas +  "\nColumna J \n" +
                        "-----------------------------\n" + 
                        "mensaje^(Módulo/2)+1 mod Módulo \n"; 
        
        lineas = lineas + this.utilidades.putPoints(message, radix) + "^" + this.utilidades.putPoints(j,radix) + " mod " + this.utilidades.putPoints(modulus, radix) + " = " + this.utilidades.putPoints(cipherJ, radix) + "\n\n";
        
        
        lineas = lineas + "\n\nCifrados sucesivos\n" 
                        + "--------------------------------\n";
        
        this.scene.getResults().setText(lineas);        
    }
    
    
    public void partialResults(String results) {
        this.scene.getResults().appendText(results);
        
    }
    
    public void colisionDetected(String text) {
        this.scene.getResults().appendText(text);
        
    }
    
    public void modInverseError(String text) {
        this.scene.getResults().appendText(text);
        
    }

    public void wValue(String i, String j, String exponent, String w, int radix) {
        this.scene.getResults().appendText("\n\n    Cálculo de la Clave Privada, Clave Privada Pareja o Falso Positivo:\n");
        this.scene.getResults().appendText(" --> Se calcula w = (i - j) / mcd (e, |i - j|)."
                + " \n     Siendo i = " + this.utilidades.putPoints(i, radix) +
                ",  j = " + this.utilidades.putPoints(j, radix) +
                "  y la clave pública = " + this.utilidades.putPoints(exponent, radix) + ".\n"
                + " --> Resultado w = " + this.utilidades.putPoints(w, radix) + "\n");
    }
     
    public void tValue(String t, int radix) {
        this.scene.getResults().appendText(" --> Se calcula  t = inv (e, w).\n");
        this.scene.getResults().appendText(" --> Resultado t = " + this.utilidades.putPoints(t, radix) + "\n\n");
    } 
    
    public void privateKey(String privateKey, int radix) {
        this.scene.getPrivateKey().setText(this.utilidades.putPoints(privateKey, radix));        
    }

    public void time(String Time) {
        this.scene.getTime().setText(Time);
    }
    
    
    
    public void goodResult() {
         this.scene.getResults().appendText(" El resultado t obtenido es la Clave Privada  o una Clave Privada Pareja.");
    }
    
    public void badResult() {
        this.scene.getResults().appendText(" El resultado t obtenido es un Falso Positivo, es decir, \n" +
                 " solo descifra el mensaje introducido. Prueba con otro mensaje.");
    }
    
    public void attackStopped() {
        this.scene.getResults().appendText("\n\n ******** EL ATAQUE SE HA DETENDIDO ******** ");
        this.scene.getPrivateKey().clear();
    }
    
    public void attackPaused() {
        this.scene.getResults().appendText("\n\n ******** EL ATAQUE SE HA PAUSADO ******** ");
        this.scene.getPrivateKey().clear();
    }
    
    public void attackContinue() {
        this.scene.getResults().appendText("\n\n ******** EL ATAQUE CONTINUA  ******** \n\n");
        this.scene.getPrivateKey().clear();
    }

    public void estimation ( final String estimation){        
        this.scene.getEstimationCiphers().setText(this.utilidades.putPoints(estimation, 10));
    }

    public void Stats(final String stats, final String numCyphers) {
        this.scene.getNumCyphers().setText(this.utilidades.putPoints(numCyphers, 10));
        this.scene.getAvgCiphersStats().setText(this.utilidades.putPoints(stats, 10));
    }
        
    
    
    
    public void partialDelete() {
        this.scene.getEstimationCiphers().clear();
        this.scene.getNumCyphers().clear();
        this.scene.getAvgCiphersStats().clear();
        this.scene.getResults().appendText("Error al realizar el ataque, la columna I \n"
                + "ha sobrepasado el valor de la columna J");
        this.scene.getPrivateKey().clear();
        this.scene.getTime().clear();
    }
    
    public void delete() {
        this.scene.getEstimationCiphers().clear();
        this.scene.getNumCyphers().clear();
        this.scene.getAvgCiphersStats().clear();
        this.scene.getResults().clear();
        this.scene.getPrivateKey().clear();
        this.scene.getTime().clear();
    }

    public void NNCmessage(int radix) {
        this.scene.getResults().setText("\n\n\n\n\n\n\n\n\n"
                + "                               *** EL MENSAJE INTRODUCIDO ES ****\n"
                + "                                 *** UN NÚMERO NO CIFRABLE ****");
        this.scene.getPrivateKey().clear();
        this.scene.getTime().clear();         
        this.scene.getEstimationCiphers().clear();
        this.scene.getNumCyphers().clear();
        this.scene.getAvgCiphersStats().clear();
    }

    
    
}
