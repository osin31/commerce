package kr.co.abcmart.bo.event.model.master;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import kr.co.abcmart.bo.event.model.master.base.BaseEvEvent;
import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.request.FileUpload;
import kr.co.abcmart.common.request.annotation.ParameterOption;
import kr.co.abcmart.common.util.UtilsDate;
import kr.co.abcmart.common.util.UtilsObject;
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
public class EvEvent extends BaseEvEvent implements Validator {

	private String eventTypeCodeName;

	private String deviceCodeName;

	private String chnnlName;

	private String eventProgressStatus;

	private String deviceCodes[];

	private String mbshpGradeCodes[];

	private String empYn;

	private String paramEventStartYmd;

	private String paramEventStartH;

	private String paramEventStartM;

	private String paramEventEndYmd;

	private String paramEventEndH;

	private String paramEventEndM;
	
	private String paramPrzwrPblcTodoYmd;
	private String paramPrzwrPblcTodoStartH;
	private String paramPrzwrPblcTodoStartM;
	
	private String paramPrzwrOrderStartYmd;
	private String paramPrzwrOrderStartH;
	private String paramPrzwrOrderStartM;

	private String paramPrzwrOrderEndYmd;
	private String paramPrzwrOrderEndH;
	private String paramPrzwrOrderEndM;
	

	@ParameterOption(target = "file")
	private FileUpload[] rouletteImages;

	private String[] rouletteImageChk;

	private FileUpload pcEventImg;

	private FileUpload moEventImg;

	private FileUpload pcEventBgImg;

	private FileUpload moEventBgImg;

	private FileUpload upload;

	private int resultCount;

	private int joinCount;

	private int eventPblicteNoCnt;

	private java.sql.Timestamp eventJoinDtm;

	private String memberTypeCodeName;

	private String eventTypeWithConditionYn;

	private String pblicteNoYn;

	private String chnnlNo;

	@ParameterOption(target = "chnnlNo")
	private EvEventTargetChannel[] evEventTargetChannelArr;

	@ParameterOption(target = "memberTypeCode")
	private EvEventTargetGrade[] evEventTargetGradeArr;

	@ParameterOption(target = "benefitType")
	private EvEventAttendanceCheckBenefit[] evEventAttendanceCheckBenefitArr;

	@ParameterOption(target = "eventRuletBenefitSeq")
	private EvEventRouletteBenefit[] evEventRouletteBenefitArr;

	@ParameterOption(target = "cpnNo")
	private EvEventTargetCoupon[] evEventTargetCouponArr;

	@ParameterOption(target = "imageName")
	private EvEventImage[] evEventImageArr;

	@ParameterOption(target = "prdtNo")
	private EvEventTargetProduct[] evEventTargetProductArr;

	@ParameterOption(target = "storeNo")
	private EvEventProductReceiptStore[] evEventProductReceiptStoreArr;

	private List<EvEventTargetGrade> evEventTargetGradeList;

	private int drawJoinCheckCnt;

	/* 관리자 정보 */
	private String rgsterName;
	private String rgsterId;

	private String moderName;
	private String moderId;

	private String pageType;

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

	public Timestamp getParamEventStartDtm() {
		Timestamp formatDate = null;
		if (UtilsText.isNotBlank(this.getParamEventStartYmd()) && UtilsText.isNotBlank(this.getParamEventStartH())
				&& UtilsText.isNotBlank(this.getParamEventStartM())) {
			formatDate = new Timestamp(
					UtilsDate.parseDate(UtilsText.concat(this.getParamEventStartYmd(), " ", this.getParamEventStartH(),
							":", this.getParamEventStartM(), ":", "00"), "yyyy.MM.dd HH:mm:ss").getTime());
		}
		return formatDate;
	}

	public Timestamp getParamEventEndDtm() {
		Timestamp formatDate = null;
		if (UtilsText.isNotBlank(this.getParamEventEndYmd()) && UtilsText.isNotBlank(this.getParamEventEndH())
				&& UtilsText.isNotBlank(this.getParamEventEndM())) {
			formatDate = new Timestamp(
					UtilsDate.parseDate(UtilsText.concat(this.getParamEventEndYmd(), " ", this.getParamEventEndH(), ":",
							this.getParamEventEndM(), ":", "59"), "yyyy.MM.dd HH:mm:ss").getTime());
		}
		return formatDate;
	}
	

	public Timestamp getPrzwrPblcTodoDtm() {
		Timestamp formatDate = null;
		if(UtilsText.isNotBlank(this.getParamPrzwrPblcTodoYmd())) {
			String[] date= UtilsText.split(this.getParamPrzwrPblcTodoYmd(), ".");
			if(date.length == 3) {
				int year = Integer.parseInt(date[0]);
				int month = Integer.parseInt(date[1]);
				int dayOfMonth = Integer.parseInt(date[2]);
				int hour = Integer.parseInt(this.getParamPrzwrPblcTodoStartH());
				int minute = Integer.parseInt(this.getParamPrzwrPblcTodoStartM());
				formatDate = Timestamp.valueOf(LocalDateTime.of(year, month, dayOfMonth, hour, minute));
			}
		}
		return formatDate;
	}
	
	public Timestamp getPrzwrOrderStartDtm() {
		Timestamp formatDate = null;
		if(UtilsText.isNotBlank(this.getParamPrzwrOrderStartYmd())) {
			String[] date= UtilsText.split(this.getParamPrzwrOrderStartYmd(), ".");
			if(date.length == 3) {
				int year = Integer.parseInt(date[0]);
				int month = Integer.parseInt(date[1]);
				int dayOfMonth = Integer.parseInt(date[2]);
				int hour = Integer.parseInt(this.getParamPrzwrOrderStartH());
				int minute = Integer.parseInt(this.getParamPrzwrOrderStartM());
				formatDate = Timestamp.valueOf(LocalDateTime.of(year, month, dayOfMonth, hour, minute, 0));
			}
		}
		return formatDate;
	}
	
	public Timestamp getPrzwrOrderEndDtm() {
		Timestamp formatDate = null;
		if(UtilsText.isNotBlank(this.getParamPrzwrOrderEndYmd())) {
			String[] date= UtilsText.split(this.getParamPrzwrOrderEndYmd(), ".");
			if(date.length == 3) {
				int year = Integer.parseInt(date[0]);
				int month = Integer.parseInt(date[1]);
				int dayOfMonth = Integer.parseInt(date[2]);
				int hour = Integer.parseInt(this.getParamPrzwrOrderEndH());
				int minute = Integer.parseInt(this.getParamPrzwrOrderEndM());
				formatDate = Timestamp.valueOf(LocalDateTime.of(year, month, dayOfMonth, hour, minute, 59));
			}
		}
		return formatDate;
	}
	
	public String getPrzwrPblcName() {
		String przwrPblcName = "";
		if (UtilsText.isNotBlank(this.getPrzwrPblcYn()) && UtilsText.equals(this.getPrzwrPblcYn(), "Y")) {
			if (this.getResultCount() > 0)
				przwrPblcName = "등록";
			else
				przwrPblcName = "등록안함";
		} else {
			przwrPblcName = "-";
		}
		return przwrPblcName;
	}

	public String getResultName() {
		String resultName = "";
		if (this.getResultCount() > 0) {
			resultName = "결과보기";
		} else {
			resultName = "";
		}
		return resultName;
	}

	@Override
	public void validate() throws ValidatorException {
		// 임시저장일때
		if (UtilsText.isNotBlank(this.getTmprSaveYn()) && UtilsText.equals(this.getTmprSaveYn(), "Y")) {
			if (UtilsText.isBlank(this.getEventName())) {
				validationMessage("promotion.valid.event.eventName");
			}

			if (UtilsText.isNotBlank(this.getEventTermSetupYn()) && UtilsText.equals(this.getEventTermSetupYn(), "Y")) {
				if (UtilsText.isBlank(this.getParamEventStartYmd()) || UtilsText.isBlank(this.getParamEventEndYmd())) {
					validationMessage("promotion.valid.event.eventYmd");
				}
			}

			if (this.getEvEventTargetGradeArr() == null && UtilsText.isBlank(this.empYn)) {
				validationMessage("promotion.valid.event.memberTypeCode");
			}

			if (UtilsText.equals(this.getEventTypeCode(),
					CommonCode.EVENT_TYPE_CODE_PARTICIPATION_TYPE_ATTENDANCE_CHECK)) {
				if (this.getEvEventAttendanceCheckBenefitArr() != null) {
					for (EvEventAttendanceCheckBenefit evEventAttendanceCheckBenefit : this
							.getEvEventAttendanceCheckBenefitArr()) {
						if (evEventAttendanceCheckBenefit.getAtendDayCount() == null) {
							validationMessage("promotion.valid.event.atendDayCount");
						}
					}
				}
			}
		} else {
			if (UtilsText.isBlank(this.getEventTypeCode())
					&& UtilsText.equals(this.getEventTypeWithConditionYn(), "N")) {
				validationMessage("promotion.valid.event.eventTypeCode");
			} else {
				if (UtilsText.equals(this.getEventTypeCode(),
						CommonCode.EVENT_TYPE_CODE_PARTICIPATION_TYPE_ATTENDANCE_CHECK)) {
					if (this.getEvEventAttendanceCheckBenefitArr() != null) {
						for (EvEventAttendanceCheckBenefit evEventAttendanceCheckBenefit : this
								.getEvEventAttendanceCheckBenefitArr()) {
							if (evEventAttendanceCheckBenefit.getAtendDayCount() == null) {
								validationMessage("promotion.valid.event.atendDayCount");
							}
						}
					}
				}

				if (UtilsText.equals(this.getEventTypeCode(), CommonCode.EVENT_TYPE_CODE_PARTICIPATION_TYPE_ROULETTE)) {
					if (this.getEvEventRouletteBenefitArr() != null) {
						for (EvEventRouletteBenefit evEventRouletteBenefit : this.getEvEventRouletteBenefitArr()) {
							if (UtilsText.isBlank(evEventRouletteBenefit.getBenefitName())) {
								validationMessage("promotion.valid.event.benefitName");
							}
						}
					}

					// 이미지
					if (UtilsObject.isNotEmpty(pcEventBgImg) && pcEventBgImg.isFileItem()) {
						// 파일이 첨부된 경우. (신규등록 또는 수정등록)
						if (!pcEventBgImg.isAllowExtAndMimeType(new String[] { "jpg", "jpeg", "png", "gif", "bmp" })) {
							this.validationMessage("promotion.valid.event.uploadFileImage.extension");
						}
					}

					if (UtilsObject.isNotEmpty(moEventBgImg) && moEventBgImg.isFileItem()) {
						// 파일이 첨부된 경우. (신규등록 또는 수정등록)
						if (!moEventBgImg.isAllowExtAndMimeType(new String[] { "jpg", "jpeg", "png", "gif", "bmp" })) {
							this.validationMessage("promotion.valid.event.uploadFileImage.extension");
						}
					}
				}

				if (UtilsText.equals(this.getEventTypeCode(), CommonCode.EVENT_TYPE_CODE_PARTICIPATION_TYPE_DRAW)) {
					if (this.getParamPrzwrOrderStartYmd() == null || this.getParamPrzwrOrderEndYmd() == null) {
						validationMessage("promotion.valid.event.przwrOrderYmd");
					}
				}
			}

			if (UtilsText.isBlank(this.getEventName())) {
				validationMessage("promotion.valid.event.eventName");
			}

			if (UtilsText.isNotBlank(this.getEventTermSetupYn()) && UtilsText.equals(this.getEventTermSetupYn(), "Y")) {
				if (UtilsText.isBlank(this.getParamEventStartYmd()) || UtilsText.isBlank(this.getParamEventEndYmd())) {
					validationMessage("promotion.valid.event.eventYmd");
				}
			}

			if (this.getEvEventTargetGradeArr() == null && UtilsText.isBlank(this.empYn)) {
				validationMessage("promotion.valid.event.memberTypeCode");
			}

			if (UtilsText.isBlank(this.getSrchKeyWord())) {
				validationMessage("promotion.valid.event.srchKeyWord");
			}

			/*
			 * if (UtilsText.isBlank(this.getPcEventContText()) ||
			 * UtilsText.isBlank(this.getMobileEventContText())) {
			 * validationMessage("promotion.valid.event.eventImageContText"); }
			 */

			if (this.getDeviceCodes() == null) {
				validationMessage("promotion.valid.event.device");
			}

			/* if (this.getChnnlNo() == null) { */
			if (this.getEvEventTargetChannelArr() == null) {
				validationMessage("promotion.valid.event.chnnl");
			}

			// 이미지
			if (UtilsObject.isNotEmpty(pcEventImg) && pcEventImg.isFileItem()) {
				// 파일이 첨부된 경우. (신규등록 또는 수정등록)
				if (!pcEventImg.isAllowExtAndMimeType(new String[] { "jpg", "jpeg", "png", "gif", "bmp" })) {
					this.validationMessage("promotion.valid.event.uploadFileImage.extension");
				}
			}

			if (UtilsObject.isNotEmpty(moEventImg) && moEventImg.isFileItem()) {
				// 파일이 첨부된 경우. (신규등록 또는 수정등록)
				if (!moEventImg.isAllowExtAndMimeType(new String[] { "jpg", "jpeg", "png", "gif", "bmp" })) {
					this.validationMessage("promotion.valid.event.uploadFileImage.extension");
				}
			}
		}

	}

}
