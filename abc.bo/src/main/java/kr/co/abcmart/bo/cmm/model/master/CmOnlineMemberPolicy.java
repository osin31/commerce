package kr.co.abcmart.bo.cmm.model.master;

import kr.co.abcmart.bo.cmm.model.master.base.BaseCmOnlineMemberPolicy;
import kr.co.abcmart.bo.promotion.model.master.PrCoupon;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.util.UtilsMasking;
import kr.co.abcmart.util.UtilsText;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@ToString
public class CmOnlineMemberPolicy extends BaseCmOnlineMemberPolicy {
	private String rgstInfo;	// 등록자 정보
	private String no;			// 번호
	private String cpnName;		//쿠폰명
	private String cpnNo;		//쿠폰번호
	private String benefitIndex;//혜택'추가' 인덱스
	private String endDate;		// 종료일자
	private String loginId;		//로그인 ID
	private String adminName;	// 관리자명

	private String validTermGbnType;
	private java.sql.Timestamp validStartDtm;
	private java.sql.Timestamp validEndDtm;
	private java.lang.Short useLimitDayCount;

	private String mbshpMemberJoinCpnName;	// 멤버십 가입 쿠폰명
	private String onlnMemberJoinCpnName;	// 온라인 회원가입 쿠폰명
	private String birthDayCpnName;			// 생일 쿠폰명
	private String soldOutCmpnsCpnName;		// 품절보상 쿠폰명
	private String artAppLoginCpnName;			// A-RT APP 로그인 쿠폰명
	private String otsAppLoginCpnName;			// OTS APP 로그인 쿠폰명

	private String mbshpMemberJoinValidTermGbnType;
	private String onlnMemberJoinValidTermGbnType;
	private String birthDayValidTermGbnType;
	private String soldOutCmpnsValidTermGbnType;
	private String artAppLoginValidTermGbnType;
	private String otsAppLoginValidTermGbnType;

	private java.sql.Timestamp mbshpMemberJoinValidStartDtm;
	private java.sql.Timestamp onlnMemberJoinValidStartDtm;
	private java.sql.Timestamp birthDayValidStartDtm;
	private java.sql.Timestamp soldOutCmpnsValidStartDtm;
	private java.sql.Timestamp artAppLoginValidStartDtm;
	private java.sql.Timestamp otsAppLoginValidStartDtm;

	private java.sql.Timestamp mbshpMemberJoinValidEndDtm;
	private java.sql.Timestamp onlnMemberJoinValidEndDtm;
	private java.sql.Timestamp birthDayValidEndDtm;
	private java.sql.Timestamp soldOutCmpnsValidEndDtm;
	private java.sql.Timestamp artAppLoginValidEndDtm;
	private java.sql.Timestamp otsAppLoginValidEndDtm;

	private java.lang.Short mbshpMemberJoinUseLimitDayCount;
	private java.lang.Short onlnMemberJoinUseLimitDayCount;
	private java.lang.Short birthDayUseLimitDayCount;
	private java.lang.Short soldOutCmpnsUseLimitDayCount;
	private java.lang.Short artAppLoginUseLimitDayCount;
	private java.lang.Short otsAppLoginUseLimitDayCount;

	/**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 정책상세순번
     */
	private int plcyDtlSeq;

    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 지급수량
     */
	private int payQty;

    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 구매금액
     */
	private int buyAmt;

    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 구매건수
     */
	private int buyQty;

	public String getAdminInfo() {
		return UtilsText.concat(UtilsMasking.loginId(getLoginId()), Const.L_PAREN,
				UtilsMasking.userName(getAdminName()), Const.R_PAREN);
	}

	public String getPersonalAdminInfo() {
		String result = "";
		boolean memberInfoMgmtYn = LoginManager.isPersonalInfoManager();

		if(memberInfoMgmtYn) {
			result = UtilsText.concat(getLoginId(), Const.L_PAREN, getAdminName(), Const.R_PAREN);
		}else {
			result = UtilsText.concat(UtilsMasking.loginId(getLoginId()), Const.L_PAREN
					,UtilsMasking.userName(getAdminName()), Const.R_PAREN);
		}

		return result;
	}

	public String getValidDate() {
		PrCoupon prCoupon = new PrCoupon();

		prCoupon.setValidTermGbnType(getValidTermGbnType());
		prCoupon.setValidStartDtm(getValidStartDtm());
		prCoupon.setValidEndDtm(getValidEndDtm());
		prCoupon.setUseLimitDayCount(getUseLimitDayCount());
		return prCoupon.getValidDate();
	}

	public String getMbshpMemberJoinCpnName() {
		PrCoupon prCoupon = new PrCoupon();

		prCoupon.setValidTermGbnType(getMbshpMemberJoinValidTermGbnType());
		prCoupon.setValidStartDtm(getMbshpMemberJoinValidStartDtm());
		prCoupon.setValidEndDtm(getMbshpMemberJoinValidEndDtm());
		prCoupon.setUseLimitDayCount(getMbshpMemberJoinUseLimitDayCount());

		return this.mbshpMemberJoinCpnName + " (쿠폰 유효기간 " + prCoupon.getValidDate() + ")";
	}

	public String getOnlnMemberJoinCpnName() {
		PrCoupon prCoupon = new PrCoupon();

		prCoupon.setValidTermGbnType(getOnlnMemberJoinValidTermGbnType());
		prCoupon.setValidStartDtm(getOnlnMemberJoinValidStartDtm());
		prCoupon.setValidEndDtm(getOnlnMemberJoinValidEndDtm());
		prCoupon.setUseLimitDayCount(getOnlnMemberJoinUseLimitDayCount());

		return this.onlnMemberJoinCpnName + " (쿠폰 유효기간 " + prCoupon.getValidDate() + ")";
	}

	public String getBirthDayCpnName() {
		PrCoupon prCoupon = new PrCoupon();

		prCoupon.setValidTermGbnType(getBirthDayValidTermGbnType());
		prCoupon.setValidStartDtm(getBirthDayValidStartDtm());
		prCoupon.setValidEndDtm(getBirthDayValidEndDtm());
		prCoupon.setUseLimitDayCount(getBirthDayUseLimitDayCount());

		return this.birthDayCpnName + " (쿠폰 유효기간 " + prCoupon.getValidDate() + ")";
	}

	public String getSoldOutCmpnsCpnName() {
		PrCoupon prCoupon = new PrCoupon();

		prCoupon.setValidTermGbnType(getSoldOutCmpnsValidTermGbnType());
		prCoupon.setValidStartDtm(getSoldOutCmpnsValidStartDtm());
		prCoupon.setValidEndDtm(getSoldOutCmpnsValidEndDtm());
		prCoupon.setUseLimitDayCount(getSoldOutCmpnsUseLimitDayCount());

		return this.soldOutCmpnsCpnName + " (쿠폰 유효기간 " + prCoupon.getValidDate() + ")";
	}

	public String getArtAppLoginCpnName() {
		PrCoupon prCoupon = new PrCoupon();

		prCoupon.setValidTermGbnType(getArtAppLoginValidTermGbnType());
		prCoupon.setValidStartDtm(getArtAppLoginValidStartDtm());
		prCoupon.setValidEndDtm(getArtAppLoginValidEndDtm());
		prCoupon.setUseLimitDayCount(getArtAppLoginUseLimitDayCount());

		return this.artAppLoginCpnName + " (쿠폰 유효기간 " + prCoupon.getValidDate() + ")";
	}

	public String getOtsAppLoginCpnName() {
		PrCoupon prCoupon = new PrCoupon();

		prCoupon.setValidTermGbnType(getOtsAppLoginValidTermGbnType());
		prCoupon.setValidStartDtm(getOtsAppLoginValidStartDtm());
		prCoupon.setValidEndDtm(getOtsAppLoginValidEndDtm());
		prCoupon.setUseLimitDayCount(getOtsAppLoginUseLimitDayCount());

		return this.otsAppLoginCpnName + " (쿠폰 유효기간 " + prCoupon.getValidDate() + ")";
	}

}
