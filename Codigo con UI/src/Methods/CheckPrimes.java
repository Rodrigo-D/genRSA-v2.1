/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Methods;

import Imprimir.ErrorDialog;
import Imprimir.Print;
import Model.Constantes;
import genrsa.GenRSAController;
import java.math.BigInteger;
import java.security.SecureRandom;

/**
 *
 * @author rdiazarr
 */
public class CheckPrimes {
    
    BigInteger probPrimeP;
    BigInteger probPrimeQ;
    Utilidades utilidades;
    int vueltas;
    //true si es P, false si es Q
    boolean isPrimeP;
    
    //decimal =10, hexadecimal =16
    private int radix;
    
    private final Print print;
    
    private final ErrorDialog errorDialog; 
    
    /**
     * Constructor de la clase
     * @param scene
     */
    public CheckPrimes(GenRSAController scene) {
        this.utilidades = new Utilidades();
        this.print = new Print(scene);
        this.radix = 10;        
        this.errorDialog = new ErrorDialog();
    }
    
    /**
     * Método que comprueba errores y llama al test a ejecutar
     * @param probNumberP
     * @param probNumberQ
     * @param vueltas
     * @param isMiller 
     */
    public void check(String probNumberP, String probNumberQ, String vueltas, 
                    final boolean isMiller){   
        
        //atributos que almacenan los resultados del test de primalidad
        boolean resultadoP, resultadoQ;
        // atributo que almacena el tiempo inicial 
        long startTime;
        // atributo que almacena el tiempo empleado
        String time;
        
        
        startTime = System.currentTimeMillis();
        this.print.flushIsPrime();
        
         
        probNumberP = this.utilidades.formatNumber(probNumberP);
        probNumberQ = this.utilidades.formatNumber(probNumberQ);                
        vueltas = this.utilidades.formatNumber(vueltas);
        
        //con que uno de los dos no sea valido se termina
        try {
            this.probPrimeP = new BigInteger (probNumberP, this.radix);
            this.probPrimeQ = new BigInteger (probNumberQ, this.radix);    
            
        } catch (NumberFormatException n){            
            this.errorDialog.primeConversion(this.radix);
            return;
        }
        
        if (!this.utilidades.isNumber(vueltas)){
            this.errorDialog.iterations();         
            return;
        } 
        
        this.vueltas = Integer.parseInt(vueltas);
        
        if (this.probPrimeP.compareTo(Constantes.THREE) <= 0 || this.probPrimeQ.compareTo(Constantes.THREE) <= 0){
                this.errorDialog.primeLittle();
                return;
        }
        if (this.probPrimeP.mod(Constantes.TWO).equals(Constantes.ZERO) || this.probPrimeQ.mod(Constantes.TWO).equals(Constantes.ZERO)){
                this.errorDialog.multipleTwo();
                return;
        }       
        
        if(isMiller){
            resultadoP = this.testPrimalityMillerRabin(this.probPrimeP);
            resultadoQ = this.testPrimalityMillerRabin(this.probPrimeQ);
        } else {
            resultadoP = this.testPrimalityFermat(this.probPrimeP);
            resultadoQ = this.testPrimalityFermat(this.probPrimeQ);
        }
        
        time = this.utilidades.millisToSeconds(System.currentTimeMillis() - startTime);
        this.print.primalityResults(resultadoP, resultadoQ, time);        
    }
    

    /**
     * Test de primalidad, algoritmos usados: Miller Rabin y Lucas-Lehmer
     * @param probPrime
     * @return 
     */
    private boolean testPrimalityMillerRabin(BigInteger probPrime) {        
        return probPrime.isProbablePrime(vueltas);
    }



    /**
     * Test de primalidad: Fermat
     * @param probPrime
     * @return 
     */	
    private boolean testPrimalityFermat(BigInteger probPrime) {
        BigInteger a;
        BigInteger probPrimeMinusTwo;
        BigInteger probPrimeMinusOne;
        

        probPrimeMinusTwo = probPrime.subtract(Constantes.TWO);
        probPrimeMinusOne = probPrime.subtract(Constantes.ONE);		

        for(int i=0;i<this.vueltas;i++){
                // entre 2 y probPrime-2 puesto que 1 y probPrime-1 eleveado a probPrime-1 
                // siempre da de resultado 1 en mod probPrime.
                a = uniformRandom(Constantes.TWO, probPrimeMinusTwo); 
                // a^(probPrime-1) mod probPrime
                a = a.modPow(probPrimeMinusOne, probPrime);

            if(!a.equals(Constantes.ONE)){ 
                return false; /* p is definitely composite */
            }
        }
       return true;	
    }


    /**
     * Devuelve un BigInteger random entre los dos valores pasados por parametros (AMBOS INCLUSIVE)
     * @param bottom
     * @param top
     * @return 
     */
    private BigInteger uniformRandom(BigInteger bottom, BigInteger top) {
        SecureRandom rnd = new SecureRandom();
        BigInteger random;
        
        do {
            random = new BigInteger(top.bitLength(), rnd);
        } while (random.compareTo(bottom) < 0 || random.compareTo(top) > 0);
        return random;
    }
    
    
    /**
     * Establece la base a utilizar
     * @param radix 
     */
    public void setUnits( int radix ){
        this.radix = radix;
    }
    
}