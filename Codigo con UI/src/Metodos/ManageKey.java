/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Metodos;

import Imprimir.SaveKey;
import Model.ComponentesRSA;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author rdiazarr
 * 
 * Clase empleada para gestionar las claves RSA. (guardar claves y recuperarlas)
 */
public class ManageKey {
          
    private final FileChooser fileChooser;
    
    
    public ManageKey(){        
        ExtensionFilter extensionFilter = new ExtensionFilter("HTML files", "*.html");        
        
        this.fileChooser = new FileChooser();
        this.fileChooser.setInitialDirectory( new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().add(extensionFilter);
                
    }
    
    /**
     * Método encargado de abrir una clave guardada y devolver una lista con los primos p, q y la clave pública.
     * @param label nodo cualquiera de la escena que se usa para ir escalando y obtener la ventana.
     * @return 
     */
    public String[] open (Label label) {
        //Pos0: primo P, Pos1: primo Q, Pos2: clave e, Pos3: unidades, Pos4: error
        String[] keys;
        File keyFile;
                
        this.fileChooser.setTitle("Seleccionar clave guardada");
        keyFile = this.fileChooser.showOpenDialog(label.getScene().getWindow());        
                
        if (keyFile != null) {
            //para que la primera vez que se elija una ruta se acuerde 
            //de cual es esa ruta para las siguientes busquedas
            this.fileChooser.setInitialDirectory(keyFile.getParentFile());
            
            
            keys = processFile(keyFile);
        } else {
            keys = new String[5];
            keys[4] = "No se ha seleccionado ningun archivo.";
        }
        
        return keys;         
    }
    
    /**
     * 
     * @param keyFile
     * @return 
     */
    private String[] processFile(File keyFile) {
        String[] keys = new String[4];
        String line;
        //para saber que se han rellenado los 4 campos y no seguir leyendo
        int PQEU = 0;
        
        try {
            Scanner file = new Scanner(keyFile);
            
            while (file.hasNextLine() && PQEU < 4){
                
                line = file.nextLine();
                
                if (StringUtils.contains(line, "P generado")){
                    line = file.nextLine();
                    //para dejar solo los números
                    keys[0] = StringUtils.replaceAll(line, "[^0-9]", "");    
                    PQEU++;
                }
                
                if (StringUtils.contains(line, "Q generado")){
                    line = file.nextLine();
                    
                    keys[1] = StringUtils.replaceAll(line, "[^0-9]", "");  
                    PQEU++;
                }
                
                if (StringUtils.contains(line, "e generada")){
                    line = file.nextLine();
                    
                    keys[2] = StringUtils.replaceAll(line, "[^0-9]", "");
                    PQEU++;
                }          
                
                if (StringUtils.contains(line, "Unidades: Decimal")){
                    
                    keys[3] = "Decimal";
                    PQEU++;
                }
                
                
                if (StringUtils.contains(line, "Unidades: Hexadecimal")){
                    
                    keys[3] = "Hexadecimal";
                    PQEU++;
                }
                    
            }
                
        } catch (FileNotFoundException e) {
             keys = new String[5];
             keys[4] = "Error al leer el fichero";
        }        
        
        
        if ( keys.length < 5){
           
            if (this.keysNotOk(keys)){
               keys = new String[5];
               keys[4] = "Error al leer el fichero, falta algún componente";
             }
        }
        
        return keys;
        
    }

    private boolean keysNotOk(String[] keys){
        boolean out;
        boolean existsP;
        boolean existsQ;
        boolean existsE;
        boolean existsUnits;
        
        existsP = keys[0] == null;
        existsQ = keys[1] == null;
        existsE = keys[2] == null;
        existsUnits = keys[3] == null;

        out = existsP || existsQ || existsE || existsUnits;
               
        return out;
    }
    
    
     /**
     * Método encargado de guardar la clave creada
     * @param label nodo cualquiera de la escena que se usa para ir escalando y obtener la ventana. 
     * @param RSA 
     * @param isDecimal 
     */
    public void save (Label label, ComponentesRSA RSA, boolean isDecimal) {        
        File keyFile;
        SaveKey saveKey;
        int radix = 16;
        
        if (isDecimal){
            radix = 10;
        }
        
        this.fileChooser.setTitle("Seleccionar directorio donde guardar la clave");        
        keyFile = fileChooser.showSaveDialog(label.getScene().getWindow());        
        
        if (keyFile != null ){
            
            this.fileChooser.setInitialDirectory(keyFile.getParentFile());
             
            if (RSA != null){
                saveKey = new SaveKey(keyFile);
                saveKey.generateHTML(RSA, radix);
            } else {
                //imprimir mensaje de error diciendo que no se ha generado una clave
            }
            
        } else {            
            //imprimir mensaje de error diciendo que no se ha seleccionado un fichero donde guardarlo
        }
    }
    
    
}
