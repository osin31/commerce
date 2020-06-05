package kr.co.abcmart.bo.product.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.product.model.master.SyStandardCategory;
import kr.co.abcmart.bo.product.repository.master.base.BaseSyStandardCategoryDao;

@Mapper
public interface SyStandardCategoryDao extends BaseSyStandardCategoryDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseSyStandardCategoryDao 클래스에 구현 되어있습니다.
	 * BaseSyStandardCategoryDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public SyStandardCategory selectByPrimaryKey(SyStandardCategory syStandardCategory) throws Exception;

	/**
	 * @Desc : 표준 카테고리 리스트 조회
	 * @Method Name : selectStandardCategoryList
	 * @Date : 2019. 10. 21.
	 * @Author : 이가영
	 * @param syStandardCategory
	 * @return
	 * @throws Exception
	 */
	public List<SyStandardCategory> selectStandardCategoryList(SyStandardCategory syStandardCategory) throws Exception;

	/**
	 * @Desc : 표준 카테고리 조회
	 * @Method Name : selectStandardCategory
	 * @Date : 2019. 10. 21.
	 * @Author : 이가영
	 * @param stdCtgrNo
	 * @return
	 * @throws Exception
	 */
	public SyStandardCategory selectStandardCategory(String stdCtgrNo) throws Exception;

	/**
	 * @Desc : 표준 카테고리 등록
	 * @Method Name : insertStandardCategory
	 * @Date : 2019. 10. 21.
	 * @Author : 이가영
	 * @param syStandardCategory
	 * @return
	 * @throws Exception
	 */
	public int insertStandardCategory(SyStandardCategory syStandardCategory) throws Exception;

	/**
	 * @Desc : 표준 카테고리 수정
	 * @Method Name : updateStandardCategory
	 * @Date : 2019. 10. 21.
	 * @Author : 이가영
	 * @param syStandardCategory
	 * @return
	 * @throws Exception
	 */
	public int updateStandardCategory(SyStandardCategory syStandardCategory) throws Exception;

	/**
	 * @Desc : 표준 카테고리 이름 조회
	 * @Method Name : selectStandardCategoryName
	 * @Date : 2019. 10. 21.
	 * @Author : 변지은
	 * @param stdCtgrNo
	 * @return
	 * @throws Exception
	 */
	public String selectStandardCategoryName(String stdCtgrNo) throws Exception;

	/**
	 * @Desc : 상품번호에 대한 최상위 표준 카테고리 정보 조회
	 * @Method Name : selectRootByStdCtgrNo
	 * @Date : 2019. 7. 15.
	 * @Author : tennessee
	 * @param stdCtgrNo
	 * @return
	 * @throws Exception
	 */
	public SyStandardCategory selectRootByStdCtgrNo(String stdCtgrNo) throws Exception;

}
