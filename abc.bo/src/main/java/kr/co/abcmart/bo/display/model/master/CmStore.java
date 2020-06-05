package kr.co.abcmart.bo.display.model.master;

import kr.co.abcmart.bo.display.model.master.base.BaseCmStore;
import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.request.FileUpload;
import kr.co.abcmart.common.util.UtilsMasking;
import kr.co.abcmart.common.validation.Validator;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.util.UtilsText;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class CmStore extends BaseCmStore implements Validator {

	/** 목록 개수 */
	private int pageCount;

	/** 이미지 파일 */
	private FileUpload pcImageFile;
	private FileUpload mobileImageFile;

	/** 검색 조건 */
	private String searchType;
	private String searchWord;

	/** 지역명 */
	private String areaName;

	/** 주소 */
	private String fullAddr;

	/** 휴대폰 번호 */
	private String telNoText1;
	private String telNoText2;
	private String telNoText3;

	/** 영업시간 */
	private String businessStartHour;
	private String businessStartMinute;
	private String businessEndHour;
	private String businessEndMinute;

	/** 매장 제공 서비스 */
	private String storeServiceArr;
	private String storeServiceCodeAll;
	private String[] storeServiceCode;

	/** 매장형태 */
	private String[] storeTypeCodes;

	/** 지도 좌표 */
	private String pointX;
	private String pointY;

	/* 관리자 정보 */
	private String rgsterName;
	private String rgsterId;
	private String rgsterInfo;

	private String moderName;
	private String moderId;
	private String moderInfo;

	/* 휴대폰 번호 format */
	private String telNoFormat;

	/* 매장 검색 */
	private String searchStoreName;
	
	private String businessStartTimeString;
	private String businessEndTimeString;
	private String rgstDtmString;
	private String modDtmString;

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
	/* 관리자 정보 */

	public String getPointX() {
		if (getXPointText() != null) {
			return Float.toString(getXPointText());
		} else {
			return this.pointX;
		}
	}

	public String getPointY() {
		if (getYPointText() != null) {
			return Float.toString(getYPointText());
		} else {
			return this.pointY;
		}
	}

	public String getFullAddr() {

		String dtlAddr = getDtlAddrText() == null ? "" : getDtlAddrText();

		String fullAddr = getPostAddrText() + " " + dtlAddr;

		return fullAddr;
	}

	public String getTelNoFormat() {

		if (this.getTelNoText() != null && this.getTelNoText() != "") {
			String regExp = "(^02.{0}|^01.{1}|[0-9]{3})([0-9]+)([0-9]{4})";
			String result = this.getTelNoText().replaceFirst(regExp, "$1-$2-$3");
			return result;
		}

		return null;
	}

	@Override
	public void validate() throws ValidatorException {

		// 전시 사이트
		if (UtilsText.isBlank(getSiteNo())) {
			validationMessage("display.store.valid.siteNo");
		}

		// 전시여부
		if (UtilsText.isBlank(getDispYn())) {
			validationMessage("display.store.valid.dispYn");
		}

		// 매장명
		if (UtilsText.isBlank(getStoreName())) {
			validationFieldMessage("display.store.valid.storeName", "storeName");
		}

		// 전화번호
		if (UtilsText.isBlank(getTelNoText())) {
			validationFieldMessage("display.store.valid.telNoText", "telNoText1");
		} else {
			String regExp = "^\\d{2,3}-\\d{3,4}-\\d{4}$";

			if (!this.getTelNoFormat().matches(regExp))
				validationFieldMessage("display.store.valid.telNoTextRegExp", "telNoText1");
		}

		// 택배연동 우편번호
		if (UtilsText.isBlank(getPostCodeText())) {
			validationFieldMessage("display.store.valid.postCodeText", "postCodeText");
		}

		// 택배연동 주소
		if (UtilsText.isBlank(getPostAddrText())) {
			validationFieldMessage("display.store.valid.postAddrText", "postAddrText");
		}

		// 택배연동 상세 주소
		if (UtilsText.isBlank(getDtlAddrText())) {
			validationFieldMessage("display.store.valid.dtlAddrText", "dtlAddrText");
		}

		// 지역구분:광역시/도
		if (UtilsText.isBlank(getAreaNo())) {
			validationMessage("display.store.valid.areaNo");
		}

		// 지역구분:구/군
		if (UtilsText.isBlank(getAreaDtlSeq() + "")) {
			validationMessage("display.store.valid.areaDtlSeq");
		}

		// 직영여부
		if (UtilsText.isBlank(getDirectYn())) {
			validationMessage("display.store.valid.directYn");
		}

		// 매장 제공 서비스
		if (getStoreServiceCode() == null) {
			validationMessage("display.store.valid.storeService");
		}

		// 지도 좌표
		if (getPointX() == null) {
			validationMessage("display.store.valid.xPointText");
		}
		if (getPointY() == null) {
			validationMessage("display.store.valid.yPointText");
		}

		// 매장 ID
		if (UtilsText.isBlank(getStoreIdText())) {
			validationMessage("display.store.valid.storeIdText");
		}

		// 찾아가는길
//		if (UtilsText.isBlank(getFindGoStrtText())) {
//			validationMessage("display.store.valid.findGoStrtText");
//		}

	}

}
