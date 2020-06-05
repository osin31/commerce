package kr.co.abcmart.security.password;

import org.springframework.security.crypto.password.PasswordEncoder;

import kr.co.abcmart.common.util.UtilsCrypt;

public class SHA256KISAPasswordEncoder implements PasswordEncoder{

	
	public String encode(CharSequence rawPassword,String salt) {
		return UtilsCrypt.sha256(rawPassword.toString(),salt);
	}
	
	@Override
	public String encode(CharSequence rawPassword) {
		return UtilsCrypt.sha256(rawPassword.toString());
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return rawPassword.equals(encodedPassword);
	}

	
}
