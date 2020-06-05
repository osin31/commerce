package kr.co.abcmart.bo.stats.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.stats.model.master.SaRestockStatus;
import kr.co.abcmart.bo.stats.repository.master.SaRestockStatusDao;
import kr.co.abcmart.bo.stats.vo.RestockStatsSearchVO;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RestockStatsService {

	@Autowired
	private SaRestockStatusDao saRestockStatusDao;

	/**
	 * @Desc : 재입고 알림 서비스 상품별 통계
	 * @Method Name : getProductRestockList
	 * @Date : 2019. 7. 30.
	 * @Author : bje0507
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<SaRestockStatus> getProductRestockList(Pageable<RestockStatsSearchVO, SaRestockStatus> pageable)
			throws Exception {

		int count = saRestockStatusDao.productRestockCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(saRestockStatusDao.productRestockStats(pageable));
		}
		return pageable.getPage();
	}

	/**
	 * @Desc : 재입고 알림 서비스기간별 통계
	 * @Method Name : getDateRestockList
	 * @Date : 2019. 7. 30.
	 * @Author : bje0507
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<SaRestockStatus> getDateRestockList(Pageable<RestockStatsSearchVO, SaRestockStatus> pageable)
			throws Exception {

		int count = saRestockStatusDao.dateRestockCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(saRestockStatusDao.dateRestockStats(pageable));
		}

		return pageable.getPage();
	}

}
