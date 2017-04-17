/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Metodos;

import java.awt.Desktop;
import java.awt.EventQueue;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
          
    private final ExtensionFilter extensionFilter;
    
    public ManageKey(){
        
        this.extensionFilter = new ExtensionFilter("HTML files (*.html)", "*.html");
                
    }
    
    /**
     * Método encargado de abrir una clave guardada y devolver una lista con los primos p, q y la clave pública.
     * @param label nodo cualquiera de la escena que se usa para ir escalando y obtener la ventana.
     * @return 
     */
    public String[] open (Label label) {
        String[] keys = null;
        FileChooser fileChooser = new FileChooser();
                
        fileChooser.setTitle("Seleccionar clave guardada");
        fileChooser.setInitialDirectory( new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().add(this.extensionFilter);
        
        
        File keyFile = fileChooser.showOpenDialog(label.getScene().getWindow());
        
        if (keyFile != null) {
           keys = processFile(keyFile);
        }
        
        return keys;         
    }
    
    /**
     * 
     * @param keyFile
     * @return 
     */
    private String[] processFile(File keyFile) {
        String[] keys = new String[3];
        String line;
        //variable para contar que esten los tres valores necesarios 
        //para volver a crear la clave de forma manual
        int countPQE = 0;
        String index;
        
        try {
            Scanner file = new Scanner(keyFile);
            
            while (file.hasNextLine()){
                
                line = file.nextLine();
                
                if (StringUtils.contains(line, "P generado")){
                    line = file.nextLine();
                    //para dejar solo los números
                    keys[0] = StringUtils.replaceAll(line, "[^0-9]", "");
                    
                    countPQE++;
                    
                }
                
                if (StringUtils.contains(line, "Q generado")){
                    line = file.nextLine();
                    
                    keys[1] = StringUtils.replaceAll(line, "[^0-9]", "");
                    
                    countPQE++;
                }
                
                if (StringUtils.contains(line, "e generada")){
                    line = file.nextLine();
                    
                    keys[2] = StringUtils.replaceAll(line, "[^0-9]", "");
                    
                    countPQE++;
                }                
                    
            }
                
            //Me queda comprobar que puedan ser bigInteger o quiza no haga falta gracias al metodo
            //Tb comprobar si es decimal o no, y poner el controller a decimal o hexadecimal.
            //poner si es decimal o no en el logNNC
            
        
      
        } catch (FileNotFoundException e) {
            keys = null;
        }        
        return keys;
    }
    
}
