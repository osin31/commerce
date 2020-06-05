package kr.co.abcmart.bo.promotion.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.event.service.EventService;
import kr.co.abcmart.bo.member.model.master.MbMember;
import kr.co.abcmart.bo.member.model.master.MbMemberCoupon;
import kr.co.abcmart.bo.member.repository.master.MbMemberCouponDao;
import kr.co.abcmart.bo.member.repository.master.MbMemberDao;
import kr.co.abcmart.bo.promotion.model.master.PrCoupon;
import kr.co.abcmart.bo.promotion.model.master.PrCouponApplyBrand;
import kr.co.abcmart.bo.promotion.model.master.PrCouponApplyCategory;
import kr.co.abcmart.bo.promotion.model.master.PrCouponApplyChannel;
import kr.co.abcmart.bo.promotion.model.master.PrCouponApplyDevice;
import kr.co.abcmart.bo.promotion.model.master.PrCouponApplyProduct;
import kr.co.abcmart.bo.promotion.model.master.PrCouponApplyStore;
import kr.co.abcmart.bo.promotion.model.master.PrCouponPaperNumber;
import kr.co.abcmart.bo.promotion.model.master.PrCouponVendorShareRate;
import kr.co.abcmart.bo.promotion.repository.master.PrCouponApplyBrandDao;
import kr.co.abcmart.bo.promotion.repository.master.PrCouponApplyCategoryDao;
import kr.co.abcmart.bo.promotion.repository.master.PrCouponApplyChannelDao;
import kr.co.abcmart.bo.promotion.repository.master.PrCouponApplyDeviceDao;
import kr.co.abcmart.bo.promotion.repository.master.PrCouponApplyProductDao;
import kr.co.abcmart.bo.promotion.repository.master.PrCouponApplyStoreDao;
import kr.co.abcmart.bo.promotion.repository.master.PrCouponDao;
import kr.co.abcmart.bo.promotion.repository.master.PrCouponPaperNumberDao;
import kr.co.abcmart.bo.promotion.repository.master.PrCouponVendorShareRateDao;
import kr.co.abcmart.bo.promotion.vo.PrCouponSearchVO;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc :
 * @FileName : CouponService.java
 * @Project : abc.bo
 * @Date : 2020. 2. 14.
 * @Author : kiowa
 */
@Slf4j
@Service
public class CouponService {
	@Autowired
	private PrCouponDao prCouponDao;

	@Autowired
	private PrCouponApplyDeviceDao prCouponApplyDeviceDao;

	@Autowired
	private PrCouponApplyChannelDao prCouponApplyChannelDao;

	@Autowired
	private PrCouponApplyCategoryDao prCouponApplyCategoryDao;

	@Autowired
	private PrCouponVendorShareRateDao prCouponVendorShareRateDao;

	@Autowired
	private MbMemberCouponDao mbMemberCouponDao;

	@Autowired
	private MbMemberDao mbMemberDao;

	@Autowired
	private PrCouponApplyStoreDao prCouponApplyStoreDao;

	@Autowired
	private PrCouponApplyProductDao prCouponApplyProductDao;

	@Autowired
	private PrCouponApplyBrandDao prCouponApplyBrandDao;

	@Autowired
	private PrCouponPaperNumberDao prCouponPaperNumberDao;

	@Autowired
	private EventService eventService;

	@Autowired
	private SqlSessionFactory sqlSessionFactoryForBatch;

	/**
	 * 쿠폰 목록 조회
	 *
	 * @Desc : 쿠폰 목록 조회
	 * @Method Name : getCouponList
	 * @Date : 2019. 2. 25
	 * @Author : easyhun
	 * @param pageable
	 * @return
	 */
	public Page<PrCoupon> getCouponList(Pageable<PrCouponSearchVO, PrCoupon> pageable) throws Exception {

		int count = prCouponDao.selectPrCouponCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(prCouponDao.selectPrCouponList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * 쿠폰 목록 조회
	 *
	 * @Desc : 쿠폰 목록 조회(회원)
	 * @Method Name : getMemberCouponList
	 * @Date : 2019. 2. 26
	 * @Author : easyhun
	 * @param pageable
	 * @return
	 */
	public Page<PrCoupon> getMemberCouponList(Pageable<PrCouponSearchVO, PrCoupon> pageable) throws Exception {

		int count = prCouponDao.selectMemberCouponCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(prCouponDao.selectMemberCouponList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * 쿠폰 목록 조회
	 *
	 * @Desc : 쿠폰 목록 조회(회원)
	 * @Method Name : getMemberCouponList
	 * @Date : 2019. 2. 26
	 * @Author : easyhun
	 * @param pageable
	 * @return
	 */
	public Page<PrCoupon> getMemberCouponPopList(Pageable<PrCouponSearchVO, PrCoupon> pageable) throws Exception {

		int count = prCouponDao.selectMemberCouponPopCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(prCouponDao.selectMemberCouponPopList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * 쿠폰 상세 조회
	 *
	 * @Desc : 쿠폰 상세 조회
	 * @Method Name : getCoupon
	 * @Date : 2019. 2. 27
	 * @Author : easyhun
	 * @param PrCoupon
	 * @return
	 */
	public PrCoupon getCoupon(PrCoupon prCoupon) throws Exception {

		return prCouponDao.selectPrCoupon(prCoupon);
	}

	/**
	 * 쿠폰 등록
	 *
	 * @Desc : 쿠폰 등록
	 * @Method Name : insertCoupon
	 * @Date : 2019. 2. 28
	 * @Author : easyhun
	 * @param prCoupon
	 * @throws Exception
	 */
	public void insertCoupon(PrCoupon prCoupon) throws Exception {

		// 세션 정보 호출
		UserDetails user = LoginManager.getUserDetails();
		prCoupon.setRgsterNo(user.getAdminNo());
		prCoupon.setRgstDtm(new Timestamp(new Date().getTime()));
		prCoupon.setModerNo(user.getAdminNo());
		prCoupon.setModDtm(prCoupon.getRgstDtm());

		// 유효기간, 발급기간 Date Set
		prCoupon.setIssueStartDtm(prCoupon.getParamIssueStartDtm());
		prCoupon.setIssueEndDtm(prCoupon.getParamIssueEndDtm());
		prCoupon.setValidStartDtm(prCoupon.getParamValidStartDtm());
		prCoupon.setValidEndDtm(prCoupon.getParamValidEndDtm());
		prCoupon.setDwldStartTime(prCoupon.getParamDayStartTime());
		prCoupon.setDwldEndTime(prCoupon.getParamDayEndTime());

		if (UtilsText.equals(prCoupon.getCpnTypeCode(), CommonCode.CPN_TYPE_CODE_FREE_DELIVERY)) {
			prCoupon.setDscntType("R");
			prCoupon.setDscntValue(100); // 무료배송쿠폰일경우 정률(R) , 100 으로 세팅
		}

		prCouponDao.insertPrCoupon(prCoupon);
		String cpnNo = prCoupon.getCpnNo();

		if (UtilsText.isNotBlank(cpnNo)) {
			// 쿠폰 채널
			if (prCoupon.getPrCouponApplyChannelArr() != null) {
				for (PrCouponApplyChannel prCouponApplyChannel : prCoupon.getPrCouponApplyChannelArr()) {
					prCouponApplyChannel.setCpnNo(cpnNo);
					prCouponApplyChannelDao.insert(prCouponApplyChannel);
				}
			}

			// 디바이스
			if (prCoupon.getDeviceCodes() != null) {
				for (String deviceCode : prCoupon.getDeviceCodes()) {
					PrCouponApplyDevice prCouponApplyDevice = new PrCouponApplyDevice();
					prCouponApplyDevice.setCpnNo(cpnNo);
					prCouponApplyDevice.setDeviceCode(deviceCode);
					prCouponApplyDeviceDao.insert(prCouponApplyDevice);
				}
			}

			// 입점사
			if (prCoupon.getPrCouponVendorShareRateArr() != null) {
				for (PrCouponVendorShareRate prCouponVendorShareRate : prCoupon.getPrCouponVendorShareRateArr()) {
					prCouponVendorShareRate.setCpnNo(cpnNo);
					prCouponVendorShareRateDao.insert(prCouponVendorShareRate);
				}
			}

			// 카테고리
			if (prCoupon.getPrCouponApplyCategoryArr() != null) {
				for (PrCouponApplyCategory prCouponApplyCategory : prCoupon.getPrCouponApplyCategoryArr()) {
					prCouponApplyCategory.setCpnNo(cpnNo);
					prCouponApplyCategoryDao.insert(prCouponApplyCategory);
				}
			}

			// 매장
			if (prCoupon.getPrCouponApplyStoreArr() != null) {
				for (PrCouponApplyStore prCouponApplyStore : prCoupon.getPrCouponApplyStoreArr()) {
					prCouponApplyStore.setCpnNo(cpnNo);
					prCouponApplyStoreDao.insert(prCouponApplyStore);
				}
			}

			// 적용상품
			if (prCoupon.getPrCouponApplyProductArr() != null) {
				for (PrCouponApplyProduct prCouponApplyProduct : prCoupon.getPrCouponApplyProductArr()) {

					if (UtilsText.isEmpty(prCouponApplyProduct.getPrdtType())) {
						// 쿠폰적용유형 이 상품일 경우에만 "T"로 등록한다.
						String prdtType = UtilsText.equals("P", prCoupon.getCpnApplyType()) ? "T" : "L";
						prCouponApplyProduct.setPrdtType(prdtType);
					}

					prCouponApplyProduct.setCpnNo(cpnNo);
					prCouponApplyProductDao.insert(prCouponApplyProduct);
				}

			}

			// 브랜드
			if (prCoupon.getPrCouponApplyBrandArr() != null) {
				for (PrCouponApplyBrand prCouponApplyBrand : prCoupon.getPrCouponApplyBrandArr()) {
					prCouponApplyBrand.setCpnNo(cpnNo);
					prCouponApplyBrandDao.insert(prCouponApplyBrand);
				}
			}

			// 지류 생성
			if (prCoupon.getPaperCrtCount() != null && prCoupon.getPaperCrtCount() != 0
					&& UtilsText.equals(prCoupon.getCpnCrtType(), "P")) {
				Long paperNumberCount = 0L;

				SqlSession sqlSessionForBatch = this.sqlSessionFactoryForBatch.openSession(ExecutorType.BATCH);
				PrCouponPaperNumberDao mapperForBatch = sqlSessionForBatch.getMapper(PrCouponPaperNumberDao.class);

				try {

					List<PrCouponPaperNumber> rows = new ArrayList<PrCouponPaperNumber>();

					for (int i = 0; i < prCoupon.getPaperCrtCount(); i++) {
						paperNumberCount = mapperForBatch.selectPrCouponPaperNumberSeq(Integer.valueOf(rows.size()));
						PrCouponPaperNumber prCouponPaperNumber = new PrCouponPaperNumber();
						String makeRandomNumber = eventService.makeRandomNumber(paperNumberCount, cpnNo, "coupon");
						prCouponPaperNumber.setCpnNo(cpnNo);
						prCouponPaperNumber.setPaperNoText(makeRandomNumber);
						rows.add(prCouponPaperNumber);

						if ((i + 1) == prCoupon.getPaperCrtCount() || (i + 1) % 1000 == 0) {
							mapperForBatch.insertRows(rows);
							rows.clear();
						}
					}

					sqlSessionForBatch.commit();

				} catch (Exception e) {
					sqlSessionForBatch.rollback(true);
					throw new Exception(e.getMessage());
				} finally {
					sqlSessionForBatch.close();
				}
			}
		}
	}

	/**
	 * 쿠폰 수정
	 *
	 * @Desc : 쿠폰 수정
	 * @Method Name : updateCoupon
	 * @Date : 2019. 3. 04
	 * @Author : easyhun
	 * @param prCoupon
	 * @throws Exception
	 */
	public void updateCoupon(PrCoupon prCoupon) throws Exception {
		if (UtilsText.equals(prCoupon.getMemberCouponCount(), "0")) {
			int memberCount = prCouponDao.selectTotalMemberCouponCount(prCoupon.getCpnNo());
			if (memberCount > 0) {
				throw new Exception("이미 쿠폰을 다운받은 회원이 존재하여 기본정보만 수정할 수 있습니다.");
			}
		}

		// 세션 정보 호출
		UserDetails user = LoginManager.getUserDetails();
		prCoupon.setModerNo(user.getAdminNo());
		prCoupon.setModDtm(new Timestamp(new Date().getTime()));

		// 유효기간, 발급기간 Date Set
		if (Integer.parseInt(prCoupon.getMemberCouponCount()) == 0) {
			prCoupon.setIssueStartDtm(prCoupon.getParamIssueStartDtm());
			prCoupon.setIssueEndDtm(prCoupon.getParamIssueEndDtm());
			prCoupon.setValidStartDtm(prCoupon.getParamValidStartDtm());
			prCoupon.setValidEndDtm(prCoupon.getParamValidEndDtm());
			prCoupon.setDwldStartTime(prCoupon.getParamDayStartTime());
			prCoupon.setDwldEndTime(prCoupon.getParamDayEndTime());
		}

		prCouponDao.updatePrCoupon(prCoupon);
		String cpnNo = prCoupon.getCpnNo();

		if (UtilsText.isNotBlank(cpnNo)) {
			if (Integer.parseInt(prCoupon.getMemberCouponCount()) == 0) {
				// 쿠폰 채널
				prCouponApplyChannelDao.deleteByCpnNo(cpnNo);
				if (prCoupon.getPrCouponApplyChannelArr() != null) {
					for (PrCouponApplyChannel prCouponApplyChannel : prCoupon.getPrCouponApplyChannelArr()) {
						prCouponApplyChannel.setCpnNo(cpnNo);
						prCouponApplyChannelDao.insert(prCouponApplyChannel);
					}
				}

				// 디바이스
				prCouponApplyDeviceDao.deleteByCpnNo(cpnNo);
				if (prCoupon.getDeviceCodes() != null) {
					for (String deviceCode : prCoupon.getDeviceCodes()) {
						PrCouponApplyDevice prCouponApplyDevice = new PrCouponApplyDevice();
						prCouponApplyDevice.setCpnNo(cpnNo);
						prCouponApplyDevice.setDeviceCode(deviceCode);
						prCouponApplyDeviceDao.insert(prCouponApplyDevice);
					}
				}

				// 카테고리
				prCouponApplyCategoryDao.deleteByCpnNo(cpnNo);
				if (prCoupon.getPrCouponApplyCategoryArr() != null) {
					for (PrCouponApplyCategory prCouponApplyCategory : prCoupon.getPrCouponApplyCategoryArr()) {
						prCouponApplyCategory.setCpnNo(cpnNo);
						prCouponApplyCategoryDao.insert(prCouponApplyCategory);
					}
				}

				// 매장
				prCouponApplyStoreDao.deleteByCpnNo(cpnNo);
				if (prCoupon.getPrCouponApplyStoreArr() != null) {
					for (PrCouponApplyStore prCouponApplyStore : prCoupon.getPrCouponApplyStoreArr()) {
						prCouponApplyStore.setCpnNo(cpnNo);
						prCouponApplyStoreDao.insert(prCouponApplyStore);
					}
				}

				// 브랜드
				prCouponApplyBrandDao.deleteByCpnNo(cpnNo);
				if (prCoupon.getPrCouponApplyBrandArr() != null) {
					for (PrCouponApplyBrand prCouponApplyBrand : prCoupon.getPrCouponApplyBrandArr()) {
						prCouponApplyBrand.setCpnNo(cpnNo);
						prCouponApplyBrandDao.insert(prCouponApplyBrand);
					}
				}

				// 발급한 지류 count
				if (prCoupon.getPaperCrtCount() != null && prCoupon.getPaperCrtCount() != 0
						&& UtilsText.equals(prCoupon.getCpnCrtType(), "P")) {
					Long paperNumberCount = prCouponPaperNumberDao.selectPrCouponPaperNumberCount(cpnNo);
					if (paperNumberCount == 0) {

						SqlSession sqlSessionForBatch = this.sqlSessionFactoryForBatch.openSession(ExecutorType.BATCH);
						PrCouponPaperNumberDao mapperForBatch = sqlSessionForBatch
								.getMapper(PrCouponPaperNumberDao.class);

						try {

							List<PrCouponPaperNumber> rows = new ArrayList<PrCouponPaperNumber>();

							for (int i = 0; i < prCoupon.getPaperCrtCount(); i++) {
								paperNumberCount = mapperForBatch
										.selectPrCouponPaperNumberSeq(Integer.valueOf(rows.size()));
								PrCouponPaperNumber prCouponPaperNumber = new PrCouponPaperNumber();
								String makeRandomNumber = eventService.makeRandomNumber(paperNumberCount, cpnNo,
										"coupon");
								prCouponPaperNumber.setCpnNo(cpnNo);
								prCouponPaperNumber.setPaperNoText(makeRandomNumber);
								rows.add(prCouponPaperNumber);

								if ((i + 1) == prCoupon.getPaperCrtCount() || (i + 1) % 1000 == 0) {
									mapperForBatch.insertRows(rows);
									rows.clear();
								}
							}

							sqlSessionForBatch.commit();

						} catch (Exception e) {
							sqlSessionForBatch.rollback(true);
							throw new Exception(e.getMessage());
						} finally {
							sqlSessionForBatch.close();
						}

					}
				}
			}

			// 입점사
			prCouponVendorShareRateDao.deleteByCpnNo(cpnNo);
			if (prCoupon.getPrCouponVendorShareRateArr() != null) {
				for (PrCouponVendorShareRate prCouponVendorShareRate : prCoupon.getPrCouponVendorShareRateArr()) {
					prCouponVendorShareRate.setCpnNo(cpnNo);
					prCouponVendorShareRateDao.insert(prCouponVendorShareRate);
				}
			}

			// 적용상품
//			prCouponApplyProductDao.deleteByCpnNo(cpnNo); 2020.02.14 주석 처리

//			deleteCouponProductList(cpnNo, prCoupon.getPrCouponApplyProductArr());

			if (prCoupon.getPrCouponApplyProductArr() != null) {

				deleteCouponProductList(cpnNo, prCoupon.getPrCouponApplyProductArr());

				for (PrCouponApplyProduct prCouponApplyProduct : prCoupon.getPrCouponApplyProductArr()) {
					if (UtilsText.isEmpty(prCouponApplyProduct.getPrdtType())) {
						// 쿠폰적용유형 이 상품일 경우에만 "T"로 등록한다.
						String prdtType = UtilsText.equals("P", prCoupon.getCpnApplyType()) ? "T" : "L";
						prCouponApplyProduct.setPrdtType(prdtType);
					}
					prCouponApplyProduct.setCpnNo(cpnNo);
					prCouponApplyProductDao.insert(prCouponApplyProduct);
				}
			}

		}
	}

	/**
	 * @Desc : 쿠폰 상품 정보를 삭제 한다.
	 * @Method Name : deleteCouponProductList
	 * @Date : 2020. 2. 14.
	 * @Author : kiowa
	 * @param cpnNo
	 * @param couponProductList
	 * @return
	 * @throws Exception
	 */
	private int deleteCouponProductList(String cpnNo, PrCouponApplyProduct[] couponProductList) throws Exception {
		return prCouponApplyProductDao.deleteCouponProduct(cpnNo, couponProductList);
	}

	/**
	 * @Desc : 쿠폰 정보 삭제 후 대상 입점사 정보 삭제 처리 한다.
	 * @Method Name : deleteCouponProductList
	 * @Date : 2020. 2. 17.
	 * @Author : kiowa
	 * @param cpnNo
	 * @param prdtNoList
	 * @return
	 * @throws Exception
	 */
	public List<String> deleteCouponProductInfo(String cpnNo, String[] prdtNoList) throws Exception {

		// 2020.02.17 선택 쿠폰 상품 삭제 처리
		prCouponApplyProductDao.deleteCouponProductList(cpnNo, prdtNoList);

		// 2020.02.17 쿠폰 상품 정보가 없는 업체 정보를 삭제
		prCouponVendorShareRateDao.deleteNotCouponProductVendor(cpnNo);

		// 2020.02.17 쿠폰 상품 정보가 있는 업체 조회
		return prCouponVendorShareRateDao.selectCouponProductVendorList(cpnNo);
	}

	/**
	 * 쿠폰 등록(회원 쿠폰 등록)
	 *
	 * @Desc : 쿠폰 등록(회원 쿠폰 등록)
	 * @Method Name : insertMemberCoupon
	 * @Date : 2019. 3. 6
	 * @Author : easyhun
	 * @param prCoupon
	 * @throws Exception
	 */
	public Map<String, Object> insertMemberCoupon(PrCoupon prCoupon) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 세션 정보 호출
		UserDetails user = LoginManager.getUserDetails();

		String cpnNo = prCoupon.getCpnNo();
		PrCoupon detailPrCoupon = prCouponDao.selectPrCoupon(prCoupon);

		if (detailPrCoupon != null && prCoupon.getMemberNos() != null) {
			for (String memberNo : prCoupon.getMemberNos()) {
				MbMemberCoupon mbMemberCoupon = new MbMemberCoupon();
				String holdCpnSeq = mbMemberCouponDao.selectMemberCouponHoldSeq(memberNo);
				mbMemberCoupon.setCpnNo(cpnNo);
				mbMemberCoupon.setMemberNo(memberNo);
				mbMemberCoupon.setCpnIssueDtm(new Timestamp(new Date().getTime()));

				// T : 지정기간 , D : 지정일
				if (UtilsText.equals(detailPrCoupon.getValidTermGbnType(), "T")) {
					mbMemberCoupon.setValidStartDtm(detailPrCoupon.getValidStartDtm());
					mbMemberCoupon.setValidEndDtm(detailPrCoupon.getValidEndDtm());
				} else {
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DATE, +detailPrCoupon.getUseLimitDayCount());
					//mbMemberCoupon.setValidStartDtm(new Timestamp(new Date().getTime()));
					mbMemberCoupon.setValidEndDtm(new Timestamp(cal.getTime().getTime()));
				}

				if (UtilsText.isBlank(prCoupon.getCpnStatCode())) {
					mbMemberCoupon.setCpnStatCode(CommonCode.CPN_STAT_CODE_ISSUANCE);
				} else {
					mbMemberCoupon.setCpnStatCode(prCoupon.getCpnStatCode());
				}
				mbMemberCoupon.setHoldCpnSeq(Integer.parseInt(holdCpnSeq));
				mbMemberCoupon.setRgsterNo(user.getAdminNo());
				mbMemberCoupon.setRgstDtm(new Timestamp(new Date().getTime()));
				mbMemberCoupon.setModerNo(user.getAdminNo());
				mbMemberCoupon.setModDtm(prCoupon.getRgstDtm());

				// 지류번호 set
				Long paperNumberCount = prCouponPaperNumberDao.selectPrCouponPaperNumberSeq(0);
				String makeRandomNumber = eventService.makeRandomNumber(paperNumberCount, cpnNo, "coupon");
				mbMemberCoupon.setPaperNoText(makeRandomNumber);

				mbMemberCouponDao.insertMemberCoupon(mbMemberCoupon);
			}

			// 발급한 count 쿠폰테이블 update
			if (prCoupon.getMemberNos().length > 0) {
				detailPrCoupon.setTotalIssueCount(detailPrCoupon.getTotalIssueCount() + prCoupon.getMemberNos().length);
				prCouponDao.updateTotalIssueCount(detailPrCoupon);
			}

			resultMap.put("result", Const.BOOLEAN_TRUE);
		} else {
			resultMap.put("result", Const.BOOLEAN_FALSE);
		}

		return resultMap;
	}

	/**
	 * 회원 체크(회원 쿠폰 등록 체크)
	 *
	 * @Desc : 회원 체크(회원 쿠폰 등록 체크)
	 * @Method Name : checkMemberCoupon
	 * @Date : 2019. 3. 6
	 * @Author : easyhun
	 * @param prCoupon
	 * @throws Exception
	 */
	public boolean checkMemberCoupon(PrCoupon prCoupon) throws Exception {
		boolean memberCheck = true;
		if (prCoupon.getMemberNos() != null && prCoupon.getMemberNos().length > 0) {
			for (String memberNo : prCoupon.getMemberNos()) {
				if (mbMemberDao.selectCountByMemberNo(memberNo) == 0) {
					memberCheck = false;
					break;
				}
			}
		}
		return memberCheck;
	}

	/**
	 * 회원 정보 셋
	 *
	 * @Desc : 회원 정보 셋
	 * @Method Name : memberInfoSet
	 * @Date : 2019. 10. 8
	 * @Author : easyhun
	 * @param String
	 * @throws Exception
	 */
	public List<MbMember> memberInfoSet(List<String> memberNos) throws Exception {
		List<MbMember> list = new ArrayList<MbMember>();

		if (memberNos.size() > 0) {
			for (String memberNo : memberNos) {
				if (memberNo != "0") {
					MbMember mbMember = mbMemberDao.selectMemberDefalutInfo(memberNo);
					if (mbMember == null) {
						mbMember = new MbMember();
						mbMember.setMemberNo(memberNo);
					}
					list.add(mbMember);
				}
			}
		}

		return list;
	}

	/**
	 * 쿠폰 상태값 수정(회원)
	 *
	 * @Desc : 쿠폰 등록(회원 쿠폰 등록)
	 * @Method Name : updateStatusCoupon
	 * @Date : 2019. 3. 6
	 * @Author : easyhun
	 * @param prCoupon
	 * @throws Exception
	 */
	public void updateStatusCoupon(PrCoupon prCoupon) throws Exception {
		// 세션 정보 호출
		UserDetails user = LoginManager.getUserDetails();
		prCoupon.setModerNo(user.getAdminNo());
		prCoupon.setModDtm(new Timestamp(new Date().getTime()));

		if (UtilsText.equals(prCoupon.getUseYnVal(), "RE_ISSUE")) {
			String[] memberNo = { prCoupon.getMemberNo() };
			prCoupon.setMemberNos(memberNo);
			this.insertMemberCoupon(prCoupon);
		} else {
			MbMemberCoupon mbMemberCoupon = new MbMemberCoupon();
			mbMemberCoupon.setCpnNo(prCoupon.getCpnNo());
			mbMemberCoupon.setMemberNo(prCoupon.getMemberNo());
			mbMemberCoupon.setHoldCpnSeq(prCoupon.getHoldCpnSeq());
			mbMemberCoupon.setCpnStatCode(prCoupon.getCpnStatCode());
			mbMemberCouponDao.update(mbMemberCoupon);
		}
	}

	public boolean getCouponCondtion(PrCoupon prCoupon) throws Exception {
		boolean condition = true;

		PrCoupon couponInfo = this.getCoupon(prCoupon);

		// 쿠폰발행수
		int totalIssueCnt = prCouponDao.selectTotalMemberCouponCount(prCoupon.getCpnNo());
		// 멤버 쿠폰 보유수
		int memberCouponCnt = Integer.parseInt(mbMemberCouponDao.selectMemberCouponHoldSeq(prCoupon.getMemberNo())) - 1;

		if (UtilsText.equals(couponInfo.getTotalIssueLimitYn(), "N")) {
			// 쿠폰 발행 무제한이지만 1인당 발급 제한
			if (UtilsText.equals(couponInfo.getPer1psnIssueLimitYn(), "Y")
					&& couponInfo.getPer1psnMaxIssueCount() <= memberCouponCnt) {
				condition = false;
			}
		} else {
			// 쿠폰 발행 무제한 X 일때
			if (couponInfo.getTotalIssueLimitCount() <= totalIssueCnt) {
				condition = false;
			}
		}

		return condition;
	}

	/**
	 * 쿠폰 관리 디바이스 조회
	 *
	 * @Desc : 쿠폰 관리 디바이스 조회
	 * @Method Name : getPrCouponDeviceListByCpnNo
	 * @Date : 2019. 3. 8
	 * @Author : easyhun
	 * @param cpnNo
	 * @return
	 * @throws Exception
	 */
	public List<PrCouponApplyDevice> getPrCouponDeviceListByCpnNo(String cpnNo) throws Exception {
		return prCouponApplyDeviceDao.selectPrCouponDeviceListByCpnNo(cpnNo);
	}

	/**
	 * 쿠폰 관리 채널 조회
	 *
	 * @Desc : 쿠폰 관리 채널 조회
	 * @Method Name : getPrCouponApplyChannelListByCpnNo
	 * @Date : 2019. 3. 8
	 * @Author : easyhun
	 * @param cpnNo
	 * @return
	 * @throws Exception
	 */
	public List<PrCouponApplyChannel> getPrCouponApplyChannelListByCpnNo(String cpnNo) throws Exception {
		return prCouponApplyChannelDao.selectPrCouponApplyChannelListByCpnNo(cpnNo);
	}

	/**
	 * 쿠폰 관리 카테고리 조회
	 *
	 * @Desc : 쿠폰 관리 카테고리 조회
	 * @Method Name : getPrCouponDeivceListByCpnNo
	 * @Date : 2019. 3. 8
	 * @Author : easyhun
	 * @param cpnNo
	 * @return
	 * @throws Exception
	 */
	public List<PrCouponApplyCategory> getPrCouponApplyCategoryListByCpnNo(String cpnNo) throws Exception {
		return prCouponApplyCategoryDao.selectPrCouponApplyCategoryListByCpnNo(cpnNo);
	}

	/**
	 * 쿠폰 관리 입점사 조회
	 *
	 * @Desc : 쿠폰 관리 입점사 조회
	 * @Method Name : getPrCouponDeivceListByCpnNo
	 * @Date : 2019. 3. 8
	 * @Author : easyhun
	 * @param cpnNo
	 * @return
	 * @throws Exception
	 */
	public List<PrCouponVendorShareRate> getPrCouponVendorShareRateListByCpnNo(String cpnNo) throws Exception {
		return prCouponVendorShareRateDao.selectPrCouponVendorShareRateListByCpnNo(cpnNo);
	}

	/**
	 * 쿠폰 관리 상품 조회
	 *
	 * @Desc : 쿠폰 관리 상품 조회
	 * @Method Name : getPrCouponApplyProductListByCpnNo
	 * @Date : 2019. 4. 16
	 * @Author : easyhun
	 * @param cpnNo
	 * @return
	 * @throws Exception
	 */
	public List<PrCouponApplyProduct> getPrCouponApplyProductListByCpnNo(String cpnNo) throws Exception {
		return prCouponApplyProductDao.selectPrCouponApplyProductListByCpnNo(cpnNo);
	}

	/**
	 * 쿠폰 관리 매장 조회
	 *
	 * @Desc : 쿠폰 관리 매장 조회
	 * @Method Name : getPrCouponApplyStoreListByCpnNo
	 * @Date : 2019. 4. 16
	 * @Author : easyhun
	 * @param cpnNo
	 * @return
	 * @throws Exception
	 */
	public List<PrCouponApplyStore> getPrCouponApplyStoreListByCpnNo(String cpnNo) throws Exception {
		return prCouponApplyStoreDao.selectPrCouponApplyStoreListByCpnNo(cpnNo);
	}

	/**
	 * 쿠폰 관리 브랜드 조회
	 *
	 * @Desc : 쿠폰 관리 브랜드 조회
	 * @Method Name : getPrCouponApplyBrandListByCpnNo
	 * @Date : 2019. 4. 17
	 * @Author : easyhun
	 * @param cpnNo
	 * @return
	 * @throws Exception
	 */
	public List<PrCouponApplyBrand> getPrCouponApplyBrandListByCpnNo(String cpnNo) throws Exception {
		return prCouponApplyBrandDao.selectPrCouponApplyBrandListByCpnNo(cpnNo);
	}

	/**
	 * 회원 사용 가능한 쿠폰 조회
	 *
	 * @Desc : 회원 사용 가능한 쿠폰 조회
	 * @Method Name : getMemberCanUseCouponCount
	 * @Date :2019. 3. 28
	 * @Author : easyhun
	 * @param prCouponSearchVO
	 * @return
	 * @throws Exception
	 */
	public int getMemberCanUseCouponCount(PrCouponSearchVO prCouponSearchVO) throws Exception {
		return prCouponDao.selectMemberCanUseCouponCount(prCouponSearchVO);
	}

	/**
	 * 상품번호에 의한 쿠폰 조회
	 *
	 * @Desc : 상품번호에 의한 쿠폰 조회
	 * @Method Name : getPrdtNoByCpnList
	 * @Date : 2019. 9. 10
	 * @Author : easyhun
	 * @param prdtNo
	 * @return
	 * @throws Exception
	 */

	public List<PrCoupon> getPrdtNoByCpnList(String prdtNo) throws Exception {
		List<PrCoupon> prdtNoByCpnList = prCouponDao.selectPrdtNoByCpnList(prdtNo);

		return prdtNoByCpnList;
	}

	/**
	 * 쿠폰 지류 조회
	 *
	 * @Desc : 쿠폰 지류 조회
	 * @Method Name : getPrCouponPaperNumberList
	 * @Date : 2019. 7. 18
	 * @Author : easyhun
	 * @param cpnNo
	 * @return
	 */
	public List<PrCouponPaperNumber> getPrCouponPaperNumberList(String cpnNo) throws Exception {

		return prCouponPaperNumberDao.selectPrCouponPaperNumberList(cpnNo);
	}

	/**
	 * @Desc : 쿠폰 기존 입점업체 정보와 추가된 입점 업체 정보를 조회한다.
	 * @Method Name : getVendorCouponShareRateList
	 * @Date : 2020. 2. 18.
	 * @Author : kiowa
	 * @param cpnNo
	 * @param vndrNoList
	 * @return
	 * @throws Exception
	 */
	public List<PrCouponVendorShareRate> getVendorCouponShareRateList(String cpnNo, List<String> vndrNoList)
			throws Exception {

		return prCouponVendorShareRateDao.selectVendorCouponShareRateList(cpnNo, vndrNoList);
	}

}
