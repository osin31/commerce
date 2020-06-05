package kr.co.abcmart.util;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;

public class Encryption {
	/**
	 * 암호화 로직 이 암호는 복호화 되지않는다.
	 * 
	 * @param source
	 * @return
	 */
	public static String password(String source) {
		SHA256 s = new SHA256(source.getBytes());
		byte[] bs = Base64.encodeBase64(s.GetHashCode());
		return byte2String(bs);
	}

	private static String byte2String(byte[] source) {
		try {
			return new String(source, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return new String(source);
		}
	}
}
