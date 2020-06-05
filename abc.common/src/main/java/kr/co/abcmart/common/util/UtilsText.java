package kr.co.abcmart.common.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.nhncorp.lucy.security.xss.XssPreventer;

import kr.co.abcmart.common.constant.BaseConst;

/**
 * 문자열을 조작 할 때 쓰이는 유틸 클래스
 *
 * @author zerocooldog@zen9.co.kr
 */
public class UtilsText extends org.apache.commons.lang3.StringUtils {

	public static ObjectMapper getObjectMapper() {

		ObjectMapper o = new ObjectMapper();
		o.setSerializationInclusion(Include.NON_NULL);
		o.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		o.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
		o.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

		return o;
	}

	/***
	 * XSS 처리
	 *
	 * @param value
	 * @return
	 */
	public static String escapeXss(String value) {
		return XssPreventer.escape(value);
	}

	/***
	 * XSS 처리 전 원래 값으로 변환
	 *
	 * @param value
	 * @return
	 */
	public static String unescapeXss(String value) {
		return XssPreventer.unescape(value);
	}

	public static String[] unescapeXss(String[] values) {

		if (values != null) {
			int valuesLength = values.length;
			String[] newValues = new String[valuesLength];

			for (int i = 0; i < valuesLength; i++) {
				newValues[i] = unescapeXss(values[i]);
			}
		}

		return values;
	}

	/***
	 * 문자열을 연결 한다.
	 *
	 * @param arrStr
	 * @return String
	 */
	public static String concat(String... arrStr) {

		StringBuilder sb = new StringBuilder();

		for (String concatStr : arrStr) {
			sb.append(concatStr);
		}

		return sb.toString();
	}

	/***
	 * 정규 표현식 group 을 지정한 문자열을 찾는다.
	 *
	 * @param patternStr 정규식 패턴
	 * @param data       정규식 대상 문자열
	 * @param idx        문자열이 그룹화 된 번호
	 * @return String
	 */
	public static String matcherGroup(String patternStr, String data, int idx) {

		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(data);

		if (matcher.find()) {
			return matcher.group(idx);
		}

		return null;
	}

	/***
	 * 정규 표현식 group 을 지정한 문자열을 찾는다.
	 *
	 * @param patternStr 정규식 패턴
	 * @param data       정규식 대상 문자열
	 * @param idx        문자열 그룹을 지정한 id
	 * @return String
	 */
	public static String matcherGroup(String patternStr, String data, String name) {

		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(data);

		if (matcher.find()) {
			return matcher.group(name);
		}

		return null;
	}

	/***
	 * 정규 표현식 group으로 지정한 항목들을 모두 출력 한다.
	 *
	 * @param patternStr 정규식 패턴
	 * @param data       정규식 대상 문자열
	 * @return String[]
	 */
	public static String[] matcherGroups(String patternStr, String data) {

		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(data);

		String[] arrString = null;

		if (matcher.find() && matcher.groupCount() > 0) {

			int groupCount = matcher.groupCount() + 1;
			arrString = new String[groupCount];

			for (int i = 0; i < groupCount; i++) {
				String value = matcher.group(i);
				if (value != null) {
					arrString[i] = matcher.group(i).trim();
				}
			}
		}

		return arrString;
	}

	/***
	 * Collections 객체를 JSON 문자열로 변환
	 *
	 * @param o Collections 객체 Map,List,Array
	 * @return String
	 */
	public static String stringify(Object o) {

		try {
			return getObjectMapper().writeValueAsString(o);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * @Desc : @JsonView annotation에 따라서 json 문자열 변환
	 * @Method Name : stringify
	 * @Date : 2019. 4. 26.
	 * @Author : Kimyounghyun
	 * @param clazz
	 * @param o
	 * @return
	 */
	public static String stringify(Class clazz, Object o) {

		try {
			if (clazz != null) {
				return getObjectMapper().writerWithView(clazz).writeValueAsString(o);
			} else {
				return getObjectMapper().writeValueAsString(o);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/***
	 * JSON 문자열을 Collections 객체로 변환
	 *
	 * @param content JSON 문자열
	 * @return Map or List
	 */
	public static Object parse(String content) {

		Class<?> clazz = null;
		if (content.startsWith("[")) {
			clazz = ArrayList.class;
		} else if (content.startsWith("{")) {
			clazz = HashMap.class;
		}

		return parse(content, clazz);
	}

	/**
	 * JSON 문자열을 Collections 객체로 변환
	 *
	 * @param content JSON 문자열
	 * @param clazz   Collections 객체로 변경할 클래스, HashMap.class or ArrayList.class
	 * @return Map or List
	 */
	public static <U> U parse(String content, Class<U> clazz) {

		try {
			return getObjectMapper().readValue(content, clazz);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * underscore ('_') 가 포함되어 있는 문자열을 Camel Case ( 낙타등 표기법 - 단어의 변경시에 대문자로 시작하는 형태.
	 * 시작은 소문자) 로 변환해주는 utility 메서드 ('_' 가 나타나지 않고 첫문자가 대문자인 경우도 변환 처리 함.)
	 *
	 * @param underScore - '_' 가 포함된 변수명
	 * @return Camel 표기법 변수명
	 */
	public static String convert2CamelCase(String underScore) {

		// '_' 가 나타나지 않으면 이미 camel case 로 가정함.
		// 단 첫째문자가 대문자이면 camel case 변환 (전체를 소문자로) 처리가
		// 필요하다고 가정함. --> 아래 로직을 수행하면 바뀜
		if (underScore.indexOf('_') < 0 && Character.isLowerCase(underScore.charAt(0))) {
			return underScore;
		}
		StringBuilder result = new StringBuilder();
		boolean nextUpper = false;
		int len = underScore.length();

		for (int i = 0; i < len; i++) {
			char currentChar = underScore.charAt(i);
			if (currentChar == '_') {
				nextUpper = true;
			} else {
				if (nextUpper) {
					result.append(Character.toUpperCase(currentChar));
					nextUpper = false;
				} else {
					result.append(Character.toLowerCase(currentChar));
				}
			}
		}
		return result.toString();
	}

	/**
	 * URL Decoding 기본 charset은 UTF-8 이다.
	 *
	 * @param s
	 * @param charset 인코딩
	 * @return String
	 */
	public static String urlDecode(String s, String charset) {
		try {
			return URLDecoder.decode(s, charset);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Error in urlDecode.", e);
		}
	}

	/**
	 * URL Decoding 기본 charset은 UTF-8 이다.
	 *
	 * @param s
	 * @return String
	 */
	public static String urlDecode(String s) {
		return urlDecode(s, BaseConst.DEFAULT_CHARSET_UTF_8);
	}

	/**
	 * URL Encoding 기본 charset은 UTF-8 이다.
	 *
	 * @param s
	 * @return String
	 */
	public static String urlEncode(String s) {
		return urlEncode(s, BaseConst.DEFAULT_CHARSET_UTF_8);
	}

	/**
	 * URL Encoding 기본 charset은 UTF-8 이다.
	 *
	 * @param s
	 * @param charset 인코딩
	 * @return String
	 */
	public static String urlEncode(String s, String charset) {
		try {
			return URLEncoder.encode(s, charset);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Error in urlDecode.", e);
		}
	}

	/***
	 *
	 * queryString 문자열을 map에 담아준다.
	 *
	 * @param queryString 파라메터 문자열 ex)a=b&b=f&c=t1&c=t2
	 * @return Map < String, String[] >
	 */
	public static Map<String, String[]> parseUrlQueryString(String s) {
		if (s == null)
			return new HashMap<String, String[]>(0);
		// In map1 we use strings and ArrayLists to collect the parameter values.
		HashMap<String, Object> map1 = new HashMap<String, Object>();
		int p = 0;
		while (p < s.length()) {
			int p0 = p;
			while (p < s.length() && s.charAt(p) != '=' && s.charAt(p) != '&')
				p++;
			String name = urlDecode(s.substring(p0, p));
			if (p < s.length() && s.charAt(p) == '=')
				p++;
			p0 = p;
			while (p < s.length() && s.charAt(p) != '&')
				p++;
			String value = urlDecode(s.substring(p0, p));
			if (p < s.length() && s.charAt(p) == '&')
				p++;
			Object x = map1.get(name);
			if (x == null) {
				// The first value of each name is added directly as a string to the map.
				map1.put(name, value);
			} else if (x instanceof String) {
				// For multiple values, we use an ArrayList.
				ArrayList<String> a = new ArrayList<String>();
				a.add((String) x);
				a.add(value);
				map1.put(name, a);
			} else {
				@SuppressWarnings("unchecked")
				ArrayList<String> a = (ArrayList<String>) x;
				a.add(value);
			}
		}
		// Copy map1 to map2. Map2 uses string arrays to store the parameter values.
		HashMap<String, String[]> map2 = new HashMap<String, String[]>(map1.size());
		for (Map.Entry<String, Object> e : map1.entrySet()) {
			String name = e.getKey();
			Object x = e.getValue();
			String[] v;
			if (x instanceof String) {
				v = new String[] { (String) x };
			} else {
				@SuppressWarnings("unchecked")
				ArrayList<String> a = (ArrayList<String>) x;
				v = new String[a.size()];
				v = a.toArray(v);
			}
			map2.put(name, v);
		}
		return map2;
	}

	/***
	 * map 에 담긴 데이터를 queryString으로 변환 하여 준다. default encoding utf-8
	 *
	 * @param parameterMap
	 * @param encoding     인코딩
	 * @return String
	 */
	public static String queryString(Map<String, ?> parameterMap, String encoding) {

		StringBuilder body = new StringBuilder();

		try {
			for (Entry<String, ?> entry : parameterMap.entrySet()) {

				if (body.length() > 0) {
					body.append('&');
				}

				createKeyValuePair(body, entry, encoding);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return body.toString();
	}

	/***
	 * map 에 담긴 데이터를 queryString으로 변환 하여 준다. default encoding utf-8
	 *
	 * @param parameterMap
	 * @return
	 */
	public static String queryString(Map<String, ?> parameterMap) {
		return queryString(parameterMap, BaseConst.DEFAULT_CHARSET_UTF_8);
	}

	private static void createKeyValuePair(StringBuilder body, Entry<String, ?> entry, String charset)
			throws Exception {

		String key = URLEncoder.encode(entry.getKey(), charset);

		Object o = entry.getValue();

		/*
		 * 일반 파라메터는 List로 넘어오기 때문에 직접 queryString을 만들어 준다. 추가 날짜 : 2018-09-09
		 * 장진철(zerocooldog)
		 */
		if (o instanceof List) {
			List values = (List) o;
			if (values != null) {
				for (Object valueObject : values) {

					String value = null;
					if (valueObject instanceof String) {
						value = valueObject.toString();
					}

					body.append('&');
					appendParameter(body, key, value, charset);
				}
			}
		} else if (o instanceof String[]) {
			String[] values = (String[]) o;
			if (values != null) {
				for (Object valueObject : values) {

					String value = null;
					if (valueObject instanceof String) {
						value = valueObject.toString();
					}

					body.append('&');
					appendParameter(body, key, value, charset);
				}
			}
		} else {
			appendParameter(body, key, o.toString(), charset);
		}
	}

	private static void appendParameter(StringBuilder sb, String key, String value, String charset)
			throws UnsupportedEncodingException {

		sb.append(key);
		sb.append('=');
		if (!UtilsText.isBlank(value)) {
			sb.append(URLEncoder.encode(value, charset));
		}
	}

	/***
	 * 문자열을 지정한 포맷 형식으로 출력한다. 문자 사이에 -, : , (공백), . 의 문자가 있으면 빈 값으로 변경하여 처리한다. 마스킹
	 * 처리시에는 * 문자를 입력 한다.
	 *
	 * ex) 1234-5678-9012 = 123456789012, 1234:5678:9012 = 123456789012
	 * 1234.5678.9012 = 123456789012, 1234^5678^9012 = 123456789012 1234,5678,9012 =
	 * 123456789012, 1234 5678 9012 = 123456789012
	 *
	 * UtilsText.formatter("4928341233434", "A-####-####-###-##") =>
	 * A-4928-3412-334-34 UtilsText.formatter("4928341233434", "A-####-####-###") =>
	 * A-4928-3412-33434 UtilsText.formatter("2013^1312^3343,122355",
	 * "####-##**-****-####") => 2013-13**-****-1223
	 * UtilsText.formatter("9011291234567, "######-#******") => 901129-1******
	 *
	 * @param value  변경할 문자열
	 * @param format 포맷 지정 문자열 ex) #-###-###
	 * @return String
	 */
	public static String formatter(String value, String format) {

		if (value == null) {
			return null;
		}

		String replaceRegex = "[\\s-:\\.^,]";

		value = value.replaceAll(replaceRegex, "");

		String[] fs = format.split("");
		StringBuilder r = new StringBuilder();
		int i = 0;

		int formatLength = fs.length;

		for (int x = 0; x < formatLength; x++) {

			if ("#".equals(fs[x])) {
				try {

					char charAt = value.charAt(i++);

					if (!"".equals(charAt)) {
						r.append(charAt);
					}
					;

				} catch (Exception e) {
				}

			} else {
				if ("*".equals(fs[x])) {
					i++;
				}
				r.append(fs[x]);
			}
			;

		}

		// 패턴에 맞지 않는 나머지 문자열들을 이어준다.
		if (i <= value.length()) {
			r.append(value.substring(i));
		}

		return r.toString();
	}

	/**
	 * @Desc : 시작문자와 끝문자를 제외한 가운데 문자열을 마스킹처리한다.
	 * @Method Name : masking
	 * @Date : 2019. 2. 22.
	 * @Author : Kimyounghyun
	 * @param value
	 * @return
	 */
	public static String masking(String value) {
		return value.replaceAll("\\B[\\w|ㄱ-ㅎ|가-힣]\\B", BaseConst.MASKING);
	}

	/***
	 *
	 * @Desc : euc-kr로 파라메터를 전달 받으면 깨지지 않게 변환한다.
	 * @Method Name : getParameterConvertEuckr
	 * @Date : 2019. 4. 30.
	 * @Author : zerocooldog
	 * @param value 파라메터 값.
	 * @return String
	 */
	public static String getParameterEuckr(String value) {

		if (value == null) {
			return null;
		}

		try {
			value = new String(value.getBytes("iso-8859-1"), "euc-kr");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return value;
	}

	/**
	 * @Desc : 특수문자 제거
	 * @Method Name : removeSpChar
	 * @Date : 2019. 5. 16.
	 * @Author : 유성민
	 * @param str
	 * @return
	 */
	public static String removeSpChar(String str) {
		String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
		str = str.replaceAll(match, "");
		return str;
	}

	/**
	 * @Desc : 전환번호 포맷
	 * @Method Name : makePhoneNumber
	 * @Date : 2019. 7. 25.
	 * @Author : 유성민
	 * @param src
	 * @return
	 */
	public static String makePhoneNumber(String src) {
		if (src == null) {
			return "";
		}
		if (src.length() == 8) {
			return src.replaceFirst("^([0-9]{4})([0-9]{4})$", "$1-$2");
		} else if (src.length() == 12) {
			return src.replaceFirst("(^[0-9]{4})([0-9]{4})([0-9]{4})$", "$1-$2-$3");
		}
		return src.replaceFirst("(^02|[0-9]{3})([0-9]{3,4})([0-9]{4})$", "$1-$2-$3");
	}

	/**
	 * @Desc : 바이트 체크 (영문 1, 한글 2, 특문 1)
	 * @Method Name : getByteLength
	 * @Date : 2019. 8. 8.
	 * @Author : 유성민
	 * @param txt
	 * @return
	 */
	public static int getByteLength(String txt) {
		// 바이트 체크 (영문 1, 한글 2, 특문 1)
		int en = 0;
		int ko = 0;
		int etc = 0;

		char[] txtChar = txt.toCharArray();
		for (int j = 0; j < txtChar.length; j++) {
			if (txtChar[j] >= 'A' && txtChar[j] <= 'z') {
				en++;
			} else if (txtChar[j] >= '\uAC00' && txtChar[j] <= '\uD7A3') {
				ko++;
				ko++;
			} else {
				etc++;
			}
		}

		int txtByte = en + ko + etc;
		return txtByte;
	}

	public static Map<String, Object> voToMapWithExcept(Object vo, String[] arrExceptList) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		BeanInfo info = Introspector.getBeanInfo(vo.getClass());
		for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
			Method reader = pd.getReadMethod();
			if (reader != null) {
				if (arrExceptList != null && arrExceptList.length > 0 && isContain(arrExceptList, pd.getName())) {
					continue;
				}
				result.put(pd.getName(), reader.invoke(vo));
			}
		}
		return result;
	}

	public static Object convertMapToObject(Map<String, ?> map, Object obj) {
		String keyAttribute = null;
		String setMethodString = "set";
		String methodString = null;
		Iterator itr = map.keySet().iterator();

		while (itr.hasNext()) {
			keyAttribute = (String) itr.next();
			methodString = setMethodString + keyAttribute.substring(0, 1).toUpperCase() + keyAttribute.substring(1);
			Method[] methods = obj.getClass().getDeclaredMethods();
			for (int i = 0; i < methods.length; i++) {
				if (methodString.equals(methods[i].getName())) {
					try {
						methods[i].invoke(obj, map.get(keyAttribute));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return obj;
	}

	public static Boolean isContain(String[] arrList, String name) {
		for (String arr : arrList) {
			if (contains(arr, name)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @Desc : 한글(EUC-KR)을 고려한 Substring
	 * @Method Name : strByteSubstring
	 * @Date : 2019. 8. 8.
	 * @Author : kiowa
	 * @param strData     : 문자열
	 * @param iStartPos   : 시작
	 * @param iByteLength : 문자 열 개수
	 * @return
	 */
	public static String strByteSubstring(String strData, int iStartPos, int iByteLength) {
		byte[] bytTemp = null;
		int iRealStart = 0;
		int iRealEnd = 0;
		int iLength = 0;
		int iChar = 0;

		try {
			// UTF-8로 변환하는경우 한글 2Byte, 기타 1Byte로 떨어짐
			bytTemp = strData.getBytes("EUC-KR");
			iLength = bytTemp.length;

			for (int iIndex = 0; iIndex < iLength; iIndex++) {
				if (iStartPos <= iIndex) {
					break;
				}
				iChar = (int) bytTemp[iIndex];
				if ((iChar > 127) || (iChar < 0)) {
					// 한글의 경우(2byte 통과처리)
					// 한글은 2Byte이기 때문에 다음 글자는 볼것도 없이 스킵한다
					iRealStart++;
					iIndex++;
				} else {
					// 기타 글씨(1Byte 통과처리)
					iRealStart++;
				}
			}

			iRealEnd = iRealStart;
			int iEndLength = iRealStart + iByteLength;
			for (int iIndex = iRealStart; iIndex < iEndLength; iIndex++) {
				iChar = (int) bytTemp[iIndex];
				if ((iChar > 127) || (iChar < 0)) {
					// 한글의 경우(2byte 통과처리)
					// 한글은 2Byte이기 때문에 다음 글자는 볼것도 없이 스킵한다
					iRealEnd++;
					iIndex++;
				} else {
					// 기타 글씨(1Byte 통과처리)
					iRealEnd++;
				}
			}
		} catch (Exception e) {

		}

		return strData.substring(iRealStart, iRealEnd);

	}

	public static String getChannelTitle(String serverName) {
		String title = "아트닷컴 - ABC마트 통합 온라인몰";
		if (serverName.indexOf("abc") > -1 || serverName.indexOf("abcmart") > -1) {
			title = "아트닷컴 - ABC마트 통합 온라인몰";
		} else if (serverName.indexOf("gs") > -1 || serverName.indexOf("grandstage") > -1) {
			title = "아트닷컴 - ABC마트 통합 온라인몰";
		} else if (serverName.indexOf("onthespot") > -1) {
			title = "On the spot";
		}

		return title;
	}
	
	public static String getChannelDescription(String serverName) {
		String description = "쇼핑의 경계를 넘는 ABC마트 통합 온라인몰 아트닷컴 A-RT.COM, ABC마트와 그랜드스테이지 제품을 한번에 아트닷컴에서 보자.";
		if (serverName.indexOf("abc") > -1 || serverName.indexOf("abcmart") > -1) {
			description = "ABC마트 공식 온라인몰, 여성,남성,키즈 다양한 카테고리 제품을 구매하세요.";
		} else if (serverName.indexOf("gs") > -1 || serverName.indexOf("grandstage") > -1) {
			description = "ABC마트 그랜드스테이지 공식 온라인몰, 나이키, 아디다스, 반스 등 프리미엄 제품을 만나보세요.";
		} else if (serverName.indexOf("onthespot") > -1) {
			description = "On the spot";
		}

		return description;
	}

}
