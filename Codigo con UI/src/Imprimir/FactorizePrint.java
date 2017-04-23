/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Imprimir;

import Factorize.FactorizeController;
import javafx.scene.effect.BlendMode;

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
    
    //para controlar que no se modifique mientras se esta calculando algo
    public void moduleEditable(boolean editable) {
        if (editable){
            this.scene.getModule().setBlendMode(BlendMode.SRC_OVER);
        } else {
            this.scene.getModule().setBlendMode(BlendMode.DARKEN);
        }
        this.scene.getModule().setEditable(editable);
    }
    
    public void clear(){
        this.scene.getPrimeP().clear();
        this.scene.getPrimeQ().clear();
        this.scene.getResults().clear();
    }
    
    
    public void primeP(String primeP){
        this.scene.getPrimeP().setText(primeP.toUpperCase());
    }


    public void primeQ(String primeQ){
        this.scene.getPrimeQ().setText(primeQ.toUpperCase());
    }
    
    
    public void module(String module) {
        this.scene.getModule().setText(module.toUpperCase());
    }
    
    
    public void lapsNum(String lapsNum) {
        this.scene.getNumLaps().setText(lapsNum);
    }

    
    public void disableLapsNum() {
        this.scene.getNumLaps().setEditable(false);        
        this.scene.getNumLaps().setBlendMode(BlendMode.DARKEN);        
    }
    
    public void enableLapsNum() {
        this.scene.getNumLaps().setEditable(true);        
        this.scene.getNumLaps().setBlendMode(BlendMode.SRC_OVER); 
        this.scene.getObtainPQ().setSelected(false);
    }
    
    
    public void disableBttns() {
        this.scene.getContinueBttn().setDisable(true);
        this.scene.getStartBttn().setDisable(true);
    }
    
    
    public void enableBttns() {
        this.scene.getStartBttn().setDisable(false);
    }
    
    
    public void dissableStart() {
        this.scene.getContinueBttn().setDisable(false);
        this.scene.getStartBttn().setDisable(true);
        this.scene.getNumLaps().setEditable(false);        
        this.scene.getNumLaps().setBlendMode(BlendMode.DARKEN);
        this.scene.getObtainPQ().setDisable(true);
        
    }
    
    
    public void EnableStart() {
        this.scene.getContinueBttn().setDisable(true);
        this.scene.getStartBttn().setDisable(false);
        this.scene.getNumLaps().setEditable(true);        
        this.scene.getNumLaps().setBlendMode(BlendMode.SRC_OVER);
        this.scene.getObtainPQ().setDisable(false);
    }
        
        
        
    public void functionValues(String x, String x2, String s) {
        String line = "xi=" + x.toUpperCase() + "      x2i=" + x2.toUpperCase() + "      s=" + s.toUpperCase() + "\n";
        
        this.scene.getResults().appendText(line);
      
    }

    public void find(String vuelta) {
        String line = "\nMódulo factorizado en la vuelta -> " + vuelta + "\n\n\n";
        
        this.scene.getResults().appendText(line);
        
    }

    public void time(String time) {
        this.scene.getTime().setText(time);
    }

  

    
}
