package kr.co.abcmart.bo.event.model.master;

import java.util.List;

import kr.co.abcmart.bo.event.model.master.base.BaseEvEventResultBenefit;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class EvEventResultBenefit extends BaseEvEventResultBenefit {

	private String winCount;

	List<EvEventResultBenefitMember> benefitMemberList;
}
