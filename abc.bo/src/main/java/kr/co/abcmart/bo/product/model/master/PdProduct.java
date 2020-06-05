package kr.co.abcmart.bo.product.model.master;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ibm.icu.util.Calendar;

import kr.co.abcmart.bo.display.model.master.DpCategory;
import kr.co.abcmart.bo.product.model.master.base.BasePdProduct;
import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.request.FileUpload;
import kr.co.abcmart.common.request.annotation.ParameterOption;
import kr.co.abcmart.common.util.UtilsArray;
import kr.co.abcmart.common.util.UtilsDate;
import kr.co.abcmart.common.util.UtilsNumber;
import kr.co.abcmart.common.util.UtilsObject;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.common.validation.Validator;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.util.UtilsMasking;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc :
 * @FileName : PdProduct.java
 * @Project : abc.bo
 * @Date : 2019. 5. 10.
 * @Author : tennessee
 */
@Slf4j
@Data
public class PdProduct extends BasePdProduct implements Validator, Comparable<PdProduct> {

	/** 유형 (PdProduct.TYPE_SAVE, PdProduct.TYPE_MODIFY, PdProduct.TYPE_TEMPORARY) */
	private String type;

	/** 등록자아이디 */
	private String rgsterId;
	/** 등록자이름 */
	private String rgsterName;
	/** 수정자아이디 */
	private String moderId;
	/** 수정자이름 */
	private String moderName;
	/** 승인자아이디 */
	private String aprverId;
	/** 승인자이름 */
	private String aprverName;

	/** 매장 등급 코드 */
	private String tierFlagCodeName;

	/** 첨부 파일 */
	@ParameterOption(target = "prdtRltnFileSeq")
	private PdProductRelationFile[] productRelationFile;

	@ParameterOption(target = "uploadYn")
	private PdProductRelationFile[] productMovieFile;

	/** 상품상세정보 (PC, MOBILE) */
	@ParameterOption(target = "divider")
	private PdProductDetail[] productDetail;

	/** 전시 카테고리 목록 */
	@ParameterOption(target = "divider")
	private DpCategory[] displayCategoryList;

	/** 상품정보고시 항목 */
	@ParameterOption(target = "divider")
	private PdProductAddInfo[] productAddInfo;

	/** 상품옵션 */
	@ParameterOption(target = "status")
	private PdProductOption[] productOption;

	/** 관련상품 (색상연계상품) */
	@ParameterOption(target = "status")
	private PdRelationProduct[] cntcPrdtSetupList;

	/** 관련상품 (관련용품) */
	@ParameterOption(target = "status")
	private PdRelationProduct[] rltnGoodsSetupList;

	/** 상품 아이콘 */
	@ParameterOption(target = "prdtIconSeq")
	private PdProductIcon[] productIcon;

	/** 상품 색상 코드 */
	@ParameterOption(target = "prdtColorCode")
	private PdProductColor[] productColor;

	/** 상품 가격 이력 (최근) */
	@ParameterOption(target = "divider")
	private PdProductPriceHistory[] productPriceHistory;

	/** 상품 승인 이력 (최근) */
	private PdProductApprovalHistory[] productApprovalHistory;

	/** 브랜드명 - 국문 */
	private String brandName;

	/** 브랜드명 - 영문 */
	private String brandEnName;

	private String newErpProductYn;

	/** PD_상품승인이력 - 상품승인요청코드 */
	private String prdtAprvReqCode; // CommonCode.PRDT_APRV_REQ_CODE_MODIFY_INFO, PdProduct.PRDT_APRV_REQ_CODE_PRICE
	/** PD_상품승인이력 - 반려사유 */
	private String returnRsnText;

	/** 사이트명 */
	private String siteName;
	/** 전시채널 */
	private String chnnlName;
	/** 표준카테고리명 */
	private String stdCtgrName;
	/** 전시카테고리명 */
	private String stdrCtgrName;
	/** 정상가 */
	private String normalAmt;
	/** 판매가 */
	private String sellAmt;
	/** ERP할인율 */
	private String erpDscntRate;
	/** 온라인할인율 */
	private String onlnDscntRate;
	/** 임직원할인율 */
	private String empDscntRate;
	/** 입점업체명 */
	private String vndrName;

	/** 대표이미지명 */
	private String titleImageName;
	/** 대표이미지경로 */
	private String titleImagePathText;
	/** 대표이미지주소 */
	private String titleImageUrl;
	/** 대표이미지대체텍스트 */
	private String titleImageAltrnText;

	/** 옵션 사용가능여부수량 */
	private Integer useYnQty;

	/** 주문가능수량 */
	private Integer orderPsbltQty;

	/** 기본수수료율 */
	private Integer dfltCmsnRate;

	/** 총재고수량 */
	private Integer totalStockQty;

	/** 가용율 */
	private Integer availabilityRate;

	/** 상품 프론트 노출가 */
	private Integer displayProductPrice;
	private Integer displayDiscountRate;

	/** 가용재고량 */
	private Integer avaiableStockQty;

	/** 온라인판매가. 엑셀다운로드용 */
	private Integer onlnSellAmt;
	/** 기간계판매가. 엑셀다운로드용 */
	private Integer erpSellAmt;
	/** 임직원판매가. 엑셀다운로드용 */
	private Integer empSellAmt;

	/** 옵션전체갯수 */
	private Integer totalOptionCount;

	/** 목록 조회 시 프로모션 정보 */
	private String appliedPromotionInfo;

	/** 다족구매할인 프로모션 적용 갯수 */
	private Integer promotionCountDiscountMultiShoues;

	/** 중복프로모션 번호. 검색 인자값에 설정된 프로모션번호는 제외됨 */
	private String duplPromoNo;

	/** 등록결과메시지 */
	private String resultMessage;

	/** 총 주문수량 */
	private Integer totalOrderQty;

	/** AI 재고량 */
	private Integer stockAiQty;
	/** AW 재고량 */
	private Integer stockAwQty;
	/** AS 재고량 */
	private Integer stockAsQty;
	/** VD 재고량 */
	private Integer stockVdQty;

	

	private Integer vndrCmsnRate;
	
	/** 등록유형 - 신규 */
	public static final String TYPE_SAVE = "save";
	/** 등록유형 - 수정 */
	public static final String TYPE_MODIFY = "modify";
	/** 등록유형 - 읽기 */
	public static final String TYPE_READ = "read";
	/** 등록유형 - 임시 */
	public static final String TYPE_TEMPORARY = "temporary";

	/** 대량상품등록 사용 변수 */
	/** 상품색상코드 */
	private String prdtColorCode;
	/** 상품아이콘코드 */
	private String insdMgmtInfoText;

	private String stockInfo;

	private String aprverAdmin;

	private String rgsterAdmin;

	private String moderAdmin;

	// 가격변동이 있었는지 체크 변수
	private String orgPriceChangeYn;
	
	

	/**
	 * @Desc : 개인정보 마스킹 수행
	 * @Method Name : setPrivacy
	 * @Date : 2019. 5. 14.
	 * @Author : tennessee
	 * @param forceMasking 개인정보 접근 권한에 상관없이 강제 적용 여부
	 */
	public void setPrivacy(boolean forceMasking) {
		if (forceMasking
				|| UtilsText.equals(LoginManager.getUserDetails().getMemberInfoMgmtYn(), Const.BOOLEAN_FALSE)) {
			// 강제마스킹이 적용된 경우나 개인정보 접근 권한이 없는 경우
			this.setRgsterId(UtilsMasking.loginId(this.getRgsterId()));
			this.setRgsterName(UtilsMasking.userName(this.getRgsterName()));
			this.setModerId(UtilsMasking.loginId(this.getModerId()));
			this.setModerName(UtilsMasking.userName(this.getModerName()));
			this.setAprverId(UtilsMasking.loginId(this.getAprverId()));
			this.setAprverName(UtilsMasking.userName(this.getAprverName()));
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see kr.co.abcmart.common.validation.Validator#validate()
	 */
	@Override
	public void validate() throws ValidatorException {
		this.validateBasic(); // 기본정보
		this.validateDisplay(); // 전시정보
		this.validateStockOption(); // 재고/옵션정보
		this.validateImage(); // 상품이미지
		this.validateDescription(); // 상세설명
		this.validateNoticeInfo(); // 상품 정보제공고시
		this.validateMemo(); // 관리자 메모
	}

	/**
	 * @Desc : 기본정보 입력값 검사
	 * @Method Name : validateBasic
	 * @Date : 2019. 3. 11.
	 * @Author : tennessee
	 */
	private void validateBasic() {
		// 상품유형코드
		if (UtilsText.isBlank(this.getPrdtTypeCode())) {
			this.validationFieldMessage("product.valid.product.prdtTypeCode", "prdtTypeCode");
		}
		// 사이트번호
		if (UtilsText.isBlank(this.getSiteNo())) {
			this.validationFieldMessage("product.valid.product.siteNo", "siteNo");
		}
		// 채널번호
		if (UtilsText.isBlank(this.getChnnlNo())) {
			this.validationFieldMessage("product.valid.product.chnnlNo", "chnnlNo");
		}
		// 상품명(국문)
		if (UtilsText.isBlank(this.getPrdtName())) {
			this.validationFieldMessage("product.valid.product.prdtName", "prdtName");
		} else if (this.getPrdtName().length() > 50) {
			this.validationFieldMessage("product.valid.product.prdtName.length", "prdtName");
		}
		// 상품명(영문)
		if (UtilsText.isBlank(this.getEngPrdtName())) {
			this.validationFieldMessage("product.valid.product.engPrdtName", "engPrdtName");
		} else if (this.getEngPrdtName().length() > 50) {
			this.validationFieldMessage("product.valid.product.engPrdtName.length", "engPrdtName");
		}
		// 판매상태
		if (UtilsText.isBlank(this.getSellStatCode())) {
			this.validationFieldMessage("product.valid.product.sellStatCode", "sellStatCode");
		}
		if (!UtilsText.equals(PdProduct.TYPE_TEMPORARY, this.getType())) {
			// 브랜드번호
			if (UtilsText.isBlank(this.getBrandNo())) {
				this.validationFieldMessage("product.valid.product.brandNo", "brandName");
			}
		}
		// 판매시작기간
		if (UtilsObject.isEmpty(this.getSellStartDtm())) {
			this.validationFieldMessage("product.valid.product.sellStartDtm", "sellStartDtm");
		}
		if (UtilsText.equals(CommonCode.SELL_STAT_CODE_PREPARE, this.getSellStatCode())) {
			// 판매준비중인 경우
			if (UtilsDate.getSqlTimeStamp().compareTo(this.getSellStartDtm()) >= 0) {
				// 판매시작시간이 현재보다 큼
				this.validationFieldMessage("product.valid.product.sellStartDtm.compare.greaterThanNow",
						"sellStartDtm");
			}
		}
		// 판매종료기간
		if (UtilsObject.isEmpty(this.getSellEndDtm())) {
			this.validationFieldMessage("product.valid.product.sellEndDtm", "sellEndDtm");
		}
		if (!UtilsText.equals(PdProduct.TYPE_TEMPORARY, this.getType())) {
			// 표준카테고리
			if (UtilsText.isBlank(this.getStdCtgrNo())) {
				this.validationFieldMessage("product.valid.product.stdCtgrNo", "stdCtgrNo");
			}
		}

		// 판매시작시간과 판매종료시간
		if (this.getSellEndDtm().compareTo(this.getSellStartDtm()) <= 0) {
			this.validationFieldMessage("product.valid.product.sellEndDtm.compare.equal", "sellEndDtm");
		}

		if (!UtilsText.equals(PdProduct.TYPE_TEMPORARY, this.getType())) {
			// 제조사
			if (UtilsText.isBlank(this.getMnftrName())) {
				this.validationFieldMessage("product.valid.product.mnftrName", "mnftrName");
			} else if (this.getMnftrName().length() > 25) {
				this.validationFieldMessage("product.valid.product.mnftrName.length", "mnftrName");
			}
			// 원산지코드
			if (UtilsText.isBlank(this.getOrgPlaceCode())) {
				this.validationFieldMessage("product.valid.product.orgPlaceCode", "orgPlaceCode");
			}
			// 스타일
			if (UtilsText.isBlank(this.getStyleInfo())) {
				this.validationFieldMessage("product.valid.product.styleInfo", "styleInfo");
			} else if (this.getStyleInfo().length() > 25) {
				this.validationFieldMessage("product.valid.product.styleInfo.length", "styleInfo");
			}
			// 색상코드
			if (UtilsText.isBlank(this.getPrdtColorInfo())) {
				this.validationFieldMessage("product.valid.product.prdtColorInfo", "prdtColorInfo");
			} else if (this.getPrdtColorInfo().length() > 50) {
				this.validationFieldMessage("product.valid.product.prdtColorInfo.length", "prdtColorInfo");
			}
		}

		// 제휴사 전송여부
		if (UtilsText.isBlank(this.getAffltsSendYn())) {
			this.validationFieldMessage("product.valid.product.affltsSendYn", "affltsSendYn");
		}
		// 무료배송 상품여부
		if (UtilsText.isBlank(this.getFreeDlvyYn())) {
			this.validationFieldMessage("product.valid.product.freeDlvyYn", "freeDlvyYn");
		}
		// 주문제작
		if (UtilsText.isBlank(this.getOrderMnfctYn())) {
			this.validationFieldMessage("product.valid.product.orderMnfctYn", "orderMnfctYn");
		}
		// 매장픽업불가
		if (UtilsText.isBlank(this.getStorePickupPsbltYn())) {
			this.validationFieldMessage("product.valid.product.storePickupPsbltYn", "storePickupPsbltYn");
		}
		// 화면에 노출되지 않는 정보 (hidden 정보)
		// 자사상품여부
		if (UtilsText.isBlank(this.getMmnyPrdtYn())) {
			this.validationFieldMessage("product.valid.product.badRequest", "mmnyPrdtYn");
		}
		// 출시일여부
		if (UtilsText.equals("Y", this.getRelisTodoYn())) {
			// 판매대기인지 확인(주석처리)
			if (!UtilsText.equals(CommonCode.SELL_STAT_CODE_PREPARE, this.getSellStatCode())) {
			//	this.validationFieldMessage("product.valid.product.relisTodoYn.sellStatCode", "sellStatCode");
			}
			// 출시일 체크
			if (UtilsObject.isEmpty(this.getRelisTodoDtm())) {
				this.validationFieldMessage("product.valid.product.relisTodoDtm", "relisTodoDtm");
			}
			// 출시일과 판매종료일 비교
			if (this.getSellEndDtm().before(this.getRelisTodoDtm())) {
				this.validationFieldMessage("product.valid.product.relisTodoYn.sellDtm", "relisTodoDtm");
			}

		}
		// 예약상품여부
		if (UtilsText.isBlank(this.getRsvPrdtYn())) {
			this.validationFieldMessage("product.valid.product.badRequest", "rsvPrdtYn");
		} else if (UtilsText.equals(Const.BOOLEAN_TRUE, this.getRsvPrdtYn())) {
			// 예약상품인 경우
			if (UtilsObject.isEmpty(this.getRsvDlvyYmd())) {
				// 예상출고일이 없는 경우
				this.validationFieldMessage("product.valid.product.rsvDlvyYmd", "rsvDlvyYmd");
			}
		}
		// 임직원할인제외
		if (UtilsText.isBlank(this.getEmpDscntYn())) {
			this.validationFieldMessage("product.valid.product.badRequest", "empDscntYn");
		}
		// 감가제외
		if (UtilsText.isBlank(this.getDprcExceptYn())) {
			this.validationFieldMessage("product.valid.product.badRequest", "dprcExceptYn");
		}

		// 테마/성별
		if (UtilsText.isBlank(this.getGenderGbnCode())) {
			this.validationFieldMessage("product.valid.product.genderGbnCode", "genderGbnCode");
		}

		if (!UtilsText.equals(PdProduct.TYPE_TEMPORARY, this.getType())) {
			if (UtilsArray.isNotEmpty(this.getProductPriceHistory())) {
				// 상품가격정보
				for (PdProductPriceHistory item : this.getProductPriceHistory()) {
					item.validate();
				}
			}
		}
	}

	/**
	 * @Desc : 전시정보 입력값 검사
	 * @Method Name : validateDisplay
	 * @Date : 2019. 3. 11.
	 * @Author : tennessee
	 */
	private void validateDisplay() {
		// 전시여부
		if (UtilsText.isBlank(this.getDispYn())) {
			this.validationFieldMessage("product.valid.product.dispYn", "dispYn");
		}
		if (!UtilsText.equals(PdProduct.TYPE_TEMPORARY, this.getType())) {
			// 전시 카테고리
			if (UtilsArray.isEmpty(this.getDisplayCategoryList())) {
				this.validationFieldMessage("product.valid.product.displayCategoryList", "ctgrNo");
				// 기준 카테고리
				if (UtilsText.isBlank(this.getStdrCtgrNo())) {
					this.validationFieldMessage("product.valid.product.getStdrCtgrNo", "stdCtgrNo");
				}
			}
		}
		// 검색가능여부
		if (UtilsText.isBlank(this.getSrchPsbltYn())) {
			this.validationFieldMessage("product.valid.product.srchPsbltYn", "srchPsbltYn");
		}
		// 연계상품설정여부
		if (UtilsText.isBlank(this.getCntcPrdtSetupYn())) {
			this.validationFieldMessage("product.valid.product.cntcPrdtSetupYn", "cntcPrdtSetupYn");
		}
		// 관련용품설정여부
		if (UtilsText.isBlank(this.getRltnGoodsSetupYn())) {
			this.validationFieldMessage("product.valid.product.rltnGoodsSetupYn", "rltnGoodsSetupYn");
		}
		// 상품아이콘
		if (UtilsArray.isNotEmpty(this.getProductIcon())) {
			for (PdProductIcon item : this.getProductIcon()) {
				item.validate();
			}
		}

		if (!UtilsText.equals(PdProduct.TYPE_TEMPORARY, this.getType())) {
			// 색상정보
			if (UtilsArray.isNotEmpty(this.getProductColor())) {
				for (PdProductColor item : this.getProductColor()) {
					item.validate();
				}
			} else {
				// 색상정보 길이 체크
				this.validationFieldMessage("product.valid.product.productColor", "productColor");
			}
		}

		// 색상연계상품
		if (UtilsText.equals(Const.BOOLEAN_TRUE, this.getCntcPrdtSetupYn())
				&& UtilsArray.isNotEmpty(this.getCntcPrdtSetupList())) {
			// 연계상품이 설정 된 경우
			for (PdRelationProduct item : this.getCntcPrdtSetupList()) {
				item.validate();
			}
		}
		// 관련용품추천
		if (UtilsText.equals(Const.BOOLEAN_TRUE, this.getRltnGoodsSetupYn())
				&& UtilsArray.isNotEmpty(this.getRltnGoodsSetupList())) {
			// 관련상품이 설정 된 경우
			for (PdRelationProduct item : this.getRltnGoodsSetupList()) {
				item.validate();
			}
		}
	}

	/**
	 * @Desc : 재고/옵션정보 입력값 검사
	 * @Method Name : validateStockOption
	 * @Date : 2019. 3. 11.
	 * @Author : tennessee
	 */
	private void validateStockOption() {
		// 구매수량제한여부
		if (UtilsText.isBlank(this.getBuyLimitYn())) {
			this.validationFieldMessage("product.valid.product.buyLimitYn", "buyLimitYn");
		} else {
			if (UtilsText.equals(Const.BOOLEAN_TRUE, this.getBuyLimitYn())) {
				// 구매수량 제한

				// 최소구매수량
				if (UtilsNumber.compare(this.getMinBuyPsbltQty(), 1) < 0) {
					this.validationFieldMessage("product.valid.product.minBuyPsbltQty", "minBuyPsbltQty");
				}
				// 최대구매수량
				if (UtilsNumber.compare(this.getMaxBuyPsbltQty(), 1) < 0) {
					this.validationFieldMessage("product.valid.product.maxBuyPsbltQty", "maxBuyPsbltQty");
				}
				if (UtilsNumber.compare(this.getMinBuyPsbltQty(), this.getMaxBuyPsbltQty()) > 0) {
					// 최소구매수량이 최대구매수량보다 큰 경우
					this.validationFieldMessage("product.valid.product.minBuyPsbltQty.compare.greaterThanMax",
							"minBuyPsbltQty");
				}
				// 1일최대구매수량
				if (UtilsNumber.compare(this.getDayMaxBuyPsbltQty(), 1) < 0) {
					this.validationFieldMessage("product.valid.product.dayMaxBuyPsbltQty", "dayMaxBuyPsbltQty");
				}

			} else {
				// 구매수량 제한안함

				this.setMinBuyPsbltQty(0); // 최소구매수량
				this.setMaxBuyPsbltQty(0); // 최대구매수량
				this.setDayMaxBuyPsbltQty(0); // 1일최대구매수량

			}
		}
		// 자사상품 체크
		if (UtilsText.equals(Const.BOOLEAN_TRUE, this.getMmnyPrdtYn())) {
			// 통합재고미연동사유코드
			if (UtilsText.equals(this.getRsvPrdtYn(), Const.BOOLEAN_FALSE)
					&& UtilsText.equals(this.getStockUnIntgrRsnCode(), CommonCode.STOCK_UN_INTGR_RSN_CODE_RESERVED)) {
				// 예약상품이 아닌데 재고미연동사유코드가 예약상품이 아닌 경우
				this.validationFieldMessage("product.valid.product.rsvPrdtYn.stockUnIntgrRsnCode.reserved",
						"stockUnIntgrRsnCode");
			}
			if (UtilsText.equals(this.getStockIntgrYn(), Const.BOOLEAN_FALSE)
					&& UtilsText.isBlank(this.getStockUnIntgrRsnCode())) {
				this.validationFieldMessage("product.valid.product.stockUnIntgrRsnCode", "stockUnIntgrRsnCode");
			}
		}
		if (UtilsArray.isNotEmpty(this.getProductOption())) {

			if (UtilsText.equals(Const.BOOLEAN_FALSE, this.getMmnyPrdtYn())) {
				// 사이즈명(입점은 색상명)이 동일한 이름이 있는지 체크

				List<String> foundDuplicatedOptnNames = new ArrayList<String>(); // 중복된 사이즈명(색상명) 저장 공간

				for (PdProductOption item : this.getProductOption()) {
					boolean foundFlag = false; // init flag.

					for (PdProductOption subItem : this.getProductOption()) {
						if (UtilsText.equals(item.getOptnName(), subItem.getOptnName())) {
							if (foundFlag) {
								// 여러번 찾은 경우
								foundDuplicatedOptnNames.add(item.getOptnName()); // 중복옵션명을 목록에 담음
								break;
							} else {
								// 최초로 찾은 경우
								foundFlag = true; // set flag.
							}
						}
					}
				}

				if (UtilsNumber.compare(foundDuplicatedOptnNames.size(), 0) > 0) {
					// 중복된 사이즈명(색상명)이 있는 경우, 추가옵션이름 유무 검사
					for (String foundDuplicatedOptnName : foundDuplicatedOptnNames) {
						for (PdProductOption item : this.getProductOption()) {
							if (UtilsText.equals(foundDuplicatedOptnName, item.getOptnName())) {
								// 중복된 사이즈명(색상명)인 경우
								if (UtilsText.isBlank(item.getAddOptn2Text())) {
									// 추가옵션이 없는 경우
									this.validationFieldMessage("product.valid.product.option.vendor.addOptn2Text",
											"wrhsAlertYn");
								}
							}
						}
					}
				}
			}

			for (PdProductOption item : this.getProductOption()) {
				item.setMmnyPrdtYn(this.getMmnyPrdtYn()); // 자사상품여부 설정
				item.setStockMgmtYn(this.getStockMgmtYn()); // 재고관리여부값 설정

				item.validate();
			}
		} else {
			// 옵션 길이 체크
			this.validationFieldMessage("product.valid.product.option.length", "wrhsAlertYn");
		}
	}

	/**
	 * @Desc : 상품이미지 입력값 검사
	 * @Method Name : validateImage
	 * @Date : 2019. 3. 11.
	 * @Author : tennessee
	 */
	private void validateImage() {
		for (PdProductRelationFile item : this.getProductRelationFile()) {
			if (UtilsText.equals("P", item.getDispPostnType())) {
				item.validate();
			}

		}
	}

	private void validateDescription() {
		for (PdProductRelationFile item : this.getProductRelationFile()) {
			if (UtilsText.equals("D", item.getDispPostnType())) {
				item.validate();
			}
		}

		if (!UtilsText.equals(PdProduct.TYPE_TEMPORARY, this.getType())) {
			for (PdProductDetail item : this.getProductDetail()) {
				item.validate();
			}
		}
	}

	/**
	 * @Desc : 상품 정보제공고시 입력값 검사
	 * @Method Name : validateNoticeInfo
	 * @Date : 2019. 3. 11.
	 * @Author : tennessee
	 */
	private void validateNoticeInfo() {
		if (!UtilsText.equals(PdProduct.TYPE_TEMPORARY, this.getType())) {
			// 상품 정보제공고시 품목명
			if (UtilsText.isBlank(this.getItemCode())) {
				this.validationFieldMessage("product.valid.product.itemCode", "itemCode");
			}
		}

		for (PdProductAddInfo item : this.getProductAddInfo()) {
			item.validate();
		}
	}

	/**
	 * @Desc : 관리자메모 입력값 검사
	 * @Method Name : validateMemo
	 * @Date : 2019. 3. 11.
	 * @Author : tennessee
	 */
	private void validateMemo() {
		// 검사항목 없음
	}

	/**
	 * @Desc : 상품 유형(등록, 수정, 임시저장)에 따른 기본 정보 설정
	 * @Method Name : setDefaultDataWhenRegist
	 * @Date : 2019. 4. 18.
	 * @Author : tennessee
	 */
	public void setDefaultDataWhenRegist() {
		switch (this.getType()) {
		case PdProduct.TYPE_SAVE:
			this.setDefaultDataWhenSave();
			break;
		case PdProduct.TYPE_MODIFY:
			this.setDefaultDataWhenModify();
			break;
		case PdProduct.TYPE_TEMPORARY:
			this.setDefaultDataWhenTemporary();
			break;
		default:
			this.setDefaultDataWhenTemporary();
			log.debug("알 수 없는 유형입니다. {}", this.getType());
		}
	}

	/**
	 * @Desc : 상품 정보 최초등록 전 기본정보 설정
	 * @Method Name : setDefaultDataWhenSave
	 * @Date : 2019. 5. 10.
	 * @Author : tennessee
	 */
	private void setDefaultDataWhenSave() {
		if (UtilsText.equals(Const.BOOLEAN_TRUE, this.getMmnyPrdtYn())) {
			this.setStockMgmtYn(Const.BOOLEAN_TRUE); // 재고관리여부
		} else {
			this.setMmnyPrdtYn(Const.BOOLEAN_FALSE); // 자사상품여부
		}

		this.setAprvStatCode(CommonCode.APRV_STAT_CODE_TEMPORARY); // 상품승인정보 임시등록. 승인이력 설정 시 재설정됨

		this.setAconnectDispYn(Const.BOOLEAN_TRUE); // A커넥트전시여부
		this.setVndrSuspdYn(Const.BOOLEAN_FALSE); // 입점사거래중지여부
		this.setUseYn(Const.BOOLEAN_TRUE); // 사용여부

		this.setIgrmallDispExceptYn(Const.BOOLEAN_FALSE); // 통합몰전시제외여부
		this.setBrandSortSeq(1); // 브랜드정렬순서
	}

	/**
	 * @Desc : 상품 정보 수정 전 기초정보 설정
	 * @Method Name : setDefaultDataWhenModify
	 * @Date : 2019. 5. 10.
	 * @Author : tennessee
	 */
	private void setDefaultDataWhenModify() {
		switch (LoginManager.getUserDetails().getUpAuthNo()) {
		case Const.ROLE_ADMIN_GROUP:
			if (UtilsText.equals(this.getMmnyPrdtYn(), Const.BOOLEAN_TRUE)) {
				// BO권한 사용자가 자사상품을 수정하는 경우, 승인완료로 설정
				this.setAprvStatCode(CommonCode.APRV_STAT_CODE_CONFIRM); // 승인완료
			} else {
				this.setAprvStatCode(CommonCode.APRV_STAT_CODE_REQUEST); // 승인요청
			}
			break;
		case Const.ROLE_VENDER_GROUP:
			this.setAprvStatCode(CommonCode.APRV_STAT_CODE_REQUEST); // 승인요청
			break;
		default:
			log.debug("알 수 없는 권한입니다. {}", LoginManager.getUserDetails().getUpAuthNo());
		}
	}

	private void setDefaultDataWhenTemporary() {
		this.setDefaultDataWhenSave();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@SuppressWarnings("serial")
	@Override
	public int compareTo(PdProduct target) {
		Method[] methods = this.getClass().getMethods();
		List<String> compareMethodNames = new ArrayList<String>() {
			{
				// 상품번호. 비교대상 제외
				add("getPrdtTypeCode"); // 상품유형코드
				// 사이트번호. 비교대상 제외
				// 채널번호. 비교대상 제외
				add("getPrdtName"); // 상품명
				add("getEngPrdtName"); // 영문상품명
//				add("getVndrNo"); // 업체번호
				add("getVndrPrdtNoText"); // 업체상품번호
				// 자사상품여부. 비교대상 제외
				add("getBrandNo"); // 브랜드번호
				add("getOrgPlaceCode"); // 원산지코드
				add("getStyleInfo"); // 스타일정보
				add("getPrdtColorInfo"); // 상품색상정보
				add("getMnftrName"); // 제조사명
				add("getStdCtgrNo"); // 표준카테고리번호
				add("getStdrCtgrNo"); // 기준카테고리번호
				add("getCntcPrdtSetupYn"); // 연계상품설정여부
				add("getRltnGoodsSetupYn"); // 관련용품설정여부
				// 추가옵션설정여부. 비교대상 제외
				add("getSizeChartDispYn"); // 사이즈조견표전시여부
				add("getSizeChartSeq"); // 사이즈조견표순번
				add("getEmpDscntYn"); // 임직원할인여부
				add("getOrderMnfctYn"); // 주문제작여부
				add("getDprcExceptYn"); // 감가제외여부
				add("getStorePickupPsbltYn"); // 매장픽업가능여부
				// 무료배송여부. 비교대상 제외
				// 재고통합여부. 비교대상 제외
				// 재고미통합사유코드. 비교대상 제외
				// 재고관리여부. 비교대상 제외
				// 구매제한여부. 비교대상 제외
				// 최소구매가능수량. 비교대상 제외
				// 1일최대구매가능수령. 비교대상 제외
				// 최대구매가능수량. 비교대상 제외
				add("getItemCode"); // 품목코드
				add("getGenderGbnCode"); // 성별구분코드
//				add("getDispFlagText"); // 전시Flag
				add("getSrchPsbltYn"); // 검색가능여부
				add("getSrchKeyWordText"); // 검색키워드
				add("getWrhsAlertYn"); // 입고알림여부
				add("getSellStartDtm"); // 판매시작일시
				add("getSellEndDtm"); // 판매종료일시
				add("getRsvPrdtYn"); // 예약상품여부
				add("getRsvDlvyYmd"); // 예약출고일
				add("getAffltsSendYn"); // 제휴사전송여부
//				add("getAconnectDispYn"); // AConnect전시여부
				add("getDispYn"); // 전시여부
//				add("getIgrmallDispExceptYn"); // 통합몰전시제외여부
//				add("getVndrSuspdYn"); // 업체일시중지여부
//				add("getAprvStatCode"); // 승인상태코드
//				add("getSellStatCode"); // 판매상태코드
//				add("getUseYn"); // 사용여부
			}
		};

		int compareCountTotal = compareMethodNames.size(); // 검사 대상 항목 총 갯수
		int compareCountEqual = 0; // 동일 항목 갯수

		for (String compareMethodName : compareMethodNames) {
			// 비교대상 메서드 목록 기준으로 검색
			for (Method method : methods) {
				if (UtilsText.equals(compareMethodName, method.getName())) {

					try {

						Object sourceValue = null;
						Object targetValue = null;
						sourceValue = method.invoke(this); // 소스 메서드 값 추출
						targetValue = method.invoke(target); // 타겟 메서드 값 추출

						if (UtilsObject.isEmpty(sourceValue) && UtilsObject.isEmpty(targetValue)) {
							// 동일함으로 판단
							compareCountEqual++;
						} else {
							if (sourceValue instanceof String) {
								if (UtilsText.equals((String) sourceValue, (String) targetValue)) {
									compareCountEqual++;
								}
							} else if (sourceValue instanceof Integer) {
								if (UtilsObject.isEmpty(sourceValue)) {
									// source가 비어있음
									if (UtilsObject.isEmpty(targetValue) || ((Integer) targetValue).intValue() == 0) {
										compareCountEqual++;
									}
								} else if (UtilsObject.isEmpty(targetValue)) {
									// target만 비어있음
								} else {
									// source & target이 모두 비어있지 않음
									if (((Integer) sourceValue).compareTo((Integer) targetValue) == 0) {
										compareCountEqual++;
									}
								}
							} else if (sourceValue instanceof Timestamp) {
								if (UtilsObject.isEmpty(sourceValue)) {
									// source가 비어있음
									if (UtilsObject.isEmpty(targetValue)) {
										compareCountEqual++;
									}
								} else if (UtilsObject.isEmpty(targetValue)) {
									// target만 비어있음
								} else {
									// source & target이 모두 비어있지 않음
									if (((Timestamp) sourceValue).compareTo((Timestamp) targetValue) == 0) {
										compareCountEqual++;
									}
								}
							} else {
								log.error("알 수 없는 필드유형입니다. sourceMethod : {}\tsourceValue : {}\ttargetValue : {}",
										method.getName(), sourceValue, targetValue);
							}
						}
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						log.error("메서드 수행 중 오류 발생. {}", e.getMessage());
						log.error("{}", e);
					}
				}
			}
		}

		return compareCountTotal - compareCountEqual;
	}

	// 등록자 정보 마스킹 처리
	public String getRgsterInfo() {
		return UtilsMasking.gridMasking(getRgsterId(), getRgsterName());
	}

	// 수정자 정보 마스킹 처리
	public String getModerInfo() {
		return UtilsMasking.gridMasking(getModerId(), getModerName());
	}

	// 승인자 정보 마스킹 처리
	public String getAprverInfo() {
		return UtilsMasking.gridMasking(getAprverId(), getAprverName());
	}

	public void validateFreeGift() throws ValidatorException {
		// 상품명(국문)
		if (UtilsText.isBlank(this.getPrdtName())) {
			this.validationMessage("product.valid.product.freeGift.prdtName");
		} else if (this.getPrdtName().length() >= 33) {
			this.validationMessage("product.valid.product.freeGift.prdtName.length");
		}

		// 전시여부
		if (UtilsText.isBlank(this.getDispYn())) {
			this.validationMessage("product.valid.product.freeGift.option.dispYn");
		}

		PdProductOption option = this.getProductOption()[0];
		// 옵션
		if (UtilsObject.isEmpty(option)) {
			this.validationMessage("product.valid.product.freeGift.option");
		}

		// 사용여부
		if (UtilsText.isBlank(option.getUseYn())) {
			this.validationMessage("product.valid.product.freeGift.option.useYn");
		}

		// 재고수량
		if (UtilsObject.isEmpty(option.getTotalStockQty()) || option.getTotalStockQty().compareTo(0) < 0) {
			this.validationMessage("product.valid.product.freeGift.option.stockVdQty");
		}

		for (PdProductRelationFile item : this.getProductRelationFile()) {
			FileUpload uploadFile = item.getUploadFileImage();
			// 이미지
			if (UtilsObject.isNotEmpty(uploadFile) && uploadFile.isFileItem()) {
				// 파일이 첨부된 경우. (신규등록 또는 수정등록)
				if (!item.getUploadFileImage()
						.isAllowExtAndMimeType(new String[] { "jpg", "jpeg", "png", "gif", "bmp" })) {
					this.validationMessage("product.valid.product.freeGift.image.banner.uploadFileImage.extension");
				}
			} else if (UtilsText.isBlank(item.getImageName())) {
				// 파일이 첨부되지 않았고, 기존에 등록된 이미지명이 없는 경우
				this.validationMessage("product.valid.product.freeGift.image.banner.uploadFileImage");
			}

			// 첨부이미지 대체텍스트
			if (UtilsText.isBlank(item.getAltrnText())) {
				// this.validationMessage("product.valid.product.freeGift.image.banner.altrnText");
			} else if (item.getAltrnText().length() > 33) {
				this.validationMessage("product.valid.product.freeGift.image.banner.altrnText.length");
			}
		}
	}

	/**
	 * @Desc : 판매기간 기본 설정
	 * @Method Name : setDefaultSellDtm
	 * @Date : 2019. 8. 26.
	 * @Author : tennessee
	 */
	public void setDefaultSellDtm() {
		if (UtilsObject.isEmpty(this.getSellStartDtm()) && UtilsObject.isEmpty(this.getSellEndDtm())) {
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);
			this.setSellStartDtm(new Timestamp(c.getTime().getTime()));
			c.set(Calendar.YEAR, 9999);
			c.set(Calendar.MONTH, Calendar.DECEMBER);
			c.set(Calendar.DAY_OF_MONTH, 31);
			c.set(Calendar.HOUR_OF_DAY, 23);
			c.set(Calendar.MINUTE, 50);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);
			this.setSellEndDtm(new Timestamp(c.getTime().getTime()));
		}
	}

	public void setStockInfo(String stockInfo) {
		if (!UtilsText.isEmpty(stockInfo)) {
			int idx = 0;
			String[] stockInfos = UtilsText.split(stockInfo, "^");
			this.orderPsbltQty = Integer.parseInt(stockInfos[idx++]);
			this.totalStockQty = Integer.parseInt(stockInfos[idx++]);
			this.totalOrderQty = Integer.parseInt(stockInfos[idx++]);
			this.stockAiQty = Integer.parseInt(stockInfos[idx++]);
			this.stockAwQty = Integer.parseInt(stockInfos[idx++]);
			this.stockAsQty = Integer.parseInt(stockInfos[idx++]);
			this.stockVdQty = Integer.parseInt(stockInfos[idx++]);
			this.avaiableStockQty = Integer.parseInt(stockInfos[idx++]);
			this.useYnQty = Integer.parseInt(stockInfos[idx++]);
			this.totalOptionCount = Integer.parseInt(stockInfos[idx++]);
		}
	}

	public void setAprverAdmin(String aprverAdmin) {
		if (!UtilsText.isEmpty(aprverAdmin)) {
			int idx = 0;
			String[] aprverAdmins = UtilsText.split(aprverAdmin, "^");
			this.aprverId = aprverAdmins[idx++];
			this.aprverName = aprverAdmins[idx++];
		}
	}

	public void setRgsterAdmin(String rgsterAdmin) {
		if (!UtilsText.isEmpty(rgsterAdmin)) {
			int idx = 0;
			String[] rgsterAdmins = UtilsText.split(rgsterAdmin, "^");
			this.rgsterId = rgsterAdmins[idx++];
			this.rgsterName = rgsterAdmins[idx++];
		}
	}

	public void setModerAdmin(String moderAdmin) {
		if (!UtilsText.isEmpty(moderAdmin)) {
			int idx = 0;
			String[] moderAdmins = UtilsText.split(moderAdmin, "^");
			this.moderId = moderAdmins[idx++];
			this.moderName = moderAdmins[idx++];

		}
	}

}
