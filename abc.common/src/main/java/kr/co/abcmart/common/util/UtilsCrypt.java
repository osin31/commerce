package kr.co.abcmart.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.data.util.Pair;

import kr.co.abcmart.common.crypt.AES256;
import kr.co.abcmart.common.crypt.KISA_SHA256;

public class UtilsCrypt {

	private static final int DEFAULT_SALT_LENGTH = 32;

	private static final KISA_SHA256 SHA256 = new KISA_SHA256();
	private static final AES256 AES256 = new AES256();

	/**
	 * sha256 암호화 한다.
	 * 
	 * @param plainText 암호화 할 내용(값)
	 * @return
	 */
	public static String sha256(String plainText) {

		try {
			return getString(encryptSHA256(plainText.getBytes()).getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 평문과 slat를 연결하여 sha256 암호화 한다.
	 * 
	 * slat 구현 방법에는 여러 가지가 있지만 다음 순서대로 진행 한다. 1. 평문을 sha256으로 한번 암호화 한다. 2. 암호화한 평문을
	 * salt와 문자열 연결을 하여 하나로 합치고 재차 sha256으로 암호화 한다. 3. hex 값으로 변환한다.
	 * 
	 * sha256(sha256(plainText).concat(salt))
	 * 
	 * @param plainText 암호화 할 내용(값)
	 * @param salt      소금 값.(기존 암호화된 평문 이외에 추가 문자열(랜덤)을 연결하여 한번더 암호화 하기 위한 값)
	 * @return String
	 */
	public static String sha256(String plainText, String salt) {

		return sha256(plainText.getBytes(), salt);
	}

	public static String sha256(byte[] plainText, String salt) {

		try {
			String encryptPlainText = encryptSHA256(plainText);
			return getString(encryptSHA256(encryptPlainText.concat(salt).getBytes()).getBytes());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static byte[] sha256Byte(String plainTextByte) throws Exception {

		try {
			return encryptSHA256Byte(plainTextByte.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@SuppressWarnings("static-access")
	private static String encryptSHA256(byte[] plainTextByte) throws Exception {

		byte[] bszDigest = new byte[32];

		SHA256.SHA256_Encrypt(plainTextByte, plainTextByte.length, bszDigest);
		return Base64.getEncoder().encodeToString(bszDigest);
	}

	@SuppressWarnings("static-access")
	private static byte[] encryptSHA256Byte(byte[] plainTextByte) throws Exception {

		byte[] bszDigest = new byte[32];
		SHA256.SHA256_Encrypt(plainTextByte, plainTextByte.length, bszDigest);
		return bszDigest;
	}

	/**
	 * SALT 생성 32바이트.
	 * 
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String getSalt() {
		return getSalt(DEFAULT_SALT_LENGTH);
	}

	/**
	 * SALT 생성 32바이트.
	 * 
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String getSalt(int saltLength) {
		String value = "";
		try {
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			// Salt generation 128 bits long
			byte[] salt = new byte[saltLength];
			secureRandom.nextBytes(salt);
			byte[] encoded = Base64.getEncoder().encode(salt);
			return new String(encoded);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	private static byte[] getBytes(String data) {
		String[] str = data.split(",");
		byte[] result = new byte[str.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = getHex(str[i]);
		}
		return result;
	}

	private static String getString(byte[] data) {
		String result = "";
		for (int i = 0; i < data.length; i++) {
			result = result + toHex(data[i]);
		}
		return result;
	}

	private static byte getHex(String str) {
		str = str.trim();
		if (str.length() == 0)
			str = "00";
		else if (str.length() == 1)
			str = "0" + str;

		str = str.toUpperCase();
		return (byte) (getHexNibble(str.charAt(0)) * 16 + getHexNibble(str.charAt(1)));
	}

	private static byte getHexNibble(char c) {
		if (c >= '0' && c <= '9')
			return (byte) (c - '0');
		if (c >= 'A' && c <= 'F')
			return (byte) (c - 'A' + 10);
		return 0;
	}

	private static String toHex(int b) {
		char c[] = new char[2];
		c[0] = toHexNibble((b >> 4) & 0x0f);
		c[1] = toHexNibble(b & 0x0f);
		return new String(c);
	}

	private static char toHexNibble(int b) {
		if (b >= 0 && b <= 9)
			return (char) (b + '0');
		if (b >= 0x0a && b <= 0x0f)
			return (char) (b + 'A' - 10);
		return '0';
	}

	public static String encryptAES256(String secretKey, String message) {
		return AES256.encrypt(secretKey, message);
	}

	public static String decryptAES256(String secretKey, String message) {
		return AES256.decrypt(secretKey, message);
	}

	/**
	 * @Desc : 회원 데이터 마이그레이션 시 asis 비밀번호 검증용...to be에서는 사용하지 않는다. 소스 간소화를 위해서 기존
	 *       분산되어 있는 클래스 & 메소드들을 하나로 합쳤다.
	 * @Method Name : asIsPassword
	 * @Date : 2019. 4. 19.
	 * @Author : Kimyounghyun
	 * @param input
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	public static String asIsPassword(String input) {
		if (input == null) {
			return null;
		}

		byte[] digest = null;
		// Stage 1
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		digest = md.digest(input.getBytes());
		// Stage 2
		digest = md.digest(digest);

		StringBuilder sb = new StringBuilder(1 + digest.length);
		sb.append("*");

		StringBuilder result = new StringBuilder();
		for (byte b : digest) {
			result.append(Integer.toString((b & 0xF0) >> 4, 16));
			result.append(Integer.toString(b & 0x0F, 16));
		}
		sb.append(result.toString().toUpperCase());

		String bs = null;
		try {
			bs = encryptSHA256(sb.toString().getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bs;
	}

	/**
	 * @Desc : 패스워드 암호화 값 및 Salt키 반환
	 * @Method Name : getEncryptPassword
	 * @Date : 2019. 6. 10.
	 * @Author : 이동엽
	 * @param password
	 * @return
	 */
	public static Pair<String, String> getEncryptPassword(String password) {

		String pswdText = password;
		String passwordSaleText = UtilsCrypt.getSalt();

		pswdText = sha256(pswdText, passwordSaleText);
		return Pair.of(pswdText, passwordSaleText);
	}

	public static void main(String[] args) {
		String password = "ABCmart1220";
//		String salt = "abgDWGHVjMIgen5FQI/rC0eOjIqtjKdf29+euxfW798=";
//		System.out.println(getSalt());
//		System.out.println(sha256(password, salt));

		System.out.println(encryptAES256("", "abcmart1"));
//		System.out.println(RandomStringUtils.randomAlphanumeric(2));

	}

}
