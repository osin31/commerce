package kr.co.abcmart.bo.stats.model.master;

import java.io.Serializable;

import kr.co.abcmart.common.bean.BaseBean;
import lombok.Data;

@Data
public class CurrentSaleStats extends BaseBean implements Serializable {

	private static final long serialVersionUID = 6223136023030862586L;

	/**
	 * 시간대별 통계 Title
	 */
	private String hourTitle;
	/**
	 * 수량 총 합계
	 */
	private int orderQtyAll;
	/**
	 * 정상가 총 합계
	 */
	private int prdtNormalAmtAll;
	/**
	 * 결제금액 총 합계
	 */
	private int orderAmtAll;
	/**
	 * 온라인, PC, 신용카드, 일반배송, 10대, 온라인회원 남성 수량 합계
	 */
	private int orderQty10000;
	/**
	 * 온라인, PC, 신용카드, 일반배송, 10대, 온라인회원 남성 정상가 합계
	 */
	private int prdtNormalAmt10000;
	/**
	 * 온라인, PC, 신용카드, 일반배송, 10대, 온라인회원 남성 결제금액 합계
	 */
	private int orderAmt10000;
	/**
	 * 스마트, MOBILE, 무통장입금, 편의점픽업, 20대, 온라인회원 여성 수량 합계
	 */
	private int orderQty10001;
	/**
	 * 스마트, MOBILE, 무통장입금, 편의점픽업, 20대, 온라인회원 여성 정상가 합계
	 */
	private int prdtNormalAmt10001;
	/**
	 * 스마트, MOBILE, 무통장입금, 편의점픽업, 20대, 온라인회원 여성 결제금액 합계
	 */
	private int orderAmt10001;
	/**
	 * 매장, APP, 실시간계좌이체, 매장픽업, 30대, 온라인회원 알수없음 수량 합계
	 */
	private int orderQty10002;
	/**
	 * 매장, APP, 실시간계좌이체, 매장픽업, 30대, 온라인회원 알수없음 정상가 합계
	 */
	private int prdtNormalAmt10002;
	/**
	 * 매장, APP, 실시간계좌이체, 매장픽업, 30대, 온라인회원 알수없음 결제금액 합계
	 */
	private int orderAmt10002;
	/**
	 * 휴대폰결제, 입점사, 40대, 멤버십회원 남성 수량 합계
	 */
	private int orderQty10003;
	/**
	 * 휴대폰결제, 입점사, 40대, 멤버십회원 남성 정상가 합계
	 */
	private int prdtNormalAmt10003;
	/**
	 * 휴대폰결제, 입점사, 40대, 멤버십회원 남성 결제금액 합계
	 */
	private int orderAmt10003;
	/**
	 * 네이버페이, 50대, 멤버십회원 여성 수량 합계
	 */
	private int orderQty10004;
	/**
	 * 네이버페이, 50대,멤버십회원 여성 정상가 합계
	 */
	private int prdtNormalAmt10004;
	/**
	 * 네이버페이, 50대, 멤버십회원 여성 결제금액 합계
	 */
	private int orderAmt10004;
	/**
	 * 카카오페이, 60대, 비회원 수량 합계
	 */
	private int orderQty10005;
	/**
	 * 카카오페이, 60대, 비회원 정상가 합계
	 */
	private int prdtNormalAmt10005;
	/**
	 * 카카오페이, 60대, 비회원 결제금액 합계
	 */
	private int orderAmt10005;
	/**
	 * 기프트카드, 기타 수량 합계
	 */
	private int orderQty10006;
	/**
	 * 기프트카드, 기타 정상가 합계
	 */
	private int prdtNormalAmt10006;
	/**
	 * 기프트카드, 기타 결제금액 합계
	 */
	private int orderAmt10006;
	/**
	 * 포인트 수량 합계
	 */
	private int orderQty99999;
	/**
	 * 포인트 정상가 합계
	 */
	private int prdtNormalAmt99999;
	/**
	 * 포인트 결제금액 합계
	 */
	private int orderAmt99999;

	/**
	 * 당일 클레임 툥계
	 */
	private String vndrNo;
	private String vndrName;
	private String vndrGbn;
	private String vndrGbnType;
	private String dateGbn;
	private String clmGbnCode;
	private String clmRsnCode;
	private String clmStatCode;
	private String clmPrdtStatCode;
	private String mmnyPrdtYn;
	private String gbn;
	private String rsn;
	private int sortSeq;
	private int cnt;

	/** 반품 **/
	// 합계
	private int returnTotalCnt;
	// 불가
	private int returnImpsblCnt;
	// 완료
	private int returnFinishCnt;
	// 처리중
	private int returnProcCnt;
	/** 반품 사유 **/
	// 불량
	private int returnRsn01;
	// 오배송
	private int returnRsn02;
	// 변심(소재)
	private int returnRsn03;
	// 변심(사이즈미스)
	private int returnRsn04;
	// 변심(화면과실물상이)
	private int returnRsn05;
	// 미출반품
	private int returnRsn06;
	// 오프라인반품
	private int returnRsn07;
	// 브랜드박스훼손
	private int returnRsn08;
	// 기타
	private int returnRsn09;

	/** 교환 **/
	// 합계
	private int exchangeTotalCnt;
	// 불가
	private int exchangeImpsblCnt;
	// 완료
	private int exchangeFinishCnt;
	// 처리중
	private int exchangeProcCnt;
	/** 교환 사유 **/
	// 불량
	private int exchangeRsn01;
	// 오배송
	private int exchangeRsn02;
	// 옵션변경
	private int exchangeRsn03;
	// 브랜드박스훼손
	private int exchangeRsn04;
	// 기타
	private int exchangeRsn05;

	// 브랜드 번호
	private String brandNo;
	// 브랜드 이름
	private String brandName;
	// 카테고리번호
	private String stdCtgrNo;
	// 카테고리이름
	private String stdCtgrName;
	// 스타일 정보
	private String styleInfo;
	// 상품색깔코드
	private String prdtColorCode;
	// 상품색깔이름
	private String prdtColorName;
	// 상품번호,상품이름
	private String prdtNoName;
	// 상품번호
	private String prdtNo;
	// 상품명
	private String prdtName;
	// 자사, 입점업체 이름
	private String comName;

}