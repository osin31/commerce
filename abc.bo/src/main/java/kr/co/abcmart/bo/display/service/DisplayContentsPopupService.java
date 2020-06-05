package kr.co.abcmart.bo.display.service;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.display.model.master.DpCategoryCorner;
import kr.co.abcmart.bo.display.model.master.DpCategoryCornerName;
import kr.co.abcmart.bo.display.model.master.DpCategoryCornerSet;
import kr.co.abcmart.bo.display.model.master.DpDisplayPageCorner;
import kr.co.abcmart.bo.display.model.master.DpDisplayPageCornerName;
import kr.co.abcmart.bo.display.model.master.DpDisplayPageCornerSet;
import kr.co.abcmart.bo.display.repository.master.DpCategoryCornerNameDao;
import kr.co.abcmart.bo.display.repository.master.DpCategoryCornerSetDao;
import kr.co.abcmart.bo.display.repository.master.DpDisplayPageCornerNameDao;
import kr.co.abcmart.bo.display.repository.master.DpDisplayPageCornerSetDao;
import kr.co.abcmart.bo.display.vo.DisplayContentsPopupVO;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.common.util.UtilsSftp;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;
import kr.co.abcmart.util.UtilsDate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DisplayContentsPopupService {

	private static final String FILE_PATH = UtilsText.concat(Const.UPLOAD_FILE_PATH, Const.IMG_SRC_DISPLAY_CONTENTS);
	private static final String[] IMAGE_ALLOW_EXT = { "jpg", "gif", "jpeg", "png", "bmp" };
	private static final String[] MOVIE_ALLOW_EXT = { "mp4" };

	@Autowired
	private DpCategoryCornerNameDao dpCategoryCornerNameDao;

	@Autowired
	private DpCategoryCornerSetDao dpCategoryCornerSetDao;

	@Autowired
	private DpDisplayPageCornerNameDao dpDisplayPageCornerNameDao;

	@Autowired
	private DpDisplayPageCornerSetDao dpDisplayPageCornerSetDao;

	/**
	 *
	 * @Desc : 전시 카테고리 코너명 리스트 조회
	 * @Method Name : getDisplayCategoryCornerNameList
	 * @Date : 2019. 4. 2.
	 * @Author : SANTA
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public Page<DpCategoryCornerName> getDisplayCategoryCornerNameList(Parameter<DisplayContentsPopupVO> parameter)
			throws Exception {

		Pageable<DisplayContentsPopupVO, DpCategoryCornerName> pageable = new Pageable<>(parameter);

		int count = dpCategoryCornerNameDao.selectDisplayCategoryCornerNameListCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(dpCategoryCornerNameDao.selectDisplayCategoryCornerNameList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 *
	 * @Desc : 전시 페이지 코너명 리스트 조회
	 * @Method Name : getDisplayPageCornerNameList
	 * @Date : 2019. 4. 2.
	 * @Author : SANTA
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public Page<DpDisplayPageCornerName> getDisplayPageCornerNameList(Parameter<DisplayContentsPopupVO> parameter)
			throws Exception {

		Pageable<DisplayContentsPopupVO, DpDisplayPageCornerName> pageable = new Pageable<>(parameter);

		int count = dpDisplayPageCornerNameDao.selectDisplayPageCornerNameListCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(dpDisplayPageCornerNameDao.selectDisplayPageCornerNameList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 *
	 * @Desc : 전시 카테고리 코너 셋 리스트 조회
	 * @Method Name : getDisplayCategoryCornerSetList
	 * @Date : 2019. 4. 2.
	 * @Author : SANTA
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public Page<DpCategoryCornerSet> getDisplayCategoryCornerSetList(Parameter<DisplayContentsPopupVO> parameter)
			throws Exception {

		Pageable<DisplayContentsPopupVO, DpCategoryCornerSet> pageable = new Pageable<>(parameter);

		int count = dpCategoryCornerSetDao.selectDisplayCategoryCornerSetListCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(dpCategoryCornerSetDao.selectDisplayCategoryCornerSetList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 *
	 * @Desc : 전시 페이지 코너 셋 조회
	 * @Method Name : getDisplayPageCornerSetList
	 * @Date : 2019. 4. 2.
	 * @Author : SANTA
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public Page<DpDisplayPageCornerSet> getDisplayPageCornerSetList(Parameter<DisplayContentsPopupVO> parameter)
			throws Exception {

		Pageable<DisplayContentsPopupVO, DpDisplayPageCornerSet> pageable = new Pageable<>(parameter);

		int count = dpDisplayPageCornerSetDao.selectDisplayPageCornerSetListCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(dpDisplayPageCornerSetDao.selectDisplayPageCornerSetList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 *
	 * @Desc : 전시 카테고리 코너명 등록
	 * @Method Name : saveCornerName
	 * @Date : 2019. 3. 18.
	 * @Author : SANTA
	 * @param dpCategoryCorner
	 * @throws Exception
	 */
	public void saveCornerName(DpCategoryCorner dpCategoryCorner) throws Exception {

		// 세션 정보 호출
		UserDetails user = LoginManager.getUserDetails();

		int i = 0;
		for (DpCategoryCornerName name : dpCategoryCorner.getList()) {
			if (UtilsText.equals(name.getStatus(), "I")) {
				name.setRgsterNo(user.getAdminNo());
				name.setRgstDtm(new Timestamp(new Date().getTime()));
			}
			name.setModerNo(user.getAdminNo());
			name.setModDtm(new Timestamp(new Date().getTime()));
			name.setDispCornerName(UtilsText.unescapeXss(name.getDispCornerName()));

//			if (name.getImageUpload() != null && name.getImageUpload().isFileItem()) {
//
//				String fileName = name.getImageUpload().getOrgFileName();
//				String uploadFileName = UtilsText.concat(String.valueOf(System.currentTimeMillis()), ".",
//						FilenameUtils.getExtension(fileName));
//				String path = UtilsText.concat(FILE_PATH, "/", UtilsDate.today(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS));
//
//				try {
//					UtilsSftp.upload(path, uploadFileName, name.getImageUpload().getMultiPartFile().getInputStream());
//				} catch (Exception e) {
//					log.error("{}", e);
//					throw new Exception("파일저장에 실패하였습니다.");
//				}
//
//				name.setImagePathText(UtilsText.concat(path, uploadFileName));
//				name.setImageUrl(UtilsText.concat(Const.URL_IMG_HTTPS, "/", path, "/", uploadFileName));
//				name.setImageName(fileName);
//
//				if (UtilsText.isBlank(name.getAltrnText())) {
//					name.setAltrnText(name.getImageUpload().getOrgFileName().split("\\.")[0]);
//				}
//			}

			if (UtilsText.equals(name.getStatus(), "I")) {
				dpCategoryCornerNameDao.insertName(name);
			} else {
				dpCategoryCornerNameDao.updateName(name);
			}
			i++;
		}
	}

	/**
	 *
	 * @Desc : 전시 페이지 코너명 저장
	 * @Method Name : saveCornerName
	 * @Date : 2019. 4. 2.
	 * @Author : SANTA
	 * @param dpDisplayPageCorner
	 * @throws Exception
	 */
	@CacheEvict(value = { "displayPageService.getPageInfo" }, allEntries = true)
	public void saveCornerName(DpDisplayPageCorner dpDisplayPageCorner) throws Exception {

		// 세션 정보 호출
		UserDetails user = LoginManager.getUserDetails();

		int i = 0;
		for (DpDisplayPageCornerName name : dpDisplayPageCorner.getList()) {
			if (UtilsText.equals(name.getStatus(), "I")) {
				name.setRgsterNo(user.getAdminNo());
				name.setRgstDtm(new Timestamp(new Date().getTime()));
			}
			name.setModerNo(user.getAdminNo());
			name.setModDtm(new Timestamp(new Date().getTime()));
			name.setDispCornerName(UtilsText.unescapeXss(name.getDispCornerName()));

//			if (name.getImageUpload() != null && name.getImageUpload().isFileItem()) {
//
//				String fileName = name.getImageUpload().getOrgFileName();
//				String uploadFileName = UtilsText.concat(String.valueOf(System.currentTimeMillis()), ".",
//						FilenameUtils.getExtension(fileName));
//				String path = UtilsText.concat(FILE_PATH, "/", UtilsDate.today(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS));
//
//				try {
//					UtilsSftp.upload(path, uploadFileName, name.getImageUpload().getMultiPartFile().getInputStream());
//				} catch (Exception e) {
//					log.error("{}", e);
//					throw new Exception("파일저장에 실패하였습니다.");
//				}
//
//				name.setImagePathText(UtilsText.concat(path, uploadFileName));
//				name.setImageUrl(UtilsText.concat(Const.URL_IMG_HTTPS, "/", path, "/", uploadFileName));
//				name.setImageName(fileName);
//
//				if (UtilsText.isBlank(name.getAltrnText())) {
//					name.setAltrnText(name.getImageUpload().getOrgFileName().split("\\.")[0]);
//				}
//			}

			if (UtilsText.equals(name.getStatus(), "I")) {
				dpDisplayPageCornerNameDao.insertName(name);
			} else {
				dpDisplayPageCornerNameDao.updateName(name);
			}
			i++;
		}
	}

	/**
	 *
	 * @Desc : 전시 콘텐츠 셋 등록
	 * @Method Name : saveCornerSet
	 * @Date : 2019. 3. 18.
	 * @Author : SANTA
	 * @param dpCategoryCorner
	 * @throws Exception
	 */
	public void saveCornerSet(DpCategoryCorner dpCategoryCorner) throws Exception {

		// 세션 정보 호출
		UserDetails user = LoginManager.getUserDetails();

		int i = 0;
		for (DpCategoryCornerSet set : dpCategoryCorner.getSet()) {
			if (UtilsText.equals(set.getStatus(), "I")) {
				set.setRgsterNo(user.getAdminNo());
				set.setRgstDtm(new Timestamp(new Date().getTime()));
			}
			set.setModerNo(user.getAdminNo());
			set.setModDtm(new Timestamp(new Date().getTime()));
			set.setAddInfo1(UtilsText.unescapeXss(set.getAddInfo1()));
			set.setAddInfo7(UtilsText.unescapeXss(set.getAddInfo7()));

			if (UtilsText.equals(set.getStatus(), "I")) {
				dpCategoryCornerSetDao.insertSet(set);
			} else {
				dpCategoryCornerSetDao.updateSet(set);
			}
			i++;
		}
	}

	/**
	 *
	 * @Desc : 전시 페이지 코너 셋 저장
	 * @Method Name : saveCornerSet
	 * @Date : 2019. 4. 2.
	 * @Author : SANTA
	 * @param dpCategoryCorner
	 * @throws Exception
	 */
	@CacheEvict(value = "displayPageService.getPageInfo", allEntries = true)
	public void saveCornerSet(DpDisplayPageCorner dpDisplayPageCorner) throws Exception {

		// 세션 정보 호출
		UserDetails user = LoginManager.getUserDetails();

		int i = 0;
		for (DpDisplayPageCornerSet set : dpDisplayPageCorner.getSet()) {
			if (UtilsText.equals(set.getStatus(), "I")) {
				set.setRgsterNo(user.getAdminNo());
				set.setRgstDtm(new Timestamp(new Date().getTime()));
			}
			set.setModerNo(user.getAdminNo());
			set.setModDtm(new Timestamp(new Date().getTime()));
			set.setAddInfo1(UtilsText.unescapeXss(set.getAddInfo1()));
			set.setAddInfo7(UtilsText.unescapeXss(set.getAddInfo7()));

			if (UtilsText.equals(set.getStatus(), "I")) {
				dpDisplayPageCornerSetDao.insertSet(set);
			} else {
				if (UtilsText.equals(set.getContTypeCode(), "10003")) {
					if (UtilsText.equals(set.getAddInfo5(), "U")) {
						// url 입력
						set.setAddInfo7("");
						set.setAddInfo8("");
					}
				} else if (UtilsText.equals(set.getContTypeCode(), "10004")) {
					if (UtilsText.equals(set.getAddInfo2(), "N")) {
						set.setAddInfo4("");
					}
				}

				dpDisplayPageCornerSetDao.updateSet(set);
			}
			i++;
		}
	}

	/**
	 *
	 * @Desc : 전시 카테고리 코너명 조회
	 * @Method Name : selectCornerName
	 * @Date : 2019. 3. 29.
	 * @Author : SANTA
	 * @param displayContentsPopupVO
	 * @return
	 * @throws Exception
	 */
	public DpCategoryCornerName selectDisplayCategoryCornerName(DisplayContentsPopupVO displayContentsPopupVO)
			throws Exception {
		return dpCategoryCornerNameDao.selectCornerName(displayContentsPopupVO);
	}

	/**
	 *
	 * @Desc : 전시 카테고리 코너셋 조회
	 * @Method Name : selectCornerSet
	 * @Date : 2019. 3. 29.
	 * @Author : SANTA
	 * @param displayContentsPopupVO
	 * @return
	 * @throws Exception
	 */
	public DpCategoryCornerSet selectDisplayCategoryCornerSet(DisplayContentsPopupVO displayContentsPopupVO)
			throws Exception {
		return dpCategoryCornerSetDao.selectDisplayCategoryCornerSet(displayContentsPopupVO);
	}

	/**
	 *
	 * @Desc : 전시 페이지 코너명 조회
	 * @Method Name : selectDisplayPageCornerName
	 * @Date : 2019. 4. 30.
	 * @Author : SANTA
	 * @param displayContentsPopupVO
	 * @return
	 * @throws Exception
	 */
	public DpDisplayPageCornerName selectDisplayPageCornerName(DisplayContentsPopupVO displayContentsPopupVO)
			throws Exception {
		return dpDisplayPageCornerNameDao.selectCornerName(displayContentsPopupVO);
	}

	/**
	 *
	 * @Desc : 전시 페이지 코너 셋 조회
	 * @Method Name : selectDisplayPageCornerSet
	 * @Date : 2019. 4. 30.
	 * @Author : SANTA
	 * @param displayContentsPopupVO
	 * @return
	 * @throws Exception
	 */
	public DpDisplayPageCornerSet selectDisplayPageCornerSet(DisplayContentsPopupVO displayContentsPopupVO)
			throws Exception {
		return dpDisplayPageCornerSetDao.selectDisplayPageCornerSet(displayContentsPopupVO);
	}

	/**
	 *
	 * @Desc : 전시카테고리 코너넷 삭제
	 * @Method Name : deleteCornerSet
	 * @Date : 2019. 3. 29.
	 * @Author : SANTA
	 * @param displayContentsPopupVO
	 * @throws Exception
	 */
	public void deleteCategoryCornerSet(DisplayContentsPopupVO displayContentsPopupVO) throws Exception {
		dpCategoryCornerSetDao.deleteDisplayCategoryCornerSet(displayContentsPopupVO);
	}

	/**
	 *
	 * @Desc : 전시 페이지 코너 셋 삭제
	 * @Method Name : deletePageCornerSet
	 * @Date : 2019. 4. 10.
	 * @Author : SANTA
	 * @param displayContentsPopupVO
	 * @throws Exception
	 */
	@CacheEvict(value = "displayPageService.getPageInfo", allEntries = true)
	public void deletePageCornerSet(DisplayContentsPopupVO displayContentsPopupVO) throws Exception {
		dpDisplayPageCornerSetDao.deleteDisplayPageCornerSet(displayContentsPopupVO);
	}

	/**
	 *
	 * @Desc : 전시 카테고리 코너명 삭제
	 * @Method Name : deleteCornerName
	 * @Date : 2019. 3. 29.
	 * @Author : SANTA
	 * @param displayContentsPopupVO
	 * @throws Exception
	 */
	public void deleteCategoryCornerName(DisplayContentsPopupVO displayContentsPopupVO) throws Exception {
		dpCategoryCornerNameDao.deleteDisplayCategoryCornerName(displayContentsPopupVO);
	}

	/**
	 *
	 * @Desc : 전시 페이지 코너명 삭제
	 * @Method Name : deletePageCornerName
	 * @Date : 2019. 4. 10.
	 * @Author : SANTA
	 * @param displayContentsPopupVO
	 * @throws Exception
	 */
	public void deletePageCornerName(DisplayContentsPopupVO displayContentsPopupVO) throws Exception {
		dpDisplayPageCornerNameDao.deleteDisplayPageCornerName(displayContentsPopupVO);
	}

	/**
	 * @Desc : 이미지 업로드
	 * @Method Name : uploadImage
	 * @Date : 2019. 11. 15.
	 * @Author : lee
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> uploadImage(DisplayContentsPopupVO vo) throws Exception {

		Map<String, Object> result = new HashMap<>();

		if (vo.getImageUpload() != null && vo.getImageUpload().isFileItem()) {
			String fileName = vo.getImageUpload().getOrgFileName();
			String uploadFileName = UtilsText.concat(String.valueOf(System.currentTimeMillis()), ".",
					FilenameUtils.getExtension(fileName));
			String path = UtilsText.concat(FILE_PATH, "/",
					UtilsDate.today(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS));

			String ext = FilenameUtils.getExtension(fileName).toLowerCase();
			if (!Arrays.asList(IMAGE_ALLOW_EXT).contains(ext)) {
				throw new Exception("허용하지 않는 파일형식 입니다.");
			}

			try {
				UtilsSftp.upload(path, uploadFileName, vo.getImageUpload().getMultiPartFile().getInputStream());
			} catch (Exception e) {
				log.error("{}", e);
				throw new Exception("파일저장에 실패하였습니다.");
			}

			result.put("imageFilePath", UtilsText.concat(path, uploadFileName));
			result.put("imageUrl", UtilsText.concat(Const.URL_IMG_HTTPS, "/", path, "/", uploadFileName));
			result.put("imageFileName", fileName);
			if (UtilsText.isBlank(vo.getAltrnText())) {
				result.put("altrnText", fileName.split("\\.")[0]);
			}
		}

		if (vo.getMovieUpload() != null && vo.getMovieUpload().isFileItem()) {
			String fileName = vo.getMovieUpload().getOrgFileName();
			String uploadFileName = UtilsText.concat(String.valueOf(System.currentTimeMillis()), ".",
					FilenameUtils.getExtension(fileName));
			String path = UtilsText.concat(FILE_PATH, "/",
					UtilsDate.today(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS));

			String ext = FilenameUtils.getExtension(fileName).toLowerCase();
			if (!Arrays.asList(MOVIE_ALLOW_EXT).contains(ext)) {
				throw new Exception("허용하지 않는 파일형식 입니다.");
			}

			try {
				UtilsSftp.upload(path, uploadFileName, vo.getMovieUpload().getMultiPartFile().getInputStream());
			} catch (Exception e) {
				log.error("{}", e);
				throw new Exception("파일저장에 실패하였습니다.");
			}

			result.put("movieFilePath", UtilsText.concat(path, uploadFileName));
			result.put("movieUrl", UtilsText.concat(Const.URL_IMG_HTTPS, "/", path, "/", uploadFileName));
			result.put("movieFileName", fileName);
		}

		return result;
	}

}
