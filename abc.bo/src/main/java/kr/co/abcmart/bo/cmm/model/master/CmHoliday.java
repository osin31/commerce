package kr.co.abcmart.bo.cmm.model.master;

import kr.co.abcmart.bo.cmm.model.master.base.BaseCmHoliday;
import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.common.validation.Validator;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.util.UtilsMasking;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@SuppressWarnings("serial")
public class CmHoliday extends BaseCmHoliday implements Validator {
	private String loginId;			// 로그인ID
	private String adminName;		//관리자명
	private String year;			//년도(2019,2020)
	private int month;				// 월(1~12)
	private String insertYear;		// 등록년도
	private String standardHoliday;	// 시스템 휴일 코드
	private String commonHoliday;	// 공통 휴일 코드
	private String isVendor;		// 업체 권한 여부
	private String[] vndrNoArr;		// 업체번호 배열
	private String holidayDataArea;	// 업체 휴일 입력 데이터(20190101,20190102)
	private String role;			// 권한
	private String yyyyMm;			// 년월
	private String yyyyMmdd;			// 년월일
	private String authType;		// 권한타입

	// 관리자 정보 마스킹
	public String getAdminInfo() {
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

	@Override
	public void validate() throws ValidatorException {
		if (UtilsText.isBlank(getHolidayDataArea())) {
			validationMessage("cmm.valid.holiday.holidayDataArea");	// 휴일 데이터의 null 체크[20190101,20190102,,,]
		}
	}


}
