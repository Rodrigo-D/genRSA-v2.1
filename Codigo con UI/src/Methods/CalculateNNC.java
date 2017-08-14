/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Methods;

import Imprimir.LogNNC;
import Model.ComponentesRSA;
import Model.Constantes;
import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.application.Platform;
import javafx.scene.control.ProgressIndicator;

/**
 *
 * @author rdiazarr
 */
public class CalculateNNC {

    public static boolean isCancelled;
    
    private final ComponentesRSA RSA;   
    
    private final LogNNC log;
    //decimal = 10, hexadecimal = 16
    private final int radix;
    
    private final ProgressIndicator progressInd;
        
    private double progress;
        
    //Array donde se almacenan los NNC del cuerpo de cifra. Para el metodo de calculate
    List <BigInteger> listNNC = new ArrayList <>();
    //Array donde se copia listNNC para que no haya problemas con la escritura en el thread. Para el metodo de calculate
    List <BigInteger> copyListNNC;
    
    
    
    
    /**
     * Constructor de la clase
     * @param radix
     * @param RSA
     * @param logFile 
     * @param progressInd
     */
    public CalculateNNC(int radix, ComponentesRSA RSA, File logFile, ProgressIndicator progressInd){
        this.log = new LogNNC(logFile); 
        this.radix = radix;
        this.RSA = RSA;        
        CalculateNNC.isCancelled = false;
        this.progressInd = progressInd;
        this.progress = 0d;
    }    
    
    
    
    /**
     * Metodo para calcular los Numeros No Cifrables, cuando la cantidad de NNC no supera los 10.000.000
     * y los primos p y q son distintos entre si
     * http://www.criptored.upm.es/crypt4you/temas/RSA/leccion5/leccion05.html
     * @throws java.lang.InterruptedException
     */
    public void quickCalculate() throws InterruptedException {
        BigInteger number;
        BigInteger inv_pq,inv_qp, p_invpq, q_invqp;
        //valor maximo alcanzado al guardar valores en la lista de numNp y de numNq
        int numNp, numNq;
        //llevan el valor de la posicion del arraylist de numNp y numNq
        int iteradorP, iteradorQ;         
                                                
        //arrays donde se almacenan los num no cifrables en P y en Q. Y los NNC en el cuerpo de cifra
        List <BigInteger> listNp = new ArrayList <>();
        List <BigInteger> listNq = new ArrayList <>();
        List <BigInteger> quickListNNC = new ArrayList <>();                 
        
                       
        inv_pq= this.RSA.getP().modInverse(this.RSA.getQ());
        inv_qp= this.RSA.getQ().modInverse(this.RSA.getP());

        p_invpq = this.RSA.getP().multiply(inv_pq);
        q_invqp = this.RSA.getQ().multiply(inv_qp);
        
        Platform.runLater(() -> this.log.createHTML(this.RSA, this.radix));

        //Obtener los NNC de p 
        numNp = this.calculatePQ_NNC(listNp, this.RSA.getpMinusOne(), this.RSA.getP(), 0.0d, Constantes.ZERO);        
        if (isCancelled){
            Platform.runLater(() ->{
                this.log.cancelledHTML();
                this.log.closeHTML();
            });
            return;
        }
        
        // Obtener los NNC de q         
        numNq = this.calculatePQ_NNC(listNq, this.RSA.getqMinusOne(), this.RSA.getQ(), 0.45d, this.RSA.getpMinusOne());        
        if (isCancelled){
            Platform.runLater(() ->{
                this.log.cancelledHTML();
                this.log.closeHTML();
            });
            return;
        }

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
                    quickListNNC.add(number);
            }//2do for
            
            this.progress = 0.9d + ((iteradorQ * 0.8)/numNq );
            Platform.runLater(() -> this.progressInd.setProgress(progress));
            
            if (isCancelled){
                break;
            }                
        }//1er for
        
        
        //ordeno la lista
        Collections.sort(quickListNNC);
        
        Thread.sleep(150);
        
        Platform.runLater(() ->{    
            this.log.WriteList(quickListNNC, this.radix);  
            if (isCancelled){
                this.log.cancelledHTML();
            }
            this.log.closeHTML();
        });
        
        
    }
    
    /**
     * Método para calcular los numeros no cifrables en el espacio de p o de q
     * @param array
     * @param P_Q_minusOne
     * @param P_Q 
     * @param startProgress
     */
    private int calculatePQ_NNC (List <BigInteger> list, BigInteger P_Q_minusOne,
            BigInteger P_Q, double startProgress, BigInteger partial ) throws InterruptedException{
        
        int position;       
        BigInteger possibleNNC = Constantes.ONE;
        BigInteger result;
        
        BigInteger BI_90 = new BigInteger("90");
        BigInteger partialTotal = P_Q_minusOne.add(partial);
        
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
            
            if(possibleNNC.mod(Constantes.BLN_REFRESH).compareTo(Constantes.ZERO) == 0){
                progress = startProgress + (Double.parseDouble(((((possibleNNC.add(partial)).multiply(BI_90))).divide(partialTotal)).toString())/100d);
                Platform.runLater(() -> this.progressInd.setProgress(progress));
            }
        } while (possibleNNC.compareTo(P_Q_minusOne)!=0 && !isCancelled);
        
        Thread.sleep(150);
        
        return position;
    }
    
  
     /**
     * Metodo para calcular los Numeros No Cifrables, cuando la cantidad de NNC supera los 10.000.000
     * o los primos p y q no son distintos entre si     * 
     */
    public void calculate() {
        BigInteger possibleNNC = Constantes.ONE;
        BigInteger result;
        //Al guardarlos en estas variables no tengo que estar accediendo a this.RSA por cada iteracion del while
        BigInteger pubKey = this.RSA.getE();
        BigInteger modulus = this.RSA.getN();
        BigInteger modulusMinusOne = this.RSA.getN().subtract(Constantes.ONE);
        
        //Variable que hará que no se haga muy grande la lista ya que cuando partialNumNNC llegue al refresh200K, la lista se escribirá
        final int refresh200K = 200000;
        int partialNumNNC = 1;
        
        
        Platform.runLater(() -> this.log.createHTML(this.RSA, this.radix));
        
        //	x^e mod n = x con 1 ≤ x ≤ n    
        listNNC.add(Constantes.ZERO);
        listNNC.add(Constantes.ONE);
        do {
            possibleNNC = possibleNNC.add(Constantes.ONE);

            result = possibleNNC.modPow(pubKey, modulus);

            if (result.compareTo(possibleNNC) == 0) {                
                listNNC.add(result);
                partialNumNNC++;
            }
            
            if (partialNumNNC == refresh200K){
                copyListNNC = listNNC; 
                Platform.runLater(() -> this.log.WriteList(copyListNNC, this.radix));
                partialNumNNC=0;
                listNNC = new ArrayList <>();                
            }       
            
            
            if(possibleNNC.mod(Constantes.BLN_REFRESH).compareTo(Constantes.ZERO) == 0){
                progress = Double.parseDouble(((possibleNNC).divide(modulusMinusOne)).toString());
                Platform.runLater(() -> this.progressInd.setProgress(progress));
            }
            
        } while (possibleNNC.compareTo(modulusMinusOne) != 0 && !isCancelled);
        
        if (isCancelled){
            Platform.runLater(() -> this.log.cancelledHTML());
        }
         
        Platform.runLater(() -> {
            this.log.WriteList(listNNC, this.radix);        
            this.log.closeHTML();      
        });
    }
        
}