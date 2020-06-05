package kr.co.abcmart.bo.cmm.model.master;

import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.abcmart.bo.cmm.model.master.base.BaseCmMessageTemplate;
import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.validation.Validator;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.util.UtilsMasking;
import kr.co.abcmart.util.UtilsText;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class CmMessageTemplate extends BaseCmMessageTemplate implements Validator {

	private static final long serialVersionUID = 1042520557976080451L;

	// 작성자 명
	private String rgsterName;

	// 작성자 id
	private String rgsterId;

	// 수정자 명
	@JsonIgnore
	private String moderName;

	// 수정자 id
	private String moderId;

	/**
	 * 문자 템플릿 유형 코드명
	 */
	private String mesgTypeCodeName;

	/**
	 * 문자 템플릿 발송 유형 코드명
	 */
	private String sendTypeCodeName;

	/**
	 * 사이트 명
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
		log.debug("moderId : {}", this.moderId);
		log.debug("moderName : {}", this.moderName);
		return morderName;
	}

	public String getMaskingDispModerName() {
		return UtilsText.concat(UtilsMasking.loginId(this.moderId), Const.L_PAREN,
				UtilsMasking.userName(this.moderName), Const.R_PAREN);
	}

	@Override
	public void validate() throws ValidatorException {
		// TODO
	}

}
