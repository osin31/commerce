package kr.co.abcmart.bo.display.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.abcmart.bo.display.model.master.DpHashtag;
import kr.co.abcmart.bo.display.service.HashTagService;
import kr.co.abcmart.bo.display.vo.HashTagSearchVO;
import kr.co.abcmart.bo.product.model.master.PdProductMapped;
import kr.co.abcmart.bo.product.service.ProductService;
import kr.co.abcmart.bo.product.vo.PdProductMappingVO;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.constant.Const;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/display/hashtag")
public class HashTagController extends BaseController {

	@Autowired
	HashTagService hashTagService;

	@Autowired
	ProductService productService;

	/**
	 * @Desc : 해쉬태그 검색 페이지 호출
	 * @Method Name : hashTagSearchForm
	 * @Date : 2019. 6. 26.
	 * @Author : kiowa
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("")
	public ModelAndView hashTagSearchForm(Parameter<?> parameter) throws Exception {
		parameter.addAttribute("siteList", hashTagService.getHashTagSearchForm());

		return forward("/display/hash-tag/hash-tag-main");
	}

	/**
	 * @Desc : 해쉬태그 정보 조회
	 * @Method Name : readHashTagList
	 * @Date : 2019. 6. 26.
	 * @Author : kiowa
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/read-list")
	public void readHashTagList(Parameter<HashTagSearchVO> parameter) throws Exception {
		Pageable<HashTagSearchVO, DpHashtag> hashTagSearchVo = new Pageable<>(parameter);

		Page<DpHashtag> page = hashTagService.getHashTagList(hashTagSearchVo);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 해쉬태그 상세 정보 조회
	 * @Method Name : readHashTagDetail
	 * @Date : 2019. 6. 27.
	 * @Author : kiowa
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/read-detail")
	public ModelAndView readHashTagDetail(Parameter<HashTagSearchVO> parameter) throws Exception {

		HashTagSearchVO hashTagSearchVo = parameter.get();

		Map<String, Object> rtnVal = hashTagService.getHashTagDetail(hashTagSearchVo);

		parameter.addAttribute("siteList", rtnVal.get("siteList")); // 사이트 리스트
		parameter.addAttribute("sellStateCode", rtnVal.get("sellStateCode")); // 판매상태 코드
		parameter.addAttribute("dpHashTag", rtnVal.get("dpHashTag")); // 해쉬 태그 정보

		return forward("/display/hash-tag/hash-tag-detail");
	}

	/**
	 * @Desc : 등록된 상품 정보를 조회한다.
	 * @Method Name : readHashTagProductList
	 * @Date : 2019. 8. 16.
	 * @Author : kiowa
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/product/read-list")
	public void readHashTagProductList(Parameter<PdProductMappingVO> parameter) throws Exception {

		log.error("hshtgSeq1 : {}", parameter.getString("hshtgSeq"));
		log.error("hshtgSeq2 : {}", parameter.getInt("hshtgSeq"));

		log.error("camel : {}",
				org.springframework.jdbc.support.JdbcUtils.convertUnderscoreNameToPropertyName("AAA_BBB"));

		Pageable<PdProductMappingVO, PdProductMapped> pageable = new Pageable<PdProductMappingVO, PdProductMapped>(
				parameter);
		pageable.getBean().setMappingTableName("DP_HASHTAG_PRODUCT");
		pageable.getBean().setConditionColumnName("HSHTG_SEQ");
		pageable.getBean().setConditionColumnValue(parameter.getString("hshtgSeq"));
		pageable.getBean().setSortColumnName("PRDT_NO");
		pageable.getBean().setSortType("ASC");
		Page<PdProductMapped> page = productService.searchProductByMapped(pageable);

//		List<Map<String, Object>> test = new ArrayList<Map<String, Object>>();
//
//		for (Map<String, Object> map : page.getContent()) {
//
//			Map<String, Object> testMap = new HashMap<String, Object>();
//
//			for (String key : map.keySet()) {
//				testMap.put(org.springframework.jdbc.support.JdbcUtils.convertUnderscoreNameToPropertyName(key),
//						map.get(key));
//			}
//
//			test.add(testMap);
//		}
//
//		page.setContent(test);

		log.error("getGrid : {}", page.getGrid());
		this.writeJson(parameter, page.getGrid());

	}

	/**
	 * @Desc : 해쉬태그 정보 등록
	 * @Method Name : createHashTag
	 * @Date : 2019. 6. 27.
	 * @Author : kiowa
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/create")
	public void createHashTag(Parameter<DpHashtag> parameter) throws Exception {
		DpHashtag dpHashTag = parameter.get();

		Map<String, Object> resultMap = new HashMap<String, Object>();

		int rtnVal = hashTagService.setCreateHashTagInfo(dpHashTag);

		if (rtnVal >= 1) {
			resultMap.put("code", Const.BOOLEAN_TRUE);
			resultMap.put("hshtgSeq", dpHashTag.getHshtgSeq());
			resultMap.put("Message", "해쉬태그 정보를 등록 했습니다.");
		} else {
			resultMap.put("code", Const.BOOLEAN_FALSE);
			resultMap.put("Message", "해쉬태그 정보를 등록 하지 못 했습니다.");
		}

		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 해쉬태그 정보를 수정한다.
	 * @Method Name : modifyHashTag
	 * @Date : 2019. 6. 27.
	 * @Author : kiowa
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/modify")
	public void modifyHashTag(Parameter<DpHashtag> parameter) throws Exception {
		DpHashtag dpHashTag = parameter.get();

		Map<String, Object> resultMap = new HashMap<String, Object>();

		int rtnVal = hashTagService.setModifyHashTagInfo(dpHashTag);

		if (rtnVal >= 1) {
			resultMap.put("code", Const.BOOLEAN_TRUE);
			resultMap.put("hshtgSeq", dpHashTag.getHshtgSeq());
			resultMap.put("Message", "해쉬태그 정보를 수정 했습니다.");
		} else {
			resultMap.put("code", Const.BOOLEAN_FALSE);
			resultMap.put("Message", "해쉬태그 정보를 수정 하지 못 했습니다.");
		}

		writeJson(parameter, resultMap);
	}

	@RequestMapping("/product/excelupload-read-list")
	public void readHashTagExcelUpLoadProductList(Parameter<DpHashtag> parameter) throws Exception {

		Pageable<DpHashtag, Map<String, Object>> pageable = new Pageable<DpHashtag, Map<String, Object>>(parameter);

		Page<Map<String, Object>> page = hashTagService.getExcelUploadProductList(pageable);

		this.writeJson(parameter, page.getGrid());
	}

	@RequestMapping("/product/hashtag-product-excel-sample-down")
	public void hashTagProductExcelSampleDown(Parameter<?> parameter) throws Exception {
		parameter.downloadExcelTemplate("display/hash-tag/excel/hashTagProductexcelUploadSample");
	}

}