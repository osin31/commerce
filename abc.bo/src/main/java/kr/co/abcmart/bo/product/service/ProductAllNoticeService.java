package kr.co.abcmart.bo.product.service;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.product.vo.VdVendorProductAllNoticeSearchVO;
import kr.co.abcmart.bo.vendor.model.master.VdVendorProductAllNotice;
import kr.co.abcmart.bo.vendor.repository.master.VdVendorProductAllNoticeDao;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.interfaces.util.UtilsText;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 상품전체공지 서비스
 * @FileName : ProductAllNoticeService.java
 * @Project : abc.bo
 * @Date : 2019. 4. 15.
 * @Author : hsjhsj19
 */
@Slf4j
@Service
public class ProductAllNoticeService {

	@Autowired
	private VdVendorProductAllNoticeDao vendorProductAllNoticeDao;

	/**
	 * @Desc : 상품전체공지 목록 조회
	 * @Method Name : searchProductAllNotice
	 * @Date : 2019. 4. 16.
	 * @Author : hsjhsj19
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<VdVendorProductAllNotice> searchProductAllNotice(
			Pageable<VdVendorProductAllNoticeSearchVO, VdVendorProductAllNotice> pageable) throws Exception {
		Integer count = this.vendorProductAllNoticeDao.selectProductAllNoticeCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(this.vendorProductAllNoticeDao.selectProductAllNotice(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 상품전체공지 상세 조회
	 * @Method Name : searchProductAllNoticeByPrimaryKey
	 * @Date : 2019. 4. 16.
	 * @Author : hsjhsj19
	 * @param vdVendorProductAllNotice
	 * @return
	 * @throws Exception
	 */
	public VdVendorProductAllNotice searchProductAllNoticeByPrimaryKey(
			VdVendorProductAllNotice vdVendorProductAllNotice) throws Exception {
		return this.vendorProductAllNoticeDao.searchByPrimaryKey(vdVendorProductAllNotice);
	}

	/**
	 * @Desc : 상품전체공지 등록/수정
	 * @Method Name : saveProductAllNotice
	 * @Date : 2019. 4. 16.
	 * @Author : hsjhsj19
	 * @param vdVendorProductAllNotice
	 * @return
	 * @throws Exception
	 */
	public int saveProductAllNotice(VdVendorProductAllNotice vdVendorProductAllNotice) throws Exception {
		// 세션 정보 호출
		UserDetails user = LoginManager.getUserDetails();
		String vndrNo = user.getVndrNo();
		int cnt = 0;

		vdVendorProductAllNotice.setModDtm(new Timestamp(new Date().getTime()));
		vdVendorProductAllNotice.setModerNo(user.getAdminNo());
		// 업체번호 지정
		if (UtilsText.equals(LoginManager.getUserDetails().getAuthApplySystemType(), Const.AUTH_APPLY_SYSTEM_TYPE_BO)) {
			vndrNo = "VD10000001";
		} else {
			if (UtilsText.isBlank(vndrNo)) {
				throw new Exception("업체번호가 없는 업체입니다.");
			}
		}
		vdVendorProductAllNotice.setVndrNo(vndrNo);

		if (Const.CRUD_C.equals(vdVendorProductAllNotice.getType())) {
			vdVendorProductAllNotice.setRgstDtm(new Timestamp(new Date().getTime()));
			vdVendorProductAllNotice.setRgsterNo(user.getAdminNo());
			cnt += this.vendorProductAllNoticeDao.insertWithSelectKey(vdVendorProductAllNotice);
		} else {
			cnt += this.vendorProductAllNoticeDao.update(vdVendorProductAllNotice);
		}

		return cnt;
	}

}
