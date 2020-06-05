package kr.co.abcmart.kiowa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.co.abcmart.bo.event.model.master.EvEventPublicationNumber;
import kr.co.abcmart.bo.event.repository.master.EvEventPublicationNumberDao;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class kiowaTest {

	@Autowired
	private EvEventPublicationNumberDao evEventDao;

	@Test
	public void kiowaTest() throws Exception {

		String name = "100000";

		// int hash = name.hashCode();

		String cpnId = null;

		for (int i = 0; i < 10000; i++) {

			Long numberCount = evEventDao.selectEvEventPublicationNumberSeq(0);
			String hashNum = String.format("%011d", numberCount);
			int hash = hashNum.hashCode();
//			String hashStr = String.valueOf(hash);
//			int last2Num = Integer.parseInt(hashStr.substring(hashStr.length() - 2));
//			int cpnIdInt = org.apache.commons.lang3.StringUtils.isNumeric(cpnId) ? Integer.parseInt(cpnId) : 0;

			long randomNumberDecimalNumber = (hash * 20171122) * 7 + 201711221305L;
//			long randomNumberDecimalNumber = (hash * 1L + last2Num * last2Num) * 7 + 400000000000L + startTime;
			String changedCouponNumber = "TC"
					+ org.apache.commons.lang3.StringUtils.upperCase(Long.toString(randomNumberDecimalNumber, 36));
//AC56FGQCG8
//			log.debug("hash : {}", hash);
//			log.debug("hashStr : {}", hashStr);
//			log.debug("last2Num : {}", last2Num);
//			log.debug("cpnIdInt : {}", cpnIdInt);

//			log.debug("randomNumberDecimalNumber : {}", randomNumberDecimalNumber);
			log.debug("changedCouponNumber : {}", changedCouponNumber);

			EvEventPublicationNumber evEventPublicationNumber = new EvEventPublicationNumber();
			evEventPublicationNumber.setEventNo("2000000176");
			evEventPublicationNumber.setRgstYn("N");
			evEventPublicationNumber.setEventPblicteNo(changedCouponNumber);
			evEventDao.insert(evEventPublicationNumber);
		}

		/*
		 * MsgTemplatetSearchVO vo = new MsgTemplatetSearchVO();
		 *
		 * vo.setFromDate("2019.03.14");
		 *
		 * log.debug("from Date : {}", vo.getFromDtm());
		 *
		 * vo.setToDate("2019.03.13");
		 *
		 * log.debug("to Date : {}", vo.getToDtm());
		 *
		 * vo.isValidation();
		 *
		 * log.debug("from Time : {}, to Time {}", vo.getFromDtm().getTime(),
		 * vo.getToDtm().getTime());
		 *
		 * log.debug("from Date : {}, to Date {}", vo.getFromDtm().getNanos(),
		 * vo.getToDtm().getNanos());
		 *
		 * log.debug("to Date : {}", vo.getToDtm());
		 */

//		String prdtNo = "1234567890";
//
//		StringBuffer sb = new StringBuffer();
//
//		for (int i = prdtNo.length(); i > 0; i = i - 2) {
//			log.error("i =========================] {}", i);
//			sb.append(UtilsText.concat(prdtNo.substring(i - 2, i), File.separator));
//		}
//
//		log.error("SB ===========================] {}", sb.toString());

	}

}
