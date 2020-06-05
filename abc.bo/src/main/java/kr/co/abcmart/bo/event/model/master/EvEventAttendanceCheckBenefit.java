package kr.co.abcmart.bo.event.model.master;

import kr.co.abcmart.bo.event.model.master.base.BaseEvEventAttendanceCheckBenefit;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class EvEventAttendanceCheckBenefit extends BaseEvEventAttendanceCheckBenefit {

	private String cpnName;

}
