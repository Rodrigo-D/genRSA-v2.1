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
    
    private final CyclicPrint print;
    
    
        
    private int radix;
    
    private BigInteger cypherMessage;
    
    private BigInteger nextMessage;
    
    private BigInteger exponent;
    
    private BigInteger module;
    
    private BigInteger totalLapsNum;
            
    private long totalTime;
    

    public CyclicAttack(CyclicPrint cyclicPrint) {
        this.errorDialog = new ErrorDialog();
        this.infoDialog = new InfoDialog();
        this.utilidades = new Utilidades();
        this.print = cyclicPrint;
        this.radix = 10;
        
    }
    
    
    
    
    public boolean init(String message, BigInteger module, BigInteger exponent) {
       BigInteger messageBI;
        
        this.exponent = exponent;
        this.module = module;
        
        
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
        
        if (messageBI.compareTo(module) > -1){
            this.errorDialog.bigMessage(radix);
            return false;
        }
        
        this.cypherMessage = messageBI.modPow(exponent, module);
        
        
        this.print.messages(this.cypherMessage.toString(this.radix).toUpperCase(),
                            message.toUpperCase());
        
        return true;        
    }
    
    
    //ataque ciclico, no para hasta que prospera
    public void complete (){
        BigInteger lap, message, next;            
        long startTime;
        String Time;

        startTime = System.currentTimeMillis();

        this.print.initResult(this.cypherMessage.toString(this.radix).toUpperCase());
        lap = Constantes.ZERO;            
        next = this.cypherMessage.modPow(this.exponent, this.module);
        lap = lap.add(Constantes.ONE);

        message =next;
        this.print.partialResults(lap.toString(), message.toString(this.radix).toUpperCase());

        while(!next.equals(this.cypherMessage)){
                message = next;
                next = message.modPow(this.exponent, this.module);
                lap = lap.add(Constantes.ONE);   
                this.print.partialResults(lap.toString(), next.toString(this.radix).toUpperCase());
        }

        Time = this.utilidades.millisToSeconds(System.currentTimeMillis() - startTime);

        this.print.enableLapsNum();
        this.print.messageRecovered(message.toString(this.radix).toUpperCase());
        this.print.time(Time);
        this.print.find(lap.toString());
            
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
        this.print.numOfCyphers(lapsNum.toString().toUpperCase());
        this.print.initResult(this.cypherMessage.toString(this.radix).toUpperCase());
               
        //lógica del metodo
        this.totalLapsNum = lapsNum;
        
        lap = Constantes.ZERO;
        next = this.cypherMessage.modPow(this.exponent, this.module);
        lap = lap.add(Constantes.ONE);

        message = next;
        this.print.partialResults(lap.toString(), message.toString(this.radix).toUpperCase());

        while(!next.equals(this.cypherMessage) && !lapsNum.equals(lap)){
                message = next;
                next = message.modPow(this.exponent, this.module);
                lap = lap.add(Constantes.ONE);   
                this.print.partialResults(lap.toString(), next.toString(this.radix).toUpperCase());
        }
        
        this.totalTime = System.currentTimeMillis() - startTime;
        Time = this.utilidades.millisToSeconds(this.totalTime);       
        
        this.print.time(Time);
        
        if (next.equals(this.cypherMessage)){
            this.print.find(lap.toString());
            this.print.messageRecovered(message.toString(this.radix).toUpperCase());
            
        } else {
            this.nextMessage = next;
            this.print.dissableStart();
            this.print.notFind(lap.toString());            
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
        next = this.nextMessage.modPow(this.exponent, this.module);
        lap = lap.add(Constantes.ONE);

        message = next;
        this.totalLapsNum = this.totalLapsNum.add(Constantes.ONE);
        this.print.partialResults(this.totalLapsNum.toString(), message.toString(this.radix).toUpperCase());

        while(!next.equals(this.cypherMessage) && !lapsNum.equals(lap)){
                message = next;
                next = message.modPow(this.exponent, this.module);
                lap = lap.add(Constantes.ONE);   
                this.totalLapsNum = this.totalLapsNum.add(Constantes.ONE);
                this.print.partialResults(this.totalLapsNum.toString(), next.toString(this.radix).toUpperCase());
        }
        
        this.totalTime = (System.currentTimeMillis() - startTime)  + this.totalTime;
        Time = this.utilidades.millisToSeconds(this.totalTime);       
        
        this.print.time(Time);
        
        if (next.equals(this.cypherMessage)){
            this.print.find(this.totalLapsNum.toString());
            this.print.messageRecovered(message.toString(this.radix).toUpperCase());
            this.print.enableStart();
            
        } else {
            this.nextMessage = next;
            this.print.notFind(lap.toString());            
        }       
        
        this.print.numOfCyphers(lapsNum.toString().toUpperCase());
    }
    
        
    public void putInfo() {
        this.infoDialog.cyclic();
    }
     
     
     
     
    
    public void setRadix (int radix){
         this.radix = radix;
    }

   
   
}
