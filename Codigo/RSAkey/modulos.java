package RSAkey;

import java.math.BigInteger;

public class modulos {

	
	
	
	public static void main(String[] args){
		
		BigInteger a = new BigInteger ("21");//a
		BigInteger m = new BigInteger ("5");//m
		BigInteger n = new BigInteger ("42");//n
		
		a = a.modPow(m, n);
		
		System.out.println ("El resultado es: " + a);
		
		
		
		
		
		
	}
}
