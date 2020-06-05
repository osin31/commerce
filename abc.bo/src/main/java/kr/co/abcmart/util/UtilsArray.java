package kr.co.abcmart.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

public class UtilsArray extends kr.co.abcmart.common.util.UtilsArray {

	/**
	 * @Desc : java stream distinctByKey
	 * @Method Name : distinctByKey
	 * @Date : 2019. 9. 10.
	 * @Author : 유성민
	 * @param keyExtractor
	 * @return
	 */
	public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
		Map<Object, Boolean> seen = new ConcurrentHashMap<>();
		return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}
}
