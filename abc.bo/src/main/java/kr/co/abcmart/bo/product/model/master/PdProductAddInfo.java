package kr.co.abcmart.bo.product.model.master;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import kr.co.abcmart.bo.product.model.master.base.BasePdProductAddInfo;
import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.util.UtilsObject;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.common.validation.Validator;
import kr.co.abcmart.constant.Const;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 상품추가정보
 * @FileName : PdProductAddInfo.java
 * @Project : abc.bo
 * @Date : 2019. 4. 30.
 * @Author : tennessee
 */
@Slf4j
@Data
public class PdProductAddInfo extends BasePdProductAddInfo implements Validator, Comparable<PdProductAddInfo> {

	/** 상품정보고시 */
	public static final String ADD_INFO_TYPE_INFORMATION = "I";
	/** 취급주의사항 */
	public static final String ADD_INFO_TYPE_PRECAUTION = "P";
	/** 인증정보 */
	public static final String ADD_INFO_TYPE_AUTHORITY_INFOMATION = "A";

	/** 정보고시명 */
	private String infoNotcName;
	/** 정보고시기본값 */
	private String infoNotcDfltValue;
	/** 필수여부 */
	private String reqYn;
	
	private String prdtInfoNotcCode;

	/*
	 * (non-Javadoc)
	 * 
	 * @see kr.co.abcmart.common.validation.Validator#validate()
	 */
	@Override
	public void validate() throws ValidatorException {
		if (UtilsText.isNotBlank(this.getAddInfoType())) {
			switch (this.getAddInfoType()) {
			case ADD_INFO_TYPE_INFORMATION:
				this.validateInformation();
				break;
			case ADD_INFO_TYPE_PRECAUTION:
				this.validatePrecautions();
				break;
			case ADD_INFO_TYPE_AUTHORITY_INFOMATION:
				this.validateAuthorityInfomation();
				break;
			default:
				this.validationFieldMessage("product.valid.product.addInfo.addInfoType",
						"productAddInfo.prdtMaterlText"); // 추가정보구분
			}
		} else {
			this.validationFieldMessage("product.valid.product.addInfo.addInfoType", "productAddInfo.prdtMaterlText"); // 추가정보구분
		}

	}

	/**
	 * @Desc : 상품정보고시 검증
	 * @Method Name : validateInformation
	 * @Date : 2019. 4. 30.
	 * @Author : tennessee
	 */
	private void validateInformation() {
		if (UtilsText.equals("I", this.getAddInfoType())) {
			if (UtilsText.equals(this.getReqYn(), Const.BOOLEAN_TRUE)) {
				// 필수값인 경우, 상세내용 입력여부 확인
				// 상품추가정보
				if (UtilsText.isBlank(this.getPrdtAddInfo())) {
					this.validationFieldMessage("product.valid.product.addInfo.information.prdtAddInfo",
							"productAddInfo.prdtAddInfo");
				} else if (this.getPrdtAddInfo().length() > 1000) {
					this.validationFieldMessage("product.valid.product.addInfo.information.prdtAddInfo.length",
							"productAddInfo.prdtAddInfo");
				}
			}
		}
	}

	/**
	 * @Desc : 취급시 주의사항 검증
	 * @Method Name : validatePrecautions
	 * @Date : 2019. 4. 30.
	 * @Author : tennessee
	 */
	private void validatePrecautions() {
		// 소재
//		if (UtilsText.isBlank(this.getPrdtMaterlText())) {
//			this.validationMessage("product.valid.product.addInfo.precaution.prdtMaterlText");
//		}
		// 소재별관리방법
		if (UtilsText.equals("P", this.getAddInfoType())) {
			if (UtilsText.isBlank(this.getPrdtAddInfo())) {
//			this.validationMessage("product.valid.product.addInfo.precaution.prdtAddInfo");
			} else if (this.getPrdtAddInfo().length() > 1000) {
				this.validationFieldMessage("product.valid.product.addInfo.precaution.prdtAddInfo.length",
						"productAddInfo.prdtMaterlText");
			}
		}
	}

	/**
	 * @Desc : 인증정보 검증
	 * @Method Name : validateAuthorityInfomation
	 * @Date : 2019. 4. 30.
	 * @Author : tennessee
	 */
	private void validateAuthorityInfomation() {
		if (UtilsText.equals("A", this.getAddInfoType())) {
			if (UtilsText.isNotBlank(this.getPrdtAddInfo())) {
				// 문구
				if (this.getPrdtAddInfo().length() > 1000) {
					this.validationFieldMessage("product.valid.product.addInfo.authorityInfomation.prdtAddInfo.length",
							"productAddInfo.prdtMaterlText");
				}
				// 인증번호
				if (UtilsText.isBlank(this.getSafeCrtfcNoText())) {
					this.validationFieldMessage("product.valid.product.addInfo.authorityInfomation.safeCrtfcNoText",
							"productAddInfo.prdtMaterlText");
				} else if (this.getSafeCrtfcNoText().length() > 25) {
					this.validationFieldMessage(
							"product.valid.product.addInfo.authorityInfomation.safeCrtfcNoText.length",
							"productAddInfo.prdtMaterlText");
				}
				// 인증마크
				if (UtilsText.isBlank(this.getSafeCrtfcImageUrl())) {
					this.validationFieldMessage("product.valid.product.addInfo.authorityInfomation.safeCrtfcImageUrl",
							"productAddInfo.safeCrtfcImageUrl");
				} else if (this.getSafeCrtfcImageUrl().length() > 100) {
					this.validationFieldMessage(
							"product.valid.product.addInfo.authorityInfomation.safeCrtfcImageUrl.length",
							"productAddInfo.safeCrtfcImageUrl");
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@SuppressWarnings("serial")
	@Override
	public int compareTo(PdProductAddInfo target) {
		Method[] methods = this.getClass().getMethods();
		List<String> compareMethodNames = new ArrayList<String>() {
			{
				// 상품번호. 비교대상 제외
				add("getAddInfoType"); // 추가정보구분
				// 상품추가번호순번. 비교대상 제외
				// 상품추가정보관련순번. 비교대상 제외
				add("getDispYn"); // 전시여부
				add("getPrdtMaterlText"); // 상품소재
				add("getPrdtAddInfo"); // 상품추가정보
				add("getPrdtSafeTypeCode"); // 상품안전유형코드
				add("getSafeCrtfcNoText"); // 안전인증번호
				add("getSafeCrtfcImageName"); // 안전인증이미지명
				add("getSafeCrtfcImagePathText"); // 안전인증이미지경로
				add("getSafeCrtfcImageUrl"); // 안전인증이미지URL
//				add("getSortSeq"); // 정렬순번. 비교대상 제외
			}
		};

		int compareCountTotal = compareMethodNames.size(); // 검사 대상 항목 총 갯수
		int compareCountEqual = 0; // 동일 항목 갯수

		for (String compareMethodName : compareMethodNames) {
			// 비교대상 메서드 목록 기준으로 검색
			for (Method method : methods) {
				if (UtilsText.equals(compareMethodName, method.getName())) {

					try {

						Object sourceValue = null;
						Object targetValue = null;

						sourceValue = method.invoke(this); // 소스 메서드 값 추출
						targetValue = method.invoke(target); // 타겟 메서드 값 추출

						if (UtilsObject.isEmpty(sourceValue) && UtilsObject.isEmpty(targetValue)) {
							// 동일함으로 판단
							compareCountEqual++;
						} else {
							if (sourceValue instanceof String) {
								if (UtilsText.equals((String) sourceValue, (String) targetValue)) {
									compareCountEqual++;
								}
//							} else if (sourceValue instanceof Integer) {
//								if (UtilsObject.isEmpty(sourceValue)) {
//									if (UtilsObject.isEmpty(targetValue) || ((Integer) targetValue).intValue() == 0) {
//										compareCountEqual++;
//									}
//								} else {
//									if (((Integer) sourceValue).compareTo((Integer) targetValue) == 0) {
//										compareCountEqual++;
//									}
//								}
							} else {
								log.error("알 수 없는 필드유형입니다. sourceMethod : {}\tsourceValue : {}\ttargetValue : {}",
										method.getName(), sourceValue, targetValue);
							}
						}

					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						log.error("메서드 수행 중 오류 발생. {}", e.getMessage());
						log.error("{}", e);
					}
				}
			}
		}

		return compareCountTotal - compareCountEqual;
	}

}
