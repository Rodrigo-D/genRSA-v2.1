package genRsa;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.PrivateKey;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;

public class RSAmethods2 {

	private KeyPair keyPair;
	private PublicKey publicKey;
	private PrivateKey privateKey;
	private KeyPairGenerator keyGenerator;
	private Cipher cipher;
	private long runningTime, runningTime1;

	public void createRSAkeys(int keySize) throws Exception {
		runningTime = System.currentTimeMillis();
		
		keyGenerator = KeyPairGenerator.getInstance("RSA");
		keyGenerator.initialize(keySize);
		keyPair = keyGenerator.generateKeyPair();
		
		runningTime1= System.currentTimeMillis();
		System.out.println("Tiempo en crear d: " + (runningTime1  - runningTime) + " s");
		
		cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
	}

	public String encrypt(String plaintext) throws Exception {
		
		publicKey = keyPair.getPublic();

		cipher.init(Cipher.ENCRYPT_MODE, publicKey);

		byte[] ciphertext = cipher.doFinal(plaintext.getBytes("UTF8"));
		return encodeBASE64(ciphertext);
	}

	public String decrypt(String ciphertext) throws Exception {
		privateKey = keyPair.getPrivate();

		cipher.init(Cipher.DECRYPT_MODE, privateKey);

		byte[] plaintext = cipher.doFinal(decodeBASE64(ciphertext));
		return new String(plaintext, "UTF8");
	}

	private static String encodeBASE64(byte[] bytes) {
		BASE64Encoder b64 = new BASE64Encoder();
		return b64.encode(bytes);
	}

	private static byte[] decodeBASE64(String text) throws Exception {
		BASE64Decoder b64 = new BASE64Decoder();
		return b64.decodeBuffer(text);
	}

	public static void main(String[] args) throws Exception {
		InputStreamReader sreader = new InputStreamReader(System.in);
		BufferedReader breader = new BufferedReader(sreader);
		int size;
		String plainText;
		
		RSAmethods2 app = new RSAmethods2();

		System.out.println("Enter a key size: ");
		size = Integer.parseInt(breader.readLine());
		app.createRSAkeys(size);
		
		System.out.println("Enter a text to encrypt: ");		
		plainText = breader.readLine();		
		System.out.println("Plaintext = " + plainText);

		String ciphertext = app.encrypt(plainText);
		
		System.out.println("\n" + "After Encryption Ciphertext = " + ciphertext);
		System.out.println("\n" + "After Decryption Plaintext = " + app.decrypt(ciphertext));
		
		System.out.println("\n" + "Public key " + app.keyPair.getPublic());
	    System.out.println("Private key " + app.keyPair.getPrivate().getEncoded());
	    
	    RSAPrivateKey clave = (RSAPrivateKey) app.keyPair.getPrivate();

	    System.out.println("Private exponent " + clave.getPrivateExponent());

	}

}
