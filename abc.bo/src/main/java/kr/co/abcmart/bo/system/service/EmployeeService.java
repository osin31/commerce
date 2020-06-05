package kr.co.abcmart.bo.system.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.system.model.master.SyEmployee;
import kr.co.abcmart.bo.system.model.master.SyEmployeeLimit;
import kr.co.abcmart.bo.system.repository.master.SyEmployeeDao;
import kr.co.abcmart.bo.system.repository.master.SyEmployeeLimitDao;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private SyEmployeeDao syEmployeeDao;
	
	@Autowired
	private SyEmployeeLimitDao syEmployeeLimitDao;
	
	/**
	 * @Desc      	: 임직원 조회
	 * @Method Name : getEmployee
	 * @Date  	  	: 2019. 2. 21.
	 * @Author    	: 이동엽
	 * @param syEmployee
	 * @return
	 * @throws Exception
	 */
	public SyEmployee getEmployee(SyEmployee syEmployee) throws Exception {
		return syEmployeeDao.selectEmployee(syEmployee);
	}
	
	/**
	 * @Desc      	: 임직원 한도를 조회
	 * @Method Name : getEmployeeLimit
	 * @Date  	  	: 2019. 2. 25.
	 * @Author    	: 이동엽
	 * @param syEmployeeLimit
	 * @return
	 * @throws Exception
	 */
	public SyEmployeeLimit getEmployeeLimit(SyEmployeeLimit syEmployeeLimit) throws Exception {
		return syEmployeeLimitDao.selectEmployeeLimit(syEmployeeLimit);
	}
	
	/**
	 * @Desc      	: 임직원 정보를 수정
	 * @Method Name : updateEmployee
	 * @Date  	  	: 2019. 2. 25.
	 * @Author    	: 이동엽
	 * @param syEmployee
	 * @throws Exception
	 */
	public void updateEmployee(SyEmployee syEmployee) throws Exception {
		syEmployeeDao.updateEmployee(syEmployee);
	}
	
	
	/**
	 * @Desc      	: 임직원 인증을 초기화
	 * @Method Name : updateCrtfcReset
	 * @Date  	  	: 2019. 2. 25.
	 * @Author    	: 이동엽
	 * @param syEmployee
	 * @throws Exception
	 */
	public Map<String, Object> updateCrtfcReset(SyEmployee syEmployee) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		// 세션정보
		UserDetails user = LoginManager.getUserDetails();
		
		syEmployee.setCrtfcFailYn(Const.BOOLEAN_TRUE);
		syEmployee.setCrtfcFailCount((short) 0);
		syEmployee.setCrtfcFailChngYn(Const.BOOLEAN_TRUE);
		syEmployee.setCertFailAdminNo(user.getAdminNo());
		syEmployee.setModerNo(user.getAdminNo());
		
		employeeService.updateEmployee(syEmployee);
		result.put("result", Const.BOOLEAN_TRUE);
		return result;
		
	}
	
}
