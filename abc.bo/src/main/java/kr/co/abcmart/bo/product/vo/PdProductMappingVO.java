package kr.co.abcmart.bo.product.vo;

import java.util.List;

import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.common.bean.BaseBean;
import lombok.Data;
import lombok.ToString;

/**
 * @Desc : 상품 매핑 테이블 조회 객체
 * @FileName : PdProductMappingVO.java
 * @Project : abc.bo
 * @Date : 2019. 5. 20.
 * @Author : tennessee
 */
@Data
@ToString(callSuper = true)
public class PdProductMappingVO extends BaseBean {

	/** 매핑 테이블 이름 */
	private String mappingTableName;

	/** 조회 조건 컬럼 이름 */
	private String conditionColumnName;
	/** 조회 조건 컬럼 값 */
	private String conditionColumnValue;

	/** 조회 조건 컬럼 이름 */
	private String conditionColumnName1;
	/** 조회 조건 컬럼 값 */
	private String conditionColumnValue1;

	/** 프로모션 조회 여부 */
	boolean searchForAppliedPromotion;
	/** 목록 조회 시 프로모션 정보 */
	private String appliedPromotionInfo;

	/** 정렬컬럼 이름 */
	private String sortColumnName;
	/** 정렬유형. ASC 또는 DESC */
	private String sortType;

	/** 재고구분코드 */
	private List<SyCodeDetail> stockGbnCodeList;

	// 상품 코드 배열
	private String[] prdtNoList;

}
