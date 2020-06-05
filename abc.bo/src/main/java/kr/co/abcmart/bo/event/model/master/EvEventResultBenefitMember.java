package kr.co.abcmart.bo.event.model.master;

import kr.co.abcmart.bo.event.model.master.base.BaseEvEventResultBenefitMember;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class EvEventResultBenefitMember extends BaseEvEventResultBenefitMember
		implements Comparable<EvEventResultBenefitMember> {

	private String sortSeq;

	private String benefitName;

	private java.lang.Short eventRsltBenefitSeq;

	private String loginId;

	private int totalRowNum;

	@Override
	public int compareTo(EvEventResultBenefitMember evEventResultBenefitMember) {
		return this.sortSeq.compareTo(evEventResultBenefitMember.getSortSeq());
	}

}
