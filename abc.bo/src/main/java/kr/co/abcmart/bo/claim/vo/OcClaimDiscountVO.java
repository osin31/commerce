package kr.co.abcmart.bo.claim.vo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 할인/할인취소 정보 - 쿠폰, 프로모션 (클레임 기준)
 * @FileName : OcClaimDiscountVO.java
 * @Project : abc.bo
 * @Date : 2019. 3. 19.
 * @Author : KTH
 */
@Slf4j
@Data
public class OcClaimDiscountVO {

	private String orgOrderNo;
	private String orderNo;
	private String memberNo;

	private String discountGbn;
	private String discountId;
	private String discountTxt;
	private String discountStartDt; // 쿠폰 유효기간 , 프로모션 기간 Strart
	private String discountEndDt; // 쿠폰 유효기간 , 프로모션 기간 End

	private String discountType;
	private Integer discountValue;
	private Integer discountAmt;
	private String discountPrdtNo;
	private String discountPrdtName;

	private String discoountPrdCode;
	private String discoountPrdName;
	private Integer discoountCnt;

	private String clmNo;

}
