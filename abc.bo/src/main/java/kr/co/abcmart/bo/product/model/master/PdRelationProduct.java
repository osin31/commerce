package kr.co.abcmart.bo.product.model.master;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import kr.co.abcmart.bo.product.model.master.base.BasePdRelationProduct;
import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.util.UtilsObject;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.common.validation.Validator;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 관련상품
 * @FileName : PdRelationProduct.java
 * @Project : abc.bo
 * @Date : 2019. 5. 2.
 * @Author : tennessee
 */
@Slf4j
@Data
public class PdRelationProduct extends BasePdRelationProduct implements Validator, Comparable<PdRelationProduct> {

	/** 상태정보 */
	private String status;

	/** 상품명 */
	private String prdtName;
	/** 채널명 */
	private String chnnlName;
	/** 표준카테고리명 */
	private String stdCtgrName;
	/** 전시카테고리명(기준) */
	private String stdrCtgrName;
	/** 브랜드명 */
	private String brandName;
	
	private String siteNo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see kr.co.abcmart.common.validation.Validator#validate()
	 */
	@Override
	public void validate() throws ValidatorException {
		if (UtilsText.isBlank(this.getRltnPrdtTypeCode())) {
//			this.validationMessage("product.valid.product.relationProduct.rltnPrdtTypeCode"); // 관련상품유형코드
			validationFieldMessage("product.valid.product.relationProduct.rltnPrdtTypeCode", "rltnPrdtTypeCode");
		}
		if (UtilsText.isBlank(this.getRltnPrdtNo())) {
//			this.validationMessage("product.valid.product.relationProduct.rltnPrdtNo"); // 관련상품번호
			validationFieldMessage("product.valid.product.relationProduct.rltnPrdtNo", "rltnPrdtNo");
		}
		// 정렬순번은 검증하지 않음.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@SuppressWarnings("serial")
	@Override
	public int compareTo(PdRelationProduct target) {
		Method[] sourceMethods = this.getClass().getMethods();
		Method[] targetMethods = target.getClass().getMethods();
		List<String> compareMethodNames = new ArrayList<String>() {
			{
				// 상품번호. 비교대상 제외
				add("getRltnPrdtTypeCode"); // 관련상품유형코드
				add("getRltnPrdtNo"); // 관련상품번호
				add("getSortSeq"); // 정렬순번
			}
		};

		int compareCountTotal = compareMethodNames.size(); // 검사 대상 항목 총 갯수
		int compareCountEqual = 0; // 동일 항목 갯수

		for (String compareMethodName : compareMethodNames) {
			// 비교대상 메서드 목록 기준으로 검색
			for (Method sourceMethod : sourceMethods) {
				if (UtilsText.equals(compareMethodName, sourceMethod.getName())) {

					try {

						Object sourceValue = null;
						Object targetValue = null;

						sourceValue = sourceMethod.invoke(sourceMethod.getClass()); // 소스 메서드 값 추출

						for (Method targetMethod : targetMethods) {
							if (UtilsText.equals(compareMethodName, targetMethod.getName())) {
								targetValue = targetMethod.invoke(targetMethod.getClass()); // 타겟 메서드 값 추출
							}
						}

						if (UtilsObject.isEmpty(sourceValue) && UtilsObject.isEmpty(targetValue)) {
							// 동일함으로 판단
						} else {
							if (sourceValue instanceof String) {
								if (UtilsText.equals((String) sourceValue, (String) targetValue)) {
									compareCountEqual++;
								}
							} else if (sourceValue instanceof Integer) {
								if (((Integer) sourceValue).compareTo((Integer) targetValue) == 0) {
									compareCountEqual++;
								}
							} else {
								log.error("알 수 없는 필드유형입니다. sourceMethod : {}\tsourceValue : {}\ttargetValue : {}",
										sourceMethod.getName(), sourceValue, targetValue);
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
