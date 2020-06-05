package kr.co.abcmart.bo.stats.model.master;

import java.io.Serializable;

import kr.co.abcmart.common.bean.BaseBean;
import lombok.Data;

@Data
public class SaCsStatus extends BaseBean implements Serializable {

	/**
	 * 기간 (일별)
	 */
	private String reviewDtm;

	/**
	 * 총 지급 포인트 합계
	 */
	private Integer totalPoint;

	/**
	 * 총 지급 건수 합계
	 */
	private Integer totalPayCount;

	/**
	 * 총 미지급 건수
	 */
	private Integer totalPayNo;

	/**
	 * 총 지급취소 건수
	 */
	private Integer totalPayCncl;

	/**
	 * 총 지급불가 건수
	 */
	private Integer totalPayImpsblt;

	/**
	 * 일반후기 지급 포인트 합계
	 */
	private Integer generalPoint;

	/**
	 * 일반후기 지급 건수 합계
	 */
	private Integer generalPayCount;

	/**
	 * 일반후기 미지급 건수
	 */
	private Integer generalPayNo;

	/**
	 * 일반후기 지급취소 건수
	 */
	private Integer generalPayCncl;

	/**
	 * 일반후기 지급불가 건수
	 */
	private Integer generalPayImpsblt;

	/**
	 * 포토후기 지급 포인트 합계
	 */
	private Integer photoPoint;

	/**
	 * 포토후기 지급 건수 합계
	 */
	private Integer photoPayCount;

	/**
	 * 포토후기 미지급 건수
	 */
	private Integer photoPayNo;

	/**
	 * 포토후기 지급취소 건수
	 */
	private Integer photoPayCncl;

	/**
	 * 포토후기 지급불가 건수
	 */
	private Integer photoPayImpsblt;

	/**
	 * 상품문의 작성 날짜
	 */
	private String inqryDtm;

	/**
	 * 상품문의 총 건수
	 */
	private String totalCount;

	/**
	 * 문의 답변 보류 건수
	 */
	private String aswrReady;

	/**
	 * 문의 미답변 건수
	 */
	private String aswrNo;

	/**
	 * 문의 답변 완료 건수
	 */
	private String aswrSuccess;

	/**
	 * 상품 문의 유형 List
	 */
	private String cnslList;

	/**
	 * 상품 문의 유형
	 */
	private String cnsl10001;
	private String cnsl10002;
	private String cnsl10003;
	private String cnsl10004;
	private String cnsl10005;
	private String cnsl10006;

}