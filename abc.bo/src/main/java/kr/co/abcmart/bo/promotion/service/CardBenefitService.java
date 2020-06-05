package kr.co.abcmart.bo.promotion.service;

import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.promotion.model.master.EvCardBenefit;
import kr.co.abcmart.bo.promotion.repository.master.EvCardBenefitDao;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.FileUpload;
import kr.co.abcmart.common.util.UtilsDate;
import kr.co.abcmart.common.util.UtilsSftp;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CardBenefitService {

	private static final String FILE_PATH = UtilsText.concat(Const.UPLOAD_FILE_PATH,
			Const.IMG_SRC_PROMOTION_CARDBENEFIT);

	@Autowired
	EvCardBenefitDao evCardBenefitDao;

	/**
	 * 
	 * @Desc :카드사 혜택관리 목록 조회
	 * @Method Name : getCardBenefitList
	 * @Date : 2019. 2. 25.
	 * @Author : 이가영
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Page<EvCardBenefit> getCardBenefitList(Pageable<EvCardBenefit, EvCardBenefit> pageable) throws Exception {

		int count = evCardBenefitDao.selectCardBenefitCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(evCardBenefitDao.selectCardBenefitList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * 
	 * @Desc :카드사 혜택관리 상세 조회
	 * @Method Name : getCardBenefitDetail
	 * @Date : 2019. 2. 26.
	 * @Author : 이가영
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public EvCardBenefit getCardBenefitDetail(EvCardBenefit parameter) throws Exception {

		EvCardBenefit detail = evCardBenefitDao.selectCardBenefitDetail(parameter);

		return detail;
	}

	/**
	 * 
	 * @Desc :카드사 혜택관리 상세 등록
	 * @Method Name : insertCardBenefit
	 * @Date : 2019. 2. 26.
	 * @Author : 이가영
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public void insertCardBenefitDetail(EvCardBenefit parameter) throws Exception {

		UserDetails user = LoginManager.getUserDetails();
		parameter.setRgsterNo(user.getAdminNo());
		parameter.setModerNo(user.getAdminNo());

		// 카드혜택 이미지
		FileUpload cardBenefitImageFile = parameter.getCardBenefitImageFile();

		if (cardBenefitImageFile != null && cardBenefitImageFile.isFileItem()) {

			String fileName = cardBenefitImageFile.getOrgFileName();
			String uploadFileName = UtilsText.concat(String.valueOf(System.currentTimeMillis()), ".",
					FilenameUtils.getExtension(fileName));
			String path = UtilsText.concat(FILE_PATH, "/", UtilsDate.today(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS));

			try {
				UtilsSftp.upload(path, uploadFileName, cardBenefitImageFile.getMultiPartFile().getInputStream());
			} catch (Exception e) {
				log.error("{}", e);
				throw new Exception("파일저장에 실패하였습니다.");
			}

			parameter.setCardBenefitImagePathText(UtilsText.concat(path, uploadFileName));
			parameter.setCardBenefitImageUrl(UtilsText.concat(Const.URL_IMG_HTTPS, "/", path, "/", uploadFileName));
			parameter.setCardBenefitImageName(fileName);

			if (UtilsText.isBlank(parameter.getCardBenefitAltrnText())) {
				parameter.setCardBenefitAltrnText(fileName.split("\\.")[0]);
			}
		}

		evCardBenefitDao.insert(parameter);

		// 등록 후 이미지 업로드 파일 초기화
		parameter.setCardBenefitImageFile(null);
	}

	/**
	 * 
	 * @Desc :카드사 혜택관리 상세 수정
	 * @Method Name : updateCardBenefit
	 * @Date : 2019. 2. 26.
	 * @Author : 이가영
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public void updateCardBenefitDetail(EvCardBenefit parameter) throws Exception {

		UserDetails user = LoginManager.getUserDetails();

		parameter.setModerNo(user.getAdminNo());
		parameter.setModDtm(UtilsDate.getSqlTimeStamp());

		// 카드혜택 이미지
		FileUpload cardBenefitImageFile = parameter.getCardBenefitImageFile();

		if (cardBenefitImageFile != null && cardBenefitImageFile.isFileItem()) {
			String fileName = cardBenefitImageFile.getOrgFileName();
			String uploadFileName = UtilsText.concat(String.valueOf(System.currentTimeMillis()), ".",
					FilenameUtils.getExtension(fileName));
			String path = UtilsText.concat(FILE_PATH, "/", UtilsDate.today(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS));

			try {
				UtilsSftp.upload(path, uploadFileName, cardBenefitImageFile.getMultiPartFile().getInputStream());
			} catch (Exception e) {
				log.error("{}", e);
				throw new Exception("파일저장에 실패하였습니다.");
			}

			parameter.setCardBenefitImagePathText(UtilsText.concat(path, uploadFileName));
			parameter.setCardBenefitImageUrl(UtilsText.concat(Const.URL_IMG_HTTPS, "/", path, "/", uploadFileName));
			parameter.setCardBenefitImageName(fileName);

			if (UtilsText.isBlank(parameter.getCardBenefitAltrnText())) {
				parameter.setCardBenefitAltrnText(fileName.split("\\.")[0]);
			}
		} else {
			if (UtilsText.isBlank(parameter.getCardBenefitAltrnText())) {
				parameter.setCardBenefitAltrnText(parameter.getCardBenefitImageName());
			}
		}

		evCardBenefitDao.updateCardBenefitDetail(parameter);

		// 등록 후 이미지 업로드 파일 초기화
		parameter.setCardBenefitImageFile(null);
	}

	/**
	 * 
	 * @Desc :카드사 혜택관리 등록/수정(전시기간 중복 조회)
	 * @Method Name : updateCardBenefit
	 * @Date : 2019. 3. 4.
	 * @Author : 이가영
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<EvCardBenefit> getCardBenefitCountByDate(EvCardBenefit parameter) throws Exception {

		List<EvCardBenefit> list = evCardBenefitDao.selectCardBenefitCountByDate(parameter);

		return list;
	}

	/**
	 * 
	 * @Desc :카드사 혜택관리 수정
	 * @Method Name : updateCardBenefit
	 * @Date : 2019. 2. 26.
	 * @Author : 이가영
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public void updateCardBenefit(EvCardBenefit parameter) throws Exception {

		evCardBenefitDao.updateCardBenefit(parameter);
	}
}
