package kr.co.abcmart.bo.product.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.abcmart.bo.display.model.master.DpCategory;
import kr.co.abcmart.bo.display.service.DisplayCategoryService;
import kr.co.abcmart.bo.product.model.master.CmProductIcon;
import kr.co.abcmart.bo.product.model.master.DpBrand;
import kr.co.abcmart.bo.product.model.master.DpSizeChart;
import kr.co.abcmart.bo.product.model.master.PdProduct;
import kr.co.abcmart.bo.product.model.master.PdProductColor;
import kr.co.abcmart.bo.product.model.master.PdProductIcon;
import kr.co.abcmart.bo.product.model.master.PdProductPriceHistory;
import kr.co.abcmart.bo.product.model.master.SyStandardCategory;
import kr.co.abcmart.bo.product.service.BrandService;
import kr.co.abcmart.bo.product.service.ProductIconService;
import kr.co.abcmart.bo.product.service.ProductService;
import kr.co.abcmart.bo.product.service.SizeChartService;
import kr.co.abcmart.bo.product.service.StandardCategoryService;
import kr.co.abcmart.bo.product.vo.PdProductSearchVO;
import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.bo.system.model.master.SySite;
import kr.co.abcmart.bo.system.model.master.SySiteChnnl;
import kr.co.abcmart.bo.system.service.CommonCodeService;
import kr.co.abcmart.bo.system.service.SiteService;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.request.FileUpload;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.common.request.ParameterMap;
import kr.co.abcmart.common.util.UtilsObject;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.interfaces.util.UtilsText;
import kr.co.abcmart.user.LoginManager;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("product/mass-product")
public class MassProductController extends BaseController {

	@Autowired
	private ProductService productService;

	@Autowired
	private CommonCodeService commonCodeService;

	@Autowired
	private SiteService siteService;

	@Autowired
	private StandardCategoryService standardCategoryService;

	@Autowired
	private DisplayCategoryService displayCategoryService;

	@Autowired
	private BrandService brandService;

	@Autowired
	private ProductIconService productIconService;

	@Autowired
	private SizeChartService sizeChartService;

	@SuppressWarnings("serial")
	private final List<String> HEADER_EXCEL = new ArrayList<String>() {
		{
			add("siteNo"); // 사이트번호
			add("vndrPrdtNoText"); // 상품코드 (판매자)
			add("prdtName"); // 상품명 (국문)
			add("engPrdtName"); // 상품명 (영문)
			add("brandNo"); // 브랜드번호
			add("sellStatCode"); // 판매상태코드
			add("sellStartDtm"); // 판매기간 (시작일)
			add("sellEndDtm"); // 판매기간 (종료일)
			add("stdCtgrNo"); // 표준카테고리번호
			add("chnnlNo"); // 전시채널코드
			add("stdrCtgrNo"); // 전시카테고리번호
			add("rsvPrdtYn"); // 예약상품여부
			add("itemCode"); // 픔목코드
			add("mnftrName"); // 제조사
			add("orgPlaceCode"); // 제조국/원산지
			add("styleInfo"); // 스타일
			add("prdtColorInfo"); // 색상정보
			add("empDscntYn"); // 임직원 할인제외 여부
			add("normalAmt"); // 정상가
			add("sellAmt"); // 판매가
			add("freeDlvyYn"); // 무료배송 상품 여부
			add("dispYn"); // 전시여부
			add("insdMgmtInfoText"); // 상품아이콘 (내부관리정보)
			add("srchPsbltYn"); // 검색가능여부
			add("srchKeyWordText"); // 검색키워드
			add("sizeChartSeq"); // 사이즈 가이드 번호
			add("prdtColorCode"); // 색상코드
			add("buyLimitYn"); // 구매수량 제한 여부
			add("minBuyPsbltQty"); // 최소구매수량
			add("maxBuyPsbltQty"); // 최대구매수량
			add("dayMaxBuyPsbltQty"); // 1일 최대구매수량
		}
	};

	@SuppressWarnings("serial")
	private final List<String> HEADER_EXCEL_NAME = new ArrayList<String>() {
		{
			add("사이트번호");
			add("상품코드 (판매자)");
			add("상품명 (국문)");
			add("상품명 (영문)");
			add("브랜드번호");
			add("판매상태코드");
			add("판매기간 (시작일)");
			add("판매기간 (종료일)");
			add("표준카테고리번호");
			add("전시채널코드");
			add("전시카테고리번호");
			add("예약상품여부");
			add("픔목코드");
			add("제조사");
			add("제조국/원산지");
			add("스타일");
			add("색상정보");
			add("임직원 할인제외 여부");
			add("정상가");
			add("판매가");
			add("무료배송 상품 여부");
			add("전시여부");
			add("상품아이콘 (내부관리정보)");
			add("검색가능여부");
			add("검색키워드");
			add("사이즈 가이드 번호");
			add("색상코드");
			add("구매수량 제한 여부");
			add("최소구매수량");
			add("최대구매수량");
			add("1일 최대구매수량");
		}
	};

	/**
	 * @Desc : 대랑샹품등록 진입
	 * @Method Name : getList
	 * @Date : 2019. 8. 30.
	 * @Author : hsjhsj19
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping
	public ModelAndView getList(Parameter<?> parameter) throws Exception {
		log.debug("대량상품등록 페이지");

		// 화면에 사용되는 공통코드 조회
		String[] codeFields = { CommonCode.SELL_STAT_CODE };
		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = this.commonCodeService
				.getCodeListByGroupByCombo(codeFields, false);
		JSONObject gridCodes = pair.getFirst();
		// 사이트 목록 조회
		gridCodes.put("SITE_NO", siteService.getSiteListByCombo().getFirst());
		// 채널 목록 조회
		gridCodes.put("CHNNL_NO", siteService.getUseChannelListByCombo().getFirst());

		parameter.addAttribute("gridCodes", gridCodes); // 그리드 공통코드. 판매상태

		return forward("/product/mass-product/mass-product-list");
	}

	/**
	 * @Desc : 액셀폼 다운로드
	 * @Method Name : downloadAsExcelFile
	 * @Date : 2019. 8. 30.
	 * @Author : hsjhsj19
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/download/excel")
	public void downloadAsExcelFile(Parameter<PdProductSearchVO> parameter) throws Exception {

		// 채널 목록
		List<SySiteChnnl> chnnl = this.getChnnlList();
		// 브랜드 목록
		List<DpBrand> brand = this.getBrandList();
		// 표준카테고리 목록
		List<SyStandardCategory> standardCategory = this.getStandardCategoryList();
		// 전시카테고리 목록
		List<DpCategory> displayCategory = this.getDisplayCategoryList(chnnl);
		// 상품아이콘 목록
		List<CmProductIcon> productIcon = this.getProductIconList(siteService.getSiteList());
		// 사이즈가이드 목록
		List<DpSizeChart> sizeChart = this.getSizeChartList();
		// 판매상태코드, 색상코드, 원산지코드, 품목코드
		String[] codeFields = { CommonCode.SELL_STAT_CODE, CommonCode.PRDT_COLOR_CODE, CommonCode.ORG_PLACE_CODE,
				CommonCode.ITEM_CODE };
		Map<String, List<SyCodeDetail>> codeDetail = this.getCodeList(codeFields).getSecond();

		// 파일 읽기
		FileInputStream fis = new FileInputStream(parameter.getRequest().getServletContext()
				.getRealPath("/WEB-INF/views/product/mass-product/excel/massProductList.xlsx"));
		XSSFWorkbook workbook = new XSSFWorkbook(fis);

		setListToSheet(workbook, "전시채널", chnnl);
		setListToSheet(workbook, "브랜드", brand);
		setListToSheet(workbook, "판매상태", codeDetail.get(CommonCode.SELL_STAT_CODE));
		setListToSheet(workbook, "표준카테고리", standardCategory);
		setListToSheet(workbook, "전시카테고리", displayCategory);
		setListToSheet(workbook, "품목", codeDetail.get(CommonCode.ITEM_CODE));
		setListToSheet(workbook, "원산지", codeDetail.get(CommonCode.ORG_PLACE_CODE));
		setListToSheet(workbook, "상품아이콘(수동)", productIcon);
		setListToSheet(workbook, "사이즈가이드", sizeChart);
		setListToSheet(workbook, "상품색상", codeDetail.get(CommonCode.PRDT_COLOR_CODE));

		FileOutputStream fos = new FileOutputStream(parameter.getRequest().getServletContext()
				.getRealPath("/WEB-INF/views/product/mass-product/excel/massProductList.xlsx"));
		workbook.write(fos);
		fos.close();
		fis.close();

		parameter.downloadExcelTemplate("product/mass-product/excel/massProductList");
	}

	/**
	 * @Desc : sheet항목 헤더 채우기
	 * @Method Name : setListToSheet
	 * @Date : 2019. 7. 19.
	 * @Author : hsjhsj19
	 * @param workbook
	 * @param sheetName
	 * @param list
	 */
	private void setListToSheet(XSSFWorkbook workbook, String sheetName, List<?> list) {
		XSSFSheet sheet = workbook.getSheet(sheetName);

		// 리스트 채우기 전 모든 행 삭제
		int lastNum = sheet.getLastRowNum();
		for (int i = lastNum; 0 < i; i--) {
			sheet.removeRow(sheet.getRow(i));
		}

		List<String> columnNames = getColumnNames(sheetName);
		switch (sheetName) {
		case "전시채널":
			list = (List<SySiteChnnl>) list;
			break;
		case "브랜드":
			list = (List<DpBrand>) list;
			break;
		case "판매상태":
			list = (List<SyCodeDetail>) list;
			break;
		case "표준카테고리":
			list = (List<SyStandardCategory>) list;
			break;
		case "전시카테고리":
			list = (List<DpCategory>) list;
			break;
		case "품목":
			list = (List<SyCodeDetail>) list;
			break;
		case "원산지":
			list = (List<SyCodeDetail>) list;
			break;
		case "상품아이콘(수동)":
			list = (List<CmProductIcon>) list;
			break;
		case "사이즈가이드":
			list = (List<DpSizeChart>) list;
			break;
		case "상품색상":
			list = (List<SyCodeDetail>) list;
			break;
		default:
			break;
		}

		forList(sheet, columnNames, list);
		setSize(sheet, columnNames);
	}

	private void setSize(XSSFSheet sheet, List<String> columnNames) {
		for (int i = 0; i < columnNames.size(); i++) {
			sheet.autoSizeColumn(i);
		}
	}

	/**
	 * @Desc : 헤더시트
	 * @Method Name : getColumnNames
	 * @Date : 2019. 7. 19.
	 * @Author : hsjhsj19
	 * @param sheetName
	 * @return
	 */
	private List<String> getColumnNames(String sheetName) {
		List<String> columnNames = new ArrayList<String>();
		switch (sheetName) {
		case "상품등록":
			columnNames.add("사이트번호");
			columnNames.add("상품코드 (판매자)");
			columnNames.add("상품명 (국문)");
			columnNames.add("상품명 (영문)");
			columnNames.add("브랜드번호	");
			columnNames.add("판매상태코드");
			columnNames.add("판매기간 (시작일)");
			columnNames.add("판매기간 (종료일)");
			columnNames.add("표준카테고리코드	");
			columnNames.add("전시채널코드");
			columnNames.add("전시카테고리코드");
			columnNames.add("예약상품여부");
			columnNames.add("품목코드");
			columnNames.add("제조사");
			columnNames.add("제조국/원산지");
			columnNames.add("스타일");
			columnNames.add("색상정보");
			columnNames.add("임직원 할인제외 여부");
			columnNames.add("정상가");
			columnNames.add("판매가");
			columnNames.add("무료배송 상품 여부");
			columnNames.add("전시여부");
			columnNames.add("상품아이콘코드");
			columnNames.add("검색가능여부");
			columnNames.add("검색키워드	");
			columnNames.add("사이즈가이드코드");
			columnNames.add("색상코드");
			columnNames.add("구매수량 제한 여부");
			columnNames.add("최소구매수량");
			columnNames.add("최대구매수량");
			columnNames.add("1일 최대구매수량");
		case "전시채널":
			columnNames.add("채널번호");
			columnNames.add("사이트번호");
			columnNames.add("채널명");
			break;
		case "브랜드":
			columnNames.add("브랜드번호");
			columnNames.add("사이트번호");
			columnNames.add("브랜드명(국문)");
			columnNames.add("브랜드명(영문)");
			break;
		case "판매상태":
			columnNames.add("상품판매코드");
			columnNames.add("상품판매코드명");
			break;
		case "표준카테고리":
			columnNames.add("표준카테고리코드");
			columnNames.add("품목코드");
			columnNames.add("표준카테고리명");
			break;
		case "전시카테고리":
			columnNames.add("전시카테고리코드");
			columnNames.add("전시카테고리명");
			columnNames.add("사이트번호");
			columnNames.add("채널번호");
			columnNames.add("채널명");
			break;
		case "품목":
			columnNames.add("품목코드");
			columnNames.add("품목코드명");
			break;
		case "원산지":
			columnNames.add("원산지코드");
			columnNames.add("원산지코드명");
			break;
		case "상품아이콘(수동)":
			columnNames.add("상품아이콘코드");
			columnNames.add("사이트번호");
			columnNames.add("상품아이콘코드명");
			break;
		case "사이즈가이드":
			columnNames.add("사이즈가이드번호");
			columnNames.add("브랜드번호");
			columnNames.add("표준카테고리코드");
			columnNames.add("사이즈가이드명");
			break;
		case "상품색상":
			columnNames.add("상품색상코드");
			columnNames.add("상품색상코드명");
			break;
		default:
			break;
		}

		return columnNames;
	}

	/**
	 * @Desc : 엑셀폼 다운로드시 코드,번호 참고 도움이 될 조회항목들 등록
	 * @Method Name : forList
	 * @Date : 2019. 7. 19.
	 * @Author : hsjhsj19
	 * @param sheet
	 * @param columnNames
	 * @param list
	 */
	private void forList(XSSFSheet sheet, List<String> columnNames, List<?> list) {
		int targetRow = 0; // 생성할 행
		int targetCell = 0; // 생성할 열
		XSSFRow row = sheet.createRow(targetRow++);
		XSSFCell cell = null;

		for (String columnName : columnNames) {
			cell = row.createCell(targetCell++);
			cell.setCellValue(columnName);
		}

		targetCell = 0;

		for (int i = 0; i < list.size(); i++) {
			Object obj = list.get(i);
			if (obj instanceof SySiteChnnl) {

				SySiteChnnl site = (SySiteChnnl) obj;
				row = sheet.createRow(targetRow++);
				cell = row.createCell(targetCell++);
				cell.setCellValue(site.getChnnlNo());
				cell = row.createCell(targetCell++);
				cell.setCellValue(site.getSiteNo());
				cell = row.createCell(targetCell++);
				cell.setCellValue(site.getChnnlName());

			} else if (obj instanceof DpBrand) {

				DpBrand brand = (DpBrand) obj;
				row = sheet.createRow(targetRow++);
				cell = row.createCell(targetCell++);
				cell.setCellValue(brand.getBrandNo());
				cell = row.createCell(targetCell++);
				cell.setCellValue(UtilsObject.isEmpty(brand.getSiteNo()) ? "공통" : brand.getSiteNo());
				cell = row.createCell(targetCell++);
				cell.setCellValue(brand.getBrandName());
				cell = row.createCell(targetCell++);
				cell.setCellValue(brand.getBrandEnName());

			} else if (obj instanceof SyCodeDetail) {

				SyCodeDetail code = (SyCodeDetail) obj;
				row = sheet.createRow(targetRow++);
				cell = row.createCell(targetCell++);
				cell.setCellValue(code.getCodeDtlNo());
				cell = row.createCell(targetCell++);
				cell.setCellValue(code.getCodeDtlName());

			} else if (obj instanceof SyStandardCategory) {

				SyStandardCategory category = (SyStandardCategory) obj;
				row = sheet.createRow(targetRow++);
				cell = row.createCell(targetCell++);
				cell.setCellValue(category.getStdCtgrNo());
				cell = row.createCell(targetCell++);
				cell.setCellValue(category.getItemCode());
				cell = row.createCell(targetCell++);
				cell.setCellValue(category.getStdCtgrName());

			} else if (obj instanceof DpCategory) {

				DpCategory category = (DpCategory) obj;
				row = sheet.createRow(targetRow++);
				cell = row.createCell(targetCell++);
				cell.setCellValue(category.getCtgrNo());
				cell = row.createCell(targetCell++);
				cell.setCellValue(category.getCtgrName());
				cell = row.createCell(targetCell++);
				cell.setCellValue(category.getSiteNo());
				cell = row.createCell(targetCell++);
				cell.setCellValue(category.getChnnlNo());
				cell = row.createCell(targetCell++);
				cell.setCellValue(category.getChnnlName());

			} else if (obj instanceof CmProductIcon) {

				CmProductIcon icon = (CmProductIcon) obj;
				row = sheet.createRow(targetRow++);
				cell = row.createCell(targetCell++);
				cell.setCellValue(icon.getInsdMgmtInfoText());
				cell = row.createCell(targetCell++);
				cell.setCellValue(icon.getSiteNo());
				cell = row.createCell(targetCell++);
				cell.setCellValue(icon.getDispIconName());

			} else if (obj instanceof DpSizeChart) {

				DpSizeChart size = (DpSizeChart) obj;
				row = sheet.createRow(targetRow++);
				cell = row.createCell(targetCell++);
				cell.setCellValue(size.getSizeChartSeq());
				cell = row.createCell(targetCell++);
				cell.setCellValue(size.getBrandNo());
				cell = row.createCell(targetCell++);
				cell.setCellValue(size.getStdCtgrNo());
				cell = row.createCell(targetCell++);
				cell.setCellValue(size.getSizeChartName());

			}

			targetCell = 0;
		}
	}

	/**
	 * @Desc : 정상등록건을 확인하기 위한 파일 업로드
	 * @Method Name : uploadAsExcelFile
	 * @Date : 2019. 7. 19.
	 * @Author : hsjhsj19
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/upload/excel")
	public void uploadAsExcelFile(Parameter<?> parameter) throws Exception {

		FileUpload uploadFile = parameter.getUploadFile("excelUpload");
		XSSFWorkbook workbook = new XSSFWorkbook(uploadFile.getMultiPartFile().getInputStream());
		XSSFSheet sheet = workbook.getSheetAt(0);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		int emptyCount = 0;
		for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
			
			// getPhysicalNumberOfRows() 이부분은사용자가 엑셀을 입력한 Row의 수를 캐치하여 리턴해주는 부분이다.
			// 허나 이부분이 간혹 사용자가 입력한 Row가 아닌데도 읽어들여 입력한 Row이상의 수를 리턴하는 경우가 종종 발생한다.
            // row == null로 잘못 인식되지 않게한다. 2020.05.12
			XSSFRow row = sheet.getRow(i);
			if(UtilsObject.isEmpty(row)) {
				break;
			}
			emptyCount = 0;
			Map<String, Object> map = new HashMap<String, Object>();
			
			for (int j = 0; j < HEADER_EXCEL.size(); j++) {
				XSSFCell cell = row.getCell(j);
				String value = "";
				if (cell != null) {
					switch (cell.getCellType()) {
					case XSSFCell.CELL_TYPE_FORMULA:
						value = cell.getCellFormula();
						break;
					case XSSFCell.CELL_TYPE_NUMERIC:
						value = cell.getNumericCellValue() + "";
						break;
					case XSSFCell.CELL_TYPE_STRING:
						value = cell.getStringCellValue() + "";
						break;
					case XSSFCell.CELL_TYPE_BLANK:
						value = "";
						break;
					case XSSFCell.CELL_TYPE_ERROR:
						value = cell.getErrorCellValue() + "";
						break;
					default:
						log.debug(value);
						break;
					}
				}
				map.put(HEADER_EXCEL.get(j), value);
				
				//빈값만 들어간 경우 count 추가
				if(UtilsText.isBlank(value)) {
					emptyCount++;
				}
			}
			
			//전체가 빈칸일경우 해당항목 추가 하지 않게 수정
			if(emptyCount == HEADER_EXCEL.size()) {
				break;
			}
			list.add(map);
			
		}

		if (list.size() < 1) {
			throw new Exception("등록할 데이터가 존재하지 않습니다.");
		}

		// 채널 목록
		List<SySiteChnnl> chnnl = this.getChnnlList();
		// 브랜드 목록
		List<DpBrand> brand = this.getBrandList();
		// 표준카테고리 목록
		List<SyStandardCategory> standardCategory = this.getStandardCategoryList();
		// 전시카테고리 목록
		List<DpCategory> displayCategory = this.getDisplayCategoryList(chnnl);
		// 상품아이콘 목록
		List<CmProductIcon> productIcon = this.getProductIconList(siteService.getSiteList());
		// 사이즈가이드 목록
		List<DpSizeChart> sizeChart = this.getSizeChartList();
		// 판매상태코드, 색상코드, 원산지코드, 품목코드
		String[] codeFields = { CommonCode.SELL_STAT_CODE, CommonCode.PRDT_COLOR_CODE, CommonCode.ORG_PLACE_CODE,
				CommonCode.ITEM_CODE };
		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = this.getCodeList(codeFields);

		// 정상등록 가능 목록
		List<Map<String, Object>> normalList = new ArrayList<Map<String, Object>>();
		// 에러 메세지
		StringBuilder errorMsg = new StringBuilder("");

		// 데이터 검증
		for (int i = 0; i < list.size(); i++) {
			boolean bool = true;

			// 엑셀 row
			Map<String, Object> map = list.get(i);

			// 필수여부 체크
			if (!(bool = validYn(map, HEADER_EXCEL, errorMsg, i))) {
				continue;
			}

			// 날짜 체크
			if (!(bool = validDate(map, errorMsg, i))) {
				continue;
			}

			// 숫자인지 체크
			if (!(bool = validNumber(map, errorMsg, i))) {
				continue;
			}

			// 2020.05.12 추가 : 정상가 >= 판매가 체크
			if (!(bool = validSellAmt(map, errorMsg, i))) {
				continue;
			}
			
			// 유효 코드인지 체크
			if (!(bool = validCode(pair, codeFields, map, errorMsg, i))) {
				continue;
			}

			if (UtilsObject.isNotEmpty(chnnl)) {
				// 유효한 입력인지 체크(사이트)
				if (!(bool = validSearchList(chnnl, map, errorMsg, i))) {
					continue;
				}
			}

			if (UtilsObject.isNotEmpty(brand)) {
				// 유효한 입력인지 체크(브랜드)
				if (!(bool = validSearchList(brand, map, errorMsg, i))) {
					continue;
				}
			}

			if (UtilsObject.isNotEmpty(standardCategory)) {
				// 유효한 입력인지 체크(표준카테고리)
				if (!(bool = validSearchList(standardCategory, map, errorMsg, i))) {
					continue;
				}
			}

			if (UtilsObject.isNotEmpty(displayCategory)) {
				// 유효한 입력인지 체크(전시카테고리)
				if (!(bool = validSearchList(displayCategory, map, errorMsg, i))) {
					continue;
				}
			}

			if (UtilsObject.isNotEmpty(productIcon)) {
				// 유효한 입력인지 체크(상품아이콘)
				if (!(bool = validSearchList(productIcon, map, errorMsg, i))) {
					continue;
				}
			}

			if (UtilsObject.isNotEmpty(sizeChart)) {
				// 유효한 입력인지 체크(사이즈가이드)
				if (!(bool = validSearchList(sizeChart, map, errorMsg, i))) {
					continue;
				}
			}

			if (bool) {
				// 200건 단위 제한
				if (200 < normalList.size()) {
					errorMsg.append("등록은 200건 이하로 제한되어 있습니다.");
					break;
				}
				normalList.add(map);
			}
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("errorMsg",
				errorMsg.toString().endsWith("\n") ? errorMsg.delete(errorMsg.length() - 1, errorMsg.length())
						: errorMsg);
		map.put("excelList", list);
		map.put("normalList", normalList);

		this.writeJson(parameter, map);
	}

	/**
	 * @Desc : 조회항목과 일치하는 값인지 확인
	 * @Method Name : validSearchList
	 * @Date : 2019. 7. 19.
	 * @Author : hsjhsj19
	 * @param searchList
	 * @param map
	 * @param errorMsg
	 * @param row
	 * @return
	 */
	private boolean validSearchList(List<?> searchList, Map<String, Object> map, StringBuilder errorMsg, int row) {
		boolean bool = false;
		Object type = searchList.get(0);
		if (type instanceof SySiteChnnl) {
			if (UtilsObject.isNotEmpty(map.get("siteNo")) && UtilsObject.isNotEmpty(map.get("chnnlNo"))) {
				String siteNo = this.sliceDot("siteNo", map);
				String chnnlNo = this.sliceDot("chnnlNo", map);
				for (SySiteChnnl obj : (List<SySiteChnnl>) searchList) {
					if (obj.getSiteNo().equals(siteNo) && obj.getChnnlNo().equals(chnnlNo)) {
						bool = true;
						break;
					}
				}
			}
		} else if (type instanceof DpBrand) {
			if (UtilsObject.isNotEmpty(map.get("brandNo"))) {
				String brandNo = this.sliceDot("brandNo", map);
				String siteNo = this.sliceDot("siteNo", map);
				for (DpBrand obj : (List<DpBrand>) searchList) {
					if (obj.getBrandNo().equals(brandNo)) {
						boolean isRight = false;
						if (UtilsObject.isNotEmpty(obj.getSiteNo())) {
							if (obj.getSiteNo().equals(siteNo)) {
								isRight = true;
							}
						} else {
							// 전체는 null
							isRight = true;
						}
						if (isRight) {
							map.put("brandName", obj.getBrandName());
							bool = true;
							break;
						}
					}
				}
			}
		} else if (type instanceof SyStandardCategory) {
			if (UtilsObject.isNotEmpty(map.get("stdCtgrNo"))) {
				String stdCtgrNo = this.sliceDot("stdCtgrNo", map);
				String itemCode = this.sliceDot("itemCode", map);
				for (SyStandardCategory obj : (List<SyStandardCategory>) searchList) {
					// if (obj.getStdCtgrNo().equals(stdCtgrNo) &&
					// obj.getItemCode().equals(itemCode)) {
					if (obj.getStdCtgrNo().equals(stdCtgrNo)) {
						map.put("stdCtgrName", obj.getStdCtgrName());
						bool = true;
						break;
					}
				}
			}
		} else if (type instanceof DpCategory) {
			if (UtilsObject.isNotEmpty(map.get("stdrCtgrNo"))) {
				String stdrCtgrNo = this.sliceDot("stdrCtgrNo", map);
				String siteNo = this.sliceDot("siteNo", map);
				String chnnlNo = this.sliceDot("chnnlNo", map);
				String[] values = stdrCtgrNo.split(",");
				String stdrCtgrName = "";
				int count = 0;
				for (DpCategory obj : (List<DpCategory>) searchList) {
					if (obj.getSiteNo().equals(siteNo) && obj.getChnnlNo().equals(chnnlNo)) {
						String ctgrNo = obj.getCtgrNo();
						if (1 < values.length) { // 복수
							for (int i = 0; i < values.length; i++) {
								String value = values[i];
								if (ctgrNo.equals(value)) {
									stdrCtgrName += obj.getCtgrName() + ",";
									count++;
								}
							}
						} else { // 단수
							if (ctgrNo.equals(stdrCtgrNo)) {
								map.put("stdrCtgrName", obj.getCtgrName());
								bool = true;
								break;
							}
						}
					}
				}

				if (1 < values.length && values.length == count) {
					if (stdrCtgrName.endsWith(",")) {
						stdrCtgrName = stdrCtgrName.substring(0, stdrCtgrName.lastIndexOf(","));
					}
					map.put("stdrCtgrName", stdrCtgrName);
					bool = true;
				}
			}
		} else if (type instanceof CmProductIcon) {
			if (UtilsObject.isNotEmpty(map.get("insdMgmtInfoText"))) {
				String insdMgmtInfoText = this.sliceDot("insdMgmtInfoText", map);
				String siteNo = this.sliceDot("siteNo", map);
				for (CmProductIcon obj : (List<CmProductIcon>) searchList) {
					if (obj.getInsdMgmtInfoText().equals(insdMgmtInfoText) && obj.getSiteNo().equals(siteNo)) {
						bool = true;
						break;
					}
				}
			} else {
				bool = true;
			}
		} else if (type instanceof DpSizeChart) {
			if (UtilsObject.isNotEmpty(this.sliceDot("sizeChartSeq", map))) {
				Integer sizeChartSeq = (int) Double.parseDouble(this.sliceDot("sizeChartSeq", map));
				String brandNo = this.sliceDot("brandNo", map);
				String stdCtgrNo = this.sliceDot("stdCtgrNo", map);
				for (DpSizeChart obj : (List<DpSizeChart>) searchList) {
					// 사이즈조건표는 브랜드번호 표준카테고리번호가 일치해야 사용 가능
					if (obj.getSizeChartSeq().equals(sizeChartSeq) && obj.getStdCtgrNo().equals(stdCtgrNo)
							&& obj.getBrandNo().equals(brandNo)) {
						bool = true;
						break;
					}
				}
			} else {
				bool = true;
			}
		}

		if (!bool) {
			errorMsg.append((row + 1) + "행 ");
			if (type instanceof SySiteChnnl) {
				errorMsg.append("사이트/채널코드가");
			} else if (type instanceof DpBrand) {
				errorMsg.append("브랜드코드가");
			} else if (type instanceof SyStandardCategory) {
				errorMsg.append("기준카테고리코드가");
			} else if (type instanceof DpCategory) {
				errorMsg.append("전시카테고리코드가");
			} else if (type instanceof CmProductIcon) {
				errorMsg.append("아이콘코드가");
			} else if (type instanceof DpSizeChart) {
				errorMsg.append("사이즈코드가");
			}
			errorMsg.append(" 일치하지 않습니다.\n");
		}

		return bool;
	}

	/**
	 * @Desc : 코드값이 정상적인지 확인
	 * @Method Name : validCode
	 * @Date : 2019. 7. 19.
	 * @Author : hsjhsj19
	 * @param pair
	 * @param codeFields
	 * @param map
	 * @param errorMsg
	 * @param row
	 * @return
	 */
	private boolean validCode(Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair, String[] codeFields,
			Map<String, Object> map, StringBuilder errorMsg, int row) {
		boolean bool = true;
		Map<String, List<SyCodeDetail>> codeMap = pair.getSecond();
		for (String key : codeFields) {
			bool = false;
			String camelCase = JdbcUtils.convertUnderscoreNameToPropertyName(key); // ABC_STRING -> abcString
			String value = (String) map.get(camelCase); // 입력값
			String[] values = value.split(",");
			String codeNames = "";
			int count = 0;
			for (SyCodeDetail codeDetail : codeMap.get(key)) {
				if (1 < values.length) { // 복수
					for (String val : values) {
						if (codeDetail.getCodeDtlNo().equals(val.trim())) {
							codeNames += codeDetail.getCodeDtlName() + ",";
							count++;
						}
					}
				} else { // 단수
					if (codeDetail.getCodeDtlNo().equals(value.trim())) {
						map.put(camelCase + "Name", codeDetail.getCodeDtlName());
						bool = true;
					}
				}
			}

			if (1 < values.length && values.length == count) { // 복수
				if (codeNames.endsWith(",")) {
					codeNames = codeNames.substring(0, codeNames.lastIndexOf(","));
				}
				map.put(camelCase + "Name", codeNames);
			}

			if (!bool) {
				errorMsg.append((row + 1) + "행 입력한 코드값 " + value + "이 유효하지 않습니다.\n");
				return bool;
			}

		}
		return bool;
	}

	/**
	 * @Desc : 숫자인지 확인
	 * @Method Name : validNumber
	 * @Date : 2019. 7. 19.
	 * @Author : hsjhsj19
	 * @param map
	 * @param errorMsg
	 * @param row
	 * @return
	 */
	private boolean validNumber(Map<String, Object> map, StringBuilder errorMsg, int row) {
		String[] numberList = { "normalAmt" // 정상가
				, "sellAmt" // 판매가
				, "minBuyPsbltQty" // 최소구매수량
				, "maxBuyPsbltQty" // 최대구매수량
				, "dayMaxBuyPsbltQty" // 1일 최대구매수량
		};

		for (String key : numberList) {
			try {
				String value = (String) map.get(key);
				if (UtilsObject.isNotEmpty(value)) {
					Integer number = (int) Double.parseDouble(value);
					map.put(key, number);
				}
			} catch (NumberFormatException e) {
				errorMsg.append((row + 1) + "행 " + findName(key) + "에 숫자를 입력해주세요.\n");
				return false;
			}
		}
		return true;
	}

	/**
	 * @Desc : 정상가 >= 판매가 체크
	 * @Method Name : validSellAmt
	 * @Date : 2020. 5. 12.
	 * @Author : 이강수
	 * @param map
	 * @param errorMsg
	 * @param row
	 * @return
	 */
	private boolean validSellAmt(Map<String, Object> map, StringBuilder errorMsg, int row) {
		
		int normalAmt = (Integer) map.get("normalAmt"); // 정상가
		int sellAmt = (Integer) map.get("sellAmt"); // 정상가
		try {
			if(UtilsObject.isNotEmpty(normalAmt) && UtilsObject.isNotEmpty(sellAmt)) {
				if(normalAmt >= sellAmt) {
					map.put("normalAmt", normalAmt);
					map.put("sellAmt", sellAmt);
				}else {
					throw new Exception();
				}
			}
		} catch (Exception e) {
			errorMsg.append((row + 1) + "행 : 정상가는 판매가보다 클 수 없습니다. (정상가 >= 판매가)\n");
			return false;
		}

		return true;
	}
	
	/**
	 * @Desc : 날짜 형식 확인
	 * @Method Name : validDate
	 * @Date : 2019. 7. 19.
	 * @Author : hsjhsj19
	 * @param map
	 * @param errorMsg
	 * @param row
	 * @return
	 */
	private boolean validDate(Map<String, Object> map, StringBuilder errorMsg, int row) {
		boolean bool = false;
		try {
			if (UtilsObject.isNotEmpty(map.get("sellStartDtm")) && UtilsObject.isNotEmpty(map.get("sellEndDtm"))) {
				// 판매기간 (시작일)
				Date sellStartDate = DateUtil.getJavaDate(Double.parseDouble((String) map.get("sellStartDtm")));
				Timestamp sellStartDtm = new Timestamp(sellStartDate.getTime());
				// 판매기간 (종료일)
				Date sellEndDate = DateUtil.getJavaDate(Double.parseDouble((String) map.get("sellEndDtm")));
				Timestamp sellEndDtm = new Timestamp(sellEndDate.getTime());

				if (sellStartDtm.before(sellEndDtm)) {
					bool = true;
					SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
					map.put("sellStartDtm", format.format(sellStartDate));
					map.put("sellEndDtm", format.format(sellEndDate));
				}
			}

		} catch (Exception e) {
			errorMsg.append((row + 1) + "행 시작일과 종료일의 날짜가 유효하지 않습니다.\n");
			return bool;
		}
		return bool;
	}

	/**
	 * @Desc : 필수여부 체크
	 * @Method Name : validYn
	 * @Date : 2019. 7. 19.
	 * @Author : hsjhsj19
	 * @param map
	 * @param header
	 * @param errorMsg
	 * @param row
	 * @return
	 */
	private boolean validYn(Map<String, Object> map, List<String> header, StringBuilder errorMsg, int row) {
		// 필수여부 Y인 항목
		List<String> ynList = new ArrayList<String>(header);
		ynList.remove("prdtColorInfo"); // 색상정보
		ynList.remove("insdMgmtInfoText"); // 상품아이콘 (내부관리정보)
		ynList.remove("srchKeyWordText"); // 검색키워드
		ynList.remove("sizeChartSeq"); // 사이즈 가이드 번호
		ynList.remove("prdtColorCode"); // 색상코드
		ynList.remove("minBuyPsbltQty"); // 최소구매수량
		ynList.remove("maxBuyPsbltQty"); // 최대구매수량
		ynList.remove("dayMaxBuyPsbltQty"); // 1일 최대구매수량

		boolean isNot = true;
		for (String key : ynList) {
			String obj = (String) map.get(key);
			if (UtilsText.isBlank(obj)) { // 2020.05.12 : "   " 같은 데이터 때문에 수정
				errorMsg.append((row + 1) + "행 " + findName(key) + " 은/는 필수입니다.\n");
				isNot = false;
			}
		}

		return isNot;
	}

	/**
	 * @Desc : 일괄등록
	 * @Method Name : saveExcelList
	 * @Date : 2019. 7. 19.
	 * @Author : hsjhsj19
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/save")
	public void saveExcelList(Parameter<?> parameter) throws Exception {
		// List<Map<String, Object>> list = (List<Map<String, Object>>) parameter.get();
		ParameterMap parameterMap = parameter.getParammeterMap();
		PdProduct[] products = parameter.createArray(PdProduct.class, "vndrPrdtNoText");

		// 채널 목록
		List<SySiteChnnl> chnnl = this.getChnnlList();
		// 전시카테고리 목록
		List<DpCategory> displayCategory = this.getDisplayCategoryList(chnnl);
		// 상품아이콘 목록
		List<CmProductIcon> productIcon = this.getProductIconList(siteService.getSiteList());
		// 색상코드
		String[] codeFields = { CommonCode.PRDT_COLOR_CODE };
		Map<String, List<SyCodeDetail>> codeDetail = this.getCodeList(codeFields).getSecond();

		for (int i = 0; i < products.length; i++) {
			PdProduct product = products[i];
			product.setSiteNo(product.getSiteNo().substring(0,5)); // 2020.05.11 : POI 함수가 double로 읽어와서 에러나는것을 수정
			product.setType(PdProduct.TYPE_TEMPORARY);
			product.setPrdtTypeCode(CommonCode.PRDT_TYPE_CODE_ONLY_ONLINE);
			product.setCntcPrdtSetupYn(Const.BOOLEAN_FALSE); // 연계상품설정여부
			product.setRltnGoodsSetupYn(Const.BOOLEAN_FALSE); // 관련용품설정여부
			product.setAddOptnSetupYn(Const.BOOLEAN_FALSE); // 추가옵션설정여부
			product.setSizeChartDispYn(Const.BOOLEAN_FALSE); // 사이즈조견표전시여부
			product.setOrderMnfctYn(Const.BOOLEAN_FALSE); // 주문제작여부
			product.setDprcExceptYn(Const.BOOLEAN_TRUE); // 감가제외여부
			product.setStorePickupPsbltYn(Const.BOOLEAN_FALSE); // 매장픽업가능여부
			product.setStockIntgrYn(Const.BOOLEAN_FALSE); // 재고통합여부
			product.setStockMgmtYn(Const.BOOLEAN_TRUE); // 재고관리여부
			product.setGenderGbnCode("10003"); // 성별구분코드
			product.setBuyLimitYn(Const.BOOLEAN_FALSE); // 구매제한여부
			product.setWrhsAlertYn(Const.BOOLEAN_FALSE); // 입고알림여부
			product.setRsvPrdtYn(Const.BOOLEAN_FALSE); // 예약상품여부
			product.setAconnectDispYn(Const.BOOLEAN_FALSE); // AConnect전시여부
			product.setIgrmallDispExceptYn(Const.BOOLEAN_FALSE); // 통합몰전시제외여부
			product.setVndrSuspdYn(Const.BOOLEAN_FALSE); // 업체일시중지여부
			product.setAprvStatCode(CommonCode.APRV_STAT_CODE_TEMPORARY); // 승인상태코드
			product.setAffltsSendYn(Const.BOOLEAN_FALSE); // 제휴사전송여부

			if (UtilsObject.isNotEmpty(product.getSizeChartSeq())) {
				product.setSizeChartDispYn(Const.BOOLEAN_TRUE); // 사이즈가이드 전시여부
			}

			// 색상목록
			setInnerClass(codeDetail.get(CommonCode.PRDT_COLOR_CODE), product);

			// 전시카테고리 목록
			setInnerClass(displayCategory, product);

			// 상품아이콘 목록
			if(product.getInsdMgmtInfoText() != null) { // 2020.05.11 : 상품아이콘 null 값 오류 수정
				setInnerClass(productIcon, product);
			}

			// 상품가격 이력
			setInnerClass(product);

			productService.regist(product);

		}

		this.writeJson(parameter, true);
	}

	/**
	 * @Desc : 상품등록시 필요한 1:N 테이블
	 * @Method Name : setInnerClass
	 * @Date : 2019. 7. 18.
	 * @Author : hsjhsj19
	 * @param map
	 */
	private void setInnerClass(PdProduct product) {
		// 상품가격
		if (UtilsObject.isNotEmpty(product.getNormalAmt()) && UtilsObject.isNotEmpty(product.getSellAmt())) {
			PdProductPriceHistory productPriceHistory = new PdProductPriceHistory();
			Integer normalAmt = Integer.parseInt(product.getNormalAmt());
			Integer sellAmt = Integer.parseInt(product.getSellAmt());
			productPriceHistory.setNormalAmt(normalAmt);
			productPriceHistory.setSellAmt(sellAmt);
			productPriceHistory.setOnlnSellAmt(sellAmt); // 입점사는 온라인판매가로 들어가야 표출 됨
			productPriceHistory.setEmpDscntRate((short) 0);
			productPriceHistory.setErpDscntRate((short) 0);
			productPriceHistory.setOnlnDscntRate((short) Math.floor(((normalAmt - sellAmt) / normalAmt) * 100));

			PdProductPriceHistory[] productPriceHistories = new PdProductPriceHistory[1];
			productPriceHistories[0] = productPriceHistory;
			product.setProductPriceHistory(productPriceHistories);
		}
	}

	/**
	 * @Desc : 상품등록시 필요한 1:N 테이블
	 * @Method Name : setInnerClass
	 * @Date : 2019. 7. 18.
	 * @Author : hsjhsj19
	 * @param searchList
	 * @param map
	 */
	private void setInnerClass(List<?> searchList, PdProduct product) {
		Object type = searchList.get(0);
		if (UtilsObject.isEmpty(type)) {
			return;
		}

		if (type instanceof SyCodeDetail) {
			if (UtilsObject.isNotEmpty(product.getPrdtColorCode())) {
				String prdtColorCode = product.getPrdtColorCode();
				String[] values = prdtColorCode.contains(",") ? prdtColorCode.split(",")
						: new String[] { prdtColorCode };
				List<PdProductColor> selectedColor = new ArrayList<PdProductColor>();
				for (SyCodeDetail codeDetail : (List<SyCodeDetail>) searchList) {
					for (String value : values) {
						if (codeDetail.getCodeDtlNo().equals(value.trim())) {
							PdProductColor productColor = new PdProductColor();
							productColor.setPrdtColorCode(codeDetail.getCodeDtlNo());
							productColor.setUseYn(Const.BOOLEAN_TRUE);
							selectedColor.add(productColor);
						}
					}
				}
				product.setProductColor(selectedColor.toArray(new PdProductColor[selectedColor.size()]));
			}
		} else if (type instanceof DpCategory) {
			if (UtilsObject.isNotEmpty(product.getStdrCtgrNo())) {
				String stdrCtgrNo = product.getStdrCtgrNo();
				String[] values = stdrCtgrNo.contains(",") ? stdrCtgrNo.split(",") : new String[] { stdrCtgrNo };
				List<DpCategory> selectedCategory = new ArrayList<DpCategory>();
				boolean setStdrCtgrNo = true;
				for (int i = 0; i < searchList.size(); i++) {
					DpCategory obj = (DpCategory) searchList.get(i);
					String ctgrNo = obj.getCtgrNo();
					for (String value : values) {
						if (ctgrNo.equals(value)) {
							selectedCategory.add(obj);
							if (setStdrCtgrNo) {
								// 전시카테고리 제일 처음 항목을 기준카테고리로 지정
								product.setStdrCtgrNo(ctgrNo);
								setStdrCtgrNo = false;
							}
						}
					}
				}
				product.setDisplayCategoryList(selectedCategory.toArray(new DpCategory[selectedCategory.size()]));
			}
		} else if (type instanceof CmProductIcon) {
			if (UtilsObject.isNotEmpty(product.getInsdMgmtInfoText())) {
				String insdMgmtInfoText = product.getInsdMgmtInfoText();
				String[] values = insdMgmtInfoText.contains(",") ? insdMgmtInfoText.split(",")
						: new String[] { insdMgmtInfoText };
				List<PdProductIcon> selectedIcon = new ArrayList<PdProductIcon>();
				for (CmProductIcon icon : (List<CmProductIcon>) searchList) {
					for (String value : values) {
						if (icon.getInsdMgmtInfoText().equals(value)) {
							PdProductIcon productIcon = new PdProductIcon();
							productIcon.setPrdtIconSeq(icon.getPrdtIconSeq());
							selectedIcon.add(productIcon);
						}
					}
				}
				product.setProductIcon(selectedIcon.toArray(new PdProductIcon[selectedIcon.size()]));
			}
		}
	}

	/**
	 * @Desc : 코드 목록 조회
	 * @Method Name : getCodeList
	 * @Date : 2019. 11. 15.
	 * @Author : hsjhsj19
	 * @param codeFields
	 * @return
	 * @throws Exception
	 */
	private Pair<JSONObject, Map<String, List<SyCodeDetail>>> getCodeList(String[] codeFields) throws Exception {
		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = this.commonCodeService
				.getCodeListByGroupByCombo(codeFields, false);
		return pair;
	}

	/**
	 * @Desc : 사이즈조건표 목록 조회
	 * @Method Name : getSizeChartList
	 * @Date : 2019. 11. 15.
	 * @Author : hsjhsj19
	 * @return
	 * @throws Exception
	 */
	private List<DpSizeChart> getSizeChartList() throws Exception {
		DpSizeChart dpSizeChart = new DpSizeChart();
		dpSizeChart.setUseYn(Const.BOOLEAN_TRUE);
		if (UtilsText.equals(Const.AUTH_APPLY_SYSTEM_TYPE_PO, LoginManager.getUserDetails().getAuthApplySystemType())) {
			dpSizeChart.setVndrNo(LoginManager.getUserDetails().getVndrNo());
		}
		return sizeChartService.getSizeChartForProduct(dpSizeChart);
	}

	/**
	 * @Desc : 표준카테고리 목록 조회
	 * @Method Name : getStandardCategoryList
	 * @Date : 2019. 11. 15.
	 * @Author : hsjhsj19
	 * @return
	 * @throws Exception
	 */
	private List<SyStandardCategory> getStandardCategoryList() throws Exception {
		SyStandardCategory syStandardCategory = new SyStandardCategory();
		syStandardCategory.setLeafCtgrYn(Const.BOOLEAN_TRUE);
		return standardCategoryService.getStandardCategoryList(syStandardCategory);
	}

	/**
	 * @Desc : 브랜드 목록 조회
	 * @Method Name : getBrandList
	 * @Date : 2019. 11. 15.
	 * @Author : hsjhsj19
	 * @return
	 * @throws Exception
	 */
	private List<DpBrand> getBrandList() throws Exception {
		return brandService.searchBrandApi(null);
	}

	/**
	 * @Desc : 입점사 사용가능 상품아이콘 목록 (통합목, OTS 따로 존재)
	 * @Method Name : getProductIconList
	 * @Date : 2019. 11. 15.
	 * @Author : hsjhsj19
	 * @return
	 * @throws Exception
	 */
	private List<CmProductIcon> getProductIconList(List<SySite> siteList) throws Exception {
		List<CmProductIcon> iconList = new ArrayList<CmProductIcon>();
		for (SySite site : siteList) {
			CmProductIcon cmProductIcon = new CmProductIcon();
			cmProductIcon.setAutoApplyYn(Const.BOOLEAN_FALSE);
			cmProductIcon.setSiteNo(site.getSiteNo());
			iconList.addAll(productIconService.getUseIconList(cmProductIcon));
		}
		return iconList;
	}

	/**
	 * @Desc : 입점사 사용가능 전시 카테고리 목록
	 * @Method Name : getDisplayCategoryList
	 * @Date : 2019. 11. 15.
	 * @Author : hsjhsj19
	 * @param chnnlList
	 * @return
	 * @throws Exception
	 */
	private List<DpCategory> getDisplayCategoryList(List<SySiteChnnl> chnnlList) throws Exception {
		List<DpCategory> displayCategory = new ArrayList<DpCategory>();
		DpCategory dpCategory = new DpCategory();
		for (SySiteChnnl siteChnnl : chnnlList) {
			dpCategory.setLeafCtgrYn(Const.BOOLEAN_TRUE);
			dpCategory.setSiteNo(siteChnnl.getSiteNo());
			dpCategory.setChnnlNo(siteChnnl.getChnnlNo());
			displayCategory.addAll(displayCategoryService.getDpCategoryList(dpCategory));
		}

		return displayCategory;
	}

	/**
	 * @Desc : 입점사 사용가능 채널 목록
	 * @Method Name : getChnnlList
	 * @Date : 2019. 11. 15.
	 * @Author : hsjhsj19
	 * @return
	 * @throws Exception
	 */
	private List<SySiteChnnl> getChnnlList() throws Exception {
		return siteService.getVendorUseChannelList();
	}

	/**
	 * @Desc : 이름 찾기
	 * @Method Name : findName
	 * @Date : 2019. 10. 10.
	 * @Author : hsjhsj19
	 * @param key
	 * @return
	 */
	private String findName(String key) {
		String name = "";
		for (int i = 0; i < HEADER_EXCEL.size(); i++) {
			if (key.equals(HEADER_EXCEL.get(i))) {
				name = HEADER_EXCEL_NAME.get(i);
				break;
			}
		}
		return name;
	}

	/**
	 * @Desc : 정수형태로 받을 경우 .을 자른다.
	 * @Method Name : sliceDot
	 * @Date : 2019. 11. 15.
	 * @Author : hsjhsj19
	 * @param key
	 * @param map
	 * @return
	 */
	private String sliceDot(String key, Map<String, Object> map) {
		String value = map.get(key).toString();
		if (value.contains(".")) {
			value = value.substring(0, value.lastIndexOf("."));
		}
		return value;
	}

}
