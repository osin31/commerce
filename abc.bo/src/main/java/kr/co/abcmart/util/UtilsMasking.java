/**
 * 
 */
package kr.co.abcmart.util;

import kr.co.abcmart.common.util.UtilsObject;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;

/**
 * @Desc :
 * @FileName : UtilsMasking.java
 * @Project : abc.bo
 * @Date : 2019. 2. 26.
 * @Author : Kimyounghyun
 */
public class UtilsMasking extends kr.co.abcmart.common.util.UtilsMasking {

	/**
	 * 상세페이지 등록/수정자 마스킹 처리
	 * 
	 * @Desc :
	 * @Method Name : adminName
	 * @Date : 2019. 7. 29.
	 * @Author : SANTA
	 * @param id   : 관리자 아이디
	 * @param name : 관리자명
	 * @return
	 */
	public static String adminName(String id, String name) {

		if (UtilsObject.isNotEmpty(id) && UtilsObject.isNotEmpty(name)) {
			if (UtilsText.equals(Const.BOOLEAN_FALSE, LoginManager.getUserDetails().getMemberInfoMgmtYn())) { // 권한 확인
				return UtilsText.concat(UtilsMasking.loginId(id), Const.L_PAREN, UtilsMasking.userName(name),
						Const.R_PAREN);
			} else {
				return UtilsText.concat(id, Const.L_PAREN, name, Const.R_PAREN);
			}
		}

		return "";
	}

	/**
	 * @Desc : IBSheet 마스킹 처리
	 * @Method Name : gridMasking
	 * @Date : 2019. 8. 2.
	 * @Author : hsjhsj19
	 * @param id
	 * @param name
	 * @return
	 */
	public static String gridMasking(String id, String name) {

		if (UtilsObject.isNotEmpty(id) && UtilsObject.isNotEmpty(name)) {
			return UtilsText.concat(UtilsMasking.loginId(id), Const.L_PAREN, UtilsMasking.userName(name),
					Const.R_PAREN);
		}

		return "";
	}

}
