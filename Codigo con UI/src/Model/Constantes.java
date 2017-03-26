/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.math.BigInteger;

/**
 *
 * @author rdiazarr
 */
public class Constantes {
    
    public static BigInteger ZERO = new BigInteger("0");
    
    public static BigInteger ONE = new BigInteger("1");
    
    public static BigInteger TWO = new BigInteger("2");
    
    public static  BigInteger THREE = new BigInteger("3");
    
    public static BigInteger MAX_NNC = new BigInteger ("400");
    // maximum Key Size to calculate NNC
    // calcular bien este número
    public static int MAX_KeySize = 40;
 
    public static int MAX_INT = 2147483647;
    //maximo valor de entero en BigInteger
    public static BigInteger MAX_INT_BI = new BigInteger ("2147483647");
    
}
