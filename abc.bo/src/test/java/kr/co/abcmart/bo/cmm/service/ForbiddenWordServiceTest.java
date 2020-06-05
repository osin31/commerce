package kr.co.abcmart.bo.cmm.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class ForbiddenWordServiceTest {

	@Autowired
	ForbiddenWordService service;

	@Test
	public void testHasForbiddenPwdWithData() throws Exception {
		// 비밀번호 금지어 중에 aaaa 존재한다고 가정.
//		assertTrue(service.hasForbiddenPwd("12aaaa34"));
//		assertEquals("동해물과 *** 백두산이 마르고 닮도록...", service.censorWords("동해물과 강아지 백두산이 마르고 닮도록..."));
//		assertTrue(service.hasForbiddenWord("영철이가 강아지를 끌고 산으로 갔다"));

		Pair<Boolean, String> pair = service.hasForbiddenPwdWithData("abc1234erq");
		log.debug("first : {}, second : {}", pair.getFirst(), pair.getSecond());
	}

}
