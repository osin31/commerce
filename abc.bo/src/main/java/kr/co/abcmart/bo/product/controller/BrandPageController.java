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
import org.springframework.web.servlet.ModelAndView;

import kr.co.abcmart.bo.product.model.master.DpBrandPage;
import kr.co.abcmart.bo.product.service.BrandPageService;
import kr.co.abcmart.bo.product.vo.DpBrandFileUploadVO;
import kr.co.abcmart.bo.product.vo.DpBrandPageSearchVO;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("product/brand/page")
public class BrandPageController extends BaseController {

	@Autowired
	private BrandPageService brandPageService;

	/**
	 * @Desc : 브랜드 페이지 순서변경 목록 저장
	 * @Method Name : saveBrandPageList
	 * @Date : 2019. 6. 5.
	 * @Author : 백인천
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("save")
	public void saveBrandPageList(Parameter<?> parameter) throws Exception {
		log.debug("브랜드 페이지 순서변경 리스트 저장");
		Integer appliedCount = 0;
		String brandNo = parameter.getString("brandNo");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<DpBrandPage> brandPageList = Arrays.asList(parameter.createArray(DpBrandPage.class, "status"));
		log.debug("brandPageList = {}", brandPageList);
		try {
			appliedCount += this.brandPageService.setBrandPageList(brandPageList, brandNo);

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
	 * @Desc : 브랜드 페이지 순서변경 목록 저장
	 * @Method Name : saveBrandPageDelete
	 * @Date : 2019. 6. 5.
	 * @Author : 백인천
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("delete")
	public void saveBrandPageDelete(Parameter<?> parameter) throws Exception {
		log.debug("브랜드 페이지 삭제");
		Integer appliedCount = 0;
		String brandNo = parameter.getString("brandNo");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<DpBrandPage> brandPageList = Arrays.asList(parameter.createArray(DpBrandPage.class, "status"));

		try {
			appliedCount += this.brandPageService.setBrandPageDelete(brandPageList, brandNo);

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
	 * @Desc : 브랜드 프로모션 저장
	 * @Method Name : savePromotion
	 * @Date : 2019. 2. 11.
	 * @Author : tennessee
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@PostMapping("promotion/save")
	public void savePromotion(Parameter<DpBrandPage[]> parameter) throws Exception {
		log.debug("브랜드 프로모션 저장");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int saveCount = this.brandPageService.updateBrand(parameter);

		resultMap.put("success", true);
		resultMap.put("Cnt", saveCount);
		resultMap.put("Message", "저장되었습니다.");

		this.writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 페이지 리스트 조회
	 * @Method Name : brandPageList
	 * @Date : 2019. 6. 5.
	 * @Author : 백인천
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("visual")
	public void brandPageList(Parameter<DpBrandPageSearchVO> parameter) throws Exception {
		log.debug("페이지 리스트");
		Pageable<DpBrandPageSearchVO, DpBrandPage> pageable = new Pageable<DpBrandPageSearchVO, DpBrandPage>(parameter);
		Page<DpBrandPage> brandPageList = this.brandPageService.searchBrandPageList(pageable);

		this.writeJson(parameter, brandPageList.getGrid());
	}

	/**
	 * @Desc : 이미지/동영상 등록 팝업
	 * @Method Name : setFilePopup
	 * @Date : 2019. 6. 11.
	 * @Author : 백인천
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("setFile")
	public ModelAndView setFilePopup(Parameter<DpBrandFileUploadVO> parameter) throws Exception {
		log.debug("이미지/동영상 등록 팝업");

		DpBrandFileUploadVO param = parameter.get();
		log.debug("param > {}", param);
		parameter.addAttribute("param", param);

		return forward("/product/brand/brand-set-popup");
	}

	/**
	 * @Desc : 브랜드 이미지/동영상 파일 등록
	 * @Method Name : uploadFile
	 * @Date : 2019. 5. 11.
	 * @Author : 백인천
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("upload/file")
	public void uploadFile(Parameter<?> parameter) throws Exception {
		log.debug("이미지/동영상 파일 업로드");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int saveCount = 0;

		try {
			saveCount = brandPageService.insertBrandPageFile(parameter);

			resultMap.put("success", true);
			resultMap.put("Cnt", saveCount);
			resultMap.put("Message", "저장되었습니다.");
		} catch (Exception e) {
			resultMap.put("success", false);
			resultMap.put("Cnt", saveCount);
			resultMap.put("Message", "저장에 실패하였습니다.");
		}

		this.writeJson(parameter, resultMap);
	}

}
