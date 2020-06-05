package kr.co.abcmart.bo.event.service;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.event.model.master.EvEvent;
import kr.co.abcmart.bo.event.model.master.EvEventAnswer;
import kr.co.abcmart.bo.event.model.master.EvEventAttendanceCheckBenefit;
import kr.co.abcmart.bo.event.model.master.EvEventImage;
import kr.co.abcmart.bo.event.model.master.EvEventJoinMember;
import kr.co.abcmart.bo.event.model.master.EvEventProductReceiptStore;
import kr.co.abcmart.bo.event.model.master.EvEventPublicationNumber;
import kr.co.abcmart.bo.event.model.master.EvEventResult;
import kr.co.abcmart.bo.event.model.master.EvEventResultBenefit;
import kr.co.abcmart.bo.event.model.master.EvEventResultBenefitMember;
import kr.co.abcmart.bo.event.model.master.EvEventRouletteBenefit;
import kr.co.abcmart.bo.event.model.master.EvEventRouletteJoinMember;
import kr.co.abcmart.bo.event.model.master.EvEventTargetChannel;
import kr.co.abcmart.bo.event.model.master.EvEventTargetCoupon;
import kr.co.abcmart.bo.event.model.master.EvEventTargetDevice;
import kr.co.abcmart.bo.event.model.master.EvEventTargetGrade;
import kr.co.abcmart.bo.event.model.master.EvEventTargetProduct;
import kr.co.abcmart.bo.event.repository.master.EvEventAnswerDao;
import kr.co.abcmart.bo.event.repository.master.EvEventAttendanceCheckBenefitDao;
import kr.co.abcmart.bo.event.repository.master.EvEventAttendanceCheckMemberDao;
import kr.co.abcmart.bo.event.repository.master.EvEventDao;
import kr.co.abcmart.bo.event.repository.master.EvEventImageDao;
import kr.co.abcmart.bo.event.repository.master.EvEventJoinMemberDao;
import kr.co.abcmart.bo.event.repository.master.EvEventProductReceiptStoreDao;
import kr.co.abcmart.bo.event.repository.master.EvEventPublicationNumberDao;
import kr.co.abcmart.bo.event.repository.master.EvEventResultBenefitDao;
import kr.co.abcmart.bo.event.repository.master.EvEventResultBenefitMemberDao;
import kr.co.abcmart.bo.event.repository.master.EvEventResultDao;
import kr.co.abcmart.bo.event.repository.master.EvEventRouletteBenefitDao;
import kr.co.abcmart.bo.event.repository.master.EvEventRouletteJoinMemberDao;
import kr.co.abcmart.bo.event.repository.master.EvEventTargetChannelDao;
import kr.co.abcmart.bo.event.repository.master.EvEventTargetCouponDao;
import kr.co.abcmart.bo.event.repository.master.EvEventTargetDeviceDao;
import kr.co.abcmart.bo.event.repository.master.EvEventTargetGradeDao;
import kr.co.abcmart.bo.event.repository.master.EvEventTargetProductDao;
import kr.co.abcmart.bo.event.vo.EvEventJoinResultVO;
import kr.co.abcmart.bo.event.vo.EvEventSearchVO;
import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.bo.system.repository.master.SyCodeDetailDao;
import kr.co.abcmart.common.config.Config;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.FileUpload;
import kr.co.abcmart.common.util.UtilsArray;
import kr.co.abcmart.common.util.UtilsDate;
import kr.co.abcmart.common.util.UtilsFile;
import kr.co.abcmart.common.util.UtilsSftp;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EventService {

	private static final String FILE_PATH = UtilsText.concat(Const.UPLOAD_FILE_PATH, Const.IMG_SRC_PROMOTION_EVENT);

	@Autowired
	private EvEventDao evEventDao;

	@Autowired
	private EvEventTargetDeviceDao evEventTargetDeviceDao;

	@Autowired
	private EvEventTargetChannelDao evEventTargetChannelDao;

	@Autowired
	private EvEventImageDao evEventImageDao;

	@Autowired
	private EvEventTargetGradeDao evEventTargetGradeDao;

	@Autowired
	private EvEventTargetCouponDao evEventTargetCouponDao;

	@Autowired
	private EvEventAttendanceCheckBenefitDao evEventAttendanceCheckBenefitDao;

	@Autowired
	private EvEventRouletteBenefitDao evEventRouletteBenefitDao;

	@Autowired
	private SyCodeDetailDao syCodeDetailDao;

	@Autowired
	private EvEventJoinMemberDao evEventJoinMemberDao;

	@Autowired
	private EvEventRouletteJoinMemberDao evEventRouletteJoinMemberDao;

	@Autowired
	private EvEventAttendanceCheckMemberDao evEventAttendanceCheckMemberDao;

	@Autowired
	private EvEventAnswerDao evEventAnswerDao;

	@Autowired
	private EvEventResultDao evEventResultDao;

	@Autowired
	private EvEventResultBenefitDao evEventResultBenefitDao;

	@Autowired
	private EvEventResultBenefitMemberDao evEventResultBenefitMemberDao;

	@Autowired
	private EvEventTargetProductDao evEventTargetProductDao;

	@Autowired
	private EvEventProductReceiptStoreDao evEventProductReceiptStoreDao;

	@Autowired
	private EvEventPublicationNumberDao evEventPublicationNumberDao;

	@Autowired
	private SqlSessionFactory sqlSessionFactoryForBatch;

	/**
	 * 이벤트 목록 조회
	 *
	 * @Desc : 이벤트 목록 조회
	 * @Method Name : getEventList
	 * @Date : 2019. 3. 25
	 * @Author : easyhun
	 * @param pageable
	 * @return
	 */
	public Page<EvEvent> getEventList(Pageable<EvEventSearchVO, EvEvent> pageable) throws Exception {

		int count = evEventDao.selectEvEventCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(evEventDao.selectEvEventList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * 이벤트 상세 조회
	 *
	 * @Desc : 이벤트 상세 조회
	 * @Method Name : getEvent
	 * @Date : 2019. 3. 25
	 * @Author : easyhun
	 * @param EvEvent
	 * @return
	 */

	public EvEvent getEvent(EvEvent evEvent) throws Exception {

		return evEventDao.selectEvEvent(evEvent);
	}

	/**
	 * 이벤트 디바이스 조회
	 *
	 * @Desc : 이벤트 디바이스 조회
	 * @Method Name : getEvEventDeviceListByEventNo
	 * @Date : 2019. 3. 26
	 * @Author : easyhun
	 * @param eventNo
	 * @return
	 */
	public List<EvEventTargetDevice> getEvEventDeviceListByEventNo(String eventNo) throws Exception {

		return evEventTargetDeviceDao.selectEvEventDeviceListByEventNo(eventNo);
	}

	/**
	 * 이벤트 채널 조회
	 *
	 * @Desc : 이벤트 채널 조회
	 * @Method Name : getEvEventChannelListByEventNo
	 * @Date : 2019. 3. 26
	 * @Author : easyhun
	 * @param eventNo
	 * @return
	 */
	public List<EvEventTargetChannel> getEvEventChannelListByEventNo(String eventNo) throws Exception {

		return evEventTargetChannelDao.selectEvEventChannelListByEventNo(eventNo);
	}

	/**
	 * 이벤트 이미지 조회
	 *
	 * @Desc : 이벤트 이미지 조회
	 * @Method Name : getEvEventImageListByEventNo
	 * @Date : 2019. 3. 28
	 * @Author : easyhun
	 * @param eventNo
	 * @return
	 */
	public List<EvEventImage> getEvEventImageListByEventNo(String eventNo) throws Exception {

		return evEventImageDao.selectEvEventImageListByEventNo(eventNo);
	}

	/**
	 * 이벤트 대상 회원 조회
	 *
	 * @Desc : 이벤트 대상 회원 조회
	 * @Method Name : getEvEventTargetGradeListByEventNo
	 * @Date : 2019. 3. 28
	 * @Author : easyhun
	 * @param eventNo
	 * @return
	 */
	public List<EvEventTargetGrade> getEvEventTargetGradeListByEventNo(String eventNo) throws Exception {

		return evEventTargetGradeDao.selectEvEventTargetGradeListByEventNo(eventNo);
	}

	/**
	 * 내부 관리번호 count 조회
	 *
	 * @Desc : 내부 관리번호 count 조회
	 * @Method Name : getEventDuplCheck
	 * @Date : 2019. 3. 27
	 * @Author : easyhun
	 * @param insdMgmtInfoText
	 * @return
	 * @throws Exception
	 */
	public boolean getEventDuplCheck(String insdMgmtInfoText, String eventNo) throws Exception {
		int count = evEventDao.selectEventDuplCheck(insdMgmtInfoText, eventNo);
		boolean duplCheckVal = true;
		if (count > 0)
			duplCheckVal = false;

		return duplCheckVal;
	}

	/**
	 * 이벤트 쿠폰 조회
	 *
	 * @Desc : 이벤트 쿠폰 조회
	 * @Method Name : getEvEventTargetCouponListByEventNo
	 * @Date : 2019. 4. 02
	 * @Author : easyhun
	 * @param eventNo
	 * @return
	 */
	public List<EvEventTargetCoupon> getEvEventTargetCouponListByEventNo(String eventNo) throws Exception {

		return evEventTargetCouponDao.selectEvEventTargetCouponListByEventNo(eventNo);
	}

	/**
	 * 이벤트 출석 혜택 조회
	 *
	 * @Desc : 이벤트 쿠폰 조회
	 * @Method Name : getEvEventAttendanceCheckBenefitListByEventNo
	 * @Date : 2019. 4. 03
	 * @Author : easyhun
	 * @param eventNo
	 * @return
	 */
	public List<EvEventAttendanceCheckBenefit> getEvEventAttendanceCheckBenefitListByEventNo(String eventNo)
			throws Exception {

		return evEventAttendanceCheckBenefitDao.selectEvEventAttendanceCheckBenefitListByEventNo(eventNo);
	}

	/**
	 * 이벤트 룰렛 혜택 조회
	 *
	 * @Desc : 이벤트 쿠폰 조회
	 * @Method Name : getEvEventRouletteBenefitListByEventNo
	 * @Date : 2019. 4. 03
	 * @Author : easyhun
	 * @param eventNo
	 * @return
	 */
	public List<EvEventRouletteBenefit> getEvEventRouletteBenefitListByEventNo(String eventNo) throws Exception {

		return evEventRouletteBenefitDao.selectEvEventRouletteBenefitListByEventNo(eventNo);
	}

	/**
	 * 이벤트 매장 조회
	 *
	 * @Desc : 이벤트 매장 조회
	 * @Method Name : getEvEventProductReceiptStoreListByEventNo
	 * @Date : 2019. 6. 07
	 * @Author : easyhun
	 * @param eventNo
	 * @return
	 */
	public List<EvEventProductReceiptStore> getEvEventProductReceiptStoreListByEventNo(String eventNo)
			throws Exception {

		return evEventProductReceiptStoreDao.selectEvEventProductReceiptStoreListByEventNo(eventNo);
	}

	/**
	 * 이벤트 등록
	 *
	 * @Desc : 이벤트 등록
	 * @Method Name : insertEvEvent
	 * @Date : 2019. 4. 03
	 * @Author : easyhun
	 * @param evEvent
	 * @return
	 */
	public void insertEvEvent(EvEvent evEvent) throws Exception {
		// 세션 정보 호출
		UserDetails user = LoginManager.getUserDetails();
		evEvent.setRgsterNo(user.getAdminNo());
		evEvent.setRgstDtm(Timestamp.valueOf(LocalDateTime.now()));
		evEvent.setModerNo(user.getAdminNo());
		evEvent.setModDtm(evEvent.getRgstDtm());

		// 유효기간, 발급기간 Date Set
		if (UtilsText.equals(evEvent.getEventTermSetupYn(), Const.BOOLEAN_FALSE)) {
			evEvent.setEventStartDtm(new Timestamp(new Date().getTime()));
			evEvent.setEventEndDtm(Timestamp.valueOf(LocalDateTime.of(9999, 12, 31, 23, 59, 59)));
		}else {
			evEvent.setEventStartDtm(evEvent.getParamEventStartDtm());
			evEvent.setEventEndDtm(evEvent.getParamEventEndDtm());
		}
		
		// 당첨발표여부 기간 세팅
		if (UtilsText.equals(evEvent.getPrzwrPblcYn(), Const.BOOLEAN_TRUE)) {
			evEvent.setPrzwrPblcTodoYmd(evEvent.getPrzwrPblcTodoDtm());
		}
		
		// 당첨자구입일 세팅(드로우이벤트)
		if(UtilsText.equals(evEvent.getEventTypeCode(), CommonCode.EVENT_TYPE_CODE_PARTICIPATION_TYPE_DRAW)) {
			evEvent.setPrzwrOrderStartYmd(evEvent.getPrzwrOrderStartDtm());
			evEvent.setPrzwrOrderEndYmd(evEvent.getPrzwrOrderEndDtm());
		}

		evEventDao.insertEvEvent(evEvent);
		String eventNo = evEvent.getEventNo();

		if (UtilsText.isNotBlank(eventNo)) {
			// 이벤트 채널
			if (evEvent.getEvEventTargetChannelArr() != null) {
				for (EvEventTargetChannel evEventTargetChannel : evEvent.getEvEventTargetChannelArr()) {
					log.debug("evEventTargetChannel = {} ", evEventTargetChannel.getChnnlNo());
					evEventTargetChannel.setEventNo(eventNo);
					evEventTargetChannelDao.insert(evEventTargetChannel);
				}
			}

			// 디바이스
			if (evEvent.getDeviceCodes() != null) {
				for (String deviceCode : evEvent.getDeviceCodes()) {
					log.debug("deviceCode = {} ", deviceCode);
					EvEventTargetDevice evEventTargetDevice = new EvEventTargetDevice();
					evEventTargetDevice.setEventNo(eventNo);
					evEventTargetDevice.setDeviceCode(deviceCode);
					evEventTargetDeviceDao.insert(evEventTargetDevice);
				}
			}

			// 대상상품지정
			if (evEvent.getEvEventTargetProductArr() != null) {
				for (EvEventTargetProduct evEventTargetProduct : evEvent.getEvEventTargetProductArr()) {
					evEventTargetProduct.setEventNo(eventNo);
					int drawDuplCheck = evEventTargetProductDao
							.selectEventDrawDuplCheck(evEventTargetProduct.getPrdtNo(), eventNo);
					if (drawDuplCheck > 0)
						throw new Exception("이미 드로우이벤트가 진행중인 상품이 포함되어있습니다.");
					evEventTargetProductDao.insert(evEventTargetProduct);
				}
			}

			// 회원 유형
			short targetGradeIdx = 0;
			if (evEvent.getEvEventTargetGradeArr() != null || UtilsText.isNotBlank(evEvent.getEmpYn())) {
				SyCodeDetail syCodeDetail = new SyCodeDetail();
				syCodeDetail.setCodeField(CommonCode.JOIN_LIMIT_TYPE_CODE);
				for (EvEventTargetGrade evEventTargetGrade : evEvent.getEvEventTargetGradeArr()) {
					evEventTargetGrade.setEventNo(eventNo);

					// 등록, 수정일시
					evEventTargetGrade.setRgsterNo(user.getAdminNo());
					evEventTargetGrade.setRgstDtm(new Timestamp(new Date().getTime()));
					evEventTargetGrade.setModerNo(user.getAdminNo());
					evEventTargetGrade.setModDtm(evEvent.getRgstDtm());

					// 참여제한 유형코드 입력
					if (UtilsText.equals(CommonCode.EVENT_TYPE_CODE_NOTICE_TYPE, evEvent.getEventTypeCode())
							&& !UtilsText.equals("Y", evEvent.getPblicteNoYn())
							&& (UtilsText.equals("C", evEvent.getEventJoinType())
									|| UtilsText.isBlank(evEvent.getEventJoinType()))) {
						syCodeDetail.setCodeDtlNo(CommonCode.JOIN_LIMIT_TYPE_CODE_NONE);
						evEventTargetGrade.setJoinLimitTypeCode(CommonCode.JOIN_LIMIT_TYPE_CODE_NONE);
					} 
					// 2020.05.19: 드로우 이벤트라면 ID당 1회 고정 (스크립트단에서 disable 되어있어서 값이 안넘어와 null error 발생!)
					else if(UtilsText.equals(CommonCode.EVENT_TYPE_CODE_PARTICIPATION_TYPE_DRAW, evEvent.getEventTypeCode())) {
						syCodeDetail.setCodeDtlNo(CommonCode.JOIN_LIMIT_TYPE_CODE_ONCE_PER_ID);
						evEventTargetGrade.setJoinLimitTypeCode(CommonCode.JOIN_LIMIT_TYPE_CODE_ONCE_PER_ID);
					}
					else {
						syCodeDetail.setCodeDtlNo(evEventTargetGrade.getJoinLimitTypeCode());
					}
					syCodeDetail = syCodeDetailDao.selectByPrimaryKey(syCodeDetail);
					evEventTargetGrade.setLimitType(syCodeDetail.getAddInfo1());
					evEventTargetGrade.setLimitCount(Short.parseShort(syCodeDetail.getAddInfo2()));

					// 회원유형 및 등급
					if (UtilsText.isNotBlank(evEvent.getEmpYn()) && UtilsText.equals(evEvent.getEmpYn(), "Y")) {
						evEventTargetGrade.setEmpYn(evEvent.getEmpYn());
						evEventTargetGrade.setMemberTypeCode(CommonCode.MEMBER_TYPE_MEMBERSHIP);
						evEventTargetGrade.setMbshpGradeCode(CommonCode.MBSHP_GRADE_CODE_NORMAL);
						evEventTargetGrade.setEventTrgtGradeSeq(++targetGradeIdx);
						evEventTargetGradeDao.insert(evEventTargetGrade);
					} else {
						evEventTargetGrade.setEmpYn("N");
						if (UtilsText.equals(evEventTargetGrade.getMemberTypeCode(), CommonCode.MEMBER_TYPE_MEMBERSHIP)
								&& evEvent.getMbshpGradeCodes() != null) {
							for (String mbshpGradeCode : evEvent.getMbshpGradeCodes()) {
								evEventTargetGrade.setEventTrgtGradeSeq(++targetGradeIdx);
								evEventTargetGrade.setMbshpGradeCode(mbshpGradeCode);
								evEventTargetGradeDao.insert(evEventTargetGrade);
							}
						} else {
							evEventTargetGrade.setMbshpGradeCode(CommonCode.MBSHP_GRADE_CODE_NORMAL);
							evEventTargetGrade.setEventTrgtGradeSeq(++targetGradeIdx);
							evEventTargetGradeDao.insert(evEventTargetGrade);
						}
					}
				}
			}

			// 쿠폰
			int couponIdx = 0;
			if (evEvent.getEvEventTargetCouponArr() != null && UtilsText.equals("C", evEvent.getEventJoinType())) {
				for (EvEventTargetCoupon evEventTargetCoupon : evEvent.getEvEventTargetCouponArr()) {
					evEventTargetCoupon.setEventNo(eventNo);
					evEventTargetCoupon.setSortSeq(++couponIdx);

					// 쿠폰 count를 따라가므로 0으로 셋
					evEventTargetCoupon.setIssueCount(0);
					evEventTargetCoupon.setIssueLimitCount(0);

					evEventTargetCouponDao.insert(evEventTargetCoupon);
				}
			}

			// 매장
			if (evEvent.getEvEventProductReceiptStoreArr() != null) {
				for (EvEventProductReceiptStore evEventProductReceiptStore : evEvent
						.getEvEventProductReceiptStoreArr()) {
					evEventProductReceiptStore.setEventNo(eventNo);
					evEventProductReceiptStoreDao.insert(evEventProductReceiptStore);
				}
			}

			// 출석혜택
			if (evEvent.getEvEventAttendanceCheckBenefitArr() != null) {
				for (EvEventAttendanceCheckBenefit evEventAttendanceCheckBenefit : evEvent
						.getEvEventAttendanceCheckBenefitArr()) {
					evEventAttendanceCheckBenefit.setEventNo(eventNo);
					if (UtilsText.isBlank(evEventAttendanceCheckBenefit.getBenefitValue())) {
						evEventAttendanceCheckBenefit.setBenefitValue("0");
					}
					evEventAttendanceCheckBenefitDao.insert(evEventAttendanceCheckBenefit);
				}
			}

			// 룰렛혜택
			short imageIdx = 0;
			if (evEvent.getEvEventRouletteBenefitArr() != null) {
				for (EvEventRouletteBenefit evEventRouletteBenefit : evEvent.getEvEventRouletteBenefitArr()) {
					evEventRouletteBenefit.setEventNo(eventNo);

					if (evEventRouletteBenefit.getIssueLimitCount() == null)
						evEventRouletteBenefit.setIssueLimitCount(0);
					if (evEventRouletteBenefit.getWinRateValue() == null)
						evEventRouletteBenefit.setWinRateValue((short) 0);

					if (UtilsText.equals(evEventRouletteBenefit.getWinRateCode(), CommonCode.WIN_RATE_CODE_INPUT))
						evEventRouletteBenefit.setWinRate(evEventRouletteBenefit.getWinRateValue());
					if (UtilsText.equals(evEventRouletteBenefit.getBenefitGbnCode(), CommonCode.BENEFIT_GBN_CODE_ETC)) {

						FileUpload imageFile = evEvent.getRouletteImages()[imageIdx++];
						if (imageFile != null && imageFile.isFileItem()) {

							String fileName = imageFile.getOrgFileName();
							String uploadFileName = UtilsText.concat(String.valueOf(System.currentTimeMillis()), ".",
									FilenameUtils.getExtension(fileName));
							String path = UtilsText.concat(FILE_PATH, "/",
									UtilsDate.today(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS));

							try {
								UtilsSftp.upload(path, uploadFileName, imageFile.getMultiPartFile().getInputStream());
							} catch (Exception e) {
								log.error("{}", e);
								throw new Exception("파일저장에 실패하였습니다.");
							}

							evEventRouletteBenefit.setImagePathText(UtilsText.concat(path, uploadFileName));
							evEventRouletteBenefit
									.setImageUrl(UtilsText.concat(Const.URL_IMG_HTTPS, "/", path, "/", uploadFileName));
							evEventRouletteBenefit.setImageName(fileName);

							if (UtilsText.isBlank(evEventRouletteBenefit.getAltrnText())) {
								evEventRouletteBenefit.setAltrnText(fileName.split("\\.")[0]);
							}
						}
					}
					evEventRouletteBenefitDao.insertEvEventRouletteBenefit(evEventRouletteBenefit);
				}
			}

			// 이미지
			if (evEvent.getEvEventImageArr() != null) {
				String filePathName = "";
				String imageDeviceCode = "";
				for (EvEventImage evEventImage : evEvent.getEvEventImageArr()) {
					evEventImage.setEventNo(eventNo);
					FileUpload imageFile = null;
					if (UtilsText.equals(evEventImage.getDeviceType(), "PC")) {
						filePathName = "pc";
						imageDeviceCode = CommonCode.DEVICE_PC;
						if (UtilsText.equals(evEventImage.getImageGbnType(), "G"))
							imageFile = evEvent.getPcEventBgImg();
						else
							imageFile = evEvent.getPcEventImg();
					} else {
						filePathName = "mobile";
						imageDeviceCode = CommonCode.DEVICE_MOBILE;
						if (UtilsText.equals(evEventImage.getImageGbnType(), "G"))
							imageFile = evEvent.getMoEventBgImg();
						else
							imageFile = evEvent.getMoEventImg();
					}
					evEventImage.setDeviceCode(imageDeviceCode);

					if (imageFile != null && imageFile.isFileItem()) {

						String fileName = imageFile.getOrgFileName();
						String uploadFileName = UtilsText.concat(String.valueOf(System.currentTimeMillis()), ".",
								FilenameUtils.getExtension(fileName));
						String path = UtilsText.concat(FILE_PATH, "/",
								UtilsDate.today(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS));

						try {
							UtilsSftp.upload(path, uploadFileName, imageFile.getMultiPartFile().getInputStream());
						} catch (Exception e) {
							log.error("{}", e);
							throw new Exception("파일저장에 실패하였습니다.");
						}

						evEventImage.setImagePathText(UtilsText.concat(path, uploadFileName));
						evEventImage.setImageUrl(UtilsText.concat(Const.URL_IMG_HTTPS, "/", path, "/", uploadFileName));
						evEventImage.setImageName(fileName);

						if (UtilsText.isBlank(evEventImage.getAltrnText())) {
							evEventImage.setAltrnText(fileName.split("\\.")[0]);
						}

						evEventImageDao.insert(evEventImage);
					}
				}
			}

			if (evEvent.getEventPblicteNoCnt() != 0) {
				Long publicationNumberCount = 0L;

				SqlSession sqlSessionForBatch = this.sqlSessionFactoryForBatch.openSession(ExecutorType.BATCH);
				EvEventPublicationNumberDao mapperForBatch = sqlSessionForBatch
						.getMapper(EvEventPublicationNumberDao.class);

				try {

					List<EvEventPublicationNumber> rows = new ArrayList<EvEventPublicationNumber>();

					for (int i = 0; i < evEvent.getEventPblicteNoCnt(); i++) {

						publicationNumberCount = mapperForBatch
								.selectEvEventPublicationNumberSeq(Integer.valueOf(rows.size()));
						EvEventPublicationNumber evEventPublicationNumber = new EvEventPublicationNumber();
						String makeRandomNumber = this.makeRandomNumber(publicationNumberCount, eventNo, "event");
						evEventPublicationNumber.setEventNo(eventNo);
						evEventPublicationNumber.setEventPblicteNo(makeRandomNumber);
						rows.add(evEventPublicationNumber);

						if ((i + 1) == evEvent.getEventPblicteNoCnt() || (i + 1) % 1000 == 0) {
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
	 * 이벤트 수정
	 *
	 * @Desc : 이벤트 수정
	 * @Method Name : updateEvEvent
	 * @Date : 2019. 4. 04
	 * @Author : easyhun
	 * @param evEvent
	 * @return
	 */
	public void updateEvEvent(EvEvent evEvent) throws Exception {
		String eventNo = evEvent.getEventNo();

		if (UtilsText.isBlank(eventNo)) {
			throw new Exception("이벤트 수정시 오류");
		}
		
		// 세션 정보 호출
		UserDetails user = LoginManager.getUserDetails();
		evEvent.setModerNo(user.getAdminNo());
		evEvent.setModDtm(Timestamp.valueOf(LocalDateTime.now()));

		// 유효기간, 발급기간 Date Set
		if (UtilsText.equals(evEvent.getEventTermSetupYn(), Const.BOOLEAN_FALSE)) {
			evEvent.setEventStartDtm(Timestamp.valueOf(LocalDateTime.now()));
			evEvent.setEventEndDtm(Timestamp.valueOf(LocalDateTime.of(9999, 12, 31, 23, 59, 59)));
		}else {
			evEvent.setEventStartDtm(evEvent.getParamEventStartDtm());
			evEvent.setEventEndDtm(evEvent.getParamEventEndDtm());
		}
		
		// 당첨발표여부 기간 세팅
		if (UtilsText.equals(evEvent.getPrzwrPblcYn(), Const.BOOLEAN_TRUE)) {
			evEvent.setPrzwrPblcTodoYmd(evEvent.getPrzwrPblcTodoDtm());
		}
		
		// 당첨자구입일 세팅(드로우이벤트)
		if(UtilsText.equals(evEvent.getEventTypeCode(), CommonCode.EVENT_TYPE_CODE_PARTICIPATION_TYPE_DRAW)) {
			evEvent.setPrzwrOrderStartYmd(evEvent.getPrzwrOrderStartDtm());
			evEvent.setPrzwrOrderEndYmd(evEvent.getPrzwrOrderEndDtm());
		}

		if(!UtilsText.equals(evEvent.getPointDdctYn(), Const.BOOLEAN_TRUE) ) {
			evEvent.setPointDdctYn(Const.BOOLEAN_FALSE);
			evEvent.setDdctPointAmt(0);
		}
		
		// 일반형일때 초기화 영역
		if(UtilsText.equals(evEvent.getEventTypeCode(), CommonCode.EVENT_TYPE_CODE_NOTICE_TYPE)) {
			// 지류일시 고정
			if (UtilsText.equals("Y", evEvent.getPblicteNoYn())) {
				evEvent.setEventJoinType("D");
			}
			
			if(UtilsText.equals(evEvent.getEventJoinType(), "C")) {
				evEvent.setLoginIdDupPermYn(null);
				evEvent.setGeBenefitType(null);
				evEvent.setGeBenefitValue(null);
				evEvent.setGeBenefitName(null);
			}
			else if(UtilsText.equals(evEvent.getEventJoinType(), "D")) {
				evEvent.setLoginIdDupPermYn(null);
				evEvent.setGeBenefitType(null);
				evEvent.setGeBenefitValue(null);
				evEvent.setGeBenefitName(null);
			}
			else if(UtilsText.equals(evEvent.getEventJoinType(), "L")) {

			}
			
		}
		
		evEventDao.updateEvEvent(evEvent);

		// 이벤트 채널
		evEventTargetChannelDao.deleteByEventNo(eventNo);
		// if (evEvent.getChnnlNo() != null) {
		// EvEventTargetChannel evEventTargetChannel = new EvEventTargetChannel();
		// evEventTargetChannel.setEventNo(eventNo);
		// evEventTargetChannel.setChnnlNo(evEvent.getChnnlNo());
		// evEventTargetChannelDao.insert(evEventTargetChannel);
		// }
		if (evEvent.getEvEventTargetChannelArr() != null) {
			for (EvEventTargetChannel evEventTargetChannel : evEvent.getEvEventTargetChannelArr()) {
				evEventTargetChannel.setEventNo(eventNo);
				evEventTargetChannelDao.insert(evEventTargetChannel);
			}
		}

		// 디바이스
		evEventTargetDeviceDao.deleteByEventNo(eventNo);
		if (evEvent.getDeviceCodes() != null) {
			for (String deviceCode : evEvent.getDeviceCodes()) {
				EvEventTargetDevice evEventTargetDevice = new EvEventTargetDevice();
				evEventTargetDevice.setEventNo(eventNo);
				evEventTargetDevice.setDeviceCode(deviceCode);
				evEventTargetDeviceDao.insert(evEventTargetDevice);
			}
		}

		// 대상상품지정
		if (evEventJoinMemberDao.selectEvEventJoinMemberCountByEventNo(eventNo) == 0) {
			evEventTargetProductDao.deleteByEventNo(eventNo);
			if (evEvent.getEvEventTargetProductArr() != null) {
				for (EvEventTargetProduct evEventTargetProduct : evEvent.getEvEventTargetProductArr()) {
					evEventTargetProduct.setEventNo(eventNo);
					int drawDuplCheck = evEventTargetProductDao
							.selectEventDrawDuplCheck(evEventTargetProduct.getPrdtNo(), eventNo);
					if (drawDuplCheck > 0)
						throw new Exception("이미 드로우이벤트가 진행중인 상품이 포함되어있습니다.");
					evEventTargetProductDao.insert(evEventTargetProduct);
				}
			}
		}

		// 회원 유형
		short targetGradeIdx = 0;
		evEventTargetGradeDao.deleteByEventNo(eventNo);
		if (evEvent.getEvEventTargetGradeArr() != null || UtilsText.isNotBlank(evEvent.getEmpYn())) {
			SyCodeDetail syCodeDetail = new SyCodeDetail();
			syCodeDetail.setCodeField(CommonCode.JOIN_LIMIT_TYPE_CODE);
			for (EvEventTargetGrade evEventTargetGrade : evEvent.getEvEventTargetGradeArr()) {
				evEventTargetGrade.setEventNo(eventNo);

				// 등록, 수정일시
				evEventTargetGrade.setRgsterNo(user.getAdminNo());
				evEventTargetGrade.setRgstDtm(new Timestamp(new Date().getTime()));
				evEventTargetGrade.setModerNo(user.getAdminNo());
				evEventTargetGrade.setModDtm(evEvent.getRgstDtm());

				// 참여제한 유형코드 입력
				if (UtilsText.equals(CommonCode.EVENT_TYPE_CODE_NOTICE_TYPE, evEvent.getEventTypeCode())
						&& !UtilsText.equals("Y", evEvent.getPblicteNoYn())
						&& (UtilsText.equals("C", evEvent.getEventJoinType())
								|| UtilsText.isBlank(evEvent.getEventJoinType()))) {
					syCodeDetail.setCodeDtlNo("10005");
					evEventTargetGrade.setJoinLimitTypeCode("10005");
				} 
				// 2020.05.19: 드로우 이벤트라면 ID당 1회 고정 (스크립트단에서 disable 되어있어서 값이 안넘어와 null error 발생!)
				else if(UtilsText.equals(CommonCode.EVENT_TYPE_CODE_PARTICIPATION_TYPE_DRAW, evEvent.getEventTypeCode())) {
					syCodeDetail.setCodeDtlNo(CommonCode.JOIN_LIMIT_TYPE_CODE_ONCE_PER_ID);
					evEventTargetGrade.setJoinLimitTypeCode(CommonCode.JOIN_LIMIT_TYPE_CODE_ONCE_PER_ID);
				}
				else {
					syCodeDetail.setCodeDtlNo(evEventTargetGrade.getJoinLimitTypeCode());
				}
				syCodeDetail = syCodeDetailDao.selectByPrimaryKey(syCodeDetail);
				evEventTargetGrade.setLimitType(syCodeDetail.getAddInfo1());
				evEventTargetGrade.setLimitCount(Short.parseShort(syCodeDetail.getAddInfo2()));

				// 회원유형 및 등급
				if (UtilsText.isNotBlank(evEvent.getEmpYn()) && UtilsText.equals(evEvent.getEmpYn(), "Y")) {
					evEventTargetGrade.setEmpYn(evEvent.getEmpYn());
					evEventTargetGrade.setMemberTypeCode(CommonCode.MEMBER_TYPE_MEMBERSHIP);
					evEventTargetGrade.setMbshpGradeCode(CommonCode.MBSHP_GRADE_CODE_NORMAL);
					evEventTargetGrade.setEventTrgtGradeSeq(++targetGradeIdx);
					evEventTargetGradeDao.insert(evEventTargetGrade);
				} else {
					evEventTargetGrade.setEmpYn("N");
					if (UtilsText.equals(evEventTargetGrade.getMemberTypeCode(), CommonCode.MEMBER_TYPE_MEMBERSHIP)
							&& evEvent.getMbshpGradeCodes() != null) {
						for (String mbshpGradeCode : evEvent.getMbshpGradeCodes()) {
							evEventTargetGrade.setEventTrgtGradeSeq(++targetGradeIdx);
							evEventTargetGrade.setMbshpGradeCode(mbshpGradeCode);
							evEventTargetGradeDao.insert(evEventTargetGrade);
						}
					} else {
						evEventTargetGrade.setMbshpGradeCode(CommonCode.MBSHP_GRADE_CODE_NORMAL);
						evEventTargetGrade.setEventTrgtGradeSeq(++targetGradeIdx);
						evEventTargetGradeDao.insert(evEventTargetGrade);
					}
				}
			}
		}

		// 쿠폰
		int couponIdx = 0;
		evEventTargetCouponDao.deleteByEventNo(eventNo);
		if (evEvent.getEvEventTargetCouponArr() != null && UtilsText.equals("C", evEvent.getEventJoinType())) {
			for (EvEventTargetCoupon evEventTargetCoupon : evEvent.getEvEventTargetCouponArr()) {
				evEventTargetCoupon.setEventNo(eventNo);
				evEventTargetCoupon.setSortSeq(++couponIdx);

				// 쿠폰 count를 따라가므로 0으로 셋
				evEventTargetCoupon.setIssueCount(0);
				evEventTargetCoupon.setIssueLimitCount(0);
				evEventTargetCouponDao.insert(evEventTargetCoupon);
			}
		}

		// 매장
		evEventProductReceiptStoreDao.deleteByEventNo(eventNo);
		if (evEvent.getEvEventProductReceiptStoreArr() != null) {
			for (EvEventProductReceiptStore evEventProductReceiptStore : evEvent
					.getEvEventProductReceiptStoreArr()) {
				evEventProductReceiptStore.setEventNo(eventNo);
				evEventProductReceiptStoreDao.insert(evEventProductReceiptStore);
			}
		}

		// 출석혜택
		// if
		// (evEventAttendanceCheckMemberDao.selectEvEventAttendMemberCountByEventNo(eventNo)
		// == 0) {
		evEventAttendanceCheckBenefitDao.deleteByEventNo(eventNo);
		if (evEvent.getEvEventAttendanceCheckBenefitArr() != null) {
			for (EvEventAttendanceCheckBenefit evEventAttendanceCheckBenefit : evEvent
					.getEvEventAttendanceCheckBenefitArr()) {
				evEventAttendanceCheckBenefit.setEventNo(eventNo);
				if (UtilsText.isBlank(evEventAttendanceCheckBenefit.getBenefitValue())) {
					evEventAttendanceCheckBenefit.setBenefitValue("0");
				}
				evEventAttendanceCheckBenefitDao.insert(evEventAttendanceCheckBenefit);
			}
		}
		// }

		// 룰렛혜택
		short rouletteIdx = 0; // 룰렛 순번
		short rouletteImgIdx = 0; // image idx
		// 참여회원이 없을때만 수정(필요시 주석해제)
		// int rouletteJoinMemberCount = evEventRouletteJoinMemberDao
		// .selectEvEventRouletteJoinMemberCountByEventNo(eventNo);
		// if (rouletteJoinMemberCount == 0) {
		if (evEvent.getEvEventRouletteBenefitArr() != null) {
			for (EvEventRouletteBenefit evEventRouletteBenefit : evEvent.getEvEventRouletteBenefitArr()) {
				rouletteIdx = evEventRouletteBenefit.getEventRuletBenefitSeq();
				evEventRouletteBenefit.setEventNo(eventNo);

				if (evEventRouletteBenefit.getIssueLimitCount() == null)
					evEventRouletteBenefit.setIssueLimitCount(0);
				if (evEventRouletteBenefit.getWinRateValue() == null)
					evEventRouletteBenefit.setWinRateValue((short) 0);

				if (UtilsText.equals(evEventRouletteBenefit.getWinRateCode(), CommonCode.WIN_RATE_CODE_INPUT))
					evEventRouletteBenefit.setWinRate(evEventRouletteBenefit.getWinRateValue());
				if (UtilsText.equals(evEventRouletteBenefit.getBenefitGbnCode(), CommonCode.BENEFIT_GBN_CODE_ETC)) {
					FileUpload imageFile = null;
					if (evEvent.getRouletteImages() != null
							&& UtilsText.equals(evEvent.getRouletteImageChk()[rouletteIdx - 1], "Y")) {
						imageFile = evEvent.getRouletteImages()[rouletteImgIdx];
						rouletteImgIdx++;
					}

					EvEventRouletteBenefit savingEvEventRouletteBenefit = evEventRouletteBenefitDao
							.selectByPrimaryKey(evEventRouletteBenefit);
					// 기존 파일 삭제 및 삽입
					if (imageFile != null && imageFile.isFileItem()) {

						String fileName = imageFile.getOrgFileName();
						String uploadFileName = UtilsText.concat(String.valueOf(System.currentTimeMillis()), ".",
								FilenameUtils.getExtension(fileName));
						String path = UtilsText.concat(FILE_PATH, "/",
								UtilsDate.today(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS));

						try {
							UtilsSftp.upload(path, uploadFileName, imageFile.getMultiPartFile().getInputStream());
						} catch (Exception e) {
							log.error("{}", e);
							throw new Exception("파일저장에 실패하였습니다.");
						}

						evEventRouletteBenefit.setImagePathText(UtilsText.concat(path, uploadFileName));
						evEventRouletteBenefit
								.setImageUrl(UtilsText.concat(Const.URL_IMG_HTTPS, "/", path, "/", uploadFileName));
						evEventRouletteBenefit.setImageName(fileName);

						if (UtilsText.isBlank(evEventRouletteBenefit.getAltrnText())) {
							evEventRouletteBenefit.setAltrnText(fileName.split("\\.")[0]);
						}
						evEventRouletteBenefitDao.delete(evEventRouletteBenefit);
					} else if (savingEvEventRouletteBenefit != null) {
						// 전 데이터는 있지만 이미지를 지운경우
						if (UtilsText.isNotEmpty(savingEvEventRouletteBenefit.getImageName())
								&& UtilsText.equals(evEvent.getRouletteImageChk()[rouletteIdx - 1], "N")) {
//									File file = new File(UtilsText.concat(Const.UPLOAD_FILE_PATH,
//											savingEvEventRouletteBenefit.getImagePathText()));
//									UtilsFile.deleteQuietly(file);
						} else { // 이미지 그대로인 경우
							evEventRouletteBenefit.setImageName(savingEvEventRouletteBenefit.getImageName());
							evEventRouletteBenefit
									.setImagePathText(savingEvEventRouletteBenefit.getImagePathText());
							evEventRouletteBenefit.setImageUrl(savingEvEventRouletteBenefit.getImageUrl());
						}
						// evEventRouletteBenefitDao.delete(evEventRouletteBenefit);
					}
				} else {
					// evEventRouletteBenefitDao.delete(evEventRouletteBenefit);
				}
				// rouletteIdx++;
				// evEventRouletteBenefitDao.insertEvEventRouletteBenefit(evEventRouletteBenefit);
				evEventRouletteBenefitDao.updateEvEventRouletteBenefit(evEventRouletteBenefit);
			}
		} else { // 룰렛 혜택이 없을경우 기존 데이터 삭제
			List<EvEventRouletteBenefit> evEventRouletteBenefitList = evEventRouletteBenefitDao
					.selectEvEventRouletteBenefitListByEventNo(eventNo);
			for (EvEventRouletteBenefit evEventRouletteBenefit : evEventRouletteBenefitList) {
				if (UtilsText.equals(evEventRouletteBenefit.getBenefitGbnCode(), CommonCode.BENEFIT_GBN_CODE_ETC)) {
					if (UtilsText.isNotEmpty(evEventRouletteBenefit.getImagePathText())) {
						File file = new File(UtilsText.concat(Const.UPLOAD_FILE_PATH,
								evEventRouletteBenefit.getImagePathText()));
						UtilsFile.deleteQuietly(file);
					}
				}
				evEventRouletteBenefitDao.delete(evEventRouletteBenefit);
			}
		}
		// }

		// 이미지
		if (evEvent.getEvEventImageArr() != null) {
			String filePathName = "";
			String imageDeviceCode = "";
			for (EvEventImage evEventImage : evEvent.getEvEventImageArr()) {
				evEventImage.setEventNo(eventNo);
				FileUpload imageFile = null;
				if (UtilsText.equals(evEventImage.getDeviceType(), "PC")) {
					filePathName = "pc";
					imageDeviceCode = CommonCode.DEVICE_PC;
					if (UtilsText.equals(evEventImage.getImageGbnType(), "G"))
						imageFile = evEvent.getPcEventBgImg();
					else
						imageFile = evEvent.getPcEventImg();
				} else {
					filePathName = "mobile";
					imageDeviceCode = CommonCode.DEVICE_MOBILE;
					if (UtilsText.equals(evEventImage.getImageGbnType(), "G"))
						imageFile = evEvent.getMoEventBgImg();
					else
						imageFile = evEvent.getMoEventImg();
				}
				evEventImage.setDeviceCode(imageDeviceCode);

				EvEventImage savingEvEventImage = evEventImageDao.selectByPrimaryKey(evEventImage);
				if (imageFile != null && imageFile.isFileItem()) {
					// 이미지가 필수가 아니기 때문에 null check
					if (savingEvEventImage != null) {
						evEventImageDao.delete(evEventImage);
					}

					String fileName = imageFile.getOrgFileName();
					String uploadFileName = UtilsText.concat(String.valueOf(System.currentTimeMillis()), ".",
							FilenameUtils.getExtension(fileName));
					String path = UtilsText.concat(FILE_PATH, "/",
							UtilsDate.today(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS));

					try {
						UtilsSftp.upload(path, uploadFileName, imageFile.getMultiPartFile().getInputStream());
					} catch (Exception e) {
						log.error("{}", e);
						throw new Exception("파일저장에 실패하였습니다.");
					}

					evEventImage.setImagePathText(UtilsText.concat(path, uploadFileName));
					evEventImage.setImageUrl(UtilsText.concat(Const.URL_IMG_HTTPS, "/", path, "/", uploadFileName));
					evEventImage.setImageName(fileName);

					if (UtilsText.isBlank(evEventImage.getAltrnText())) {
						evEventImage.setAltrnText(fileName.split("\\.")[0]);
					}

					evEventImageDao.insert(evEventImage);
				} else if (savingEvEventImage != null && UtilsText.isBlank(evEventImage.getImageName())) {
					File file = new File(UtilsText.concat(Const.UPLOAD_FILE_PATH, evEventImage.getImagePathText()));
					UtilsFile.deleteQuietly(file);
					evEventImageDao.delete(evEventImage);
				}
			}
		}

		// 발급한 지류 count
		if (evEvent.getEventPblicteNoCnt() != 0
				&& UtilsText.equals(evEvent.getEventTypeCode(), CommonCode.EVENT_TYPE_CODE_NOTICE_TYPE)) {
			Long publicationNumberCount = evEventPublicationNumberDao.selectEvEventPublicationNumberCount(eventNo);
			if (publicationNumberCount == 0) {
				for (int i = 0; i < evEvent.getEventPblicteNoCnt(); i++) {
					publicationNumberCount = evEventPublicationNumberDao.selectEvEventPublicationNumberCount(null);
					EvEventPublicationNumber evEventPublicationNumber = new EvEventPublicationNumber();
					String makeRandomNumber = this.makeRandomNumber(publicationNumberCount + 1, eventNo, "event");
					evEventPublicationNumber.setEventNo(eventNo);
					evEventPublicationNumber.setEventPblicteNo(makeRandomNumber);
					evEventPublicationNumber.setRgstYn("N");
					evEventPublicationNumberDao.insert(evEventPublicationNumber);
				}
			}
		}

	}

	/**
	 * 이벤트 응모 목록 조회
	 *
	 * @Desc : 이벤트 응모 목록 조회
	 * @Method Name : getEventJoinList
	 * @Date : 2019. 3. 25
	 * @Author : easyhun
	 * @param pageable
	 * @return
	 */
	public Page<EvEventJoinResultVO> getEventJoinList(Pageable<EvEventSearchVO, EvEventJoinResultVO> pageable)
			throws Exception {

		int count = 0;
		String eventTypeCode = pageable.getBean().getEventTypeCode();
		List<EvEventJoinResultVO> result = new ArrayList<EvEventJoinResultVO>();

		if (UtilsText.equals(eventTypeCode, CommonCode.EVENT_TYPE_CODE_PARTICIPATION_TYPE_ROULETTE)) { // 룰렛
			count = evEventRouletteJoinMemberDao.selectEvEventRouletteJoinMemberCount(pageable);
			if (count > 0) {
				result = evEventRouletteJoinMemberDao.selectEvEventRouletteJoinMemberList(pageable);
			}
		} else if (UtilsText.equals(eventTypeCode, CommonCode.EVENT_TYPE_CODE_REPLY_TYPE)) { // 댓글
			count = evEventAnswerDao.selectEvEventAnswerCount(pageable);
			if (count > 0) {
				result = evEventAnswerDao.selectEvEventAnswerList(pageable);
			}
		} else if (UtilsText.equals(eventTypeCode, CommonCode.EVENT_TYPE_CODE_PARTICIPATION_TYPE_ATTENDANCE_CHECK)) { // 출석체크
			count = evEventAttendanceCheckMemberDao.selectEvEventAttendanceCheckMemberCount(pageable);
			if (count > 0) {
				result = evEventAttendanceCheckMemberDao.selectEvEventAttendanceCheckMemberList(pageable);
			}
		} else { // 일반형, 드로우
			count = evEventJoinMemberDao.selectEvEventJoinMemberCount(pageable);
			if (count > 0) {
				result = evEventJoinMemberDao.selectEvEventJoinMemberList(pageable);
			}
		}

		pageable.setTotalCount(count);
		pageable.setContent(result);
		return pageable.getPage();
	}

	/**
	 * 이벤트 응모 목록 조회
	 *
	 * @Desc : 이벤트 응모 목록 조회
	 * @Method Name : getEventJoinList
	 * @Date : 2019. 3. 25
	 * @Author : easyhun
	 * @param pageable
	 * @return
	 */
	public Page<EvEventJoinResultVO> getEventResultMemberList(Pageable<EvEventSearchVO, EvEventJoinResultVO> pageable)
			throws Exception {

		int count = 0;
		String eventTypeCode = pageable.getBean().getEventTypeCode();
		List<EvEventJoinResultVO> result = new ArrayList<EvEventJoinResultVO>();

		if (UtilsText.equals(eventTypeCode, CommonCode.EVENT_TYPE_CODE_PARTICIPATION_TYPE_ROULETTE)) { // 룰렛
			count = evEventRouletteJoinMemberDao.selectEvEventRouletteResultMemberCount(pageable);
			if (count > 0) {
				result = evEventRouletteJoinMemberDao.selectEvEventRouletteResultMemberList(pageable);
			}
		} else if (UtilsText.equals(eventTypeCode, CommonCode.EVENT_TYPE_CODE_REPLY_TYPE)) { // 댓글
			count = evEventAnswerDao.selectEvEventResultAnswerCount(pageable);
			if (count > 0) {
				result = evEventAnswerDao.selectEvEventResultAnswerList(pageable);
			}
		} else if (UtilsText.equals(eventTypeCode, CommonCode.EVENT_TYPE_CODE_PARTICIPATION_TYPE_ATTENDANCE_CHECK)) { // 출석체크
			count = evEventAttendanceCheckMemberDao.selectEvEventAttendanceResultMemberCount(pageable);
			if (count > 0) {
				result = evEventAttendanceCheckMemberDao.selectEvEventAttendanceReusltMemberList(pageable);
			}
		} else { // 일반형, 드로우
			count = evEventJoinMemberDao.selectEvEventResultMemberCount(pageable);
			if (count > 0) {
				result = evEventJoinMemberDao.selectEvEventResultMemberList(pageable);
			}
		}

		pageable.setTotalCount(count);
		pageable.setContent(result);
		return pageable.getPage();
	}

	/**
	 * 이벤트 당첨자 조회
	 *
	 * @Desc : 이벤트 당첨자 조회
	 * @Method Name : getEventResultList
	 * @Date : 2019. 4. 11
	 * @Author : easyhun
	 * @param eventNo
	 * @return
	 */
	public Page<EvEventResult> getEventResultList(Pageable<EvEventSearchVO, EvEventResult> pageable) throws Exception {

		int count = evEventResultDao.selectEventResultCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			List<EvEventResult> list = evEventResultDao.selectEventResultList(pageable);
			for (EvEventResult evEventResult : list) {
				// 상세주소 - 이스케이프로 변환되어 DB에 들어있는 데이터 가져오기
				evEventResult.setEventName(UtilsText.unescapeXss(evEventResult.getEventName()));
			}
			pageable.setContent(list);
		}

		return pageable.getPage();
	}

	/**
	 * 당첨자 관리 등록
	 *
	 * @Desc : 당첨자 관리 등록
	 * @Method Name : insertEvEventResult
	 * @Date : 2019. 4. 15
	 * @Author : easyhun
	 * @param evEventResult
	 * @return
	 */
	public void insertEvEventResult(EvEventResult evEventResult) throws Exception {
		// 세션 정보 호출
		UserDetails user = LoginManager.getUserDetails();
		evEventResult.setRgsterNo(user.getAdminNo());
		evEventResult.setModerNo(user.getAdminNo());

		String eventNo = evEventResult.getEventNo();
		String eventTypeCode = evEventResult.getEventTypeCode();

		EvEvent ev = new EvEvent();
		ev.setEventNo(eventNo);
		ev = evEventDao.selectByPrimaryKey(ev);
		evEventResult.setPblcYn(ev.getPrzwrPblcYn());
		// 당첨발표여부가 "Y" 라면
		if (UtilsText.equals(evEventResult.getPblcYn(), Const.BOOLEAN_TRUE)) {
			// 이벤트 마스터 테이블의 "당첨발표예정일" -> 이벤트 결과의 "당첨발표일"
			evEventResult.setPblcYmd(ev.getPrzwrPblcTodoYmd());
		}

		// 이벤트결과 저장(EV_EVENT_RESULT)
		log.debug("이벤트결과 저장 :EV_EVENT_RESULT = {}", eventNo);
		evEventResultDao.insert(evEventResult);

		if (UtilsText.isNotBlank(eventNo)) {
			evEventResultBenefitMemberDao.deleteByEventNo(eventNo);
			evEventResultBenefitDao.deleteByEventNo(eventNo);

			// 이벤트 결과 혜택정보
			short benefitIdx = 0;
			if (UtilsArray.isNotEmpty(evEventResult.getEvEventResultBenefitArr())) {

				for (EvEventResultBenefit evEventResultBenefit : evEventResult.getEvEventResultBenefitArr()) {
					evEventResultBenefit.setEventNo(eventNo);
					evEventResultBenefit.setEventRsltBenefitSeq(++benefitIdx);
					evEventResultBenefit.setSortSeq((int) benefitIdx);
					// 이벤트결과혜택 저장(EV_EVENT_RESULT_BENEFIT)
					log.debug("이벤트결과혜택 저장 :EV_EVENT_RESULT_BENEFIT = {}", benefitIdx);
					evEventResultBenefitDao.insert(evEventResultBenefit);

					if (UtilsArray.isNotEmpty(evEventResult.getEvEventResultBenefitMemberArr())) {

						for (EvEventResultBenefitMember evEventResultBenefitMember : evEventResult
								.getEvEventResultBenefitMemberArr()) {
							if (evEventResultBenefitMember.getEventRsltBenefitSeq() == benefitIdx) {
								evEventResultBenefitMember.setEventNo(eventNo);
								if (UtilsText.equals(evEventResult.getIndvdlRgstYn(), "N")) { // 엑셀형식
									Long evEventJoinMemberSeq = 0L;
									log.debug("엑셀형식");

									if (UtilsText.equals(eventTypeCode,
											CommonCode.EVENT_TYPE_CODE_PARTICIPATION_TYPE_ROULETTE)) { // 룰렛
										evEventJoinMemberSeq = evEventRouletteJoinMemberDao
												.selectEvEventRouletteJoinMemberSeq(
														evEventResultBenefitMember.getMemberNo(), eventNo);
									} else if (UtilsText.equals(eventTypeCode, CommonCode.EVENT_TYPE_CODE_REPLY_TYPE)) { // 댓글
										evEventJoinMemberSeq = evEventAnswerDao.selectEvEventAnswerSeq(
												evEventResultBenefitMember.getMemberNo(), eventNo);
									} else if (UtilsText.equals(eventTypeCode,
											CommonCode.EVENT_TYPE_CODE_PARTICIPATION_TYPE_ATTENDANCE_CHECK)) { // 출석체크
										evEventJoinMemberSeq = evEventAttendanceCheckMemberDao
												.selectEvEventAttendanceCheckMemberSeq(
														evEventResultBenefitMember.getMemberNo(), eventNo);
									} else { // 일반형, 드로우
										evEventJoinMemberSeq = evEventJoinMemberDao.selectEventJoinMemberSeq(
												evEventResultBenefitMember.getMemberNo(), eventNo);
									}

									log.debug("memberNo = {}, evEventJoinMemberSeq = {}",
											evEventResultBenefitMember.getMemberNo(), evEventJoinMemberSeq);
									evEventResultBenefitMember.setEvEventJoinMemberSeq(evEventJoinMemberSeq);
								}

								if (evEventResultBenefitMember.getEvEventJoinMemberSeq() != null
										&& evEventResultBenefitMember.getEvEventJoinMemberSeq() != 0L) {
									// 이벤트결과혜택회원저장(EV_EVENT_RESULT_BENEFIT_MEMBER)
									log.debug("이벤트결과혜택회원저장  EV_EVENT_RESULT_BENEFIT_MEMBER");
									evEventResultBenefitMemberDao.insert(evEventResultBenefitMember);

									if (UtilsText.equals(eventTypeCode,
											CommonCode.EVENT_TYPE_CODE_PARTICIPATION_TYPE_DRAW)
											|| UtilsText.equals(eventTypeCode,
													CommonCode.EVENT_TYPE_CODE_NOTICE_TYPE)) {
										EvEventJoinMember winMember = new EvEventJoinMember();
										winMember.setEvEventJoinMemberSeq(
												evEventResultBenefitMember.getEvEventJoinMemberSeq());
										winMember.setEventNo(eventNo);
										winMember.setWinYn("Y");
										log.debug("EvEventJoinMemberSeq = {}, winYn = {}",
												winMember.getEvEventJoinMemberSeq(), winMember.getWinYn());
										evEventJoinMemberDao.updateEvEventJoinMember(winMember);
									}
								}
							}
						}
					}
				}
			}

			/*
			 * EvEvent evEvent = new EvEvent(); evEvent.setEventNo(eventNo);
			 * evEvent.setPrzwrPblcYn(evEventResult.getPblcYn());
			 * evEventDao.update(evEvent);
			 */
		}

	}

	/**
	 * 당첨자 관리 등록(보류)
	 *
	 * @Desc : 당첨자 관리 수정
	 * @Method Name : updateEvEventResult
	 * @Date : 2019. 4. 15
	 * @Author : easyhun
	 * @param evEventResult
	 * @return
	 */
	public void updateEvEventResult(EvEventResult evEventResult) throws Exception {
		// 세션 정보 호출
		UserDetails user = LoginManager.getUserDetails();
		evEventResult.setModerNo(user.getAdminNo());
		evEventResult.setModDtm(new Timestamp(new Date().getTime()));

		String eventNo = evEventResult.getEventNo();
		String eventTypeCode = evEventResult.getEventTypeCode();

		EvEvent evEvent = new EvEvent();
		evEvent.setEventNo(eventNo);
		evEvent = evEventDao.selectEvEvent(evEvent);
		if (UtilsText.equals(evEvent.getPrzwrPblcYn(), "Y") && evEvent.getPrzwrPblcTodoYmd() != null) {
			Date przwrPblcTodoYmd = new Timestamp(evEvent.getPrzwrPblcTodoYmd().getTime());
			Date today = new Timestamp(new Date().getTime());

			if (przwrPblcTodoYmd.getTime() <= today.getTime()) {
				throw new Exception("발표일이 지난 경우 수정하실 수 없습니다.");
			}
		}

		evEventResultDao.updateEventResult(evEventResult);

		if (UtilsText.isNotBlank(eventNo)) {
			evEventResultBenefitMemberDao.deleteByEventNo(eventNo);
			evEventResultBenefitDao.deleteByEventNo(eventNo);

			// 이벤트 결과 혜택정보
			short benefitIdx = 0;
			if (UtilsArray.isNotEmpty(evEventResult.getEvEventResultBenefitArr())) {
				// 이전당첨자를 다시 원복
				EvEventJoinMember beForeWinMember = new EvEventJoinMember();
				beForeWinMember.setEventNo(eventNo);
				beForeWinMember.setWinYn("N");
				evEventJoinMemberDao.updateWinYnByEventNo(beForeWinMember);

				for (EvEventResultBenefit evEventResultBenefit : evEventResult.getEvEventResultBenefitArr()) {
					evEventResultBenefit.setEventNo(eventNo);
					evEventResultBenefit.setEventRsltBenefitSeq(++benefitIdx);
					evEventResultBenefit.setSortSeq((int) benefitIdx);
					// 이벤트결과혜택 저장(EV_EVENT_RESULT_BENEFIT)
					log.debug("이벤트결과혜택 저장 :EV_EVENT_RESULT_BENEFIT = {}", benefitIdx);
					evEventResultBenefitDao.insert(evEventResultBenefit);

					if (UtilsArray.isNotEmpty(evEventResult.getEvEventResultBenefitMemberArr())) {

						for (EvEventResultBenefitMember evEventResultBenefitMember : evEventResult
								.getEvEventResultBenefitMemberArr()) {
							if (evEventResultBenefitMember.getEventRsltBenefitSeq() == benefitIdx) {
								evEventResultBenefitMember.setEventNo(eventNo);
								if (UtilsText.equals(evEventResult.getIndvdlRgstYn(), "N")) { // 엑셀형식
									Long evEventJoinMemberSeq = 0L;
									log.debug("엑셀형식");

									if (UtilsText.equals(eventTypeCode,
											CommonCode.EVENT_TYPE_CODE_PARTICIPATION_TYPE_ROULETTE)) { // 룰렛
										evEventJoinMemberSeq = evEventRouletteJoinMemberDao
												.selectEvEventRouletteJoinMemberSeq(
														evEventResultBenefitMember.getMemberNo(), eventNo);
									} else if (UtilsText.equals(eventTypeCode, CommonCode.EVENT_TYPE_CODE_REPLY_TYPE)) { // 댓글
										evEventJoinMemberSeq = evEventAnswerDao.selectEvEventAnswerSeq(
												evEventResultBenefitMember.getMemberNo(), eventNo);
									} else if (UtilsText.equals(eventTypeCode,
											CommonCode.EVENT_TYPE_CODE_PARTICIPATION_TYPE_ATTENDANCE_CHECK)) { // 출석체크
										evEventJoinMemberSeq = evEventAttendanceCheckMemberDao
												.selectEvEventAttendanceCheckMemberSeq(
														evEventResultBenefitMember.getMemberNo(), eventNo);
									} else { // 일반형, 드로우
										evEventJoinMemberSeq = evEventJoinMemberDao.selectEventJoinMemberSeq(
												evEventResultBenefitMember.getMemberNo(), eventNo);
									}

									log.debug("memberNo = {}, evEventJoinMemberSeq = {}",
											evEventResultBenefitMember.getMemberNo(), evEventJoinMemberSeq);
									evEventResultBenefitMember.setEvEventJoinMemberSeq(evEventJoinMemberSeq);
								}

								if (evEventResultBenefitMember.getEvEventJoinMemberSeq() != null
										&& evEventResultBenefitMember.getEvEventJoinMemberSeq() != 0L) {
									log.debug("이벤트결과혜택회원저장  EV_EVENT_RESULT_BENEFIT_MEMBER");
									evEventResultBenefitMemberDao.insert(evEventResultBenefitMember);

									if (UtilsText.equals(eventTypeCode,
											CommonCode.EVENT_TYPE_CODE_PARTICIPATION_TYPE_DRAW)
											|| UtilsText.equals(eventTypeCode,
													CommonCode.EVENT_TYPE_CODE_NOTICE_TYPE)) {
										EvEventJoinMember winMember = new EvEventJoinMember();
										winMember.setEvEventJoinMemberSeq(
												evEventResultBenefitMember.getEvEventJoinMemberSeq());
										winMember.setEventNo(eventNo);
										winMember.setWinYn("Y");
										log.debug("EvEventJoinMemberSeq = {}, winYn = {}",
												winMember.getEvEventJoinMemberSeq(), winMember.getWinYn());
										evEventJoinMemberDao.updateEvEventJoinMember(winMember); // 당첨됐다가 취소 됏을경우 ?
									}
								}
							}
						}
					}
				}
			}

			/*
			 * EvEvent evEvent = new EvEvent(); evEvent.setEventNo(eventNo);
			 * evEvent.setPrzwrPblcYn(evEventResult.getPblcYn());
			 * evEventDao.update(evEvent);
			 */
		}
	}

	/**
	 * 이벤트 당첨자 헤택 회원 조회
	 *
	 * @Desc : 이벤트 당첨자 헤택 회원 조회
	 * @Method Name : getEvEventResultBenefitListByEventNo
	 * @Date : 2019. 4. 11
	 * @Author : easyhun
	 * @param eventNo
	 * @return
	 */
	public List<EvEventResultBenefit> getEvEventResultBenefitListByEventNo(String eventNo) throws Exception {

		return evEventResultBenefitDao.selectEvEventResultBenefitListByEventNo(eventNo);
	}

	/**
	 * 이벤트 당첨자 상세 조회
	 *
	 * @Desc : 이벤트 당첨자 상세 조회
	 * @Method Name : getEventResult
	 * @Date : 2019. 4. 11
	 * @Author : easyhun
	 * @param EvEventResult
	 * @return
	 */

	public EvEventResult getEventResult(EvEventResult evEventResult) throws Exception {

		return evEventResultDao.selectEventResult(evEventResult);
	}

	/**
	 * 이벤트 댓글 수정
	 *
	 * @Desc : 이벤트 댓글 수정
	 * @Method Name : updateEvEventAnswer
	 * @Date : 2019. 6. 7
	 * @Author : easyhun
	 * @param evEventResult
	 * @return
	 */
	public void updateEvEventAnswer(EvEventAnswer evEventAnswer) throws Exception {
		// 세션 정보 호출
		UserDetails user = LoginManager.getUserDetails();
		evEventAnswer.setModerNo(user.getAdminNo());
		evEventAnswer.setModDtm(new Timestamp(new Date().getTime()));

		evEventAnswerDao.updateEvEventAnswer(evEventAnswer);
	}

	/**
	 * 이벤트 룰렛 수정
	 *
	 * @Desc : 이벤트 룰렛 수정
	 * @Method Name : updateEvEventRouletteJoinMember
	 * @Date : 2019. 8. 6
	 * @Author : easyhun
	 * @param evEventResult
	 * @return
	 */
	public void updateEvEventRouletteJoinMember(EvEventRouletteJoinMember evEventRouletteJoinMember) throws Exception {
		// 세션 정보 호출
		UserDetails user = LoginManager.getUserDetails();
		evEventRouletteJoinMember.setModerNo(user.getAdminNo());
		evEventRouletteJoinMember.setModDtm(new Timestamp(new Date().getTime()));
		evEventRouletteJoinMember.setIssueDtm(new Timestamp(new Date().getTime()));

		evEventRouletteJoinMemberDao.updateEvEventRouletteJoinMember(evEventRouletteJoinMember);
	}

	public Map<String, Object> uploadImageByEditor(FileUpload upload) throws Exception {

		Map<String, Object> result = new HashMap<>();

		String fileName = upload.getOrgFileName();
		String uploadFileName = UtilsText.concat(String.valueOf(System.currentTimeMillis()), ".",
				FilenameUtils.getExtension(fileName));
		String path = UtilsText.concat(FILE_PATH, "/", UtilsDate.today(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS));

		try {
			UtilsSftp.upload(path, uploadFileName, upload.getMultiPartFile().getInputStream());
		} catch (Exception e) {
			log.error("{}", e);
			throw new Exception("파일저장에 실패하였습니다.");
		}

		result.put("uploaded", 1);
		result.put("url", UtilsText.concat(Const.URL_IMG_HTTPS, "/", path, "/", uploadFileName));
		result.put("fileName", fileName);

		return result;
	}

	/**
	 * @Desc : 로그인 아이디에 의한 회원 정보
	 * @Method Name : getMemberByLoginId
	 * @Date : 2019. 6. 10.
	 * @Author : 이지훈
	 * @param String
	 */
	public String getMemberNoByLoginId(String loginId, String eventNo, String eventTypeCode) throws Exception {
		String memberNo = "";
		if (UtilsText.equals(eventTypeCode, CommonCode.EVENT_TYPE_CODE_PARTICIPATION_TYPE_ROULETTE)) { // 룰렛
			memberNo = evEventRouletteJoinMemberDao.selectMemberNoByLoginId(loginId, eventNo);
		} else if (UtilsText.equals(eventTypeCode, CommonCode.EVENT_TYPE_CODE_REPLY_TYPE)) { // 댓글
			memberNo = evEventAnswerDao.selectMemberNoByLoginId(loginId, eventNo);
		} else if (UtilsText.equals(eventTypeCode, CommonCode.EVENT_TYPE_CODE_PARTICIPATION_TYPE_ATTENDANCE_CHECK)) { // 출석체크
			memberNo = evEventAttendanceCheckMemberDao.selectMemberNoByLoginId(loginId, eventNo);
		} else { // 일반형, 드로우
			memberNo = evEventJoinMemberDao.selectMemberNoByLoginId(loginId, eventNo);
		}

		return memberNo;
	}

	/**
	 * @Desc : 지류번호 생성
	 * @Method Name : makeRandomNumber
	 * @Date : 2019. 6. 11.
	 * @Author : 이지훈
	 * @param String pkNumber, String type
	 */
	public String makeRandomNumber(Long numberCount, String pkNumber, String type) {
		String hashNum = String.format("%011d", numberCount);
		int hash = hashNum.hashCode();
//		String hashStr = String.valueOf(hash);
//		int last2Num = Integer.parseInt(hashStr.substring(2, hashStr.length()));
//		int pkNumberInt = Integer.parseInt(pkNumber);

		String prefixValue = "";
		if (UtilsText.equals(type, "coupon"))
			prefixValue = "AC";
		else
			prefixValue = "EV";

		// 쿠폰 난수번호 10자리 > 'AC' + 8 자리로 개편 (2015년 12월 28일 매장포스 바코드 인식을 위해)
		// long randomNumberDecimalNumber = (hash * 10000L + last2Num * last2Num) * 7 +
		// 400000000000000L + cpnIdInt;
		// String changedCouponNumber =
		// StringUtils.upperCase(Long.toString(randomNumberDecimalNumber, 36));
		// as-is 방식 그대로 옮겨옴
		// as-is prefixValue AC to-be PR, EV

		long randomNumberDecimalNumber = (hash * 20171122) * 7 + 201711221305L;

//		long randomNumberDecimalNumber = (hash * 1L + last2Num * last2Num) * 7 + 400000000000L + pkNumberInt;
		String changedCouponNumber = prefixValue + StringUtils.upperCase(Long.toString(randomNumberDecimalNumber, 36));

		return changedCouponNumber;
	}

	/**
	 * @Desc : 회원 참여 이벤트 건수 조회(최근 2개월)
	 * @Method Name : getEventJoinMemberCount
	 * @Date : 2019. 6. 25.
	 * @Author : 이지훈
	 * @param evEventJoinMember
	 * @return
	 */
	public Map<String, Object> getEventJoinMemberCount(EvEventJoinMember evEventJoinMember) throws Exception {
		Map<String, Object> map = new HashMap<>();
		evEventJoinMember.setRecentYn("Y");
		evEventJoinMember.setWinYn("N");
		map.put("joinCount", evEventJoinMemberDao.selectEventJoinMemberCount(evEventJoinMember));
		evEventJoinMember.setWinYn("Y");
		map.put("winCount", evEventJoinMemberDao.selectEventJoinMemberCount(evEventJoinMember));

		return map;
	}

	/**
	 * 이벤트 발행 조회
	 *
	 * @Desc : 이벤트 발행 조회
	 * @Method Name : getEvEventPublicationNumberList
	 * @Date : 2019. 7. 18
	 * @Author : easyhun
	 * @param eventNo
	 * @return
	 */
	public List<EvEventPublicationNumber> getEvEventPublicationNumberList(String eventNo) throws Exception {

		return evEventPublicationNumberDao.selectEvEventPublicationNumberListByEventNo(eventNo);
	}

	/**
	 * 이벤트 리스트 조회(회원참여)
	 *
	 * @Desc : 이벤트 리스트 조회(회원참여)
	 * @Method Name : getMemberHistoryEventList
	 * @Date : 2019. 7. 24
	 * @Author : easyhun
	 * @param pageable
	 * @return
	 */
	public Page<EvEvent> getMemberHistoryEventList(Pageable<EvEventSearchVO, EvEvent> pageable) throws Exception {

		int count = evEventDao.selectMemberHistoryEventCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(evEventDao.selectMemberHistoryEventList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * 이벤트 결과 이벤트번호 중복체크 조회
	 *
	 * @Desc : 이벤트 결과 이벤트번호 중복체크 조회
	 * @Method Name : getEventDuplCheck
	 * @Date : 2019. 7. 29
	 * @Author : easyhun
	 * @param eventNo
	 * @return
	 * @throws Exception
	 */
	public boolean getEventResultDuplCheck(String eventNo) throws Exception {
		int count = evEventResultDao.selectEventResultDuplCheck(eventNo);
		boolean duplCheckVal = true;
		if (count > 0)
			duplCheckVal = false;

		return duplCheckVal;
	}

	/**
	 * @Desc : 이벤트 미리보기
	 * @Method Name : getUrl
	 * @Date : 2019. 8. 19.
	 * @param eventNo, deviceCode
	 * @return String
	 * @throws Exception
	 */
	public String getUrl(String deviceCode) throws Exception {
		String uri = "/promotion/event/detail";
		String domain = "";

		switch (deviceCode) {
		case CommonCode.DEVICE_PC:
			domain = Config.getString("service.domain.art.fo");
			break;
		case CommonCode.DEVICE_MOBILE:
			domain = Config.getString("service.domain.art.mo");
			break;
		default:
			log.debug("알 수 없는 유형입니다. deviceCode : {}", deviceCode);
		}

		return UtilsText.concat(domain, uri);
	}

	/**
	 * 진행중인 드로우 상품 중복 체크
	 *
	 * @Desc : 진행중인 드로우 상품 중복 체크
	 * @Method Name : getEventDrawDuplCheck
	 * @Date : 2019. 9. 06
	 * @Author : easyhun
	 * @param prdtNo
	 * @return
	 * @throws Exception
	 */
	public boolean getEventDrawDuplCheck(String prdtNo, String eventNo) throws Exception {
		int count = evEventTargetProductDao.selectEventDrawDuplCheck(prdtNo, eventNo);
		boolean duplCheckVal = true;
		if (count > 0)
			duplCheckVal = false;

		return duplCheckVal;
	}

}
