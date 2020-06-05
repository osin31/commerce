package kr.co.abcmart.common.crypt;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES256 {

	private final String DEFAULT_IV_KEY = "1q2w3e4r5t6y7u8o";
	private final int IV_LENGTH = 16;

	/**
	 * AES256 으로 암호화 한다.
	 *
	 * @param str 암호화할 문자열
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws GeneralSecurityException
	 * @throws UnsupportedEncodingException
	 */
	public String encrypt(String secretKey, String str) {

		byte[] keyData = DEFAULT_IV_KEY.getBytes();

		try {
			SecretKey keySpec = new SecretKeySpec(keyData, "AES");

			Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
			c.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(keyData));
			byte[] encrypted = c.doFinal(str.getBytes("UTF-8"));
			String enStr = new String(Base64.getEncoder().encode(encrypted));
			return enStr;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	/**
	 * AES256으로 암호화된 txt 를 복호화한다.
	 *
	 * @param str 복호화할 문자열
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws GeneralSecurityException
	 * @throws UnsupportedEncodingException
	 */
	public String decrypt(String secretKey, String str) {

		try {

			byte[] keyData = DEFAULT_IV_KEY.getBytes();

			SecretKey keySpec = new SecretKeySpec(keyData, "AES");

			Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
			c.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(keyData));
			byte[] byteStr = Base64.getDecoder().decode(str.getBytes());

			return new String(c.doFinal(byteStr), "UTF-8");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static void main(String[] args) {

		String secretKey = "_abcdefghijklml_";
		String password = "ABCmart1220";

		AES256 aes = new AES256();
		System.out.println(aes.encrypt(secretKey, password));
		System.out.println(aes.decrypt(secretKey, "xTDtP4bJtKK9I/0NZoPTOQ=="));

	}
}
