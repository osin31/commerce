/**
 *
 */
package kr.co.abcmart.bo.stats.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.stats.model.master.SaMemberJoinStatus;
import kr.co.abcmart.bo.stats.vo.MemberInterestBrandVO;
import kr.co.abcmart.bo.stats.vo.MemberInterestStoreVO;
import kr.co.abcmart.bo.stats.vo.MemberPointStatusVO;
import kr.co.abcmart.bo.stats.vo.MemberStatsVO;
import kr.co.abcmart.common.paging.Pageable;

/**
 * @Desc :
 * @FileName : MemberStatsDao.java
 * @Project : abc.bo
 * @Date : 2019. 6. 17.
 * @Author : Kimyounghyun
 */
@Mapper
public interface MemberStatsDao {

	/**
	 * @Desc : 당일회원 가입 현황 데이터 조회
	 * @Method Name : selectTodayJoinList
	 * @Date : 2019. 6. 18.
	 * @Author : 최경호
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public List<MemberStatsVO> selectTodayJoinList(MemberStatsVO memberStatsVO) throws Exception;

	/**
	 * @Desc : 회원가입 현황 화면 리스트 조회
	 * @Method Name : selectMemberJoinList
	 * @Date : 2019. 6. 13.
	 * @Author : 최경호
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public List<SaMemberJoinStatus> selectMemberJoinList(Pageable<SaMemberJoinStatus, SaMemberJoinStatus> pageable)
			throws Exception;

	/**
	 * @Desc : 회원가입 현황 화면 리스트 조회
	 * @Method Name : selectMemberAgeJoinList
	 * @Date : 2019. 6. 25.
	 * @Author : 최경호
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public List<SaMemberJoinStatus> selectMemberAgeJoinList(Pageable<SaMemberJoinStatus, SaMemberJoinStatus> pageable)
			throws Exception;

	/**
	 * @Desc : 회원가입 현황 화면 리스트 개수 조회
	 * @Method Name : selectMemberJoinListCount
	 * @Date : 2019. 6. 13.
	 * @Author : 최경호
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public int selectMemberJoinListCount(Pageable<SaMemberJoinStatus, SaMemberJoinStatus> pageable) throws Exception;

	/**
	 * @Desc : 회원가입 현황 연령별 화면 리스트 개수 조회
	 * @Method Name : selectMemberJoinListCount
	 * @Date : 2019. 6. 13.
	 * @Author : 최경호
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public int selectMemberAgeJoinListCount(Pageable<SaMemberJoinStatus, SaMemberJoinStatus> pageable) throws Exception;

	/**
	 * @Desc : 당일 로그인 리스트 조회
	 * @Method Name : selectTodayLoginList
	 * @Date : 2019. 6. 24.
	 * @Author : 최경호
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public List<MemberStatsVO> selectTodayLoginList(MemberStatsVO memberStatsVO) throws Exception;

	/**
	 * @Desc : 전일 기준 회원 현화
	 * @Method Name : selectYesterdayMemberInfo
	 * @Date : 2019. 6. 24.
	 * @Author : 최경호
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public SaMemberJoinStatus selectYesterdayMemberInfo() throws Exception;

	/**
	 * @Desc : 단골 매장 현황 카운트
	 * @Method Name : selectStoreTotalCount
	 * @Date : 2019. 6. 25.
	 * @Author : 신인철
	 * @return
	 * @throws Exception
	 */
	public int selectStoreTotalCount() throws Exception;

	/**
	 * @Desc : 단골 매장 현황 조회
	 * @Method Name : selectInterestStoreList
	 * @Date : 2019. 6. 25.
	 * @Author : 신인철
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<MemberInterestStoreVO> selectInterestStoreList() throws Exception;

	/**
	 * @Desc : 단골 브랜드 현황 카운트
	 * @Method Name : selectBrandTotalCount
	 * @Date : 2019. 6. 25.
	 * @Author : 신인철
	 * @return
	 * @throws Exception
	 */
	public int selectBrandTotalCount() throws Exception;

	/**
	 * @Desc : 단골 브랜드 현황 조회
	 * @Method Name : selectInterestBrandList
	 * @Date : 2019. 6. 25.
	 * @Author : 신인철
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<MemberInterestBrandVO> selectInterestBrandList() throws Exception;

	/**
	 * @Desc : 포인트 현황 카운트
	 * @Method Name : selectPointGridCount
	 * @Date : 2019. 7. 16.
	 * @Author : 신인철
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public int selectPointGridCount(Pageable<MemberPointStatusVO, MemberPointStatusVO> pageable) throws Exception;

	/**
	 * @Desc : 포인트 현황 그리드 조회
	 * @Method Name : selectPointGrid
	 * @Date : 2019. 7. 16.
	 * @Author : 신인철
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<MemberPointStatusVO> selectPointGrid(Pageable<MemberPointStatusVO, MemberPointStatusVO> pageable)
			throws Exception;

	/**
	 * @Desc : 포인트현황 엑셀 다운로드
	 * @Method Name : selectPointExcelList
	 * @Date : 2019. 7. 16.
	 * @Author : 신인철
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<MemberPointStatusVO> selectPointExcelList(MemberPointStatusVO param) throws Exception;

}
