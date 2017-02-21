package genRsa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;


public class RSAhexaMethods {
	
	private final int HEX=16; 
	private BigInteger p, q,  pMinusOne, qMinusOne;
	private BigInteger n;
	private BigInteger phiN;

	private BigInteger e, d;

	long runningTime, runningTime1;
	
	public String BigIntegerToHex(BigInteger number){
		return number.toString(16);
	}
	
	//creo que no hará falta
	public BigInteger HexToBigInteger (String number){
		return new BigInteger (number, 16);
	}
	
	//manual style (dec and hex)
	public boolean putPrimesAndPubKey (String p, String q, String e, boolean decimal){
		//faltaria comprobar que todos son primos 
		
		if ( decimal ){
			this.p= new BigInteger(p);
			this.q= new BigInteger(q);
			this.e= new BigInteger(e);
		} else {
			this.p= new BigInteger(p, this.HEX);
			this.q= new BigInteger(q, this.HEX);
			this.e= new BigInteger(e, this.HEX);
		}
		
		if (calculatePrivateKey()){
			return true;
		}
		return false;
	}
	
	public boolean calculatePrivateKey () {
		n = p.multiply(q);
		
		pMinusOne = p.subtract(BigInteger.ONE);
		qMinusOne = q.subtract(BigInteger.ONE);
		phiN = pMinusOne.multiply(qMinusOne);
		
		if ((e.compareTo(phiN) > -1) || (e.gcd(phiN).compareTo(BigInteger.ONE)) != 0){
			return  false;
		} 
		
		d = e.modInverse(phiN);
		
		return true;
	}
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Valores en decimal");
		builder.append("p=");
		builder.append(p);
		builder.append("\n q=");
		builder.append(q);
		builder.append("\n pMinusOne=");
		builder.append(pMinusOne);
		builder.append("\n qMinusOne=");
		builder.append(qMinusOne);
		builder.append("\n n=");
		builder.append(n);
		builder.append("\n phiN=");
		builder.append(phiN);
		builder.append("\n e=");
		builder.append(e);
		builder.append("\n d=");
		builder.append(d);
		builder.append("]");
		return builder.toString();
	}
	
	
	public String toStringHexadecimal() {
		return "\n\n HEXADECIMAL RSAmethods: \n p=" + p.toString(16) + "\n q=" + q.toString(16) +  "\n n=" + n.toString(16)
				+ "\n e=" + e.toString(16) + "\n d=" + d.toString(16) ;
	}

	
	
	public BigInteger encrypt(BigInteger plaintext) {
		return plaintext.modPow(e, n);
	}

	public BigInteger decrypt(BigInteger ciphertext) {
		return ciphertext.modPow(d, n);
	}
	
	public int countBits (BigInteger number){
		int numBits;
		
		numBits = number.bitLength();
		
		return numBits;
	}

	public static void main(String[] args) throws IOException {
		
		int size;
		int plaintext;
		BigInteger bplaintext, bciphertext;
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader (isr);
		String answer;
		RSAhexaMethods app = new RSAhexaMethods();
		
		System.out.println("Do you want e = 65537? (yes/no)");
		answer = br.readLine();
		System.out.println("Enter key size: ");
		size = Integer.parseInt (br.readLine());
			
		System.out.println("Do you want to encrypt some text? (yes/no)");
		answer = br.readLine();
		
		if (answer.equals("yes") || answer.equals("YES")){
		
			System.out.println("Enter any character : ");
			plaintext = Integer.parseInt (br.readLine());
	
			bplaintext = BigInteger.valueOf((long) plaintext);
	
			bciphertext = app.encrypt(bplaintext);
			System.out.println("Plaintext : " + bplaintext.toString());
			System.out.println("Ciphertext : " + bciphertext.toString());
	
			bplaintext = app.decrypt(bciphertext);
			System.out.println("After Decryption Plaintext : " + bplaintext.toString());
		}
		
		System.out.println(app.toString());
		
		System.out.println(app.toStringHexadecimal());
		
		System.out.println("e-->" + app.countBits(app.e));
		System.out.println("d-->" + app.countBits(app.d));
		System.out.println("p-->" + app.countBits(app.p));
		System.out.println("q-->" + app.countBits(app.q));
	}
	
	//crear p y q de igual tamaño al generarlo automaticamente para e conocida
	//crear p y q de distinto tamaño al generarlo automaticamente para e desconocida


}
