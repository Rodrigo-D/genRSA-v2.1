/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Imprimir;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author rdiazarr
 */
public class InfoDialog {
    
    Alert info = new Alert(AlertType.INFORMATION);
    
    public InfoDialog() {
        info.setTitle("Mensaje de información");
        info.setHeaderText(null);
        Stage stage = (Stage) this.info.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(InfoDialog.class.getResourceAsStream("/allImages/info.png")));
        
    }
    

    public void KeySaved() {
        info.setHeaderText(null);
        info.setContentText("Clave RSA guardada correctamente");
        info.showAndWait();
    }

    public void LogNNCSaved() {
        info.setHeaderText(null);
        info.setContentText("Log de NNNC creado correctamente");
        info.showAndWait();
    }
    
    public void LogNNCStopped() {
        info.setHeaderText(null);
        info.setContentText("Log de NNNC parado correctamente");
        info.showAndWait();
    }

    public void factorization() {
        info.setHeaderText(null);
        info.setContentText("La función pollard Rho(N) se calcula según la siguiente función: \n\n" +
                            "  # Valores Iniciales x(i) y x(2*i) for i = 0. \n" +
                            "  xi  := 2 \n" +
                            "  x2i := 2 \n" +
                            "  do \n" +
                            "    # Calcuar x(i+1) y x(2*(i+1)) \n" +
                            "     xiPrime  := xi ^ 2 + 1 \n" +
                            "     x2iPrime := (x2i ^ 2 + 1) ^ 2 + 1 \n" +
                            "    # Incrementar i: modificar los valores para x(i), x(2*i). \n" +
                            "     xi  := xiPrime % N \n" +
                            "     x2i := x2iPrime % N \n" +
                            "     s := mcd(xi - x2i, N) \n" +
                            "     if s <> 1 and s <> N then \n" +
                            "       return s, N/s \n" +
                            "       siendo s el primo p y N/s el primo q\n" +
                            "     end if \n" +
                            "  end do \n" +
                            "end function ");
        info.showAndWait();
    }

    public void cyclic() {
        info.setHeaderText(null);
         info.setContentText("El ataque por cifrado cíclico se basa en lo siguiente:\n\n" 
                 + "Como C = N^e mod n, siendo N un valor secreto. \n"
                 + "Se realizan cifrados sucesivos de los criptogramas Ci \n"
                 + "resultantes con la misma clave pública e. \n"
                 + "Si en uno de estos cifrados obtenemos nuevamente \n"
                 + "el cifrado C original con el que se ha iniciado el ataque,\n"
                 + "resulta obvio que el valor del paso anterior será el secreto N buscado.\n\n" 
                 + "Esto se debe a que RSA es un grupo mutiplicativo.\n\n"
                 + "Consejo -> Para dificultar este tipo de ataques, es interesante\n"
                 + "usar primos seguros de forma que los subgrupos de trabajo  \n"
                 + "sean lo suficientemente altos.\n");
                 
                 
        info.showAndWait();
    }

    public void paradox() {
        info.setHeaderText(null);
        info.setContentText("Pasos del ataque por la paraoja del cumpleaños:\n\n" 
                 + "El atacante elige un número cualquiera N.\n"
                 + "Se toma i=1 y j=módulo/2. \n"                
                 + "Para i=i+1 se calcula N^i mod n. \n"
                 + "Para j se calcula N^j mod n. \n"
                 + "Cuando se encuentra un resultado en i  igual al \n"
                 + "al resultado en j se calcula la clave privada d.\n"
                 + "Se lee el valor del contador de i y de j, se calcula\n"
                 + " w = (i - j) / mcd (e, |i - j|).\n" 
                 + "Deberán existir dos valores (s, t) de forma que se\n" 
                 + "cumpla lo siguiente: w*s + e*t = 1 (en mod e y en mod w).\n"
                 + "Se calcula s = inv (w, e)\n"
                 + "Se calcula t = inv (e, w)\n"
                 + "Se comprueba que w*s + e*t = 1.\n\n"        
                 + "El valor t será la clave privada, una clave privada pareja\n"
                 + "o bien un falso positivo.\n");
                 
                 
        info.showAndWait();
    }

    public void warningAttack() {
        info.setHeaderText(null);
        info.setContentText("Si se modifica el módulo o el exponente no se\n"
                + "garantiza la correcta finalización del ataque. ");
        info.showAndWait();
    }

    public void warningDeCipher() {
        info.setHeaderText("Atención, no se han introducido de forma correcta los datos.");
        info.setContentText("Para que se garantize el cifrado/descifrado de todos los números, \n"
                + "introduzca un número menor al valor del módulo en cada línea.");
        info.showAndWait();
    }
    
    public void warningSign() {
        info.setHeaderText("Atención, no se han introducido de forma correcta los datos.");
        info.setContentText("Para que se garantize la firma/validación de todos los números, \n"
                + "introduzca un número menor al valor del módulo en cada línea.");
        info.showAndWait();
    }

    public void putCipherInfo() {
        info.setHeaderText(null);
        info.setContentText("El proceso de cifrado siempre se realiza con la clave\n" 
                 + "pública del destinatario. De este modo, solo el  destinatario \n"
                 + "podrá, con su clave privada, descifrar los datos.\n"
                 + "Se garantiza la Confidencialidad.\n\n"                
                 + "Si los datos introducidos no son texto, se introducirá un\n"
                 + "número por línea. Si el número introducido es mayor que el\n"
                 + "módulo se dividirá en números de menor o igual valor que el módulo.\n\n"
                 + "Si los datos introducidos son texto, se pasarán a formato ASCII\n"
                 + "y se cifrarán en bloques de bytes. Los bloques serán de \n"
                 + "tamaño máximo igual al número de bytes del módulo menos uno.");
                 
                 
        info.showAndWait();
    }

    public void putDecipherInfo() {
        info.setContentText("El proceso de descifrado siempre se realiza con la clave\n" 
                 + "privada del destinatario. De este modo, solo el  destinatario \n"
                 + "podrá descifrar los datos.\n\n"                
                 + "Si los datos introducidos no son texto, se introducirá un\n"
                 + "número por línea. Si el número introducido es mayor que el\n"
                 + "módulo se dividirá en números de menor o igual valor que el módulo.\n\n"
                 + "Si los datos introducidos son texto, se descifrarán y se pasarán a \n"
                 + "formato ASCII. Es el usuario el que debe asegurarse que los \n"
                 + "datos introducidos tienen caracteres ASCII legibles.");
                 
                 
        info.showAndWait();
    }

    public void putSignInfo() {
        info.setHeaderText(null);
        info.setContentText("El proceso de firma siempre se realiza con la clave privada\n" 
                 + "del emisor. De este modo, se garantiza el no repudio de los datos.\n"
                 + "La firma se suele realizar a un hash de un documento para\n"
                 + "garantizar la Integridad.\n\n"                
                 + "Si los datos introducidos no son texto, se introducirá un\n"
                 + "número por línea. Si el número introducido es mayor que el\n"
                 + "módulo se dividirá en números de menor o igual valor que el módulo.\n\n"
                 + "Si los datos introducidos son texto, se pasarán a formato ASCII\n"
                 + "y se firmarán en bloques de bytes. Los bloques serán de tamaño \n"
                 + "máximo igual al número de bytes del módulo menos uno.");
                 
                 
        info.showAndWait();
    }

    public void putValidateSignInfo() {
        info.setHeaderText(null);
        info.setContentText("El proceso de comprobación de la firma siempre se realiza\n" 
                 + "con la clave pública del emisor. Para comprobar una firma, se \n"
                 + "realiza el hash del documento recibido y se compara con el hash\n"
                 + "recibido (que esta firmado) una vez lo hemos obtenido \n"
                 + "aplicandole la clave pública del emisor\n\n"
                 + "Si los datos introducidos no son texto, se introducirá un\n"
                 + "número por línea. Si el número introducido es mayor que el\n"
                 + "módulo se dividirá en números de menor o igual valor que el módulo.\n\n"
                 + "Si los datos introducidos son texto, se validarán y se pasarán a \n"
                 + "formato ASCII. Es el usuario el que debe asegurarse que los datos \n"
                 + "introducidos tienen caracteres ASCII legibles.");
                 
                 
        info.showAndWait();
        
    }

    public void bigKeySize() {
        info.setHeaderText("Atención, calcular los números no cifrables para un valor de\n"
                + "clave demasiado grande puede resultar un proceso lento.");        
       
        info.setContentText("En cualquier caso, puede parar el proceso cuando lo desee.");       
        
        info.showAndWait();   
    }

    

}
