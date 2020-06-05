package kr.co.abcmart.bo.display.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.display.model.master.DpWebzine;
import kr.co.abcmart.bo.display.model.master.DpWebzineDetailImage;
import kr.co.abcmart.bo.display.model.master.DpWebzineProduct;
import kr.co.abcmart.bo.display.repository.master.DpWebzineDao;
import kr.co.abcmart.bo.display.repository.master.DpWebzineDetailImageDao;
import kr.co.abcmart.bo.display.repository.master.DpWebzineProductDao;
import kr.co.abcmart.bo.display.vo.DpWebzineSearchVO;
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
public class DisplayContentsService {

	private static final String FILE_PATH = UtilsText.concat(Const.UPLOAD_FILE_PATH, Const.IMG_SRC_DISPLAY_WEBZINE);

	public static final SimpleDateFormat YMD = new SimpleDateFormat("yyyy.MM.dd");

	@Autowired
	private DpWebzineDao dpWebzineDao;

	@Autowired
	private DpWebzineDetailImageDao dpWebzineDetailImageDao;

	@Autowired
	private DpWebzineProductDao dpWebzineProductDao;

	/**
	 * 웹진 지정
	 * 
	 * @Desc : 웹진 지정
	 * @Method Name : saveWebzine
	 * @Date : 2019. 1. 31.
	 * @Author : SANTA
	 * @param dpWebzine
	 * @throws Exception
	 */
	public void insertWebzine(DpWebzine dpWebzine) throws Exception {

		// pc 목록 썸네일
		FileUpload pcImageFile = dpWebzine.getPcImageFile();
		// mo 목록 썸네일
		FileUpload mobileImageFile = dpWebzine.getMobileImageFile();
		// 동영상
		FileUpload movieFile = dpWebzine.getMovieFile();
		// 세션 정보 호출
		UserDetails user = LoginManager.getUserDetails();
		dpWebzine.setRgsterNo(user.getAdminNo());
		dpWebzine.setRgstDtm(new Timestamp(new Date().getTime()));
		dpWebzine.setModerNo(user.getAdminNo());
		dpWebzine.setModDtm(dpWebzine.getRgstDtm());
		if (UtilsText.equals(dpWebzine.getDispYn(), "Y")) {
			dpWebzine.setDispStartYmd(new Timestamp(YMD.parse(dpWebzine.getDispStartYmdDot()).getTime()));
		}

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

			dpWebzine.setPcImagePathText(UtilsText.concat(path, uploadFileName));
			dpWebzine.setPcImageUrl(UtilsText.concat(Const.URL_IMG_HTTPS, "/", path, "/", uploadFileName));
			dpWebzine.setPcImageName(fileName);

			if (UtilsText.isBlank(dpWebzine.getPcAltrnText())) {
				dpWebzine.setPcAltrnText(pcImageFile.getOrgFileName().split("\\.")[0]);
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

			dpWebzine.setMobileImagePathText(UtilsText.concat(path, uploadFileName));
			dpWebzine.setMobileImageUrl(UtilsText.concat(Const.URL_IMG_HTTPS, "/", path, "/", uploadFileName));
			dpWebzine.setMobileImageName(fileName);

			if (UtilsText.isBlank(dpWebzine.getMobileAltrnText())) {
				dpWebzine.setMobileAltrnText(mobileImageFile.getOrgFileName().split("\\.")[0]);
			}
		}

		if (UtilsText.equals(dpWebzine.getMovieUploadYn(), "Y")) {
			if (movieFile.isFileItem()) {

				String fileName = movieFile.getOrgFileName();
				String uploadFileName = UtilsText.concat(String.valueOf(System.currentTimeMillis()), ".",
						FilenameUtils.getExtension(fileName));
				String path = UtilsText.concat(FILE_PATH, "/", UtilsDate.today(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS));

				try {
					UtilsSftp.upload(path, uploadFileName, movieFile.getMultiPartFile().getInputStream());
				} catch (Exception e) {
					log.error("{}", e);
					throw new Exception("파일저장에 실패하였습니다.");
				}

				dpWebzine.setMoviePathText(UtilsText.concat(path, uploadFileName));
				dpWebzine.setMovieUrl(UtilsText.concat(Const.URL_IMG_HTTPS, "/", path, "/", uploadFileName));
				dpWebzine.setMovieName(fileName);

			}
		}

		dpWebzineDao.insert(dpWebzine);

		// 상품 등록
		if (dpWebzine.getDpWebzineProduct() != null && dpWebzine.getDpWebzineProduct().length > 0) {

			for (DpWebzineProduct product : dpWebzine.getDpWebzineProduct()) {
				product.setWbznSeq(dpWebzine.getWbznSeq());
				dpWebzineProductDao.insert(product);
			}
		}

		// 상세 이미지 등록
		if (dpWebzine.getDpWebzineDetailImage() != null && dpWebzine.getDpWebzineDetailImage().length > 0
				&& UtilsText.equals(dpWebzine.getWbznType(), "C")) {
			for (DpWebzineDetailImage dpWebzineDetailImage : dpWebzine.getDpWebzineDetailImage()) {

				String fileName = dpWebzineDetailImage.getAddImageFile().getOrgFileName();
				String uploadFileName = UtilsText.concat(String.valueOf(System.currentTimeMillis()), ".",
						FilenameUtils.getExtension(fileName));
				String path = UtilsText.concat(FILE_PATH, "/", UtilsDate.today(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS));

				try {
					UtilsSftp.upload(path, uploadFileName,
							dpWebzineDetailImage.getAddImageFile().getMultiPartFile().getInputStream());
				} catch (Exception e) {
					log.error("{}", e);
					throw new Exception("파일저장에 실패하였습니다.");
				}

				dpWebzineDetailImage.setImagePathText(UtilsText.concat(path, uploadFileName));
				dpWebzineDetailImage.setImageUrl(UtilsText.concat(Const.URL_IMG_HTTPS, "/", path, "/", uploadFileName));
				dpWebzineDetailImage.setImageName(fileName);

				dpWebzineDetailImage.setWbznSeq(dpWebzine.getWbznSeq());
				dpWebzineDetailImage.setRgsterNo(user.getAdminNo());
				dpWebzineDetailImage.setRgstDtm(new Timestamp(new Date().getTime()));

				if (UtilsText.isBlank(dpWebzineDetailImage.getAltrnText())) {
					dpWebzineDetailImage
							.setAltrnText(dpWebzineDetailImage.getAddImageFile().getOrgFileName().split("\\.")[0]);
				}

				dpWebzineDetailImageDao.insertDpWebzineDetailImage(dpWebzineDetailImage);
			}
		}

		// 등록 후 이미지 업로드 파일 초기화
		dpWebzine.setPcImageFile(null);
		dpWebzine.setMobileImageFile(null);
		dpWebzine.setMovieFile(null);
	}

	/**
	 * @Desc : 웹진 수정
	 * @Method Name : updateWebzine
	 * @Date : 2019. 11. 15.
	 * @Author : lee
	 * @param dpWebzine
	 * @throws Exception
	 */
	public void updateWebzine(DpWebzine dpWebzine) throws Exception {

		// pc 목록 썸네일
		FileUpload pcImageFile = dpWebzine.getPcImageFile();
		// mo 목록 썸네일
		FileUpload mobileImageFile = dpWebzine.getMobileImageFile();
		// 동영상
		FileUpload movieFile = dpWebzine.getMovieFile();

		// 세션 정보 호출
		UserDetails user = LoginManager.getUserDetails();
		dpWebzine.setModerNo(user.getAdminNo());
		dpWebzine.setModDtm(new Timestamp(new Date().getTime()));
		if (UtilsText.equals(dpWebzine.getDispYn(), "Y")) {
			dpWebzine.setDispStartYmd(new Timestamp(YMD.parse(dpWebzine.getDispStartYmdDot()).getTime()));
		}

		DpWebzine savingDpWebzine = getDpWebzine(dpWebzine);

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

			dpWebzine.setPcImagePathText(UtilsText.concat(path, uploadFileName));
			dpWebzine.setPcImageUrl(UtilsText.concat(Const.URL_IMG_HTTPS, "/", path, "/", uploadFileName));
			dpWebzine.setPcImageName(fileName);

			if (UtilsText.isBlank(dpWebzine.getPcAltrnText())) {
				dpWebzine.setPcAltrnText(pcImageFile.getOrgFileName().split("\\.")[0]);
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

			dpWebzine.setMobileImagePathText(UtilsText.concat(path, uploadFileName));
			dpWebzine.setMobileImageUrl(UtilsText.concat(Const.URL_IMG_HTTPS, "/", path, "/", uploadFileName));
			dpWebzine.setMobileImageName(fileName);

			if (UtilsText.isBlank(dpWebzine.getMobileAltrnText())) {
				dpWebzine.setMobileAltrnText(mobileImageFile.getOrgFileName());
			}
		}

		if (movieFile != null && movieFile.isFileItem()) {

			String fileName = movieFile.getOrgFileName();
			String uploadFileName = UtilsText.concat(String.valueOf(System.currentTimeMillis()), ".",
					FilenameUtils.getExtension(fileName));
			String path = UtilsText.concat(FILE_PATH, "/", UtilsDate.today(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS));

			try {
				UtilsSftp.upload(path, uploadFileName, movieFile.getMultiPartFile().getInputStream());
			} catch (Exception e) {
				log.error("{}", e);
				throw new Exception("파일저장에 실패하였습니다.");
			}

			dpWebzine.setMoviePathText(UtilsText.concat(path, uploadFileName));
			dpWebzine.setMovieUrl(UtilsText.concat(Const.URL_IMG_HTTPS, "/", path, "/", uploadFileName));
			dpWebzine.setMovieName(fileName);
		}

		dpWebzineDao.updateDpWebzine(dpWebzine);

		// 연관상품 상제 전체 삭제
		DpWebzineProduct deleteProduct = new DpWebzineProduct();
		deleteProduct.setWbznSeq(dpWebzine.getWbznSeq());
		dpWebzineProductDao.deleteDpWebzineProduct(deleteProduct);

		// 상품 등록
		if (dpWebzine.getDpWebzineProduct() != null && dpWebzine.getDpWebzineProduct().length > 0) {

			for (DpWebzineProduct product : dpWebzine.getDpWebzineProduct()) {
				product.setWbznSeq(dpWebzine.getWbznSeq());
				dpWebzineProductDao.insert(product);
			}
		}

		if (dpWebzine.getDpWebzineDetailImage() != null && dpWebzine.getDpWebzineDetailImage().length > 0
				&& UtilsText.equals(dpWebzine.getWbznType(), "C")) {

			for (DpWebzineDetailImage dpWebzineDetailImage : dpWebzine.getDpWebzineDetailImage()) {
				FileUpload addImage = dpWebzineDetailImage.getAddImageFile();

				dpWebzineDetailImage.setWbznSeq(dpWebzine.getWbznSeq());

				// 이미지 파일이 잇는 경우는 신규 등록 or 수정
				if (addImage != null && addImage.isFileItem()) {

					// 기존 파일 변경
					if (dpWebzineDetailImage.getWbznDtlImageSeq() != null
							&& dpWebzineDetailImage.getWbznDtlImageSeq() > 0) {
						DpWebzineDetailImage savingDpWebzineDetailImage = dpWebzineDetailImageDao
								.selectByPrimaryKey(dpWebzineDetailImage);

						// 기존 상세이미지 row 삭제
						dpWebzineDetailImageDao.delete(savingDpWebzineDetailImage);
					}

					String fileName = dpWebzineDetailImage.getAddImageFile().getOrgFileName();
					String uploadFileName = UtilsText.concat(String.valueOf(System.currentTimeMillis()), ".",
							FilenameUtils.getExtension(fileName));
					String path = UtilsText.concat(FILE_PATH, "/", UtilsDate.today(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS));

					try {
						UtilsSftp.upload(path, uploadFileName,
								dpWebzineDetailImage.getAddImageFile().getMultiPartFile().getInputStream());
					} catch (Exception e) {
						log.error("{}", e);
						throw new Exception("파일저장에 실패하였습니다.");
					}

					dpWebzineDetailImage.setImagePathText(UtilsText.concat(path, uploadFileName));
					dpWebzineDetailImage
							.setImageUrl(UtilsText.concat(Const.URL_IMG_HTTPS, "/", path, "/", uploadFileName));
					dpWebzineDetailImage.setImageName(fileName);

					dpWebzineDetailImage.setWbznSeq(dpWebzine.getWbznSeq());
					dpWebzineDetailImage.setRgsterNo(user.getAdminNo());
					dpWebzineDetailImage.setRgstDtm(new Timestamp(new Date().getTime()));

					if (UtilsText.isBlank(dpWebzineDetailImage.getAltrnText())) {
						dpWebzineDetailImage.setAltrnText(addImage.getOrgFileName().split("\\.")[0]);
					}

					dpWebzineDetailImageDao.insertDpWebzineDetailImage(dpWebzineDetailImage);
				} else {
					dpWebzineDetailImageDao.update(dpWebzineDetailImage);
				}
			}

		}

		// 삭제된 이미지 처리
		if (dpWebzine.getChangedImageSeq() != null && dpWebzine.getChangedImageSeq().length > 0) {

			for (Short seq : dpWebzine.getChangedImageSeq()) {
				DpWebzineDetailImage dpWebzineDetailImage = new DpWebzineDetailImage();
				dpWebzineDetailImage.setWbznSeq(dpWebzine.getWbznSeq());
				dpWebzineDetailImage.setWbznDtlImageSeq(seq);
				dpWebzineDetailImageDao.delete(dpWebzineDetailImage);
			}
		}

		// 등록 후 이미지 업로드 파일 초기화
		dpWebzine.setPcImageFile(null);
		dpWebzine.setMobileImageFile(null);
		dpWebzine.setMovieFile(null);

	}

	/**
	 * 웹진 목록 조회
	 * 
	 * @Desc : 웹진 목록 조회
	 * @Method Name : readDpWebzineList
	 * @Date : 2019. 1. 31.
	 * @Author : SANTA
	 * @param pageable
	 * @return
	 */
	public Page<DpWebzine> getDpWebzineList(Pageable<DpWebzineSearchVO, DpWebzine> pageable) throws Exception {

		int count = dpWebzineDao.selectDpWebzineCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(dpWebzineDao.selectDpWebzineList(pageable));
		}

		return pageable.getPage();

	}

	/**
	 * 웹진 조회
	 * 
	 * @Desc : 웹진 조회
	 * @Method Name : readDpWebzine
	 * @Date : 2019. 2. 1.
	 * @Author : SANTA
	 * @param dpWebzine
	 * @return
	 * @throws Exception
	 */
	public DpWebzine getDpWebzine(DpWebzine dpWebzine) throws Exception {

		return dpWebzineDao.selectDpWebzine(dpWebzine);

	}

	/**
	 * 
	 * @Desc : 웹진 상세이미지 리스트 조회
	 * @Method Name : getDpWebzineDetailImageList
	 * @Date : 2019. 4. 11.
	 * @Author : SANTA
	 * @param dpWebzineDetailImage
	 * @return
	 * @throws Exception
	 */
	public List<DpWebzineDetailImage> getDpWebzineDetailImageList(DpWebzineDetailImage dpWebzineDetailImage)
			throws Exception {
		return dpWebzineDetailImageDao.select(dpWebzineDetailImage);
	}

	/**
	 * 
	 * @Desc : 웹진 연관 상품 리스트 조회
	 * @Method Name : getDpWebzineProductList
	 * @Date : 2019. 4. 15.
	 * @Author : SANTA
	 * @param dpWebzineProduct
	 * @return
	 * @throws Exception
	 */
	public List<DpWebzineProduct> getDpWebzineProductList(DpWebzineProduct dpWebzineProduct) throws Exception {
		return dpWebzineProductDao.selectDpWebzineProductList(dpWebzineProduct);
	}

	/**
	 * @Desc : 에디터 이미지 업로드
	 * @Method Name : uploadImageByEditor
	 * @Date : 2019. 11. 15.
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

}
