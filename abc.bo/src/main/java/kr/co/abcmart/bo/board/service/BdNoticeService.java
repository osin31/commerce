package kr.co.abcmart.bo.board.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.board.model.master.BdNotice;
import kr.co.abcmart.bo.board.repository.master.BdNoticeDao;
import kr.co.abcmart.bo.system.service.CommonCodeService;
import kr.co.abcmart.bo.system.service.SiteService;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;

@Service
public class BdNoticeService {

	@Autowired
	BdNoticeDao bdNoticeDao;

	@Autowired
	CommonCodeService commonCodeService;

	@Autowired
	BdNoticeService bdNoticeService;

	@Autowired
	SiteService siteService;

	/**
	 * @Desc : 공지사항관리 검색 Form 기초 데이터를 조회
	 * @Method Name : getInquerySearchForm
	 * @Date : 2019. 2. 11.
	 * @Author : 신인철
	 * @param codeFields
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getNoticeSearchForm(String codeField) throws Exception {
		Map<String, Object> rtnVal = new HashMap<String, Object>();

		rtnVal.put("siteList", siteService.getSiteList());
		rtnVal.put("notcTypeCodeList", commonCodeService.getCodeNoName(codeField));

		return rtnVal;
	}

	/**
	 * @Desc : 공지사항 등록
	 * @Method Name : setNotice
	 * @Date : 2019. 3. 1.
	 * @Author : 신인철
	 * @param param
	 * @throws Exception
	 */
	public Map<String, Object> setNotice(BdNotice param) throws Exception {
		Map<String, Object> rsMap = new HashMap<>();
		try {
			UserDetails user = LoginManager.getUserDetails();
			param.setRgsterNo(user.getAdminNo());
			param.setModerNo(user.getAdminNo());

			bdNoticeDao.insertNotice(param);
			rsMap.put("result", 1);
		} catch (Exception e) {
			rsMap.put("result", 0);
			rsMap.put("Message", e.toString());

		}
		return rsMap;
	}

	/**
	 * @Desc : 공지사항 그리드 호출
	 * @Method Name : getNoticeGridList
	 * @Date : 2019. 3. 1.
	 * @Author : 신인철
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<BdNotice> getNoticeGridList(Pageable<BdNotice, BdNotice> pageable) throws Exception {
		int totalCount = bdNoticeDao.selectBdNoticeCount(pageable);
		pageable.setTotalCount(totalCount);
		if (totalCount > 0) {
			pageable.setContent(bdNoticeDao.selectBdNoticeList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 상단공지 공통 개수 조회
	 * @Method Name : getTopNotcCount
	 * @Date : 2019. 3. 11.
	 * @Author : 신인철
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getTopNotcTotalCount() throws Exception {
		Map<String, Object> rsMap = new HashMap<String, Object>();

		try {
			int artCount = bdNoticeDao.selectTopNoticeARTCount();
			int otsCount = bdNoticeDao.selectTopNoticeOTSCount();

			rsMap.put("artCount", artCount);
			rsMap.put("otsCount", otsCount);
			rsMap.put("result", 1);
		} catch (Exception e) {
			rsMap.put("result", 0);
			rsMap.put("Message", e.toString());
		}

		return rsMap;
	}

	/**
	 * @Desc : 상단공지 개별 개수 조회
	 * @Method Name : getTopNotcSiteCount
	 * @Date : 2019. 3. 26.
	 * @Author : 신인철
	 * @param bdNotice
	 * @return
	 * @throws Exception
	 */
	public int getTopNotcSiteCount(BdNotice bdNotice) throws Exception {
		int count;
		if (UtilsText.equals(bdNotice.getSiteNo(), Const.SITE_NO_ART)) {
			count = bdNoticeDao.selectTopNoticeARTCount();
		} else {
			count = bdNoticeDao.selectTopNoticeOTSCount();
		}

		return count;
	}

	/**
	 * @Desc : 공지사항 상세보기 페이지
	 * @Method Name : getNoticeDetailForm
	 * @Date : 2019. 3. 1.
	 * @Author : 신인철
	 * @param codeField
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getNoticeDetailForm(String codeField, BdNotice param) throws Exception {
		Map<String, Object> rtnVal = new HashMap<String, Object>();
		BdNotice bdNotice = bdNoticeService.getNoticeDetail(param);

		rtnVal.put("siteList", siteService.getSiteList());
		rtnVal.put("notcTypeCodeList", commonCodeService.getCodeNoName(codeField));
		rtnVal.put("bdNotice", bdNotice);

		return rtnVal;
	}

	/**
	 * @Desc : 공지사항 상세보기
	 * @Method Name : getNoticeDetail
	 * @Date : 2019. 3. 1.
	 * @Author : 신인철
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public BdNotice getNoticeDetail(BdNotice param) throws Exception {
		return bdNoticeDao.selectNoticeDetail(param);
	}

	/**
	 * @Desc : 공지사항 상세 수정
	 * @Method Name : updateNoticeDetail
	 * @Date : 2019. 3. 1.
	 * @Author : 신인철
	 * @param bdNotice
	 * @return
	 * @throws Exception
	 */
	public int updateNoticeDetail(BdNotice bdNotice) throws Exception {
		UserDetails user = LoginManager.getUserDetails();
		bdNotice.setModerNo(user.getAdminNo());

		return bdNoticeDao.updateBdNotice(bdNotice);
	}

	/**
	 * @Desc : 공지사항 삭제
	 * @Method Name : deleteNoticeDetail
	 * @Date : 2019. 3. 11.
	 * @Author : 신인철
	 * @param bdNotice
	 * @throws Exception
	 */
	public void deleteNoticeDetail(BdNotice bdNotice) throws Exception {
		bdNoticeDao.delete(bdNotice);

	}

}
