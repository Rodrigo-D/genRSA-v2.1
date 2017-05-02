/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Imprimir;

import Factorize.FactorizeController;

/**
 *
 * @author rdiazarr
 */
public class FactorizePrint {
    
    private final FactorizeController scene;
    
    /**
     * Constructor de la clase
     * @param factorizeScene 
     */
    public FactorizePrint (FactorizeController factorizeScene){
        this.scene = factorizeScene;
    }
    
    
    
    
    public void clear(){
        this.scene.getPrimeP().clear();
        this.scene.getPrimeQ().clear();
        this.scene.getResults().clear();
        this.scene.getTime().clear();
    }    
    
    public void editableModulus(boolean disable) {
       this.scene.getModulus().setEditable(disable);
    }
    
    public void disableBttns() {
        this.scene.getContinueBttn().setDisable(true);
        this.scene.getStartBttn().setDisable(true);
        this.scene.getClearBttn().setDisable(true);
        this.scene.getObtainPQ().setDisable(true);
    }
    
    public void functionValues(String lines) {        
        this.scene.getResults().appendText(lines);
      
    }    
    
    public void primeP(String primeP){
        this.scene.getPrimeP().setText(primeP.toUpperCase());
    }

    public void primeQ(String primeQ){
        this.scene.getPrimeQ().setText(primeQ.toUpperCase());
    }    
    
    public void modulus(String modulus) {
        this.scene.getModulus().setText(modulus.toUpperCase());
    }
    
    public void lapsNum(String lapsNum) {
        this.scene.getNumLaps().setText(lapsNum);
    }
    
    public void find(String vuelta) {
        String line = "\nMódulo factorizado en la vuelta -> " + vuelta + "\n\n\n";        
        this.scene.getResults().appendText(line);        
    }
    
    public void EnableStartBttns() {
        this.scene.getContinueBttn().setDisable(true);
        this.scene.getStartBttn().setDisable(false);  
        this.scene.getClearBttn().setDisable(false);
        this.scene.getObtainPQ().setDisable(false);
        this.scene.getObtainPQ().setSelected(false);
        this.scene.getNumLaps().setDisable(false);
    }
    
    public void dissableStartBttns() {
        this.scene.getContinueBttn().setDisable(false);
        this.scene.getStartBttn().setDisable(true);
        this.scene.getClearBttn().setDisable(false);
        this.scene.getObtainPQ().setDisable(true);
        
    }
    
    public void time(String time) {
        this.scene.getTime().setText(time);
    }
}
