/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Imprimir;

import Sign.SignController;
import javafx.scene.control.TextArea;

/**
 *
 * @author rdiazarr
 */
public class SignPrint {
    
    private final SignController SignScene;
    
    
    
    /**
     * Constructor de la clase
     * @param SignScene 
     */
    public SignPrint (SignController SignScene){
        this.SignScene = SignScene;
    }    
    
    
    
    
    public void inputData(String[] processedNumbers, boolean isOriginal) {
        int i, numbers = processedNumbers.length;
        String num;
        TextArea data;
        
        if(isOriginal){
           data = this.SignScene.getOriginalData();
        } else {
            data = this.SignScene.getSignedData2();
        }
        
        
        data.clear();
        
        for (i=0; i<numbers; i++){
            num = processedNumbers[i];
            if (num!=null){
                data.appendText(num + "\n");
            }            
        }
    }
    
    
    public void clearValidatedData() {
        this.SignScene.getValidatedData().clear();
    }

    public void addValidatedData(String validatedNum) {
        this.SignScene.getValidatedData().appendText(validatedNum + "\n");
    }

    public void clearSignedData() {
        this.SignScene.getSignedData1().clear();
    }
    
    public void addSignedData(String signedNum) {
        this.SignScene.getSignedData1().appendText(signedNum + "\n");
    }
    
}
