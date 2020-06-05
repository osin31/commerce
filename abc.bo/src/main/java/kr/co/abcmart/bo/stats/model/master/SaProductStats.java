package kr.co.abcmart.bo.stats.model.master;

import kr.co.abcmart.bo.stats.model.master.base.BaseSaProductStats;
import lombok.Data;

@Data
public class SaProductStats extends BaseSaProductStats {

	/**
	 * 업체명
	 */
	private String vndrName;
	/**
	 * 브랜드명
	 */
	private String brandName;
	/**
	 * 사이트명
	 */
	private String siteName;
	/**
	 * 카테고리
	 */
	private String stdCtgr;
	/**
	 * 상품코드
	 */
	private String pdCode;
	/**
	 * 온라인 상품코드
	 */
	private String onPdCode;
	/**
	 * 상품명
	 */
	private String pdName;
	/**
	 * 판매상태
	 */
	private String sellYn;
	/**
	 * 전시여부
	 */
	private String dispYn;
	// 변경전 수수료
	private String beforeRate;
	// 변경후 수수료
	private String afterRate;
	// 수수료 유형
	private String rateType;
	// 수정자
	private String rgsterName;
	// 수정일
	private String rgstDate;

	/**
	 * 합계
	 */
	private int totalCount;
	/**
	 * 판매대기
	 */
	private int sellWait;
	/**
	 * 판매중
	 */
	private int sellSelling;
	/**
	 * 일시품절
	 */
	private int sellPause;
	/**
	 * 판매중지
	 */
	private int sellStop;
	/**
	 * 임시저장
	 */
	private int stgTemp;
	/**
	 * 승인요청
	 */
	private int stgRequest;
	/**
	 * 승인대기
	 */
	private int stgWait;
	/**
	 * 승인반려
	 */
	private int stgReturn;
	/**
	 * 승인완료
	 */
	private int stgComplete;

	/**
	 * sns 공유 날짜
	 */
	private String snsDtm;
	private String snsTotal;
	// 페이스북
	private String sns10000;
	// 트위터
	private String sns10001;
	// 카카오스토리
	private String sns10002;
	// 네이버밴드
	private String sns10003;
	// 라인
	private String sns10004;
	// 카카오톡
	private String sns10005;
	// url 복사
	private String sns10006;

	private String prdtImageUrl;

}