/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Imprimir;

import Metodos.Utilidades;
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
    
    private Utilidades utilidades;
    
    public SaveKey (File keyFile) {
        try {
            file = new FileWriter(keyFile);
            print = new PrintWriter (file);
            utilidades = new Utilidades();
        } catch (IOException e) {
           ErrorDialog error = new ErrorDialog();
           error.writingFile();
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
        
        print.println("<center><h2><font color=\"navy\"> CLAVE GENERADA </font></h2></center>");
        print.println("<center><h3><font color=\"grey\"> ( " + fechaStr + " ) </font></h3></center>");
        
        if(radix==10){
             print.println("<B><font color=\"Black\">Unidades: Decimal</font></B>");
        } else {
             print.println("<B><font color=\"Black\">Unidades: Hexadecimal</font></B>");
        }
        
        print.println("<B><font color=\"IndianRed\">Numero primo P generado:</font></B>");
        print.println("<B>" + this.utilidades.putPoints(RSA.getP().toString(radix).toUpperCase()) + "</B>");        
        print.println("<B><font color=\"IndianRed\">Numero primo Q generado:</font></B>");
        print.println("<B>" + this.utilidades.putPoints(RSA.getQ().toString(radix).toUpperCase()) + "</B>");
        print.println("<b><font color=\"IndianRed\">Modulo N generado:</font></B>");
        print.println("<B>" + this.utilidades.putPoints(RSA.getN().toString(radix).toUpperCase()) + "</B>");        
        print.println("<B><font color=\"IndianRed\">Clave Publica e generada:</font></B>");
        print.println("<B>" + this.utilidades.putPoints(RSA.getE().toString(radix).toUpperCase()) + "</B>");
        print.println("<B><font color=\"IndianRed\">Clave Privada d generada:</font></B>");
        print.println("<B>" + this.utilidades.putPoints(RSA.getD().toString(radix).toUpperCase()) + "</B>");
        
        
        this.print.println("</PRE>");
        this.print.println("</body>");
        this.print.println("</html>");
        this.print.close();
     
    }
    
}
