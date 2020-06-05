package kr.co.abcmart.common.crypt;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;

public class RSA {
	
    private final int keySize = 2048;
    
	public KeyPair buildKeyPair(int keySize) throws NoSuchAlgorithmException {
	    KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
	    keyPairGenerator.initialize(keySize);      
	    return keyPairGenerator.genKeyPair();
	}
	
	public KeyPair buildKeyPair() throws NoSuchAlgorithmException {
	    return buildKeyPair(keySize);
	}
	
	public byte[] encrypt(PrivateKey privateKey, String message) throws Exception {
	    Cipher cipher = Cipher.getInstance("RSA");  
	    cipher.init(Cipher.ENCRYPT_MODE, privateKey);  
	
	    return cipher.doFinal(message.getBytes());  
	}
	
	public byte[] decrypt(PublicKey publicKey, byte [] encrypted) throws Exception {
	    Cipher cipher = Cipher.getInstance("RSA");  
	    cipher.init(Cipher.DECRYPT_MODE, publicKey);
	    
	    return cipher.doFinal(encrypted);
	}
}

