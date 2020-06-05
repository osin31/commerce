package kr.co.abcmart.bo.stats.model.master;

import kr.co.abcmart.bo.stats.model.master.base.BaseSaSalesOrder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class SaSalesOrder extends BaseSaSalesOrder {

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
	private long prdtNormalAmtAll;
	/**
	 * 결제금액 총 합계
	 */
	private long orderAmtAll;
	/**
	 * 온라인, PC, 신용카드, 일반배송, 10대, 온라인회원 남성 수량 합계
	 */
	private int orderQty10000;
	/**
	 * 온라인, PC, 신용카드, 일반배송, 10대, 온라인회원 남성 정상가 합계
	 */
	private long prdtNormalAmt10000;
	/**
	 * 온라인, PC, 신용카드, 일반배송, 10대, 온라인회원 남성 결제금액 합계
	 */
	private long orderAmt10000;
	/**
	 * 스마트, MOBILE, 무통장입금, 편의점픽업, 20대, 온라인회원 여성 수량 합계
	 */
	private int orderQty10001;
	/**
	 * 스마트, MOBILE, 무통장입금, 편의점픽업, 20대, 온라인회원 여성 정상가 합계
	 */
	private long prdtNormalAmt10001;
	/**
	 * 스마트, MOBILE, 무통장입금, 편의점픽업, 20대, 온라인회원 여성 결제금액 합계
	 */
	private long orderAmt10001;
	/**
	 * 매장, APP, 실시간계좌이체, 매장픽업, 30대, 온라인회원 알수없음 수량 합계
	 */
	private int orderQty10002;
	/**
	 * 매장, APP, 실시간계좌이체, 매장픽업, 30대, 온라인회원 알수없음 정상가 합계
	 */
	private long prdtNormalAmt10002;
	/**
	 * 매장, APP, 실시간계좌이체, 매장픽업, 30대, 온라인회원 알수없음 결제금액 합계
	 */
	private long orderAmt10002;
	/**
	 * 휴대폰결제, 입점사, 40대, 멤버십회원 남성 수량 합계
	 */
	private int orderQty10003;
	/**
	 * 휴대폰결제, 입점사, 40대, 멤버십회원 남성 정상가 합계
	 */
	private long prdtNormalAmt10003;
	/**
	 * 휴대폰결제, 입점사, 40대, 멤버십회원 남성 결제금액 합계
	 */
	private long orderAmt10003;
	/**
	 * 네이버페이, 50대, 멤버십회원 여성 수량 합계
	 */
	private int orderQty10004;
	/**
	 * 네이버페이, 50대,멤버십회원 여성 정상가 합계
	 */
	private long prdtNormalAmt10004;
	/**
	 * 네이버페이, 50대, 멤버십회원 여성 결제금액 합계
	 */
	private long orderAmt10004;
	/**
	 * 카카오페이, 60대, 비회원 수량 합계
	 */
	private int orderQty10005;
	/**
	 * 카카오페이, 60대, 비회원 정상가 합계
	 */
	private long prdtNormalAmt10005;
	/**
	 * 카카오페이, 60대, 비회원 결제금액 합계
	 */
	private long orderAmt10005;
	/**
	 * 기프트카드, 기타 수량 합계
	 */
	private int orderQty10006;
	/**
	 * 기프트카드, 기타 정상가 합계
	 */
	private long prdtNormalAmt10006;
	/**
	 * 기프트카드, 기타 결제금액 합계
	 */
	private long orderAmt10006;
	/**
	 * 포인트 수량 합계
	 */
	private int orderQty99999;
	/**
	 * 포인트 정상가 합계
	 */
	private long prdtNormalAmt99999;
	/**
	 * 포인트 결제금액 합계
	 */
	private long orderAmt99999;

	// 브랜드명
	private String brandName;
	// 카테고리 이름
	private String stdCtgrName;
	// 스타일정보
	private String styleInfo;
	// 색깔명
	private String prdtColorName;
	// 상품이름
	private String prdtName;
	// 사이트명
	private String siteName;
	// 상품코드 or 상품명
	private String prdtNoName;

}
