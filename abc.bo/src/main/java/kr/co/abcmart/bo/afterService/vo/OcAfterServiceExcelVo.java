/**
 * 
 */
package kr.co.abcmart.bo.afterService.vo;

import kr.co.abcmart.bo.afterService.model.master.OcAsAccept;
import kr.co.abcmart.common.bean.BaseBean;
import kr.co.abcmart.common.request.annotation.ParameterOption;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 엑셀에 필요한 변수
 * @FileName : OcAfterServiceExcelVo.java
 * @Project : abc.bo
 * @Date : 2019. 3. 11.
 * @Author : lee
 */
@Slf4j
@Data
public class OcAfterServiceExcelVo extends BaseBean {

	private String siteName;

	private String asStatCodeName;

	private String asAcceptNo;

	private String orderNo;

	private String dlvyIdText;

	private String prdtNo;

	private String styleInfo;

	private String prdtName;

	private String optnName;

	private String pymntAmt;

	private String asRsnName;

	private String asAcceptContText;

	private String asProcess;

	private String addDlvyAmt;

	private String methodOfPayment;

	private String asAmt;

	private String dlvrName;

	private String unProcYn;

	@ParameterOption(target = "asAcceptNo")
	private OcAsAccept[] asNos;

}
