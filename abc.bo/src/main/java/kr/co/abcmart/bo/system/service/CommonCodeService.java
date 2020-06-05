package kr.co.abcmart.bo.system.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.system.model.master.SyCode;
import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.bo.system.repository.master.SyCodeDao;
import kr.co.abcmart.bo.system.repository.master.SyCodeDetailDao;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;

@Service
public class CommonCodeService {

	@Autowired
	private SyCodeDao syCodeDao;

	@Autowired
	private SyCodeDetailDao syCodeDetailDao;

	@Autowired
	private CommonCodeService commonCodeService;

	/**
	 * @Desc : 코드그룹 리스트를 조회한다.
	 * @Method Name : getCodeGroup
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<SyCode> getCodeGroup(Pageable<SyCode, SyCode> pageable) throws Exception {
		pageable.setTotalCount(syCodeDao.selectCodeListCount(pageable));
		pageable.setContent(syCodeDao.selectCodeGroup(pageable));

		return pageable.getPage();

	}

	/**
	 * @Desc : 하위코드 등록시에 상위코드 목록 조회한다.
	 * @Method Name : getUpCodeList
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<SyCodeDetail> getUpCodeList(SyCodeDetail params) throws Exception {
		List<SyCodeDetail> data = syCodeDetailDao.selectUpCodeList(params);
		return data;

	}

	/**
	 * @Desc : 상위,하위코드 리스트 그리드조회
	 * @Method Name : getUpDownCode
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<SyCodeDetail> getUpDownCode(Pageable<SyCodeDetail, SyCodeDetail> pageable) throws Exception {
		pageable.setTotalCount(syCodeDetailDao.selectCodeDetailListCount(pageable));
		pageable.setContent(syCodeDetailDao.selectUpDownCode(pageable));

		return pageable.getPage();

	}

	/**
	 * @Desc : 코드그룹을 상세 조회한다.
	 * @Method Name : getGroupDetail
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<SyCode> getGroupDetail(SyCode params) throws Exception {

		return syCodeDao.selectGroupDetail(params);

	}

	/**
	 * @Desc : 코드그룹 삭제시에 상위,하위코드들을 가지고 있는지 체크.
	 * @Method Name : getRemoveCheck
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int getRemoveCheck(SyCodeDetail params) throws Exception {
		int count = syCodeDetailDao.selectRemoveCheck(params);

		return count;
	}

	/**
	 * @Desc : 삭제시에 하위 코드 가지는지 체크
	 * @Method Name : getRemoveUpCodeCheck
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int getRemoveUpCodeCheck(SyCodeDetail params) throws Exception {
		int count = syCodeDetailDao.selectRemoveUpCodeCheck(params);

		return count;
	}

	/**
	 * @Desc : 코드그룹을 수정한다.
	 * @Method Name : updateCodeGroup
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param entity
	 * @throws Exception
	 */
	public void updateCodeGroup(SyCode entity) throws Exception {
		entity.setCodeName(UtilsText.unescapeXss(entity.getCodeName()));
		
		UserDetails user = LoginManager.getUserDetails();
		entity.setModerNo(user.getAdminNo());

		syCodeDao.updateCodeGroup(entity);
	}

	/**
	 * @Desc : 코드필드 중복 체크
	 * @Method Name : checkOverlapCodefield
	 * @Date : 2019. 9. 27.
	 * @Author : sic
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public int checkOverlapCodefield(SyCode entity) throws Exception {
		return syCodeDao.selectOverlapCodefield(entity);

	}

	/**
	 * @Desc : 상위, 하위코드 상세를 수정한다.
	 * @Method Name : updateUpDownCodeDetail
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param syCodeDetail
	 * @throws Exception
	 */
	public void updateUpDownCodeDetail(SyCodeDetail syCodeDetail) throws Exception {
		syCodeDetail.setCodeDtlName(UtilsText.unescapeXss(syCodeDetail.getCodeDtlName()));
		
		UserDetails user = LoginManager.getUserDetails();
		syCodeDetail.setModerNo(user.getAdminNo());

		syCodeDetailDao.updateUpDownCodeDetail(syCodeDetail);

	}

	/**
	 * @Desc : 코드그룹을 등록한다.
	 * @Method Name : insertCodeGroup
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param syCode
	 * @throws Exception
	 */
	public void insertCodeGroup(SyCode syCode) throws Exception {
		syCode.setCodeName(UtilsText.unescapeXss(syCode.getCodeName()));
		
		UserDetails user = LoginManager.getUserDetails();
		syCode.setRgsterNo(user.getAdminNo());
		syCode.setModerNo(user.getAdminNo());

		syCodeDao.insertCodeGroup(syCode);
	}

	/**
	 * @Desc : 코드그룹을 삭제한다.
	 * @Method Name : deleteCodeGroup
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param params
	 * @throws Exception
	 */
	public void deleteCodeGroup(SyCode params) throws Exception {
		syCodeDao.deleteCodeGroup(params);

	}

	/**
	 * @Desc : 상위,하위 코드 삭제
	 * @Method Name : deleteUpDownCode
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param params
	 * @throws Exception
	 */
	public void deleteUpDownCode(SyCodeDetail params) throws Exception {
		syCodeDetailDao.deleteUpDownCode(params);

	}

	/**
	 * @Desc : 상위코드를 등록한다.
	 * @Method Name : insertUpCodeDetail
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param syCodeDetail
	 * @throws Exception
	 */
	public void insertUpCodeDetail(SyCodeDetail syCodeDetail) throws Exception {
		syCodeDetail.setCodeDtlName(UtilsText.unescapeXss(syCodeDetail.getCodeDtlName()));
		
		UserDetails user = LoginManager.getUserDetails();
		syCodeDetail.setRgsterNo(user.getAdminNo());
		syCodeDetail.setModerNo(user.getAdminNo());

		syCodeDetailDao.insertUpCodeDetail(syCodeDetail);

	}

	/**
	 * @Desc : 하위코드를 등록한다.
	 * @Method Name : insertDownCodeDetail
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param syCodeDetail
	 * @throws Exception
	 */
	public void insertDownCodeDetail(SyCodeDetail syCodeDetail) throws Exception {
		syCodeDetail.setCodeDtlName(UtilsText.unescapeXss(syCodeDetail.getCodeDtlName()));
		
		UserDetails user = LoginManager.getUserDetails();
		syCodeDetail.setRgsterNo(user.getAdminNo());
		syCodeDetail.setModerNo(user.getAdminNo());

		syCodeDetailDao.insertDownCodeDetail(syCodeDetail);
	}

	/**
	 * @Desc : 상위,하위코드 상세를 검색한다.
	 * @Method Name : getUpDownDetail
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param syCodeDetail
	 * @return
	 * @throws Exception
	 */
	public SyCodeDetail getUpDownDetail(SyCodeDetail syCodeDetail) throws Exception {
		return syCodeDetailDao.selectUpDownDetail(syCodeDetail);
	}

	/**
	 * @Desc : 상위,하위 코드 수정한다.
	 * @Method Name : updateUpDownCodeGrid
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public void updateUpDownCodeGrid(SyCodeDetail[] entity) throws Exception {
		UserDetails user = LoginManager.getUserDetails();
		for (SyCodeDetail syCodeDetail : entity) {
			if (UtilsText.isNotBlank(syCodeDetail.getUpCodeDtlName())) {
				syCodeDetail.setCodeDtlName(syCodeDetail.getUpCodeDtlName());
			}
			syCodeDetail.setCodeDtlName(UtilsText.unescapeXss(syCodeDetail.getCodeDtlName()));
			syCodeDetail.setUpCodeDtlName(null);
			syCodeDetail.setModerNo(user.getAdminNo());

			syCodeDetailDao.updateUpDownCodeGrid(syCodeDetail);
		}
	}

	/**
	 * @Desc : 상위코드 name을 바꿔주고 공백으로 만들어줌.
	 * @Method Name : upDownCheck
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param syCodeDetail
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> upDownCheck(SyCodeDetail syCodeDetail) throws Exception {

		if (UtilsText.isNotBlank(syCodeDetail.getUpCodeDtlName())) {
			syCodeDetail.setCodeDtlName(syCodeDetail.getUpCodeDtlName());
		}
		syCodeDetail.setUpCodeDtlName("");
		syCodeDetail = commonCodeService.getUpDownDetail(syCodeDetail);

		Map<String, Object> codeMap = new HashMap<>();
		String path = "system/commoncode/create-upcode-form-pop";

		if (UtilsText.isNotBlank(syCodeDetail.getUpCodeDtlNo())) {
			path = "system/commoncode/create-downcode-form-pop";
			codeMap.put("downCodeList", syCodeDetailDao.selectUpCodeList(syCodeDetail));
		}
		codeMap.put("syCodeDetail", syCodeDetail);
		codeMap.put("path", path);

		return codeMap;
	}

	/**
	 * @Desc : codeField 와 addinfo로 코드 번호와 이름 조회
	 * @Method Name : getCodeDtlInfoList
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param syCodeDetail
	 * @return
	 * @throws Exception
	 */
	public List<SyCodeDetail> getCodeDtlInfoList(SyCodeDetail syCodeDetail) throws Exception {
		return syCodeDetailDao.selectCodeDtlInfoList(syCodeDetail);
	}

	/**
	 * @Desc : String 코드필드 값으로 코드 no와 name을 가지고 온다. (사용중인 코드만 조회)
	 * @Method Name : getCodeNoName
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param codeField
	 * @return
	 * @throws Exception
	 */
	public List<SyCodeDetail> getCodeNoName(String codeField) throws Exception {
		SyCodeDetail syCodeDetail = new SyCodeDetail();

		syCodeDetail.setCodeField(codeField);
		syCodeDetail.setUseYn(Const.BOOLEAN_TRUE);

		return getCodeDtlInfoList(syCodeDetail);
	}

	/**
	 * @Desc : 사용중이지 않은 코드들도 조회함
	 * @Method Name : getAllCodeNoName
	 * @Date : 2019. 2. 12.
	 * @Author : 신인철
	 * @param codeField
	 * @return
	 * @throws Exception
	 */
	public List<SyCodeDetail> getAllCodeNoName(String codeField) throws Exception {
		SyCodeDetail syCodeDetail = new SyCodeDetail();

		syCodeDetail.setCodeField(codeField);

		return getCodeDtlInfoList(syCodeDetail);
	}

	/**
	 * @Desc : String 코드필드, add_info1 값으로 코드 no와 name을 가지고 온다.
	 * @Method Name : getCodeNoNameFilterAddInfo
	 * @Date : 2019. 2. 25.
	 * @Author : KTH
	 * @param codeField
	 * @param addInfoField
	 * @return
	 * @throws Exception
	 */
	public List<SyCodeDetail> getCodeNoNameFilterAddInfo(String codeField, String addInfoField) throws Exception {
		SyCodeDetail syCodeDetail = new SyCodeDetail();

		syCodeDetail.setCodeField(codeField);
		syCodeDetail.setUseYn("Y");
		syCodeDetail.setAddInfo1(addInfoField);

		return getCodeDtlInfoList(syCodeDetail);
	}

	/**
	 * @Desc : String 코드필드, add_info1 값으로 코드 no와 name을 가지고 온다(사용중이지 않은 코드 포함)
	 * @Method Name : getAllCodeNoNameFilterAddInfo
	 * @Date : 2019. 2. 25.
	 * @Author : KTH
	 * @param codeField
	 * @param addInfoField
	 * @return
	 * @throws Exception
	 */
	public List<SyCodeDetail> getAllCodeNoNameFilterAddInfo(String codeField, String addInfoField) throws Exception {
		SyCodeDetail syCodeDetail = new SyCodeDetail();

		syCodeDetail.setCodeField(codeField);

		return getCodeDtlInfoList(syCodeDetail);
	}

	/**
	 * @Desc : 여러개의 코드정보를 가져올때 이용
	 * @Method Name : getCodeListByGroup
	 * @Date : 2019. 2. 1.
	 * @Author : 유성민
	 * @param codeFields
	 * @return
	 */
	public Map<String, List<SyCodeDetail>> getCodeListByGroup(String[] codeFields) throws Exception {
		List<SyCodeDetail> codeList = syCodeDetailDao.selectCodeListByGroup(codeFields);
		if (codeList == null) {
			return null;
		}
		return codeList.stream().collect(Collectors.groupingBy(SyCodeDetail::getCodeField));
	}

	/**
	 * @Desc : 여러개의 코드 조회시 사용(use_yn, addinfo1..... 조건 추가 가능)
	 * @Method Name : getCodeListByGroup
	 * @Date : 2019. 3. 28.
	 * @Author : kiowa
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public Map<String, List<SyCodeDetail>> getCodeListByGroup(List<SyCodeDetail> list) throws Exception {
		List<SyCodeDetail> codeList = syCodeDetailDao.selectSysCodeDetialList(list);
		if (codeList == null) {
			return null;
		}
		return codeList.stream().collect(Collectors.groupingBy(SyCodeDetail::getCodeField));
	}

	/**
	 * @Desc : 사용중이지 않은 코드들도 함께 조회
	 * @Method Name : getAllCodeListByGroup
	 * @Date : 2019. 2. 12.
	 * @Author : 신인철
	 * @param codeFields
	 * @return
	 * @throws Exception
	 */
	public Map<String, List<SyCodeDetail>> getAllCodeListByGroup(String[] codeFields) throws Exception {
		List<SyCodeDetail> codeList = syCodeDetailDao.selectAllCodeListByGroup(codeFields);
		if (codeList == null) {
			return null;
		}
		return codeList.stream().collect(Collectors.groupingBy(SyCodeDetail::getCodeField));
	}

	/**
	 * @Desc : 코드 데이터 조회 & grid combo code 조회
	 * @Method Name : getCodeListByGroupByCombo
	 * @Date : 2019. 2. 15.
	 * @Author : 신인철
	 * @param codeFields
	 * @param isUse      true- 사용중인 코드 , false - all
	 * @return
	 * @throws Exception
	 */
	public Pair<JSONObject, Map<String, List<SyCodeDetail>>> getCodeListByGroupByCombo(String[] codeFields,
			boolean isUse) throws Exception {

		Map<String, List<SyCodeDetail>> codeList = getCodeListGroupByUse(codeFields, isUse); // 사용 여부에 따른 코드그룹 목록

		return getPairCodeListGroup(codeFields, codeList); // delimiter로 구분된 json 코드 문자열 및 코드리스트 리턴
	}

	/**
	 * @Desc : 코드 데이터 조회 & grid combo code 조회(공통코드 add_info1 값을 조건으로 추출한 코드리스트를 다시
	 *       set)
	 * @Method Name : getCodeListByGroupByComboByAddInfo
	 * @Date : 2019. 2. 21.
	 * @Author : KTH
	 * @param String[] codeFields
	 * @param String[] addInfoFields
	 * @param boolean isUse
	 * @return Pair<JSONObject, Map<String, List<SyCodeDetail>>>
	 * @throws Exception
	 */
	public Pair<JSONObject, Map<String, List<SyCodeDetail>>> getCodeListByGroupByComboFilterAddInfo(String[] codeFields,
			String[] addInfoFields, boolean isUse) throws Exception {

		Map<String, List<SyCodeDetail>> codeList = getCodeListGroupByUse(codeFields, isUse); // 사용 여부에 따른 코드그룹 목록

		// add_info1 값이 넘어온 경우 추출한 코드리스트를 해당 값으로 필터링하여 다시 set
		for (int i = 0; i < codeFields.length; i++) {
			if (UtilsText.isNotEmpty(addInfoFields[i])) {
				String addInfoFieldsText = addInfoFields[i].toString();

				codeList.put(codeFields[i].toString(),
						codeList.get(codeFields[i].toString()).stream()
								.filter(x -> UtilsText.equals(x.getAddInfo1(), addInfoFieldsText))
								.collect(Collectors.toList()));
			}
		}

		return getPairCodeListGroup(codeFields, codeList); // delimiter로 구분된 json 코드 문자열 및 코드리스트 리턴
	}

	/**
	 * @Desc : 사용 여부에 따른 코드그룹 목록
	 * @Method Name : getCodeListGroupByUse
	 * @Date : 2019. 2. 21.
	 * @Author : KTH
	 * @param String[] codeFields
	 * @param boolean isUse
	 * @return Map<String, List<SyCodeDetail>>
	 * @throws Exception
	 */
	public Map<String, List<SyCodeDetail>> getCodeListGroupByUse(String[] codeFields, boolean isUse) throws Exception {
		Map<String, List<SyCodeDetail>> codeList = new HashMap<String, List<SyCodeDetail>>();

		if (isUse) {
			codeList = commonCodeService.getCodeListByGroup(codeFields);
		} else {
			codeList = commonCodeService.getAllCodeListByGroup(codeFields);
		}

		return codeList;
	}

	/**
	 * @Desc : delimiter로 구분된 json 코드 문자열 및 코드리스트
	 * @Method Name : getPairCodeListGroup
	 * @Date : 2019. 2. 21.
	 * @Author : KTH
	 * @param String[] codeFields
	 * @param Map<String, List<SyCodeDetail>> codeList
	 * @return Pair<JSONObject, Map<String, List<SyCodeDetail>>>
	 * @throws Exception
	 */
	public Pair<JSONObject, Map<String, List<SyCodeDetail>>> getPairCodeListGroup(String[] codeFields,
			Map<String, List<SyCodeDetail>> codeList) throws Exception {

		JSONObject fieldJson = new JSONObject();
		for (int i = 0; i < codeFields.length; i++) {
			List<SyCodeDetail> list = codeList.get(codeFields[i].toString());

			String codeName = list.stream().map(SyCodeDetail::getCodeDtlName).collect(Collectors.joining("|"));
			String codeValue = list.stream().map(SyCodeDetail::getCodeDtlNo).collect(Collectors.joining("|"));

			JSONObject json = new JSONObject();
			json.put("code", codeValue);
			json.put("text", codeName);

			fieldJson.put(codeFields[i].toString(), json);
		}

		return Pair.of(fieldJson, codeList);
	}

	/**
	 * @Desc : 코드그룹 등록할시에 정렬순서 체크
	 * @Method Name : selectMaxSortSeq
	 * @Date : 2019. 8. 7.
	 * @Author :
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> selectMaxSortSeq() throws Exception {
		Map<String, Object> rsMap = new HashMap<>();
		try {
			int maxSortSeq = syCodeDao.selectMaxSortSeq();

			rsMap.put("result", 1);
			rsMap.put("maxSortSeq", maxSortSeq);
		} catch (Exception e) {
			rsMap.put("result", 0);
			rsMap.put("Message", e.toString());
		}
		return rsMap;
	}

	/**
	 * @Desc : 코드필드와 addInfo조회로 단일객체 반환
	 * @Method Name : getCodeDetailByFiledAddInfo
	 * @Date : 2019. 9. 27.
	 * @Author : sic
	 * @param syCodeDetail
	 * @return
	 * @throws Exception
	 */
	public SyCodeDetail getCodeDetailByFiledAddInfo(SyCodeDetail syCodeDetail) throws Exception {
		return syCodeDetailDao.selectCodeDetailByFiledAddInfo(syCodeDetail);
	}

}
