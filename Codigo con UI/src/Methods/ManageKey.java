/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Methods;

import Imprimir.ErrorDialog;
import Imprimir.InfoDialog;
import Imprimir.SaveKey;
import Model.ComponentesRSA;
import Model.Constantes;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author rdiazarr
 * 
 * Clase empleada para gestionar las claves RSA (guardar claves y recuperarlas) 
 * y para generar el Log de NNC.
 */
public class ManageKey {
          
    private final FileChooser fileChooser;
    
    private final ErrorDialog errorDialog;
    
    private final InfoDialog infoDialog;
    //decimal = 10, hexadecimal = 16
    private int radix;    
    
    /**
     * Constructor de la clase
     */
    public ManageKey(){        
        ExtensionFilter extensionFilter = new ExtensionFilter("HTML files", "*.html");        
        
        this.fileChooser = new FileChooser();
        this.fileChooser.setInitialDirectory( new File(System.getProperty("user.dir")));
        fileChooser.getExtensionFilters().add(extensionFilter);
        
        this.errorDialog = new ErrorDialog();
        this.infoDialog = new InfoDialog();
        this.radix=10;                
    }
    
    /**
     * Método encargado de abrir una clave guardada 
     * y devolver una lista con los primos p, q y la clave pública.
     * @param label nodo cualquiera de la escena que 
     * se usa para ir escalando y obtener la ventana.
     * @return 
     */
    public String[] open (Label label) {
        //Pos0: primo P, Pos1: primo Q, Pos2: clave e, Pos3: unidades
        String[] keys = null;
        File keyFile;
                
        this.fileChooser.setTitle("Seleccionar clave guardada");
        keyFile = this.fileChooser.showOpenDialog(label.getScene().getWindow());        
                
        if (keyFile != null) {
            //para que la primera vez que se elija una ruta se acuerde 
            //de cual es esa ruta para las siguientes busquedas
            this.fileChooser.setInitialDirectory(keyFile.getParentFile());             
            keys = processFile(keyFile);

        } else {
            this.errorDialog.FileToOpen();
        }
        
        return keys;         
    }
    
    /**
     * Método que procesa el fichero obtenido en el metodo Open
     * @param keyFile
     * @return 
     */
    private String[] processFile(File keyFile) {
        String[] keys = new String[4];
        String line;
        // dentro de las primeras 20 lineas han de estar todos los componentes necesarios
        int numLines = 0;
        
        try {
            Scanner file = new Scanner(keyFile);
            
            while (file.hasNextLine() && numLines < 20){
                
                line = file.nextLine();
                numLines++;
                
                if (StringUtils.contains(line, "P generado")){
                    line = file.nextLine();
                    //para dejar solo los números
                    keys[0] = StringUtils.replaceAll(line, "[^0-9]", "");    
                    
                    numLines++;
                }
                
                if (StringUtils.contains(line, "Q generado")){
                    line = file.nextLine();
                    
                    keys[1] = StringUtils.replaceAll(line, "[^0-9]", "");  
                    
                    numLines++;
                }
                
                if (StringUtils.contains(line, "e generada")){
                    line = file.nextLine();
                    
                    keys[2] = StringUtils.replaceAll(line, "[^0-9]", "");
                    
                    numLines++;
                }          
                
                if (StringUtils.contains(line, "Unidades:")){
                    
                    if (StringUtils.contains(line,"Decimal")){
                        keys[3] = "Decimal";
                    } else {
                        keys[3] = "Hexadecimal";
                    }
                    
                    numLines++;
                }                    
            }
            
        } catch (FileNotFoundException e) {
             this.errorDialog.readingFile();
             return null;
        }
        
        if (this.keysNotOk(keys)){
            this.errorDialog.missingComponents();
            keys = null;
        }
        
        return keys;
        
    }

     /**
     * Método encargado de guardar la clave generada
     * @param label nodo cualquiera de la escena que se usa para ir escalando y obtener la ventana. 
     * @param RSA 
     */
    public void saveKey (Label label, ComponentesRSA RSA) {        
        File keyFile;
        SaveKey saveKey;
       
        if (RSA == null){
            this.errorDialog.RSAnotGenerated();
            return;
        }
               
        
        this.fileChooser.setTitle("Seleccionar directorio donde guardar la clave");        
        this.fileChooser.setInitialFileName("Clave genRSA");
        keyFile = this.fileChooser.showSaveDialog(label.getScene().getWindow());        
        
        if (keyFile != null ){            
            this.fileChooser.setInitialDirectory(keyFile.getParentFile());             
            
            saveKey = new SaveKey(keyFile);
            saveKey.generateHTML(RSA, this.radix);
            this.infoDialog.KeySaved();
                        
        } else {            
            this.errorDialog.FileToSave();
        }        
    }    
    
    /**
     * Método encargado de guardar el log de Número No Cifrables
     * @param label --> nodo cualquiera de la escena que se usa para ir escalando y obtener la ventana. 
     * @param RSA 
     * @param logNNCFile 
     */
    public void saveLogNNC (Label label, ComponentesRSA RSA, File logNNCFile) {        
        CalculateNNC NNC;
        
        if (RSA != null){            
            if (RSA.getD().bitLength() > Constantes.MAX_KeySize && logNNCFile != null) {
                Platform.runLater(() ->this.infoDialog.bigKeySize());
            }            
        } else {
            //no se puede dar, no estaria activo el boton del log
            Platform.runLater(() ->this.errorDialog.RSAnotGenerated());
            return;
        }
               
        if (logNNCFile != null ){            
            this.fileChooser.setInitialDirectory(logNNCFile.getParentFile());
            
            NNC = new CalculateNNC(this.radix, RSA, logNNCFile);
           
            if ((RSA.getNumNNC().compareTo(Constantes.MAX_NNC_BI) == -1) 
                    && !(RSA.getP().equals(RSA.getQ()))){
                NNC.quickCalculate(); 
            } else {
                NNC.calculate();
            }
            
            if (CalculateNNC.isCancelled){
                Platform.runLater(() ->this.infoDialog.LogNNCStopped());
            } else {
                Platform.runLater(() ->this.infoDialog.LogNNCSaved());                
            } 
            
        } else {            
            Platform.runLater(() ->this.errorDialog.FileToSave());
        }
    }
    
    /**
     * Método para parar el cálculo del log de Num No Cifrables
     */
    public void setLogCancelled() {
        CalculateNNC.isCancelled = true;
    }
  
    
    /**
     * Método que comprueba que se hayan recuperado todos los 
     * numeros necesarios del archivo procesado
     * @param keys
     * @return 
     */
    private boolean keysNotOk(final String[] keys){       
        boolean existsP;
        boolean existsQ;
        boolean existsE;
        boolean existsUnits;
        
        existsP = keys[0] == null;
        existsQ = keys[1] == null;
        existsE = keys[2] == null;
        existsUnits = keys[3] == null;
               
        return (existsP || existsQ || existsE || existsUnits);
    }
    
       
    
    /**
     * @param radix 
     */
    public void setUnits( int radix){
        this.radix = radix;
    }

   
    
}
