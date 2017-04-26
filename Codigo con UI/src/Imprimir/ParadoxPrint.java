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

    //IMPRESION DE RESULTADOS
    public void initialResults(String cipherI, String cipherJ, String j, String modulus) {
        //el mensaje/número elegido es igual que el cipherI por estar elevado a 1
        this.scene.getResults().setText(cipherI + "^1 mod " + modulus + "=" + cipherI + "         " +
               cipherI + "^" + j + " mod " + modulus + "=" + cipherJ + "\n\n" );
        
    }
    
    public void partialResults(String message, String i, String modulus, String cipherI) {
        this.scene.getResults().appendText(message + "^" + i + " mod " + modulus + " = " + cipherI + "\n" );
        
    }

    public void wValue(String i, String j, String exponent, String w) {
        this.scene.getResults().appendText("\n\n    Calculo de la Clave Privada, Clave Privada Pareja o Falso Positivo:\n");
        this.scene.getResults().appendText(" --> Se calcula w = (i - j) / mcd (e, |i - j|)."
                + " \n     Siendo i=" + i + ", j=" + j + " y la clave pública= " + exponent + ".\n"
                + " --> Resultado w = " + w + "\n");
    }
     
    public void tValue(String t) {
         this.scene.getResults().appendText(" --> Se calcula  t = inv (e, w).\n");
         this.scene.getResults().appendText(" --> Resultado t = " + t + "\n\n");
    } 
    
    public void goodResult() {
         this.scene.getResults().appendText(" El resultado t obtenido es la Clave Privada  o una Clave Privada Pareja.");
    }
    
    public void badResult() {
         this.scene.getResults().appendText(" El resultado t obtenido es un Falso Positivo, \n" +
                 " es decir, solo descifra el mensaje introducido."
                 + " Prueba con otro mensaje.");
    }
    
    //TERMINA LA IMPRESION DE RESULTADOS EN EL CUADRO
    
    public void partialDelete() {
        this.scene.getMessage().clear();
        this.scene.getCiphersStats().clear();
        this.scene.getAvgCiphersStats().clear();
        this.scene.getResults().clear();
        this.scene.getPrivateKey().clear();
        this.scene.getTime().clear();
    }

    public void privateKey(String privateKey) {
        this.scene.getPrivateKey().setText(privateKey);
        
    }

    public void time(String Time) {
        this.scene.getTime().setText(Time);
    }
    
    public void avgStats(String stats) {
        this.scene.getAvgCiphersStats().setText(stats);
    }

    
    
}
