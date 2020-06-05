package kr.co.abcmart.bo.cmm.model.master;

import kr.co.abcmart.bo.cmm.model.master.base.BaseCmPushMessage;
import kr.co.abcmart.common.request.FileUpload;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.util.UtilsMasking;
import kr.co.abcmart.util.UtilsText;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class CmPushMessage extends BaseCmPushMessage {
	// push메시지
	private String rgsterName;
	private String rgsterId;
	private String moderName;
	private String moderId;
	private java.sql.Timestamp sendIrDtm;
	private FileUpload imageFile;
	private String isFileChange;
	private String pushDate;
	private String pushHours;
	private String pushMin;
	private String fromDate;
	private String toDate;
	private String searchDateKey;
	private String siteName;

	// 발송관리
	private java.sql.Timestamp setupDtm;
	private String sendType;
	private String sendTypeName;
	private String sendGbnType;
	private String sendGbnTypeName;
	private String pushIngStatCode;
	private String pushIngStatCodeName;
	private FileUpload excelFile;

	private String memberNo;
	private String memberName;
	private String loginId;
	private java.lang.Long appDwldMemberSeq;
	private String appOsCode;
	private String appVerText;
	private String fcmTokenText;
	private int sendNum;
	private int sendPerOnce;

	/**
	 * @Desc : 작성자 정보(마스킹)
	 * @Method Name : setMaskingRsterInfo
	 * @Date : 2019. 6. 10.
	 * @Author : 고웅환
	 * @return
	 */
	public String getRgsterInfo() {
		String maskingText = "";

		if (null != this.getRgsterId() || null != this.getRgsterName()) {
			if (LoginManager.isPersonalInfoManager()) {
				maskingText = UtilsText.concat(this.getRgsterId(), Const.L_PAREN, this.getRgsterName(), Const.R_PAREN);
			} else {
				maskingText = UtilsText.concat(UtilsMasking.loginId(this.getRgsterId()), Const.L_PAREN,
						UtilsMasking.userName(this.getRgsterName()), Const.R_PAREN);
			}
		}

		return maskingText;
	}

	/**
	 * @Desc : 작성자 정보(마스킹) 권한 X
	 * @Method Name : setMaskingRsterInfo
	 * @Date : 2019. 6. 10.
	 * @Author : 고웅환
	 * @return
	 */
	public String getMaskingRgsterInfo() {
		String maskingText = "";

		if (null != this.getRgsterId() || null != this.getRgsterName()) {
			maskingText = UtilsText.concat(UtilsMasking.loginId(this.getRgsterId()), Const.L_PAREN,
					UtilsMasking.userName(this.getRgsterName()), Const.R_PAREN);
		}

		return maskingText;
	}

	/**
	 * @Desc : 수정자 정보(마스킹)
	 * @Method Name : setMaskingModInfo
	 * @Date : 2019. 6. 10.
	 * @Author : 고웅환
	 * @return
	 */
	public String getModerInfo() {
		String maskingText = "";

		if (null != this.getModerId() || null != this.getModerName()) {
			if (LoginManager.isPersonalInfoManager()) {
				maskingText = UtilsText.concat(this.getModerId(), Const.L_PAREN, this.getModerName(), Const.R_PAREN);
			} else {
				maskingText = UtilsText.concat(UtilsMasking.loginId(this.getModerId()), Const.L_PAREN,
						UtilsMasking.userName(this.getModerName()), Const.R_PAREN);
			}
		}

		return maskingText;
	}

	/**
	 * @Desc : 수정자 정보(마스킹) 권한 X
	 * @Method Name : setMaskingModInfo
	 * @Date : 2019. 6. 10.
	 * @Author : 고웅환
	 * @return
	 */
	public String getMaskingModerInfo() {
		String maskingText = "";

		if (null != this.getModerId() || null != this.getModerName()) {
			maskingText = UtilsText.concat(UtilsMasking.loginId(this.getModerId()), Const.L_PAREN,
					UtilsMasking.userName(this.getModerName()), Const.R_PAREN);
		}

		return maskingText;
	}

	/**
	 * @Desc : 테스트발송대상 회원 아이디 마스킹
	 * @Method Name : setMaskingLoginId
	 * @Date : 2019. 8. 08.
	 * @Author : 고웅환
	 * @return
	 */
	public String getLoginIdInfo() {
		String maskingText = "";

		if (LoginManager.isPersonalInfoManager()) {
			maskingText = this.getLoginId();
		} else {
			if (null != this.getLoginId()) {
				maskingText = UtilsMasking.loginId(this.getLoginId());
			}
		}

		return maskingText;
	}

	/**
	 * @Desc : 테스트발송대상 회원 이름 마스킹
	 * @Method Name : setMaskingMemberName
	 * @Date : 2019. 8. 08.
	 * @Author : 고웅환
	 * @return
	 */
	public String getMemberNameInfo() {
		String maskingText = "";

		if (LoginManager.isPersonalInfoManager()) {
			maskingText = this.getMemberName();
		} else {
			if (null != this.getMemberName()) {
				maskingText = UtilsMasking.userName(this.getMemberName());
			}
		}
		return maskingText;
	}

}
