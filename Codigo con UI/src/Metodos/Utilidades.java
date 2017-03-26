/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Metodos;

import java.math.BigInteger;

/**
 *
 * @author rdiazarr
 */
public class Utilidades {
    
    public String formatNumber (String number){
        
        number = number.replaceAll("[,. ]","");
        
        return number;
    }
    
    
    public boolean checkKeySize (String keySize){
        boolean out = false;
        if (this.isNumber(keySize)){
           if (Integer.parseInt(keySize) > 3){
              out=true;
            }
        } 
        return out;
    }
    
    
    private boolean isNumber(String possibleNum) {        
        boolean out = true;
        try{
            Integer.parseInt(possibleNum);
        } catch (NumberFormatException e){
            out = false;
        }
        
        return out;
    }
    
    public String countBits (BigInteger number){
            int numBits;

            numBits = number.bitLength();

            return String.valueOf(numBits);
    }
    
}
