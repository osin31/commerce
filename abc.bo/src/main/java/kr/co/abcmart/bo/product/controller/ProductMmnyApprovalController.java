package kr.co.abcmart.bo.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.abcmart.bo.product.service.ProductInterfaceService;
import kr.co.abcmart.bo.product.vo.PdProductApprovalSearchVO;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.common.util.UtilsObject;
import kr.co.abcmart.interfaces.zinterfacesdb.model.master.InterfacesSangPumToErp;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 자사상품 승인관리
 * @FileName : ProductMmnyApprovalController.java
 * @Project : abc.bo
 * @Date : 2019. 4. 2.
 * @Author : hsjhsj19
 */
@Slf4j
@Controller
@RequestMapping("product/mmnyApproval")
public class ProductMmnyApprovalController extends BaseController {

	@Autowired
	private ProductInterfaceService productInterfaceService;

//	@Autowired
//	private SiteService siteService;
//
//	@Autowired
//	private CommonCodeService commonCodeService;

	/**
	 * @Desc : 자사상품 화면
	 * @Method Name : getList
	 * @Date : 2019. 4. 2.
	 * @Author : hsjhsj19
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping
	public ModelAndView getList(Parameter<?> parameter) throws Exception {
		log.debug("자사상품 승인관리 (BO) 목록 페이지");

		productInterfaceService.getPrdtApprSearchCondition(parameter);

		return forward("/product/mmny-approval/mmny-approval-list");
	}

	/**
	 * @Desc : 중간계에서 등록된 자사상품 목록 조회
	 * @Method Name : searchApprovalProductList
	 * @Date : 2019. 4. 2.
	 * @Author : hsjhsj19
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping
	public void searchApprovalProductList(Parameter<PdProductApprovalSearchVO> parameter) throws Exception {
		log.debug("자사상품 승인관리 (BO) 목록 조회");

		// 코드 목록 리스트화
		parameter.validate();

		PdProductApprovalSearchVO searchVO = parameter.get();
		InterfacesSangPumToErp sangPumToErp = new InterfacesSangPumToErp();

		sangPumToErp.setSangPumCdList(searchVO.getPrdtCodeList());
		sangPumToErp.setSangPumNm(searchVO.getSearchKeyword());
		sangPumToErp.setBrandNm(searchVO.getBrandName());
		sangPumToErp.setRegStartDate(searchVO.getPeriodStartDate());
		sangPumToErp.setRegEndDate(searchVO.getPeriodEndDate());
		sangPumToErp.setMaejangCd(searchVO.getMaejangCd());

		if (UtilsObject.isNotEmpty(searchVO.getTierFlagCode())) {
			StringBuffer tierFlagCodeArr = new StringBuffer();

			for (String tierFlagCode : searchVO.getTierFlagCode()) {
				tierFlagCodeArr.append("''").append(tierFlagCode).append("''").append(",");
			}

			sangPumToErp.setTierFlagCode(tierFlagCodeArr.append("''^''").toString());
		}

		// 중간계 조회용으로 새로 생성
		Parameter<InterfacesSangPumToErp> newParameter = new Parameter<InterfacesSangPumToErp>(sangPumToErp);
		newParameter.setModel(parameter.getModel());
		newParameter.setRequest(parameter.getRequest());
		newParameter.setResponse(parameter.getResponse());

		Pageable<InterfacesSangPumToErp, InterfacesSangPumToErp> pageable = new Pageable<InterfacesSangPumToErp, InterfacesSangPumToErp>(
				newParameter);
		Page<InterfacesSangPumToErp> interfaceNonErpSangPumList = productInterfaceService
				.selectInterfaceNonErpSangPum(pageable);

		this.writeJson(parameter, interfaceNonErpSangPumList.getGrid());
	}

}
