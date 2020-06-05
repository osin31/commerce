package kr.co.abcmart.bo.promotion.model.master;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import kr.co.abcmart.bo.promotion.model.master.base.BasePrPromotion;
import kr.co.abcmart.common.config.Config;
import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.request.FileUpload;
import kr.co.abcmart.common.request.annotation.ParameterOption;
import kr.co.abcmart.common.util.UtilsDate;
import kr.co.abcmart.common.util.UtilsDevice;
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
public class PrPromotion extends BasePrPromotion implements Validator {

	private String deviceCodeName;

	private String chnnlName;

	private String promoTypeCodeName;

	private String promoProgressStatus;

	private String paramPromoStartYmd;

	private String paramPromoEndYmd;

	private String paramPromoStartH;

	private String paramPromoStartM;

	private String paramPromoEndH;

	private String paramPromoEndM;

	private String[] deviceCodes;

	private String[] mbshpGradeCodes;

	private String empYn;

	@ParameterOption(target = "chnnlNo")
	private PrPromotionTargetChannel[] prPromotionTargetChannelArr;

	@ParameterOption(target = "memberTypeCode")
	private PrPromotionTargetGrade[] prPromotionTargetGradeArr;

	@ParameterOption(target = "ctgrNo")
	private PrPromotionTargetCategory[] prPromotionTargetCategoryArr;

	@ParameterOption(target = "brandNo")
	private PrPromotionTargetBrand[] prPromotionTargetBrandArr;

	@ParameterOption(target = "dscntRate")
	private PrPromotionMultiBuyDiscount[] prPromotionMultiBuyDiscountArr;

	@ParameterOption(target = "imageName")
	private PrPromotionImage[] prPromotionImageArr;

	@ParameterOption(target = "prdtNo")
	private PrPromotionTargetProduct[] prPromotionTargetProductArr;

	@ParameterOption(target = "prdtNo")
	private PrPromotionTargetProduct[] prPromotionTargetGiftArr;

	FileUpload pcBannerImg;

	FileUpload mobileBannerImg;

	private String totalOrdCount;

	private String totalOrdQty;

	private String totalOrdPayment;

	private String siteName;

	private String orderNo;

	private String productName;

	private String productCount;

	private String ordNormalAmt;

	private String ordPayment;

	private String ordDscntAmt;

	private String orderStatCodeName;

	private List<PrPromotionTargetGrade> prPromotionTargetGradeList;

	private List<PrPromotionTargetDevice> prPromotionTargetDeviceList;

	private List<PrPromotionTargetProduct> prPromotionTargetProductList;

	private String memberTypeCodeName;

	/* 관리자 정보 */
	private String rgsterName;
	private String rgsterId;

	private String moderName;
	private String moderId;

	private String pageType;

	private int excelIndex;

	private String orderPrdtStatCode;

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

	public Timestamp getParamPromoStartDtm() {
		Timestamp formatDate = null;
		if (UtilsText.isNotBlank(this.getParamPromoStartYmd()) && UtilsText.isNotBlank(this.getParamPromoStartH())
				&& UtilsText.isNotBlank(this.getParamPromoStartM())) {
			formatDate = new Timestamp(
					UtilsDate.parseDate(UtilsText.concat(this.getParamPromoStartYmd(), " ", this.getParamPromoStartH(),
							":", this.getParamPromoStartM(), ":", "00"), "yyyy.MM.dd HH:mm:ss").getTime());
		}
		return formatDate;
	}

	public Timestamp getParamPromoEndDtm() {
		Timestamp formatDate = null;
		if (UtilsText.isNotBlank(this.getParamPromoEndYmd()) && UtilsText.isNotBlank(this.getParamPromoEndH())
				&& UtilsText.isNotBlank(this.getParamPromoEndM())) {
			formatDate = new Timestamp(
					UtilsDate.parseDate(UtilsText.concat(this.getParamPromoEndYmd(), " ", this.getParamPromoEndH(), ":",
							this.getParamPromoEndM(), ":", "59"), "yyyy.MM.dd HH:mm:ss").getTime());
		}
		return formatDate;
	}

	public String getSummaryProductName() {
		String summaryProductName = "";
		if (UtilsText.isNotBlank(this.getProductCount()) && UtilsText.isNotBlank(this.getProductName())) {
			summaryProductName = UtilsText.concat(this.getProductName(), " 외 ", this.getProductCount(), "건");
		}

		return summaryProductName;
	}

	/**
	 *
	 * @Desc : 프로모션 디바이스 목록 조합
	 * @Method Name : getPromotionTargetDevice
	 * @Date : 2019. 5. 29.
	 * @Author : hsjhsj19
	 * @return
	 */
	public String getPromotionTargetDevice() {
		String deviceHTML = "";
		if (this.getPrPromotionTargetDeviceList() != null) {
			List<String> codeNameList = new ArrayList<String>();
			for (PrPromotionTargetDevice device : this.getPrPromotionTargetDeviceList()) {
				codeNameList.add("<u onclick=\"window.open(\'"
						+ this.getUrl(device.getPrdtNo(), device.getChnnlNo(), device.getDeviceCode())
						+ "\', \'미리보기\', \'width="
						+ (CommonCode.DEVICE_PC.equals(device.getDeviceCode()) ? "1700, height=970" : "450, height=700")
						+ ", toolbar=no, menubar=no, scrollbars=no, resizable=yes\')\">" + device.getDeviceName()
						+ "</u>");
			}
			deviceHTML = String.join(" ", codeNameList);
		}

		return deviceHTML;
	}

	/**
	 *
	 * @Desc : 프로모션 회원 등급 목록 조합
	 * @Method Name : getPromotionTargetDevice
	 * @Date : 2019. 5. 29.
	 * @Author : hsjhsj19
	 * @return
	 */
	public String getPromotionMemberTypeCodeName() {
		List<String> codeNameList = new ArrayList<String>();
		if (this.getPrPromotionTargetGradeList() != null) {
			for (PrPromotionTargetGrade prPromotionTargetGrade : this.getPrPromotionTargetGradeList()) {
				codeNameList.add(prPromotionTargetGrade.getMemberTypeCodeName());
			}
		}

		return String.join(",", codeNameList);
	}

	@Override
	public void validate() throws ValidatorException {
		if (UtilsText.isBlank(this.getPromoName())) {
			validationFieldMessage("promotion.valid.promotion.promoName", "promoName");
		}

		if (UtilsText.isNotBlank(this.getPromoProgressStatus())
				&& !UtilsText.equals(this.getPromoProgressStatus(), "ing")) {
			// none(신규), wait(대기,시작전) 일때 검사
			// ing(진행중)일 때는 script 상 수정 불가처리 end(종료) 일 때는 저장버튼 없음므로 제외

			if (UtilsText.isBlank(this.getPromoTypeCode())) {
				validationFieldMessage("promotion.valid.promotion.promoTypeCode", "promoTypeCode");
			}

			if (this.getPrPromotionTargetChannelArr() == null) {
				validationFieldMessage("promotion.valid.coupon.chnnlNo", "chnnlNo");
			}

			if (UtilsText.isBlank(this.getParamPromoStartYmd())) {
				validationFieldMessage("promotion.valid.promotion.promoYmd", "paramPromoStartYmd");
			}

			if (UtilsText.isBlank(this.getParamPromoEndYmd())) {
				validationFieldMessage("promotion.valid.promotion.promoYmd", "paramPromoEndYmd");
			}

			if (this.getPrPromotionTargetGradeArr() == null && !UtilsText.equals(this.getEmpYn(), "Y")) {
				validationFieldMessage("promotion.valid.promotion.memberTypeCode", "memberTypeCode");
			}

			if (UtilsText.isBlank(this.getPromoTypeCode())) {
				if (UtilsText.equals(this.getPromoTypeCode(), CommonCode.PROMO_TYPE_CODE_ONE_PLUS_ONE)) {
					/*
					 * if (this.getStdrSellAmt() != null) {
					 * validationMessage("promotion.valid.promotion.stdrSellAmt"); }
					 */
				} else if (UtilsText.equals(this.getPromoTypeCode(),
						CommonCode.PROMO_TYPE_CODE_DISCOUNT_MULTI_SHOUES)) {
					if (this.getPrPromotionMultiBuyDiscountArr() != null
							&& this.getPrPromotionMultiBuyDiscountArr().length > 0) {
						for (PrPromotionMultiBuyDiscount prPromotionMultiBuyDiscount : this
								.getPrPromotionMultiBuyDiscountArr()) {
							if (prPromotionMultiBuyDiscount.getDscntRate() == null) {
								validationFieldMessage("promotion.valid.coupon.shareRate", "shareRate");
							}
						}
					}
				} else if (UtilsText.equals(this.getPromoTypeCode(), CommonCode.PROMO_TYPE_CODE_DISCOUNT_IMMEDIATELY)
						|| UtilsText.equals(this.getPromoTypeCode(), CommonCode.PROMO_TYPE_CODE_SPECIAL_PRICE_TIME)) {
					if (this.getImdtlDscntValue() != null) {
						validationFieldMessage("promotion.valid.promotion.imdtlDscntValue", "imdtlDscntValue");
					}
				} else if (UtilsText.equals(this.getPromoTypeCode(),
						CommonCode.PROMO_TYPE_CODE_DISCOUNT_IMMEDIATELY_AFFILIATES)) {
					/*
					 * if (UtilsText.isBlank(this.getAffltsCode())) {
					 * validationMessage("promotion.valid.promotion.affltsCode"); }
					 */
					if (this.getImdtlDscntValue() != null) {
						validationFieldMessage("promotion.valid.promotion.imdtlDscntValue", "imdtlDscntValue");
					}
				}
			}
		}

	}

	/**
	 * @Desc : 상품 URL 반환 서비스
	 * @Method Name : getUrl
	 * @Date : 2019. 10. 2.
	 * @Author : hsjhsj19
	 * @param prdtNo
	 * @param chnnlNo
	 * @param deviceCode
	 * @return
	 */
	public String getUrl(String prdtNo, String chnnlNo, String deviceCode) {
		StringBuffer url = new StringBuffer();

		String uri = "/product";
		String parameter = "?prdtNo=" + prdtNo;

		url.append(this.getDomain(String.valueOf(chnnlNo), deviceCode));
		url.append(uri);
		url.append(parameter);

		return url.toString();
	}

	/**
	 * @Desc : 채널에 해당하는 도메인 반환 서비스
	 * @Method Name : getDomain
	 * @Date : 2019. 7. 8.
	 * @Author : tennessee
	 * @param chnnlNo
	 * @param deviceCode
	 * @return
	 */
	private String getDomain(String chnnlNo, String deviceCode) {
		StringBuffer key = new StringBuffer();
		key.append("service.domain");

		switch (chnnlNo) {
		case Const.CHANNEL_NO_ART:
			key.append(".art");
			break;
		case Const.CHANNEL_NO_ABCMART:
			key.append(".abc");
			break;
		case Const.CHANNEL_NO_GRANDSTAGE:
			key.append(".gs");
			break;
		case Const.CHANNEL_NO_OTS:
			key.append(".ots");
			break;
		case Const.CHANNEL_NO_KIDS:
			key.append(".kids");
			break;
		default:
			log.debug("알 수 없는 유형입니다. chnnlNo : {}", chnnlNo);
		}

		if (UtilsDevice.isNotPc(deviceCode)) {
			key.append(".mo");
		} else {
			key.append(".fo");
		}

		return Config.getString(key.toString(), "");
	}

}
