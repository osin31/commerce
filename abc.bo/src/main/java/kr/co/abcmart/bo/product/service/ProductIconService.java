package kr.co.abcmart.bo.product.service;

import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.product.model.master.CmProductIcon;
import kr.co.abcmart.bo.product.model.master.PdProductIcon;
import kr.co.abcmart.bo.product.repository.master.CmProductIconDao;
import kr.co.abcmart.bo.product.repository.master.PdProductIconDao;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.FileUpload;
import kr.co.abcmart.common.util.UtilsDate;
import kr.co.abcmart.common.util.UtilsSftp;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;
import kr.co.abcmart.util.UtilsText;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductIconService {

	private static final String FILE_PATH = UtilsText.concat(Const.UPLOAD_FILE_PATH, Const.IMG_SRC_PRODUCT_ICON);

	@Autowired
	CmProductIconDao cmProductIconDao;

	@Autowired
	PdProductIconDao pdProductIconDao;

	/**
	 * 
	 * @Desc : 상품 아이콘 목록 조회
	 * @Method Name : getIconList
	 * @Date : 2019. 2. 12.
	 * @Author : 이가영
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Page<CmProductIcon> getIconList(Pageable<CmProductIcon, CmProductIcon> pageable) throws Exception {

		int count = cmProductIconDao.selectIconCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(cmProductIconDao.selectIconList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * 
	 * @Desc : 사용 중인 상품 아이콘 목록 조회
	 * @Method Name : getUseIconList
	 * @Date : 2019. 3. 7.
	 * @Author : 이가영
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<CmProductIcon> getUseIconList() throws Exception {
		CmProductIcon cmProductIcon = new CmProductIcon();
		return cmProductIconDao.selectUseIconList(cmProductIcon);
	}

	/**
	 * @Desc : 사용 중인 상품 아이콘 목록 조회
	 * @Method Name : getUseIconList
	 * @Date : 2019. 7. 18.
	 * @Author : hsjhsj19
	 * @param productIcon
	 * @return
	 * @throws Exception
	 */
	public List<CmProductIcon> getUseIconList(CmProductIcon productIcon) throws Exception {
		return cmProductIconDao.selectUseIconList(productIcon);
	}

	/**
	 * 
	 * @Desc :상품아이콘 이미지 등록
	 * @Method Name : setIcon
	 * @Date : 2019. 2. 13.
	 * @Author : 이가영
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public CmProductIcon setIconImage(CmProductIcon params) throws Exception {

		FileUpload imageFile = params.getImageFile();
		CmProductIcon result = new CmProductIcon();

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

			result.setIconPathText(UtilsText.concat(path, uploadFileName));
			result.setIconUrl(UtilsText.concat(Const.URL_IMG_HTTPS, "/", path, "/", uploadFileName));
			result.setIconName(fileName);

		}

		return result;
	}

	/**
	 * 
	 * @Desc :상품아이콘 등록
	 * @Method Name : setIcon
	 * @Date : 2019. 2. 13.
	 * @Author : 이가영
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public void setIcon(CmProductIcon[] params) throws Exception {

		UserDetails user = LoginManager.getUserDetails();

		for (CmProductIcon icon : params) {

			icon.setModerNo(user.getAdminNo());

			if (UtilsText.equals(icon.getStatus(), "I")) {
				icon.setRgsterNo(user.getAdminNo());
				cmProductIconDao.insertIcon(icon);
			} else if (UtilsText.equals(icon.getStatus(), Const.CRUD_U)) {
				icon.setModDtm(UtilsDate.getSqlTimeStamp());
				cmProductIconDao.updateIcon(icon);
			}
		}
	}

	/**
	 * @Desc : 아이콘 대상 상품 리스트 조회
	 * @Method Name : getIconMappingProductList
	 * @Date : 2019. 5. 22.
	 * @Author : 이가영
	 * @param cmProductIcon
	 */
	public Page<PdProductIcon> getIconMappingProductList(Pageable<PdProductIcon, PdProductIcon> pageable)
			throws Exception {

		int count = pdProductIconDao.selectIconMappingProductListCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(pdProductIconDao.selectIconMappingProductList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 사용 중인 수동설정 상품 아이콘 목록 조회
	 * @Method Name : getUseManualIconList
	 * @Date : 2019. 11. 27.
	 * @Author : tennessee
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	public List<CmProductIcon> getUseManualIconList() throws Exception {
		CmProductIcon cmProductIcon = new CmProductIcon();
		cmProductIcon.setAutoApplyYn(Const.BOOLEAN_FALSE);
		return cmProductIconDao.selectUseIconList(cmProductIcon);
	}

}
