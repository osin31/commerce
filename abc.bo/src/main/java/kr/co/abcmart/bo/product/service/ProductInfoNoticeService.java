package kr.co.abcmart.bo.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.product.model.master.CmProductInfoNotice;
import kr.co.abcmart.bo.product.repository.master.CmProductInfoNoticeDao;
import kr.co.abcmart.bo.vendor.model.master.VdVendor;
import kr.co.abcmart.bo.vendor.service.VendorService;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.util.UtilsDate;
import kr.co.abcmart.common.util.UtilsObject;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;
import kr.co.abcmart.util.UtilsText;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductInfoNoticeService {

	@Autowired
	private CmProductInfoNoticeDao cmProductInfoNoticeDao;

	@Autowired
	private VendorService vendorService;

	/**
	 * 상품정보고시관리 목록 조회
	 * 
	 * @Desc : 상품정보고시관리 목록 조회
	 * @Method Name : getCmProductInfoNoticeList
	 * @Date : 2019. 2. 21.
	 * @Author : 이가영
	 * @param pageable
	 * @return
	 */
	public Page<CmProductInfoNotice> getCmProductInfoNoticeList(
			Pageable<CmProductInfoNotice, CmProductInfoNotice> pageable) throws Exception {

		int count = cmProductInfoNoticeDao.selectCmProductInfoNoticeCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(cmProductInfoNoticeDao.selectCmProductInfoNoticeList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * 사용 중인 상품정보고시관리 목록 조회
	 * 
	 * @Desc : 상품정보고시관리 목록 조회
	 * @Method Name : getUseCmProductInfoNoticeList
	 * @Date : 2019. 3. 7.
	 * @Author : 이가영
	 * @param
	 * @return
	 */
	public List<CmProductInfoNotice> getUseCmProductInfoNoticeList(String itemCode, String mmnyPrdtYn, String vndrNo)
			throws Exception {
		List<CmProductInfoNotice> result = cmProductInfoNoticeDao.selectUseCmProductInfoNoticeList(itemCode);

		if (UtilsText.equals(Const.BOOLEAN_FALSE, mmnyPrdtYn)) {
			// PO권한인 경우, AS 책임자와 전화번호에 대한 값 설정을 위한 입점업체정보 조회
			VdVendor vendor = this.vendorService.getVendorAsMngrInfo(vndrNo);

			if (UtilsObject.isEmpty(vendor) || UtilsText.isBlank(vendor.getAsMngrText())) {
				// 조회된 정보가 없는 경우, 빈 값으로 설정
				result.stream().filter(item -> UtilsText.equals(CommonCode.PRDT_INFO_NOTC_CODE_AS_MANAGER_INFO,
						item.getPrdtInfoNotcCode())).forEach(item -> item.setInfoNotcDfltValue(""));
			} else {
				// 조회된 정보가 있는 경우, 해당 값으로 설정
				result.stream()
						.filter(item -> UtilsText.equals(CommonCode.PRDT_INFO_NOTC_CODE_AS_MANAGER_INFO,
								item.getPrdtInfoNotcCode()))
						.forEach(item -> item.setInfoNotcDfltValue(vendor.getAsMngrText()));
			}
		}
		return result;
	}

	/**
	 * 상품정보고시관리 수정
	 * 
	 * @Desc : 상품정보고시관리 수정
	 * @Method Name : updateCmProductInfoNotice
	 * @Date : 2019. 2. 21.
	 * @Author : 이가영
	 * @param pageable
	 * @return
	 */
	public void updateCmProductInfoNotice(CmProductInfoNotice[] infos) throws Exception {

		UserDetails user = LoginManager.getUserDetails();

		for (CmProductInfoNotice cmProductInfoNotice : infos) {

			cmProductInfoNotice.validate();

			cmProductInfoNotice.setModDtm(UtilsDate.getSqlTimeStamp());
			cmProductInfoNotice.setModerNo(user.getAdminNo());

			cmProductInfoNoticeDao.updateCmProductInfoNotice(cmProductInfoNotice);
		}
	}

	/**
	 * 상품정보고시관리 상세 조회
	 * 
	 * @Desc : 상품정보고시관리 상세 조회
	 * @Method Name : getCmProductInfoNoticeDetail
	 * @Date : 2019. 2. 21.
	 * @Author : 이가영
	 * @param pageable
	 * @return
	 */
	public CmProductInfoNotice getCmProductInfoNoticeDetail(CmProductInfoNotice cmProductInfoNotice) throws Exception {

		return cmProductInfoNoticeDao.selectCmProductInfoNotice(cmProductInfoNotice);
	}

	/**
	 * 
	 * @Desc : 상품정보고시관리 상세 조회 (by sequence)
	 * @Method Name : getCmProductInfoNoticeDetail
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param cmProductInfoNotice
	 * @return
	 * @throws Exception
	 */
	public CmProductInfoNotice getCmProductInfoNoticeDetailBySeq(CmProductInfoNotice cmProductInfoNotice)
			throws Exception {

		return cmProductInfoNoticeDao.selectByPrimaryKey(cmProductInfoNotice);
	}

	/**
	 * 상품정보고시관리 수정/등록
	 * 
	 * @Desc : 상품정보고시관리 수정/등록
	 * @Method Name : setCmProductInfoNoticeDetail
	 * @Date : 2019. 2. 21.
	 * @Author : 이가영
	 * @param pageable
	 * @return
	 */
	public void setCmProductInfoNoticeDetail(CmProductInfoNotice cmProductInfoNotice) throws Exception {

		cmProductInfoNotice.validate();

		// 수정자/수정날짜 세팅
		UserDetails user = LoginManager.getUserDetails();
		cmProductInfoNotice.setModerNo(user.getAdminNo());
		cmProductInfoNotice.setModDtm(UtilsDate.getSqlTimeStamp());

		if (cmProductInfoNotice.getPrdtInfoNotcSeq() != null) {
			// 수정
			cmProductInfoNoticeDao.updateCmProductInfoNotice(cmProductInfoNotice);
		} else {
			// 등록
			cmProductInfoNotice.setRgsterNo(user.getAdminNo());
			cmProductInfoNotice.setRgstDtm(UtilsDate.getSqlTimeStamp());
			cmProductInfoNoticeDao.insert(cmProductInfoNotice);
		}
	}

	/**
	 * 
	 * @Desc : 상품정보고시 중복 데이터 조회
	 * @Method Name : getDuplicationOfCmProductInfoNotice
	 * @Date : 2019. 11. 15.
	 * @Author : lee
	 * @param cmProductInfoNotice
	 * @return
	 * @throws Exception
	 */
	public CmProductInfoNotice getDuplicationOfCmProductInfoNotice(CmProductInfoNotice cmProductInfoNotice)
			throws Exception {
		return cmProductInfoNoticeDao.selectDuplicationOfCmProductInfoNotice(cmProductInfoNotice);
	}
}
