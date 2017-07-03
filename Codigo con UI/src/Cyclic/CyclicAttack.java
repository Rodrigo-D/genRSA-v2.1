/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cyclic;

import Imprimir.ErrorDialog;
import Imprimir.InfoDialog;
import Imprimir.CyclicPrint;
import Methods.Utilities;
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
    
    private final Utilities utilidades;
    
    private final CyclicPrint Cprint;
    
    
        
    private int radix;
    
    private BigInteger cypherMessage;
    
    private BigInteger nextMessage;
    
    private BigInteger exponent;
    
    private BigInteger modulus;
    
    private BigInteger totalLapsNum;
            
    private long totalTime;
    
    
    private String  result;
    
    private String xplResult;
    
    public static boolean isCancelled;

    /**
     * Constructor de la clase
     * @param cyclicPrint 
     */
    public CyclicAttack(CyclicPrint cyclicPrint) {
        this.errorDialog = new ErrorDialog();
        this.infoDialog = new InfoDialog();
        this.utilidades = new Utilities();
        this.Cprint = cyclicPrint;
        this.radix = 10;
        CyclicAttack.isCancelled = false;
        
    }
    
    
    
    /**
     * Método que inicializa variables, comprueba errores y 
     * permite continuar o muestra un mensaje de error.
     * @param cypheredMessage
     * @param modulus
     * @param exponent
     * @return 
     */
    public boolean init(String cypheredMessage, String modulus, String exponent) {
        final String processedCypheredMessage;
       
       //CHECK MODULUS------------  
        modulus = this.utilidades.formatNumber(modulus);
        
        try{
            this.modulus = new BigInteger(modulus, this.radix);
        } catch (NumberFormatException n){            
            Platform.runLater(() -> errorDialog.Modulus(radix));
            return false;
        }
       
       //CHECK EXPONENT------------  
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
        
        //CHECK CYPHEREDMESSAGE------------ 
        processedCypheredMessage = this.utilidades.formatNumber(cypheredMessage);
        
        try{
            this.cypherMessage = new BigInteger(processedCypheredMessage, this.radix);
        } catch (NumberFormatException n){  
            Platform.runLater(() -> this.errorDialog.cyclicMessage(this.radix));
            return false;
        }
        
        if (this.cypherMessage.compareTo(Constantes.ONE) == -1){
            Platform.runLater(() -> this.errorDialog.cyclicMessage(radix));
            return false;
        }
        
        if (this.cypherMessage.compareTo(this.modulus) > -1){
            Platform.runLater(() -> errorDialog.bigMessage(radix));
            return false;
        }
        
        //CHECK CYPHEREDMESSAGE != NNC
        if (this.cypherMessage.modPow(this.exponent, this.modulus).equals(this.cypherMessage)){
            Platform.runLater(() -> this.Cprint.NNCmessage(radix));
            return false;
        }
        
        //PARTE GRAFICA-------------
        Platform.runLater(() ->{ 
            this.Cprint.messages(this.cypherMessage.toString(this.radix).toUpperCase(),
                            this.modulus.toString(this.radix).toUpperCase(),
                            this.exponent.toString(this.radix).toUpperCase(),
                            this.radix);
            this.Cprint.enableStop();
        });
        
        return true;        
    }
    
    
    /**
     * Método para decidir que tipo de visualización de los resultados se
     * lleva a cabo para el ataque completo. 
     * No para hasta que prospera o se pulsa el boton de parar.
     */
    public void complete (){
        
        Platform.runLater(() ->{ 
            this.Cprint.clearResults();
            this.Cprint.inProgress();
        });   
         
        if (this.modulus.bitLength() < 24){
            LMcomplete();
        } else if (this.modulus.bitLength() < 32){
            BMcomplete(Constantes.BLN_REFRESH);
        } else if (this.modulus.bitLength() < 40){
            BMcomplete(Constantes.BM_REFRESH);
        } else {
            BMcomplete(Constantes.MAX_REFRESH);
        }        
    }
    
   
    /**
     * Método para decidir que tipo de visualización de los resultados se
     * lleva a cabo para el ataque indicando el num de vueltas. 
     * Realiza el ataque para un número de vueltas determinado.
     * @param numOfCyphers 
     */
    public void start(String numOfCyphers) {
        final BigInteger lapsNum;
        
        Platform.runLater(() -> this.Cprint.clearResults());     
            
        //Num.Vueltas --> comprobación de errores
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
    
 
    /**
     * Método para decidir que tipo de visualización de los resultados se
     * lleva a cabo para CONTINUAR el ataque. 
     * Continua el ataque para un número de vueltas determinado.
     * @param numOfCyphers 
     */
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
            this.Cprint.enableStop();
        }); 
        
        if ((lapsNum.add(this.totalLapsNum)).compareTo(Constantes.MAX_LAPSNUM)>0){
            BLNcontinue(lapsNum.add(Constantes.MINUS_ONE));            
        } else{
            LLNcontinue(lapsNum.add(Constantes.MINUS_ONE));   
        }
        
        
    }
     

    
    /**
     * Método para imprimir todos los valores, no para hasta que termina o se pulsa el boton de parar.
     * LM= little modulus
     */
    public void LMcomplete (){    
    
        BigInteger lap, message, next;            
        long startTime;
        final String Time, messageStr, lapStr;
        boolean printFinals = true;

        startTime = System.currentTimeMillis();
        //Preparación de valores iniciales del ataque
        lap = Constantes.ZERO;            
        next = this.cypherMessage.modPow(this.exponent, this.modulus);
        message = this.cypherMessage;
        
        this.xplResult="Mensaje a descifrar = " + this.utilidades.putPoints(this.cypherMessage.toString(this.radix).toUpperCase(), this.radix) + "\n" +
                          "c0 = " + this.utilidades.putPoints(next.toString(this.radix).toUpperCase(), this.radix) + "\n";
       
        //Comienza el bucle del ataque
        while(!next.equals(this.cypherMessage) && !(isCancelled)){
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
        
        //Por si ha encontrado el mensaje en claro o se ha parado y le faltan valores por escribir
        if (printFinals){            
            Platform.runLater(() -> this.Cprint.partialResults(this.xplResult));
        }
        
        Time = this.utilidades.millisToSeconds(System.currentTimeMillis() - startTime);
        
        
        if (next.equals(this.cypherMessage)){
            messageStr = message.toString(this.radix).toUpperCase();
            lapStr = lap.toString();
            Platform.runLater(() -> {
                this.Cprint.messageRecovered(messageStr, this.radix);
                this.Cprint.find(lapStr);
             });
        } else {
            Platform.runLater(() -> {
                this.Cprint.attackStopped();
            });
        }
        
        
        Platform.runLater(() -> {
            this.Cprint.enableStart();
            this.Cprint.endProgress(); 
            this.Cprint.enableLapsNum();            
            this.Cprint.time(Time);            
        });
                
    }
    
    /**
     * Método para imprimir solo 1 de cada "REFRESH" valores, no para hasta que termina o se pulsa el boton de parar.
     * BM= big modulus
     * @param REFRESH
     */
    public void BMcomplete (BigInteger REFRESH){    
    
        BigInteger lap, message, next;            
        long startTime;
        final String Time, messageStr, lapStr;

        startTime = System.currentTimeMillis();
        //Preparación de valores iniciales del ataque
        lap = Constantes.ZERO;            
        next = this.cypherMessage.modPow(this.exponent, this.modulus);
        message = this.cypherMessage;
        
        this.result="Mensaje a descifrar = " + this.utilidades.putPoints(this.cypherMessage.toString(this.radix).toUpperCase(), this.radix) + "\n"
                +  "c0 = " + this.utilidades.putPoints(next.toString(this.radix).toUpperCase(), this.radix) + "\n";
        Platform.runLater(() -> this.Cprint.partialResults(this.result));
       
        //Comienza el bucle del ataque
        while(!next.equals(this.cypherMessage) && !(isCancelled)){
                message = next;
                next = message.modPow(this.exponent, this.modulus);
                lap = lap.add(Constantes.ONE);                 
                
                //para imprimir por pantalla cada REFRESH vueltas
                if ((lap.mod(REFRESH)).equals(Constantes.ZERO)){                    
                    this.result =  "c" + this.utilidades.putPoints(lap.toString(), 10) +
                            " = " + this.utilidades.putPoints(next.toString(this.radix).toUpperCase(), this.radix) + "\n";
                    Platform.runLater(() -> this.Cprint.partialResults(this.result));
                }          
        }
        
        //Por si ha encontrado el mensaje en claro o se ha parado y le faltan valores por escribir
        if(!lap.equals(Constantes.ZERO)){
            this.xplResult = "c" + this.utilidades.putPoints(lap.add(Constantes.MINUS_ONE).toString(), 10) +
                    " = " + this.utilidades.putPoints(message.toString(this.radix).toUpperCase(), this.radix) + "\n" +
                    "c" + this.utilidades.putPoints(lap.toString(), 10) + " = " +
                    this.utilidades.putPoints(next.toString(this.radix).toUpperCase(), this.radix) + "\n";    
        
            Platform.runLater(() -> this.Cprint.partialResults(this.xplResult));
        }        

        
        Time = this.utilidades.millisToSeconds(System.currentTimeMillis() - startTime);
        
        if (next.equals(this.cypherMessage)){
            messageStr = message.toString(this.radix).toUpperCase();
            lapStr = lap.toString();
            Platform.runLater(() -> {
               this.Cprint.messageRecovered(messageStr, this.radix);
               this.Cprint.find(lapStr);
            });
        } else {
            Platform.runLater(() -> {
                this.Cprint.attackStopped();
            });
        }
        
        Platform.runLater(() -> {
            this.Cprint.enableStart();
            this.Cprint.endProgress();
            this.Cprint.enableLapsNum();
            this.Cprint.time(Time);
        });
                 
    }
    
       
    
    /**
     * Metodo para comenzar a imprimir todos los valores del ataque.
     * LLN=little laps num
     * @param lapsNum 
     */
    public void LLNstart(final BigInteger lapsNum) {
        BigInteger lap, message, next;                    
        long startTime;
        final String Time, messageStr, lapStr;
        boolean printFinals = true;

        startTime = System.currentTimeMillis();             
                       
        //Preparación de valores iniciales del ataque
        this.totalLapsNum = lapsNum;        
        lap = Constantes.ZERO;
        next = this.cypherMessage.modPow(this.exponent, this.modulus);
        message = this.cypherMessage;
        
        this.xplResult="Mensaje a descifrar = " + this.utilidades.putPoints(this.cypherMessage.toString(this.radix).toUpperCase(), this.radix) + "\n"
                    +  "c0 = " + this.utilidades.putPoints(next.toString(this.radix).toUpperCase(), this.radix) + "\n";

        while(!next.equals(this.cypherMessage) && !lapsNum.equals(lap) && !(isCancelled)){
            
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
        //Por si ha encontrado el mensaje en claro o se ha parado y le faltan valores por escribir
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
                this.Cprint.enableStart();
            });
            
        } else if(CyclicAttack.isCancelled) {
            Platform.runLater(() -> {
                this.Cprint.time(Time);
                this.Cprint.attackStopped();
                this.Cprint.enableStart();
            });
            
        } else {            
            
            this.nextMessage = next;
            Platform.runLater(() -> {
                this.Cprint.time(Time);
                this.Cprint.dissableStart();
                this.Cprint.notFind(lapStr);
                this.Cprint.enableContinue();
            });
        }        
    }
    
    
    /**
     * Metodo para comenzar a imprimir 1 de cada 10.000 de los valores del ataque
     * BLN=big laps num
     * @param lapsNum 
     */
    public void BLNstart(final BigInteger lapsNum) {
        BigInteger lap, message, next;                    
        long startTime;
        final String Time, messageStr, lapStr;

        startTime = System.currentTimeMillis();
                               
        //Preparación de valores iniciales del ataque
        this.totalLapsNum = lapsNum;                   
        lap = Constantes.ZERO;
        next = this.cypherMessage.modPow(this.exponent, this.modulus);
        message = this.cypherMessage;
        
        this.xplResult="Mensaje a descifrar = " + this.utilidades.putPoints(this.cypherMessage.toString(this.radix).toUpperCase(), this.radix) + "\n"
                    +  "c0 = " + this.utilidades.putPoints(next.toString(this.radix).toUpperCase(), this.radix) + "\n";
        
        Platform.runLater(() -> this.Cprint.partialResults(this.xplResult));

        while(!next.equals(this.cypherMessage) && !lapsNum.equals(lap) && !(isCancelled)){
                message = next;
                next = message.modPow(this.exponent, this.modulus);
                lap = lap.add(Constantes.ONE); 
                                
                if ((lap.mod(Constantes.BLN_REFRESH)).equals(Constantes.ZERO)){                    
                    this.xplResult = "c" + this.utilidades.putPoints(lap.toString(), 10) +
                            " = " + this.utilidades.putPoints(next.toString(this.radix).toUpperCase(), this.radix) + "\n";
                    Platform.runLater(() -> this.Cprint.partialResults(this.xplResult));                     
                }        
        }
        
         //Por si ha encontrado el mensaje en claro o se ha parado y le faltan valores por escribir
        if (!lap.equals(Constantes.ZERO)){
            this.result = "c" + this.utilidades.putPoints(lap.add(Constantes.MINUS_ONE).toString(), 10) +
                    " = " + this.utilidades.putPoints(message.toString(this.radix).toUpperCase(), this.radix) + "\n" +
                "c" + this.utilidades.putPoints(lap.toString(), 10) + " = " + next.toString(this.radix).toUpperCase() + "\n";      

            Platform.runLater(() -> this.Cprint.partialResults(this.result));
                
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
                this.Cprint.enableStart();
            });
            
        } else if(CyclicAttack.isCancelled) {
            Platform.runLater(() -> {
                this.Cprint.time(Time);
                this.Cprint.attackStopped();
                this.Cprint.enableStart();
            });    
            
        } else {
            this.nextMessage = next;
            Platform.runLater(() -> {
                this.Cprint.time(Time);
                this.Cprint.dissableStart();
                this.Cprint.notFind(lapStr);                 
                this.Cprint.enableContinue();
            });
        }        
        
    }
    
    /**
     * Metodo para continuar imprimiendo todos los valores del
     * ataque a no ser que se pare el ataque
     * @param lapsNum 
     */
    public void LLNcontinue(BigInteger lapsNum) {
        BigInteger lap, message, next;            
        long startTime;
        final String Time, messageStr, lapStr;
        boolean printFinals = true;

        startTime = System.currentTimeMillis();        
        
               
        //Preparación de valores iniciales del ataque         
        lap = Constantes.ZERO;
        next = this.nextMessage.modPow(this.exponent, this.modulus);
        message = this.nextMessage;
        this.totalLapsNum = this.totalLapsNum.add(Constantes.ONE);
        
        this.xplResult="c" + this.utilidades.putPoints(this.totalLapsNum.toString(), 10) +
                " = " + this.utilidades.putPoints(next.toString(this.radix).toUpperCase(), this.radix) + "\n";

        while(!next.equals(this.cypherMessage) && !lapsNum.equals(lap) && !(isCancelled)){
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
        
        //Por si ha encontrado el mensaje en claro o se ha parado y le faltan valores por escribir
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
                this.Cprint.time(Time);
                this.Cprint.find(lapStr);
                this.Cprint.messageRecovered(messageStr, this.radix);
                this.Cprint.enableStart();                
            });
            
        } else if(CyclicAttack.isCancelled) {
            Platform.runLater(() -> {
                this.Cprint.time(Time);
                this.Cprint.attackStopped();
                this.Cprint.enableStart();
            });
        }else {
            this.nextMessage = next;
            Platform.runLater(() -> {                
                this.Cprint.time(Time); 
                this.Cprint.notFind(lapStr);  
                this.Cprint.dissableStart();
            });
        }               
    }
    
    
    
    /**
     * Método para continuar imprimiendo, pero esta vez 1 de 10.000
     * valores del ataque a no ser que se pare el ataque
     * @param lapsNum 
     */
    public void BLNcontinue(BigInteger lapsNum) {
        BigInteger lap, message, next;            
        long startTime;
        final String Time, messageStr, lapStr;
        boolean write = false;

        startTime = System.currentTimeMillis();        
        
               
        //Preparación de valores iniciales del ataque       
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
       
        while(!next.equals(this.cypherMessage) && !lapsNum.equals(lap) && !(isCancelled)){
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
            //ha encontrado el mensaje en claro y lo escribe          
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
            //Si le quedan cifrados por escribir los escribe
            if (!write){
                this.xplResult = "c" + this.utilidades.putPoints(lapStr, 10) + " = " +
                        this.utilidades.putPoints(next.toString(this.radix).toUpperCase(), this.radix) + "\n";    
                Platform.runLater(() -> this.Cprint.partialResults(this.xplResult));
            }
            
            if(CyclicAttack.isCancelled) {
                Platform.runLater(() -> {
                    this.Cprint.time(Time);
                    this.Cprint.attackStopped();
                    this.Cprint.enableStart();
                });
            
            }else {            
                this.nextMessage = next;
                Platform.runLater(() -> {
                    this.Cprint.notFind(lapStr);  
                    this.Cprint.dissableStart();
                    this.Cprint.time(Time); 
                });
            }
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
    
    
    public void setIsCancelled (boolean value){
        CyclicAttack.isCancelled = value;
    }  
}