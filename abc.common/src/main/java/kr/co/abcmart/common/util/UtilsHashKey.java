/**
 * 
 */
package kr.co.abcmart.common.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.dao.DuplicateKeyException;

/**
 * @Desc : 난수키를 생성하는 유틸
 * @FileName : UtilsHashKey.java
 * @Project : abc.common
 * @Date : 2019. 3. 18.
 * @Author : Kimyounghyun
 */
public class UtilsHashKey {

	private static final String PREFIX = "AC";

	/**
	 * @Desc : "AC"로 시작하는 10자리 난수키를 생성한다.
	 * @Method Name : make10digitKey
	 * @Date : 2019. 3. 18.
	 * @Author : Kimyounghyun
	 * @return
	 * @throws Exception
	 */
	public static String make10digitKey() throws Exception {
		long time = System.nanoTime();
		String str = Long.toString(time, 36);
		String result = UtilsText.concat(PREFIX, str.substring(0, str.length() - 2));

		return result;
	}

	/**
	 * @Desc : digit에 해당하는 자릿수를 가지는 랜덤번호를 생성한다. isDuplicate true이면 중복을 허용한다.
	 * @Method Name : makeRandomNumber
	 * @Date : 2019. 3. 26.
	 * @Author : Kimyounghyun
	 * @param digit
	 * @param isDuplicate
	 * @return
	 * @throws Exception
	 */
	public static String makeRandomNumber(int digit, boolean isDuplicate) throws Exception {
		String result = "";
		Random random = new Random();

		for (int i = 0; i < digit; i++) {
			String s = Integer.toString(random.nextInt(10));

			if (isDuplicate) {
				result += s;
			} else {
				if (!result.contains(s)) {
					result += s;
				} else {
					i -= 1;
				}
			}
		}
		return result;
	}

	public static void main(String[] args) throws Exception {
//		testMake10digitKey();
		testMakeRandomNumber();
	}

	private static void testMakeRandomNumber() throws Exception {
		System.out.println(makeRandomNumber(4, true));
		System.out.println(makeRandomNumber(4, false));
		System.out.println(makeRandomNumber(6, true));
		System.out.println(makeRandomNumber(6, false));
		System.out.println(makeRandomNumber(8, true));
		System.out.println(makeRandomNumber(8, false));
	}

	private static void testMake10digitKey() throws Exception {
		// 중복확인 test
		Map map = new HashMap<>();
		String key = null;
		for (int i = 0; i <= 10000; i++) {
			key = make10digitKey();
			System.out.println(i + " : " + key + ", " + key.length());
			if (!map.containsKey(key)) {
				map.put(key, "a");
			} else {
				throw new DuplicateKeyException(key);
			}
		}

		if (!map.containsKey(key)) {
			map.put(key, "a");
		} else {
			throw new DuplicateKeyException(key);
		}
	}

}
