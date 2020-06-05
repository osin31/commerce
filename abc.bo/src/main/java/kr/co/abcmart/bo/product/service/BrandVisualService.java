package kr.co.abcmart.bo.product.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.product.model.master.DpBrand;
import kr.co.abcmart.bo.product.model.master.DpBrandProduct;
import kr.co.abcmart.bo.product.model.master.DpBrandVisual;
import kr.co.abcmart.bo.product.repository.master.DpBrandProductDao;
import kr.co.abcmart.bo.product.repository.master.DpBrandVisualDao;
import kr.co.abcmart.bo.product.vo.DpBrandVisualSearchVO;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 브랜드 페이지 서비스
 * @FileName : BrandVisualService.java
 * @Project : abc.bo
 * @Date : 2019. 6. 17.
 * @Author : 백인천
 */
@Slf4j
@Service
public class BrandVisualService {

	@Autowired
	private DpBrandVisualDao dpBrandVisualDao;

	@Autowired
	private DpBrandProductDao dpBrandProductDao;

	/**
	 * @Desc : 브랜드 비주얼 목록 저장
	 * @Method Name : setBrandVisualList
	 * @Date : 2019. 6. 5.
	 * @Author : 백인천
	 * @param dpBrandVisualList
	 * @return
	 * @throws Exception
	 */
	public int setBrandVisualList(List<DpBrandVisual> dpBrandVisualList, String brandNo) throws Exception {
		UserDetails user = LoginManager.getUserDetails();
		Integer appliedCount = 0;
		log.debug("setBrandVisualList brandNo > " + brandNo);

		for (DpBrandVisual item : dpBrandVisualList) {
			log.debug("item.getStatus() > " + item.getStatus());
			item.setBrandNo(brandNo);
			item.setRgsterNo(user.getAdminNo());
			switch (item.getStatus()) {
			case "I":
				// 등록처리
				item.setRgstDtm(new Timestamp(new Date().getTime()));
				item.setAltrnText(item.getImageName());
				appliedCount += this.dpBrandVisualDao.insertBrandVisual(item);
				break;
			case "U":
				// 업데이트 처리
				item.setModDtm(new Timestamp(new Date().getTime()));
				item.setAltrnText(item.getImageName());
				appliedCount += this.dpBrandVisualDao.update(item);
				break;
			case "D":
				// 삭제 처리
				appliedCount += this.dpBrandVisualDao.delete(item);
				break;
			default:
				log.debug("등록되지 않은 상태값입니다. {}", item.getStatus());
			}
		}
		return appliedCount;
	}

	/**
	 * @Desc : 브랜드비쥬얼 리스트 반환
	 * @Method Name : searchBrandVisualList
	 * @Date : 2019. 4. 4.
	 * @Author : tennessee
	 * @param brandNo
	 * @return
	 * @throws Exception
	 */
	public Page<DpBrandVisual> searchBrandVisualList(Pageable<DpBrandVisualSearchVO, DpBrandVisual> pageable)
			throws Exception {
		Integer count = this.dpBrandVisualDao.selectBrandVisualListCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(dpBrandVisualDao.selectBrandVisualList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 브랜드 통합몰 페이지 PC 저장
	 * @Method Name : setBrandVisualList
	 * @Date : 2019. 2. 28.
	 * @Author : tennessee
	 * @param brandVisualList
	 * @param brand
	 * @return
	 * @throws Exception
	 */
	public int setBrandVisualList(List<DpBrandVisual> brandVisualList, DpBrand brand) throws Exception {
		return this.setBrandVisualList(brandVisualList, brand.getBrandNo());
	}

	/**
	 * @Desc : 브랜드 비주얼 목록 삭제
	 * @Method Name : setBrandVisualDelete
	 * @Date : 2019. 7. 1.
	 * @Author : 백인천
	 * @param dpBrandStyleList
	 * @return
	 * @throws Exception
	 */
	public int setBrandVisualDelete(List<DpBrandVisual> brandVisualList, String brandNo) throws Exception {
		UserDetails user = LoginManager.getUserDetails();
		Integer appliedCount = 0;
		for (DpBrandVisual item : brandVisualList) {
			if (!"0".equals(item.getChecked())) {
				DpBrandProduct bsProduct = new DpBrandProduct();
				bsProduct.setBrandNo(item.getBrandNo());
				bsProduct.setBrandBannerSeq(item.getBrandBannerSeq());

				// 대상상품 삭제
				this.dpBrandProductDao.deleteBrandProduct(bsProduct);

				// 삭제 처리
				appliedCount += this.dpBrandVisualDao.delete(item);
			}

		}
		return appliedCount;
	}

	/**
	 * @Desc : 브랜드 비주얼 전시이미지 조회
	 * @Method Name : getBrandVisualInfo
	 * @Date : 2019. 7. 24.
	 * @Author : 백인천
	 * @param brandVisual
	 * @return
	 * @throws Exception
	 */
	public DpBrandVisual getBrandVisualInfo(DpBrandVisual brandVisual) throws Exception {
		return dpBrandVisualDao.getBrandVisualInfo(brandVisual);
	}

}
