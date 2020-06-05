package kr.co.abcmart.bo.product.model.master;

import java.sql.Timestamp;

import kr.co.abcmart.bo.display.model.master.DpCategoryProduct;
import kr.co.abcmart.common.request.annotation.ParameterOption;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc :
 * @FileName : PdProduct.java
 * @Project : abc.bo
 * @Date : 2019. 5. 10.
 * @Author : tennessee
 */
@Slf4j
@Data
public class PdProductBulkUpdateWrapper {

	/** 권한 (B, P) */
	private String auth;

	/** 대상상품 */
	@ParameterOption(target = "checked")
	private PdProduct[] products;

	/**
	 * Tab 1
	 */
	/** 판매상태 */
	private String sellStatCode;
	/** 판매기간 - 시작 */
	private Timestamp sellStartDtm;
	/** 판매기간 - 종료 */
	private Timestamp sellEndDtm;
	/** 예약상품여부 */
	private String rsvPrdtYn;
	/** 예상출고일 */
	private Timestamp rsvDlvyYmd;
	/** 무료배송상품여부 */
	private String freeDlvyYn;
	/** 임직원할인여부 */
	private String empDscntYn;
	/** 감가제외여부 */
	private String dprcExceptYn;
	/** 매장픽업가능여부 */
	private String storePickupPsbltYn;
	/** 주문제작여부 */
	private String orderMnfctYn;
	/** 제휴사전송여부 */
	private String affltsSendYn;

	/**
	 * Tab 2
	 */
	/** 전시여부 */
	private String dispYn;
	/** 아이콘 */
	@ParameterOption(target = "prdtIconSeq")
	private PdProductIcon[] productIcon;
	/** 전시카테고리 */
	@ParameterOption(target = "divider")
	private DpCategoryProduct[] displayCategories;
	/** 기준카테고리 */
	private String stdrCtgrNo;
	/** 검색가능여부 */
	private String srchPsbltYn;
	/** 검색키워드 */
	private String srchKeyWordText;
	/** 색상 */
	@ParameterOption(target = "prdtColorCode")
	private PdProductColor[] productColor;
	/** 관련용품설정여부 */
	private String rltnGoodsSetupYn;
	/** 관련용품 */
//	@ParameterOption(target = "checked")
//	private PdProduct[] rltnGoodsSetupList;
	/** 관련용품(rltnGoodsSetupList에서 변환) */
	@ParameterOption(target = "checked")
	private PdRelationProduct[] relationProducts;

	/**
	 * Tab 3
	 */
	/** 구매수량제한여부 */
	private String buyLimitYn;
	/** 구매수량제한여부 - 최소구매수량 */
	private Integer minBuyPsbltQty;
	/** 구매수량제한여부 - 최대구매수량 */
	private Integer maxBuyPsbltQty;
	/** 구매수량제한여부 - 1일최대구매수량 */
	private Integer dayMaxBuyPsbltQty;
	/** 재입고알림여부 */
	private String wrhsAlertYn;
	/** 재고통합여부 */
	private String stockIntgrYn;
	/** 재고통합여부 - 미연동사유코드 */
	private String stockUnIntgrRsnCode;

	/**
	 * 공통사항
	 */
	/** 승인상태코드 */
	private String aprvStatCode;
	/** 승인자번호 */
	private String aprverNo;
	/** 승인일시 */
	private Timestamp aprverDtm;
	/** 수정자번호 */
	private String moderNo;
	/** 수정일시 */
	private Timestamp modDtm;

}
