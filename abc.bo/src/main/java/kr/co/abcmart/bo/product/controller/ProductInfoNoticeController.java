package kr.co.abcmart.bo.product.controller;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.abcmart.bo.product.model.master.CmProductInfoNotice;
import kr.co.abcmart.bo.product.service.ProductInfoNoticeService;
import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.bo.system.service.CommonCodeService;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.common.util.UtilsObject;
import kr.co.abcmart.constant.CommonCode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("product/info-notice")
public class ProductInfoNoticeController extends BaseController {

	@Autowired
	private ProductInfoNoticeService productInfoNoticeService;

	@Autowired
	private CommonCodeService commonCodeService;

	/**
	 * 
	 * @Desc : 상품정보고시관리
	 * @Method Name : infoNotice
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("")
	public ModelAndView infoNotice(Parameter<?> parameter) throws Exception {

		String[] codeFields = { CommonCode.ITEM_CODE };

		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				true);

		JSONObject codeCombo = pair.getFirst();
		Map<String, List<SyCodeDetail>> codeList = pair.getSecond();

		parameter.addAttribute("codeCombo", codeCombo);
		parameter.addAttribute("itemCode", codeList.get(CommonCode.ITEM_CODE));

		return forward("/product/info-notice/info-notice-list");
	}

	/**
	 * 
	 * @Desc : 상품정보고시 리스트 조회
	 * @Method Name : readInfoNotice
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/list/read")
	public void readInfoNotice(Parameter<CmProductInfoNotice> parameter) throws Exception {

		Pageable<CmProductInfoNotice, CmProductInfoNotice> pageable = new Pageable<CmProductInfoNotice, CmProductInfoNotice>(
				parameter);

		Page<CmProductInfoNotice> page = productInfoNoticeService.getCmProductInfoNoticeList(pageable);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * 
	 * @Desc : 상품정보고시 저장 (리스트 화면에서)
	 * @Method Name : saveInfoNotice
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/save")
	public void saveInfoNotice(Parameter<CmProductInfoNotice[]> parameter) throws Exception {

		CmProductInfoNotice[] params = parameter.get();

		productInfoNoticeService.updateCmProductInfoNotice(params);
	}

	/**
	 * 
	 * @Desc : 상품정보고시 상세
	 * @Method Name : infoNoticeDetail
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/detail")
	public ModelAndView infoNoticeDetail(Parameter<CmProductInfoNotice> parameter) throws Exception {

		CmProductInfoNotice info = parameter.get();

		if (info.getPrdtInfoNotcSeq() != null) {
			info = productInfoNoticeService.getCmProductInfoNoticeDetail(info);
		}

		String[] codeFields = { CommonCode.ITEM_CODE, CommonCode.PRDT_INFO_NOTC_CODE };

		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				true);

		parameter.addAttribute("itemCode", pair.getSecond().get(CommonCode.ITEM_CODE));
		parameter.addAttribute("prdtInfoNotcCode", pair.getSecond().get(CommonCode.PRDT_INFO_NOTC_CODE));
		parameter.addAttribute("info", info);

		return forward("/product/info-notice/info-notice-detail");
	}

	/**
	 * 
	 * @Desc : 상품정보고시 저장 (상세 화면에서)
	 * @Method Name : saveInfoNoticeDetail
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/save/detail")
	public void saveInfoNoticeDetail(Parameter<CmProductInfoNotice> parameter) throws Exception {

		CmProductInfoNotice params = parameter.get();

		productInfoNoticeService.setCmProductInfoNoticeDetail(params);

		writeJson(parameter, params);
	}

	/**
	 * 
	 * @Desc : 상품정보고시 중복 체크
	 * @Method Name : infoNoticeDupCheck
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/dupCheck")
	public void infoNoticeDupCheck(Parameter<CmProductInfoNotice> parameter) throws Exception {

		// 신규 or 수정 데이터
		CmProductInfoNotice saveData = parameter.get();
		// 중복 데이터
		CmProductInfoNotice duplicateData = this.productInfoNoticeService.getDuplicationOfCmProductInfoNotice(saveData);

		// 중복 데이터 있는 경우
		if (UtilsObject.isNotEmpty(duplicateData) && UtilsObject.isNotEmpty(duplicateData.getPrdtInfoNotcSeq())) {
			writeJson(parameter, duplicateData);
		}
	}

}
