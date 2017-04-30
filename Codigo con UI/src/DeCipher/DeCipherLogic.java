/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DeCipher;

import Imprimir.DeCipherPrint;
import Imprimir.ErrorDialog;
import Imprimir.InfoDialog;
import Metodos.Utilidades;
import Model.Constantes;
import java.math.BigInteger;
import javafx.scene.control.TextArea;

/**
 *
 * @author rdiazarr
 */
public class DeCipherLogic {
    
    private final ErrorDialog errorDialog;
    
    private final InfoDialog infoDialog;
       
    private final Utilidades utilidades;
    
    private final DeCipherPrint DCprint;
    
    
    private int radix;
    
    private BigInteger privKey;
    
    private BigInteger pubKey;
    
    private BigInteger modulus;
    
    private BigInteger[] DataBI;
    
    
    
    
    public DeCipherLogic(DeCipherPrint DCprint){
        this.errorDialog = new ErrorDialog();
        this.infoDialog = new InfoDialog();
        this.utilidades = new Utilidades();
        this.DCprint = DCprint;
    }
    
    
    
    
    public void initCipher (BigInteger modulus, BigInteger pubKey){         
        this.modulus = modulus;
        this.pubKey = pubKey;
    }
    
    public boolean initDecipher (BigInteger modulus, String privKey){
        this.modulus = modulus;
        
        try{
            this.privKey = new BigInteger(privKey, this.radix);
        } catch (NumberFormatException n){            
            this.errorDialog.selectCombo();
            return false;
        }
        
        return true;
    }
    
    public boolean processData(TextArea Data,  boolean isText, boolean isOriginal) {        
        String data;
        String lines[];
        boolean result;       

        //PARTE DEL TRATADO DEL TEXTO A CIFRAR
        data = Data.getText();

        if (data.length() > 7500){
            this.errorDialog.toMuchData();
            return false;
        }
      
        lines = data.split("\n");        
        if (isText) {
            result=false;           
        } else  {
            result = this.processNumbers(lines, isOriginal);
        } 
       
        return result;        
    }
    
    public void encrypt() {
        BigInteger originalNum;
        String cipheredNum;
        int iterator, size = this.DataBI.length;
        
        this.DCprint.clearCipheredData();
        
        for(iterator=0; iterator<size; iterator++){
            originalNum = this.DataBI[iterator];
            
            if (originalNum!=null){
                cipheredNum = originalNum.modPow(this.pubKey, this.modulus).toString(this.radix).toUpperCase();                
                this.DCprint.addCipheredData(cipheredNum);
            }            
        }        
    }

    public void decrypt() {
        BigInteger cipheredNum;
        String decipheredNum;
        int iterator, size = this.DataBI.length;
        
        this.DCprint.clearDecipheredData();
        
        for(iterator=0; iterator<size; iterator++){
            cipheredNum = this.DataBI[iterator];
            
            if (cipheredNum != null){
                decipheredNum = cipheredNum.modPow(this.privKey, this.modulus).toString(this.radix).toUpperCase();                
                this.DCprint.addDecipheredData(decipheredNum);
            }            
        }
    }
    
    public void putDecipherInfo() {
        this.infoDialog.putDecipherInfo();        
    }

    public void putCipherInfo() {
        this.infoDialog.putCipherInfo();        
    }
    
        
        
        
        
    private boolean processNumbers(String lines[], boolean isOriginal){
        //arrays donde se guardaran los numeros preprocesados y procesados respectivamente
        String numbers[], processedNumbers[];
        //iteradores que llevaran la cuenta de las lineas y los numeros procesados
        int lineIterator=0, processedIterator=0;
        BigInteger number;
        int linesNum;
        
        int digitsOfNumber, digitsOfModulus;
        boolean continuar = true;
        String cutNumStr;
        BigInteger cutNum;
        int beginIndex, endIndex;
        boolean modified = false;
        
        linesNum = lines.length;
        numbers = new String [linesNum];
        processedNumbers = new String [linesNum+10];
        this.DataBI = new BigInteger[linesNum+10];
        while (lineIterator < linesNum && processedIterator < linesNum+10){
            //quita puntos, comas y espacios a la linea
            numbers[lineIterator] = this.utilidades.formatNumber(lines[lineIterator]);

            //compruebo q no se dejen lineas vacias
            if(numbers[lineIterator].equals("")){
                this.errorDialog.blankLines();
                return false;
            }

            //compruebo q la linea sea un numero decima o hexadecimal (segúna la base/radix)
            try{
                number = new BigInteger(numbers[lineIterator], this.radix);
            } catch (NumberFormatException n){            
                this.errorDialog.numberData(this.radix);
                return false;
            }
            //compruebo q sea un numero mayor que cero
            if (number.compareTo(Constantes.ZERO)==-1){
                this.errorDialog.formatData(this.radix);
                return false;
            }
            
            
            
            
            
            //compruebo que sea un numero menor que el modulo
            if (number.compareTo(this.modulus) < 1){
                processedNumbers[processedIterator] = numbers[lineIterator];
                this.DataBI[processedIterator] = number;

                processedIterator++;
            } 
            //creo varios numeros si es un numero mayor que el modulo
            //ATENCIÓN SI SE HAN INTRODUCIDO DE FORMA INCORRECTA LOS NÚMEROS NO SE GARANTIZA
            //QUE SE CIFREN/DESCIFREN TODOS, PARA UN CORRECTO CIFRADO/DESCIFRADO INTRODUCIR
            //UN NUMERO MENOR AL MODULO EN CADA LINEA
            else {
                modified=true;
                //obtengo el número de dígitos de n(módulo)                
                digitsOfModulus = this.modulus.toString(this.radix).length();
                //obtengo el numero de digitos del numero que hay en la linea
                digitsOfNumber = numbers[lineIterator].length();
                beginIndex = 0;
                endIndex = digitsOfModulus;
                while (continuar){
                    //corto el numero de la linea en un numero de digitos igual al modulo
                    cutNumStr = numbers[lineIterator].substring(beginIndex, endIndex);
                    cutNum =  new BigInteger(cutNumStr, this.radix);
                    //si el numero cortado es mayor que el modulo vuelvo a cortar quitandole un digito
                    if (cutNum.compareTo(this.modulus)>0){
                        endIndex = endIndex -1;
                        cutNumStr = numbers[lineIterator].substring(beginIndex, endIndex);
                        cutNum =  new BigInteger(cutNumStr, this.radix);
                    }

                    //guardo los datos obtenidos
                    processedNumbers[processedIterator] = cutNumStr;
                    this.DataBI[processedIterator] = cutNum;
                    processedIterator++;

                    //Compruebo si continuo
                    continuar=false;
                    if (endIndex < digitsOfNumber && processedIterator < linesNum+10){
                        continuar=true;
                        //asigno los indices del cortado del string para la siguiente ronda
                        beginIndex = endIndex;
                        endIndex = endIndex + digitsOfModulus;
                        if (endIndex > digitsOfNumber){
                            endIndex = digitsOfNumber;
                        }
                    } 
                }//while de detro                    
            }//else de numero mayor q modulo
            lineIterator++;
        }//while de fuera

       
       this.DCprint.inputData(processedNumbers, isOriginal);
               
        
        if(modified){
            this.infoDialog.warningDeCipher();
        }
        
        return true;
    }
    
    
    
   public void setRadix (int radix){
         this.radix = radix;
    }

  
}