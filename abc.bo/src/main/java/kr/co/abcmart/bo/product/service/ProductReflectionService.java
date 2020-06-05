package kr.co.abcmart.bo.product.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import kr.co.abcmart.bo.product.model.master.PdProduct;
import kr.co.abcmart.bo.product.model.master.PdProductChangeHistory;
import kr.co.abcmart.bo.product.model.master.PdProductPriceHistory;
import kr.co.abcmart.common.util.UtilsNumber;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 상품 반영 서비스
 * @FileName : ProductReflectionService.java
 * @Project : abc.bo
 * @Date : 2019. 4. 17.
 * @Author : tennessee
 */
@Slf4j
@Component
public class ProductReflectionService {

	private final String METHOD_PREFIX_GETTER = "get";
	private final String METHOD_PREFIX_SETTER = "set";

	/** 등록자번호 */
	private final String METHOD_NAME_REGIST_NO = "RGSTER_NO";
	/** 등록일시 */
	private final String METHOD_NAME_REGIST_DT = "RGST_DTM";
	/** 수정자번호 */
	private final String METHOD_NAME_MODIFY_NO = "MODER_NO";
	/** 수정일시 */
	private final String METHOD_NAME_MODIFY_DT = "MOD_DTM";

	/** 승인자번호 */
	private final String METHOD_NAME_APRVER_NO = "APRVER_NO";
	/** 승인일시 */
	private final String METHOD_NAME_APRVER_DT = "APRVER_DTM";

	/** 요청자번호 */
	private final String METHOD_NAME_REQTR_NO = "REQTR_NO";
	/** 요청일시 */
	private final String METHOD_NAME_REQ_DT = "REQ_DTM";

	/** 상품 검사 필드 */
	@SuppressWarnings("serial")
	private final Map<String, String> CHANGE_CHECK_FIELD_FOR_PRODUCT = new HashMap<String, String>() {
		{
			put("prdt_name", "상품명"); // AS-IS 상품명
			put("eng_prdt_name", "영문상품명"); // AS-IS 영문상품명
			put("sell_stat_code", "판매상태코드"); // AS-IS 상품상태코드
			put("aprv_stat_code", "승인상태코드"); // AS-IS 상품승인코드
			put("prdt_color_info", "상품색상정보"); // AS-IS 상품색상코드
			put("disp_yn", "전시여부"); // AS-IS 상품전시여부
		}
	};

	/** 상품가격이력 검사 필드 */
	@SuppressWarnings("serial")
	private final Map<String, String> CHANGE_CHECK_FIELD_FOR_PRODUCT_PRICE_HISTORY_BO = new HashMap<String, String>() {
		{
//			put("sell_amt", "판매가"); // AS-IS 상품판매가격
			put("onln_sell_amt", "온라인판매가");
			put("erp_sell_amt", "오프라인판매가");
		}
	};
	/** 상품가격이력 검사 필드 */
	@SuppressWarnings("serial")
	private final Map<String, String> CHANGE_CHECK_FIELD_FOR_PRODUCT_PRICE_HISTORY_PO = new HashMap<String, String>() {
		{
			put("onln_sell_amt", "온라인판매가");
		}
	};

	/**
	 * @Desc : 상품 객체 내 등록/수정자 정보 기입
	 * @Method Name : setUserInfo
	 * @Date : 2019. 4. 22.
	 * @Author : tennessee
	 * @param object
	 */
	public void setUserInfo(Object object) {
		this.setRegisterInfo(object);
		this.setModifierInfo(object);
	}

	/**
	 * @Desc : 상품 객체 내 등록자 정보 기입
	 * @Method Name : setRegisterInfo
	 * @Date : 2019. 11. 18.
	 * @Author : tennessee
	 * @param object
	 */
	public void setRegisterInfo(Object object) {
		this.invokeSetterMethod(object, this.METHOD_NAME_REGIST_NO, LoginManager.getUserDetails().getAdminNo());
		this.invokeSetterMethod(object, this.METHOD_NAME_REGIST_DT, new Timestamp(new Date().getTime()));
	}

	/**
	 * @Desc : 상품 객체 내 수정자 정보 기입
	 * @Method Name : setModifierInfo
	 * @Date : 2019. 11. 18.
	 * @Author : tennessee
	 * @param object
	 */
	public void setModifierInfo(Object object) {
		this.invokeSetterMethod(object, this.METHOD_NAME_MODIFY_NO, LoginManager.getUserDetails().getAdminNo());
		this.invokeSetterMethod(object, this.METHOD_NAME_MODIFY_DT, new Timestamp(new Date().getTime()));
	}

	/**
	 * @Desc : 승인자 정보 입력
	 * @Method Name : setAprverInfo
	 * @Date : 2019. 4. 29.
	 * @Author : tennessee
	 * @param object
	 */
	public void setAprverInfo(Object object) {
		this.invokeSetterMethod(object, this.METHOD_NAME_APRVER_NO, LoginManager.getUserDetails().getAdminNo());
		this.invokeSetterMethod(object, this.METHOD_NAME_APRVER_DT, new Timestamp(new Date().getTime()));
	}

	/**
	 * @Desc : 요청자 정보 입력
	 * @Method Name : setReqtrInfo
	 * @Date : 2019. 4. 29.
	 * @Author : tennessee
	 * @param object
	 */
	public void setReqtrInfo(Object object) {
		this.invokeSetterMethod(object, this.METHOD_NAME_REQTR_NO, LoginManager.getUserDetails().getAdminNo());
		this.invokeSetterMethod(object, this.METHOD_NAME_REQ_DT, new Timestamp(new Date().getTime()));
	}

	/**
	 * @Desc : SETTER 메서드를 찾은 후 실행
	 * @Method Name : invokeMethod
	 * @Date : 2019. 4. 22.
	 * @Author : tennessee
	 * @param runObject
	 * @param fieldName
	 * @param setFieldValue
	 */
	private void invokeSetterMethod(Object runObject, String fieldName, Object setFieldValue) {
		for (Method method : runObject.getClass().getMethods()) {
			if (UtilsText.equals(method.getName(), this.getSetterMethodName(fieldName))) {
				try {
					method.invoke(runObject, setFieldValue);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					log.error("{}", e);
				}
				break;
			}
		}
	}

	/**
	 * @Desc : GETTER 메서드를 찾은 후 수행
	 * @Method Name : invokeGetterMethod
	 * @Date : 2019. 5. 13.
	 * @Author : tennessee
	 * @param runObject
	 * @param findFieldName
	 * @param returnClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private <T> T invokeGetterMethod(Object runObject, String findFieldName, Class<T> returnClass) {
		T result = null;

		for (Method method : runObject.getClass().getMethods()) {
			if (UtilsText.equalsIgnoreCase(method.getName(), this.getGetterMethodName(findFieldName))) {
				try {
					result = (T) method.invoke(runObject);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					log.error("{}", e);
				}
				break;
			}
		}

		return result;
	}

	/**
	 * @Desc : 입력된 컬럼명(ex. test_field)을 SET 메서드명으로 반환
	 * @Method Name : getSetterMethodName
	 * @Date : 2019. 4. 18.
	 * @Author : tennessee
	 * @param fieldName
	 * @return
	 */
	private String getSetterMethodName(String fieldName) {
		return UtilsText.concat(this.METHOD_PREFIX_SETTER,
				UtilsText.capitalize(this.toCamelCaseWithUnderBar(fieldName)));
	}

	/**
	 * @Desc : 테이블명 패턴을 camel case로 변환하여 반환. ex) from "test_field" to "testField"
	 * @Method Name : toCamelCaseWithUnderBar
	 * @Date : 2019. 4. 18.
	 * @Author : tennessee
	 * @param text
	 * @return
	 */
	private String toCamelCaseWithUnderBar(String text) {
		Pattern pattern = Pattern.compile("_(.)");
		Matcher matcher = pattern.matcher(text.toLowerCase());
		StringBuffer stringBuffer = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(stringBuffer, matcher.group(1).toUpperCase());
		}
		matcher.appendTail(stringBuffer);

		return stringBuffer.toString();
	}

	/**
	 * @Desc : 입력된 컬럼명(ex. test_field)을 GET 메서드명으로 반환
	 * @Method Name : getMethodName
	 * @Date : 2019. 4. 18.
	 * @Author : tennessee
	 * @param fieldName
	 * @return
	 */
	private String getGetterMethodName(String fieldName) {
		return UtilsText.concat(this.METHOD_PREFIX_GETTER,
				UtilsText.capitalize(this.toCamelCaseWithUnderBar(fieldName)));
	}

	/**
	 * @Desc : 상품변경이력 비교 후 결과 반환
	 * @Method Name : compareChangeHistoryForProduct
	 * @Date : 2019. 5. 13.
	 * @Author : tennessee
	 * @param before
	 * @param after
	 * @return
	 */
	public List<PdProductChangeHistory> compareChangeHistoryForProduct(PdProduct before, PdProduct after) {
		List<PdProductChangeHistory> result = new ArrayList<PdProductChangeHistory>();
		PdProductChangeHistory temp = null;
		// 상품 비교
		for (String findFieldName : this.CHANGE_CHECK_FIELD_FOR_PRODUCT.keySet()) {
			String beforeValue = this.invokeGetterMethod(before, findFieldName, String.class);
			String afterValue = this.invokeGetterMethod(after, findFieldName, String.class);

			if (!UtilsText.equals(beforeValue, afterValue)) {
				temp = new PdProductChangeHistory();
				temp.setPrdtNo(after.getPrdtNo());
				temp.setChngField(findFieldName.toUpperCase()); // 변경필드
				temp.setChngFieldName(this.CHANGE_CHECK_FIELD_FOR_PRODUCT.get(findFieldName)); // 변경필드명
				temp.setChngBeforeFieldValue(beforeValue); // 변경전필드값
				temp.setChngAfterFieldValue(afterValue); // 변경후필드값
				this.setUserInfo(temp);
				result.add(temp);

				// 상품이력 저장 시 설정데이터인 변경 이력 유형 설정
				after.setPrdtAprvReqCode(CommonCode.PRDT_APRV_REQ_CODE_MODIFY_INFO);
			}
		}
		return result;
	}

	/**
	 * @Desc : 상품가격이력 비교 후 결과 반환
	 * @Method Name : compareChangeHistoryForPrice
	 * @Date : 2019. 5. 13.
	 * @Author : tennessee
	 * @param before
	 * @param after
	 * @return
	 */
	public List<PdProductChangeHistory> compareChangeHistoryForPrice(PdProductPriceHistory before, PdProduct after) {
		List<PdProductChangeHistory> result = new ArrayList<PdProductChangeHistory>();
		PdProductChangeHistory temp = null;
		// 상품가격이력 비교

		Map<String, String> changeCheckField = null;
		if (UtilsText.equals(Const.BOOLEAN_TRUE, after.getMmnyPrdtYn())) {
			changeCheckField = this.CHANGE_CHECK_FIELD_FOR_PRODUCT_PRICE_HISTORY_BO;
		} else {
			changeCheckField = this.CHANGE_CHECK_FIELD_FOR_PRODUCT_PRICE_HISTORY_PO;
		}

		for (String findFieldName : changeCheckField.keySet()) {
			Integer beforeValue = this.invokeGetterMethod(before, findFieldName, Integer.class);
			Integer afterValue = this.invokeGetterMethod(after.getProductPriceHistory()[0], findFieldName,
					Integer.class);

			if (UtilsNumber.compare(beforeValue, afterValue) != 0) {
				temp = new PdProductChangeHistory();
				temp.setPrdtNo(after.getPrdtNo());
				temp.setChngField(findFieldName.toUpperCase()); // 변경필드
				temp.setChngFieldName(changeCheckField.get(findFieldName)); // 변경필드명
				temp.setChngBeforeFieldValue(String.valueOf(beforeValue)); // 변경전필드값
				temp.setChngAfterFieldValue(String.valueOf(afterValue)); // 변경후필드값
				this.setUserInfo(temp);
				result.add(temp);

				// 상품이력 저장 시 설정데이터인 변경 이력 유형 설정
				after.setPrdtAprvReqCode(CommonCode.PRDT_APRV_REQ_CODE_CHANGE_PRICE);
			}
		}
		return result;
	}

}
