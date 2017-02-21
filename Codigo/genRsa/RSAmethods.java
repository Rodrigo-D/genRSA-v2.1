package genRsa;

/*
BigInteger (int bitLength, int certainty, Random rnd)

bitLength - bitLength of the returned BigInteger.

certainty - a measure of the uncertainty that the caller is willing to tolerate. The probability that the new BigInteger represents a prime number will exceed
(1 - 1/2^certainty). The execution time of this constructor is proportional to the value of this parameter.

rnd - source of random bits used to select candidates to be tested for primality.
*/

import java.math.*;
import java.security.SecureRandom;
import java.util.Random;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RSAmethods {

	private BigInteger p, q,  pMinusOne, qMinusOne;
	private BigInteger n;
	private BigInteger phiN;
	private BigInteger gamma;

	private BigInteger e, d;
	
	private int numCKP;
	private BigInteger numMNC;

	long runningTime, runningTime1;
	
	//automatic, primes with same number of bits. PublicKeySize equal to PrivateKeySize
	public void createRSAkeys(int keySize) {
		runningTime = System.currentTimeMillis();
		
		/* Step 1: Select two large prime numbers. Say p and q. */
		p = BigInteger.probablePrime(keySize/2, new SecureRandom());//en este constructor controlar que no se le 
		q = BigInteger.probablePrime(keySize/2, new SecureRandom());//pueda meter un bitLength aka keySize <2
		
		/* Step 2: Calculate n = p.q */
		n = p.multiply(q);
		
		/* Step 3: Calculate ø(n) = (p - 1).(q - 1) */
		pMinusOne = p.subtract(BigInteger.ONE);
		qMinusOne = q.subtract(BigInteger.ONE);
		phiN = pMinusOne.multiply(qMinusOne);
		
		/* Step 4: Find e such that gcd(e, ø(n)) = 1 ; 1 < e < ø(n) */
		do {
			e = new BigInteger(keySize, new SecureRandom());
			// compareTo da 1 si es mayor que el valor entre parentesis
		} while ((e.compareTo(phiN) > -1) || (e.gcd(phiN).compareTo(BigInteger.ONE)) != 0);

		/* Step 5: Calculate d such that e.d = 1 (mod ø(n)) */
		d = e.modInverse(phiN);
		
		runningTime1= System.currentTimeMillis();
		System.out.println("Tiempo en crear d: " + (runningTime1  - runningTime) + " ms");
	}
	
	//automatic with e=65537, primes with diferent number of bits. 
	public void createRSAPrivateKey(int keySize) {
	//ojo ajustar el numero de bits de diferencia
		runningTime = System.currentTimeMillis();
		
		//random between 2 and 21 in order to have two secure primes
		Random difference = new Random();
		int dif = difference.nextInt(20)+2; 
		
		e = new BigInteger("65537");			
		
		/* Step 1: Select two large prime numbers. Say p and q. */
		p = BigInteger.probablePrime((keySize/2)-dif, new SecureRandom());//en este constructor controlar que no se le 
		q = BigInteger.probablePrime ((keySize/2)+dif, new SecureRandom());//pueda meter un bitLength aka keySize <2
				
		/* Step 2: Calculate ø(n) = (p - 1).(q - 1) */
		pMinusOne = p.subtract(BigInteger.ONE);
		qMinusOne = q.subtract(BigInteger.ONE);
		phiN = pMinusOne.multiply(qMinusOne);
		
				
		/* Step 3: Find q such that gcd(e, ø(n)) = 1 and 1 < e < ø(n) */
		// compareTo da 1 si es mayor que el valor entre parentesis
		while ((e.compareTo(phiN) > -1) || (e.gcd(phiN).compareTo(BigInteger.ONE)) != 0){
			
			//tb se podría usar nextProbablePrime
			q = BigInteger.probablePrime ((keySize/2)+dif, new SecureRandom());
			
			qMinusOne = q.subtract(BigInteger.ONE);
			phiN = pMinusOne.multiply(qMinusOne);			
			
		}//meter una condicion para decir que valor tiene que tener la clave para poder usar como e 65537

		/* Step 4: Calculate n = p.q */
		n = p.multiply(q);
		
		runningTime1= System.currentTimeMillis();
		System.out.println("Tiempo en crear p, q, n y phiN: " + (runningTime1  - runningTime) + " ms");
		runningTime = runningTime1;
		
		/* Step 5: Calculate d such that e.d = 1 (mod ø(n)) */
		d = e.modInverse(phiN);
		
		runningTime1= System.currentTimeMillis();
		System.out.println("Tiempo en crear d: " + (runningTime1  - runningTime) + " ms");
	}
	
	//comprobar que no se coge 7 cuando es 6.98, se tiene que coger 6 claves privadas parejas
	public void calculateCKP(){
		BigInteger pp;
		
		int iterador=1;
		
		this.gamma = this.pMinusOne.multiply(this.qMinusOne.divide(pMinusOne.gcd(qMinusOne)));	 //minimo comun multiplo a través del mcd-gcd	
		pp = this.e.modInverse(this.gamma);
		
		this.numCKP = ((this.n.subtract(pp)).divide(this.gamma)).intValue();
		
		System.out.println("Numero de claves privadas parejas = " + this.numCKP);
		System.out.println("  --> " + pp);
		
		while (this.numCKP >= iterador){
			pp=pp.add(this.gamma);
			if (pp.compareTo(this.d) != 0){
				System.out.println("  --> " + pp.toString());
			}
			iterador++;
		}
	}
	
	public BigInteger numMessagesNoCipherable(boolean calculateMNC ){
		BigInteger eMinusOne,parte1, parte2;
		
		eMinusOne= this.e.subtract(BigInteger.ONE);
		
		parte1 = (BigInteger.ONE.add((eMinusOne.gcd(pMinusOne))));
		parte2 = (BigInteger.ONE.add((eMinusOne.gcd(qMinusOne))));
		this.numMNC = parte1.multiply(parte2);
		
		if (calculateMNC){
			calculateMessagesNoCipherable();
		}
		
		return this.numMNC;
	}
	
	public void calculateMessagesNoCipherable( ){
		BigInteger i = BigInteger.ONE;
		BigInteger message;
				
		System.out.println(" NNC de p ");
		//	x^e mod p = x con 1 ≤ x ≤ p-1
		do{
			i=i.add(BigInteger.ONE);
			
			message = i.modPow(this.e, this.p);
			
			if (message.compareTo(i)==0){
				System.out.println("Mensaje no cifrable --> " + message.toString());
			}			
		} while (i.compareTo(this.pMinusOne)!=0);
	
		System.out.println(" NNC de q ");
		i = BigInteger.ONE;
		//x^e mod q = x con 1 ≤ x ≤ q-1
		do{
			i=i.add(BigInteger.ONE);
			
			message = i.modPow(this.e, this.q);
			
			if (message.compareTo(i)==0){
				System.out.println("Mensaje no cifrable --> " + message.toString());
			}			
		} while (i.compareTo(this.qMinusOne)!=0);
		
		System.out.println(" Los de n ");
		i = BigInteger.ONE;
	
		do{
			i=i.add(BigInteger.ONE);
			
			message = i.modPow(this.e, this.n);
			
			if (message.compareTo(i)==0){
				System.out.println("Mensaje no cifrable --> " + message.toString());
			}			
		} while (i.compareTo(this.n)!=0);
	}
	
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Valores en decimal \n");
		builder.append(" p=");
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
		RSAmethods app = new RSAmethods();
		
		System.out.println("Do you want e = 65537? (yes/no)");
		answer = br.readLine();
		System.out.println("Enter key size: ");
		size = Integer.parseInt (br.readLine());
		
		if (answer.equals("yes") || answer.equals("YES")){			
			app.createRSAPrivateKey(size);			
		} else {										
			app.createRSAkeys(size);
		}
		
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
		
		System.out.println("How do you want to print the keys? (hex/dec)");
		answer = br.readLine();
		
		if (answer.equals("hex") || answer.equals("HEX")){
			System.out.println(app.toStringHexadecimal());
		} else {
			System.out.println(app.toString());
		}
		
		System.out.println("\n");
		
		app.calculateCKP();
		
		System.out.println("\n\n" + "Do you want to show all MNC? (yes/no)");
		answer = br.readLine();
		
		if (answer.equals("yes") || answer.equals("YES")){
			System.out.println(app.numMessagesNoCipherable(true));
		} else {
			System.out.println(app.numMessagesNoCipherable(false));
		}
		
		System.out.println("e-->" + app.countBits(app.e));
		System.out.println("d-->" + app.countBits(app.d));
		System.out.println("p-->" + app.countBits(app.p));
		System.out.println("q-->" + app.countBits(app.q));
		System.out.println("n-->" + app.countBits(app.n));
		
	}
	
	//mensajes no cifrables
	//crear p y q de igual tamaño al generarlo automaticamente para e conocida
	//crear p y q de distinto tamaño al generarlo automaticamente para e desconocida
	//TeoremaChinoDelResto
	//guardar las claves en fichero
	//Algoritmo de exponenciación rápida
	//test de primalidad: Miller Rabin y Fermat
	
	//ataques: factorizar n, paradoja del cumpleaños y ciclico.
}
