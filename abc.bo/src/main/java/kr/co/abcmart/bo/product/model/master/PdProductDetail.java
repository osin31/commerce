package kr.co.abcmart.bo.product.model.master;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import kr.co.abcmart.bo.product.model.master.base.BasePdProductDetail;
import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.util.UtilsObject;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.common.validation.Validator;
import kr.co.abcmart.constant.CommonCode;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 상품상세
 * @FileName : PdProductDetail.java
 * @Project : abc.bo
 * @Date : 2019. 4. 30.
 * @Author : tennessee
 */
@Slf4j
@Data
public class PdProductDetail extends BasePdProductDetail implements Validator, Comparable<PdProductDetail> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see kr.co.abcmart.common.validation.Validator#validate()
	 */
	@Override
	public void validate() throws ValidatorException {
		if (UtilsText.isBlank(this.getDeviceCode())) {
//			this.validationMessage("product.valid.product.detail.deviceCode"); // 장치코드
			validationFieldMessage("product.valid.product.detail.deviceCode", "productDetail.divider");
		}
		if (UtilsText.isBlank(this.getPrdtDtlInfo())) {
			if (UtilsText.equals(CommonCode.DEVICE_PC, this.getDeviceCode())) {
//				this.validationMessage("product.valid.product.detail.prdtDtlInfo"); // 상품상세정보
				validationFieldMessage("product.valid.product.detail.prdtDtlInfo", "productDetail.divider");
			} else {
				// 모바일은 필수값 아님
			}
		}
//		if (UtilsObject.isEmpty(this.getSortSeq()) || this.getSortSeq().compareTo(0) <= 0) {
//			this.validationMessage("product.valid.product.detail.sortSeq"); // 정렬순번
//		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@SuppressWarnings("serial")
	@Override
	public int compareTo(PdProductDetail target) {
		Method[] methods = this.getClass().getMethods();
		List<String> compareMethodNames = new ArrayList<String>() {
			{
				// 상품번호. 비교대상 제외
				add("getDeviceCode"); // 디바이스코드
				add("getPrdtDtlInfo"); // 상품상세정보
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
							} else if (sourceValue instanceof Integer) {
								if (UtilsObject.isEmpty(sourceValue)) {
									if (UtilsObject.isEmpty(targetValue) || ((Integer) targetValue).intValue() == 0) {
										compareCountEqual++;
									}
								} else {
									if (((Integer) sourceValue).compareTo((Integer) targetValue) == 0) {
										compareCountEqual++;
									}
								}
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
