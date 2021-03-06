package kr.co.abcmart.bo.product.model.master;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import kr.co.abcmart.bo.product.model.master.base.BasePdProductIcon;
import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.util.UtilsObject;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.common.validation.Validator;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 상품아이콘
 * @FileName : PdProductIcon.java
 * @Project : abc.bo
 * @Date : 2019. 5. 2.
 * @Author : tennessee
 */
@Slf4j
@Data
public class PdProductIcon extends BasePdProductIcon implements Validator, Comparable<PdProductIcon> {

	/** 대상 상품 리스트 */
	private String chnnlName;
	private String stdCtgrName;
	private String sellStatCode;
	private String dispYn;
	private String imageName;
	private String imagePathText;
	private String imageUrl;
	private String altrnText;

	/*
	 * (non-Javadoc)
	 * 
	 * @see kr.co.abcmart.common.validation.Validator#validate()
	 */
	@Override
	public void validate() throws ValidatorException {
		if (UtilsObject.isEmpty(this.getPrdtIconSeq()) || this.getPrdtIconSeq().compareTo(0) <= 0) {
//			this.validationMessage("product.valid.product.icon.prdtIconSeq"); // 상품아이콘순번
			validationFieldMessage("product.valid.product.icon.prdtIconSeq", "prdtIconSeq");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@SuppressWarnings("serial")
	@Override
	public int compareTo(PdProductIcon target) {
		Method[] sourceMethods = this.getClass().getMethods();
		Method[] targetMethods = target.getClass().getMethods();
		List<String> compareMethodNames = new ArrayList<String>() {
			{
				// 상품번호. 비교대상 제외
				add("getPrdtIconSeq"); // 상품아이콘순번
				// 사용여부. 비교대상 제외
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
