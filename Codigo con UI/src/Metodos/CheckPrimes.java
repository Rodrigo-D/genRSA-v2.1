/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Metodos;

import Imprimir.Print;
import Model.Constantes;
import genrsa.SceneController;
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
    
    /**
     * Constructor de la clase
     * @param scene
     */
    public CheckPrimes(SceneController scene) {
        this.utilidades = new Utilidades();
        this.print = new Print(scene);
    }
    
    /**
     * 
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
            this.print.primeError(this.radix);
            return;
        }
        
        if (!this.utilidades.isNumber(vueltas)){
           // imprimir un mensaje diciendo que no es un numero
            this.print.iterationsError();         
            return;
        } 
        
        this.vueltas = Integer.parseInt(vueltas);
        
        if (this.probPrimeP.compareTo(Constantes.THREE) <= 0 || this.probPrimeQ.compareTo(Constantes.THREE) <= 0){
                this.print.primeLittleError();        
                return;
        }
        if (this.probPrimeP.mod(Constantes.TWO).equals(Constantes.ZERO) || this.probPrimeQ.mod(Constantes.TWO).equals(Constantes.ZERO)){
                this.print.multipleTwoError();          
                return;
                //imprimir por pantalla que el numero ha de ser un probable primo impar
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
    

    //Miller Rabin
    private boolean testPrimalityMillerRabin(BigInteger probPrime) {        
        //probPrime - 1 = (2^k) * m 
        int k = 0;
        BigInteger m;		
        BigInteger probPrimeMinusOne = probPrime.subtract(Constantes.ONE);

        int iterador;
        // aleatorio  tal que 2 >= a <= n-2
        //usado para comprobar si es primo o compuesto el numero a evaluar
        BigInteger a;

        // para comprobar si es compuesto o primo
        BigInteger x;		        
        

        // Calculate: probPrime - 1 = (2^k) * m 
        m = probPrimeMinusOne;
        do{
                k++;
                m = m.divide(Constantes.TWO);

        }while (m.mod(Constantes.TWO).equals(Constantes.ZERO));

        //Check primality
        for (int i = 0; i < this.vueltas; i++) {
                //2 >= a <= n-2
                a = uniformRandom(Constantes.TWO, probPrime.subtract(Constantes.TWO)); 

                //Step 1
                x = a.modPow(m, probPrime);
                if (x.equals(Constantes.ONE) || x.equals(probPrimeMinusOne)){
                        // primera ronda (iteracion del for) PASADA con exito
                        continue;
                }		
                for (iterador=1; iterador<k; iterador ++) {
                        x = x.modPow(Constantes.TWO, probPrime);

                        if (x.equals(Constantes.ONE)){
                                //log fallo en ronda tal con random tal
                                return false;
                        }
                        if (x.equals(probPrimeMinusOne)){
                                //pasado con exito
                                break;
                        }

                        iterador++;
                }

              //  if (iterador.equals(k) && !(x.equals(probPrimeMinusOne))){
               if (k==iterador && !(x.equals(probPrimeMinusOne))){
                        return false;
                }
        }
        return true;
    }



    //test de primalidad: Fermat	
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


    //Devuelve un BigInteger random entre los dos valores pasados por parametros (AMBOS INCLUSIVE)
    private BigInteger uniformRandom(BigInteger bottom, BigInteger top) {
        SecureRandom rnd = new SecureRandom();
        BigInteger random;
        do {
                random = new BigInteger(top.bitLength(), rnd);
        } while (random.compareTo(bottom) < 0 || random.compareTo(top) > 0);
        return random;
    }
    
    
    /**
     * 
     * @param radix 
     */
    public void setUnits( int radix){
        this.radix = radix;
    }
}
