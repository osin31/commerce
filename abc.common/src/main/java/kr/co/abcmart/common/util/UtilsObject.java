package kr.co.abcmart.common.util;

import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;

public class UtilsObject extends ObjectUtils {

	/**
	 * 객체가 비어있지 않은지 검사.
	 * 
	 * @Desc :
	 * @Method Name : isNotEmpty
	 * @Date : 2019. 4. 29.
	 * @Author : tennessee
	 * @param object
	 * @return
	 */
	public static boolean isNotEmpty(@Nullable Object object) {
		return !isEmpty(object);
	}

}
