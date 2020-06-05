package kr.co.abcmart.bo.cmm.service;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.cmm.model.master.CmPushMessage;
import kr.co.abcmart.bo.cmm.model.master.CmPushSendHistory;
import kr.co.abcmart.bo.cmm.repository.master.CmPushMessageDao;
import kr.co.abcmart.bo.member.model.master.MbMember;
import kr.co.abcmart.bo.member.service.MemberService;
import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.bo.system.model.master.SySite;
import kr.co.abcmart.bo.system.service.CommonCodeService;
import kr.co.abcmart.bo.system.service.SiteService;
import kr.co.abcmart.common.excel.ExcelReader;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.FileUpload;
import kr.co.abcmart.common.util.UtilsSftp;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.interfaces.module.fcm.FCMService;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.util.UtilsDate;
import kr.co.abcmart.util.UtilsText;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CmPushMessageService {

	@Autowired
	CmPushMessageService cmPushMessageService;

	@Autowired
	private CmPushMessageDao cmPushMessageDao;

	@Autowired
	private CommonCodeService commonCodeService;

	@Autowired
	private SiteService siteService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private FCMService fcmService;

	private static final String FILE_PATH = UtilsText.concat(Const.UPLOAD_FILE_PATH, Const.IMG_SRC_APP_PUSH,
			UtilsDate.today(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS));

	public static final int CHUNK_SIZE = 100; // APP PUSH는 한번에 100개씩만 가능
	public static final int EXCELUPLOAD_SIZE = 1000;

	/**
	 * @Desc : app push 메시지 정보 저장.
	 * @Method Name : createPushMsg
	 * @Date : 2019. 5. 9.
	 * @Author : 이재렬
	 * @param cmPushMessage
	 * @throws Exception
	 */
	public Map<String, Object> createPushMsg(CmPushMessage cmPushMessage) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Const.DEFAULT_DATE_PATTERN_NOT_CHARACTERS);
		String todayDate = simpleDateFormat.format(System.currentTimeMillis());
		String appPushSeq = todayDate.concat(String.format("%02d", cmPushMessageDao.appPushSeq(todayDate) + 1));
		int rtnVal = 0;

		// 최초 메시지 등록 기본세팅.
		cmPushMessage.setPushMesgNo(appPushSeq);
		cmPushMessage.setRgsterNo(LoginManager.getUserDetails().getAdminNo());
		cmPushMessage.setModerNo(LoginManager.getUserDetails().getAdminNo());
		cmPushMessage.setSendTrgtMemberCount(0);
		cmPushMessage.setPushSendCount(0);
		cmPushMessage.setPushIngStatCode(CommonCode.PUSH_ING_STAT_CODE_READY);

		// 이미지 파일이 있을때
		if (cmPushMessage.getImageFile().isFileItem()) {
			Map<String, String> map = cmPushMessageService.uploadFile(cmPushMessage.getImageFile());
			cmPushMessage.setImageName(map.get("name"));
			cmPushMessage.setImagePathText(map.get("path"));
			cmPushMessage.setImageUrl(map.get("url"));
		}

		rtnVal = cmPushMessageDao.insertAppPush(cmPushMessage);

		resultMap.put("message", (rtnVal >= 1 ? "메시지 저장에 성공하였습니다." : "메시지 저장에 실패하였습니다."));
		resultMap.put("pushMesgNo", cmPushMessage.getPushMesgNo());

		return resultMap;
	}

	/**
	 * @Desc : app push관리 진행상태 코드 검색.
	 * @Method Name : SyCodeDetail
	 * @Date : 2019. 5. 15.
	 * @Author : 이재렬
	 * @param codeField
	 * @throws Exception
	 */
	public List<SyCodeDetail> pushIngStatCode(String codeField) throws Exception {
		SyCodeDetail codeDetail = new SyCodeDetail();
		codeDetail.setCodeField(codeField);

		return commonCodeService.getCodeDtlInfoList(codeDetail);
	}

	/**
	 * @Desc : app push관리 사이트 코드 검색.
	 * @Method Name : pushSiteCode
	 * @Date : 2019. 6. 11.
	 * @Author : 고웅환
	 * @param codeField
	 * @throws Exception
	 */
	public List<SySite> pushSiteCode() throws Exception {
		return siteService.getSiteList();
	}

	/**
	 * @Desc : app push관리 메시지 목록 조회.
	 * @Method Name : appPushReadList
	 * @Date : 2019. 5. 16.
	 * @Author : 이재렬
	 * @param pageable
	 * @throws Exception
	 */
	public Page<CmPushMessage> appPushReadList(Pageable<CmPushMessage, CmPushMessage> pageable) throws Exception {
		int count = cmPushMessageDao.appPushTotalCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			List<CmPushMessage> resultList = cmPushMessageDao.appPushReadList(pageable);

			pageable.setContent(resultList);
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : app push관리 메시지 상세보기.
	 * @Method Name : selectReadDetailAppPush
	 * @Date : 2019. 5. 19.
	 * @Author : 이재렬
	 * @param pushMesgNo
	 * @throws Exception
	 */
	public CmPushMessage readDetailAppPush(String pushMesgNo) throws Exception {
		CmPushMessage cmPushMessage = cmPushMessageDao.selectReadDetailAppPush(pushMesgNo);

		if (UtilsText.equals(CommonCode.CM_PUSH_MESSAGE_SEND_TYPE_R, cmPushMessage.getSendType())
				&& cmPushMessage.getSendIrDtm() != null) {
			String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(cmPushMessage.getSendIrDtm());
			String[] times = timeStamp.replace(".", "-").split("-");

			cmPushMessage.setPushDate(UtilsText.concat(times[0], ".", times[1], ".", times[2]));
			cmPushMessage.setPushHours(times[3]);
			cmPushMessage.setPushMin(times[4]);
		}

		return cmPushMessage;
	}

	/**
	 * @Desc : 상세보기 + 사이트 목록 가져오기
	 * @Method Name : readDetailAppPushForm
	 * @Date : 2019. 8. 6.
	 * @Author : 신인철
	 * @param pushMesgNo
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> readDetailAppPushForm(String pushMesgNo) throws Exception {
		Map<String, Object> rsMap = new HashMap<>();
		CmPushMessage cmPushMessage = cmPushMessageDao.selectReadDetailAppPush(pushMesgNo);

		if (UtilsText.equals(CommonCode.CM_PUSH_MESSAGE_SEND_TYPE_R, cmPushMessage.getSendType())
				&& cmPushMessage.getSendIrDtm() != null) {
			String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(cmPushMessage.getSendIrDtm());
			String[] times = timeStamp.replace(".", "-").split("-");

			cmPushMessage.setPushDate(UtilsText.concat(times[0], ".", times[1], ".", times[2]));
			cmPushMessage.setPushHours(times[3]);
			cmPushMessage.setPushMin(times[4]);
		}

		List<SySite> siteList = siteService.getSiteList();

		rsMap.put("cmPushMessage", cmPushMessage);
		rsMap.put("siteList", siteList);

		return rsMap;
	}

	/**
	 * @Desc : app push 메시지 수정.
	 * @Method Name : modifyAppPush
	 * @Date : 2019. 5. 21.
	 * @Author : 이재렬
	 * @param cmPushMessage
	 * @throws Exception
	 */
	public HashMap<String, Object> modifyAppPush(CmPushMessage cmPushMessage) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		String prevImagePath = "";
		int rtnVal = 0;

		cmPushMessage.setModerNo(LoginManager.getUserDetails().getAdminNo());

		// 파일 변경이 이루어졌을때
		if (UtilsText.equals(Const.BOOLEAN_TRUE, cmPushMessage.getIsFileChange())) {
			prevImagePath = cmPushMessage.getImagePathText();
			cmPushMessage.setImagePathText(null);

			// 이미지 파일이 있을때
			if (cmPushMessage.getImageFile().isFileItem()) {
				Map<String, String> map = cmPushMessageService.uploadFile(cmPushMessage.getImageFile());
				cmPushMessage.setImageName(map.get("name"));
				cmPushMessage.setImagePathText(map.get("path"));
				cmPushMessage.setImageUrl(map.get("url"));
			}

			// 이미지 서버 파일 삭제 및 DB값 수정
			cmPushMessageService.deleteFile(UtilsText.concat(Const.UPLOAD_FILE_PATH, prevImagePath));

		}

		rtnVal = cmPushMessageDao.updateAppPush(cmPushMessage);

		resultMap.put("message", (rtnVal >= 1 ? "메시지 수정에 성공하였습니다." : "메시지 수정에 실패하였습니다."));

		return resultMap;
	}

	/**
	 * @Desc : app push 파일 업로드
	 * @Method Name : uploadFile
	 * @Date : 2019. 6. 12.
	 * @Author : 고웅환
	 * @param cmPushMessage
	 * @throws Exception
	 */
	public Map<String, String> uploadFile(FileUpload uploadFile) throws Exception {
		Map<String, String> map = new HashMap<>();
		try {
			String fileName = "";
			String uploadFileName = "";
			String path = "";
			String url = "";

			// 업로드한 파일 파일명
			fileName = uploadFile.getOrgFileName();
			// DB에 저장될 파일 파일명
			uploadFileName = UtilsText.concat(String.valueOf(System.currentTimeMillis()), ".",
					FilenameUtils.getExtension(fileName));
			// DB에 저장될 이미지 서버 경로
			path = UtilsText.concat(FILE_PATH, "/", uploadFileName);
			// DB에 저장될 이미지 URL
			url = UtilsText.concat(Const.URL_IMG_HTTPS, path);

			// 이미지 서버에 저장
			UtilsSftp.upload(FILE_PATH, uploadFileName, uploadFile.getMultiPartFile().getInputStream());

			map.put("name", fileName);
			map.put("path", path);
			map.put("url", url);

		} catch (ArrayIndexOutOfBoundsException arrayIndexException) {
			log.error(arrayIndexException.toString());
			arrayIndexException.printStackTrace();
			throw new Exception("파일 형식을 확인해주세요.");
		} catch (Exception e) {
			log.error(e.toString());
			e.printStackTrace();
			throw new Exception("파일 업로드에 실패했습니다.");
		}

		return map;
	}

	/**
	 * @Desc : app push 파일 삭제
	 * @Method Name : deleteFile
	 * @Date : 2019. 6. 25.
	 * @Author : 고웅환
	 * @param cmPushMessage
	 * @throws Exception
	 */
	public Map<String, String> deleteFile(String imagePathText) throws Exception {
		Map<String, String> map = new HashMap<>();
		try {
			File deleFile = new File(imagePathText);
			deleFile.delete();

		} catch (Exception e) {
			log.error(e.toString());
			e.printStackTrace();
		}

		return map;
	}

	/**
	 * @Desc : App Push 메시지 상세 정보를 가져온다.
	 * @Method Name : getMessageDetail
	 * @Date : 2019. 6. 20.
	 * @Author : 고웅환
	 * @param cmPushMessage
	 * @throws Exception
	 */
	public Map<String, Object> getMessageDetail(String messageNo) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> historyMap = new HashMap<>();

		int successCount = 0;
		int failCount = 0;

		CmPushMessage cmPushMessage = cmPushMessageService.readDetailAppPush(messageNo);
		List<CmPushSendHistory> historyList = cmPushMessageDao.getPushSendCountFromHistory(messageNo);

		// 전송이력 성공/실패 갯수
		for (CmPushSendHistory list : historyList) {
			if (UtilsText.equals(Const.SEND_STAT_TYPE_SUCCESS, list.getSendStatType())) {
				successCount = list.getSendCount();
			} else if (UtilsText.equals(Const.SEND_STAT_TYPE_FAIL, list.getSendStatType())) {
				failCount = list.getSendCount();
			}
		}

		historyMap.put("successCount", successCount);
		historyMap.put("failCount", failCount);

		resultMap.put("trgtCodeList", commonCodeService.getCodeNoName(CommonCode.PUSH_SEND_TRGT_CODE));
		resultMap.put("messageDetail", cmPushMessage);
		resultMap.put("messageHistory", historyMap);

		return resultMap;
	}

	/**
	 * @Desc : 테스트 발송 대상자 회원 조회
	 * @Method Name : getMessageDetail
	 * @Date : 2019. 6. 20.
	 * @Author : 고웅환
	 * @param cmPushMessage
	 * @throws Exception
	 */
	public List<CmPushMessage> sendTestTargetMemberList(CmPushMessage cmPushMessage) throws Exception {
		List<CmPushMessage> excelList = new ArrayList<CmPushMessage>();
		List<CmPushMessage> targetList = new ArrayList<CmPushMessage>();
		if (UtilsText.equals(CommonCode.PUSH_ING_STAT_CODE_READY, cmPushMessage.getPushIngStatCode())) {
			// 발송준비 상태일때만 대상자 삭제
			cmPushMessageDao.deleteTargetMember(cmPushMessage);
		}

		try {
			if (cmPushMessage.getExcelFile() != null) {
				if (cmPushMessage.getExcelFile().isFileItem()) {
					// excel파일에서 회원 추출 후 target_member table에 insert
					excelList = cmPushMessageService.getPushTargetUploadExcel(cmPushMessage);

					if (excelList.size() > 10) {
						throw new Exception("테스트발송은 10명까지만 가능합니다.");
					}

					// 회원ID로 회원정보가 존재하는 회원만 정보를 조회해온다
					excelList = cmPushMessageService.getMemberInfo(excelList);

					if (excelList.isEmpty()) {
						throw new Exception("유효한 회원정보를 가진 회원이 없습니다.");
					}

					// 푸시 대상회원 테이블에 insert
					cmPushMessageDao.insertTargetMember(excelList);
				}
			}

			cmPushMessage.setSendNum(1);
			cmPushMessage.setSendPerOnce(10);
			// target_member table과 app_download 테이블과 조인하여 유효한 회원들 추출
			targetList = cmPushMessageDao.getPushTargetMemberDeviceTokenTextList(cmPushMessage);

		} catch (DuplicateKeyException dke) {
			log.error(dke.toString());
			dke.printStackTrace();
			// 대상자 삭제
			cmPushMessageDao.deleteTargetMember(cmPushMessage);
			// 엑셀 파일에 회원번호가 중복되어 있는 경우
			throw new Exception("중복되는 회원번호가 있습니다.");
		} catch (DataIntegrityViolationException dive) {
			log.error(dive.toString());
			dive.printStackTrace();
			throw new Exception("엑셀파일 회원번호 자리수를 확인해주세요.");
		} catch (Exception e) {
			log.error(e.toString());
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}

		return targetList;
	}

	/**
	 * @Desc : 일반발송 대상별 대상회원 조회.
	 * @Method Name : sendTargetMemberList
	 * @Date : 2019. 6. 27.
	 * @Author : 고웅환
	 * @param cmPushMessage
	 * @throws Exception
	 */
	public Map<String, Object> sendTargetMemberList(CmPushMessage cmPushMessage) throws Exception {
		Map<String, Object> map = new HashMap<>();
		List<CmPushMessage> excelList = new ArrayList<CmPushMessage>();
		if (UtilsText.equals(CommonCode.PUSH_ING_STAT_CODE_READY, cmPushMessage.getPushIngStatCode())) {
			// 발송준비 상태일때만 대상자 삭제
			cmPushMessageDao.deleteTargetMember(cmPushMessage);
		}

		// 발송대상이 파일업로드일때
		if (UtilsText.equals(CommonCode.PUSH_SEND_TRGT_CODE_FILEUPLOAD, cmPushMessage.getPushSendTrgtCode())) {
			if (cmPushMessage.getExcelFile() != null) {
				if (cmPushMessage.getExcelFile().isFileItem()) {
					try {
						excelList = cmPushMessageService.getPushTargetUploadExcel(cmPushMessage);

						int pageNum = 0;
						pageNum = ((int) Math.ceil((double) excelList.size() / (double) EXCELUPLOAD_SIZE));
						log.debug("pageNum = {}", pageNum);
						int fromIndex = 0;
						int toIndex = 0;
						for (int i = 1; i <= pageNum; i++) {

							if (i == pageNum || pageNum == 1) {
								toIndex = excelList.size();
							} else {
								toIndex = fromIndex + EXCELUPLOAD_SIZE;
							}
							log.debug("fromIndex ={}, fromIndex = {}, excelList.size = {}", fromIndex, toIndex,
									excelList.size());
							if (fromIndex == toIndex) {
								log.error("sendTargetMemberList excel upload error fromIndex = {} , toIndex = {} ",
										fromIndex, toIndex);
								break;
							} else if (toIndex > excelList.size()) {
								log.error("sendTargetMemberList excel upload error excelList.size = {} , toIndex = {} ",
										excelList.size(), toIndex);
								break;
							}
							List<CmPushMessage> dataList = excelList.subList(fromIndex, toIndex);

							// 회원ID로 회원정보가 존재하는 회원만 정보를 조회해온다
							dataList = cmPushMessageService.getMemberInfo(dataList);

							if (dataList.isEmpty()) {
								throw new Exception("유효한 회원정보를 가진 회원이 없습니다.");
							}

							// 푸시 대상회원 테이블에 insert
							cmPushMessageDao.insertTargetMember(dataList);

							fromIndex = toIndex;

						}

					} catch (DuplicateKeyException dke) {
						log.error("DuplicateKeyException error = {}", dke);
						// 대상자 삭제
						cmPushMessageDao.deleteTargetMember(cmPushMessage);
						// 엑셀 파일에 회원번호가 중복되어 있는 경우
						throw new Exception("중복되는 회원번호가 있습니다.");

					} catch (Exception e) {
						log.error("sendTargetMemberList Exception = {}", e);
						throw new Exception(e.getMessage());
					}
				}
			}

			map.put("targetCount", cmPushMessageDao.getPushTargetMemberDeviceTokenTextListCount(cmPushMessage));
		} else {
			// 발송대상이 app 설치회원 또는 전체일 경우
			map.put("targetCount", cmPushMessageDao.getPushAppDownloarMemberDeviceTokenTextListCount(cmPushMessage));
		}

		return map;
	}

	/**
	 * @Desc : App Push 지정사용자(파일업로드) 엑셀업로드
	 * @Method Name : getPushTargetUpload
	 * @Date : 2019. 6. 26.
	 * @Author : 고웅환
	 * @param cmPushMessage
	 * @throws Exception
	 */
	public List<CmPushMessage> getPushTargetUploadExcel(CmPushMessage cmPushMessage) throws Exception {
		List<CmPushMessage> excelList = new ArrayList<CmPushMessage>();

		ExcelReader reader = ExcelReader.builder(1).converter((Sheet sheet, int rowNum, String[] row) -> {
			if (UtilsText.equals(Const.PUSH_SEND_GBN_TYPE_TEST, cmPushMessage.getSendGbnType()) && rowNum > 11) {
				return null;
			}
			CmPushMessage memberVO = new CmPushMessage();

			// 회원ID VARCHAR(20)
			String loginId = row[0].length() > 20 ? row[0].substring(0, 20) : row[0];
			memberVO.setLoginId(UtilsText.trim(loginId));
			memberVO.setPushMesgNo(cmPushMessage.getPushMesgNo());

			return memberVO;

		}).build();

		excelList = reader.read(cmPushMessage.getExcelFile().getMultiPartFile().getInputStream());

		return excelList;
	}

	/**
	 * @Desc : 발송/예약발송
	 * @Method Name : sendAppPush
	 * @Date : 2019. 7. 3.
	 * @Author : 고웅환
	 * @param cmPushMessage
	 * @throws Exception
	 */
	public Map<String, Object> sendAppPush(CmPushMessage cmPushMessage) throws Exception {
		Map<String, Object> map = new HashMap<>();
		String resultMsg = "";

		cmPushMessage.setPushIngStatCode(CommonCode.PUSH_ING_STAT_CODE_WAITING);
		cmPushMessage.setModerNo(LoginManager.getUserDetails().getAdminNo());

		// 발송형태를 예약과 즉시로 구분하여 예약발송의 경우에는 테스트/일반 하나로 가고, 즉시 발송일 경우에만 테스트|일반 으로 한번 더 구분한다.
		if (UtilsText.equals(CommonCode.CM_PUSH_MESSAGE_SEND_TYPE_R, cmPushMessage.getSendType())) {
			resultMsg = cmPushMessageService.sendAppPushReservation(cmPushMessage);
		} else {
			if (UtilsText.equals(Const.PUSH_SEND_GBN_TYPE_TEST, cmPushMessage.getSendGbnType())) {
				resultMsg = cmPushMessageService.sendTestAppPushImmediately(cmPushMessage);
			} else {
				resultMsg = cmPushMessageService.sendRegularAppPushImmediately(cmPushMessage);
			}
		}

		map.put("message", resultMsg);

		return map;
	}

	/**
	 * @Desc : 발송 취소
	 * @Method Name : cancelAppPush
	 * @Date : 2019. 7. 24.
	 * @Author : 고웅환
	 * @param pushMesgNo
	 * @throws Exception
	 */
	public Map<String, Object> cancelAppPush(CmPushMessage cmPushMessage) throws Exception {
		Map<String, Object> map = new HashMap<>();
		int rtnVal = 0;

		cmPushMessage.setPushIngStatCode(CommonCode.PUSH_ING_STAT_CODE_READY);
		cmPushMessage.setModerNo(LoginManager.getUserDetails().getAdminNo());
		cmPushMessage.setSendTrgtMemberCount(0);

		rtnVal = cmPushMessageDao.updateCanselAppPush(cmPushMessage);
		map.put("message", rtnVal >= 1 ? "발송취소에  성공하였습니다." : "발송취소에 실패하였습니다.");

		return map;
	}

	/**
	 * @Desc : 테스트 즉시 발송
	 * @Method Name : sendAppPushMessage
	 * @Date : 2019. 6. 26.
	 * @Author : 고웅환
	 * @param cmPushMessage
	 * @throws Exception
	 */
	public String sendTestAppPushImmediately(CmPushMessage cmPushMessage) throws Exception {
		int rtnVal = 0;
		int dataCount = 0;
		int sendCount = 0;
		int sendNum = 0;

		try {
			CmPushMessage params = cmPushMessageDao.selectReadDetailAppPush(cmPushMessage.getPushMesgNo());
			params.setPushSendTrgtCode(cmPushMessage.getPushSendTrgtCode());
			params.setSendGbnType(cmPushMessage.getSendGbnType());
			params.setSendType(cmPushMessage.getSendType());
			params.setSendPerOnce(CHUNK_SIZE);

			dataCount = cmPushMessageDao.getPushTargetMemberDeviceTokenTextListCount(params);

			if (dataCount > 0) {
				cmPushMessage.setModerNo(Const.SYSTEM_ADMIN_NO);
				// CM_PUSH_MESSAGE 발송진행 상태 및 발송대상 회원 수 업데이트
				cmPushMessage.setPushIngStatCode(CommonCode.PUSH_ING_STAT_CODE_PROGRESS);
				cmPushMessage.setSendTrgtMemberCount(dataCount);
				cmPushMessageDao.updateAppPushManage(cmPushMessage);

				sendNum = ((int) Math.ceil((double) dataCount / (double) CHUNK_SIZE)) + 1;
				for (int i = 1; i < sendNum; i++) {

					params.setSendNum(i);

					// app발신 대상 토큰 조회
					List<CmPushMessage> dataList = cmPushMessageDao.getPushTargetMemberDeviceTokenTextList(params);

					List<String> registrationTokens = dataList.stream().map(CmPushMessage::getFcmTokenText)
							.collect(Collectors.toList());

					// app push 발송(multi 사용)
					Map<String, String> failedTokens = fcmService.sendMultiAppPush(registrationTokens,
							params.getSiteNo(), params.getPushTitleText(), params.getContText(), params.getTrgtUrl(),
							params.getImageUrl());
					log.error("sendTestAppPushImmediately failedTokens = {}", UtilsText.stringify(failedTokens));
					// app push 발송이력
					cmPushMessageService.insertPushSendHistory(dataList, failedTokens);

					sendCount = sendCount + registrationTokens.size() - failedTokens.size();
				}
			}
		} catch (Exception e) {
			log.error(e.toString());
			e.printStackTrace();
			throw new Exception("발송/예약발송에 실패하였습니다.");
		}

		// CM_PUSH_MESSAGE 발송진행 상태 및 발송일시 업데이트
		cmPushMessage.setPushIngStatCode(CommonCode.PUSH_ING_STAT_CODE_SUCCESS);
		cmPushMessage.setPushSendCount(sendCount);
		rtnVal = cmPushMessageDao.updateAppPushManage(cmPushMessage);

		return rtnVal >= 1 ? "발송/예약발송에  성공하였습니다." : "발송/예약발송에 실패하였습니다.";
	}

	/**
	 * @Desc : 테스트 즉시 발송 이력
	 * @Method Name : insertPushSendHistory
	 * @Date : 2019. 6. 27.
	 * @Author : 고웅환
	 * @param cmPushMessage
	 * @throws Exception
	 */
	public void insertPushSendHistory(List<CmPushMessage> dataList, Map<String, String> failedTokens) {
		CmPushSendHistory historyParam = null;
		for (CmPushMessage data : dataList) {
			historyParam = new CmPushSendHistory();
			historyParam.setPushMesgNo(data.getPushMesgNo());
			historyParam.setAppDwldMemberSeq(data.getAppDwldMemberSeq());
			historyParam.setAppOsCode(data.getAppOsCode());
			historyParam.setFcmTokenText(data.getFcmTokenText());
			// historyParam.setSendDtm(UtilsDate.getSqlTimeStamp());
			// historyParam.setRecvDtm(UtilsDate.getSqlTimeStamp());

			historyParam.setSendStatType(Const.RESULT_SUCCESS);
			for (String failedToken : failedTokens.keySet()) {
				if (UtilsText.equals(data.getFcmTokenText(), failedToken)) {
					historyParam.setSendStatType(Const.RESULT_FAIL);
					// historyParam.setRecvDtm(null);
					break;
				}
			}

			try {
				// 이력 테이블 insert
				cmPushMessageDao.insertPushSendHistoryNoTrx(historyParam);
			} catch (Exception e) {
				log.error(e.toString());
				e.printStackTrace();
			}
		}
	}

	/**
	 * @Desc : 일반 즉시 발송
	 * @Method Name : sendRegularAppPushImmediately
	 * @Date : 2019. 7. 22.
	 * @Author : 고웅환
	 * @param cmPushMessage
	 * @throws Exception
	 */
	public String sendRegularAppPushImmediately(CmPushMessage cmPushMessage) throws Exception {
		int dataCount = 0;
		int rtnVal = 0;

		// 발송형태(죽시|예약)에 따른 대상자 건수 조회
		dataCount = UtilsText.equals(Const.PUSH_SEND_GBN_TYPE_TEST, cmPushMessage.getSendGbnType())
				? cmPushMessageService.sendTestTargetMemberList(cmPushMessage).size()
				: (int) cmPushMessageService.sendTargetMemberList(cmPushMessage).get("targetCount");

		if (dataCount > 0) {
			cmPushMessage.setSendTrgtMemberCount(dataCount);

			// CM_PUSH_MESSAGE 발송진행 상태 및 발송대상 회원 수 업데이트
			rtnVal = cmPushMessageDao.updateAppPushManage(cmPushMessage);
		}

		return rtnVal >= 1 ? "발송/예약발송에  성공하였습니다." : "발송/예약발송에 실패하였습니다.";
	}

	/**
	 * @Desc : 테스트/일반 예약 발송
	 * @Method Name : sendAppPushMessage
	 * @Date : 2019. 7. 3.
	 * @Author : 고웅환
	 * @param cmPushMessage
	 * @throws Exception
	 */
	public String sendAppPushReservation(CmPushMessage cmPushMessage) throws Exception {
		int dataCount = 0;
		int rtnVal = 0;
		String rsvSendDtm = "";

		// 예약 발송 시간
		rsvSendDtm = cmPushMessage.getPushDate().replace('.', '-') + " " + cmPushMessage.getPushHours() + ":"
				+ cmPushMessage.getPushMin() + ":00";

		// 발송형태(죽시|예약)에 따른 대상자 건수 조회
		dataCount = UtilsText.equals(Const.PUSH_SEND_GBN_TYPE_TEST, cmPushMessage.getSendGbnType())
				? cmPushMessageService.sendTestTargetMemberList(cmPushMessage).size()
				: (int) cmPushMessageService.sendTargetMemberList(cmPushMessage).get("targetCount");

		if (dataCount > 0) {
			cmPushMessage.setSendTrgtMemberCount(dataCount);
			cmPushMessage.setRsvSendDtm(Timestamp.valueOf(rsvSendDtm));

			// CM_PUSH_MESSAGE 발송진행 상태 및 발송대상 회원 수, 예약발송 시간 업데이트
			rtnVal = cmPushMessageDao.updateAppPushManage(cmPushMessage);
		}

		return rtnVal >= 1 ? "발송/예약발송에  성공하였습니다." : "발송/예약발송에 실패하였습니다.";
	}

	/**
	 * @Desc : 테스트 발송 대상 선택 삭제
	 * @Method Name : deleteTestSendTarget
	 * @Date : 2019. 6. 27.
	 * @Author : 고웅환
	 * @param cmPushMessage
	 * @throws Exception
	 */
	public List<CmPushMessage> deleteTestSendTarget(CmPushMessage cmPushMessage) throws Exception {
		List<CmPushMessage> targetList = new ArrayList<>();
		int rtnVal = cmPushMessageDao.deleteTargetMember(cmPushMessage);
		if (rtnVal >= 1) {
			cmPushMessage.setSendNum(1);
			cmPushMessage.setSendPerOnce(10);

			targetList = cmPushMessageDao.getPushTargetMemberDeviceTokenTextList(cmPushMessage);

		}
		return targetList;
	}

	/**
	 * @Desc : 엑셀업로드 대상회원 정보를 조회해온다
	 * @Method Name : getMemberInfo
	 * @Date : 2019. 7. 31.
	 * @Author : 고웅환
	 * @param pushMesgeNo
	 * @throws Exception
	 */
	public List<CmPushMessage> getMemberInfo(List<CmPushMessage> targetList) throws Exception {
		List<MbMember> memberList = new ArrayList<>();
		MbMember mbMember = new MbMember();

		mbMember.setLoginIds(targetList.stream().map(el -> {
			return UtilsText.trim(el.getLoginId());
		}).collect(Collectors.toList()));

		memberList = memberService.selectAppPushMemberList(mbMember);

		if (!memberList.isEmpty()) {

			for (int i = 0; i < targetList.size(); i++) {
				for (int j = 0; j < memberList.size(); j++) {
					if (targetList.get(i).getLoginId().equals(memberList.get(j).getLoginId())) {

						targetList.get(i).setMemberName(memberList.get(j).getMemberName());
						targetList.get(i).setMemberNo(memberList.get(j).getMemberNo());

					}
				}
			}

		}

		targetList.removeIf(el -> UtilsText.isBlank(el.getMemberNo()));

		return targetList;
	}

}
