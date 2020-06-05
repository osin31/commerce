package kr.co.abcmart.bo.product.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.product.model.master.DpBrand;
import kr.co.abcmart.bo.product.model.master.DpBrandPage;
import kr.co.abcmart.bo.product.model.master.DpBrandProduct;
import kr.co.abcmart.bo.product.model.master.DpBrandStyleProduct;
import kr.co.abcmart.bo.product.repository.master.DpBrandDao;
import kr.co.abcmart.bo.product.repository.master.DpBrandPageDao;
import kr.co.abcmart.bo.product.repository.master.DpBrandStyleProductDao;
import kr.co.abcmart.bo.product.repository.master.DpBrandVisualDao;
import kr.co.abcmart.bo.product.vo.DpBrandPageSearchVO;
import kr.co.abcmart.bo.product.vo.DpBrandSearchVO;
import kr.co.abcmart.bo.vendor.model.master.VdVendor;
import kr.co.abcmart.bo.vendor.repository.master.VdVendorDao;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.FileUpload;
import kr.co.abcmart.common.util.UtilsObject;
import kr.co.abcmart.common.util.UtilsSftp;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;
import kr.co.abcmart.util.UtilsDate;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 브랜드 서비스
 * @FileName : BrandService.java
 * @Project : abc.bo
 * @Date : 2019. 2. 11.
 * @Author : tennessee
 */
@Slf4j
@Service
public class BrandService {

	private static final String FILE_PATH = UtilsText.concat(Const.UPLOAD_FILE_PATH, Const.IMG_SRC_PRODUCT_BRAND);

	@Autowired
	private DpBrandDao dpBrandDao;

	@Autowired
	private DpBrandVisualDao dpBrandVisualDao;

	@Autowired
	private DpBrandPageDao dpBrandPageDao;

	@Autowired
	private DpBrandStyleProductDao dpBrandStyleProductDao;

	@Autowired
	private VdVendorDao vdVendorDao;

	@Autowired
	private ProductService productService;

	/**
	 *
	 * @Desc : 브랜드 단건 조회
	 * @Method Name : readBrand
	 * @Date : 2019. 2. 8.
	 * @Author : tennessee
	 * @param dpBrandSearch
	 * @return
	 * @throws Exception
	 */
	public DpBrand searchBrand(DpBrandSearchVO dpBrandSearch) throws Exception {
		DpBrand criteria = new DpBrand();
		criteria.setBrandNo(dpBrandSearch.getBrandNo());
		return this.dpBrandDao.selectBrand(criteria);
	}

	/**
	 *
	 * @Desc : 브랜드 통합몰 PC 조회
	 * @Method Name : searchMallPc
	 * @Date : 2019. 6. 13.
	 * @Author : 백인천
	 * @param dpBrandSearch
	 * @return
	 * @throws Exception
	 */
	public Map<String, DpBrandPage> searchMallPc(DpBrandSearchVO dpBrandSearch) throws Exception {
		DpBrandPage criteria = new DpBrandPage();
		criteria.setBrandNo(dpBrandSearch.getBrandNo());
		criteria.setDispGbnType("P");
		criteria.setDeviceCode(CommonCode.DEVICE_PC);
		criteria.setSiteNo(Const.SITE_NO_ART);
		List<DpBrandPage> dataList = this.dpBrandPageDao.selectBrandPageTypeList(criteria);
		if (dataList == null) {
			return null;
		}
		return dataList.stream().collect(Collectors.toMap(DpBrandPage::getChnnlNo, Function.identity()));
	}

	/**
	 *
	 * @Desc : 브랜드 통합몰 Mobile 조회
	 * @Method Name : searchMallMo
	 * @Date : 2019. 2. 13.
	 * @Author : 백인천
	 * @param dpBrandSearch
	 * @return
	 * @throws Exception
	 */
	public Map<String, DpBrandPage> searchMallMo(DpBrandSearchVO dpBrandSearch) throws Exception {
		DpBrandPage criteria = new DpBrandPage();
		criteria.setBrandNo(dpBrandSearch.getBrandNo());
		criteria.setDispGbnType("P");
		criteria.setDeviceCode(CommonCode.DEVICE_MOBILE);
		criteria.setSiteNo(Const.SITE_NO_ART);
		List<DpBrandPage> dataList = this.dpBrandPageDao.selectBrandPageTypeList(criteria);
		if (dataList == null) {
			return null;
		}
		return dataList.stream().collect(Collectors.toMap(DpBrandPage::getChnnlNo, Function.identity()));
	}

	/**
	 *
	 * @Desc : 브랜드 목록 조회
	 * @Method Name : readBrandList
	 * @Date : 2019. 2. 8.
	 * @Author : tennessee
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<DpBrand> searchBrandList(Pageable<DpBrandSearchVO, DpBrand> pageable) throws Exception {
		UserDetails user = LoginManager.getUserDetails();

		if (UtilsText.equals(LoginManager.getUserDetails().getAuthApplySystemType(), Const.AUTH_APPLY_SYSTEM_TYPE_BO)) {
			// BO권한인 경우, 모든 브랜드 조회 가능
		} else {
			// PO권한인 경우, 입점업체와 연계된 브랜드만 조회 가능
			if (UtilsText.isNotEmpty(user.getVndrNo())) {
				// 회원정보에 벤더정보를 포함하고 있을 때
				VdVendor vendor = new VdVendor();
				vendor.setVndrNo(user.getVndrNo());
				VdVendor vndrData = vdVendorDao.selectByPrimaryKey(vendor);
				pageable.getBean().setVndrNo(user.getVndrNo());
				pageable.getBean().setVndrGbnType(vndrData.getVndrGbnType());
			}
		}

		Integer count = this.dpBrandDao.selectBrandListCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(dpBrandDao.selectBrandList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 *
	 * @Desc : 브랜드스타일상품 조회
	 * @Method Name : readBrandStyleProduct
	 * @Date : 2019. 2. 8.
	 * @Author : tennessee
	 * @param dpBrandSearch
	 * @return
	 * @throws Exception
	 */
	public List<DpBrandStyleProduct> searchBrandStyleProduct(DpBrandSearchVO dpBrandSearch) throws Exception {
		DpBrandStyleProduct criteria = new DpBrandStyleProduct();
		criteria.setBrandNo(dpBrandSearch.getBrandNo());
		return this.dpBrandStyleProductDao.select(criteria);
	}

	/**
	 * @Desc : 브랜드스타일상품 전체갯수 조회
	 * @Method Name : readBrandStyleProductTotalCount
	 * @Date : 2019. 2. 8.
	 * @Author : tennessee
	 * @param dpBrandSearch
	 * @return
	 * @throws Exception
	 */
	public Integer searchBrandStyleProductTotalCount(DpBrandSearchVO dpBrandSearch) throws Exception {
		return this.searchBrandStyleProduct(dpBrandSearch).size();
	}

	/**
	 * @Desc : 브랜드 저장
	 * @Method Name : saveBrand
	 * @Date : 2019. 2. 19.
	 * @Author : tennessee
	 * @param brand
	 * @return
	 * @throws Exception
	 */
	public DpBrand inputBrand(DpBrand brand) throws Exception {
		brand.setAconnectDispYn(Const.BOOLEAN_FALSE);

		if (UtilsText.isBlank(brand.getAltrnText())) {
			brand.setAltrnText(brand.getBrandEnName());
		}

		// 브랜드 대표 이미지
		FileUpload fileBrandImage = brand.getFileBrandImageColor();
		if (fileBrandImage.isFileItem()) {

			String fileName = fileBrandImage.getOrgFileName();
			String uploadFileName = UtilsText.concat(String.valueOf(System.currentTimeMillis()), ".",
					FilenameUtils.getExtension(fileName));
			String path = UtilsText.concat(FILE_PATH, "/",
					UtilsDate.today(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS));

			try {
				UtilsSftp.upload(path, uploadFileName, fileBrandImage.getMultiPartFile().getInputStream());
			} catch (Exception e) {
				log.error("{}", e);
				throw new Exception("파일저장에 실패하였습니다.");
			}

			brand.setImagePathText(UtilsText.concat(path, uploadFileName));
			brand.setImageUrl(UtilsText.concat(Const.URL_IMG_HTTPS, "/", path, "/", uploadFileName));
			brand.setImageName(fileName);

			if (UtilsText.isBlank(brand.getAltrnText())) {
				brand.setAltrnText(fileName.split("\\.")[0]);
			}
		}

		brand.setRgsterNo(LoginManager.getUserDetails().getAdminNo());
		brand.setRgstrName(LoginManager.getUserDetails().getUsername());
		brand.setModerNo(LoginManager.getUserDetails().getAdminNo());

		this.dpBrandDao.insert(brand);

		// 브랜드 사용여부가 [사용안함]인 경우, 해당 브랜드의 모든 상품의 전시여부 및 판매상태를 일괄변경처리.
		if (UtilsText.equals(Const.BOOLEAN_FALSE, brand.getUseYn())) {
			productService.setDisableByBrandNo(brand.getBrandNo());
		}

		log.debug("브랜드 기본정보 저장완료. 브랜드번호 : {}", brand.getBrandNo());

		return brand;
	}

	public DpBrand updateBrand(DpBrand brand) throws Exception {
		UserDetails user = LoginManager.getUserDetails();

		DpBrand brdNo = new DpBrand();
		brdNo.setBrandNo(brand.getBrandNo());

		if (UtilsText.isBlank(brand.getSiteNo())) {
			brand.setSiteNo("");
		}
		if (UtilsText.isBlank(brand.getAltrnText())) {
			brand.setAltrnText(brand.getBrandEnName());
		}

		if (UtilsText.isBlank(brand.getDfltMvmnChnnl())) {
			brand.setDfltMvmnChnnl("");
		}

		DpBrand oldBrand = dpBrandDao.selectByPrimaryKey(brdNo);

		// 브랜드 대표 이미지
		FileUpload fileBrandImage = brand.getFileBrandImageColor();
		if (fileBrandImage != null && fileBrandImage.isFileItem()) {

			String fileName = fileBrandImage.getOrgFileName();
			String uploadFileName = UtilsText.concat(String.valueOf(System.currentTimeMillis()), ".",
					FilenameUtils.getExtension(fileName));
			String path = UtilsText.concat(FILE_PATH, "/",
					UtilsDate.today(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS));

			try {
				UtilsSftp.upload(path, uploadFileName, fileBrandImage.getMultiPartFile().getInputStream());
			} catch (Exception e) {
				log.error("{}", e);
				throw new Exception("파일저장에 실패하였습니다.");
			}

			brand.setImagePathText(UtilsText.concat(path, uploadFileName));
			brand.setImageUrl(UtilsText.concat(Const.URL_IMG_HTTPS, "/", path, "/", uploadFileName));
			brand.setImageName(fileName);

			if (UtilsText.isBlank(brand.getAltrnText())) {
				brand.setAltrnText(fileName.split("\\.")[0]);
			}
		}

		brand.setModerNo(user.getAdminNo());
		brand.setModerName(user.getUsername());
		brand.setModDtm(new Timestamp(new Date().getTime()));

		if (oldBrand != null) {
			this.dpBrandDao.updateBrand(brand);
		} else {
			if (UtilsText.isBlank(brand.getSiteNo())) {
				brand.setSiteNo(null);
			}
			brand.setAconnectDispYn("N");
			this.dpBrandDao.insert(brand);
		}

		// 브랜드 사용여부가 [사용안함]인 경우, 해당 브랜드의 모든 상품의 전시여부 및 판매상태를 일괄변경처리.
		log.debug("brand.getUseYn() = {} ", brand.getUseYn());
		if (UtilsText.equals(brand.getUseYn(), Const.BOOLEAN_FALSE)) {
			productService.setDisableByBrandNo(brand.getBrandNo());
		}

		// A-rt브랜드 상품 전시여부 수정시
		if (!UtilsText.equals(brand.getBrandPrdtArtDispYn(), brand.getOrgBrandPrdtArtDispYn())
				&& !UtilsText.equals(Const.SITE_NO_OTS, brand.getSiteNo())) {
			DpBrandPage brandPage = new DpBrandPage();
			brandPage.setBrandNo(brand.getBrandNo());
			brandPage.setSiteNo(Const.SITE_NO_ART);
			brandPage.setDispYn(Const.BOOLEAN_FALSE);
			dpBrandPageDao.updateBrandPageDispYn(brandPage);
		}

		log.debug("브랜드 기본정보 수정완료. 브랜드번호 : {}", brand.getBrandNo());

		return brand;
	}

	public void updateBrands(DpBrandSearchVO[] vos) throws Exception {

		UserDetails user = LoginManager.getUserDetails();

		for (DpBrandSearchVO vo : vos) {

			DpBrand brand = new DpBrand();

			brand.setModerNo(user.getAdminNo());
			brand.setModerName(user.getUsername());
			brand.setModDtm(new Timestamp(new Date().getTime()));

			brand.setAconnectDispYn(vo.getAconnectDispYn());
			brand.setBrandNo(vo.getBrandNo());

			this.dpBrandDao.update(brand);
		}
	}

	/**
	 *
	 * @Desc : 브랜드ID 중복 검사
	 * @Method Name : isDuplicateBrandId
	 * @Date : 2019. 1. 31.
	 * @Author : tennessee
	 * @param dpBrand
	 * @return 중복여부
	 * @throws Exception
	 */
	public Boolean isDuplicateBrandId(DpBrandSearchVO dpBrandSearch) throws Exception {
		Boolean result = false;
		DpBrand criteria = new DpBrand();
		List<DpBrand> brandList = this.dpBrandDao.select(criteria);

		if (CollectionUtils.isNotEmpty(brandList)) {
			result = true;
		}

//		List<DpBrand> brandListERP = this.getBrandListOnERP();
//		for (DpBrand brandERP : brandListERP) {
//			if (dpBrandSearch.getSearchInsdMgmtInfoText().equals(brandERP.getInsdMgmtInfoText())) {
//				result = true;
//			}
//		}

		return result;
	}

	/**
	 *
	 * @Desc : 기간계 브랜드 정보 조회
	 * @Method Name : getBrandListOnERP
	 * @Date : 2019. 2. 8.
	 * @Author : tennessee
	 * @return
	 * @throws Exception
	 */
	public List<DpBrand> searchBrandListOnERP() throws Exception {
		List<DpBrand> result = null;

		return result;
	}

	/**
	 * @Desc : 브랜드 비주얼 대상상품 목록 저장
	 * @Method Name : inputBrandVisualProductList
	 * @Date : 2019. 2. 11.
	 * @Author : tennessee
	 * @param dpBrandStyleList
	 * @return
	 * @throws Exception
	 */
	public int inputBrandVisualProductList(List<DpBrandProduct> brandProductList, String brandNo) throws Exception {
		Integer appliedCount = 0;
		for (DpBrandProduct item : brandProductList) {
			appliedCount += this.dpBrandVisualDao.insertBrandVisualProduct(item);
		}
		return appliedCount;
	}

	/**
	 * @Desc : 브랜드 조회 API
	 * @Method Name : searchBrandApi
	 * @Date : 2019. 2. 25.
	 * @Author : tennessee
	 * @param criteria
	 * @return
	 * @throws Exception
	 */
	public List<DpBrand> searchBrandApi(DpBrandSearchVO criteria) throws Exception {
		return this.dpBrandDao.selectBrandApi(criteria);
	}

	/**
	 * @Desc : 브랜드번호에 해당하는 브랜드이름 반환
	 * @Method Name : getBrandNameByBrandNo
	 * @Date : 2019. 4. 4.
	 * @Author : tennessee
	 * @param brandNo
	 * @return
	 * @throws Exception
	 */
	public String getBrandNameByBrandNo(String brandNo) throws Exception {
		String result = null;
		if (UtilsText.isNotBlank(brandNo)) {
			DpBrandSearchVO criteria = new DpBrandSearchVO();
			criteria.setBrandNo(brandNo);

			DpBrand brand = this.searchBrand(criteria);

			if (!UtilsObject.isEmpty(brand)) {
				result = brand.getBrandName();
			}
		}
		return result;
	}

	/**
	 * @Desc : 브랜드 통합몰 페이지 리스트 반환
	 * @Method Name : searchBrandMallPageList
	 * @Date : 2019. 6. 5.
	 * @Author : 백인천
	 * @param brandNo
	 * @return
	 * @throws Exception
	 */
	public Page<DpBrandPage> searchBrandPageList(Pageable<DpBrandPageSearchVO, DpBrandPage> pageable) throws Exception {
		Integer count = this.dpBrandPageDao.selectBrandPageListCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(dpBrandPageDao.selectBrandPageList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 브랜드 번호 중복카운트 체크
	 * @Method Name : getBrandNoCount
	 * @Date : 2019. 12. 23.
	 * @Author : 유성민
	 * @param brand
	 * @return
	 */
	public int getBrandNoCount(DpBrand brand) throws Exception {
		return this.dpBrandDao.selectBrandNoCount(brand);
	}

}
