package kr.co.abcmart.common.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import kr.co.abcmart.common.util.UtilsCrypt;

public class ABCHikariDatasource extends HikariDataSource {

	private static final String SECRET_KEY = "iMT8/hPIK6EdxVRjhZ6Hdyp6QCYWxRC4YBOv3IJvo0I="; // 고정. 되도록이면 변경하지 않는다.

	public ABCHikariDatasource(HikariConfig hikariConfig) {
		super(hikariConfig);
	}

	@Override
	public void setPassword(String password) {
		super.setPassword(password);
	}

	@Override
	public String getPassword() {
		return UtilsCrypt.decryptAES256(SECRET_KEY, super.getPassword());
	}

	public static void main(String[] args) {
		System.out.println(UtilsCrypt.getSalt());
		System.out.println(UtilsCrypt.encryptAES256(SECRET_KEY, "@#Art1@!8Admin&*"));
		System.out.println(UtilsCrypt.decryptAES256(SECRET_KEY, "SOxaVC/4xLqYIQ1ylTlG9A=="));
	}
}
