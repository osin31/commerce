package kr.co.abcmart.bo.stats.model.master;

import java.io.Serializable;

import kr.co.abcmart.common.bean.BaseBean;
import lombok.Data;

@Data
public class SaRestockStatus extends BaseBean implements Serializable {

	/** 브랜드명 */
	private String brandName;

	/** 상품명 */
	private String productName;

	/** 상품코드 */
	private String productNo;

	/** 사이즈코드 */
	private String sizeCode;

	/** 옵션명 */
	private String optionName;

	/** 대기 */
	private String readyRestock;

	/** 처리완료 */
	private String successRestock;

	/** 취소 */
	private String cancelRestock;

	/** 기간만료 */
	private String endDtmRestock;

	/** 구매건수 */
	private String buyCount;

	/** 결제금액 */
	private String sellPrice;

	/** 순번 */
	private String selectSeq;

	/** 재입고 알림 서비스 발송기간 */
	private String alertSendDtm;

	/** 상품코드 구분 */
	private String vndrGbnType;

	/** 채널번호 */
	private String chnnlNo;
}