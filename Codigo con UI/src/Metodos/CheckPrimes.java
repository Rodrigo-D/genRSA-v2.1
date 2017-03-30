/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Metodos;

import Imprimir.Print;
import Model.Constantes;
import genrsa.sceneController;
import java.math.BigInteger;
import java.security.SecureRandom;

/**
 *
 * @author rdiazarr
 */
public class CheckPrimes {
    
    BigInteger probPrime;
    Utilidades utilidades;
    int vueltas;
    //true si es P, false si es Q
    boolean isPrimeP;
    
    private final Print print;
    
    /**
     * Constructor de la clase
     * @param scene
     */
    public CheckPrimes(sceneController scene) {
        this.utilidades = new Utilidades();
        this.print = new Print(scene);
    }
    
    /**
     * 
     * @param probNumber
     * @param vueltas
     * @param isMiller
     * @param isPrimeP 
     */
    public void check(String probNumber, String vueltas, final boolean isMiller, final boolean isPrimeP){      
        boolean resultado;
        
        probNumber = this.utilidades.formatNumber(probNumber);
        vueltas = this.utilidades.formatNumber(vueltas);
        
        try {
            this.probPrime = new BigInteger (probNumber);
        } catch (NumberFormatException n){
            //imprimir mensaje diciendo que no es un número
            return;
        }
        
        if (!this.utilidades.isNumber(vueltas)){
           // imprimir un mensaje diciendo que no es un numero
           // hacerlo en tiempo real, no cuando se pulse el boton de miller o fermat
           // ,es decir, quitar este if 
            return;
        } 
        
        this.vueltas = Integer.parseInt(vueltas);
        
        if (this.probPrime.compareTo(Constantes.THREE) <= 0){
                this.print.isPrime(isPrimeP);
                return;
        }
        if (this.probPrime.mod(Constantes.TWO).equals(Constantes.ZERO)){
                this.print.isNotPrime(isPrimeP);
                //devolver que no es primo FALSE
                return;
                //imprimir por pantalla que el numero ha de ser un probable primo impar
        }
        
        
        
        if(isMiller){
            resultado = this.testPrimalityMillerRabin();
        } else {
            resultado = this.testPrimalityFermat();
        }
        
        if (resultado){
            this.print.isPrime(isPrimeP);
        } else{
            this.print.isNotPrime(isPrimeP);
        }
    }
    

    //Miller Rabin
    private boolean testPrimalityMillerRabin() {
        //probPrime - 1 = (2^k) * m 
        BigInteger k = Constantes.ZERO;
        BigInteger m;		
        BigInteger probPrimeMinusOne = this.probPrime.subtract(Constantes.ONE);

        BigInteger kMinusOne;
        BigInteger iterador = Constantes.ONE;
        // aleatorio  tal que 2 >= a <= n-2
        //usado para comprobar si es primo o compuesto el numero a evaluar
        BigInteger a;

        // para comprobar si es compuesto o primo
        BigInteger x;		        
        

        // Calculate: probPrime - 1 = (2^k) * m 
        m = this.probPrime.subtract(Constantes.ONE);
        do{
                k.add(Constantes.ONE);
                m = m.divide(Constantes.TWO);

        }while (m.mod(Constantes.TWO).equals(Constantes.ZERO));

        kMinusOne = k.subtract(Constantes.ONE);

        //Check primality
        for (int i = 0; i < this.vueltas; i++) {
                //2 >= a <= n-2
                a = uniformRandom(Constantes.TWO, this.probPrime.subtract(Constantes.TWO)); 

                //Step 1
                x = a.modPow(m, this.probPrime);
                if (x.equals(Constantes.ONE) || x.equals(probPrimeMinusOne)){
                        // primera ronda (iteracion del for) PASADA con exito
                        continue;
                }			

                while (iterador.compareTo(kMinusOne) < 1) {
                        x = x.modPow(Constantes.TWO, this.probPrime);

                        if (x.equals(Constantes.ONE)){
                                //Mensaje diciendo en que ronda falla
                                return false;
                        }
                        if (x.equals(probPrimeMinusOne)){
                                //pasado con exito
                                break;
                        }

                        iterador = iterador.add(Constantes.ONE);
                }

                if (iterador.equals(k) && !(x.equals(probPrimeMinusOne))){
                        return false;
                }
        }
        return true;
    }



    //test de primalidad: Fermat	
    private boolean testPrimalityFermat() {
        BigInteger a;
        BigInteger probPrimeMinusTwo;
        BigInteger probPrimeMinusOne;
        

        probPrimeMinusTwo=this.probPrime.subtract(Constantes.TWO);
        probPrimeMinusOne=this.probPrime.subtract(Constantes.ONE);		

        for(int i=0;i<this.vueltas;i++){
                // entre 2 y probPrime-2 puesto que 1 y probPrime-1 eleveado a probPrime-1 
                // siempre da de resultado 1 en mod probPrime.
                a = uniformRandom(Constantes.TWO, probPrimeMinusTwo); 
                // a^(probPrime-1) mod probPrime
                a = a.modPow(probPrimeMinusOne,this.probPrime);

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
    
}
