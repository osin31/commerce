package kr.co.abcmart.bo.order.model.master;

import kr.co.abcmart.bo.order.model.master.base.BaseOcOrderMemo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class OcOrderMemo extends BaseOcOrderMemo {

	private String memoGbn;

	private String adminId;

	private String adminName;

}
