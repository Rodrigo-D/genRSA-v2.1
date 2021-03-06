/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Imprimir;

import Methods.Utilities;
import Model.ComponentesRSA;
import java.io.File;
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
    
    private Utilities utilidades;
        
    public LogNNC (File logFile) {
        try {
            file = new FileWriter (logFile); 
            print = new PrintWriter (file);
            utilidades = new Utilities();
        } catch (IOException e) {
            ErrorDialog error = new ErrorDialog();            
            error.FileToSave();
        }
    }
    
    
    public void createHTML(ComponentesRSA RSA, int radix) {
        Date fecha = new Date();
        SimpleDateFormat estilo = new SimpleDateFormat( "dd 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
        String fechaStr = estilo.format(fecha);
        
        print.println("<html>");
        print.println("<head><title> Log N&uacute;meros No Cifrables </title></head>");    
        
        print.println("<body>");
        print.println("<PRE>");
        
        print.println("<center><h2><font color=\"navy\"> CLAVE GENERADA </font></h2></center>");
        print.println("<center><h3><font color=\"grey\"> ( " + fechaStr + " ) </font></h3></center>");
        
        if(radix==10){
             print.println("<B><font color=\"Black\">Unidades: Decimal</font></B>");
        } else {
             print.println("<B><font color=\"Black\">Unidades: Hexadecimal</font></B>");
        }
        
        print.println("<B><font color=\"IndianRed\">N&uacute;mero primo P generado:</font></B>");
        print.println("<B>" + this.utilidades.putPoints(RSA.getP().toString(radix).toUpperCase(), radix) + "</B>");        
        print.println("<B><font color=\"IndianRed\">N&uacute;mero primo Q generado:</font></B>");
        print.println("<B>" + this.utilidades.putPoints(RSA.getQ().toString(radix).toUpperCase(), radix) + "</B>");
        print.println("<b><font color=\"IndianRed\">M&oacute;dulo N generado:</font></B>");
        print.println("<B>" + this.utilidades.putPoints(RSA.getN().toString(radix).toUpperCase(), radix) + "</B>");        
        print.println("<B><font color=\"IndianRed\">Clave P&uacute;blica e generada:</font></B>");
        print.println("<B>" + this.utilidades.putPoints(RSA.getE().toString(radix).toUpperCase(), radix) + "</B>");
        print.println("<B><font color=\"IndianRed\">Clave Privada d generada:</font></B>");
        print.println("<B>" + this.utilidades.putPoints(RSA.getD().toString(radix).toUpperCase(), radix) + "<br /><br /></B>");
        
        print.println("<H4><B>N&Uacute;MEROS NO CIFRABLES</B></H4>");
        print.println("<B>La cantidad de N&uacute;meros No Cifrables es: " + this.utilidades.putPoints(RSA.getNumNNC().toString(), radix) + "</B>");
    }
    
    
    public void cancelledHTML() {
        print.println("<B> </B>");
        print.println("<B><font color=\"IndianRed\"> SE HA CANCELADO EL LOG, POR LO TANTO PUEDE NO ESTAR COMPLETO.</font></B>");

    }
    
    public void closeHTML() {
        
        this.print.println("</PRE>");
        this.print.println("</body>");
        this.print.println("</html>");
        this.print.close();
    }

    public void WriteList(List<BigInteger> listNNC, int radix) {
        
        for (BigInteger NNC: listNNC){
            this.print.println(this.utilidades.putPoints(NNC.toString(radix).toUpperCase(), radix));
        }
    }

}
