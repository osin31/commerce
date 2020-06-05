package kr.co.abcmart.bo.promotion.model.master;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import kr.co.abcmart.bo.promotion.model.master.base.BasePrPlanningDisplay;
import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.request.FileUpload;
import kr.co.abcmart.common.util.UtilsMasking;
import kr.co.abcmart.common.util.UtilsObject;
import kr.co.abcmart.common.validation.Validator;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.util.UtilsText;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class PrPlanningDisplay extends BasePrPlanningDisplay implements Validator {

	private String pageType;

	private String chnnlName;

	/** 디바이스 코드 */
	private String deviceCodeAll;
	private String[] deviceCodeArr;
	private String deviceCodeStr; // 그리드 노출
	private String deviceCodeNameStr; // 그리드 노출

	/** 기획전 기간 */
	private String plndpStartD;
	private String plndpStartH;
	private String plndpStartM;
	private String plndpEndD;
	private String plndpEndH;
	private String plndpEndM;

	/** 기획전 대상 */
	private String memberTypeCodeAll;
	private String[] memberTypeCodeArr;
	private String[] mbshpGradeCodeArr;
	private String memberTypeCodeStr; // 그리드 노출

	/** 기획전 유형 */
	private String[] plndpTypeCodeArr;
	private String plndpTypeCodeAll;

	/** 파일 */
	private FileUpload pcImageFile;
	private FileUpload mobileImageFile;
	private FileUpload movieImageFile;
	private FileUpload movieFile;

	/** 관리자 정보 */
	private String rgsterName;
	private String rgsterId;
	private String rgsterInfo;

	private String moderName;
	private String moderId;
	private String moderInfo;

	private java.sql.Timestamp reqDtm;
	private java.sql.Timestamp aprverDtm;
	private String aprverName;
	private String aprverId;
	private String aprverInfo;

	/** 업체명 */
	private String vndrName;
	private String vndrNameLabel;

	/** 기획전 상태 */
	private String[] plndpStatCodeArr;
	private String plndpStatName;

	/** 리스트 검색 */
	private String[] chnnlNoArr;

	private String progress;
	private String progressAll;
	private String[] progressArr;

	private String searchType;
	private String searchWord;

	private String dateSearchType;
	private String dateSearchS;
	private String dateSearchE;

	private String mappingPrdtNo;

	/** 기획전 승인 관리 */
	private List<String> plndpNoList;
	private String returnRsnText;

	/** 에디터 업로드 */
	private FileUpload upload;

	private String isPreview;

	// 등록자 정보 마스킹 처리
	public String getRgsterInfo() {
		return UtilsText.concat(UtilsMasking.loginId(getRgsterId()), Const.L_PAREN,
				UtilsMasking.userName(getRgsterName()), Const.R_PAREN);
	}

	// 수정자 정보 마스킹 처리
	public String getModerInfo() {
		return UtilsText.concat(UtilsMasking.loginId(getModerId()), Const.L_PAREN,
				UtilsMasking.userName(getModerName()), Const.R_PAREN);
	}

	// 승인자 정보 마스킹 처리
	public String getAprverInfo() {
		if (UtilsObject.isNotEmpty(getAprverId()) && UtilsObject.isNotEmpty(getAprverName())) {
			return UtilsText.concat(UtilsMasking.loginId(getAprverId()), Const.L_PAREN,
					UtilsMasking.userName(getAprverName()), Const.R_PAREN);
		} else {
			return "";
		}
	}

	public String getPlndpDtm() {

		String startDtm = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(this.getPlndpStartDtm());
		String endDtm = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(this.getPlndpEndDtm());

		String result = startDtm + "~" + endDtm;

		return result;
	}

	public String getVndrNameLabel() {

		if (UtilsText.equals(getVndrGbnType(), Const.VNDR_GBN_TYPE_C)) {
			return "자사";
		} else if (UtilsText.equals(getVndrGbnType(), Const.VNDR_GBN_TYPE_V)) {
			return getVndrName();
		}

		return null;
	}

	public String getProgress() {

		Date today = new Date();

		if (getPlndpStartDtm() == null || getPlndpEndDtm() == null)
			return null;

		if (getPlndpStartDtm().compareTo(today) < 0 && getPlndpEndDtm().compareTo(today) > 0) {
			return "진행중";
		} else if (getPlndpStartDtm().compareTo(today) > 0) {
			return "대기";
		} else if (getPlndpEndDtm().compareTo(today) < 0) {
			return "종료";
		}

		return null;
	}

	@Override
	public void validate() throws ValidatorException {

		// 기획전 명
		if (UtilsText.isBlank(getPlndpName())) {
			validationFieldMessage("promotion.valid.planning.display.plndpName", "plndpName");
		} else {
			if (UtilsText.getByteLength(getPlndpName()) > 50) {
				validationFieldMessage("promotion.valid.planning.display.plndpNameLength", "plndpName");
			}
		}

		if (!UtilsText.equals(getPageType(), "PM")) {

			// 기획전 시작일시
			if (UtilsText.isBlank(getPlndpStartD())) {
				validationFieldMessage("promotion.valid.planning.display.plndpStartDtm", "plndpStartD");
			}
			// 기획전 종료일시
			if (UtilsText.isBlank(getPlndpEndD())) {
				validationFieldMessage("promotion.valid.planning.display.plndpEndDtm", "plndpEndD");
			}
			// 채널 구분
			if (UtilsText.isBlank(getChnnlNo())) {
				validationFieldMessage("promotion.valid.planning.display.chnnlNo", "chnnlNo");
			}
			// 대상회원
			if (UtilsText.equals(getVndrGbnType(), Const.VNDR_GBN_TYPE_C) && getMemberTypeCodeArr() == null
					&& getEmpYn() == null) {
				validationFieldMessage("promotion.valid.planning.display.memberTypeCode", "memberTypeCodeArr");
			}
			// 디바이스
			if (getDeviceCodeArr() == null) {
				validationFieldMessage("promotion.valid.planning.display.deviceCode", "deviceCodeAll");
			}
			// 검색어
			if (UtilsText.isBlank(getSrchWordText())) {
				validationFieldMessage("promotion.valid.planning.display.srchWordText", "srchWordText");
			}

			// 체크된 디바이스에 따라 유효성 체크
			boolean isPcChecked = false, isMobileChecked = false;
			if (UtilsObject.isNotEmpty(getDeviceCodeArr())) {
				for (String deviceCode : getDeviceCodeArr()) {
					if (UtilsText.equals(CommonCode.DEVICE_PC, deviceCode)) {
						isPcChecked = true;
					}
					if (UtilsText.equals(CommonCode.DEVICE_MOBILE, deviceCode)
							|| UtilsText.equals(CommonCode.DEVICE_APP, deviceCode)) {
						isMobileChecked = true;
					}
				}
			}
			// 기획전 PC 배너 이미지
			if (UtilsText.equals(getDispYn(), Const.BOOLEAN_TRUE)) {
				if (UtilsText.isBlank(getPcBannerImagePathText())
						&& (getPcImageFile() == null || (getPcImageFile() != null && !getPcImageFile().isFileItem()))
						&& isPcChecked) {
					validationFieldMessage("promotion.valid.planning.display.pcBannerImageUrl", "pcImageFile");
				}
				// 기획전 MOBILE 배너 이미지
				if (UtilsText.isBlank(getMobileBannerImagePathText())
						&& (getMobileImageFile() == null
								|| (getMobileImageFile() != null && !getMobileImageFile().isFileItem()))
						&& isMobileChecked) {
					validationFieldMessage("promotion.valid.planning.display.mobileBannerImageUrl", "mobileImageFile");
				}
				// 기획전 PC 상세설명
				if (UtilsText.isBlank(super.getPcDtlDescText()) && isPcChecked) {
					validationFieldMessage("promotion.valid.planning.display.pcDtlDescText", "pcDtlDescText");
				}
				// 기획전 MOBILE 상세설명
				if (UtilsText.isBlank(super.getMobileDtlDescText()) && isMobileChecked) {
					validationFieldMessage("promotion.valid.planning.display.mobileDtlDescText", "mobileDtlDescText");
				}
			}

			// 노출 순서
			if (getThreedImageDispSeq() == null || getMovieDispSeq() == null || getDtlDescDispSeq() == null) {
				validationFieldMessage("promotion.valid.planning.display.dispSeq", "threedImageDispSeq");
			}
			// 노출순서 중복체크
			int[] seqs = { getMovieDispSeq(), getDtlDescDispSeq(), getThreedImageDispSeq() };
			int dup = 0;
			for (int i = 0; i < seqs.length; i++) {
				for (int j = 0; j < seqs.length; j++) {
					if (seqs[i] == seqs[j] && seqs[i] != 0 && seqs[j] != 0)
						dup++;
				}
			}
			if (seqs.length < dup)
				validationFieldMessage("promotion.valid.planning.display.sortSeqDup", "threedImageDispSeq");
		}
	}
}