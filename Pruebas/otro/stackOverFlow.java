package otro;

import java.math.BigInteger;
import java.security.SecureRandom;

public class stackOverFlow {

    public static double runningTime;
    private BigInteger n, d, e;
    private int bitlen = 1024;
    static int eValue = 65537;


    /** Create an instance that can both encrypt and decrypt. */
    public stackOverFlow(int bits) {
        bitlen = bits;
        SecureRandom random = new SecureRandom();
        BigInteger p = new BigInteger(bitlen, 100, random);
        BigInteger q = new BigInteger(bitlen, 100, random);
        n = p.multiply(q);
        BigInteger phiN = (p.subtract(BigInteger.ONE)).multiply(q
                .subtract(BigInteger.ONE));
        e = new BigInteger(Integer.toString(eValue));
        while (phiN.gcd(e).intValue() > 1) {
            e = e.add(new BigInteger("2"));
        }
        d = e.modInverse(phiN);
    }

    /** Encrypt the given plain-text message. */
    public String encrypt(String message) {
        return (new BigInteger(message.getBytes())).modPow(e, n).toString();
    }

    /** Encrypt the given plain-text message. */
    public BigInteger encrypt(BigInteger message) {
        return message.modPow(e, n);
    }

    /** Decrypt the given cipher-text message. */
    public String decrypt(String message) {
        return new String((new BigInteger(message)).modPow(d, n).toByteArray());
    }

    /** Decrypt the given cipher-text message. */
    public BigInteger decrypt(BigInteger message) {
        return message.modPow(d, n);
    }


    /** Return the modulus. */
    public BigInteger getN() {
        return n;
    }

    /** Return the public key. */
    public BigInteger getE() {
        return e;
    }

    /** Test program. */
    public static void main(String[] args) {
        runningTime = System.nanoTime();
        stackOverFlow rsa = new stackOverFlow(1024);

        String text1 = "RSA-Encryption Practice";
        System.out.println("Plaintext: " + text1);
        BigInteger plaintext = new BigInteger(text1.getBytes());

        BigInteger ciphertext = rsa.encrypt(plaintext);
        System.out.println("cipher-text: " + ciphertext);
        plaintext = rsa.decrypt(ciphertext);

        String text2 = new String(plaintext.toByteArray());
        System.out.println("Plaintext: " + text2);
        System.out.println("RunningTime: "
                + (runningTime = System.nanoTime() - runningTime) / 1000000
                + " ms");
    }
}