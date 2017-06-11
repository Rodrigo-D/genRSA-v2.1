/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Imprimir;

import genrsa.GenRSAController;
import javafx.scene.image.Image;


/**
 *
 * @author rdiazarr
 */
public class MainWindow {  
    
    private final GenRSAController scene;
    
    private final Image interrogation;
    
    /**
     * Constructor de la clase
     * @param sceneC 
     */
    public MainWindow (GenRSAController sceneC){
        this.scene = sceneC;
        this.interrogation = new Image(MainWindow.class.getResourceAsStream("/allImages/interrogation.png"));
    }
    
    
    public void changeUnits(String decHex ){
        
        this.scene.getUnitsP().setText(decHex);
        this.scene.getUnitsQ().setText(decHex);
        this.scene.getUnitsD().setText(decHex);
        this.scene.getUnitsN().setText(decHex);
        this.scene.getUnitsE().setText(decHex);
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
        this.scene.getTipicalPubKey().setSelected(false);
    
        this.scene.getCantidadNNC().clear();
        
        this.scene.getNum_claves_parejas().clear();
        this.scene.getClaves_parejas().clear();
        
        this.scene.getIteraciones_primalidad().clear();
        this.scene.getIsPrime_P().setImage(this.interrogation);
        this.scene.getIsPrime_Q().setImage(this.interrogation);
        this.scene.getTiempo_primalidad().clear();
    
    }

   
    
}
