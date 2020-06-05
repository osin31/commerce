/**
 * 
 */
package kr.co.abcmart.bo.cmm.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.co.abcmart.bo.cmm.vo.MailTemplateVO;
import kr.co.abcmart.bo.order.model.master.OcOrder;
import kr.co.abcmart.constant.MailCode;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc :
 * @FileName : MailServiceTest.java
 * @Project : abc.bo
 * @Date : 2019. 3. 25.
 * @Author : Kimyounghyun
 */
@SpringBootTest
@Slf4j
class MailServiceTest {

	@Autowired
	MailService mailService;

	@Test
	void testSendMail() throws Exception {

		OcOrder order = new OcOrder();
		order.setOrderNo("11111111");
		order.setPymntAmt(10000);

		MailTemplateVO mailTemplateVo = new MailTemplateVO();
		mailTemplateVo.setMailTemplateId(MailCode.ORDER_COMPLETE);
		mailTemplateVo.setMailTemplateModel(order);
		mailTemplateVo.setReceiverMemberNo("MB00000001");
		mailTemplateVo.setReceiverEmail("kyh@3top.co.kr");
		mailTemplateVo.setReceiverName("홍길동");
//		mailTemplateVo.setRealTime(false);
		mailTemplateVo.setRealTime(true);

		mailService.sendMail(mailTemplateVo);
	}
}
