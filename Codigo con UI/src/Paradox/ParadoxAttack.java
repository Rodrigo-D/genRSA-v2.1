/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Paradox;

import Imprimir.ErrorDialog;
import Imprimir.InfoDialog;
import Imprimir.ParadoxPrint;
import Methods.Utilities;
import Model.Constantes;
import java.math.BigInteger;
import javafx.application.Platform;

/**
 *
 * @author rdiazarr
 */
public class ParadoxAttack {    
    
    private final ErrorDialog errorDialog;
    
    private final InfoDialog infoDialog;
    
    private final Utilities utilidades;
    
    private final ParadoxPrint Pprint;
    
    
        
    private int radix;
    
    private BigInteger modulus;
    
    private BigInteger exponent;
    
    private BigInteger message;
    
    private BigInteger i, j, initialI, initialJ;
           
    private String result;
    
    private String partialResult;
            
    private BigInteger avgStats;
    
    public static boolean isCancelled;

    /**
     * Constructor de la clase
     * @param paradoxPrint 
     */
    public ParadoxAttack(ParadoxPrint paradoxPrint) {
        this.errorDialog = new ErrorDialog();
        this.infoDialog = new InfoDialog();
        this.utilidades = new Utilities();
        this.Pprint = paradoxPrint;
        this.radix = 10;
        ParadoxAttack.isCancelled = false;
        
    }
    
    /**
     * Método que inicializa variables, comprueba errores y 
     * permite continuar o muestra un mensaje de error.
     * @param message
     * @param modulus
     * @param exponent
     * @return 
     */
    public boolean init(String message, String modulus, String exponent) {       
        //MODULUS------------  
        modulus = this.utilidades.formatNumber(modulus);
        
        try{
            this.modulus = new BigInteger(modulus, this.radix);
        } catch (NumberFormatException n){            
            Platform.runLater(() -> this.errorDialog.Modulus(this.radix));
            return false;
        }
        
        //EXPONENT------------  
        exponent = this.utilidades.formatNumber(exponent);
        
        try{
            this.exponent = new BigInteger(exponent, this.radix);
        } catch (NumberFormatException n){            
            Platform.runLater(() -> this.errorDialog.Exponent(this.radix));
            return false;
        }
        
        if (this.exponent.compareTo(this.modulus)>-1){
            Platform.runLater(() -> this.errorDialog.bigExponent());
            return false;
        }     
                
        //MESSAGE------------        
        message = this.utilidades.formatNumber(message);
        
        try{
            this.message = new BigInteger(message, this.radix);
        } catch (NumberFormatException n){            
            Platform.runLater(() -> this.errorDialog.paradoxMessage(this.radix));
            return false;
        }
        
        if (this.message.compareTo(Constantes.ONE) == -1){
            Platform.runLater(() -> this.errorDialog.paradoxMessage(radix));
            return false;
        }
        
        if (this.message.compareTo(this.modulus) > -1){
            Platform.runLater(() -> this.errorDialog.bigMessage(radix));
            return false;
        }
        
        //CHECK MESSAGE != NNC
        if (this.message.modPow(this.exponent, this.modulus).equals(this.message)){
            Platform.runLater(() -> this.Pprint.NNCmessage(radix));
            return false;
        }
        
        //PARTE GRAFICA-------------
        Platform.runLater(() ->{
           this.Pprint.numbers(this.modulus.toString(this.radix).toUpperCase(),
                                                    this.exponent.toString(this.radix).toUpperCase(),
                                                    this.message.toString(this.radix).toUpperCase(),
                                                    this.radix);
           this.Pprint.enableStop();
           this.Pprint.editableModExp(false);
           this.Pprint.partialClear();
           this.Pprint.estimation(Constantes.THREE.multiply(this.sqrt(this.modulus)).toString());
        });
        
        return true;        
    }
    
    
    /**
     * Método para decidir que tipo de visualización de los resultados se
     * lleva a cabo para el ataque completo. 
     * No para hasta que prospera o se pulsa el boton de parar.
     */
    public void start() {
        if (this.modulus.bitLength() < 30){
            this.LMstart();
        } else if (this.modulus.bitLength() < 40){
            this.BMstart(Constantes.MIDDLE_REFRESH);
        } else if (this.modulus.bitLength() < 50){
            this.BMstart(Constantes.BM_REFRESH);
        } else {
            this.BMstart(Constantes.MAX_REFRESH);
        }        
    }
    
    
    
    /**
     * Ataque por la paradoja del cumpleaños. Solo para en el caso de que se pulse "Parar"
     * imprime todos los resultados del ataque LM = little modulus
     */
    public void LMstart(){
       
        BigInteger cipherI, cipherJ, initialCipherI, initialCipherJ;
        BigInteger IMinusJ, w, t;
        long startTime, totalTime;
        final String time, cipherIstr, cipherJstr;        
        long statsTime;
        boolean write = false;
        BigInteger REFRESH = Constantes.MIN_REFRESH.divide(Constantes.TWO);
        
        startTime = System.currentTimeMillis();
				
        //Calculo e impresion de primeros resultados
        this.i = new BigInteger("3");
        this.initialI = new BigInteger("3");
        this.j = this.modulus.divide(Constantes.TWO);        
        this.initialJ = this.modulus.divide(Constantes.TWO);

        initialCipherI = cipherI = this.message.modPow(this.i, this.modulus);
        initialCipherJ = cipherJ = this.message.modPow(this.j, this.modulus);
        
        
        cipherIstr = cipherI.toString(this.radix).toUpperCase();
        cipherJstr = cipherJ.toString(this.radix).toUpperCase();
        
        
        Platform.runLater(() -> this.Pprint.initialResults(cipherIstr, cipherJstr,
                                                        this.initialJ.toString(this.radix).toUpperCase(),
                                                        this.modulus.toString(this.radix).toUpperCase(),
                                                        this.radix));
        this.result = ""; 
        
        //Comienza el bucle del ataque
        while (!(cipherI.equals(initialCipherJ)) && !(cipherJ.equals(initialCipherI))
                && !(isCancelled)){
            
            
            this.i = this.i.add(Constantes.ONE);
            this.j = this.j.add(Constantes.ONE);
            cipherI = this.message.multiply(cipherI).mod(this.modulus);
            cipherJ = this.message.multiply(cipherJ).mod(this.modulus);
                    
            write = false;
            this.result = this.result + "Col I --> " + this.utilidades.putPoints(this.message.toString(this.radix).toUpperCase(), this.radix) + "^" +
                          this.utilidades.putPoints(this.i.toString(this.radix).toUpperCase(), this.radix) + " mod " +
                          this.utilidades.putPoints(this.modulus.toString(this.radix).toUpperCase(), this.radix) + " = " + 
                          this.utilidades.putPoints(cipherI.toString(this.radix).toUpperCase(), this.radix) + "\n" + 
                          "Col J --> " + this.utilidades.putPoints(this.message.toString(this.radix).toUpperCase(), this.radix) + "^" +
                          this.utilidades.putPoints(this.j.toString(this.radix).toUpperCase(), this.radix) + " mod " +
                          this.utilidades.putPoints(this.modulus.toString(this.radix).toUpperCase(), this.radix) + " = " + 
                          this.utilidades.putPoints(cipherJ.toString(this.radix).toUpperCase(), this.radix) + "\n";
            
            if (i.mod(REFRESH).equals(Constantes.ZERO)){
                this.partialResult = this.result;
                write = true;
                this.avgStats = this.i.multiply(Constantes.TWO);
                statsTime = (System.currentTimeMillis() - startTime)/1000;
                if (statsTime > 1){
                    this.avgStats = (this.i.multiply(Constantes.TWO)).divide(new BigInteger(String.valueOf(statsTime)));
                }                 
                Platform.runLater(() -> {
                   this.Pprint.partialResults(this.partialResult);
                   this.Pprint.Stats(this.avgStats.toString(), (this.i.multiply(Constantes.TWO)).toString(10));
                });
                this.result="";
            }               
        }         
        //Por si ha encontrado la clave y le faltan valores por escribir
        if (!write){
            Platform.runLater(() -> this.Pprint.partialResults(this.result));
        }             
        
        //si existe una colision de un valor de la columna I con el primer  valor de la columna J
        if (cipherI.equals(initialCipherJ)){
            //CALCULOS PARA OBTENER LA POSIBLE CLAVE
            IMinusJ = this.i.subtract(this.initialJ).abs();
            w = (IMinusJ).divide(this.exponent.gcd(IMinusJ));        

            
            Platform.runLater(() -> {
                this.Pprint.colisionDetected("\n\nSe ha detectado una colision de la posición " + 
                        this.utilidades.putPoints(this.i.toString().toUpperCase(), 10) + 
                        " de la Columna I\ncon la primera posición de la Columna J.");
                
                this.Pprint.wValue(this.i.toString(this.radix).toUpperCase(), 
                                    this.initialJ.toString(this.radix).toUpperCase(),
                                    this.exponent.toString(this.radix).toUpperCase(),
                                    w.toString(this.radix).toUpperCase(),
                                    this.radix);});


            //t es la clave privada o una clave privada pareja o un falso positivo
            try {
                t = this.exponent.modInverse(w);  
                Platform.runLater(() -> {
                    this.Pprint.privateKey(t.toString(this.radix).toUpperCase(), this.radix);
                    this.Pprint.tValue(t.toString(this.radix).toUpperCase(), this.radix);
                });

                this.checkObtainedKey(t);
            }catch(ArithmeticException e){
                Platform.runLater(() -> this.Pprint.modInverseError("\n\n ERROR NO EXISTE EL INVERSO DEL EXPONENTE EN EL CUERPO W.\n"));
            }             
            
        //si existe una colision de un valor de la columna J con el primer  valor de la columna I
        } else if (cipherJ.equals(initialCipherI)){
           
            
            //CALCULOS PARA OBTENER LA POSIBLE CLAVE
            IMinusJ = this.j.subtract(this.initialI).abs();
            w = (IMinusJ).divide(this.exponent.gcd(IMinusJ));        

            Platform.runLater(() -> {
                this.Pprint.colisionDetected("\n\nSe ha detectado una colision de la posición " +
                        this.utilidades.putPoints(this.j.toString().toUpperCase(), 10) + 
                        " de la Columna J con la primera posición de la Columna I.");
                
                this.Pprint.wValue(this.j.toString(this.radix).toUpperCase(), 
                                    this.initialI.toString(this.radix).toUpperCase(),
                                    this.exponent.toString(this.radix).toUpperCase(),
                                    w.toString(this.radix).toUpperCase(),
                                    this.radix);});


            //t es la clave privada o una clave privada pareja o un falso positivo
            try {
                t = this.exponent.modInverse(w); 
               
                Platform.runLater(() -> {
                this.Pprint.privateKey(t.toString(this.radix).toUpperCase(), this.radix);
                this.Pprint.tValue(t.toString(this.radix).toUpperCase(), this.radix);
                });
            
                this.checkObtainedKey(t);        
            
            }catch(ArithmeticException e){
                Platform.runLater(() -> this.Pprint.modInverseError("\n\n ERROR NO EXISTE EL INVERSO DEL EXPONENTE EN EL CUERPO W.\n"));
            }               
            
        //Si se ha  pulsado el boton de parar el ataque y no se ha encontrado la clave privada
        }else {
            Platform.runLater(() -> {
                this.Pprint.attackStopped();   
            });
            
        }        
        
        totalTime = System.currentTimeMillis() - startTime;
        time = this.utilidades.millisToSeconds(totalTime);
        
        totalTime = (totalTime/1000);
        this.avgStats = (this.i.subtract(Constantes.TWO)).multiply(Constantes.TWO);
        if (totalTime > 1){
            this.avgStats = ((this.i.subtract(Constantes.TWO)).multiply(Constantes.TWO)).divide(new BigInteger(String.valueOf(totalTime)));
        }     
        
        Platform.runLater(() -> {
            this.Pprint.Stats(this.avgStats.toString(), ((this.i.subtract(Constantes.TWO)).multiply(Constantes.TWO)).toString(10));            
            this.Pprint.time(time);
            this.Pprint.enableStart();            
            this.Pprint.editableModExp(true);
        });
        
        this.setIsCancelled(false);
    }
    

    /**
     * Ataque por la paradoja del cumpleaños. Solo para en el caso de que se pulse "Parar"
     * no imprime todos los resultados del ataque BM = big modulus
     * @param MAX_REFRESH 
     */
    public void BMstart(BigInteger MAX_REFRESH){
       
        BigInteger cipherI, cipherJ, initialCipherI, initialCipherJ;
        BigInteger IMinusJ, w, t;
        long startTime, totalTime;
        final String time, cipherIstr, cipherJstr;
        long statsTime;
        boolean write = false;
                
        startTime = System.currentTimeMillis();
        
	//Calculo e impresion de primeros resultados			
        this.i = new BigInteger("3");
        this.initialI = new BigInteger("3");
        this.j = this.modulus.divide(Constantes.TWO);
        this.initialJ = this.modulus.divide(Constantes.TWO);
        
        
        initialCipherI = cipherI = this.message.modPow(this.i, this.modulus);
        initialCipherJ = cipherJ = this.message.modPow(this.j, this.modulus);
        
        
        cipherIstr = cipherI.toString(this.radix).toUpperCase();
        cipherJstr = cipherJ.toString(this.radix).toUpperCase();
        
        
        Platform.runLater(() -> this.Pprint.initialResults(cipherIstr, cipherJstr,
                                                        this.initialJ.toString(this.radix).toUpperCase(),
                                                        this.modulus.toString(this.radix).toUpperCase(),
                                                        this.radix));
        
        //Comienza el bucle del ataque
        while (!(cipherI.equals(initialCipherJ)) && !(cipherJ.equals(initialCipherI))
                && !(isCancelled)){
            
            
            this.i = this.i.add(Constantes.ONE);
            this.j = this.j.add(Constantes.ONE);
            
            cipherI = this.message.multiply(cipherI).mod(this.modulus);
            cipherJ = this.message.multiply(cipherJ).mod(this.modulus);
            
            write = false;

            if (i.mod(MAX_REFRESH).equals(Constantes.ZERO)){
                this.result = "Col I --> " + this.utilidades.putPoints(this.message.toString(this.radix).toUpperCase(), this.radix) + "^" +
                          this.utilidades.putPoints(this.i.toString(this.radix).toUpperCase(), this.radix) + " mod " +
                          this.utilidades.putPoints(this.modulus.toString(this.radix).toUpperCase(), this.radix) + " = " + 
                          this.utilidades.putPoints(cipherI.toString(this.radix).toUpperCase(), this.radix) + "\n" +
                          "Col J --> " + this.utilidades.putPoints(this.message.toString(this.radix).toUpperCase(), this.radix) + "^" +
                          this.utilidades.putPoints(this.j.toString(this.radix).toUpperCase(), this.radix) + " mod " +
                          this.utilidades.putPoints(this.modulus.toString(this.radix).toUpperCase(), this.radix) + " = " + 
                          this.utilidades.putPoints(cipherJ.toString(this.radix).toUpperCase(), this.radix) + "\n";
            
                
                write = true;
                
                this.avgStats = this.i.multiply(Constantes.TWO);
                statsTime = (System.currentTimeMillis() - startTime)/1000;
                if (statsTime > 1){
                    this.avgStats = (this.i.multiply(Constantes.TWO)).divide(new BigInteger(String.valueOf(statsTime)));
                }                 
                Platform.runLater(() -> {
                   this.Pprint.partialResults(this.result);
                   this.Pprint.Stats(this.avgStats.toString(), (this.i.multiply(Constantes.TWO)).toString(10));
                });
            }             
        } 
        
        //Por si ha encontrado la clave y le faltan valores por escribir
        if (!write){
            this.partialResult = "Col I --> " + this.utilidades.putPoints(this.message.toString(this.radix).toUpperCase(), this.radix) + "^" +
                          this.utilidades.putPoints(this.i.toString(this.radix).toUpperCase(), this.radix) + " mod " +
                          this.utilidades.putPoints(this.modulus.toString(this.radix).toUpperCase(), this.radix) + " = " + 
                          this.utilidades.putPoints(cipherI.toString(this.radix).toUpperCase(), this.radix) + "\n" +
                          "Col J --> " + this.utilidades.putPoints(this.message.toString(this.radix).toUpperCase(), this.radix) + "^" +
                          this.utilidades.putPoints(this.j.toString(this.radix).toUpperCase(), this.radix) + " mod " +
                          this.utilidades.putPoints(this.modulus.toString(this.radix).toUpperCase(), this.radix) + " = " + 
                          this.utilidades.putPoints(cipherJ.toString(this.radix).toUpperCase(), this.radix) + "\n";
            
            Platform.runLater(() -> this.Pprint.partialResults(this.partialResult));
        }  

        
        
        //si existe una colision de un valor de la columna I con el primer  valor de la columna J
        if (cipherI.equals(initialCipherJ)){
            //CALCULOS PARA OBTENER LA POSIBLE CLAVE           
            IMinusJ = this.i.subtract(this.initialJ).abs();
            w = (IMinusJ).divide(this.exponent.gcd(IMinusJ));        

            Platform.runLater(() -> {
                this.Pprint.colisionDetected("\n\nSe ha detectado una colision de la posición " + 
                        this.utilidades.putPoints(this.i.toString().toUpperCase(), 10) + 
                        " de la Columna I\ncon la primera posición de la Columna J.");
                
                this.Pprint.wValue(this.i.toString(this.radix).toUpperCase(), 
                                                    this.initialJ.toString(this.radix).toUpperCase(),
                                                    this.exponent.toString(this.radix).toUpperCase(),
                                                    w.toString(this.radix).toUpperCase(),
                                                    this.radix);});

           //t es la clave privada o una clave privada pareja o un falso positivo
           try {
                t = this.exponent.modInverse(w);  
                Platform.runLater(() -> {
                    this.Pprint.privateKey(t.toString(this.radix).toUpperCase(), this.radix);
                    this.Pprint.tValue(t.toString(this.radix).toUpperCase(), this.radix);
                });
            
                this.checkObtainedKey(t);
            }catch(ArithmeticException e){
                Platform.runLater(() -> this.Pprint.modInverseError("\n\n ERROR NO EXISTE EL INVERSO DEL EXPONENTE EN EL CUERPO W.\n"));
            }         
        
           
           
        //si existe una colision de un valor de la columna J con el primer  valor de la columna I
        } else if (cipherJ.equals(initialCipherI)){
           
            //CALCULOS PARA OBTENER LA POSIBLE CLAVE
            IMinusJ = this.j.subtract(this.initialI).abs();
            w = (IMinusJ).divide(this.exponent.gcd(IMinusJ));        

            Platform.runLater(() -> {
                this.Pprint.colisionDetected("\n\nSe ha detectado una colision de la posición " +
                        this.utilidades.putPoints(this.j.toString().toUpperCase(), 10) + 
                        " de la Columna J con la primera posición de la Columna I.");
                
                this.Pprint.wValue(this.j.toString(this.radix).toUpperCase(), 
                                    this.initialI.toString(this.radix).toUpperCase(),
                                    this.exponent.toString(this.radix).toUpperCase(),
                                    w.toString(this.radix).toUpperCase(),
                                    this.radix);});


            //t es la clave privada o una clave privada pareja o un falso positivo
            try {
                t = this.exponent.modInverse(w); 
               
                Platform.runLater(() -> {
                this.Pprint.privateKey(t.toString(this.radix).toUpperCase(), this.radix);
                this.Pprint.tValue(t.toString(this.radix).toUpperCase(), this.radix);
                });
            
                this.checkObtainedKey(t);        
            
            }catch(ArithmeticException e){
                Platform.runLater(() -> this.Pprint.modInverseError("\n\n ERROR NO EXISTE EL INVERSO DEL EXPONENTE EN EL CUERPO W.\n"));
            }               
            
        //Si se ha  pulsado el boton de parar el ataque y no se ha encontrado la clave privada
        }else {
            Platform.runLater(() -> {
                this.Pprint.attackStopped();   
            });
            
        }        
           
           
                
        totalTime = System.currentTimeMillis() - startTime;
        time = this.utilidades.millisToSeconds(totalTime);
        
        totalTime = (totalTime / 1000);
        this.avgStats =  (this.i.subtract(Constantes.TWO)).multiply(Constantes.TWO);
        if (totalTime > 1){
            this.avgStats = ((this.i.subtract(Constantes.TWO)).multiply(Constantes.TWO)).divide(new BigInteger(String.valueOf(totalTime)));
        }     
        
        Platform.runLater(() -> {
            this.Pprint.Stats(this.avgStats.toString(), ((this.i.subtract(Constantes.TWO)).multiply(Constantes.TWO)).toString(10));  
            
            this.Pprint.time(time);
            this.Pprint.enableStart();
            this.Pprint.editableModExp(true);
        });
        
        this.setIsCancelled(false);
    }
    
    /**
     * Metodo que comprueba que la clave obtenida no sea un falso positivo
     * @param key 
     */
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
            Platform.runLater(() -> this.Pprint.goodResult());
        } else{
            Platform.runLater(() -> this.Pprint.badResult());
        }
    }
    
    /**
     * Método para calcular la raiz cuadrada de un número
     * @param number
     * @return 
     */
    private BigInteger sqrt(BigInteger number) {
	    BigInteger a = Constantes.ONE;
	    BigInteger b = number.shiftRight(5).add(BigInteger.valueOf(8));
            BigInteger mid ;
            
	    while (b.compareTo(a) >= 0) {
	        mid = a.add(b).shiftRight(1);
	        if (mid.multiply(mid).compareTo(number) > 0) {
	            b = mid.subtract(Constantes.ONE);
	        } else {
	            a = mid.add(Constantes.ONE);
	        }
	    }
	    return a.subtract(Constantes.ONE);
    }
    
    
    public void  clear(){
        this.Pprint.delete();
    }
    
    
    
    public void putInfo() {
        this.infoDialog.paradox();
    }
     
     
    public void warning() {
        this.infoDialog.warningAttack();
    }
     
        
    
    
    public void setRadix (int radix){
        this.radix = radix;
    }

    
    public void setIsCancelled (boolean value){
        ParadoxAttack.isCancelled = value;
    }  
    
}