package kr.co.abcmart.bo.product.model.master;

import kr.co.abcmart.bo.product.model.master.base.BaseCmProductIcon;
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
public class CmProductIcon extends BaseCmProductIcon implements Validator {

	private FileUpload imageFile;

	private String imageName;

	private String prdtCount;

	private int pageCount;

	private String status;

	private String siteNo;

	private String siteName;

	/* 관리자 정보 */
	private String rgsterName;
	private String rgsterId;
	private String rgsterInfo;

	private String moderName;
	private String moderId;
	private String moderInfo;

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

	public String getImageName() {

		if (getIconUrl() != null) {
			String[] arr = getIconUrl().split("/");

			int len = arr.length;

			return arr[len - 1];
		} else {
			return "";
		}
	}

	@Override
	public void validate() throws ValidatorException {

		// 아이콘명
		if (UtilsText.isBlank(getDispIconName())) {
			validationMessage("product.valid.dispIconName");
		}

		// 아이콘코드
		if (UtilsText.isBlank(getInsdMgmtInfoText())) {
			validationMessage("product.valid.insdMgmtInfoText");
		}

	}

}
