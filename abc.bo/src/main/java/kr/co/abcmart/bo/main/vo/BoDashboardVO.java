package kr.co.abcmart.bo.main.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class BoDashboardVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 기본
	 */
	private int tabSeq; // 탭 구분 번호

	
	/**
	 * 상품 대시보드
	 */
	private Integer sellproduct; // 판매상품
	private Integer artsell;     //art 판매상품
	private Integer abcsell;
	private Integer gssell;
	private Integer otssell;
	private Integer soldproduct; // 일시품절
	private Integer aprvproduct; // 승인요청
	private Integer denyproduct; // 승인반려
	
	
	/**
	 * 미처리 답변 현황
	 */
	private Integer inquiryCnt; // 1:1 문의
	private Integer coworkCnt; // 협력게시판
	private Integer voiceCnt; // 고객의소리
	private Integer prdtQnaCnt; // 상품Q&A
	private Integer reviewCnt; // 상품 후기

	/**
	 * 기타문의 현황
	 */
	private Integer contactCnt; // 입점/제휴 문의
	private Integer bulkBuyCnt; // 단체주문 문의

}
