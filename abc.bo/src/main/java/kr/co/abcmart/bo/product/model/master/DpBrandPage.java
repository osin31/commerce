package kr.co.abcmart.bo.product.model.master;

import kr.co.abcmart.bo.product.model.master.base.BaseDpBrandPage;
import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.request.FileUpload;
import kr.co.abcmart.common.util.UtilsObject;
import kr.co.abcmart.common.validation.Validator;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.interfaces.util.UtilsText;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@ToString(callSuper = true)
public class DpBrandPage extends BaseDpBrandPage implements Validator {

	/** IBSheet 고유키 */
	private String seq;

	/** IBSheet 상태값 */
	private String status;

	/** IBSheet 전시 이미지 */
	private String imageView;

	/** 업로드 파일 */
	private FileUpload uploadFile;

	@Override
	public void validate() throws ValidatorException {
		// 프로모션 디바이스코드
		String deviceCode = this.getDeviceCode();
		if (UtilsObject.isEmpty(deviceCode)) {
			validationMessage("product.valid.brand.page.deviceCode");
		}
		String suffix = CommonCode.DEVICE_PC.equals(deviceCode) ? "pc" : "mo";
		// 전시여부
		if (UtilsObject.isEmpty(this.getDispYn())) {
			validationFieldMessage("product.valid.brand.page.dispYn." + suffix, "dispYn");
		}

		if ("Y".equals(this.getDispYn())) {
			// 전시시작일
			if (UtilsObject.isEmpty(this.getDispStartYmd())) {
				validationFieldMessage("product.valid.brand.page.dispStartYmd." + suffix, suffix + "-dispStartYmd");
			}
			// 전시종료일
			if (UtilsObject.isEmpty(this.getDispEndYmd())) {
				validationFieldMessage("product.valid.brand.page.dispEndYmd." + suffix, suffix + "-dispEndYmd");
			}
			// 이미지
			if (Const.CRUD_I.equals(this.getStatus()) && UtilsObject.isEmpty(this.getUploadFile())) {
				validationFieldMessage("product.valid.brand.page.uploadFile." + suffix, suffix + "-uploadFile");
			}
			// 링크연결 방법
			if (UtilsText.equals("U", this.getCnnctnType()) || UtilsText.equals("M", this.getCnnctnType())) {
				// URL입력이거나 이미지맵인 경우
				if (UtilsObject.isEmpty(this.getLinkInfo())) {
					validationFieldMessage("product.valid.brand.page.linkInfo." + suffix, suffix + "-linkInfo");
				}
			} else {
				// 연결안함인 경우
				this.setLinkTargetType("S");// 링크대상유형값 기본값 설정
			}
			// 전시일 일치 확인
			if (this.getDispEndYmd().before(this.getDispStartYmd())) {
				validationFieldMessage("product.valid.brand.page.dispYmdBefore." + suffix, suffix + "-dispStartYmd");
			}
		}
	}
}
