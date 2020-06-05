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

import kr.co.abcmart.bo.cmm.model.master.CmOnlineMemberPolicy;
import kr.co.abcmart.bo.cmm.service.OnlinePolicyService;
import kr.co.abcmart.bo.product.model.master.BdProductReview;
import kr.co.abcmart.bo.product.service.ProductReviewService;
import kr.co.abcmart.bo.product.vo.BdProductReviewSearchVO;
import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.bo.system.service.CommonCodeService;
import kr.co.abcmart.bo.system.service.SiteService;
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

@Slf4j
@Controller
@RequestMapping("product/review")
public class ProductReviewController extends BaseController {

	@Autowired
	private ProductReviewService productReviewService;

	@Autowired
	private CommonCodeService commonCodeService;

	@Autowired
	private SiteService siteService;

	@Autowired
	private OnlinePolicyService onlinePolicyService;

	/**
	 * @Desc : 상품후기 목록 화면
	 * @Method Name : getList
	 * @Date : 2019. 4. 9.
	 * @Author : hsjhsj19
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping
	public ModelAndView getList(Parameter<BdProductReview> parameter) throws Exception {
		log.debug("상품후기 목록 페이지");

		// 답변상태코드, 회원유형코드
		String[] codeFields = { CommonCode.ASWR_STAT_CODE, CommonCode.MEMBER_TYPE_CODE };
		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				true);
		JSONObject codeCombo = pair.getFirst();
		parameter.addAttribute("codeCombo", codeCombo);

		// 채널 코드
		parameter.addAttribute("searchConditionSiteChannelList", this.siteService.getUseChannelList());
		// 사이트 코드
		parameter.addAttribute("searchConditionSiteList", this.siteService.getSiteList());
		// 답변상태 코드
		parameter.addAttribute("searchAswrStatCodeList", pair.getSecond().get(CommonCode.ASWR_STAT_CODE));

		return forward("/product/review/review-list");
	}

	/**
	 * @Desc : 상품후기 목록 조회
	 * @Method Name : searchReviewList
	 * @Date : 2019. 3. 26.
	 * @Author : hsjhsj19
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping
	public void searchReviewList(Parameter<BdProductReviewSearchVO> parameter) throws Exception {
		log.debug("상품후기 목록 조회");

		// 코드 목록 리스트화
		parameter.validate();

		Pageable<BdProductReviewSearchVO, BdProductReview> pageable = new Pageable<BdProductReviewSearchVO, BdProductReview>(
				parameter);

		BdProductReviewSearchVO bdProductReviewSearchVO = parameter.get();
		UserDetails user = LoginManager.getUserDetails();
		if (UtilsText.equals(user.getAuthApplySystemType(), Const.AUTH_APPLY_SYSTEM_TYPE_PO)) {
			bdProductReviewSearchVO.setVndrNo(user.getVndrNo());
		}

		Page<BdProductReview> productReviewList = this.productReviewService.selectProductReview(pageable);

		this.writeJson(parameter, productReviewList.getGrid());
	}

	/**
	 * @Desc : 상품후기 상세화면
	 * @Method Name : getDetail
	 * @Date : 2019. 4. 9.
	 * @Author : hsjhsj19
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("detail")
	public ModelAndView getDetail(Parameter<BdProductReview> parameter) throws Exception {
		log.debug("상품후기 상세 화면");

		String prdtRvwSeq = parameter.getString("prdtRvwSeq"); // 상품후기순번

		if (UtilsText.isNotBlank(prdtRvwSeq)) {
			// BD_상품후기
			BdProductReview review = this.productReviewService
					.searchProductReviewByPrimaryKey(Integer.parseInt(prdtRvwSeq));
			parameter.addAttribute("review", review);

			// 상품후기 포인트 지급되었다면 지급된 포인트 혜택 기간의 포인트양을 찾아 일치하는지 확인 후 일반,포토 후기포인트로 지정되었는지 판단이 필요
			// 포인트 지급불가가 N 포인트 지급여부 Y 포인트 지급취소 N인 항목만
			if (Const.BOOLEAN_FALSE.equals(review.getPointPayImpsbltYn())
					&& Const.BOOLEAN_TRUE.equals(review.getPointPayYn())
					&& Const.BOOLEAN_FALSE.equals(review.getPointPayCnclYn())) {

				CmOnlineMemberPolicy searchOnlineMemberPolicy = new CmOnlineMemberPolicy();
				searchOnlineMemberPolicy.setPlcySeq(review.getPlcySeq());

				Map<String, Object> result = onlinePolicyService.getOnlinePolicyData(searchOnlineMemberPolicy);
				CmOnlineMemberPolicy cmOnlineMemberPolicy = (CmOnlineMemberPolicy) result.get("DATA");

				if (cmOnlineMemberPolicy != null) {
					Integer payPointAmt = review.getPayPointAmt();
					Integer prdtRvwPointAmt = cmOnlineMemberPolicy.getPrdtRvwPointAmt();
					Integer photoPrdtRvwPointAmt = cmOnlineMemberPolicy.getPhotoPrdtRvwPointAmt();

					// 포인트 비교
					if (payPointAmt == prdtRvwPointAmt) {
						review.setPointPayType("G");
					} else if (payPointAmt == photoPrdtRvwPointAmt) {
						review.setPointPayType("P");
					} else {
						throw new Exception("상품 포인트 금액이 일치하지 않습니다.");
					}
				}
			}

			List<SyCodeDetail> codeSearchCondition = new ArrayList<SyCodeDetail>();
			// 상담유형 코드
			SyCodeDetail syCodeDetail = new SyCodeDetail();
			syCodeDetail.setCodeField(CommonCode.CNSL_TYPE_CODE);
			codeSearchCondition.add(syCodeDetail);

			// 상담분류 코드
			syCodeDetail = new SyCodeDetail();
			syCodeDetail.setCodeField(CommonCode.CNSL_TYPE_DTL_CODE);
			syCodeDetail.setAddInfo1(CommonCode.CNSL_TYPE_CODE_PRODUCT_REVIEW);
			codeSearchCondition.add(syCodeDetail);

			Map<String, List<SyCodeDetail>> codeListMap = commonCodeService.getCodeListByGroup(codeSearchCondition);

			parameter.addAttribute("cnslTypeCode", codeListMap.get(CommonCode.CNSL_TYPE_CODE)); // 상담유형
			parameter.addAttribute("cnslTypeDtlCode", codeListMap.get(CommonCode.CNSL_TYPE_DTL_CODE)); // 상담분류

		}

		return forward("/product/review/review-detail-popup");
	}

	/**
	 * @Desc : 상품후기 상세화면에서 저장
	 * @Method Name : save
	 * @Date : 2019. 4. 8.
	 * @Author : hsjhsj19
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("save")
	public void save(Parameter<BdProductReview[]> parameter) throws Exception {
		log.debug("상품후기 변경");

		BdProductReview[] reviews = parameter.get();

		productReviewService.updateProductReview(reviews);

		this.writeJson(parameter, reviews);
	}

	/**
	 * @Desc : 상품후기 상세화면에서 삭제
	 * @Method Name : delete
	 * @Date : 2019. 4. 8.
	 * @Author : hsjhsj19
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("delete")
	public void delete(Parameter<BdProductReview> parameter) throws Exception {
		log.debug("상품후기 삭제");

		BdProductReview review = parameter.get();

		productReviewService.deleteProductReview(review);

		this.writeJson(parameter, review);
	}

	/**
	 * @Desc : 상품후기 목록 변경사항 일괄저장
	 * @Method Name : batchSave
	 * @Date : 2019. 4. 8.
	 * @Author : hsjhsj19
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("batchSave")
	public void batchSave(Parameter<BdProductReview[]> parameter) throws Exception {
		log.debug("상품후기 변경 저장");

		BdProductReview[] reviews = parameter.get();

		productReviewService.batchSave(reviews);

		this.writeJson(parameter, reviews);
	}

}
