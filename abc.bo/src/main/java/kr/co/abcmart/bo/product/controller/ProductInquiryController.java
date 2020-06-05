package kr.co.abcmart.bo.product.controller;

import java.util.ArrayList;
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

import kr.co.abcmart.bo.product.model.master.BdProductInquiry;
import kr.co.abcmart.bo.product.model.master.BdProductInquiryMemo;
import kr.co.abcmart.bo.product.model.master.BdProductReview;
import kr.co.abcmart.bo.product.service.ProductInquiryService;
import kr.co.abcmart.bo.product.vo.BdProductInquirySearchVO;
import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.bo.system.service.CommonCodeService;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 상품Q&A 컨트롤러
 * @FileName : ProductInquiryController.java
 * @Project : abc.bo
 * @Date : 2019. 4. 9.
 * @Author : hsjhsj19
 */
@Slf4j
@Controller
@RequestMapping("product/inquiry")
public class ProductInquiryController extends BaseController {

	@Autowired
	private ProductInquiryService productInquiryService;

	@Autowired
	private CommonCodeService commonCodeService;

	/**
	 * @Desc : 상품Q&A 목록 화면
	 * @Method Name : getList
	 * @Date : 2019. 4. 9.
	 * @Author : hsjhsj19
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping
	public ModelAndView getList(Parameter<BdProductReview> parameter) throws Exception {
		log.debug("상품Q&A 목록 페이지");

		// 답변상태코드
		String[] codeFields = { CommonCode.ASWR_STAT_CODE, CommonCode.CNSL_TYPE_CODE };
		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				true);
		JSONObject codeCombo = pair.getFirst();
		parameter.addAttribute("codeCombo", codeCombo);

		List<SyCodeDetail> codeSearchCondition = new ArrayList<SyCodeDetail>();

		// 문의유형 코드
		SyCodeDetail syCodeDetail = new SyCodeDetail();
		syCodeDetail.setCodeField(CommonCode.CNSL_TYPE_CODE);
		codeSearchCondition.add(syCodeDetail);

		// 문의구분 코드
		syCodeDetail = new SyCodeDetail();
		syCodeDetail.setCodeField(CommonCode.CNSL_TYPE_DTL_CODE);
		syCodeDetail.setAddInfo1(CommonCode.CNSL_TYPE_CODE_PRODUCT_INFO);
		codeSearchCondition.add(syCodeDetail);

		Map<String, List<SyCodeDetail>> codeListMap = commonCodeService.getCodeListByGroup(codeSearchCondition);

		parameter.addAttribute("cnslTypeCode", codeListMap.get(CommonCode.CNSL_TYPE_CODE)); // 문의유형
		parameter.addAttribute("cnslTypeDtlCode", codeListMap.get(CommonCode.CNSL_TYPE_DTL_CODE)); // 문의분류
		parameter.addAttribute("aswrStatCode", pair.getSecond().get(CommonCode.ASWR_STAT_CODE));// 답변상태코드

		// BO/PO 구분
		parameter.addAttribute("authApplySystemType",
				UtilsText.equals(LoginManager.getUserDetails().getAuthApplySystemType(),
						Const.AUTH_APPLY_SYSTEM_TYPE_BO) ? Const.AUTH_APPLY_SYSTEM_TYPE_BO
								: Const.AUTH_APPLY_SYSTEM_TYPE_PO);

		return forward("/product/inquiry/inquiry-list");
	}

	/**
	 * @Desc : 상품Q&A 목록 조회
	 * @Method Name : searchInquiryList
	 * @Date : 2019. 3. 26.
	 * @Author : hsjhsj19
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping
	public void searchInquiryList(Parameter<BdProductInquirySearchVO> parameter) throws Exception {
		log.debug("상품Q&A 목록 조회");

		// 코드 목록 리스트화
		parameter.validate();

		Pageable<BdProductInquirySearchVO, BdProductInquiry> pageable = new Pageable<BdProductInquirySearchVO, BdProductInquiry>(
				parameter);

		BdProductInquirySearchVO bdProductInquirySearchVO = parameter.get();
		UserDetails user = LoginManager.getUserDetails();
		if (UtilsText.equals(user.getAuthApplySystemType(), Const.AUTH_APPLY_SYSTEM_TYPE_PO)) {
			bdProductInquirySearchVO.setVndrNo(user.getVndrNo());
		}

		pageable.setBean(bdProductInquirySearchVO);

		Page<BdProductInquiry> productInquiryList = this.productInquiryService.selectProductInquiry(pageable);

		this.writeJson(parameter, productInquiryList.getGrid());
	}

	/**
	 * @Desc : 상품Q&A 단건 조회
	 * @Method Name : getDetail
	 * @Date : 2019. 4. 9.
	 * @Author : hsjhsj19
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("detail")
	public ModelAndView getDetail(Parameter<BdProductInquiry> parameter) throws Exception {
		log.debug("상품Q&A 단건 조회");

		String prdtInqrySeq = parameter.getString("prdtInqrySeq"); // 상품문의순번

		if (UtilsText.isNotBlank(prdtInqrySeq)) {
			// BD_상품문의
			BdProductInquiry inquiry = this.productInquiryService.searchProductInquiryByPrimaryKey(prdtInqrySeq);
			parameter.addAttribute("inquiry", inquiry);

			// BD_상품문의메모
			parameter.addAttribute("memo", this.productInquiryService.searchProductInquiryMemo(inquiry));

			List<SyCodeDetail> codeSearchCondition = new ArrayList<SyCodeDetail>();

			// 문의유형 코드
			SyCodeDetail syCodeDetail = new SyCodeDetail();
			syCodeDetail.setCodeField(CommonCode.CNSL_TYPE_CODE);
			codeSearchCondition.add(syCodeDetail);

			// 문의구분 코드
			syCodeDetail = new SyCodeDetail();
			syCodeDetail.setCodeField(CommonCode.CNSL_TYPE_DTL_CODE);
			syCodeDetail.setAddInfo1(inquiry.getCnslTypeCode());
			codeSearchCondition.add(syCodeDetail);

			Map<String, List<SyCodeDetail>> codeListMap = commonCodeService.getCodeListByGroup(codeSearchCondition);

			parameter.addAttribute("cnslTypeCode", codeListMap.get(CommonCode.CNSL_TYPE_CODE)); // 문의유형
			parameter.addAttribute("cnslTypeDtlCode", codeListMap.get(CommonCode.CNSL_TYPE_DTL_CODE)); // 문의분류

			// BO/PO 구분
			parameter.addAttribute("isAdmin",
					UtilsText.equals(LoginManager.getUserDetails().getUpAuthNo(), Const.ROLE_ADMIN_GROUP));
		}

		return forward("/product/inquiry/inquiry-detail-popup");
	}

	/**
	 * @Desc : 상품 Q&A 저장
	 * @Method Name : save
	 * @Date : 2019. 4. 10.
	 * @Author : hsjhsj19
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("save")
	public void save(Parameter<BdProductInquiry> parameter) throws Exception {
		log.debug("상품Q&A 저장");

		parameter.validate();

		this.writeJson(parameter, this.productInquiryService.updateProductInquiry(parameter.get()));
	}

	/**
	 * @Desc : 상품Q&A 삭제
	 * @Method Name : delete
	 * @Date : 2019. 4. 10.
	 * @Author : hsjhsj19
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("delete")
	public void delete(Parameter<BdProductInquiry> parameter) throws Exception {
		log.debug("상품Q&A 삭제");

		BdProductInquiry inquiry = parameter.get();
		this.productInquiryService.deleteProductInquiry(inquiry);

		this.writeJson(parameter, inquiry);
	}

	/**
	 * @Desc : 상품 q&a 문의 메모 목록 조회
	 * @Method Name : getMemoList
	 * @Date : 2019. 4. 17.
	 * @Author : hsjhsj19
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("getMemoList")
	public void getMemoList(Parameter<BdProductInquiryMemo> parameter) throws Exception {

		this.writeJson(parameter, this.productInquiryService.getMemoList(parameter.get()));
	}

	/**
	 * @Desc : 상품 Q&A 메모 저장
	 * @Method Name : saveMemo
	 * @Date : 2019. 4. 10.
	 * @Author : hsjhsj19
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("saveMemo")
	public void saveMemo(Parameter<BdProductInquiryMemo> parameter) throws Exception {
		log.debug("상품Q&A 메모 저장");

		BdProductInquiryMemo memo = parameter.get();

		parameter.validate();

		this.productInquiryService.insertProductInquiryMemo(memo);

		this.writeJson(parameter, memo);
	}

	/**
	 * @Desc : 상품Q&A 메모 삭제
	 * @Method Name : deleteMemo
	 * @Date : 2019. 4. 10.
	 * @Author : hsjhsj19
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("deleteMemo")
	public void deleteMemo(Parameter<BdProductInquiryMemo> parameter) throws Exception {
		log.debug("상품Q&A 메모 삭제");

		BdProductInquiryMemo memo = parameter.get();
		this.productInquiryService.deleteProductInquiryMemo(memo);

		this.writeJson(parameter, memo);
	}

}
