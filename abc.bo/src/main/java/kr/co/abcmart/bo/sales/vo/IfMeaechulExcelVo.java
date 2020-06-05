package kr.co.abcmart.bo.sales.vo;

import kr.co.abcmart.bo.sales.model.master.IfMaechul;
import kr.co.abcmart.common.bean.BaseBean;
import kr.co.abcmart.common.request.annotation.ParameterOption;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class IfMeaechulExcelVo extends BaseBean {

	private String cbcd;
	private String maejangcd;
	private String iljai;
	private String posno;
	private String grNo;
	private String seqNo;
	private String pan;
	private String hhmm;
	private String sawonno;
	private String srcmkcd;
	private String saleqty;
	private String saleamt;
	private String salehalin;
	private String saleenury;
	private String pointamt;
	private String coupon;
	private String eventgb;
	private String wonIlja;
	private String wonPosno;
	private String wonGrNo;
	private String regdate;
	private String condate;
	private String errorstatus;
	private String workdiv;
	private String ordno;
	private String itemsno;
	private String mallname;
	private String workday;
	private String chasu;
	private String safeKey;
	private String eventpointamt;

	@ParameterOption(target = "rowSeq")
	private IfMaechul[] asNos;

}
