package kr.co.abcmart.bo.cmm.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.cmm.model.master.CmHoliday;
import kr.co.abcmart.bo.cmm.repository.master.CmHolidayDao;
import kr.co.abcmart.bo.vendor.model.master.VdVendor;
import kr.co.abcmart.bo.vendor.repository.master.VdVendorDao;
import kr.co.abcmart.common.config.Config;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;
import kr.co.abcmart.util.UtilsDate;
import kr.co.abcmart.util.UtilsText;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HolidayService {

	@Autowired
	HolidayService holidayService;

	@Autowired
	CmHolidayDao cmHolidayDao;

	@Autowired
	VdVendorDao vdVendorDao;

	/**
	 *
	 * @Desc : 관리자의 권한에 따른 휴일 데이터(count, data)를 조회한다.
	 * @Method Name : getHolidayList
	 * @Date : 2019. 2. 19.
	 * @Author : choi
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<CmHoliday> getHolidayList(Pageable<CmHoliday, CmHoliday> pageable) throws Exception {
		CmHoliday param = new CmHoliday();
		CmHoliday cmHoliday = pageable.getBean();
		String role = cmHoliday.getRole();
		String roleAdminGroup = Const.ROLE_ADMIN_GROUP; // 총괄 관리자 그룹 코드(관리자 그룹별로 관리하는 휴일 데이터가 다르므로)

		if (roleAdminGroup.equals(role)) { // 관리자 그룹일 때는 공통 휴일 정보를 보여준다.
			param.setHldyGbnType(Config.getString("holiday.common"));
		} else {
			param.setVndrNo(cmHoliday.getVndrNo()); // 업체 권한일 때는 업체관리자의 세션의 업체ID를 세팅 또는 화면 파라미터로 받음
			param.setHldyGbnType(Config.getString("holiday.default"));
		}
		param.setYear(cmHoliday.getYear());
		pageable.setBean(param);

		int count = cmHolidayDao.selectHolidayYearListCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(cmHolidayDao.selectHolidayYearList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 *
	 * @Desc : 공통휴일 존재 유무 체크 후, 권한별 휴일 등록/수정 작업을 한다.
	 * @Method Name : setHolidayDate
	 * @Date : 2019. 2. 20.
	 * @Author : choi
	 * @param cmHoliday
	 * @return
	 * @throws Exception
	 */
	public void setHolidayDate(CmHoliday cmHoliday) throws Exception {
		String[] vendorArr = cmHoliday.getVndrNoArr(); // vndrNo
		String holidayList = cmHoliday.getHolidayDataArea();
		String[] arrHolidayList = holidayList.split(",");
		UserDetails user = LoginManager.getUserDetails();
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		String authType = cmHoliday.getAuthType(); // 권한의 타입(B:BO 권한,P:PO 권한)
		List<CmHoliday> vendorlist = null;

		String stYear = cmHoliday.getInsertYear(); // param으로 넘어오는 년도정보(jsp에서 세팅 필요)
		String standardHoliday = cmHoliday.getStandardHoliday();

		// 기준 코드가 없을 때 365일의 시스템 코드를 insert 시킴
		// S:기준코드
		CmHoliday cmHolidaySystem = new CmHoliday();
		cmHolidaySystem.setYyyyMm(stYear);
		cmHolidaySystem.setHldyGbnType(standardHoliday);
		List<CmHoliday> standardHolidayList = cmHolidayDao.selectStandardHolidayList(cmHolidaySystem);

		if (standardHolidayList.size() < 1) {
			resultList = getHolidayInfoProcess(cmHoliday, stYear, resultList); // 토요일,일요일,국가공휴일 정보를 리턴

			// resultList로 시스템 공휴일 데이터 insert
			cmHolidayDao.insertsystemHolidayNo(resultList);
		}
		// E:기준 코드

		if ("P".equals(authType)) { // PO 권한의 작업[자기 업체의 휴일만 저장]
			cmHoliday.setYyyyMm(stYear);
			cmHoliday.setVndrNo(user.getVndrNo());
			cmHoliday.setHldyGbnType(Const.HOLIDAY_DEFAULT);
			vendorlist = cmHolidayDao.selectHolidayList(cmHoliday); // 해당 업체코드의 휴일 리스트를 조회

			if (vendorlist.size() > 0) { // 해당 년도의 업체 휴일 데이터가 존재[UPDATE 대상]
				for (int i = 0; i < arrHolidayList.length; i++) {
					String strDate = arrHolidayList[i]; // "2016-07-21T21:30:47.492+0000";

					DateFormat df = new SimpleDateFormat("yyyyMMdd");
					Date date = df.parse(strDate);

					cmHoliday.setHldyYmd(UtilsDate.getSqlTimeStamp(date));
					cmHoliday.setHldyYn("Y");
					cmHoliday.setModerNo(user.getAdminNo()); // 수정자[관리자 번호] 세팅
					cmHolidayDao.updateHoliday(cmHoliday);
				}

			} else { // 해당 년도의 업체 휴일 데이터가 없음[INSERT 대상]
				List<CmHoliday> standardList = new ArrayList<CmHoliday>();

				cmHolidaySystem.setInsertYear(stYear);
				standardHolidayList = cmHolidayDao.selectStandardHolidayListAll(cmHolidaySystem); // 시스템 휴일 데이터 365개 데이터
																									// 조회
				int count = 0;

				for (int i = 0; i < standardHolidayList.size(); i++) {
					cmHoliday = standardHolidayList.get(i); // HLDY_YMD
					cmHoliday.setVndrNo(user.getVndrNo());
					cmHoliday.setHldyGbnType(Const.HOLIDAY_DEFAULT);
					cmHoliday.setRgsterNo(user.getAdminNo());
					String yyyyMMdd = UtilsDate.formatter("yyyyMMdd", UtilsDate.getDate(cmHoliday.getHldyYmd()));

					boolean isTure = holidayList.contains(yyyyMMdd);

					if (isTure) {
						cmHoliday.setHldyYn("Y");
					} else {
						cmHoliday.setHldyYn("N");
					}
					standardList.add(cmHoliday);
					count++;

					if (count % 50 == 0) {
						cmHolidayDao.insertHoliday(standardList);
						standardList = new ArrayList<CmHoliday>();
					}
				}
				cmHolidayDao.insertHoliday(standardList);
			}

		} else { // BO[공통휴일과 모든 업체의 휴일을 저장]
			// 업체에 대한 휴일 등록 작업
			for (int x = 0; x < vendorArr.length; x++) {
				cmHoliday.setYyyyMm(stYear);
				cmHoliday.setVndrNo(vendorArr[x]);
				cmHoliday.setHldyGbnType(Const.HOLIDAY_DEFAULT);
				vendorlist = cmHolidayDao.selectHolidayList(cmHoliday); // 해당 업체코드의 휴일 리스트를 조회

				if (vendorlist.size() > 0) { // 해당 년도의 업체 휴일 데이터가 존재[UPDATE 대상]
					for (int i = 0; i < arrHolidayList.length; i++) {
						String strDate = arrHolidayList[i]; // "2016-07-21T21:30:47.492+0000";

						DateFormat df = new SimpleDateFormat("yyyyMMdd");
						Date date = df.parse(strDate);

						cmHoliday.setHldyYmd(UtilsDate.getSqlTimeStamp(date));
						cmHoliday.setHldyYn("Y");
						cmHoliday.setModerNo(user.getAdminNo()); // 수정자[관리자 번호] 세팅
						cmHolidayDao.updateHoliday(cmHoliday);
					}
				} else { // 해당 년도의 업체 휴일 데이터가 없음[INSERT 대상]
					List<CmHoliday> standardList = new ArrayList<CmHoliday>();

					cmHolidaySystem.setInsertYear(stYear);
					standardHolidayList = cmHolidayDao.selectStandardHolidayListAll(cmHolidaySystem); // 시스템 휴일 데이터 365개
																										// 데이터 조회
					int count = 0;

					for (int i = 0; i < standardHolidayList.size(); i++) {
						cmHoliday = standardHolidayList.get(i); // HLDY_YMD
						cmHoliday.setVndrNo(vendorArr[x]);
						cmHoliday.setHldyGbnType(Const.HOLIDAY_DEFAULT);
						cmHoliday.setRgsterNo(user.getAdminNo());
						String yyyyMMdd = UtilsDate.formatter("yyyyMMdd", UtilsDate.getDate(cmHoliday.getHldyYmd()));

						boolean isTure = holidayList.contains(yyyyMMdd);

						if (isTure) {
							cmHoliday.setHldyYn("Y");
						} else {
							cmHoliday.setHldyYn("N");
						}
						standardList.add(cmHoliday);
						count++;

						if (count % 50 == 0) {
							cmHolidayDao.insertHoliday(standardList);
							standardList = new ArrayList<CmHoliday>();
						}

					}

					cmHolidayDao.insertHoliday(standardList);
				}
			}

			// 공통휴일 코드에 대한 작업
			cmHolidaySystem = new CmHoliday();
			cmHolidaySystem.setYyyyMm(stYear);
			cmHolidaySystem.setHldyGbnType(Const.HOLIDAY_COMMON);
			vendorlist = cmHolidayDao.selectHolidayList(cmHolidaySystem);

			if (vendorlist.size() > 0) { // 공통 업체 휴일 데이터가 존재[UPDATE 대상]
				for (int i = 0; i < arrHolidayList.length; i++) {
					String strDate = arrHolidayList[i];

					DateFormat df = new SimpleDateFormat("yyyyMMdd");
					Date date = df.parse(strDate);

					cmHoliday.setHldyYmd(UtilsDate.getSqlTimeStamp(date));
					cmHoliday.setHldyYn("Y");
					cmHolidayDao.updateCommonHoliday(cmHoliday);
				}

			} else { // 해당 년도의 업체 휴일 데이터가 없음[INSERT 대상]
				List<CmHoliday> commonList = new ArrayList<CmHoliday>();

				cmHolidaySystem.setInsertYear(stYear);
				standardHolidayList = cmHolidayDao.selectStandardHolidayListAll(cmHolidaySystem); // 시스템 휴일 데이터 365개 데이터
																									// 조회
				int count = 0;

				for (int i = 0; i < standardHolidayList.size(); i++) {
					cmHoliday = standardHolidayList.get(i); // HLDY_YMD
					cmHoliday.setVndrNo(null);
					cmHoliday.setHldyGbnType(Const.HOLIDAY_COMMON);
					cmHoliday.setRgsterNo(user.getAdminNo());
					String yyyyMMdd = UtilsDate.formatter("yyyyMMdd", UtilsDate.getDate(cmHoliday.getHldyYmd()));

					boolean isTure = holidayList.contains(yyyyMMdd);

					if (isTure) {
						cmHoliday.setHldyYn("Y");
					} else {
						cmHoliday.setHldyYn("N");
					}
					commonList.add(cmHoliday);
					count++;

					if (count % 50 == 0) {
						cmHolidayDao.insertHoliday(commonList);
						commonList = new ArrayList<CmHoliday>();
					}
				}
				cmHolidayDao.insertHoliday(commonList);
			}
		}
	}

	/**
	 *
	 * @Desc : 그리드에서 휴일 여부 N으로 업데이트
	 * @Method Name : setUpdateHoliday
	 * @Date : 2019. 2. 20.
	 * @Author : choi
	 * @param cmHoliday
	 * @param cmHolidayArray
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> setUpdateHoliday(CmHoliday cmHoliday, CmHoliday[] cmHolidayArray) throws Exception {
		Map<String, Object> result = new HashMap<>();
		CmHoliday cmHolidayUpdate = new CmHoliday();
		UserDetails user = LoginManager.getUserDetails();
		String upAuthNo = user.getUpAuthNo();

		int delCount = 0; // 삭제 성공 개수

		for (int i = 0; i < cmHolidayArray.length; i++) { // 그리드 개수만큼 loop
			cmHolidayUpdate = cmHolidayArray[i];

			if (upAuthNo.equals(Const.ROLE_VENDER_GROUP)) { // 업체 관리자 그룹일 때
				cmHolidayUpdate.setIsVendor("true");
			} else {
				cmHolidayUpdate.setIsVendor("false");
			}

			cmHolidayUpdate.setVndrNo(user.getVndrNo());
			cmHolidayUpdate.setHldyYn("N");
			cmHolidayUpdate.setModerNo(user.getAdminNo());
			cmHolidayUpdate.setModerNo(user.getAdminNo()); // 수정자[관리자 번호] 세팅
			cmHolidayDao.updateHolidayGrid(cmHolidayUpdate);

			delCount += 1;
		}

		result.put("result", delCount);
		return result;
	}

	/**
	 *
	 * @Desc : 업체의 월별 휴일 정보를 조회한다.
	 * @Method Name : getHolidayDetailCalendarPop
	 * @Date : 2019. 2. 20.
	 * @Author : choi
	 * @param cmHoliday
	 * @return
	 * @throws Exception
	 */
	public List<CmHoliday> getHolidayDetailCalendarPop(CmHoliday cmHoliday) throws Exception {
		cmHoliday.setStandardHoliday(Const.HOLIDAY_SYSTEM);
		String vndrNo = cmHoliday.getVndrNo();

		if (vndrNo == null) { // 관리자 그룹일 때는 공통 휴일 정보를 보여준다.
			cmHoliday.setVndrNo(null);
			cmHoliday.setHldyGbnType(Const.HOLIDAY_COMMON);
		} else {
			cmHoliday.setVndrNo(vndrNo);
			cmHoliday.setHldyGbnType(Const.HOLIDAY_DEFAULT);
		}

		List<CmHoliday> dataList = cmHolidayDao.selectHolidayDetailCalendarPop(cmHoliday);
		return dataList;
	}

	/**
	 *
	 * @Desc : 주말 휴일 정보와 국가 공휴일 휴일 정보를 세팅한다.
	 * @Method Name : getHolidayDate
	 * @Date : 2019. 2. 20.
	 * @Author : choi
	 * @param cmHoliday
	 * @return
	 * @throws Exception
	 */
	public List<CmHoliday> getHolidayDate(CmHoliday cmHoliday) throws Exception {
		String yearStr = cmHoliday.getInsertYear();
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

		/*
		 * 휴일정보 List<String>로 자동으로 휴일정보를 세팅해주는 프로세스 시스템 휴일정보가 있는지 체크 L 없다면 휴일 정보[토/일요일
		 * 정보, api 국가 공휴일 정보]를 시스템 공휴일 정보로 insert 시스템 휴일 정보를 List<String>로 리턴
		 *
		 */
		cmHoliday.setYyyyMm(yearStr);

		List<CmHoliday> arrsystemHoliday = cmHolidayDao.selectStandardHolidayList(cmHoliday);

		if (arrsystemHoliday.size() < 1) {
			resultList = getHolidayInfoProcess(cmHoliday, yearStr, resultList);
			cmHolidayDao.insertsystemHolidayNo(resultList);
		}

		arrsystemHoliday = cmHolidayDao.selectStandardHolidayList(cmHoliday);
		return arrsystemHoliday;
	}

	/**
	 *
	 * @Desc : 365일 주말과 국가 공휴일 정보를 리턴한다.
	 * @Method Name : getHolidayInfoProcess
	 * @Date : 2019. 2. 20.
	 * @Author : choi
	 * @param cmHoliday
	 * @param year
	 * @param resultList
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getHolidayInfoProcess(CmHoliday cmHoliday, String year,
			List<Map<String, Object>> resultList) throws Exception {
		UserDetails user = LoginManager.getUserDetails();
		List<Map<String, Object>> weekendResultList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> apiResultList = new ArrayList<Map<String, Object>>();

		// 토요일,일요일 정보를 가져온다.
		UtilsDate.getYearDateList(Integer.parseInt(year), weekendResultList); // 해당 년도 365일 데이터 토요일, 일요일은 1, 이외의 요일은
																				// 0으로를 가져온다.

		// api를 통해서 국가공휴일 정보를 가져온다.
		getHolidayApi(year, apiResultList); // 해당 년도의 국가 공휴일 정보를 가져온다.

		for (int i = 0; i < weekendResultList.size(); i++) {
			Map<String, Object> weekendData = weekendResultList.get(i);
			Object weekendDate = weekendData.get("locdate"); // 20190101 형식의 365일 정보

			for (int j = 0; j < apiResultList.size(); j++) {
				Map<String, Object> apiHolidayData = apiResultList.get(j);
				Object apiHolidayDate = apiHolidayData.get("locdate"); // 20190101 형식의 국가 공휴일 정보

				if (weekendDate.equals(apiHolidayDate)) {
					weekendData.put("dateName", apiHolidayData.get("dateName"));
					weekendData.put("isHoliday", apiHolidayData.get("isHoliday"));
				}
			}
			weekendData.put("rgsterNo", user.getAdminNo());
			resultList.add(weekendData);
		}
		return resultList;
	}

	/**
	 *
	 * @Desc : 특일정보 api를 통해서 년도별 월별로 휴일 정보를 조회한다.
	 * @Method Name : getHolidayApi
	 * @Date : 2019. 2. 20.
	 * @Author : choi
	 * @param year
	 * @param resultList
	 * @throws Exception
	 */
	public void getHolidayApi(String year, List<Map<String, Object>> resultList) throws Exception {
		// <?xml version="1.0" encoding="UTF-8"
		// standalone="yes"?><response><header><resultCode>00</resultCode><resultMsg>NORMAL
		// SERVICE.</resultMsg></header><body><items><item><dateKind>01</dateKind><dateName>1월1일</dateName><isHoliday>Y</isHoliday><locdate>20190101</locdate><seq>1</seq></item></items><numOfRows>10</numOfRows><pageNo>1</pageNo><totalCount>1</totalCount></body></response>
		String holidayApiUrl = Config.getString("holiday.api.url");
		String holidayApiKey = Config.getString("holiday.api.key");
		String month = "";

		// loop S : 1~12월
		for (int i = 1; i <= 12; i++) {
			StringBuilder urlBuilder = new StringBuilder(holidayApiUrl); /* URL */
			month = ((i < 10) ? "0" + i : "" + i);

			urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + holidayApiKey); /* Service Key */
			urlBuilder.append(
					"&" + URLEncoder.encode("solYear", "UTF-8") + "=" + URLEncoder.encode(year, "UTF-8")); /* 연 */
			urlBuilder.append(
					"&" + URLEncoder.encode("solMonth", "UTF-8") + "=" + URLEncoder.encode(month, "UTF-8")); /* 월 */

			URL url = new URL(urlBuilder.toString());

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "application/json");

			BufferedReader rd;
			if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
				rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			} else {
				rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			}

			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = rd.readLine()) != null) {
				sb.append(line);
			}
			rd.close();
			conn.disconnect();
			// sb.toString()
			resultList = UtilsText.parseXmlToMap(resultList, sb.toString(), "item");
		}
		// loop E : 1~12월
	}

	/**
	 *
	 * @Desc : 올해, 10전년, 내년 정보 세팅
	 * @Method Name : getYearInfo
	 * @Date : 2019. 2. 20.
	 * @Author : choi
	 * @return
	 */
	public Map<String, Object> getYearInfo() {
		Map<String, Object> result = new HashMap<String, Object>();
		Calendar cal = Calendar.getInstance();

		cal.add(Calendar.YEAR, -10); // -10년
		result.put("beforeTenYear", String.valueOf(cal.get(Calendar.YEAR))); // 10년 전 세팅
		cal = Calendar.getInstance();
		result.put("thisYear", String.valueOf(cal.get(Calendar.YEAR))); // 올해
		cal.add(Calendar.YEAR, 1);
		result.put("nextYear", String.valueOf(cal.get(Calendar.YEAR))); // 내년
		result.put("thisMonth", String.valueOf(cal.get(Calendar.MONTH) + 1)); // 내년

		return result;
	}

	/**
	 *
	 * @Desc : 총괄 관리자, 업체 관리자별 업체 정보를 조회
	 * @Method Name : getVendorList
	 * @Date : 2019. 2. 20.
	 * @Author : choi
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getVendorList() throws Exception {
		UserDetails user = LoginManager.getUserDetails();
		String upAuthNo = user.getUpAuthNo();
		List<Map<String, Object>> vendorList = new ArrayList<Map<String, Object>>();
		Map<String, Object> data = new HashMap<String, Object>();

		VdVendor vdVendor = new VdVendor();
		vdVendor.setVndrGbnType("V");

		if (!upAuthNo.equals(Const.ROLE_ADMIN_GROUP)) {
			String vndrNo = (user.getVndrNo() == null) ? "" : user.getVndrNo();
			vdVendor.setVndrNo(vndrNo);// vndrNo
		}

		List<VdVendor> list = vdVendorDao.select(vdVendor);
		for (VdVendor vendorData : list) {
			if (!"10002".equals(vendorData.getVndrStatCode())) {
				data = new HashMap<String, Object>();
				data.put("vndrNo", vendorData.getVndrNo());
				data.put("vndrName", vendorData.getVndrName());
				vendorList.add(data);
			}
		}

		return vendorList;

	}

	/**
	 *
	 * @Desc : 입점사 등록 시 입점사의 휴일 데이터 select > insert함(시스템 휴일정보로 등록함)
	 * @Method Name : setSystemHolidayVndr
	 * @Date : 2019. 2. 20.
	 * @Author : choi
	 * @param cmHoliday
	 * @throws Exception
	 */
	public void setSystemHolidayVndr(CmHoliday cmHoliday) throws Exception {
		UserDetails user = LoginManager.getUserDetails();
		cmHoliday.setRgsterNo(user.getAdminNo());
		cmHoliday.setModerNo(user.getAdminNo());
		cmHoliday.setAuthType("V");

		if (cmHoliday.getYear() == null) { // 년도정보가 없다면 해당년도 세팅
			cmHoliday.setYear(UtilsDate.today("yyyy"));
		}
		cmHolidayDao.insertSystemHolidayVndr(cmHoliday);
	}
}
