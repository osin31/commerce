package kr.co.abcmart.bo.board.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.board.model.master.BdFaq;
import kr.co.abcmart.bo.board.repository.master.BdFaqDao;
import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.bo.system.service.CommonCodeService;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;

/**
 * @Desc :
 * @FileName : BdFaqService.java
 * @Project : abc.bo
 * @Date : 2019. 4. 16.
 * @Author : 3TOP_118
 */
@Service
public class BdFaqService {

	@Autowired
	BdFaqDao bdFaqDao;

	@Autowired
	CommonCodeService commonCodeService;

	@Autowired
	BdFaqService bdFaqService;

	/**
	 * @Desc : FAQ조회 폼 정보
	 * @Method Name : getFaqForm
	 * @Date : 2019. 3. 4.
	 * @Author : 신인철
	 * @param codeField
	 * @return
	 * @throws Exception
	 */
	public List<SyCodeDetail> getFaqForm() throws Exception {
		SyCodeDetail codeParam = new SyCodeDetail();
		codeParam.setCodeField(CommonCode.CNSL_TYPE_CODE);
		codeParam.setAddInfo1(CommonCode.CNSL_GBN_CODE_INQUIRY);
		codeParam.setAddInfo2(Const.BOOLEAN_TRUE);
		List<SyCodeDetail> cnslCodeList = commonCodeService.getCodeDtlInfoList(codeParam);

		return cnslCodeList;
	}

	/**
	 * @Desc : FAQ그리드 조회
	 * @Method Name : getFaqGridList
	 * @Date : 2019. 3. 5.
	 * @Author : 신인철
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<BdFaq> getFaqGridList(Pageable<BdFaq, BdFaq> pageable) throws Exception {
		int totalCount = bdFaqDao.selectBdFaqListCount(pageable);
		pageable.setTotalCount(totalCount);
		if (totalCount > 0) {
			pageable.setContent(bdFaqDao.selectBdFaqList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : top10 그리드 검색
	 * @Method Name : getTop10GridList
	 * @Date : 2019. 3. 12.
	 * @Author : 신인철
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<BdFaq> getTop10GridList(Pageable<BdFaq, BdFaq> pageable) throws Exception {
		int totalCount = bdFaqDao.selectTop10ListCount(pageable);
		pageable.setTotalCount(totalCount);
		if (totalCount > 0) {
			pageable.setContent(bdFaqDao.selectTop10List(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : FAQ 상세보기
	 * @Method Name : getFaqDetailForm
	 * @Date : 2019. 3. 5.
	 * @Author : 신인철
	 * @param bdFaqParam
	 * @param codeFields
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getFaqDetailForm(BdFaq bdFaqParam, String[] codeFields) throws Exception {
		Map<String, Object> rtnVal = new HashMap<String, Object>();
		Map<String, List<SyCodeDetail>> codeListMap = commonCodeService.getCodeListByGroup(codeFields);

		rtnVal.put("cnslTypeDtlCode", codeListMap.get(CommonCode.CNSL_TYPE_DTL_CODE));

		codeListMap.get(CommonCode.CNSL_TYPE_CODE).removeIf(
				cnslTypeCode -> !UtilsText.equals(CommonCode.CNSL_GBN_CODE_INQUIRY, cnslTypeCode.getAddInfo1())
						|| UtilsText.equals(Const.BOOLEAN_FALSE, cnslTypeCode.getAddInfo2()));

		rtnVal.put("cnslTypeCode", codeListMap.get(CommonCode.CNSL_TYPE_CODE));
		rtnVal.put("bdFaq", bdFaqService.getFaqDetail(bdFaqParam));

		return rtnVal;
	}

	/**
	 * @Desc : FAQ 상세 조회
	 * @Method Name : getFaqDetail
	 * @Date : 2019. 3. 5.
	 * @Author : 신인철
	 * @param bdFaq
	 * @return
	 * @throws Exception
	 */
	public BdFaq getFaqDetail(BdFaq bdFaq) throws Exception {
		return bdFaqDao.selectFaqDetail(bdFaq);
	}

	/**
	 * @Desc : FAQ수정
	 * @Method Name : updateFaqDetail
	 * @Date : 2019. 3. 5.
	 * @Author : 신인철
	 * @param bdFaq
	 * @throws Exception
	 */
	public Map<String, Object> updateFaqDetail(BdFaq bdFaq) throws Exception {
		Map<String, Object> rsMap = new HashMap<>();

		try {
			UserDetails user = LoginManager.getUserDetails();
			bdFaq.setModerNo(user.getAdminNo());

			if (UtilsText.equals(Const.BOOLEAN_TRUE, bdFaq.getTop10SetupYn())) {
				if (bdFaq.getTop10SortSeq() == 0) {
					bdFaq.setTop10SortSeq(bdFaqDao.selectTop10UpdateCount());
				}
			} else {
				bdFaqService.setPullingTop10List(bdFaq);
				bdFaq.setTop10SortSeq(0);
			}
			bdFaqDao.updateFaqDetail(bdFaq);

			rsMap.put("result", 1);
		} catch (Exception e) {
			rsMap.put("result", 0);
			rsMap.put("Message", e.toString());
		}
		return rsMap;
	}

	/**
	 * @Desc : FAQ 등록
	 * @Method Name : setFaqDetail
	 * @Date : 2019. 3. 5.
	 * @Author : 신인철
	 * @param bdFaq
	 * @throws Exception
	 */
	public Map<String, Object> setFaqDetail(BdFaq bdFaq) throws Exception {
		Map<String, Object> rsMap = new HashMap<>();
		try {
			UserDetails user = LoginManager.getUserDetails();
			bdFaq.setModerNo(user.getAdminNo());
			bdFaq.setRgsterNo(user.getAdminNo());
			if (UtilsText.equals(Const.BOOLEAN_FALSE, bdFaq.getTop10SetupYn())) {
				bdFaq.setTop10SortSeq(0);
				bdFaqDao.insertFaqDetail(bdFaq);
			} else {
				bdFaqDao.insertTop10Faq(bdFaq);
			}

			rsMap.put("result", 1);
		} catch (Exception e) {
			rsMap.put("result", 0);
			rsMap.put("Message", e.toString());
		}
		return rsMap;
	}

	/**
	 * @Desc : FAQ 삭제
	 * @Method Name : deleteFaqDetail
	 * @Date : 2019. 3. 26.
	 * @Author : 신인철
	 * @param bdFaq
	 * @throws Exception
	 */
	public Map<String, Object> deleteFaqDetail(BdFaq bdFaq) throws Exception {
		Map<String, Object> rsMap = new HashMap<>();
		try {
			bdFaqDao.deleteFaqDetail(bdFaq);

			if (UtilsText.equals(Const.BOOLEAN_TRUE, bdFaq.getCurrentTop10Setup())) {
				bdFaqService.setPullingTop10List(bdFaq);
			}

			rsMap.put("result", 1);
		} catch (Exception e) {
			rsMap.put("result", 0);
			rsMap.put("Message", e.toString());
		}
		return rsMap;
	}

	/**
	 * @Desc : TOP10 설정된 카운트
	 * @Method Name : getTop10Count
	 * @Date : 2019. 4. 16.
	 * @Author : 신인철
	 * @param bdFaq
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getTop10Count() throws Exception {
		Map<String, Object> rsMap = new HashMap<>();
		try {
			int count = bdFaqDao.selectTop10SetCount();

			rsMap.put("count", count);
			rsMap.put("result", 1);
		} catch (Exception e) {
			rsMap.put("result", 0);
			rsMap.put("Message", e.toString());
		}
		return rsMap;
	}

	/**
	 * @Desc : TOP10 데이터 삭제나 수정후에 정렬순서 저장
	 * @Method Name : setPullingTop10List
	 * @Date : 2019. 4. 16.
	 * @Author : 신인철
	 * @param bdFaq
	 * @throws Exception
	 */
	public void setPullingTop10List(BdFaq bdFaq) throws Exception {
		List<BdFaq> pullingList = bdFaqDao.selectPuulingTop10List(bdFaq);

		UserDetails user = LoginManager.getUserDetails();
		int currentSeq;

		for (BdFaq faq : pullingList) {
			currentSeq = faq.getTop10SortSeq();
			faq.setTop10SortSeq(currentSeq - 1);
			faq.setModerNo(user.getAdminNo());
		}

		bdFaqDao.updateMultiTop10Sort(pullingList);
	}

	/**
	 * @Desc : TOP10 정렬순서 변경
	 * @Method Name : updateTop10Sort
	 * @Date : 2019. 4. 12.
	 * @Author : 신인철
	 * @param paramArray
	 * @throws Exception
	 */
	public void updateTop10Sort(BdFaq[] paramArray) throws Exception {
		UserDetails user = LoginManager.getUserDetails();
		for (BdFaq bdFaq : paramArray) {
			if (bdFaq.getFaqSeq() != null) {
				bdFaq.setModerNo(user.getAdminNo());
				bdFaqDao.updateTop10Sort(bdFaq);
			}
		}
	}

	/**
	 * @Desc : TOP10 설정 취소
	 * @Method Name : updateTop10Cancel
	 * @Date : 2019. 8. 30.
	 * @Author : sic
	 * @param bdfaq
	 * @return
	 * @throws Exception
	 */
	public void updateTop10Cancel(BdFaq bdFaq) throws Exception {
		bdFaqService.setPullingTop10List(bdFaq);

		bdFaq.setTop10SetupYn(Const.BOOLEAN_FALSE);
		bdFaq.setTop10SortSeq(0);
		bdFaq.setModerNo(LoginManager.getUserDetails().getAdminNo());

		bdFaqDao.updateFaqDetail(bdFaq);

	}

}
