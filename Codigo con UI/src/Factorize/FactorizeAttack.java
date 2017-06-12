/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Factorize;

import Imprimir.ErrorDialog;
import Imprimir.FactorizePrint;
import Imprimir.InfoDialog;
import Methods.Utilities;
import Model.Constantes;
import java.math.BigInteger;
import javafx.application.Platform;

/**
 *
 * @author rdiazarr
 */
public class FactorizeAttack {    
    
    private final ErrorDialog errorDialog;
    
    private final InfoDialog infoDialog;
    
    private final Utilities utilidades;
    
    private final FactorizePrint print;
        
    private int radix;
    
    
    
    private boolean find = false;
    
    private BigInteger x;
    
    private BigInteger x2;
    
    private BigInteger lapsNum;
    
    private BigInteger modulus;
    
    private BigInteger lapsNumTotal;
    
    private long totalTime;
    
    private String result;
    
    private String xplResult;
    
    private boolean isCancelled;
    
    
    
    public FactorizeAttack(FactorizePrint print) {
        this.errorDialog = new ErrorDialog();
        this.infoDialog = new InfoDialog();
        this.utilidades = new Utilities();
        this.print = print;
        this.radix = 10;
        this.isCancelled = false;
    }
    
    
    public boolean init(final String modulus){
        final String modulusStr;
        
        //comprobación de errores        
        //MODULUS------------  
        modulusStr = this.utilidades.formatNumber(modulus);
        
        try{
            this.modulus = new BigInteger(modulusStr, this.radix);
        } catch (NumberFormatException n){            
            Platform.runLater(() -> this.errorDialog.modulus(this.radix));
            return false;
        }
        
        //comprobar que no sea primo antes
        if (this.modulus.isProbablePrime(100)){
            Platform.runLater(() -> this.errorDialog.modulusPrime());
            return false;
        }
            
        
        Platform.runLater(() -> {
            this.print.modulus(this.modulus.toString(this.radix), this.radix);
            this.print.clear();
            this.print.disableBttns();
            this.print.editableModulus(false);
        });
        
        return true;
    }
    
    public void start (String lapsNumStr){
        //Num.Vueltas --> comprobación de errores
        lapsNumStr  = this.utilidades.formatNumber(lapsNumStr);            

        try{
            this.lapsNum =  new BigInteger(lapsNumStr);
        } catch (NumberFormatException n){            
            Platform.runLater(() -> this.errorDialog.laps());
            return;
        }

        if (this.lapsNum.compareTo(Constantes.ONE) == -1){
            Platform.runLater(() -> this.errorDialog.littleNumLaps());
            return;
        }        
        
        Platform.runLater(() -> this.print.lapsNum(this.lapsNum.toString()));
        this.lapsNumTotal = this.lapsNum;
        
        if(this.lapsNum.compareTo(Constantes.MAX_LAPS_FACTORIZE) > 0){
            this.BLNstart();
        } else {
            this.LLNstart();
        }
        
    }
    
    public void Continue (String lapsNumStr){
        //comprobación de errores
        lapsNumStr  = this.utilidades.formatNumber(lapsNumStr);            

        try{
            this.lapsNum =  new BigInteger(lapsNumStr);
        } catch (NumberFormatException n){            
            Platform.runLater(() -> this.errorDialog.laps());
            return;
        }

        if (this.lapsNum.compareTo(Constantes.ONE) == -1){
            Platform.runLater(() -> this.errorDialog.littleNumLaps());
            return;
        }        
        
        Platform.runLater(() -> {
            this.print.lapsNum(this.lapsNum.toString());
            this.print.disableBttns();
        });
        
        if((this.lapsNumTotal.add(this.lapsNum)).compareTo(Constantes.MAX_LAPS_FACTORIZE) > 0){
            this.BLNcontinue();
        } else {
            this.LLNcontinue();
        }
        
    }
    
    public void complete (){
                
        if(this.modulus.bitLength() < 50){
            this.LMComplete();
            
        } else if(this.modulus.bitLength() < 100){
            this.BMComplete(Constantes.BM_REFRESH);
            
        } else {
            this.BMComplete(Constantes.MAX_REFRESH);
        }
        
    }
    
    
    /**
     * Metodo para comenzar a factorizar n, imprimiendo todos los valores del ataque. Se puede parar
     */
    public void LLNstart (){
        BigInteger xPrime, x2Prime, s, antesDeS, laps; 
        final String time;
        long startTime;

        startTime = System.currentTimeMillis();   

        //Preparación de valores iniciales del ataque
        laps = Constantes.ZERO; 
        this.x = Constantes.TWO;
        this.x2 = Constantes.TWO;
        
        this.result = "Vuelta = " + this.utilidades.putPoints(laps.toString(),10) +
                    "\n    --> xi=" + this.utilidades.putPoints(this.x.toString(radix).toUpperCase(), this.radix) + 
                    "\n    --> x2i=" +  this.utilidades.putPoints(this.x2.toString(radix).toUpperCase(), this.radix) + "\n";
        Platform.runLater(() -> this.print.functionValues(this.result));
        this.xplResult = "";
        
        //Comienza el bucle del ataque
        do{
            xPrime = (this.x.pow(2)).add(BigInteger.ONE);
            x2Prime = (((this.x2.modPow(Constantes.TWO,this.modulus)).add(Constantes.ONE)).
                                    modPow(Constantes.TWO,this.modulus)).add(Constantes.ONE);

            this.x = xPrime.mod(this.modulus);
            this.x2 = x2Prime.mod(this.modulus);
            antesDeS=(this.x.subtract(this.x2));
            s = antesDeS.gcd(this.modulus);

            laps = laps.add(Constantes.ONE);

            this.xplResult = this.xplResult + "Vuelta = " + this.utilidades.putPoints(laps.toString(), 10) +
                    "\n    --> xi=" + this.utilidades.putPoints(this.x.toString(radix).toUpperCase(), this.radix) + 
                    "\n    --> x2i=" +  this.utilidades.putPoints(this.x2.toString(radix).toUpperCase(), this.radix) +
                    "\n    --> s=" + this.utilidades.putPoints(s.toString(this.radix).toUpperCase(), this.radix) + "\n";

            if (laps.mod(Constantes.L_REFRESH).equals(Constantes.ZERO)){
                this.result = this.xplResult;
                Platform.runLater(() -> this.print.functionValues(this.result));
                this.xplResult="";
            }               

            if (!(s.equals(Constantes.ONE)) && !(s.equals(this.modulus)) ){
                this.find = true;
                break;
            }

            if (laps.equals(this.lapsNum)){
                this.find = false;
                break;
            }
        } while (!this.isCancelled);
               
        
        this.totalTime = System.currentTimeMillis() - startTime;
        time = this.utilidades.millisToSeconds(this.totalTime);
        //Siempre se imprimen los valores porque si no tocase habria un string vacio
        Platform.runLater(() -> { 
            this.print.time(time);  
            this.print.functionValues(this.xplResult);
        });
       
        
        if (this.find){
            final String strPrimeP = s.toString(this.radix);
            final String strPrimeQ = this.modulus.divide(s).toString(this.radix);
            final String strLaps = laps.toString();
            
            Platform.runLater(() -> {
                this.print.primeP(strPrimeP, this.radix);
                this.print.primeQ(strPrimeQ, this.radix);
                this.print.find(strLaps);
                this.print.EnableStartBttns();
                this.print.editableModulus(true);
            });
            
        } else if (isCancelled){
            Platform.runLater(() -> {
                this.print.EnableStartBttns();
                this.print.editableModulus(true);
                this.print.attackStopped();
            });
        
        } else {            
            Platform.runLater(() ->this.print.dissableStartBttns());             
        }        
    }
    
    /**
     * Metodo para comenzar a factorizar n, sin imprimir todos los valores del ataque. Se puede parar
     */
    public void BLNstart (){
        BigInteger xPrime, x2Prime, s, antesDeS, laps; 
        final String time;
        long startTime;
        boolean write = false;
        
        startTime = System.currentTimeMillis();   

        //Preparación de valores iniciales del ataque
        laps = Constantes.ZERO; 
        this.x = Constantes.TWO;
        this.x2 = Constantes.TWO;
        
        this.xplResult = "Vuelta = " + this.utilidades.putPoints(laps.toString(),10) +
                    "\n    --> xi=" + this.utilidades.putPoints(this.x.toString(radix).toUpperCase(), this.radix) + 
                    "\n    --> x2i=" +  this.utilidades.putPoints(this.x2.toString(radix).toUpperCase(), this.radix) + "\n";
        Platform.runLater(() -> this.print.functionValues(this.xplResult));
         
        //Comienza el bucle del ataque
        do{
            xPrime = (this.x.pow(2)).add(BigInteger.ONE);
            x2Prime = (((this.x2.modPow(Constantes.TWO,this.modulus)).add(Constantes.ONE)).
                                    modPow(Constantes.TWO,this.modulus)).add(Constantes.ONE);

            this.x = xPrime.mod(this.modulus);
            this.x2 = x2Prime.mod(this.modulus);
            antesDeS=(this.x.subtract(this.x2));
            s = antesDeS.gcd(this.modulus);

            laps = laps.add(Constantes.ONE);
            write=false;
                       
            if (laps.mod(Constantes.BLN_REFRESH).equals(Constantes.ZERO)){
                this.result = "Vuelta = " + this.utilidades.putPoints(laps.toString(), 10) +
                    "\n    --> xi=" + this.utilidades.putPoints(this.x.toString(radix).toUpperCase(), this.radix) + 
                    "\n    --> x2i=" +  this.utilidades.putPoints(this.x2.toString(radix).toUpperCase(), this.radix) +
                    "\n    --> s=" + this.utilidades.putPoints(s.toString(this.radix).toUpperCase(), this.radix) + "\n";
                Platform.runLater(() -> this.print.functionValues(this.result));
                write = true;
            }     

            if (!(s.equals(Constantes.ONE)) && !(s.equals(this.modulus)) ){
                this.find = true;
                break;
            }

            if ( laps.equals(this.lapsNum)){
                this.find = false;
                break;
            }
        } while (!this.isCancelled);
        
        //Por si ha factorizado el modulo o se ha pulsado el boton de "parar" o ha terminado las vueltas
        //y le faltan valores por escribir
        if (!write){
                final String lastResult = "Vuelta = " + this.utilidades.putPoints(laps.toString(), 10) +
                    "\n    --> xi=" + this.utilidades.putPoints(this.x.toString(radix).toUpperCase(), this.radix) + 
                    "\n    --> x2i=" +  this.utilidades.putPoints(this.x2.toString(radix).toUpperCase(), this.radix) +
                    "\n    --> s=" + this.utilidades.putPoints(s.toString(this.radix).toUpperCase(), this.radix) + "\n";
                Platform.runLater(() -> this.print.functionValues(lastResult));
            }     
        
        this.totalTime = System.currentTimeMillis() - startTime;
        time = this.utilidades.millisToSeconds(this.totalTime);
        
        Platform.runLater(() ->this.print.time(time));

        if (this.find){
            final String strPrimeP = s.toString(this.radix);
            final String strPrimeQ = this.modulus.divide(s).toString(this.radix);
            final String strLaps = laps.toString();
            
            Platform.runLater(() -> {
                this.print.primeP(strPrimeP, this.radix);
                this.print.primeQ(strPrimeQ, this.radix);
                this.print.find(strLaps);
                this.print.EnableStartBttns();
                this.print.editableModulus(true);
            });
            
        } else if (isCancelled){
            Platform.runLater(() -> {
                this.print.EnableStartBttns();
                this.print.editableModulus(true);
                this.print.attackStopped();
            });
        
        } else {            
            Platform.runLater(() ->this.print.dissableStartBttns());             
        }        
    }
    
    /**
     * Metodo para continuar factorizando n, en caso de que no se haya terminado en el start.
     * Imprime todos los valores del ataque, se puede parar.
     */
    public void LLNcontinue(){
        BigInteger xPrime, x2Prime, s, antesDeS, laps;  
        final String time;
        long startTime;

        startTime = System.currentTimeMillis();

        //Preparación de valores iniciales del ataque
        laps = Constantes.ZERO; 
        this.xplResult = "";

        //Comienza el bucle del ataque
        do{
            xPrime = (this.x.pow(2)).add(BigInteger.ONE);
            x2Prime = (((this.x2.modPow(Constantes.TWO,this.modulus)).add(Constantes.ONE)).
                                    modPow(Constantes.TWO,this.modulus)).add(Constantes.ONE);

            this.x = xPrime.mod(this.modulus);
            this.x2 = x2Prime.mod(this.modulus);
            antesDeS=(this.x.subtract(this.x2));
            s = antesDeS.gcd(this.modulus);

            laps = laps.add(Constantes.ONE);
            this.lapsNumTotal = this.lapsNumTotal.add(Constantes.ONE);
            
            this.xplResult = this.xplResult + "Vuelta = " + this.utilidades.putPoints(this.lapsNumTotal.toString(), 10) +
                    "\n    --> xi=" + this.utilidades.putPoints(this.x.toString(radix).toUpperCase(), this.radix) + 
                    "\n    --> x2i=" +  this.utilidades.putPoints(this.x2.toString(radix).toUpperCase(), this.radix) +
                    "\n    --> s=" + this.utilidades.putPoints(s.toString(this.radix).toUpperCase(), this.radix) + "\n";

            if (this.lapsNumTotal.mod(Constantes.L_REFRESH).equals(Constantes.ZERO)){
                this.result = this.xplResult;
                Platform.runLater(() -> this.print.functionValues(this.result));
                this.xplResult="";
            }     
            
            if (!(s.equals(Constantes.ONE)) && !(s.equals(this.modulus)) ){
                this.find = true;
                break;
            }

            if ( laps.equals(this.lapsNum)){
                this.find = false;
                break;
            }

        } while (!this.isCancelled);
        
        
        this.totalTime = (System.currentTimeMillis() - startTime) + this.totalTime;
        time = this.utilidades.millisToSeconds(this.totalTime);        
        
        //Siempre se imprimen los valores porque si no tocase imprimir habria un string vacio
        Platform.runLater(() -> { 
            this.print.time(time);  
            this.print.functionValues(this.xplResult);
        });       
        

        if (this.find){
            final String strPrimeP = s.toString(this.radix);
            final String strPrimeQ = this.modulus.divide(s).toString(this.radix);
            final String strLaps = this.lapsNumTotal.toString();
            
            Platform.runLater(() -> {
                this.print.primeP(strPrimeP, this.radix);
                this.print.primeQ(strPrimeQ, this.radix);
                this.print.find(strLaps);
                this.print.EnableStartBttns();
                this.print.editableModulus(true);
            });            
        } else if (isCancelled){
            Platform.runLater(() -> {
                this.print.EnableStartBttns();
                this.print.editableModulus(true);
                this.print.attackStopped();
            });
        
        } else {            
            Platform.runLater(() ->this.print.dissableStartBttns());             
        }        
    }
    
    /**
     * Metodo para continuar factorizando n, en caso de que no se haya terminado en el start.
     * No imprime todos los valores del ataque, se puede parar.
     */
    public void BLNcontinue(){
        BigInteger xPrime, x2Prime, s, antesDeS, laps;  
        final String time;
        long startTime;
        boolean write = false;

        startTime = System.currentTimeMillis();

        //Preparación de valores iniciales del ataque
        laps = Constantes.ZERO; 

        //Comienza el bucle del ataque
        do{
            xPrime = (this.x.pow(2)).add(BigInteger.ONE);
            x2Prime = (((this.x2.modPow(Constantes.TWO,this.modulus)).add(Constantes.ONE)).
                                    modPow(Constantes.TWO,this.modulus)).add(Constantes.ONE);

            this.x = xPrime.mod(this.modulus);
            this.x2 = x2Prime.mod(this.modulus);
            antesDeS=(this.x.subtract(this.x2));
            s = antesDeS.gcd(this.modulus);

            laps = laps.add(Constantes.ONE);
            this.lapsNumTotal = this.lapsNumTotal.add(Constantes.ONE);
            write = false;
            
            if (this.lapsNumTotal.mod(Constantes.BLN_REFRESH).equals(Constantes.ZERO)){
                this.result = "Vuelta = " + this.utilidades.putPoints(this.lapsNumTotal.toString(), 10) +
                    "\n    --> xi=" + this.utilidades.putPoints(this.x.toString(radix).toUpperCase(), this.radix) + 
                    "\n    --> x2i=" +  this.utilidades.putPoints(this.x2.toString(radix).toUpperCase(), this.radix) +
                    "\n    --> s=" + this.utilidades.putPoints(s.toString(this.radix).toUpperCase(), this.radix) + "\n";
                Platform.runLater(() -> this.print.functionValues(this.result));
                write = true;
            }    

            if (!(s.equals(Constantes.ONE)) && !(s.equals(this.modulus)) ){
                this.find = true;
                break;
            }

            if ( laps.equals(this.lapsNum)){
                this.find = false;
                break;
            }
        } while (!this.isCancelled);
        
        //Por si ha factorizado el modulo o se ha pulsado el boton de "parar" o ha terminado las vueltas
        //y le faltan valores por escribir
        if(!write){
            this.xplResult = "Vuelta = " + this.utilidades.putPoints(this.lapsNumTotal.toString(), 10) +
                "\n    --> xi=" + this.utilidades.putPoints(this.x.toString(radix).toUpperCase(), this.radix) + 
                "\n    --> x2i=" +  this.utilidades.putPoints(this.x2.toString(radix).toUpperCase(), this.radix) +
                "\n    --> s=" + this.utilidades.putPoints(s.toString(this.radix).toUpperCase(), this.radix) + "\n";
            Platform.runLater(() -> this.print.functionValues(this.xplResult));
        }            
        
        this.totalTime = (System.currentTimeMillis() - startTime) + this.totalTime;
        time = this.utilidades.millisToSeconds(this.totalTime);
        
        Platform.runLater(() -> this.print.time(time));       
        

        if (this.find){
            final String strPrimeP = s.toString(this.radix);
            final String strPrimeQ = this.modulus.divide(s).toString(this.radix);
            final String strLaps = this.lapsNumTotal.toString();
            
            Platform.runLater(() -> {
                this.print.primeP(strPrimeP, this.radix);
                this.print.primeQ(strPrimeQ, this.radix);
                this.print.find(strLaps);
                this.print.EnableStartBttns();
                this.print.editableModulus(true);
            });            
        } else if (isCancelled){
            Platform.runLater(() -> {
                this.print.EnableStartBttns();
                this.print.editableModulus(true);
                this.print.attackStopped();
            });
        
        } else {            
            Platform.runLater(() ->this.print.dissableStartBttns());             
        }          
    }


    /**
     * Metodo para factorizar n hasta encontrar los primos P y Q o hasta que se pulse el boton de parar.
     */
    public void LMComplete (){
        BigInteger xPrime, x2Prime, s, antesDeS, laps;  
        final String time;
        long startTime;
        
        startTime = System.currentTimeMillis(); 
        
        //Preparación de valores iniciales del ataque
        laps = Constantes.ZERO; 
        this.x = Constantes.TWO;
        this.x2 = Constantes.TWO;
        
        this.result = "Vuelta = " + this.utilidades.putPoints(laps.toString(),10) +
                    "\n    --> xi=" + this.utilidades.putPoints(this.x.toString(radix).toUpperCase(), this.radix) + 
                    "\n    --> x2i=" +  this.utilidades.putPoints(this.x2.toString(radix).toUpperCase(), this.radix) + "\n";
        Platform.runLater(() -> this.print.functionValues(this.result));
        this.xplResult = "";
        
        //Comienza el bucle del ataque
        do{
            xPrime = (this.x.pow(2)).add(BigInteger.ONE);
            x2Prime = (((this.x2.modPow(Constantes.TWO,this.modulus)).add(Constantes.ONE)).
                                    modPow(Constantes.TWO,this.modulus)).add(Constantes.ONE);

            this.x = xPrime.mod(this.modulus);
            this.x2 = x2Prime.mod(this.modulus);
            antesDeS=(this.x.subtract(this.x2));
            s = antesDeS.gcd(this.modulus);
            
            laps = laps.add(Constantes.ONE);
            
            this.xplResult = this.xplResult + "Vuelta = " + this.utilidades.putPoints(laps.toString(), 10) +
                    "\n    --> xi=" + this.utilidades.putPoints(this.x.toString(radix).toUpperCase(), this.radix) + 
                    "\n    --> x2i=" +  this.utilidades.putPoints(this.x2.toString(radix).toUpperCase(), this.radix) +
                    "\n    --> s=" + this.utilidades.putPoints(s.toString(this.radix).toUpperCase(), this.radix) + "\n";

            if (laps.mod(Constantes.L_REFRESH).equals(Constantes.ZERO)){
                this.result = this.xplResult;
                Platform.runLater(() -> this.print.functionValues(this.result));
                this.xplResult="";
            }               

        } while ((s.equals(Constantes.ONE) || s.equals(this.modulus)) && !(this.isCancelled));

        //Por si ha factorizado el modulo o se ha pulsado el boton de "parar" y le faltan valores por escribir
        Platform.runLater(() -> this.print.functionValues(this.xplResult));
        
        if (this.isCancelled){
            Platform.runLater(() -> this.print.attackStopped());
            
        } else {
            final String strPrimeP = s.toString(this.radix);
            final String strPrimeQ = this.modulus.divide(s).toString(this.radix);
            final String strLaps = laps.toString();
            
            Platform.runLater(() -> {
                this.print.primeP(strPrimeP, this.radix);
                this.print.primeQ(strPrimeQ, this.radix);
                this.print.find(strLaps);
            });
        }
        time = this.utilidades.millisToSeconds(System.currentTimeMillis() - startTime);
       

        Platform.runLater(() -> {
            this.print.time(time);  
            this.print.EnableStartBttns();
            this.print.editableModulus(true);
            this.print.lapsNum("10");
        });           
        
    }

     /**
     * Metodo para factorizar n hasta encontrar los primos P y Q o hasta que se pulse el boton de parar.
     * Imprime cada REFRESH veces los valores del ataque.
     * @param REFRESH
     */
    public void BMComplete (BigInteger REFRESH){
        BigInteger xPrime, x2Prime, s, antesDeS, laps;  
        final String time;
        long startTime;
        boolean write;
        
        startTime = System.currentTimeMillis();

        //Preparación de valores iniciales del ataque
        laps = Constantes.ZERO; 
        this.x = Constantes.TWO;
        this.x2 = Constantes.TWO;
        
        this.result = "Vuelta = " + this.utilidades.putPoints(laps.toString(), 10) +
                    "\n    --> xi=" + this.utilidades.putPoints(this.x.toString(radix).toUpperCase(), this.radix) + 
                    "\n    --> x2i=" +  this.utilidades.putPoints(this.x2.toString(radix).toUpperCase(), this.radix) + "\n";
        Platform.runLater(() -> this.print.functionValues(this.result));
                
        //Comienza el bucle del ataque
        do{
            xPrime = (this.x.pow(2)).add(BigInteger.ONE);
            x2Prime = (((this.x2.modPow(Constantes.TWO,this.modulus)).add(Constantes.ONE)).
                                    modPow(Constantes.TWO,this.modulus)).add(Constantes.ONE);

            this.x = xPrime.mod(this.modulus);
            this.x2 = x2Prime.mod(this.modulus);
            antesDeS=(this.x.subtract(this.x2));
            s = antesDeS.gcd(this.modulus);

            laps = laps.add(Constantes.ONE);
            write=false;
            
            if (laps.mod(REFRESH).equals(Constantes.ZERO)){
                this.xplResult = "Vuelta = " + this.utilidades.putPoints(laps.toString(), 10) +
                    "\n    --> xi=" + this.utilidades.putPoints(this.x.toString(radix).toUpperCase(), this.radix) + 
                    "\n    --> x2i=" +  this.utilidades.putPoints(this.x2.toString(radix).toUpperCase(), this.radix) +
                    "\n    --> s=" + this.utilidades.putPoints(s.toString(this.radix).toUpperCase(), this.radix) + "\n";
               
                Platform.runLater(() -> this.print.functionValues(this.xplResult));
                write = true;
            }      
        } while ((s.equals(Constantes.ONE) || s.equals(this.modulus)) && !(this.isCancelled));

        //Por si ha factorizado el modulo o se ha pulsado el boton de "parar" y le faltan valores por escribir
        if (!write){
            final String lastResult = "Vuelta = " + this.utilidades.putPoints(laps.toString(), 10) +
                "\n    --> xi=" + this.utilidades.putPoints(this.x.toString(radix).toUpperCase(), this.radix) + 
                "\n    --> x2i=" +  this.utilidades.putPoints(this.x2.toString(radix).toUpperCase(), this.radix) +
                "\n    --> s=" + this.utilidades.putPoints(s.toString(this.radix).toUpperCase(), this.radix) + "\n";

            Platform.runLater(() -> this.print.functionValues(lastResult));
        }             
        
          
        if (this.isCancelled){
            Platform.runLater(() -> this.print.attackStopped());
            
        } else {
            final String strPrimeP = s.toString(this.radix);
            final String strPrimeQ = this.modulus.divide(s).toString(this.radix);
            final String strLaps = laps.toString();
            
            Platform.runLater(() -> {
                this.print.primeP(strPrimeP, this.radix);
                this.print.primeQ(strPrimeQ, this.radix);
                this.print.find(strLaps);
            });
        }

        time = this.utilidades.millisToSeconds(System.currentTimeMillis() - startTime);
        Platform.runLater(() -> {
            this.print.time(time);  
            this.print.EnableStartBttns();
            this.print.editableModulus(true);
            this.print.lapsNum("10");
        });           
    }

        
    public void putInfo() {
        this.infoDialog.factorization();
    }    
    
    
    
    
    
    public void setRadix (int radix){
         this.radix = radix;
    }
    
    public void setIsCancelled (boolean value){
        this.isCancelled = value;
    }     
}
