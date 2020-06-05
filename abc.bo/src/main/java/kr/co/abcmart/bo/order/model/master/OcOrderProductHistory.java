package kr.co.abcmart.bo.order.model.master;

import kr.co.abcmart.bo.order.model.master.base.BaseOcOrderProductHistory;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class OcOrderProductHistory extends BaseOcOrderProductHistory {

	private String adminId;

	private String adminName;

}
