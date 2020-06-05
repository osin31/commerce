package kr.co.abcmart.bo.product.model.master;

import kr.co.abcmart.bo.product.model.master.base.BaseDpBrandStyle;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class DpBrandStyle extends BaseDpBrandStyle {

	/** IBSheet 고유키 */
	private String seq;

	/** IBSheet 상태값 */
	private String status;

	/** IBSheet 대상상품 수량 */
	private int productCount;

	/** IBSheet 대상 상품관리 */
	private String manageProduct = "대상 상품관리";

}
