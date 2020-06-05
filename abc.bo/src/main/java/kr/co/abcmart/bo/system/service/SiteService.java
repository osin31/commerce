package kr.co.abcmart.bo.system.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.product.model.master.PdProduct;
import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.bo.system.model.master.SySite;
import kr.co.abcmart.bo.system.model.master.SySiteApplySns;
import kr.co.abcmart.bo.system.model.master.SySiteChnnl;
import kr.co.abcmart.bo.system.model.master.SySiteChnnlImage;
import kr.co.abcmart.bo.system.model.master.SySiteDeliveryGuide;
import kr.co.abcmart.bo.system.model.master.SySiteDeliveryType;
import kr.co.abcmart.bo.system.model.master.SySitePaymentMeans;
import kr.co.abcmart.bo.system.repository.master.SySiteApplySnsDao;
import kr.co.abcmart.bo.system.repository.master.SySiteChnnlDao;
import kr.co.abcmart.bo.system.repository.master.SySiteChnnlImageDao;
import kr.co.abcmart.bo.system.repository.master.SySiteDao;
import kr.co.abcmart.bo.system.repository.master.SySiteDeliveryGuideDao;
import kr.co.abcmart.bo.system.repository.master.SySiteDeliveryTypeDao;
import kr.co.abcmart.bo.system.repository.master.SySitePaymentMeansDao;
import kr.co.abcmart.bo.vendor.service.VendorService;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.FileUpload;
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
public class SiteService {

	// 사이트번호 초기값
	public static final String SITE_NO_INIT = "10000";
	// 채널번호 초기값
	public static final String CHANNEL_NO_INIT = "10000";

	private static final String FILE_PATH = UtilsText.concat(Const.UPLOAD_FILE_PATH, "/",
			Const.IMG_SRC_SYSTEM_SITE_LOGO);

	@Autowired
	SiteService service;

	@Autowired
	CommonCodeService commonCodeService;

	@Autowired
	SySiteDao sySiteDao;

	@Autowired
	SySiteApplySnsDao sySiteApplySnsDao;

	@Autowired
	SySiteChnnlDao sySiteChnnlDao;

	@Autowired
	SySiteChnnlImageDao sySiteChnnlImageDao;

	@Autowired
	SySiteDeliveryGuideDao syDeliveryGuideDao;

	@Autowired
	SySiteDeliveryTypeDao sySiteDeliveryTypeDao;

	@Autowired
	SySitePaymentMeansDao sySitePaymentMeansDao;

	@Autowired
	VendorService vendorService;

	/**
	 * @Desc : 사이트, 배송비용 조회
	 * @Method Name : getSite
	 * @Date : 2019. 4. 24.
	 * @Author : Kimyounghyun
	 * @param siteNo
	 * @return
	 * @throws Exception
	 */
	public SySite getSite(String siteNo) throws Exception {
		return sySiteDao.selectSite(siteNo);
	}

	/**
	 * @Desc : 사이트 목록을 조회한다.
	 * @Method Name : selectSiteList
	 * @Date : 2019. 2. 7.
	 * @Author : Kimyounghyun
	 * @return
	 * @throws Exception
	 */
	public List<SySite> getSiteList() throws Exception {

		return sySiteDao.selectSiteList();
	}

	/**
	 * @Desc : 사이트 목록을 조회한다. join admin
	 * @Method Name : selectAllWithAdmin
	 * @Date : 2019. 2. 7.
	 * @Author : Kimyounghyun
	 * @return
	 * @throws Exception
	 */
	public List<SySite> getAllWithAdmin() throws Exception {

		return sySiteDao.selectSiteListWithAdmin();
	}

	/**
	 * 사이트 정보를 등록/수정 한다. backend 서버에서 참조하는 cache를 삭제한다.
	 *
	 * @param sySite
	 * @throws Exception
	 */
	@CacheEvict(value = "siteService.getSiteList", allEntries = true)
	public void setSite(SySite sySite) throws Exception {
		setMod(sySite);

		if (UtilsText.isBlank(sySite.getSiteNo())) {
			sySite.setSiteNo(getMaxSiteNo());
			sySite.setRgsterNo(LoginManager.getUserDetails().getAdminNo());
			sySiteDao.insertSite(sySite);
		} else {
			sySiteDao.updateSite(sySite);

			// 사이트 '무료배송기준금액'이나 사이트 '배송비'가 변경되었다면 변경된 사이트번호를 물고있는 자사업체 정보를 수정한다.
			vendorService.setVendorBySySite(sySite);
		}

		service.setApplySns(sySite.getSiteNo(), sySite.getApplySnsArray());
		service.setDeliveryType(sySite.getSiteNo(), sySite.getDeliveryTypeList());
	}

	/**
	 * 사이트 수정정보를 set한다.
	 *
	 * @param sySite
	 * @throws Exception
	 */
	private void setMod(SySite sySite) throws Exception {
		sySite.setModerNo(LoginManager.getUserDetails().getAdminNo());
	}

	/**
	 * max site_no를 얻는다
	 *
	 * @return
	 * @throws Exception
	 */
	private String getMaxSiteNo() throws Exception {
		if (sySiteDao.selectMaxSiteNo() == null) {
			return SITE_NO_INIT;
		} else {
			int maxSiteNo = sySiteDao.selectMaxSiteNo();
			return Integer.toString(maxSiteNo + 1);
		}
	}

	/**
	 * 사이트에 해당하는 채널 목록을 페이징 형태로 조회한다.
	 *
	 * @param sySiteChnnl
	 * @return
	 * @throws Exception
	 */
	public Page<SySiteChnnl> getChannelForPaging(Pageable<SySiteChnnl, SySiteChnnl> pageable) throws Exception {
		int count = sySiteChnnlDao.selectChannelForPagingCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(sySiteChnnlDao.selectChannelForPaging(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 사용중인 채널목록을 조회한다.
	 * @Method Name : getUseChannelList
	 * @Date : 2019. 2. 7.
	 * @Author : Kimyounghyun
	 * @return
	 * @throws Exception
	 */
	public List<SySiteChnnl> getUseChannelList() throws Exception {
		return sySiteChnnlDao.selectUseChannelList();
	}

	/**
	 * @Desc : 채널 정보 조회
	 * @Method Name : getUseChannelListByCombo
	 * @Date : 2019. 7. 2.
	 * @Author : kiowa
	 * @return
	 * @throws Exception
	 */
	public Pair<JSONObject, List<SySiteChnnl>> getUseChannelListByCombo() throws Exception {
		List<SySiteChnnl> chnnelList = null;

		chnnelList = service.getUseChannelList().stream().filter(x -> Const.BOOLEAN_TRUE.equals(x.getSellPsbltYn()))
				.collect(Collectors.toList());

		/// site_no, site_name
		String siteName = chnnelList.stream().map(SySiteChnnl::getChnnlName).collect(Collectors.joining("|"));
		String siteValue = chnnelList.stream().map(SySiteChnnl::getChnnlNo).collect(Collectors.joining("|"));

		JSONObject json = new JSONObject();
		json.put("code", siteValue);
		json.put("text", siteName);

		return Pair.of(json, chnnelList);
	}

	/**
	 * @Desc : 입점사 사용 가능한 채널 목록을 조회한다.
	 * @Method Name : getVendorUseChannelList
	 * @Date : 2019. 4. 16.
	 * @Author : Kimyounghyun
	 * @return
	 * @throws Exception
	 */
	public List<SySiteChnnl> getVendorUseChannelList() throws Exception {
		return sySiteChnnlDao.selectVendorUseChannelList();
	}

	/**
	 * @Desc : 사이트별 사용중인 채널리스트 조회
	 * @Method Name : getUseChannelListBySiteNo
	 * @Date : 2019. 2. 20.
	 * @Author : Kimyounghyun
	 * @param siteNo
	 * @return
	 * @throws Exception
	 */
	public List<SySiteChnnl> getUseChannelListBySiteNo(String siteNo) throws Exception {
		return getChannelListBySiteNo(siteNo, true);
	}

	/**
	 * @Desc : 사이트별 채널리스트 조회
	 * @Method Name : getChannelListBySiteNo
	 * @Date : 2019. 2. 20.
	 * @Author : Kimyounghyun
	 * @param siteNo
	 * @param isUse  : true - 사용중, false - 전체
	 * @return
	 * @throws Exception
	 */
	public List<SySiteChnnl> getChannelListBySiteNo(String siteNo, boolean isUse) throws Exception {
		return sySiteChnnlDao.selectChannelList(siteNo, isUse);
	}

	/**
	 * @Desc : 채널에 부여된 입점사 번호를 반환한다.
	 * @Method Name : getVendorNo
	 * @Date : 2019. 12. 20.
	 * @Author : 최경호
	 * @param product
	 * @return
	 * @throws Exception
	 */
	public SySiteChnnl getVendorNo(PdProduct product) throws Exception {
		SySiteChnnl sySiteChnnl = new SySiteChnnl();
		sySiteChnnl.setChnnlNo(product.getChnnlNo());

		sySiteChnnl = sySiteChnnlDao.selectByPrimaryKey(sySiteChnnl);

		return sySiteChnnl;
	}

	/**
	 * @Desc : 채널에 부여된 입점사 번호를 반환한다.
	 * @Method Name : getVendorNo
	 * @Date : 2019. 7. 25.
	 * @Author : Kimyounghyun
	 * @param channelNo
	 * @return
	 * @throws Exception
	 */
	public String getVendorNo(String channelNo) throws Exception {

		SySiteChnnl sySiteChnnl = new SySiteChnnl();
		sySiteChnnl.setChnnlNo(channelNo);

		sySiteChnnl = sySiteChnnlDao.selectByPrimaryKey(sySiteChnnl);

		return sySiteChnnl.getVndrNo();
	}

	/**
	 * 채널 정보를 등록/수정한다. backend 서버에서 참조하는 cache를 삭제한다.
	 *
	 * @param sySiteChnnl
	 * @throws Exception
	 */
	@CacheEvict(value = { "siteService.getUseChannelListBySiteNo", "siteService.getUseChannelList",
			"siteService.getUseChannelImgListBySiteNo", "siteService.getUseChannelImgBySiteNo",
			"siteService.getSearchDisplayChannel" }, allEntries = true)
	public void setChannel(SySiteChnnl sySiteChnnl) throws Exception {

		UserDetails userDetails = LoginManager.getUserDetails();

		// 채널번호 존재 유무에 따라 등록, 상세 분기 처리
		if (UtilsText.isEmpty(sySiteChnnl.getChnnlNo())) {
			// 채널 상품 구분 번호 중복체크
			if (!service.hasCheckChannelProdNo(sySiteChnnl.getChnnlPrdtGbnNo())) {
				throw new Exception("이미 사용되고 있는 채널 상품 구분 번호입니다.");
			}

			// 등록
			sySiteChnnl.setChnnlNo(getMaxChannelNo());
			sySiteChnnl.setRgsterNo(userDetails.getAdminNo());
			sySiteChnnl.setModerNo(userDetails.getAdminNo());
			sySiteChnnlDao.insertChannel(sySiteChnnl);

			// 이미지 파일 등록
			service.setLogoImgUpload(sySiteChnnl.getChnnlNo(), CommonCode.DEVICE_PC, Const.IMG_TYPE_CHNNL,
					sySiteChnnl.getPcLogoImg());
			service.setLogoImgUpload(sySiteChnnl.getChnnlNo(), CommonCode.DEVICE_MOBILE, Const.IMG_TYPE_CHNNL,
					sySiteChnnl.getMoLogoImg());
			service.setLogoImgUpload(sySiteChnnl.getChnnlNo(), CommonCode.DEVICE_PC, Const.IMG_TYPE_GNB,
					sySiteChnnl.getGnbLogoImg());

		} else {
			// 채널 상세 데이터 수정
			sySiteChnnl.setModerNo(userDetails.getAdminNo());
			sySiteChnnlDao.updateChannel(sySiteChnnl);
			// 로고 이미지 업데이트
			service.setLogoImgUpdate(sySiteChnnl);
		}
	}

	/**
	 * @Desc : 로고이미지 업로드
	 * @Method Name : setLogoImgUpload
	 * @Date : 2019. 12. 19.
	 * @Author : 이동엽
	 * @param chnnlNo
	 * @param deviceCode
	 * @param imgType
	 * @param imgFile
	 * @throws Exception
	 */
	public void setLogoImgUpload(String chnnlNo, String deviceCode, String imgType, FileUpload imgFile)
			throws Exception {
		SySiteChnnlImage sySiteChnnlImage = new SySiteChnnlImage();
		UserDetails userDetails = LoginManager.getUserDetails();

		String fileName = imgFile.getOrgFileName();
		String uploadFileName = UtilsText.concat(String.valueOf(System.currentTimeMillis()), ".",
				FilenameUtils.getExtension(fileName));
		String path = UtilsText.concat(FILE_PATH, "/", UtilsDate.today(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS));

		try {
			UtilsSftp.upload(path, uploadFileName, imgFile.getMultiPartFile().getInputStream());
		} catch (Exception e) {
			log.error("{}", e);
			throw new Exception("파일저장에 실패하였습니다.");
		}

		sySiteChnnlImage.setChnnlNo(chnnlNo);
		sySiteChnnlImage.setDeviceCode(deviceCode);
		sySiteChnnlImage.setImageType(imgType);
		sySiteChnnlImage.setImageName(fileName);
		sySiteChnnlImage.setImagePathText(UtilsText.concat(path, uploadFileName));
		sySiteChnnlImage.setImageUrl(UtilsText.concat(Const.URL_IMG_HTTPS, "/", path, "/", uploadFileName));
		sySiteChnnlImage.setRgsterNo(userDetails.getAdminNo());
		sySiteChnnlImage.setModerNo(userDetails.getAdminNo());

		sySiteChnnlImageDao.insertSiteChnnlImg(sySiteChnnlImage);
	}

	/**
	 * @Desc : 로고이미지 업데이트
	 * @Method Name : setLogoImgUpdate
	 * @Date : 2019. 12. 19.
	 * @Author : 이동엽
	 * @param sySiteChnnl
	 * @throws Exception
	 */
	public void setLogoImgUpdate(SySiteChnnl sySiteChnnl) throws Exception {
		// 채널로고이미지(PC)
		if (sySiteChnnl.getPcLogoImg() != null && sySiteChnnl.getPcLogoImg().isFileItem()) {
			service.setRemoveLogoImg(sySiteChnnl.getChnnlNo(), CommonCode.DEVICE_PC, Const.IMG_TYPE_CHNNL);
			service.setLogoImgUpload(sySiteChnnl.getChnnlNo(), CommonCode.DEVICE_PC, Const.IMG_TYPE_CHNNL,
					sySiteChnnl.getPcLogoImg());
		}
		// 채널로고이미지(MO)
		if (sySiteChnnl.getMoLogoImg() != null && sySiteChnnl.getMoLogoImg().isFileItem()) {
			service.setRemoveLogoImg(sySiteChnnl.getChnnlNo(), CommonCode.DEVICE_MOBILE, Const.IMG_TYPE_CHNNL);
			service.setLogoImgUpload(sySiteChnnl.getChnnlNo(), CommonCode.DEVICE_MOBILE, Const.IMG_TYPE_CHNNL,
					sySiteChnnl.getMoLogoImg());
		}
		// GNB로고이미지
		if (sySiteChnnl.getGnbLogoImg() != null && sySiteChnnl.getGnbLogoImg().isFileItem()) {
			service.setRemoveLogoImg(sySiteChnnl.getChnnlNo(), CommonCode.DEVICE_PC, Const.IMG_TYPE_GNB);
			service.setLogoImgUpload(sySiteChnnl.getChnnlNo(), CommonCode.DEVICE_PC, Const.IMG_TYPE_GNB,
					sySiteChnnl.getGnbLogoImg());
		}
	}

	/**
	 * max chnnl_no를 얻는다.
	 *
	 * @return
	 * @throws Exception
	 */
	private String getMaxChannelNo() throws Exception {
		if (sySiteChnnlDao.selectMaxChannelNo() == null) {
			return CHANNEL_NO_INIT;
		} else {
			int maxChannelNo = sySiteChnnlDao.selectMaxChannelNo();
			return Integer.toString(maxChannelNo + 1);
		}
	}

	/**
	 * 배송/클레임 정책을 조회한다.
	 *
	 * @param sySiteDeliveryGuide
	 * @return
	 * @throws Exception
	 */
	public SySiteDeliveryGuide getDeliveryGuide(SySiteDeliveryGuide sySiteDeliveryGuide) throws Exception {
		return syDeliveryGuideDao.selectWithSyAdminByPrimaryKey(sySiteDeliveryGuide);
	}

	/**
	 * @Desc : 사이트의 사용중인 배송유형을 조회한다.
	 * @Method Name : getUseDeliveryType
	 * @Date : 2019. 2. 21.
	 * @Author : Kimyounghyun
	 * @param siteNo
	 * @return
	 * @throws Exception
	 */
	public List<SySiteDeliveryType> getUseDeliveryType(String siteNo) throws Exception {
		return getDeliveryType(siteNo, true);
	}

	/**
	 * @Desc : 사이트의 배송유형 전체를 조회한다.
	 * @Method Name : getDeliveryType
	 * @Date : 2019. 2. 21.
	 * @Author : Kimyounghyun
	 * @param siteNo
	 * @return
	 * @throws Exception
	 */
	public List<SySiteDeliveryType> getDeliveryType(String siteNo) throws Exception {
		return getDeliveryType(siteNo, false);
	}

	/**
	 * @Desc : 사아트의 배송유형을 조회한다.
	 * @Method Name : getUseDeliveryType
	 * @Date : 2019. 2. 21.
	 * @Author : Kimyounghyun
	 * @param siteNo
	 * @param isUse  : true - 사용중, false - all
	 * @return
	 * @throws Exception
	 */
	public List<SySiteDeliveryType> getDeliveryType(String siteNo, boolean isUse) throws Exception {
		return sySiteDeliveryTypeDao.selectBySiteNo(siteNo, isUse);
	}

	/**
	 * 배송유형을 저장한다.
	 *
	 * @param list
	 * @throws Exception
	 */
	public void setDeliveryType(String siteNo, SySiteDeliveryType[] list) throws Exception {
		sySiteDeliveryTypeDao.deleteBySiteNo(siteNo);

		for (SySiteDeliveryType sySiteDeliveryType : list) {
			sySiteDeliveryType.setSiteNo(siteNo);
			sySiteDeliveryType.setRgsterNo(LoginManager.getUserDetails().getAdminNo());
			sySiteDeliveryType.setModerNo(LoginManager.getUserDetails().getAdminNo());

			sySiteDeliveryTypeDao.insertDeliveryType(sySiteDeliveryType);
		}
	}

	/**
	 * 정책을 삭제/등록한다.
	 *
	 * @param sySiteDeliveryGuide
	 * @throws Exception
	 */
	public void setDeliveryGuide(SySiteDeliveryGuide sySiteDeliveryGuide) throws Exception {
		syDeliveryGuideDao.deleteBySiteNoAndCode(sySiteDeliveryGuide);

		sySiteDeliveryGuide.setRgsterNo(LoginManager.getUserDetails().getAdminNo());
		sySiteDeliveryGuide.setModerNo(LoginManager.getUserDetails().getAdminNo());

		syDeliveryGuideDao.insertGuide(sySiteDeliveryGuide);
	}

	/**
	 * 결제수단 정보를 조회한다.
	 *
	 * @param sySitePaymentMeans
	 * @return
	 */
	public List<SySitePaymentMeans> getPaymentBySiteNo(SySitePaymentMeans sySitePaymentMeans) {
		return sySitePaymentMeansDao.selectPaymentBySiteNo(sySitePaymentMeans);
	}

	/**
	 * 결제수단 삭제/등록한다.
	 *
	 * @param sySitePaymentMeans
	 * @throws Exception
	 */
	public void setPayment(SySitePaymentMeans[] sySitePaymentMeans) throws Exception {
		sySitePaymentMeansDao.deleteBySiteNo(sySitePaymentMeans[0]);

		for (SySitePaymentMeans paymentMeans : sySitePaymentMeans) {
			paymentMeans.setRgsterNo(LoginManager.getUserDetails().getAdminNo());
			paymentMeans.setModerNo(LoginManager.getUserDetails().getAdminNo());

			sySitePaymentMeansDao.insertByMeansSeqMax(paymentMeans);
		}
	}

	/**
	 * @Desc : 사이트 관리에서 필요한 공통코드를 조회한다.
	 * @Method Name : getCommonCode
	 * @Date : 2019. 2. 8.
	 * @Author : Kimyounghyun
	 * @return
	 * @throws Exception
	 */
	public Map<String, List<SyCodeDetail>> getCommonCode() throws Exception {
		Map<String, List<SyCodeDetail>> map = new HashMap<String, List<SyCodeDetail>>();

		map.put(CommonCode.DLVY_TYPE_CODE, commonCodeService.getCodeNoName(CommonCode.DLVY_TYPE_CODE));
		map.put(CommonCode.PYMNT_MEANS_CODE, commonCodeService.getCodeNoName(CommonCode.PYMNT_MEANS_CODE));
		map.put(CommonCode.DLVY_GUIDE_BGN_CODE, commonCodeService.getCodeNoName(CommonCode.DLVY_GUIDE_BGN_CODE));
		map.put(CommonCode.SNS_CHNNL_CODE, commonCodeService.getCodeNoName(CommonCode.SNS_CHNNL_CODE));

		return map;
	}

	/**
	 * @Desc : 사이트 데이터 조회 & grid combo code 조회
	 * @Method Name : getSiteListByCombo
	 * @Date : 2019. 4. 1.
	 * @Author : Kimyounghyun
	 * @return
	 * @throws Exception
	 */
	public Pair<JSONObject, List<SySite>> getSiteListByCombo() throws Exception {
		List<SySite> siteList = null;

		siteList = service.getSiteList();

		/// site_no, site_name
		String siteName = siteList.stream().map(SySite::getSiteName).collect(Collectors.joining("|"));
		String siteValue = siteList.stream().map(SySite::getSiteNo).collect(Collectors.joining("|"));

		JSONObject json = new JSONObject();
		json.put("code", siteValue);
		json.put("text", siteName);

		return Pair.of(json, siteList);
	}

	/**
	 * @Desc : sns공유를 저장한다.
	 * @Method Name : setApplySns
	 * @Date : 2019. 3. 20.
	 * @Author : Kimyounghyun
	 * @param siteNo
	 * @param applySnsList
	 */
	public void setApplySns(String siteNo, SySiteApplySns[] applySnsArray) throws Exception {
		sySiteApplySnsDao.deleteBySiteNo(siteNo);

		for (SySiteApplySns sySiteApplySns : applySnsArray) {
			sySiteApplySns.setSiteNo(siteNo);
			sySiteApplySns.setRgsterNo(LoginManager.getUserDetails().getAdminNo());
			sySiteApplySns.setModerNo(LoginManager.getUserDetails().getAdminNo());

			sySiteApplySnsDao.insertApplySns(sySiteApplySns);
		}

	}

	/**
	 * @Desc : 채널에 따른 사이트 조회
	 * @Method Name : getSiteNo
	 * @Date : 2019. 12. 11.
	 * @Author : 이동엽
	 * @param chnnlNo
	 * @return
	 * @throws Exception
	 */
	public String getSiteNo(String chnnlNo) throws Exception {
		return sySiteChnnlDao.selectSiteNo(chnnlNo);
	}

	/**
	 * @Desc : 채널상세 조회
	 * @Method Name : getChnnlDetailInfo
	 * @Date : 2019. 12. 17.
	 * @Author : 이동엽
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public SySiteChnnl getChnnlDetailInfo(SySiteChnnl params) throws Exception {
		// 채널 상세조회
		SySiteChnnl result = sySiteChnnlDao.selectChnnlDetailInfo(params);

		// 해당 채널의 이미지 리스트를 조회
		SySiteChnnlImage siteChnnlImage = new SySiteChnnlImage();
		siteChnnlImage.setChnnlNo(result.getChnnlNo());
		List<SySiteChnnlImage> listResult = sySiteChnnlImageDao.select(siteChnnlImage);

		// 조회해 온 이미지 리스트를 상세에 세팅
		for (SySiteChnnlImage sySiteChnnlImage : listResult) {
			if (UtilsText.equals(sySiteChnnlImage.getDeviceCode(), CommonCode.DEVICE_PC)
					&& UtilsText.equals(sySiteChnnlImage.getImageType(), "C")) {
				result.setPcLogoImgUrl(sySiteChnnlImage.getImageUrl());
				result.setPcLogoImgName(sySiteChnnlImage.getImageName());
			}

			if (UtilsText.equals(sySiteChnnlImage.getDeviceCode(), CommonCode.DEVICE_MOBILE)
					&& UtilsText.equals(sySiteChnnlImage.getImageType(), "C")) {
				result.setMoLogoImgUrl(sySiteChnnlImage.getImageUrl());
				result.setMoLogoImgName(sySiteChnnlImage.getImageName());
			}

			if (UtilsText.equals(sySiteChnnlImage.getDeviceCode(), CommonCode.DEVICE_PC)
					&& UtilsText.equals(sySiteChnnlImage.getImageType(), "G")) {
				result.setGnbLogoImgUrl(sySiteChnnlImage.getImageUrl());
				result.setGnbLogoImgName(sySiteChnnlImage.getImageName());
			}
		}

		return result;
	}

	/**
	 * @Desc : 로고 이미지 삭제
	 * @Method Name : setRemoveLogoImg
	 * @Date : 2019. 12. 19.
	 * @Author : 이동엽
	 * @param chnnlNo
	 * @param deviceCode
	 * @param imgType
	 * @throws Exception
	 */
	public void setRemoveLogoImg(String chnnlNo, String deviceCode, String imgType) throws Exception {
		SySiteChnnlImage params = new SySiteChnnlImage();
		params.setChnnlNo(chnnlNo);
		params.setDeviceCode(deviceCode);
		params.setImageType(imgType);
		sySiteChnnlImageDao.deleteChnnlImg(params);
	}

	/**
	 * @Desc : 검색영역 전시 가능 채널 목록 조회
	 * @Method Name : getSearchDisplayChannel
	 * @Date : 2019. 12. 19.
	 * @Author : 3TOP
	 * @return
	 * @throws Exception
	 */
	public List<SySiteChnnl> getSearchDisplayChannel(SySiteChnnl sySiteChnnl) throws Exception {
		return sySiteChnnlDao.selectSearchDisplayChannel(sySiteChnnl);
	}

	/**
	 * @Desc : 채널 상품 구분번호 중복 체크
	 * @Method Name : hasCheckChannelProdNo
	 * @Date : 2019. 12. 26.
	 * @Author : 3TOP
	 * @param chnnlPrdtGbnNo
	 * @return
	 * @throws Exception
	 */
	public boolean hasCheckChannelProdNo(String chnnlPrdtGbnNo) throws Exception {
		boolean result = true;
		if (sySiteChnnlDao.selectChannelProdNo(chnnlPrdtGbnNo) > 0) {
			result = false;
		}
		return result;
	}
}
