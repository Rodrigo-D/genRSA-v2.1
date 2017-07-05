/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Imprimir;

import Cyclic.CyclicController;
import Methods.Utilities;
import javafx.scene.effect.BlendMode;

/**
 *
 * @author rdiazarr
 */
public class CyclicPrint {
    
    private final CyclicController scene;
    
    private final Utilities utilidades;
    
    /**
     * Constructor de la clase
     * @param cyclicScene 
     */
    public CyclicPrint (CyclicController cyclicScene){
        this.scene = cyclicScene;
        this.utilidades = new Utilities();
    }
   
    public void messages(String cypheredMessage, String modulus, String exponent, int radix) {
        this.scene.getCypherMessage().setText(this.utilidades.putPoints(cypheredMessage, radix));
        this.scene.getExponent().setText(this.utilidades.putPoints(exponent, radix));
        this.scene.getModulus().setText(this.utilidades.putPoints(modulus, radix));
        this.scene.getMRecovered().clear();
        this.scene.getMessage().clear();
    }
    
    public void NNCmessage(int radix) {
        this.scene.getResults().setText("\n\n\n\n\n\n\n\n\n *** EL MENSAJE CIFRADO INTRODUCIDO ES ****\n"
                + "             *** UN NÚMERO NO CIFRABLE ****");
        this.scene.getMRecovered().clear();
        this.scene.getMessage().clear();
        this.scene.getTime().clear();
    }

    public void time(String Time) {
        this.scene.getTime().setText(Time);
    }

    public void clearResults() {
        this.scene.getResults().clear();
    }
   
    public void partialResults(String lines) {
        this.scene.getResults().appendText(lines );
    }

    public void find(String lap) {
        this.scene.getResults().appendText("\n  Mensaje descifrado en la vuelta " + this.utilidades.putPoints(lap, 10));        
        this.scene.getResults().setScrollTop(Double.MAX_VALUE);
    }
    
    public void notFind(String lap) {
        this.scene.getResults().appendText("\n  Mensaje no recuperado. Vuelta -> " +
                this.utilidades.putPoints(lap, 10) + "\n\n");
        this.scene.getResults().setScrollTop(Double.MAX_VALUE);
        this.scene.getMRecovered().setText("   NO");
    }
    
    public void attackStopped() {
        this.scene.getResults().appendText("\n\n ******** EL ATAQUE SE HA DETENDIDO ******** ");
        this.scene.getMRecovered().clear();
    }


    public void messageRecovered(String message, int radix) {
        this.scene.getMRecovered().setText("    SI");
        this.scene.getMessage().setText(this.utilidades.putPoints(message, radix));
        this.scene.getContinueBttn().setDisable(true);
    }

    public void numOfCyphers(String numOfCyphers) {
        this.scene.getNumCiphers().setText(this.utilidades.putPoints(numOfCyphers, 10));
    }
    
    //parte de botones
    public void dissableStart() {
        this.scene.getCypherMessage().setEditable(false);
        this.scene.getModulus().setEditable(false);
        this.scene.getExponent().setEditable(false);
        this.scene.getComplete().setDisable(true);
        this.scene.getContinueBttn().setDisable(false);
        this.scene.getStartBttn().setDisable(true);
    }
    
    /**
     * Método para permitir parar el ataque.
     * Activa el boton Parar
     */
    public void enableStop() {
        this.scene.getStartBttn().setText("    Parar    ");
        this.scene.getStartBttn().setDisable(false);
        this.scene.getClearBttn().setDisable(true);
    }
    
    /**
     * Método para permitir comenzar el ataque.
     * Activa el botn Comenzar
     */
    public void enableStart() {
        this.scene.getStartBttn().setText("Comenzar");
        this.scene.getStartBttn().setDisable(false);
        this.scene.getClearBttn().setDisable(false);        
        this.scene.getContinueBttn().setDisable(true);
    }
   
    public void enableLapsNum() {
        this.scene.getNumCiphers().setDisable(false);
        this.scene.getNumCiphers().setBlendMode(BlendMode.SRC_OVER);
        this.scene.getNumCiphers().setText("10");
        this.scene.getComplete().setSelected(false);
    }
    
    public void inProgress(){
        this.scene.getNumCiphers().setEditable(false);
        this.scene.getComplete().setDisable(true);        
        this.scene.getModulus().setEditable(false);        
        this.scene.getExponent().setEditable(false);                
        this.scene.getCypherMessage().setEditable(false);
        
        this.scene.getContinueBttn().setDisable(true);                
        this.scene.getClearBttn().setDisable(true);
    }
    
    public void endProgress(){
        this.scene.getNumCiphers().setEditable(true);
        this.scene.getComplete().setDisable(false);        
        this.scene.getModulus().setEditable(true);        
        this.scene.getExponent().setEditable(true);                
        this.scene.getCypherMessage().setEditable(true);           
        this.scene.getClearBttn().setDisable(false);
    }

    public void enableContinue() {
        this.scene.getContinueBttn().setDisable(false);
    }

   
}
