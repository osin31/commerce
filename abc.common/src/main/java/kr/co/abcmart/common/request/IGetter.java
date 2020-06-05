package kr.co.abcmart.common.request;

import kr.co.abcmart.common.util.UtilsText;

public interface IGetter {

	default String removeNumberTypeComma(String value) {
		return removeNumberTypeComma(value, null);
	}

	default String removeNumberTypeComma(String value, String defaultValue) {

		if (UtilsText.isBlank(value)) {
			value = defaultValue;
		}

		if (!UtilsText.isBlank(value)) {
			return value.replaceAll("\\,", "");
		}

		return value;
	}

	String getString(String key);

	String getString(String key, boolean enableXsss);

	String getString(String key, String defaultValue);

	String getString(String key, String defaultValue, boolean enableXss);

	String[] getStringArray(String key);

	String[] getStringArray(String key, boolean enableXss);

	public char getChar(String key);

	public char getChar(String key, boolean enableXss);

	public char getChar(String key, char defaultValue);

	public char getChar(String key, char defaultValue, boolean enableXss);

	public char[] getCharArray(String key);

	public char[] getCharArray(String key, boolean enableXss);

	public boolean getBoolean(String key);

	public boolean getBoolean(String key, boolean defaultValue);

	boolean[] getBooleanArray(String key);

	public byte getByte(String key);

	public byte getByte(String key, byte defaultValue);

	byte[] getByteArray(String key);

	public short getShort(String key);

	public short getShort(String key, short defaultValue);

	short[] getShortArray(String key);

	int getInt(String key);

	int getInt(String key, int defaultValue);

	int[] getIntArray(String key);

	long getLong(String key);

	long getLong(String key, long defaultValue);

	long[] getLongArray(String key);

	double getDouble(String key);

	double getDouble(String key, double defaultValue);

	double[] getDoubleArray(String key);

	float getFloat(String key);

	float getFloat(String key, float defaultValue);

	float[] getFloatArray(String key);
}
