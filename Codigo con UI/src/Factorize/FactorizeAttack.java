/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Factorize;

import Imprimir.ErrorDialog;
import Imprimir.FactorizePrint;
import Imprimir.InfoDialog;
import Metodos.Utilidades;
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
    
    private final Utilidades utilidades;
    
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
    
    
    
    public FactorizeAttack(FactorizePrint print) {
        this.errorDialog = new ErrorDialog();
        this.infoDialog = new InfoDialog();
        this.utilidades = new Utilidades();
        this.print = print;
        this.radix = 10;
    }
    
    
    public boolean init(final String modulus){
        final String modulusStr;
        //comprobación de errores
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
    
     public void obtainPQ (){
                
        if(this.modulus.bitLength() > 50){
            this.BMobtainPQ();
        } else {
            this.LMobtainPQ();
        }
        
    }
    
    
    //comenzar a factorizar n, imprimiendo todos los valores del ataque
    public void LLNstart (){
        BigInteger xPrime, x2Prime, s, antesDeS, laps; 
        final String time;
        long startTime;

        startTime = System.currentTimeMillis();   

        //lógica del metodo
        laps = Constantes.ZERO; 
        this.x = Constantes.TWO;
        this.x2 = Constantes.TWO;
        
        this.result = "Vuelta= " + this.utilidades.putPoints(laps.toString(),10) +
                    "\n    --> xi=" + this.utilidades.putPoints(this.x.toString(radix).toUpperCase(), this.radix) + 
                    "\n    --> x2i=" +  this.utilidades.putPoints(this.x2.toString(radix).toUpperCase(), this.radix) + "\n";
        Platform.runLater(() -> this.print.functionValues(this.result));
        this.xplResult = "";
        
        do{
            xPrime = (this.x.pow(2)).add(BigInteger.ONE);
            x2Prime = (((this.x2.modPow(Constantes.TWO,this.modulus)).add(Constantes.ONE)).
                                    modPow(Constantes.TWO,this.modulus)).add(Constantes.ONE);

            this.x = xPrime.mod(this.modulus);
            this.x2 = x2Prime.mod(this.modulus);
            antesDeS=(this.x.subtract(this.x2));
            s = antesDeS.gcd(this.modulus);

            laps = laps.add(Constantes.ONE);

            this.xplResult = this.xplResult + "Vuelta= " + this.utilidades.putPoints(laps.toString(), 10) +
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
        } while (true);
               
        
        this.totalTime = System.currentTimeMillis() - startTime;
        time = this.utilidades.millisToSeconds(this.totalTime);
       
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
        } else {
            Platform.runLater(() ->this.print.dissableStartBttns());             
        }        
    }
    
    //comenzar a factorizar n, sin imprimir todos los valores del ataque
    public void BLNstart (){
        BigInteger xPrime, x2Prime, s, antesDeS, laps; 
        final String time;
        long startTime;
        boolean write = false;
        
        startTime = System.currentTimeMillis();   

        //lógica del metodo
        laps = Constantes.ZERO; 
        this.x = Constantes.TWO;
        this.x2 = Constantes.TWO;
        
        this.xplResult = "Vuelta= " + this.utilidades.putPoints(laps.toString(),10) +
                    "\n    --> xi=" + this.utilidades.putPoints(this.x.toString(radix).toUpperCase(), this.radix) + 
                    "\n    --> x2i=" +  this.utilidades.putPoints(this.x2.toString(radix).toUpperCase(), this.radix) + "\n";
        Platform.runLater(() -> this.print.functionValues(this.xplResult));
                
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
                this.result = "Vuelta= " + this.utilidades.putPoints(laps.toString(), 10) +
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
        } while (true);
        
        
        if (!write){
                final String lastResult = "Vuelta= " + this.utilidades.putPoints(laps.toString(), 10) +
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
        } else {
            Platform.runLater(() ->this.print.dissableStartBttns());             
        }
    }
    
    
    //Continuar factorizar n, en caso de que no se haya terminado en el start.
    //imprimiendo todos  los valores del ataque
    public void LLNcontinue(){
        BigInteger xPrime, x2Prime, s, antesDeS, laps;  
        final String time;
        long startTime;

        startTime = System.currentTimeMillis();

        //lógica del metodo
        laps = Constantes.ZERO; 
        this.xplResult = "";

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
            
            this.xplResult = this.xplResult + "Vuelta= " + this.utilidades.putPoints(this.lapsNumTotal.toString(), 10) +
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

        } while (true);
        
        
        this.totalTime = (System.currentTimeMillis() - startTime) + this.totalTime;
        time = this.utilidades.millisToSeconds(this.totalTime);        
        
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
        } else {
             Platform.runLater(() ->this.print.dissableStartBttns());
        }  
    }
    
    //Continuar factorizar n, en caso de que no se haya terminado en el start.
    //sin imprimir todos  los valores del ataque
    public void BLNcontinue(){
        BigInteger xPrime, x2Prime, s, antesDeS, laps;  
        final String time;
        long startTime;
        boolean write = false;

        startTime = System.currentTimeMillis();

        //lógica del metodo
        laps = Constantes.ZERO; 

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
                this.result = "Vuelta= " + this.utilidades.putPoints(this.lapsNumTotal.toString(), 10) +
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
        } while (true);
        
        
        if (!write){
            this.xplResult = "Vuelta= " + this.utilidades.putPoints(this.lapsNumTotal.toString(), 10) +
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
        } else {
             Platform.runLater(() ->this.print.dissableStartBttns());
        }      
    }


    //factorizar n hasta encontrar los primos P y Q
    public void LMobtainPQ (){
        BigInteger xPrime, x2Prime, s, antesDeS, laps;  
        final String time;
        long startTime;
        
        startTime = System.currentTimeMillis(); 
        //lógica del metodo
        laps = Constantes.ZERO; 
        this.x = Constantes.TWO;
        this.x2 = Constantes.TWO;
        
        this.result = "Vuelta= " + this.utilidades.putPoints(laps.toString(),10) +
                    "\n    --> xi=" + this.utilidades.putPoints(this.x.toString(radix).toUpperCase(), this.radix) + 
                    "\n    --> x2i=" +  this.utilidades.putPoints(this.x2.toString(radix).toUpperCase(), this.radix) + "\n";
        Platform.runLater(() -> this.print.functionValues(this.result));
        this.xplResult = "";
        

        do{
            xPrime = (this.x.pow(2)).add(BigInteger.ONE);
            x2Prime = (((this.x2.modPow(Constantes.TWO,this.modulus)).add(Constantes.ONE)).
                                    modPow(Constantes.TWO,this.modulus)).add(Constantes.ONE);

            this.x = xPrime.mod(this.modulus);
            this.x2 = x2Prime.mod(this.modulus);
            antesDeS=(this.x.subtract(this.x2));
            s = antesDeS.gcd(this.modulus);

            laps = laps.add(Constantes.ONE);
            
            this.xplResult = this.xplResult + "Vuelta= " + this.utilidades.putPoints(laps.toString(), 10) +
                    "\n    --> xi=" + this.utilidades.putPoints(this.x.toString(radix).toUpperCase(), this.radix) + 
                    "\n    --> x2i=" +  this.utilidades.putPoints(this.x2.toString(radix).toUpperCase(), this.radix) +
                    "\n    --> s=" + this.utilidades.putPoints(s.toString(this.radix).toUpperCase(), this.radix) + "\n";

            if (laps.mod(Constantes.L_REFRESH).equals(Constantes.ZERO)){
                this.result = this.xplResult;
                Platform.runLater(() -> this.print.functionValues(this.result));
                this.xplResult="";
            }               

        } while (s.equals(Constantes.ONE) || s.equals(this.modulus));

            
        time = this.utilidades.millisToSeconds(System.currentTimeMillis() - startTime);
        final String strPrimeP = s.toString(this.radix);
        final String strPrimeQ = this.modulus.divide(s).toString(this.radix);
        final String strLaps = laps.toString();

        Platform.runLater(() -> {
            this.print.time(time);  
            this.print.functionValues(this.xplResult);
            this.print.primeP(strPrimeP, this.radix);
            this.print.primeQ(strPrimeQ, this.radix);
            this.print.find(strLaps);
            this.print.EnableStartBttns();
            this.print.editableModulus(true);
            this.print.lapsNum("10");
        });           
        
    }

    //factorizar n hasta encontrar los primos P y Q
    public void BMobtainPQ (){
        BigInteger xPrime, x2Prime, s, antesDeS, laps;  
        final String time;
        long startTime;
        boolean write;
        
        startTime = System.currentTimeMillis(); 
        //lógica del metodo
        laps = Constantes.ZERO; 
        this.x = Constantes.TWO;
        this.x2 = Constantes.TWO;
        
        this.result = "Vuelta= " + this.utilidades.putPoints(laps.toString(), 10) +
                    "\n    --> xi=" + this.utilidades.putPoints(this.x.toString(radix).toUpperCase(), this.radix) + 
                    "\n    --> x2i=" +  this.utilidades.putPoints(this.x2.toString(radix).toUpperCase(), this.radix) + "\n";
        Platform.runLater(() -> this.print.functionValues(this.result));
                

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
            
            if (laps.mod(Constantes.BM_REFRESH).equals(Constantes.ZERO)){
                this.xplResult = "Vuelta= " + this.utilidades.putPoints(laps.toString(), 10) +
                    "\n    --> xi=" + this.utilidades.putPoints(this.x.toString(radix).toUpperCase(), this.radix) + 
                    "\n    --> x2i=" +  this.utilidades.putPoints(this.x2.toString(radix).toUpperCase(), this.radix) +
                    "\n    --> s=" + this.utilidades.putPoints(s.toString(this.radix).toUpperCase(), this.radix) + "\n";
               
                Platform.runLater(() -> this.print.functionValues(this.xplResult));
                write = true;
            }         

        } while (s.equals(Constantes.ONE) || s.equals(this.modulus));

            
        if (!write){
            final String lastResult = "Vuelta= " + this.utilidades.putPoints(laps.toString(), 10) +
                "\n    --> xi=" + this.utilidades.putPoints(this.x.toString(radix).toUpperCase(), this.radix) + 
                "\n    --> x2i=" +  this.utilidades.putPoints(this.x2.toString(radix).toUpperCase(), this.radix) +
                "\n    --> s=" + this.utilidades.putPoints(s.toString(this.radix).toUpperCase(), this.radix) + "\n";

            Platform.runLater(() -> this.print.functionValues(lastResult));
        }         
        
        
        
        time = this.utilidades.millisToSeconds(System.currentTimeMillis() - startTime);
        final String strPrimeP = s.toString(this.radix);
        final String strPrimeQ = this.modulus.divide(s).toString(this.radix);
        final String strLaps = laps.toString();

        Platform.runLater(() -> {
            this.print.time(time);  
            this.print.primeP(strPrimeP, this.radix);
            this.print.primeQ(strPrimeQ, this.radix);
            this.print.find(strLaps);
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
   
}
