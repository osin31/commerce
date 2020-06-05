package kr.co.abcmart.bo.member.model.master;

import kr.co.abcmart.bo.member.model.master.base.BaseMbMemberGiftCard;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
//public class MbMemberGiftCard extends BaseMbMemberGiftCard implements Validator {
public class MbMemberGiftCard extends BaseMbMemberGiftCard {

	private String fromDate;

	private String toDate;

	private String orderNo;

	private String giftCardOrderNo;

	private String pymntMeansCode;

//	@Override
//	public void validate() throws ValidatorException {
//		if (UtilsText.isBlank(getCardNoText())) {
//			validationMessage("보유카드 선택된 카드번호가 없습니다.", true);
//		}
//	}
}
