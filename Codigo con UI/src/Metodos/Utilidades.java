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
        
    
    public boolean isNumber(String possibleNum) {        
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

    public String millisToSeconds(long runningTime) {
       String time;
       int numChars;
       
       time = String.valueOf(runningTime);
       numChars = time.length();
       
       if (numChars > 3){
           time = time.substring(0, numChars-3) + "," + time.substring(numChars-3, numChars);
       } else if (numChars == 3){
           time = "0," + time;
       } else if (numChars == 2){
           time = "0,0" + time;
       } else {
           time ="0,00" + time;
       }
                  
        return time;
    }
    
    
}
