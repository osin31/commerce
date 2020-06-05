package kr.co.abcmart.bo.promotion.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.product.model.master.PdFreeGift;
import kr.co.abcmart.bo.promotion.model.master.PrPromotion;
import kr.co.abcmart.bo.promotion.model.master.PrPromotionImage;
import kr.co.abcmart.bo.promotion.model.master.PrPromotionMultiBuyDiscount;
import kr.co.abcmart.bo.promotion.model.master.PrPromotionTargetBrand;
import kr.co.abcmart.bo.promotion.model.master.PrPromotionTargetCategory;
import kr.co.abcmart.bo.promotion.model.master.PrPromotionTargetChannel;
import kr.co.abcmart.bo.promotion.model.master.PrPromotionTargetDevice;
import kr.co.abcmart.bo.promotion.model.master.PrPromotionTargetGrade;
import kr.co.abcmart.bo.promotion.model.master.PrPromotionTargetProduct;
import kr.co.abcmart.bo.promotion.repository.master.PrPromotionDao;
import kr.co.abcmart.bo.promotion.repository.master.PrPromotionImageDao;
import kr.co.abcmart.bo.promotion.repository.master.PrPromotionMultiBuyDiscountDao;
import kr.co.abcmart.bo.promotion.repository.master.PrPromotionTargetBrandDao;
import kr.co.abcmart.bo.promotion.repository.master.PrPromotionTargetCategoryDao;
import kr.co.abcmart.bo.promotion.repository.master.PrPromotionTargetChannelDao;
import kr.co.abcmart.bo.promotion.repository.master.PrPromotionTargetDeviceDao;
import kr.co.abcmart.bo.promotion.repository.master.PrPromotionTargetGradeDao;
import kr.co.abcmart.bo.promotion.repository.master.PrPromotionTargetProductDao;
import kr.co.abcmart.bo.promotion.vo.PrPromotionSearchVO;
import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PromotionService {
	@Autowired
	private PrPromotionDao prPromotionDao;

	@Autowired
	private PrPromotionTargetDeviceDao prPromotionTargetDeviceDao;

	@Autowired
	private PrPromotionTargetChannelDao prPromotionTargetChannelDao;

	@Autowired
	private PrPromotionTargetCategoryDao prPromotionTargetCategoryDao;

	@Autowired
	private PrPromotionTargetGradeDao prPromotionTargetGradeDao;

	@Autowired
	private PrPromotionMultiBuyDiscountDao prPromotionMultiBuyDiscountDao;

	@Autowired
	private PrPromotionImageDao prPromotionImageDao;

	@Autowired
	private PrPromotionTargetBrandDao prPromotionTargetBrandDao;

	@Autowired
	private PrPromotionTargetProductDao prPromotionTargetProductDao;

	/**
	 * 프로모션 목록 조회
	 * 
	 * @Desc : 프로모션 목록 조회
	 * @Method Name : getPromotionList
	 * @Date : 2019. 3. 13
	 * @Author : easyhun
	 * @param pageable
	 * @return
	 */
	public Page<PrPromotion> getPromotionList(Pageable<PrPromotionSearchVO, PrPromotion> pageable) throws Exception {

		int count = prPromotionDao.selectPrPromotionCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(prPromotionDao.selectPrPromotionList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * 프로모션 상세 조회
	 * 
	 * @Desc : 프로모션 상세 조회
	 * @Method Name : getPromotion
	 * @Date : 2019. 3. 13
	 * @Author : easyhun
	 * @param PrCoupon
	 * @return
	 */
	public PrPromotion getPromotion(PrPromotion prPromotion) throws Exception {

		return prPromotionDao.selectPrPromotion(prPromotion);
	}

	/**
	 * 프로모션 디바이스 조회
	 * 
	 * @Desc : 프로모션 디바이스 조회
	 * @Method Name : getPrPromotionDeviceListByPromoNo
	 * @Date : 2019. 3. 14
	 * @Author : easyhun
	 * @param promoNo
	 * @return
	 */
	public List<PrPromotionTargetDevice> getPrPromotionDeviceListByPromoNo(String promoNo) throws Exception {

		return prPromotionTargetDeviceDao.selectPrPromotionDeviceListByPromoNo(promoNo);
	}

	/**
	 * 프로모션 채널 조회
	 * 
	 * @Desc : 프로모션 채널 조회
	 * @Method Name : getPrPromotionDeviceListByPromoNo
	 * @Date : 2019. 3. 14
	 * @Author : easyhun
	 * @param promoNo
	 * @return
	 */
	public List<PrPromotionTargetChannel> getPrPromotionChannelListByPromoNo(String promoNo) throws Exception {

		return prPromotionTargetChannelDao.selectPrPromotionChannelListByPromoNo(promoNo);
	}

	/**
	 * 프로모션 카테고리 조회
	 * 
	 * @Desc : 프로모션 카테고리 조회
	 * @Method Name : getPrPromotionDeviceListByPromoNo
	 * @Date : 2019. 3. 14
	 * @Author : easyhun
	 * @param promoNo
	 * @return
	 */
	public List<PrPromotionTargetCategory> getPrPromotionCategoryListByPromoNo(String promoNo) throws Exception {

		return prPromotionTargetCategoryDao.selectPrPromotionCategoryListByPromoNo(promoNo);
	}

	/**
	 * 프로모션 다족구매 조회
	 * 
	 * @Desc : 프로모션 다족구매 조회
	 * @Method Name : getPromotionMultiBuyDiscountListByPromoNo
	 * @Date : 2019. 3. 20
	 * @Author : easyhun
	 * @param promoNo
	 * @return
	 */
	public List<PrPromotionMultiBuyDiscount> getPromotionMultiBuyDiscountListByPromoNo(String promoNo)
			throws Exception {

		return prPromotionMultiBuyDiscountDao.selectPromotionMultiBuyDiscountListByPromoNo(promoNo);
	}

	/**
	 * 프로모션 회원 유형 조회
	 * 
	 * @Desc : 프로모션 회원 유형 조회
	 * @Method Name : getPrPromotionTargetGradeByPromoNo
	 * @Date : 2019. 3. 20
	 * @Author : easyhun
	 * @param promoNo
	 * @return
	 */
	public List<PrPromotionTargetGrade> getPrPromotionTargetGradeListByPromoNo(String promoNo) throws Exception {

		return prPromotionTargetGradeDao.selectPrPromotionTargetGradeListByPromoNo(promoNo);
	}

	/**
	 * 프로모션 브랜드 조회
	 * 
	 * @Desc : 프로모션 브랜드 조회
	 * @Method Name : getPrPromotionTargetBrandListByPromoNo
	 * @Date : 2019. 4. 24
	 * @Author : easyhun
	 * @param promoNo
	 * @return
	 */
	public List<PrPromotionTargetBrand> getPrPromotionTargetBrandListByPromoNo(String promoNo) throws Exception {

		return prPromotionTargetBrandDao.selectPrPromotionTargetBrandListByPromoNo(promoNo);
	}

	/**
	 * 프로모션 이미지 조회
	 * 
	 * @Desc : 프로모션 이미지 조회
	 * @Method Name : getPrPromotionImageListByPromoNo
	 * @Date : 2019. 3. 20
	 * @Author : easyhun
	 * @param promoNo
	 * @return
	 */
	public List<PrPromotionImage> getPrPromotionImageListByPromoNo(String promoNo) throws Exception {

		return prPromotionImageDao.selectPrPromotionImageListByPromoNo(promoNo);
	}

	/**
	 * 내부 관리번호 count 조회
	 * 
	 * @Desc : 내부 관리번호 count 조회
	 * @Method Name : getPromotionDuplCheck
	 * @Date : 2019. 3. 15
	 * @Author : easyhun
	 * @param promoNo
	 * @return
	 * @throws Exception
	 */
	public boolean getPromotionDuplCheck(String insdMgmtInfoText) throws Exception {
		int count = prPromotionDao.selectPromotionDuplCheck(insdMgmtInfoText);
		boolean duplCheckVal = true;
		if (count > 0)
			duplCheckVal = false;

		return duplCheckVal;
	}

	/**
	 * 프로모션 등록
	 * 
	 * @Desc : 프로모션 등록
	 * @Method Name : insertPromotion
	 * @Date : 2019. 3. 15
	 * @Author : easyhun
	 * @param prCoupon
	 * @throws Exception
	 */
	public void insertPrPromotion(PrPromotion prPromotion) throws Exception {
		// 타임특가, 다족구매 상품 중복체크
		this.getDuplPrdtNo(prPromotion);

		// 세션 정보 호출
		UserDetails user = LoginManager.getUserDetails();
		prPromotion.setRgsterNo(user.getAdminNo());
		prPromotion.setRgstDtm(new Timestamp(new Date().getTime()));
		prPromotion.setModerNo(user.getAdminNo());
		prPromotion.setModDtm(prPromotion.getRgstDtm());

		// 유효기간, 발급기간 Date Set
		prPromotion.setPromoStartDtm(prPromotion.getParamPromoStartDtm());
		prPromotion.setPromoEndDtm(prPromotion.getParamPromoEndDtm());

		if (UtilsText.equals(prPromotion.getPromoTypeCode(), CommonCode.PROMO_TYPE_CODE_GIFT_PAYMENT)) {
			prPromotion.setCpnSmtmApplyPsbltYn("Y"); // 사은품이면 쿠폰 사용 허용여부를 무조건 허용으로
		}

		prPromotionDao.insertPrPromotion(prPromotion);
		String promoNo = prPromotion.getPromoNo();

		if (UtilsText.isNotBlank(promoNo)) {
			// 쿠폰 채널
			if (prPromotion.getPrPromotionTargetChannelArr() != null) {
				for (PrPromotionTargetChannel prPromotionTargetChannel : prPromotion.getPrPromotionTargetChannelArr()) {
					prPromotionTargetChannel.setPromoNo(promoNo);
					prPromotionTargetChannelDao.insert(prPromotionTargetChannel);
				}
			}

			// 디바이스
			if (prPromotion.getDeviceCodes() != null) {
				for (String deviceCode : prPromotion.getDeviceCodes()) {
					PrPromotionTargetDevice prPromotionTargetDevice = new PrPromotionTargetDevice();
					prPromotionTargetDevice.setPromoNo(promoNo);
					prPromotionTargetDevice.setDeviceCode(deviceCode);
					prPromotionTargetDeviceDao.insert(prPromotionTargetDevice);
				}
			}

			// 적용 대상 , 제외 대상 상품
			if (prPromotion.getPrPromotionTargetProductArr() != null) {
				for (PrPromotionTargetProduct prPromotionTargetProduct : prPromotion.getPrPromotionTargetProductArr()) {
					prPromotionTargetProduct.setPromoNo(promoNo);
					prPromotionTargetProduct.setEventLimitQty(prPromotionTargetProduct.getMaxEventLimitQty());
					prPromotionTargetProductDao.insertPrPromotionTargetProduct(prPromotionTargetProduct);
				}
			}

			// 사은품
			if (prPromotion.getPrPromotionTargetGiftArr() != null) {
				for (PrPromotionTargetProduct prPromotionTargetProduct : prPromotion.getPrPromotionTargetGiftArr()) {
					prPromotionTargetProduct.setPromoNo(promoNo);
					prPromotionTargetProduct.setEventLimitQty(prPromotionTargetProduct.getMaxEventLimitQty());
					prPromotionTargetProductDao.insertPrPromotionTargetProduct(prPromotionTargetProduct);
				}
			}

			if (UtilsText.equals(prPromotion.getPromoTypeCode(), CommonCode.PROMO_TYPE_CODE_DISCOUNT_IMMEDIATELY)
					|| UtilsText.equals(prPromotion.getPromoTypeCode(),
							CommonCode.PROMO_TYPE_CODE_DISCOUNT_IMMEDIATELY_AFFILIATES)) {
				// 카테고리
				if (prPromotion.getPrPromotionTargetCategoryArr() != null
						&& UtilsText.equals(prPromotion.getPromoApplyType(), "C")) {
					for (PrPromotionTargetCategory prPromotionTargetCategory : prPromotion
							.getPrPromotionTargetCategoryArr()) {
						prPromotionTargetCategory.setPromoNo(promoNo);
						prPromotionTargetCategoryDao.insert(prPromotionTargetCategory);
					}
				}

				// 브랜드
				if (prPromotion.getPrPromotionTargetBrandArr() != null
						&& UtilsText.equals(prPromotion.getPromoApplyType(), "B")) {
					for (PrPromotionTargetBrand prPromotionTargetBrand : prPromotion.getPrPromotionTargetBrandArr()) {
						prPromotionTargetBrand.setPromoNo(promoNo);
						prPromotionTargetBrandDao.insert(prPromotionTargetBrand);
					}
				}
			}

			// 회원 유형
			short targetGradeIdx = 0;
			if (prPromotion.getPrPromotionTargetGradeArr() != null || UtilsText.isNotBlank(prPromotion.getEmpYn())) {
				SyCodeDetail syCodeDetail = new SyCodeDetail();
				syCodeDetail.setCodeField(CommonCode.JOIN_LIMIT_TYPE_CODE);
				for (PrPromotionTargetGrade prPromotionTargetGrade : prPromotion.getPrPromotionTargetGradeArr()) {
					prPromotionTargetGrade.setPromoNo(promoNo);

					// 회원유형 및 등급
					if (UtilsText.isNotBlank(prPromotion.getEmpYn()) && UtilsText.equals(prPromotion.getEmpYn(), "Y")) {
						prPromotionTargetGrade.setEmpYn(prPromotion.getEmpYn());
						prPromotionTargetGrade.setMemberTypeCode(CommonCode.MEMBER_TYPE_MEMBERSHIP);
						prPromotionTargetGrade.setMbshpGradeCode(CommonCode.MBSHP_GRADE_CODE_NORMAL);
						prPromotionTargetGrade.setPromoTrgtGradeSeq(++targetGradeIdx);
						prPromotionTargetGradeDao.insert(prPromotionTargetGrade);
					} else {
						prPromotionTargetGrade.setEmpYn("N");
						if (UtilsText.equals(prPromotionTargetGrade.getMemberTypeCode(),
								CommonCode.MEMBER_TYPE_MEMBERSHIP) && prPromotion.getMbshpGradeCodes() != null) {
							for (String mbshpGradeCode : prPromotion.getMbshpGradeCodes()) {
								prPromotionTargetGrade.setPromoTrgtGradeSeq(++targetGradeIdx);
								prPromotionTargetGrade.setMbshpGradeCode(mbshpGradeCode);
								prPromotionTargetGradeDao.insert(prPromotionTargetGrade);
							}
						} else {
							prPromotionTargetGrade.setMbshpGradeCode(CommonCode.MBSHP_GRADE_CODE_NORMAL);
							prPromotionTargetGrade.setPromoTrgtGradeSeq(++targetGradeIdx);
							prPromotionTargetGradeDao.insert(prPromotionTargetGrade);
						}
					}
				}
			}

			// 다족구매
			if (UtilsText.equals(prPromotion.getPromoTypeCode(), CommonCode.PROMO_TYPE_CODE_DISCOUNT_MULTI_SHOUES)) {
				int multiBuyDiscountIdx = 0;
				if (prPromotion.getPrPromotionMultiBuyDiscountArr() != null) {
					for (PrPromotionMultiBuyDiscount prPromotionMultiBuyDiscount : prPromotion
							.getPrPromotionMultiBuyDiscountArr()) {
						multiBuyDiscountIdx++;
						prPromotionMultiBuyDiscount.setPromoNo(promoNo);
						prPromotionMultiBuyDiscount.setMultiBuyDscntSeq(multiBuyDiscountIdx);
						prPromotionMultiBuyDiscount.setBuyQty(multiBuyDiscountIdx); // 다족구매는 2족부터 시작 하므로 // 2020.04.27 : 1족부터로 변경
						prPromotionMultiBuyDiscountDao.insert(prPromotionMultiBuyDiscount);
					}
				}
			}
		}
	}

	/**
	 * 프로모션 수정
	 * 
	 * @Desc : 프로모션 수정
	 * @Method Name : updatePromotion
	 * @Date : 2019. 3. 15
	 * @Author : easyhun
	 * @param prCoupon
	 * @throws Exception
	 */
	public void updatePrPromotion(PrPromotion prPromotion) throws Exception {
		// 타임특가, 다족구매 중복체크
		this.getDuplPrdtNo(prPromotion);

		// 세션 정보 호출
		UserDetails user = LoginManager.getUserDetails();
		prPromotion.setModerNo(user.getAdminNo());
		prPromotion.setModDtm(new Timestamp(new Date().getTime()));

		// 유효기간, 발급기간 Date Set
		prPromotion.setPromoStartDtm(prPromotion.getParamPromoStartDtm());
		prPromotion.setPromoEndDtm(prPromotion.getParamPromoEndDtm());

		if (UtilsText.equals(prPromotion.getPromoTypeCode(), CommonCode.PROMO_TYPE_CODE_GIFT_PAYMENT)) {
			prPromotion.setCpnSmtmApplyPsbltYn("Y"); // 사은품이면 쿠폰 사용 허용여부를 무조건 허용으로
		}

		prPromotionDao.update(prPromotion);
		String promoNo = prPromotion.getPromoNo();

		if (UtilsText.isNotBlank(promoNo)) {
			if (UtilsText.isNotBlank(prPromotion.getPromoProgressStatus())
					&& UtilsText.equals(prPromotion.getPromoProgressStatus(), "wait")) {
				// 쿠폰 채널
				prPromotionTargetChannelDao.deleteByPromoNo(promoNo);
				if (prPromotion.getPrPromotionTargetChannelArr() != null) {
					for (PrPromotionTargetChannel prPromotionTargetChannel : prPromotion
							.getPrPromotionTargetChannelArr()) {
						prPromotionTargetChannel.setPromoNo(promoNo);
						prPromotionTargetChannelDao.insert(prPromotionTargetChannel);
					}
				}

				// 디바이스
				prPromotionTargetDeviceDao.deleteByPromoNo(promoNo);
				if (prPromotion.getDeviceCodes() != null) {
					for (String deviceCode : prPromotion.getDeviceCodes()) {
						PrPromotionTargetDevice prPromotionTargetDevice = new PrPromotionTargetDevice();
						prPromotionTargetDevice.setPromoNo(promoNo);
						prPromotionTargetDevice.setDeviceCode(deviceCode);
						prPromotionTargetDeviceDao.insert(prPromotionTargetDevice);
					}
				}

				// 카테고리, 브랜드
				prPromotionTargetBrandDao.deleteByPromoNo(promoNo);
				prPromotionTargetCategoryDao.deleteByPromoNo(promoNo);
				if (UtilsText.equals(prPromotion.getPromoTypeCode(), CommonCode.PROMO_TYPE_CODE_DISCOUNT_IMMEDIATELY)
						|| UtilsText.equals(prPromotion.getPromoTypeCode(),
								CommonCode.PROMO_TYPE_CODE_DISCOUNT_IMMEDIATELY_AFFILIATES)) {
					// 카테고리
					if (prPromotion.getPrPromotionTargetCategoryArr() != null
							&& UtilsText.equals(prPromotion.getPromoApplyType(), "C")) {
						for (PrPromotionTargetCategory prPromotionTargetCategory : prPromotion
								.getPrPromotionTargetCategoryArr()) {
							prPromotionTargetCategory.setPromoNo(promoNo);
							prPromotionTargetCategoryDao.insert(prPromotionTargetCategory);
						}
					}

					// 브랜드
					if (prPromotion.getPrPromotionTargetBrandArr() != null
							&& UtilsText.equals(prPromotion.getPromoApplyType(), "B")) {
						for (PrPromotionTargetBrand prPromotionTargetBrand : prPromotion
								.getPrPromotionTargetBrandArr()) {
							prPromotionTargetBrand.setPromoNo(promoNo);
							prPromotionTargetBrandDao.insert(prPromotionTargetBrand);
						}
					}
				}

				// 회원 유형
				short targetGradeIdx = 0;
				prPromotionTargetGradeDao.deleteByPromoNo(promoNo);
				if (prPromotion.getPrPromotionTargetGradeArr() != null
						|| UtilsText.isNotBlank(prPromotion.getEmpYn())) {
					for (PrPromotionTargetGrade prPromotionTargetGrade : prPromotion.getPrPromotionTargetGradeArr()) {
						prPromotionTargetGrade.setPromoNo(promoNo);

						if (UtilsText.isNotBlank(prPromotion.getEmpYn())
								&& UtilsText.equals(prPromotion.getEmpYn(), "Y")) {
							prPromotionTargetGrade.setEmpYn(prPromotion.getEmpYn());
							prPromotionTargetGrade.setMemberTypeCode(CommonCode.MEMBER_TYPE_MEMBERSHIP);
							prPromotionTargetGrade.setMbshpGradeCode(CommonCode.MBSHP_GRADE_CODE_NORMAL);
							prPromotionTargetGrade.setPromoTrgtGradeSeq(++targetGradeIdx);
							prPromotionTargetGradeDao.insert(prPromotionTargetGrade);
						} else {
							prPromotionTargetGrade.setEmpYn("N");
							if (UtilsText.equals(prPromotionTargetGrade.getMemberTypeCode(),
									CommonCode.MEMBER_TYPE_MEMBERSHIP) && prPromotion.getMbshpGradeCodes() != null) {
								for (String mbshpGradeCode : prPromotion.getMbshpGradeCodes()) {
									prPromotionTargetGrade.setPromoTrgtGradeSeq(++targetGradeIdx);
									prPromotionTargetGrade.setMbshpGradeCode(mbshpGradeCode);
									prPromotionTargetGradeDao.insert(prPromotionTargetGrade);
								}
							} else {
								prPromotionTargetGrade.setMbshpGradeCode(CommonCode.MBSHP_GRADE_CODE_NORMAL);
								prPromotionTargetGrade.setPromoTrgtGradeSeq(++targetGradeIdx);
								prPromotionTargetGradeDao.insert(prPromotionTargetGrade);
							}
						}
					}
				}

				// 다족구매
				prPromotionMultiBuyDiscountDao.deleteByPromoNo(promoNo);
				if (UtilsText.equals(prPromotion.getPromoTypeCode(),
						CommonCode.PROMO_TYPE_CODE_DISCOUNT_MULTI_SHOUES)) {
					int multiBuyDiscountIdx = 0;
					if (prPromotion.getPrPromotionMultiBuyDiscountArr() != null) {
						for (PrPromotionMultiBuyDiscount prPromotionMultiBuyDiscount : prPromotion
								.getPrPromotionMultiBuyDiscountArr()) {
							multiBuyDiscountIdx++;
							prPromotionMultiBuyDiscount.setPromoNo(promoNo);
							prPromotionMultiBuyDiscount.setMultiBuyDscntSeq(multiBuyDiscountIdx);
							prPromotionMultiBuyDiscount.setBuyQty(multiBuyDiscountIdx); // 다족구매는 2족부터 시작 하므로 // 2020.04.27 : 1족부터로 변경
							prPromotionMultiBuyDiscountDao.insert(prPromotionMultiBuyDiscount);
						}
					}
				}

			}

			// 적용 대상 , 제외 대상 상품
			// prPromotionTargetProductDao.deleteByPromoNo(promoNo); (delete -> insert 에서
			// insert, update 로 바꿈)
			List<String> targetProductNoList = prPromotionTargetProductDao.selectPromotionPrdtNoByPromoNo(promoNo);
			if (prPromotion.getPrPromotionTargetProductArr() != null) {
				for (PrPromotionTargetProduct prPromotionTargetProduct : prPromotion.getPrPromotionTargetProductArr()) {
					if (prPromotionTargetProduct.getMaxEventLimitQty() == null)
						prPromotionTargetProduct.setMaxEventLimitQty(0);

					int productNoCheck = targetProductNoList.indexOf(prPromotionTargetProduct.getPrdtNo());
					prPromotionTargetProduct.setPromoNo(promoNo);
					if (productNoCheck != -1) {
						prPromotionTargetProductDao.updatePrPromotionTargetProduct(prPromotionTargetProduct);
						targetProductNoList.remove(prPromotionTargetProduct.getPrdtNo());
					} else {
						prPromotionTargetProduct.setPromoNo(promoNo);
						prPromotionTargetProduct.setEventLimitQty(prPromotionTargetProduct.getMaxEventLimitQty());
						prPromotionTargetProductDao.insertPrPromotionTargetProduct(prPromotionTargetProduct);
					}
				}
			}

			// 사은품
			if (prPromotion.getPrPromotionTargetGiftArr() != null) {
				for (PrPromotionTargetProduct prPromotionTargetProduct : prPromotion.getPrPromotionTargetGiftArr()) {
					int productNoCheck = targetProductNoList.indexOf(prPromotionTargetProduct.getPrdtNo());
					prPromotionTargetProduct.setPromoNo(promoNo);
					if (productNoCheck != -1) {
						prPromotionTargetProductDao.updatePrPromotionTargetProduct(prPromotionTargetProduct);
						targetProductNoList.remove(prPromotionTargetProduct.getPrdtNo());
					} else {
						prPromotionTargetProduct.setPromoNo(promoNo);
						prPromotionTargetProduct.setEventLimitQty(prPromotionTargetProduct.getMaxEventLimitQty());
						prPromotionTargetProductDao.insertPrPromotionTargetProduct(prPromotionTargetProduct);
					}
				}
			}

			if (targetProductNoList.size() > 0) {
				for (String prdtNo : targetProductNoList) {
					prPromotionTargetProductDao.deleteTargetProduct(promoNo, prdtNo);
				}
			}

		}
	}

	/**
	 * 프로모션 현황 총 주문, 금액 조회
	 * 
	 * @Desc : 프로모션 현황 총 주문, 금액 조회
	 * @Method Name : getPromotionTotalStatus
	 * @Date : 2019. 3. 19
	 * @Author : easyhun
	 * @param promoNo
	 * @return PrPromotion
	 */
	public PrPromotion getPromotionTotalStatus(String promoNo) throws Exception {

		return prPromotionDao.selectPromotionTotalStatus(promoNo);
	}

	/**
	 * 프로모션 현황 조회
	 * 
	 * @Desc : 프로모션 현황 조회
	 * @Method Name : getPromotionStatusList
	 * @Date : 2019. 3. 20
	 * @Author : easyhun
	 * @param pageable
	 * @return
	 */
	public Page<PrPromotion> getPromotionStatusList(Pageable<PrPromotionSearchVO, PrPromotion> pageable)
			throws Exception {

		int count = prPromotionDao.selectPromotionStatusCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(prPromotionDao.selectPromotionStatusList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * 
	 * @Desc : 프로모션 현황 엑셀 다운로드
	 * @Method Name : getPromotionStatusList
	 * @Date : 2019. 12. 18.
	 * @Author : NKB
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<PrPromotion> getPromotionStatusListExcel(Pageable<PrPromotionSearchVO, PrPromotion> pageable)
			throws Exception {

		int count = prPromotionDao.selectPromotionStatusCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(prPromotionDao.selectPromotionStatusListExcel(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 대상상품 프로모션 목록 조회
	 * @Method Name : getPromotionProductList
	 * @Date : 2019. 4. 25.
	 * @Author : hsjhsj19
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<PrPromotion> getPromotionProductList(Pageable<PrPromotionSearchVO, PrPromotion> pageable)
			throws Exception {

		int count = prPromotionDao.selectPromotionProductCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(prPromotionDao.selectPromotionProductList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 대상상품 사은품 목록
	 * @Method Name : getPromotionTargetFreeGift
	 * @Date : 2019. 4. 25.
	 * @Author : hsjhsj19
	 * @param freeGift
	 * @return
	 * @throws Exception
	 */
	public List<PdFreeGift> getPromotionTargetFreeGift(PdFreeGift freeGift) throws Exception {
		return prPromotionDao.selectTargetProductFreeGift(freeGift);
	}

	/**
	 * 상품 중복 프로모션 조회
	 * 
	 * @Desc : 상품 중복 프로모션 조회
	 * @Method Name : getPromotionDuplProduct
	 * @Date : 2019. 5. 29
	 * @Author : easyhun
	 * @param prdtNo
	 * @return
	 * @throws Exception
	 */

	public String getPromotionDuplProduct(String prdtNo, String promoNo) throws Exception {
		Map<String, String> map = new HashMap<>();
		map.put("prdtNo", prdtNo);
		map.put("promoNo", promoNo);
		String duplPromoNo = prPromotionTargetProductDao.selectPromotionDuplProduct(map);

		return duplPromoNo;
	}

	/**
	 * 상품번호에 의한 프로모션 조회
	 * 
	 * @Desc : 상품번호에 의한 프로모션 조회
	 * @Method Name : getPromoNoByPrdtNoList
	 * @Date : 2019. 9. 10
	 * @Author : easyhun
	 * @param prdtNo
	 * @return
	 * @throws Exception
	 */

	public List<PrPromotion> getPrdtNoByPromoList(String prdtNo) throws Exception {
		List<PrPromotion> promoNoByPrdtNoList = prPromotionDao.selectPrdtNoByPromoList(prdtNo);

		return promoNoByPrdtNoList;
	}

	/**
	 * @Desc : 프로모션 상품 단건 조회
	 * @Method Name : getPromotionProduct
	 * @Date : 2019. 10. 4.
	 * @Author : hsjhsj19
	 * @param prPromotion
	 * @return
	 * @throws Exception
	 */
	public PrPromotion getPromotionProduct(PrPromotion prPromotion) throws Exception {
		return prPromotionDao.selectPromotionProduct(prPromotion);
	}

	/**
	 * @Desc : 프로모션 다족, 타임특가 중복 체크
	 * @Method Name : getDuplPrdtNo
	 * @Date : 2019. 10. 22.
	 * @Author : easyhun
	 * @param prPromotion
	 * @throws Exception
	 */
	public void getDuplPrdtNo(PrPromotion prPromotion) throws Exception {
		if (prPromotion.getPrPromotionTargetProductArr() != null
				&& prPromotion.getPrPromotionTargetProductArr().length > 0) {

			// array -> arrayList
			List<PrPromotionTargetProduct> targetProductArr = new ArrayList<>(
					Arrays.asList(prPromotion.getPrPromotionTargetProductArr()));

			// 중복 체크 대상 상품 번호 추출
			List<String> targetPrdtNos = targetProductArr.stream().map(PrPromotionTargetProduct::getPrdtNo)
					.collect(Collectors.<String>toList());

			// 중복 체크 조건 data set
			PrPromotionTargetProduct prPromotionTargetProduct = new PrPromotionTargetProduct();
			prPromotionTargetProduct.setPromoNo(prPromotion.getPromoNo());
			prPromotionTargetProduct.setPromoTypeCode(prPromotion.getPromoTypeCode());
			prPromotionTargetProduct.setTargetPrdtNos(targetPrdtNos);

			// 중복 체크 조회
			List<PrPromotionTargetProduct> duplTargetProduct = prPromotionTargetProductDao
					.selectDuplPrdtNo(prPromotionTargetProduct);

			if (duplTargetProduct != null) {
				// 중복된 상품 번호 추출
				List<String> duplPrdtNos = duplTargetProduct.stream().filter(item -> item.getPromoDuplCount() > 0)
						.map(PrPromotionTargetProduct::getPrdtNo).collect(Collectors.<String>toList());

				// 중복 상품 번호가 있을경우 exception
				if (duplPrdtNos != null && duplPrdtNos.size() > 0) {
					if (UtilsText.equals(prPromotion.getPromoTypeCode(),
							CommonCode.PROMO_TYPE_CODE_SPECIAL_PRICE_TIME)) {
						throw new Exception(duplPrdtNos.toString() + "상품이 타 프로모션(타임특가 또는 다족구매)과 중복되었습니다.");
					} else {
						throw new Exception(duplPrdtNos.toString() + "상품이 타 프로모션과 중복되었습니다.");
					}
				}
			}
		}
	}

}
