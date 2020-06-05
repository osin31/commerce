package kr.co.abcmart.bo.product.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.product.model.master.PdProduct;
import kr.co.abcmart.bo.product.model.master.PdProductColor;
import kr.co.abcmart.bo.product.model.master.PdProductOption;
import kr.co.abcmart.bo.product.model.master.PdProductOptionStockSabangnet;
import kr.co.abcmart.bo.product.model.master.PdProductPriceHistory;
import kr.co.abcmart.bo.product.repository.master.PdProductOptionStockSabangnetDao;
import kr.co.abcmart.bo.product.vo.PdProductOptionStockSearchVO;
import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.bo.system.model.master.SySiteChnnl;
import kr.co.abcmart.bo.system.service.CommonCodeService;
import kr.co.abcmart.bo.system.service.SiteService;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.common.util.UtilsCollection;
import kr.co.abcmart.common.util.UtilsNumber;
import kr.co.abcmart.common.util.UtilsObject;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.interfaces.zinterfacesdb.model.master.InterfacesGamGaToErp;
import kr.co.abcmart.interfaces.zinterfacesdb.model.master.InterfacesSangPumDetailToErp;
import kr.co.abcmart.interfaces.zinterfacesdb.model.master.InterfacesSangPumToErp;
import kr.co.abcmart.interfaces.zinterfacesdb.model.master.InterfacesStockToErp;
import kr.co.abcmart.interfaces.zinterfacesdb.service.InterfacesProductService;
import kr.co.abcmart.util.UtilsText;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 상품 중간계 I/F 연계 서비스
 * @FileName : ProductInterfaceService.java
 * @Project : abc.bo
 * @Date : 2019. 4. 2.
 * @Author : tennessee
 */
@Slf4j
@Service
public class ProductInterfaceService {

	@Autowired
	private PdProductOptionStockSabangnetDao productOptionStockSabangnetDao;

	@Autowired
	private InterfacesProductService interfacesProductService;

	@Autowired
	private ProductReflectionService productReflectionService;

	@Autowired
	private CommonCodeService commonCodeService;

	@Autowired
	private SiteService siteService;

	private SimpleDateFormat dateFormatYYYYMMDD;

	@PostConstruct
	private void init() {
		this.dateFormatYYYYMMDD = new SimpleDateFormat("yyyyMMdd");
	}

	/** 기본채널번호. ABC-MART */
	private static final String CHANNEL_NO_DEFAULT = "10001";

	/** 물류구분코드. 온라인매장 */
	private static final String CBCD_CODE_AI = "AI";

	/** 물류구분코드. 물류창고 */
	private static final String CBCD_CODE_AW = "AW";

	/** 물류구분코드. 오프라인매장 */
	private static final String CBCD_CODE_AS = "AS";

	/**
	 * @Desc : ERP상품정보를 조회하여 온라인에서 사용가능한 상품정보로 반환
	 * @Method Name : getErpProduct
	 * @Date : 2019. 4. 2.
	 * @Author : tennessee
	 * @param vndrPrdtNoText
	 * @param siteNo
	 * @param chnnlNo
	 * @return
	 * @throws Exception
	 */
	public PdProduct getErpProduct(String vndrPrdtNoText, String siteNo, String chnnlNo) throws Exception {
		PdProduct result = new PdProduct();

		// 상품정보 조회
		List<InterfacesSangPumDetailToErp> erpProducts = this.interfacesProductService
				.selectErpSangPumDetail(vndrPrdtNoText, chnnlNo);

		// FIXME 2020-01-02 상품 정보가 없을 경우 Exception 처리
		if (UtilsObject.isEmpty(erpProducts)) {
			throw new Exception();
		}

		// 상품가격정보 조회
		// FIXME 가격 정보 조회시 매장 코드 정의 필요함.
		List<InterfacesGamGaToErp> gamgas = this.interfacesProductService.selectErpGamGa(vndrPrdtNoText);
		this.convertProductFromErpToOnline(erpProducts, result); // 온라인 데이터로 변경
		this.convertPrice(gamgas, result); // 가격정보 설정

		log.error("erpProducts1 : {}", erpProducts.get(0));
		log.error("erpProducts2 : {}", erpProducts.get(0).getEmpDiscount());
		log.error("erpProducts3 : {}", result.getProductPriceHistory()[0]);

		// 임직원할인율은 상품 마스터정보에 있으므로 이곳에서 설정
		// 가격 정보가 조회 되었을 때만 정의 한다.
		if (UtilsObject.isNotEmpty(gamgas)) {
			result.getProductPriceHistory()[0].setEmpDscntRate(Short.parseShort(erpProducts.get(0).getEmpDiscount())); // 임직원할인율
		}

		result.setSiteNo(siteNo); // 사이트번호
		result.setChnnlNo(chnnlNo); // 채널번호
		result.setVndrNo(this.siteService.getVendorNo(result.getChnnlNo()));
		result.setMmnyPrdtYn("Y"); // 자사상품여부 설정

		result.setDefaultSellDtm(); // 판매기간 기본값 설정

		result.setAprvStatCode(CommonCode.APRV_STAT_CODE_TEMPORARY); // 승인상태코드. 임시저장상태.
		result.setSellStatCode(CommonCode.SELL_STAT_CODE_PREPARE); // 판매상태코드. 판매준비중.

		return result;

	}

	/**
	 * @Desc : ERP상품정보를 조회한 후 온라인 상품객체로 변환
	 * @Method Name : convertProductFromErpToOnline
	 * @Date : 2019. 4. 2.
	 * @Author : tennessee
	 * @param before
	 * @param after
	 * @throws Exception
	 */
	private void convertProductFromErpToOnline(List<InterfacesSangPumDetailToErp> before, PdProduct after)
			throws Exception {
		this.convertProductFromErpToOnline(before.get(0), after);
	}

	/**
	 * @Desc : ERP상품정보를 조회한 후 온라인 상품객체로 변환
	 * @Method Name : convertFromErpToOnline
	 * @Date : 2019. 4. 2.
	 * @Author : tennessee
	 * @param before
	 * @param after
	 * @throws Exception
	 */
	private void convertProductFromErpToOnline(InterfacesSangPumDetailToErp before, PdProduct after) throws Exception {

		String[] codeFields = { CommonCode.PRDT_COLOR_CODE, CommonCode.ORG_PLACE_CODE };
		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = this.commonCodeService
				.getCodeListByGroupByCombo(codeFields, false);

//		before.getCbcd(); // B코드
//		before.getSangPumfullcd(); // 상품전체코드
		after.setVndrPrdtNoText(before.getSangpumCd()); // ERP상품코드. 내부관리정보
		// 색상명
		if (UtilsText.isNotBlank(before.getColorNm())) {
			String[] splitedColors = before.getColorNm().split("/");

			if (splitedColors.length > 0) {

				List<PdProductColor> productColors = new ArrayList<PdProductColor>();

				for (String colorName : splitedColors) {

					for (SyCodeDetail colorCode : pair.getSecond().get(CommonCode.PRDT_COLOR_CODE)) {
						if (UtilsText.equals(colorCode.getCodeDtlName().toLowerCase(),
								colorName.trim().toLowerCase())) {
							PdProductColor productColor = new PdProductColor();
							productColor.setPrdtColorCode(colorCode.getCodeDtlNo());
							if (!productColors.contains(productColor)) {
								productColors.add(productColor);
							}
							break;
						}
					}
				}

				if (UtilsNumber.compare(productColors.size(), 0) > 0) {
					after.setProductColor(productColors.toArray(new PdProductColor[] {}));
				}
			}
		}
		// 바코드1/2는 무시
		after.setPrdtName(before.getSangPumNm()); // 상품명(국문)
		after.setEngPrdtName(before.getSangPumNm()); // 상품명(영문)
		after.setStyleInfo(before.getStyle()); // 스타일
//		before.getAkStyle(); // 자사스타일
		after.setBrandNo(before.getBrandCd()); // 브랜드코드
		after.setBrandName(before.getBrandNm()); // 브랜드이름
		after.setMnftrName(before.getManuFacturer());// 제조사이름
//		before.getSangPumDiv(); // 상품구분. 온/오프라인코드 확인 필요.
//		before.getSex(); // 성별
//		before.getVendCd(); // 거래처코드
		// 원산지
		for (SyCodeDetail item : pair.getSecond().get(CommonCode.ORG_PLACE_CODE)) {
			if (UtilsText.equals(item.getInsdMgmtInfoText(), before.getWonSanJi())) {
				after.setOrgPlaceCode(item.getCodeDtlNo());
				break;
			}
		}
//		before.getUseFlag(); // 사용구분
//		before.getBoxInQty(); // 패킹수량
//		before.getRegDate(); // 등록일시
//		before.getWms(); // WMS 처리일시
//		before.getAbcmart(); // ABC-MART 처리일시
//		before.getOpenMarket(); // 오픈마켓 처리일시
//		before.getErrorStatus(); // 에러상태
//		before.getWorkDiv(); // 상태값
//		before.getMaejangcd(); // 매장코드

		// 최초입고일
		if (UtilsText.isBlank(before.getFirstEntryDate()) || UtilsText.equals("NULL", before.getLastEntryDate())) {
			after.setFirstWrhsDay(new Timestamp(new Date().getTime()));
		} else {
			after.setFirstWrhsDay(new Timestamp(this.dateFormatYYYYMMDD.parse(before.getFirstEntryDate()).getTime()));
		}
		// 최종입고일
		if (UtilsText.isBlank(before.getLastEntryDate()) || UtilsText.equals("NULL", before.getLastEntryDate())) {
			after.setFirstWrhsDay(new Timestamp(new Date().getTime()));
		} else {
			after.setLastWrhsDay(new Timestamp(this.dateFormatYYYYMMDD.parse(before.getLastEntryDate()).getTime()));
		}
		after.setDispFlagText(before.getTierflag()); // A-Connect TierFlag 설정

		after.setPrdtColorInfo(before.getColorNm()); // 상품색상코드(텍스트문구)

		// 매장 등급 표시
		if (UtilsText.isNotBlank(before.getTierflag())) {
			after.setTierFlagCodeName(commonCodeService.getCodeNoName("TIER_FLAG_CODE").stream()
					.filter(x -> UtilsText.equals(x.getCodeDtlName(), before.getTierflag())).toString());
		}

		// AS-IS. 최소구매수량을 1로 설정
		// AS-IS. 최대구매수량을 99999로 설정
	}

	/**
	 * @Desc : 중간계에 ERP상품이 등록되었음을 알림
	 * @Method Name : sendErpProductRegistered
	 * @Date : 2019. 4. 3.
	 * @Author : tennessee
	 * @param product
	 * @throws Exception
	 */
	public void sendErpProductRegistered(PdProduct product) throws Exception {
		this.interfacesProductService.updateProcedureErpSangPumNoTrx(product.getChnnlNo(), product.getVndrPrdtNoText());
	}

	/**
	 * @Desc : ERP 상품옵션 및 재고 조회
	 * @Method Name : getErpProductOptionWithStock
	 * @Date : 2019. 4. 4.
	 * @Author : tennessee
	 * @param search
	 * @return
	 * @throws Exception
	 */
	public List<PdProductOption> getErpProductOptionWithStock(PdProductOptionStockSearchVO search) throws Exception {
		List<PdProductOption> result = new ArrayList<PdProductOption>();

		// 채널번호가 없는 경우, 기본채널 설정
		if (UtilsText.isBlank(search.getChnnlNo())) {
			search.setChnnlNo(CHANNEL_NO_DEFAULT);
		}

		List<InterfacesSangPumDetailToErp> sangpums = this.interfacesProductService
				.selectErpSangPumDetail(search.getVndrPrdtNoText(), search.getChnnlNo());
		List<InterfacesStockToErp> stocks = this.interfacesProductService.selectErpStock(search.getVndrPrdtNoText(),
				search.getChnnlNo());

		PdProductOption tempOption = null;
		int sortSeq = 0;
		for (InterfacesSangPumDetailToErp item : sangpums) {
			tempOption = new PdProductOption();
			tempOption.setPrdtOptnNo(item.getSangPumFullCd().substring(item.getSangPumFullCd().length() - 3));
			tempOption.setOptnName(item.getSizeCd());
			tempOption.setVndrPrdtNoText(item.getSangPumFullCd()); // ERP연동에 사용되는 필드
			tempOption.setOptnAddAmt(0); // 추가금액 기본 설정
			tempOption.setSortSeq(++sortSeq); // 노출순서 기본 설정
			this.setAmount(item.getSangPumFullCd(), stocks, tempOption); // 재고수량 설정
			tempOption.setUseYn("Y"); // 전시여부 기본설정

			result.add(tempOption);
		}
		return result;
	}

	/**
	 * @Desc : 상품수량 반환
	 * @Method Name : getAmount
	 * @Date : 2019. 4. 4.
	 * @Author : tennessee
	 * @param erpProductFullCode
	 * @param stocks
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public void setAmount(String erpProductFullCode, List<InterfacesStockToErp> stocks, PdProductOption productOption)
			throws Exception {
		if (UtilsCollection.isNotEmpty(stocks)) {

			// 기본값 설정
			productOption.setStockAiQty(0);
			productOption.setStockAwQty(0);
			productOption.setStockAsQty(0);
			productOption.setTotalOrderQty(0);
			productOption.setOrderPsbltQty(0);

			stocks.forEach((item) -> {
				if (UtilsText.equals(erpProductFullCode, item.getSangPumFullCd())) {
					switch (item.getCbcd()) {
					case CBCD_CODE_AI:
						productOption.setStockAiQty(Integer.parseInt(item.getQty()));
						productOption.setOrderPsbltQty(
								UtilsNumber.sum(productOption.getOrderPsbltQty(), Integer.parseInt(item.getQty())));
						break;
					case CBCD_CODE_AW:
						productOption.setStockAwQty(Integer.parseInt(item.getQty()));
						productOption.setOrderPsbltQty(
								UtilsNumber.sum(productOption.getOrderPsbltQty(), Integer.parseInt(item.getQty())));
						break;
					case CBCD_CODE_AS:
						productOption.setStockAsQty(Integer.parseInt(item.getQty()));
						productOption.setOrderPsbltQty(
								UtilsNumber.sum(productOption.getOrderPsbltQty(), Integer.parseInt(item.getQty())));
						break;
					default:
						log.debug("확인되지 않은 유형입니다. InterfacesStockToErp.getCbcd() : {}", item.getCbcd());
					}
				}
			});
		}
	}

	/**
	 * @Desc : 상품에 ERP감가정보를 입력
	 * @Method Name : convertPrice
	 * @Date : 2019. 4. 4.
	 * @Author : tennessee
	 * @param gamga
	 * @param product
	 * @throws Exception
	 */
	public void convertPrice(List<InterfacesGamGaToErp> gamga, PdProduct product) throws Exception {
		if (UtilsCollection.isNotEmpty(gamga)) {
			PdProductPriceHistory[] result = new PdProductPriceHistory[1];

			result[0] = new PdProductPriceHistory();

			// 적용일자는 등록 시 현재날짜와 최대날짜로 입력되므로 입력하지 않음
//			result.setApplyStartDtm(new Timestamp(c.getTimeInMillis()));
//			result.setApplyEndDtm(new Timestamp(c.getTimeInMillis()));
			result[0].setNormalAmt(Integer.parseInt(gamga.get(0).getMgAmt())); // 정상가

			result[0].setErpSellAmt(Integer.parseInt(gamga.get(0).getGamgaAmt())); // 오프라인판매가
			result[0].setOnlnSellAmt(Integer.parseInt(gamga.get(0).getGamgaAmt())); // 온라인판매가

			// 판매가(노출가) 설정
			if (UtilsNumber.compare(result[0].getErpSellAmt(), 0) > 0
					&& UtilsNumber.compare(result[0].getOnlnSellAmt(), result[0].getErpSellAmt()) > 0) {
				// 오프라인판매가가 0보다 크고, 온라인 판매가보다 적은 경우
				result[0].setSellAmt(result[0].getErpSellAmt());
			} else {
				result[0].setSellAmt(result[0].getOnlnSellAmt());
			}

			result[0].setErpDscntRate((short) gamga.get(0).getErpDscntRate()); // 오프라인할인율
			result[0].setOnlnDscntRate((short) gamga.get(0).getErpDscntRate()); // 온라인할인율
//			result[0].setEmpDscntRate(Short.parseShort(gamga.get(0).getStaff())); // 임직원할인율

			this.productReflectionService.setUserInfo(result[0]);

			product.setProductPriceHistory(result);
		} else {
			product.setProductPriceHistory(new PdProductPriceHistory[1]);
		}
	}

	/**
	 * @Desc : 자사상품 승인관리 목록 조회
	 * @Method Name : selectInterfaceNonErpSangPum
	 * @Date : 2019. 4. 17.
	 * @Author : hsjhsj19
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<InterfacesSangPumToErp> selectInterfaceNonErpSangPum(
			Pageable<InterfacesSangPumToErp, InterfacesSangPumToErp> pageable) throws Exception {

		// IF_GAMGA
		// '0072' : A-RT (ABC, GS)
		// '0350' : OTS
		Integer count = this.interfacesProductService.selectErpSangPumCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(this.interfacesProductService.selectErpSangPum(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 사방넷 재고 등록 작업
	 * @Method Name : getStockForSabangnet
	 * @Date : 2019. 10. 16.
	 * @Author : tennessee
	 * @param product
	 * @throws Exception
	 */
	public void setStockForSabangnet(PdProduct product) throws Exception {
		List<InterfacesStockToErp> stocksBeforeConvert = this.interfacesProductService
				.selectSabangnetStock(product.getVndrPrdtNoText()); // ERP상품코드기반으로 사방넷 재고 조회

		PdProductOptionStockSabangnet productOptionStockSabangnet = null;

		for (PdProductOption option : product.getProductOption()) {

			productOptionStockSabangnet = new PdProductOptionStockSabangnet();
			productOptionStockSabangnet.setPrdtNo(option.getPrdtNo());
			productOptionStockSabangnet.setPrdtOptnNo(option.getPrdtOptnNo());
			productOptionStockSabangnet
					.setStockQty(this.findStockQtyForSabangnet(stocksBeforeConvert, option.getVndrPrdtNoText()));
			productOptionStockSabangnet.setOrderCount(0); // 옵션에 기록된 상품전체코드로 사방넷 재고 검색

			this.productReflectionService.setUserInfo(productOptionStockSabangnet);

			this.productOptionStockSabangnetDao.insert(productOptionStockSabangnet);

		}
	}

	/**
	 * @Desc : 사방넷 재고목록에서 상품전체코드번호가 동일한 옵션을 찾아 재고량을 반환. 기본값은 0.
	 * @Method Name : findStockQtyForSabangnet
	 * @Date : 2019. 10. 16.
	 * @Author : tennessee
	 * @param stocks
	 * @param vndrPrdtNoText
	 * @return
	 * @throws Exception
	 */
	private int findStockQtyForSabangnet(List<InterfacesStockToErp> stocks, String vndrPrdtNoText) throws Exception {
		int result = 0; // 기본값은 0.

		for (InterfacesStockToErp item : stocks) {
			if (UtilsText.equals(item.getSangPumFullCd(), vndrPrdtNoText)) {
				try {
					result = Integer.parseInt(item.getQty());
				} catch (NumberFormatException nfe) {
					result = 0; // 재고량 값 변환에 실패한 경우, 0으로 설정.
				}
				break;
			}
		}
		return result;
	}

	public void getPrdtApprSearchCondition(Parameter<?> parameter) throws Exception {

		// 사이트 채널 코드
		Pair<JSONObject, List<SySiteChnnl>> chnnlList = this.siteService.getUseChannelListByCombo();
		parameter.addAttribute("searchConditionSiteChannelList", chnnlList.getSecond());
		parameter.addAttribute("codeCombo", chnnlList.getFirst());

		// 매장 등급 코드
		String[] codeFields = { CommonCode.TIER_FLAG_CODE };
		Map<String, List<SyCodeDetail>> codeList = commonCodeService.getAllCodeListByGroup(codeFields);

		List<SyCodeDetail> tierFlagCodeList = codeList.get(codeFields[0]);

		String codeName = tierFlagCodeList.stream().map(SyCodeDetail::getCodeDtlName).collect(Collectors.joining("|"));
		String codeAddInfo1 = tierFlagCodeList.stream().map(SyCodeDetail::getAddInfo1).collect(Collectors.joining("|"));

		JSONObject json = new JSONObject();
		json.put("name", codeName);
		json.put("addInfo1", codeAddInfo1);

		parameter.addAttribute("tierFlagCodeCombo", json);
		parameter.addAttribute("tierFlagCodeList", tierFlagCodeList);

	}

}
