package kr.co.abcmart.bo.event.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class EvEventSearchVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String eventNo;

	/** 목록 개수 */
	private int pageCount;

	/** 날짜 타입 */
	private String dateType;

	/** 시작일시 */
	private String startYmd;

	/** 종료일시 */
	private String endYmd;

	/** 사용여부 */
	private String useYn;

	/** 검색 타입 */
	private String keywordType;

	/** 검색어 */
	private String keyword;

	/** 디바이스 */
	private String[] deviceCodes;

	/** 채널 */
	private String[] chnnlNos;

	/** 회원 등급 배열 */
	private String[] mbshpGradeCodes;

	/** 회원 유형 배열 */
	private String[] memberTypeCodes;

	/** 당첨자 유무 */
	private String przwrPblcYn;

	/** 당첨자 등록 여부 */
	private String[] answerYns;

	/** 임직원여부 */
	private String empYn;

	/** 이벤트 유형 */
	private String eventTypeCode;

	/** 당첨자 발표 여부 */
	private String pblcYn;

	/** 노출 여부 */
	private String dispYn;

	/** A-RT 전시 여부 */
	private String artDispYn;

	private String[] dispYnArr;

	/** 당쳠 여부 */
	private String winYn;

	/** 이벤트 참여 회원 순번 */
	private String eventJoinMemberSeq;

	/** 진행상태 */
	private String eventProgressStatus;

	private String[] eventProgressStatusArr;

	/** 로그인 아이디 */
	private String loginId;

	/** 회원번호 */
	private String memberNo;

	/** 발급여부 */
	private String issueYn;

	private String[] issueYnArr;

	/** 이벤트 혜택 순번 */
	private String eventRsltBenefitSeq;
}
