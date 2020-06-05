package kr.co.abcmart.bo.event.model.master;

import kr.co.abcmart.bo.event.model.master.base.BaseEvEventRouletteBenefit;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class EvEventRouletteBenefit extends BaseEvEventRouletteBenefit {
	private String cpnName;

	private String benefitGbnCodeName;

}
