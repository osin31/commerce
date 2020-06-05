package kr.co.abcmart.bo.promotion.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.promotion.model.master.EvCardBenefit;
import kr.co.abcmart.bo.promotion.repository.master.base.BaseEvCardBenefitDao;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface EvCardBenefitDao extends BaseEvCardBenefitDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseEvCardBenefitDao 클래스에 구현 되어있습니다.
	 * BaseEvCardBenefitDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public EvCardBenefit selectByPrimaryKey(EvCardBenefit evCardBenefit) throws Exception;

	public int selectCardBenefitCount(Pageable<EvCardBenefit, EvCardBenefit> pageable) throws Exception;

	public List<EvCardBenefit> selectCardBenefitList(Pageable<EvCardBenefit, EvCardBenefit> pageable) throws Exception;

	public EvCardBenefit selectCardBenefitDetail(EvCardBenefit parameter) throws Exception;

	public int updateCardBenefitDetail(EvCardBenefit parameter) throws Exception;

	public List<EvCardBenefit> selectCardBenefitCountByDate(EvCardBenefit parameter) throws Exception;

	public int updateCardBenefit(EvCardBenefit parameter) throws Exception;

}
