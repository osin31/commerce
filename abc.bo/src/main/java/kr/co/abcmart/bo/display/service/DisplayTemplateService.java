package kr.co.abcmart.bo.display.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.display.model.master.DpDisplayTemplate;
import kr.co.abcmart.bo.display.model.master.DpDisplayTemplateCorner;
import kr.co.abcmart.bo.display.model.master.DpDisplayTemplateCornerSet;
import kr.co.abcmart.bo.display.repository.master.DpDisplayTemplateCornerDao;
import kr.co.abcmart.bo.display.repository.master.DpDisplayTemplateCornerSetDao;
import kr.co.abcmart.bo.display.repository.master.DpDisplayTemplateDao;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.util.UtilsSftp;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;
import kr.co.abcmart.util.UtilsDate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DisplayTemplateService {

	private static final String FILE_PATH = UtilsText.concat(Const.UPLOAD_FILE_PATH, Const.IMG_SRC_DISPLAY_TEMPLATE);
	private static final String[] IMAGE_ALLOW_EXT = { "jpg", "gif", "jpeg", "png", "bmp" };

	@Autowired
	private DpDisplayTemplateDao dpDisplayTemplateDao;

	@Autowired
	private DpDisplayTemplateCornerDao dpDisplayTemplateCornerDao;

	@Autowired
	private DpDisplayTemplateCornerSetDao dpDisplayTemplateCornerSetDao;

	@Autowired
	private DisplayPageService displayPageService;

	@Autowired
	private DisplayCategoryService displayCategoryService;

	/**
	 * 
	 * @Desc : 전시 템플릿 저장
	 * @Method Name : insertTemplate
	 * @Date : 2019. 2. 21.
	 * @Author : SANTA
	 * @param dpDisplayTemplate
	 * @throws Exception
	 */
	public void insertTemplate(DpDisplayTemplate dpDisplayTemplate) throws Exception {

		// 세션 정보 호출
		UserDetails user = LoginManager.getUserDetails();
		dpDisplayTemplate.setRgsterNo(user.getAdminNo());
		dpDisplayTemplate.setRgstDtm(new Timestamp(new Date().getTime()));
		dpDisplayTemplate.setModerNo(user.getAdminNo());
		dpDisplayTemplate.setModDtm(dpDisplayTemplate.getRgstDtm());

		if (dpDisplayTemplate.getImageFile() != null && dpDisplayTemplate.getImageFile().isFileItem()) {

			String fileName = dpDisplayTemplate.getImageFile().getOrgFileName();
			String uploadFileName = UtilsText.concat(String.valueOf(System.currentTimeMillis()), ".",
					FilenameUtils.getExtension(fileName));
			String path = UtilsText.concat(FILE_PATH, "/", UtilsDate.today(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS));

			String ext = FilenameUtils.getExtension(fileName).toLowerCase();
			if (!Arrays.asList(IMAGE_ALLOW_EXT).contains(ext)) {
				throw new Exception("허용하지 않는 파일형식 입니다.");
			}

			try {
				UtilsSftp.upload(path, uploadFileName,
						dpDisplayTemplate.getImageFile().getMultiPartFile().getInputStream());
			} catch (Exception e) {
				log.error("{}", e);
				throw new Exception("파일저장에 실패하였습니다.");
			}

			dpDisplayTemplate.setImagePathText(UtilsText.concat(path, uploadFileName));
			dpDisplayTemplate.setImageUrl(UtilsText.concat(Const.URL_IMG_HTTPS, "/", path, "/", uploadFileName));
			dpDisplayTemplate.setImageName(fileName);

			if (UtilsText.isBlank(dpDisplayTemplate.getAltrnText())) {
				dpDisplayTemplate.setAltrnText(dpDisplayTemplate.getImageFile().getOrgFileName().split("\\.")[0]);
			}

		}

		// 템플릿 기본 정보 저정
		dpDisplayTemplateDao.insertTemplate(dpDisplayTemplate);

		// 템플릿 코너 등록
		if (dpDisplayTemplate.getCorner() != null) {
			for (DpDisplayTemplateCorner corner : dpDisplayTemplate.getCorner()) {
				corner.setDispTmplNo(dpDisplayTemplate.getDispTmplNo());
//				corner.setSetUseYn("Y");
//				corner.setSetCount(corner.getSet() != null ? corner.getSet().length : 0);
				corner.setRgsterNo(user.getAdminNo());
				corner.setRgstDtm(dpDisplayTemplate.getRgstDtm());
				corner.setModerNo(user.getAdminNo());
				corner.setModDtm(dpDisplayTemplate.getRgstDtm());
				corner.setCornerName(UtilsText.unescapeXss(corner.getCornerName()));

				dpDisplayTemplateCornerDao.insertTemplateCorner(corner);

				if (corner.getSet() != null) {
					int i = 0;
					for (DpDisplayTemplateCornerSet set : corner.getSet()) {
						set.setDispTmplNo(corner.getDispTmplNo());
						set.setDispTmplCornerSeq(corner.getDispTmplCornerSeq());
						set.setDispTmplCornerSetSeq(i);
						set.setRgsterNo(user.getAdminNo());
						set.setRgstDtm(dpDisplayTemplate.getRgstDtm());
						set.setModerNo(user.getAdminNo());
						set.setModDtm(dpDisplayTemplate.getRgstDtm());

						dpDisplayTemplateCornerSetDao.insertTemplateCornerSet(set);
						i++;
					}
				}
			}
		}
	}

	/**
	 * 
	 * @Desc : 전시 템플릿 리스트 조회
	 * @Method Name : selectTemplateList
	 * @Date : 2019. 2. 21.
	 * @Author : SANTA
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<DpDisplayTemplate> getTemplateList(Pageable<DpDisplayTemplate, DpDisplayTemplate> pageable)
			throws Exception {

		int count = dpDisplayTemplateDao.selectTemplateListCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(dpDisplayTemplateDao.selectTemplateList(pageable));
		}

		return pageable.getPage();

	}

	/**
	 * 
	 * @Desc : 전시 템플릿 조회
	 * @Method Name : getTemplate
	 * @Date : 2019. 2. 22.
	 * @Author : SANTA
	 * @param dpDisplayTemplate
	 * @return
	 * @throws Exception
	 */
	public DpDisplayTemplate getTemplate(DpDisplayTemplate dpDisplayTemplate) throws Exception {
		return dpDisplayTemplateDao.selectTemplate(dpDisplayTemplate);
	}

	/**
	 * 
	 * @Desc : 전시 템플릿 수정
	 * @Method Name : updateTemplate
	 * @Date : 2019. 2. 25.
	 * @Author : SANTA
	 * @param dpDisplayTemplate
	 * @throws Exception
	 */
	public void updateTemplate(DpDisplayTemplate dpDisplayTemplate) throws Exception {
		// 세션 정보 호출
		UserDetails user = LoginManager.getUserDetails();
		dpDisplayTemplate.setModerNo(user.getAdminNo());
		dpDisplayTemplate.setModDtm(new Timestamp(new Date().getTime()));

		if (dpDisplayTemplate.getImageFile() != null && dpDisplayTemplate.getImageFile().isFileItem()) {

			String fileName = dpDisplayTemplate.getImageFile().getOrgFileName();
			String uploadFileName = UtilsText.concat(String.valueOf(System.currentTimeMillis()), ".",
					FilenameUtils.getExtension(fileName));
			String path = UtilsText.concat(FILE_PATH, "/", UtilsDate.today(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS));

			String ext = FilenameUtils.getExtension(fileName).toLowerCase();
			if (!Arrays.asList(IMAGE_ALLOW_EXT).contains(ext)) {
				throw new Exception("허용하지 않는 파일형식 입니다.");
			}

			try {
				UtilsSftp.upload(path, uploadFileName,
						dpDisplayTemplate.getImageFile().getMultiPartFile().getInputStream());
			} catch (Exception e) {
				log.error("{}", e);
				throw new Exception("파일저장에 실패하였습니다.");
			}

			dpDisplayTemplate.setImagePathText(UtilsText.concat(path, uploadFileName));
			dpDisplayTemplate.setImageUrl(UtilsText.concat(Const.URL_IMG_HTTPS, "/", path, "/", uploadFileName));
			dpDisplayTemplate.setImageName(fileName);

			if (UtilsText.isBlank(dpDisplayTemplate.getAltrnText())) {
				dpDisplayTemplate.setAltrnText(dpDisplayTemplate.getImageFile().getOrgFileName().split("\\.")[0]);
			}
		}

		// 템플릿 코너 등록
		if (dpDisplayTemplate.getCorner() != null) {
			for (DpDisplayTemplateCorner corner : dpDisplayTemplate.getCorner()) {
				corner.setDispTmplNo(dpDisplayTemplate.getDispTmplNo());
//				corner.setSetUseYn("Y");
//				corner.setSetCount(corner.getSet() != null ? corner.getSet().length : 0);
				corner.setModerNo(user.getAdminNo());
				corner.setModDtm(dpDisplayTemplate.getModDtm());
				corner.setCornerName(UtilsText.unescapeXss(corner.getCornerName()));

				if (UtilsText.equals(corner.getStatus(), "U")) {
					dpDisplayTemplateCornerDao.updateTemplCornerArr(corner);
				} else {
					corner.setRgsterNo(user.getAdminNo());
					corner.setRgstDtm(new Timestamp(new Date().getTime()));
					dpDisplayTemplateCornerDao.insertTemplateCorner(corner);
				}

				// 코너 세트의 경우 수정 여부 판단이 되지 않아 전체 삭제 후 insert 처리
				if (corner.getSet() != null) {

					Integer[] dispTmplCornerSeqArr = { corner.getDispTmplCornerSeq() };
					corner.setDispTmplCornerSeqArr(dispTmplCornerSeqArr);

					dpDisplayTemplateCornerSetDao.deleteCornerSetByDispTmplCornerSeqArr(corner);
					int i = 0;
					for (DpDisplayTemplateCornerSet set : corner.getSet()) {
						set.setDispTmplNo(corner.getDispTmplNo());
						set.setDispTmplCornerSeq(corner.getDispTmplCornerSeq());
						set.setDispTmplCornerSetSeq(i);
						set.setRgsterNo(user.getAdminNo());
						set.setRgstDtm(dpDisplayTemplate.getModDtm());
						set.setModerNo(user.getAdminNo());
						set.setModDtm(dpDisplayTemplate.getModDtm());

						dpDisplayTemplateCornerSetDao.insertTemplateCornerSet(set);
						i++;
					}
				}
			}
		}

		dpDisplayTemplateDao.updateTemplate(dpDisplayTemplate);
	}

	/**
	 * 
	 * @Desc : 전시 템플릿 리스트 조회
	 * @Method Name : selectTemplateList
	 * @Date : 2019. 2. 21.
	 * @Author : SANTA
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<DpDisplayTemplateCorner> getTemplateCornerList(
			Pageable<DpDisplayTemplateCorner, DpDisplayTemplateCorner> pageable) throws Exception {

		int count = dpDisplayTemplateCornerDao.selectTemplateCornerListCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(dpDisplayTemplateCornerDao.selectTemplateCornerList(pageable));
		}

		return pageable.getPage();

	}

	/**
	 * 
	 * @Desc : 전시 템플릿 코너 조회
	 * @Method Name : getTemplateCorner
	 * @Date : 2019. 2. 28.
	 * @Author : SANTA
	 * @param dpDisplayTemplateCorner
	 * @return
	 */
	public DpDisplayTemplateCorner getTemplateCorner(DpDisplayTemplateCorner dpDisplayTemplateCorner) throws Exception {
		List<DpDisplayTemplateCorner> list = dpDisplayTemplateCornerDao.select(dpDisplayTemplateCorner);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	/**
	 * 
	 * @Desc : 전시 템플릿 코너 셋 리스트 조회
	 * @Method Name : getTemplateCornerSetList
	 * @Date : 2019. 2. 28.
	 * @Author : SANTA
	 * @param dpDisplayTemplateCornerSet
	 * @return
	 * @throws Exception
	 */
	public List<DpDisplayTemplateCornerSet> getTemplateCornerSetList(
			DpDisplayTemplateCornerSet dpDisplayTemplateCornerSet) throws Exception {

		List<DpDisplayTemplateCornerSet> list = dpDisplayTemplateCornerSetDao
				.selectTemplateCornerSet(dpDisplayTemplateCornerSet);

		return list;
	}

	/**
	 * 
	 * @Desc : 전시 템플릿 코너 셋 리스트 -> map 으로 변경
	 * @Method Name : getTemplateCornerSetListToMap
	 * @Date : 2019. 3. 11.
	 * @Author : SANTA
	 * @param dpDisplayTemplateCornerSet
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, List<DpDisplayTemplateCornerSet>> getTemplateCornerSetListToMap(
			DpDisplayTemplateCornerSet dpDisplayTemplateCornerSet) throws Exception {
		Map<Integer, List<DpDisplayTemplateCornerSet>> map = new HashMap<>();

		List<DpDisplayTemplateCornerSet> list = this.getTemplateCornerSetList(dpDisplayTemplateCornerSet);

		Integer key = null;
		List<DpDisplayTemplateCornerSet> tmpList = null;
		int i = 1;
		for (DpDisplayTemplateCornerSet set : list) {
			if (key == null || key != set.getSortSeq()) {

				if (key != null) {
					map.put(i, tmpList);
					i++;
				}

				key = set.getSortSeq();
				tmpList = new ArrayList<DpDisplayTemplateCornerSet>();
			}

			tmpList.add(set);
		}

		map.put(i, tmpList);

		return map;
	}

	/**
	 * 
	 * @Desc : 코너 삭제
	 * @Method Name : deleteCorner
	 * @Date : 2019. 3. 4.
	 * @Author : SANTA
	 * @param dpDisplayTemplateCorner
	 * @throws Exception
	 */
	public void deleteCorner(DpDisplayTemplateCorner dpDisplayTemplateCorner) throws Exception {

		dpDisplayTemplateCornerSetDao.deleteCornerSetByDispTmplCornerSeqArr(dpDisplayTemplateCorner);
		dpDisplayTemplateCornerDao.deleteCornerByDispTmplCornerSeqArr(dpDisplayTemplateCorner);
	}

	/**
	 * 
	 * @Desc : 템플릿 사용 여부
	 * @Method Name : checkTmpl
	 * @Date : 2019. 3. 15.
	 * @Author : lee
	 * @param dispTmplNo
	 * @throws Exception
	 */
	public boolean isTmplUsing(String dispTmplNo) throws Exception {

		int pageCnt = displayPageService.getDpDisplayPageCount(dispTmplNo);
		int ctgrCnt = displayCategoryService.getDpCategoryCount(dispTmplNo);
		int totalCnt = pageCnt + ctgrCnt;

		if (totalCnt > 0) {
			return true;
		}

		return false;
	}

}
