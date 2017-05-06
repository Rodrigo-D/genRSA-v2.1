/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Imprimir;

import DeCipher.DeCipherController;
import Metodos.Utilidades;
import javafx.scene.control.TextArea;

/**
 *
 * @author rdiazarr
 */
public class DeCipherPrint {
     
    private final DeCipherController DCscene;
    
    private final Utilidades utilidades;
    
    /**
     * Constructor de la clase
     * @param DCscene 
     */
    public DeCipherPrint (DeCipherController DCscene){
        this.DCscene = DCscene;
        this.utilidades = new Utilidades();
    }

    
    
    
    public void inputData(String[] processedNumbers, boolean isOriginal) {
        int i, numbers = processedNumbers.length;
        String num;
        TextArea data;
        
        if(isOriginal){
           data = this.DCscene.getOriginalData();
        } else {
            data = this.DCscene.getCipheredData2();
        }
        
        
        data.clear();
        
        for (i=0; i<numbers; i++){
            num = processedNumbers[i];
            if (num!=null){
                data.appendText(this.utilidades.putPoints(num) + "\n");
            }            
        }
    }

    public void clearCipheredData() {
        this.DCscene.getCipheredData1().clear();
    }

    public void addCipheredData(String cipheredNum) {
        this.DCscene.getCipheredData1().appendText(this.utilidades.putPoints(cipheredNum) + "\n");
    }

    public void clearDecipheredData() {
        this.DCscene.getDecipheredData().clear();
    }
    
    public void addDecipheredData(String decipheredNum) {
        this.DCscene.getDecipheredData().appendText(this.utilidades.putPoints(decipheredNum) + "\n");
    }
    
   
}
