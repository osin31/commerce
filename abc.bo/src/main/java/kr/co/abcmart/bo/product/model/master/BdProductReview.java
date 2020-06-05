package kr.co.abcmart.bo.product.model.master;

import kr.co.abcmart.bo.order.model.master.IfOffDealHistory;
import kr.co.abcmart.bo.order.model.master.OcOrderProduct;
import kr.co.abcmart.bo.product.model.master.base.BaseBdProductReview;
import kr.co.abcmart.util.UtilsMasking;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class BdProductReview extends BaseBdProductReview {

	/** 상품후기 이미지 */
	private BdProductReviewImage[] productReviewImages;

	/** 상품후기 평점 */
	private BdProductReviewEvlt[] productReviewEvlts;

	/** 주문상품 */
	private OcOrderProduct orderProduct;

	/** 오프라인주문이력 */
	private IfOffDealHistory offDealHistory;

	/** 자사/입점 */
	private String mmnyPrdtYn;

	/** 회원등급 */
	private String memberTypeCode;

	/** 회원등급 */
	private String memberTypeCodeName;

	/** 작성자 ID */
	private String memberId;

	/** 작성자 */
	private String memberName;

	/** 사진구분 */
	private String reviewImage;

	/** 사진 URL */
	private String reviewImageUrl;

	/** 사진명 */
	private String reviewImageName;

	/** 상품이미지 */
	private String productImageUrl;

	/** 상품이미지대체텍스트 */
	private String productAltrnText;

	/** 상품명 */
	private String prdtName;

	/** 수정자 ID */
	private String moderId;

	/** 수정자 */
	private String moderName;

	/** 확인자 ID */
	private String cnfrmrId;

	/** 확인자 */
	private String cnfrmrName;

	/** 주문일 */
	private Integer orderDtm;

	/** 구매확정일 */
	private Integer buyDcsnDtm;

	/** 사이트명 */
	private String siteName;

	/** ERP상품코드 */
	private String vndrPrdtNoText;

	/** 입점사명 */
	private String vndrName;

	/** 답변상태코드명 */
	private String aswrStatCodeName;

	/** 채널명 */
	private String chnnlName;

	/** 브랜드명 */
	private String brandName;

	/** 채널번호 */
	private String chnnlNo;

	/** 표준카테고리명 */
	private String stdCtgrName;

	/** 스타일 */
	private String styleInfo;

	/** 포인트 지급 구분 */
	private String pointPayType;

	/** 답변자명 */
	private String aswrName;

	/** 답변자 ID */
	private String aswrId;

	/*
	 * @Override public void validate() throws ValidatorException {
	 *
	 * if (UtilsText.isBlank(getAswrContText())) { // 답변내용
	 * validationMessage("product.valid.product.review.aswrContText"); }
	 *
	 *
	 * }
	 */

	// 작성자 정보 마스킹 처리
	public String getMemberInfo() {
		return UtilsMasking.gridMasking(getMemberId(), getMemberName());
	}

	// 수정자 정보 마스킹 처리
	public String getModerInfo() {
		return UtilsMasking.gridMasking(getModerId(), getModerName());
	}

	// 답변자 정보 마스킹 처리
	public String getAswrInfo() {
		return UtilsMasking.gridMasking(getAswrId(), getAswrName());
	}

}
