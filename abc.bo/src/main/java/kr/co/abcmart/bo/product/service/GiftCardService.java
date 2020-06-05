package kr.co.abcmart.bo.product.service;

import java.sql.Timestamp;
import java.util.Date;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.product.model.master.PdGiftCard;
import kr.co.abcmart.bo.product.repository.master.PdGiftCardDao;
import kr.co.abcmart.bo.product.vo.PdGiftCardSearchVO;
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

/**
 * @Desc : 기프트 카드 서비스
 * @FileName : GiftCardService.java
 * @Project : abc.bo
 * @Date : 2019. 4. 11.
 * @Author : hsjhsj19
 */
@Slf4j
@Service
public class GiftCardService {

	private static final String FILE_PATH = UtilsText.concat(Const.UPLOAD_FILE_PATH, Const.IMG_SRC_PRODUCT_GIFTCARD);

	@Autowired
	private PdGiftCardDao giftCardDao;

	/**
	 * @Desc : 기프트 카드 목록 조회
	 * @Method Name : selectGiftCard
	 * @Date : 2019. 4. 11.
	 * @Author : hsjhsj19
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<PdGiftCard> selectGiftCard(Pageable<PdGiftCardSearchVO, PdGiftCard> pageable) throws Exception {
		Integer count = this.giftCardDao.selectGiftCardCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(this.giftCardDao.selectGiftCard(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 기프트카드 등록/수정
	 * @Method Name : saveGiftCard
	 * @Date : 2019. 4. 17.
	 * @Author : hsjhsj19
	 * @param giftCard
	 * @return
	 * @throws Exception
	 */
	public int saveGiftCard(PdGiftCard giftCard) throws Exception {
		// 세션 정보 호출
		UserDetails user = LoginManager.getUserDetails();
		giftCard.setModDtm(new Timestamp(new Date().getTime()));
		giftCard.setModerNo(user.getAdminNo());

		String[] giftCardTypeCodeArr = giftCard.getGiftCardTypeCodeArr();

		// PVC와 MMS를 두개 다 선택한 경우
		if (1 < giftCardTypeCodeArr.length) {
			giftCard.setGiftCardTypeCode("10002");
		} else {
			giftCard.setGiftCardTypeCode(giftCardTypeCodeArr[0]);
		}

		// 파일 저장
		FileUpload imageFile = giftCard.getImageFile();
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

			giftCard.setImageName(fileName);
			giftCard.setImagePathText(UtilsText.concat(path, uploadFileName));
			giftCard.setImageUrl(UtilsText.concat(Const.URL_IMG_HTTPS, "/", path, "/", uploadFileName));

			// 대체 텍스트가 비어있으면 이미지명으로 대체
			if (UtilsText.isEmpty(giftCard.getAltrnText())) {
				giftCard.setAltrnText(fileName);
			}
		}

		if ("save".equals(giftCard.getTypeGbn())) {
			// 충전형일 경우 판매가 0
			if ("1".equals(giftCard.getGiftCardGbnType())) {
				giftCard.setSellAmt(0);
			}

			giftCard.setRgstDtm(new Timestamp(new Date().getTime()));
			giftCard.setRgsterNo(user.getAdminNo());

			return giftCardDao.insertWithSelectKey(giftCard);
		} else {
			return giftCardDao.update(giftCard);
		}
	}

	/**
	 * @Desc : 기프트카드 상세 조회
	 * @Method Name : searchGiftCardByPrimaryKey
	 * @Date : 2019. 4. 12.
	 * @Author : hsjhsj19
	 * @param giftCard
	 * @return
	 * @throws Exception
	 */
	public PdGiftCard searchGiftCardByPrimaryKey(PdGiftCard giftCard) throws Exception {
		return this.giftCardDao.selectByPrimaryKey(giftCard);
	}

}
