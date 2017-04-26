/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Paradox;

import Imprimir.ErrorDialog;
import Imprimir.InfoDialog;
import Imprimir.ParadoxPrint;
import Metodos.Utilidades;
import Model.Constantes;
import java.math.BigInteger;

/**
 *
 * @author rdiazarr
 */
public class ParadoxAttack {
    
    
     private final ErrorDialog errorDialog;
    
    private final InfoDialog infoDialog;
    
    private final Utilidades utilidades;
    
    private final ParadoxPrint Pprint;
    
    
        
    private int radix;
    
    private BigInteger modulus;
    
    private BigInteger exponent;
    
    private BigInteger message;
    
    private long avgCipherStats;
    
    
    private BigInteger totalLapsNum;
            
    

    public ParadoxAttack(ParadoxPrint paradoxPrint) {
        this.errorDialog = new ErrorDialog();
        this.infoDialog = new InfoDialog();
        this.utilidades = new Utilidades();
        this.Pprint = paradoxPrint;
        this.radix = 10;
        
    }
    
    public boolean init(String message, String modulus, String exponent) {
       
        //MODULUS------------  
        modulus = this.utilidades.formatNumber(modulus);
        
        try{
            this.modulus = new BigInteger(modulus, this.radix);
        } catch (NumberFormatException n){            
            this.errorDialog.paradoxModule(this.radix);
            return false;
        }
        
        //EXPONENT------------  
        exponent = this.utilidades.formatNumber(exponent);
        
        try{
            this.exponent = new BigInteger(exponent, this.radix);
        } catch (NumberFormatException n){            
            this.errorDialog.paradoxExponent(this.radix);
            return false;
        }
        
        
        //pregunta en doc de preguntas
        
        //MESSAGE------------        
        message = this.utilidades.formatNumber(message);
        
        try{
            this.message = new BigInteger(message, this.radix);
        } catch (NumberFormatException n){            
            this.errorDialog.paradoxMessage(this.radix);
            return false;
        }
        
        if (this.message.compareTo(Constantes.ONE) == -1){
            this.errorDialog.paradoxMessage(radix);
            return false;
        }
        
        if (this.message.compareTo(this.modulus) > -1){
            this.errorDialog.bigMessage(radix);
            return false;
        }
        
       this.Pprint.numbers(this.modulus.toString(this.radix).toUpperCase(),
                            this.exponent.toString(this.radix).toUpperCase(),
                            this.message.toString(this.radix).toUpperCase());
        
        return true;        
    }
    
    
    
    
    
    
    //ataque por la paradoja del cumpleaños, no para hasta que prospera
    public void start(){
        BigInteger i, cipherI;
        BigInteger j, CipherJ;
        BigInteger IMinusJ, w, s, t;
        long startTime, totalTime;
        String time;
        
        startTime = System.currentTimeMillis();
				
        i = new BigInteger("1");
        j = this.modulus.divide(Constantes.TWO);

        cipherI = this.message;
        CipherJ = this.message.modPow(j, this.modulus);

        this.Pprint.initialResults(cipherI.toString(this.radix).toUpperCase(),
                                    CipherJ.toString(this.radix).toUpperCase(),
                                    j.toString(this.radix).toUpperCase(),
                                    this.modulus.toString(this.radix).toUpperCase());

        while (!(cipherI.equals(CipherJ)) && i.compareTo(j) == -1){
                i = i.add(Constantes.ONE);
                cipherI = this.message.multiply(cipherI).mod(this.modulus);

                this.Pprint.partialResults(this.message.toString(this.radix).toUpperCase(),
                                    i.toString(this.radix).toUpperCase(),
                                    this.modulus.toString(this.radix).toUpperCase(),
                                    cipherI.toString(this.radix).toUpperCase());

                //cuando se haga concurrente ir imprimiendo los cifrados por segundo
        } 

        if (cipherI.equals(CipherJ)){
                j = this.modulus.divide(Constantes.TWO);
        } else {
                this.Pprint.partialDelete();
                return;
                //error ¿Realmente se puede dar el caso?
        }

        IMinusJ = i.subtract(j).abs();

        w = (IMinusJ).divide(this.exponent.gcd(IMinusJ));

        this.Pprint.wValue(i.toString(this.radix).toUpperCase(), 
                            j.toString(this.radix).toUpperCase(),
                            this.exponent.toString(this.radix).toUpperCase(),
                            w.toString(this.radix).toUpperCase());

        s = w.modInverse(this.exponent);
        //t es la clave privada o una clave privada pareja o un falso positivo
        t = this.exponent.modInverse(w);
        
        // comprobar, pero no sirve para nada((w.multiply(s)).add(publica.multiply(t))).equals(BigInteger.ONE));
        
        
        totalTime = System.currentTimeMillis() - startTime;
        time = this.utilidades.millisToSeconds(totalTime);
        
        
        this.Pprint.tValue(t.toString(this.radix).toUpperCase());
        this.Pprint.privateKey(t.toString(this.radix).toUpperCase());
        this.Pprint.time(time);
        //imprimir media de claves cifradas por segundo
       // this.Pprint.avgStats();

        this.checkObtainedKey(t);
    }
    
    
    private void checkObtainedKey(BigInteger key) {
        BigInteger numberEncrypt, numberDecrypt;
        BigInteger modulusMinusOne = this.modulus.subtract(Constantes.ONE);
        BigInteger number = new BigInteger("20");
        
        number = number.mod(this.modulus);
        
        while (number.equals(this.message) || number.equals(Constantes.ONE) || 
                number.equals(Constantes.ZERO) || number.equals(modulusMinusOne)){
            number = number.add(Constantes.ONE);
            number = number.mod(this.modulus);
        }
        
        numberEncrypt = number.modPow(this.exponent, this.modulus);
        
        numberDecrypt = numberEncrypt.modPow(key, this.modulus);
        
        if (numberDecrypt.equals(number)){
            this.Pprint.goodResult();
        } else{
            this.Pprint.badResult();
        }
        

    }
    
    
    public void  clean(){
        this.Pprint.partialDelete();
    }
    
    
    
    public void putInfo() {
        this.infoDialog.paradox();
    }
     
     
    void warning() {
        this.infoDialog.warningParadox();
    }
     
    
    
    
    
    public void setRadix (int radix){
         this.radix = radix;
    }

   

   
    
}
