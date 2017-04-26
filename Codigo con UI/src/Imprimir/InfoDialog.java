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
public class InfoDialog {
    
    Alert info = new Alert(AlertType.INFORMATION);
    
    public InfoDialog() {
        info.setTitle("Mensaje de información");
        info.setHeaderText(null);
        
    }
    

    public void KeySaved() {
        info.setContentText("Clave RSA guardada correctamente");
        info.showAndWait();
    }

    public void LogNNCSaved() {
        info.setContentText("Log de NNNC creado correctamente");
        info.showAndWait();
    }

    public void factorization() {
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

    public void warningParadox() {
        info.setContentText("Si se modifica el módulo o el exponente no se"
                + " garantiza la correcta finalización del ataque. ");
        info.showAndWait();
    }

}
