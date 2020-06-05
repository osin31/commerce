package kr.co.abcmart.bo.promotion.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.promotion.model.master.PrPlanningDisplay;
import kr.co.abcmart.bo.promotion.model.master.PrPlanningDisplayApplyDevice;
import kr.co.abcmart.bo.promotion.model.master.PrPlanningDisplayApplyGrade;
import kr.co.abcmart.bo.promotion.model.master.PrPlanningDisplayApprovalHistory;
import kr.co.abcmart.bo.promotion.model.master.PrPlanningDisplayCorner;
import kr.co.abcmart.bo.promotion.model.master.PrPlanningDisplayCornerProduct;
import kr.co.abcmart.bo.promotion.model.master.PrPlanningDisplayProduct;
import kr.co.abcmart.bo.promotion.model.master.PrPlanningDisplayType;
import kr.co.abcmart.bo.promotion.repository.master.PrPlanningDisplayApplyDeviceDao;
import kr.co.abcmart.bo.promotion.repository.master.PrPlanningDisplayApplyGradeDao;
import kr.co.abcmart.bo.promotion.repository.master.PrPlanningDisplayApprovalHistoryDao;
import kr.co.abcmart.bo.promotion.repository.master.PrPlanningDisplayCornerDao;
import kr.co.abcmart.bo.promotion.repository.master.PrPlanningDisplayCornerProductDao;
import kr.co.abcmart.bo.promotion.repository.master.PrPlanningDisplayDao;
import kr.co.abcmart.bo.promotion.repository.master.PrPlanningDisplayDeviceDao;
import kr.co.abcmart.bo.promotion.repository.master.PrPlanningDisplayProductDao;
import kr.co.abcmart.bo.promotion.repository.master.PrPlanningDisplayTypeDao;
import kr.co.abcmart.bo.promotion.vo.PrExhibitionPlanningVO;
import kr.co.abcmart.bo.system.repository.master.SyCodeDetailDao;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.FileUpload;
import kr.co.abcmart.common.request.Parameter;
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
public class PlanningDisplayService {

	private static final String FILE_PATH = UtilsText.concat(Const.UPLOAD_FILE_PATH,
			Const.IMG_SRC_PROMOTION_PLANNINGDISPLAY);

	@Autowired
	private PrPlanningDisplayDao prPlanningDisplayDao;

	@Autowired
	private PrPlanningDisplayApplyDeviceDao prPlanningDisplayApplyDeviceDao;

	@Autowired
	private PrPlanningDisplayApplyGradeDao prPlanningDisplayApplyGradeDao;

	@Autowired
	private PrPlanningDisplayDeviceDao prPlanningDisplayDeviceDao;

	@Autowired
	private PrPlanningDisplayTypeDao prPlanningDisplayTypeDao;

	@Autowired
	private PrPlanningDisplayCornerDao prPlanningDisplayCornerDao;

	@Autowired
	private PrPlanningDisplayCornerProductDao prPlanningDisplayCornerProductDao;

	@Autowired
	private PrPlanningDisplayApprovalHistoryDao prPlanningDisplayApprovalHistoryDao;

	@Autowired
	private PrPlanningDisplayProductDao prPlanningDisplayProductDao;

	@Autowired
	private SyCodeDetailDao syCodeDetailDao;

	/**
	 * 
	 * @Desc : 기획전 리스트 조회
	 * @Method Name : getPrPlanningDisplayList
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<PrPlanningDisplay> getPrPlanningDisplayList(Pageable<PrPlanningDisplay, PrPlanningDisplay> pageable)
			throws Exception {

		int count = prPlanningDisplayDao.selectPrPlanningDisplayCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(prPlanningDisplayDao.selectPrPlanningDisplayList(pageable));
		}

		return pageable.getPage();

	}

	/**
	 * 
	 * @Desc : 기획전 상세 조회
	 * @Method Name : getPrPlanningDisplayDetail
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param prPlanningDisplay
	 * @return
	 * @throws Exception
	 */
	public PrPlanningDisplay getPrPlanningDisplayDetail(PrPlanningDisplay prPlanningDisplay) throws Exception {

		return prPlanningDisplayDao.selectPrPlanningDisplayDetail(prPlanningDisplay);
	}

	/**
	 * 
	 * @Desc : 기획전 저장
	 * @Method Name : insertPrPlanningDisplay
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param prPlanningDisplay
	 * @throws Exception
	 */
	public void insertPrPlanningDisplay(PrPlanningDisplay prPlanningDisplay) throws Exception {

		/** files */
		// pc image
		FileUpload pcImageFile = prPlanningDisplay.getPcImageFile();
		// mobile image
		FileUpload mobileImageFile = prPlanningDisplay.getMobileImageFile();
		// movie image
		FileUpload movieImageFile = prPlanningDisplay.getMovieImageFile();
		// movie
		FileUpload movieFile = prPlanningDisplay.getMovieFile();

		// PC 이미지 업로드
		if (pcImageFile != null && pcImageFile.isFileItem()) {

			String fileName = pcImageFile.getOrgFileName();
			String uploadFileName = UtilsText.concat(String.valueOf(System.currentTimeMillis()), ".",
					FilenameUtils.getExtension(fileName));
			String path = UtilsText.concat(FILE_PATH, "/",
					UtilsDate.today(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS));

			try {
				UtilsSftp.upload(path, uploadFileName, pcImageFile.getMultiPartFile().getInputStream());
			} catch (Exception e) {
				log.error("{}", e);
				throw new Exception("파일저장에 실패하였습니다.");
			}

			prPlanningDisplay.setPcBannerImagePathText(UtilsText.concat(path, uploadFileName));
			prPlanningDisplay
					.setPcBannerImageUrl(UtilsText.concat(Const.URL_IMG_HTTPS, "/", path, "/", uploadFileName));
			prPlanningDisplay.setPcBannerImageName(fileName);

			if (UtilsText.isBlank(prPlanningDisplay.getPcBannerImageAltrnText())) {
				prPlanningDisplay.setPcBannerImageAltrnText(pcImageFile.getOrgFileName().split("\\.")[0]);
			}
		}
		// 모바일 이미지 업로드
		if (mobileImageFile != null && mobileImageFile.isFileItem()) {

			String fileName = mobileImageFile.getOrgFileName();
			String uploadFileName = UtilsText.concat(String.valueOf(System.currentTimeMillis()), ".",
					FilenameUtils.getExtension(fileName));
			String path = UtilsText.concat(FILE_PATH, "/",
					UtilsDate.today(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS));

			try {
				UtilsSftp.upload(path, uploadFileName, mobileImageFile.getMultiPartFile().getInputStream());
			} catch (Exception e) {
				log.error("{}", e);
				throw new Exception("파일저장에 실패하였습니다.");
			}

			prPlanningDisplay.setMobileBannerImagePathText(UtilsText.concat(path, uploadFileName));
			prPlanningDisplay
					.setMobileBannerImageUrl(UtilsText.concat(Const.URL_IMG_HTTPS, "/", path, "/", uploadFileName));
			prPlanningDisplay.setMobileBannerImageName(fileName);

			if (UtilsText.isBlank(prPlanningDisplay.getMobileBannerImageAltrnText())) {
				prPlanningDisplay.setMobileBannerImageAltrnText(mobileImageFile.getOrgFileName().split("\\.")[0]);
			}
		}

		// 동영상 썸네일 업로드
		if (movieImageFile != null && movieImageFile.isFileItem()) {

			String fileName = movieImageFile.getOrgFileName();
			String uploadFileName = UtilsText.concat(String.valueOf(System.currentTimeMillis()), ".",
					FilenameUtils.getExtension(fileName));
			String path = UtilsText.concat(FILE_PATH, "/",
					UtilsDate.today(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS));

			try {
				UtilsSftp.upload(path, uploadFileName, movieImageFile.getMultiPartFile().getInputStream());
			} catch (Exception e) {
				log.error("{}", e);
				throw new Exception("파일저장에 실패하였습니다.");
			}

			prPlanningDisplay.setMovieImagePathText(UtilsText.concat(path, uploadFileName));
			prPlanningDisplay.setMovieImageUrl(UtilsText.concat(Const.URL_IMG_HTTPS, "/", path, "/", uploadFileName));
			prPlanningDisplay.setMovieImageName(fileName);

			if (UtilsText.isBlank(prPlanningDisplay.getMovieImageAltrnText())) {
				prPlanningDisplay.setMovieImageAltrnText(movieImageFile.getOrgFileName().split("\\.")[0]);
			}
		}

		// 동영상 업로드
		if (movieFile != null && movieFile.isFileItem()) {
			String fileName = movieFile.getOrgFileName();
			String uploadFileName = UtilsText.concat(String.valueOf(System.currentTimeMillis()), ".",
					FilenameUtils.getExtension(fileName));
			String path = UtilsText.concat(FILE_PATH, "/",
					UtilsDate.today(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS));

			try {
				UtilsSftp.upload(path, uploadFileName, movieFile.getMultiPartFile().getInputStream());
			} catch (Exception e) {
				log.error("{}", e);
				throw new Exception("파일저장에 실패하였습니다.");
			}

			prPlanningDisplay.setMoviePathText(UtilsText.concat(path, uploadFileName));
			prPlanningDisplay.setMovieUrl(UtilsText.concat(Const.URL_IMG_HTTPS, "/", path, "/", uploadFileName));
			prPlanningDisplay.setMovieName(fileName);
		}
		/** files */
		log.error("##insertPrPlanningDisplay.prPlanningDisplay" + prPlanningDisplay.toString());
		// 기획전 정보 저장
		prPlanningDisplayDao.insertPrPlanningDisplay(prPlanningDisplay);

		// 저장한 기획전 번호
		String plndpNo = prPlanningDisplay.getPlndpNo();

		// 기획전 대상 회원 정보 저장
		PrPlanningDisplayApplyGrade prPlanningDisplayApplyGrade = new PrPlanningDisplayApplyGrade();
		prPlanningDisplayApplyGrade.setPlndpNo(plndpNo);

		if (prPlanningDisplay.getMemberTypeCodeArr() != null) {
			for (String memberTypeCode : prPlanningDisplay.getMemberTypeCodeArr()) {
				prPlanningDisplayApplyGrade.setMemberTypeCode(memberTypeCode);

				if (UtilsText.equals(memberTypeCode, CommonCode.MEMBER_TYPE_CODE_MEMBERSHIP)
						&& prPlanningDisplay.getMbshpGradeCodeArr() != null) {

					for (String mbshpGradeCode : prPlanningDisplay.getMbshpGradeCodeArr()) {
						prPlanningDisplayApplyGrade.setMbshpGradeCode(mbshpGradeCode);

						prPlanningDisplayApplyGradeDao.insertPrPlanningDisplayApplyGrade(prPlanningDisplayApplyGrade);
					}

					prPlanningDisplayApplyGrade.setMbshpGradeCode(null);
				} else {
					prPlanningDisplayApplyGradeDao.insertPrPlanningDisplayApplyGrade(prPlanningDisplayApplyGrade);
				}
			}
		}

		// 기획전 대상 디바이스 정보 저장
		PrPlanningDisplayApplyDevice prPlanningDisplayApplyDevice = new PrPlanningDisplayApplyDevice();
		prPlanningDisplayApplyDevice.setPlndpNo(plndpNo);

		if (prPlanningDisplay.getDeviceCodeArr() != null) {
			for (String deviceCode : prPlanningDisplay.getDeviceCodeArr()) {
				prPlanningDisplayApplyDevice.setDeviceCode(deviceCode);

				prPlanningDisplayApplyDeviceDao.insertPrPlanningDisplayApplyDevice(prPlanningDisplayApplyDevice);
			}
		}

		// 기획전 타입 정보 저장
		PrPlanningDisplayType prPlanningDisplayType = new PrPlanningDisplayType();
		prPlanningDisplayType.setPlndpNo(plndpNo);

		if (prPlanningDisplay.getPlndpTypeCodeArr() != null) {
			for (String plndpTypeCode : prPlanningDisplay.getPlndpTypeCodeArr()) {
				prPlanningDisplayType.setPlndpTypeCode(plndpTypeCode);

				prPlanningDisplayTypeDao.insertPrPlanningDisplayType(prPlanningDisplayType);
			}
		}

		// 기획전 승인 이력 저장
		UserDetails user = LoginManager.getUserDetails();

		if (UtilsText.equals(user.getUpAuthNo(), Const.ROLE_VENDER_GROUP)
				&& prPlanningDisplay.getPlndpStatCode() == "10001") {
			PrPlanningDisplayApprovalHistory history = new PrPlanningDisplayApprovalHistory();

			history.setPlndpNo(plndpNo);
			history.setPlndpStatCode(prPlanningDisplay.getPlndpStatCode());
			history.setReqtrNo(user.getAdminNo());
			history.setReqDtm(new Timestamp(new Date().getTime()));

			prPlanningDisplayApprovalHistoryDao.insertPrPlanningDisplayApprovalHistory(history);
		}

	}

	/**
	 * 
	 * @Desc : 기획전 수정
	 * @Method Name : updatePrPlanningDisplay
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param prPlanningDisplay
	 * @param isManage
	 * @throws Exception
	 */
	public void updatePrPlanningDisplay(PrPlanningDisplay prPlanningDisplay, boolean isManage) throws Exception {

		String plndpNo = prPlanningDisplay.getPlndpNo();

		// 기획전 정보 관리 페이지일 경우
		if (!isManage) {
			// 기획전 대상 회원 정보 저장
			prPlanningDisplayApplyGradeDao.deletePrPlanningDisplayApplyGradeByPlndpNo(plndpNo);
			PrPlanningDisplayApplyGrade prPlanningDisplayApplyGrade = new PrPlanningDisplayApplyGrade();
			prPlanningDisplayApplyGrade.setPlndpNo(plndpNo);

			if (prPlanningDisplay.getMemberTypeCodeArr() != null) {
				for (String memberTypeCode : prPlanningDisplay.getMemberTypeCodeArr()) {
					prPlanningDisplayApplyGrade.setMemberTypeCode(memberTypeCode);

					if (UtilsText.equals(memberTypeCode, CommonCode.MEMBER_TYPE_CODE_MEMBERSHIP)
							&& prPlanningDisplay.getMbshpGradeCodeArr() != null) {

						for (String mbshpGradeCode : prPlanningDisplay.getMbshpGradeCodeArr()) {
							prPlanningDisplayApplyGrade.setMbshpGradeCode(mbshpGradeCode);

							prPlanningDisplayApplyGradeDao
									.insertPrPlanningDisplayApplyGrade(prPlanningDisplayApplyGrade);
						}

						prPlanningDisplayApplyGrade.setMbshpGradeCode(null);
					} else {
						prPlanningDisplayApplyGradeDao.insertPrPlanningDisplayApplyGrade(prPlanningDisplayApplyGrade);
					}
				}
			}

			// 기획전 대상 디바이스 정보 저장
			prPlanningDisplayApplyDeviceDao.deletePrPlanningDisplayApplyDeviceByPlndpNo(plndpNo);
			PrPlanningDisplayApplyDevice prPlanningDisplayApplyDevice = new PrPlanningDisplayApplyDevice();
			prPlanningDisplayApplyDevice.setPlndpNo(plndpNo);

			if (prPlanningDisplay.getDeviceCodeArr() != null) {
				for (String deviceCode : prPlanningDisplay.getDeviceCodeArr()) {
					prPlanningDisplayApplyDevice.setDeviceCode(deviceCode);

					prPlanningDisplayApplyDeviceDao.insertPrPlanningDisplayApplyDevice(prPlanningDisplayApplyDevice);
				}
			}

			// 기획전 타입 정보 저장
			prPlanningDisplayTypeDao.deletePrPlanningDisplayTypeListByPlndpNo(plndpNo);
			PrPlanningDisplayType prPlanningDisplayType = new PrPlanningDisplayType();
			prPlanningDisplayType.setPlndpNo(plndpNo);

			if (prPlanningDisplay.getPlndpTypeCodeArr() != null) {
				for (String plndpTypeCode : prPlanningDisplay.getPlndpTypeCodeArr()) {
					prPlanningDisplayType.setPlndpTypeCode(plndpTypeCode);

					prPlanningDisplayTypeDao.insertPrPlanningDisplayType(prPlanningDisplayType);
				}
			}

			// 기획전 이력 저장
			UserDetails user = LoginManager.getUserDetails();

			if (UtilsText.equals(user.getUpAuthNo(), Const.ROLE_VENDER_GROUP)
					&& prPlanningDisplay.getPlndpStatCode() == "10001") {
				PrPlanningDisplayApprovalHistory history = new PrPlanningDisplayApprovalHistory();

				history.setPlndpNo(plndpNo);
				history.setPlndpStatCode(prPlanningDisplay.getPlndpStatCode());
				history.setReqtrNo(user.getAdminNo());
				history.setReqDtm(new Timestamp(new Date().getTime()));

				prPlanningDisplayApprovalHistoryDao.insertPrPlanningDisplayApprovalHistory(history);
			}

			/** files */
			// pc image
			FileUpload pcImageFile = prPlanningDisplay.getPcImageFile();
			// mobile image
			FileUpload mobileImageFile = prPlanningDisplay.getMobileImageFile();
			// movie image
			FileUpload movieImageFile = prPlanningDisplay.getMovieImageFile();
			// movie
			FileUpload movieFile = prPlanningDisplay.getMovieFile();

			if (pcImageFile != null && pcImageFile.isFileItem()) {

				String fileName = pcImageFile.getOrgFileName();
				String uploadFileName = UtilsText.concat(String.valueOf(System.currentTimeMillis()), ".",
						FilenameUtils.getExtension(fileName));
				String path = UtilsText.concat(FILE_PATH, "/",
						UtilsDate.today(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS));

				try {
					UtilsSftp.upload(path, uploadFileName, pcImageFile.getMultiPartFile().getInputStream());
				} catch (Exception e) {
					log.error("{}", e);
					throw new Exception("파일저장에 실패하였습니다.");
				}

				prPlanningDisplay.setPcBannerImagePathText(UtilsText.concat(path, uploadFileName));
				prPlanningDisplay
						.setPcBannerImageUrl(UtilsText.concat(Const.URL_IMG_HTTPS, "/", path, "/", uploadFileName));
				prPlanningDisplay.setPcBannerImageName(fileName);

				if (UtilsText.isBlank(prPlanningDisplay.getPcBannerImageAltrnText())) {
					prPlanningDisplay.setPcBannerImageAltrnText(pcImageFile.getOrgFileName().split("\\.")[0]);
				}
			}
			if (mobileImageFile != null && mobileImageFile.isFileItem()) {

				String fileName = mobileImageFile.getOrgFileName();
				String uploadFileName = UtilsText.concat(String.valueOf(System.currentTimeMillis()), ".",
						FilenameUtils.getExtension(fileName));
				String path = UtilsText.concat(FILE_PATH, "/",
						UtilsDate.today(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS));

				try {
					UtilsSftp.upload(path, uploadFileName, mobileImageFile.getMultiPartFile().getInputStream());
				} catch (Exception e) {
					log.error("{}", e);
					throw new Exception("파일저장에 실패하였습니다.");
				}

				prPlanningDisplay.setMobileBannerImagePathText(UtilsText.concat(path, uploadFileName));
				prPlanningDisplay
						.setMobileBannerImageUrl(UtilsText.concat(Const.URL_IMG_HTTPS, "/", path, "/", uploadFileName));
				prPlanningDisplay.setMobileBannerImageName(fileName);

				if (UtilsText.isBlank(prPlanningDisplay.getMobileBannerImageAltrnText())) {
					prPlanningDisplay.setMobileBannerImageAltrnText(mobileImageFile.getOrgFileName().split("\\.")[0]);
				}
			}
			if (movieImageFile != null && movieImageFile.isFileItem()) {

				String fileName = movieImageFile.getOrgFileName();
				String uploadFileName = UtilsText.concat(String.valueOf(System.currentTimeMillis()), ".",
						FilenameUtils.getExtension(fileName));
				String path = UtilsText.concat(FILE_PATH, "/",
						UtilsDate.today(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS));

				try {
					UtilsSftp.upload(path, uploadFileName, movieImageFile.getMultiPartFile().getInputStream());
				} catch (Exception e) {
					log.error("{}", e);
					throw new Exception("파일저장에 실패하였습니다.");
				}

				prPlanningDisplay.setMovieImagePathText(UtilsText.concat(path, uploadFileName));
				prPlanningDisplay
						.setMovieImageUrl(UtilsText.concat(Const.URL_IMG_HTTPS, "/", path, "/", uploadFileName));
				prPlanningDisplay.setMovieImageName(fileName);

				if (UtilsText.isBlank(prPlanningDisplay.getMovieImageAltrnText())) {
					prPlanningDisplay.setMovieImageAltrnText(movieImageFile.getOrgFileName().split("\\.")[0]);
				}
			}
			if (movieFile != null && movieFile.isFileItem()) {
				String fileName = movieFile.getOrgFileName();
				String uploadFileName = UtilsText.concat(String.valueOf(System.currentTimeMillis()), ".",
						FilenameUtils.getExtension(fileName));
				String path = UtilsText.concat(FILE_PATH, "/",
						UtilsDate.today(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS));

				try {
					UtilsSftp.upload(path, uploadFileName, movieFile.getMultiPartFile().getInputStream());
				} catch (Exception e) {
					log.error("{}", e);
					throw new Exception("파일저장에 실패하였습니다.");
				}

				prPlanningDisplay.setMoviePathText(UtilsText.concat(path, uploadFileName));
				prPlanningDisplay.setMovieUrl(UtilsText.concat(Const.URL_IMG_HTTPS, "/", path, "/", uploadFileName));
				prPlanningDisplay.setMovieName(fileName);
			}
			/** files */
		} else { // 기획전 상품 관리 페이지일 경우
			// 임시저장
		}

		prPlanningDisplayDao.updatePrPlanningDisplay(prPlanningDisplay);

	}

	/**
	 * 
	 * @Desc : 에디터 이미지 업로드
	 * @Method Name : uploadImageByEditor
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param upload
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> uploadImageByEditor(FileUpload upload) throws Exception {

		Map<String, Object> result = new HashMap<>();

		String fileName = upload.getOrgFileName();
		String uploadFileName = UtilsText.concat(String.valueOf(System.currentTimeMillis()), ".",
				FilenameUtils.getExtension(fileName));
		String path = UtilsText.concat(FILE_PATH, "/", UtilsDate.today(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS));

		try {
			UtilsSftp.upload(path, uploadFileName, upload.getMultiPartFile().getInputStream());
		} catch (Exception e) {
			log.error("{}", e);
			throw new Exception("파일저장에 실패하였습니다.");
		}

		result.put("uploaded", 1);
		result.put("url", UtilsText.concat(Const.URL_IMG_HTTPS, "/", path, "/", uploadFileName));
		result.put("fileName", fileName);

		return result;
	}

	/**
	 * 
	 * @Desc : 기획전 삭제
	 * @Method Name : deletePrPlanningDisplay
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param prPlanningDisplay
	 * @throws Exception
	 */
	public void deletePrPlanningDisplay(PrPlanningDisplay prPlanningDisplay) throws Exception {

		String plndpNo = prPlanningDisplay.getPlndpNo();

		// 1) 디바이스 삭제
		prPlanningDisplayApplyDeviceDao.deletePrPlanningDisplayApplyDeviceByPlndpNo(plndpNo);
		// 2) 등급 삭제
		prPlanningDisplayApplyGradeDao.deletePrPlanningDisplayApplyGradeByPlndpNo(plndpNo);
		// 3) 유형 삭제
		prPlanningDisplayTypeDao.deletePrPlanningDisplayTypeListByPlndpNo(plndpNo);
		// 4) 코너-상품 삭제
		prPlanningDisplayCornerProductDao.deletePrPlanningDisplayCornerProductByPlndpNo(plndpNo);
		// 5) 코너 삭제
		prPlanningDisplayCornerDao.deletePrPlanningDisplayCornerByPlndpNo(plndpNo);
		// 6) 승인 내역 삭제
		prPlanningDisplayApprovalHistoryDao.deletePrPlanningDisplayApprovalHistorayByPlndpNo(plndpNo);
		// 7) 상품 삭제
		PrPlanningDisplayProduct prdt = new PrPlanningDisplayProduct();
		prdt.setPlndpNo(plndpNo);
		prPlanningDisplayProductDao.deletePrPlanningDisplayProduct(prdt);
		// 최종) 기획전 삭제
		prPlanningDisplayDao.delete(prPlanningDisplay);
	}

	/**
	 * 
	 * @Desc : 기획전 대상 디바이스 리스트 조회
	 * @Method Name : getPrPlanningDisplayApplyDeviceListByPlndpNo
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param plndpNo
	 * @return
	 * @throws Exception
	 */
	public List<PrPlanningDisplayApplyDevice> getPrPlanningDisplayApplyDeviceListByPlndpNo(String plndpNo)
			throws Exception {

		return prPlanningDisplayApplyDeviceDao.selectPrPlanningDisplayApplyDeviceListByPlndpNo(plndpNo);
	}

	/**
	 * 
	 * @Desc : 기획전 대상 회원 리스트 조회
	 * @Method Name : getPrPlanningDisplayApplyGradeListByPlndpNo
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param plndpNo
	 * @return
	 * @throws Exception
	 */
	public List<PrPlanningDisplayApplyGrade> getPrPlanningDisplayApplyGradeListByPlndpNo(String plndpNo)
			throws Exception {

		return prPlanningDisplayApplyGradeDao.selectPrPlanningDisplayApplyGradeListByPlndpNo(plndpNo);
	}

	/**
	 * 
	 * @Desc : 기획전 타입 리스트 조회
	 * @Method Name : getPrPlanningDisplayTypeListByPlndpNo
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param plndpNo
	 * @return
	 * @throws Exception
	 */
	public List<PrPlanningDisplayType> getPrPlanningDisplayTypeListByPlndpNo(String plndpNo) throws Exception {

		return prPlanningDisplayTypeDao.selectPrPlanningDisplayTypeListByPlndpNo(plndpNo);
	}

	/**
	 * 
	 * @Desc : 기획전 상품 리스트 조회
	 * @Method Name : getPrPlanningDisplayProductList
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<PrPlanningDisplayProduct> getPrPlanningDisplayProductList(
			Pageable<PrPlanningDisplayProduct, PrPlanningDisplayProduct> pageable) throws Exception {

		int count = prPlanningDisplayProductDao.selectPrPlanningDisplayProductListCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(prPlanningDisplayProductDao.selectPrPlanningDisplayProductList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * 
	 * @Desc : 기획전 상품 저장
	 * @Method Name : insertPrPlanningDisplayProduct
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param prPlanningDisplayProducts
	 * @throws Exception
	 */
	public void insertPrPlanningDisplayProduct(PrPlanningDisplayProduct[] prPlanningDisplayProducts) throws Exception {
		for (PrPlanningDisplayProduct prPlanningDisplayProduct : prPlanningDisplayProducts) {
			if (prPlanningDisplayProductDao.selectByPrimaryKey(prPlanningDisplayProduct) == null) {
				prPlanningDisplayProductDao.insertPrPlanningDisplayProduct(prPlanningDisplayProduct);
			}
		}
	}

	/**
	 * 
	 * @Desc : 기획전 상품 순서 저장
	 * @Method Name : updatePrPlanningDisplayProduct
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param prPlanningDisplayProducts
	 * @throws Exception
	 */
	public void updatePrPlanningDisplayProduct(PrPlanningDisplayProduct[] prPlanningDisplayProducts) throws Exception {

		for (PrPlanningDisplayProduct prPlanningDisplayProduct : prPlanningDisplayProducts) {

			prPlanningDisplayProductDao.updatePrPlanningDisplayProduct(prPlanningDisplayProduct);
		}
	}

	/**
	 * 
	 * @Desc : 기획전 상품 삭제
	 * @Method Name : deletePrPlanningDisplayProduct
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param prPlanningDisplayProducts
	 * @throws Exception
	 */
	public void deletePrPlanningDisplayProduct(PrPlanningDisplayProduct[] prPlanningDisplayProducts) throws Exception {

		for (PrPlanningDisplayProduct prPlanningDisplayProduct : prPlanningDisplayProducts) {

			prPlanningDisplayProductDao.deletePrPlanningDisplayProduct(prPlanningDisplayProduct);
		}
	}

	/**
	 * 
	 * @Desc : 기획전 코너 리스트 조회
	 * @Method Name : getPrPlanningDisplayCornerList
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<PrPlanningDisplayCorner> getPrPlanningDisplayCornerList(
			Pageable<PrPlanningDisplayCorner, PrPlanningDisplayCorner> pageable) throws Exception {

		int count = prPlanningDisplayCornerDao.selectPrPlanningDisplayCornerCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(prPlanningDisplayCornerDao.selectPrPlanningDisplayCornerList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * 
	 * @Desc : 기획전 코너 상세 조회
	 * @Method Name : getPrPlanningDisplayCorner
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param prPlanningDisplayCorner
	 * @return
	 * @throws Exception
	 */
	public PrPlanningDisplayCorner getPrPlanningDisplayCorner(PrPlanningDisplayCorner prPlanningDisplayCorner)
			throws Exception {

		return prPlanningDisplayCornerDao.selectByPrimaryKey(prPlanningDisplayCorner);
	}

	/**
	 * @Desc : 기획전 코너 저장
	 * @Method Name : setPrPlanningDisplayCorners
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param corners
	 * @throws Exception
	 */
	public void setPrPlanningDisplayCorners(PrPlanningDisplayCorner[] corners) throws Exception {

		for (PrPlanningDisplayCorner corner : corners) {
			corner.setPlndpCornerName(UtilsText.unescapeXss(corner.getPlndpCornerName()));

			if (corner.getPlndpCornerSeq() != null) {
				this.updatePrPlanningDisplayCorner(corner);
			} else {
				this.insertPrPlanningDisplayCorner(corner);
			}
		}
	}

	/**
	 * 
	 * @Desc : 기획전 코너 등록
	 * @Method Name : insertPrPlanningDisplayCorner
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param prPlanningDisplayCorner
	 * @throws Exception
	 */
	public void insertPrPlanningDisplayCorner(PrPlanningDisplayCorner prPlanningDisplayCorner) throws Exception {

		UserDetails user = LoginManager.getUserDetails();
		prPlanningDisplayCorner.setModerNo(user.getAdminNo());
		prPlanningDisplayCorner.setRgsterNo(user.getAdminNo());

		FileUpload imageFile = prPlanningDisplayCorner.getImageFile();

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

			prPlanningDisplayCorner.setImagePathText(UtilsText.concat(path, uploadFileName));
			prPlanningDisplayCorner.setImageUrl(UtilsText.concat(Const.URL_IMG_HTTPS, "/", path, "/", uploadFileName));
			prPlanningDisplayCorner.setImageName(fileName);

		}

		prPlanningDisplayCornerDao.insertPrPlanningDisplayCorner(prPlanningDisplayCorner);
	}

	/**
	 * 
	 * @Desc : 기획전 코너 수정
	 * @Method Name : updatePrPlanningDisplayCorner
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param prPlanningDisplayCorner
	 * @throws Exception
	 */
	public void updatePrPlanningDisplayCorner(PrPlanningDisplayCorner prPlanningDisplayCorner) throws Exception {

		UserDetails user = LoginManager.getUserDetails();
		prPlanningDisplayCorner.setModerNo(user.getAdminNo());

		FileUpload imageFile = prPlanningDisplayCorner.getImageFile();

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

			prPlanningDisplayCorner.setImagePathText(UtilsText.concat(path, uploadFileName));
			prPlanningDisplayCorner.setImageUrl(UtilsText.concat(Const.URL_IMG_HTTPS, "/", path, "/", uploadFileName));
			prPlanningDisplayCorner.setImageName(fileName);
		} else {
			prPlanningDisplayCorner.setImagePathText("");
			prPlanningDisplayCorner.setImageUrl("");
			prPlanningDisplayCorner.setImageName("");
			prPlanningDisplayCorner.setAltrnText("");
		}

		prPlanningDisplayCornerDao.updatePrPlanningDisplayCorner(prPlanningDisplayCorner);
	}

	/**
	 * 
	 * @Desc : 기획전 코너 순서 저장
	 * @Method Name : updatePrPlanningDisplayCornerSort
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param prPlanningDisplayCorner
	 * @throws Exception
	 */
	public void updatePrPlanningDisplayCornerSort(PrPlanningDisplayCorner[] prPlanningDisplayCorner) throws Exception {

		UserDetails user = LoginManager.getUserDetails();

		for (PrPlanningDisplayCorner corner : prPlanningDisplayCorner) {

			corner.setModerNo(user.getAdminNo());

			prPlanningDisplayCornerDao.updatePrPlanningDisplayCorner(corner);
		}
	}

	/**
	 * 
	 * @Desc : 기획전 코너 삭제
	 * @Method Name : deletePrPlanningDisplayCorner
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param prPlanningDisplayCorner
	 * @throws Exception
	 */
	public void deletePrPlanningDisplayCorner(PrPlanningDisplayCorner prPlanningDisplayCorner) throws Exception {

		// 기획전 코너 상품 삭제
		PrPlanningDisplayCornerProduct prPlanningDisplayCornerProduct = new PrPlanningDisplayCornerProduct();
		prPlanningDisplayCornerProduct.setPlndpNo(prPlanningDisplayCorner.getPlndpNo());
		prPlanningDisplayCornerProduct.setCornerSeqArr(prPlanningDisplayCorner.getCornerSeqArr());
		prPlanningDisplayCornerProductDao.deletePrPlanningDisplayCornerProduct(prPlanningDisplayCornerProduct);

		// 기획전 코너 삭제
		prPlanningDisplayCornerDao.deletePrPlanningDisplayCorner(prPlanningDisplayCorner);
	}

	/**
	 * 
	 * @Desc : 기획전 승인상태 일괄 변경
	 * @Method Name : updatePrPlanningDisplayStatus
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param params
	 * @param plndpStatCode
	 * @throws Exception
	 */
	public void updatePrPlanningDisplayStatus(PrPlanningDisplay[] params, String plndpStatCode) throws Exception {

		UserDetails user = LoginManager.getUserDetails();

		PrPlanningDisplay display = new PrPlanningDisplay();
		display.setPlndpStatCode(plndpStatCode);
		display.setModerNo(user.getAdminNo());

		List<String> plndpNoList = new ArrayList<String>();

		for (PrPlanningDisplay prPlanningDisplay : params) {

			if (prPlanningDisplay.getPlndpNo() != null) {

				plndpNoList.add(prPlanningDisplay.getPlndpNo());

				PrPlanningDisplayApprovalHistory history = new PrPlanningDisplayApprovalHistory();

				history.setPlndpNo(prPlanningDisplay.getPlndpNo());
				history.setReqtrNo(user.getAdminNo());
				history.setReqDtm(new Timestamp(new Date().getTime()));
				history.setPlndpStatCode(plndpStatCode);

				if (UtilsText.equals(plndpStatCode, "10002")) { // 승인반려 : 반려사유
					history.setReturnRsnText(prPlanningDisplay.getReturnRsnText());
				}
				if (UtilsText.equals(plndpStatCode, "10003")) { // 승인완료 : 승인자 번호, 승인 날짜
					history.setAprverNo(user.getAdminNo());
					history.setAprverDtm(new Timestamp(new Date().getTime()));
				}

				// 기획전 이력 저장
				prPlanningDisplayApprovalHistoryDao.insertPrPlanningDisplayApprovalHistory(history);
			}
		}

		display.setPlndpNoList(plndpNoList);

		// 기획전 update
		prPlanningDisplayDao.updatePrPlanningDisplayStatus(display);
	}

	/**
	 * 
	 * @Desc : 기획전 일괄 삭제
	 * @Method Name : deletePrPlanningDisplayByArray
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param params
	 * @throws Exception
	 */
	public void deletePrPlanningDisplayByArray(PrPlanningDisplay[] params) throws Exception {

		for (PrPlanningDisplay prPlanningDisplay : params) {
			this.deletePrPlanningDisplay(prPlanningDisplay);
		}
	}

	/**
	 * 
	 * @Desc : 기획전 코너 상품 리스트 조회
	 * @Method Name : getPrPlanningDisplayCornerProductList
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<PrPlanningDisplayCornerProduct> getPrPlanningDisplayCornerProductList(
			Pageable<PrPlanningDisplayCornerProduct, PrPlanningDisplayCornerProduct> pageable) throws Exception {

		int count = prPlanningDisplayCornerProductDao.selectPrPlanningDisplayCornerProductListCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(prPlanningDisplayCornerProductDao.selectPrPlanningDisplayCornerProductList(pageable));
		}

		return pageable.getPage();

	}

	/**
	 * 
	 * @Desc : 기획전 코너 상품 저장
	 * @Method Name : setPlanningDisplayProductManagementList
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param array
	 * @throws Exception
	 */
	public void setPlanningDisplayProductManagementList(PrPlanningDisplayCornerProduct[] array) throws Exception {

		if (array != null && array.length > 0) {

			String plndpNo = array[0].getPlndpNo();
			int plndpCornerSeq = array[0].getPlndpCornerSeq();

			// delete all
			// prPlanningDisplayCornerProductDao.deletePrPlanningDisplayCornerProductByPlndpCornerSeq(plndpCornerSeq);

			for (PrPlanningDisplayCornerProduct prPlanningDisplayCornerProduct : array) {

				prPlanningDisplayCornerProduct.setPlndpNo(plndpNo);
				prPlanningDisplayCornerProduct.setPlndpCornerSeq(plndpCornerSeq);

				if (UtilsText.isNotEmpty(prPlanningDisplayCornerProduct.getPrdtNo())) {
					if (!prPlanningDisplayCornerProduct.isValidation()) {
						throw new Exception("노출 순서를 입력해 주세요.");
					}

					boolean newYn = prPlanningDisplayCornerProductDao
							.selectByPrimaryKey(prPlanningDisplayCornerProduct) == null;

					String status = prPlanningDisplayCornerProduct.getStatus();

					if (UtilsText.equals(status, "U") && !newYn) { // update
						prPlanningDisplayCornerProductDao
								.updatePrPlanningDisplayCornerProduct(prPlanningDisplayCornerProduct);
					} else if (UtilsText.equals(status, "I") && newYn) { // insert
						prPlanningDisplayCornerProductDao
								.insertPrPlanningDisplayCornerProduct(prPlanningDisplayCornerProduct);
					} else if (UtilsText.equals(status, "D") && !newYn) { // delete
						prPlanningDisplayCornerProductDao.delete(prPlanningDisplayCornerProduct);
					}
				}
			}
		}
	}

	/**
	 * @Desc : 한 상품에 대한 기획전 정보 조회
	 * @Method Name : getExhibitionPlanningForProduct
	 * @Date : 2019. 4. 23.
	 * @Author : tennessee
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<PrExhibitionPlanningVO> getExhibitionPlanningForProduct(
			Pageable<PrExhibitionPlanningVO, PrExhibitionPlanningVO> pageable) throws Exception {
		Integer count = this.prPlanningDisplayProductDao.selectByExhibitionPlanningForProductTotalCount(pageable);
		pageable.setTotalCount(count);
		if (count > 0) {
			pageable.setContent(this.prPlanningDisplayProductDao.selectByExhibitionPlanningForProduct(pageable));
		}
		return pageable.getPage();
	}

	/**
	 * @Desc : po기획전(진행중,요청,반려) 카운트
	 * @Method Name : getFilePath
	 * @Date : 2019. 6. 10.
	 * @Author : 김영진
	 * @param :
	 * @return
	 */
	public List<Map<String, Object>> getProductInquiryGroupCount() throws Exception {
		PrPlanningDisplay prPlanning = new PrPlanningDisplay();

		prPlanning.setVndrNo(LoginManager.getUserDetails().getVndrNo());

		return this.prPlanningDisplayDao.selectDashboardDisplanningCount(prPlanning);
	}

	/**
	 * 
	 * @Desc : 기획전 승인 완료
	 * @Method Name : setPlanningDisplayStatusApprove
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param parameter
	 * @throws Exception
	 */
	public void setPlanningDisplayStatusApprove(Parameter<PrPlanningDisplay[]> parameter) throws Exception {

		PrPlanningDisplay[] prPlanningDisplay = parameter.get();

		// 기획전 update
		for (PrPlanningDisplay item : prPlanningDisplay) {
			if (item.getPlndpNo() != null) {
				item.setVndrGbnType(Const.VNDR_GBN_TYPE_V);
				item.validate();
				// 대상회원
				if (item.getMemberTypeCodeArr() == null && item.getEmpYn() == null) {
					throw new Exception("기획전 대상을 설정해주세요.");
				}
				this.updatePrPlanningDisplay(item, false);
			}
		}

		this.updatePrPlanningDisplayStatus(prPlanningDisplay, "10003");
	}

}
