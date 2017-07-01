/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sign;

import Imprimir.ErrorDialog;
import Imprimir.InfoDialog;
import Imprimir.SignPrint;
import Methods.Utilities;
import Model.Constantes;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import javafx.scene.control.TextArea;

/**
 *
 * @author rdiazarr
 */
public class SignLogic {
    
    private final ErrorDialog errorDialog;
    
    private final InfoDialog infoDialog;
       
    private final Utilities utilidades;
    
    private final SignPrint Sprint;
    
    
    private int radix;
    
    private BigInteger privKey;
    
    private BigInteger pubKey;
    
    private BigInteger modulus;
    
    private BigInteger[] DataBI;
    
    /**
     * Constructor de la clase
     * @param Sprint 
     */
    public SignLogic (SignPrint Sprint){
        this.errorDialog = new ErrorDialog();
        this.infoDialog = new InfoDialog();
        this.utilidades = new Utilities();
        this.Sprint = Sprint;
    }
    
    
    /**
     * 
     * @param modulus
     * @param pubKey 
     */
    public void initValidateSign (BigInteger modulus, BigInteger pubKey){         
        this.modulus = modulus;
        this.pubKey = pubKey;
    }
    
    public boolean initSign (BigInteger modulus, String privKey){
        this.modulus = modulus;
        
        //quito la información acerca de los bits"
        int position = privKey.indexOf('-');
        if (position != -1){
            privKey = privKey.substring(0, position);
        }
        
        privKey = this.utilidades.formatNumber(privKey);
        
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
        if (isText && isOriginal) {
            result = this.processText(lines);           
        } else  {
            result = this.processNumbers(lines, isOriginal, isText);
        } 
       
        return result;        
    }
     
    public void validateSign(boolean isText) {
        BigInteger signedNum;
        String validatedNum, validatedText;
        byte[] validateByte;        
        int iterator, size = this.DataBI.length;
        
        this.Sprint.clearValidatedData();
        
        for(iterator=0; iterator<size; iterator++){
            signedNum = this.DataBI[iterator];
            
            if (signedNum != null){
                if (isText){
                    validateByte = signedNum.modPow(this.pubKey, this.modulus).toByteArray();
                    try {
                        validatedText = new String (validateByte, "US-ASCII");
                    } catch (UnsupportedEncodingException ex) {
                        this.errorDialog.unsupportedASCIIDecipherValidate();
                        return;
                    }
                    this.Sprint.addValidatedData(validatedText);
                } else {
                    validatedNum = signedNum.modPow(this.pubKey, this.modulus).toString(this.radix).toUpperCase();                
                    this.Sprint.addValidatedData(this.utilidades.putPoints(validatedNum, this.radix));
                }                
            }            
        }        
    }

    public void sign() {
        BigInteger originalNum;
        String signedNum;
        int iterator, size = this.DataBI.length;
        
        this.Sprint.clearSignedData();
        
        for(iterator=0; iterator<size; iterator++){
            originalNum = this.DataBI[iterator];
            
            if (originalNum != null){
                signedNum = originalNum.modPow(this.privKey, this.modulus).toString(this.radix).toUpperCase();                
                this.Sprint.addSignedData(signedNum, this.radix);
            }            
        }
    } 
    
    public void putSignInfo() {
        this.infoDialog.putSignInfo();        
    }

    public void putValidateSignInfo() {
        this.infoDialog.putValidateSignInfo();        
    }
    
    
    
    /**
     * 
     * @param lines
     * @param isOriginal
     * @param isText
     * @return 
     */
    private boolean processNumbers(String lines[], boolean isOriginal, boolean isText){
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
        
        //compruebo que se pueda validar texto
        if(this.modulus.bitLength()<12 && !isOriginal && isText){
            this.errorDialog.littleModulus("validar");
            return false;            
        }
        
        linesNum = lines.length;
        numbers = new String [linesNum];
        processedNumbers = new String [linesNum+10];
        this.DataBI = new BigInteger[linesNum+10];
        while (lineIterator < linesNum && processedIterator < linesNum+10){
            //quita puntos, comas y espacios a la linea
            numbers[lineIterator] = this.utilidades.formatNumber(lines[lineIterator]);

            //compruebo q no se dejen lineas vacias
            if(numbers[lineIterator].equals("")){
                lineIterator++;
                continue;
            }

            //compruebo q la linea sea un numero decimal o hexadecimal (según la base/radix)
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
            } else {
            //creo varios numeros si es un numero mayor que el modulo
            //ATENCIÓN SI SE HAN INTRODUCIDO DE FORMA INCORRECTA LOS NÚMEROS NO SE GARANTIZA
            //QUE SE FIRMEN/valides TODOS, PARA UN CORRECTO FIRMADO/VALIDADO INTRODUCIR UN
            //NUMERO MENOR AL MODULO EN CADA LINEA
            
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

       
       this.Sprint.inputNumbersData(processedNumbers, isOriginal, this.radix);
               
        
        if(modified){
            this.infoDialog.warningSign();
        }
        
        return true;
    }    
    
    
    
     private boolean processText(String lines[]){
        //arrays donde se guardaran los numeros preprocesados y procesados respectivamente
        String processedText[];
        //iteradores que llevaran la cuenta de las lineas y los numeros procesados
        int lineIterator=0, processedIterator=0;
        BigInteger number;
        int linesNum;
        
        int bytesOfNumber, bytesOfModulus;
        boolean continuar;
        BigInteger cutNum;
        int beginIndex, endIndex;
        boolean modified = false;
        
        //metidos
        byte[] asciiText;
        byte[] cutAsciiText;
        
        //compruebo que se pueda firmar texto
        if(this.modulus.bitLength()<12){
            this.errorDialog.littleModulus("firmar");
            return false;            
        }
        
        
        linesNum = lines.length;
        this.DataBI = new BigInteger[linesNum+10];
        processedText = new String [linesNum+10];
        while (lineIterator < linesNum && processedIterator < linesNum+10){            

            //compruebo q no se dejen lineas vacias
            if(lines[lineIterator].equals("") || lines[lineIterator].equals(" ")){
                lineIterator++;
                continue;
            }

            //Transformo el texto a numeros ASCII decimales;
            asciiText = lines[lineIterator].getBytes(StandardCharsets.US_ASCII);
            
            
            //Transformo el ASCII a bigInteger
            try{
                number = new BigInteger(asciiText);
            } catch (NumberFormatException n){            
                this.errorDialog.unsupportedASCII();
                return false;
            }
                                    
           
            //obtengo el número de bytes de n(módulo)                
            bytesOfModulus = this.modulus.bitLength()/8;
            if (this.modulus.bitLength()%8 != 0){
                bytesOfModulus++;
            }
            //obtengo el numero de bytes del texto que hay en la linea
            bytesOfNumber = asciiText.length;
            
            //compruebo que el numero de bytes de la linea es menor que el numero de bytes del modulo
            if (bytesOfModulus > bytesOfNumber){
                                 
                try {
                    processedText[processedIterator] = new String (asciiText, "US-ASCII");
                } catch (UnsupportedEncodingException ex) {
                    this.errorDialog.unsupportedASCII();
                    return false;
                }
                
                
                this.DataBI[processedIterator] = number;

                processedIterator++;
                
            } else {                
                //ATENCIÓN SI SE HAN INTRODUCIDO DE FORMA INCORRECTA EL TEXTO NO SE GARANTIZA
                //QUE SE CIFREN/DESCIFREN TODO EL TEXTO, PARA UN CORRECTO CIFRADO/DESCIFRADO INTRODUCIR
                //EN CADA LINEA UN TEXTO CUYO ASCII SEA MENOR AL MODULO 
                
                modified=true;
                continuar= true;                
                
                beginIndex = 0;
                endIndex = bytesOfModulus-1;
                while (continuar){
                    //corto la linea en un numero de bytes igual a los bytes del modulo - 1
                    cutAsciiText = Arrays.copyOfRange(asciiText, beginIndex, endIndex);
                    cutNum =  new BigInteger(cutAsciiText);
                    
                    //guardo los datos obtenidos
                    try {
                        processedText[processedIterator] = new String (cutAsciiText, "US-ASCII");
                    } catch (UnsupportedEncodingException ex) {
                        this.errorDialog.unsupportedASCII();
                        return false;
                    }
                    this.DataBI[processedIterator] = cutNum;
                    processedIterator++;

                    //Compruebo si continuo
                    continuar=false;
                    if (endIndex < bytesOfNumber && processedIterator < linesNum+10){
                        continuar=true;
                        //asigno los indices del cortado del string para la siguiente ronda
                        beginIndex = endIndex;
                        endIndex = endIndex + (bytesOfModulus-1);
                        if (endIndex > bytesOfNumber){
                            endIndex = bytesOfNumber;
                        }
                    } 
                }//while de detro                  
            }//else de numero mayor q modulo
            lineIterator++;
        }//while de fuera

       
        this.Sprint.inputTextData(processedText);
               
        
        if(modified){
            this.infoDialog.warningDeCipher();
        }
        
        return true;
    }
    
    
    public void setRadix (int radix){
         this.radix = radix;
    }
    
}
