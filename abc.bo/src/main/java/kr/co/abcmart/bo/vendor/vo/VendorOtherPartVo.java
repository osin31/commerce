
package kr.co.abcmart.bo.vendor.vo;

import kr.co.abcmart.common.bean.BaseBean;
import lombok.Data;

@Data
public class VendorOtherPartVo extends BaseBean {

	private static final long serialVersionUID = -8678034413548336504L;

	private String vndrNo;

	private java.lang.Short cmsnRate; // 판매수수료율

	private String empDscntYn; // 임직원 할인여부

	private java.lang.Short empDscntRate; // 임직원 할인율

}
