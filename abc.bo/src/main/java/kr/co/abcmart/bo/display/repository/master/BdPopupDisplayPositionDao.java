package kr.co.abcmart.bo.display.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.display.model.master.BdPopupDisplayPosition;
import kr.co.abcmart.bo.display.repository.master.base.BaseBdPopupDisplayPositionDao;

@Mapper
public interface BdPopupDisplayPositionDao extends BaseBdPopupDisplayPositionDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseBdPopupDisplayPositionDao 클래스에 구현 되어있습니다.
	 * BaseBdPopupDisplayPositionDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면
	 * 됩니다.
	 * 
	 */

	public BdPopupDisplayPosition selectByPrimaryKey(BdPopupDisplayPosition bdPopupDisplayPosition) throws Exception;

	/**
	 * 팝업 전시위치 삭제
	 * 
	 * @Desc : 팝업 전시위치 삭제
	 * @Method Name : deleteByPopupSeq
	 * @Date : 2019. 2. 20
	 * @Author : easyhun
	 * @param popupSeq
	 * @return
	 */
	public void deleteByPopupSeq(Integer popupSeq) throws Exception;

	/**
	 * 팝업 전시위치 리스트 조회
	 * 
	 * @Desc : 팝업 전시위치 리스트 조회
	 * @Method Name : selectBdPopupDisplayPositionListByPopupSeq
	 * @Date : 2019. 2. 20
	 * @Author : easyhun
	 * @param popupSeq
	 * @return
	 */
	public List<BdPopupDisplayPosition> selectBdPopupDisplayPositionListByPopupSeq(Integer popupSeq) throws Exception;

}
