package kr.co.abcmart.bo.display.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.display.model.master.DpDisplayPage;
import kr.co.abcmart.bo.display.model.master.DpDisplayPageCorner;
import kr.co.abcmart.bo.display.model.master.DpDisplayTemplateCorner;
import kr.co.abcmart.bo.display.repository.master.DpDisplayPageCornerDao;
import kr.co.abcmart.bo.display.repository.master.DpDisplayPageCornerNameDao;
import kr.co.abcmart.bo.display.repository.master.DpDisplayPageCornerSetDao;
import kr.co.abcmart.bo.display.repository.master.DpDisplayPageDao;
import kr.co.abcmart.bo.display.repository.master.DpDisplayTemplateCornerDao;
import kr.co.abcmart.bo.display.vo.DisplayContentsPopupVO;
import kr.co.abcmart.bo.system.service.SiteService;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;
import kr.co.abcmart.util.UtilsText;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DisplayPageService {

	@Autowired
	private DpDisplayPageDao dpDisplayPageDao;

	@Autowired
	private DpDisplayPageCornerDao dpDisplayPageCornerDao;

	@Autowired
	private DpDisplayTemplateCornerDao dpDisplayTemplateCornerDao;

	@Autowired
	private DpDisplayPageCornerNameDao dpDisplayPageCornerNameDao;

	@Autowired
	private DpDisplayPageCornerSetDao dpDisplayPageCornerSetDao;

	@Autowired
	private SiteService siteService;

	/**
	 *
	 * @Desc : 전시 페이지 목록 조회
	 * @Method Name : getDpDisplayPageList
	 * @Date : 2019. 3. 13.
	 * @Author : 이가영
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	public List<DpDisplayPage> getDpDisplayPageList(DpDisplayPage params) throws Exception {

		List<DpDisplayPage> list = dpDisplayPageDao.selectDpDisplayPageList(params);

		return list;
	}

	/**
	 * @Desc : 전시 페이지 목록 조회
	 * @Method Name : getDisplayPageList
	 * @Date : 2019. 12. 12.
	 * @Author : sic
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getDisplayPageList(DpDisplayPage params) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		params.setSiteNo(siteService.getSiteNo(params.getChnnlNo()));

		List<DpDisplayPage> pageData = dpDisplayPageDao.selectDisplayPageList(params);
		List<DpDisplayPage> data = new LinkedList<>();

		DpDisplayPage rootPage = new DpDisplayPage();
		switch (params.getChnnlNo()) {
		case Const.CHNNL_NO_ART:
			rootPage.setDispPageName(Const.CHNNL_TEXT_ART);
			break;
		case Const.CHNNL_NO_ABCMART:
			rootPage.setDispPageName(Const.CHNNL_TEXT_ABCMART);
			break;
		case Const.CHNNL_NO_GRANDSTAGE:
			rootPage.setDispPageName(Const.CHNNL_TEXT_GRANDSTAGE);
			break;
		case Const.CHNNL_NO_OTS:
			rootPage.setDispPageName(Const.CHNNL_TEXT_OTS);
			break;
		case Const.CHNNL_NO_KIDS:
			rootPage.setDispPageName(Const.CHNNL_TEXT_KIDS);
			break;
		}
		rootPage.setSortSeq("1");
		rootPage.setLevel("0");
		data.add(rootPage);

		pageData.forEach(o -> {
			data.add(o);
		});

		resultMap.put("Total", 1);
		resultMap.put("Data", data);

		return resultMap;
	}

	/**
	 *
	 * @Desc : 전시 페이지 등록
	 * @Method Name : getStandardCategoryList
	 * @Date : 2019. 3. 13.
	 * @Author : 이가영
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public void insertDpDisplayPage(DpDisplayPage params) throws Exception {

		UserDetails user = LoginManager.getUserDetails();
		params.setRgsterNo(user.getAdminNo());
		params.setModerNo(user.getAdminNo());

		dpDisplayPageDao.insertDpDisplayPage(params);
		this.insertDpPageCorner(params);

	}

	/**
	 *
	 * @Desc : 전시 페이지 수정
	 * @Method Name : updateDpDisplayPage
	 * @Date : 2019. 3. 13.
	 * @Author : 이가영
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@CacheEvict(value = { "displayPageService.getPageInfo" }, allEntries = true)
	public void updateDpDisplayPage(DpDisplayPage params) throws Exception {

		UserDetails user = LoginManager.getUserDetails();
		params.setModerNo(user.getAdminNo());

		DpDisplayPage original = dpDisplayPageDao.selectByPrimaryKey(params);

		dpDisplayPageDao.updateDpDisplayPage(params);

		this.updateDpPageCorner(params, original);
	}

	/**
	 *
	 * @Desc :전시 페이지 코너 등록
	 * @Method Name : insertDpPageCorner
	 * @Date : 2019. 3. 14.
	 * @Author : 이가영
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public void insertDpPageCorner(DpDisplayPage parameter) throws Exception {

		DpDisplayPageCorner corner = new DpDisplayPageCorner();
		DpDisplayTemplateCorner param = new DpDisplayTemplateCorner();
		corner.setDispPageNo(parameter.getDispPageNo());

		DisplayContentsPopupVO vo = new DisplayContentsPopupVO();
		vo.setDispPageNo(parameter.getDispPageNo());
		dpDisplayPageCornerNameDao.deleteDisplayPageCornerName(vo);
		dpDisplayPageCornerSetDao.deleteDisplayPageCornerSet(vo);

		dpDisplayPageCornerDao.deleteDpPageCorner(corner);

		// pc
		if (!UtilsText.isBlank(parameter.getPcDispTmplNo())) {
			corner.setDispTmplNo(parameter.getPcDispTmplNo());

			param.setDispTmplNo(parameter.getPcDispTmplNo());
			List<DpDisplayTemplateCorner> list1 = dpDisplayTemplateCornerDao.select(param);

			for (DpDisplayTemplateCorner item : list1) {

				corner.setDispTmplCornerSeq(item.getDispTmplCornerSeq());
				dpDisplayPageCornerDao.insert(corner);
			}
		}

		// mobile
		if (!UtilsText.isBlank(parameter.getMobileDispTmplNo())) {
			corner.setDispTmplNo(parameter.getMobileDispTmplNo());

			param.setDispTmplNo(parameter.getMobileDispTmplNo());
			List<DpDisplayTemplateCorner> list2 = dpDisplayTemplateCornerDao.select(param);

			for (DpDisplayTemplateCorner item : list2) {

				corner.setDispTmplCornerSeq(item.getDispTmplCornerSeq());
				dpDisplayPageCornerDao.insert(corner);
			}
		}
	}

	/**
	 *
	 * @Desc :전시 페이지 코너 수정
	 * @Method Name : updateDpPageCorner
	 * @Date : 2019. 3. 14.
	 * @Author : 이가영
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public void updateDpPageCorner(DpDisplayPage parameter, DpDisplayPage original) throws Exception {

		DpDisplayPageCorner corner = new DpDisplayPageCorner();
		DpDisplayTemplateCorner param = new DpDisplayTemplateCorner();
		corner.setDispPageNo(parameter.getDispPageNo());

		DisplayContentsPopupVO vo = new DisplayContentsPopupVO();
		vo.setDispPageNo(parameter.getDispPageNo());

		// pc
		corner.setDispTmplNo(original.getPcDispTmplNo());
		vo.setDispTmplNo(original.getPcDispTmplNo());
		if (!UtilsText.isBlank(parameter.getPcDispTmplNo())
				&& !(UtilsText.equals(parameter.getPcDispTmplNo(), original.getPcDispTmplNo()))) {

			// 기존 전시 페이지 코너 데이터 삭제
			if (!UtilsText.isBlank(original.getPcDispTmplNo())) {
				dpDisplayPageCornerNameDao.deleteDisplayPageCornerName(vo);
				dpDisplayPageCornerSetDao.deleteDisplayPageCornerSet(vo);
				dpDisplayPageCornerDao.deleteDpPageCorner(corner);
			}

			param.setDispTmplNo(parameter.getPcDispTmplNo());
			List<DpDisplayTemplateCorner> list1 = dpDisplayTemplateCornerDao.select(param);

			corner.setDispTmplNo(parameter.getPcDispTmplNo());

			for (DpDisplayTemplateCorner item : list1) {

				corner.setDispTmplCornerSeq(item.getDispTmplCornerSeq());
				dpDisplayPageCornerDao.insert(corner);
			}
		} else if (UtilsText.isBlank(parameter.getPcDispTmplNo()) && !UtilsText.isBlank(original.getPcDispTmplNo())) {

			dpDisplayPageCornerNameDao.deleteDisplayPageCornerName(vo);
			dpDisplayPageCornerSetDao.deleteDisplayPageCornerSet(vo);
			dpDisplayPageCornerDao.deleteDpPageCorner(corner);
		}

		// mobile
		corner.setDispTmplCornerSeq(null);
		corner.setDispTmplNo(original.getMobileDispTmplNo());
		vo.setDispTmplNo(original.getMobileDispTmplNo());
		if (!UtilsText.isBlank(parameter.getMobileDispTmplNo())
				&& !(UtilsText.equals(parameter.getMobileDispTmplNo(), original.getMobileDispTmplNo()))) {

			if (!(UtilsText.isBlank(original.getMobileDispTmplNo()))) {
				dpDisplayPageCornerNameDao.deleteDisplayPageCornerName(vo);
				dpDisplayPageCornerSetDao.deleteDisplayPageCornerSet(vo);
				dpDisplayPageCornerDao.deleteDpPageCorner(corner);
			}

			param.setDispTmplNo(parameter.getMobileDispTmplNo());
			List<DpDisplayTemplateCorner> list2 = dpDisplayTemplateCornerDao.select(param);

			corner.setDispTmplNo(parameter.getMobileDispTmplNo());

			for (DpDisplayTemplateCorner item : list2) {

				corner.setDispTmplCornerSeq(item.getDispTmplCornerSeq());
				dpDisplayPageCornerDao.insert(corner);
			}
		} else if (UtilsText.isBlank(parameter.getMobileDispTmplNo())
				&& !(UtilsText.isBlank(original.getMobileDispTmplNo()))) {

			dpDisplayPageCornerNameDao.deleteDisplayPageCornerName(vo);
			dpDisplayPageCornerSetDao.deleteDisplayPageCornerSet(vo);
			dpDisplayPageCornerDao.deleteDpPageCorner(corner);
		}
	}

	/**
	 *
	 * @Desc : 카테고리 코너 리스트 조회
	 * @Method Name : getDpCategoryCornerList
	 * @Date : 2019. 3. 14.
	 * @Author : SANTA
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<DpDisplayTemplateCorner> getDpDisplayPageCornerList(
			Pageable<DpDisplayPageCorner, DpDisplayTemplateCorner> pageable) throws Exception {

		int count = dpDisplayPageCornerDao.selectDpPageCornerListCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setRowsPerPage(count);
			pageable.setContent(dpDisplayPageCornerDao.selectDpPageCornerList(pageable));
		}
		return pageable.getPage();
	}

	/**
	 *
	 * @Desc : 전시 페이지에서 사용 중인 전시 템플릿 수 조회
	 * @Method Name : getDpDisplayPageCount
	 * @Date : 2019. 3. 15.
	 * @Author : lee
	 * @param dispTmplNo
	 * @return
	 * @throws Exception
	 */
	public int getDpDisplayPageCount(String dispTmplNo) throws Exception {

		int count = dpDisplayPageDao.selectDpTmplCountInDisplayPage(dispTmplNo);

		return count;

	}

	/**
	 * @Desc : 전시 콘텐츠관리 그리드 전시여부 수정
	 * @Method Name : updateDispCornerDispYn
	 * @Date : 2019. 12. 16.
	 * @Author : sic
	 * @param dpDisplayPageCorner
	 * @throws Exception
	 */
	@CacheEvict(value = { "displayPageService.getPageInfo" }, allEntries = true)
	public void updateDispCornerDispYn(DpDisplayPageCorner dpDisplayPageCorner) throws Exception {
		dpDisplayPageCornerDao.updateDispYn(dpDisplayPageCorner);
	}

	/**
	 * @Desc : 채널에 맞는 uri 불러오기
	 * @Method Name : getCurrentPreviewUrl
	 * @Date : 2019. 12. 27.
	 * @Author : sic
	 * @param chnnlNo
	 * @return
	 * @throws Exception
	 */
	public String getCurrentPreviewUrl(String chnnlNo, String deviceCode) throws Exception {
		String previewUri = "";
		if (deviceCode.equals(CommonCode.DEVICE_PC)) {
			switch (chnnlNo) {
			case Const.CHNNL_NO_ART:
				previewUri = Const.SERVICE_DOMAIN_ART_FO;
				break;
			case Const.CHNNL_NO_ABCMART:
				previewUri = Const.SERVICE_DOMAIN_ABC_FO;
				break;
			case Const.CHNNL_NO_GRANDSTAGE:
				previewUri = Const.SERVICE_DOMAIN_GS_FO;
				break;
			case Const.CHNNL_NO_KIDS:
				previewUri = Const.SERVICE_DOMAIN_KIDS_FO;
				break;
			case Const.CHNNL_NO_OTS:
				previewUri = Const.SERVICE_DOMAIN_OTS_FO;
				break;
			}
		} else {
			switch (chnnlNo) {
			case Const.CHNNL_NO_ART:
				previewUri = Const.SERVICE_DOMAIN_ART_MO;
				break;
			case Const.CHNNL_NO_ABCMART:
				previewUri = Const.SERVICE_DOMAIN_ABC_MO;
				break;
			case Const.CHNNL_NO_GRANDSTAGE:
				previewUri = Const.SERVICE_DOMAIN_GS_MO;
				break;
			case Const.CHNNL_NO_KIDS:
				previewUri = Const.SERVICE_DOMAIN_KIDS_MO;
				break;
			case Const.CHNNL_NO_OTS:
				previewUri = Const.SERVICE_DOMAIN_OTS_MO;
				break;
			}
		}

		return previewUri;
	}

}
