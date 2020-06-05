/**
 * 
 */
package kr.co.abcmart.bo.claim.vo;

import kr.co.abcmart.bo.claim.model.master.OcClaimPayment;
import kr.co.abcmart.common.bean.BaseBean;
import kr.co.abcmart.common.request.annotation.ParameterOption;
import kr.co.abcmart.common.util.UtilsMasking;
import kr.co.abcmart.common.util.UtilsObject;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc :
 * @FileName : OcClaimPaymentExcelVo.java
 * @Project : abc.bo
 * @Date : 2019. 3. 6.
 * @Author : 이강수
 */

@Slf4j
@Data
public class OcClaimPaymentExcelVo extends BaseBean {

	private String redempRfndGbnType;

	private String clmSiteName;

	private String clmGbnCodeName;

	private String ocrncRsnCodeName;

	private String clmNo;

	private String orgOrderNo;

	private String loginId;

	private String memberNo;

	private String memberName;

	private String buyerName;
	
	// 주문자명
	public String getBuyerName() {
		
		if (LoginManager.isPersonalInfoManager()) {
			return buyerName;
		} else {
			return UtilsMasking.userName(buyerName);
		}
	}

	private String orderMember;

	public String getOrderMember() {
		if (LoginManager.isPersonalInfoManager()) {
			if (UtilsText.equals(memberNo, Const.NON_MEMBER_NO)) {
				return buyerName;
			} else {
				return UtilsText.concat(loginId, Const.L_PAREN, memberName, Const.R_PAREN);
			}
		} else {
			if (UtilsText.equals(memberNo, Const.NON_MEMBER_NO)) {
				return UtilsMasking.userName(buyerName);
			} else {
				return UtilsText.concat(UtilsMasking.loginId(loginId), Const.L_PAREN, UtilsMasking.userName(memberName),
						Const.R_PAREN);
			}
		}
	}

	private java.lang.Integer pymntAmt;

	private String pymntStatCode;

	private String pymntStatCodeName;

	private String strNowDtm;

	private String strVrtlAcntExprDtm;

	public String getPymntStatCodeName() {
		if (UtilsText.equals(pymntStatCode, CommonCode.PYMNT_STAT_CODE_REDEMP_ACCEPT)) {
			if (UtilsObject.isNotEmpty(strVrtlAcntExprDtm)) {
				if (Integer.parseInt(strNowDtm) - Integer.parseInt(strVrtlAcntExprDtm) > 0) {
					return "입금기한만료";
				} else {
					return pymntStatCodeName;
				}
			} else {
				return pymntStatCodeName;
			}
		} else {
			return pymntStatCodeName;
		}
	}

	private String procImpsbltYn;

	private String procImpsbltRsnText;

	private String bankCodeName;

	private String acntNoText;

	private String clmAcnt;

	public String getClmAcnt() {
		if (LoginManager.isPersonalInfoManager()) {
			return getAcntNoText();
		} else {
			return UtilsMasking.bankAccoutNumber(getAcntNoText());
		}
	}

	private String acntHldrName;

	public String getAcntHldrName() {
		if (LoginManager.isPersonalInfoManager()) {
			return this.acntHldrName;
		} else {
			return UtilsMasking.userName(this.acntHldrName);
		}
	}

	private String rgstDtm;

	private String clmHandlerId;
	private String clmHandlerName;
	private String clmHandler;

	public String getClmHandler() {
		if (LoginManager.isPersonalInfoManager()) {
			return UtilsText.concat(this.clmHandlerName, Const.L_PAREN, this.clmHandlerId, Const.R_PAREN);
		} else {
			return UtilsText.concat(UtilsMasking.userName(this.clmHandlerName), Const.L_PAREN,
					UtilsMasking.loginId(this.clmHandlerId), Const.R_PAREN);
		}
	}

	private String modDtm;

	@ParameterOption(target = "clmNo")
	private OcClaimPayment[] clmNos;

	private String strPymntAmt;

	private String redempAmtRndmProcYn;

	public String getRedempAmtRndmProcYn() {
		if (UtilsText.equals(redempAmtRndmProcYn, Const.BOOLEAN_FALSE)) {
			return "임의처리안함";
		} else if (UtilsText.equals(redempAmtRndmProcYn, Const.BOOLEAN_TRUE)) {
			return "임의처리";
		}
		return redempAmtRndmProcYn;
	}

	// 결제수단코드명 여러개 한번에 보여주기
	private String pymntMeansCodeStuff;
}
