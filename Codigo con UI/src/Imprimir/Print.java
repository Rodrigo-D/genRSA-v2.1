/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Imprimir;

import Metodos.Utilidades;
import Model.ComponentesRSA;
import genrsa.SceneController;
import java.math.BigInteger;

/**
 *
 * @author rdiazarr
 */
public class Print {
    /**
     * Atributo encargado de manejar todos 
     */
    private final SceneController scene;
    
    private final Utilidades utilidades;
    
    public Print (SceneController sceneC){
        this.scene = sceneC;
        utilidades = new Utilidades();
    }
    
    public void rsaGeneration (ComponentesRSA RSA,  String tiempo, int radix){
        
        this.scene.getPrimo_P().setText(RSA.getP().toString(radix).toUpperCase());
        this.scene.getPrimo_Q().setText(RSA.getQ().toString(radix).toUpperCase());
        this.scene.getClave_Privada().setText(RSA.getD().toString(radix).toUpperCase());
        this.scene.getClave_Publica().setText(RSA.getE().toString(radix).toUpperCase());
        this.scene.getModulo_N().setText(RSA.getN().toString(radix).toUpperCase());
        
        this.scene.getBits_primo_P().setText(this.utilidades.countBits(RSA.getP()));
        this.scene.getBits_primo_Q().setText(this.utilidades.countBits(RSA.getQ()));
        this.scene.getBits_clave_Privada().setText(this.utilidades.countBits(RSA.getD()));
        this.scene.getBits_clave_Publica().setText(this.utilidades.countBits(RSA.getE()));
        this.scene.getBits_modulo_N().setText(this.utilidades.countBits(RSA.getN()));
        
        this.scene.getTiempo_clave_automatica().setText(tiempo);
    }
    
    
    public void autoBitsKey (String keySize){
        
        this.scene.getBits_clave_automatica().setText(keySize);        
    }

    
    public void clearPrivPairKey() {
        
        this.scene.getClaves_parejas().clear();
    }
    
    public void addPrivPairKey(BigInteger claveP, int radix) {
        
        this.scene.getClaves_parejas().appendText(claveP.toString(radix).toUpperCase());
        this.scene.getClaves_parejas().appendText("\n");
    }
    
    public void limitPrivPairKey() {
        this.scene.getClaves_parejas().appendText("Se ha alcanzado número máximo de claves privadas parejas: 30");
    }

    public void numClavesParejas(BigInteger numCKP) {
        this.scene.getNum_claves_parejas().setText(numCKP.toString());
    }

    public void numNNC(BigInteger numMNC) {
        this.scene.getNum_mensajes_noCifrables().setText(numMNC.toString());
    }

    //hacer que parpadee el cuadrito cuando de el resultado
    public void primalityResults(boolean resultadoP, boolean resultadoQ, String time) {
        if (resultadoP){
            this.scene.getEsPrimo_P().setText("SI");
        } else {
            this.scene.getEsPrimo_P().setText("NO");
        }
        
        if (resultadoQ){
            this.scene.getEsPrimo_Q().setText("SI");
        } else {
            this.scene.getEsPrimo_Q().setText("NO");
        }
        
        this.scene.getTiempo_primalidad().setText(time);
    }
    
    public void flushIsPrime() {
        this.scene.getEsPrimo_P().clear();
        this.scene.getEsPrimo_Q().clear();
        this.scene.getTiempo_primalidad().clear();
    }

    
    public void flushNotManual() {
        this.scene.getModulo_N().clear();
        this.scene.getBits_modulo_N().clear();
        this.scene.getClave_Privada().clear();
        this.scene.getBits_clave_Privada().clear();
        this.scene.getClaves_parejas().clear();
        this.scene.getNum_claves_parejas().clear();
        this.scene.getNum_mensajes_noCifrables().clear();
    }

   
    
}
