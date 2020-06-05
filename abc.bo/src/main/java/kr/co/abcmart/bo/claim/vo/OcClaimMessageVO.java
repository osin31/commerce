package kr.co.abcmart.bo.claim.vo;

import kr.co.abcmart.bo.claim.model.master.OcClaim;
import kr.co.abcmart.bo.claim.model.master.OcClaimProduct;
import kr.co.abcmart.common.bean.BaseBean;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 클레임메세지VO
 * @FileName : OcClaimMessageVO.java
 * @Project : abc.bo
 * @Date : 2019. 7. 2.
 * @Author : 이강수
 */
@Slf4j
@Data
public class OcClaimMessageVO extends BaseBean {

	private OcClaim ocClaim;
	private OcClaimProduct ocClaimProduct;

	// 메세지 Id
	private String mesgId;
	// 메세지 템플릿 model object
	private Object messageTemplateModel;
	// 메세지 제목
	private String mesgTitleText;
	// 메세지 내용
	private String mesgContText;
	// 실패 메세지 내용
	private String failMesgContText;
	// 발송자명
	private String sndrName;
	// 발송 전화번호
	private String sendTelNoText;
	// 수신자 전화번호
	private String recvTelNoText;
	// 수신자명
	private String rcvrName;
	// 회원번호
	private String memberNo;
	// 회원명
	private String memberName;
	// 사이트
	private String siteNo;
	// 사이트
	private String messageType;
	// 송신타입
	private String sendTypeCode;
	// 관리자 발송 여부
	private String adminSendYn = "N";
	// 실시간 여부
	private boolean isReal = false;
	// 주문번호
	private String orderNo;
	// 주문상품순번
	private String orderPrdtSeq;
	// 클레임번호
	private String clmNo;
	// 클레임상품순번
	private String clmPrdtSeq;

	/**
	 * 클레임 - 주문취소
	 */
	// 취소금액
	private String cancelAmt;
	// 취소예정금액
	private String todoCancelAmt;
	// 운송장번호
	private String waybilNoText;
	// 택배사명
	private String logisVndrName;
	// 인수편의점명
	private String cvnstrName;
	// 랜딩 URL
	private String landingUrl;
	// 택배사 랜딩단축 URL
	private String logisVndrLandingUrl;

	/**
	 * 클레임 - 교환접수
	 */
	// 상품명
	private String prdtName;
	// 기존옵션명
	private String crntOptnName;
	// 변경옵션명
	private String chngOptnName;
	// 결제방식코드
	private String pymntMeansCode;
	// 결제방식명
	private String pymntMeansName;
	// 결제금액
	private String pymntAmt;
	// 입금정보
	private String depositInfo;
	// 은행명
	private String bankName;
	// 계좌번호
	private String acntNoText;
	// 입금마감기한
	private String acntExprDtm;
	// 예금주
	private String acntHldr;
	// 브랜드명
	private String brandName;

	/**
	 * 클레임 - 반품접수
	 */
	// 반품배송비정보
	private String returnDlvyInfo;
	// 환불금액
	private String refundAmt;
}
