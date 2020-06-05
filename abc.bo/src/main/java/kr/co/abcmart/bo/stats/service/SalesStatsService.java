/**
 *
 */
package kr.co.abcmart.bo.stats.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.product.model.master.SyStandardCategory;
import kr.co.abcmart.bo.product.service.StandardCategoryService;
import kr.co.abcmart.bo.stats.model.master.SaSalesOrder;
import kr.co.abcmart.bo.stats.repository.master.SaSalesOrderDao;
import kr.co.abcmart.bo.stats.vo.SalesStatsSearchVO;
import kr.co.abcmart.bo.vendor.model.master.VdVendor;
import kr.co.abcmart.bo.vendor.service.VendorService;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;

@Service
public class SalesStatsService {
	
	@Autowired
	private StandardCategoryService standardCategoryService;
	@Autowired
	private SaSalesOrderDao saSalesOrderDao;
	@Autowired
	private VendorService vendorService;

	/**
	 * 
	 * @Desc : 발송처별 매출 통계
	 * @Method Name : getStatsList
	 * @Date : 2019. 7. 6.
	 * @Author : NKB
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<SaSalesOrder> getSendTypeList(Pageable<SalesStatsSearchVO, SaSalesOrder> pageable) throws Exception {

		int count = saSalesOrderDao.sendTypeCount(pageable);
		
		if (count > 0) {
			// +1은 합계 로우 추가
			pageable.setTotalCount(count+1);
			pageable.setContent(saSalesOrderDao.selectSendTypeStats(pageable));

		}

		return pageable.getPage();
	}

	/**
	 * 
	 * @Desc : 발송처별 매출 통계 엑셀
	 * @Method Name : getStatsListForExcel
	 * @Date : 2019. 7. 6.
	 * @Author : NKB
	 * @param saSalesOrder
	 * @return
	 * @throws Exception
	 */
	public List<SaSalesOrder> getSendTypeListForExcel(SalesStatsSearchVO salesStatsSearchVO) throws Exception {

		return saSalesOrderDao.selectSendTypeStatsForExcel(salesStatsSearchVO);
	}

	/**
	 * 
	 * @Desc : 디바이스별 매출 통계
	 * @Method Name : getDeviceList
	 * @Date : 2019. 7. 6.
	 * @Author : NKB
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<SaSalesOrder> getDeviceTypeList(Pageable<SalesStatsSearchVO, SaSalesOrder> pageable) throws Exception {

		int count = saSalesOrderDao.selectDeviceTypeCount(pageable);

		if (count > 0) {
			// +1은 합계 로우 추가
			pageable.setTotalCount(count+1);
			pageable.setContent(saSalesOrderDao.selectDeviceTypeStats(pageable));

		}

		return pageable.getPage();
	}

	/**
	 * 
	 * @Desc : 디바이스별 매출 통계 엑셀
	 * @Method Name : getDeviceListForExcel
	 * @Date : 2019. 7. 6.
	 * @Author : NKB
	 * @param saSalesOrder
	 * @return
	 * @throws Exception
	 */
	public List<SaSalesOrder> getDeviceListForExcel(SalesStatsSearchVO salesStatsSearchVO) throws Exception {

		return saSalesOrderDao.selectDeviceTypeStatsForExcel(salesStatsSearchVO);
	}

	/**
	 * 
	 * @Desc : 결제수단별 매출통계
	 * @Method Name : getPaymentTypeList
	 * @Date : 2019. 7. 8.
	 * @Author : NKB
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<SaSalesOrder> getPaymentTypeList(Pageable<SalesStatsSearchVO, SaSalesOrder> pageable) throws Exception {

		int count = saSalesOrderDao.selectPaymentTypeCount(pageable);
		
		if (count > 0) {
			// +1은 합계 로우 추가
			pageable.setTotalCount(count);
			pageable.setContent(saSalesOrderDao.selectPaymentTypeStats(pageable));

		}

		return pageable.getPage();
	}

	/**
	 * 
	 * @Desc : 결제수단별 매출통계 엑셀
	 * @Method Name : getPaymentListForExcel
	 * @Date : 2019. 7. 8.
	 * @Author : NKB
	 * @param saSalesOrder
	 * @return
	 * @throws Exception
	 */
	public List<SaSalesOrder> getPaymentListForExcel(SalesStatsSearchVO salesStatsSearchVO) throws Exception {

		return saSalesOrderDao.selectPaymentTypeStatsForExcel(salesStatsSearchVO);
	}
	
	/**
		 * @Desc : 배송수단별 통계 현황 조회
		 * @Method Name : getDlvyTypeList
		 * @Date : 2019. 7. 10.
		 * @Author : 이재렬
		 * @param pageable
		 * @throws Exception
		 */
	public Page<SaSalesOrder> getDlvyTypeList(Pageable<SalesStatsSearchVO, SaSalesOrder> pageable) throws Exception {
		
		int count = saSalesOrderDao.selectDlvyTypeCount(pageable);

		if (count > 0) {
			// +1은 합계 로우 추가
			pageable.setTotalCount(count+1);
			pageable.setContent(saSalesOrderDao.selectDlvyTypeList(pageable));

		}

		return pageable.getPage();
	}
	
	/**
		 * @Desc : 배송수단별 통계 엑셀 다운로드
		 * @Method Name : getDlvyListForExcel
		 * @Date : 2019. 7. 10.
		 * @Author : 이재렬
		 * @param saSalesOrder
		 * @throws Exception
		 */
	public List<SaSalesOrder> getDlvyListForExcel(SalesStatsSearchVO salesStatsSearchVO) throws Exception {

		return saSalesOrderDao.selectDlvyTypeStatsForExcel(salesStatsSearchVO);
	}
	
	/**
		 * @Desc : 시간별 통계 현황 조회
		 * @Method Name : getTimeTypeList
		 * @Date : 2019. 7. 10.
		 * @Author : 이재렬
		 * @param pageable
		 * @throws Exception
		 */
	public Page<SaSalesOrder> getTimeTypeList(Pageable<SalesStatsSearchVO, SaSalesOrder> pageable) throws Exception {
		
		pageable.setContent(saSalesOrderDao.selectTimeTypeList(pageable));
	
		return pageable.getPage();
	}

	/**
		 * @Desc : 시간별 통계 엑셀 다운로드
		 * @Method Name : getTimeListForExcel
		 * @Date : 2019. 7. 10.
		 * @Author : 이재렬
		 * @param saSalesOrder
		 * @throws Exception
		 */
	public List<SaSalesOrder> getTimeListForExcel(SalesStatsSearchVO salesStatsSearchVO) throws Exception {
	
		return saSalesOrderDao.selectTimeTypeStatsForExcel(salesStatsSearchVO);
	}
	
	/**
		 * @Desc : 연령별 통계 현황 조회
		 * @Method Name : getTimeTypeList
		 * @Date : 2019. 7. 10.
		 * @Author : 이재렬
		 * @param pageable
		 * @throws Exception
		 */
	public Page<SaSalesOrder> getAgeTypeList(Pageable<SalesStatsSearchVO, SaSalesOrder> pageable) throws Exception {
		
		int count = saSalesOrderDao.selectAgeTypeCount(pageable);
	
		if (count > 0) {
			// +1은 합계 로우 추가
			pageable.setTotalCount(count+1);
			pageable.setContent(saSalesOrderDao.selectAgeTypeList(pageable));
	
		}
	
		return pageable.getPage();
	}

	/**
		 * @Desc : 연령별 통계 엑셀 다운로드
		 * @Method Name : getAgeListForExcel
		 * @Date : 2019. 7. 10.
		 * @Author : 이재렬
		 * @param saSalesOrder
		 * @throws Exception
		 */
	public List<SaSalesOrder> getAgeListForExcel(SalesStatsSearchVO salesStatsSearchVO) throws Exception {
	
		return saSalesOrderDao.selectAgeTypeStatsForExcel(salesStatsSearchVO);
	}
	
	/**
		 * @Desc : 성별 통계 현황 조회
		 * @Method Name : getGenderTypeList
		 * @Date : 2019. 7. 11.
		 * @Author : 이재렬
		 * @param pageable
		 * @throws Exception
		 */
	public Page<SaSalesOrder> getGenderTypeList(Pageable<SalesStatsSearchVO, SaSalesOrder> pageable) throws Exception {
		
		int count = saSalesOrderDao.selectGenderTypeCount(pageable);
	
		if (count > 0) {
			// +1은 합계 로우 추가
			pageable.setTotalCount(count+1);
			pageable.setContent(saSalesOrderDao.selectGenderTypeList(pageable));
	
		}
	
		return pageable.getPage();
	}
	
	/**
		 * @Desc : 성별 통계 엑셀 다운로드
		 * @Method Name : getGenderListForExcel
		 * @Date : 2019. 7. 11.
		 * @Author : 이재렬
		 * @param saSalesOrder
		 * @throws Exception
		 */
	public List<SaSalesOrder> getGenderListForExcel(SalesStatsSearchVO salesStatsSearchVO) throws Exception {
	
		return saSalesOrderDao.selectGenderTypeStatsForExcel(salesStatsSearchVO);
	}
	
	/**
		 * @Desc : 상품별 통계 현황조회
		 * @Method Name : getPrdtTypeList
		 * @Date : 2019. 7. 15.
		 * @Author : 이재렬
		 * @param parameter
		 * @throws Exception
		 */
	public Page<SaSalesOrder> getPrdtTypeList(Pageable<SalesStatsSearchVO, SaSalesOrder> pageable) throws Exception {
			
		int count = saSalesOrderDao.selectPrdtTypeCount(pageable);

		if (count > 0) {
			// +1은 합계 로우 추가
			pageable.setTotalCount(count+1);
			pageable.setContent(saSalesOrderDao.selectPrdtTypeList(pageable));
	
		}
	
		return pageable.getPage();
	}
	
	/**
		 * @Desc : 상품별 통계 엑셀 다운로드
		 * @Method Name : getPrdtListForExcel
		 * @Date : 2019. 7. 15.
		 * @Author : 이재렬
		 * @param saSalesOrder
		 * @throws Exception
		 */
	public List<SaSalesOrder> getPrdtListForExcel(SalesStatsSearchVO salesStatsSearchVO) throws Exception {
	
		return saSalesOrderDao.selectPrdtTypeStatsForExcel(salesStatsSearchVO);
	}
	
	/**
		 * @Desc : 입점사 매출 통계 목록 조회.
		 * @Method Name : getVendorTypeList
		 * @Date : 2019. 7. 16.
		 * @Author : 이재렬
		 * @param pageable
		 * @throws Exception
		 */
	public Page<SaSalesOrder> getVendorTypeList(Pageable<SalesStatsSearchVO, SaSalesOrder> pageable) throws Exception {
		
		int count = saSalesOrderDao.selectVendorTypeCount(pageable);
	
		if (count > 0) {
			// +1은 합계 로우 추가
			pageable.setTotalCount(count+1);
			pageable.setContent(saSalesOrderDao.selectVendorTypeList(pageable));
	
		}
	
		return pageable.getPage();
	}
	
	/**
		 * @Desc : 입점사 매출 통계 엑셀 다운로드
		 * @Method Name : getPrdtListForExcel
		 * @Date : 2019. 7. 15.
		 * @Author : 이재렬
		 * @param salesStatsSearchVO
		 * @throws Exception
		 */
	public List<SaSalesOrder> getVendorListForExcel(SalesStatsSearchVO salesStatsSearchVO) throws Exception {
	
		return saSalesOrderDao.selectVendorTypeStatsForExcel(salesStatsSearchVO);
	}
	
	/**
		 * @Desc : 입점사 상품 매출현황 팝업 목록 조회.
		 * @Method Name : getVendorPopList
		 * @Date : 2019. 7. 16.
		 * @Author : 이재렬
		 * @param pageable
		 * @throws Exception
		 */
	public Page<SaSalesOrder> getVendorPopList(Pageable<SalesStatsSearchVO, SaSalesOrder> pageable) throws Exception {
		
		int count = saSalesOrderDao.selectVendorPopCount(pageable);
	
		if (count > 0) {
			// +1은 합계 로우 추가
			pageable.setTotalCount(count+1);
			pageable.setContent(saSalesOrderDao.selectVendorPopList(pageable));
	
		}
	
		return pageable.getPage();
	}
	
	/**
		 * @Desc : 입점사 상품 매출현황 팝업 엑셀 다운로드
		 * @Method Name : getVendorPopListForExcel
		 * @Date : 2019. 7. 16.
		 * @Author : 이재렬
		 * @param salesStatsSearchVO
		 * @throws Exception
		 */
	public List<SaSalesOrder> getVendorPopListForExcel(SalesStatsSearchVO salesStatsSearchVO) throws Exception {
	
		return saSalesOrderDao.selectVendorPopForExcel(salesStatsSearchVO);
	}
	
	/**
		 * @Desc : 대분류 표준 카테고리 조회
		 * @Method Name : getBigCategoryList
		 * @Date : 2019. 7. 15.
		 * @Author : 이재렬
		 * @param 
		 * @throws Exception
		 */
	public void getBigCategoryList(Parameter<?> parameter) throws Exception {
		SyStandardCategory syStandardCategory = new SyStandardCategory();
		
		syStandardCategory.setLeafCtgrYn(Const.BOOLEAN_FALSE);
		syStandardCategory.setLevel("1");
		parameter.addAttribute("bigCategory", standardCategoryService.getStandardCategoryList(syStandardCategory));
	}
	
	/**
		 * @Desc : 중분류 표준 카테고리 조회
		 * @Method Name : getMidCategoryList
		 * @Date : 2019. 7. 15.
		 * @Author : 이재렬
		 * @param ctgrNo
		 * @throws Exception
		 */
	public List<?> getMidCategoryList(String ctgrNo) throws Exception {
		SyStandardCategory syStandardCategory = new SyStandardCategory();
		syStandardCategory.setLevel("2");
		syStandardCategory.setUpStdCtgrNo(ctgrNo);
		return standardCategoryService.getStandardCategoryList(syStandardCategory);
	}
	
	/**
		 * @Desc : 소분류 표준 카테고리 조회
		 * @Method Name : getSmallCategoryList
		 * @Date : 2019. 7. 15.
		 * @Author : 이재렬
		 * @param ctgrNo
		 * @throws Exception
		 */
	public List<?> getSmallCategoryList(String ctgrNo) throws Exception {
		SyStandardCategory syStandardCategory = new SyStandardCategory();
		syStandardCategory.setLevel("3");
		syStandardCategory.setUpStdCtgrNo(ctgrNo);
		return standardCategoryService.getStandardCategoryList(syStandardCategory);
	}

	/**
		 * @Desc : 오늘날짜 데이터
		 * @Method Name : getToday
		 * @Date : 2019. 7. 5.
		 * @Author : 이재렬
		 * @param parameter
		 * @throws Exception
		 */
	public void getToday(Parameter<?> parameter) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
		Date date = new Date();
		
		parameter.addAttribute("thisYear", formatter.format(date).substring(0, 4));
		parameter.addAttribute("thisMonth", formatter.format(date).substring(5, 7));
		parameter.addAttribute("thisDay", formatter.format(date).substring(8, 10));
	}
	
	/**
		 * @Desc : 페이지 공통
		 * @Method Name : getCommonCondition
		 * @Date : 2019. 10. 11.
		 * @Author : 이재렬
		 * @param parameter
		 * @throws Exception
		 */
	public void getCommonCondition(Parameter<?> parameter) throws Exception {
		getToday(parameter);
		
		UserDetails user = LoginManager.getUserDetails();
		Boolean isAdmin = UtilsText.equals(user.getUpAuthNo(), Const.ROLE_ADMIN_GROUP) ? true : false;
		parameter.addAttribute("isAdmin", isAdmin);
		
		if (!isAdmin) {
			VdVendor vndr = new VdVendor();
			vndr.setVndrNo(user.getVndrNo());
			vndr = vendorService.getVendorBaseInfo(vndr);

			parameter.addAttribute("vndrNo", vndr.getVndrNo());
			parameter.addAttribute("vndrName", vndr.getVndrName());
		}
	}

}
