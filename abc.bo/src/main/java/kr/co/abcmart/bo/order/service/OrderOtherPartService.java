package kr.co.abcmart.bo.order.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.order.model.master.OcOrder;
import kr.co.abcmart.bo.order.model.master.OcOrderProduct;
import kr.co.abcmart.bo.order.repository.master.OrderOtherPartDao;
import kr.co.abcmart.bo.order.vo.OrderOtherPartVo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderOtherPartService {

	@Autowired
	private OrderOtherPartDao orderOtherPartDao;

	/**
	 * 
	 * @Desc : 포인트 사후적립 -회원
	 * @Method Name : getOrderMemberPointExportList
	 * @Date : 2019. 5. 22.
	 * @Author : NKB
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<OcOrder> getOrderMemberPointExportList(OcOrder params) throws Exception {
		return orderOtherPartDao.getOrderMemberPointExportList(params);
	}

	/**
	 * @Desc : 구매확정 후 30 진행 여부 유효성 검사
	 * @Method Name : getOverDayYnAfterBuyDecision
	 * @Date : 2019. 4. 24.
	 * @Author : 이강수
	 * @param OcOrder
	 * @return OrderOtherPartVo
	 * @throws Exception
	 */
	public OrderOtherPartVo getOverDayYnAfterBuyDecision(OcOrder param) throws Exception {
		return orderOtherPartDao.selectOverDayYnAfterBuyDecision(param);
	}

	/**
	 * @Desc :온라인/ 매장 사후적립을 위한 적합성 체크 프로시저
	 * @Method Name : callProcedureForDecodeOrderNum
	 * @Date : 2019. 4. 24.
	 * @Author : 유성민
	 * @param onOffType
	 * @param buyNoText
	 * @param crtfcNoText
	 * @return output
	 */
	public String callProcedureForDecodeOrderNum(String onOffType, String buyNoText, String crtfcNoText)
			throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("onOffType", onOffType);
		map.put("buyNoText", buyNoText);
		map.put("crtfcNoText", crtfcNoText);

		orderOtherPartDao.callProcedureForDecodeOrderNum(map);
		return (String) map.get("output");
	}

	/**
	 * @Desc : 주문상품 정보 조회
	 * @Method Name : getOrderProductInfo
	 * @Date : 2019. 4. 25.
	 * @Author : 유성민
	 * @param orderProductParams
	 * @return
	 */
	public List<OcOrderProduct> getOrderProductInfoList(OcOrderProduct params) throws Exception {
		return orderOtherPartDao.getOrderProductInfoList(params);
	}

	/**
	 * @Desc : 포인트 적럽여부 update
	 * @Method Name : updatePointSaveYn
	 * @Date : 2019. 4. 25.
	 * @Author : 유성민
	 * @param OcOrder params
	 */
	public int updatePointSaveYn(OcOrder params) throws Exception {
		return orderOtherPartDao.updatePointSaveYn(params);
	}

	/**
	 * @Desc : 주문, 클레임, AS의 갯수 - 진행중 대상건수
	 * @Method Name : getProgressOrderClmAsCount
	 * @Date : 2020. 3. 13.
	 * @Author : 3TOP
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public OrderOtherPartVo getProgressOrderClmAsCount(OcOrder param) throws Exception {
		return orderOtherPartDao.selectProgressOrderClmAsCount(param);
	}

}
