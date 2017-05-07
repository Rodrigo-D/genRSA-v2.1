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
import java.util.List;
import javafx.scene.image.Image;

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
    
    private final Image cross;
    private final Image tick;
    private final Image interrogation;
    
    public Print (SceneController sceneC){
        this.scene = sceneC;
        utilidades = new Utilidades();
        cross = new Image("file:resources/primality/cross.png");
        tick = new Image("file:resources/primality/tick.png");
        interrogation = new Image("file:resources/primality/interrogation.png");
    }
    
    public void rsaGeneration (ComponentesRSA RSA,  String tiempo, int radix){
        
        this.scene.getPrimo_P().setText(this.utilidades.putPoints(RSA.getP().toString(radix).toUpperCase()));
        this.scene.getPrimo_Q().setText(this.utilidades.putPoints(RSA.getQ().toString(radix).toUpperCase()));
        this.scene.getClave_Privada().setText(this.utilidades.putPoints(RSA.getD().toString(radix).toUpperCase()));
        this.scene.getClave_Publica().setText(this.utilidades.putPoints(RSA.getE().toString(radix).toUpperCase()));
        this.scene.getModulo_N().setText(this.utilidades.putPoints(RSA.getN().toString(radix).toUpperCase()));
        
        this.scene.getBits_primo_P().setText(this.utilidades.countBits(RSA.getP()));
        this.scene.getBits_primo_Q().setText(this.utilidades.countBits(RSA.getQ()));
        this.scene.getBits_clave_Privada().setText(this.utilidades.countBits(RSA.getD()));
        this.scene.getBits_clave_Publica().setText(this.utilidades.countBits(RSA.getE()));
        this.scene.getBits_modulo_N().setText(this.utilidades.countBits(RSA.getN()));
        
        this.scene.getTiempo_clave_automatica().setText(tiempo);
    }
    
    
    public void autoBitsKey (String keySize){
        
        this.scene.getBits_clave_automatica().setText(this.utilidades.putPoints(keySize));        
    }

    
    public void clearPrivPairKey() {
        
        this.scene.getClaves_parejas().clear();
    }
    
    public void privPairKey(List<String> listCPP) {
        
        listCPP.forEach((cpp) -> {
            this.scene.getClaves_parejas().appendText(cpp + "\n");
        });
       
    }
    
    public void limitPrivPairKey() {
        this.scene.getClaves_parejas().appendText("Se ha alcanzado número máximo de claves privadas parejas a imprimir: 60");
    }

    public void numClavesParejas(BigInteger numCKP) {
        this.scene.getNum_claves_parejas().setText(this.utilidades.putPoints(numCKP.toString()));
    }

    public void numNNC(BigInteger numNNC) {
        this.scene.getCantidadNNC().setText(this.utilidades.putPoints(numNNC.toString()));
    }

    public void primalityResults(boolean Ptrue, boolean Qtrue, String time) {
        if (Ptrue){
            this.scene.getIsPrime_P().setImage(this.tick);
        } else {
            this.scene.getIsPrime_P().setImage(this.cross);
        }
        
        if (Qtrue){
            this.scene.getIsPrime_Q().setImage(this.tick);
        } else {
            this.scene.getIsPrime_Q().setImage(this.cross);
        }
        
        this.scene.getTiempo_primalidad().setText(time);
    }
    
    public void flushIsPrime() {
        this.scene.getIsPrime_P().setImage(this.interrogation);
        this.scene.getIsPrime_Q().setImage(this.interrogation);
        this.scene.getTiempo_primalidad().clear();
    }

    
    public void flushNotManual() {
        this.scene.getModulo_N().clear();
        this.scene.getBits_modulo_N().clear();
        this.scene.getClave_Privada().clear();
        this.scene.getBits_clave_Privada().clear();
        this.scene.getClaves_parejas().clear();
        this.scene.getNum_claves_parejas().clear();
        this.scene.getCantidadNNC().clear();
    }

   
    
}
