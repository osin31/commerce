package kr.co.abcmart.bo.product.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.product.model.master.PdGiftCard;
import kr.co.abcmart.bo.product.repository.master.base.BasePdGiftCardDao;
import kr.co.abcmart.bo.product.vo.PdGiftCardSearchVO;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface PdGiftCardDao extends BasePdGiftCardDao {

	/**
	 * 기본 insert, update, delete 메소드는 BasePdGiftCardDao 클래스에 구현 되어있습니다.
	 * BasePdGiftCardDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public PdGiftCard selectByPrimaryKey(PdGiftCard pdGiftCard) throws Exception;

	/**
	 * @Desc : 기프트목록 갯수
	 * @Method Name : selectGiftCardCount
	 * @Date : 2019. 4. 12.
	 * @Author : hsjhsj19
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Integer selectGiftCardCount(Pageable<PdGiftCardSearchVO, PdGiftCard> pageable) throws Exception;

	/**
	 * @Desc : 기프트목록 조회
	 * @Method Name : selectGiftCard
	 * @Date : 2019. 4. 12.
	 * @Author : hsjhsj19
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<PdGiftCard> selectGiftCard(Pageable<PdGiftCardSearchVO, PdGiftCard> pageable) throws Exception;

	/**
	 * @Desc : 채번과 함께 등록
	 * @Method Name : insertWithSelectKey
	 * @Date : 2019. 4. 12.
	 * @Author : hsjhsj19
	 * @param giftCard
	 * @return
	 * @throws Exception
	 */
	public int insertWithSelectKey(PdGiftCard giftCard) throws Exception;

}
