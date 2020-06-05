package kr.co.abcmart.bo.product.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import kr.co.abcmart.bo.display.model.master.DpCategory;
import kr.co.abcmart.bo.display.service.DisplayCategoryService;
import kr.co.abcmart.bo.product.model.master.PdProduct;
import kr.co.abcmart.bo.product.model.master.SyHandlingPrecaution;
import kr.co.abcmart.bo.product.model.master.SyStandardCategory;
import kr.co.abcmart.bo.product.repository.master.SyHandlingPrecautionDao;
import kr.co.abcmart.bo.product.repository.master.SyStandardCategoryDao;
import kr.co.abcmart.common.constant.BaseConst;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;
import kr.co.abcmart.util.UtilsText;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StandardCategoryService {

	@Autowired
	private SyStandardCategoryDao syStandardCategoryDao;

	@Autowired
	private SyHandlingPrecautionDao syHandlingPrecautionDao;

	@Autowired
	private DisplayCategoryService displayCategoryService;

	/**
	 * @Desc :표준 카테고리 리스트 조회
	 * @Method Name : getStandardCategoryList
	 * @Date : 2019. 2. 12.
	 * @Author : 이가영
	 * @param params
	 * @return
	 */
	public List<SyStandardCategory> getStandardCategoryList(SyStandardCategory params) throws Exception {

		// 사용 여부가 null로 들어올 경우 사용인 카테고리만 조회
		if (!UtilsText.equals(params.getIsMasterPage(), Const.BOOLEAN_TRUE)) {
			params.setUseYn(Const.BOOLEAN_TRUE);
		}

		List<SyStandardCategory> list = syStandardCategoryDao.selectStandardCategoryList(params);
		return list;
	}

	/**
	 * @Desc : 표준 카테고리 등록
	 * @Method Name : insertStandardCategory
	 * @Date : 2019. 3. 7.
	 * @Author : 이가영
	 * @param params
	 * @return
	 */
	public void insertStandardCategory(SyStandardCategory parameter) throws Exception {

		UserDetails user = LoginManager.getUserDetails();
		parameter.setModerNo(user.getAdminNo());
		parameter.setRgsterNo(user.getAdminNo());

		syStandardCategoryDao.insertStandardCategory(parameter);
	}

	/**
	 * @Desc : 표준 카테고리 수정
	 * @Method Name : updateStandardCategory
	 * @Date : 2019. 3. 7.
	 * @Author : 이가영
	 * @param params
	 * @return
	 */
	public void updateStandardCategory(SyStandardCategory parameter) throws Exception {

		UserDetails user = LoginManager.getUserDetails();
		parameter.setModerNo(user.getAdminNo());

		syStandardCategoryDao.updateStandardCategory(parameter);
	}

	/**
	 * @Desc :표준 카테고리 등록/수정
	 * @Method Name : setStandardCategory
	 * @Date : 2019. 2. 13.
	 * @Author : 이가영
	 * @param params
	 * @return
	 */
	public void setStandardCategory(SyStandardCategory syStandardCategory) throws Exception {

		// 등록
		if (UtilsText.equals(syStandardCategory.getStatus(), BaseConst.CRUD_C)) {
			this.insertStandardCategory(syStandardCategory);
		}
		// 수정
		else if (UtilsText.equals(syStandardCategory.getStatus(), BaseConst.CRUD_U)) {
			this.updateStandardCategory(syStandardCategory);
		}

		displayCategoryService.setStandardCategoryReset(syStandardCategory.getStdCtgrNo());

		if (!ObjectUtils.isEmpty(syStandardCategory.getDisplayCategoryList())) {
			for (DpCategory category : syStandardCategory.getDisplayCategoryList()) {
				if (UtilsText.equals(syStandardCategory.getLeafCtgrYn(), Const.BOOLEAN_TRUE)) {
					category.setStdCtgrNo(syStandardCategory.getStdCtgrNo());
				}
				category.setModerNo(syStandardCategory.getModerNo());

				displayCategoryService.setStandardCategory(category);
			}
		}

		// 취급주의사항 등록/수정
		this.setSyHandlingPrecaution(syStandardCategory.getSyHandlingPrecautionList());
		// 취급주의사항 삭제
		this.deleteSyHandlingPrecaution(syStandardCategory);
	}

	/**
	 * @Desc :표준 카테고리 순서 등록/수정
	 * @Method Name : setStandardCategory
	 * @Date : 2019. 2. 13.
	 * @Author : 이가영
	 * @param params
	 * @return
	 */
	public void setStandardCategorySort(Parameter<SyStandardCategory[]> parameter) throws Exception {

		for (SyStandardCategory params : parameter.get()) {
			if (params.getLevel() != null && Integer.parseInt(params.getLevel()) > 0) {
				if (UtilsText.equals(params.getStatus(), Const.CRUD_C)) {
					params.setInsdMgmtInfoText(null);

					this.insertStandardCategory(params);
				} else {

					this.updateStandardCategory(params);
				}
			}
		}
	}

	/**
	 * @Desc : depth별 표준 카테고리 목록 조회 (인자값: 소카테고리 번호)
	 * @Method Name : getStandardCategoryMap
	 * @Date : 2019. 3. 8.
	 * @Author : 이가영
	 * @param params
	 * @return
	 */
	public Map<String, Object> getStandardCategoryMap(SyStandardCategory syStandardCategory) throws Exception {

		Map<String, Object> resultMap = new HashMap<>();

		String noPath = syStandardCategoryDao.selectStandardCategory(syStandardCategory.getStdCtgrNo()).getNoPath();
		String[] arr = noPath.split(" > ");

		resultMap.put("pathArr", arr);

		SyStandardCategory parameter = new SyStandardCategory();

		parameter.setUseYn(Const.BOOLEAN_TRUE); // 사용 가능한 표준 카테고리만 조회
		parameter.setLevel("1");
		if (UtilsText.isNotEmpty(syStandardCategory.getVndrNo())
				&& (!UtilsText.equals("VD10000001", syStandardCategory.getVndrNo())
						&& !UtilsText.equals("VD10000002", syStandardCategory.getVndrNo())
						&& !UtilsText.equals("VD10000003", syStandardCategory.getVndrNo()))) {
			parameter.setVndrNo(syStandardCategory.getVndrNo());
		}
		resultMap.put("1depth", syStandardCategoryDao.selectStandardCategoryList(parameter));
		parameter.setLevel(null);
		parameter.setVndrNo(null);

		for (int i = 0; i < arr.length - 1; i++) {

			parameter.setUpStdCtgrNo(arr[i]);
			resultMap.put(i + 2 + "depth", syStandardCategoryDao.selectStandardCategoryList(parameter));
		}

		return resultMap;
	}

	/**
	 * @Desc : 카테고리 경로 조회
	 * @Method Name : getStandardCategoryPathMap
	 * @Date : 2019. 3. 13.
	 * @Author : 이가영
	 * @param 표준 소카테고리 번호
	 * @return
	 */
	public Map<String, Object> getStandardCategoryPathMap(String stdCtgrNo) throws Exception {

		Map<String, Object> resultMap = new HashMap<>();

		String noPath = syStandardCategoryDao.selectStandardCategory(stdCtgrNo).getNoPath();
		String[] arr = noPath.split(" > ");

		for (int i = 0; i < arr.length; i++) {

			resultMap.put(i + 1 + "depth", syStandardCategoryDao.selectStandardCategory(arr[i]));
		}

		return resultMap;
	}

	/**
	 * @Desc : 표준카테고리 목록 조회(ITEM_CODE 얻기 휘함)
	 * @Method Name : searchSyStanardCategory
	 * @Date : 2019. 4. 5.
	 * @Author : hsjhsj19
	 * @param product
	 * @return
	 * @throws Exception
	 */
	public List<SyStandardCategory> searchSyStanardCategory(PdProduct product) throws Exception {
		SyStandardCategory syStandardCategory = new SyStandardCategory();
		syStandardCategory.setStdCtgrNo(product.getStdCtgrNo());
		return this.syStandardCategoryDao.select(syStandardCategory);
	}

	/**
	 * @Desc : 취급주의사항 리스트 조회
	 * @Method Name : getSyHandlingPrecautionById
	 * @Date : 2019. 5. 14.
	 * @Author : 이가영
	 * @param syHandlingPrecaution
	 * @return
	 */
	public List<SyHandlingPrecaution> getSyHandlingPrecautionById(String stdCtgrNo) throws Exception {
		return syHandlingPrecautionDao.selectSyHandlingPrecautionById(stdCtgrNo);
	}

	/**
	 * @Desc : 취급주의사항 저장
	 * @Method Name : setSyHandlingPrecaution
	 * @Date : 2019. 5. 15.
	 * @Author : 이가영
	 * @param syHandlingPrecaution
	 */
	public void setSyHandlingPrecaution(SyHandlingPrecaution[] syHandlingPrecaution) throws Exception {

		if (syHandlingPrecaution != null) {
			for (SyHandlingPrecaution precaution : syHandlingPrecaution) {

				Boolean isValid = precaution.isValidation();

				if (!isValid) {
					throw new Exception("소재별 취급방법을 올바르게 입력해주세요.");
				}

				UserDetails userDetails = LoginManager.getUserDetails();
				precaution.setModerNo(userDetails.getAdminNo());

				if (precaution.getHandlPrecauSeq() != null) {
					// update
					this.updateSyHandlingPrecaution(precaution);
				} else {
					// insert
					precaution.setRgsterNo(userDetails.getAdminNo());
					this.insertSyHandlingPrecaution(precaution);
				}
			}
		}
	}

	/**
	 * @Desc : 취급주의사항 등록
	 * @Method Name : insertSyHandlingPrecaution
	 * @Date : 2019. 5. 15.
	 * @Author : 이가영
	 * @param syHandlingPrecaution
	 */
	public void insertSyHandlingPrecaution(SyHandlingPrecaution syHandlingPrecaution) throws Exception {
		syHandlingPrecautionDao.insertSyHandlingPrecaution(syHandlingPrecaution);
	}

	/**
	 * @Desc : 취급주의사항 수정
	 * @Method Name : updateSyHandlingPrecaution
	 * @Date : 2019. 5. 15.
	 * @Author : 이가영
	 * @param syHandlingPrecaution
	 */
	public void updateSyHandlingPrecaution(SyHandlingPrecaution syHandlingPrecaution) throws Exception {
		syHandlingPrecautionDao.updateSyHandlingPrecaution(syHandlingPrecaution);
	}

	/**
	 * @Desc : 취급주의사항 삭제
	 * @Method Name : deleteSyHandlingPrecaution
	 * @Date : 2019. 5. 15.
	 * @Author : 이가영
	 * @param syStandardCategory
	 */
	public void deleteSyHandlingPrecaution(SyStandardCategory syStandardCategory) throws Exception {
		if (syStandardCategory.getRemoveSeqs() != null) {
			syHandlingPrecautionDao.deleteSyHandlingPrecaution(syStandardCategory);
		}
	}

	/**
	 * @Desc : 표준카테고리명 조회
	 * @Method Name : getStandardCategoryName
	 * @Date : 2019. 6. 28.
	 * @Author : bje0507
	 * @param syStandardCategory
	 * @return
	 * @throws Exception
	 */
	public String getStandardCategoryName(String stdCtgrNo) throws Exception {

		String stgrName = syStandardCategoryDao.selectStandardCategoryName(stdCtgrNo);

		return stgrName;
	}

	/**
	 * @Desc : 표준 카테고리 번호에 대한 최상위 표준 카테고리 정보 조회
	 * @Method Name : getRootByStdCtgrNo
	 * @Date : 2019. 7. 15.
	 * @Author : tennessee
	 * @param stdCtgrNo
	 * @return
	 * @throws Exception
	 */
	public SyStandardCategory getRootByStdCtgrNo(String stdCtgrNo) throws Exception {
		return this.syStandardCategoryDao.selectRootByStdCtgrNo(stdCtgrNo);
	}

}
