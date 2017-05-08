/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cyclic;

import Imprimir.CyclicPrint;
import Imprimir.ErrorDialog;
import Imprimir.InfoDialog;
import Metodos.Utilidades;
import Model.Constantes;
import java.math.BigInteger;
import javafx.application.Platform;

/**
 *
 * @author rdiazarr
 */
public class CyclicAttack {
    
    private final ErrorDialog errorDialog;
    
    private final InfoDialog infoDialog;
    
    private final Utilidades utilidades;
    
    private final CyclicPrint Cprint;
    
    
        
    private int radix;
    
    private BigInteger cypherMessage;
    
    private BigInteger nextMessage;
    
    private BigInteger exponent;
    
    private BigInteger modulus;
    
    private BigInteger totalLapsNum;
            
    private long totalTime;
    
    //prueba
    private String  result;
    
    private String xplResult;

    public CyclicAttack(CyclicPrint cyclicPrint) {
        this.errorDialog = new ErrorDialog();
        this.infoDialog = new InfoDialog();
        this.utilidades = new Utilidades();
        this.Cprint = cyclicPrint;
        this.radix = 10;
        
    }
    
    
    
    
    public boolean init(String message, String modulus, String exponent) {
       BigInteger messageBI;
       final String processedMessage;
       
        modulus = this.utilidades.formatNumber(modulus);
        
        try{
            this.modulus = new BigInteger(modulus, this.radix);
        } catch (NumberFormatException n){            
            Platform.runLater(() -> errorDialog.Modulus(radix));
            return false;
        }
       
       
       exponent = this.utilidades.formatNumber(exponent);
        
        try{
            this.exponent = new BigInteger(exponent, this.radix);
        } catch (NumberFormatException n){     
            Platform.runLater(() -> errorDialog.Exponent(radix));
            return false;
        }
        
        if (this.exponent.compareTo(this.modulus)>-1){
            Platform.runLater(() -> errorDialog.bigExponent());
            return false;
        }       
        
        
        processedMessage = this.utilidades.formatNumber(message);
        
        try{
            messageBI = new BigInteger(processedMessage, this.radix);
        } catch (NumberFormatException n){  
            Platform.runLater(() -> errorDialog.cyclicMessage(radix));
            return false;
        }
        
        if (messageBI.compareTo(Constantes.ONE) == -1){
            Platform.runLater(() -> errorDialog.cyclicMessage(radix));
            return false;
        }
        
        if (messageBI.compareTo(this.modulus) > -1){
            Platform.runLater(() -> errorDialog.bigMessage(radix));
            return false;
        }
        
        this.cypherMessage = messageBI.modPow(this.exponent, this.modulus);
        
        
        Platform.runLater(() -> Cprint.messages(this.cypherMessage.toString(this.radix).toUpperCase(),
                            processedMessage.toUpperCase(),
                            this.modulus.toString(this.radix).toUpperCase(),
                            this.exponent.toString(this.radix).toUpperCase(),
                            this.radix));
        
        return true;        
    }
    
    
    //ataque ciclico, no para hasta que prospera    
    public void complete (){
        
        Platform.runLater(() -> this.Cprint.clearResults());   
        Platform.runLater(() -> this.Cprint.inProgress());   
         
        if (this.modulus.bitLength()>23){
            BMcomplete();
        } else {
            LMcomplete();
        }
        
    }
    
    
    public void start(String numOfCyphers) {
        final BigInteger lapsNum;
        
        Platform.runLater(() -> this.Cprint.clearResults());     
            
        //comprobación de errores
        numOfCyphers = this.utilidades.formatNumber(numOfCyphers);
        
        try{
            lapsNum =  new BigInteger(numOfCyphers);
            
        } catch (NumberFormatException n){      
            Platform.runLater(() -> errorDialog.cyphers());
            return;
        }
            
        if (lapsNum.compareTo(Constantes.ONE) == -1){
            Platform.runLater(() -> errorDialog.littleNumOfCyphers());
            return;
        }

        //primera impresion por pantalla
        Platform.runLater(() -> {
            this.Cprint.numOfCyphers(lapsNum.toString());
            this.Cprint.inProgress();
        }); 
        
        if (lapsNum.compareTo(Constantes.MAX_LAPSNUM)>0){
            BLNstart(lapsNum.add(Constantes.MINUS_ONE));            
        } else{
            LLNstart(lapsNum.add(Constantes.MINUS_ONE));   
        }
    }
    
    
    public void Continue (String numOfCyphers) {
        final BigInteger lapsNum;
        
         //comprobación que de errores
        numOfCyphers = this.utilidades.formatNumber(numOfCyphers);
        
        try{
            lapsNum =  new BigInteger(numOfCyphers);
            
        } catch (NumberFormatException n){      
            Platform.runLater(() -> errorDialog.cyphers());
            
            return;
        }
            
        if (lapsNum.compareTo(Constantes.ONE) == -1){
            Platform.runLater(() -> errorDialog.littleNumOfCyphers());
            return;
        }        
        
        //primera impresion por pantalla
        Platform.runLater(() -> {
            this.Cprint.numOfCyphers(lapsNum.toString());
            this.Cprint.inProgress();
        }); 
        
        if ((lapsNum.add(this.totalLapsNum)).compareTo(Constantes.MAX_LAPSNUM)>0){
            BLNcontinue(lapsNum.add(Constantes.MINUS_ONE));            
        } else{
            LLNcontinue(lapsNum.add(Constantes.MINUS_ONE));   
        }
        
        
    }
     

    
    /**
     * Método para imprimir todos los valores 
     * LM= little modulus
     */
    public void LMcomplete (){    
    
        BigInteger lap, message, next;            
        long startTime;
        final String Time, messageStr, lapStr;
        boolean printFinals = true;

        startTime = System.currentTimeMillis();

        lap = Constantes.ZERO;            
        next = this.cypherMessage.modPow(this.exponent, this.modulus);
        message = this.cypherMessage;
        
        this.xplResult="Mensaje a descifrar = " + this.utilidades.putPoints(this.cypherMessage.toString(this.radix).toUpperCase(), this.radix) + "\n" +
                          "c0 = " + this.utilidades.putPoints(next.toString(this.radix).toUpperCase(), this.radix) + "\n";
       
        while(!next.equals(this.cypherMessage)){
                message = next;
                next = message.modPow(this.exponent, this.modulus);
                lap = lap.add(Constantes.ONE);
                
                //para refrescar la pantalla cada 2000 vueltas
                if ((lap.mod(Constantes.L_REFRESH)).equals(Constantes.ZERO)){
                    
                    this.result = xplResult;
                    Platform.runLater(() -> this.Cprint.partialResults(this.result));
                     this.xplResult = "";
                     printFinals= false;
                } else {
                     this.xplResult = this.xplResult + "c" + this.utilidades.putPoints(lap.toString(), 10) +
                             " = " + this.utilidades.putPoints(next.toString(this.radix).toUpperCase(), this.radix) + "\n";
                     printFinals = true;
                }   
                
        }
        
        if (printFinals){            
            Platform.runLater(() -> this.Cprint.partialResults(this.xplResult));
        }

        
        Time = this.utilidades.millisToSeconds(System.currentTimeMillis() - startTime);
        messageStr = message.toString(this.radix).toUpperCase();
        lapStr = lap.toString();
        
        Platform.runLater(() -> {
            this.Cprint.endProgress(); 
            this.Cprint.enableLapsNum();
            this.Cprint.messageRecovered(messageStr, this.radix);
            this.Cprint.time(Time);
            this.Cprint.find(lapStr);
        });
            
    }
    
     /**
     * Método para imprimir solo 1 de cada 100.000 valores 
     * BM= big modulus
     */
    public void BMcomplete (){    
    
        BigInteger lap, message, next;            
        long startTime;
        final String Time, messageStr, lapStr;

        startTime = System.currentTimeMillis();

        lap = Constantes.ZERO;            
        next = this.cypherMessage.modPow(this.exponent, this.modulus);
        message = this.cypherMessage;
        
        this.result="Mensaje a descifrar = " + this.utilidades.putPoints(this.cypherMessage.toString(this.radix).toUpperCase(), this.radix) + "\n"
                +  "c0 = " + this.utilidades.putPoints(next.toString(this.radix).toUpperCase(), this.radix) + "\n";
        Platform.runLater(() -> this.Cprint.partialResults(this.result));
       
        while(!next.equals(this.cypherMessage)){
                message = next;
                next = message.modPow(this.exponent, this.modulus);
                lap = lap.add(Constantes.ONE);                 
                
                if ((lap.mod(Constantes.BM_REFRESH)).equals(Constantes.ZERO)){                    
                    this.result =  "c" + this.utilidades.putPoints(lap.toString(), 10) +
                            " = " + this.utilidades.putPoints(next.toString(this.radix).toUpperCase(), this.radix) + "\n";
                    Platform.runLater(() -> this.Cprint.partialResults(this.result));
                }          
        }
        
        if(!lap.equals(Constantes.ZERO)){
            this.xplResult = "c" + this.utilidades.putPoints(lap.add(Constantes.MINUS_ONE).toString(), 10) +
                    " = " + this.utilidades.putPoints(message.toString(this.radix).toUpperCase(), this.radix) + "\n" +
                    "c" + this.utilidades.putPoints(lap.toString(), 10) + " = " +
                    this.utilidades.putPoints(next.toString(this.radix).toUpperCase(), this.radix) + "\n";    
        
            Platform.runLater(() -> this.Cprint.partialResults(this.xplResult));
        }        

        
        Time = this.utilidades.millisToSeconds(System.currentTimeMillis() - startTime);
        messageStr = message.toString(this.radix).toUpperCase();
        lapStr = lap.toString();
        
        Platform.runLater(() -> {
            this.Cprint.endProgress();
            this.Cprint.enableLapsNum();
            this.Cprint.messageRecovered(messageStr, this.radix);
            this.Cprint.time(Time);
            this.Cprint.find(lapStr);
        });
            
    }
    
       
    
    /**
     * Metodo para comenzar a imprimir todos los valores del ataque
     * LLN=little laps num
     * @param lapsNum 
     */
    public void LLNstart(final BigInteger lapsNum) {
        BigInteger lap, message, next;                    
        long startTime;
        final String Time, messageStr, lapStr;
        boolean printFinals = true;

        startTime = System.currentTimeMillis();             
                       
        //lógica del metodo
        this.totalLapsNum = lapsNum;        
        lap = Constantes.ZERO;
        next = this.cypherMessage.modPow(this.exponent, this.modulus);
        message = this.cypherMessage;
        
        this.xplResult="Mensaje a descifrar = " + this.utilidades.putPoints(this.cypherMessage.toString(this.radix).toUpperCase(), this.radix) + "\n"
                    +  "c0 = " + this.utilidades.putPoints(next.toString(this.radix).toUpperCase(), this.radix) + "\n";

        while(!next.equals(this.cypherMessage) && !lapsNum.equals(lap)){
                message = next;
                next = message.modPow(this.exponent, this.modulus);
                lap = lap.add(Constantes.ONE); 
                                
                if ((lap.mod(Constantes.L_REFRESH)).equals(Constantes.ZERO)){                    
                    this.result = xplResult;
                    Platform.runLater(() -> this.Cprint.partialResults(this.result));
                     this.xplResult = "";
                     printFinals= false;
                } else {
                     this.xplResult = this.xplResult + "c" + this.utilidades.putPoints(lap.toString(), 10) +
                             " = " + this.utilidades.putPoints(next.toString(this.radix).toUpperCase(), this.radix) + "\n";
                     printFinals = true;
                }           
        }
        
        if (printFinals){            
            Platform.runLater(() -> this.Cprint.partialResults(this.xplResult));
        }
        
        this.totalTime = System.currentTimeMillis() - startTime;
        Time = this.utilidades.millisToSeconds(this.totalTime);
        messageStr = message.toString(this.radix).toUpperCase();
        lapStr = lap.toString();        
        
        Platform.runLater(() -> this.Cprint.endProgress());
        
        if (next.equals(this.cypherMessage)){
            Platform.runLater(() -> {
                this.Cprint.time(Time);
                this.Cprint.find(lapStr);
                this.Cprint.messageRecovered(messageStr, this.radix);
            });
            
        } else {
            this.nextMessage = next;
            Platform.runLater(() -> {
                this.Cprint.time(Time);
                this.Cprint.dissableStart();
                this.Cprint.notFind(lapStr); 
            });
        }        
    }
    
    
    /**
     * Metodo para comenzar a imprimir 1 de cada 100.000 de los valores del ataque
     * BLN=big laps num
     * @param lapsNum 
     */
    public void BLNstart(final BigInteger lapsNum) {
        BigInteger lap, message, next;                    
        long startTime;
        final String Time, messageStr, lapStr;

        startTime = System.currentTimeMillis();
                               
        //lógica del metodo
        this.totalLapsNum = lapsNum;                   
        lap = Constantes.ZERO;
        next = this.cypherMessage.modPow(this.exponent, this.modulus);
        message = this.cypherMessage;
        
        this.xplResult="Mensaje a descifrar = " + this.utilidades.putPoints(this.cypherMessage.toString(this.radix).toUpperCase(), this.radix) + "\n"
                    +  "c0 = " + this.utilidades.putPoints(next.toString(this.radix).toUpperCase(), this.radix) + "\n";
        
        Platform.runLater(() -> this.Cprint.partialResults(this.xplResult));

        while(!next.equals(this.cypherMessage) && !lapsNum.equals(lap)){
                message = next;
                next = message.modPow(this.exponent, this.modulus);
                lap = lap.add(Constantes.ONE); 
                                
                if ((lap.mod(Constantes.BLN_REFRESH)).equals(Constantes.ZERO)){                    
                    this.xplResult = "c" + this.utilidades.putPoints(lap.toString(), 10) +
                            " = " + this.utilidades.putPoints(next.toString(this.radix).toUpperCase(), this.radix) + "\n";
                    Platform.runLater(() -> this.Cprint.partialResults(this.xplResult));
                     
                }        
        }
        
              
        this.totalTime = System.currentTimeMillis() - startTime;
        Time = this.utilidades.millisToSeconds(this.totalTime);
        messageStr = message.toString(this.radix).toUpperCase();
        lapStr = lap.toString();  
        
        Platform.runLater(() -> this.Cprint.endProgress());
        
        if (next.equals(this.cypherMessage)){
            if (!lap.equals(Constantes.ZERO)){
                this.result = "c" + this.utilidades.putPoints(lap.add(Constantes.MINUS_ONE).toString(), 10) +
                        " = " + this.utilidades.putPoints(message.toString(this.radix).toUpperCase(), this.radix) + "\n" +
                    "c" + lap.toString() + " = " + next.toString(this.radix).toUpperCase() + "\n";      
                
                Platform.runLater(() -> this.Cprint.partialResults(this.result));
                
            }
            Platform.runLater(() -> {
                this.Cprint.time(Time);
                this.Cprint.find(lapStr);
                this.Cprint.messageRecovered(messageStr, this.radix);
            });
            
        } else {
            this.nextMessage = next;
            Platform.runLater(() -> {
                this.Cprint.time(Time);
                this.Cprint.dissableStart();
                this.Cprint.notFind(lapStr); 
            });
        }        
    }
    
    /**
     * Metodo para continuar imprimiendo todos los valores del ataque
     * @param lapsNum 
     */
    public void LLNcontinue(BigInteger lapsNum) {
        BigInteger lap, message, next;            
        long startTime;
        final String Time, messageStr, lapStr;
        boolean printFinals = true;

        startTime = System.currentTimeMillis();        
        
               
        //lógica del metodo        
        lap = Constantes.ZERO;
        next = this.nextMessage.modPow(this.exponent, this.modulus);
        message = this.nextMessage;
        this.totalLapsNum = this.totalLapsNum.add(Constantes.ONE);
        
        this.xplResult="c" + this.utilidades.putPoints(this.totalLapsNum.toString(), 10) +
                " = " + this.utilidades.putPoints(next.toString(this.radix).toUpperCase(), this.radix) + "\n";

        while(!next.equals(this.cypherMessage) && !lapsNum.equals(lap)){
                message = next;
                next = message.modPow(this.exponent, this.modulus);
                lap = lap.add(Constantes.ONE);   
                this.totalLapsNum = this.totalLapsNum.add(Constantes.ONE);
                
                if ((this.totalLapsNum.mod(Constantes.L_REFRESH)).equals(Constantes.ZERO)){                    
                    this.result = xplResult;
                    Platform.runLater(() -> this.Cprint.partialResults(this.result));
                     this.xplResult = "";
                     printFinals= false;
                } else {
                     this.xplResult = this.xplResult + "c" + this.utilidades.putPoints(this.totalLapsNum.toString(), 10) +
                             " = " + this.utilidades.putPoints(next.toString(this.radix).toUpperCase(), this.radix) + "\n";
                     printFinals = true;
                }     
        }
        
        if (printFinals){            
            Platform.runLater(() -> this.Cprint.partialResults(this.xplResult));
        }        
        
        this.totalTime = (System.currentTimeMillis() - startTime)  + this.totalTime;
        Time = this.utilidades.millisToSeconds(this.totalTime);       
        messageStr = message.toString(this.radix).toUpperCase();
        lapStr = this.totalLapsNum.toString();          
        
        Platform.runLater(() -> this.Cprint.endProgress());
        
        if (next.equals(this.cypherMessage)){
            Platform.runLater(() -> {
                this.Cprint.find(lapStr);
                this.Cprint.messageRecovered(messageStr, this.radix);
                this.Cprint.enableStart();
                this.Cprint.time(Time);
            });
            
        } else {
            this.nextMessage = next;
            Platform.runLater(() -> {
                this.Cprint.notFind(lapStr);  
                this.Cprint.dissableStart();
                this.Cprint.time(Time); 
            });
        }       
        
    }
    
    
    
    /**
     * Método para continuar imprimiendo, pero esta vez 1 de 100.000 valores del ataque
     * @param lapsNum 
     */
    public void BLNcontinue(BigInteger lapsNum) {
        BigInteger lap, message, next;            
        long startTime;
        final String Time, messageStr, lapStr;
        boolean write = false;

        startTime = System.currentTimeMillis();        
        
               
        //lógica del metodo        
        lap = Constantes.ZERO;
        next = this.nextMessage.modPow(this.exponent, this.modulus);
        message = this.nextMessage;
        this.totalLapsNum = this.totalLapsNum.add(Constantes.ONE);
        
        if ((this.totalLapsNum.mod(Constantes.BLN_REFRESH)).equals(Constantes.ZERO)){
            this.result = "c" + this.utilidades.putPoints(this.totalLapsNum.toString(), 10) +
                    " = " + this.utilidades.putPoints(next.toString(this.radix).toUpperCase(), this.radix) + "\n";
            Platform.runLater(() -> this.Cprint.partialResults(this.result));
            write = true;
        }         
       
        while(!next.equals(this.cypherMessage) && !lapsNum.equals(lap)){
                message = next;
                next = message.modPow(this.exponent, this.modulus);
                lap = lap.add(Constantes.ONE);   
                this.totalLapsNum = this.totalLapsNum.add(Constantes.ONE);
                
                if ((this.totalLapsNum.mod(Constantes.BLN_REFRESH)).equals(Constantes.ZERO)){                    
                    this.result = "c" + this.utilidades.putPoints(this.totalLapsNum.toString(), 10) +
                            " = " + this.utilidades.putPoints(next.toString(this.radix).toUpperCase(), this.radix) + "\n";
                    Platform.runLater(() -> this.Cprint.partialResults(this.result)); 
                    write = true;
                } 
        }
        
        
        this.totalTime = (System.currentTimeMillis() - startTime)  + this.totalTime;
        Time = this.utilidades.millisToSeconds(this.totalTime);       
        messageStr = message.toString(this.radix).toUpperCase();
        lapStr = this.totalLapsNum.toString();        
        
        Platform.runLater(() -> this.Cprint.endProgress());
        
        if (next.equals(this.cypherMessage)){
                        
            this.xplResult = "c" + this.utilidades.putPoints(lap.add(Constantes.MINUS_ONE).toString(), 10) +
                    " = " + this.utilidades.putPoints(messageStr, this.radix) + "\n" +
                    "c" + this.utilidades.putPoints(lapStr, 10) +
                    " = " + this.utilidades.putPoints(next.toString(this.radix).toUpperCase(), this.radix) + "\n";           
            
            Platform.runLater(() -> {
                this.Cprint.partialResults(this.xplResult);
                this.Cprint.find(this.totalLapsNum.toString());
                this.Cprint.messageRecovered(messageStr, this.radix);
                this.Cprint.enableStart();
                this.Cprint.time(Time);
            });
            
        } else {
            if (!write){
                this.xplResult = "c" + this.utilidades.putPoints(lapStr, 10) + " = " +
                        this.utilidades.putPoints(next.toString(this.radix).toUpperCase(), this.radix) + "\n";    
                Platform.runLater(() -> this.Cprint.partialResults(this.xplResult));
            }
            this.nextMessage = next;
            Platform.runLater(() -> {
                this.Cprint.notFind(lapStr);  
                this.Cprint.dissableStart();
                this.Cprint.time(Time); 
            });
        }       
        
    }
        
    public void putInfo() {
        this.infoDialog.cyclic();
    }
  
    
    public void warning() {
        this.infoDialog.warningAttack();
    }  
     
     
     
    
    public void setRadix (int radix){
        this.radix = radix;
    }
}
