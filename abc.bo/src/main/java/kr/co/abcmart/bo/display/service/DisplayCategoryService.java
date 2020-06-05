package kr.co.abcmart.bo.display.service;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.display.model.master.DpCategory;
import kr.co.abcmart.bo.display.model.master.DpCategoryCorner;
import kr.co.abcmart.bo.display.model.master.DpCategoryProduct;
import kr.co.abcmart.bo.display.model.master.DpDisplayTemplateCorner;
import kr.co.abcmart.bo.display.repository.master.DpCategoryCornerDao;
import kr.co.abcmart.bo.display.repository.master.DpCategoryCornerNameDao;
import kr.co.abcmart.bo.display.repository.master.DpCategoryCornerSetDao;
import kr.co.abcmart.bo.display.repository.master.DpCategoryDao;
import kr.co.abcmart.bo.display.repository.master.DpCategoryProductDao;
import kr.co.abcmart.bo.display.repository.master.DpDisplayTemplateCornerDao;
import kr.co.abcmart.bo.display.vo.DisplayContentsPopupVO;
import kr.co.abcmart.bo.display.vo.DpExhibitionPageVO;
import kr.co.abcmart.bo.product.model.master.PdProduct;
import kr.co.abcmart.bo.product.service.ProductService;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.FileUpload;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.common.util.UtilsArray;
import kr.co.abcmart.common.util.UtilsCollection;
import kr.co.abcmart.common.util.UtilsFile;
import kr.co.abcmart.common.util.UtilsObject;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;
import kr.co.abcmart.util.UtilsText;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DisplayCategoryService {

	@Autowired
	private DpCategoryDao dpCategoryDao;

	@Autowired
	private DpCategoryCornerDao dpCategoryCornerDao;

	@Autowired
	private DpDisplayTemplateCornerDao dpDisplayTemplateCornerDao;

	@Autowired
	private DpCategoryProductDao dpCategoryProductDao;

	@Autowired
	private ProductService productService;

	@Autowired
	private DpCategoryCornerNameDao dpCategoryCornerNameDao;

	@Autowired
	private DpCategoryCornerSetDao dpCategoryCornerSetDao;

	/**
	 *
	 * @Desc :전시 카테고리 목록 조회
	 * @Method Name : getStandardCategoryList
	 * @Date : 2019. 3. 5.
	 * @Author : 이가영
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<DpCategory> getDpCategoryList(DpCategory params) throws Exception {

		List<DpCategory> list = dpCategoryDao.selectDpCategoryList(params);
		return list;
	}

	/**
	 * @Desc : 전시 카테고리 상세 조회
	 * @Method Name : getDpCategory
	 * @Date : 2019. 4. 26.
	 * @Author : 이가영
	 * @param params
	 * @return
	 */
	public DpCategory getDpCategory(DpCategory params) throws Exception {

		return dpCategoryDao.selectDpCategory(params);
	}

	/**
	 *
	 * @Desc :표준 카테고리에 대상된 전시 카테고리 목록 조회
	 * @Method Name : getDpCategoryListForStdCategory
	 * @Date : 2019. 3. 8.
	 * @Author : 이가영
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Page<DpCategory> getDpCategoryListForStdCategory(Parameter<DpCategory> parameter) throws Exception {

		Pageable<DpCategory, DpCategory> pageable = new Pageable<DpCategory, DpCategory>(parameter);

		int count = dpCategoryDao.selectDpCategoryListForGridCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(dpCategoryDao.selectDpCategoryListForGrid(pageable));
		}

		return pageable.getPage();
	}

	/**
	 *
	 * @Desc :전시 카테고리 등록
	 * @Method Name : insertDpCategoryList
	 * @Date : 2019. 3. 6.
	 * @Author : 이가영
	 * @param params
	 * @return
	 * @throws Exception
	 */
	//@CacheEvict(value = "displayCategoryService.getHierarchyCategoryList", allEntries = true)
	@Caching(evict = { @CacheEvict(value = "displayCategoryService.getHierarchyCategoryList", allEntries = true),
			@CacheEvict(value = "displayCategoryService.getArtCategoryList", allEntries = true) })
	public void insertDpCategoryList(DpCategory params) throws Exception {

		UserDetails user = LoginManager.getUserDetails();

		params.setRgsterNo(user.getAdminNo());
		params.setModerNo(user.getAdminNo());

		FileUpload pcImageFile = params.getPcImageFile();
		FileUpload mobileImageFile = params.getMobileImageFile();

		if (UtilsText.equals(params.getCtgrNameDispType(), "I")) {
			if (pcImageFile != null && pcImageFile.isFileItem()) {

				String filePath = this.getFilePath("display", "category");
				String fileName = this.getFileName(pcImageFile);

				pcImageFile.transferTo(UtilsText.concat(Const.UPLOAD_FILE_PATH, filePath), fileName);

				params.setPcImagePathText(UtilsText.concat(filePath, fileName));
				params.setPcImageUrl(UtilsText.concat(Const.URL_IMG_UPLOAD_PATH, filePath, fileName));
				params.setPcImageName(pcImageFile.getOrgFileName());

				if (UtilsText.isBlank(params.getPcAltrnText())) {
					params.setPcAltrnText(pcImageFile.getOrgFileName());
				}
			}

			if (mobileImageFile != null && mobileImageFile.isFileItem()) {

				String filePath = this.getFilePath("display", "category");
				String fileName = this.getFileName(mobileImageFile);

				mobileImageFile.transferTo(UtilsText.concat(Const.UPLOAD_FILE_PATH, filePath), fileName);

				params.setMobileImagePathText(UtilsText.concat(filePath, fileName));
				params.setMobileImageUrl(UtilsText.concat(Const.URL_IMG_UPLOAD_PATH, filePath, fileName));
				params.setMobileImageName(mobileImageFile.getOrgFileName());

				if (UtilsText.isBlank(params.getMobileAltrnText())) {
					params.setMobileAltrnText(mobileImageFile.getOrgFileName());
				}
			}
		}

		dpCategoryDao.insertDpCategory(params);
		this.insertDpCategoryCorner(params);
	}

	/**
	 *
	 * @Desc :전시 카테고리 수정
	 * @Method Name : updateDpCategoryList
	 * @Date : 2019. 3. 6.
	 * @Author : 이가영
	 * @param params
	 * @return
	 * @throws Exception
	 */
	// @CacheEvict(value = "displayCategoryService.getHierarchyCategoryList",
	// allEntries = true)
	@Caching(evict = { @CacheEvict(value = "displayCategoryService.getHierarchyCategoryList", allEntries = true),
			@CacheEvict(value = "displayCategoryService.getArtCategoryList", allEntries = true) })
	public void updateDpCategoryList(DpCategory params) throws Exception {

		DpCategory original = dpCategoryDao.selectByPrimaryKey(params);

		// 전시카테고리 사용>사용안함 으로 수정하는 경우
		if (UtilsText.equals(original.getUseYn(), Const.BOOLEAN_TRUE)
				&& UtilsText.equals(params.getUseYn(), Const.BOOLEAN_FALSE)) {
			boolean isExistActive = this.selectActiveSubCategoryCount(original);

			if (isExistActive) {
				throw new Exception("사용중인 하위 카테고리가 있을 경우 사용안함 처리가 불가합니다.");
			}
		}

		UserDetails user = LoginManager.getUserDetails();

		params.setModerNo(user.getAdminNo());

		FileUpload pcImageFile = params.getPcImageFile();
		FileUpload mobileImageFile = params.getMobileImageFile();

		if (UtilsText.equals(params.getCtgrNameDispType(), "I")) {
			if (pcImageFile != null && pcImageFile.isFileItem()) {

				// 기존 파일 삭제
				File file = new File(UtilsText.concat(Const.UPLOAD_FILE_PATH, params.getPcImagePathText()));
				UtilsFile.deleteQuietly(file);

				String filePath = this.getFilePath("display", "category");
				String fileName = this.getFileName(pcImageFile);

				pcImageFile.transferTo(UtilsText.concat(Const.UPLOAD_FILE_PATH, filePath), fileName);

				params.setPcImagePathText(UtilsText.concat(filePath, fileName));
				params.setPcImageUrl(UtilsText.concat(Const.URL_IMG_UPLOAD_PATH, filePath, fileName));
				params.setPcImageName(pcImageFile.getOrgFileName());

				if (UtilsText.isBlank(params.getPcAltrnText())) {
					params.setPcAltrnText(pcImageFile.getOrgFileName());
				}
			} else {
				if (UtilsText.isBlank(params.getPcAltrnText())) {
					params.setPcAltrnText(params.getPcImageName());
				}
			}

			if (mobileImageFile != null && mobileImageFile.isFileItem()) {

				// 기존 파일 삭제
				File file = new File(UtilsText.concat(Const.UPLOAD_FILE_PATH, params.getMobileImagePathText()));
				UtilsFile.deleteQuietly(file);

				String filePath = this.getFilePath("display", "category");
				String fileName = this.getFileName(mobileImageFile);

				mobileImageFile.transferTo(UtilsText.concat(Const.UPLOAD_FILE_PATH, filePath), fileName);

				params.setMobileImagePathText(UtilsText.concat(filePath, fileName));
				params.setMobileImageUrl(UtilsText.concat(Const.URL_IMG_UPLOAD_PATH, filePath, fileName));
				params.setMobileImageName(mobileImageFile.getOrgFileName());

				if (UtilsText.isBlank(params.getMobileAltrnText())) {
					params.setMobileAltrnText(mobileImageFile.getOrgFileName());
				}
			} else {
				if (UtilsText.isBlank(params.getMobileAltrnText())) {
					params.setMobileAltrnText(params.getMobileImageName());
				}
			}
		}

		dpCategoryDao.updateDpCategory(params);

		this.updateDpCategoryCorner(params, original);
	}

	/**
	 * @Desc : 사용 중인 하위 전시 카테고리 유무 체크
	 * @Method Name : hasActiveSubCategory
	 * @Date : 2019. 9. 19.
	 * @Author : lee
	 * @param parameter
	 * @return
	 */
	public boolean selectActiveSubCategoryCount(DpCategory params) throws Exception {

		int cnt = dpCategoryDao.selectActiveSubCategoryCount(params);

		if (cnt > 1) {
			return true;
		}
		return false;
	}

	/**
	 *
	 * @Desc :전시 카테고리 정렬 순서 수정
	 * @Method Name : updateDpCategorySort
	 * @Date : 2019. 3. 7.
	 * @Author : 이가영
	 * @param params
	 * @return
	 * @throws Exception
	 */
	// @CacheEvict(value = "displayCategoryService.getHierarchyCategoryList",
	// allEntries = true)
	@Caching(evict = { @CacheEvict(value = "displayCategoryService.getHierarchyCategoryList", allEntries = true),
			@CacheEvict(value = "displayCategoryService.getArtCategoryList", allEntries = true) })
	public void updateDpCategorySort(Parameter<DpCategory[]> parameter) throws Exception {

		for (DpCategory category : parameter.get()) {
			if (category.getCtgrNo() != null)
				dpCategoryDao.updateDpCategory(category);
		}
	}

	/**
	 *
	 * @Desc :전시 카테고리 코너 등록
	 * @Method Name : insertDpCategoryCorner
	 * @Date : 2019. 3. 7.
	 * @Author : 이가영
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public void insertDpCategoryCorner(DpCategory parameter) throws Exception {

		DpCategoryCorner corner = new DpCategoryCorner();
		DpDisplayTemplateCorner param = new DpDisplayTemplateCorner();
		corner.setCtgrNo(parameter.getCtgrNo());

		DisplayContentsPopupVO vo = new DisplayContentsPopupVO();
		vo.setCtgrNo(parameter.getCtgrNo());

		// pc
		if (parameter.getPcDispTmplNo() != null) {
			corner.setDispTmplNo(parameter.getPcDispTmplNo());

			param.setDispTmplNo(parameter.getPcDispTmplNo());
			List<DpDisplayTemplateCorner> list1 = dpDisplayTemplateCornerDao.select(param);

			for (DpDisplayTemplateCorner item : list1) {

				corner.setDispTmplCornerSeq(item.getDispTmplCornerSeq());
				dpCategoryCornerDao.insert(corner);
			}
		}

		// mobile
		if (parameter.getMobileDispTmplNo() != null) {
			corner.setDispTmplNo(parameter.getMobileDispTmplNo());

			param.setDispTmplNo(parameter.getMobileDispTmplNo());
			List<DpDisplayTemplateCorner> list2 = dpDisplayTemplateCornerDao.select(param);

			for (DpDisplayTemplateCorner item : list2) {

				corner.setDispTmplCornerSeq(item.getDispTmplCornerSeq());
				dpCategoryCornerDao.insert(corner);
			}
		}
	}

	/**
	 *
	 * @Desc :전시 카테고리 코너 수정
	 * @Method Name : updateDpCategoryCorner
	 * @Date : 2019. 3. 7.
	 * @Author : 이가영
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public void updateDpCategoryCorner(DpCategory parameter, DpCategory original) throws Exception {

		DpCategoryCorner corner = new DpCategoryCorner();
		DpDisplayTemplateCorner param = new DpDisplayTemplateCorner();
		corner.setCtgrNo(parameter.getCtgrNo());

		DisplayContentsPopupVO vo = new DisplayContentsPopupVO();
		vo.setCtgrNo(parameter.getCtgrNo());

		// pc
		corner.setDispTmplNo(original.getPcDispTmplNo());
		vo.setDispTmplNo(original.getPcDispTmplNo());
		if (!UtilsText.isBlank(parameter.getPcDispTmplNo())
				&& !(UtilsText.equals(parameter.getPcDispTmplNo(), original.getPcDispTmplNo()))) {

			if (!UtilsText.isBlank(original.getPcDispTmplNo())) {
				dpCategoryCornerNameDao.deleteDisplayCategoryCornerName(vo);
				dpCategoryCornerSetDao.deleteDisplayCategoryCornerSet(vo);
				dpCategoryCornerDao.deleteDpCtgrCorner(corner);
			}

			param.setDispTmplNo(parameter.getPcDispTmplNo());
			List<DpDisplayTemplateCorner> list1 = dpDisplayTemplateCornerDao.select(param);

			corner.setDispTmplNo(parameter.getPcDispTmplNo());

			for (DpDisplayTemplateCorner item : list1) {

				corner.setDispTmplCornerSeq(item.getDispTmplCornerSeq());
				dpCategoryCornerDao.insert(corner);
			}
		} else if (UtilsText.isBlank(parameter.getPcDispTmplNo()) && !(UtilsText.isBlank(original.getPcDispTmplNo()))) {
			dpCategoryCornerNameDao.deleteDisplayCategoryCornerName(vo);
			dpCategoryCornerSetDao.deleteDisplayCategoryCornerSet(vo);
			dpCategoryCornerDao.deleteDpCtgrCorner(corner);
		}

		// mobile
		corner.setDispTmplCornerSeq(null);
		corner.setDispTmplNo(original.getMobileDispTmplNo());
		vo.setDispTmplNo(original.getMobileDispTmplNo());
		if (!UtilsText.isBlank(parameter.getMobileDispTmplNo())
				&& !(UtilsText.equals(parameter.getMobileDispTmplNo(), original.getMobileDispTmplNo()))) {

			if (!(UtilsText.isBlank(original.getMobileDispTmplNo()))) {
				dpCategoryCornerNameDao.deleteDisplayCategoryCornerName(vo);
				dpCategoryCornerSetDao.deleteDisplayCategoryCornerSet(vo);
				dpCategoryCornerDao.deleteDpCtgrCorner(corner);
			}

			param.setDispTmplNo(parameter.getMobileDispTmplNo());
			List<DpDisplayTemplateCorner> list2 = dpDisplayTemplateCornerDao.select(param);

			corner.setDispTmplNo(parameter.getMobileDispTmplNo());

			for (DpDisplayTemplateCorner item : list2) {

				corner.setDispTmplCornerSeq(item.getDispTmplCornerSeq());
				dpCategoryCornerDao.insert(corner);
			}
		} else if (UtilsText.isBlank(parameter.getMobileDispTmplNo())
				&& !(UtilsText.isBlank(original.getMobileDispTmplNo()))) {
			dpCategoryCornerNameDao.deleteDisplayCategoryCornerName(vo);
			dpCategoryCornerSetDao.deleteDisplayCategoryCornerSet(vo);
			dpCategoryCornerDao.deleteDpCtgrCorner(corner);
		}
	}

	/**
	 *
	 * @Desc :상품 조회
	 * @Method Name : searchProductByCtgrNo
	 * @Date : 2019. 3. 7.
	 * @Author : 이가영
	 * @param params
	 * @return
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> searchProductByCtgrNo(Parameter<DpCategoryProduct> parameter) throws Exception {

		Pageable<DpCategoryProduct, PdProduct> pageable = new Pageable<DpCategoryProduct, PdProduct>(parameter);
		Page<PdProduct> productList = this.productService.searchByCtgrNo(pageable);

		return productList.getGrid();

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
	public Page<DpDisplayTemplateCorner> getDpCategoryCornerList(
			Pageable<DpCategoryCorner, DpDisplayTemplateCorner> pageable) throws Exception {

		int count = dpCategoryCornerDao.selectDpCategoryCornerListCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(dpCategoryCornerDao.selectDpCategoryCornerList(pageable));
		}
		return pageable.getPage();
	}

	/**
	 *
	 * @Desc : 전시 카테고리에서 사용 중인 전시 템플릿 수 조회
	 * @Method Name : getDpCategoryCount
	 * @Date : 2019. 3. 15.
	 * @Author : lee
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public int getDpCategoryCount(String dispTmplNo) throws Exception {

		int count = dpCategoryDao.selectDpTmplCountInDisplayCtgr(dispTmplNo);

		return count;
	}

	/**
	 * @Desc : 상품번호에 대한 전시카테고리 목록 조회
	 * @Method Name : getDpCategoryListForPrdtNo
	 * @Date : 2019. 3. 27.
	 * @Author : tennessee
	 * @param product
	 * @return
	 * @throws Exception
	 */
	public List<DpCategory> getDpCategoryListByPrdtNo(PdProduct product) throws Exception {
		List<DpCategory> result = new ArrayList<DpCategory>();

		if (!UtilsObject.isEmpty(product) && UtilsText.isNotBlank(product.getPrdtNo())) {
			DpCategoryProduct criteriaProduct = new DpCategoryProduct();
			criteriaProduct.setPrdtNo(product.getPrdtNo());
			// 전시카테고리와 상품간 매핑정보 조회
			List<DpCategoryProduct> categoryProductList = this.dpCategoryProductDao.select(criteriaProduct);

			if (UtilsCollection.isNotEmpty(categoryProductList)) {
				for (DpCategoryProduct item : categoryProductList) {
					DpCategory criteria = new DpCategory();
					criteria.setCtgrNo(item.getCtgrNo());
					List<DpCategory> categoryList = this.getDpCategoryList(criteria);
					if (UtilsCollection.isNotEmpty(categoryList)) {
						result.add(categoryList.get(0));
					}
				}
			}
		}
		product.setDisplayCategoryList(result.toArray(new DpCategory[result.size()]));
		return result;
	}

	/**
	 * @Desc : 전시카테고리 조회
	 * @Method Name : getDpCategoryListBySelf
	 * @Date : 2019. 3. 29.
	 * @Author : tennessee
	 * @param criteria
	 * @return
	 * @throws Exception
	 */
	public List<DpCategory> getDpCategoryListBySelf(DpCategory criteria) throws Exception {
		return this.dpCategoryDao.select(criteria);
	}

	/**
	 * @Desc : 카테고리상품 등록
	 * @Method Name : insertDpCategoryProduct
	 * @Date : 2019. 4. 5.
	 * @Author : tennessee
	 * @param categoryProductList
	 * @return
	 * @throws Exception
	 */
	public Integer insertDpCategoryProduct(List<DpCategoryProduct> categoryProductList) throws Exception {
		int appliedCount = 0;
		if (UtilsCollection.isNotEmpty(categoryProductList)) {
			for (DpCategoryProduct item : categoryProductList) {
				appliedCount += this.dpCategoryProductDao.insert(item);
			}
		}
		return appliedCount;
	}

//	/**
//	 * @Desc : 상품 번호에 해당하는 전시카테고리 매핑정보 삭제
//	 * @Method Name : deleteByPrdtNo
//	 * @Date : 2019. 4. 16.
//	 * @Author : tennessee
//	 * @param prdtNo
//	 * @return
//	 * @throws Exception
//	 */
//	public Integer deleteByPrdtNo(String prdtNo) throws Exception {
//		int appliedCount = 0;
//		if (UtilsText.isNotEmpty(prdtNo)) {
//			DpCategoryProduct criteria = new DpCategoryProduct();
//			criteria.setPrdtNo(prdtNo);
//			appliedCount = this.dpCategoryProductDao.delete(criteria);
//		} else {
//			log.debug("상품 번호가 없습니다.");
//		}
//		return appliedCount;
//	}

	/**
	 * @Desc : 전시카테고리 등록
	 * @Method Name : insertCategoryProduct
	 * @Date : 2019. 4. 5.
	 * @Author : tennessee
	 * @param product
	 * @throws Exception
	 */
	public void insertDisplayCategory(PdProduct product) throws Exception {
		if (!UtilsObject.isEmpty(product) && UtilsArray.isNotEmpty(product.getDisplayCategoryList())) {
			// 기존 전시 카테고리 모두 제거
			this.dpCategoryProductDao.deleteByPrdtNo(product.getPrdtNo());

			List<DpCategoryProduct> categoryProductList = new ArrayList<DpCategoryProduct>();
			DpCategoryProduct temp = null;
			int sortSeq = 0;
			for (DpCategory item : product.getDisplayCategoryList()) {
				temp = new DpCategoryProduct();
				temp.setPrdtNo(product.getPrdtNo());
				temp.setCtgrNo(item.getCtgrNo());
				temp.setSortSeq(++sortSeq);
				temp.setRgsterNo(LoginManager.getUserDetails().getAdminNo());
				temp.setRgstDtm(new Timestamp(new Date().getTime()));
				categoryProductList.add(temp);
			}
			this.insertDpCategoryProduct(categoryProductList);
		}
	}

	/**
	 * @Desc : 한 상품과 연계된 전시페이지 정보 반환
	 * @Method Name : getExhibitionPage
	 * @Date : 2019. 4. 23.
	 * @Author : tennessee
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<DpExhibitionPageVO> getExhibitionPageForProduct(
			Pageable<DpExhibitionPageVO, DpExhibitionPageVO> pageable) throws Exception {
		Integer count = this.dpCategoryProductDao.selectByExhibitionPageForProductTotalCount(pageable);
		pageable.setTotalCount(count);
		if (count > 0) {
			pageable.setContent(this.dpCategoryProductDao.selectByExhibitionPageForProduct(pageable));
		}
		return pageable.getPage();
	}

	/**
	 * @Desc : 카테고리 번호 및 그와 연계된 카테고리에 연결된 상품이 있는지 확인
	 * @Method Name : hasProduct
	 * @Date : 2019. 6. 19.
	 * @Author : tennessee
	 * @param ctgrNo
	 * @return
	 * @throws Exception
	 */
	public boolean hasProduct(String ctgrNo) throws Exception {
		return this.dpCategoryProductDao.selectCountForRelatedProduct(ctgrNo) > 0 ? true : false;
	}

	/**
	 *
	 * @Desc : 파일명 생성[임시]
	 * @Method Name : getFileName
	 * @Date : 2019. 2. 8.
	 * @Author : SANTA
	 * @param uploadFile
	 * @return
	 */
	private String getFileName(FileUpload uploadFile) {
		return UtilsText.concat(Long.toString(System.currentTimeMillis()), ".", uploadFile.getExt());
	}

	/**
	 *
	 * @Desc : 파일 path 생성
	 * @Method Name : getFilePath
	 * @Date : 2019. 4. 1.
	 * @Author : SANTA
	 * @param path
	 * @return
	 */
	private String getFilePath(String... path) {

		String fileSeparator = "/";
		StringBuilder sb = new StringBuilder();
		Calendar cal = Calendar.getInstance();

		sb.append(fileSeparator);
		for (String str : path) {
			sb.append(str).append(fileSeparator);
		}

		sb.append(Integer.toString(cal.get(Calendar.YEAR))).append(fileSeparator);

		if (cal.get(Calendar.MONTH) < 9) {
			sb.append("0");
		}

		sb.append(Integer.toString(cal.get(Calendar.MONTH) + 1));
		sb.append(fileSeparator);

		return sb.toString();
	}

	/**
	 * @Desc : 표준 카테고리 정보 업데이트
	 * @Method Name : setStandardCategory
	 * @Date : 2019. 11. 20.
	 * @Author : kiowa
	 * @param params
	 * @throws Exception
	 */
	public void setStandardCategory(DpCategory params) throws Exception {
		this.dpCategoryDao.updateStdCtgrNo(params);
	}

	/**
	 * @Desc : 표준카테고리에 연결된 전시 카테고리 정보 조회
	 * @Method Name : getCategoryListByStgCtgrNo
	 * @Date : 2019. 11. 21.
	 * @Author : kiowa
	 * @param stdCtgrNo
	 * @return
	 * @throws Exception
	 */
	public List<DpCategory> getCategoryListByStdCtgrNo(String stdCtgrNo) throws Exception {
		return dpCategoryDao.selectCategoryListByStdCtgrNo(stdCtgrNo);
	}

	/**
	 * @Desc : 선택한 표준 카테고리에 맵핑된 전시 카테고리를 초기화 한다.
	 * @Method Name : setStandardCategoryReset
	 * @Date : 2019. 12. 26.
	 * @Author : kiowa
	 * @param stdCtgrNo
	 * @throws Exception
	 */
	public void setStandardCategoryReset(String stdCtgrNo) throws Exception {
		this.dpCategoryDao.updateStdCtgrNoReset(stdCtgrNo);
	}

}
