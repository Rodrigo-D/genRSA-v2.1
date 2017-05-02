/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Imprimir;

import Cyclic.CyclicController;
import javafx.scene.effect.BlendMode;

/**
 *
 * @author rdiazarr
 */
public class CyclicPrint {
    
    private final CyclicController scene;
    
    /**
     * Constructor de la clase
     * @param cyclicScene 
     */
    public CyclicPrint (CyclicController cyclicScene){
        this.scene = cyclicScene;
    }
   
    public void messages(String cypherMessage, String message, String modulus, String exponent) {
        this.scene.getMessage().setText(message);
        this.scene.getCypherMessage().setText(cypherMessage);
        this.scene.getExponent().setText(exponent);
        this.scene.getModulus().setText(modulus);
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
        this.scene.getResults().appendText("\n  Mensaje descifrado en la vuelta " + lap);        
        this.scene.getResults().setScrollTop(Double.MAX_VALUE);
    }
    
    public void notFind(String toString) {
        this.scene.getResults().appendText("\n  Mensaje no recuperado \n\n");
        this.scene.getResults().setScrollTop(Double.MAX_VALUE);
    }

    public void messageRecovered(String message) {
        this.scene.getMRecovered().setText(message);
        this.scene.getContinueBttn().setDisable(true);
    }

    public void numOfCyphers(String numOfCyphers) {
        this.scene.getNumCiphers().setText(numOfCyphers);
    }
    
    //parte de botones
    public void dissableStart() {
        this.scene.getMessage().setEditable(false);
        this.scene.getModulus().setEditable(false);
        this.scene.getExponent().setEditable(false);
        this.scene.getComplete().setDisable(true);
        this.scene.getContinueBttn().setDisable(false);
        this.scene.getStartBttn().setDisable(true);
    }
    
    public void enableStart() {
        this.scene.getMessage().setEditable(true);
        this.scene.getComplete().setDisable(false);
        this.scene.getModulus().setEditable(true);
        this.scene.getExponent().setEditable(true);
        this.scene.getContinueBttn().setDisable(true);
        this.scene.getStartBttn().setDisable(false);
    }

    public void enableLapsNum() {
        this.scene.getNumCiphers().setDisable(false);
        this.scene.getNumCiphers().setBlendMode(BlendMode.SRC_OVER);
        this.scene.getNumCiphers().setText("10");
        this.scene.getComplete().setSelected(false);
        this.scene.getContinueBttn().setDisable(true);
    }
    
    public void inProgress(){
        this.scene.getNumCiphers().setEditable(false);
        this.scene.getComplete().setDisable(true);        
        this.scene.getModulus().setEditable(false);        
        this.scene.getExponent().setEditable(false);                
        this.scene.getMessage().setEditable(false);
        
        this.scene.getStartBttn().setDisable(true);
        this.scene.getContinueBttn().setDisable(true);                
        this.scene.getClearBttn().setDisable(true);
    }
    
    public void endProgress(){
        this.scene.getNumCiphers().setEditable(true);
        this.scene.getComplete().setDisable(false);        
        this.scene.getModulus().setEditable(true);        
        this.scene.getExponent().setEditable(true);                
        this.scene.getMessage().setEditable(true);
        
        this.scene.getStartBttn().setDisable(false);
        this.scene.getContinueBttn().setDisable(false);                
        this.scene.getClearBttn().setDisable(false);
    }

   

   
}
