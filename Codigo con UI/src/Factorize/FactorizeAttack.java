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
    
    
    
    public FactorizeAttack(FactorizePrint print) {
        this.errorDialog = new ErrorDialog();
        this.infoDialog = new InfoDialog();
        this.utilidades = new Utilidades();
        this.print = print;
        this.radix = 10;
    }
    
    
    //comenzar a factorizar n	
    public void start (String modulusStr, String lapsNumStr){
            BigInteger xPrime, x2Prime, s, antesDeS, laps; 
            String time;
            long startTime;
            
            startTime = System.currentTimeMillis();
            
            this.print.clear();
            this.print.modulusEditable(false);
            
            //comprobación de errores
            lapsNumStr  = this.utilidades.formatNumber(lapsNumStr);
            modulusStr   = this.utilidades.formatNumber(modulusStr);

            
            try{
              this.modulus = new BigInteger(modulusStr, this.radix);
            } catch (NumberFormatException n){            
                this.errorDialog.modulus(this.radix);
                return;
            }
            
            try{
                this.lapsNum =  new BigInteger(lapsNumStr);
            } catch (NumberFormatException n){            
                this.errorDialog.laps();
                return;
            }
            
            if (this.lapsNum.compareTo(Constantes.ONE) == -1){
                this.errorDialog.littleNumLaps();
                return;
            }

            //comprobar que no sea primo antes
            if (this.modulus.isProbablePrime(100)){
                this.errorDialog.modulusPrime();
                return;
            }
            
            //lógica del metodo
            laps = Constantes.ZERO; 
            this.x = Constantes.TWO;
            this.x2 = Constantes.TWO;

            do{
                    xPrime = (this.x.pow(2)).add(BigInteger.ONE);
                    x2Prime = (((this.x2.modPow(Constantes.TWO,this.modulus)).add(Constantes.ONE)).
                                            modPow(Constantes.TWO,this.modulus)).add(Constantes.ONE);

                    this.x = xPrime.mod(this.modulus);
                    this.x2 = x2Prime.mod(this.modulus);
                    antesDeS=(this.x.subtract(this.x2));
                    s = antesDeS.gcd(this.modulus);

                    laps = laps.add(Constantes.ONE);
                    print.functionValues(this.x.toString(radix), this.x2.toString(radix), s.toString(radix));
                    
                    if (!(s.equals(Constantes.ONE)) && !(s.equals(this.modulus)) ){
                        this.find = true;
                        break;
                    }
                    
                    if ( laps.equals(this.lapsNum)){
                        this.find = false;
                        break;
                    }
                    
            } while (true);

        if (this.find){
            this.print.primeP(s.toString(radix));
            this.print.primeQ(this.modulus.divide(s).toString(radix));
            this.print.find(laps.toString());
        } else {
             this.print.dissableStart();
             this.lapsNumTotal = this.lapsNum;
        }
        
        this.totalTime = System.currentTimeMillis() - startTime;
        time = this.utilidades.millisToSeconds(this.totalTime);
       
        this.print.modulusEditable(true);
        this.print.modulus(this.modulus.toString(this.radix));
        this.print.lapsNum(this.lapsNum.toString());
        this.print.time(time);
    }
    
    //Continuar factorizar n, en caso de que no se haya terminado en el start.	
    public void Continue(){
        BigInteger xPrime, x2Prime, s, antesDeS, laps;  
        String time;
        long startTime;

        startTime = System.currentTimeMillis();

        this.print.modulusEditable(false);
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
            this.print.functionValues(this.x.toString(radix), this.x2.toString(this.radix), s.toString(this.radix));

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

        if (this.find){
            this.lapsNumTotal = this.lapsNumTotal.add(laps);
            this.print.primeP(s.toString(this.radix));
            this.print.primeQ(this.modulus.divide(s).toString(this.radix));            
            this.print.find(this.lapsNumTotal.toString());
            this.print.EnableStart();
        } else {
            this.lapsNumTotal = this.lapsNumTotal.add(this.lapsNum);
        }       
        this.print.modulusEditable(true);
        this.print.time(time);

    }


    //factorizar n hasta encontrar los primos P y Q
    public void obtainPQ (String modulusStr){
        BigInteger xPrime, x2Prime, s, antesDeS, laps;  
        String time;
        long startTime;
        
        startTime = System.currentTimeMillis();

        this.print.clear();
        this.print.modulusEditable(false);
        this.print.disableLapsNum();
        this.print.disableBttns();

        //comprobación de errores
        modulusStr   = this.utilidades.formatNumber(modulusStr);

        try{
          this.modulus = new BigInteger(modulusStr, radix);
        } catch (NumberFormatException n){            
            this.errorDialog.modulus(radix);
            return;
        }

        //comprobar que no sea primo antes
        if (this.modulus.isProbablePrime(100)){
            this.errorDialog.modulusPrime();
            return;
        }

        //COMPROBAR QUE EL NUMERO DE BITS DEL MODULO NO SEA MAYOR QUE Z CIERTO NUMERO
        //SI ES MAYOR IMPRIMIR DIALOGO POR PANTALLA PREGUNTANDO SI SE QUIERE ABORTAR

        //lógica del metodo
        laps = Constantes.ZERO; 
        this.x = Constantes.TWO;
        this.x2 = Constantes.TWO;

        do{
            xPrime = (this.x.pow(2)).add(BigInteger.ONE);
            x2Prime = (((this.x2.modPow(Constantes.TWO,this.modulus)).add(Constantes.ONE)).
                                    modPow(Constantes.TWO,this.modulus)).add(Constantes.ONE);

            this.x = xPrime.mod(this.modulus);
            this.x2 = x2Prime.mod(this.modulus);
            antesDeS=(this.x.subtract(this.x2));
            s = antesDeS.gcd(this.modulus);

            laps = laps.add(Constantes.ONE);
            print.functionValues(this.x.toString(radix), this.x2.toString(radix), s.toString(radix));

        } while (s.equals(Constantes.ONE) || s.equals(this.modulus));

            
        time = this.utilidades.millisToSeconds(System.currentTimeMillis() - startTime);
        
        this.print.primeP(s.toString(radix));
        this.print.primeQ(this.modulus.divide(s).toString(radix));
        this.print.find(laps.toString());       
        this.print.modulusEditable(true);
        this.print.modulus(this.modulus.toString(this.radix));
        
        this.print.enableLapsNum();
        this.print.enableBttns();
        this.print.time(time);
    }

        
    public void putInfo() {
        this.infoDialog.factorization();
    }    
    
    
    
    
    
    public void setRadix (int radix){
         this.radix = radix;
    }
   
}
