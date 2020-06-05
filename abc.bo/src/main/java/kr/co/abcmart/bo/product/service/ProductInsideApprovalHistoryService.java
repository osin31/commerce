package kr.co.abcmart.bo.product.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.product.model.master.PdProduct;
import kr.co.abcmart.bo.product.model.master.PdProductApprovalHistory;
import kr.co.abcmart.bo.product.repository.master.PdProductApprovalHistoryDao;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.util.UtilsCollection;
import kr.co.abcmart.common.util.UtilsObject;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 상품 승인 이력 서비스
 * @FileName : ProductInsideApprovalHistoryService.java
 * @Project : abc.bo
 * @Date : 2019. 4. 17.
 * @Author : tennessee
 */
@Slf4j
@Service
public class ProductInsideApprovalHistoryService {

	@Autowired
	private PdProductApprovalHistoryDao productApprovalHistoryDao;

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductReflectionService productReflectionService;

	/**
	 * @Desc : 상품 승인이력 저장. 임시저장은 이력을 남기지 않음.
	 * @Method Name : insert
	 * @Date : 2019. 4. 17.
	 * @Author : tennessee
	 * @param product
	 * @param isNew
	 * @throws Exception
	 */
	public void insert(PdProduct product) throws Exception {
		if (UtilsText.isNotBlank(product.getType())) {
			// 저장유형과 승인이력 저장 중 설정된 변경이력유형이 있는 경우에만 진입

			if (UtilsText.equals("Y", product.getMmnyPrdtYn())
					&& UtilsText.equals(Const.ROLE_ADMIN_GROUP, LoginManager.getUserDetails().getUpAuthNo())) {
				// 자사상품 및 BO권한인 경우
				this.insertByAdmin(product);
			} else {
				// BO권한이면서 입점상품인경우 및 PO권한인 경우
				this.insertByVendor(product);
			}
		}
	}

	/**
	 * @Desc : 관리자가 자사상품을 저장하는 경우, 승인이력 처리
	 * @Method Name : insertByAdmin
	 * @Date : 2019. 4. 29.
	 * @Author : tennessee
	 * @param product
	 * @throws Exception
	 */
	private void insertByAdmin(PdProduct product) throws Exception {
		// 기존 승인상태 조회
		PdProductApprovalHistory lastestRecord = this.searchLastestByPrdtNo(product.getPrdtNo());
		PdProductApprovalHistory newestRecord = new PdProductApprovalHistory();
		if (UtilsObject.isEmpty(lastestRecord) || UtilsText.equals(PdProduct.TYPE_SAVE, product.getType())) {
			// 기존 이력 없음. 신규 등록.
			// NOTICE 신규등록 상품은 저장하지 않습니다.

			// 상품기본정보 승인정보만 갱신합니다.
			newestRecord.setPrdtAprvReqCode(CommonCode.PRDT_APRV_REQ_CODE_NEW_PRODUCT); // 신규상품
		} else {
			// 기존 이력 있음. 수정 등록.
			// 상품승인요청코드는 변경처리에서 설정된 코드정보를 통해 설정
			if (UtilsText.isNotBlank(product.getPrdtAprvReqCode())) {
				newestRecord.setPrdtAprvReqCode(product.getPrdtAprvReqCode());
			} else {
				newestRecord.setPrdtAprvReqCode(CommonCode.PRDT_APRV_REQ_CODE_MODIFY_INFO);
			}
		}

		// 상품정보 기록
		product.setAprvStatCode(CommonCode.APRV_STAT_CODE_CONFIRM);
		this.productReflectionService.setAprverInfo(product);

		newestRecord.setPrdtNo(product.getPrdtNo());
		newestRecord.setReqtrNo(LoginManager.getUserDetails().getAdminNo());
		newestRecord.setReqDtm(new Timestamp(new Date().getTime()));
		newestRecord.setPrdtAprvStatCode(CommonCode.PRDT_APRV_STAT_CODE_COMPLETE); // 승인완료
		newestRecord.setAprverNo(LoginManager.getUserDetails().getAdminNo());
		newestRecord.setAprverDtm(new Timestamp(new Date().getTime()));
		this.productApprovalHistoryDao.insertWithSelectKey(newestRecord);
	}

	/**
	 * @Desc : 관리자 또는 입점업체관계자가 상품을 저장하는 경우, 승인이력 처리
	 * @Method Name : insertByVendor
	 * @Date : 2019. 4. 29.
	 * @Author : tennessee
	 * @param product
	 * @throws Exception
	 */
	private void insertByVendor(PdProduct product) throws Exception {
		// 기존 승인상태 조회
		PdProductApprovalHistory lastestRecord = this.searchLastestByPrdtNo(product.getPrdtNo());
		PdProductApprovalHistory newestRecord = new PdProductApprovalHistory();
		if (UtilsObject.isEmpty(lastestRecord)) {
			// 기존 이력 없음. 신규 등록.
			// 상품승인요청코드는 신규상픔으로 설정됨
			newestRecord.setPrdtAprvReqCode(CommonCode.PRDT_APRV_REQ_CODE_NEW_PRODUCT); // 신규상품
		} else {
			// 기존 이력 있음. 수정 등록.
			// 상품승인요청코드는 정보/가격 변경여부 확인으로 설정됨
			if (UtilsText.isNotBlank(product.getPrdtAprvReqCode())) {
				newestRecord.setPrdtAprvReqCode(product.getPrdtAprvReqCode());
			} else {
				// 변경이력 등록 시 변경정보가 없다고 판단된 경우, 정보수정으로 설정
				newestRecord.setPrdtAprvReqCode(CommonCode.PRDT_APRV_REQ_CODE_MODIFY_INFO);
			}
		}

		// 기존 정보 조회
		PdProduct storedProduct = this.productService.getProduct(product.getPrdtNo(), product.getSiteNo(),
				product.getChnnlNo(), product.getVndrPrdtNoText());

		if (UtilsObject.isNotEmpty(storedProduct)
				&& UtilsText.equals(CommonCode.APRV_STAT_CODE_CONFIRM, storedProduct.getAprvStatCode())) {
			// 기 저장된 상품정보가 있으며, 그 상품승인상태코드가 승인완료된 경우
			newestRecord.setPrdtAprvStatCode(CommonCode.PRDT_APRV_STAT_CODE_COMPLETE); // 승인완료
			// 상품 승인상태 설정. 앞단에서 재설정 된 상태이므로 작업 안함.
		} else {
			newestRecord.setPrdtAprvStatCode(CommonCode.PRDT_APRV_STAT_CODE_REQUEST); // 승인요청
			product.setAprvStatCode(CommonCode.APRV_STAT_CODE_REQUEST); // 상품 승인상태 설정
		}
		// 상품기본정보 승인정보 갱신
		this.productReflectionService.setAprverInfo(product);

		newestRecord.setPrdtNo(product.getPrdtNo());
		this.productReflectionService.setReqtrInfo(newestRecord); // 요청자 정보 입력
		this.productReflectionService.setAprverInfo(newestRecord); // 승인자정보 입력

		this.productApprovalHistoryDao.insertWithSelectKey(newestRecord);
	}

	/**
	 * @Desc : 상품 번호에 대한 이력 역순 조회
	 * @Method Name : searchWithDescentOrderByPrdtNo
	 * @Date : 2019. 4. 18.
	 * @Author : tennessee
	 * @param prdtNo
	 * @return
	 * @throws Exception
	 */
	private List<PdProductApprovalHistory> searchWithDescentOrderByPrdtNo(String prdtNo) throws Exception {
		return this.productApprovalHistoryDao.selectWithDescentOrder(prdtNo);
	}

	/**
	 * @Desc : 상품 번호에 대한 최근 이력 조회
	 * @Method Name : searchLastestByPrdtNo
	 * @Date : 2019. 4. 18.
	 * @Author : tennessee
	 * @param prdtNo
	 * @return
	 * @throws Exception
	 */
	private PdProductApprovalHistory searchLastestByPrdtNo(String prdtNo) throws Exception {
		List<PdProductApprovalHistory> result = this.searchWithDescentOrderByPrdtNo(prdtNo);
		return UtilsCollection.isNotEmpty(result) ? result.get(0) : null;
	}

	/**
	 * @Desc : 한 상품에 대한 이력 조회
	 * @Method Name : getProdutChangeHistory
	 * @Date : 2019. 4. 23.
	 * @Author : tennessee
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<PdProductApprovalHistory> getProdutChangeHistory(
			Pageable<PdProductApprovalHistory, PdProductApprovalHistory> pageable) throws Exception {
		Integer count = this.productApprovalHistoryDao.selectApprovalHistoryByPrdtNoTotalCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(this.productApprovalHistoryDao.selectApprovalHistoryByPrdtNo(pageable));

			for (PdProductApprovalHistory item : pageable.getContent()) {
				item.setPrivacy();
			}
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 조회 후 한 상품에 대한 승인 이력 추가
	 * @Method Name : setProductApprovalHistory
	 * @Date : 2019. 5. 9.
	 * @Author : hsjhsj19
	 * @param product
	 * @throws Exception
	 */
	public void setProductApprovalHistory(PdProduct product) throws Exception {
		if (!UtilsObject.isEmpty(product) && UtilsText.isNotBlank(product.getPrdtNo())) {
			PdProductApprovalHistory result = this.getApplyingApprovalByPrdtNo(product.getPrdtNo());
			if (UtilsObject.isNotEmpty(result)) {
				product.setProductApprovalHistory(new PdProductApprovalHistory[] { result });
			}
		}
	}

	/**
	 * @Desc : 최근 상품에 대한 승인 이력 조회
	 * @Method Name : getApplyingApprovalByPrdtNo
	 * @Date : 2019. 5. 9.
	 * @Author : hsjhsj19
	 * @param prdtNo
	 * @return
	 * @throws Exception
	 */
	private PdProductApprovalHistory getApplyingApprovalByPrdtNo(String prdtNo) throws Exception {
		return this.productApprovalHistoryDao.selectApplyingApprovalByPrdtNo(prdtNo);
	}

	/**
	 * @Desc : 상품 승인 이력 등록
	 * @Method Name : insert
	 * @Date : 2019. 7. 19.
	 * @Author : tennessee
	 * @param productApprovalHistory
	 * @throws Exception
	 */
	public void insert(PdProductApprovalHistory productApprovalHistory) throws Exception {
		this.productApprovalHistoryDao.insertWithSelectKey(productApprovalHistory);
	}

}
