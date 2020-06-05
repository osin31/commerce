package kr.co.abcmart.bo.product.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.product.model.master.PdProduct;
import kr.co.abcmart.bo.product.model.master.PdProductAddInfo;
import kr.co.abcmart.bo.product.model.master.PdProductApprovalHistory;
import kr.co.abcmart.bo.product.model.master.PdProductColor;
import kr.co.abcmart.bo.product.model.master.PdProductIcon;
import kr.co.abcmart.bo.product.repository.master.PdProductAddInfoDao;
import kr.co.abcmart.bo.product.repository.master.PdProductApprovalHistoryDao;
import kr.co.abcmart.bo.product.repository.master.PdProductChangeHistoryDao;
import kr.co.abcmart.bo.product.repository.master.PdProductColorDao;
import kr.co.abcmart.bo.product.repository.master.PdProductDao;
import kr.co.abcmart.bo.product.repository.master.PdProductDetailDao;
import kr.co.abcmart.bo.product.repository.master.PdProductIconDao;
import kr.co.abcmart.bo.product.repository.master.PdProductMemoDao;
import kr.co.abcmart.bo.product.repository.master.PdProductPriceHistoryDao;
import kr.co.abcmart.bo.product.repository.master.PdProductRelationFileDao;
import kr.co.abcmart.bo.product.repository.master.PdRelationProductDao;
import kr.co.abcmart.bo.product.vo.PdProductApprovalSearchVO;
import kr.co.abcmart.bo.system.model.master.SyAdmin;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.util.UtilsArray;
import kr.co.abcmart.common.util.UtilsObject;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 상품승인대상관리 (PO) 서비스
 * @FileName : ProductApprovalService.java
 * @Project : abc.bo
 * @Date : 2019. 3. 29.
 * @Author : hsjhsj19
 */
@Slf4j
@Service
public class ProductApprovalService {

	@Autowired
	private PdProductDao productDao;

	@Autowired
	private PdProductApprovalHistoryDao productApprovalHistoryDao;

	@Autowired
	private PdProductMemoDao productMemoDao;

	@Autowired
	private PdProductRelationFileDao productRelationFileDao;

	@Autowired
	private PdRelationProductDao relationProductDao;

	@Autowired
	private PdProductDetailDao detailDao;

	@Autowired
	private PdProductAddInfoDao productAddInfoDao;

	@Autowired
	private PdProductPriceHistoryDao productPriceHistoryDao;

	@Autowired
	private PdProductIconDao productIconDao;

	@Autowired
	private PdProductChangeHistoryDao productChangeHistoryDao;

	@Autowired
	private PdProductColorDao productColorDao;

	/**
	 * @Desc : 상품 승인 목록 조회
	 * @Method Name : selectProduct
	 * @Date : 2019. 4. 1.
	 * @Author : hsjhsj19
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<PdProduct> selectProduct(Pageable<PdProductApprovalSearchVO, PdProduct> pageable) throws Exception {
		Integer count = this.productDao.selectProductApprovalCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(this.productDao.selectProductApproval(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 상품승인관리(PO) 선택상품 일괄또는 단일 수정(승인,반려)
	 * @Method Name : batchModify
	 * @Date : 2019. 4. 2.
	 * @Author : hsjhsj19
	 * @param pdProductApprovalHistories
	 * @throws Exception
	 */
	public void batchModify(PdProductApprovalHistory[] pdProductApprovalHistories) throws Exception {

	}

	/**
	 * @Desc : 입점상품 승인관리 (BO) 선택상품 일괄 또는 단일 수정(승인,반려)
	 * @Method Name : batchVndrModify
	 * @Date : 2019. 4. 3.
	 * @Author : hsjhsj19
	 * @param pdProductApprovalHistories
	 * @throws Exception
	 */
	public int batchVndrModify(PdProductApprovalHistory[] pdProductApprovalHistories) throws Exception {
		return approvalModify(pdProductApprovalHistories, CommonCode.PRDT_APRV_REQ_CODE_MODIFY_INFO); // 정보변경
	}

	/**
	 * @Desc : 상품승인대상관리 (PO) 선택 상품 일괄 승인요청 또는 반려
	 * @Method Name : modify
	 * @Date : 2019. 4. 3.
	 * @Author : hsjhsj19
	 * @param pdProductApprovalHistories
	 * @throws Exception
	 */
	public int modify(PdProductApprovalHistory[] pdProductApprovalHistories) throws Exception {
		return approvalModify(pdProductApprovalHistories, CommonCode.PRDT_APRV_REQ_CODE_NEW_PRODUCT); // 신규상품
	}

	private int approvalModify(PdProductApprovalHistory[] pdProductApprovalHistories, String prdtAprvReqCode)
			throws Exception {
		int cnt = 0;
		// 세션 정보 호출
		UserDetails user = LoginManager.getUserDetails();

		for (PdProductApprovalHistory productApprovalHistory : pdProductApprovalHistories) {
			productApprovalHistory.setPrdtAprvReqCode(prdtAprvReqCode);

			switch (productApprovalHistory.getAprvStatCode() + "") {
			// PD_PRODUCT 임시저장 코드값 : 10000 / PD_PRODUCT_APPROVAL_HISTORY 승인요청 코드값 : 없음
			case CommonCode.APRV_STAT_CODE_TEMPORARY:
				continue;
			// PD_PRODUCT 승인요청 코드값 : 10001 / PD_PRODUCT_APPROVAL_HISTORY 승인요청 코드값 : 10000
			case CommonCode.APRV_STAT_CODE_REQUEST:
				productApprovalHistory.setPrdtAprvStatCode(CommonCode.PRDT_APRV_STAT_CODE_REQUEST);
				break;
			// PD_PRODUCT 승인대기 코드값 : 10002 / PD_PRODUCT_APPROVAL_HISTORY 승인요청 코드값 : 10001
			case CommonCode.APRV_STAT_CODE_STANDBY:
				productApprovalHistory.setPrdtAprvStatCode(CommonCode.PRDT_APRV_STAT_CODE_WAIT);
				break;
			// PD_PRODUCT 승인반려 코드값 : 10003 / PD_PRODUCT_APPROVAL_HISTORY 승인반려 코드값 : 10002
			case CommonCode.APRV_STAT_CODE_REJECT:
				productApprovalHistory.setPrdtAprvStatCode(CommonCode.PRDT_APRV_STAT_CODE_REJECT);
				break;
			// PD_PRODUCT 승인완료 코드값 : 10004 / PD_PRODUCT_APPROVAL_HISTORY 승인완료 코드값 : 10003
			case CommonCode.APRV_STAT_CODE_CONFIRM:
				productApprovalHistory.setPrdtAprvStatCode(CommonCode.PRDT_APRV_STAT_CODE_COMPLETE);
				break;
			default:
				throw new ValidationException("저장된 승인상태코드가 없습니다.");
			}

			productApprovalHistory.setReqtrNo(productApprovalHistory.getRgsterNo());
			productApprovalHistory.setReqDtm(productApprovalHistory.getRgstDtm());
			productApprovalHistory.setAprverNo(user.getAdminNo());
			productApprovalHistory.setAprverDtm(new Timestamp(new Date().getTime()));

			productApprovalHistory.validate();

			productApprovalHistoryDao.insertWithSelectKey(productApprovalHistory);

			PdProduct pdProduct = new PdProduct();
			pdProduct.setPrdtNo(productApprovalHistory.getPrdtNo());
			pdProduct.setAprvStatCode(productApprovalHistory.getAprvStatCode());
			pdProduct.setModerNo(user.getAdminNo());
			pdProduct.setModDtm(new Timestamp(new Date().getTime()));

			// 승인 완료일시에 최종 입고일 추가
			if (UtilsText.equals(productApprovalHistory.getPrdtAprvStatCode(),
					CommonCode.PRDT_APRV_STAT_CODE_COMPLETE)) {
				pdProduct.setLastWrhsDay(new Timestamp(new Date().getTime()));
			}

			cnt += productDao.update(pdProduct);
		}
		return cnt;
	}

	/**
	 * @Desc : 선택상품 일괄 삭제
	 * @Method Name : delete
	 * @Date : 2019. 4. 3.
	 * @Author : hsjhsj19
	 * @param pdProducts
	 * @throws Exception
	 */
	public int delete(PdProduct[] pdProducts) throws Exception {
		int cnt = 0;
		for (PdProduct pdProduct : pdProducts) {
			// 검색 조건 세팅
			UserDetails user = LoginManager.getUserDetails();
			pdProduct.setVndrNo(user.getVndrNo());
			pdProduct.setRgstDtm(null);
			pdProduct.setModDtm(null); // 2020.05.14 : PO 상품승인대상목록에서 삭제 오류 수정
			pdProduct.setAprverDtm(null);
			List<PdProduct> selectList = productDao.select(pdProduct);
			
			if(UtilsArray.isEmpty(selectList)) { // 2020.05.14 : PO 상품승인대상목록에서 삭제 alert 수정
				throw new Exception("삭제할 수 있는 상품이 존재하지 않습니다.");
			}

			String prdtNo = selectList.get(0).getPrdtNo();

			if (UtilsObject.isEmpty(prdtNo)) {
				continue;
			}

			productMemoDao.deleteByPrdtNo(prdtNo); // 상품메모 삭제

			productRelationFileDao.deleteByPrdtNo(prdtNo); // 상품관련파일 삭제

			detailDao.deleteByPrdtNo(prdtNo); // 상품상세 삭제

			relationProductDao.deleteByPrdtNo(prdtNo); // 관련상품 삭제

			PdProductAddInfo addInfo = new PdProductAddInfo();
			addInfo.setPrdtNo(prdtNo);
			productAddInfoDao.deleteByForeignKeys(addInfo); // 상품추가정보 삭제

			productPriceHistoryDao.deleteByPrdtNo(prdtNo); // 상품가격이력 삭제

			PdProductColor color = new PdProductColor();
			color.setPrdtNo(prdtNo);
			productColorDao.deleteByForeignKeys(color); // 상품색상 삭제

			PdProductIcon icon = new PdProductIcon();
			icon.setPrdtNo(prdtNo);
			productIconDao.deleteByForeignKeys(icon); // 상품아이콘 삭제

			productApprovalHistoryDao.deleteByPrdtNo(prdtNo); // 상품승인이력 삭제

			productChangeHistoryDao.deleteByPrdtNo(prdtNo); // 상품변경이력 삭제

			cnt += productDao.delete(pdProduct); // 상품 삭제
		}
		return cnt;
	}

	public List<SyAdmin> getAprvList() throws Exception {
		return this.productDao.selectAprvAdminList();
	}

}
