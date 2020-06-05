package kr.co.abcmart.bo.product.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.product.model.master.DpBrandProduct;
import kr.co.abcmart.bo.product.repository.master.DpBrandProductDao;
import kr.co.abcmart.bo.product.vo.DpBrandProductSearchVO;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.util.UtilsNumber;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 브랜드 페이지 서비스
 * @FileName : BrandPageService.java
 * @Project : abc.bo
 * @Date : 2019. 6. 17.
 * @Author : 백인천
 */
@Slf4j
@Service
public class BrandProductService {

	@Autowired
	private DpBrandProductDao dpBrandProductDao;

	/**
	 * @Desc : 브랜드 스타일 대상상품 목록 저장
	 * @Method Name : saveBrandStyle
	 * @Date : 2019. 2. 11.
	 * @Author : tennessee
	 * @param dpBrandStyleList
	 * @return
	 * @throws Exception
	 */
	public int inputBrandProductList(DpBrandProduct dpBrandProduct) throws Exception {
		UserDetails user = LoginManager.getUserDetails();
		Integer appliedCount = 0;
		dpBrandProduct.setRgsterNo(user.getAdminNo());
		appliedCount = dpBrandProductDao.insertBrandProduct(dpBrandProduct);
		return appliedCount;
	}

	/**
	 * @Desc : 브랜드 비주얼 대상상품 조회
	 * @Method Name : searchBrandProductList
	 * @Date : 2019. 2. 8.
	 * @Author : tennessee
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<DpBrandProduct> searchBrandProductList(Pageable<DpBrandProductSearchVO, DpBrandProduct> pageable)
			throws Exception {
		int count = this.dpBrandProductDao.selectBrandProductCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(dpBrandProductDao.selectBrandProduct(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @param brandBannerSeq
	 * @param brandNo
	 * @Desc : 브랜드 비주얼 대상상품 시트 저장
	 * @Method Name : setPlanningDisplayProductManagementList
	 * @Date : 2019. 7. 9.
	 * @Author : 백인천
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Map<String, List<String>> setBrandProductManagementList(List<DpBrandProduct> brandProductList,
			String brandNo, int brandBannerSeq) throws Exception {
		UserDetails user = LoginManager.getUserDetails();
		Map<String, List<String>> duplicateCheckMap = new HashMap<String, List<String>>();
		List<String> duplicateCheckList = new ArrayList<String>();
		List<String> duplicateBannerCheckList = new ArrayList<String>();
		for (DpBrandProduct item : brandProductList) {

			DpBrandProduct criteria = new DpBrandProduct();
			criteria.setBrandNo(brandNo);
			criteria.setBrandBannerSeq(brandBannerSeq);
			criteria.setPrdtNo(item.getPrdtNo());

			item.setBrandNo(brandNo);
			item.setBrandBannerSeq(brandBannerSeq);
			switch (item.getStatus()) {
			case "I":
				// 등록처리
				// 타 브랜드 배너에 등록되었는지 체크
				Integer duplicateBannerCheck = this.dpBrandProductDao.selectDuplicateBannerCheckCount(criteria);
				if (UtilsNumber.compare(0, duplicateBannerCheck) == 0) {
					// 현 등록 브랜드 배너에 등록되었는지 체크
					Integer duplicateCheck = this.dpBrandProductDao.select(criteria).size();
					if (UtilsNumber.compare(0, duplicateCheck) == 0) {
						item.setRgsterNo(user.getAdminNo());
						item.setRgstDtm(new Timestamp(new Date().getTime()));
						this.dpBrandProductDao.insert(item);
					} else {
						duplicateCheckList.add(item.getPrdtNo());
					}
				} else {
					duplicateBannerCheckList.add(item.getPrdtNo());
				}
				break;
			case "U":
				// 업데이트 처리
				item.setRgsterNo(user.getAdminNo());
				item.setRgstDtm(new Timestamp(new Date().getTime()));
				this.dpBrandProductDao.update(item);
				break;
			case "D":
				// 삭제 처리
				this.dpBrandProductDao.delete(item);
				break;
			default:
				log.debug("등록되지 않은 상태값입니다. {}", item.getStatus());
			}
		}

		duplicateCheckMap.put("duplicateCheckList",
				duplicateCheckList.stream().distinct().collect(Collectors.toList()));
		duplicateCheckMap.put("duplicateBannerCheckList",
				duplicateBannerCheckList.stream().distinct().collect(Collectors.toList()));

		return duplicateCheckMap;
	}

}
