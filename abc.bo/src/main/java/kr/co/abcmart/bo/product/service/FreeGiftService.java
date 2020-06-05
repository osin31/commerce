package kr.co.abcmart.bo.product.service;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.product.model.master.PdFreeGift;
import kr.co.abcmart.bo.product.model.master.PdProduct;
import kr.co.abcmart.bo.product.model.master.PdProductOption;
import kr.co.abcmart.bo.product.model.master.PdProductRelationFile;
import kr.co.abcmart.bo.product.repository.master.PdProductDao;
import kr.co.abcmart.bo.product.repository.master.PdProductOptionDao;
import kr.co.abcmart.bo.product.repository.master.PdProductRelationFileDao;
import kr.co.abcmart.bo.product.vo.PdFreeGiftSearchVO;
import kr.co.abcmart.bo.system.service.SiteService;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.FileUpload;
import kr.co.abcmart.common.util.UtilsObject;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.interfaces.zinterfacesdb.service.InterfacesProductService;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;
import kr.co.abcmart.util.UtilsDate;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 사은품 서비스
 * @FileName : GiftService.java
 * @Project : abc.bo
 * @Date : 2019. 3. 26.
 * @Author : hsjhsj19
 */
@Slf4j
@Service
public class FreeGiftService {

	public static final String FILE_SEPARATOR = File.separator;

	/** PC 정렬순서 */
	private static final int SORT_SEQ_1 = 1;
	/** MO 정렬순서 */
	private static final int SORT_SEQ_2 = 2;

	/** PC 상품관련파일 순번 */
	private static final int PRDT_RLTN_FILE_SEQ_41 = 41;
	/** MO 상품관련파일 순번 */
	private static final int PRDT_RLTN_FILE_SEQ_42 = 42;

	/** 파일유형 - 이미지 */
	public static final String FILE_TYPE_IMAGE = "I";

	@Autowired
	private PdProductDao pdProductDao;

	@Autowired
	private PdProductOptionDao pdProductOptionDao;

	@Autowired
	private PdProductRelationFileDao pdProductRelationFileDao;

	@Autowired
	private ProductFileService productFileService;

	@Autowired
	private InterfacesProductService interfaceProductService;

	@Autowired
	private SiteService siteService;

	/**
	 * @Desc : 사은품 정보 등록
	 * @Method Name : inputGift
	 * @Date : 2019. 3. 27.
	 * @Author : hsjhsj19
	 * @param giftS
	 * @throws Exception
	 */
	public String insertFreeGift(PdProduct freeGift) throws Exception {
		log.debug("사은품 정보 등록 서비스");

		// 사은품 항목에 입력되지 않은 상품 테이블 컬럼명을 맞추기 위해 생성
		freeGift = this.forFreeGiftValidate(freeGift);

		// DB insert시 들어가야할 필수 컬럼들
		freeGift = this.forFreeGiftNotNull(freeGift);

		// 세션 정보 호출
		UserDetails user = LoginManager.getUserDetails();
		freeGift.setRgsterNo(user.getAdminNo());
		freeGift.setRgstDtm(new Timestamp(new Date().getTime()));
		freeGift.setModerNo(user.getAdminNo());
		freeGift.setModDtm(new Timestamp(new Date().getTime()));

		// (판매기간) Date Set
		freeGift.setSellStartDtm(new Timestamp(new Date().getTime()));
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		freeGift.setSellEndDtm(new Timestamp(format.parse("9999-12-31 23:59:59").getTime()));

		String vndrPrdtNoText = "F" + this.pdProductDao.selectFreegiftVndrPrdtNoText();
		freeGift.setVndrPrdtNoText(vndrPrdtNoText);
		
		// 상품번호[prdtNo]를 채번
		freeGift.setPrdtNo(pdProductDao.selectKey(freeGift));

		this.pdProductDao.insertWithSelectKey(freeGift);
		String prdtNo = freeGift.getPrdtNo();

		// 사은품 상품 옵션 등록
		if (UtilsText.isNotBlank(prdtNo)) {
			PdProductOption[] freeGiftOptions = freeGift.getProductOption();
			PdProductOption freeGiftOption = freeGiftOptions[0];
			freeGiftOption.setPrdtNo(prdtNo);
			freeGiftOption.setPrdtOptnNo("001");
			freeGiftOption.setOptnName("사은품");
			freeGiftOption.setSortSeq(SORT_SEQ_1);
			freeGiftOption.setSellStatCode("10001");// 판매상태코드
			freeGiftOption.setTotalOrderQty(0);// 총주문수량
			freeGiftOption.setOrderPsbltQty(0);// 주문가능수량

			freeGiftOption.setRgsterNo(freeGift.getRgsterNo());
			freeGiftOption.setRgstDtm(freeGift.getRgstDtm());
			freeGiftOption.setModerNo(freeGift.getModerNo());
			freeGiftOption.setModDtm(freeGift.getRgstDtm());

			this.pdProductOptionDao.insertWithSelectKey(freeGiftOption);
		}

		// 상품관련파일(사은품은 이미지) 등록
		PdProductRelationFile[] pdProductRelationFiles = freeGift.getProductRelationFile();
		for (int i = 0; i < pdProductRelationFiles.length; i++) {
			PdProductRelationFile uploadProductRelationFile = pdProductRelationFiles[i];

			if (!UtilsObject.isEmpty(uploadProductRelationFile.getUploadFileImage())) {
				// 첨부파일 경로 설정
				uploadProductRelationFile.setUploadFilePathImage(
						this.productFileService.uploadFile(uploadProductRelationFile.getUploadFileImage(),
								Arrays.asList(Const.IMG_SRC_PRODUCT_FREEGIFT_PREFIX, UtilsDate.today("yyyy"),
										UtilsDate.today("MM"))));

				PdProductRelationFile pdProductRelationFile = this.setProductRelationFile(uploadProductRelationFile,
						freeGift);
				this.pdProductRelationFileDao.insert(pdProductRelationFile);
			}
		}

		// 인터페이스 호출
		if (UtilsObject.isNotEmpty(prdtNo)) {
			interfaceProductService.insertFreeGiftToErpNoTrx(vndrPrdtNoText);
			// interfaceProductService.insertFreeGiftToErpNoTrx(prdtNo, vndrPrdtNoText);
		}

		return prdtNo;
	}

	/**
	 * @Desc : 사은품 정보 수정
	 * @Method Name : updateFreeGift
	 * @Date : 2019. 4. 17.
	 * @Author : hsjhsj19
	 * @param freeGift
	 * @throws Exception
	 */
	public void updateFreeGift(PdProduct freeGift) throws Exception {
		log.debug("사은품 정보 수정 서비스");

		// 세션 정보 호출
		UserDetails user = LoginManager.getUserDetails();
		freeGift.setModerNo(user.getAdminNo());
		freeGift.setModDtm(new Timestamp(new Date().getTime()));

		// (판매기간) Date Set
		freeGift.setSellStartDtm(new Timestamp(new Date().getTime()));

		this.pdProductDao.update(freeGift);
		String prdtNo = freeGift.getPrdtNo();

		// 사은품 상품 옵션 등록
		if (UtilsText.isNotBlank(prdtNo)) {
			PdProductOption[] freeGiftOptions = freeGift.getProductOption();
			PdProductOption freeGiftOption = freeGiftOptions[0];
			freeGiftOption.setPrdtNo(prdtNo);

			freeGiftOption.setModerNo(freeGift.getModerNo());
			freeGiftOption.setModDtm(freeGift.getModDtm());

			this.pdProductOptionDao.update(freeGiftOption);

		}

		// 상품관련파일(사은품은 이미지) 수정
		PdProductRelationFile pdProductRelationFileForDelete = new PdProductRelationFile();
		pdProductRelationFileForDelete.setPrdtNo(prdtNo);
		// 사은품 이미지 목록 조회
		List<PdProductRelationFile> insertedPdProductRelationFiles = this.pdProductRelationFileDao
				.select(pdProductRelationFileForDelete);

		// 넘어온 이미지
		PdProductRelationFile[] pdProductRelationFiles = freeGift.getProductRelationFile();
		for (PdProductRelationFile uploadProductRelationFile : pdProductRelationFiles) {
			for (PdProductRelationFile insertedProductRelationFile : insertedPdProductRelationFiles) {
				if (uploadProductRelationFile.getPrdtRltnFileSeq()
						.equals(insertedProductRelationFile.getPrdtRltnFileSeq())) {
					// 첨부파일 경로 설정
					uploadProductRelationFile.setUploadFilePathImage(
							this.productFileService.uploadFile(uploadProductRelationFile.getUploadFileImage(),
									Arrays.asList(Const.IMG_SRC_PRODUCT_FREEGIFT_PREFIX, UtilsDate.today("yyyy"),
											UtilsDate.today("MM"))));

					PdProductRelationFile pdProductRelationFile = this.setProductRelationFile(uploadProductRelationFile,
							freeGift);
					this.pdProductRelationFileDao.update(pdProductRelationFile);
				}
			}
		}
	}

	/**
	 * @Desc : 상품번호와 관련된 상품옵션 조회(사은품용)
	 * @Method Name : searchProductOption
	 * @Date : 2019. 3. 28.
	 * @Author : hsjhsj19
	 * @param prdtNo
	 * @return
	 * @throws Exception
	 */
	public List<PdProductOption> searchProductOption(String prdtNo) throws Exception {
		return this.pdProductOptionDao.selectFreeGiftByPrdtNo(prdtNo);
	}

	/**
	 * @Desc : 상품관련파일 기본 정보 세팅
	 * @Method Name : setProductRelationFile
	 * @Date : 2019. 3. 27.
	 * @Author : hsjhsj19
	 * @param freeGift
	 * @param filePathName
	 * @return
	 * @throws Exception
	 */
	private PdProductRelationFile setProductRelationFile(PdProductRelationFile pdProductRelationFile,
			PdProduct freeGift) throws Exception {
		FileUpload imageFile = pdProductRelationFile.getUploadFileImage();
		int sortSeq = PRDT_RLTN_FILE_SEQ_41 == pdProductRelationFile.getPrdtRltnFileSeq() ? SORT_SEQ_1 : SORT_SEQ_2;

		pdProductRelationFile.setPrdtNo(freeGift.getPrdtNo());
		pdProductRelationFile.setSortSeq(sortSeq);
		pdProductRelationFile.setFileType(FILE_TYPE_IMAGE);
		// 연결유형 컬럼없음
		// 링크target 컬럼없음
		// 링크정보 컬럼없음
		// 업로드여부 컬럼없음
		if (freeGift.getType().equals(PdProduct.TYPE_SAVE)) {
			pdProductRelationFile.setRgsterNo(freeGift.getModerNo());
			pdProductRelationFile.setRgstDtm(new Timestamp(new Date().getTime()));
		}
		pdProductRelationFile.setModerNo(freeGift.getModerNo());
		pdProductRelationFile.setModDtm(pdProductRelationFile.getRgstDtm());
		pdProductRelationFile.setDispPostnType("D"); // 상품상세

		if (UtilsObject.isEmpty(pdProductRelationFile.getAltrnText())) {
			if (UtilsObject.isNotEmpty(imageFile) && imageFile.isFileItem()) {
				pdProductRelationFile.setAltrnText(imageFile.getOrgFileName());
			}
		}

		return pdProductRelationFile;
	}

	/**
	 * @Desc : 상품관련파일 이미지명 얻기
	 * @Method Name : getFileName
	 * @Date : 2019. 3. 27.
	 * @Author : hsjhsj19
	 * @param uploadFile
	 * @return
	 */
	private String getFileName(FileUpload uploadFile) {

		String[] orgFileNameAndExt = uploadFile.getOrgFileName().split("\\.");
		String fileName = UtilsText.concat(orgFileNameAndExt[0], "_" + System.currentTimeMillis(), ".",
				uploadFile.getExt());

		return fileName;

	}

	/**
	 * @Desc : 사은품 목록 조회
	 * @Method Name : selectFreeGift
	 * @Date : 2019. 3. 28.
	 * @Author : hsjhsj19
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<PdFreeGift> selectFreeGift(Pageable<PdFreeGiftSearchVO, PdFreeGift> pageable) throws Exception {
		Integer count = this.pdProductDao.selectFreeGiftCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(this.pdProductDao.selectFreeGift(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 기존 쿼리를 사용하기위해 기본값 세팅..
	 * @Method Name : forFreeGiftNotNull
	 * @Date : 2019. 3. 27.
	 * @Author : hsjhsj19
	 * @param freeGift
	 * @return
	 */
	private PdProduct forFreeGiftNotNull(PdProduct freeGift) throws Exception {
		freeGift.setMmnyPrdtYn(Const.BOOLEAN_TRUE); // 자사상품여부
		freeGift.setCntcPrdtSetupYn(Const.BOOLEAN_FALSE); // 연계상품설정여부
		freeGift.setRltnGoodsSetupYn(Const.BOOLEAN_FALSE); // 관련용품설정여부
		freeGift.setAddOptnSetupYn(Const.BOOLEAN_FALSE); // 추가옵션설정여부
		freeGift.setSizeChartDispYn(Const.BOOLEAN_FALSE); // 사이즈조견표전시여부
		freeGift.setOrderMnfctYn(Const.BOOLEAN_FALSE); // 주문제작여부
		freeGift.setDprcExceptYn(Const.BOOLEAN_TRUE); // 감가제외여부
		freeGift.setStorePickupPsbltYn(Const.BOOLEAN_FALSE); // 매장픽업가능여부
		freeGift.setStockIntgrYn(Const.BOOLEAN_FALSE); // 재고통합여부
		freeGift.setStockMgmtYn(Const.BOOLEAN_TRUE); // 재고관리여부
		freeGift.setGenderGbnCode("10003"); // 성별구분코드

		PdProductOption[] pdProductOptions = freeGift.getProductOption();
		freeGift.setUseYn(pdProductOptions[0].getUseYn()); // 사용여부
		freeGift.setBuyLimitYn(Const.BOOLEAN_FALSE); // 구매제한여부
		freeGift.setWrhsAlertYn(Const.BOOLEAN_FALSE); // 입고알림여부
		freeGift.setRsvPrdtYn(Const.BOOLEAN_FALSE); // 예약상품여부
		freeGift.setAconnectDispYn(Const.BOOLEAN_FALSE); // AConnect전시여부
		freeGift.setIgrmallDispExceptYn(Const.BOOLEAN_FALSE); // 통합몰전시제외여부
		freeGift.setVndrSuspdYn(Const.BOOLEAN_FALSE); // 업체일시중지여부
		freeGift.setAprvStatCode(CommonCode.APRV_STAT_CODE_CONFIRM);

		// erd 확인 후에도 나오는 컬럼명들
		freeGift.setSellStatCode(CommonCode.SELL_STAT_CODE_PROC); // 판매상태코드

		// 상품 테이블에서 있지만 쓰지 않을 예정이라 0 입력
		freeGift.setMinBuyPsbltQty(0); // 최소구매가능수량
		freeGift.setDayMaxBuyPsbltQty(0); // 1일최대구매가능수량
		freeGift.setMaxBuyPsbltQty(0); // 최대구매가능수량

		// 2019.05.14 NOT NULL 추가
		freeGift.setSiteNo(Const.SITE_NO_ART); // 통합몰
		freeGift.setChnnlNo("10001"); // ABC
		freeGift.setBrandSortSeq(1);
		freeGift.setItemCode("10003"); // 잡화

		// 2019.10.14 업체번호 추가. ABC 업체번호로 설정
		freeGift.setVndrNo(this.siteService.getVendorNo(freeGift.getChnnlNo()));

		return freeGift;
	}

	/**
	 * @Desc : validate 함수에서 에러를 피하기 위한 미입력 데이터들
	 * @Method Name : forFreeGiftValidate
	 * @Date : 2019. 3. 27.
	 * @Author : hsjhsj19
	 * @param parameter
	 * @throws Exception
	 */
	private PdProduct forFreeGiftValidate(PdProduct freeGift) throws Exception {
		freeGift.setPrdtTypeCode("10003"); // 상품유형코드
		freeGift.setEngPrdtName(freeGift.getPrdtName()); // 영문상품명
		freeGift.setStyleInfo("FREEGIFT"); // 스타일정보
		freeGift.setMnftrName("ABCMart"); // 제조사명
		freeGift.setEmpDscntYn(Const.BOOLEAN_FALSE); // 임직원할인여부
		freeGift.setSrchPsbltYn(Const.BOOLEAN_FALSE); // 검색가능여부
		freeGift.setFreeDlvyYn(Const.BOOLEAN_FALSE); // 무료배송여부 (2019-09-03 주문파트 요청으로 변경)
		freeGift.setAffltsSendYn(Const.BOOLEAN_FALSE); // 제휴사전송여부
		freeGift.setPrdtColorInfo("10017"); // 상품색상코드

		return freeGift;
	}

}
