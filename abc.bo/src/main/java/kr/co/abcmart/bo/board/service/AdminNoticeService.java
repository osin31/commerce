package kr.co.abcmart.bo.board.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.board.model.master.BdAdminNotice;
import kr.co.abcmart.bo.board.model.master.BdAdminNoticeAttachFile;
import kr.co.abcmart.bo.board.model.master.BdAdminNoticeTargetVendor;
import kr.co.abcmart.bo.board.repository.master.BdAdminNoticeDao;
import kr.co.abcmart.bo.board.repository.master.BdAdminNoticeTargetVendorDao;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 관리자공지 서비스
 * @FileName : AdminNoticeService.java
 * @Project : abc.bo
 * @Date : 2019. 2. 13.
 * @Author : 3top
 */
@Slf4j
@Service
public class AdminNoticeService {

	@Autowired
	private BdAdminNoticeDao bdAdminNoticeDao;

	@Autowired
	private BdAdminNoticeTargetVendorDao bdAdminNoticeTargetVendorDao;

	/**
	 * @Desc : 관리자 공지 목록을 조회
	 * @Method Name : getAdminNoticeList
	 * @Date : 2019. 1. 31.
	 * @Author : 3top [lks@3top.co.kr]
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<BdAdminNotice> getAdminNoticeList(Pageable<BdAdminNotice, BdAdminNotice> pageable) throws Exception {

		int count = bdAdminNoticeDao.selectAdminNoticeListCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(bdAdminNoticeDao.selectAdminNoticeList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 관리자 공지 상세 조회
	 * @Method Name : getAdminNoticeDetail
	 * @Date : 2019. 2. 22.
	 * @Author : kiowa
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getAdminNoticeDetail(BdAdminNotice bdAdminNotice) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		// 미리보기 여부
		String preViewYn = bdAdminNotice.getPreViewYN();

		// 공지 상세 정보
		bdAdminNotice = bdAdminNoticeDao.selectAdminNotice(bdAdminNotice);
		result.put("bdAdminNotice", bdAdminNotice);

		if (Const.BOOLEAN_FALSE.equals(bdAdminNotice.getVndrAllChoiceYn())) {
			// 공지 대상 업체 정보
			BdAdminNoticeTargetVendor bdAdminNoticeTargetVendor = new BdAdminNoticeTargetVendor();
			bdAdminNoticeTargetVendor.setAdminNotcSeq(bdAdminNotice.getAdminNotcSeq());
			result.put("bdAdminNoticeTargetVendorList", getBdAdminNoticeTargetVendor(bdAdminNoticeTargetVendor));
		}

		// 미리보기 아닐 경우에만 업데이트 한다.
		if (preViewYn == null || !Const.BOOLEAN_TRUE.equals(preViewYn))
			bdAdminNoticeDao.updateAdminNoticeHitCount(bdAdminNotice);

		return result;
	}

	/**
	 * @Desc : 관리자 공지 등록
	 * @Method Name : setAdminNotice
	 * @Date : 2019. 2. 19.
	 * @Author : kiowa
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> setAdminNotice(BdAdminNotice params) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			// 세션 정보 호출 -> 관리자 공지 작성자의 adminNo set
			params.setRgsterNo(LoginManager.getUserDetails().getAdminNo());

			// 관리자 공지를 INSERT
			bdAdminNoticeDao.insertAdminNotice(params);

			if (Const.BOOLEAN_FALSE.equals(params.getVndrAllChoiceYn())) {
				for (String vndrNo : params.getVndrNo()) {
					BdAdminNoticeTargetVendor bdAdminNoticeTargetVendor = new BdAdminNoticeTargetVendor();
					bdAdminNoticeTargetVendor.setAdminNotcSeq(params.getAdminNotcSeq());
					bdAdminNoticeTargetVendor.setVndrNo(vndrNo);

					bdAdminNoticeTargetVendorDao.insert(bdAdminNoticeTargetVendor);
				}
			}

			result.put("result", Const.BOOLEAN_TRUE);

		} catch (Exception e) {
			result.put("result", Const.BOOLEAN_FALSE);
		}

		return result;
	}

	/**
	 * @Desc : 관리자 공지를 수정
	 * @Method Name : updateAdminNotice
	 * @Date : 2019. 2. 19.
	 * @Author : kiowa
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public void updateAdminNotice(BdAdminNotice params) throws Exception {

		params.setModerNo(LoginManager.getUserDetails().getAdminNo());

		// 관리자 공지를 UPDATE
		bdAdminNoticeDao.updateAdminNotice(params);

		// 기존 업체 데이터 삭제 처리
		bdAdminNoticeTargetVendorDao.deleteBdAdminNoticeTargetVendorByAdminNotcSeq(params);
		if (Const.BOOLEAN_FALSE.equals(params.getVndrAllChoiceYn())) {
			for (String vndrNo : params.getVndrNo()) {
				BdAdminNoticeTargetVendor bdAdminNoticeTargetVendor = new BdAdminNoticeTargetVendor();
				bdAdminNoticeTargetVendor.setAdminNotcSeq(params.getAdminNotcSeq());
				bdAdminNoticeTargetVendor.setVndrNo(vndrNo);

				bdAdminNoticeTargetVendorDao.insert(bdAdminNoticeTargetVendor);
			}
		}

	}

	/**
	 * @Desc : 관리자 공지를 삭제
	 * @Method Name : deleteAdminNotice
	 * @Date : 2019. 1. 31.
	 * @Author : 3top [lks@3top.co.kr]
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public void deleteAdminNotice(BdAdminNotice[] entity) throws Exception {
		for (BdAdminNotice bdAdminNotice : entity) {
			if (bdAdminNotice.getAdminNotcSeq() != null) {
				BdAdminNoticeAttachFile params = new BdAdminNoticeAttachFile();
				params.setAdminNotcSeq(bdAdminNotice.getAdminNotcSeq());

				bdAdminNoticeTargetVendorDao.deleteBdAdminNoticeTargetVendorByAdminNotcSeq(bdAdminNotice);
				bdAdminNoticeDao.deleteAdminNotice(bdAdminNotice);
			}
		}
	}

	/**
	 * @Desc : 관리자 공지 대상 업체 정보 조회
	 * @Method Name : getBdAdminNoticeTargetVendor
	 * @Date : 2019. 2. 21.
	 * @Author : kiowa
	 * @param bdAdminNoticeTargetVendor
	 * @return
	 * @throws Exception
	 */
	public List<BdAdminNoticeTargetVendor> getBdAdminNoticeTargetVendor(
			BdAdminNoticeTargetVendor bdAdminNoticeTargetVendor) throws Exception {

		return bdAdminNoticeTargetVendorDao.selectBdAdminNoticeTargetVendorList(bdAdminNoticeTargetVendor);
	}

	/**
	 * @Desc : po용 관리자 공지사항 목록 top 5
	 * @Method Name : selectAdminNoticeForPoDashboardTopFive
	 * @Date : 2019.4.25
	 * @Author : 이재렬
	 * @param parameter
	 * @throws Exception
	 */
	public List<BdAdminNotice> selectAdminNoticeForPoDashboardTopFive(BdAdminNotice bdAdminNotice, String vndrNo)
			throws Exception {

		return bdAdminNoticeDao.selectAdNoticeForDashboardTopFive(bdAdminNotice, vndrNo);
	}

	/**
	 * @Desc : po용 관리자 공지사항 목록
	 * @Method Name : selectAdminNoticeForPoDashboard
	 * @Date : 2019.6.12.
	 * @Author : 이재렬
	 * @param parameter
	 * @throws Exception
	 */
	public Page<BdAdminNotice> selectAdminNoticeForPoDashboard(Pageable<BdAdminNotice, BdAdminNotice> pageable)
			throws Exception {

		int count = bdAdminNoticeDao.selectAdNoticeForDashboardCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(bdAdminNoticeDao.selectAdNoticeForDashboard(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : BO 관리자 공지 메인팝업
	 * @Method Name : getMainPopAdminNotice
	 * @Date : 2019. 6. 11.
	 * @Author : Kimyounghyun
	 * @return
	 */
	public String[] getBoMainPopAdminNotice() {
		return bdAdminNoticeDao.selectBoMainPopAdminNotice();
	}

	/**
	 * @Desc : PO 관리자 공지 메인팝업
	 * @Method Name : getMainPopAdminNotice
	 * @Date : 2019. 6. 11.
	 * @Author : Kimyounghyun
	 * @return
	 */
	public String[] getPoMainPopAdminNotice(String vndrNo) {
		return bdAdminNoticeDao.selectPoMainPopAdminNotice(vndrNo);
	}

}
