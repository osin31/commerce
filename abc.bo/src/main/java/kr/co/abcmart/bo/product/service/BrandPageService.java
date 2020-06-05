package kr.co.abcmart.bo.product.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.product.model.master.DpBrand;
import kr.co.abcmart.bo.product.model.master.DpBrandPage;
import kr.co.abcmart.bo.product.repository.master.DpBrandPageDao;
import kr.co.abcmart.bo.product.vo.DpBrandPageSearchVO;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.FileUpload;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.common.util.UtilsObject;
import kr.co.abcmart.common.util.UtilsSftp;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;
import kr.co.abcmart.util.UtilsDate;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 브랜드 페이지 서비스
 * @FileName : BrandPageService.java
 * @Project : abc.bo
 * @Date : 2019. 6. 17.
 * @Author : 백인천
 */
@Slf4j
@Service
public class BrandPageService {

	private static final String FILE_PATH = UtilsText.concat(Const.UPLOAD_FILE_PATH, Const.IMG_SRC_PRODUCT_BRAND);

	@Autowired
	private DpBrandPageDao dpBrandPageDao;

	/**
	 * @Desc : 브랜드 저장
	 * @Method Name : saveBrand
	 * @Date : 2019. 6. 17.
	 * @Author : 백인천
	 * @param brand
	 * @return
	 * @throws Exception
	 */
	public int insertBrandPageFile(Parameter<?> parameter) throws Exception {
		UserDetails user = LoginManager.getUserDetails();
		DpBrandPage brandPageUpload = new DpBrandPage();
		FileUpload uploadFile = parameter.getUploadFile("imageUpload");
		FileUpload uploadMovie = null;
		int evCount = 0;

		if ("10003".equals(parameter.getString("contTypeCode"))) {
			uploadMovie = parameter.getUploadFile("movieUpload");
		}

		if (uploadFile != null && uploadFile.isFileItem()) {

			// 이미지 파일업로드 정보 구성
			brandPageUpload = this.setUploadFileInfo(uploadFile, brandPageUpload, "I");

			log.debug("parameter altrnText > " + parameter.getString("altrnText"));
			if (UtilsText.isNotBlank(parameter.getString("altrnText"))) {
				brandPageUpload.setAltrnText(parameter.getString("altrnText"));
			} else {
				brandPageUpload.setAltrnText(parameter.getString("addInfo1"));
			}
			brandPageUpload.setDeviceCode(parameter.getString("deviceCode"));
			brandPageUpload.setSiteNo(parameter.getString("siteNo"));
			brandPageUpload.setChnnlNo(parameter.getString("chnnlNo"));

			if ("10002".equals(parameter.getString("contTypeCode"))) {
				brandPageUpload.setImageName(uploadFile.getOrgFileName());

				brandPageUpload.setFileType("I");
				brandPageUpload.setCnnctnType(parameter.getString("addInfo5"));
				brandPageUpload.setBckgColorYn(parameter.getString("addInfo8"));
				brandPageUpload.setLinkTargetType(parameter.getString("addInfo6"));
			} else if ("10003".equals(parameter.getString("contTypeCode"))) {
				brandPageUpload.setImageName(uploadFile.getOrgFileName());

				if ("D".equals(parameter.getString("addInfo5"))) {
					// 동영상 파일업로드 정보 구성
					brandPageUpload = this.setUploadFileInfo(uploadMovie, brandPageUpload, "M");
					brandPageUpload.setMovieName(uploadMovie.getOrgFileName());
				} else {
					brandPageUpload.setMovieUrl(parameter.getString("addInfo9"));
				}
				brandPageUpload.setMoviePlayType(parameter.getString("L"));

				brandPageUpload.setCnnctnType("N");
				brandPageUpload.setFileType("M");
				brandPageUpload.setUploadYn(parameter.getString("addInfo5"));
				brandPageUpload.setBckgColorYn("N");
				brandPageUpload.setLinkTargetType("N");
			}

			brandPageUpload.setBrandNo(parameter.getString("brandNo"));
			brandPageUpload.setTitleText(parameter.getString("addInfo1"));
			brandPageUpload.setDispGbnType("T");
			brandPageUpload.setDispYn(parameter.getString("dispYn"));
			brandPageUpload.setLinkInfo(parameter.getString("addInfo7"));
			brandPageUpload.setTextColorType("N");
			brandPageUpload.setSortSeq(parameter.getInt("sortSeq"));

			if (UtilsText.equals(brandPageUpload.getDispYn(), Const.BOOLEAN_TRUE)) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
				Date parsedStartDate = dateFormat.parse(parameter.getString("dispStartYmd"));
				Date parsedEndDate = dateFormat.parse(parameter.getString("dispEndYmd"));
				Timestamp startTimestamp = new java.sql.Timestamp(parsedStartDate.getTime());
				Timestamp endTimestamp = new java.sql.Timestamp(parsedEndDate.getTime());
				brandPageUpload.setDispStartYmd(startTimestamp);
				brandPageUpload.setDispEndYmd(endTimestamp);
			}

			brandPageUpload.setRgsterNo(user.getAdminNo());

			evCount = dpBrandPageDao.insertBrandPage(brandPageUpload);
		} else {
			log.warn("파일 데이터가 존재 하지 않습니다.");
		}

		return evCount;
	}

	/**
	 * @Desc : 브랜드 통합몰 페이지 리스트 반환
	 * @Method Name : searchBrandMallPageList
	 * @Date : 2019. 6. 5.
	 * @Author : 백인천
	 * @param brandNo
	 * @return
	 * @throws Exception
	 */
	public Page<DpBrandPage> searchBrandPageList(Pageable<DpBrandPageSearchVO, DpBrandPage> pageable) throws Exception {
		Integer count = this.dpBrandPageDao.selectBrandPageListCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(dpBrandPageDao.selectBrandPageList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 브랜드 페이지 상단 비주얼 전시정보 저장
	 * @Method Name : updateBrand
	 * @Date : 2019. 6. 5.
	 * @Author : 백인천
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public int updateBrand(Parameter<DpBrandPage[]> parameter) throws Exception {

		parameter.validate();
		DpBrandPage[] brandPageList = parameter.get();
		int evCount = 0;

		for (int i = 0; i < brandPageList.length; i++) {
			DpBrandPage brandPage = brandPageList[i];
			FileUpload uploadFile = brandPage.getUploadFile();

			// 브랜드페이지순번이 없으면 신규, 전시여부가 N이면 등록하지 않음
			if (UtilsObject.isEmpty(brandPage.getBrandPageSeq()) && !"Y".equals(brandPage.getDispYn())) {
				continue;
			}

			if (uploadFile != null && uploadFile.isFileItem()) {
				// 파일업로드 정보 구성
				brandPage = this.setUploadFileInfo(uploadFile, brandPage, "I");
				// 추가정보 구성
				brandPage.setImageName(uploadFile.getOrgFileName());
			}

			brandPage.setDispGbnType("P"); // 전시구분 T(상단비주얼), P(프로모션배너)
			brandPage.setBrandPageSeq(1); // 브랜드페이지순번
			brandPage.setSiteNo(Const.SITE_NO_ART); // 사이트번호 통합몰 고정
			brandPage.setTitleText( // 제목
					(CommonCode.DEVICE_PC.equals(brandPage.getDeviceCode()) ? "PC" : "Mobile") + " 브랜드 프로모션 배너");
			brandPage.setTextColorType("N"); // 텍스트컬러 W(White), B(Black), N(사용안함:d)
			brandPage.setBckgColorYn("N"); // 배경컬러사용여부
			brandPage.setFileType("I"); // 파일유형 I(이미지), M(동영상)
			brandPage.setSortSeq(1); // 정렬순번
			brandPage.setRgsterNo(LoginManager.getUserDetails().getAdminNo());

			String altrnText = brandPage.getAltrnText();
			brandPage.setAltrnText(UtilsObject.isEmpty(altrnText) ? "A-RT" : altrnText); // 대체텍스트

			if (Const.CRUD_I.equals(brandPage.getStatus())) {
				evCount = this.dpBrandPageDao.insert(brandPage);
			} else {
				evCount = this.dpBrandPageDao.update(brandPage);
			}
		}

		return evCount;
	}

	/**
	 * @Desc : 브랜드 페이지 저장
	 * @Method Name : inputBrandPageList
	 * @Date : 2019. 6. 5.
	 * @Author : 백인천
	 * @param brandPageList
	 * @return
	 * @throws Exception
	 */
	public int setBrandPageList(List<DpBrandPage> dpBrandPageList, String brandNo) throws Exception {
		UserDetails user = LoginManager.getUserDetails();
		Integer appliedCount = 0;
		for (DpBrandPage item : dpBrandPageList) {
			switch (item.getStatus()) {
			case "I":
				// 등록처리
				item.setBrandNo(brandNo);
				item.setRgsterNo(user.getAdminNo());
				item.setRgstDtm(new Timestamp(new Date().getTime()));
				this.dpBrandPageDao.insertBrandPage(item);
				break;
			case "U":
				// 업데이트 처리
				item.setModerNo(user.getAdminNo());
				item.setModDtm(new Timestamp(new Date().getTime()));
				this.dpBrandPageDao.update(item);
				break;
			case "D":
				// 삭제 처리
				this.dpBrandPageDao.delete(item);
				break;
			default:
				log.debug("등록되지 않은 상태값입니다. {}", item.getStatus());
			}
		}
		return appliedCount;
	}

	/**
	 * @Desc : 브랜드 페이지 삭제
	 * @Method Name : setBrandPageDelete
	 * @Date : 2019. 6. 5.
	 * @Author : 백인천
	 * @param brandPageList
	 * @return
	 * @throws Exception
	 */
	public int setBrandPageDelete(List<DpBrandPage> dpBrandPageList, String brandNo) throws Exception {
		Integer appliedCount = 0;
		for (DpBrandPage item : dpBrandPageList) {
			// 삭제 처리
			this.dpBrandPageDao.delete(item);
		}
		return appliedCount;
	}

	/**
	 * @Desc : 브랜드 통합몰 페이지 저장
	 * @Method Name : setBrandPageList
	 * @Date : 2019. 6. 5.
	 * @Author : 백인천
	 * @param brandPageList
	 * @param brand
	 * @return
	 * @throws Exception
	 */
	public int setBrandPageList(List<DpBrandPage> brandPageList, DpBrand brand) throws Exception {
		return this.setBrandPageList(brandPageList, brand.getBrandNo());
	}

	/**
	 * @Desc : 파일업로드 정보 구성
	 * @Method Name : setUploadFileInfo
	 * @Date : 2019. 8. 1.
	 * @Author : 백인천
	 * @param brandPage
	 * @param uploadFile
	 * @param Type
	 * @throws Exception
	 * @return DpBrandPage
	 */
	public DpBrandPage setUploadFileInfo(FileUpload uploadFile, DpBrandPage brandPage, String fileType)
			throws Exception {

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

		if (fileType.equals("I")) {
			brandPage.setImagePathText(UtilsText.concat(path, uploadFileName));
			brandPage.setImageUrl(UtilsText.concat(Const.URL_IMG_HTTPS, path, "/", uploadFileName));
		} else if (fileType.equals("M")) {
			brandPage.setMoviePathText(UtilsText.concat(path, uploadFileName));
			brandPage.setMovieUrl(UtilsText.concat(Const.URL_IMG_HTTPS, path, "/", uploadFileName));
		}
		return brandPage;
	}

}
