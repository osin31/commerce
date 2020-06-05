package kr.co.abcmart.common.request;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.MultiValueMap;

import kr.co.abcmart.common.util.UtilsText;

public class ParameterMap extends HashMap<String, Object> implements IGetter{
	
	private static final long serialVersionUID = 1L;
		
	private MultiValueMap<String, Object> multiValueMapRest;

	public ParameterMap() {}
	
	public void setParameterMap(Map<String, String[]> paramMultiValueMap, MultiValueMap<String, Object> parentRest) {
		
		Map < String,String[] > map = paramMultiValueMap;
		for (Map.Entry < String, String[] > entry: map.entrySet()) {
			String key = String.valueOf(entry.getKey());
			String[] valueArray = (String[]) entry.getValue();
			if (valueArray.length == 1) {
				super.put(key, valueArray[0]);
				parentRest.add(key, valueArray[0]);
			} else {
				super.put(key, valueArray);
				for (String value : valueArray) {
					parentRest.add(key, value);					
				}
			}
		}
		
		this.multiValueMapRest = parentRest;
	}
	
	@Override
	public Object put(String key, Object value) {

		multiValueMapRest.remove(key);

		if(value != null) {
			if(value.getClass().isArray()) {
				Object[] oArr = (Object[])value;
				for (Object o : oArr) {
					multiValueMapRest.add(key, o);	
				}
				
			}else {
				multiValueMapRest.add(key, value);	
			}			
		}

		return super.put(key, value);
	}

	@Override
	public Object remove(Object key) {
		multiValueMapRest.remove(key);
		return super.remove(key);
	}
	
	@Override
	public String getString(String key) {
		return getString(key,null, true);
	}

	@Override
	public String getString(String key, boolean enableXss) {
		return getString(key,null, enableXss);
	}
	
	public String getString(String key, String defaultValue) {
		return getString(key,defaultValue, true);
	}
	
	@Override
	public String getString(String key, String defaultValue, boolean enableXss) {
		
		
		Object o = super.get(key);
		String value = null;
		
		if(o instanceof String[]) {
			String[] values = (String[])o;
			
			if(values != null && values.length > 0 ) {
				value = values[0];
				
				if(enableXss) {
					value = UtilsText.escapeXss(values[0]);			
				}else {
					value = values[0];
				}
			}
		}else if(o instanceof String) {
			value = (String)o;
		}else {
			//throw e?
		}
		

		if(UtilsText.isBlank(value)) {
			value = defaultValue;
		}
		
		return value;
		
	}


	@Override
	public String[] getStringArray(String key) {
		return getStringArray(key, true);
	}
	
	@Override
	public String[] getStringArray(String key, boolean enableXss) {
		
		Object o = super.get(key);
		String[] valuesNew = null;

		if(o instanceof String[]) {
			String[] values = (String[])o;
			
			if(values != null) {
				
				int valuesLength = values.length;
				
				valuesNew = new String[valuesLength];
				
				for (int i = 0; i < valuesLength; i++) {

					if(enableXss) {
						valuesNew[i] = UtilsText.escapeXss(values[i]);					
					}else {
						valuesNew[i] = values[i] ;					
					}
				}			
			}
			
		}else if(o instanceof String) {
			valuesNew = new String[1];
			if(enableXss) {
				valuesNew[0] = UtilsText.escapeXss((String)o);					
			}else {
				valuesNew[0] = (String)o ;					
			}
		}else {
			//throw e?
		}

		return valuesNew;
	}
	
	@Override
	public char getChar(String key) {
		return getString(key).charAt(0);
	}

	@Override
	public char getChar(String key, boolean enableXss) {
		return getString(key,enableXss).charAt(0);
	}

	@Override
	public char getChar(String key, char defaultValue) {
		return getString(key,String.valueOf(defaultValue)).charAt(0);
	}

	@Override
	public char getChar(String key, char defaultValue, boolean enableXss) {
		return getString(key,String.valueOf(defaultValue),enableXss).charAt(0);
	}

	@Override
	public char[] getCharArray(String key) {
		return getCharArray(key,true);
	}

	@Override
	public char[] getCharArray(String key, boolean enableXss) {
		
		String[] values = getStringArray(key);
		char[] valuesNew = null;
		if(values != null) {
			
			int valuesLength = values.length;
			valuesNew = new char[valuesLength];
			
			for (int i = 0; i < valuesLength; i++) {
				if(enableXss) {
					valuesNew[i] = UtilsText.escapeXss(values[i]).charAt(0);					
				}else {
					valuesNew[i] = values[i].charAt(0);
				}
			}			
		}
		
		return valuesNew;
	}
	
	@Override
	public boolean getBoolean(String key) {
		return Boolean.parseBoolean(getString(key));
	}

	@Override
	public boolean getBoolean(String key, boolean defaultValue) {
		
		String value = getString(key);
		if(UtilsText.isBlank(value)) {
			return defaultValue;
		}
		return Boolean.parseBoolean(value);
	}

	@Override
	public boolean[] getBooleanArray(String key) {
		
		String[] array = getStringArray(key);
		boolean[] bool = null;
		
		if(array != null) {

			int arrayLength = array.length;
			
			bool = new boolean[arrayLength];
			for (int i = 0; i < arrayLength; i++) {
				bool[i] = Boolean.parseBoolean(array[i]);
			}
		}
		
		return bool;
	}
	
	@Override
	public byte getByte(String key) {				
		return getByte(key,(byte)0);
	}

	@Override
	public byte getByte(String key, byte defaultValue) {
		
		String value = getString(key);
		if(UtilsText.isBlank(value)) {
			return defaultValue;
		}
		return Byte.parseByte(getString(key));
	}

	@Override
	public byte[] getByteArray(String key) {
		
		String[] array = getStringArray(key);
		byte[] bytes = null;
		
		if(array != null) {

			int arrayLength = array.length;
			
			bytes = new byte[arrayLength];
			for (int i = 0; i < arrayLength; i++) {
				bytes[i] = Byte.parseByte(array[i]);
			}
		}
		
		return bytes;
	}

	@Override
	public short getShort(String key) {				
		return getShort(key,(short)0);
	}

	@Override
	public short getShort(String key, short defaultValue) {
		
		String value = getString(key);
		if(UtilsText.isBlank(value)) {
			return defaultValue;
		}
		return Short.parseShort(removeNumberTypeComma(getString(key)));
	}

	@Override
	public short[] getShortArray(String key) {
		
		String[] array = getStringArray(key);
		short[] numbers = null;
		
		if(array != null) {

			int arrayLength = array.length;
			
			numbers = new short[arrayLength];
			for (int i = 0; i < arrayLength; i++) {
				numbers[i] = Short.parseShort(removeNumberTypeComma(array[i]));
			}
		}
		
		return numbers;
	}
	
	@Override
	public int getInt(String key) {				
		return getInt(key,0);
	}

	@Override
	public int getInt(String key, int defaultValue) {
		
		String value = getString(key);
		if(UtilsText.isBlank(value)) {
			return defaultValue;
		}
		return Integer.parseInt(removeNumberTypeComma(getString(key)));
	}

	@Override
	public int[] getIntArray(String key) {
		
		String[] array = getStringArray(key);
		int[] numbers = null;
		
		if(array != null) {

			int arrayLength = array.length;
			
			numbers = new int[arrayLength];
			for (int i = 0; i < arrayLength; i++) {
				numbers[i] = Integer.parseInt(removeNumberTypeComma(array[i]));
			}
		}
		
		return numbers;
	}

	@Override
	public long getLong(String key) {
		return getLong(key,0L);
	}

	@Override
	public long getLong(String key, long defaultValue) {
		
		String value = getString(key);
		if(UtilsText.isBlank(value)) {
			return defaultValue;
		}
		return Long.parseLong(removeNumberTypeComma(getString(key)));
		
	}

	@Override
	public long[] getLongArray(String key) {
		
		String[] array = getStringArray(key);		
		long[] numbers = null;
		
		if(array != null) {
		
			int arrayLength = array.length;
			
			numbers = new long[arrayLength];
			for (int i = 0; i < arrayLength; i++) {
				numbers[i] = Long.parseLong(removeNumberTypeComma(array[i]));
			}
		}
		
		return numbers;
	}

	@Override
	public double getDouble(String key) {
		return getDouble(key,0.0);
	}

	@Override
	public double getDouble(String key, double defaultValue) {
		String value = getString(key);
		if(UtilsText.isBlank(value)) {
			return defaultValue;
		}
		return Double.parseDouble(removeNumberTypeComma(getString(key)));
	}

	@Override
	public double[] getDoubleArray(String key) {
		
		String[] array = getStringArray(key);
		double[] numbers = null;
		
		if(array != null) {
		
			int arrayLength = array.length;
			
			numbers = new double[arrayLength];
			for (int i = 0; i < arrayLength; i++) {
				numbers[i] = Double.parseDouble(removeNumberTypeComma(array[i]));
			}
		}
		
		return numbers;
	}

	@Override
	public float getFloat(String key) {
		return getFloat(key,0.0f);
	}

	@Override
	public float getFloat(String key, float defaultValue) {
		
		String value = getString(key);
		if(UtilsText.isBlank(value)) {
			return defaultValue;
		}
		return Float.parseFloat(removeNumberTypeComma(getString(key)));
		
	}

	@Override
	public float[] getFloatArray(String key) {
		
		String[] array = getStringArray(key);
		float[] numbers = null;
		
		if(array != null) {
		
			int arrayLength = array.length;
			
			numbers = new float[arrayLength];
			for (int i = 0; i < arrayLength; i++) {
				numbers[i] = Float.parseFloat(removeNumberTypeComma(array[i]));
			}
		}
		return numbers;
	}

}
