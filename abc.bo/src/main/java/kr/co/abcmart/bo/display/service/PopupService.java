package kr.co.abcmart.bo.display.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.display.model.master.BdPopup;
import kr.co.abcmart.bo.display.model.master.BdPopupDevice;
import kr.co.abcmart.bo.display.model.master.BdPopupDisplayPosition;
import kr.co.abcmart.bo.display.repository.master.BdPopupDao;
import kr.co.abcmart.bo.display.repository.master.BdPopupDeviceDao;
import kr.co.abcmart.bo.display.repository.master.BdPopupDisplayPositionDao;
import kr.co.abcmart.bo.display.vo.BdPopupSearchVO;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.FileUpload;
import kr.co.abcmart.common.util.UtilsSftp;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;
import kr.co.abcmart.util.UtilsDate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PopupService {

	private static final String FILE_PATH = UtilsText.concat(Const.UPLOAD_FILE_PATH, Const.IMG_SRC_DISPLAY_POPUP);

	@Autowired
	private BdPopupDao bdPopupDao;

	@Autowired
	private BdPopupDeviceDao bdPopupDeviceDao;

	@Autowired
	private BdPopupDisplayPositionDao bdPopupDisplayPositionDao;

	/**
	 * 팝업 목록 조회
	 * 
	 * @Desc : 팝업 목록 조회
	 * @Method Name : getPopupList
	 * @Date : 2019. 2. 14
	 * @Author : easyhun
	 * @param pageable
	 * @return
	 */
	public Page<BdPopup> getPopupList(Pageable<BdPopupSearchVO, BdPopup> pageable) throws Exception {

		int count = bdPopupDao.selectBdPopupCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(bdPopupDao.selectBdPopupList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * 팝업 조회
	 * 
	 * @Desc : 팝업 조회
	 * @Method Name : getPopup
	 * @Date : 2019. 2. 14
	 * @Author : easyhun
	 * @param bdPopup
	 * @return
	 * @throws Exception
	 */
	public BdPopup getPopup(BdPopup bdPopup) throws Exception {

		return bdPopupDao.selectBdPopup(bdPopup);

	}

	/**
	 * 팝업 등록
	 * 
	 * @Desc : 팝업 등록
	 * @Method Name : insertPopup
	 * @Date : 2019. 2. 18
	 * @Author : easyhun
	 * @param bdPopup
	 * @throws Exception
	 */
	public void insertPopup(BdPopup bdPopup) throws Exception {

		// 세션 정보 호출
		UserDetails user = LoginManager.getUserDetails();
		bdPopup.setRgsterNo(user.getAdminNo());
		bdPopup.setRgstDtm(new Timestamp(new Date().getTime()));
		bdPopup.setModerNo(user.getAdminNo());
		bdPopup.setModDtm(bdPopup.getRgstDtm());

		// 전시기간 , 특정일 전시 시간
		bdPopup.setDispStartDtm(bdPopup.getParamDispStartDtm());
		bdPopup.setDispEndDtm(bdPopup.getParamDispEndDtm());
		bdPopup.setDispDayStartTime(bdPopup.getParamDayStartTime());
		bdPopup.setDispDayEndTime(bdPopup.getParamDayEndTime());

		bdPopupDao.insertBdPopup(bdPopup);
		Integer popupSeq = bdPopup.getPopupSeq();

		if (popupSeq != null) {
			for (String deviceCode : bdPopup.getDeviceCodes()) {
				FileUpload imageFile = null;
				// String FilePathType = null;

				// PC, MOBILE 구분
				BdPopupDevice paramBdPopupDevice = new BdPopupDevice();
				paramBdPopupDevice = bdPopup.getBdPopupDevice();
				paramBdPopupDevice.setPopupSeq(popupSeq);
				paramBdPopupDevice.setDeviceCode(deviceCode);
				if (UtilsText.equals(deviceCode, CommonCode.DEVICE_PC)) {
					imageFile = bdPopup.getPcImageFile();
					paramBdPopupDevice.setCnnctnType(paramBdPopupDevice.getPcCnnctnType());
					paramBdPopupDevice.setLinkInfo(paramBdPopupDevice.getPcLinkInfo());
					paramBdPopupDevice.setImageName(paramBdPopupDevice.getPcImageName());
					paramBdPopupDevice.setPopupType(paramBdPopupDevice.getPcPopupType());
					paramBdPopupDevice.setPopupXPostn(paramBdPopupDevice.getPcPopupXPostn());
					paramBdPopupDevice.setPopupYPostn(paramBdPopupDevice.getPcPopupYPostn());
					/*
					 * paramBdPopupDevice.setPopupWidthNum(paramBdPopupDevice.getPcPopupWidthNum());
					 * paramBdPopupDevice.setPopupHeightNum(paramBdPopupDevice.getPcPopupHeightNum()
					 * );
					 */
					paramBdPopupDevice.setLinkTargetType(paramBdPopupDevice.getPcTargetType());
					paramBdPopupDevice.setAltrnText(paramBdPopupDevice.getPcArtrnText());
					// FilePathType = "pc";
				} else {
					imageFile = bdPopup.getMoImageFile();
					paramBdPopupDevice.setCnnctnType(paramBdPopupDevice.getMoCnnctnType());
					paramBdPopupDevice.setLinkInfo(paramBdPopupDevice.getMoLinkInfo());
					paramBdPopupDevice.setImageName(paramBdPopupDevice.getMoImageName());
					/*
					 * paramBdPopupDevice.setPopupWidthNum(paramBdPopupDevice.getMoPopupWidthNum());
					 * paramBdPopupDevice.setPopupHeightNum(paramBdPopupDevice.getMoPopupHeightNum()
					 * );
					 */
					paramBdPopupDevice.setLinkTargetType(paramBdPopupDevice.getMoTargetType());
					paramBdPopupDevice.setAltrnText(paramBdPopupDevice.getMoArtrnText());
					// FilePathType = "mobile";
				}

				if (UtilsText.isBlank(paramBdPopupDevice.getLinkInfo())) {
					paramBdPopupDevice.setLinkInfo("/promotion/event/main");
				}

				if (imageFile != null && imageFile.isFileItem()) {

					String fileName = imageFile.getOrgFileName();
					String uploadFileName = UtilsText.concat(String.valueOf(System.currentTimeMillis()), ".",
							FilenameUtils.getExtension(fileName));
					String path = UtilsText.concat(FILE_PATH, "/",
							UtilsDate.today(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS));

					try {
						UtilsSftp.upload(path, uploadFileName, imageFile.getMultiPartFile().getInputStream());
					} catch (Exception e) {
						log.error("{}", e);
						throw new Exception("파일저장에 실패하였습니다.");
					}

					paramBdPopupDevice.setImagePathText(UtilsText.concat(path, uploadFileName));
					paramBdPopupDevice
							.setImageUrl(UtilsText.concat(Const.URL_IMG_HTTPS, "/", path, "/", uploadFileName));
					paramBdPopupDevice.setImageName(fileName);

					if (UtilsText.isBlank(paramBdPopupDevice.getAltrnText())) {
						paramBdPopupDevice.setAltrnText(imageFile.getOrgFileName().split("\\.")[0]);
					}

					bdPopupDeviceDao.insertPopupDevice(paramBdPopupDevice);
				}
			}

			// 전시위치 등록
			Integer popupDispPostnSeq = 1;
			if (bdPopup.getBdPopupDisplayPositionPcArr() != null) {
				for (BdPopupDisplayPosition bdPopupDisplayPosition : bdPopup.getBdPopupDisplayPositionPcArr()) {
					bdPopupDisplayPosition.setPopupDispPostnSeq(popupDispPostnSeq++);
					bdPopupDisplayPosition.setPopupSeq(popupSeq);
					bdPopupDisplayPosition.setRgsterNo(user.getAdminNo());
					bdPopupDisplayPosition.setRgstDtm(new Timestamp(new Date().getTime()));
					bdPopupDisplayPosition.setDeviceCode(CommonCode.DEVICE_PC);
					if (UtilsText.equals(bdPopupDisplayPosition.getPopupDispPostnCode(), "10000")) {
						bdPopupDisplayPosition.setDispPostnUrl("/");
					}
					bdPopupDisplayPositionDao.insert(bdPopupDisplayPosition);
				}
			}

			if (bdPopup.getBdPopupDisplayPositionMoArr() != null) {
				for (BdPopupDisplayPosition bdPopupDisplayPosition : bdPopup.getBdPopupDisplayPositionMoArr()) {
					bdPopupDisplayPosition.setPopupDispPostnSeq(popupDispPostnSeq++);
					bdPopupDisplayPosition.setPopupSeq(popupSeq);
					bdPopupDisplayPosition.setRgsterNo(user.getAdminNo());
					bdPopupDisplayPosition.setRgstDtm(new Timestamp(new Date().getTime()));
					bdPopupDisplayPosition.setDeviceCode(CommonCode.DEVICE_MOBILE);
					if (UtilsText.equals(bdPopupDisplayPosition.getPopupDispPostnCode(), "10000")) {
						bdPopupDisplayPosition.setDispPostnUrl("/");
					}
					bdPopupDisplayPositionDao.insert(bdPopupDisplayPosition);
				}
			}
		}
	}

	/**
	 * 팝업 수정
	 * 
	 * @Desc : 팝업 수정
	 * @Method Name : updatePopup
	 * @Date : 2019. 2. 20
	 * @Author : easyhun
	 * @param bdPopup
	 * @throws Exception
	 */
	public void updatePopup(BdPopup bdPopup) throws Exception {
		Integer popupSeq = bdPopup.getPopupSeq();
		if (popupSeq != null) {
			// 세션 정보 호출
			UserDetails user = LoginManager.getUserDetails();
			bdPopup.setModerNo(user.getAdminNo());
			bdPopup.setModDtm(new Timestamp(new Date().getTime()));

			// 전시기간 , 특정일 전시 시간
			bdPopup.setDispStartDtm(bdPopup.getParamDispStartDtm());
			bdPopup.setDispEndDtm(bdPopup.getParamDispEndDtm());
			bdPopup.setDispDayStartTime(bdPopup.getParamDayStartTime());
			bdPopup.setDispDayEndTime(bdPopup.getParamDayEndTime());

			bdPopupDao.updateBdPopup(bdPopup);

			for (String deviceCode : bdPopup.getDeviceCodes()) {
				FileUpload imageFile = null;
				// String FilePathType = null;

				// PC, MOBILE 구분
				BdPopupDevice paramBdPopupDevice = new BdPopupDevice();
				paramBdPopupDevice = bdPopup.getBdPopupDevice();
				paramBdPopupDevice.setPopupSeq(popupSeq);
				paramBdPopupDevice.setDeviceCode(deviceCode);
				if (UtilsText.equals(deviceCode, CommonCode.DEVICE_PC)) {
					imageFile = bdPopup.getPcImageFile();
					paramBdPopupDevice.setCnnctnType(paramBdPopupDevice.getPcCnnctnType());
					paramBdPopupDevice.setLinkInfo(paramBdPopupDevice.getPcLinkInfo());
					paramBdPopupDevice.setImageName(paramBdPopupDevice.getPcImageName());
					paramBdPopupDevice.setPopupType(paramBdPopupDevice.getPcPopupType());
					paramBdPopupDevice.setPopupXPostn(paramBdPopupDevice.getPcPopupXPostn());
					paramBdPopupDevice.setPopupYPostn(paramBdPopupDevice.getPcPopupYPostn());
					/*
					 * paramBdPopupDevice.setPopupWidthNum(paramBdPopupDevice.getPcPopupWidthNum());
					 * paramBdPopupDevice.setPopupHeightNum(paramBdPopupDevice.getPcPopupHeightNum()
					 * );
					 */
					paramBdPopupDevice.setLinkTargetType(paramBdPopupDevice.getPcTargetType());
					paramBdPopupDevice.setAltrnText(paramBdPopupDevice.getPcArtrnText());
					// FilePathType = "pc";
				} else {
					imageFile = bdPopup.getMoImageFile();
					paramBdPopupDevice.setCnnctnType(paramBdPopupDevice.getMoCnnctnType());
					paramBdPopupDevice.setLinkInfo(paramBdPopupDevice.getMoLinkInfo());
					paramBdPopupDevice.setImageName(paramBdPopupDevice.getMoImageName());
					/*
					 * paramBdPopupDevice.setPopupWidthNum(paramBdPopupDevice.getMoPopupWidthNum());
					 * paramBdPopupDevice.setPopupHeightNum(paramBdPopupDevice.getMoPopupHeightNum()
					 * );
					 */
					paramBdPopupDevice.setLinkTargetType(paramBdPopupDevice.getMoTargetType());
					paramBdPopupDevice.setAltrnText(paramBdPopupDevice.getMoArtrnText());
					// FilePathType = "mobile";
				}

				if (imageFile != null && imageFile.isFileItem()) {
					String fileName = imageFile.getOrgFileName();
					String uploadFileName = UtilsText.concat(String.valueOf(System.currentTimeMillis()), ".",
							FilenameUtils.getExtension(fileName));
					String path = UtilsText.concat(FILE_PATH, "/",
							UtilsDate.today(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS));

					try {
						UtilsSftp.upload(path, uploadFileName, imageFile.getMultiPartFile().getInputStream());
					} catch (Exception e) {
						log.error("{}", e);
						throw new Exception("파일저장에 실패하였습니다.");
					}

					paramBdPopupDevice.setImagePathText(UtilsText.concat(path, uploadFileName));
					paramBdPopupDevice
							.setImageUrl(UtilsText.concat(Const.URL_IMG_HTTPS, "/", path, "/", uploadFileName));
					paramBdPopupDevice.setImageName(fileName);

					if (UtilsText.isBlank(paramBdPopupDevice.getAltrnText())) {
						paramBdPopupDevice.setAltrnText(imageFile.getOrgFileName().split("\\.")[0]);
					}
				}
				bdPopupDeviceDao.updatePopupDevice(paramBdPopupDevice);
			}

			// 전시위치 등록
			bdPopupDisplayPositionDao.deleteByPopupSeq(popupSeq);
			Integer popupDispPostnSeq = 1;
			if (bdPopup.getBdPopupDisplayPositionPcArr() != null) {
				for (BdPopupDisplayPosition bdPopupDisplayPosition : bdPopup.getBdPopupDisplayPositionPcArr()) {
					bdPopupDisplayPosition.setPopupDispPostnSeq(popupDispPostnSeq++);
					bdPopupDisplayPosition.setPopupSeq(popupSeq);
					bdPopupDisplayPosition.setRgsterNo(user.getAdminNo());
					bdPopupDisplayPosition.setRgstDtm(new Timestamp(new Date().getTime()));
					bdPopupDisplayPosition.setDeviceCode(CommonCode.DEVICE_PC);
					if (UtilsText.equals(bdPopupDisplayPosition.getPopupDispPostnCode(), "10000")) {
						bdPopupDisplayPosition.setDispPostnUrl("/");
					}
					bdPopupDisplayPositionDao.insert(bdPopupDisplayPosition);
				}
			}

			if (bdPopup.getBdPopupDisplayPositionMoArr() != null) {
				for (BdPopupDisplayPosition bdPopupDisplayPosition : bdPopup.getBdPopupDisplayPositionMoArr()) {
					bdPopupDisplayPosition.setPopupDispPostnSeq(popupDispPostnSeq++);
					bdPopupDisplayPosition.setPopupSeq(popupSeq);
					bdPopupDisplayPosition.setRgsterNo(user.getAdminNo());
					bdPopupDisplayPosition.setRgstDtm(new Timestamp(new Date().getTime()));
					bdPopupDisplayPosition.setDeviceCode(CommonCode.DEVICE_MOBILE);
					if (UtilsText.equals(bdPopupDisplayPosition.getPopupDispPostnCode(), "10000")) {
						bdPopupDisplayPosition.setDispPostnUrl("/");
					}
					bdPopupDisplayPositionDao.insert(bdPopupDisplayPosition);
				}
			}
		}
	}

	/**
	 * 팝업 삭제
	 * 
	 * @Desc : 팝업 삭제
	 * @Method Name : deletePopup
	 * @Date : 2019. 2. 21
	 * @Author : easyhun
	 * @param bdPopup
	 * @throws Exception
	 */
	public void deletePopup(Integer popupSeq) throws Exception {
		BdPopup bdPopup = new BdPopup();
		bdPopup.setPopupSeq(popupSeq);
		// if (result > 0) {
		bdPopupDeviceDao.deleteByPopupSeq(popupSeq);
		bdPopupDisplayPositionDao.deleteByPopupSeq(popupSeq);
		// }
		int result = bdPopupDao.delete(bdPopup);
	}

	/**
	 * 팝업 디바이스 조회
	 * 
	 * @Desc : 팝업 디바이스 조회
	 * @Method Name : getPopupDeviceList
	 * @Date : 2019. 2. 20
	 * @Author : easyhun
	 * @param bdPopup
	 * @return
	 * @throws Exception
	 */
	public BdPopupDevice getPopupDeviceListByPrimaryKey(BdPopupDevice bdPopupDevice) throws Exception {
		return bdPopupDeviceDao.selectByPrimaryKey(bdPopupDevice);
	}

	/**
	 * 팝업 전시 위치 조회
	 * 
	 * @Desc : 팝업 디바이스 조회
	 * @Method Name : getPopupDisplayPositionList
	 * @Date : 2019. 2. 20
	 * @Author : easyhun
	 * @param bdPopup
	 * @return
	 * @throws Exception
	 */
	public List<BdPopupDisplayPosition> getPopupDisplayPositionListByPopupSeq(Integer popupSeq) throws Exception {
		return bdPopupDisplayPositionDao.selectBdPopupDisplayPositionListByPopupSeq(popupSeq);
	}

}
