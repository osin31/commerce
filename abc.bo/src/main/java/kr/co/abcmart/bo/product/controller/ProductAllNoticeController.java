package kr.co.abcmart.bo.product.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.abcmart.bo.product.service.ProductAllNoticeService;
import kr.co.abcmart.bo.product.service.ProductFileService;
import kr.co.abcmart.bo.product.vo.PdProductEditorUploadVO;
import kr.co.abcmart.bo.product.vo.VdVendorProductAllNoticeSearchVO;
import kr.co.abcmart.bo.vendor.model.master.VdVendor;
import kr.co.abcmart.bo.vendor.model.master.VdVendorProductAllNotice;
import kr.co.abcmart.bo.vendor.service.VendorService;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.common.util.UtilsObject;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.util.UtilsDate;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 상품전체공지 컨트롤러
 * @FileName : ProductAllNotciceController.java
 * @Project : abc.bo
 * @Date : 2019. 4. 15.
 * @Author : hsjhsj19
 */
@Slf4j
@Controller
@RequestMapping("product/allNotice")
public class ProductAllNoticeController extends BaseController {

	@Autowired
	private ProductAllNoticeService productAllNoticeService;

	@Autowired
	private VendorService vendorService;

	@Autowired
	private ProductFileService productFileService;

	/**
	 * @Desc : 상품전체공지 목록 페이지
	 * @Method Name : getList
	 * @Date : 2019. 4. 16.
	 * @Author : hsjhsj19
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping
	public ModelAndView getList(Parameter<?> parameter) throws Exception {
		log.debug("상품전체공지 목록 페이지");
		if (UtilsText.equals(LoginManager.getUserDetails().getAuthApplySystemType(), Const.AUTH_APPLY_SYSTEM_TYPE_BO)) {
			parameter.addAttribute("authApplySystemType", Const.AUTH_APPLY_SYSTEM_TYPE_BO);
		} else {
			parameter.addAttribute("authApplySystemType", Const.AUTH_APPLY_SYSTEM_TYPE_PO);
		}
		return forward("/product/all-notice/all-notice-list");
	}

	/**
	 * @Desc : 상품 목록 조회
	 * @Method Name : searchProductAllNoticeList
	 * @Date : 2019. 4. 16.
	 * @Author : hsjhsj19
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping
	public void searchProductAllNoticeList(Parameter<VdVendorProductAllNoticeSearchVO> parameter) throws Exception {
		log.debug("상품 목록 조회");
		Pageable<VdVendorProductAllNoticeSearchVO, VdVendorProductAllNotice> pageable = new Pageable<VdVendorProductAllNoticeSearchVO, VdVendorProductAllNotice>(
				parameter);

		if (UtilsText.equals(LoginManager.getUserDetails().getAuthApplySystemType(), Const.AUTH_APPLY_SYSTEM_TYPE_PO)) {
			pageable.getBean().setVndrNo(LoginManager.getUserDetails().getVndrNo());
		}

		Page<VdVendorProductAllNotice> productList = this.productAllNoticeService.searchProductAllNotice(pageable);

		this.writeJson(parameter, productList.getGrid());

	}

	/**
	 * @Desc : 상품전체공지 상세 화면
	 * @Method Name : getDetail
	 * @Date : 2019. 4. 16.
	 * @Author : hsjhsj19
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("detail")
	public ModelAndView getDetail(Parameter<?> parameter) throws Exception {
		log.debug("상품전체공지 등록/수정 화면");

		int vndrPrdtAllNotcSeq = parameter.getInt("vndrPrdtAllNotcSeq");
		String vndrNo = parameter.getString("vndrNo");

		// 해당 DTO를 다 볼 수 있다.
		// log.debug(BeanUtils.describe(vdVendorProductAllNotice).toString());

		if (UtilsObject.isNotEmpty(vndrPrdtAllNotcSeq) && UtilsObject.isNotEmpty(vndrNo)) {
			// VD_업체상품전체공지 조회
			VdVendorProductAllNotice vdVendorProductAllNotice = new VdVendorProductAllNotice();
			vdVendorProductAllNotice.setVndrPrdtAllNotcSeq(vndrPrdtAllNotcSeq);
			vdVendorProductAllNotice.setVndrNo(vndrNo);
			parameter.addAttribute("allNotice",
					productAllNoticeService.searchProductAllNoticeByPrimaryKey(vdVendorProductAllNotice));
		}

		if (UtilsText.equals(LoginManager.getUserDetails().getAuthApplySystemType(), Const.AUTH_APPLY_SYSTEM_TYPE_BO)) {
			parameter.addAttribute("authApplySystemType", Const.AUTH_APPLY_SYSTEM_TYPE_BO);
		} else {
			parameter.addAttribute("authApplySystemType", Const.AUTH_APPLY_SYSTEM_TYPE_PO);

			VdVendor vndr = new VdVendor();
			vndr.setVndrNo(LoginManager.getUserDetails().getVndrNo());
			vndr = vendorService.getVendorBaseInfo(vndr);

			parameter.addAttribute("vndrNo", vndr.getVndrNo());
			parameter.addAttribute("vndrName", vndr.getVndrName());
		}

		return forward("/product/all-notice/all-notice-detail");
	}

	/**
	 * @Desc : 상품전체공지 등록
	 * @Method Name : save
	 * @Date : 2019. 4. 16.
	 * @Author : hsjhsj19
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("save")
	public void save(Parameter<VdVendorProductAllNotice> parameter) throws Exception {

		parameter.validate();

		this.writeJson(parameter, this.productAllNoticeService.saveProductAllNotice(parameter.get()));
	}

	/**
	 * @Desc : 에디터에 의한 이미지 업로드
	 * @Method Name : uploadByEditor
	 * @Date : 2019. 9. 23.
	 * @Author : tennessee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/editer/upload")
	public void uploadByEditor(Parameter<PdProductEditorUploadVO> parameter) throws Exception {
		Map<String, String> result = this.productFileService.uploadFile(parameter.get().getUpload(),
				Arrays.asList(Const.IMG_SRC_PRODUCT_ALL_NOTICE_PREFIX,
						UtilsDate.today(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS)));
		result.put("uploaded", "1"); // 에디터에서 사용 될 성공여부
		result.put("url", result.get(ProductFileService.KEY_FILE_URL)); // 에디터에서 사용 될 URL
		this.writeJson(parameter, result);

	}
}
