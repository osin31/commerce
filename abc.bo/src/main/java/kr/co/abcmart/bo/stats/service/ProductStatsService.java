package kr.co.abcmart.bo.stats.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.stats.model.master.SaProductStats;
import kr.co.abcmart.bo.stats.repository.master.SaProductStatusDao;
import kr.co.abcmart.bo.stats.vo.ProductStatsSearchVO;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductStatsService {

	@Autowired
	private SaProductStatusDao saProductStatusDao;

	/**
	 * @Desc : 상품 등록 현황 리스트 조회
	 * @Method Name : getProductStatsList
	 * @Date : 2019. 7. 26.
	 * @Author : 백인천
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<SaProductStats> getProductStatsList(Pageable<ProductStatsSearchVO, SaProductStats> pageable)
			throws Exception {

		int count = saProductStatusDao.productStatsListCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(saProductStatusDao.productStatsList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 상품 등록 현황 통계 엑셀다운로드
	 * @Method Name : getProductStatsListForExcel
	 * @Date : 2019. 7. 19.
	 * @Author : 백인천
	 * @param couponStatsSearchVO
	 * @throws Exception
	 */
	public List<SaProductStats> getProductStatsListForExcel(ProductStatsSearchVO productStatsSearchVO)
			throws Exception {

		return saProductStatusDao.selectProductStatsListForExcel(productStatsSearchVO);
	}

	/**
	 * @Desc : 공유하기 현황 list 조회
	 * @Method Name : getShareStatsList
	 * @Date : 2019. 8. 1.
	 * @Author : bje0507
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<SaProductStats> getShareStatsList(Pageable<ProductStatsSearchVO, SaProductStats> pageable)
			throws Exception {

		int count = saProductStatusDao.shareStatsListCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(saProductStatusDao.shareStatsList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 입점사 수수료 통계 그리드 조회
	 * @Method Name : getCmsnStatsList
	 * @Date : 2019. 8. 5.
	 * @Author : bje0507
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<SaProductStats> getCmsnStatsList(Pageable<ProductStatsSearchVO, SaProductStats> pageable)
			throws Exception {

		int count = saProductStatusDao.cmsnStatsListCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(saProductStatusDao.cmsnStatsList(pageable));
		}

		return pageable.getPage();
	}

}
