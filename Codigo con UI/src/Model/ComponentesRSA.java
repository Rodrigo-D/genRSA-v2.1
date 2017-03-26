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
public class ComponentesRSA {
    //tamaño de la clave
    private int keySize;
    //primo p
    private BigInteger p;
    //primo q
    private BigInteger q;
    //modulo
    private BigInteger n;
    //clave publica
    private BigInteger e;
    //clave privada
    private BigInteger d;        
    
    private BigInteger pMinusOne;
    
    private BigInteger qMinusOne;    
    //este viene bien
    private BigInteger numNNC;  
    
    
    
    //este tambien
    private BigInteger phiN;
    
    
    
    
    
    private BigInteger gamma;
    //este yo creo que se podría quitar
    private BigInteger numCKP;  
      

    public int getKeySize() {
            return keySize;
    }

    public void setKeySize(int keySize) {
            this.keySize = keySize;
    }   
    
    public BigInteger getP() {
            return p;
    }

    public void setP(BigInteger p) {
            this.p = p;
    }

    public BigInteger getQ() {
            return q;
    }

    public void setQ(BigInteger q) {
            this.q = q;
    }

    public BigInteger getN() {
            return n;
    }

    public void setN(BigInteger n) {
            this.n = n;
    }

    public BigInteger getE() {
            return e;
    }

    public void setE(BigInteger e) {
            this.e = e;
    }

    public BigInteger getD() {
            return d;
    }

    public void setD(BigInteger d) {
            this.d = d;
    }

    public BigInteger getpMinusOne() {
            return pMinusOne;
    }

    public void setpMinusOne(BigInteger pMinusOne) {
            this.pMinusOne = pMinusOne;
    }

    public BigInteger getqMinusOne() {
            return qMinusOne;
    }

    public void setqMinusOne(BigInteger qMinusOne) {
            this.qMinusOne = qMinusOne;
    }

    public BigInteger getPhiN() {
            return phiN;
    }

    public void setPhiN(BigInteger phiN) {
            this.phiN = phiN;
    }

    public BigInteger getGamma() {
            return gamma;
    }

    public void setGamma(BigInteger gamma) {
            this.gamma = gamma;
    }

    public BigInteger getNumNNC() {
            return numNNC;
    }

    public void setNumNNC(BigInteger numMNC) {
            this.numNNC = numMNC;
    }

    public BigInteger getNumCKP() {
            return numCKP;
    }

    public void setNumCKP(BigInteger numCKP) {
            this.numCKP = numCKP;
    }   

    
}
