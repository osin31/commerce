package kr.co.abcmart.bo.order;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.co.abcmart.interfaces.module.payment.PaymentEntrance;
import kr.co.abcmart.interfaces.module.payment.base.PaymentResult;
import kr.co.abcmart.interfaces.module.payment.base.model.PaymentInfo;
import kr.co.abcmart.interfaces.module.payment.config.KcpPaymentConfig;
import kr.co.abcmart.interfaces.module.payment.kcp.model.KcpPaymentApproval;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 결제모듈 호출 test
 * @FileName : PaymentTest.java
 * @Project : abc.bo
 * @Date : 2019. 1. 31.
 * @Author : KTH
 */
@Slf4j
@SpringBootTest
public class PaymentTest {

	@Autowired
	PaymentEntrance payment;

	@Autowired
	KcpPaymentConfig kcpPaymentConfig;

	@Test
	public void paymentTest() throws Exception {
		log.debug("########################## paymentTest start");

		log.debug("################# kcp sitecode ==> {}", kcpPaymentConfig.getSiteCode());
		log.debug("################# kcp sitekey ==> {}", kcpPaymentConfig.getSiteKey());

		KcpPaymentApproval kcpPaymentApproval = new KcpPaymentApproval();
		kcpPaymentApproval.setGoodMny(323000);

		PaymentResult paymentResult = payment.approval(new PaymentInfo("KCP", kcpPaymentApproval));

		log.debug("################# 성공여부 ==> {}", paymentResult.getSuccessYn());

		payment.cancel(new PaymentInfo("KCP", null));
		payment.approval(new PaymentInfo("KAKAO", null));
		payment.cancel(new PaymentInfo("KAKAO", null));
		// service.approval(new Payment("NAVER", null));

		log.debug("########################## paymentTest end");
	}

}
