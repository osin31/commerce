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

import kr.co.abcmart.bo.product.model.master.PdProductBulkUpdateWrapper;
import kr.co.abcmart.bo.product.service.ProductBulkUpdateService;
import kr.co.abcmart.bo.product.service.ProductIconService;
import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.bo.system.service.CommonCodeService;
import kr.co.abcmart.bo.system.service.SiteService;
import kr.co.abcmart.bo.vendor.model.master.VdVendorDisplayChnnl;
import kr.co.abcmart.bo.vendor.service.VendorService;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/product/product/update/bulk")
public class ProductBulkUpdateController extends BaseController {

	@Autowired
	private ProductBulkUpdateService productBulkUpdateService;
	@Autowired
	private ProductIconService productIconService;

	@Autowired
	private SiteService siteService;
	@Autowired
	private CommonCodeService commonCodeService;
	@Autowired
	private VendorService vendorService;

	/**
	 *
	 * @Desc : 일괄 수정 화면
	 * @Method Name : goToPage
	 * @Date : 2019. 7. 16.
	 * @Author : tennessee
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping
	public ModelAndView goToPage(Parameter<?> parameter) throws Exception {
		log.debug("상품 일괄수정 페이지");

		parameter.addAttribute("auth", LoginManager.getUserDetails().getAuthApplySystemType()); // BO(B) 또는 PO(P)

		// 화면에 사용되는 공통코드 조회
		String[] codeFields = { CommonCode.SELL_STAT_CODE, CommonCode.PRDT_COLOR_CODE,
				CommonCode.STOCK_UN_INTGR_RSN_CODE };
		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = this.commonCodeService
				.getCodeListByGroupByCombo(codeFields, false);

		parameter.addAttribute("gridCodes", pair.getFirst()); // 그리드 공통코드. 판매상태
		parameter.addAttribute("sellStatCodes", pair.getSecond().get(CommonCode.SELL_STAT_CODE)); // 판매상태코드
		parameter.addAttribute("icons", this.productIconService.getUseIconList()); // 사용 중인 아이콘 정보 조회
		// 채널조회
		if (UtilsText.equals(LoginManager.getUserDetails().getAuthApplySystemType(), Const.AUTH_APPLY_SYSTEM_TYPE_BO)) {
			// 자사
			parameter.addAttribute("channels", this.siteService.getUseChannelList());
		} else {
			// 입점
			VdVendorDisplayChnnl criteriaForVendorChannel = new VdVendorDisplayChnnl();
			criteriaForVendorChannel.setVndrNo(LoginManager.getUserDetails().getVndrNo());
			parameter.addAttribute("channels",
					this.vendorService.selectVendorDisplayChnnlList(criteriaForVendorChannel));
		}
		parameter.addAttribute("prdtColorCodes", pair.getSecond().get(CommonCode.PRDT_COLOR_CODE)); // 상품색상코드
		parameter.addAttribute("stockUnIntgrRsnCodes", pair.getSecond().get(CommonCode.STOCK_UN_INTGR_RSN_CODE)); // 재입고미통합사유코드

		return this.forward("/product/bulk-update/bulk-update");
	}

	/**
	 * @Desc : 일괄 수정
	 * @Method Name : save
	 * @Date : 2019. 7. 17.
	 * @Author : tennessee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping
	public void save(Parameter<PdProductBulkUpdateWrapper> parameter) throws Exception {

		PdProductBulkUpdateWrapper params= parameter.get();

		try {
			log.error("상품일괄 수정 >>>>  adminNo = {}, params = {} "
						, LoginManager.getUserDetails().getAdminNo(), UtilsText.stringify(params));
		}catch(Exception e) {
			log.error("ex ={}", e);
		}

		this.writeJson(parameter, this.productBulkUpdateService.regist(params));
	}

}
