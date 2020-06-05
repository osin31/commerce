package kr.co.abcmart.bo.display.model.master;

import java.sql.Time;
import java.sql.Timestamp;

import kr.co.abcmart.bo.display.model.master.base.BaseBdPopup;
import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.request.FileUpload;
import kr.co.abcmart.common.request.annotation.ParameterOption;
import kr.co.abcmart.common.util.UtilsDate;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.common.validation.Validator;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.util.UtilsMasking;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class BdPopup extends BaseBdPopup implements Validator {
	// 팝업 디바이스 param
	@ParameterOption(target = "deviceCode")
	private BdPopupDevice bdPopupDevice;

	private String[] deviceCodes;

	private String devicePopupType;

	// 팝업 전시위치 param
	@ParameterOption(target = "popupDispPostnCode")
	private BdPopupDisplayPosition[] bdPopupDisplayPositionPcArr;

	@ParameterOption(target = "popupDispPostnCode")
	private BdPopupDisplayPosition[] bdPopupDisplayPositionMoArr;

	// 전시기간 Date param
	private String paramStartYmd;

	private String paramEndYmd;

	private String paramStartH;

	private String paramEndH;

	private String paramStartM;

	private String paramEndM;

	// 특정일 전시 Date param
	private String paramDayStartH;

	private String paramDayEndH;

	private String paramDayStartM;

	private String paramDayEndM;

	private FileUpload pcImageFile;

	private FileUpload moImageFile;

	// result
	private String deviceCodeName;

	private String popupType;

	// preview
	private String imageSrc;

	/* 관리자 정보 */
	private String rgsterName;
	private String rgsterId;

	private String moderName;
	private String moderId;

	private String rgsterNo;
	private String moderNo;

	private String pageType;

	/* 미리보기용 */
	private String pcImageUrl;
	private String mobileImageUrl;
	private String isPreview;

	// 등록자 정보 마스킹 처리
	public String getRgsterInfo() {

		String maskingStr = "";
		if (UtilsText.equals(this.getPageType(), "L")) {
			maskingStr = UtilsText.concat(UtilsMasking.loginId(this.rgsterId), Const.L_PAREN,
					UtilsMasking.userName(this.rgsterName), Const.R_PAREN);
		} else {
			if (UtilsText.equals(LoginManager.getUserDetails().getMemberInfoMgmtYn(), Const.BOOLEAN_FALSE)) {
				maskingStr = UtilsText.concat(UtilsMasking.loginId(this.rgsterId), Const.L_PAREN,
						UtilsMasking.userName(this.rgsterName), Const.R_PAREN);
			} else {
				maskingStr = UtilsText.concat(this.rgsterId, Const.L_PAREN, this.rgsterName, Const.R_PAREN);
			}
		}

		return maskingStr;
	}

	// 수정자 정보 마스킹 처리
	public String getModerInfo() {
		String maskingStr = "";

		if (UtilsText.equals(this.getPageType(), "L")) {
			maskingStr = UtilsText.concat(UtilsMasking.loginId(this.moderId), Const.L_PAREN,
					UtilsMasking.userName(this.moderName), Const.R_PAREN);
		} else {
			if (UtilsText.equals(LoginManager.getUserDetails().getMemberInfoMgmtYn(), Const.BOOLEAN_FALSE)) {
				maskingStr = UtilsText.concat(UtilsMasking.loginId(this.moderId), Const.L_PAREN,
						UtilsMasking.userName(this.moderName), Const.R_PAREN);
			} else {
				maskingStr = UtilsText.concat(this.moderId, Const.L_PAREN, this.moderName, Const.R_PAREN);
			}
		}
		return maskingStr;
	}

	public String getDispDate() {
		String formatDate = null;
		if (this.getDispStartDtm() != null && this.getDispEndDtm() != null) {
			formatDate = UtilsText.concat(UtilsDate.formatter("yyyy.MM.dd HH:mm", this.getDispStartDtm()), " ~ ",
					UtilsDate.formatter("yyyy.MM.dd HH:mm", this.getDispEndDtm()));
		}

		return formatDate;
	}

	public Timestamp getParamDispStartDtm() {
		Timestamp formatDate = null;
		if (UtilsText.isNotBlank(this.getParamStartYmd()) && UtilsText.isNotBlank(this.getParamStartH())
				&& UtilsText.isNotBlank(this.getParamStartM())) {
			formatDate = new Timestamp(UtilsDate.parseDate(UtilsText.concat(this.getParamStartYmd(), " ",
					this.getParamStartH(), ":", this.getParamStartM(), ":", "00"), "yyyy.MM.dd HH:mm:ss").getTime());
		}
		return formatDate;
	}

	public Timestamp getParamDispEndDtm() {
		Timestamp formatDate = null;
		if (UtilsText.isNotBlank(this.getParamEndYmd()) && UtilsText.isNotBlank(this.getParamEndH())
				&& UtilsText.isNotBlank(this.getParamEndM())) {
			formatDate = new Timestamp(UtilsDate.parseDate(UtilsText.concat(this.getParamEndYmd(), " ",
					this.getParamEndH(), ":", this.getParamEndM(), ":", "59"), "yyyy.MM.dd HH:mm:ss").getTime());
		}
		return formatDate;
	}

	public Time getParamDayStartTime() {
		Time formatDate = null;
		if (this.getParamDayStartH() != null && this.getParamDayStartM() != null) {
			formatDate = new Time(UtilsDate
					.parseDate(UtilsText.concat(this.getParamDayStartH(), ":", this.getParamDayStartM(), ":", "00"),
							"HH:mm:ss")
					.getTime());
		}
		return formatDate;
	}

	public Time getParamDayEndTime() {
		Time formatDate = null;
		if (this.getParamDayEndH() != null && this.getParamDayEndM() != null) {
			formatDate = new Time(UtilsDate
					.parseDate(UtilsText.concat(this.getParamDayEndH(), ":", this.getParamDayEndM(), ":", "59"),
							"HH:mm:ss")
					.getTime());
		}
		return formatDate;
	}

	@Override
	public void validate() throws ValidatorException {
		if (UtilsText.isBlank(getPopupTitleText())) {
			validationMessage("display.popup.valid.title");
		}

		if (UtilsText.equals(getDispYn(), "Y")) {
			if (UtilsText.isBlank(getParamStartYmd()) || UtilsText.isBlank(getParamEndYmd())) {
				validationMessage("display.popup.valid.dispYmd");
			}
		}

		if (UtilsText.equals(this.getPopupType(), "G")) {
			if (getPriorSeq() == null) {
				validationMessage("display.popup.valid.priorSeq");
			}

			if (UtilsText.isBlank(this.getDeviceCode())) {
				for (BdPopupDisplayPosition bdPopupDisplayPosition : getBdPopupDisplayPositionPcArr()) {
					if (UtilsText.isBlank(bdPopupDisplayPosition.getDispPostnUrl())
							&& !UtilsText.equals(bdPopupDisplayPosition.getPopupDispPostnCode(), "10000")) {
						validationMessage("display.popup.valid.dispPosition");
					}
				}

				for (BdPopupDisplayPosition bdPopupDisplayPosition : getBdPopupDisplayPositionMoArr()) {
					if (UtilsText.isBlank(bdPopupDisplayPosition.getDispPostnUrl())
							&& !UtilsText.equals(bdPopupDisplayPosition.getPopupDispPostnCode(), "10000")) {
						validationMessage("display.popup.valid.dispPosition");
					}
				}

				if (!UtilsText.equals(bdPopupDevice.getPcCnnctnType(), "N")
						&& UtilsText.isBlank(bdPopupDevice.getPcLinkInfo())) {
					validationMessage("display.popup.valid.linkUrText");
				}

				if (!UtilsText.equals(bdPopupDevice.getMoCnnctnType(), "N")
						&& UtilsText.isBlank(bdPopupDevice.getMoLinkInfo())) {
					validationMessage("display.popup.valid.linkUrText");
				}

				if (UtilsText.isBlank(bdPopupDevice.getPcImageName())
						|| UtilsText.isBlank(bdPopupDevice.getMoImageName())) {
					validationMessage("display.popup.valid.popupImage");
				}
			} else if (UtilsText.equals(this.getDeviceCode(), CommonCode.DEVICE_PC)) {
				for (BdPopupDisplayPosition bdPopupDisplayPosition : getBdPopupDisplayPositionPcArr()) {
					if (UtilsText.isBlank(bdPopupDisplayPosition.getDispPostnUrl())
							&& !UtilsText.equals(bdPopupDisplayPosition.getPopupDispPostnCode(), "10000")) {
						validationMessage("display.popup.valid.dispPosition");
					}
				}

				if (!UtilsText.equals(bdPopupDevice.getPcCnnctnType(), "N")
						&& UtilsText.isBlank(bdPopupDevice.getPcLinkInfo())) {
					validationMessage("display.popup.valid.linkUrText");
				}

				if (UtilsText.isBlank(bdPopupDevice.getPcImageName())) {
					validationMessage("display.popup.valid.popupImage");
				}

				if (bdPopupDevice.getPcPopupXPostn() == null || bdPopupDevice.getPcPopupYPostn() == null) {
					validationMessage("display.popup.valid.popupPosition");
				}
			} else if (UtilsText.equals(this.getDeviceCode(), CommonCode.DEVICE_MOBILE)) {
				for (BdPopupDisplayPosition bdPopupDisplayPosition : getBdPopupDisplayPositionMoArr()) {
					if (UtilsText.isBlank(bdPopupDisplayPosition.getDispPostnUrl())
							&& !UtilsText.equals(bdPopupDisplayPosition.getPopupDispPostnCode(), "10000")) {
						validationMessage("display.popup.valid.dispPosition");
					}
				}

				if (!UtilsText.equals(bdPopupDevice.getMoCnnctnType(), "N")
						&& UtilsText.isBlank(bdPopupDevice.getMoLinkInfo())) {
					validationMessage("display.popup.valid.linkUrText");
				}

				if (UtilsText.isBlank(bdPopupDevice.getMoImageName())) {
					validationMessage("display.popup.valid.popupImage");
				}
			}
		} else {
			if (UtilsText.isBlank(bdPopupDevice.getPcImageName())
					|| UtilsText.isBlank(bdPopupDevice.getMoImageName())) {
				validationMessage("display.popup.valid.popupImage");
			}
		}
	}
}
