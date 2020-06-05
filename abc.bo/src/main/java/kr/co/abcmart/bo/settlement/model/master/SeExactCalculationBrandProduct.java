package kr.co.abcmart.bo.settlement.model.master;

import kr.co.abcmart.bo.settlement.model.master.base.BaseSeExactCalculationBrandProduct;
import kr.co.abcmart.common.util.UtilsMasking;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class SeExactCalculationBrandProduct extends BaseSeExactCalculationBrandProduct {

	// 년월
	private String excclcYm;
	// 주문상태 네임
	private String salesCnclGbnTypeName;
	// 브랜드 네임
	private String vndrName;
	// 배송지연여부
	private String delayDayYn;
	// 쿠폰 네임
	private String cpnName;
	// ART분담액
	private int abcAmt;
	// 플러스 쿠폰 네임
	private String plusCpnName;
	// 플러스 ART분담액
	private String plusAbcAmt;
	// 정산확정여부
	private String excclcDcsnYn;
	// 정산확정일시
	private String excclcDtm;

	// 처리자 아이디
	private String moderId;

	// 처리자 이름
	private String moderName;

	// 최종 수정일시
	private String finalModDtm;

	// 배송비 탭 에 배송비
	private int orderAmt;

	// 공급가
	private int supplyAmt;
	// 부가세
	private int vatAmt;
	// 업체 공급가
	private int vndrSupplyAmt;
	// 업체 부가세
	private int vndrVatAmt;
	// 실 결제금액
	private int actPymnt;
	// 쿠폰할인액 String 임시 변수
	private String plusCpnDscntAmtTemp;
	private String supplyAmtTemp;
	private String vatAmtTemp;
	private String vndrCmsnRateTemp;
	private String vndrSupplyAmtTemp;
	private String vndrVatAmtTemp;
	private String buyPointSaveRateTemp;
	private String penltyLevyRateTemp;
	private String penltyAmtTemp;
	private String delayDayCountTemp;

	// 처리자 마스킹 GRID 용
	public String getModerName() {
		String moderName = "";
		if (this.moderId != null && this.moderName != null) {
			if (LoginManager.isPersonalInfoManager()) {
				moderName = UtilsText.concat(this.moderId, Const.L_PAREN, this.moderName, Const.R_PAREN);
			} else {
				moderName = UtilsText.concat(UtilsMasking.loginId(this.moderId), Const.L_PAREN,
						UtilsMasking.userName(this.moderName), Const.R_PAREN);
			}
		}

		return moderName;
	}

	// 처리자 엑셀용
	public String getModerNo() {
		String moderNo = "";
		if (this.moderId != null && this.moderName != null) {
			moderNo = UtilsText.concat(this.moderId, Const.L_PAREN, this.moderName, Const.R_PAREN);
		} else {
			moderNo = "";
		}

		return moderNo;
	}
}
