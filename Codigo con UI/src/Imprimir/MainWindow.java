/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Imprimir;

import genrsa.sceneController;
import javafx.scene.control.Label;


/**
 *
 * @author rdiazarr
 */
public class MainWindow {  
    
    private final sceneController scene;
    
    /**
     * Constructor de la clase
     * @param sceneC 
     */
    public MainWindow (sceneController sceneC){
        this.scene = sceneC;
    }
    
    //esto quitarlo y hacerlo con un .css
    public void changeUnits(String decHex, Label units ){
        units.setText(decHex);
    }

    public void delete() {
                
        this.scene.getPrimo_P().clear();
        this.scene.getPrimo_Q().clear();
        this.scene.getClave_Privada().clear();
        this.scene.getClave_Publica().clear();
        this.scene.getModulo_N().clear();
        
        this.scene.getBits_primo_P().clear();
        this.scene.getBits_primo_Q().clear();
        this.scene.getBits_clave_Privada().clear();
        this.scene.getBits_clave_Publica().clear();
        this.scene.getBits_modulo_N().clear();
        
        this.scene.getTiempo_clave_automatica().clear();
        this.scene.getBits_clave_automatica().clear();
    
        this.scene.getNum_mensajes_noCifrables().clear();
        
        this.scene.getNum_claves_parejas().clear();
        this.scene.getClaves_parejas().clear();
        
        this.scene.getIteraciones_primalidad().clear();
        this.scene.getEsPrimo_P().clear();
        this.scene.getEsPrimo_Q().clear();
        this.scene.getTiempo_primalidad().clear();
    
    }

   
    
}
