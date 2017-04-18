/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Imprimir;

import Model.ComponentesRSA;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author rdiazarr
 */
public class SaveKey {
    
    private FileWriter file;
    
    private PrintWriter print;
    
    public SaveKey (File keyFile) {
        try {
            file = new FileWriter(keyFile);
            print = new PrintWriter (file);
        } catch (IOException e) {
            //imprimir mensaje de error por pantalla debido a que el nombre no es correcto
        }
    }
    
    public void generateHTML(ComponentesRSA RSA, int radix) {
        Date fecha = new Date();
        SimpleDateFormat estilo = new SimpleDateFormat( "dd 'de' MMMM 'de' yyyy", new Locale("es_ES"));
        String fechaStr = estilo.format(fecha);
        
        print.println("<html>");
        print.println("<head><title> Clave RSA </title></head>");    
        
        print.println("<body>");
        print.println("<PRE>");
        
        print.println("<center><h1><font color=\"navy\"> CLAVE GENERADA </font></h1></center>");
        print.println("<center><h1><font color=\"grey\"> ( " + fechaStr + " ) </font></h1></center>");
        
        if(radix==10){
             print.println("<B><font color=\"Black\">Unidades: Decimal</font></B>");
        } else {
             print.println("<B><font color=\"Black\">Unidades: Hexadecimal</font></B>");
        }
        
        print.println("<B><font color=\"IndianRed\">Numero primo P generado:</font></B>");
        print.println("<B>" + RSA.getP().toString(radix).toUpperCase() + "</B>");        
        print.println("<B><font color=\"IndianRed\">Numero primo Q generado:</font></B>");
        print.println("<B>" + RSA.getQ().toString(radix).toUpperCase() + "</B>");
        print.println("<b><font color=\"IndianRed\">Modulo N generado:</font></B>");
        print.println("<B>" + RSA.getN().toString(radix).toUpperCase() + "</B>");        
        print.println("<B><font color=\"IndianRed\">Clave Publica e generada:</font></B>");
        print.println("<B>" + RSA.getE().toString(radix).toUpperCase() + "</B>");
        print.println("<B><font color=\"IndianRed\">Clave Privada d generada:</font></B>");
        print.println("<B>" + RSA.getD().toString(radix).toUpperCase() + "</B>");
        
        
        this.print.println("</PRE>");
        this.print.println("</body>");
        this.print.println("</html>");
        this.print.close();
     
    }
    
}
