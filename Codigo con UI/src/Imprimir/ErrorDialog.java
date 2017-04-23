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
        this.alertError = new Alert(AlertType.ERROR);
        this.alertError.setTitle("Error");
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

    public void manyNNC() {
        alertError.setHeaderText("Error, demasiados NNC a calcular");        
       
        alertError.setContentText("Por favor, genere una clave con menos Números No Cifrables");       
        
        alertError.showAndWait(); 
    }

    public void bigKeySize() {
        alertError.setHeaderText("Error, longitud de clave demasiado grande como para calcular los NNC");        
       
        alertError.setContentText("Por favor, genere una clave con una longitud de clave menor");       
        
        alertError.showAndWait();   
    }

    public void RSAnotGenerated() {
        alertError.setHeaderText("Error, clave RSA no generada");        
       
        alertError.setContentText("Por favor, genere una clave RSA");       
        
        alertError.showAndWait(); 
    }

    public void FileToSave() {
        alertError.setHeaderText("Error, no se ha seleccionado/creado ningún fichero");        
       
        alertError.setContentText("Por favor, seleccione un fichero válido para"
                + " guardar la clave RSA o cree uno nuevo");       
        
        alertError.showAndWait(); 
    }
    
    public void FileToOpen() {
        alertError.setHeaderText("Error, no se ha seleccionado ningún fichero");        
       
        alertError.setContentText("Por favor, seleccione un fichero HTML válido");       
        
        alertError.showAndWait(); 
    }

    public void readingFile() {
        alertError.setHeaderText("Error, al leer el fichero. ");        
       
        alertError.setContentText("El fichero seleccionado puede estar dañado.\n"
                + "Por favor, seleccione un fichero HTML válido");       
        
        alertError.showAndWait(); 
    }

    public void missingComponents() {
        alertError.setHeaderText("Error, el fichero está incompleto ");        
       
        alertError.setContentText("Faltan componentes necesarios para recuperar la clave.\n"
                + "Por favor, seleccione un fichero HTML válido");       
        
        alertError.showAndWait(); 
    }

    public void modulePrime() {
         alertError.setHeaderText("Error, el módulo n no puede ser un número primo");        
       
        alertError.setContentText("Por favor, introduzca un número compuesto "
                + "o genere una clave para factorizar su módulo.");       
        
        alertError.showAndWait(); 
    }

    public void module(int radix) {
        alertError.setHeaderText("Error al introducir el módulo a factorizar");
        
        if (radix==10){
            alertError.setContentText("Por favor, compruebe que se ha introducido correctamente el módulo.\n\n"
                + "Solo se permiten números, guiones, puntos y espacios");
        } else {
            alertError.setContentText("Por favor, compruebe que se ha introducido correctamente el módulo.\n\n"
                + "Solo se permiten letras, números, guiones, puntos y espacios");
        }
        
        alertError.showAndWait();
       
    }

    public void laps() {
        alertError.setHeaderText("Error al introducir el número de vueltas de la factorización");        
       
        alertError.setContentText("Por favor, introduzca un número decimal. \n"
                + "Otros caracteres permitidos son: espacios, puntos y comas.");       
        
        alertError.showAndWait();  
    }

    public void littleNumLaps() {
        alertError.setHeaderText("Error al introducir el número de vueltas de la factorización");        
       
        alertError.setContentText("Por favor, introduzca un número decimal mayor que cero. \n"
                + "Otros caracteres permitidos son: espacios, puntos y comas.");       
        
        alertError.showAndWait();  
    }

    public void cyclicMessage(int radix) {
        alertError.setHeaderText("Error al introducir el mensaje cuyo cifrado se quiere atacar");        
       
        if (radix==10){
            alertError.setContentText("Por favor, introduzca un número decimal mayor que uno. \n"
                + "Otros caracteres permitidos son: espacios, puntos y comas.");
        } else {
            alertError.setContentText("Por favor, introduzca un número hexadecimal mayor que uno. \n"
                + "Otros caracteres permitidos son: espacios, puntos y comas.");
        }
               
        alertError.showAndWait();  
    }

    public void bigCyclicMessage(int radix) {
        alertError.setHeaderText("Error el mensaje introducido es mayor que el módulo");        
       
        if (radix==10){
            alertError.setContentText("Por favor, introduzca un número decimal menor que el módulo.\n"
                + "Otros caracteres permitidos son: espacios, puntos y comas.");
        } else {
            alertError.setContentText("Por favor, introduzca un número hexadecimal menor que el módulo.\n"
                + "Otros caracteres permitidos son: espacios, puntos y comas.");
        }
               
        alertError.showAndWait();  
    }
    
}
