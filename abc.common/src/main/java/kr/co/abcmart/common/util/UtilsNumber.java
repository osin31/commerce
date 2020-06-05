package kr.co.abcmart.common.util;

import java.util.Random;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilsNumber extends NumberUtils {

	public static final Logger logger = LoggerFactory.getLogger(UtilsNumber.class);

	/**
	 * Integer 정수를 랜덤으로 생성,
	 * 
	 * @return int
	 */
	public static int randomInt() {
		return randomInt(-1);
	}

	/**
	 * Integer 정수를 랜덤으로 생성, 난수 범위를 지정 하면 해당 숫자 사이의 임의의 번호 생성
	 * 
	 * @param range
	 * @return
	 */
	public static int randomInt(int range) {
		Random randomGenerator = new Random();
		return (range > -1) ? randomGenerator.nextInt(range) : randomGenerator.nextInt();

	}

	public static long randomLong() {
		Random randomGenerator = new Random();
		long randomLongUniform = randomGenerator.nextLong();

		return randomLongUniform;
	}

	public static double randomDouble() {
		Random randomGenerator = new Random();
		double randomDoubleUniform = randomGenerator.nextDouble();

		return randomDoubleUniform;
	}

	public static float randomFloat() {
		Random randomGenerator = new Random();
		float randomFloatUniform = randomGenerator.nextFloat();

		return randomFloatUniform;
	}

	public static String toStr(int integer) {
		return Integer.toString(integer);
	}

	public static String toStr(long longNumber) {
		return Long.toString(longNumber);
	}

	public static String toStr(double doubleNumber) {
		return Double.toString(doubleNumber);
	}

	public static String toStr(float floatNumber) {
		return Float.toString(floatNumber);
	}

	/**
	 * 
	 * @Desc : 인자값으로 전달된 모든 숫자를 더한 결과를 반환
	 * @Method Name : sum
	 * @Date : 2019. 4. 3.
	 * @Author : tennessee
	 * @param integers
	 * @return
	 */
	public static Integer sum(Integer... integers) {
		Integer result = 0;
		for (Integer i : integers) {
			result = Integer.sum(result, i);
		}
		return result;
	}
}
