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
   
    public void messages(String cypherMessage, String message) {
        this.scene.getMessage().setText(message);
        this.scene.getCypherMessage().setText(cypherMessage);
    }

    public void time(String Time) {
        this.scene.getTime().setText(Time);
    }

    public void initResult(String cypherMessage) {
        this.scene.getResults().setText("c0 = " + cypherMessage + "\n" );
    }

    public void partialResults(String lap, String message) {
        this.scene.getResults().appendText("c" + lap + " = " + message + "\n" );
    }

    public void find(String lap) {
        this.scene.getResults().appendText("\n  Mensaje descifrado en la vuelta " + lap);
    }
    
    public void notFind(String toString) {
        this.scene.getResults().appendText("\n  Mensaje no recuperado \n\n");
    }

    public void messageRecovered(String message) {
        this.scene.getMRecovered().setText(message);
    }

    public void numOfCyphers(String numOfCyphers) {
        this.scene.getNumCiphers().setText(numOfCyphers);
    }
    
    //parte de botones
    public void dissableStart() {
        this.scene.getMessage().setEditable(false);
        this.scene.getComplete().setDisable(true);
        this.scene.getContinueBttn().setDisable(false);
        this.scene.getStartBttn().setDisable(true);
    }
    
    public void enableStart() {
        this.scene.getMessage().setEditable(true);
        this.scene.getComplete().setDisable(false);
        this.scene.getContinueBttn().setDisable(true);
        this.scene.getStartBttn().setDisable(false);
    }

    public void enableLapsNum() {
        this.scene.getNumCiphers().setDisable(false);
        this.scene.getNumCiphers().setBlendMode(BlendMode.SRC_OVER);
        this.scene.getNumCiphers().setText("10");
        this.scene.getComplete().setSelected(false);
    }

   
}
