/**
 *
 */
package kr.co.abcmart.bo.cmm.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.co.abcmart.bo.cmm.vo.MessageVO;
import kr.co.abcmart.bo.member.model.master.MbMember;
import kr.co.abcmart.common.util.UtilsDate;
import lombok.extern.slf4j.Slf4j;


/**
 *
 * @Desc      :
 * @FileName  : MessageServiceTest.java
 * @Project   : abc.bo
 * @Date  	  : 2019. 4. 2.
 * @Author    : choi
 */
@SpringBootTest
@Slf4j
class MessageServiceTest {

	@Autowired
	MessageService messageService;

	@Test
	void test() throws Exception {
		try {
			MbMember member = new MbMember();
			member.setLoginId("login_id_111");
			member.setBirthYmd(UtilsDate.getSqlTimeStamp());
			MessageVO messageVO = new MessageVO();

			messageVO.setMessageTemplateModel(member);				// 템플릿으로 insert 될 때 프리마커로 변수가 세팅되는 값(선택)	// 업무마다 다름(member, order, mypage, system object,,,)
			messageVO.setSndrName("발송자명");						// 발송자명(필수)
			messageVO.setSendTelNoText("021234567");				// 발송전화번호(필수)
			messageVO.setRcvrName("최경호");						// 수신자(필수)
			messageVO.setRecvTelNoText("01050454628");				// 수신자 전화번호(필수)
			messageVO.setMesgContText("카카오톡 메세지 내용\n\r메세지 내용");// 내용(필수)

			messageVO.setMesgTitleText("제목");		// 제목(선택)
			messageVO.setSiteNo("10000");			// 사이트 코드(선택)[10000:통합몰[A-RT], 100001:OTS]
			messageVO.setSendTypeCode("10003");		// 메세지 타입코드(10000 : SMS,10001 : LMS,10002 : MMS,10003 : KKO)
			messageVO.setMesgId("M0123");		// 추후에 카카오로부터 부여 받아야함(선택)
			messageVO.setReal(true);				// 실시간 발송 여부(선택)
			messageVO.setAdminSendYn("N");			// 관리자 발송 여부(선택)
			messageVO.setOrderNo("1234567890123");
			messageVO.setOrderPrdtSeq("123");
			messageVO.setCnslScriptSeq("456");

			messageService.setSendMessageProcess(messageVO);
		} catch (Exception e) {
			System.out.println("error >>> {}" + e.getMessage());
			// TODO: handle exception
		}
	}

}
