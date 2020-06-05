package kr.co.abcmart.bo.board.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.board.model.master.BdBulkBuyInquiry;
import kr.co.abcmart.bo.board.model.master.BdBulkBuyInquiryMemo;
import kr.co.abcmart.bo.board.repository.master.BdBulkBuyInquiryDao;
import kr.co.abcmart.bo.board.repository.master.BdBulkBuyInquiryMemoDao;
import kr.co.abcmart.bo.member.service.MemberService;
import kr.co.abcmart.bo.system.model.master.SySite;
import kr.co.abcmart.bo.system.service.SiteService;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;

@Service
public class BdBulkBuyInquiryService {

	@Autowired
	private BdBulkBuyInquiryDao bdBulkbuyDao;

	@Autowired
	private BdBulkBuyInquiryService bdBulkBuyService;

	@Autowired
	private BdBulkBuyInquiryMemoDao bdBulkBuyMemoDao;

	@Autowired
	private MemberService memberService;

	@Autowired
	private SiteService siteService;

	/**
	 * @Desc : 검색 조건에 맞는 문의 정보를 조회한다.
	 * @Method Name : getInqueryReadList
	 * @Date : 2019. 3. 13.
	 * @Author : 신인철
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<BdBulkBuyInquiry> getBulkBuyReadList(Pageable<BdBulkBuyInquiry, BdBulkBuyInquiry> pageable)
			throws Exception {

		int totalCount = bdBulkbuyDao.selectBulkBuyistCount(pageable);

		pageable.setTotalCount(totalCount);

		if (totalCount > 0) {
			pageable.setContent(bdBulkbuyDao.selectBulkBuylList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 대량구매문의 상세보기
	 * @Method Name : getInquiryDetail
	 * @Date : 2019. 3. 13.
	 * @Author : 신인철
	 * @param bdBulkBuyInquiry
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getBulkBuyDetail(BdBulkBuyInquiry bdBulkBuyInquiry) throws Exception {
		Map<String, Object> rsMap = new HashMap<>();

		BdBulkBuyInquiry bulkbuyResult = bdBulkbuyDao.selectBulkBuyDetail(bdBulkBuyInquiry);

		BdBulkBuyInquiryMemo bulkbuyMemo = new BdBulkBuyInquiryMemo();
		bulkbuyMemo.setBlkBuyInqrySeq(bdBulkBuyInquiry.getBlkBuyInqrySeq());

		rsMap.put("bdBulkBuyDetail", bulkbuyResult);
		rsMap.put("memberInfo", memberService.getMember(bulkbuyResult.getMemberNo()));
		rsMap.put("bdBulkBuyMemo", bdBulkBuyService.getBdBulkBuyMemoList(bulkbuyMemo));

		return rsMap;
	}

	/**
	 * @Desc : 대량구매문의 메모 리스트
	 * @Method Name : getBdBulkBuyMemoList
	 * @Date : 2019. 3. 13.
	 * @Author : 신인철
	 * @param bdBulkBuyInquiryMemo
	 * @return
	 * @throws Exception
	 */
	public List<BdBulkBuyInquiryMemo> getBdBulkBuyMemoList(BdBulkBuyInquiryMemo bdBulkBuyInquiryMemo) throws Exception {
		return bdBulkBuyMemoDao.selectBulkBuyMemoList(bdBulkBuyInquiryMemo);
	}

	/**
	 * @Desc : 대량구매 문의 메모 작성
	 * @Method Name : setBulkBuyInqryMemo
	 * @Date : 2019. 3. 13.
	 * @Author : 신인철
	 * @param bdBulkBuyInquiryMemo
	 * @return
	 * @throws Exception
	 */
	public BdBulkBuyInquiryMemo setBulkBuyInqryMemo(BdBulkBuyInquiryMemo bdBulkBuyInquiryMemo) throws Exception {
		UserDetails user = LoginManager.getUserDetails();
		bdBulkBuyInquiryMemo.setRgsterNo(user.getAdminNo());
		bdBulkBuyInquiryMemo.setDelYn(Const.BOOLEAN_FALSE);

		bdBulkBuyMemoDao.insertBulkBuyMemo(bdBulkBuyInquiryMemo);

		BdBulkBuyInquiryMemo resultVO = bdBulkBuyMemoDao.selectBulkBuyMemo(bdBulkBuyInquiryMemo);

		return resultVO;
	}

	/**
	 * @Desc : 대량구매 문의 메모 삭제
	 * @Method Name : deleteBulkBuyInqryMemo
	 * @Date : 2019. 3. 13.
	 * @Author : 신인철
	 * @param bdBulkBuyInquiryMemo
	 * @throws Exception
	 */
	public void deleteBulkBuyMemo(BdBulkBuyInquiryMemo bdBulkBuyInquiryMemo) throws Exception {
		bdBulkBuyMemoDao.delete(bdBulkBuyInquiryMemo);

	}

	/************************************************************************************************************************************************************/
	/**
	 * @Desc : BO 대시보드 단체주문 그룹별 건수조회
	 * @Method Name : getBulkBuyGroupCount
	 * @Date : 2019. 5. 8.
	 * @Author : 고웅환
	 * @param
	 * @throws Exception
	 */
	public List<BdBulkBuyInquiry> getBulkBuyGroupCount() throws Exception {
		return bdBulkbuyDao.selectBulkBuyGroupCount();

	}

	/**
	 * @Desc : 단체주문 메인 사이트 번호
	 * @Method Name : getBulkBuyMainSiteNo
	 * @Date : 2019. 5. 9.
	 * @Author : 고웅환
	 * @param
	 * @throws Exception
	 */
	public List<SySite> getBulkBuyMainSiteNo() throws Exception {
		return siteService.getSiteList();
	}

}
