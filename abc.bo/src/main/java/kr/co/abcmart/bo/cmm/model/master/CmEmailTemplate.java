package kr.co.abcmart.bo.cmm.model.master;

import kr.co.abcmart.bo.cmm.model.master.base.BaseCmEmailTemplate;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.util.UtilsMasking;
import kr.co.abcmart.util.UtilsText;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class CmEmailTemplate extends BaseCmEmailTemplate {

	/**
	 * 이메일 유형 코드
	 */
	private String emailTypeCodeName;

	/**
	 * 발송일
	 */
	private String sendDay;

	/**
	 * 발송시간
	 */
	private String sendHour;

	/**
	 * 발송분
	 */
	private String sendMinute;

	/**
	 * 수정자 명
	 */
	private String moderName;

	/**
	 * 수정자 ID
	 */
	private String moderId;

	/**
	 * 사이트 이름
	 */
	private String siteName;

	/**
	 * @Desc : 수정자 정보(관리자ID(관리자명))
	 * @Method Name : getMaskingModerName
	 * @Date : 2019. 2. 25.
	 * @Author : kiowa
	 * @return
	 */
	public String getDispModerName() {
		String morderName = "";
		if (LoginManager.isPersonalInfoManager()) {
			if (this.moderId != null) {
				morderName = UtilsText.concat(this.moderId, Const.L_PAREN, this.moderName, Const.R_PAREN);
			}
		} else {
			if (this.moderId != null) {
				morderName = getMaskingDispModerName();
			}
		}
		return morderName;
	}

	public String getMaskingDispModerName() {
		return UtilsText.concat(UtilsMasking.loginId(this.moderId), Const.L_PAREN,
				UtilsMasking.userName(this.moderName), Const.R_PAREN);
	}

	/**
	 * @Desc : 입력한 발송일시 문자열을
	 * @Method Name : getSendDateTime
	 * @Date : 2019. 3. 15.
	 * @Author : kiowa
	 * @return
	 */
	public String getSendDateTime() {
		return sendDay + " " + sendHour + ":" + sendMinute + ":00";
	}

}
