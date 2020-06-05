package kr.co.abcmart.bo.event.model.master;

import kr.co.abcmart.bo.event.model.master.base.BaseEvEventTargetCoupon;
import kr.co.abcmart.common.util.UtilsText;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class EvEventTargetCoupon extends BaseEvEventTargetCoupon {

	private String cpnNo;

	private String cpnName;

	private String dscntType;

	private String dscntValue;

	private String useYn;

	private String issueStartDtm;

	private String issueEndDtm;

	private String cpnTypeCodeName;

	private String deviceCodeName;

	private String totalIssueLimitYn;

	private String totalIssueLimitCount;

	private String per1psnIssueLimitYn;

	private String per1psnMaxIssueCount;

	public String getDscntText() {
		String dscntext = "";
		if (UtilsText.isNotBlank(this.getDscntType()) && this.getDscntValue() != null) {
			if (UtilsText.equals(this.getDscntType(), "R")) {
				dscntext = UtilsText.concat(String.valueOf(this.getDscntValue()), "%");
			} else if (UtilsText.equals(this.getDscntType(), "A")) {
				dscntext = UtilsText.concat(String.valueOf(this.getDscntValue()), "원");
			} else {
				dscntext = String.valueOf(this.getDscntValue());
			}
		}
		return dscntext;
	}

}
