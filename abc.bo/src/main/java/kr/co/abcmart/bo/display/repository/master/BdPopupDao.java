package kr.co.abcmart.bo.display.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.display.model.master.BdPopup;
import kr.co.abcmart.bo.display.repository.master.base.BaseBdPopupDao;
import kr.co.abcmart.bo.display.vo.BdPopupSearchVO;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface BdPopupDao extends BaseBdPopupDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseBdPopupDao 클래스에 구현 되어있습니다. BaseBdPopupDao는
	 * 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public BdPopup selectByPrimaryKey(BdPopup bdPopup) throws Exception;

	/**
	 * 팝업 리스트 조회
	 * 
	 * @Desc : 팝업 리스트 조회
	 * @Method Name : selectBdPopupList
	 * @Date : 2019. 2. 14
	 * @Author : easyhun
	 * @param pageable
	 * @return
	 */
	public List<BdPopup> selectBdPopupList(Pageable<BdPopupSearchVO, BdPopup> pageable) throws Exception;

	/**
	 * 팝업 리스트 카운트 조회
	 * 
	 * @Desc :
	 * @Method Name : selectBdPopupCount
	 * @Date : 2019. 2. 14
	 * @Author : easyhun
	 * @param pageable
	 * @return
	 */
	public int selectBdPopupCount(Pageable<BdPopupSearchVO, BdPopup> pageable) throws Exception;

	/**
	 * 팝업 상세
	 * 
	 * @Desc : 팝업 저장
	 * @Method Name : selectPopup
	 * @Date : 2019. 2. 18
	 * @Author : easyhun
	 * @param bdPopup
	 * @return
	 * @throws Exception
	 */
	public BdPopup selectBdPopup(BdPopup bdPopup) throws Exception;

	/**
	 * 팝업 저장
	 * 
	 * @Desc : 팝업 저장
	 * @Method Name : insertBdPopup
	 * @Date : 2019. 2. 14
	 * @Author : easyhun
	 * @param bdPopup
	 * @return
	 * @throws Exception
	 */
	public int insertBdPopup(BdPopup bdPopup) throws Exception;

	/**
	 * 팝업 수정
	 * 
	 * @Desc : 팝업 수정
	 * @Method Name : updateBdPopup
	 * @Date : 2019. 2. 14
	 * @Author : easyhun
	 * @param bdPopup
	 * @return
	 * @throws Exception
	 */
	public int updateBdPopup(BdPopup bdPopup) throws Exception;
}
