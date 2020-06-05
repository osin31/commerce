package kr.co.abcmart.bo.product.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.cmm.model.master.CmMessageTemplate;
import kr.co.abcmart.bo.cmm.service.MailService;
import kr.co.abcmart.bo.cmm.service.MessageService;
import kr.co.abcmart.bo.cmm.service.MsgTemplateService;
import kr.co.abcmart.bo.cmm.vo.MailTemplateVO;
import kr.co.abcmart.bo.cmm.vo.MessageVO;
import kr.co.abcmart.bo.member.model.master.MbMember;
import kr.co.abcmart.bo.member.service.MemberService;
import kr.co.abcmart.bo.product.model.master.BdProductInquiry;
import kr.co.abcmart.bo.product.model.master.BdProductInquiryMemo;
import kr.co.abcmart.bo.product.repository.master.BdProductInquiryDao;
import kr.co.abcmart.bo.product.repository.master.BdProductInquiryMemoDao;
import kr.co.abcmart.bo.product.vo.BdProductInquirySearchVO;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.util.UtilsObject;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 상품Q&A 서비스
 * @FileName : ProductInquiryService.java
 * @Project : abc.bo
 * @Date : 2019. 4. 9.
 * @Author : hsjhsj19
 */
@Slf4j
@Service
public class ProductInquiryService {

	@Autowired
	private BdProductInquiryDao productInquiryDao;

	@Autowired
	private BdProductInquiryMemoDao productInquiryMemoDao;

	@Autowired
	private MessageService messageService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private MsgTemplateService msgTemplateService;

	@Autowired
	private MailService mailService;

	@Autowired
	private ProductInquiryService inquiryService;

	/**
	 * @Desc : 상품 문의 목록 조회
	 * @Method Name : selectProductInquiry
	 * @Date : 2019. 4. 9.
	 * @Author : hsjhsj19
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<BdProductInquiry> selectProductInquiry(Pageable<BdProductInquirySearchVO, BdProductInquiry> pageable)
			throws Exception {
		Integer count = this.productInquiryDao.selectProductInquiryCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(this.productInquiryDao.selectProductInquiry(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 상품 문의 단건 조회
	 * @Method Name : searchProductInquiryByPrimaryKey
	 * @Date : 2019. 4. 9.
	 * @Author : hsjhsj19
	 * @param prdtInqrySeq
	 * @return
	 * @throws Exception
	 */
	public BdProductInquiry searchProductInquiryByPrimaryKey(String prdtInqrySeq) throws Exception {
		BdProductInquiry bdProductInquiry = new BdProductInquiry();
		bdProductInquiry.setPrdtInqrySeq(Integer.parseInt(prdtInqrySeq));
		return this.productInquiryDao.selectByPK(bdProductInquiry);
	}

	/**
	 * @Desc : 상품문의메모 목록 조회
	 * @Method Name : searchProductInquiryMemo
	 * @Date : 2019. 4. 9.
	 * @Author : hsjhsj19
	 * @param inquiry
	 * @return
	 * @throws Exception
	 */
	public List<BdProductInquiryMemo> searchProductInquiryMemo(BdProductInquiry inquiry) throws Exception {
		BdProductInquiryMemo bdProductInquiryMemo = new BdProductInquiryMemo();
		bdProductInquiryMemo.setPrdtInqrySeq(inquiry.getPrdtInqrySeq());
		bdProductInquiryMemo.setDelYn("N");
		return this.productInquiryMemoDao.searchProductInquiryMemo(bdProductInquiryMemo);
	}

	/**
	 * @Desc : [서비스 요쳥] 상품Q&A 답변, 미답변 상태별 건수(최근2개월 기준)
	 * @Method Name : getAswrCount
	 * @Date : 2019. 4. 10.
	 * @Author : hsjhsj19
	 * @return
	 */
	public Map<String, Object> getAswrCount(String memberNo) throws Exception {
		Map<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("memberNo", memberNo);
		hashMap.put("aswrStatCodeCm", CommonCode.ASWR_STAT_CODE_CM);
		hashMap.put("aswrStatCodeHd", CommonCode.ASWR_STAT_CODE_HD);
		hashMap.put("aswrStatCodeUn", CommonCode.ASWR_STAT_CODE_UN);

		return this.productInquiryDao.getAswrCount(hashMap);
	}

	/**
	 * @Desc : 상품 Q&A 저장
	 * @Method Name : updateProductInquiry
	 * @Date : 2019. 4. 10.
	 * @Author : hsjhsj19
	 * @param bdProductInquiry
	 * @return
	 * @throws Exception
	 */
	public int updateProductInquiry(BdProductInquiry bdProductInquiry) throws Exception {

		BdProductInquiry searchInquiry = this.searchProductInquiryByPrimaryKey("" + bdProductInquiry.getPrdtInqrySeq());

		bdProductInquiry.setPrdtNo(searchInquiry.getPrdtNo());
		bdProductInquiry.setMemberNo(searchInquiry.getMemberNo());

		// 세션 정보 호출
		UserDetails user = LoginManager.getUserDetails();
		Timestamp time = new Timestamp(new Date().getTime());
		bdProductInquiry.setAswrDtm(time);
		bdProductInquiry.setAswrNo(user.getAdminNo());

		int cnt = this.productInquiryDao.updateWithoutPK(bdProductInquiry);

		// 최초 답변저장 시 고객에게 알림톡, 이메일 전송
		if (UtilsObject.isEmpty(searchInquiry.getAswrNo())
				&& CommonCode.ASWR_STAT_CODE_UN.equals(searchInquiry.getAswrStatCode())) {
			MbMember member = this.memberService.getMember(searchInquiry.getMemberNo());

			if (UtilsText.isNotBlank(member.getHdphnNoText())) {
				inquiryService.aswrSendMessageNoTrx(member, bdProductInquiry, searchInquiry);
				// SMS발송여부
				bdProductInquiry.setSmsSendYn(Const.BOOLEAN_TRUE);
			}
			if (UtilsText.isNotBlank(member.getEmailAddrText())) {
				inquiryService.aswrSendEmailNoTrx(member, bdProductInquiry, searchInquiry);
				// 이메일 발송여부
				bdProductInquiry.setEmailSendYn(Const.BOOLEAN_TRUE);
			}
			cnt += this.productInquiryDao.updateWithoutPK(bdProductInquiry);

		}

		return cnt;
	}

	/**
	 * @Desc : 상품 Q&A 답변 메시지 발송
	 * @Method Name : aswrSendMessageNoTrx
	 * @Date : 2020. 2. 12.
	 * @Author : sic
	 * @param member
	 * @param bdProductInquiry
	 * @param searchInquiry
	 */
	public void aswrSendMessageNoTrx(MbMember member, BdProductInquiry bdProductInquiry,
			BdProductInquiry searchInquiry) {

		Map<String, String> map = new HashMap<>();
		MessageVO messageVO = new MessageVO();
		String siteNo = searchInquiry.getSiteNo();

		try {
			messageVO.setSiteNo(siteNo);
			// 발신자
			messageVO.setSndrName(Const.SYS_SENDER_MESSAGE_NAME);
			// 발신번호
			messageVO.setSendTelNoText(Const.SYS_SENDER_MESSAGE_NUMBER);
			// 즉시발송여부
			messageVO.setReal(true);
			// 수신자명
			messageVO.setRcvrName(member.getMemberName());

			// 수신번호
			messageVO.setRecvTelNoText(member.getHdphnNoText());
			// 수신이메일

			String linkUrl = "/product?prdtNo=" + searchInquiry.getPrdtNo() + "#productQna";
			// String linkUrl = "/mypage/shopping/notebook/product-inquiry";
			// 통합몰
			linkUrl = UtilsText.concat(Const.SERVICE_DOMAIN_ART_MO, linkUrl);
			// 메세지 번호
			messageVO.setMesgId("ART1014");

			CmMessageTemplate cmMessageTemplate = msgTemplateService.getMessageTemplateByMesgId(messageVO.getMesgId());

			// ${수신자명}
			map.put("memberName", member.getMemberName());
			// ${랜딩URL}
			map.put("linkUrl", linkUrl);
			// 알림톡/SMS 발송
			messageVO.setMesgContText(cmMessageTemplate.getMesgContText());
			messageVO.setMessageTemplateModel(map);
			messageService.setSendMessageProcess(messageVO);
		} catch (Exception e) {
			log.error("상품 Q&A 문자발송에 실패하였습니다. :{}", e);
		}
	}

	/**
	 * @Desc : 상품 Q&A 이메일 발송
	 * @Method Name : aswrSendEmailNoTrx
	 * @Date : 2020. 2. 12.
	 * @Author : sic
	 * @param member
	 * @param bdProductInquiry
	 * @param searchInquiry
	 */
	public void aswrSendEmailNoTrx(MbMember member, BdProductInquiry bdProductInquiry, BdProductInquiry searchInquiry) {
		Map<String, String> map = new HashMap<>();
		MailTemplateVO mailTempVO = new MailTemplateVO();

		try {
			String linkUrl = "/product?prdtNo=" + searchInquiry.getPrdtNo() + "#productQna";
			// String linkUrl = "/mypage/shopping/notebook/product-inquiry";
			// 통합몰
			linkUrl = UtilsText.concat(Const.SERVICE_DOMAIN_ART_FO, linkUrl);
			// 이메일 번호
			mailTempVO.setMailTemplateId("EC-04003");

			mailTempVO.setReceiverName(member.getMemberName());
			mailTempVO.setReceiverEmail(member.getEmailAddrText());

			// ${문의내용}
			map.put("memberName", member.getMemberName());
			map.put("inqryContText", searchInquiry.getInqryContText());
			// ${답변내용}
			map.put("aswrContText", bdProductInquiry.getAswrContText());
			// ${답변일}
			map.put("aswrDtm", "" + new SimpleDateFormat("yyyy년 MM월 dd일").format(bdProductInquiry.getAswrDtm()));
			// ${abcUrl}
			map.put("abcUrl", Const.SERVICE_DOMAIN_ART_FO);
			// 메일 발송
			mailTempVO.setMailTemplateModel(map);
			mailService.sendMail(mailTempVO);
		} catch (Exception e) {
			log.error("상품 Q&A 메일발송에 실패하였습니다. :{}", e);
		}
	}

	/**
	 * @Desc : 상품 Q&A 삭제
	 * @Method Name : deleteProductInquiry
	 * @Date : 2019. 4. 10.
	 * @Author : hsjhsj19
	 * @param bdProductInquiry
	 * @return
	 * @throws Exception
	 */
	public int deleteProductInquiry(BdProductInquiry bdProductInquiry) throws Exception {
		BdProductInquiryMemo bdProductInquiryMemo = new BdProductInquiryMemo();
		bdProductInquiry.setPrdtInqrySeq(bdProductInquiry.getPrdtInqrySeq());
		this.productInquiryMemoDao.delete(bdProductInquiryMemo);
		return this.productInquiryDao.delete(bdProductInquiry);
	}

	/**
	 * @Desc : 상품 Q&A 메모 저장
	 * @Method Name : insertProductInquiryMemo
	 * @Date : 2019. 4. 10.
	 * @Author : hsjhsj19
	 * @param bdProductInquiryMemo
	 * @return
	 * @throws Exception
	 */
	public int insertProductInquiryMemo(BdProductInquiryMemo bdProductInquiryMemo) throws Exception {
		// 세션 정보 호출
		UserDetails user = LoginManager.getUserDetails();
		bdProductInquiryMemo.setRgstDtm(new Timestamp(new Date().getTime()));
		bdProductInquiryMemo.setRgsterNo(user.getAdminNo());

		return this.productInquiryMemoDao.insertWithoutPrdtInqryMemoSeq(bdProductInquiryMemo);
	}

	/**
	 * @Desc : 상품 Q&A 메모 삭제
	 * @Method Name : deleteProductInquiryMemo
	 * @Date : 2019. 4. 10.
	 * @Author : hsjhsj19
	 * @param bdProductInquiryMemo
	 * @return
	 * @throws Exception
	 */
	public int deleteProductInquiryMemo(BdProductInquiryMemo bdProductInquiryMemo) throws Exception {
		bdProductInquiryMemo.setDelYn("Y");
		return this.productInquiryMemoDao.update(bdProductInquiryMemo);
	}

	/**
	 * @Desc : 상품Q&A 메모 목록
	 * @Method Name : getMemoList
	 * @Date : 2019. 4. 10.
	 * @Author : hsjhsj19
	 * @param bdProductInquiryMemo
	 * @return
	 * @throws Exception
	 */
	public List<BdProductInquiryMemo> getMemoList(BdProductInquiryMemo bdProductInquiryMemo) throws Exception {
		bdProductInquiryMemo.setDelYn("N");
		return this.productInquiryMemoDao.searchProductInquiryMemo(bdProductInquiryMemo);
	}

	/**
	 * @Desc : 미답변 갯수 조회
	 * @Method Name : getProductInquiryGroupCount
	 * @Date : 2019. 8. 20.
	 * @Author : hsjhsj19
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getProductInquiryGroupCount() throws Exception {
		BdProductInquirySearchVO bdProductInquiry = new BdProductInquirySearchVO();

		bdProductInquiry.setVndrNo(LoginManager.getUserDetails().getVndrNo());

		return this.productInquiryDao.selectProductInquiryGroupCount(bdProductInquiry);
	}

	/**
	 * @Desc : 상품문의 답변현황
	 * @Method Name : getMemberInquiryStats
	 * @Date : 2019. 8. 20.
	 * @Author : hsjhsj19
	 * @param inquiry
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getMemberInquiryStats(BdProductInquiry inquiry) throws Exception {
		return this.productInquiryDao.selectMemberInquiryStats(inquiry);
	}

}
