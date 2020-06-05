/**
 * 
 */
package kr.co.abcmart.bo.stats.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.stats.model.master.SaMemberLeaveStatus;
import kr.co.abcmart.bo.stats.repository.master.SaMemberLeaveStatusDao;
import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.bo.system.service.CommonCodeService;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MemberLeaveStatusService {

	@Autowired
	CommonCodeService codeService;

	@Autowired
	SaMemberLeaveStatusDao leaveDao;

	/**
	 * @Desc : 탈퇴사유 통꼐 화면
	 * @Method Name : getLeaveMain
	 * @Date : 2019. 6. 21.
	 * @Author : 신인철
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getLeaveMain() throws Exception {
		Map<String, Object> leaveMap = new HashMap<String, Object>();

		SyCodeDetail codeParam = new SyCodeDetail();

		codeParam.setCodeField(CommonCode.MEMBER_TYPE_CODE);
		codeParam.setUseYn(Const.BOOLEAN_TRUE);

		List<SyCodeDetail> memberTypeCode = codeService.getCodeDtlInfoList(codeParam);

		leaveMap.put("memberTypeCode", memberTypeCode);

		return leaveMap;
	}

	/**
	 * @Desc : 탈퇴 사유 통계 그리드 조회
	 * @Method Name : getLeaveGrid
	 * @Date : 2019. 6. 21.
	 * @Author : 신인철
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<SaMemberLeaveStatus> getLeaveGrid(Pageable<SaMemberLeaveStatus, SaMemberLeaveStatus> pageable)
			throws Exception {
		int totalCount = leaveDao.selectLeaveGridCount(pageable);
		pageable.setTotalCount(totalCount);
		log.debug(">>>>>>>>" + totalCount);
		if (totalCount > 0) {
			pageable.setContent(leaveDao.selectLeaveGrid(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 엑셀 다운로드할 리스트 조회
	 * @Method Name : getLeaveExcelList
	 * @Date : 2019. 6. 21.
	 * @Author : 신인철
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<SaMemberLeaveStatus> getLeaveExcelList(SaMemberLeaveStatus param) throws Exception {
		List<SaMemberLeaveStatus> list = leaveDao.selectExcelList(param);
		return list;
	}

}
