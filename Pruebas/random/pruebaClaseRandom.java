package random;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class pruebaClaseRandom {

	public static void main(String[] args) throws IOException {
		int count0=0 ,count1=0, count2=0, count21=0, count22=0, count23=0;

		Random difference = new Random();
		int dif = -99;

		for (int i = 1; i < 1000; i++) {
			dif = difference.nextInt(20) + 2;

//			System.out.print(" " + dif + " ,");
//			if (i%10==0){
//				System.out.print("\n");
//			}
			
			if (dif==0){
				count0++;
			}
			if (dif==1){
				count1++;
			}
			if (dif==2){
				count2++;
			}
			if (dif==21){
				count21++;
			}
			if (dif==22){
				count22++;
			}
			if (dif==23){
				count23++;
			}
		}

		System.out.println("count0: " + count0);
		System.out.println("count1: " + count1);
		System.out.println("count2: " + count2);
		System.out.println("count21: " + count21);
		System.out.println("count22: " + count22);
		System.out.println("count23: " + count23);
		
		BigInteger p;
		 
		p= BigInteger.probablePrime(90, new SecureRandom());
		
		System.out.println(p);

		System.out.println(p.bitLength());
	}
}
