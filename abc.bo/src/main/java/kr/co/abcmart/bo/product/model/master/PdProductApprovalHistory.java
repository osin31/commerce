package kr.co.abcmart.bo.product.model.master;

import java.sql.Timestamp;

import kr.co.abcmart.bo.product.model.master.base.BasePdProductApprovalHistory;
import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.common.validation.Validator;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.util.UtilsMasking;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class PdProductApprovalHistory extends BasePdProductApprovalHistory implements Validator {

	/** 승인상태코드 */
	String aprvStatCode;

	/** 등록자번호 */
	String rgsterNo;
	/** 등록일시 */
	Timestamp rgstDtm;

	/** 승인자ID */
	private String aprverId;
	/** 승인자명 */
	private String aprverName;
	/** 요청자ID */
	private String reqtrId;
	/** 요청자명 */
	private String reqtrName;

	/**
	 * @Desc : 개인정보 마스킹 수행
	 * @Method Name : setPrivacy
	 * @Date : 2019. 5. 14.
	 * @Author : tennessee
	 */
	public void setPrivacy() {
		if (UtilsText.equals(LoginManager.getUserDetails().getMemberInfoMgmtYn(), Const.BOOLEAN_FALSE)) {
			// 개인정보 접근 권한이 없는 경우
			this.setAprverId(UtilsMasking.loginId(this.getAprverId()));
			this.setAprverName(UtilsMasking.userName(this.getAprverName()));
			this.setReqtrId(UtilsMasking.loginId(this.getReqtrId()));
			this.setReqtrName(UtilsMasking.userName(this.getReqtrName()));
		}
	}

	@Override
	public void validate() throws ValidatorException {
		if (this.getAprvStatCode().equals(CommonCode.APRV_STAT_CODE_REJECT)) {
			if (UtilsText.isBlank(this.getReturnRsnText())) { // 반려사유
//				validationMessage("product.valid.product.approval.history.returnRsnText");
				validationFieldMessage("product.valid.product.approval.history.returnRsnText", "returnRsnText");
			}
		} else {
			this.setReturnRsnText(null);
		}
	}

}
