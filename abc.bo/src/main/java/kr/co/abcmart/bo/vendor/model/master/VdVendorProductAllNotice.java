package kr.co.abcmart.bo.vendor.model.master;

import kr.co.abcmart.bo.vendor.model.master.base.BaseVdVendorProductAllNotice;
import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.util.UtilsObject;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.common.validation.Validator;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.util.UtilsMasking;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class VdVendorProductAllNotice extends BaseVdVendorProductAllNotice implements Validator {

	/** 유형 (등록 : C 수정 : U) */
	private String type;

	/** 업체명 */
	private String vndrName;

	/** 등록자 */
	private String rgsterName;

	/** 등록자 Id */
	private String rgsterId;

	/** 수정자 */
	private String moderName;

	/** 수정자 Id */
	private String moderId;

	/** 업체구분 */
	private String vndrGbnType;

	@Override
	public void validate() throws ValidatorException {

		if (UtilsObject.isEmpty(getVndrNo())) { // 업체번호
			// validationMessage("vendor.valid.product.all.notice.vndrNoisnull");
		}
		if (UtilsObject.isEmpty(getAllNotcStartYmd())) { // 전체공지시작일
			validationMessage("vendor.valid.product.all.notice.allNotcStartYmd");
		}
		if (UtilsObject.isEmpty(getAllNotcEndYmd())) { // 전체공지종료일
			validationMessage("vendor.valid.product.all.notice.allNotcEndYmd");
		}
		if (UtilsText.isBlank(getAllNotcTitleText())) { // 전체공지제목
			validationMessage("vendor.valid.product.all.notice.allNotcTitleText");
		}

		if (getAllNotcStartYmd().after(getAllNotcEndYmd())) { // 날짜 유효성
			validationMessage("vendor.valid.product.all.notice.allNotcYmd");
		}

		if (UtilsText.isBlank(getAllNotcContText())) { // 전체공지내용
			validationMessage("vendor.valid.product.all.notice.allNotcContText");
		}
	}

	// 등록자 정보 마스킹 처리
	public String getRgsterInfo() {
		return UtilsText.concat(UtilsMasking.loginId(getRgsterId()), Const.L_PAREN,
				UtilsMasking.userName(getRgsterName()), Const.R_PAREN);
	}

	// 수정자 정보 마스킹 처리
	public String getModerInfo() {
		return UtilsText.concat(UtilsMasking.loginId(getModerId()), Const.L_PAREN,
				UtilsMasking.userName(getModerName()), Const.R_PAREN);
	}

}
