package kr.co.abcmart.bo.product.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.product.model.master.DpSizeChart;
import kr.co.abcmart.bo.product.repository.master.DpSizeChartDao;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.FileUpload;
import kr.co.abcmart.common.util.UtilsSftp;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;
import kr.co.abcmart.util.UtilsDate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SizeChartService {

	private static final String FILE_PATH = UtilsText.concat(Const.UPLOAD_FILE_PATH, Const.IMG_SRC_PRODUCT_SIZECHART);

	@Autowired
	private DpSizeChartDao dpSizeChartDao;

	/**
	 * 사이즈조견표 목록 조회
	 * 
	 * @Desc : 사이즈조견표 목록 조회
	 * @Method Name : getSizeChartList
	 * @Date : 2019. 2. 12.
	 * @Author : easyhun
	 * @param pageable
	 * @return
	 */
	public Page<DpSizeChart> getSizeChartList(Pageable<DpSizeChart, DpSizeChart> pageable) throws Exception {

		int count = dpSizeChartDao.selectDpSizeChartCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(dpSizeChartDao.selectDpSizeChartList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * 사이즈조견표 조회
	 * 
	 * @Desc : 사이즈조견표 조회
	 * @Method Name : getSizeChart
	 * @Date : 2019. 2. 12.
	 * @Author : easyhun
	 * @param dpSizeChart
	 * @return
	 * @throws Exception
	 */
	public DpSizeChart getSizeChart(DpSizeChart dpSizeChart) throws Exception {

		return dpSizeChartDao.selectDpSizeChart(dpSizeChart);

	}

	/**
	 * 사이즈조견표 등록
	 * 
	 * @Desc : 사이즈조견표 등록
	 * @Method Name : insertSizeChart
	 * @Date : 2019. 2. 12.
	 * @Author : easyhun
	 * @param dpSizeChart
	 * @throws Exception
	 */
	public void insertSizeChart(DpSizeChart dpSizeChart) throws Exception {

		// 이미지
		FileUpload uploadFile = dpSizeChart.getImageFile();

		// 세션 정보 호출
		UserDetails user = LoginManager.getUserDetails();
		dpSizeChart.setRgsterNo(user.getAdminNo());
		dpSizeChart.setRgstDtm(new Timestamp(new Date().getTime()));
		dpSizeChart.setModerNo(user.getAdminNo());
		dpSizeChart.setModDtm(dpSizeChart.getRgstDtm());

		if (uploadFile != null && uploadFile.isFileItem()) {

			String fileName = uploadFile.getOrgFileName();
			String uploadFileName = UtilsText.concat(String.valueOf(System.currentTimeMillis()), ".",
					FilenameUtils.getExtension(fileName));
			String path = UtilsText.concat(FILE_PATH, "/", UtilsDate.today(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS));

			try {
				UtilsSftp.upload(path, uploadFileName, uploadFile.getMultiPartFile().getInputStream());
			} catch (Exception e) {
				log.error("{}", e);
				throw new Exception("파일저장에 실패하였습니다.");
			}

			dpSizeChart.setImagePathText(UtilsText.concat(path, uploadFileName));
			dpSizeChart.setImageUrl(UtilsText.concat(Const.URL_IMG_HTTPS, "/", path, "/", uploadFileName));
			dpSizeChart.setImageName(fileName);

			if (UtilsText.isBlank(dpSizeChart.getAltrnText())) {
				dpSizeChart.setAltrnText(fileName.split("\\.")[0]);
			}

		} else {
			log.warn("파일 데이터가 존재 하지 않습니다.");
		}
		dpSizeChartDao.insert(dpSizeChart);

	}

	/**
	 * 사이즈조견표 수정
	 * 
	 * @Desc : 사이즈조견표 수정
	 * @Method Name : updateSizeChart
	 * @Date : 2019. 2. 12.
	 * @Author : easyhun
	 * @param dpSizeChart
	 * @throws Exception
	 */
	public void updateSizeChart(DpSizeChart dpSizeChart) throws Exception {

		// 이미지
		FileUpload uploadFile = dpSizeChart.getImageFile();

		// 세션 정보 호출
		UserDetails user = LoginManager.getUserDetails();
		dpSizeChart.setModerNo(user.getAdminNo());
		dpSizeChart.setModDtm(new Timestamp(new Date().getTime()));

		if (uploadFile != null && uploadFile.isFileItem()) {
			String fileName = uploadFile.getOrgFileName();
			String uploadFileName = UtilsText.concat(String.valueOf(System.currentTimeMillis()), ".",
					FilenameUtils.getExtension(fileName));
			String path = UtilsText.concat(FILE_PATH, "/", UtilsDate.today(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS));

			try {
				UtilsSftp.upload(path, uploadFileName, uploadFile.getMultiPartFile().getInputStream());
			} catch (Exception e) {
				log.error("{}", e);
				throw new Exception("파일저장에 실패하였습니다.");
			}

			dpSizeChart.setImagePathText(UtilsText.concat(path, uploadFileName));
			dpSizeChart.setImageUrl(UtilsText.concat(Const.URL_IMG_HTTPS, "/", path, "/", uploadFileName));
			dpSizeChart.setImageName(fileName);

			if (UtilsText.isBlank(dpSizeChart.getAltrnText())) {
				dpSizeChart.setAltrnText(fileName.split("\\.")[0]);
			}
		} else {
			log.warn("파일 데이터가 존재 하지 않습니다.");
		}

		dpSizeChartDao.updateDpSizeChart(dpSizeChart);

	}

	/**
	 * @Desc : 사이즈조견표 목록 검색
	 * @Method Name : getSizeChartList
	 * @Date : 2019. 4. 22.
	 * @Author : tennessee
	 * @param criteria
	 * @return
	 * @throws Exception
	 */
	public List<DpSizeChart> getSizeChartForProduct(DpSizeChart criteria) throws Exception {
		return this.dpSizeChartDao.selectDpSizeChartForProduct(criteria);
	}

}
