package kr.co.abcmart.bo.member.model.master;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import kr.co.abcmart.bo.member.model.master.base.BaseMbMember;
import kr.co.abcmart.common.bean.BaseJsonView;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.util.UtilsMasking;
import kr.co.abcmart.util.UtilsText;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class MbMember extends BaseMbMember {

	private String memberInfo;
	private String mbshpGradeCodeName;
	private java.sql.Timestamp inactDtm;
	private String memberState;
	private String memberTypeCodeName;
	private String dtmType;
	private String hdphnBackNoText;
	private String memberSearchType;
	private String toDate;
	private String fromDate;
	private String interestStore;
	private String interestBrand;
	private String interestCategory;
	private String memberSns;
	private String pswdChangeYn;
	private String anniversary;
	private String dtlAddressText;
	private String marketing;
	private String accountInfo;
	private String bankName;
	private int couponCnt;
	private String mailSendPswd;
	private String mailSendCode;
	private String lastOrderNo;
	private int condtnTermValue;
	private List<String> loginIds;
	private int statusCnt;

	/**
	 * 회원유형
	 */
	private String[] memberTypeCodes;

	/**
	 * 회원등급
	 */
	private String[] mbshpGradeCodes;

	/**
	 * 임직원
	 */
	private String chkMemberTypeERP;

	private MbMemberDeliveryAddress mbMemberDeliveryAddress;

	@JsonView(BaseJsonView.Read.class)
	public String getMemberInfo() {
		String maskingStr = "";
		if (UtilsText.equals(LoginManager.getUserDetails().getMemberInfoMgmtYn(), Const.BOOLEAN_FALSE)) {
			maskingStr = UtilsText.concat(UtilsMasking.loginId(this.getLoginId()), Const.L_PAREN,
					UtilsMasking.userName(this.getMemberName()), Const.R_PAREN);
		} else {
			maskingStr = UtilsText.concat(this.getLoginId(), Const.L_PAREN, this.getMemberName(), Const.R_PAREN);
		}
		return maskingStr;
	}

	public String getGridMemberName() {
		return UtilsText.concat(UtilsMasking.loginId(this.getLoginId()), Const.L_PAREN,
				UtilsMasking.userName(this.getMemberName()), Const.R_PAREN);
	}

	public String getGridEmailAddrText() {
		return UtilsMasking.emailAddress(this.getEmailAddrText());
	}

	public String getGridHdphnNoText() {
		return UtilsMasking.cellPhoneNumber(this.getHdphnNoText());
	}

	public String getDetailLoginId() {
		String maskingStr = "";
		if (UtilsText.equals(LoginManager.getUserDetails().getMemberInfoMgmtYn(), Const.BOOLEAN_FALSE)) {
			maskingStr = UtilsMasking.loginId(this.getLoginId());
		} else {
			maskingStr = this.getLoginId();
		}
		return maskingStr;
	}

	@JsonView(BaseJsonView.Read.class)
	public String getDetailMemberName() {
		String maskingStr = "";
		if (UtilsText.equals(LoginManager.getUserDetails().getMemberInfoMgmtYn(), Const.BOOLEAN_FALSE)) {
			maskingStr = UtilsMasking.userName(this.getMemberName());
		} else {
			maskingStr = this.getMemberName();
		}
		return maskingStr;
	}

	public String getDetailEmailAddrText() {
		String maskingStr = "";
		if (UtilsText.equals(LoginManager.getUserDetails().getMemberInfoMgmtYn(), Const.BOOLEAN_FALSE)) {
			maskingStr = UtilsMasking.emailAddress(this.getEmailAddrText());
		} else {
			maskingStr = this.getEmailAddrText();
		}
		return maskingStr;
	}

	public String getDetailHdphnNoText() {
		String maskingStr = "";
		if (UtilsText.equals(LoginManager.getUserDetails().getMemberInfoMgmtYn(), Const.BOOLEAN_FALSE)) {
			maskingStr = UtilsMasking.cellPhoneNumber(this.getHdphnNoText());
		} else {
			maskingStr = this.getHdphnNoText();
		}
		return maskingStr;
	}

}
