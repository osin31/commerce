package kr.co.abcmart.bo.order.model.master;

import kr.co.abcmart.bo.order.model.master.base.BaseOcOrderUseCoupon;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class OcOrderUseCoupon extends BaseOcOrderUseCoupon {
	private String orgOrderNo;
	private String clmNo;
	private String cpnName; // 쿠폰명
	private String cpnTypeCode; // 쿠폰유형코드
	private String cpnStatCode; // 쿠폰상태코드
	private java.sql.Timestamp validStartDtm; // 유효시작일시
	private java.sql.Timestamp validEndDtm; // 유효종료일시
	private String prdtTypeCode; // 상품유형코드
}
