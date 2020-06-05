package kr.co.abcmart.bo.product.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.product.model.master.CmProductIcon;
import kr.co.abcmart.bo.product.repository.master.base.BaseCmProductIconDao;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface CmProductIconDao extends BaseCmProductIconDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseCmProductIconDao 클래스에 구현 되어있습니다.
	 * BaseCmProductIconDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public CmProductIcon selectByPrimaryKey(CmProductIcon cmProductIcon) throws Exception;

	/**
	 * @Desc : 아이콘 카운트 조회
	 * @Method Name : selectIconCount
	 * @Date : 2019. 10. 21.
	 * @Author : 이가영
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public int selectIconCount(Pageable<CmProductIcon, CmProductIcon> pageable) throws Exception;

	/**
	 * @Desc : 아이콘 리스트 조회
	 * @Method Name : selectIconList
	 * @Date : 2019. 10. 21.
	 * @Author : 이가영
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<CmProductIcon> selectIconList(Pageable<CmProductIcon, CmProductIcon> pageable) throws Exception;

	/**
	 * @Desc : 사용 중인 아이콘 리스트 조회
	 * @Method Name : selectUseIconList
	 * @Date : 2019. 10. 21.
	 * @Author : 이가영
	 * @param productIcon
	 * @return
	 * @throws Exception
	 */
	public List<CmProductIcon> selectUseIconList(CmProductIcon productIcon) throws Exception;

	/**
	 * @Desc : 아이콘 등록
	 * @Method Name : insertIcon
	 * @Date : 2019. 10. 21.
	 * @Author : 이가영
	 * @param cmProductIcon
	 * @return
	 * @throws Exception
	 */
	public int insertIcon(CmProductIcon cmProductIcon) throws Exception;

	/**
	 * @Desc : 아이콘 수정
	 * @Method Name : updateIcon
	 * @Date : 2019. 10. 21.
	 * @Author : 이가영
	 * @param cmProductIcon
	 * @return
	 * @throws Exception
	 */
	public int updateIcon(CmProductIcon cmProductIcon) throws Exception;

}
