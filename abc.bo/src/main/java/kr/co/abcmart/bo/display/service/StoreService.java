package kr.co.abcmart.bo.display.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.display.model.master.CmArea;
import kr.co.abcmart.bo.display.model.master.CmAreaDetail;
import kr.co.abcmart.bo.display.model.master.CmStore;
import kr.co.abcmart.bo.display.model.master.CmStoreService;
import kr.co.abcmart.bo.display.repository.master.CmAreaDao;
import kr.co.abcmart.bo.display.repository.master.CmAreaDetailDao;
import kr.co.abcmart.bo.display.repository.master.CmStoreDao;
import kr.co.abcmart.bo.display.repository.master.CmStoreImageDao;
import kr.co.abcmart.bo.display.repository.master.CmStoreServiceDao;
import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.bo.system.service.CommonCodeService;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.FileUpload;
import kr.co.abcmart.common.util.UtilsSftp;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;
import kr.co.abcmart.util.UtilsDate;
import kr.co.abcmart.util.UtilsText;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StoreService {

	private static final String FILE_PATH = UtilsText.concat(Const.UPLOAD_FILE_PATH, Const.IMG_SRC_DISPLAY_STORE);

	@Autowired
	private CmStoreDao cmStoreDao;

	@Autowired
	private CmStoreServiceDao cmStoreServiceDao;

	@Autowired
	private CmStoreImageDao cmStoreImageDao;

	@Autowired
	private CmAreaDao cmAreaDao;

	@Autowired
	private CmAreaDetailDao cmAreaDetailDao;

	@Autowired
	private CommonCodeService commonCodeService;

	/**
	 * @Desc : 오프라인 매장 정보 조회
	 * @Method Name : selectCmStoreInfo
	 * @Date : 2019. 3. 4.
	 * @Author : flychani@3top.co.kr
	 * @param cmStore
	 * @return
	 */
	public CmStore selectCmStoreInfo(CmStore cmStore) throws Exception {

		return cmStoreDao.selectCmStoreInfo(cmStore);
	}

	/**
	 * @Desc : 오프라인 매장 리스트 조회
	 * @Method Name : selectCmStoreList
	 * @Date : 2019. 3. 18.
	 * @Author : 이가영
	 * @param pageable
	 * @return
	 */
	public Page<CmStore> getCmStoreList(Pageable<CmStore, CmStore> pageable) throws Exception {

		int count = cmStoreDao.selectCmStoreCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(cmStoreDao.selectCmStoreList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 오프라인 매장 상세 조회
	 * @Method Name : getCmStoreDetail
	 * @Date : 2019. 3. 18.
	 * @Author : 이가영
	 * @param cmStore
	 * @return
	 */
	public CmStore getCmStoreDetail(CmStore cmStore) throws Exception {

		return cmStoreDao.selectCmStoreDetail(cmStore);
	}

	/**
	 * @Desc : 오프라인 매장 등록·수정
	 * @Method Name : setCmStore
	 * @Date : 2019. 3. 19.
	 * @Author : 이가영
	 * @param cmStore
	 */
	public void setCmStore(CmStore cmStore) throws Exception {

		if (cmStore.getStoreNo() != null) {
			this.updateCmStore(cmStore);
		} else {
			this.insertCmStore(cmStore);
		}

		this.updateCmStoreService(cmStore);

	}

	/**
	 * @Desc : 오프라인 매장 등록
	 * @Method Name : insertCmStore
	 * @Date : 2019. 3. 19.
	 * @Author : lee
	 * @param cmStore
	 */
	public void insertCmStore(CmStore cmStore) throws Exception {

		UserDetails user = LoginManager.getUserDetails();
		cmStore.setModerNo(user.getAdminNo());
		cmStore.setRgsterNo(user.getAdminNo());

		FileUpload pcImageFile = cmStore.getPcImageFile();
		FileUpload mobileImageFile = cmStore.getMobileImageFile();

		if (pcImageFile != null && pcImageFile.isFileItem()) {

			String fileName = pcImageFile.getOrgFileName();
			String uploadFileName = UtilsText.concat(String.valueOf(System.currentTimeMillis()), ".",
					FilenameUtils.getExtension(fileName));
			String path = UtilsText.concat(FILE_PATH, "/", UtilsDate.today(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS));

			try {
				UtilsSftp.upload(path, uploadFileName, pcImageFile.getMultiPartFile().getInputStream());
			} catch (Exception e) {
				log.error("{}", e);
				throw new Exception("파일저장에 실패하였습니다.");
			}

			cmStore.setPcImagePathText(UtilsText.concat(path, uploadFileName));
			cmStore.setPcImageUrl(UtilsText.concat(Const.URL_IMG_HTTPS, "/", path, "/", uploadFileName));
			cmStore.setPcImageName(fileName);

			if (UtilsText.isBlank(cmStore.getPcAltrnText())) {
				cmStore.setPcAltrnText(fileName.split("\\.")[0]);
			}

		}

		if (mobileImageFile != null && mobileImageFile.isFileItem()) {

			String fileName = mobileImageFile.getOrgFileName();
			String uploadFileName = UtilsText.concat(String.valueOf(System.currentTimeMillis()), ".",
					FilenameUtils.getExtension(fileName));
			String path = UtilsText.concat(FILE_PATH, "/", UtilsDate.today(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS));

			try {
				UtilsSftp.upload(path, uploadFileName, mobileImageFile.getMultiPartFile().getInputStream());
			} catch (Exception e) {
				log.error("{}", e);
				throw new Exception("파일저장에 실패하였습니다.");
			}

			cmStore.setMobileImagePathText(UtilsText.concat(path, uploadFileName));
			cmStore.setMobileImageUrl(UtilsText.concat(Const.URL_IMG_HTTPS, "/", path, "/", uploadFileName));
			cmStore.setMobileImageName(fileName);

			if (UtilsText.isBlank(cmStore.getMobileAltrnText())) {
				cmStore.setMobileAltrnText(fileName.split("\\.")[0]);
			}
		}

		cmStoreDao.insertCmStore(cmStore);
	}

	/**
	 * @Desc : 오프라인 매장 수정
	 * @Method Name : updateCmStore
	 * @Date : 2019. 3. 19.
	 * @Author : 이가영
	 * @param cmStore
	 */
	public void updateCmStore(CmStore cmStore) throws Exception {

		UserDetails user = LoginManager.getUserDetails();
		cmStore.setModerNo(user.getAdminNo());

		FileUpload pcImageFile = cmStore.getPcImageFile();
		FileUpload mobileImageFile = cmStore.getMobileImageFile();

		if (pcImageFile != null && pcImageFile.isFileItem()) {
			String fileName = pcImageFile.getOrgFileName();
			String uploadFileName = UtilsText.concat(String.valueOf(System.currentTimeMillis()), ".",
					FilenameUtils.getExtension(fileName));
			String path = UtilsText.concat(FILE_PATH, "/", UtilsDate.today(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS));

			try {
				UtilsSftp.upload(path, uploadFileName, pcImageFile.getMultiPartFile().getInputStream());
			} catch (Exception e) {
				log.error("{}", e);
				throw new Exception("파일저장에 실패하였습니다.");
			}

			cmStore.setPcImagePathText(UtilsText.concat(path, uploadFileName));
			cmStore.setPcImageUrl(UtilsText.concat(Const.URL_IMG_HTTPS, "/", path, "/", uploadFileName));
			cmStore.setPcImageName(fileName);

			if (UtilsText.isBlank(cmStore.getPcAltrnText())) {
				cmStore.setPcAltrnText(fileName.split("\\.")[0]);
			}
		} else {
			if (UtilsText.isBlank(cmStore.getPcAltrnText())) {
				cmStore.setPcAltrnText(cmStore.getPcImageName());
			}
		}

		if (mobileImageFile != null && mobileImageFile.isFileItem()) {
			String fileName = mobileImageFile.getOrgFileName();
			String uploadFileName = UtilsText.concat(String.valueOf(System.currentTimeMillis()), ".",
					FilenameUtils.getExtension(fileName));
			String path = UtilsText.concat(FILE_PATH, "/", UtilsDate.today(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS));

			try {
				UtilsSftp.upload(path, uploadFileName, mobileImageFile.getMultiPartFile().getInputStream());
			} catch (Exception e) {
				log.error("{}", e);
				throw new Exception("파일저장에 실패하였습니다.");
			}

			cmStore.setMobileImagePathText(UtilsText.concat(path, uploadFileName));
			cmStore.setMobileImageUrl(UtilsText.concat(Const.URL_IMG_HTTPS, "/", path, "/", uploadFileName));
			cmStore.setMobileImageName(fileName);

			if (UtilsText.isBlank(cmStore.getMobileAltrnText())) {
				cmStore.setMobileAltrnText(fileName.split("\\.")[0]);
			}
		} else {
			if (UtilsText.isBlank(cmStore.getMobileAltrnText())) {
				cmStore.setMobileAltrnText(cmStore.getMobileImageName());
			}
		}

		cmStoreDao.updateCmStore(cmStore);
	}

	/**
	 * @Desc : 매장 제공 서비스 업데이트
	 * @Method Name : updateCmStoreService
	 * @Date : 2019. 3. 19.
	 * @Author : 이가영
	 * @param cmStore
	 */
	public void updateCmStoreService(CmStore cmStore) throws Exception {

		CmStoreService cmStoreService = new CmStoreService();

		cmStoreService.setStoreNo(cmStore.getStoreNo());

		// 기존 매장 제공 서비스 delete
		cmStoreServiceDao.deleteCmStoreService(cmStoreService);

		// 새 매장 제공 서비스 insert
		UserDetails user = LoginManager.getUserDetails();
		cmStoreService.setRgsterNo(user.getAdminNo());
		cmStoreService.setModerNo(user.getAdminNo());

		String[] codes = cmStore.getStoreServiceCode();

		String[] codeFields = { CommonCode.STORE_SERVICE_CODE };

		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				true);

		Map<String, List<SyCodeDetail>> codeList = pair.getSecond();

		List<SyCodeDetail> storeServiceCodeList = codeList.get(CommonCode.STORE_SERVICE_CODE);

		boolean isChecked;
		for (SyCodeDetail storeServiceCode : storeServiceCodeList) {
			isChecked = false;

			for (String code : codes) {
				if (UtilsText.equals(code, storeServiceCode.getCodeDtlNo())) {
					cmStoreService.setPsbltYn(Const.BOOLEAN_TRUE);
					cmStoreService.setStoreServiceCode(code);
					cmStoreServiceDao.insertCmStoreService(cmStoreService);
					isChecked = true;
				}
			}

			if (!isChecked) {
				cmStoreService.setPsbltYn(Const.BOOLEAN_FALSE);
				cmStoreService.setStoreServiceCode(storeServiceCode.getCodeDtlNo());
				cmStoreServiceDao.insertCmStoreService(cmStoreService);
			}
		}
	}

	/**
	 * @Desc : 지역 리스트 조회
	 * @Method Name : selectCmAreaList
	 * @Date : 2019. 3. 18.
	 * @Author : 이가영
	 * @return
	 */
	public List<CmArea> getCmAreaList() throws Exception {

		CmArea cmArea = new CmArea();

		return cmAreaDao.select(cmArea);
	}

	/**
	 * @Desc : 지역 상세 리스트 조회
	 * @Method Name : getCmAreaDetailList
	 * @Date : 2019. 3. 18.
	 * @Author : 이가영
	 * @return
	 */
	public List<CmAreaDetail> getCmAreaDetailList(CmStore cmStore) throws Exception {

		CmAreaDetail cmDetail = new CmAreaDetail();

		String areaNo = cmStore.getAreaNo();

		if (areaNo != null) {
			cmDetail.setAreaNo(areaNo);
		}

		return cmAreaDetailDao.select(cmDetail);
	}

	/**
	 * @Desc : 매장 제공 서비스 리스트 조회
	 * @Method Name : getStoreServiceList
	 * @Date : 2019. 3. 19.
	 * @Author : 이가영
	 * @param cmStoreService
	 * @return
	 */
	public List<CmStoreService> getStoreServiceList(CmStoreService cmStoreService) throws Exception {

		List<CmStoreService> list = cmStoreServiceDao.select(cmStoreService);

		return list;

	}

	/**
	 * @Desc : 오프라인 매장 삭제
	 * @Method Name : deleteCmStore
	 * @Date : 2019. 9. 20.
	 * @Author : 이가영
	 * @param storeNo
	 */
	public void deleteCmStore(String storeNo) throws Exception {

		// 오프라인 매장 제공 서비스 삭제
		cmStoreServiceDao.deleteCmStoreServiceByStoreNo(storeNo);
		// 오프라인 매장 삭제
		cmStoreDao.deleteCmStoreByStoreNo(storeNo);

	}
	
	public List<CmStore> getStoreExcelList(CmStore cmStore)throws Exception{
		List<CmStore> list = cmStoreDao.selectStoreExcelList(cmStore);
		return list;
	}

}
