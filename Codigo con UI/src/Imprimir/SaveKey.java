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
    
    private Utilities utilidades;
    
    public SaveKey (File keyFile) {
        try {
            file = new FileWriter(keyFile);
            print = new PrintWriter (file);
            utilidades = new Utilities();
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
        print.println("<B>" + this.utilidades.putPoints(RSA.getP().toString(radix).toUpperCase(), radix) + "</B>");        
        print.println("<B><font color=\"IndianRed\">Numero primo Q generado:</font></B>");
        print.println("<B>" + this.utilidades.putPoints(RSA.getQ().toString(radix).toUpperCase(), radix) + "</B>");
        print.println("<b><font color=\"IndianRed\">Modulo N generado:</font></B>");
        print.println("<B>" + this.utilidades.putPoints(RSA.getN().toString(radix).toUpperCase(), radix) + "</B>");        
        print.println("<B><font color=\"IndianRed\">Clave Publica e generada:</font></B>");
        print.println("<B>" + this.utilidades.putPoints(RSA.getE().toString(radix).toUpperCase(), radix) + "</B>");
        print.println("<B><font color=\"IndianRed\">Clave Privada d generada:</font></B>");
        print.println("<B>" + this.utilidades.putPoints(RSA.getD().toString(radix).toUpperCase(), radix) + "</B>");
        
        
        if (RSA.getN().bitLength()<26 && radix ==10){
            this.calculateAEE(RSA.getPhiN().intValue(), RSA.getE().intValue());
        }
       
        
        
        
        this.print.println("</PRE>");
        this.print.println("</body>");
        this.print.println("</html>");
        this.print.close();
     
    }
    
    
    private void calculateAEE(int phiN, int pubKey){
        
        
        this.print.println("<p></p>");
        this.print.println("<p></p>");
        this.print.println("<p></p>");
        this.print.println("<B><font color=\"DarkGreen \">Algoritmo Extendido de Euclides para el calculo de la clave privada:</font></B>");
        this.print.println("<B><font color=\"DarkGreen \">   phiN = " + this.utilidades.putPoints(String.valueOf(phiN), 10) + "  y clave pública = " + this.utilidades.putPoints(String.valueOf(pubKey), 10)+ "</font></B>");
        this.print.println("<B>  </B>");
        this.print.println("<B>Para i=0   </B>");
        this.print.println("<B>        y = -     g = <font color=\"Brown\">" + this.utilidades.putPoints(String.valueOf(phiN),10) + "</font></B>");
        this.print.println("<B>        u = 1     v = 0</B>");
        this.print.println("<B>Para i=1   </B>");
        this.print.println("<B>        y = -     g = <font color=\"DeepSkyBlue\">" + this.utilidades.putPoints(String.valueOf(pubKey), 10) + "</font></B>");
        this.print.println("<B>        u = 0     v = 1</B>");
        
        
        
        
        int gMinus1, g, gPlus1;
        int yMinus1, y, yPlus1;
        int uMinus1, u, uPlus1;
        int vMinus1, v, vPlus1;
        int i=1;
        
        y=0;//para inicializarlo
        
        gMinus1 = phiN;
        g = pubKey;
        uMinus1=1;
        u=0;
        vMinus1=0;
        v=1;
        
        while (g!=0){
            yPlus1 = gMinus1 / g;
            gPlus1 = gMinus1 - (yPlus1 * g);
            uPlus1 = uMinus1 - (yPlus1 * u);
            vPlus1 = vMinus1 - (yPlus1 * v);
            i++;

            
            yMinus1=y;
            gMinus1=g;
            uMinus1=u;
            vMinus1=v;
            
            y = yPlus1;
            g = gPlus1;
            u = uPlus1;
            v = vPlus1;
            this.print.println("<B>Para i="   + this.utilidades.putPoints(String.valueOf(i), 10) + " </B>");
            this.print.println("<B>        y = " + this.utilidades.putPoints(String.valueOf(y), 10) + "      g = " + this.utilidades.putPoints(String.valueOf(g), 10) + " </B>");
            this.print.println("<B>        u = " + u + "      v = " + v + " </B>");       
        }
        
        if (vMinus1 < 0) {
            vMinus1 = vMinus1 + phiN;
        }
        
        this.print.println("<B><font color=\"Green\"> Clave Privada encontrada = " + this.utilidades.putPoints(String.valueOf(vMinus1), 10) + "</font></B>");
        
    }
    
}
