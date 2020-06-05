package kr.co.abcmart.bo.board.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.board.model.master.BdMemberCounsel;
import kr.co.abcmart.bo.board.model.master.BdMemberCounselAttachFile;
import kr.co.abcmart.bo.board.model.master.BdMemberCounselMemo;
import kr.co.abcmart.bo.board.repository.master.BdMemberCounselAttachFileDao;
import kr.co.abcmart.bo.board.repository.master.BdMemberCounselDao;
import kr.co.abcmart.bo.board.repository.master.BdMemberCounselMemoDao;
import kr.co.abcmart.bo.board.vo.BdInquirySearchVO;
import kr.co.abcmart.bo.cmm.model.master.CmCounselScript;
import kr.co.abcmart.bo.cmm.service.CmCounselScriptService;
import kr.co.abcmart.bo.cmm.service.MailService;
import kr.co.abcmart.bo.cmm.service.MessageService;
import kr.co.abcmart.bo.cmm.vo.MailTemplateVO;
import kr.co.abcmart.bo.cmm.vo.MessageVO;
import kr.co.abcmart.bo.member.model.master.MbMember;
import kr.co.abcmart.bo.member.service.MemberService;
import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.bo.system.service.CommonCodeService;
import kr.co.abcmart.bo.system.service.SiteService;
import kr.co.abcmart.common.message.Message;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.FileUpload;
import kr.co.abcmart.common.util.UtilsDate;
import kr.co.abcmart.common.util.UtilsFile;
import kr.co.abcmart.common.util.UtilsObject;
import kr.co.abcmart.common.util.UtilsSftp;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.constant.MailCode;
import kr.co.abcmart.constant.MessageCode;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BdInquiryService {

	@Autowired
	private BdMemberCounselDao bdMemberCounselDao;

	@Autowired
	private BdMemberCounselAttachFileDao bdMemberCounselAttachFileDao;

	@Autowired
	private BdMemberCounselMemoDao bdMemberCounselMemoDao;

	@Autowired
	private CommonCodeService commonCodeService;

	@Autowired
	private CmCounselScriptService counselScriptService;

	@Autowired
	private BdInquiryService bdInquiryService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private MessageService messageService;

	@Autowired
	private MailService mailService;

	@Autowired
	private SiteService siteService;

	private static final String FILE_PATH = UtilsText.concat(Const.UPLOAD_FILE_PATH, Const.IMG_SRC_MEMBERCOUNSEL,
			UtilsDate.today(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS));

	/**
	 * @Desc : 문의 관리 리스트 Form 기초 데이터를 조회
	 * @Method Name : getInquerySearchForm
	 * @Date : 2019. 2. 11.
	 * @Author : 신인철
	 * @param codeFields
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getInquirySearchForm() throws Exception {
		Map<String, Object> rtnVal = new HashMap<String, Object>();
		List<SyCodeDetail> codeSearchParam = new ArrayList<SyCodeDetail>();

		SyCodeDetail codeParam = new SyCodeDetail();
		codeParam.setCodeField(CommonCode.CNSL_TYPE_CODE);
		codeParam.setAddInfo1(CommonCode.CNSL_GBN_CODE_INQUIRY);
		codeParam.setAddInfo2(Const.BOOLEAN_TRUE);
		codeSearchParam.add(codeParam);

		codeParam = new SyCodeDetail();
		codeParam.setCodeField(CommonCode.ASWR_STAT_CODE);
		codeSearchParam.add(codeParam);

		Map<String, List<SyCodeDetail>> codeListMap = commonCodeService.getCodeListByGroup(codeSearchParam);

		rtnVal.put("cnslTypeCode", codeListMap.get(CommonCode.CNSL_TYPE_CODE));
		rtnVal.put("aswrStatCode", codeListMap.get(CommonCode.ASWR_STAT_CODE));
		rtnVal.put("siteNoList", siteService.getSiteList());

		return rtnVal;
	}

	/**
	 * @Desc : 고객의소리 메인 폼 조회
	 * @Method Name : getVocSearchForm
	 * @Date : 2019. 8. 8.
	 * @Author : 신인철
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getVocSearchForm() throws Exception {
		Map<String, Object> rtnVal = new HashMap<String, Object>();
		List<SyCodeDetail> codeSearchParam = new ArrayList<SyCodeDetail>();

		SyCodeDetail codeParam = new SyCodeDetail();
		codeParam.setCodeField(CommonCode.CNSL_TYPE_CODE);
		codeParam.setAddInfo1(CommonCode.CNSL_GBN_CODE_VOC);
		codeSearchParam.add(codeParam);

		codeParam = new SyCodeDetail();
		codeParam.setCodeField(CommonCode.ASWR_STAT_CODE);
		codeSearchParam.add(codeParam);

		Map<String, List<SyCodeDetail>> codeListMap = commonCodeService.getCodeListByGroup(codeSearchParam);
		rtnVal.put("vocTypeList", codeListMap.get(CommonCode.CNSL_TYPE_CODE));
		rtnVal.put("aswrStatCode", codeListMap.get(CommonCode.ASWR_STAT_CODE));
		rtnVal.put("siteNoList", siteService.getSiteList());

		return rtnVal;
	}

	/**
	 * @Desc : 검색 조건에 맞는 문의 정보를 조회한다.
	 * @Method Name : getInqueryReadList
	 * @Date : 2019. 2. 11.
	 * @Author : 신인철
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<BdMemberCounsel> getInquiryReadList(Pageable<BdInquirySearchVO, BdMemberCounsel> pageable)
			throws Exception {

		int totalCount = bdMemberCounselDao.selectBdMemberCounselListCount(pageable);

		pageable.setTotalCount(totalCount);

		if (totalCount > 0) {
			pageable.setContent(bdMemberCounselDao.selectBdMemberCounselList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 문의내용 상세 정보를 조회한다.
	 * @Method Name : getInquiryDetail
	 * @Date : 2019. 2. 20.
	 * @Author : 신인철
	 * @param bdMemberCounsel
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getInquiryDetail(BdMemberCounsel bdMemberCounsel) throws Exception {
		Map<String, Object> rtnVal = new HashMap<String, Object>();
		// 문의 정보 조회
		bdMemberCounsel = bdInquiryService.selectInquiryDetail(bdMemberCounsel);

		if (bdMemberCounsel.getCnslScriptSeq() != null) { // 답변 스크립트를 사용해서 답변했을 경우
			CmCounselScript counselScript = counselScriptService.getAswrCnslScript(bdMemberCounsel.getCnslScriptSeq());
			bdMemberCounsel.setAswrCnslTypeCode(counselScript.getCnslTypeCode());
			bdMemberCounsel.setAswrCnslTypeDtlCode(counselScript.getCnslTypeDtlCode());
			rtnVal.put("counselScript", counselScript);
		}

		bdMemberCounsel = bdInquiryService.readContentNewLineProcess(bdMemberCounsel);

		rtnVal.put("bdMemberCounsel", bdMemberCounsel);
		// 회원 정보 조회
		rtnVal.put("memberInfo", memberService.getMember(bdMemberCounsel.getMemberNo()));

		// 상세 정보에 쓰이는 코드 가져오기
		Map<String, List<SyCodeDetail>> codeListMap = bdInquiryService.getCounselDetailNeedCodeList(bdMemberCounsel,
				CommonCode.CNSL_GBN_CODE_INQUIRY);
		List<SyCodeDetail> aswrCnslList = codeListMap.get(CommonCode.ASWR_CNSL_TYPE_CODE);
		SyCodeDetail syCodeDetail = new SyCodeDetail();
		syCodeDetail.setCodeField(CommonCode.CNSL_TYPE_CODE);
		syCodeDetail.setAddInfo1(CommonCode.CNSL_GNB_CODE_PRODUCT_REVIEW);
		aswrCnslList.add(commonCodeService.getCodeDetailByFiledAddInfo(syCodeDetail));

		rtnVal.put("cnslTypeCode", codeListMap.get(CommonCode.CNSL_TYPE_CODE)); // 문의 유형 코드
		rtnVal.put("cnslTypeDtlCode", codeListMap.get(CommonCode.CNSL_TYPE_DTL_CODE)); // 문의 구분코드
		rtnVal.put("aswrCnslTypeCode", aswrCnslList); // 답변스크립트 상담유형 코드
		rtnVal.put("aswrCnslTypeDtlCode", codeListMap.get(CommonCode.ASWR_CNSL_TYPE_DTL_CODE)); // 답변스크립트 상담유형 코드

		// 문의/답변 정보 파일 조회
		Map<String, List<BdMemberCounselAttachFile>> fileListMap = bdInquiryService
				.getCounselfileListMap(bdMemberCounsel);

		rtnVal.put("inqryCounselAttachFiles", fileListMap.get("inqryFile"));
		rtnVal.put("aswrCounselAttachFiles", fileListMap.get("aswrFile"));

		// 관리자 메모 정보 조회
		List<BdMemberCounselMemo> memoList = bdInquiryService.getCounselMemoList(bdMemberCounsel);
		rtnVal.put("bdMemberCounselMemo", memoList);

		return rtnVal;
	}

	/**
	 * @Desc : 상세보기시에 개행처리
	 * @Method Name : contentNewLineProcess
	 * @Date : 2019. 10. 17.
	 * @Author : sic
	 * @param bdMemberCounsel
	 * @return
	 * @throws Exception
	 */
	public BdMemberCounsel readContentNewLineProcess(BdMemberCounsel bdMemberCounsel) throws Exception {
		String replaceCont = bdMemberCounsel.getInqryContText().replace(Const.STRING_HTML_BR_TAG,
				Const.STRING_HTML_NEW_LINE);
		bdMemberCounsel.setInqryContText(replaceCont);
		if (UtilsText.isNotBlank(bdMemberCounsel.getAswrContText())) {
			replaceCont = bdMemberCounsel.getAswrContText().replace(Const.STRING_HTML_BR_TAG,
					Const.STRING_HTML_NEW_LINE);
			bdMemberCounsel.setAswrContText(replaceCont);
		}
		return bdMemberCounsel;
	}

	/**
	 * @Desc : 답변 등록시에 개행 처리
	 * @Method Name : readContentNewLineProcess
	 * @Date : 2020. 1. 17.
	 * @Author : sic
	 * @param bdMemberCounsel
	 * @return
	 * @throws Exception
	 */
	public BdMemberCounsel aswrContentNewLineProcess(BdMemberCounsel bdMemberCounsel) throws Exception {
		String replaceCont;
		if (UtilsText.isNotBlank(bdMemberCounsel.getInqryContText())) {
			replaceCont = bdMemberCounsel.getInqryContText().replace(Const.STRING_HTML_NEW_LINE,
					Const.STRING_HTML_BR_TAG);
			bdMemberCounsel.setInqryContText(replaceCont);
		}
		if (UtilsText.isNotBlank(bdMemberCounsel.getAswrContText())) {
			replaceCont = bdMemberCounsel.getAswrContText().replace(Const.STRING_HTML_NEW_LINE,
					Const.STRING_HTML_BR_TAG);
			bdMemberCounsel.setAswrContText(replaceCont);
		}
		return bdMemberCounsel;
	}

	/**
	 * @Desc : 상담 관리자 메모 내역 가져오기
	 * @Method Name : getCounselMemoList
	 * @Date : 2019. 8. 8.
	 * @Author : sic
	 * @param bdMemberCounsel
	 * @return
	 * @throws Exception
	 */
	public List<BdMemberCounselMemo> getCounselMemoList(BdMemberCounsel bdMemberCounsel) throws Exception {
		BdMemberCounselMemo bdMemberCounselMemo = new BdMemberCounselMemo();
		bdMemberCounselMemo.setMemberCnslSeq(bdMemberCounsel.getMemberCnslSeq());

		List<BdMemberCounselMemo> memoList = bdInquiryService.selectBdMemberCounselMemoList(bdMemberCounselMemo);
		memoList.stream().filter(o -> UtilsText.equals(Message.getMessage("system.msg.escalation"), o.getMemoText()))
				.forEach(ob -> ob.setEscalationYn(Const.BOOLEAN_TRUE));
		return memoList;
	}

	/**
	 * @Desc : 상담내역 파일 조회
	 * @Method Name : getCounselfileListMap
	 * @Date : 2019. 8. 8.
	 * @Author : sic
	 * @param bdMemberCounsel
	 * @return
	 * @throws Exception
	 */
	public Map<String, List<BdMemberCounselAttachFile>> getCounselfileListMap(BdMemberCounsel bdMemberCounsel)
			throws Exception {
		Map<String, List<BdMemberCounselAttachFile>> fileListMap = new HashMap<>();
		BdMemberCounselAttachFile bdMemberCounselAttachFile = new BdMemberCounselAttachFile();

		bdMemberCounselAttachFile.setMemberCnslSeq(bdMemberCounsel.getMemberCnslSeq());

		List<BdMemberCounselAttachFile> counselAttachFiles = bdInquiryService
				.selectInquryAttachFiles(bdMemberCounselAttachFile);
		// 문의 첨부
		List<BdMemberCounselAttachFile> inqryCounselAttachFiles = counselAttachFiles.stream()
				.filter(o -> UtilsText.equals(Const.COUNSEL_INQRY_FILE_GBN_TYPE, o.getAtchFileGbnType()))
				.collect(Collectors.toList());
		// 답변 첨부 파일
		List<BdMemberCounselAttachFile> aswrCounselAttachFiles = counselAttachFiles.stream()
				.filter(o -> !UtilsText.equals(Const.COUNSEL_INQRY_FILE_GBN_TYPE, o.getAtchFileGbnType()))
				.collect(Collectors.toList());

		fileListMap.put("inqryFile", inqryCounselAttachFiles);
		fileListMap.put("aswrFile", aswrCounselAttachFiles);

		return fileListMap;
	}

	/**
	 * @Desc : 고객의소리 상세정보
	 * @Method Name : getVocDetail
	 * @Date : 2019. 4. 17.
	 * @Author : 신인철
	 * @param bdMemberCounsel
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getVocDetail(BdMemberCounsel bdMemberCounsel) throws Exception {
		Map<String, Object> rtnVal = new HashMap<String, Object>();

		// 문의 정보 조회
		bdMemberCounsel = bdInquiryService.selectInquiryDetail(bdMemberCounsel);

		if (bdMemberCounsel.getCnslScriptSeq() != null) { // 답변 스크립트를 사용해서 답변했을 경우
			CmCounselScript counselScript = counselScriptService.getAswrCnslScript(bdMemberCounsel.getCnslScriptSeq());
			bdMemberCounsel.setAswrCnslTypeCode(counselScript.getCnslTypeCode());
			bdMemberCounsel.setAswrCnslTypeDtlCode(counselScript.getCnslTypeDtlCode());
			rtnVal.put("counselScript", counselScript);
		}

		bdMemberCounsel = bdInquiryService.readContentNewLineProcess(bdMemberCounsel);
		rtnVal.put("bdMemberCounsel", bdMemberCounsel);

		// 회원 정보 조회
		rtnVal.put("memberInfo", memberService.getMember(bdMemberCounsel.getMemberNo()));

		Map<String, List<SyCodeDetail>> codeListMap = bdInquiryService.getCounselDetailNeedCodeList(bdMemberCounsel,
				CommonCode.CNSL_GBN_CODE_VOC);

		rtnVal.put("cnslTypeCode", codeListMap.get(CommonCode.CNSL_TYPE_CODE)); // 문의 유형 코드
		rtnVal.put("cnslTypeDtlCode", codeListMap.get(CommonCode.CNSL_TYPE_DTL_CODE)); // 문의 구분코드
		rtnVal.put("aswrCnslTypeCode", codeListMap.get("ASWR_CNSL_TYPE_CODE")); // 답변스크립트 상담유형 코드
		rtnVal.put("aswrCnslTypeDtlCode", codeListMap.get("ASWR_CNSL_TYPE_DTL_CODE")); // 답변스크립트 상담유형 코드

		// 관리자 메모 정보 조회
		BdMemberCounselMemo bdMemberCounselMemo = new BdMemberCounselMemo();

		bdMemberCounselMemo.setMemberCnslSeq(bdMemberCounsel.getMemberCnslSeq());

		rtnVal.put("bdMemberCounselMemo", bdInquiryService.selectBdMemberCounselMemoList(bdMemberCounselMemo));

		return rtnVal;
	}

	/**
	 * @Desc : 1:1 문의, 고객의소리 상세에 필요한 코드 조회
	 * @Method Name : getCounselDetailNeedCodeList
	 * @Date : 2019. 10. 28.
	 * @Author : sic
	 * @param bdMemberCounsel
	 * @param cnslGbnCode
	 * @return
	 * @throws Exception
	 */
	public Map<String, List<SyCodeDetail>> getCounselDetailNeedCodeList(BdMemberCounsel bdMemberCounsel,
			String cnslGbnCode) throws Exception {
		List<SyCodeDetail> codeSearchCondition = new ArrayList<SyCodeDetail>();
		// 문의 유형 코드
		SyCodeDetail syCodeDetail = new SyCodeDetail();
		syCodeDetail.setCodeField(CommonCode.CNSL_TYPE_CODE);

		if (cnslGbnCode.equals(CommonCode.CNSL_GBN_CODE_INQUIRY)) {
			syCodeDetail.setAddInfo1(CommonCode.CNSL_GBN_CODE_INQUIRY);
			syCodeDetail.setAddInfo2(Const.BOOLEAN_TRUE);
		} else if (cnslGbnCode.equals(CommonCode.CNSL_GBN_CODE_VOC)) {
			syCodeDetail.setAddInfo1(CommonCode.CNSL_GBN_CODE_VOC);
		}
		codeSearchCondition.add(syCodeDetail);

		// 문의 구분 코드
		syCodeDetail = new SyCodeDetail();
		syCodeDetail.setCodeField(CommonCode.CNSL_TYPE_DTL_CODE);

		if (cnslGbnCode.equals(CommonCode.CNSL_GBN_CODE_INQUIRY)) {
			syCodeDetail.setAddInfo1(bdMemberCounsel.getCnslTypeCode());
		} else if (cnslGbnCode.equals(CommonCode.CNSL_GBN_CODE_VOC)) {
			syCodeDetail.setAddInfo1(CommonCode.CNSL_TYPE_CODE_VOC);
		}
		codeSearchCondition.add(syCodeDetail);

		// 답변 스크립크 유형 코드
		syCodeDetail = new SyCodeDetail();
		syCodeDetail.setCodeField(CommonCode.CNSL_TYPE_CODE);

		if (cnslGbnCode.equals(CommonCode.CNSL_GBN_CODE_INQUIRY)) {
			syCodeDetail.setAddInfo1(CommonCode.CNSL_GBN_CODE_INQUIRY);
		} else if (cnslGbnCode.equals(CommonCode.CNSL_GBN_CODE_VOC)) {
			syCodeDetail.setAddInfo1(CommonCode.CNSL_GBN_CODE_VOC);
		}
		syCodeDetail.setDispCodeField(CommonCode.ASWR_CNSL_TYPE_CODE);
		syCodeDetail.setUseYn(Const.BOOLEAN_TRUE);
		codeSearchCondition.add(syCodeDetail);

		// 답변 스크립크 문의 구분 코드
		if (bdMemberCounsel.getAswrCnslTypeDtlCode() != null) {
			// 답변 스크립크 문의 구분 코드
			syCodeDetail = new SyCodeDetail();
			syCodeDetail.setCodeField(CommonCode.CNSL_TYPE_DTL_CODE);
			syCodeDetail.setAddInfo1(bdMemberCounsel.getCnslTypeCode());
			syCodeDetail.setDispCodeField(CommonCode.ASWR_CNSL_TYPE_DTL_CODE);
			codeSearchCondition.add(syCodeDetail);
		}
		return commonCodeService.getCodeListByGroup(codeSearchCondition);
	}

	/**
	 * @Desc : 문의 상세 정보를 조회한다.
	 * @Method Name : selectInquiryDetail
	 * @Date : 2019. 2. 18.
	 * @Author : 신인철
	 * @param bdMemberCounsel
	 * @return
	 * @throws Exception
	 */
	public BdMemberCounsel selectInquiryDetail(BdMemberCounsel bdMemberCounsel) throws Exception {
		return bdMemberCounselDao.selectBdMemberCounsel(bdMemberCounsel);
	}

	/**
	 * @Desc : 상담 첨부 파일 조회
	 * @Method Name : selectInquiryAttachFiles
	 * @Date : 2019. 2. 12.
	 * @Author : 신인철
	 * @param bdMemberCounselAttachFile
	 * @return
	 * @throws Exception
	 */
	public List<BdMemberCounselAttachFile> selectInquryAttachFiles(BdMemberCounselAttachFile bdMemberCounselAttachFile)
			throws Exception {

		return bdMemberCounselAttachFileDao.selectBdMemberCounselAttachFileList(bdMemberCounselAttachFile);
	}

	/**
	 * @Desc : 문의 관리자 메모 조회
	 * @Method Name : selectBdMemberCounselMemoList
	 * @Date : 2019. 3. 4.
	 * @Author : 이동엽
	 * @param bdMemberCounselMemo
	 * @return
	 * @throws Exception
	 */
	public List<BdMemberCounselMemo> selectBdMemberCounselMemoList(BdMemberCounselMemo bdMemberCounselMemo)
			throws Exception {
		List<BdMemberCounselMemo> memoList = bdMemberCounselMemoDao.selectBdMemberCounselMemoList(bdMemberCounselMemo);

		memoList.forEach(o -> {
			o.setMemoText(o.getMemoText().replace(Const.STRING_HTML_NEW_LINE, Const.STRING_HTML_BR_TAG));
		});
		return memoList;
	}

	/**
	 * @Desc : 관리자 메모 작성
	 * @Method Name : setAdminMemo
	 * @Date : 2019. 2. 20.
	 * @Author : 신인철
	 * @param bdMemberCounselMemo
	 * @return
	 * @throws Exception
	 */
	public BdMemberCounselMemo setAdminMemo(BdMemberCounselMemo bdMemberCounselMemo) throws Exception {
		UserDetails user = LoginManager.getUserDetails();
		bdMemberCounselMemo.setRgsterNo(user.getAdminNo());
		bdMemberCounselMemo.setDelYn(Const.BOOLEAN_FALSE);
		bdMemberCounselMemo.setMemoText(
				bdMemberCounselMemo.getMemoText().replace(Const.STRING_HTML_NEW_LINE, Const.STRING_HTML_BR_TAG));

		bdMemberCounselMemoDao.insertAdminMemo(bdMemberCounselMemo);

		BdMemberCounselMemo resultVO = bdMemberCounselMemoDao.selectAddMemo(bdMemberCounselMemo);

		return resultVO;
	}

	/**
	 * @Desc : 관리자 메모 삭제
	 * @Method Name : deleteAdminMemo
	 * @Date : 2019. 2. 19.
	 * @Author : 신인철
	 * @param bdMemberCounselMemo
	 * @throws Exception
	 */
	public void deleteAdminMemo(BdMemberCounselMemo bdMemberCounselMemo) throws Exception {
		bdMemberCounselMemo.setDelYn(Const.BOOLEAN_TRUE);
		bdMemberCounselMemoDao.deleteAddminMemo(bdMemberCounselMemo);

	}

	/**
	 * @Desc : 상담정보 수정
	 * @Method Name : updateInquryDetail
	 * @Date : 2019. 2. 21.
	 * @Author : 신인철
	 * @param bdMemberCounsel
	 * @return
	 * @throws Exception
	 */
	public void updateInquryDetail(BdMemberCounsel bdMemberCounsel) throws Exception {
		UserDetails user = LoginManager.getUserDetails();
		MbMember memberInfo = memberService.getMember(bdMemberCounsel.getMemberNo());
		if (!UtilsText.equals(memberInfo.getLeaveYn(), Const.BOOLEAN_FALSE)) {
			throw new Exception("이미 탈퇴한 회원입니다.");
		}

		if (bdMemberCounsel.getRemoveAtchFileSeq() != null) {
			// 제거한 첨부파일이 존재할시 파일 제거
			bdInquiryService.removeImageFile(bdMemberCounsel);
		}
		// 등록 파일 있을시 파일 등록
		bdInquiryService.setAttachFile(bdMemberCounsel);

		// 이관 하였을경우 처음에 이관 메모와 이관 담당자 추가
		if (UtilsText.equals(Const.BOOLEAN_TRUE, bdMemberCounsel.getVndrAssignYn())) {
			if (bdMemberCounsel.getVndrAsnrNo() == null) {
				bdMemberCounsel.setVndrAsnrNo(user.getAdminNo());
				bdInquiryService.setEscalaionAdminMemo(bdMemberCounsel, user.getAdminNo());
			} else {
				bdMemberCounsel.setVndrAsnrNo(null);
			}
		}
		if (UtilsText.equals(bdMemberCounsel.getAswrStatCode(), CommonCode.ASWR_STAT_CODE_CM)) {
			bdMemberCounsel.setAswrNo(user.getAdminNo());
			bdMemberCounsel.setAswrDtm(UtilsDate.getSqlTimeStamp());
		}
		// 개행 처리
		if (bdMemberCounsel.getAswrContText() != null) {
			bdMemberCounsel = bdInquiryService.aswrContentNewLineProcess(bdMemberCounsel);
		}

		bdMemberCounselDao.updatebdMemberCounsel(bdMemberCounsel);

		if (!UtilsText.equals(bdMemberCounsel.getCnslGbnCode(), CommonCode.CNSL_GBN_CODE_TELCNSL)) {
			// 답변 등록 후 메세지 발송
			if (UtilsText.equals(bdMemberCounsel.getAswrStatCode(), CommonCode.ASWR_STAT_CODE_CM)
					&& UtilsText.isBlank(bdMemberCounsel.getCheckAswrDtm())
					&& UtilsText.isNotBlank(memberInfo.getHdphnNoText())) {
				bdInquiryService.sendMsgNoTrx(bdMemberCounsel, memberInfo, CommonCode.CNSL_GBN_CODE_INQUIRY);
			}
			// 이메일 발송
			if (UtilsText.equals(bdMemberCounsel.getAswrStatCode(), CommonCode.ASWR_STAT_CODE_CM)
					&& UtilsText.isBlank(bdMemberCounsel.getCheckAswrDtm())
					&& UtilsText.isNotBlank(memberInfo.getEmailAddrText())) {
				bdInquiryService.sendEmailNoTrx(bdMemberCounsel, memberInfo, CommonCode.CNSL_GBN_CODE_INQUIRY);
			}
		}
	}

	/**
	 * @Desc : 첨부파일 삭제
	 * @Method Name : removeImageFile
	 * @Date : 2019. 8. 7.
	 * @Author : 신인철
	 * @param bdMemberCounsel
	 * @throws Exception
	 */
	public void removeImageFile(BdMemberCounsel bdMemberCounsel) throws Exception {
		BdMemberCounselAttachFile bdMemberCounselAttachFile = new BdMemberCounselAttachFile();
		bdMemberCounselAttachFile.setMemberCnslSeq(bdMemberCounsel.getMemberCnslSeq());
		List<BdMemberCounselAttachFile> removeFileList = new ArrayList<>();

		for (int atchFileSeq : bdMemberCounsel.getRemoveAtchFileSeq()) {
			bdMemberCounselAttachFile.setAtchFileSeq(atchFileSeq);
			removeFileList.add(bdMemberCounselAttachFileDao.selectByPrimaryKey(bdMemberCounselAttachFile));

			bdMemberCounselAttachFileDao.delete(bdMemberCounselAttachFile);
		}
		deleteImage(removeFileList);
	}

	/**
	 * @Desc : 나스 이미지 삭제
	 * @Method Name : deleteImage
	 * @Date : 2019. 7. 3.
	 * @Author : 신인철
	 * @param removeFileList
	 */
	public void deleteImage(List<BdMemberCounselAttachFile> removeFileList) {
		for (BdMemberCounselAttachFile file : removeFileList) {
			String delPath = file.getAtchFilePathText();
			File delFile = new File(delPath);
			if (delFile.exists()) {
				delFile.delete();
			} else {
				log.error("파일이 존재하지 않습니다.");
			}
		}
	}

	/**
	 * @Desc : 상담완료후 알림톡 보내기
	 * @Method Name : sendMsg
	 * @Date : 2019. 4. 29.
	 * @Author : 신인철
	 * @param bdMemberCounsel
	 * @throws Exception
	 */
	public void sendMsgNoTrx(BdMemberCounsel bdMemberCounsel, MbMember memberInfo, String cnslGbnCode)
			throws Exception {
		MessageVO messageVO = new MessageVO();
		Map<String, Object> dataMap = new HashMap<>();
		dataMap.put("memberName", memberInfo.getMemberName());
		// 발신자, 발신자 전화번호, 수신사, 수신자 전화번호, 내용
		if (UtilsText.equals(bdMemberCounsel.getSiteNo(), Const.SITE_NO_ART)) {
			messageVO.setMesgId(MessageCode.INQUIRY_COMPLETED_ART);
			messageVO.setSiteNo(Const.SITE_NO_ART);
			dataMap.put("linkUrl",
					UtilsText.concat(Const.URL_WWW_ART_FO_HTTPS, "/mypage/member-counsel/read-inqry-list"));
			messageVO.setSendTelNoText(Const.SYS_SENDER_MESSAGE_NUMBER);
			messageVO.setSndrName(Const.SYS_SENDER_MESSAGE_NAME);
		} else {
			messageVO.setMesgId(MessageCode.INQUIRY_COMPLETED_OTS);
			messageVO.setSiteNo(Const.SITE_NO_OTS);
			dataMap.put("linkUrl",
					UtilsText.concat(Const.URL_WWW_OTS_FO_HTTPS, "/mypage/member-counsel/read-inqry-list"));
			messageVO.setSendTelNoText(Const.SYS_OTS_SENDER_MESSAGE_NUMBER);
			messageVO.setSndrName(Const.SYS_OTS_SENDER_MESSAGE_NAME);
		}
		messageVO.setRecvTelNoText(memberInfo.getHdphnNoText());
		messageVO.setRcvrName(memberInfo.getMemberName());
		messageVO.setMessageTemplateModel(dataMap);
		messageService.setSendMessageProcess(messageVO);
	}

	/**
	 * @Desc : 이메일 발송
	 * @Method Name : sendEmail
	 * @Date : 2019. 7. 1.
	 * @Author : 신인철
	 * @param bdMemberCounsel
	 * @param cnslGbnCode
	 * @throws Exception
	 */
	public void sendEmailNoTrx(BdMemberCounsel bdMemberCounsel, MbMember memberInfo, String cnslGbnCode)
			throws Exception {
		MailTemplateVO mailVO = new MailTemplateVO();
		Map<String, Object> dataMap = new HashMap<>();
		String replaceCont = bdMemberCounsel.getInqryContText().replace("\r\n", "<br/>");
		bdMemberCounsel.setInqryContText(replaceCont);

		log.debug("inqry>>>>>>>>>>>>>>", bdMemberCounsel.getInqryContText());
		log.debug("aswr>>>>>>>>>>>>>>", bdMemberCounsel.getAswrContText());
		dataMap.put("memberName", memberInfo.getMemberName());
		dataMap.put("counsel", bdMemberCounsel);
		dataMap.put("abcUrl", Const.SERVICE_DOMAIN_ART_FO);

		// 템플릿ID, 템플릿에 들어가는 모델, 회원 번호, 수신자 메일, 이름
		if (UtilsText.equals(cnslGbnCode, CommonCode.CNSL_GBN_CODE_INQUIRY)) {
			mailVO.setMailTemplateId(MailCode.INQUIRY_ASWR_COM);
		} else if (UtilsText.equals(cnslGbnCode, CommonCode.CNSL_GBN_CODE_VOC)) {
			mailVO.setMailTemplateId(MailCode.VOC_ASWR_COM);
		}
		mailVO.setMailTemplateModel(dataMap);
		mailVO.setReceiverMemberNo(memberInfo.getMemberNo());
		mailVO.setReceiverEmail(memberInfo.getEmailAddrText());
		mailVO.setReceiverName(memberInfo.getMemberName());

		mailService.sendMail(mailVO);
	}

	/**
	 * @Desc : 이관후 관리자 메모 추가
	 * @Method Name : setEscalaionAdminMemo
	 * @Date : 2019. 4. 12.
	 * @Author : 신인철
	 * @param bdMemberCounsel
	 * @throws Exception
	 */
	public void setEscalaionAdminMemo(BdMemberCounsel bdMemberCounsel, String adminNo) throws Exception {
		BdMemberCounselMemo bdAdminMemo = new BdMemberCounselMemo();

		bdAdminMemo.setMemberCnslSeq(bdMemberCounsel.getMemberCnslSeq());
		bdAdminMemo.setRgsterNo(adminNo);
		bdAdminMemo.setMemoText(Message.getMessage("system.msg.escalation"));
		bdAdminMemo.setDelYn(Const.BOOLEAN_FALSE);

		bdMemberCounselMemoDao.insertAdminMemo(bdAdminMemo);
	}

	/**
	 * @Desc : 첨부파일 DB저장
	 * @Method Name : setAttachFile
	 * @Date : 2019. 4. 10.
	 * @Author : 신인철
	 * @param bdMemberCounsel
	 * @throws Exception
	 */
	public void setAttachFile(BdMemberCounsel bdMemberCounsel) throws Exception {
		BdMemberCounselAttachFile[] aswrAtchFiles = bdMemberCounsel.getAswrAtchFiles();
		if (aswrAtchFiles != null) {
			for (int i = 0; i < aswrAtchFiles.length; i++) {
				if (!(aswrAtchFiles[i].getAtchFileName() == null) && !("".equals(aswrAtchFiles[i].getAtchFileName()))) {
					aswrAtchFiles[i].setMemberCnslSeq(bdMemberCounsel.getMemberCnslSeq());
					aswrAtchFiles[i].setAtchFileGbnType(Const.ATCH_FILE_GBN_TYPE_A);
					bdInquiryService.insertAtchFile(aswrAtchFiles[i]);
				}
			}
		}
	}

	/**
	 * @Desc :BO용 미답변 건수 조회
	 * @Method Name : getNoAswrCounselListOfBO
	 * @Date : 2019. 4. 24.
	 * @Author : 신인철
	 * @return
	 * @throws Exception
	 */
	public List<BdMemberCounsel> getNoAswrCounselListOfBO() throws Exception {
		BdMemberCounsel bdMemberCounsel = new BdMemberCounsel();

		bdMemberCounsel.setCnslGbnCode(CommonCode.CNSL_GBN_CODE_INQUIRY);
		bdMemberCounsel.setAswrStatCode(CommonCode.ASWR_STAT_CODE_UN);

		return bdMemberCounselDao.selectNoAswrCounselList(bdMemberCounsel);
	}

	/**
	 * @Desc : PO용 미답변 건수 조회
	 * @Method Name : getNoAswrCounselListOfPO
	 * @Date : 2019. 4. 24.
	 * @Author : 신인철
	 * @param bdMemberCounsel
	 * @return
	 * @throws Exception
	 */
	public int getNoAswrCounselListOfPO(BdMemberCounsel bdMemberCounsel) throws Exception {
		return bdMemberCounselDao.selectNoAswrCounselListForPo(bdMemberCounsel);
	}

	/**
	 * @Desc : 고객의소리 답변 등록
	 * @Method Name : updateInquryNoFile
	 * @Date : 2019. 4. 12.
	 * @Author : 신인철
	 * @param bdMemberCounsel
	 * @return
	 * @throws Exception
	 */
	public void updateInquryNoFile(BdMemberCounsel bdMemberCounsel) throws Exception {
		MbMember memberInfo = memberService.getMember(bdMemberCounsel.getMemberNo());
		if (!UtilsText.equals(memberInfo.getLeaveYn(), Const.BOOLEAN_FALSE)) {
			throw new Exception("이미 탈퇴한 회원입니다.");
		}

		UserDetails user = LoginManager.getUserDetails();
		bdMemberCounsel.setAswrNo(user.getAdminNo());
		bdMemberCounsel.setAswrDtm(UtilsDate.getSqlTimeStamp());
		if (bdMemberCounsel.getAswrContText() != null) {
			bdMemberCounsel = bdInquiryService.aswrContentNewLineProcess(bdMemberCounsel);
		}

		bdMemberCounselDao.updatebdMemberCounsel(bdMemberCounsel);

		// 답변 등록 후 메세지 발송
		if (UtilsText.equals(CommonCode.ASWR_STAT_CODE_CM, bdMemberCounsel.getAswrStatCode())
				&& UtilsText.isBlank(bdMemberCounsel.getCheckAswrDtm())) {
			if (UtilsText.isNotBlank(memberInfo.getHdphnNoText())) {
				bdInquiryService.sendMsgNoTrx(bdMemberCounsel, memberInfo, CommonCode.CNSL_GBN_CODE_VOC);
			}
		}
		// 이메일 발송
		if (UtilsText.equals(CommonCode.ASWR_STAT_CODE_CM, bdMemberCounsel.getAswrStatCode())
				&& UtilsText.isBlank(bdMemberCounsel.getCheckAswrDtm())) {
			if (UtilsText.isNotBlank(memberInfo.getEmailAddrText())) {
				bdInquiryService.sendEmailNoTrx(bdMemberCounsel, memberInfo, CommonCode.CNSL_GBN_CODE_VOC);
			}
		}
	}

	/**
	 * @Desc : 첨부파일 등록
	 * @Method Name : insertAtchFile
	 * @Date : 2019. 3. 5.
	 * @Author : 신인철
	 * @param aswrAtchFiles
	 * @throws Exception
	 */
	public void insertAtchFile(BdMemberCounselAttachFile aswrAtchFiles) throws Exception {
		bdMemberCounselAttachFileDao.insertAtchFile(aswrAtchFiles);
	}

	/**
	 * @Desc : 상담첨부파일 삭제
	 * @Method Name : deleteCounselAtchFile
	 * @Date : 2019. 2. 21.
	 * @Author : 신인철
	 * @param bdMemberCounselAttachFile
	 * @throws Exception
	 */
	public void deleteCounselAtchFile(BdMemberCounselAttachFile bdMemberCounselAttachFile) throws Exception {
		bdMemberCounselAttachFileDao.deleteCounselAtchFile(bdMemberCounselAttachFile);
	}

	/**
	 * @Desc : 상담 첨부파일 업로드
	 * @Method Name : uploadAtchFile
	 * @Date : 2019. 2. 21.
	 * @Author : 신인철
	 * @param uploadFiles
	 * @return
	 * @throws Exception
	 */
	public BdMemberCounselAttachFile[] uploadAtchFile(FileUpload[] uploadFiles) throws Exception {
		List<BdMemberCounselAttachFile> bdMemberCounselAttachFiles = new ArrayList<BdMemberCounselAttachFile>();
		try {
			for (FileUpload uploadFile : uploadFiles) {
				if (uploadFile.isFileItem()) {
					String fileName = uploadFile.getOrgFileName();
					String hashPath = UtilsFile.getHashCodeDirectory(fileName);
					String uploadFileName = UtilsText.concat(UtilsFile.getUserUploadFilename()
							+ uploadFile.getOrgFileName().substring(uploadFile.getOrgFileName().lastIndexOf(".")));
					String path = UtilsText.concat(FILE_PATH, "/", hashPath);

					UtilsSftp.upload(path, uploadFileName, uploadFile.getMultiPartFile().getInputStream());

					BdMemberCounselAttachFile counselAttachFile = new BdMemberCounselAttachFile();

					counselAttachFile.setAtchFileName(fileName); // 고객 선택 파일 명

					// cdn 도메인 제외한 url
					counselAttachFile.setAtchFilePathText(UtilsText.concat("/", path, "/", uploadFileName));
					// 파일업로드 폴더 full url
					counselAttachFile
							.setAtchFileUrl(UtilsText.concat(Const.URL_IMG_HTTPS, "/", path, "/", uploadFileName));
					bdMemberCounselAttachFiles.add(counselAttachFile);
				} else {
					log.warn("파일 데이터가  존재 하지 않습니다.");
				}
			}
		} catch (Exception e) {
			log.error(e.toString());
			e.printStackTrace(System.err);
			throw new Exception("파일저장에 실패하였습니다.");
		}
		return bdMemberCounselAttachFiles.toArray(new BdMemberCounselAttachFile[bdMemberCounselAttachFiles.size()]);
	}

	/**
	 * @Desc : 나스 이미지 삭제
	 * @Method Name : deleteNasImage
	 * @Date : 2019. 4. 9.
	 * @Author : 신인철
	 * @param bdMemberAtchFiles
	 */
	public void deleteNasImage(BdMemberCounselAttachFile[] bdMemberAtchFiles) {
		for (int i = 0; i < bdMemberAtchFiles.length; i++) {
			String delPath = bdMemberAtchFiles[i].getAtchFilePathText();
			File deleFile = new File(delPath);
			if (deleFile.exists()) {
				deleFile.delete();
			}
		}
	}

	/**
	 * @Desc : 회원 상담 등록
	 * @Method Name : insertMemberCounsel
	 * @Date : 2019. 3. 4.
	 * @Author : 3TOP
	 * @param bdMemberCounsel
	 * @return
	 * @throws Exception
	 */
	public void insertMemberCounsel(BdMemberCounsel bdMemberCounsel) throws Exception {
		UserDetails user = LoginManager.getUserDetails();
		bdMemberCounsel.setAswrNo(user.getAdminNo());

		bdMemberCounselDao.insertMemberCounsel(bdMemberCounsel);
	}

	/************************************************************************************************************************************************************/
	/**
	 * @Desc :
	 * @Method Name : selectOrderiquiryArr
	 * @Date : 2019. 3. 8.
	 * @Author : flychani@3top.co.kr
	 * @param orderNos
	 * @return
	 */
	public List<BdMemberCounsel> selectOrderiquiryArr(String[] orderNos) throws Exception {
		List<BdMemberCounsel> list = bdMemberCounselDao.selectOrderiquiryArr(orderNos);
		return list;
	}

	/************************************************************************************************************************************************************/
	/**
	 * @Desc :BO용 미답변 건수 그룹별 조회
	 * @Method Name : getInquiryGroupCount
	 * @Date : 2019. 5. 8.
	 * @Author : 고웅환
	 * @return
	 * @throws Exception
	 */
	public List<BdMemberCounsel> getInquiryGroupCount(BdMemberCounsel bdMemberCounsel) throws Exception {
		return bdMemberCounselDao.selectInquiryGroupCount(bdMemberCounsel);
	}

	/**
	 * @Desc : BO용 고객의소리 미답변 건수 조회
	 * @Method Name : getNoAswrVoiceCustom
	 * @Date : 2019. 5. 16.
	 * @Author : 이재렬
	 * @param
	 * @throws Exception
	 */
	public int getNoAswrVoiceCustom(BdMemberCounsel bdMemberCounsel) throws Exception {
		return bdMemberCounselDao.selectNoAswrVoiceCustom(bdMemberCounsel);
	}

	/**
	 * @Desc : 대시보드 관리대상 업체 조회
	 * @Method Name : getManagementVndrList
	 * @Date : 2019. 10. 14.
	 * @Author : sic
	 * @return
	 * @throws Exception
	 */
	public List<BdMemberCounsel> getManagementVndrList() throws Exception {
		return bdMemberCounselDao.selectManagementVndrList();
	}

	/**
	 * @Desc : 탈퇴회원 상담 내역 저장
	 * @Method Name : setMemberCounselLeave
	 * @Date : 2020. 3. 24.
	 * @Author : sic
	 * @param counsel
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> setMemberCounselLeave(BdMemberCounsel counsel) throws Exception {
		Map<String, Object> rsMap = new HashMap<>();
		if (UtilsText.equals(counsel.getVndrAssignYn(), Const.BOOLEAN_TRUE)) {
			throw new Exception("탈퇴회원은 이관처리가 불가능합니다.");
		}
		try {
			UserDetails user = LoginManager.getUserDetails();
			counsel.setAswrNo(user.getAdminNo());
			counsel.setAswrDtm(UtilsDate.getSqlTimeStamp());
			if (counsel.getAswrContText() != null) {
				counsel = bdInquiryService.aswrContentNewLineProcess(counsel);
			}
			bdMemberCounselDao.updatebdMemberCounsel(counsel);

			rsMap.put("code", Const.BOOLEAN_TRUE);
		} catch (Exception e) {
			rsMap.put("code", Const.BOOLEAN_FALSE);
			rsMap.put("Message", e.getMessage());
		}

		return rsMap;
	}
	
	/**
	 * @Desc      	: 엑셀다운로드 관련 조회
	 * @Method Name : getCounselExcelList
	 * @Date  	  	: 2020. 5. 29.
	 * @Author    	: sic
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<BdMemberCounsel> getCounselExcelList(BdInquirySearchVO param) throws Exception{
		List<BdMemberCounsel> list = bdMemberCounselDao.selectCounselExcelList(param);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dateParam = new Date();
		
		list.forEach(o -> {
			dateParam.setTime(o.getInqryDtm().getTime());
			o.setInqryDtmString(dateFormat.format(dateParam));
			if(UtilsObject.isNotEmpty(o.getAswrDtm())) {
				dateParam.setTime(o.getAswrDtm().getTime());
				o.setAswrDtmString(dateFormat.format(dateParam));
			}
		});
		return list;
	}

}
