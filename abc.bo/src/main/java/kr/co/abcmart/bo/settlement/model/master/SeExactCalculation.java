package kr.co.abcmart.bo.settlement.model.master;

import kr.co.abcmart.bo.settlement.model.master.base.BaseSeExactCalculation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class SeExactCalculation extends BaseSeExactCalculation {

	// 업체 명
	private String vndrName;

	// 브랜드 네임
	private String brandName;

	// 월정산/판매수수료/배송비/프로모션비용/패널티산정 탭 구분하는 변수
	private String tabGubun;

	// 주문/클레임 유형
	private String[] deviceTypeCodeArr;

	// 정산확정여부
	private String[] chkCalceArr;

	// 주문번호
	private String orderNo;

	// 상품번호
	private String prdtNo;

	//
	private String salesCnclGbnType;

	// 판매수수료
	private String sellCmsnAmtN;
	private String sellCmsnCountN;
	private String sellCmsnAmtY;
	private String sellCmsnCountY;
	// 배송비
	private String dlvyAmtN;
	private String dlvyAmtCountN;
	private String dlvyAmtY;
	private String dlvyAmtCountY;
	// 프로모션비용;
	private String promoAmtN;
	private String promoCountN;
	private String promoAmtY;
	private String promoCountY;
	// 패널티신청
	private String penltyAmtN;
	private String penltyCountN;
	private String penltyAmtY;
	private String penltyCountY;

}
