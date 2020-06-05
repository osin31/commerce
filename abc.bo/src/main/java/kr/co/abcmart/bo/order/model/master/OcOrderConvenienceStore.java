package kr.co.abcmart.bo.order.model.master;

import kr.co.abcmart.bo.order.model.master.base.BaseOcOrderConvenienceStore;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class OcOrderConvenienceStore extends BaseOcOrderConvenienceStore {
	/**
	 * 
	 * 설명 : 편의점 코드
	 */
	private String hoCode;

}
