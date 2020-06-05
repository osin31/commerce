package kr.co.abcmart.bo.cmm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.cmm.model.master.CmMessageSendHistory;
import kr.co.abcmart.bo.cmm.model.master.CmMessageTemplate;
import kr.co.abcmart.bo.cmm.repository.master.CmMessageSendHistoryDao;
import kr.co.abcmart.bo.cmm.repository.master.CmMessageTemplateDao;
import kr.co.abcmart.bo.cmm.vo.MessageVO;
import kr.co.abcmart.bo.cmm.vo.MsgTemplateSearchVO;
import kr.co.abcmart.bo.member.model.master.MbMember;
import kr.co.abcmart.bo.member.service.MemberService;
import kr.co.abcmart.bo.noacl.vo.TextMsgVO;
import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.bo.system.service.CommonCodeService;
import kr.co.abcmart.bo.system.service.SiteService;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;

@Service
public class MsgTemplateService {

	@Autowired
	CmMessageTemplateDao cmMessageTemplateDao;

	@Autowired
	CmMessageSendHistoryDao cmMessageSendHistoryDao;

	@Autowired
	CommonCodeService commonCodeService;

	@Autowired
	SiteService siteService;

	@Autowired
	MemberService memberService;

	/**
	 * @Desc : 문자 템플릿 검색 Form에 필요한 공통 코드 정보를 조회한다.
	 * @Method Name : getMsgTemplateSearchForm
	 * @Date : 2019. 3. 11.
	 * @Author : kiowa
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getTextMsgTemplateSearchForm() throws Exception {
		Map<String, Object> rtnVal = new HashMap<String, Object>();
		String codeFiled = CommonCode.MESG_TYPE_CODE;

		rtnVal.put("mesgTypeCodeList", commonCodeService.getCodeNoName(codeFiled));
		rtnVal.put("siteList", siteService.getSiteList());

		return rtnVal;
	}

	/**
	 * @Desc : 검색 조건에 맞는 문자 템플릿 정보를 조회한다.
	 * @Method Name : getMsgTemplateReadList
	 * @Date : 2019. 3. 13.
	 * @Author : kiowa
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<CmMessageTemplate> getTextMsgTemplateReadList(Pageable<MsgTemplateSearchVO, CmMessageTemplate> pageable)
			throws Exception {

		int totalCount = cmMessageTemplateDao.selectCmMessageTemplateListCount(pageable);

		pageable.setTotalCount(totalCount);

		if (totalCount > 0) {
			pageable.setContent(cmMessageTemplateDao.selectCmMessageTemplateList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 검색 조건에 맞는 문자 템플릿 정보를 조회하여 엑셀다운.
	 * @Method Name : getMsgTemplateReadListExcelDown
	 * @Date : 2019. 4. 11.
	 * @Author : 이재렬
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<CmMessageTemplate> getTextMsgTemplateReadListExcelDown(
			Pageable<MsgTemplateSearchVO, CmMessageTemplate> pageable) throws Exception {

		int totalCount = cmMessageTemplateDao.selectCmMessageTemplateListCount(pageable);

		pageable.setTotalCount(totalCount);

		if (totalCount > 0) {
			pageable.setContent(cmMessageTemplateDao.selectCmMessageTemplateListExcelDown(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 문자 템플릿 상세 정보를 조회한다.
	 * @Method Name : getTextMsgTemplateReadDetail
	 * @Date : 2019. 3. 13.
	 * @Author : kiowa
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getTextMsgTemplateReadDetail(CmMessageTemplate params) throws Exception {
		Map<String, Object> rtnVal = new HashMap<String, Object>(); // 결과 정보
		String codeFiled = CommonCode.MESG_TYPE_CODE;

		rtnVal.put("mesgTypeCodeList", commonCodeService.getCodeNoName(codeFiled));
		rtnVal.put("siteList", siteService.getSiteList());

		if (null != params.getMesgTmplSeq() && params.getMesgTmplSeq() > 0) {
			rtnVal.put("cmMessageTemplate", cmMessageTemplateDao.selectCmMessageTemplate(params));
		}

		return rtnVal;
	}

	/**
	 * @Desc : 문자 템플릿 정보를 저장한다.
	 * @Method Name : setTextMsgTemplate
	 * @Date : 2019. 3. 14.
	 * @Author : kiowa
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int setTextMsgTemplateCreate(CmMessageTemplate params) throws Exception {
		return insertCmMsgTemplate(params);
	}

	/**
	 * @Desc : 문자템플릿 정보를 수정한다.
	 * @Method Name : setTextMsgTemplateModify
	 * @Date : 2019. 3. 14.
	 * @Author : kiowa
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int setTextMsgTemplateModify(CmMessageTemplate params) throws Exception {
		return updateCmMsgTemplate(params);
	}

	/**
	 * @Desc : 문자템필릇 정보를 삭제한다.
	 * @Method Name : setTextMsgTemplateDelete
	 * @Date : 2019. 3. 14.
	 * @Author : kiowa
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int setTextMsgTemplateDelete(CmMessageTemplate params) throws Exception {
		return deleteCmMsgTemplate(params);
	}

	/**
	 * @Desc : CM_MSG_TEMPLATE 테이블에 데이터를 저장한다.
	 * @Method Name : insertCmMsgTemplage
	 * @Date : 2019. 3. 14.
	 * @Author : kiowa
	 * @param params
	 * @return
	 * @throws Exception
	 */
	private int insertCmMsgTemplate(CmMessageTemplate params) throws Exception {
		return cmMessageTemplateDao.insertCmMessageTemplate(params);
	}

	/**
	 * @Desc : CM_MSG_TEMPLATE 테이블에 데이터를 수정한다.
	 * @Method Name : updateCmMsgTemplate
	 * @Date : 2019. 3. 14.
	 * @Author : kiowa
	 * @param params
	 * @return
	 * @throws Exception
	 */
	private int updateCmMsgTemplate(CmMessageTemplate params) throws Exception {
		return cmMessageTemplateDao.updateCmMessageTemplate(params);
	}

	/**
	 * @Desc : CM_MSG_TEMPLATE 테이블에 데이터를 삭제한다.
	 * @Method Name : deleteCmMsgTemplate
	 * @Date : 2019. 3. 14.
	 * @Author : kiowa
	 * @param params
	 * @return
	 * @throws Exception
	 */
	private int deleteCmMsgTemplate(CmMessageTemplate params) throws Exception {
		return cmMessageTemplateDao.delete(params);
	}

	/**
	 * @Desc :
	 * @Method Name : getTextMsgSendForm
	 * @Date : 2019. 3. 20.
	 * @Author : kiowa
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getTextMsgSendForm(TextMsgVO params) throws Exception {
		Map<String, Object> rtnVal = new HashMap<String, Object>();
		String codeFields[] = { CommonCode.SEND_TYPE_CODE, CommonCode.MESG_TYPE_CODE };

		Map<String, List<SyCodeDetail>> codeGroupMap = commonCodeService.getAllCodeListByGroup(codeFields);

		rtnVal.put("sendTypeCodeList", codeGroupMap.get(CommonCode.SEND_TYPE_CODE).stream()
				.filter(x -> UtilsText.equals(x.getAddInfo1(), "Y")).collect(Collectors.toList()));
		rtnVal.put("mesgTypeCodeList", codeGroupMap.get(CommonCode.MESG_TYPE_CODE).stream()
				.filter(x -> UtilsText.equals(x.getUseYn(), "Y")).collect(Collectors.toList()));
		rtnVal.put("siteList", siteService.getSiteList());

		if (!UtilsText.isAllBlank(params.getMemberNo()) && !Const.NON_MEMBER_NO.equals(params.getMemberNo())
				&& !UtilsText.equals(Const.ADMIN_SEND_MSG_TO_MEMBER, params.getMemberNo())) {
			MbMember member = memberService.getMember(params.getMemberNo());
			if (null != member) {
				params.setRecvTelNoText(member.getHdphnNoText());
				params.setRcvrName(member.getMemberName());
			}
		}

		rtnVal.put("textMsgVo", params);

		return rtnVal;
	}

	/**
	 * @Desc : 선택한 조건에 맞는 문자 템플릿 정보를 조회한다.
	 * @Method Name : getTextMesgTemplateList
	 * @Date : 2019. 3. 20.
	 * @Author : kiowa
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<CmMessageTemplate> getTextMesgTemplateList(MsgTemplateSearchVO params) throws Exception {
		return cmMessageTemplateDao.selectTextMesgSendPopTemplateList(params);
	}

	public CmMessageTemplate getMessageTemplateByMesgId(String mesgId) throws Exception {
		return cmMessageTemplateDao.selectMessageTemplateByMesgId(mesgId);
	}

	public int setMessageSendHistory(CmMessageSendHistory params) throws Exception {
		String rgsterNo = LoginManager.getUserDetails().getAdminNo();
		rgsterNo = (rgsterNo == null ? Const.SYSTEM_ADMIN_NO : rgsterNo);

		params.setRgsterNo(rgsterNo);

		return insertCmMessageSendHistory(params);
	}

	/**
	 * @Desc : CM_MESSAGE_SEND_HISTORY에 데이터를 저장한다.
	 * @Method Name : insertCmMessageSendHistory
	 * @Date : 2019. 3. 21.
	 * @Author : kiowa
	 * @param params
	 * @return
	 * @throws Exception
	 */
	private int insertCmMessageSendHistory(CmMessageSendHistory params) throws Exception {
		return cmMessageSendHistoryDao.insertMessageSendHistory(params);
	}

	/**
	 * @Desc : SMS 즉시발송 테이블에 insert 한다.
	 * @Method Name : setSendRealTimeSms
	 * @Date : 2019. 4. 23.
	 * @Author : choi
	 * @param messageVO
	 * @return
	 * @throws Exception
	 */
	public int setSendRealTimeSmsNoTrx(MessageVO messageVO) throws Exception {
		return cmMessageSendHistoryDao.insertSendRealTimeSms(messageVO);
	}

	/**
	 * @Desc : LMS 즉시발송 테이블에 insert 한다.
	 * @Method Name : setSendRealTimeSms
	 * @Date : 2019. 4. 23.
	 * @Author : choi
	 * @param messageVO
	 * @return
	 * @throws Exception
	 */
	public int setSendRealTimeLmsNoTrx(MessageVO messageVO) throws Exception {
		return cmMessageSendHistoryDao.insertSendRealTimeLms(messageVO);
	}

	/**
	 * @Desc : KKO 즉시발송 테이블에 insert 한다.
	 * @Method Name : setSendRealTimeKko
	 * @Date : 2019. 4. 23.
	 * @Author : choi
	 * @param messageVO
	 * @return
	 * @throws Exception
	 */
	public int setSendRealTimeKkoNoTrx(MessageVO messageVO) throws Exception {
		return cmMessageSendHistoryDao.insertSendRealTimeKko(messageVO);
	}

	/**
	 * @Desc :
	 * @Method Name : getMessageTemplate
	 * @Date : 2019. 7. 4.
	 * @Author : 유성민
	 * @param messageVO
	 * @return
	 */
	public CmMessageTemplate getMessageTemplate(MessageVO messageVO) throws Exception {
		return cmMessageTemplateDao.selectMessageTemplate(messageVO);
	}

	/**
	 * @Desc : 발송코드 중복확인을 한다.
	 * @Method Name : getMesgIdCount
	 * @Date : 2019. 7. 11.
	 * @Author : 고웅환
	 * @param emailId
	 * @return
	 * @throws Exception
	 */
	public int getMesgIdCount(String mesgId) throws Exception {
		return cmMessageTemplateDao.selectCmMesgIdCount(mesgId);
	}
}
