package kr.co.abcmart.bo.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.abcmart.bo.system.service.EmployeeService;
import kr.co.abcmart.common.controller.BaseController;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("system/employee")
public class EmployeeController extends BaseController{
	
	private EmployeeService employeeService;
	
}
