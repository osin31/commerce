/**
 *
 */
package kr.co.abcmart.bo.stats.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.stats.model.master.SaMemberJoinStatus;
import kr.co.abcmart.bo.stats.model.master.SaMemberLoginStatus;
import kr.co.abcmart.bo.stats.model.master.SaMemberSnsConnectionStatus;
import kr.co.abcmart.bo.stats.repository.master.MemberStatsDao;
import kr.co.abcmart.bo.stats.repository.master.SaMemberLoginStatusDao;
import kr.co.abcmart.bo.stats.repository.master.SaMemberSnsConnectionStatusDao;
import kr.co.abcmart.bo.stats.vo.MemberInterestBrandVO;
import kr.co.abcmart.bo.stats.vo.MemberInterestStoreVO;
import kr.co.abcmart.bo.stats.vo.MemberPointStatusVO;
import kr.co.abcmart.bo.stats.vo.MemberStatsVO;
import kr.co.abcmart.bo.system.service.SiteService;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc :
 * @FileName : MemberStatsService.java
 * @Project : abc.bo
 * @Date : 2019. 6. 17.
 * @Author : Kimyounghyun
 */
@Slf4j
@Service
public class MemberStatsService {
	@Autowired
	private MemberStatsDao memberStatsDao;

	@Autowired
	private SaMemberLoginStatusDao saMemberLoginStatusDao;

	@Autowired
	private SaMemberSnsConnectionStatusDao saMemberSnsConnectionStatusDao;

	@Autowired
	private SiteService siteService;

	/**
	 * @Desc : 당일회원 가입 현황 데이터 조회
	 * @Method Name : todayJoinList
	 * @Date : 2019. 6. 18.
	 * @Author : 최경호
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public List<MemberStatsVO> getTodayJoinList(MemberStatsVO memberStatsVO) throws Exception {
		return memberStatsDao.selectTodayJoinList(memberStatsVO);
	}

	/**
	 * @Desc : 회원가입 현황 화면 리스트 조회
	 * @Method Name : todayJoinList
	 * @Date : 2019. 6. 13.
	 * @Author : 최경호
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public Page<SaMemberJoinStatus> getMemberJoinList(Pageable<SaMemberJoinStatus, SaMemberJoinStatus> pageable)
			throws Exception {
		int totalCount = memberStatsDao.selectMemberJoinListCount(pageable);
		;

		pageable.setTotalCount(totalCount);

		if (totalCount > 0) {
			List<SaMemberJoinStatus> list = memberStatsDao.selectMemberJoinList(pageable);
			pageable.setContent(list);
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 회원가입 연령별 현황 화면 리스트 조회
	 * @Method Name : getMemberAgeJoinList
	 * @Date : 2019. 6. 13.
	 * @Author : 최경호
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public Page<SaMemberJoinStatus> getMemberAgeJoinList(Pageable<SaMemberJoinStatus, SaMemberJoinStatus> pageable)
			throws Exception {
		int totalCount = memberStatsDao.selectMemberAgeJoinListCount(pageable);
		;

		pageable.setTotalCount(totalCount);

		if (totalCount > 0) {
			List<SaMemberJoinStatus> list = memberStatsDao.selectMemberAgeJoinList(pageable);
			pageable.setContent(list);
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 당일 로그인 리스트 조회
	 * @Method Name : getTodayLoginList
	 * @Date : 2019. 6. 24.
	 * @Author : 최경호
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public List<MemberStatsVO> getTodayLoginList(MemberStatsVO memberStatsVO) throws Exception {
		return memberStatsDao.selectTodayLoginList(memberStatsVO);
	}

	/**
	 * @Desc : 전일 기준 회원 카운트 데이터 조회
	 * @Method Name : getYesterdayMemberInfo
	 * @Date : 2019. 6. 26.
	 * @Author : 최경호
	 * @param String
	 * @return
	 * @throws Exception
	 */
	public SaMemberJoinStatus getYesterdayMemberInfo() throws Exception {
		return memberStatsDao.selectYesterdayMemberInfo();
	}

	/**
	 * @Desc : 로그인 현황 데이터 조회
	 * @Method Name : getYesterdayMemberInfo
	 * @Date : 2019. 6. 28.
	 * @Author : 최경호
	 * @param saMemberLoginStatus
	 * @return
	 * @throws Exception
	 */
	public List<SaMemberLoginStatus> getLoginList(SaMemberLoginStatus saMemberLoginStatus) throws Exception {
		return saMemberLoginStatusDao.selectLoginList(saMemberLoginStatus);
	}

	/**
	 * @Desc : SNS 계정 연결 현황 데이터 조회
	 * @Method Name : getSnsConnectList
	 * @Date : 2019. 7. 1.
	 * @Author : 최경호
	 * @param getSnsConnectList
	 * @return
	 * @throws Exception
	 */
	public Page<SaMemberSnsConnectionStatus> getSnsConnectList(
			Pageable<SaMemberSnsConnectionStatus, SaMemberSnsConnectionStatus> pageable) throws Exception {
		int totalCount = saMemberSnsConnectionStatusDao.selectSnsConnectListCount(pageable);

		pageable.setTotalCount(totalCount);

		if (totalCount > 0) {
			List<SaMemberSnsConnectionStatus> list = saMemberSnsConnectionStatusDao.selectSnsConnectList(pageable);
			;
			pageable.setContent(list);
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 전일기준 SNS 계정 연결 현황 데이터
	 * @Method Name : getYesterdaySnsTotal
	 * @Date : 2019. 7. 2.
	 * @Author : 최경호
	 * @param getYesterdaySnsTotal
	 * @return
	 * @throws Exception
	 */
	public SaMemberSnsConnectionStatus getYesterdaySnsTotal() throws Exception {

		return saMemberSnsConnectionStatusDao.selectYesterdaySnsTotal();
	}

	/*********************************************************************
	 * S : 신인철
	 *******************************************************************/
	/**
	 * @Desc : 단골 매장 현황 조회
	 * @Method Name : getInterestStore
	 * @Date : 2019. 6. 27.
	 * @Author : 신인철
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getInterestStore() throws Exception {
		Map<String, Object> rsMap = new HashMap<String, Object>();
		int totalCount = memberStatsDao.selectStoreTotalCount();

		List<MemberInterestStoreVO> storeList = memberStatsDao.selectInterestStoreList();
		storeList.get(0).setNoSeq("");

		rsMap.put("Total", totalCount);
		rsMap.put("Data", storeList);

		return rsMap;
	}

	/**
	 * @Desc : 단골 브랜드 현황 조회
	 * @Method Name : getInterestBrand
	 * @Date : 2019. 6. 25.
	 * @Author : 신인철
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getInterestBrand() throws Exception {
		Map<String, Object> rsMap = new HashMap<String, Object>();
		int totalCount = memberStatsDao.selectBrandTotalCount();

		List<MemberInterestBrandVO> brandList = memberStatsDao.selectInterestBrandList();
		brandList.get(0).setNoSeq("");

		rsMap.put("Total", totalCount);
		rsMap.put("Data", brandList);

		return rsMap;
	}

	/**
	 * @Desc : 포인트 현황 그리드 조회
	 * @Method Name : getPointGrid
	 * @Date : 2019. 7. 16.
	 * @Author : 신인철
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<MemberPointStatusVO> getPointGrid(Pageable<MemberPointStatusVO, MemberPointStatusVO> pageable)
			throws Exception {
		int totalCount = memberStatsDao.selectPointGridCount(pageable);
		pageable.setTotalCount(totalCount);
		log.debug(">>>>>>>>" + totalCount);
		if (totalCount > 0) {
			pageable.setContent(memberStatsDao.selectPointGrid(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 포인트 현황 엑셀 다운로드
	 * @Method Name : getPointExcelList
	 * @Date : 2019. 7. 16.
	 * @Author : 신인철
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<MemberPointStatusVO> getPointExcelList(MemberPointStatusVO param) throws Exception {
		List<MemberPointStatusVO> list = memberStatsDao.selectPointExcelList(param);
		return list;
	}
	/*********************************************************************
	 * E : 신인철
	 *******************************************************************/
}
