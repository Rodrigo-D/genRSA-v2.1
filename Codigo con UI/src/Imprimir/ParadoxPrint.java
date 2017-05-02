/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Imprimir;

import Paradox.ParadoxController;

/**
 *
 * @author rdiazarr
 */
public class ParadoxPrint {
    
    private final ParadoxController scene;
    
    /**
     * Constructor de la clase
     * @param paradoxScene 
     */
    public ParadoxPrint (ParadoxController paradoxScene){
        this.scene = paradoxScene;
    }
   
    
    public void numbers(String modulus, String exponent, String message) {
        this.scene.getModulus().setText(modulus);
        this.scene.getExponent().setText(exponent);
        this.scene.getMessage().setText(message);    
        
    }
    
    public void partialClear() {
        this.scene.getAvgCiphersStats().clear();       
        this.scene.getResults().clear();
        this.scene.getPrivateKey().clear();
        this.scene.getTime().clear();
    }
    
    public void dissableStart() {
        this.scene.getStartBttn().setDisable(true);
        this.scene.getClearBttn().setDisable(true);
    }
    
     public void enableStart() {
        this.scene.getStartBttn().setDisable(false);
        this.scene.getClearBttn().setDisable(false);
    }

    //IMPRESION DE RESULTADOS
    public void initialResults(String cipherI, String cipherJ, String j, String modulus) {
        //el mensaje/número elegido es igual que el cipherI por estar elevado a 1
        String lineas = "Columna i (valor inicial)            Columna J (valor buscado)\n" +
                        "----------------------------------------------------------------\n" + 
                        "mensaje^1 mod Módulo            mensaje^(modulo/2) mod Módulo \n";        
        
        
        lineas = lineas + cipherI + "^1 mod " + modulus + "=" + cipherI + "         " +
               cipherI + "^" + j + " mod " + modulus + "=" + cipherJ + "\n\n";
        
        lineas = lineas + "\nCifrados sucesivos columna I\n" 
                        + "--------------------------------\n";
        
        this.scene.getResults().setText(lineas);        
    }
    
    
    public void partialResults(String results) {
        this.scene.getResults().appendText(results);
        
    }

    public void wValue(String i, String j, String exponent, String w) {
        this.scene.getResults().appendText("\n\n    Cálculo de la Clave Privada, Clave Privada Pareja o Falso Positivo:\n");
        this.scene.getResults().appendText(" --> Se calcula w = (i - j) / mcd (e, |i - j|)."
                + " \n     Siendo i=" + i + ", j=" + j + " y la clave pública= " + exponent + ".\n"
                + " --> Resultado w = " + w + "\n");
    }
     
    public void tValue(String t) {
        this.scene.getResults().appendText(" --> Se calcula  t = inv (e, w).\n");
        this.scene.getResults().appendText(" --> Resultado t = " + t + "\n\n");
    } 
    
    public void privateKey(String privateKey) {
        this.scene.getPrivateKey().setText(privateKey);        
    }

    public void time(String Time) {
        this.scene.getTime().setText(Time);
    }
    
    
    
    public void goodResult() {
         this.scene.getResults().appendText(" El resultado t obtenido es la Clave Privada  o una Clave Privada Pareja.");
    }
    
    public void badResult() {
         this.scene.getResults().appendText(" El resultado t obtenido es un Falso Positivo, \n" +
                 " es decir, solo descifra el mensaje introducido."
                 + " Prueba con otro mensaje.");
    }
    
    public void Stats(String stats) {
        this.scene.getAvgCiphersStats().setText(stats);
    }
        
    
    
    
    public void partialDelete() {
        this.scene.getAvgCiphersStats().clear();
        this.scene.getResults().setText("Error al realizar el ataque, la columna I \n"
                + "ha sobrepasado el valor de la columna J");
        this.scene.getPrivateKey().clear();
        this.scene.getTime().clear();
    }

   
    
    

    
    
}
