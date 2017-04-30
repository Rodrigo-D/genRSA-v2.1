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
    

    public CyclicAttack(CyclicPrint cyclicPrint) {
        this.errorDialog = new ErrorDialog();
        this.infoDialog = new InfoDialog();
        this.utilidades = new Utilidades();
        this.Cprint = cyclicPrint;
        this.radix = 10;
        
    }
    
    
    
    
    public boolean init(String message, String modulus, String exponent) {
       BigInteger messageBI;
       
       
        modulus = this.utilidades.formatNumber(modulus);
        
        try{
            this.modulus = new BigInteger(modulus, this.radix);
        } catch (NumberFormatException n){            
            this.errorDialog.Modulus(this.radix);
            return false;
        }
       
       
       exponent = this.utilidades.formatNumber(exponent);
        
        try{
            this.exponent = new BigInteger(exponent, this.radix);
        } catch (NumberFormatException n){            
            this.errorDialog.Exponent(this.radix);
            return false;
        }
        
        if (this.exponent.compareTo(this.modulus)>-1){
            this.errorDialog.bigExponent();
            return false;
        }       
        
        
        message = this.utilidades.formatNumber(message);
        
        try{
            messageBI = new BigInteger(message, this.radix);
        } catch (NumberFormatException n){            
            this.errorDialog.cyclicMessage(this.radix);
            return false;
        }
        
        if (messageBI.compareTo(Constantes.ONE) == -1){
            this.errorDialog.cyclicMessage(radix);
            return false;
        }
        
        if (messageBI.compareTo(this.modulus) > -1){
            this.errorDialog.bigMessage(radix);
            return false;
        }
        
        this.cypherMessage = messageBI.modPow(this.exponent, this.modulus);
        
        
        this.Cprint.messages(this.cypherMessage.toString(this.radix).toUpperCase(),
                            message.toUpperCase(),
                            this.modulus.toString(this.radix).toUpperCase(),
                            this.exponent.toString(this.radix).toUpperCase());
        
        return true;        
    }
    
    
    //ataque ciclico, no para hasta que prospera
    public void complete (){
        BigInteger lap, message, next;            
        long startTime;
        String Time;

        startTime = System.currentTimeMillis();

        this.Cprint.initResult(this.cypherMessage.toString(this.radix).toUpperCase());
        lap = Constantes.ZERO;            
        next = this.cypherMessage.modPow(this.exponent, this.modulus);
        lap = lap.add(Constantes.ONE);

        message =next;
        this.Cprint.partialResults(lap.toString(), message.toString(this.radix).toUpperCase());

        while(!next.equals(this.cypherMessage)){
                message = next;
                next = message.modPow(this.exponent, this.modulus);
                lap = lap.add(Constantes.ONE);   
                this.Cprint.partialResults(lap.toString(), next.toString(this.radix).toUpperCase());
        }

        Time = this.utilidades.millisToSeconds(System.currentTimeMillis() - startTime);

        this.Cprint.enableLapsNum();
        this.Cprint.messageRecovered(message.toString(this.radix).toUpperCase());
        this.Cprint.time(Time);
        this.Cprint.find(lap.toString());
            
    }
    
    
    public void start(String numOfCyphers) {
        BigInteger lap, message, next, lapsNum;            
        long startTime;
        String Time;

        startTime = System.currentTimeMillis();
        
        //comprobación que de errores
        numOfCyphers = this.utilidades.formatNumber(numOfCyphers);
        
        try{
            lapsNum =  new BigInteger(numOfCyphers);
            
        } catch (NumberFormatException n){            
            this.errorDialog.cyphers();
            return;
        }
            
        if (lapsNum.compareTo(Constantes.ONE) == -1){
            this.errorDialog.littleNumOfCyphers();
            return;
        }

        //primeras impresiones por pantalla
        this.Cprint.numOfCyphers(lapsNum.toString().toUpperCase());
        this.Cprint.initResult(this.cypherMessage.toString(this.radix).toUpperCase());
               
        //lógica del metodo
        this.totalLapsNum = lapsNum;
        
        lap = Constantes.ZERO;
        next = this.cypherMessage.modPow(this.exponent, this.modulus);
        lap = lap.add(Constantes.ONE);

        message = next;
        this.Cprint.partialResults(lap.toString(), message.toString(this.radix).toUpperCase());

        while(!next.equals(this.cypherMessage) && !lapsNum.equals(lap)){
                message = next;
                next = message.modPow(this.exponent, this.modulus);
                lap = lap.add(Constantes.ONE);   
                this.Cprint.partialResults(lap.toString(), next.toString(this.radix).toUpperCase());
        }
        
        this.totalTime = System.currentTimeMillis() - startTime;
        Time = this.utilidades.millisToSeconds(this.totalTime);       
        
        this.Cprint.time(Time);
        
        if (next.equals(this.cypherMessage)){
            this.Cprint.find(lap.toString());
            this.Cprint.messageRecovered(message.toString(this.radix).toUpperCase());
            
        } else {
            this.nextMessage = next;
            this.Cprint.dissableStart();
            this.Cprint.notFind(lap.toString());            
        }        
    }
    
    
    public void Continue(String numOfCyphers) {
        BigInteger lap, message, next, lapsNum;            
        long startTime;
        String Time;

        startTime = System.currentTimeMillis();
        
        //comprobación que de errores
        numOfCyphers = this.utilidades.formatNumber(numOfCyphers);
        
        try{
            lapsNum =  new BigInteger(numOfCyphers);
            
        } catch (NumberFormatException n){            
            this.errorDialog.cyphers();
            return;
        }
            
        if (lapsNum.compareTo(Constantes.ONE) == -1){
            this.errorDialog.littleNumOfCyphers();
            return;
        }        
        
               
        //lógica del metodo        
        lap = Constantes.ZERO;
        next = this.nextMessage.modPow(this.exponent, this.modulus);
        lap = lap.add(Constantes.ONE);

        message = next;
        this.totalLapsNum = this.totalLapsNum.add(Constantes.ONE);
        this.Cprint.partialResults(this.totalLapsNum.toString(), message.toString(this.radix).toUpperCase());

        while(!next.equals(this.cypherMessage) && !lapsNum.equals(lap)){
                message = next;
                next = message.modPow(this.exponent, this.modulus);
                lap = lap.add(Constantes.ONE);   
                this.totalLapsNum = this.totalLapsNum.add(Constantes.ONE);
                this.Cprint.partialResults(this.totalLapsNum.toString(), next.toString(this.radix).toUpperCase());
        }
        
        this.totalTime = (System.currentTimeMillis() - startTime)  + this.totalTime;
        Time = this.utilidades.millisToSeconds(this.totalTime);       
        
        this.Cprint.time(Time);
        
        if (next.equals(this.cypherMessage)){
            this.Cprint.find(this.totalLapsNum.toString());
            this.Cprint.messageRecovered(message.toString(this.radix).toUpperCase());
            this.Cprint.enableStart();
            
        } else {
            this.nextMessage = next;
            this.Cprint.notFind(lap.toString());            
        }       
        
        this.Cprint.numOfCyphers(lapsNum.toString().toUpperCase());
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
