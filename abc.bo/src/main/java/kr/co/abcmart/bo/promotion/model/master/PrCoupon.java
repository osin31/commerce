package kr.co.abcmart.bo.promotion.model.master;

import java.sql.Time;
import java.sql.Timestamp;

import kr.co.abcmart.bo.promotion.model.master.base.BasePrCoupon;
import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.request.FileUpload;
import kr.co.abcmart.common.request.annotation.ParameterOption;
import kr.co.abcmart.common.util.UtilsDate;
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
public class PrCoupon extends BasePrCoupon implements Validator {
	// param
	private String paramIssueStartYmd;

	private String paramIssueEndYmd;

	private String paramIssueStartH;

	private String paramIssueStartM;

	private String paramIssueEndH;

	private String paramIssueEndM;

	private String paramValidStartYmd;

	private String paramValidEndYmd;

	private String paramValidStartH;

	private String paramValidStartM;

	private String paramValidEndH;

	private String paramValidEndM;

	private String paramDayStartH;

	private String paramDayStartM;

	private String paramDayEndH;

	private String paramDayEndM;

	private String[] deviceCodes;

	private String memberCouponCount;

	@ParameterOption(target = "vndrNo")
	private PrCouponVendorShareRate[] prCouponVendorShareRateArr;

	@ParameterOption(target = "chnnlNo")
	private PrCouponApplyChannel[] prCouponApplyChannelArr;

	@ParameterOption(target = "ctgrNo")
	private PrCouponApplyCategory[] prCouponApplyCategoryArr;

	@ParameterOption(target = "storeNo")
	private PrCouponApplyStore[] prCouponApplyStoreArr;

	@ParameterOption(target = "prdtNo")
	private PrCouponApplyProduct[] prCouponApplyProductArr;

	@ParameterOption(target = "brandNo")
	private PrCouponApplyBrand[] prCouponApplyBrandArr;

	private FileUpload excelUpload;

	private String[] stdCtgrNo;

	private String relayCpnName;

	// 회원
	private String cpnTypeCodeName;

	private String dispDate;

	private String deviceCodeName;

	private String chnnlName;

	private String cpnUseDtm;

	private String deviceName;

	private String cpnIssueDtm;

	private String orderNo;

	private String cpnStatCode;

	private String cpnStatCodeName;

	private String adminName;

	private String loginId;

	private String memberName;

	private String mbshpGradeCodeName;

	private String memberTypeCodeName;

	private String memberNo;

	private String[] memberNos;

	private String useYnVal;

	private Integer holdCpnSeq;

	private String[] cpnUseYns;

	/* 관리자 정보 */
	private String rgsterName;
	private String rgsterId;

	private String moderName;
	private String moderId;

	private String rgsterNo;
	private String moderNo;

	private String pageType;

	// 등록자 정보 마스킹 처리
	public String getRgsterInfo() {

		String maskingStr = "";
		if (UtilsText.equals(this.getPageType(), "L")) {
			maskingStr = UtilsText.concat(UtilsMasking.loginId(this.rgsterId), Const.L_PAREN,
					UtilsMasking.userName(this.rgsterName), Const.R_PAREN);
		} else {
			if (UtilsText.equals(LoginManager.getUserDetails().getMemberInfoMgmtYn(), Const.BOOLEAN_FALSE)) {
				maskingStr = UtilsText.concat(UtilsMasking.loginId(this.rgsterId), Const.L_PAREN,
						UtilsMasking.userName(this.rgsterName), Const.R_PAREN);
			} else {
				maskingStr = UtilsText.concat(this.rgsterId, Const.L_PAREN, this.rgsterName, Const.R_PAREN);
			}
		}

		return maskingStr;
	}

	// 수정자 정보 마스킹 처리
	public String getModerInfo() {
		String maskingStr = "";

		if (UtilsText.equals(this.getPageType(), "L")) {
			maskingStr = UtilsText.concat(UtilsMasking.loginId(this.moderId), Const.L_PAREN,
					UtilsMasking.userName(this.moderName), Const.R_PAREN);
		} else {
			if (UtilsText.equals(LoginManager.getUserDetails().getMemberInfoMgmtYn(), Const.BOOLEAN_FALSE)) {
				maskingStr = UtilsText.concat(UtilsMasking.loginId(this.moderId), Const.L_PAREN,
						UtilsMasking.userName(this.moderName), Const.R_PAREN);
			} else {
				maskingStr = UtilsText.concat(this.moderId, Const.L_PAREN, this.moderName, Const.R_PAREN);
			}
		}
		return maskingStr;
	}

	public String getValidDate() {
		String formatDate = null;
		if (UtilsText.equals(this.getValidTermGbnType(), "T")) {
			if (this.getValidStartDtm() != null && this.getValidEndDtm() != null) {
				formatDate = UtilsText.concat(UtilsDate.formatter("yyyy.MM.dd HH:mm", this.getValidStartDtm()), " ~ ",
						UtilsDate.formatter("yyyy.MM.dd HH:mm", this.getValidEndDtm()));
			}
		} else {
			if (this.getUseLimitDayCount() != null) {
				formatDate = UtilsText.concat("발급 후 ", String.valueOf(this.getUseLimitDayCount()), "일");
			}
		}

		return formatDate;
	}

	public String getDetailLoginId() {
		String maskingStr = "";
		if (UtilsText.equals(LoginManager.getUserDetails().getMemberInfoMgmtYn(), Const.BOOLEAN_FALSE)) {
			maskingStr = UtilsMasking.loginId(this.getLoginId());
		} else {
			maskingStr = this.getLoginId();
		}
		return maskingStr;
	}

	public String getDetailMemberName() {
		String maskingStr = "";
		if (UtilsText.equals(LoginManager.getUserDetails().getMemberInfoMgmtYn(), Const.BOOLEAN_FALSE)) {
			maskingStr = UtilsMasking.userName(this.getMemberName());
		} else {
			maskingStr = this.getMemberName();
		}
		return maskingStr;
	}

	public Timestamp getParamIssueStartDtm() {
		Timestamp formatDate = null;
		if (UtilsText.isNotBlank(this.getParamIssueStartYmd()) && UtilsText.isNotBlank(this.getParamIssueStartH())
				&& UtilsText.isNotBlank(this.getParamIssueStartM())) {
			formatDate = new Timestamp(
					UtilsDate.parseDate(UtilsText.concat(this.getParamIssueStartYmd(), " ", this.getParamIssueStartH(),
							":", this.getParamIssueStartM(), ":", "00"), "yyyy.MM.dd HH:mm:ss").getTime());
		}
		return formatDate;
	}

	public Timestamp getParamIssueEndDtm() {
		Timestamp formatDate = null;
		if (UtilsText.isNotBlank(this.getParamIssueEndYmd()) && UtilsText.isNotBlank(this.getParamIssueEndH())
				&& UtilsText.isNotBlank(this.getParamIssueEndM())) {
			formatDate = new Timestamp(
					UtilsDate.parseDate(UtilsText.concat(this.getParamIssueEndYmd(), " ", this.getParamIssueEndH(), ":",
							this.getParamIssueEndM(), ":", "59"), "yyyy.MM.dd HH:mm:ss").getTime());
		}
		return formatDate;
	}

	public Timestamp getParamValidStartDtm() {
		Timestamp formatDate = null;
		if (UtilsText.isNotBlank(this.getParamValidStartYmd()) && UtilsText.isNotBlank(this.getParamValidStartH())
				&& UtilsText.isNotBlank(this.getParamValidStartM())) {
			formatDate = new Timestamp(
					UtilsDate.parseDate(UtilsText.concat(this.getParamValidStartYmd(), " ", this.getParamValidStartH(),
							":", this.getParamValidStartM(), ":", "00"), "yyyy.MM.dd HH:mm:ss").getTime());
		}
		return formatDate;
	}

	public Timestamp getParamValidEndDtm() {
		Timestamp formatDate = null;
		if (UtilsText.isNotBlank(this.getParamValidEndYmd()) && UtilsText.isNotBlank(this.getParamValidEndH())
				&& UtilsText.isNotBlank(this.getParamValidEndM())) {
			formatDate = new Timestamp(
					UtilsDate.parseDate(UtilsText.concat(this.getParamValidEndYmd(), " ", this.getParamValidEndH(), ":",
							this.getParamValidEndM(), ":", "59"), "yyyy.MM.dd HH:mm:ss").getTime());
		}
		return formatDate;
	}

	public Time getParamDayStartTime() {
		Time formatDate = null;
		if (this.getParamDayStartH() != null && this.getParamDayStartM() != null) {
			formatDate = new Time(UtilsDate
					.parseDate(UtilsText.concat(this.getParamDayStartH(), ":", this.getParamDayStartM(), ":", "00"),
							"HH:mm:ss")
					.getTime());
		}
		return formatDate;
	}

	public Time getParamDayEndTime() {
		Time formatDate = null;
		if (this.getParamDayEndH() != null && this.getParamDayEndM() != null) {
			formatDate = new Time(UtilsDate
					.parseDate(UtilsText.concat(this.getParamDayEndH(), ":", this.getParamDayEndM(), ":", "59"),
							"HH:mm:ss")
					.getTime());
		}
		return formatDate;
	}

	public String getDscntText() {
		String dscntext = "";
		if (UtilsText.isNotBlank(this.getDscntType()) && this.getDscntValue() != null) {
			if (UtilsText.equals(this.getDscntType(), "R")) {
				dscntext = UtilsText.concat(String.valueOf(this.getDscntValue()), "%");
			} else if (UtilsText.equals(this.getDscntType(), "A")) {
				dscntext = UtilsText.concat(String.valueOf(this.getDscntValue()), "원");
			} else {
				dscntext = String.valueOf(this.getDscntValue());
			}
		}
		return dscntext;
	}

	public String getCpnUseGbnTypeName() {
		String cpnUseGbnTypeName = "";
		if (UtilsText.isNotBlank(this.getCpnUseGbnType())) {
			if (UtilsText.equals(this.getCpnUseGbnType(), "O")) {
				cpnUseGbnTypeName = "온라인";
			} else if (UtilsText.equals(this.getCpnUseGbnType(), "P")) {
				cpnUseGbnTypeName = "오프라인";
			} else if (UtilsText.equals(this.getCpnUseGbnType(), "A")) {
				cpnUseGbnTypeName = "온/오프라인";
			}
		}
		return cpnUseGbnTypeName;
	}

	public String getCpnManage() {
		String cpnManage = "";

		if (UtilsText.isNotBlank(this.getUseYnVal())) {
			if (UtilsText.equals(this.getUseYnVal(), "POSSIBLE_STOP")) {
				cpnManage = "사용중지";
			} else if (UtilsText.equals(this.getUseYnVal(), "RE_ISSUE")) {
				cpnManage = "재발급";
			} else if (UtilsText.equals(this.getUseYnVal(), "UNPAUSE")) {
				cpnManage = "중지해제";
			} else {
				cpnManage = "-";
			}
		}

		return cpnManage;
	}

	@Override
	public void validate() throws ValidatorException {
		if (UtilsText.isBlank(getCpnName())) {
			validationFieldMessage("promotion.valid.coupon.cpnName", "cpnName");
		}

		if (getPrCouponVendorShareRateArr() != null && getPrCouponVendorShareRateArr().length > 0) {
			for (PrCouponVendorShareRate prCouponVendorShareRateArr : getPrCouponVendorShareRateArr()) {
				if (prCouponVendorShareRateArr.getShareRate() == null) {
					validationMessage("promotion.valid.coupon.shareRate");
				}
			}
		}

		if (UtilsText.isNotBlank(getMemberCouponCount()) && UtilsText.equals(getMemberCouponCount(), "0")) {
			if (UtilsText.isBlank(getCpnTypeCode())) {
				validationFieldMessage("promotion.valid.coupon.cpnTypeCode", "cpnTypeCode");
			}

			if (UtilsText.isNotBlank(getValidTermGbnType()) && !UtilsText.equals(getValidTermGbnType(), "D")) {
				if (UtilsText.isBlank(getParamValidStartYmd())) {
					validationFieldMessage("promotion.valid.coupon.paramValidYmd", "paramValidStartYmd");
				}

				if (UtilsText.isBlank(getParamValidEndYmd())) {
					validationFieldMessage("promotion.valid.coupon.paramValidYmd", "paramValidEndYmd");
				}
			}

			if (UtilsText.isBlank(getParamIssueStartYmd())) {
				validationFieldMessage("promotion.valid.coupon.paramIssueYmd", "paramIssueStartYmd");
			}

			if (UtilsText.isBlank(getParamIssueEndYmd())) {
				validationFieldMessage("promotion.valid.coupon.paramIssueYmd", "paramIssueEndYmd");
			}

			if (UtilsText.isNotBlank(getUsePlaceGbnType()) && !UtilsText.equals(getUsePlaceGbnType(), "F")) {
				if (getDeviceCodes() == null || getDeviceCodes().length == 0) {
					validationMessage("promotion.valid.coupon.deviceCodes");
				}

				if (getPrCouponApplyChannelArr() == null) {
					validationMessage("promotion.valid.coupon.chnnlNo");
				}
			}

			if (UtilsText.isNotBlank(getCpnTypeCode())) {
				// 할인쿠폰, 제휴사 할인쿠폰, 품절보상쿠폰
				if (UtilsText.equals(getCpnTypeCode(), CommonCode.CPN_TYPE_CODE_DISCOUNT)
						|| UtilsText.equals(getCpnTypeCode(), CommonCode.CPN_TYPE_CODE_SOLD_OUT_COMPENSATION)
						|| UtilsText.equals(getCpnTypeCode(), CommonCode.CPN_TYPE_CODE_DISCOUNT_AFFILIATES)) {
					if (getDscntValue() == null) {
						validationFieldMessage("promotion.valid.coupon.dscntValue", "dscntValue");
					}
				}

				if (UtilsText.equals(getCpnTypeCode(), CommonCode.CPN_TYPE_CODE_DISCOUNT_AFFILIATES)) {
					if (UtilsText.isBlank(getAffltsCode())) {
						validationFieldMessage("promotion.valid.coupon.affltsCode", "affltsCode");
					}
				}

				if (!UtilsText.equals(getCpnTypeCode(), CommonCode.CPN_TYPE_CODE_DOUBLE_POINT)
						&& !UtilsText.equals(getCpnTypeCode(), CommonCode.CPN_TYPE_CODE_FREE_RETURN)
						&& !UtilsText.equals(getCpnTypeCode(), CommonCode.CPN_TYPE_CODE_FREE_CHANGE)) {
					if (!UtilsText.equals(getCpnTypeCode(), CommonCode.CPN_TYPE_CODE_FREE_DELIVERY)) {
						if (getLimitDscntRate() == null) {
							validationFieldMessage("promotion.valid.coupon.limitDscntRate", "limitDscntRate");
						}
					}

					if (getMinLimitSellAmt() == null) {
						validationFieldMessage("promotion.valid.coupon.minLimitSellAmt", "minLimitSellAmt");
					}
				}
			}

			/*
			 * if (UtilsText.isNotBlank(getCpnApplyType())) { if
			 * (UtilsText.equals(getCpnApplyType(), "C")) { if
			 * (getPrCouponApplyCategoryArr() != null) { for (PrCouponApplyCategory
			 * prCouponApplyCategory : getPrCouponApplyCategoryArr()) { if
			 * (UtilsText.isBlank(prCouponApplyCategory.getCtgrNo())) {
			 * validationMessage("promotion.valid.coupon.ctgrNo"); } } } } }
			 */
		}

	}
}