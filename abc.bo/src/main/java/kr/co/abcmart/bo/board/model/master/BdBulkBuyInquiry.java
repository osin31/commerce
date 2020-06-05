package kr.co.abcmart.bo.board.model.master;

import kr.co.abcmart.bo.board.model.master.base.BaseBdBulkBuyInquiry;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.util.UtilsMasking;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class BdBulkBuyInquiry extends BaseBdBulkBuyInquiry {

	private String searchDateKey;

	private String fromDate;

	private String toDate;

	private String searchKey;

	private String searchValue;

	private String memberId;

	private String memberName;

	private String mngrDpName;

	private String memberTypeCode;

	private String loginId;

	private String adminName;

	private int pageCount;

	private String siteName;

	private int totalCnt;
	
	private String hdphnBackNoText;

	public String getMngrDpName() {
		return UtilsText.concat(UtilsMasking.loginId(this.memberId), Const.L_PAREN,
				UtilsMasking.userName(this.memberName), Const.R_PAREN);
	}

	public String getMngrDetailDpName() {
		if (UtilsText.equals(LoginManager.getUserDetails().getMemberInfoMgmtYn(), Const.BOOLEAN_FALSE)) {
			return UtilsText.concat(UtilsMasking.loginId(this.memberId), Const.L_PAREN,
					UtilsMasking.userName(this.memberName), Const.R_PAREN);
		} else {
			return UtilsText.concat(this.memberId, Const.L_PAREN, this.memberName, Const.R_PAREN);
		}
	}

}
