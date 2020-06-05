package kr.co.abcmart.bo.settlement.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.settlement.vo.RvGiftCardComparision;
import kr.co.abcmart.bo.settlement.vo.RvKakaoComparision;
import kr.co.abcmart.bo.settlement.vo.RvKcpComparision;
import kr.co.abcmart.bo.settlement.vo.RvNaverComparision;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface PgDao {

	List<RvKakaoComparision> selectKakaoList(Pageable<RvKakaoComparision, RvKakaoComparision> pageable)
			throws Exception;

	int selectKakaoCount(Pageable<RvKakaoComparision, RvKakaoComparision> pageable) throws Exception;

	List<RvKakaoComparision> selectKakaoExcelList(RvKakaoComparision rvKakaoComparision) throws Exception;

	RvKakaoComparision selectKakaoSettlementHistory(RvKakaoComparision rvKakaoComparision) throws Exception;

	List<RvNaverComparision> selectNaverList(Pageable<RvNaverComparision, RvNaverComparision> pageable)
			throws Exception;

	int selectNaverCount(Pageable<RvNaverComparision, RvNaverComparision> pageable) throws Exception;

	List<RvNaverComparision> selectNaverExcelList(RvNaverComparision rvNaverComparision) throws Exception;

	RvNaverComparision selectNaverSettlementHistory(RvNaverComparision rvNaverComparision) throws Exception;

	List<RvGiftCardComparision> selectGiftCardList(Pageable<RvGiftCardComparision, RvGiftCardComparision> pageable)
			throws Exception;

	int selectGiftCardCount(Pageable<RvGiftCardComparision, RvGiftCardComparision> pageable) throws Exception;

	List<RvGiftCardComparision> selectGiftCardExcelList(RvGiftCardComparision rvGiftCardComparision) throws Exception;

	RvGiftCardComparision selectGiftCardSettlementHistory(RvGiftCardComparision rvGiftCardComparision) throws Exception;

	List<RvKcpComparision> selectKcpList(Pageable<RvKcpComparision, RvKcpComparision> pageable) throws Exception;

	int selectKcpCount(Pageable<RvKcpComparision, RvKcpComparision> pageable) throws Exception;

	List<RvKcpComparision> selectKcpExcelList(RvKcpComparision rvKcpComparision) throws Exception;

	RvKcpComparision selectKcpSettlementHistory(RvKcpComparision rvKcpComparision) throws Exception;
}
