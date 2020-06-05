package kr.co.abcmart.bo.product.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.abcmart.bo.product.model.master.DpBrandStyle;
import kr.co.abcmart.bo.product.model.master.DpBrandStyleProduct;
import kr.co.abcmart.bo.product.service.BrandStyleService;
import kr.co.abcmart.bo.product.vo.DpBrandStyleProductSearchVO;
import kr.co.abcmart.bo.product.vo.DpBrandStyleSearchVO;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.common.util.UtilsNumber;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("product/brand/style")
public class BrandStyleController extends BaseController {

	@Autowired
	private BrandStyleService brandStyleService;

	/**
	 * @Desc : 브랜드 스타일 목록 조회
	 * @Method Name : searchBrandStyleList
	 * @Date : 2019. 2. 21.
	 * @Author : tennessee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping
	public void searchBrandStyleList(Parameter<DpBrandStyleSearchVO> parameter) throws Exception {
		log.debug("브랜드 스타일 목록 조회");
		Pageable<DpBrandStyleSearchVO, DpBrandStyle> pageable = new Pageable<DpBrandStyleSearchVO, DpBrandStyle>(
				parameter);
		Page<DpBrandStyle> brandStyleList = this.brandStyleService.searchBrandStyleList(pageable);

		this.writeJson(parameter, brandStyleList.getGrid());
	}

	/**
	 * @Desc : 브랜드 스타일 목록 저장
	 * @Method Name : saveBrandStyleList
	 * @Date : 2019. 2. 13.
	 * @Author : tennessee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("save")
	public void saveBrandStyleList(Parameter<?> parameter) throws Exception {
		log.debug("브랜드 스타일 저장");
		Integer appliedCount = 0;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			List<DpBrandStyle> brandStyleList = Arrays.asList(parameter.createArray(DpBrandStyle.class, "status"));
			String brandNo = parameter.getString("brandNo");
			log.debug("brandNo > " + brandNo);

			appliedCount += this.brandStyleService.setBrandStyleList(brandStyleList, brandNo);

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
	 * @Desc : 브랜드 스타일 저장
	 * @Method Name : modifyBrandStyleList
	 * @Date : 2019. 11. 8.
	 * @Author : 백인천
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("modify")
	public void modifyBrandStyleList(Parameter<?> parameter) throws Exception {
		log.debug("브랜드 스타일 저장");
		Integer appliedCount = 0;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			List<DpBrandStyle> brandStyleList = Arrays.asList(parameter.createArray(DpBrandStyle.class, "checked"));

			appliedCount += this.brandStyleService.updateBrandStyleList(brandStyleList);

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
	 * @Desc : 브랜드 스타일 삭제
	 * @Method Name : deleteBrandStyleSave
	 * @Date : 2019. 2. 13.
	 * @Author : tennessee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("delete")
	public void deleteBrandStyleSave(Parameter<?> parameter) throws Exception {
		log.debug("브랜드 스타일 삭제");
		Integer appliedCount = 0;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			List<DpBrandStyle> brandStyleList = Arrays.asList(parameter.createArray(DpBrandStyle.class, "status"));
			String brandNo = parameter.getString("brandNo");

			appliedCount += this.brandStyleService.setBrandStyleDelete(brandStyleList, brandNo);

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
	 * @Desc : 브랜드 스타일 대상상품 목록 저장
	 * @Method Name : saveBrandStyleProductSave
	 * @Date : 2019. 6. 13.
	 * @Author : 백인천
	 * @param parameter
	 * @throws Exception
	 */
	@GetMapping("product/save")
	public void saveBrandStyleProductSave(Parameter<?> parameter) throws Exception {
		log.debug("브랜드 스타일 대상상품 목록 저장");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String brandNo = parameter.getString("brandNo");
		String sortSeq = parameter.getString("sortSeq");
		String[] prdtNoArr = parameter.getStringArray("prdtNo");

		for (String prdtNo : prdtNoArr) {
			DpBrandStyleProduct bStylePrdt = new DpBrandStyleProduct();
			bStylePrdt.setBrandNo(brandNo);
			bStylePrdt.setBrandStyleSeq(Integer.parseInt(sortSeq));
			bStylePrdt.setPrdtNo(prdtNo);

			brandStyleService.insertBrandStyleProductList(bStylePrdt);
		}

		resultMap.put("success", true);
		resultMap.put("Message", "저장되었습니다.");

		this.writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 브랜드 스타일 대상상품 목록 조회
	 * @Method Name : searchBrandStyleProductList
	 * @Date : 2019. 7. 4.
	 * @Author : 백인천
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("product/list")
	public void searchBrandStyleProductList(Parameter<DpBrandStyleProductSearchVO> parameter) throws Exception {
		log.debug("브랜드 스타일 목록 조회");
		log.debug("parameter check >> {}", parameter);
		Pageable<DpBrandStyleProductSearchVO, DpBrandStyleProduct> pageable = new Pageable<DpBrandStyleProductSearchVO, DpBrandStyleProduct>(
				parameter);
		Page<DpBrandStyleProduct> brandStyleList = this.brandStyleService.searchBrandStyleProductList(pageable);

		this.writeJson(parameter, brandStyleList.getGrid());
	}

	/**
	 * @Desc : 브랜드 스타일 대상상품 리스트 저장
	 * @Method Name : saveSheetBrandStyleList
	 * @Date : 2019. 7. 9.
	 * @Author : 백인천
	 * @param parameter
	 */
	@PostMapping("sheet/product/save")
	public void saveSheetBrandStyleList(Parameter<DpBrandStyleProduct> parameter) throws Exception {
		log.debug("브랜드 스타일 대상상품 리스트 저장");
		String brandNo = parameter.getString("brandNo");
		int brandStyleSeq = parameter.getInt("brandStyleSeq");
//		boolean scFlag = true;

		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<DpBrandStyleProduct> brandStyleProductList = Arrays
				.asList(parameter.createArray(DpBrandStyleProduct.class, "status"));

//		for (DpBrandStyleProduct styleProduct : brandStyleProductList) {
//			styleProduct.setBrandNo(brandNo);
//
//			if ("I".equals(styleProduct.getStatus())) {
//				int getProductCount = brandStyleService.getBrandStyleProduct(styleProduct);
//
//				if (getProductCount > 0) {
//					scFlag = false;
//					break;
//				}
//			}
//		}

		try {

//			if (scFlag) {
			List<String> duplicatedPrdtNos = this.brandStyleService
					.setBrandStyleProductManagementList(brandStyleProductList, brandNo, brandStyleSeq);

			String message = "저장되었습니다.";
			if (UtilsNumber.compare(duplicatedPrdtNos.size(), 0) > 0) {
				message = message.concat("\n중복된 ").concat(duplicatedPrdtNos.size() + "건은 제외됩니다.");
			}

			resultMap.put("Success", true);
			resultMap.put("Message", message);
//			} else {
//				resultMap.put("Success", false);
//				resultMap.put("Message", "동일한 상품정보가 등록되어있습니다.\n상품정보를 다시 확인해주세요.");
//			}

		} catch (Exception e) {
			resultMap.put("Success", false);
			resultMap.put("Message", "저장에 실패하였습니다.");
		}

		this.writeJson(parameter, resultMap);

	}

	/**
	 * @Desc : 브랜드 스타일 행 추가
	 * @Method Name : saveBrandStyle
	 * @Date : 2019. 8. 16.
	 * @Author : 백인천
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("addRow")
	public void saveBrandStyle(Parameter<?> parameter) throws Exception {
		log.debug("브랜드 스타일 행 추가");
		Integer appliedCount = 0;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String brandNo = parameter.getString("brandNo");

		try {
			appliedCount += this.brandStyleService.insertBrandStyleRow(brandNo);

			resultMap.put("success", true);
			resultMap.put("Cnt", appliedCount);
			resultMap.put("Message", "추가되었습니다.");
		} catch (Exception e) {
			resultMap.put("success", false);
			resultMap.put("Cnt", appliedCount);
			resultMap.put("Message", "행 추가에 실패하였습니다.");
		}

		this.writeJson(parameter, resultMap);
	}

}
