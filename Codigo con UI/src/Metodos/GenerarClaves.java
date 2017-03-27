/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Metodos;

import Imprimir.Print;
import Model.ComponentesRSA;
import Model.Constantes;
import genrsa.sceneController;
import java.math.BigInteger;
import java.security.SecureRandom;

/**
 *
 * @author rdiazarr
 */
public class GenerarClaves {
    
    private final ComponentesRSA RSA;   
    
    private final Utilidades utilidades;
    
    private final Print print;
    //decimal =10, hexadecimal =16
    private int radix;
    
    // atributo que almacena el tiempo inicial de una operacion
    private long runningTime0;
    // atributo que almacena el tiempo final de una operacion
    private long runningTime1;
    // atributo que almacena el tiempo empleado en una operacion
    private String runningTimeFinal;
    
    public GenerarClaves (sceneController scene){
        this.RSA = new ComponentesRSA();
        this.utilidades = new Utilidades();
        this.print = new Print(scene);
        this.radix = 10;
    }
    
    
    
    /**
     * automatic, primes with same number of bits. PublicKeySize equal to PrivateKeySize
     * @param keySize
     * @param sameSizePrimes
     * @return 
     */
    //preguntar al profesor y comprobar cual tiene que ser el keySize minimo 
    //comprobar en la clave que p y q no sean el mismo numero
    public ComponentesRSA autoRSAkeys(String keySize, boolean sameSizePrimes) {        
        this.runningTime0 = System.currentTimeMillis();
        int distanceBits;
        
        keySize = this.utilidades.formatNumber(keySize);
        
        if (!this.utilidades.checkKeySize(keySize)){            
            //  llamar al metodo que imprima error en autoRSAKeys    
            return null;
        } 
        this.RSA.setKeySize(Integer.parseInt(keySize));
        
        distanceBits = this.calculateDistanceBits(sameSizePrimes);
        this.createRSAKeys(distanceBits);        
        
        this.runningTime1 = System.currentTimeMillis();
        this.runningTimeFinal = this.utilidades.millisToSeconds(runningTime1  - runningTime0) ;
        
        this.print.autoGeneration(this.RSA, keySize, this.runningTimeFinal, this.radix);          
        
        this.calculateCKP();
        this.calculateNumNNC();
        
        return this.RSA;
    }
    
    /**
     * 
     * @param keySize
     */
    private void createRSAKeys(int distanceBits) {
         /* Step 1: Select the prime numbers (p and q) */
        this.RSA.setP( BigInteger.probablePrime((this.RSA.getKeySize()/2)+distanceBits, new SecureRandom()));
        this.RSA.setQ( BigInteger.probablePrime((this.RSA.getKeySize()/2)-distanceBits, new SecureRandom()));

        /* Step 2:  n = p.q */
        this.RSA.setN( this.RSA.getP().multiply(this.RSA.getQ()));

        /* Step 3: phi N = (p - 1).(q - 1) */
        this.RSA.setpMinusOne( this.RSA.getP().subtract(Constantes.ONE));
        this.RSA.setqMinusOne( this.RSA.getQ().subtract(Constantes.ONE));
        this.RSA.setPhiN( this.RSA.getpMinusOne().multiply(this.RSA.getqMinusOne()));

        /* Step 4: Find e, gcd(e, ø(n)) = 1 ; 1 < e < ø(n) */
        do {
                this.RSA.setE( new BigInteger(this.RSA.getKeySize(), new SecureRandom()));
                // compareTo da 1 si es mayor que el valor entre parentesis
        } while ((this.RSA.getE().compareTo(this.RSA.getPhiN()) > -1) || 
                 (this.RSA.getE().gcd(this.RSA.getPhiN()).compareTo(Constantes.ONE)) != 0);

        /* Step 5: Calculate d such that e.d = 1 (mod ø(n)) */
        this.RSA.setD( this.RSA.getE().modInverse(this.RSA.getPhiN()));
    }
    
    
    /**
     * Metodo que calcula las claves privada parejas
     */
    //comprobar que no se coge 7 cuando es 6.98, se tiene que coger 6 claves privadas parejas
    public void calculateCKP(){
            //almacena la clave privada pareja
            BigInteger cpp;
            int iterador=1;
            int CKP_int;

            //minimo comun multiplo a través del mcd-gcd
            this.RSA.setGamma(this.RSA.getpMinusOne().multiply
                               (this.RSA.getqMinusOne().divide
                              (this.RSA.getpMinusOne().gcd(this.RSA.getqMinusOne()))));	 	
            
            cpp = this.RSA.getE().modInverse(this.RSA.getGamma());

            this.RSA.setNumCKP( ((this.RSA.getN().subtract(cpp)).divide(this.RSA.getGamma())) );
            
            //Imprime           
            this.print.numClavesParejas(this.RSA.getNumCKP());
            this.print.clavePareja(cpp, this.radix);
            
            //para controlar el while, dado que si el numero es mayor que el max_value de los integer
            //podria llegar a ser un numero negativo y no se calcularian las CKP
            CKP_int = this.CKPtoInt();
            //OJO, he añadido condicion para que pare a las 30
            while (CKP_int >= iterador || iterador > 30){
                    cpp=cpp.add(this.RSA.getGamma());
                    if (cpp.compareTo(this.RSA.getD()) != 0){
                            this.print.addClavePareja(cpp, this.radix);
                    }
                    iterador++;
            }
    }
    /**
     * Calcula el numero de numeros no cifrables
     */
    //ojito, este metodo quiza cambiarlo a otra clase, junto con el de calculateCKP
    private void calculateNumNNC( ){
        BigInteger eMinusOne,part1, part2;

        eMinusOne= this.RSA.getE().subtract(Constantes.ONE);

        part1 = (Constantes.ONE.add((eMinusOne.gcd(this.RSA.getpMinusOne()))));
        part2 = (Constantes.ONE.add((eMinusOne.gcd(this.RSA.getqMinusOne()))));
        this.RSA.setNumNNC( part1.multiply(part2));
        
        this.print.numNNC(this.RSA.getNumNNC());
    }

    
    
    
    
    private int CKPtoInt() {
        int numCKP = Constantes.MAX_INT;
        
        //si no sobrepasa el maximo
        if (this.RSA.getNumCKP().compareTo(Constantes.MAX_INT_BI)<1){
            numCKP = this.RSA.getNumCKP().intValue();
        } 
        
        return numCKP;
    }
    /**
     * Metodo para calcular la diferencia de bits en el caso de que no se quieran p y q del mismo tamaño
     * @param isSameSize
     * @return 
     */
    private int calculateDistanceBits(boolean isSameSize) {
        int keySize = this.RSA.getKeySize();
        int distance = 0;
        
        if (!isSameSize){
             if (keySize > 40){
            distance = 4;
            } else if (keySize > 30){
                distance = 3;
            } else if (keySize > 25){
                distance = 2;
            } else if (keySize > 18){
                distance = 1;
            }
        }       
        
        return distance;
    }
    
    
    /**
     * 
     * @param radix 
     */
    public void setUnits( int radix){
        this.radix = radix;
    }

    
}
