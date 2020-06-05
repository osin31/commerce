package kr.co.abcmart.bo.event.model.master;

import kr.co.abcmart.bo.event.model.master.base.BaseEvEventTargetGrade;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class EvEventTargetGrade extends BaseEvEventTargetGrade {

	private String memberTypeCodeName;

	private String mbshpGradeCodeName;

}
