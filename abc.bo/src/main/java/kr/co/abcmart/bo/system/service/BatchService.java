package kr.co.abcmart.bo.system.service;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.bo.system.model.master.SyJobInstance;
import kr.co.abcmart.bo.system.model.master.SySchedulerHistory;
import kr.co.abcmart.bo.system.model.master.SySchedulerHistoryLog;
import kr.co.abcmart.bo.system.model.master.SySite;
import kr.co.abcmart.bo.system.model.master.SyStepExecution;
import kr.co.abcmart.bo.system.model.master.SyTriggers;
import kr.co.abcmart.bo.system.repository.master.SyJobInstanceDao;
import kr.co.abcmart.bo.system.repository.master.SySchedulerHistoryDao;
import kr.co.abcmart.bo.system.repository.master.SySchedulerHistoryLogDao;
import kr.co.abcmart.bo.system.repository.master.SyStepExecutionDao;
import kr.co.abcmart.bo.system.repository.master.SyTriggersDao;
import kr.co.abcmart.bo.system.vo.ScheduleVO;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.util.UtilsSchedule;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.user.LoginManager;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 배치 서비스 클래스
 * @FileName : BatchService.java
 * @Project : abc.bo
 * @Date : 2019. 1. 31.
 * @Author : Kimyounghyun
 */
@Slf4j
@Service
public class BatchService {

	@Autowired
	private SiteService siteService;

	@Autowired
	CommonCodeService commonCodeService;

	@Autowired
	private SyTriggersDao syTriggersDao;

	@Autowired
	private SySchedulerHistoryDao sySchedulerHistoryDao;

	@Autowired
	private SySchedulerHistoryLogDao sySchedulerHistoryLogDao;

	@Autowired
	private SyJobInstanceDao syJobInstanceDao;

	@Autowired
	private SyStepExecutionDao syStepExecutionDao;

	/**
	 * @Desc : 일정 목록을 페이징용으로 조회한다.
	 * @Method Name : getTroggerJoinForPaging
	 * @Date : 2019. 1. 31.
	 * @Author : Kimyounghyun
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<SyTriggers> getTriggerJoinForPaging(Pageable<ScheduleVO, SyTriggers> pageable) throws Exception {
		int count = syTriggersDao.selectTriggerJoinForPagingCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(syTriggersDao.selectTriggerJoinForPaging(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 일정 실행 정보를 페이징으로 조회한다.
	 * @Method Name : getHistoryForPaging
	 * @Date : 2019. 2. 1.
	 * @Author : Kimyounghyun
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<SySchedulerHistory> getHistoryForPaging(Pageable<ScheduleVO, SySchedulerHistory> pageable)
			throws Exception {
		genSchedId(pageable.getBean());

		int count = sySchedulerHistoryDao.selectHistoryForPagingCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(sySchedulerHistoryDao.selectHistoryForPaging(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 일정 실행 로그를 페이징으로 조회한다.
	 * @Method Name : getHistoryLogForPaging
	 * @Date : 2019. 2. 1.
	 * @Author : Kimyounghyun
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<SySchedulerHistoryLog> getHistoryLogForPaging(Pageable<ScheduleVO, SySchedulerHistoryLog> pageable)
			throws Exception {
		genSchedId(pageable.getBean());

		int count = sySchedulerHistoryLogDao.selectHistoryLogForPagingCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(sySchedulerHistoryLogDao.selectHistoryLogForPaging(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 배치 실행정보를 페이징으로 조회한다.
	 * @Method Name : getJobInstanceForPaging
	 * @Date : 2019. 2. 7.
	 * @Author : Kimyounghyun
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<SyJobInstance> getBatchJobInstanceForPaging(Pageable<ScheduleVO, SyJobInstance> pageable)
			throws Exception {

		int count = syJobInstanceDao.selectJobInstanceForPagingCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(syJobInstanceDao.selectJobInstanceForPaging(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 배치 단계별 실행정보를 페이징으로 조회한다.
	 * @Method Name : getBatchStepExecutionForPaging
	 * @Date : 2019. 2. 7.
	 * @Author : Kimyounghyun
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<SyStepExecution> getBatchStepExecutionForPaging(Pageable<ScheduleVO, SyStepExecution> pageable)
			throws Exception {
		int count = syStepExecutionDao.selectBatchStepExecutionForPagingCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(syStepExecutionDao.selectBatchStepExecutionForPaging(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 스케줄러 아이디를 생성한다. 사이트아이디^트리거명^트리거그룹명
	 * @Method Name : genSchedId
	 * @Date : 2019. 2. 7.
	 * @Author : Kimyounghyun
	 * @param scheduleVO
	 * @throws Exception
	 */
	private void genSchedId(ScheduleVO scheduleVO) throws Exception {
		if (UtilsText.isBlank(scheduleVO.getSchedId())) {
			String schedId = UtilsSchedule.genSchedId(scheduleVO.getSiteId(), scheduleVO.getTriggerName(),
					scheduleVO.getTriggerGroup());

			scheduleVO.setSchedId(schedId);
		}

	}

	/**
	 * @Desc : 배치 관련된 공통코드 조회
	 * @Method Name : getCommonCode
	 * @Date : 2019. 2. 8.
	 * @Author : Kimyounghyun
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public List<SyCodeDetail> getCommonCode(String code) throws Exception {
		return commonCodeService.getCodeNoName(code);
	}

	/**
	 * @Desc : ScheduleVO[] 사용자 정보 등 필요한 값을 설정한다.
	 * @Method Name : setSchedule
	 * @Date : 2019. 2. 22.
	 * @Author : Kimyounghyun
	 * @param scheduleVOs
	 * @return
	 */
	public ScheduleVO[] setSchedule(ScheduleVO[] scheduleVOs) {
		for (ScheduleVO scheduleVO : scheduleVOs) {
			scheduleVO.setUserId(LoginManager.getUserDetails().getAdminNo());
		}

		return scheduleVOs;
	}

	/**
	 * @Desc : ibsheet grig에서 사용하기 위해 사이트를 조회한다.
	 * @Method Name : getSiteListCombo
	 * @Date : 2019. 2. 22.
	 * @Author : Kimyounghyun
	 * @return
	 * @throws Exception
	 */
	public Pair<JSONObject, List<SySite>> getSiteListCombo() throws Exception {
		return siteService.getSiteListByCombo();
	}

	/**
	 * @Desc : ibsheet grig에서 사용하기 위해 사용중인 공통코드를 조회한다.
	 * @Method Name : getCommonCodeCombo
	 * @Date : 2019. 2. 22.
	 * @Author : Kimyounghyun
	 * @param jobGroup
	 * @return
	 * @throws Exception
	 */
	public Pair<JSONObject, Map<String, List<SyCodeDetail>>> getCommonCodeCombo(String[] codeFields) throws Exception {
		return commonCodeService.getCodeListByGroupByCombo(codeFields, true);
	}

}
