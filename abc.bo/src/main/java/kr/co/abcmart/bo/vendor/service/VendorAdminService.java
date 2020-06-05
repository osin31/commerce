package kr.co.abcmart.bo.vendor.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import kr.co.abcmart.bo.cmm.model.master.CmDaysCondtn;
import kr.co.abcmart.bo.cmm.service.DaysCondtnService;
import kr.co.abcmart.bo.system.model.master.SyAdmin;
import kr.co.abcmart.bo.system.model.master.SyAdminAccessIp;
import kr.co.abcmart.bo.system.model.master.SyAuthority;
import kr.co.abcmart.bo.system.repository.master.SyAdminDao;
import kr.co.abcmart.bo.system.service.AdminService;
import kr.co.abcmart.bo.system.service.CommonCodeService;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.util.UtilsDate;
import kr.co.abcmart.util.UtilsText;

@Service
public class VendorAdminService {

	@Autowired
	private AdminService adminService;

	@Autowired
	private CommonCodeService commonCodeService;

	@Autowired
	private DaysCondtnService daysCondtnService;

	@Autowired
	private SyAdminDao syAdminDao;

	/**
	 * @Desc : PO 관리자 화면 생성 데이터(코드성 데이터)
	 * @Method Name : getAdminDetailCodeData
	 * @Date : 2019. 9. 30.
	 * @Author : 이동엽
	 * @return Map
	 * @throws Exception
	 */
	public Map<String, Object> getAdminDetailCodeData() throws Exception {

		ModelMap resultMap = new ModelMap();
		SyAuthority syAuthority = new SyAuthority();
		syAuthority.setAuthApplySystemType(Const.AUTH_APPLY_SYSTEM_PO);
		syAuthority.setUseYn(Const.BOOLEAN_TRUE);

		// 관리자 권한
		List<SyAuthority> authorytyList = adminService.getAuthorytyList(syAuthority);
		// 이메일
		resultMap.addAttribute("emailSiteCode", commonCodeService.getCodeNoName(CommonCode.EMAIL_SITE_CODE));
		resultMap.addAttribute("authGroup", authorytyList);

		return resultMap;
	}

	/**
	 * @Desc : PO 관리자 목록을 조회
	 * @Method Name : getAdminList
	 * @Date : 2019. 9. 30.
	 * @Author : 이동엽
	 * @param pageable
	 * @return pageable
	 * @throws Exception
	 */
	public Page<SyAdmin> getAdminList(Pageable<SyAdmin, SyAdmin> pageable) throws Exception {
		// PO만 조회되도록 값 세팅
		pageable.getBean().setAuthApplySystemType(Const.AUTH_APPLY_SYSTEM_PO);

		int count = syAdminDao.selectAdminListCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(syAdminDao.selectAdminList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 관리자 상세 관련 데이터 조회
	 * @Method Name : getAdminDetailInfo
	 * @Date : 2019. 1. 31.
	 * @Author : 이동엽
	 * @param adminNo
	 * @return Map
	 * @throws Exception
	 */
	public Map<String, Object> getAdminDetailInfo(String adminNo) throws Exception {
		ModelMap resultMap = new ModelMap();
		// 2019. 02. 18 Aconnect 관리자 관련 소스 추가
		if (adminNo != null && !"".equals(adminNo)) {
			String pswdInitYn = "";
			String condtnTermName = Const.ADMIN_PSWD_RENEW_CONDITION;
			Date pswdChngDtm = new Date();
			// 2019.02.15 리택토링을 위해 추가
			Map<String, Object> detailCodeMap = new ModelMap();
			// 관리자 기본정보 조회
			SyAdmin detailData = adminService.selectAdminDetailInfo(adminNo);
			pswdInitYn = detailData.getPswdInitYn();
			pswdChngDtm = detailData.getPswdChngDtm();

			// 비밀번호 초기화 값이 Y인 경우 조건날짜 관리 테이블에서 조건 값을 조회하여 비밀번호 변경기한 값을 세팅.
			if (UtilsText.equals(pswdInitYn, Const.BOOLEAN_TRUE)) {
				// 조건날짜 테이블 조회
				CmDaysCondtn cmDaysCondtn = daysCondtnService.getDaysCondtn(condtnTermName);

				String condtnTermValue = cmDaysCondtn.getCondtnTermValue();
				pswdChngDtm = UtilsDate.addDays(pswdChngDtm, Integer.parseInt(condtnTermValue));
				// Date 형태의 값을 Timestamp 형태의 값으로 변환
				Timestamp convertDate = new Timestamp(pswdChngDtm.getTime());
				detailData.setPswdChngDtm(convertDate);
			}
			// 접근 허용 아이피 조회
			List<SyAdminAccessIp> accessIpData = adminService.selectAdminAccessIp(adminNo);

			resultMap.addAttribute("detailData", detailData);
			resultMap.addAttribute("accessIpData", accessIpData);

			// 2019.02.15 리택토링을 위해 추가
			detailCodeMap = this.getAdminDetailCodeData();
			resultMap.addAttribute("authGroup", detailCodeMap.get("authGroup"));
		}

		// 2019.02.18 이메일 코드 정보 조회 추가.
		resultMap.addAttribute("emailSiteCode", commonCodeService.getCodeNoName(CommonCode.EMAIL_SITE_CODE));

		return resultMap;
	}
}
