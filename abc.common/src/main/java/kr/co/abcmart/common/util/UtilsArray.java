package kr.co.abcmart.common.util;

import java.util.List;

public class UtilsArray extends org.apache.commons.lang3.ArrayUtils {

	/**
	 * List type 의 Collection 객체가 null 이거나 size()가 0 이면 빈 값으로 확인
	 *
	 * @param list List type의 Collection 객체
	 * @return boolean
	 */
	public static boolean isEmpty(List list) {

		if (isNull(list) || list.isEmpty()) {
			return true;
		} else if (!isNull(list) && list.size() > 0) {
			return false;
		}
		return false;
	}

	/**
	 * List type 의 Collection 객체가 null 인지 여부 확인
	 * 
	 * @param list List type의 Collection 객체
	 * @return boolean
	 */
	public static boolean isNull(List list) {
		if (list == null) {
			return true;
		}
		return false;
	}

}
