package kr.co.abcmart.bo.product.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.abcmart.bo.product.model.master.DpBrandProduct;
import kr.co.abcmart.bo.product.model.master.DpBrandVisual;
import kr.co.abcmart.bo.product.service.BrandProductService;
import kr.co.abcmart.bo.product.service.BrandVisualService;
import kr.co.abcmart.bo.product.service.ProductFileService;
import kr.co.abcmart.bo.product.vo.DpBrandProductSearchVO;
import kr.co.abcmart.bo.product.vo.DpBrandVisualSearchVO;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.FileUpload;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.Const;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("product/brand/visual")
public class BrandVisualController extends BaseController {

	@Autowired
	private BrandVisualService brandVisualService;

	@Autowired
	private BrandProductService brandProductService;

	@Autowired
	private ProductFileService productFileService;

	/**
	 * @Desc : 비주얼 리스트 조회
	 * @Method Name : brandVisualList
	 * @Date : 2019. 2. 19.
	 * @Author : tennessee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("list")
	public void brandVisualList(Parameter<DpBrandVisualSearchVO> parameter) throws Exception {
		log.debug("상세비주얼 리스트");
		Pageable<DpBrandVisualSearchVO, DpBrandVisual> pageable = new Pageable<DpBrandVisualSearchVO, DpBrandVisual>(
				parameter);
		Page<DpBrandVisual> brandVisualList = this.brandVisualService.searchBrandVisualList(pageable);

		this.writeJson(parameter, brandVisualList.getGrid());
	}

	/**
	 * @Desc : 브랜드 비주얼 순서변경 목록 저장
	 * @Method Name : saveBrandVisualList
	 * @Date : 2019. 6. 5.
	 * @Author : 백인천
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("save")
	public void saveBrandVisualList(Parameter<?> parameter) throws Exception {
		log.debug("브랜드 비주얼 순서변경 리스트 저장");
		Integer appliedCount = 0;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String brandNo = parameter.getString("brandNo");
			List<DpBrandVisual> brandVisualList = Arrays.asList(parameter.createArray(DpBrandVisual.class, "status"));

			appliedCount += this.brandVisualService.setBrandVisualList(brandVisualList, brandNo);

			resultMap.put("success", true);
			resultMap.put("Cnt", appliedCount);
			resultMap.put("Message", "저장되었습니다.");
		} catch (Exception e) {
			resultMap.put("success", false);
			resultMap.put("Cnt", appliedCount);
			resultMap.put("Message", "저장에 실패하였습니다.");
		}

		this.writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 브랜드 상품상세 삭제
	 * @Method Name : deleteBrandVisual
	 * @Date : 2019. 2. 13.
	 * @Author : tennessee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("delete")
	public void deleteBrandVisual(Parameter<?> parameter) throws Exception {
		log.debug("브랜드 상품상세 삭제");
		Integer appliedCount = 0;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			List<DpBrandVisual> brandVisualList = Arrays.asList(parameter.createArray(DpBrandVisual.class, "status"));
			String brandNo = parameter.getString("brandNo");

			appliedCount += this.brandVisualService.setBrandVisualDelete(brandVisualList, brandNo);

			resultMap.put("success", true);
			resultMap.put("Cnt", appliedCount);
			resultMap.put("Message", "삭제되었습니다.");
		} catch (Exception e) {
			resultMap.put("success", false);
			resultMap.put("Cnt", appliedCount);
			resultMap.put("Message", "삭제에 실패하였습니다.");
		}

		this.writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 브랜드 비주얼 대상상품 목록 저장
	 * @Method Name : saveBrandVisualProductList
	 * @Date : 2019. 6. 5.
	 * @Author : 백인천
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("product/save")
	public void saveBrandVisualProductList(Parameter<?> parameter) throws Exception {
		log.debug("브랜드 비주얼 대상상품 목록 저장");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String brandNo = parameter.getString("brandNo");
		String sortSeq = parameter.getString("sortSeq");
		String[] prdtNoArr = parameter.getStringArray("prdtNo");

		for (String prdtNo : prdtNoArr) {
			DpBrandProduct brandPrdt = new DpBrandProduct();
			brandPrdt.setBrandNo(brandNo);
			brandPrdt.setBrandBannerSeq(Integer.parseInt(sortSeq));
			brandPrdt.setPrdtNo(prdtNo);

			brandProductService.inputBrandProductList(brandPrdt);
		}

		resultMap.put("success", true);
		resultMap.put("Message", "저장되었습니다.");

		this.writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 브랜드 비주얼 대상상품 목록 조회
	 * @Method Name : searchBrandVisualProductList
	 * @Date : 2019. 7. 4.
	 * @Author : 백인천
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("product/list")
	public void searchBrandVisualProductList(Parameter<DpBrandProductSearchVO> parameter) throws Exception {
		log.debug("브랜드 비주얼 상세 리스트 조회");
		Pageable<DpBrandProductSearchVO, DpBrandProduct> pageable = new Pageable<DpBrandProductSearchVO, DpBrandProduct>(
				parameter);
		Page<DpBrandProduct> brandVisualList = this.brandProductService.searchBrandProductList(pageable);

		this.writeJson(parameter, brandVisualList.getGrid());
	}

	/**
	 * @Desc : 브랜드 비주얼 대상상품 리스트 저장
	 * @Method Name : saveSheetBrandProductList
	 * @Date : 2019. 7. 9.
	 * @Author : 백인천
	 * @param parameter
	 */
	@PostMapping("sheet/product/save")
	public void saveSheetBrandProductList(Parameter<DpBrandProduct> parameter) throws Exception {
		log.debug("브랜드 비주얼 대상상품 리스트 저장");
		String brandNo = parameter.getString("brandNo");
		int brandBannerSeq = parameter.getInt("brandBannerSeq");

		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<DpBrandProduct> brandProductList = Arrays.asList(parameter.createArray(DpBrandProduct.class, "status"));

		try {
			Map<String, List<String>> duplicateBannerCheckList = this.brandProductService
					.setBrandProductManagementList(brandProductList, brandNo, brandBannerSeq);

			String message = "저장되었습니다.";

			if (duplicateBannerCheckList.get("duplicateCheckList").size() > 0) {
				message = UtilsText.concat(message, "\n중복된 ",
						String.valueOf(duplicateBannerCheckList.get("duplicateCheckList").size()), "건은 제외됩니다.");
			}

			if (duplicateBannerCheckList.get("duplicateBannerCheckList").size() > 0) {
				message = UtilsText.concat(message, "\n등록실패 상품코드 : ",
						duplicateBannerCheckList.get("duplicateBannerCheckList").toString());
			}
			resultMap.put("Message", message);
			resultMap.put("success", true);
		} catch (Exception e) {
			resultMap.put("success", false);
			resultMap.put("Message", "저장에 실패하였습니다.");
		}

		this.writeJson(parameter, resultMap);

	}

	/**
	 * @Desc : 브랜드 배너 파일 등록
	 * @Method Name : uploadBanner
	 * @Date : 2019. 2. 18.
	 * @Author : tennessee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("upload/banner")
	public void uploadBanner(Parameter<?> parameter) throws Exception {
		log.debug("상세비주얼 파일 업로드");
		FileUpload uploadFile = parameter.getUploadFile("fileUpload");
		log.debug("FileName > " + uploadFile.isFileItem());
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();

		result.add(this.productFileService.uploadFile(uploadFile, Arrays.asList(Const.IMG_SRC_PRODUCT_BRAND_PREFIX)));

		String brandNo = parameter.getString("brandNo");

		List<DpBrandVisual> brandVisualList = Arrays.asList(parameter.createArray(DpBrandVisual.class, "status"));
		this.brandVisualService.setBrandVisualList(brandVisualList, brandNo);

		this.writeJson(parameter, result);
	}

}
