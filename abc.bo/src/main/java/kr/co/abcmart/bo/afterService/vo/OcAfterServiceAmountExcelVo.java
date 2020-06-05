/**
 * 
 */
package kr.co.abcmart.bo.afterService.vo;

import kr.co.abcmart.bo.afterService.model.master.OcAsAccept;
import kr.co.abcmart.common.bean.BaseBean;
import kr.co.abcmart.common.request.annotation.ParameterOption;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 엑셀에 필요한 변수
 * @FileName : OcAfterServiceAmountExcelVo.java
 * @Project : abc.bo
 * @Date : 2019.03.20
 * @Author : dtimer2@3top.co.kr
 */
@Slf4j
@Data
public class OcAfterServiceAmountExcelVo extends BaseBean {

	private String siteName;

	private String asStatCodeName;

	private String asAcceptNo;

	private String orderNo;

	private String prdtNo;

	private String styleInfo;

	private String prdtName;

	private String optnName;

	private String pymntAmt;

	private String asRsnName;

	private String asAcceptContText;

	private String asProcess;

	private String addDlvyAmt;

	private String methodOfPayment;

	private int asAmt;

	private int orderAmt;

	private int totalAsAmt;

	private int totalPymntAmt;

	private String dlvrName;

	@ParameterOption(target = "asAcceptNo")
	private OcAsAccept[] asNos;

	// 사이트번호
	private String siteNo;

	// 원주문번호
	private String orgOrderNo;

	// 주문회원번호
	private String memberNo;

	// 주문자, 처리자 loginId
	private String rgsterId;

	// 주문자명
	private String rgsterName;

	// 접수상태코드
	private String asStatCode;

	// 은행 코드 명
	private String bankCodeName;

	// 기간검색 : ~부터
	private String fromDate;

	// 기간검색 : ~까지
	private String toDate;

	// 기간검색 - 환불,환수금액 발생일시 : R / 처리일시 : M
	private char dateGbnType;

	// 브랜드 네임
	private String brandName;

	// 주문자 명
	private String buyerName;

	// 주문자 Id
	private String loginId;

	// 휴대폰 번호
	private String buyerHdphnNoText;

	// 사유구분
	private String asGbnCode;

	// 상품 순번
	private short asAcceptPrdtSeq;

	// 접수일시
	private String rgstDtm;

	// 관리자 접수 여부
	private String adminAcceptYn;

	// 온라인 / 비회원 / 맵버쉽
	private String memberTypeName;

	// str 상품주문금액
	private String strOrderAmt;
	// str AS추가배송비
	private String strAddDlvyAmt;
	// str AS비용
	private String strAsAmt;
	// str 총AS결제금액
	private String strTotalPymntAmt;

}
