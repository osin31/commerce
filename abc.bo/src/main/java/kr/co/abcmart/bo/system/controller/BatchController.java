package kr.co.abcmart.bo.system.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.bo.system.model.master.SyJobInstance;
import kr.co.abcmart.bo.system.model.master.SySchedulerHistory;
import kr.co.abcmart.bo.system.model.master.SySchedulerHistoryLog;
import kr.co.abcmart.bo.system.model.master.SySite;
import kr.co.abcmart.bo.system.model.master.SyStepExecution;
import kr.co.abcmart.bo.system.model.master.SyTriggers;
import kr.co.abcmart.bo.system.service.BatchService;
import kr.co.abcmart.bo.system.vo.ScheduleVO;
import kr.co.abcmart.common.client.ClientParameter;
import kr.co.abcmart.common.client.NetResult;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.common.util.UtilsNet;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("system/batch")
public class BatchController extends BaseController {

	@Autowired
	BatchService service;

	/**
	 * @Desc : 배치 메인 페이지
	 * @Method Name : main
	 * @Date : 2019. 1. 30.
	 * @Author : Kimyounghyun
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("")
	public ModelAndView main(Parameter<?> parameter) throws Exception {
		String[] codeFields = { CommonCode.JOB_GROUP };

		Pair<JSONObject, List<SySite>> sitePair = service.getSiteListCombo();
		Pair<JSONObject, Map<String, List<SyCodeDetail>>> codePair = service.getCommonCodeCombo(codeFields);

		parameter.addAttribute("siteCombo", sitePair.getFirst());
		parameter.addAttribute("codeCombo", codePair.getFirst());

		return forward("system/batch/batch-main");
	}

	/**
	 * @Desc : 일정 목록을 조회한다.
	 * @Method Name : list
	 * @Date : 2019. 1. 30.
	 * @Author : Kimyounghyun
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("list")
	public void list(Parameter<ScheduleVO> parameter) throws Exception {
		Pageable<ScheduleVO, SyTriggers> pageable = new Pageable<>(parameter);
		Page<SyTriggers> page = service.getTriggerJoinForPaging(pageable);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 일정 실행 정보 조회.
	 * @Method Name : history
	 * @Date : 2019. 1. 30.
	 * @Author : Kimyounghyun
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("history")
	public void history(Parameter<ScheduleVO> parameter) throws Exception {
		Pageable<ScheduleVO, SySchedulerHistory> pageable = new Pageable<>(parameter);
		Page<SySchedulerHistory> page = service.getHistoryForPaging(pageable);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 일정 실행 로그 조회
	 * @Method Name : historyLog
	 * @Date : 2019. 1. 30.
	 * @Author : Kimyounghyun
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("history-log")
	public void historyLog(Parameter<ScheduleVO> parameter) throws Exception {
		Pageable<ScheduleVO, SySchedulerHistoryLog> pageable = new Pageable<>(parameter);
		Page<SySchedulerHistoryLog> page = service.getHistoryLogForPaging(pageable);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 배치 실행 정보 조회
	 * @Method Name : searchBatchJobInstance
	 * @Date : 2019. 1. 30.
	 * @Author : Kimyounghyun
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("job-instance")
	public void jobInstance(Parameter<ScheduleVO> parameter) throws Exception {
		Pageable<ScheduleVO, SyJobInstance> pageable = new Pageable<>(parameter);
		Page<SyJobInstance> page = service.getBatchJobInstanceForPaging(pageable);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 배치 실행 단계별 조회
	 * @Method Name : stepExecution
	 * @Date : 2019. 1. 30.
	 * @Author : Kimyounghyun
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("step-execution")
	public void stepExecution(Parameter<ScheduleVO> parameter) throws Exception {
		Pageable<ScheduleVO, SyStepExecution> pageable = new Pageable<>(parameter);
		Page<SyStepExecution> page = service.getBatchStepExecutionForPaging(pageable);

		writeJson(parameter, page.getGrid());
	}

	/*
	 * 미사용
	 */
	@PostMapping("save-job-popup")
	public ModelAndView saveJobPopup(Parameter<?> parameter) throws Exception {
		parameter.addAttribute("siteList", service.getSiteListCombo().getSecond());
		parameter.addAttribute("jobGroupList", service.getCommonCode(CommonCode.JOB_GROUP));

		return forward("system/batch/create-job-popup");
	}

	/*
	 * 미사용
	 */
	@PostMapping("save-job")
	public void saveJob(Parameter<ScheduleVO[]> parameter) throws Exception {
		log.debug("parameter.get() {} ", parameter);

		String url = UtilsText.concat(Const.BATCH_SERVER_URL, "/schedule/save-job");

		ClientParameter restParameter = ClientParameter.builder().target(url, HashMap.class)
				.parameterObject(parameter.get()).build();

		@SuppressWarnings("rawtypes")
		NetResult<HashMap> rest = UtilsNet.send(restParameter);
		resultHandler(rest);

		writeJson(parameter, rest);
	}

	/**
	 * @Desc : 배치업무일정등록 팝업
	 * @Method Name : saveSchedulePopup
	 * @Date : 2019. 2. 7.
	 * @Author : Kimyounghyun
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save-trigger-popup")
	public ModelAndView saveSchedulePopup(Parameter<?> parameter) throws Exception {
		parameter.addAttribute("siteList", service.getSiteListCombo().getSecond());
		parameter.addAttribute("jobGroupList", service.getCommonCode(CommonCode.JOB_GROUP));

		return forward("system/batch/create-trigger-popup");
	}

	/**
	 * @Desc : 배치 업무 일정 저장
	 * @Method Name : saveTrigger
	 * @Date : 2019. 1. 30.
	 * @Author : Kimyounghyun
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("save-trigger")
	public void saveTrigger(Parameter<ScheduleVO[]> parameter) throws Exception {
		String url = UtilsText.concat(Const.BATCH_SERVER_URL, "/schedule/save-trigger");

		ClientParameter restParameter = ClientParameter.builder().target(url, HashMap.class)
				.parameterObject(parameter.get()).build();

		@SuppressWarnings("rawtypes")
		NetResult<HashMap> rest = UtilsNet.send(restParameter);
		resultHandler(rest);

		writeJson(parameter, rest);
	}

	/**
	 * @Desc : 중지된 배치 일정 재시작
	 * @Method Name : resumeTrigger
	 * @Date : 2019. 1. 30.
	 * @Author : Kimyounghyun
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("resume-trigger")
	public void resumeTrigger(Parameter<ScheduleVO[]> parameter) throws Exception {
		String url = UtilsText.concat(Const.BATCH_SERVER_URL, "/schedule/resume-trigger");

		ClientParameter restParameter = ClientParameter.builder().target(url, HashMap.class)
				.parameterObject(parameter.get()).build();

		@SuppressWarnings("rawtypes")
		NetResult<HashMap> rest = UtilsNet.send(restParameter);
		resultHandler(rest);

		writeJson(parameter, rest);
	}

	/**
	 * @Desc : 배치 일정 멈춤
	 * @Method Name : pauseTrigger
	 * @Date : 2019. 1. 30.
	 * @Author : Kimyounghyun
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("pause-trigger")
	public void pauseTrigger(Parameter<ScheduleVO[]> parameter) throws Exception {
		String url = UtilsText.concat(Const.BATCH_SERVER_URL, "/schedule/pause-trigger");

		ClientParameter restParameter = ClientParameter.builder().target(url, HashMap.class)
				.parameterObject(parameter.get()).build();

		@SuppressWarnings("rawtypes")
		NetResult<HashMap> rest = UtilsNet.send(restParameter);
		resultHandler(rest);

		writeJson(parameter, rest);
	}

	/**
	 * @Desc : 배치 즉시 실행
	 * @Method Name : executeJob
	 * @Date : 2019. 1. 30.
	 * @Author : Kimyounghyun
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("execute-job")
	public void executeJob(Parameter<ScheduleVO[]> parameter) throws Exception {
		String url = UtilsText.concat(Const.BATCH_SERVER_URL, "/schedule/execute-job");

		ClientParameter restParameter = ClientParameter.builder().target(url, HashMap.class)
				.parameterObject(parameter.get()).build();

		@SuppressWarnings("rawtypes")
		NetResult<HashMap> rest = UtilsNet.send(restParameter);
		resultHandler(rest);

		writeJson(parameter, rest);
	}

	/**
	 * @Desc : 배치 일정 제거
	 * @Method Name : removeTrigger
	 * @Date : 2019. 1. 30.
	 * @Author : Kimyounghyun
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("remove-trigger")
	public void removeTrigger(Parameter<ScheduleVO[]> parameter) throws Exception {
		String url = UtilsText.concat(Const.BATCH_SERVER_URL, "/schedule/remove-trigger");

		ClientParameter restParameter = ClientParameter.builder().target(url, HashMap.class)
				.parameterObject(parameter.get()).build();

		@SuppressWarnings("rawtypes")
		NetResult<HashMap> rest = UtilsNet.send(restParameter);
		resultHandler(rest);

		writeJson(parameter, rest);
	}

	/**
	 * @Desc : 배치서버와 통신결과가 성공이 아니면 예외처리.
	 * @Method Name : resultHandler
	 * @Date : 2019. 2. 27.
	 * @Author : Kimyounghyun
	 * @param rest
	 */
	@SuppressWarnings("rawtypes")
	private void resultHandler(NetResult<HashMap> rest) throws Exception {
		if (HttpStatus.SC_OK != rest.getStatus()) {
			throw new Exception(rest.getMessage());
		}
	}

}
