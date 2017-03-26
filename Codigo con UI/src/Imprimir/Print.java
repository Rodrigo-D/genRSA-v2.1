/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Imprimir;

import Metodos.Utilidades;
import Model.ComponentesRSA;
import genrsa.sceneController;
import java.math.BigInteger;
import javafx.scene.control.TextArea;

/**
 *
 * @author rdiazarr
 */
public class Print {
    /**
     * Atributo encargado de manejar todos 
     */
    private final sceneController scene;
    
    private final Utilidades utilidades;
    
    public Print (sceneController sceneC){
        this.scene = sceneC;
        utilidades = new Utilidades();
    }
    
    public TextArea automaticGeneration (ComponentesRSA RSA, String keySize, long tiempo){
        
        this.scene.getBits_clave_automatica().setText(keySize);
        
        this.scene.getPrimo_P().setText(RSA.getP().toString());
        this.scene.getPrimo_Q().setText(RSA.getQ().toString());
        this.scene.getClave_Privada().setText(RSA.getD().toString());
        this.scene.getClave_Publica().setText(RSA.getE().toString());
        this.scene.getModulo_N().setText(RSA.getN().toString());
        
        this.scene.getBits_primo_P().setText(this.utilidades.countBits(RSA.getP()));
        this.scene.getBits_primo_Q().setText(this.utilidades.countBits(RSA.getQ()));
        this.scene.getBits_clave_Privada().setText(this.utilidades.countBits(RSA.getD()));
        this.scene.getBits_clave_Publica().setText(this.utilidades.countBits(RSA.getE()));
        this.scene.getBits_modulo_N().setText(this.utilidades.countBits(RSA.getN()));
        
        this.scene.getTiempo_clave_automatica().setText(String.valueOf(tiempo));
        
        return this.scene.getClaves_parejas();
}

    public void clavePareja(BigInteger claveP) {
        
        this.scene.getClaves_parejas().setText(claveP.toString());
    }
    
    public void addClavePareja(BigInteger claveP) {
        
        this.scene.getClaves_parejas().appendText("\n");
        this.scene.getClaves_parejas().appendText(claveP.toString());
    }

    public void numClavesParejas(BigInteger numCKP) {
        this.scene.getNum_claves_parejas().setText(numCKP.toString());
    }

    public void numNNC(BigInteger numMNC) {
        this.scene.getNum_mensajes_noCifrables().setText(numMNC.toString());
    }
    
    
}
