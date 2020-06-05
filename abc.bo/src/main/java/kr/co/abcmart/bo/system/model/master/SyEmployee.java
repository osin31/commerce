package kr.co.abcmart.bo.system.model.master;

import kr.co.abcmart.bo.system.model.master.base.BaseSyEmployee;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class SyEmployee extends BaseSyEmployee {
	
	private String crtfcFailChngYn;
	private SyEmployeeLimit syEmployeeLimit;
	
}
