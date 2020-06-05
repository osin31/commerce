package kr.co.abcmart.bo.order;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.co.abcmart.bo.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class ordderServiceTest {

	@Autowired
	OrderService orderService;

	@Test
	void testOrderStock() throws Exception {
		/*
		 * Map<String, Object> rtn1 = orderService.updateOrderProductStock("2000000203",
		 * "250", 1, "N", false); log.debug("-->rtn1 : {}", rtn1);
		 * 
		 * Map<String, Object> rtn2 = orderService.updateOrderProductStock("2000000203",
		 * "250", 1, "N", false); log.debug("--> rtn2 : {}", rtn2);
		 */
		// Map<String, Object> rtn3 = orderService.updateOrderProductStock("2000000286",
		// "001", 1, "N", false);

		Map<String, Object> rtn3 = orderService.updateProductStockAdjust("1000004417", "255", 1, "10000", false);

		log.debug("-------------------- Bo > rtn3 : {}", rtn3.get("stockGbnCode"));

	}

	/*
	 * void testOrderOrder() throws Exception { OcOrder orderPara = new OcOrder();
	 * orderPara.setOrderNo("201902130004");
	 * 
	 * orderService.getOrderDetailInfo(orderPara);
	 * 
	 * // log.debug("testGetAvailableCouponCount : {}", cnt);
	 * 
	 * }
	 */
}
