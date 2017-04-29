/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Imprimir;

import DeCipher.DeCipherController;
import javafx.scene.control.TextArea;

/**
 *
 * @author rdiazarr
 */
public class DeCipherPrint {
     
    private final DeCipherController DCscene;
    
    /**
     * Constructor de la clase
     * @param DCscene 
     */
    public DeCipherPrint (DeCipherController DCscene){
        this.DCscene = DCscene;
    }

    public void OriginalData(String[] processedNumbers, boolean isOriginal) {
        int i, numbers = processedNumbers.length;
        String num;
        TextArea data;
        
        if(isOriginal){
           data = this.DCscene.getOriginalData();
        } else {
            data = this.DCscene.getCipherData2();
        }
        
        
        data.clear();
        
        for (i=0; i<numbers; i++){
            num = processedNumbers[i];
            if (num!=null){
                data.appendText(num + "\n");
            }            
        }
    }

    public void clearCipheredData() {
        this.DCscene.getCipherData1().clear();
    }

    public void cipheredData(String cipheredNum) {
        this.DCscene.getCipherData1().appendText(cipheredNum + "\n");
    }

    public void clearDecipheredData() {
        this.DCscene.getDecipherData().clear();
    }
    
    public void decipheredData(String decipheredNum) {
        this.DCscene.getDecipherData().appendText(decipheredNum + "\n");
    }
    
   
}
