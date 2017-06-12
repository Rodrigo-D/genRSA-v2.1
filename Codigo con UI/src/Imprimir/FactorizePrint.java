/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Imprimir;

import Factorize.FactorizeController;
import Methods.Utilities;

/**
 *
 * @author rdiazarr
 */
public class FactorizePrint {
    
    private final FactorizeController scene;
    
    private final Utilities utilidades;
    
    /**
     * Constructor de la clase
     * @param factorizeScene 
     */
    public FactorizePrint (FactorizeController factorizeScene){
        this.scene = factorizeScene;
        this.utilidades  = new Utilities();
    } 
    
    
    
    public void clear(){
        this.scene.getPrimeP().clear();
        this.scene.getPrimeQ().clear();
        this.scene.getResults().clear();
        this.scene.getTime().clear();
    }    
    
    public void editableModulus(boolean value) {
       this.scene.getModulus().setEditable(value);
    }
    
    public void disableBttns() {
        this.scene.getContinueBttn().setDisable(true);
        this.scene.getStartBttn().setText("    Parar    ");        
        this.scene.getStartBttn().setDisable(false);
        this.scene.getClearBttn().setDisable(true);
        this.scene.getObtainPQ().setDisable(true);
    }
    
    public void functionValues(String lines) {        
        this.scene.getResults().appendText(lines);
      
    }    
    
    public void primeP(String primeP, int radix){
        this.scene.getPrimeP().setText(this.utilidades.putPoints(primeP.toUpperCase(), radix));
    }

    public void primeQ(String primeQ, int radix){
        this.scene.getPrimeQ().setText(this.utilidades.putPoints(primeQ.toUpperCase(), radix));
    }    
    
    public void modulus(String modulus, int radix) {
        this.scene.getModulus().setText(this.utilidades.putPoints(modulus.toUpperCase(), radix));
    }
    
    public void lapsNum(String lapsNum) {
        this.scene.getNumLaps().setText(this.utilidades.putPoints(lapsNum, 10));
    }
    
    public void find(String vuelta) {
        String line = "\nMódulo factorizado en la vuelta -> " + this.utilidades.putPoints(vuelta, 10) + "\n\n\n";        
        this.scene.getResults().appendText(line);        
    }
    
    public void attackStopped() {
        this.scene.getResults().appendText("\n\n ******** EL ATAQUE SE HA DETENDIDO ******** ");
    }
    
    public void EnableStartBttns() {
        this.scene.getContinueBttn().setDisable(true);
        this.scene.getStartBttn().setDisable(false);  
        this.scene.getStartBttn().setText("Comenzar");
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
