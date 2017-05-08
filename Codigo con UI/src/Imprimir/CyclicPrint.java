/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Imprimir;

import Cyclic.CyclicController;
import Metodos.Utilidades;
import javafx.scene.effect.BlendMode;

/**
 *
 * @author rdiazarr
 */
public class CyclicPrint {
    
    private final CyclicController scene;
    
    private final Utilidades utilidades;
    
    /**
     * Constructor de la clase
     * @param cyclicScene 
     */
    public CyclicPrint (CyclicController cyclicScene){
        this.scene = cyclicScene;
        this.utilidades = new Utilidades();
    }
   
    public void messages(String cypherMessage, String message, String modulus, String exponent, int radix) {
        this.scene.getMessage().setText(this.utilidades.putPoints(message, radix));
        this.scene.getCypherMessage().setText(this.utilidades.putPoints(cypherMessage, radix));
        this.scene.getExponent().setText(this.utilidades.putPoints(exponent, radix));
        this.scene.getModulus().setText(this.utilidades.putPoints(modulus, radix));
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
    }

    public void messageRecovered(String message, int radix) {
        this.scene.getMRecovered().setText(this.utilidades.putPoints(message, radix));
        this.scene.getContinueBttn().setDisable(true);
    }

    public void numOfCyphers(String numOfCyphers) {
        this.scene.getNumCiphers().setText(this.utilidades.putPoints(numOfCyphers, 10));
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
