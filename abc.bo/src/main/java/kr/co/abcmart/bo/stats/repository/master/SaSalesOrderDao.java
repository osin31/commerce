package kr.co.abcmart.bo.stats.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.display.model.master.DpCategory;
import kr.co.abcmart.bo.stats.model.master.SaSalesOrder;
import kr.co.abcmart.bo.stats.repository.master.base.BaseSaSalesOrderDao;
import kr.co.abcmart.bo.stats.vo.SalesStatsSearchVO;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface SaSalesOrderDao extends BaseSaSalesOrderDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseSaSalesOrderDao 클래스에 구현 되어있습니다.
	 * BaseSaSalesOrderDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public SaSalesOrder selectByPrimaryKey(SaSalesOrder saSalesOrder) throws Exception;

	/**
	 * 
	 * @Desc : 발송처별 매출현황 count
	 * @Method Name : sendTypeCount
	 * @Date : 2019. 7. 6.
	 * @Author : NKB
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public int sendTypeCount(Pageable<SalesStatsSearchVO, SaSalesOrder> pageable) throws Exception;

	/**
	 * 
	 * @Desc : 발송처별 매출현황
	 * @Method Name : selectSendTypeStats
	 * @Date : 2019. 7. 6.
	 * @Author : NKB
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<SaSalesOrder> selectSendTypeStats(Pageable<SalesStatsSearchVO, SaSalesOrder> pageable) throws Exception;

	/**
	 * 
	 * @Desc : 발송처별 매출현황 엑셀
	 * @Method Name : selectSendTypeStatsForExcel
	 * @Date : 2019. 7. 6.
	 * @Author : NKB
	 * @param saSalesOrder
	 * @return
	 * @throws Exception
	 */
	public List<SaSalesOrder> selectSendTypeStatsForExcel(SalesStatsSearchVO salesStatsSearchVO) throws Exception;

	/**
	 * 
	 * @Desc :디바이스별 매출현황 count
	 * @Method Name : selectDeviceTypeCount
	 * @Date : 2019. 7. 6.
	 * @Author : NKB
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public int selectDeviceTypeCount(Pageable<SalesStatsSearchVO, SaSalesOrder> pageable) throws Exception;

	/**
	 * 
	 * @Desc : 디바이스별 매출현황
	 * @Method Name : selectSendTypeStats
	 * @Date : 2019. 7. 6.
	 * @Author : NKB
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<SaSalesOrder> selectDeviceTypeStats(Pageable<SalesStatsSearchVO, SaSalesOrder> pageable) throws Exception;

	/**
	 * 
	 * @Desc : 디바이스별 매출현황 엑셀
	 * @Method Name : selectSendTypeStatsForExcel
	 * @Date : 2019. 7. 6.
	 * @Author : NKB
	 * @param saSalesOrder
	 * @return
	 * @throws Exception
	 */
	public List<SaSalesOrder> selectDeviceTypeStatsForExcel(SalesStatsSearchVO salesStatsSearchVO) throws Exception;

	/**
	 * 
	 * @Desc : 결제수단별 매출 현황 Count
	 * @Method Name : selectPaymentTypeCount
	 * @Date : 2019. 7. 8.
	 * @Author : NKB
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public int selectPaymentTypeCount(Pageable<SalesStatsSearchVO, SaSalesOrder> pageable) throws Exception;

	/**
	 * @Desc : 결제수단별 매출 현황
	 * @Method Name : selectPaymentTypeStats
	 * @Date : 2019. 7. 8.
	 * @Author : NKB
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<SaSalesOrder> selectPaymentTypeStats(Pageable<SalesStatsSearchVO, SaSalesOrder> pageable) throws Exception;

	/**
	 * @Desc : 결제수단별 매출 현황 엑셀다운로드
	 * @Method Name : selectPaymentTypeStatsForExcel
	 * @Date : 2019. 7. 8.
	 * @Author : NKB
	 * @param saSalesOrder
	 * @return
	 * @throws Exception
	 */
	public List<SaSalesOrder> selectPaymentTypeStatsForExcel(SalesStatsSearchVO salesStatsSearchVO) throws Exception;
	
	/**
		 * @Desc : 배송수단별 매출 현황 count
		 * @Method Name : selectDlvyTypeCount
		 * @Date : 2019. 7. 10.
		 * @Author : 이재렬
		 * @param pageable
		 * @throws Exception
		 */
	public int selectDlvyTypeCount(Pageable<SalesStatsSearchVO, SaSalesOrder> pageable) throws Exception;
	
	/**
		 * @Desc : 배송수단별 매출 현황 조회
		 * @Method Name : selectDlvyTypeList
		 * @Date : 2019. 7. 10.
		 * @Author : 이재렬
		 * @param pageable
		 * @throws Exception
		 */
	public List<SaSalesOrder> selectDlvyTypeList(Pageable<SalesStatsSearchVO, SaSalesOrder> pageable) throws Exception;
	
	/**
		 * @Desc : 배송수단별 매출 현황 엑셀 다운로드
		 * @Method Name : selectDlvyTypeStatsForExcel
		 * @Date : 2019. 7. 10.
		 * @Author : 이재렬
		 * @param saSalesOrder
		 * @throws Exception
		 */
	public List<SaSalesOrder> selectDlvyTypeStatsForExcel(SalesStatsSearchVO salesStatsSearchVO) throws Exception;
	
	/**
		 * @Desc : 시간별 매출 현황 count
		 * @Method Name : selectTimeTypeCount
		 * @Date : 2019. 7. 10.
		 * @Author : 이재렬
		 * @param pageable
		 * @throws Exception
		 */
	public int selectTimeTypeCount(Pageable<SalesStatsSearchVO, SaSalesOrder> pageable) throws Exception;
	
	/**
		 * @Desc : 시간별 매출 현황 조회.
		 * @Method Name : selectTimeTypeList
		 * @Date : 2019. 7. 10.
		 * @Author : 이재렬
		 * @param pageable
		 * @throws Exception
		 */
	public List<SaSalesOrder> selectTimeTypeList(Pageable<SalesStatsSearchVO, SaSalesOrder> pageable) throws Exception;
	
	/**
		 * @Desc : 시간별 매출 현황 엑셀다운로드
		 * @Method Name : selectTimeTypeStatsForExcel
		 * @Date : 2019. 7. 10.
		 * @Author : 이재렬
		 * @param saSalesOrder
		 * @throws Exception
		 */
	public List<SaSalesOrder> selectTimeTypeStatsForExcel(SalesStatsSearchVO salesStatsSearchVO) throws Exception;
	/**
		 * @Desc : 연령별 매출 현황 count
		 * @Method Name : selectAgeTypeCount
		 * @Date : 2019. 7. 10.
		 * @Author : 이재렬
		 * @param pageable
		 * @throws Exception
		 */
	public int selectAgeTypeCount(Pageable<SalesStatsSearchVO, SaSalesOrder> pageable) throws Exception;
	/**
		 * @Desc : 연령별 매출 현황 조회.
		 * @Method Name : selectAgeTypeList
		 * @Date : 2019. 7. 10.
		 * @Author : 이재렬
		 * @param pageable
		 * @throws Exception
		 */
	public List<SaSalesOrder> selectAgeTypeList(Pageable<SalesStatsSearchVO, SaSalesOrder> pageable) throws Exception;
	/**
		 * @Desc : 연령별 매출 현황 엑셀다운로드
		 * @Method Name : selectTimeTypeStatsForExcel
		 * @Date : 2019. 7. 10.
		 * @Author : 이재렬
		 * @param saSalesOrder
		 * @throws Exception
		 */
	public List<SaSalesOrder> selectAgeTypeStatsForExcel(SalesStatsSearchVO salesStatsSearchVO) throws Exception;
	/**
		 * @Desc : 성별 매출 현황 count
		 * @Method Name : selectGenderTypeCount
		 * @Date : 2019. 7. 11.
		 * @Author : 이재렬
		 * @param pageable
		 * @throws Exception
		 */
	public int selectGenderTypeCount(Pageable<SalesStatsSearchVO, SaSalesOrder> pageable) throws Exception;
	/**
		 * @Desc : 성별 매출 현황 조회.
		 * @Method Name : selectGenderTypeList
		 * @Date : 2019. 7. 11.
		 * @Author : 이재렬
		 * @param pageable
		 * @throws Exception
		 */
	public List<SaSalesOrder> selectGenderTypeList(Pageable<SalesStatsSearchVO, SaSalesOrder> pageable) throws Exception;
	/**
		 * @Desc : 성별 매출 현황 엑셀다운로드
		 * @Method Name : selectGenderTypeStatsForExcel
		 * @Date : 2019. 7. 11.
		 * @Author : 이재렬
		 * @param saSalesOrder
		 * @throws Exception
		 */
	public List<SaSalesOrder> selectGenderTypeStatsForExcel(SalesStatsSearchVO salesStatsSearchVO) throws Exception;
	/**
		 * @Desc : 상품별 매출 현황 카운트
		 * @Method Name : selectPrdtTypeCount
		 * @Date : 2019. 7. 15.
		 * @Author : 이재렬
		 * @param parameter
		 * @throws Exception
		 */
	public int selectPrdtTypeCount(Pageable<SalesStatsSearchVO, SaSalesOrder> pageable) throws Exception;
	/**
		 * @Desc : 상품별 매출 현황 조회.
		 * @Method Name : selectPrdtTypeList
		 * @Date : 2019. 7. 15.
		 * @Author : 이재렬
		 * @param parameter
		 * @throws Exception
		 */
	public List<SaSalesOrder> selectPrdtTypeList(Pageable<SalesStatsSearchVO, SaSalesOrder> pageable) throws Exception;
	/**
		 * @Desc : 상품별 매출 현황 엑셀 다운로드
		 * @Method Name : selectPrdtTypeStatsForExcel
		 * @Date : 2019. 7. 15.
		 * @Author : 이재렬
		 * @param parameter
		 * @throws Exception
		 */
	public List<SaSalesOrder> selectPrdtTypeStatsForExcel(SalesStatsSearchVO salesStatsSearchVO) throws Exception;
	/**
		 * @Desc : 입점사 매출 현황 카운트
		 * @Method Name : selectVendorTypeCount
		 * @Date : 2019. 7. 16.
		 * @Author : 이재렬
		 * @param pageable
		 * @throws Exception
		 */
	public int selectVendorTypeCount(Pageable<SalesStatsSearchVO, SaSalesOrder> pageable) throws Exception;
	/**
		 * @Desc : 입점사 매출 현황 조회.
		 * @Method Name : selectVendorTypeList
		 * @Date : 2019. 7. 16.
		 * @Author : 이재렬
		 * @param pageable
		 * @throws Exception
		 */
	public List<SaSalesOrder> selectVendorTypeList(Pageable<SalesStatsSearchVO, SaSalesOrder> pageable) throws Exception;
	/**
		 * @Desc : 입점사 매출 현황 엑셀 다운로드
		 * @Method Name : selectVendorTypeStatsForExcel
		 * @Date : 2019. 7. 16.
		 * @Author : 이재렬
		 * @param salesStatsSearchVO
		 * @throws Exception
		 */
	public List<SaSalesOrder> selectVendorTypeStatsForExcel(SalesStatsSearchVO salesStatsSearchVO) throws Exception;
	/**
		 * @Desc : 입점사 상품 매출 현황 팝업 카운트
		 * @Method Name : selectVendorTypeCount
		 * @Date : 2019. 7. 16.
		 * @Author : 이재렬
		 * @param pageable
		 * @throws Exception
		 */
	public int selectVendorPopCount(Pageable<SalesStatsSearchVO, SaSalesOrder> pageable) throws Exception;
	/**
		 * @Desc : 입점사 상품 매출 현황 팝업 조회.
		 * @Method Name : selectVendorTypeList
		 * @Date : 2019. 7. 16.
		 * @Author : 이재렬
		 * @param pageable
		 * @throws Exception
		 */
	public List<SaSalesOrder> selectVendorPopList(Pageable<SalesStatsSearchVO, SaSalesOrder> pageable) throws Exception;
	/**
		 * @Desc : 입점사 상품 매출 현황 팝업 엑셀 다운로드
		 * @Method Name : selectVendorTypeStatsForExcel
		 * @Date : 2019. 7. 16.
		 * @Author : 이재렬
		 * @param salesStatsSearchVO
		 * @throws Exception
		 */
	public List<SaSalesOrder> selectVendorPopForExcel(SalesStatsSearchVO salesStatsSearchVO) throws Exception;
	
	/**
		 * @Desc : 대분류 카테고리 조회
		 * @Method Name : selectBigCategory
		 * @Date : 2019. 7. 15.
		 * @Author : 이재렬
		 * @param 
		 * @throws Exception
		 */
	public List<DpCategory> selectBigCategory() throws Exception;
	
	/**
		 * @Desc : 중분류 카테고리 조회
		 * @Method Name : selectMidCategory
		 * @Date : 2019. 7. 15.
		 * @Author : 이재렬
		 * @param ctgrNo
		 * @throws Exception
		 */
	public List<DpCategory> selectMidCategory(String ctgrNo) throws Exception;
	
	/**
		 * @Desc : 소분류 카테고리 조회
		 * @Method Name : selectSmallCategory
		 * @Date : 2019. 7. 15.
		 * @Author : 이재렬
		 * @param ctgrNo
		 * @throws Exception
		 */
	public List<DpCategory> selectSmallCategory(String ctgrNo) throws Exception;

}
