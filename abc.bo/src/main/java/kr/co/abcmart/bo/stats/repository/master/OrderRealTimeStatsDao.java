package kr.co.abcmart.bo.stats.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.stats.model.master.CurrentSaleStats;
import kr.co.abcmart.bo.stats.vo.OrderStatsSearchVO;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface OrderRealTimeStatsDao {

	public java.sql.Timestamp selectCurrentDateTime() throws Exception;

	public List<CurrentSaleStats> selectCurrentSaleStatsList(Pageable<OrderStatsSearchVO, CurrentSaleStats> pageable)
			throws Exception;

	public List<CurrentSaleStats> selectCurrentSaleDetailList(Pageable<OrderStatsSearchVO, CurrentSaleStats> pageable)
			throws Exception;

	public List<CurrentSaleStats> selectCurrentDeviceList(Pageable<OrderStatsSearchVO, CurrentSaleStats> pageable)
			throws Exception;

	public List<CurrentSaleStats> selectCurrentPaymentList(Pageable<OrderStatsSearchVO, CurrentSaleStats> pageable)
			throws Exception;

	public List<CurrentSaleStats> selectCurrentPrdtList(Pageable<OrderStatsSearchVO, CurrentSaleStats> pageable)
			throws Exception;

	public List<CurrentSaleStats> selectClaimCntGroupbyVndr(Pageable<OrderStatsSearchVO, CurrentSaleStats> pageable)
			throws Exception;

}
