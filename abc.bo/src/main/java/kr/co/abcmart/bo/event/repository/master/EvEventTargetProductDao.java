package kr.co.abcmart.bo.event.repository.master;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.event.model.master.EvEventTargetProduct;
import kr.co.abcmart.bo.event.repository.master.base.BaseEvEventTargetProductDao;

@Mapper
public interface EvEventTargetProductDao extends BaseEvEventTargetProductDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseEvEventTargetProductDao 클래스에 구현 되어있습니다.
	 * BaseEvEventTargetProductDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면
	 * 됩니다.
	 * 
	 */

	public EvEventTargetProduct selectByPrimaryKey(EvEventTargetProduct evEventTargetProduct) throws Exception;

	/**
	 * 이벤트 대상 지정 상품 삭제
	 * 
	 * @Desc : 이벤트 대상 지정 상품 삭제
	 * @Method Name : deleteByEventNo
	 * @Date : 2019. 4. 26
	 * @Author : easyhun
	 * @param eventNo
	 * @return
	 */
	public void deleteByEventNo(String eventNo) throws Exception;

	/**
	 * 진행중인 드로우 상품 중복 체크
	 * 
	 * @Desc : 진행중인 드로우 상품 중복 체크
	 * @Method Name : selectEventDrawDuplCheck
	 * @Date : 2019. 9. 06
	 * @Author : easyhun
	 * @param prdtNo
	 * @return
	 */
	public int selectEventDrawDuplCheck(String prdtNo, String eventNo) throws Exception;

}
