package kr.co.abcmart.bo.display.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.display.model.master.DpHashtag;
import kr.co.abcmart.bo.display.model.master.DpHashtagProduct;
import kr.co.abcmart.bo.display.repository.master.DpHashtagDao;
import kr.co.abcmart.bo.display.repository.master.DpHashtagProductDao;
import kr.co.abcmart.bo.display.vo.HashTagSearchVO;
import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.bo.system.model.master.SySite;
import kr.co.abcmart.bo.system.service.CommonCodeService;
import kr.co.abcmart.bo.system.service.SiteService;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.interfaces.util.UtilsText;
import kr.co.abcmart.user.LoginManager;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HashTagService {

	@Autowired
	SiteService siteService;

	@Autowired
	private CommonCodeService commonCodeService;

	@Autowired
	DpHashtagDao hashTagDao;

	@Autowired
	DpHashtagProductDao hashtagProductDao;

	/**
	 * @Desc : 해쉬 태그 관리 검색 기본 데이터를 조회 한다.
	 * @Method Name : getHashTagSearchForm
	 * @Date : 2019. 6. 26.
	 * @Author : kiowa
	 * @return
	 * @throws Exception
	 */
	public List<SySite> getHashTagSearchForm() throws Exception {
		return siteService.getSiteList();
	}

	/**
	 * @Desc : 해쉬 태그 정보를 조회 한다.
	 * @Method Name : getHashTagList
	 * @Date : 2019. 6. 26.
	 * @Author : kiowa
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<DpHashtag> getHashTagList(Pageable<HashTagSearchVO, DpHashtag> pageable) throws Exception {
		int totalCount = hashTagDao.selectHashTagListCount(pageable);

		pageable.setTotalCount(totalCount);

		if (totalCount > 0) {
			pageable.setContent(hashTagDao.selectHashTagList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 해쉬태그 상세 정보를 조회한다.
	 * @Method Name : getHashTagDetail
	 * @Date : 2019. 6. 26.
	 * @Author : kiowa
	 * @param hashTagSearchVo
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getHashTagDetail(HashTagSearchVO hashTagSearchVo) throws Exception {
		Map<String, Object> rtnVal = new HashMap<String, Object>(); // 결과 정보
		rtnVal.put("siteList", this.getHashTagSearchForm());

		String[] codeFields = { CommonCode.SELL_STAT_CODE };
		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				false);

		rtnVal.put("sellStateCode", pair.getFirst());

		if (hashTagSearchVo.getHshtgSeq() != null) {
			rtnVal.put("dpHashTag", this.selectDpHashTag(hashTagSearchVo.getHshtgSeq()));
		}

		return rtnVal;
	}

	/**
	 * @Desc : 해쉬태그 정보 등록
	 * @Method Name : setCreateHashTagInfo
	 * @Date : 2019. 6. 27.
	 * @Author : kiowa
	 * @param dpHashTag
	 * @return
	 * @throws Exception
	 */
	public int setCreateHashTagInfo(DpHashtag dpHashTag) throws Exception {

		dpHashTag.setRgsterNo(LoginManager.getUserDetails().getAdminNo());
		dpHashTag.setModerNo(LoginManager.getUserDetails().getAdminNo());

		int cnt = this.insertDpHashTag(dpHashTag);

		Integer hshtgSeq = dpHashTag.getHshtgSeq();

		log.error("hstgSeq ===] " + hshtgSeq);

		if (dpHashTag.getPrdtNoArr() != null) {
			List<DpHashtagProduct> hashTagProductList = new ArrayList<DpHashtagProduct>();

			String[] prdtNoArr = dpHashTag.getPrdtNoArr().split(",");

			for (String prdtNo : prdtNoArr) {
				DpHashtagProduct dpHashtagProduct = new DpHashtagProduct();

				dpHashtagProduct.setHshtgSeq(hshtgSeq);
				dpHashtagProduct.setPrdtNo(prdtNo);

				hashTagProductList.add(dpHashtagProduct);
			}

			cnt += this.insertDpHashtagProductList(hashTagProductList);
		}
		return cnt;
	}

	/**
	 * @Desc : 해쉬태그 정보 수정
	 * @Method Name : setModifyHashTagInfo
	 * @Date : 2019. 6. 27.
	 * @Author : kiowa
	 * @param dpHashTag
	 * @return
	 * @throws Exception
	 */
	public int setModifyHashTagInfo(DpHashtag dpHashTag) throws Exception {

		dpHashTag.setModerNo(LoginManager.getUserDetails().getAdminNo());

		int cnt = this.updateDpHashTag(dpHashTag);

		Integer hshtgSeq = dpHashTag.getHshtgSeq();

		cnt += this.deleteDpHashtagProduct(hshtgSeq); // 해당 해쉬태그 상품 모두 삭제

		if (dpHashTag.getPrdtNoArr() != null) {
			List<DpHashtagProduct> hashTagProductList = new ArrayList<DpHashtagProduct>();

			String[] prdtNoArr = dpHashTag.getPrdtNoArr().split(",");

			for (String prdtNo : prdtNoArr) {
				DpHashtagProduct dpHashtagProduct = new DpHashtagProduct();

				dpHashtagProduct.setHshtgSeq(hshtgSeq);
				dpHashtagProduct.setPrdtNo(prdtNo);

				hashTagProductList.add(dpHashtagProduct);
			}

			cnt += this.insertDpHashtagProductList(hashTagProductList);
		}
		return cnt;
	}

	public Page<Map<String, Object>> getExcelUploadProductList(Pageable<DpHashtag, Map<String, Object>> pageable)
			throws Exception {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

		DpHashtag dpHashTag = pageable.getBean();

		if (!UtilsText.isAllEmpty(dpHashTag.getPrdtNoArr())) {

			dpHashTag.setPrdtNoArr1(UtilsText.split(dpHashTag.getPrdtNoArr(), ","));

			resultList = hashtagProductDao.selectExcelUploadPorudctList(dpHashTag);

			/*
			 * List<Map<String, Object>> test = new ArrayList<Map<String, Object>>();
			 *
			 * for (Map<String, Object> map : page.getContent()) {
			 *
			 * Map<String, Object> testMap = new HashMap<String, Object>();
			 *
			 * for (String key : map.keySet()) {
			 * testMap.put(org.springframework.jdbc.support.JdbcUtils.
			 * convertUnderscoreNameToPropertyName(key), map.get(key)); }
			 *
			 * test.add(testMap); }
			 *
			 * page.setContent(test);
			 */

			if (!resultList.isEmpty()) {
				pageable.setTotalCount(resultList.size());
				List<Map<String, Object>> keyCamalTransResultList = new ArrayList<Map<String, Object>>();

				for (Map<String, Object> map : resultList) {
					Map<String, Object> keyCamalTransMap = new HashMap<String, Object>();

					for (String key : map.keySet()) {
						if (UtilsText.equals(key, "TITLE_IMAGE_URL")) {
							keyCamalTransMap.put(
									org.springframework.jdbc.support.JdbcUtils.convertUnderscoreNameToPropertyName(key),
									UtilsText.concat(map.get(key).toString(), "?shrink=100:100"));
						} else {
							keyCamalTransMap.put(
									org.springframework.jdbc.support.JdbcUtils.convertUnderscoreNameToPropertyName(key),
									map.get(key));
						}
					}

					keyCamalTransResultList.add(keyCamalTransMap);
				}

				pageable.setContent(keyCamalTransResultList);
			}
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : DP_해시태그 정보 등록
	 * @Method Name : insertDpHashTag
	 * @Date : 2019. 6. 27.
	 * @Author : kiowa
	 * @param dpHashTag
	 * @return
	 * @throws Exception
	 */
	private int insertDpHashTag(DpHashtag dpHashTag) throws Exception {
		return hashTagDao.insertHashTag(dpHashTag);
	}

	/**
	 * @Desc : DP_해시태그상품 정보 등록
	 * @Method Name : insertDpHashtagProductList
	 * @Date : 2019. 6. 27.
	 * @Author : kiowa
	 * @param hashTagProductList
	 * @return
	 * @throws Exception
	 */
	private int insertDpHashtagProductList(List<DpHashtagProduct> hashTagProductList) throws Exception {
		return hashtagProductDao.insertDpHashtagProductList(hashTagProductList);
	}

	/**
	 * @Desc : 해쉬태그 순번으로 정보 조회
	 * @Method Name : selectByPrimaryKey
	 * @Date : 2019. 6. 27.
	 * @Author : kiowa
	 * @param hshtgSeq
	 * @return
	 * @throws Exception
	 */
	private DpHashtag selectDpHashTag(Integer hshtgSeq) throws Exception {
		DpHashtag dpHashtag = new DpHashtag();
		dpHashtag.setHshtgSeq(hshtgSeq);
		return hashTagDao.selectByPrimaryKey(dpHashtag);
	}

	/**
	 * @Desc : DP_해시태그 정보 수정
	 * @Method Name : updateDpHashTag
	 * @Date : 2019. 6. 27.
	 * @Author : kiowa
	 * @param dpHashTag
	 * @return
	 * @throws Exception
	 */
	private int updateDpHashTag(DpHashtag dpHashTag) throws Exception {
		return hashTagDao.updateHashTag(dpHashTag);
	}

	/**
	 * @Desc : 선택한 해쉬태그 순번에 속한 모든 삭품을 삭제한다.
	 * @Method Name : deleteDpHashtagProduct
	 * @Date : 2019. 6. 27.
	 * @Author : kiowa
	 * @param hshtgSeq
	 * @return
	 * @throws Exception
	 */
	private int deleteDpHashtagProduct(Integer hshtgSeq) throws Exception {
		return hashtagProductDao.deleteDpHashtagProductList(hshtgSeq);
	}

}