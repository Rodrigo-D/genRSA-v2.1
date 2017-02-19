package theDevelopersPoint;

/*
BigInteger (int bitLength, int certainty, Random rnd)

bitLength - bitLength of the returned BigInteger.

certainty - a measure of the uncertainty that the caller is willing to tolerate. The probability that the new BigInteger represents a prime number will exceed
(1 - 1/2certainty). The execution time of this constructor is proportional to the value of this parameter.

rnd - source of random bits used to select candidates to be tested for primality.
*/

import java.util.*;
import java.math.*;
import java.io.IOException;

public class testRSA {
	private BigInteger p, q;
	private BigInteger n;
	private BigInteger PhiN;

	private BigInteger e, d;

	public testRSA() {
		Initialize();
	}

	public void Initialize() {
		int SIZE = 512;

		/* Step 1: Select two large prime numbers. Say p and q. */
		p = new BigInteger(SIZE, 15, new Random());
		q = new BigInteger(SIZE, 15, new Random());

		/* Step 2: Calculate n = p.q */
		n = p.multiply(q);

		/* Step 3: Calculate ø(n) = (p - 1).(q - 1) */
		PhiN = p.subtract(BigInteger.valueOf(1));
		PhiN = PhiN.multiply(q.subtract(BigInteger.valueOf(1)));

		/* Step 4: Find e such that gcd(e, ø(n)) = 1 ; 1 < e < ø(n) */
		do {
			e = new BigInteger(2 * SIZE, new Random());
			//compareTo da 1 si es mayor que el valor enre parentesis
		} while ((e.compareTo(PhiN) != 1) || (e.gcd(PhiN).compareTo(BigInteger.valueOf(1)) != 0));

		/* Step 5: Calculate d such that e.d = 1 (mod ø(n)) */
		d = e.modInverse(PhiN);
	}

	public BigInteger encrypt(BigInteger plaintext) {
		return plaintext.modPow(e, n);
	}

	public BigInteger decrypt(BigInteger ciphertext) {
		return ciphertext.modPow(d, n);
	}

	public static void main(String[] args) throws IOException {
		testRSA app = new testRSA();

		int plaintext;
		System.out.println("Enter any character : ");
		plaintext = System.in.read();

		BigInteger bplaintext, bciphertext;
		bplaintext = BigInteger.valueOf((long) plaintext);

		bciphertext = app.encrypt(bplaintext);
		System.out.println("Plaintext : " + bplaintext.toString());
		System.out.println("Ciphertext : " + bciphertext.toString());

		bplaintext = app.decrypt(bciphertext);
		System.out.println("After Decryption Plaintext : " + bplaintext.toString());
	}
}
