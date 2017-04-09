/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Imprimir;

import Model.ComponentesRSA;
import Model.Constantes;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author rdiazarr
 */
public class LogNNC {
    
    private FileWriter file;
    
    private PrintWriter print;
    
    public LogNNC () {
        try {
            file = new FileWriter ("LogNNC.html"); //ojo poner un buen nombre de fichero
            print = new PrintWriter (file);
        } catch (IOException e) {
            //imprimir mensaje de error por pantalla debido a que el nombre no es correcto
        }
    }
    
    
    public void createHTML(ComponentesRSA RSA, int radix) {
        Date fecha = new Date();
        SimpleDateFormat estilo = new SimpleDateFormat( "dd 'de' MMMM 'de' yyyy", new Locale("es_ES"));
        String fechaStr = estilo.format(fecha);
        
        print.println("<html>");
        print.println("<head><title> Log Numeros No Cifrables </title></head>");    
        
        print.println("<body>");
        print.println("<PRE>");
        
        print.println("<center><h1><font color=\"navy\"> CLAVE GENERADA </font></h1></center>");
        print.println("<center><h1><font color=\"grey\"> ( " + fechaStr + " ) </font></h1></center>");
        
        print.println("<B><font color=\"IndianRed\">Numero primo P generado:</font></B>");
        print.println("<B>" + RSA.getP().toString(radix) + "</B>");        
        print.println("<B><font color=\"IndianRed\">Numero primo Q generado:</font></B>");
        print.println("<B>" + RSA.getQ().toString(radix) + "</B>");
        print.println("<b><font color=\"IndianRed\">Modulo N generado:</font></B>");
        print.println("<B>" + RSA.getN().toString(radix) + "</B>");        
        print.println("<B><font color=\"IndianRed\">Clave Publica e generada:</font></B>");
        print.println("<B>" + RSA.getE().toString(radix) + "</B>");
        print.println("<B><font color=\"IndianRed\">Clave Privada d generada:</font></B>");
        print.println("<B>" + RSA.getD().toString(radix) + "</B>");
        
        print.println("<H3><B>NUMEROS NO CIFRABLES</B></H3>");
        print.println("<B>El numero de Numeros No Cifrables es:" + RSA.getNumNNC() + "</B>");
    }
    
     public void closeHTML() {
        
        this.print.println("</PRE>");
        this.print.println("</body>");
        this.print.println("</html>");
        this.print.close();
    }

    public void writeErrorHTML(BigInteger numNNC) {
        
        Date fecha = new Date();
        SimpleDateFormat estilo = new SimpleDateFormat( "dd 'de' MMMM 'de' yyyy", new Locale("es_ES"));
        String fechaStr = estilo.format(fecha);
        
        print.println("<html>");
        print.println("<head><title> Log Numeros No Cifrables </title></head>");    
        
        print.println("<body>");
        print.println("<PRE>");
        
        print.println("<center><h1><font color=\"navy\"> ERROR AL GENERAR EL LOG </font></h1></center>");        
        print.println("<center><h1><font color=\"grey\"> ( " + fechaStr + " ) </font></h1></center>");
        
        print.println("<B><font color=\"IndianRed\">El numero de Numeros No Cifrables es"
                + " mucho mayor del permitido(" + Constantes.MAX_NNC + ")</font></B>");
        print.println("<B> NNC = " + numNNC + "</B>");        
        
    }

    public void WriteList(List<BigInteger> listNNC, int radix) {
        
        for (BigInteger NNC: listNNC){
            this.print.println(NNC.toString(radix));
        }
    }

}