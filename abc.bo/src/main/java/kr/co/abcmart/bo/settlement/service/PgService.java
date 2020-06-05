package kr.co.abcmart.bo.settlement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.settlement.repository.master.PgDao;
import kr.co.abcmart.bo.settlement.vo.RvGiftCardComparision;
import kr.co.abcmart.bo.settlement.vo.RvKakaoComparision;
import kr.co.abcmart.bo.settlement.vo.RvKcpComparision;
import kr.co.abcmart.bo.settlement.vo.RvNaverComparision;
import kr.co.abcmart.common.config.Config;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.Const;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PgService {

	@Autowired
	PgDao pgDao;

	/**
	 * @Desc : 카카오 대사관리 리스트 조회
	 * @Method Name : getKakaoList
	 * @Date : 2019. 11. 8.
	 * @Author : sic
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<RvKakaoComparision> getKakaoList(Pageable<RvKakaoComparision, RvKakaoComparision> pageable)
			throws Exception {
		if (UtilsText.isNotBlank(pageable.getBean().getSiteNo())) {
			if (Const.SITE_NO_ART.equals(pageable.getBean().getSiteNo())) {
				pageable.getBean().setCid(Config.getString("art.kakao.api.cid"));
			} else if (Const.SITE_NO_OTS.equals(pageable.getBean().getSiteNo())) {
				pageable.getBean().setCid(Config.getString("ots.kakao.api.cid"));
			}
		}
		int totalCount = pgDao.selectKakaoCount(pageable);
		if (totalCount > 0) {
			pageable.setContent(pgDao.selectKakaoList(pageable));
		}
		return pageable.getPage();
	}

	/**
	 * @Desc : 카카오 대사관리 엑셀 다운로드
	 * @Method Name : getKakaoExcelLIst
	 * @Date : 2019. 11. 13.
	 * @Author : sic
	 * @param rvKakaoComparision
	 * @return
	 * @throws Exception
	 */
	public List<RvKakaoComparision> getKakaoExcelLIst(RvKakaoComparision rvKakaoComparision) throws Exception {
		return pgDao.selectKakaoExcelList(rvKakaoComparision);
	}

	/**
	 * @Desc : 카카오 대사관리 정산내역 조회
	 * @Method Name : getKakaoSettlementHistory
	 * @Date : 2019. 11. 13.
	 * @Author : sic
	 * @param rvKakaoComparision
	 * @return
	 * @throws Exception
	 */
	public RvKakaoComparision getKakaoSettlementHistory(RvKakaoComparision rvKakaoComparision) throws Exception {
		if (UtilsText.isNotBlank(rvKakaoComparision.getSiteNo())) {
			if (Const.SITE_NO_ART.equals(rvKakaoComparision.getSiteNo())) {
				rvKakaoComparision.setCid(Config.getString("art.kakao.api.cid"));
			} else if (Const.SITE_NO_OTS.equals(rvKakaoComparision.getSiteNo())) {
				rvKakaoComparision.setCid(Config.getString("ots.kakao.api.cid"));
			}
		}
		return pgDao.selectKakaoSettlementHistory(rvKakaoComparision);
	}

	/**
	 * @Desc : 네이버 대사관리 리스트 조회
	 * @Method Name : getNaverList
	 * @Date : 2019. 11. 8.
	 * @Author : sic
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<RvNaverComparision> getNaverList(Pageable<RvNaverComparision, RvNaverComparision> pageable)
			throws Exception {
		if (UtilsText.isNotBlank(pageable.getBean().getSiteNo())) {
			if (Const.SITE_NO_ART.equals(pageable.getBean().getSiteNo())) {
				pageable.getBean().setMerchantId(Config.getString("art.naver.api.partnerCode"));
			} else if (Const.SITE_NO_OTS.equals(pageable.getBean().getSiteNo())) {
				pageable.getBean().setMerchantId(Config.getString("ots.naver.api.partnerCode"));
			}
		}
		int totalCount = pgDao.selectNaverCount(pageable);
		if (totalCount > 0) {
			pageable.setContent(pgDao.selectNaverList(pageable));
		}

		pageable.setTotalCount(totalCount);
		return pageable.getPage();
	}

	/**
	 * @Desc : 네이버 대사관리 엑셀 다운로드
	 * @Method Name : getNaverExcelLIst
	 * @Date : 2019. 11. 11.
	 * @Author : sic
	 * @param rvNaverComparision
	 * @return
	 * @throws Exception
	 */
	public List<RvNaverComparision> getNaverExcelLIst(RvNaverComparision rvNaverComparision) throws Exception {
		return pgDao.selectNaverExcelList(rvNaverComparision);
	}

	/**
	 * @Desc : 네이버 대사관리 정산내역 조회
	 * @Method Name : getNaverSettlementHistory
	 * @Date : 2019. 11. 11.
	 * @Author : sic
	 * @param rvNaverComparision
	 * @return
	 * @throws Exception
	 */
	public RvNaverComparision getNaverSettlementHistory(RvNaverComparision rvNaverComparision) throws Exception {
		if (UtilsText.isNotBlank(rvNaverComparision.getSiteNo())) {
			if (Const.SITE_NO_ART.equals(rvNaverComparision.getSiteNo())) {
				rvNaverComparision.setMerchantId(Config.getString("art.naver.api.partnerCode"));
			} else if (Const.SITE_NO_OTS.equals(rvNaverComparision.getSiteNo())) {
				rvNaverComparision.setMerchantId(Config.getString("ots.naver.api.partnerCode"));
			}
		}
		return pgDao.selectNaverSettlementHistory(rvNaverComparision);
	}

	/**
	 * @Desc : 기프트카드 대사관리 리스트 조회
	 * @Method Name : getGiftCardList
	 * @Date : 2019. 11. 11.
	 * @Author : sic
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<RvGiftCardComparision> getGiftCardList(Pageable<RvGiftCardComparision, RvGiftCardComparision> pageable)
			throws Exception {
		int totalCount = pgDao.selectGiftCardCount(pageable);
		if (totalCount > 0) {
			pageable.setContent(pgDao.selectGiftCardList(pageable));
		}

		pageable.setTotalCount(totalCount);
		return pageable.getPage();
	}

	/**
	 * @Desc : 키프트카드 엑셀 다운로드
	 * @Method Name : getGiftCardExcelLIst
	 * @Date : 2019. 11. 11.
	 * @Author : sic
	 * @param rvGiftCardComparision
	 * @return
	 * @throws Exception
	 */
	public List<RvGiftCardComparision> getGiftCardExcelLIst(RvGiftCardComparision rvGiftCardComparision)
			throws Exception {
		return pgDao.selectGiftCardExcelList(rvGiftCardComparision);
	}

	/**
	 * @Desc : 기프트카드 정산내역 조회
	 * @Method Name : getGiftCardSettlementHistory
	 * @Date : 2019. 11. 11.
	 * @Author : sic
	 * @param rvGiftCardComparision
	 * @return
	 * @throws Exception
	 */
	public RvGiftCardComparision getGiftCardSettlementHistory(RvGiftCardComparision rvGiftCardComparision)
			throws Exception {
		return pgDao.selectGiftCardSettlementHistory(rvGiftCardComparision);
	}

	/**
	 * @Desc : KCP 대사관리 리스트 조회
	 * @Method Name : getKcpList
	 * @Date : 2019. 11. 12.
	 * @Author : sic
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<RvKcpComparision> getKcpList(Pageable<RvKcpComparision, RvKcpComparision> pageable) throws Exception {
		int totalCount = pgDao.selectKcpCount(pageable);
		if (totalCount > 0) {
			pageable.setContent(pgDao.selectKcpList(pageable));
		}

		pageable.setTotalCount(totalCount);
		return pageable.getPage();
	}

	/**
	 * @Desc : KCP 엑셀 다운로드
	 * @Method Name : getGiftCardExcelLIst
	 * @Date : 2019. 11. 12.
	 * @Author : sic
	 * @param rvKcpComparision
	 * @return
	 * @throws Exception
	 */
	public List<RvKcpComparision> getKcpExcelLIst(RvKcpComparision rvKcpComparision) throws Exception {
		return pgDao.selectKcpExcelList(rvKcpComparision);
	}

	/**
	 * @Desc : KCP 정산내역 조회
	 * @Method Name : getKcpSettlementHistory
	 * @Date : 2019. 11. 12.
	 * @Author : sic
	 * @param rvKcpComparision
	 * @return
	 * @throws Exception
	 */
	public RvKcpComparision getKcpSettlementHistory(RvKcpComparision rvKcpComparision) throws Exception {
		return pgDao.selectKcpSettlementHistory(rvKcpComparision);
	}

}
