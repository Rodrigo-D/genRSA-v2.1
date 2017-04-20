/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Imprimir;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author rdiazarr
 */
public class ErrorDialog {
    
    Alert alertError;
    
    public ErrorDialog() {
        alertError = new Alert(AlertType.ERROR);
        alertError.setTitle("Error");
    }
    
            
    public void componentConversion(int radix){                
        alertError.setHeaderText("Error al introducir los componentes");
        
        if (radix==10){
             alertError.setContentText("Por favor, compruebe que se han introducido correctamente"
                + " la clave pública y los primos p y q. \n\n"
                + "Solo se permiten números, guiones, puntos y espacios");
        } else {
            alertError.setContentText("Por favor, compruebe que se han introducido correctamente"
                + " la clave pública y los primos p y q. \n\n"
                + "Solo se permiten letras, números, guiones, puntos y espacios");
        }
        
        alertError.showAndWait();
    }
    
        public void primeConversion(int radix){                
        alertError.setHeaderText("Error al introducir los primos p y q");
        
        if (radix==10){
             alertError.setContentText("Por favor, compruebe que se han introducido correctamente"
                + " los primos p y q. \n\n"
                + "Solo se permiten números, guiones, puntos y espacios");
        } else {
            alertError.setContentText("Por favor, compruebe que se han introducido correctamente"
                + " los primos p y q. \n\n"
                + "Solo se permiten letras, números, guiones, puntos y espacios");
        }
        
        alertError.showAndWait();
    }

    public void primeLittle() {
        alertError.setHeaderText("Error al introducir los primos p y q");        
       
        alertError.setContentText("Por favor, introduzca valores mayores que 3");       
        
        alertError.showAndWait();
    }

    
    public void multipleTwo() {
        alertError.setHeaderText("Error, los probables primos p y q han de tener valor impar");        
       
        alertError.setContentText("Por favor, introduzca un número que no sea multiplo de 2");       
        
        alertError.showAndWait();
    
    }
    
    
    public void invalidPubKey() {        
        alertError.setHeaderText("Error en la clave pública");        
       
        alertError.setContentText("Por favor, introduzca una clave publica"
                + " tal que mcd [e, Φ(n)] = 1, dado que 1 < e < Φ(n)");       
        
        alertError.showAndWait(); 
    }
    
    

    public void keySize() {
        alertError.setHeaderText("Error al introducir la longitud de la clave a generar ");        
       
        alertError.setContentText("Por favor, introduzca un número. \n"
                + "Otros caracteres permitidos son: espacios, puntos y comas.");       
        
        alertError.showAndWait();    
    }

    public void littleKeySize() {        
        alertError.setHeaderText("Error al introducir la longitud de la clave a generar ");        
       
        alertError.setContentText("Por favor, introduzca una longitud de clave mayor que 5");       
        
        alertError.showAndWait(); 
    }
             
             
    public void iterations() {
        alertError.setHeaderText("Error al introducir el número de vueltas del test de primalidad");        
       
        alertError.setContentText("Por favor, introduzca un número. \n"
                + "Otros caracteres permitidos son: espacios, puntos y comas.");       
        
        alertError.showAndWait();    
    }
    
}
