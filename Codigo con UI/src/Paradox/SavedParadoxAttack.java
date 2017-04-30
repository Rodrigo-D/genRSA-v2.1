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
public class SavedParadoxAttack {
    
    
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
            
    private long totalTime;
    

    public SavedParadoxAttack(ParadoxPrint paradoxPrint) {
        this.errorDialog = new ErrorDialog();
        this.infoDialog = new InfoDialog();
        this.utilidades = new Utilidades();
        this.Pprint = paradoxPrint;
        this.radix = 10;
        
    }
    
    public boolean init(String message, String modulus, String exponent) {
       
        //MODULE------------  
        modulus = this.utilidades.formatNumber(modulus);
        
        try{
            this.modulus = new BigInteger(modulus, this.radix);
        } catch (NumberFormatException n){            
            this.errorDialog.Modulus(this.radix);
            return false;
        }
        
        //EXPONENT------------  
        exponent = this.utilidades.formatNumber(exponent);
        
        try{
            this.exponent = new BigInteger(exponent, this.radix);
        } catch (NumberFormatException n){            
            this.errorDialog.Exponent(this.radix);
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
    public void complete (){
        BigInteger i, cipherI, initialCipherI;
        BigInteger j, cipherJ, initialCipherJ;
        BigInteger IMinusJ, w, s, t;
				
		i = new BigInteger("1");
		j = this.modulus.divide(Constantes.TWO);
		
		initialCipherI = this.message.modPow(i, this.modulus);
		initialCipherJ = this.message.modPow(j, this.modulus);
		do{
			i = i.add(Constantes.ONE);
			j = j.add(Constantes.ONE);
			cipherI = this.message.modPow(i, this.modulus);
			cipherJ = this.message.modPow(j, this.modulus);
		} while (!(cipherI.equals(initialCipherI) || cipherJ.equals(initialCipherJ)));
		
		if (cipherI.equals(initialCipherJ)){
			j = this.modulus.divide(Constantes.TWO);
		} else {
			i = Constantes.ONE;
		}
		
                IMinusJ = i.subtract(j).abs();
                
		w = (IMinusJ).divide(this.exponent.gcd(IMinusJ));
		
		s = w.modInverse(this.exponent);
		t = this.exponent.modInverse(w);
			
		// comprobar, pero no sirve para nada((w.multiply(s)).add(publica.multiply(t))).equals(BigInteger.ONE));
		/*
		System.out.println ("Ci = " + Ci);
		System.out.println ("initCj = " + initCj);
		System.out.println ("Cj = " + Cj);
		System.out.println ("initCi = " + initCi);
		
		System.out.println("\n\n La clave privada o la publica pareja hallada es: " + t.toString());*/
    }
    
    
    /*
    
    public void putInfo() {
        this.infoDialog.paradox();
    }
     
     
     */
     
    
    public void setRadix (int radix){
         this.radix = radix;
    }
    
}
