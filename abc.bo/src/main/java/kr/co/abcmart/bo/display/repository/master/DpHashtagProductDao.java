package kr.co.abcmart.bo.display.repository.master;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.display.model.master.DpHashtag;
import kr.co.abcmart.bo.display.model.master.DpHashtagProduct;
import kr.co.abcmart.bo.display.repository.master.base.BaseDpHashtagProductDao;

@Mapper
public interface DpHashtagProductDao extends BaseDpHashtagProductDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseDpHashtagProductDao 클래스에 구현 되어있습니다.
	 * BaseDpHashtagProductDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 *
	 */

	public DpHashtagProduct selectByPrimaryKey(DpHashtagProduct dpHashtagProduct) throws Exception;

	/**
	 * @Desc : 해쉬태그 멀티 상품 등록
	 * @Method Name : insertDpHashtagProductList
	 * @Date : 2019. 6. 27.
	 * @Author : kiowa
	 * @param dpHashtagProductList
	 * @return
	 * @throws Exception
	 */
	public int insertDpHashtagProductList(List<DpHashtagProduct> dpHashtagProductList) throws Exception;

	/**
	 * @Desc : 해쉬태그 상품 삭제
	 * @Method Name : deleteDpHashtagProductList
	 * @Date : 2019. 6. 27.
	 * @Author : kiowa
	 * @param hshtgSeq
	 * @return
	 * @throws Exception
	 */
	public int deleteDpHashtagProductList(Integer hshtgSeq) throws Exception;

	public List<Map<String, Object>> selectExcelUploadPorudctList(DpHashtag dpHashTag) throws Exception;

}
