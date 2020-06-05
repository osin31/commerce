package kr.co.abcmart.bo.product.model.master;

import kr.co.abcmart.bo.product.model.master.base.BaseDpBrandVisual;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class DpBrandVisual extends BaseDpBrandVisual {

	/** IBSheet 고유키 */
	private String seq;

	/** IBSheet 상태값 */
	private String status;

	/** IBSheet 체크박스 */
	private String checked;

	/** IBSheet 대상상품 수량 */
	private String productCount;

	/** IBSheet 대상 상품관리 */
	private String manageProduct = "대상 상품관리";

	/** IBSheet 전시 이미지 */
	private String imagePreview;

	/** 조회용 상품코드 */
	private String prdtNo;

}
