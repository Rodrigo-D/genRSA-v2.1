/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Metodos;

import Imprimir.LogNNC;
import Model.ComponentesRSA;
import Model.Constantes;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author rdiazarr
 */
public class CalculateNNC {
    
    private ComponentesRSA RSA;   
    
    private LogNNC log;
    //decimal =10, hexadecimal =16
    private int radix;
    
    public CalculateNNC(){
        this.RSA = null;
        this.log = null;
        this.radix = 10;
    }
    
    //comprueba que se haya creado una clave y su num de NNC
    public void calculateNNC() {
        //quiza añadir variable tipo isCreated en componentes
        try{
            this.RSA.getNumNNC(); //devuelve la excepción si no se ha creado una clave
            this.log = new LogNNC();
            calculate();
        } catch (NullPointerException e){
            //de momento asi hasta que sepa imprimir un cuadrado por pantalla dando el error
        }
    }  
    
    /**
    * Metodo para calcular los Numeros No Cifrables
    */
    //http://www.criptored.upm.es/crypt4you/temas/RSA/leccion5/leccion05.html
    public void calculate() {  
        BigInteger number;
        BigInteger inv_pq,inv_qp, p_invpq, q_invqp;
        //numNp y numNq llevan el valor de la posicion del array
        int numNp, numNq, iteradorP, iteradorQ; //ojito  cuidado que es un int y puede llegar a crecer una barbaridad
                                                // se controla con el if de abajo
                                     //si se cambiase ojito con todos los int de este metodo y metodos llamados desde aqui

        
        //Preguntar cual queremos que sea el maximo de nnc a imprimir
        //Preguntar/calclar por el keySize maximo con el q se calcularan los nnc
        if (this.nncGreaterThanMAX() || this.keySizeGreaterThanMAX()){
           this.log.writeErrorHTML(this.RSA.getNumNNC());
           this.log.closeHTML();
           return;
        } 
       
        //arrays donde se almacenan los num no cifrables  en p y en q. Y los NNC en el cuerpo de cifra
        List <BigInteger> listNp = new ArrayList <>();
        List <BigInteger> listNq = new ArrayList <>();
        List <BigInteger> listNNC = new ArrayList <>();                 
        
        
        //comprobar en la clave que p y q no sean el mismo numero
        inv_pq= this.RSA.getP().modInverse(this.RSA.getQ());
        inv_qp= this.RSA.getQ().modInverse(this.RSA.getP());

        p_invpq = this.RSA.getP().multiply(inv_pq);
        q_invqp = this.RSA.getQ().multiply(inv_qp);
        
        this.log.createHTML(this.RSA, this.radix);

        //Obtener los NNC de p 
        numNp = this.calculatePQ_NNC(listNp, this.RSA.getpMinusOne(), this.RSA.getP());
        
        // Obtener los NNC de q         
        numNq = this.calculatePQ_NNC(listNq, this.RSA.getqMinusOne(), this.RSA.getQ());        

        //----> NUMEROS NO CIFRABLES de N<-----
        //recorro el array de los NNC de P
        for (iteradorP=0; iteradorP<=numNp; iteradorP++){
                listNp.set(iteradorP, listNp.get(iteradorP).multiply(q_invqp));
                //por cada NNC de p hago una operación con los NNC de Q
                for (iteradorQ=0; iteradorQ<=numNq; iteradorQ++){
                        if (iteradorP==0){
                            listNq.set(iteradorQ, listNq.get(iteradorQ).multiply(p_invpq));
                           
                        }
                        
                        number=(listNp.get(iteradorP).add(listNq.get(iteradorQ))).mod(this.RSA.getN());
                        listNNC.add(number);
                }
        }
        //ordeno la lista
        Collections.sort(listNNC);
        
        this.log.WriteList(listNNC, this.radix);        
        this.log.closeHTML();
    }
    
    /**
     * 
     * @param array
     * @param P_Q_minusOne
     * @param P_Q 
     */
    private int calculatePQ_NNC (List <BigInteger> list, BigInteger P_Q_minusOne, BigInteger P_Q ){
        int position;
        BigInteger possibleNNC = Constantes.ONE;
        BigInteger result;
        
        //	x^e mod p = x con 1 ≤ x ≤ p-1
        //      x^e mod q = x con 1 ≤ x ≤ q-1        
        list.add(Constantes.ZERO);
        list.add(Constantes.ONE);
        position = 1;
        do{
                possibleNNC=possibleNNC.add(Constantes.ONE);

                result = possibleNNC.modPow(this.RSA.getE(), P_Q);

                if (result.compareTo(possibleNNC)==0){
                        position++;
                        list.add(result);                                                
                }			
        } while (possibleNNC.compareTo(P_Q_minusOne)!=0);
        
        return position;
    }
    
    public boolean nncGreaterThanMAX (){
        
       return (this.RSA.getNumNNC().compareTo(Constantes.MAX_NNC))==1;        
    }

    private boolean keySizeGreaterThanMAX() {
        return (this.RSA.getKeySize()>Constantes.MAX_KeySize);
    }
    
    /**
     * @param radix 
     */
    public void setUnits( int radix){
        this.radix = radix;
    }
    
    public void setRSA( ComponentesRSA RSA){
        this.RSA = RSA;
    }
    
}