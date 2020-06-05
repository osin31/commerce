package kr.co.abcmart.bo.order.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.co.abcmart.bo.order.service.OrderTestService;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.request.Parameter;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc :
 * @FileName : OrderController.java
 * @Project : abc.bo
 * @Date : 2019. 2. 11.
 * @Author : ljyoung@3top.co.kr
 */
@Slf4j
@Controller
@RequestMapping("orderTest")
public class OrderTestController extends BaseController {

	@Autowired
	private OrderTestService service;

	/*************************************************************************************************
	 * jeon start
	 *************************************************************************************************/

	/**
	 * 
	 * @Desc : Channel 검색
	 * @Method Name : changeHistoryFields
	 * @Date : 2019. 2. 22.
	 * @Author : flychani@3top.co.kr
	 * @param parameter
	 * @throws Exception
	 */
	@GetMapping("/order-test")
	public ModelAndView orderTest(Parameter<?> parameter) throws Exception {

		return forward("/order/order-test");
	}

	@RequestMapping("/read-authority")
	@ResponseBody
	public void readList(Parameter<?> parameter) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = service.authority();
		writeJson(parameter, resultMap);
	}

	@GetMapping("/kakao-approve")
	public void kakaoApprove(Parameter<?> parameter) throws Exception {
		System.out.println("parameter" + parameter);
		System.out.println("parameter pgToken : " + parameter.getString("pg_token"));
		Map<String, Object> resultMap = new HashMap<String, Object>();

		resultMap = service.kakaoApprove(parameter);
		writeJson(parameter, resultMap);
	}

	@RequestMapping("/kakao-cancel")
	@ResponseBody
	public void kakaoCancel(Parameter<?> parameter) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = service.kakaoCancel(parameter);
		writeJson(parameter, resultMap);
	}

	//
	@RequestMapping("/kakao-order")
	@ResponseBody
	public void kakaoOrder(Parameter<?> parameter) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = service.kakaoOrder(parameter);
		writeJson(parameter, resultMap);
	}

	@RequestMapping("/kakao-orders")
	@ResponseBody
	public void kakaoOrders(Parameter<?> parameter) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = service.kakaoOrders(parameter);
		writeJson(parameter, resultMap);
	}

}