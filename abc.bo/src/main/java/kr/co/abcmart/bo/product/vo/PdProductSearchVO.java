package kr.co.abcmart.bo.product.vo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.util.UtilsNumber;
import kr.co.abcmart.common.util.UtilsObject;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.common.validation.Validator;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 상품검색객체
 * @FileName : PdProductSearchVO.java
 * @Project : abc.bo
 * @Date : 2019. 5. 3.
 * @Author : tennessee
 */
@Slf4j
@Data
public class PdProductSearchVO implements Validator {

	/** 그리드 페이징. 현재페이지번호 */
	private int pageNum;
	/** 그리드 페이징. 한페이지당목록갯수 */
	private int onepagerow;

	private int rowsPerPage;

	/** 승인상태코드 */
	private String aprvStatCode;

	// BO 전용 검색 옵션
	/** 자사상품여부 (Y:자사, N:입점) */
	private String mmnyPrdtYn;
	/** 업체구분 중 입접업체 번호 */
	private String vndrNo;
	/** 업체구분 중 입접업체 번호 */
	private String vndrName;
	/** 상품유형코드(온라인/매장전용) */
	private String prdtTypeCode;

	// 공통검색옵션
	/** 검색어. 구분 */
	private String searchItemType;
	/** 검색어. 상품명(국문) */
	private String prdtName;
	/** 검색어. 상품명(영문) */
	private String engPrdtName;
	/** 검색어. 스타일정보 */
	private String styleInfo;
	/** 검색어. 색상정보 */
	private String prdtColorInfo;
	/** 검색어. 상품검색어 */
	private String srchKeyWordText;
	/** 검색어. 키워드 */
	private String searchItemKeyword;
	/** 상품관리자. 구분 */
	private String searchManagerType;
	/** 상품관리자. 승인자ID */
	private String aprverId;
	/** 상품관리자. 승인자이름 */
	private String aprverName;
	/** 상품관리자. 등록자ID */
	private String rgsterId;
	/** 상품관리자. 등록자이름 */
	private String rgsterName;
	/** 상품관리자. 수정자ID */
	private String moderId;
	/** 상품관리자. 수정자이름 */
	private String moderName;
	/** 상품관리자. 키워드 */
	private String searchManagerKeyword;
	/** 상품코드. 구분 */
	private String prdtCodeType;
	/** 상품코드. 상품코드목록 */
	private String prdtCodeKeyword;
	/** 상품코드. 상품번호목록 */
	private List<String> prdtCodeList;
	/** 브랜드 */
	private String brandNo;
	/** 브랜드명 */
	private String brandName;
	/** 판매상태 (판매준비중, 판매중, 일시품절, 판매종료) */
	private String[] sellStatCodes;
	/** 전시여부 */
	private String dispYn;
	/** 표준 카테고리 */
	private String stdCtgrNo;
	/** 대카테고리 */
	private String stdCtgrNo1;
	/** 중카테고리 */
	private String stdCtgrNo2;
	/** 소카테고리 */
	private String stdCtgrNo3;
	/** 전시 채널 */
	private String chnnlNo;
	/** 전시카테고리(기준카테고리) 번호 */
	private String ctgrNo;
	/** 기간. 형태 */
	private String periodType;
	/** 기간. 시작일자 */
	private String periodStartDate;
	/** 기간. 종료일자 */
	private String periodEndDate;

	// 추가검색
	private String useAdvancedSearch;
	/** 테마 */
	private String genderGbnCode;
	/** 예약상품여부 */
	private String rsvPrdtYn;
	/** 무료배송 상품여부 */
	private String freeDlvyYn;
	/** 임직원 할인제외여부 */
	private String empDscntYn;
	/** 감가제외여부 */
	private String dprcExecptYn;
	/** 통합재고연동여부 */
	private String stockIntgrYn;
	/** 매장등급 */
	private String dispFlagText;
	private List<String> dispFlag;
	/** 매장픽업 가능여부 */
	private String storePickupPsbltYn;
	/** 제휴사 전송대상 */
	private String affltsSendYn;
	/** 사방넷 전송 여부 */
	private String sabangnetSendYn;
	/** 판매가 시작 */
	private Integer sellPriceStartWith;
	/** 판매가 종료 */
	private Integer sellPriceEndWith;
	/** 재입고 상품조회 중 재입고제한일자 */
	private Integer restockWithinDays;
	/** 재입고 상품조회 중 재고보유수량 */
	private Integer restockMoreThanCount;

	/**
	 * 상품전시관리 페이지 추가 조건
	 */
	/** A-Connect 전시여부 */
	private String aconnectDispYn;
	/** 상품번호 */
	private String prdtNo;

	/** 상품유형코드 중 온/오프라인 상품유형을 제외한 모든 코드(사은품, 배송비) */
	private List<String> excludePrdtTypeCodeList;

	/** 재고구분코드 목록 */
	private List<SyCodeDetail> stockGbnCodeList;

	/** 매장 등급 코드 **/
	private String[] tierFlagCode;

	// 인기상품 팝업용
	/** 인기상품 팝업인지 확인 */
	private String bestYn;
	/** 사이트 구분 */
	private String siteNo;
	/** 집계 기준 */
	private String totalType;
	
	/** 검색 갯수 제한  */
	private int prdtListLimit = 100;

	/** 목록 조회 시 프로모션 정보 조회 여부 */
	private boolean searchForAppliedPromotion = false;

	/** 프로모션 정보 조회. 값으로 설정된 프로모션번호에 해당하는 프로모션은 조회하지 않음 */
	private String searchPromoInfo;

	/** 상품코드유형 - 온라인상품코드. 해당 코드가 설정되므로 인해 prdtCodeList가 온라인상품코드인 것으로 인식 */
	public static final String PRDT_CODE_TYPE_ONLINE = "prdt-code-online";
	/** 상품코드유형 - ERP상품코드. 해당 코드가 설정되므로 인해 prdtCodeList가 ERP상품코드인 것으로 인식 */
	public static final String PRDT_CODE_TYPE_ERP = "prdt-code-erp";

	@SuppressWarnings("serial")
	public PdProductSearchVO() {
		this.setExcludePrdtTypeCodeList(new ArrayList<String>() {
			{
				add(CommonCode.PRDT_TYPE_CODE_GIFT);
				add(CommonCode.PRDT_TYPE_CODE_DELIVERY);
			}
		});
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see kr.co.abcmart.common.validation.Validator#validate()
	 */
	@Override
	public void validate() throws ValidatorException {
		this.organizeParameters();
	}

	/**
	 * @Desc : PdProduct 객체에 맞게 파라미터 구성
	 * @Method Name : organizeParameters
	 * @Date : 2019. 3. 4.
	 * @Author : tennessee
	 */
	public void organizeParameters() {
		this.organizeSearchItemKeyword();
		this.organizeSearchManagerKeyword();
		this.organizePrdtCodeKeyword();
		this.organizePeriodDate();
		this.organizeRestock();
		this.organizeDispText();
	}

	/**
	 * @Desc : 검색어 구성
	 * @Method Name : organizeSearchKeyword
	 * @Date : 2019. 3. 4.
	 * @Author : tennessee
	 */
	private void organizeSearchItemKeyword() {
		if (UtilsText.isNotBlank(this.getSearchItemKeyword())) {
			// 검색키워드가 입력되고, 검색유형이 설정된 경우에만 데이터 설정
			switch (this.getSearchItemType()) {
			case "prdt-name":
				// 상품명(국문/영문) 검색
				this.setPrdtName(this.getSearchItemKeyword());
				this.setEngPrdtName(this.getSearchItemKeyword());
				break;
			case "style-info":
				// 스타일 정보 검색
				this.setStyleInfo(this.getSearchItemKeyword());
				break;
			case "prdt-color-info":
				// 색상정보 검색
				this.setPrdtColorInfo(this.getSearchItemKeyword());
				break;
			case "srch-key-word-text":
				// 검색키워드 검색
				this.setSrchKeyWordText(this.getSearchItemKeyword());
				break;
			case "prdt-no":
				this.setPrdtNo(this.getSearchItemKeyword());
				break;
			default:
				this.validationMessage("product.valid.product.search.period.default.searchItemType");
				log.debug("지원하지 않는 검색 유형입니다. {}", this.getSearchItemType());
			}
		}
	}

	private void organizeSearchManagerKeyword() {
		if (UtilsText.isNotBlank(this.getSearchManagerKeyword())) {
			switch (this.getSearchManagerType()) {
			case "aprver-id":
				// 승인자ID
				this.setAprverId(this.getSearchManagerKeyword());
				break;
			case "aprver-name":
				// 승인자명
				this.setAprverName(this.getSearchManagerKeyword());
				break;
			case "rgster-id":
				// 등록자ID
				this.setRgsterId(this.getSearchManagerKeyword());
				break;
			case "rgster-name":
				// 등록자명
				this.setRgsterName(this.getSearchManagerKeyword());
				break;
			case "moder-id":
				// 수정자ID
				this.setModerId(this.getSearchManagerKeyword());
				break;
			case "moder-name":
				// 수정자명
				this.setModerName(this.getSearchManagerKeyword());
				break;
			default:
				this.validationMessage("product.valid.product.search.period.default.searchManagerType");
				log.debug("지원하지 않는 검색 유형입니다. {}", this.getSearchManagerType());
			}
		}
	}

	/**
	 * @Desc : 상품코드검색 구성
	 * @Method Name : organizePrdtCodeKeyword
	 * @Date : 2019. 3. 4.
	 * @Author : tennessee
	 */
	private void organizePrdtCodeKeyword() {
		if (UtilsText.isNotBlank(this.getPrdtCodeType()) && UtilsText.isNotBlank(this.getPrdtCodeKeyword())) {
			switch (this.getPrdtCodeType()) {
			case PdProductSearchVO.PRDT_CODE_TYPE_ONLINE:
				// 온라인 상품코드 검색
				break;
			case PdProductSearchVO.PRDT_CODE_TYPE_ERP:
				// ERP 상품코드 검색
				break;
			default:
				this.validationMessage("product.valid.product.search.period.default.prdtCodeType");
				log.debug("지원하지 않는 상품코드 검색 유형입니다. {}", this.getPrdtCodeType());
			}

			List<String> codeList = Arrays.asList(this.getPrdtCodeKeyword().split("\r\n"));
			
			// 상품관리 검색시 100개까지 제한
			if (UtilsNumber.compare(codeList.size(), 0) > 0 && UtilsNumber.compare(this.prdtListLimit, codeList.size()) >= 0) {
				this.setPrdtCodeList(codeList);
			} else {
				String msg = UtilsNumber.compare(this.prdtListLimit, 100) == 0  
						? "product.valid.product.search.period.default.prdtCodeType.prdtCodeKeyword100"
						: "product.valid.product.search.period.default.prdtCodeType.prdtCodeKeyword";
				this.validationMessage(msg);
			}
		}
	}

	/**
	 * @Desc : 기간 검색 구성
	 * @Method Name : organizePeriodDate
	 * @Date : 2019. 5. 3.
	 * @Author : tennessee
	 */
	private void organizePeriodDate() {
		if (UtilsText.isNotBlank(this.getPeriodStartDate()) && UtilsText.isNotBlank(this.getPeriodEndDate())) {
			if (UtilsText.isNotBlank(this.getPeriodStartDate())) {
				this.setPeriodStartDate(this.getPeriodStartDate().replaceAll("\\.", "-") + " 00:00:00:000");
			}
			if (UtilsText.isNotBlank(this.getPeriodEndDate())) {
				this.setPeriodEndDate(this.getPeriodEndDate().replaceAll("\\.", "-") + " 23:59:59:000");
			}
		} else if (UtilsText.isBlank(this.getPeriodStartDate()) && UtilsText.isBlank(this.getPeriodEndDate())) {
			// 검색 사용안함
		} else {
			this.validationMessage("product.valid.product.search.period.default.date"); // 기간정보
		}
	}

	/**
	 * @Desc : 재입고 상품조회 구성
	 * @Method Name : organizeRestock
	 * @Date : 2019. 8. 5.
	 * @Author : tennessee
	 */
	private void organizeRestock() {
		if (UtilsObject.isNotEmpty(this.getRestockWithinDays())
				|| UtilsObject.isNotEmpty(this.getRestockMoreThanCount())) {
			// 재입고 상품조회 검색 사용
			if (UtilsObject.isEmpty(this.getRestockWithinDays())
					|| UtilsObject.isEmpty(this.getRestockMoreThanCount())) {
				this.validationMessage("product.valid.product.search.restock"); // 기간 또는 재고수량 미입력
			} else {
				if (UtilsNumber.compare(0, this.getRestockWithinDays()) == 0) {
					this.validationMessage("product.valid.product.search.restock.withinDays.length"); // 기간 1 이상 입력
				}
				if (UtilsNumber.compare(0, this.getRestockMoreThanCount()) == 0) {
					this.validationMessage("product.valid.product.search.restock.moreThanCount.length"); // 재고수량 1 이상 입력
				}
			}
		}
	}

	/**
	 * @Desc : 매장등급 검색 구성
	 * @Method Name : organizeDispText
	 * @Date : 2019. 8. 5.
	 * @Author : tennessee
	 */
	private void organizeDispText() {
		if (UtilsText.isNotBlank(this.getDispFlagText())) {
			switch (this.getDispFlagText()) {
			case "st":
				this.setDispFlag(Const.DISP_FLAG_TEXT_OF_ST);
				break;
			case "ms":
				this.setDispFlag(Const.DISP_FLAG_TEXT_OF_MS);
				break;
			case "gsmg":
				this.setDispFlag(Const.DISP_FLAG_TEXT_OF_GS);
				this.getDispFlag().addAll(Const.DISP_FLAG_TEXT_OF_MG);
				break;
			default:
				log.debug("알 수 없는 유형입니다. this.getDispTierText() : {}", this.getDispFlagText());
			}
		}
	}

}
