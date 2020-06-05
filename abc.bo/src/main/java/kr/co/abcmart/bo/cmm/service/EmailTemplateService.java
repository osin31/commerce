package kr.co.abcmart.bo.cmm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.cmm.model.master.CmEmailTemplate;
import kr.co.abcmart.bo.cmm.repository.master.CmEmailTemplateDao;
import kr.co.abcmart.bo.cmm.vo.EmailTemplateSearchVO;
import kr.co.abcmart.bo.member.model.master.MbMember;
import kr.co.abcmart.bo.member.service.MemberService;
import kr.co.abcmart.bo.noacl.vo.CommonEmailPopupVO;
import kr.co.abcmart.bo.system.service.CommonCodeService;
import kr.co.abcmart.bo.system.service.SiteService;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.util.UtilsDate;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;

@Service
public class EmailTemplateService {

	@Autowired
	CmEmailTemplateDao cmEmailTemplateDao;

	@Autowired
	CommonCodeService commonCodeService;

	@Autowired
	SiteService siteService;

	@Autowired
	MemberService memberService;

	/**
	 * @Desc : 이메일 템플릿 검색 Form에 필요항 공통코드 정보를 조회한다.
	 * @Method Name : getEmailTemplateSearchForm
	 * @Date : 2019. 3. 14.
	 * @Author : kiowa
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getEmailTemplateSearchForm() throws Exception {
		Map<String, Object> rtnVal = new HashMap<String, Object>();
		String codeFiled = CommonCode.EMAIL_TYPE_CODE;

		rtnVal.put("emailTypeCodeList", commonCodeService.getCodeNoName(codeFiled));
		rtnVal.put("siteList", siteService.getSiteList());

		return rtnVal;
	}

	/**
	 * @Desc : 검색 조건에 맞는 이메일 템플릿 정보를 조회한다.
	 * @Method Name : getdEmailTemplateList
	 * @Date : 2019. 3. 14.
	 * @Author : kiowa
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<CmEmailTemplate> getEmailTemplateList(Pageable<EmailTemplateSearchVO, CmEmailTemplate> pageable)
			throws Exception {

		int totalCount = cmEmailTemplateDao.selectCmEmailTemplateListCount(pageable);

		pageable.setTotalCount(totalCount);

		if (totalCount > 0) {
			pageable.setContent(cmEmailTemplateDao.selectCmEmailTemplateList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 이메일 템플릿 상세 정보를 조회한다.
	 * @Method Name : getEmailTemplateReadDetail
	 * @Date : 2019. 3. 14.
	 * @Author : kiowa
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getEmailTemplateReadDetail(CmEmailTemplate params) throws Exception {
		Map<String, Object> rtnVal = new HashMap<String, Object>(); // 결과 정보
		String codeFiled = CommonCode.EMAIL_TYPE_CODE;

		rtnVal.put("emailTypeCodeList", commonCodeService.getCodeNoName(codeFiled));
		rtnVal.put("siteList", siteService.getSiteList());

		if (null != params.getEmailTmplSeq() && params.getEmailTmplSeq() > 0) {

			params = cmEmailTemplateDao.selectCmEmailTemplateDetail(params);

			// 발송일이 등록된 경우 상세 페이지 노출을 위해 날짜, 시간, 분 정보를 구한다.
			if (null != params.getSendDtm()) {
				long time = params.getSendDtm().getTime();

				params.setSendDay(UtilsDate.formatter(Const.DEFAULT_DATE_PATTERN, time));
				params.setSendHour(UtilsDate.formatter("H", time));
				params.setSendMinute(UtilsDate.formatter("m", time));
			}

			rtnVal.put("cmEmailTemplate", params);

		}

		return rtnVal;
	}

	/**
	 * @Desc : 이메일 Key 상세 정보를 조회한다.
	 * @Method Name : getEmailTemplateReadDetail
	 * @Date : 2019. 3. 14.
	 * @Author : kiowa
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getEmailKeyReadDetail(CmEmailTemplate params) throws Exception {
		Map<String, Object> rtnVal = new HashMap<String, Object>(); // 결과 정보

		rtnVal.put("siteList", siteService.getSiteList());

		if (null != params.getEmailTmplSeq() && params.getEmailTmplSeq() > 0) {

			params = cmEmailTemplateDao.selectCmEmailTemplateDetail(params);

			// 발송일이 등록된 경우 상세 페이지 노출을 위해 날짜, 시간, 분 정보를 구한다.
			if (null != params.getSendDtm()) {
				long time = params.getSendDtm().getTime();

				params.setSendDay(UtilsDate.formatter(Const.DEFAULT_DATE_PATTERN, time));
				params.setSendHour(UtilsDate.formatter("H", time));
				params.setSendMinute(UtilsDate.formatter("m", time));
			}

			rtnVal.put("cmEmailTemplate", params);
		}

		return rtnVal;
	}

	/**
	 * @Desc : 이메일 템플릿 정보를 저장한다.
	 * @Method Name : setEmailTemplateCreate
	 * @Date : 2019. 3. 15.
	 * @Author : kiowa
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int setEmailTemplateCreate(CmEmailTemplate params) throws Exception {
		return cmEmailTemplateDao.insertCmEmailTemplate(params);
	}

	/**
	 * @Desc : 이메일 템플릿 정보를 수정한다.
	 * @Method Name : setEmailTemplateModify
	 * @Date : 2019. 3. 18.
	 * @Author : kiowa
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int setEmailTemplateModify(CmEmailTemplate params) throws Exception {
		return cmEmailTemplateDao.updateCmEmailTemplate(params);
	}

	/**
	 * @Desc : 이메일 템플릿 정보를 삭제한다.
	 * @Method Name : setEmailTemplateDelete
	 * @Date : 2019. 3. 18.
	 * @Author : kiowa
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int setEmailTemplateDelete(CmEmailTemplate params) throws Exception {
		return cmEmailTemplateDao.delete(params);
	}

	/**
	 * @Desc : 공통 이메일 발송 팝업 폼 데이터 조회
	 * @Method Name : getEmailSendForm
	 * @Date : 2019. 3. 18.
	 * @Author : kiowa
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getEmailSendForm(CommonEmailPopupVO commonEmailPopupVo) throws Exception {
		Map<String, Object> rtnVal = new HashMap<String, Object>();
		String codeFiled = CommonCode.EMAIL_TYPE_CODE;

		rtnVal.put("emailTypeCodeList", commonCodeService.getCodeNoName(codeFiled));
		rtnVal.put("siteList", siteService.getSiteList());

		if (!UtilsText.isAllBlank(commonEmailPopupVo.getRcvrMemberNo())
				&& !Const.NON_MEMBER_NO.equals(commonEmailPopupVo.getRcvrMemberNo())) {
			MbMember member = memberService.getMember(commonEmailPopupVo.getRcvrMemberNo());
			if (null != member) {
				commonEmailPopupVo.setRcvrEmailAddrText(member.getEmailAddrText());
				commonEmailPopupVo.setRcvrName(member.getMemberName());
			}
		}

		rtnVal.put("commonEmailPopupVo", commonEmailPopupVo);

		return rtnVal;
	}

	/**
	 * @Desc : 선택한 조건에 맞는 Email 템플릿 정보를 조회한다.
	 * @Method Name : getEmailTemplateList
	 * @Date : 2019. 3. 20.
	 * @Author : kiowa
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<CmEmailTemplate> getEmailTemplateList(EmailTemplateSearchVO params) throws Exception {
		return cmEmailTemplateDao.selectEmailSendPopTemplateList(params);
	}

	/**
	 * @Desc : 이메일 키 검색 Form에 필요항 사이트 정보를 조회한다.
	 * @Method Name : getEmailKeySearchForm
	 * @Date : 2019. 3. 21.
	 * @Author : kiowa
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getEmailKeySearchForm() throws Exception {
		Map<String, Object> rtnVal = new HashMap<String, Object>();

		rtnVal.put("siteList", siteService.getSiteList());

		return rtnVal;
	}

	/**
	 * @Desc : 이메일 키 관리 조회
	 * @Method Name : getEmailKeyList
	 * @Date : 2019. 3. 22.
	 * @Author : kiowa
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<CmEmailTemplate> getEmailKeyList(Pageable<EmailTemplateSearchVO, CmEmailTemplate> pageable)
			throws Exception {

		int totalCount = cmEmailTemplateDao.selectCmEmailTemplateListCount(pageable);

		pageable.setTotalCount(totalCount);

		if (totalCount > 0) {
			pageable.setContent(cmEmailTemplateDao.selectCmEmailTemplateList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : email_id에 해당하는 템플릿 조회
	 * @Method Name : getEmailTemplateByMailId
	 * @Date : 2019. 3. 25.
	 * @Author : Kimyounghyun
	 * @param mailId
	 * @return
	 */
	public CmEmailTemplate getEmailTemplateByMailId(String emailId) {
		return cmEmailTemplateDao.selectCmEmailTemplateByEmailId(emailId);
	}

	/**
	 * @Desc : 이메일 ID 중복확인을 한다.
	 * @Method Name : getEmailIdCount
	 * @Date : 2019. 4. 12.
	 * @Author : 고웅환
	 * @param emailId
	 * @return
	 * @throws Exception
	 */
	public int getEmailIdCount(String emailId) throws Exception {
		return cmEmailTemplateDao.selectCmEmailIdCount(emailId);
	}

}
