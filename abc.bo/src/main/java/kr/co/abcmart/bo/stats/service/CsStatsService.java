package kr.co.abcmart.bo.stats.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.stats.model.master.CurrentSaleStats;
import kr.co.abcmart.bo.stats.model.master.SaCsStatus;
import kr.co.abcmart.bo.stats.repository.master.SaCsStatusDao;
import kr.co.abcmart.bo.stats.vo.CsStatsSearchVO;
import kr.co.abcmart.bo.stats.vo.OrderStatsSearchVO;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.CommonCode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CsStatsService {

	@Autowired
	private SaCsStatusDao saCsStatusDao;

	/**
	 * @Desc : 기획전 현황 통계
	 * @Method Name : getPlanningList
	 * @Date : 2019. 7. 16.
	 * @Author : bje0507
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<SaCsStatus> getReviewList(Pageable<CsStatsSearchVO, SaCsStatus> pageable) throws Exception {

		int count = saCsStatusDao.reviewCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(saCsStatusDao.reviewStats(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 기획전 현황 통계
	 * @Method Name : getInquiryList
	 * @Date : 2019. 7. 16.
	 * @Author : bje0507
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<SaCsStatus> getInquiryList(Pageable<CsStatsSearchVO, SaCsStatus> pageable) throws Exception {

		int count = saCsStatusDao.inquiryCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(saCsStatusDao.inquiryStats(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 반품/교환 완료 통계 목록 조회
	 * @Method getClaimList
	 * @Date : 2019. 10. 16.
	 * @Author : 이강수
	 * @param Pageable<OrderStatsSearchVO, CurrentSaleStats>
	 * @return Page<CurrentSaleStats>
	 * @throws Exception
	 */
	public Page<CurrentSaleStats> getClaimList(Pageable<OrderStatsSearchVO, CurrentSaleStats> pageable)
			throws Exception {

		String claimType = pageable.getBean().getClaimType();

		if (UtilsText.equals(claimType, "return")) {
			pageable.getBean().setClmPrdtStatCode(CommonCode.CLM_PRDT_STAT_CODE_RETURN_FINISH);
		} else if (UtilsText.equals(claimType, "change")) {
			pageable.getBean().setClmPrdtStatCode(CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_FINISH);
		}

		int count = saCsStatusDao.selectClmCntGroupbyModDtmCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			List<CurrentSaleStats> list = saCsStatusDao.selectClmCntGroupbyModDtmPaging(pageable);
			// 어떤 소팅 기준이던 "합계"를 가장 위에
			CurrentSaleStats totalCss = list.stream().filter(x -> UtilsText.equals(x.getDateGbn(), "합계"))
					.collect(Collectors.toList()).get(0);
			list = list.stream().filter(x -> !UtilsText.equals(x.getDateGbn(), "합계")).collect(Collectors.toList());
			list.add(0, totalCss);
			pageable.setContent(list);
		}

		return pageable.getPage();
	}

}
