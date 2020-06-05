package kr.co.abcmart.bo.product.model.master;

import kr.co.abcmart.bo.product.model.master.base.BasePdProductOptionStock;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class PdProductOptionStock extends BasePdProductOptionStock {
	/**
	 * 자사 상품 재고 통합 여부
	 */
	private String stockIntgrYn;

	/**
	 * 자사 상품 여부
	 */
	private String mmnyPrdtYn;

	/**
	 * 입점업체 옵션 상품 판매 상태
	 */
	private String sellStatCode;

	/**
	 * 옵션 전시 여부
	 */
	private String useYn;

	/**
	 * 입점업체 재고 관리 여부
	 */
	private String stockMgmtYn;

	/**
	 * 온라인 물류 재고 수량
	 */
	private Integer stockAiQty;

	/**
	 * 스마트 물류 재고 수량
	 */
	private Integer stockAwQty;

	/**
	 * 오프라인 매장 재고 수량
	 */
	private Integer stockAsQty;

	/**
	 * 입점사 재고 수량
	 */
	private Integer stockVdQty;

	/**
	 * 업체상품 번호
	 */
	private String vndrPrdtNoText;

	/**
	 * 프로모션 재고 차감 위치 A:전체 재고, O:온라인 재고
	 */
	private String stockDdctType;

	/**
	 * 타임 특가(Hot Deal) 한정 수량
	 */
	private Integer eventLimitQty;

	/**
	 * 프로모션 번호
	 */
	private String promoNo;

	/**
	 * 프로모션유형코드
	 */
	private String promoTypeCode;

	/**
	 * 사이트 번호
	 */
	private String stieNo;

	/*
	 * 오프라인 재고 조회를 위한 오프라인 채널 구분 코드 01 : 통합 07 : OTS
	 */
	private String channelGb;
}
