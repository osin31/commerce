package kr.co.abcmart.bo.settlement.vo;

import java.io.Serializable;

import kr.co.abcmart.common.bean.BaseBean;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc :
 * @FileName : MemberStatsVO.java
 * @Project : abc.bo
 * @Date : 2019. 6. 19.
 * @Author : choi
 */
@Slf4j
@Data
public class SettleMentVO extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1862741944074691065L;

	/**
	 * 주문 / 클레임 유형 배열
	 */
	private String[] deviceTypeCodeArr;

	/**
	 * 정산확정여부
	 */
	private String[] chkCalceArr;

	/**
	 * 정산월
	 */
	private String excclcYm;

	/**
	 * 브랜드
	 */
	private String siteNo;

	/**
	 * 시작 시간대
	 */
	private String startHour;

	/**
	 * 종료 시간대
	 */
	private String endHour;

	private String title; // 타이틀
	private String dtmTitle; // 시간대 타이틀
	private String total; // 전체 총합
	private String totot; // 전체 총합
	private String tomen; // 온라인회원 남자 총합
	private String tofemale; // 온라인회원 여자 총합
	private String toetc; // 온라인 기타 총합
	private String tmmen; // 멤버십 남자 총합
	private String tmfemale; // 멤버십 여자 총합
	private String tmtot; // 멤버십 전체 총합
	private String tmcmen; // 멤버십 전환 남자 총합
	private String tmcfemale; // 멤버십 전환 여자 총합
	private String tmctot; // 멤버십 전환 전체 총합

}
