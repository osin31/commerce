package kr.co.abcmart.bo.display.service;

import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.display.model.master.CmCatalogEvent;
import kr.co.abcmart.bo.display.model.master.CmCatalogEventStore;
import kr.co.abcmart.bo.display.repository.master.CmCatalogEventDao;
import kr.co.abcmart.bo.display.repository.master.CmCatalogEventStoreDao;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.FileUpload;
import kr.co.abcmart.common.util.UtilsSftp;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;
import kr.co.abcmart.util.UtilsDate;
import kr.co.abcmart.util.UtilsText;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CatalogEventService {

	private static final String FILE_PATH = UtilsText.concat(Const.UPLOAD_FILE_PATH, Const.IMG_SRC_DISPLAY_ACONNECT);

	@Autowired
	CmCatalogEventDao cmCatalogEventDao;

	@Autowired
	CmCatalogEventStoreDao cmCatalogEventStoreDao;

	/**
	 * @Desc : 이벤트 목록 조회
	 * @Method Name : getCmCatalogEventList
	 * @Date : 2019. 4. 26.
	 * @Author : 이가영
	 * @param pageable
	 * @return
	 */
	public Page<CmCatalogEvent> getCmCatalogEventList(Pageable<CmCatalogEvent, CmCatalogEvent> pageable)
			throws Exception {

		List<Integer> counts = cmCatalogEventDao.selectCmCatalogEventCount(pageable);

		int count = 0;

		for (Integer integer : counts) {
			count += integer;
		}
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(cmCatalogEventDao.selectCmCatalogEventList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 이벤트 조회
	 * @Method Name : getCmCatalogEvent
	 * @Date : 2019. 4. 26.
	 * @Author : 이가영
	 * @param cmCatalogEvent
	 * @return
	 */
	public CmCatalogEvent getCmCatalogEvent(CmCatalogEvent cmCatalogEvent) throws Exception {

		return cmCatalogEventDao.selectCmCatalogEvent(cmCatalogEvent);
	}

	/**
	 * @Desc : 이벤트 등록
	 * @Method Name : insertCmCatalogEvent
	 * @Date : 2019. 4. 26.
	 * @Author : 이가영
	 * @param cmCatalogEvent
	 */
	public void insertCmCatalogEvent(CmCatalogEvent cmCatalogEvent) throws Exception {

		UserDetails user = LoginManager.getUserDetails();

		cmCatalogEvent.setRgsterNo(user.getAdminNo());
		cmCatalogEvent.setModerNo(user.getAdminNo());

		FileUpload imageFile = cmCatalogEvent.getImageFile();

		if (imageFile != null && imageFile.isFileItem()) {

			String fileName = imageFile.getOrgFileName();
			String uploadFileName = UtilsText.concat(String.valueOf(System.currentTimeMillis()), ".",
					FilenameUtils.getExtension(fileName));
			String path = UtilsText.concat(FILE_PATH, "/", UtilsDate.today(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS));

			try {
				UtilsSftp.upload(path, uploadFileName, imageFile.getMultiPartFile().getInputStream());
			} catch (Exception e) {
				log.error("{}", e);
				throw new Exception("파일저장에 실패하였습니다.");
			}

			cmCatalogEvent.setImagePathText(UtilsText.concat(path, uploadFileName));
			cmCatalogEvent.setImageUrl(UtilsText.concat(Const.URL_IMG_HTTPS, "/", path, "/", uploadFileName));
			cmCatalogEvent.setImageName(fileName);

			if (UtilsText.isBlank(cmCatalogEvent.getAltrnText())) {
				cmCatalogEvent.setAltrnText(fileName.split("\\.")[0]);
			}
		}

		cmCatalogEventDao.insertCmCatalogEvent(cmCatalogEvent);

		// CM_CATALOG_EVENT_STORE update
		if (UtilsText.equals(cmCatalogEvent.getStoreCommonYn(), Const.BOOLEAN_FALSE)) {

			if (cmCatalogEvent.getStoreNoArr() != null && cmCatalogEvent.getStoreNoArr().length > 0) {

				this.setCmCatalogEventStore(cmCatalogEvent);
			}
		}
	}

	/**
	 * @Desc : 이벤트 수정
	 * @Method Name : updateCmCatalogEvent
	 * @Date : 2019. 4. 26.
	 * @Author : 이가영
	 * @param cmCatalogEvent
	 */
	public void updateCmCatalogEvent(CmCatalogEvent cmCatalogEvent) throws Exception {

		UserDetails user = LoginManager.getUserDetails();

		cmCatalogEvent.setModerNo(user.getAdminNo());

		FileUpload imageFile = cmCatalogEvent.getImageFile();

		if (imageFile != null && imageFile.isFileItem()) {
			String fileName = imageFile.getOrgFileName();
			String uploadFileName = UtilsText.concat(String.valueOf(System.currentTimeMillis()), ".",
					FilenameUtils.getExtension(fileName));
			String path = UtilsText.concat(FILE_PATH, "/", UtilsDate.today(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS));

			try {
				UtilsSftp.upload(path, uploadFileName, imageFile.getMultiPartFile().getInputStream());
			} catch (Exception e) {
				log.error("{}", e);
				throw new Exception("파일저장에 실패하였습니다.");
			}

			cmCatalogEvent.setImagePathText(UtilsText.concat(path, uploadFileName));
			cmCatalogEvent.setImageUrl(UtilsText.concat(Const.URL_IMG_HTTPS, "/", path, "/", uploadFileName));
			cmCatalogEvent.setImageName(fileName);

			if (UtilsText.isBlank(cmCatalogEvent.getAltrnText())) {
				cmCatalogEvent.setAltrnText(fileName.split("\\.")[0]);
			}
		} else {
			if (UtilsText.isBlank(cmCatalogEvent.getAltrnText())) {
				cmCatalogEvent.setAltrnText(cmCatalogEvent.getImageName());
			}
		}

		cmCatalogEventDao.updateCmCatalogEvent(cmCatalogEvent);

		// CM_CATALOG_EVENT_STORE update
		if (UtilsText.equals(cmCatalogEvent.getStoreCommonYn(), Const.BOOLEAN_FALSE)) {

			if (cmCatalogEvent.getStoreNoArr() != null && cmCatalogEvent.getStoreNoArr().length > 0) {

				this.setCmCatalogEventStore(cmCatalogEvent);
			}
		}
	}

	/**
	 * @Desc : 카탈로그 이벤트 매장 목록 조회
	 * @Method Name : getCmCatalogEventStoreListByCtlgEventNo
	 * @Date : 2019. 4. 26.
	 * @Author : 이가영
	 * @param ctlgEventNo
	 * @return
	 */
	public List<CmCatalogEventStore> getCmCatalogEventStoreListByCtlgEventNo(String ctlgEventNo) throws Exception {

		return cmCatalogEventStoreDao.selectCmCatalogEventStoreListByCtlgEventNo(ctlgEventNo);
	}

	/**
	 * @Desc : 이벤트 매장 등록
	 * @Method Name : updateCmCatalogEventStore
	 * @Date : 2019. 4. 26.
	 * @Author : 이가영
	 * @param cmCatalogEvent
	 */
	public void setCmCatalogEventStore(CmCatalogEvent cmCatalogEvent) throws Exception {

		cmCatalogEventStoreDao.deleteCmCatalogEventStoreByCtlgEventNo(cmCatalogEvent.getCtlgEventNo());

		cmCatalogEventStoreDao.insertCmCatalogEventStore(cmCatalogEvent);
	}
}
