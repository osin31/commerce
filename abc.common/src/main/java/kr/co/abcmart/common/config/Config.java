package kr.co.abcmart.common.config;

import org.springframework.core.env.Environment;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Config {

	private static Environment env;

	public static void load(Environment env) {
		Config.env = env;
	}

	public static String getString(String key) {

		String propertiesValue = null;

		try {
			propertiesValue = getString(key, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return propertiesValue;
	}

	public static String getString(String key, String defaultValue) {

		String propertiesValue = null;

		try {

			if (env == null) {
				propertiesValue = System.getProperty(key, defaultValue);
			} else {
				propertiesValue = env.getProperty(key, defaultValue);
			}

			if (propertiesValue == null) {
				throw new ConfigValueNotFoundException(key.concat(" 에 해당하는 환경 변수값을 찾을 수 없음"));
			}

			propertiesValue = new String(propertiesValue.getBytes("ISO-8859-1"), "UTF-8");

		} catch (Exception e) {
			log.error(e.getMessage());
			propertiesValue = defaultValue;
		}

		return propertiesValue;
	}

	public static int getInt(String key) {
		return getInt(key, 0);
	}

	public static int getInt(String key, int defaultValue) {

		try {
			return Integer.parseInt(getString(key));
		} catch (Exception e) {
			e.printStackTrace();
			return defaultValue;
		}
	}

	public static double getDouble(String key) {
		return getDouble(key, 0);
	}

	public static double getDouble(String key, int defaultValue) {
		try {
			return Double.parseDouble(getString(key));
		} catch (Exception e) {
			e.printStackTrace();
			return defaultValue;
		}
	}

	public static boolean getBoolean(String key) {
		return stringToBoolean(env.getProperty(key));
	}

	public static long getLong(String key) {
		return getLong(key, 0);
	}

	public static long getLong(String key, long defaultValue) {

		try {
			return Long.parseLong(getString(key));
		} catch (Exception e) {
			e.printStackTrace();
			return defaultValue;
		}
	}

	private static boolean stringToBoolean(String value) {
		if (value == null) {
			return false;
		}
		if (value.equals("true")) {
			return true;
		}
		if (value.equals("on")) {
			return true;
		}
		if (value.equals("yes")) {
			return true;
		}
		if (value.equals("1")) {
			return true;
		}
		if (value.equals("Y")) {
			return true;
		}
		return false;
	}

}
