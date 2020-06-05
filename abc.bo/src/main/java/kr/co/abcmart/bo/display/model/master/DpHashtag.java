package kr.co.abcmart.bo.display.model.master;

import java.util.List;

import kr.co.abcmart.bo.display.model.master.base.BaseDpHashtag;
import kr.co.abcmart.common.util.UtilsDate;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.util.UtilsMasking;
import kr.co.abcmart.util.UtilsText;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@ToString(callSuper = true)
public class DpHashtag extends BaseDpHashtag {
	private static final long serialVersionUID = 291419155508278558L;

	// 수정자 명
	private String moderName;

	// 등록 상품 개수
	private String prdtCnt;

	// 사이트 이름
	private String siteName;

	// 사용 기간
	private String dispTerm;

	// 선택한 상품 코드 ','구분자로 구분됨
	private String prdtNoArr;

	private String[] prdtNoArr1;

	// 마스킹 관련
	private String adminId;

	public String getMaskingModerName() {
		return UtilsText.concat(UtilsMasking.loginId(this.adminId), Const.L_PAREN,
				UtilsMasking.userName(this.moderName), Const.R_PAREN);
	}

	public String getDispTerm() {
		if (this.getDispStartYmd() != null && this.getDispEndYmd() != null) {
			dispTerm = UtilsText.concat(UtilsDate.formatter(this.getDispStartYmd()), " ~ ",
					UtilsDate.formatter(this.getDispEndYmd()));
		}

		return dispTerm;
	}

	private List<DpHashtagProduct> hashTagProductList;
}
