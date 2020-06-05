/**
 * 
 */
package kr.co.abcmart.common.util;

/**
 * @Desc : 배치 유틸 클래스
 * @FileName : UtilsSchedule.java
 * @Project : abc.common
 * @Date : 2019. 2. 1.
 * @Author : Kimyounghyun
 */
public class UtilsSchedule {

	/**
	 * @Desc : 사이트아이디, 트리거, 트리거그룹을 가지고 스케줄아이디를 생성한다.
	 * @Method Name : genSchedId
	 * @Date : 2019. 2. 1.
	 * @Author : Kimyounghyun
	 * @param siteId
	 * @param triggerName
	 * @param triggerGroup
	 * @return
	 */
	public static String genSchedId(String siteId, String triggerName, String triggerGroup) {
		return UtilsText.concat(siteId, "^", triggerName, "^", triggerGroup);
	}

}
