package kr.co.abcmart.bo.system.model.master;

import kr.co.abcmart.bo.system.model.master.base.BaseSySiteChnnl;
import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.request.FileUpload;
import kr.co.abcmart.common.validation.Validator;
import kr.co.abcmart.util.UtilsText;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class SySiteChnnl extends BaseSySiteChnnl implements Validator {

	private String status;
	private SyAdmin rgster;
	private SyAdmin moder;
	private FileUpload pcLogoImg;
	private String pcLogoImgUrl;
	private String pcLogoImgName;
	private FileUpload moLogoImg;
	private String moLogoImgUrl;
	private String moLogoImgName;
	private FileUpload gnbLogoImg;
	private String gnbLogoImgUrl;
	private String gnbLogoImgName;

	public String getRgsterDisplayName() {
		if (getRgster() != null) {
			return rgster.getAdminInfo();
		} else {
			return "";
		}
	}

	public String getModerDisplayName() {
		if (getModer() != null) {
			return moder.getAdminInfo();
		} else {
			return "";
		}
	}

	@Override
	public void validate() throws ValidatorException {
		if (UtilsText.isBlank(getChnnlName())) {
			validationMessage("채널명이 존재하지 않습니다.", true);
		}

		if (UtilsText.isBlank(getUseYn())) {
			validationMessage("사용여부가 존재하지 않습니다.", true);
		}
	}
}
