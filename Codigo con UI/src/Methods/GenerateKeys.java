/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Methods;

import Imprimir.ErrorDialog;
import Imprimir.GenRSAPrint;
import Model.ComponentesRSA;
import Model.Constantes;
import genrsa.GenRSAController;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.scene.control.TextField;

/**
 *
 * @author rdiazarr
 */
public class GenerateKeys {
    
    public static boolean isCancelled;
    
    private final ErrorDialog errorDialog;
    
    private final ComponentesRSA RSA;   
    
    private final Utilities utilidades;
    
    private final GenRSAPrint print;
    //decimal =10, hexadecimal =16
    private int radix;
    
    // atributo que almacena el tiempo inicial 
    private long startTime;
    // atributo que almacena el tiempo empleado 
    private String time;
    // atributo que almacena las claves privadas parejas
    private List<String> listCPP;
    
    /**
     * Constructor de la clase
     * @param scene 
     */
    public GenerateKeys (GenRSAController scene){
        this.RSA = new ComponentesRSA();
        this.utilidades = new Utilities();
        this.print = new GenRSAPrint(scene);
        this.radix = 10;
        this.errorDialog = new ErrorDialog();
        GenerateKeys.isCancelled = false;
    }
    
    /**
     * manual generation of RSAKeys
     * 
     * @param primeP
     * @param primeQ
     * @param pubKey
     * @return 
     */
     public ComponentesRSA manualRSAkeys(String primeP, String primeQ, String pubKey) {
        this.startTime = System.currentTimeMillis();
         
        primeP = this.utilidades.formatNumber(primeP);
        primeQ = this.utilidades.formatNumber(primeQ);                
        pubKey = this.utilidades.formatNumber(pubKey);
        
        //con que uno de los tres no sea valido se visualiza una pantalla de error
        /* Step 1: Get the prime numbers (p and q) and the public key */
        try {
            this.RSA.setP ( new BigInteger (primeP, this.radix));
            this.RSA.setQ ( new BigInteger (primeQ, this.radix));    
            this.RSA.setE ( new BigInteger (pubKey, this.radix));
            
        } catch (NumberFormatException n){
            
            this.errorDialog.componentConversion(this.radix);
            this.print.flushNotManual();
            return null;
        }
        
        //se comprueba que p y q no sean menores que 3
        if (this.RSA.getP().compareTo(Constantes.THREE) <= 0 || this.RSA.getQ().compareTo(Constantes.THREE) <= 0){
            
            this.errorDialog.primeLittle();  
            this.print.flushNotManual();
            return null;
        }
        
        // se comprueba que p y q no sean pares
        if (this.RSA.getP().mod(Constantes.TWO).equals(Constantes.ZERO) || this.RSA.getQ().mod(Constantes.TWO).equals(Constantes.ZERO)){
            
            this.errorDialog.multipleTwoGeneration();    
            this.print.flushNotManual();
            return null;
        }
        
        // se comprueba que p y q no sean iguales
        if (this.RSA.getP().equals(this.RSA.getQ())){
            
            this.errorDialog.PQEquals();
            this.print.flushNotManual();
            return null;
        }
        
        // se comprueba que e sea mayor que 1
        if (this.RSA.getE().compareTo(BigInteger.ONE) < 1){
            
            this.errorDialog.pubKeyEqualOne();
            this.print.flushNotManual();
            return null;
        }       
        
        
        /* Step 2:  n = p.q */
        this.RSA.setN( this.RSA.getP().multiply(this.RSA.getQ()));
        
        /* Step 3: phi N = (p - 1).(q - 1) */
        this.RSA.setpMinusOne( this.RSA.getP().subtract(Constantes.ONE));
        this.RSA.setqMinusOne( this.RSA.getQ().subtract(Constantes.ONE));
        this.RSA.setPhiN( this.RSA.getpMinusOne().multiply(this.RSA.getqMinusOne()));

        /* Step 4: Check e, gcd(e, ø(n)) = 1 ; 1 < e < ø(n) */
        // compareTo da 1 si es mayor que el valor entre parentesis
        if ((this.RSA.getE().compareTo(this.RSA.getPhiN()) > -1) || 
                 (this.RSA.getE().gcd(this.RSA.getPhiN()).compareTo(Constantes.ONE)) != 0){
            
            this.errorDialog.invalidPubKey();
            this.print.flushNotManual();
            return null;
        }

        /* Step 5: Calculate d such that e.d = 1 (mod ø(n)) */
        this.RSA.setD( this.RSA.getE().modInverse(this.RSA.getPhiN()));
        
                
        this.time = this.utilidades.millisToSeconds(System.currentTimeMillis()  - this.startTime);
        
        this.print.rsaGeneration(this.RSA, this.time, this.radix);          
        
        this.calculateCKP();
        this.calculateNumNNC();
        
        return this.RSA;        
    }
    
    
    
    /**
     * Automatic generation of RSA KEYS, with the same or different number of bits in prime P and Q
     * @param KeySize
     * @param sameSizePrimes
     * @param tipicalPubKey
     * @param securePrimes
     * @return 
     */
    public ComponentesRSA autoRSAkeys(String KeySize, boolean sameSizePrimes, boolean tipicalPubKey, boolean securePrimes) {        
        this.startTime = System.currentTimeMillis();
        int distanceBits;
        final String keySize;
        
        keySize = this.utilidades.formatNumber(KeySize);
        
        //se comprueba que el keySize introducido sea un número
        if (!this.utilidades.isNumber(keySize)){
            Platform.runLater(() ->this.errorDialog.keySize()); 
            return null;
        } 
        
        this.RSA.setKeySize(Integer.parseInt(keySize));
            
        if (this.RSA.getKeySize() < 6){ 
            Platform.runLater(() ->this.errorDialog.littleKeySize()); 
            return null;
        }      
        
        if (this.RSA.getKeySize() > 8192){ 
            Platform.runLater(() ->this.errorDialog.bigKeySize()); 
            return null;
        }
        
        distanceBits = this.calculateDistanceBits(sameSizePrimes);
        
        if (tipicalPubKey){
            
            if(this.RSA.getKeySize()>18){
                this.createRSAKeysWithTipicalPubKey(distanceBits, sameSizePrimes, securePrimes);
            } else {
                Platform.runLater(() ->this.errorDialog.keySizeTipicalPubKey(this.radix));
                return null;
            }
            
        } else {
            if (securePrimes){
                
                if(this.RSA.getKeySize()>7){
                    this.createRSAKeysSecure(distanceBits, sameSizePrimes);
                } else {
                    Platform.runLater(() ->this.errorDialog.keySizeSecurePrime());
                    return null;
                }    
            } else {
                this.createRSAKeys(distanceBits, sameSizePrimes);        

            }
        }
        
        if (!GenerateKeys.isCancelled) {
            this.time = this.utilidades.millisToSeconds(System.currentTimeMillis()  - this.startTime);        

            Platform.runLater(() ->{
                this.print.rsaGeneration(this.RSA, this.time, this.radix);
                this.print.autoBitsKey(keySize);
            });

            this.calculateCKP();
            this.calculateNumNNC();
            GenerateKeys.isCancelled=false;
            return this.RSA;
            
        } else {            
            GenerateKeys.isCancelled=false;
            return null;
        }        
    }
    
    /**
     * Método para generar de manera automatica las claves RSA con una distancia de bits
     * entre p y q igual a distanceBits. La clave pública será el valor más bajo posible.
     * Y se usarán primos seguros. 
     * @param keySize
     * @param sameSizePrimes
     */
    private boolean createRSAKeysSecure(int distanceBits, final boolean sameSizePrimes) {
  
        /* Step 1: Select the prime numbers (p and q) */
        do {
            this.RSA.setP( BigInteger.probablePrime((this.RSA.getKeySize()/2)+distanceBits, new SecureRandom()));
        } while (!(this.RSA.getP().divide(Constantes.TWO)).isProbablePrime(150) && !GenerateKeys.isCancelled);
        
        if (GenerateKeys.isCancelled){
                return false;
        }

        do {
            this.RSA.setQ( BigInteger.probablePrime((this.RSA.getKeySize()/2)-distanceBits, new SecureRandom()));
        } while (!(this.RSA.getQ().divide(Constantes.TWO)).isProbablePrime(150) && !GenerateKeys.isCancelled);
        
        if (GenerateKeys.isCancelled){
                return false;
        }
            
        /* Step 2:  n = p.q */
        this.RSA.setN( this.RSA.getP().multiply(this.RSA.getQ()));
        
        if (!sameSizePrimes || (this.RSA.getKeySize() % 2) == 1){
            distanceBits--;
        }        
        
        //se comprueba que n sea de la longitud pedida y que p sea distinto de q
        while ((this.RSA.getN().bitLength() != this.RSA.getKeySize() || this.RSA.getP().equals(this.RSA.getQ())) && !GenerateKeys.isCancelled){
            
            do {
                this.RSA.setQ( BigInteger.probablePrime((this.RSA.getKeySize()/2)-(distanceBits), new SecureRandom()));
            } while (!(this.RSA.getQ().divide(Constantes.TWO)).isProbablePrime(150) && !GenerateKeys.isCancelled);
                        
            this.RSA.setN(this.RSA.getP().multiply(this.RSA.getQ()));
        }
        
        if (GenerateKeys.isCancelled){
            return false;
        }

        /* Step 3: phi N = (p - 1).(q - 1) */
        this.RSA.setpMinusOne( this.RSA.getP().subtract(Constantes.ONE));
        this.RSA.setqMinusOne( this.RSA.getQ().subtract(Constantes.ONE));
        this.RSA.setPhiN( this.RSA.getpMinusOne().multiply(this.RSA.getqMinusOne()));

        /* Step 4: Find e, gcd(e, ø(n)) = 1 ; 1 < e < ø(n) */
        this.RSA.setE(Constantes.ONE);
        do {
            this.RSA.setE( this.RSA.getE().add(Constantes.TWO));
                // compareTo da 1 si es mayor que el valor entre parentesis
        } while ((this.RSA.getE().compareTo(this.RSA.getPhiN()) > -1) || 
                 (this.RSA.getE().gcd(this.RSA.getPhiN()).compareTo(Constantes.ONE)) != 0);
        
        /* Step 5: Calculate d such that e.d = 1 (mod ø(n)) */
        this.RSA.setD( this.RSA.getE().modInverse(this.RSA.getPhiN()));
        
        return false;
        
    }
    
    
     /**
     * Método para generar de manera automatica las claves RSA con una distancia de bits
     * entre p y q igual a distanceBits. La clave pública será el valor más bajo posible.
	 * Con primos al azar, no tienen porque ser seguros.
     * @param keySize
     * @param sameSizePrimes
     */
    private boolean createRSAKeys(int distanceBits, final boolean sameSizePrimes) {
        /* Step 1: Select the prime numbers (p and q) */
        this.RSA.setP( BigInteger.probablePrime((this.RSA.getKeySize()/2)+distanceBits, new SecureRandom()));
        
        if (GenerateKeys.isCancelled){
            return false;
        }
        
        this.RSA.setQ( BigInteger.probablePrime((this.RSA.getKeySize()/2)-distanceBits, new SecureRandom()));
        
        if (GenerateKeys.isCancelled){
            return false;
        }
      
        /* Step 2:  n = p.q */
        this.RSA.setN( this.RSA.getP().multiply(this.RSA.getQ()));
        
        if (!sameSizePrimes || (this.RSA.getKeySize() % 2) == 1){
            distanceBits--;
        }        
        
        //se comprueba que n sea de la longitud pedida y que p sea distinto de q
        while ((this.RSA.getN().bitLength() != this.RSA.getKeySize() || this.RSA.getP().equals(this.RSA.getQ())) && !GenerateKeys.isCancelled){
            this.RSA.setQ( BigInteger.probablePrime((this.RSA.getKeySize()/2)-(distanceBits), new SecureRandom()));
            this.RSA.setN( this.RSA.getP().multiply(this.RSA.getQ()));
        }
        
        if (GenerateKeys.isCancelled){
            return false;
        }

        /* Step 3: phi N = (p - 1).(q - 1) */
        this.RSA.setpMinusOne( this.RSA.getP().subtract(Constantes.ONE));
        this.RSA.setqMinusOne( this.RSA.getQ().subtract(Constantes.ONE));
        this.RSA.setPhiN( this.RSA.getpMinusOne().multiply(this.RSA.getqMinusOne()));

        /* Step 4: Find e, gcd(e, ø(n)) = 1 ; 1 < e < ø(n) */
        this.RSA.setE(Constantes.ONE);
        do {
                this.RSA.setE( this.RSA.getE().add(Constantes.TWO));
                // compareTo da 1 si es mayor que el valor entre parentesis
        } while ((this.RSA.getE().compareTo(this.RSA.getPhiN()) > -1) || 
                 (this.RSA.getE().gcd(this.RSA.getPhiN()).compareTo(Constantes.ONE)) != 0);

        /* Step 5: Calculate d such that e.d = 1 (mod ø(n)) */
        this.RSA.setD( this.RSA.getE().modInverse(this.RSA.getPhiN()));
        
        return false;
    }
    
    
    
    /**
     * Método para generar de manera automatica las claves RSA con la clave publica = 65.537 y
     * una distancia entre p y q igual a distanceBits.
     * @param keySize
     * @param sameSizePrimes
     * @param securePrimes
     */
    private boolean createRSAKeysWithTipicalPubKey(int distanceBits, final boolean sameSizePrimes, boolean securePrimes) {
        
        /* Step 1: Set e=65537 */
        this.RSA.setE(new BigInteger("65537"));
        
        /* Step 2: Select the prime numbers (p and q) */        
        if (securePrimes){
            
            do {
                this.RSA.setP( BigInteger.probablePrime((this.RSA.getKeySize()/2)+distanceBits, new SecureRandom()));
            } while (!(this.RSA.getP().divide(Constantes.TWO)).isProbablePrime(150) && !GenerateKeys.isCancelled);
            
            if (GenerateKeys.isCancelled){
                return false;
            }            
            
            do {
                this.RSA.setQ( BigInteger.probablePrime((this.RSA.getKeySize()/2)-distanceBits, new SecureRandom()));
            } while (!(this.RSA.getQ().divide(Constantes.TWO)).isProbablePrime(150) && !GenerateKeys.isCancelled);
            
            if (GenerateKeys.isCancelled){
                return false;
            }
                        
        } else {
            this.RSA.setP( BigInteger.probablePrime((this.RSA.getKeySize()/2)+distanceBits, new SecureRandom()));
            
            if (GenerateKeys.isCancelled){
                return false;
            }
            this.RSA.setQ( BigInteger.probablePrime((this.RSA.getKeySize()/2)-distanceBits, new SecureRandom()));
            
            if (GenerateKeys.isCancelled){
                return false;
            }
        }
        
        
        /* Step 3: phi N = (p - 1).(q - 1) */
        this.RSA.setpMinusOne( this.RSA.getP().subtract(Constantes.ONE));
        this.RSA.setqMinusOne( this.RSA.getQ().subtract(Constantes.ONE));
        this.RSA.setPhiN( this.RSA.getpMinusOne().multiply(this.RSA.getqMinusOne()));
        /* Step 5:  n = p.q ;; se realiza antes para poder comparar*/
        this.RSA.setN( this.RSA.getP().multiply(this.RSA.getQ()));
        
        if (!sameSizePrimes || (this.RSA.getKeySize() % 2) == 1){
            distanceBits--;
        } 

        /* Step 4: Find q, such tah gcd(e, ø(n)) = 1 and 1 < e < ø(n) and p!=q and*/
        while (((this.RSA.getE().compareTo(this.RSA.getPhiN()) > -1) || 
                 (this.RSA.getE().gcd(this.RSA.getPhiN()).compareTo(Constantes.ONE)) != 0 ||
                    (this.RSA.getP().equals(this.RSA.getQ())) ||
                        (this.RSA.getN().bitLength() != this.RSA.getKeySize())) && !GenerateKeys.isCancelled){
                        
            if (securePrimes){
                do {
                    this.RSA.setQ( BigInteger.probablePrime((this.RSA.getKeySize()/2)-(distanceBits), new SecureRandom()));
                } while (!(this.RSA.getQ().divide(Constantes.TWO)).isProbablePrime(150) && !GenerateKeys.isCancelled);
                        
            } else {               
                this.RSA.setQ( BigInteger.probablePrime((this.RSA.getKeySize()/2)-(distanceBits), new SecureRandom()));
            }
            
            this.RSA.setN( this.RSA.getP().multiply(this.RSA.getQ()));            
            
            this.RSA.setqMinusOne( this.RSA.getQ().subtract(Constantes.ONE));
            this.RSA.setPhiN( this.RSA.getpMinusOne().multiply(this.RSA.getqMinusOne()));            
        }     
        
        if (GenerateKeys.isCancelled){
                return false;
        }
        
        /* Step 6: Calculate d such that e.d = 1 (mod ø(n)) */
        this.RSA.setD( this.RSA.getE().modInverse(this.RSA.getPhiN()));
        
        return false;
    }
    
    
    
    
    /**
     * Metodo que calcula las claves privada parejas
     */
    public void calculateCKP(){
        //almacena la clave privada pareja
        BigInteger cpp;
        int iterador=1;
        int CKP_int;
        listCPP = new ArrayList<>();

        //minimo comun multiplo a través del mcd-gcd
        this.RSA.setGamma(this.RSA.getpMinusOne().multiply
                           (this.RSA.getqMinusOne().divide
                          (this.RSA.getpMinusOne().gcd(this.RSA.getqMinusOne()))));	 	

        cpp = this.RSA.getE().modInverse(this.RSA.getGamma());

        this.RSA.setNumCKP( ((this.RSA.getN().subtract(cpp)).divide(this.RSA.getGamma())) );

        //Imprime           
        Platform.runLater(() ->{
            this.print.numClavesParejas(this.RSA.getNumCKP());
            this.print.clearPrivPairKey();
        });

        if (cpp.compareTo(this.RSA.getD()) != 0){
            listCPP.add(this.utilidades.putPoints(cpp.toString(this.radix).toUpperCase(), this.radix) + " -> " + cpp.bitLength() + " bits");
        }

        //para controlar el while, dado que si el numero es mayor que el max_value de los integer
        //podria llegar a ser un numero negativo y no se calcularian las CKP
        CKP_int = this.CKPtoInt();
        
        
        //OJO, he añadido condicion para que pare a las 300
        while (CKP_int >= iterador && iterador <= 300){
            
            cpp=cpp.add(this.RSA.getGamma());
            if (cpp.compareTo(this.RSA.getD()) != 0){
                    listCPP.add(this.utilidades.putPoints(cpp.toString(this.radix).toUpperCase(), this.radix) + " -> " + cpp.bitLength() + " bits");
            }
            iterador++;
        }

        Platform.runLater(() ->this.print.privPairKey(listCPP));
        if (iterador > 300){
            Platform.runLater(() ->this.print.limitPrivPairKey());
        }
    }
    
    
    /**
     * Calcula la cantidad de numeros no cifrables
     */
    private void calculateNumNNC( ){
        BigInteger eMinusOne,part1, part2;

        eMinusOne= this.RSA.getE().subtract(Constantes.ONE);

        part1 = (Constantes.ONE.add((eMinusOne.gcd(this.RSA.getpMinusOne()))));
        part2 = (Constantes.ONE.add((eMinusOne.gcd(this.RSA.getqMinusOne()))));
        this.RSA.setNumNNC( part1.multiply(part2));
        
        Platform.runLater(() ->this.print.numNNC(this.RSA.getNumNNC()));
    }

    
    
    
    /**
     * Método usado para que no desborde un int al pasarle un bigInteger
     * @return 
     */
    private int CKPtoInt() {
        int numCKP = Constantes.MAX_INT;
        
        //si no sobrepasa el maximo
        if (this.RSA.getNumCKP().compareTo(Constantes.MAX_INT_BI)<1){
            numCKP = this.RSA.getNumCKP().intValue();
        } 
        
        return numCKP;
    }
    
    
    /**
     * Metodo para calcular la diferencia de bits en
     * el caso de que no se quieran p y q del mismo tamaño
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
     * Método para calcular los bits de los numeros que se van introduciendo para la clave manual.
     * @param number
     * @param bits
     */
    public void numberToBits(String number, TextField bits) {
        number = this.utilidades.formatNumber(number);
        
        BigInteger num;
        
        try {
            num = new BigInteger (number, this.radix);
            bits.setText(this.utilidades.countBits(num));
            
        } catch (NumberFormatException n){
            bits.setText("0");
        }
    }
    
    /**
     * Método para parar la generación de la clave
     */
    public void setGenKeyCancelled() {
        GenerateKeys.isCancelled = true;
    }
    
    /**
     * 
     * @param radix 
     */
    public void setUnits( int radix){
        this.radix = radix;
    }

    
}