package kr.co.abcmart.bo.display.model.master;

import java.text.SimpleDateFormat;
import java.util.Date;

import kr.co.abcmart.bo.display.model.master.base.BaseCmCatalogEvent;
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
public class CmCatalogEvent extends BaseCmCatalogEvent implements Validator {

	/* 이미지 파일 */
	private FileUpload imageFile;

	/* 오프라인 매장 번호 */
	private String[] storeNoArr;

	/* 이벤트 기간 */
	private String eventStartD;
	private String eventStartH;
	private String eventStartM;
	private String eventEndD;
	private String eventEndH;
	private String eventEndM;

	private String eventDtm;

	/* 관리자 정보 */
	private String rgsterName;
	private String rgsterId;
	private String rgsterInfo;

	private String moderName;
	private String moderId;
	private String moderInfo;

	/* 날짜 검색 */
	private String searchDateType;
	private java.sql.Timestamp searchDateS;
	private java.sql.Timestamp searchDateE;

	/* 키워드 검색 */
	private String searchType;
	private String searchWord;

	/* 진행상태 */
	private String progress;
	private String progressYn;

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

	public String getEventDtm() {

		if (getEventStartDtm() == null || getEventEndDtm() == null)
			return null;

		String startDtm = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(this.getEventStartDtm());
		String endDtm = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(this.getEventEndDtm());

		String result = startDtm + "~" + endDtm;

		return result;
	}

	public String getProgress() {

		Date today = new Date();

		if (getEventStartDtm() == null || getEventEndDtm() == null)
			return null;

		if (getEventStartDtm().compareTo(today) < 0 && getEventEndDtm().compareTo(today) > 0) {
			return "진행중";
		} else {
			return "진행안함";
		}
	}

	@Override
	public void validate() throws ValidatorException {

		// 제목
		if (UtilsText.isBlank(getEventName())) {
			validationMessage("display.connect.event.valid.title");
		}

		// 적용 매장
		if (UtilsText.equals(getStoreCommonYn(), "N")) {
			if (getStoreNoArr() == null) {
				validationMessage("display.connect.event.valid.store");
			}
		}

		// 이벤트 기간
		if (UtilsText.isBlank(getEventStartD())) {
			log.debug("startDate" + getEventStartD());
			validationMessage("display.connect.event.valid.startDate");
		}
		if (UtilsText.isBlank(getEventEndD())) {
			log.debug("endDate" + getEventEndD());
			validationMessage("display.connect.event.valid.endDate");
		}

		// 전시목록 이미지
		if (UtilsText.isBlank(getImageName())) {
			validationMessage("display.connect.event.valid.image");
		} else {
			if (this.getImageFile() != null) {
				if (this.getImageFile().isFileItem() && !this.getImageFile().isAllowExtAndMimeType("jpg", "gif")) {
					validationMessage("display.connect.event.valid.imageType");
				}
			}
		}

		// 내용
		if (UtilsText.isBlank(getEventInfo())) {
			validationMessage("display.connect.event.valid.eventInfo");
		}

	}

}
