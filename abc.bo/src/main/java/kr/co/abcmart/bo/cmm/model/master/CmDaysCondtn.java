package kr.co.abcmart.bo.cmm.model.master;

import kr.co.abcmart.bo.cmm.model.master.base.BaseCmDaysCondtn;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class CmDaysCondtn extends BaseCmDaysCondtn {

	private String rgsterName;
	
	private String moderName;
	
	private String status;

}
