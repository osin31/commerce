package kr.co.abcmart.bo.product.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.product.model.master.DpBrand;
import kr.co.abcmart.bo.product.model.master.DpBrandStyle;
import kr.co.abcmart.bo.product.model.master.DpBrandStyleProduct;
import kr.co.abcmart.bo.product.repository.master.DpBrandStyleDao;
import kr.co.abcmart.bo.product.repository.master.DpBrandStyleProductDao;
import kr.co.abcmart.bo.product.vo.DpBrandStyleProductSearchVO;
import kr.co.abcmart.bo.product.vo.DpBrandStyleSearchVO;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.util.UtilsNumber;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 브랜드 페이지 서비스
 * @FileName : BrandStyleService.java
 * @Project : abc.bo
 * @Date : 2019. 6. 17.
 * @Author : 백인천
 */
@Slf4j
@Service
public class BrandStyleService {

	@Autowired
	private DpBrandStyleDao dpBrandStyleDao;

	@Autowired
	private DpBrandStyleProductDao dpBrandStyleProductDao;

	/**
	 * @Desc : 브랜드 스타일 대상상품 조회
	 * @Method Name : getBrandStyleProduct
	 * @Date : 2019. 8. 19.
	 * @Author : 백인천
	 * @param dpBrandStyleProduct
	 * @return
	 * @throws Exception
	 */
	public int getBrandStyleProduct(DpBrandStyleProduct dpBrandStyleProduct) throws Exception {
		List<DpBrandStyleProduct> bsProduct = new ArrayList<DpBrandStyleProduct>();
		bsProduct = dpBrandStyleProductDao.select(dpBrandStyleProduct);
		return bsProduct.size();
	}

	/**
	 *
	 * @Desc : 브랜드스타일 다건 조회
	 * @Method Name : readBrandStyleList
	 * @Date : 2019. 2. 8.
	 * @Author : tennessee
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<DpBrandStyle> searchBrandStyleList(Pageable<DpBrandStyleSearchVO, DpBrandStyle> pageable)
			throws Exception {
		Integer count = this.dpBrandStyleDao.selectBrandStyleListCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(dpBrandStyleDao.selectBrandStyleList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 브랜드 스타일 대상상품 목록 저장
	 * @Method Name : saveBrandStyle
	 * @Date : 2019. 2. 11.
	 * @Author : tennessee
	 * @param dpBrandStyleList
	 * @return
	 * @throws Exception
	 */
	public int insertBrandStyleProductList(DpBrandStyleProduct dpBrandStyleProduct) throws Exception {
		UserDetails user = LoginManager.getUserDetails();
		Integer appliedCount = 0;
		dpBrandStyleProduct.setRgsterNo(user.getAdminNo());
		appliedCount = dpBrandStyleProductDao.insertBrandStyleProduct(dpBrandStyleProduct);
		return appliedCount;
	}

	/**
	 * @Desc : 브랜드 스타일 업데이트
	 * @Method Name : updateBrandStyleList
	 * @Date : 2019. 2. 21.
	 * @Author : tennessee
	 * @param dpBrandStyleList
	 * @return
	 * @throws Exception
	 */
	public int updateBrandStyleList(List<DpBrandStyle> dpBrandStyleList) throws Exception {
		Integer appliedCount = 0;
		for (DpBrandStyle dpBrandStyle : dpBrandStyleList) {
			appliedCount += this.dpBrandStyleDao.update(dpBrandStyle);
		}
		return appliedCount;
	}

	/**
	 * @Desc : 브랜드 스타일 목록 저장
	 * @Method Name : saveBrandStyle
	 * @Date : 2019. 2. 11.
	 * @Author : tennessee
	 * @param brandStyleList
	 * @return
	 * @throws Exception
	 */
	public int setBrandStyleList(List<DpBrandStyle> dpBrandStyleList, String brandNo) throws Exception {
		UserDetails user = LoginManager.getUserDetails();
		Integer appliedCount = 0;
		for (DpBrandStyle item : dpBrandStyleList) {
			item.setBrandNo(brandNo);
			// log.debug(item.getBrandNo() + " / " + item.getStatus());
			switch (item.getStatus()) {
			case "I":
				// 등록처리
				item.setRgsterNo(user.getAdminNo());
				item.setRgstDtm(new Timestamp(new Date().getTime()));
				appliedCount += this.dpBrandStyleDao.insertBrandStyle(item);
				break;
			case "U":
				// 업데이트 처리
				item.setModerNo(user.getAdminNo());
				item.setModDtm(new Timestamp(new Date().getTime()));
				appliedCount += this.dpBrandStyleDao.update(item);
				break;
			case "D":
				// 삭제 처리
				appliedCount += this.dpBrandStyleDao.delete(item);
				break;
			default:
				log.debug("등록되지 않은 상태값입니다. {}", item.getStatus());
			}
		}
		return appliedCount;
	}

	/**
	 * @Desc : 브랜드 스타일 목록 삭제
	 * @Method Name : setBrandStyleDelete
	 * @Date : 2019. 7. 1.
	 * @Author : 백인천
	 * @param dpBrandStyleList
	 * @return
	 * @throws Exception
	 */
	public int setBrandStyleDelete(List<DpBrandStyle> dpBrandStyleList, String brandNo) throws Exception {
		Integer appliedCount = 0;
		for (DpBrandStyle item : dpBrandStyleList) {
			DpBrandStyleProduct bsProduct = new DpBrandStyleProduct();
			bsProduct.setBrandNo(item.getBrandNo());
			bsProduct.setBrandStyleSeq(item.getBrandStyleSeq());

			// 대상상품 삭제
			this.dpBrandStyleProductDao.deleteBrandStyleProduct(bsProduct);

			// 삭제 처리
			appliedCount += this.dpBrandStyleDao.delete(item);

		}
		return appliedCount;
	}

	/**
	 * @Desc : 브랜드 스타일 저장
	 * @Method Name : setBrandStyleList
	 * @Date : 2019. 2. 28.
	 * @Author : tennessee
	 * @param brandStyleList
	 * @param brand
	 * @return
	 * @throws Exception
	 */
	public int setBrandStyleList(List<DpBrandStyle> brandStyleList, DpBrand brand) throws Exception {
		return this.setBrandStyleList(brandStyleList, brand.getBrandNo());
	}

	/**
	 * @Desc : 브랜드스타일 대상상품 조회
	 * @Method Name : searchBrandStyleProductList
	 * @Date : 2019. 2. 8.
	 * @Author : tennessee
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<DpBrandStyleProduct> searchBrandStyleProductList(
			Pageable<DpBrandStyleProductSearchVO, DpBrandStyleProduct> pageable) throws Exception {
		Integer count = this.dpBrandStyleProductDao.selectBrandStyleProductListCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(dpBrandStyleProductDao.selectBrandStyleProductList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @param brandStyleSeq
	 * @param brandNo
	 * @Desc : 브랜드스타일 대상상품 시트 저장
	 * @Method Name : setPlanningDisplayProductManagementList
	 * @Date : 2019. 7. 9.
	 * @Author : 백인천
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<String> setBrandStyleProductManagementList(List<DpBrandStyleProduct> brandStyleProductList,
			String brandNo, int brandStyleSeq) throws Exception {
		List<String> duplicatedPrdtNos = new ArrayList<String>(); // 중복등록된 상품코드 번호
		UserDetails user = LoginManager.getUserDetails();
		for (DpBrandStyleProduct item : brandStyleProductList) {

			DpBrandStyleProduct criteria = new DpBrandStyleProduct();
			criteria.setBrandNo(brandNo);
			criteria.setBrandStyleSeq(brandStyleSeq);
			criteria.setPrdtNo(item.getPrdtNo());

			item.setBrandNo(brandNo);
			item.setBrandStyleSeq(brandStyleSeq);
			log.debug("status > " + item.getStatus());

			switch (item.getStatus()) {
			case "I":
				// 등록처리
				Integer duplicateCheck = this.dpBrandStyleProductDao.select(criteria).size();
				if (UtilsNumber.compare(0, duplicateCheck) == 0) {
					item.setRgsterNo(user.getAdminNo());
					item.setRgstDtm(new Timestamp(new Date().getTime()));
					this.dpBrandStyleProductDao.insert(item);
				} else {
					duplicatedPrdtNos.add(item.getPrdtNo());
				}
				continue;
			case "U":
				// 업데이트 처리
				item.setRgsterNo(user.getAdminNo());
				item.setRgstDtm(new Timestamp(new Date().getTime()));
				this.dpBrandStyleProductDao.update(item);
				continue;
			case "D":
				// 삭제 처리
				this.dpBrandStyleProductDao.delete(item);
				continue;
			default:
				log.debug("등록되지 않은 상태값입니다. {}", item.getStatus());
			}
		}
		return duplicatedPrdtNos.stream().distinct().collect(Collectors.toList());
	}

	/**
	 * @Desc : 브랜드 스타일 행 추가
	 * @Method Name : insertBrandStyleRow
	 * @Date : 2019. 8. 16.
	 * @Author : 백인천
	 * @param brandNo
	 * @return
	 * @throws Exception
	 */
	public int insertBrandStyleRow(String brandNo) throws Exception {
		DpBrandStyle brandStyle = new DpBrandStyle();
		brandStyle.setBrandNo(brandNo);
		brandStyle.setRgsterNo(LoginManager.getUserDetails().getAdminNo());
		brandStyle.setBrandStyleName("스타일명 입력");
		brandStyle.setDispYn(Const.BOOLEAN_FALSE);

		return this.dpBrandStyleDao.insertBrandStyle(brandStyle);
	}

}
