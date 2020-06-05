/***
 * 배치 관리.
 *
 */
(function() {

	var _batch = abc.object.createNestedObject(window,"abc.biz.system.batch");

	_batch.selectedSiteId = 0;
	_batch.selectedSchedId = 0;

	// 서버쪽 페이징 처리는 되어 있으나 grid filter가 서버페이징모드에서는 동작하지 않기 때문에 목록을 다 가져오도록 수정.
	_batch.mainBatchPageCount = 200;
	_batch.historyPageCount = 30;
	_batch.historyLogPageCount = 10;
	_batch.jobInstancePageCount = 10;
	_batch.stepExecutionPageCount = 10;

	/**
	 * init batch grids...
	 */
	_batch.initBatch = function() {
		abc.biz.system.batch.initMainBatchSheet();
		abc.biz.system.batch.initHistorySheet();
		abc.biz.system.batch.initHistoryLogSheet();
		abc.biz.system.batch.initJobInstanceSheet();
		abc.biz.system.batch.initStepExecutionSheet();

		// 배치 업무 등록
		/*
		$("#saveJobBtn").off().on('click', function() {
			var url = "/system/batch/save-job-popup";
			var params = {};

			abc.open.popup({
				winname :	"saveJobPopup",
				url 	:	url,
				width 	:	645,
				height	:	525,
				params	:	params
			});
		});
		*/

		// 배치 업무 일정  등록 팝업
		$("#saveScheduleBtn").off().on('click', function() {
			var url = "/system/batch/save-trigger-popup";
			var params = {};

			var schedulePopup = abc.open.popup({
				winname :	"saveTriggerPopup",
				url 	:	url,
				width 	:	645,
				height	:	705,
				params	:	params
			});

			// 팝업 unload
			schedulePopup.onbeforeunload = function() {
				abc.biz.system.batch.doActionMainBatch("search");
			}
		});

		// 배치 시작
		$("#resumeBtn").off().on('click', function() {
			var saveData = mainBatchSheet.GetSaveString({Col: 0});

			if (abc.text.isBlank(saveData)) {
				alert("시작할 배치 실행 일정 정보를 선택해 주십시요.");
				return false;
			}

			$.ajax({
				type:"post",
				url: "/system/batch/resume-trigger",
				data: saveData
			}).done(function(data, textStatus, jqXHR) {
				alert("시작 되었습니다.");
				abc.biz.system.batch.doActionMainBatch("search");
			}).fail(function(jqXHR, textStatus, errorThrown) {
//				console.log(jqXHR, textStatus, errorThrown);
				alert("오류가 발생했습니다. - " + jqXHR.responseJSON.message);
			});
		});

		// 배치 정지
		$("#pauseBtn").off().on('click', function() {
			var saveData = mainBatchSheet.GetSaveString({Col: 0});

			if (abc.text.isBlank(saveData)) {
				alert("정지할 배치 실행 일정 정보를 선택해 주십시요.");
				return false;
			}

			$.ajax({
				type:"post",
				url: "/system/batch/pause-trigger",
				data: saveData
			}).done(function(data, textStatus, jqXHR) {
				alert("중지 되었습니다.");
				abc.biz.system.batch.doActionMainBatch("search");
			}).fail(function(jqXHR, textStatus, errorThrown) {
//				console.log(jqXHR, textStatus, errorThrown);
				alert("오류가 발생했습니다. - " + jqXHR.responseJSON.message);
			});
		});

		// 배치 실행
		$("#executeBtn").off().on('click', function() {
			var saveData = mainBatchSheet.GetSaveString({Col: 0});

			if (abc.text.isBlank(saveData)) {
				alert("실행할 배치 실행 일정 정보를 선택해 주십시요.");
				return false;
			}

			$.ajax({
				type:"post",
				url: "/system/batch/execute-job",
				data: saveData
			}).done(function(data, textStatus, jqXHR) {
				alert("실행 되었습니다.");
				var checkedRow = mainBatchSheet.FindCheckedRow(0).split("|")[0];
				var data = mainBatchSheet.GetRowData(checkedRow);
				abc.biz.system.batch.setMainBatchForm(data);
				historyLogSheet.RemoveAll();
				jobInstanceSheet.RemoveAll();
				stepExecutionSheet.RemoveAll();

				mainBatchSheet.CheckAll(0, 0);
			//	abc.biz.system.batch.doActionMainBatch("search");
			}).fail(function(jqXHR, textStatus, errorThrown) {
//				console.log(jqXHR, textStatus, errorThrown);
				alert("오류가 발생했습니다. - " + jqXHR.responseJSON.message);
			});
		});

		// 배치 삭제
		$("#deleteBtn").off().on('click', function() {
			var saveData = mainBatchSheet.GetSaveString({Col: 0});

			if (abc.text.isBlank(saveData)) {
				alert("삭제할 배치 실행 일정 정보를 선택해 주십시요.");
				return false;
			}

			$.ajax({
				type:"post",
				url: "/system/batch/remove-trigger",
				data: saveData
			}).done(function(data, textStatus, jqXHR) {
				alert("삭제 되었습니다.");
				abc.biz.system.batch.doActionMainBatch("search");
			}).fail(function(jqXHR, textStatus, errorThrown) {
//				console.log(jqXHR, textStatus, errorThrown);
				alert("오류가 발생했습니다. - " + jqXHR.responseJSON.message);
			});
		});

		// 배치 수정
		$("#saveBtn").off().on('click', function() {
			var saveData = mainBatchSheet.GetSaveString({Col: 0});

			if (abc.text.isBlank(saveData)) {
				alert("수정할 배치 실행 일정 정보를 선택해 주십시요.");
				return false;
			}

			$.ajax({
				type:"post",
				url: "/system/batch/save-trigger",
				data: saveData
			}).done(function(data, textStatus, jqXHR) {
				alert("수정 되었습니다.");
				abc.biz.system.batch.doActionMainBatch("search");
			}).fail(function(jqXHR, textStatus, errorThrown) {
//				console.log(jqXHR, textStatus, errorThrown);
				alert("오류가 발생했습니다. - " + jqXHR.responseJSON.message);
			});
		});
	}

	// set main batch form data
	_batch.setMainBatchForm = function(data) {
		$("#siteId").val(data.siteId);
		$("#triggerName").val(data.triggerName);
		$("#triggerGroup").val(data.triggerGroup);
		$("#schedId").val(data.schedId);

		abc.biz.system.batch.doActionHistory("search");
//		abc.biz.system.batch.doActionHistoryLog("search");
	}

	// set history form data
	_batch.setHistoryForm = function(data) {
		$("#schedulerHistoryId").val(data.seq);

		abc.biz.system.batch.doActionHistoryLog("search");
		abc.biz.system.batch.doActionJobInstance("search");
	}

	// set jobInstance form data
	_batch.setJobInstanceForm = function(data) {
		$("#jobExecutionId").val(data.jobExecutionId);

		abc.biz.system.batch.doActionStepExecution("search");
	}

	/**
	 * init main batch sheet
	 */
	_batch.initMainBatchSheet = function() {
		var initMainBatchSheet = {};
		initMainBatchSheet.Cfg = {SearchMode:2, Page:abc.biz.system.batch.mainBatchPageCount, MaxSort:1};
		initMainBatchSheet.Cols = [
			    {Header:"",	 			Type:"CheckBox",	SaveName:"checkbox",			Width:0,	Align:"Center",	Hidden:0,	Edit:1, Sort:0}
			  , {Header:"스케줄아이디",		Type:"Text",		SaveName:"schedId",				Width:0,	Align:"Center",	Hidden:1,	Edit:0, Sort:0}
			  , {Header:"트리거그룹",		Type:"Text",		SaveName:"triggerGroup",		Width:100,	Align:"Center",	Hidden:1,	Edit:0, Sort:0}
			  , {Header:"사이트",	 		Type:"Combo",		SaveName:"siteId",				Width:50,	Align:"Center",	Hidden:0,	Edit:1, Sort:0}
			  , {Header:"업무그룹",		Type:"Combo",		SaveName:"jobGroup",			Width:50,	Align:"Center",	Hidden:0,	Edit:1, Sort:0}
			  , {Header:"업무명",			Type:"Text",		SaveName:"jobName",				Width:50,	Align:"Center",	Hidden:0,	Edit:1, Sort:0}
			  , {Header:"배치명",			Type:"Text",		SaveName:"triggerName",			Width:150,	Align:"Left",	Hidden:0,	Edit:1, Sort:0}
			  , {Header:"파라메터",		Type:"Text",		SaveName:"parameters",			Width:100,	Align:"Left",	Hidden:0,	Edit:1, Sort:0}
			  , {Header:"설명",			Type:"Text",		SaveName:"description",			Width:250,	Align:"Left",	Hidden:0,	Edit:1, Sort:0}
			  , {Header:"일정표현식",		Type:"Text",		SaveName:"cronExpression",		Width:80,	Align:"Center",	Hidden:0,	Edit:1, Sort:0}
			  , {Header:"일정진행상태",		Type:"Text",		SaveName:"triggerState",		Width:80,	Align:"Center",	Hidden:0,	Edit:0, Sort:0}
			  , {Header:"다음실행일시",		Type:"Text",		SaveName:"nextFireTime",		Width:100,	Align:"Center",	Hidden:0,	Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN, Sort:0}
			  , {Header:"최근실행일시",		Type:"Text",		SaveName:"prevFireTime",		Width:100,	Align:"Center",	Hidden:0,	Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN, Sort:0}
			  , {Header:"일정시작일시",		Type:"Text",		SaveName:"startTime",			Width:100,	Align:"Center",	Hidden:1,	Edit:1, Format:abc.consts.DEFAULT_DATETIME_PATTERN, Sort:0}
			  , {Header:"일정종료일시",		Type:"Text",		SaveName:"endTime",				Width:100,	Align:"Center",	Hidden:1,	Edit:1, Format:abc.consts.DEFAULT_DATETIME_PATTERN, Sort:0}
			  , {Header:'수정자번호',		Type:"Text",		SaveName:"modifyUserId",		Width:100,	Align:"Center",	Hidden:1,	Edit:0, Sort:0}
			  , {Header:'수정자',			Type:"Text",		SaveName:"moderDisplayName",	Width:100,	Align:"Center",	Hidden:1,	Edit:0, Sort:0}
			  , {Header:'수정일시',		Type:"Date",		SaveName:"modifyDtime",			Width:100,	Align:"Center",	Hidden:1,	Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN, Sort:0}
			  , {Header:'작성자번호',		Type:"Text",		SaveName:"regUserId",			Width:100,	Align:"Center",	Hidden:1,	Edit:0, Sort:0}
			  , {Header:'작성자',			Type:"Text",		SaveName:"rgsterDisplayName",	Width:100,	Align:"Center",	Hidden:1,	Edit:0, Sort:0}
			  , {Header:'작성일시',		Type:"Date",		SaveName:"regDtime",			Width:100,	Align:"Center",	Hidden:1,	Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN, Sort:0}
		];

		createIBSheet2(document.getElementById("mainBatchGrid"), "mainBatchSheet", "100%", "600px");
		IBS_InitSheet(mainBatchSheet, initMainBatchSheet);

		mainBatchSheet.SetCountPosition(3);
		mainBatchSheet.SetPagingPosition(2);
		mainBatchSheet.SetFocusAfterProcess(0);
		mainBatchSheet.FitColWidth();
		mainBatchSheet.InitDataCombo(0, "siteId", this.siteCombo.text, this.siteCombo.code);
		mainBatchSheet.InitDataCombo(0, "jobGroup", this.codeCombo.JOB_GROUP.text, this.codeCombo.JOB_GROUP.code);
		mainBatchSheet.ShowFilterRow();

		abc.biz.system.batch.doActionMainBatch("search");
	}

	/**
	 * action main batch
	 */
	_batch.doActionMainBatch = function(sAction) {
		switch (sAction) {
		case "search":
			var param = {
				url : "/system/batch/list"
				, onePageRow : abc.biz.system.batch.mainBatchPageCount
//				, subparam : FormQueryStringEnc(document.Form)
				, sheet : "mainBatchSheet"
			};

			DataSearchPaging(param);
			break;
		}
	}

	/**
	 *  init history sheet
	 */
	_batch.initHistorySheet = function() {
		var initHistorySheet = {};
		initHistorySheet.Cfg = {SearchMode:smServerPaging2, Page:abc.biz.system.batch.historyPageCount, MaxSort:1};
		initHistorySheet.Cols = [
				{Header:"번호",	 		Type:"Text",		SaveName:"seq",					Width:50,	Align:"Center",	Hidden:0,	Edit:0, Sort:0}
			  , {Header:"스케줄아이디", 		Type:"Text",		SaveName:"schedId",				Width:100,	Align:"Center",	Hidden:0,	Edit:0, Sort:0}
			  , {Header:"내용",			Type:"Text",		SaveName:"message",				Width:100,	Align:"Center",	Hidden:0,	Edit:0, Sort:0}
			  , {Header:"일정시작일시",		Type:"Date",		SaveName:"schedStartDtime",		Width:100,	Align:"Center",	Hidden:0,	Edit:0,		Format:abc.consts.DEFAULT_DATETIME_PATTERN, Sort:0}
			  , {Header:"일정종료일시",		Type:"Date",		SaveName:"schedEndDtime",		Width:100,	Align:"Center",	Hidden:0,	Edit:0,		Format:abc.consts.DEFAULT_DATETIME_PATTERN, Sort:0}
			  , {Header:"작성일시",		Type:"Date",		SaveName:"regDtime",			Width:100,	Align:"Center",	Hidden:0,	Edit:0,		Format:abc.consts.DEFAULT_DATETIME_PATTERN, Sort:0}
		];

		createIBSheet2(document.getElementById("historyGrid"), "historySheet", "100%", "1000px");
		IBS_InitSheet(historySheet, initHistorySheet);

		historySheet.SetCountPosition(3);
		historySheet.SetPagingPosition(2);
		historySheet.SetFocusAfterProcess(0);
		historySheet.FitColWidth();

//		abc.biz.system.batch.doActionHistory("search");
	}

	/**
	 * action history
	 */
	_batch.doActionHistory = function(sAction) {
		switch (sAction) {
		case "search":
			var param = {
				url : "/system/batch/history"
				, onePageRow : abc.biz.system.batch.historyPageCount
				, subparam : FormQueryStringEnc(document.batchForm)
				, sheet : "historySheet"
			};

			DataSearchPaging(param);
			break;
		}
	}


	/**
	 *  history log sheet init
	 */
	_batch.initHistoryLogSheet = function() {
		var initHistoryLogSheet = {};
		initHistoryLogSheet.Cfg = {SearchMode:smServerPaging2, Page:abc.biz.system.batch.historyLogPageCount, MaxSort:1};
		initHistoryLogSheet.Cols = [
				{Header:"번호",	 		Type:"Text",		SaveName:"seq",					Width:50,	Align:"Center",	Hidden:0,	Edit:0, Sort:0}
			  , {Header:"내용",			Type:"Text",		SaveName:"message",				Width:500,	Align:"Center",	Hidden:0,	Edit:0, Sort:0}
			  , {Header:"작성일시",		Type:"Date",		SaveName:"regDtime",			Width:100,	Align:"Center",	Hidden:0,	Edit:0,		Format:abc.consts.DEFAULT_DATETIME_PATTERN, Sort:0}
		];

		createIBSheet2(document.getElementById("historyLogGrid"), "historyLogSheet", "100%", "300px");
		IBS_InitSheet(historyLogSheet, initHistoryLogSheet);

		historyLogSheet.SetCountPosition(3);
		historyLogSheet.SetPagingPosition(2);
		historyLogSheet.SetFocusAfterProcess(0);
		historyLogSheet.FitColWidth();

//		abc.biz.system.batch.doActionHistoryLog("search");
	}

	/**
	 * action history log
	 */
	_batch.doActionHistoryLog = function(sAction) {
		switch (sAction) {
		case "search":
			var param = {
				url : "/system/batch/history-log"
				, onePageRow : abc.biz.system.batch.historyLogPageCount
				, subparam : FormQueryStringEnc(document.batchForm)
				, sheet : "historyLogSheet"
			};

			DataSearchPaging(param);
			break;
		}
	}

	/**
	 *  job instance sheet init
	 */
	_batch.initJobInstanceSheet = function() {
		var initJobInstanceSheet = {};
		initJobInstanceSheet.Cfg = {SearchMode:smServerPaging2, Page:abc.biz.system.batch.jobInstancePageCount, MaxSort:1}
		initJobInstanceSheet.Cols = [
//				{Header:"번호",	 		Type:"Text",		SaveName:"seq",					Width:50,	Align:"Center",	Hidden:0,	Edit:0}
			  {Header:"배치인스턴스아이디",	Type:"Text",		SaveName:"jobExecutionId",		Width:100,	Align:"Center",	Hidden:0,	Edit:0, Sort:0}
			  , {Header:"배치실행일시",		Type:"Date",		SaveName:"createTime",			Width:100,	Align:"Center",	Hidden:0,	Edit:0, Sort:0}
			  , {Header:"상태",			Type:"Text",		SaveName:"status",				Width:100,	Align:"Center",	Hidden:0,	Edit:0, Sort:0}
			  , {Header:"배치시작일시",		Type:"Date",		SaveName:"startTime",			Width:100,	Align:"Center",	Hidden:0,	Edit:0,		Format:abc.consts.DEFAULT_DATETIME_PATTERN, Sort:0}
			  , {Header:"배치종료일시",		Type:"Date",		SaveName:"endTime",				Width:100,	Align:"Center",	Hidden:0,	Edit:0,		Format:abc.consts.DEFAULT_DATETIME_PATTERN, Sort:0}
			  , {Header:"내용",			Type:"Text",		SaveName:"message",				Width:100,	Align:"Center",	Hidden:0,	Edit:0,		Format:abc.consts.DEFAULT_DATETIME_PATTERN, Sort:0}
		];

		createIBSheet2(document.getElementById("jobInstanceGrid"), "jobInstanceSheet", "100%", "300px");
		IBS_InitSheet(jobInstanceSheet, initJobInstanceSheet);

		jobInstanceSheet.SetCountPosition(3);
		jobInstanceSheet.SetPagingPosition(2);
		jobInstanceSheet.SetFocusAfterProcess(0);
		jobInstanceSheet.FitColWidth();

//		abc.biz.system.batch.doActionJobInstance("search");
	}

	/**
	 * action jobInstance
	 */
	_batch.doActionJobInstance = function(sAction) {
		switch (sAction) {
		case "search":
			var param = {
				url : "/system/batch/job-instance"
				, onePageRow : abc.biz.system.batch.jobInstancePageCount
				, subparam : FormQueryStringEnc(document.batchForm)
				, sheet : "jobInstanceSheet"
			};

			DataSearchPaging(param);
			break;
		}
	}

	/**
	 *  step execution sheet init
	 */
	_batch.initStepExecutionSheet = function() {
		var initStepExecutionSheet = {};
		initStepExecutionSheet.Cfg = {SearchMode:smServerPaging2, Page:abc.biz.system.batch.stepExecutionPageCount, MaxSort:1};
		initStepExecutionSheet.Cols = [
//				{Header:"번호",	 		Type:"Text",		SaveName:"",					Width:100,	Align:"Center",	Hidden:0,	Edit:0}
			  {Header:"단계명",	 		Type:"Text",		SaveName:"stepName",			Width:200,	Align:"Center",	Hidden:0,	Edit:0, Sort:0}
			  , {Header:"상태",			Type:"Text",		SaveName:"status",				Width:100,	Align:"Center",	Hidden:0,	Edit:0, Sort:0}
			  , {Header:"시작일시",		Type:"Date",		SaveName:"startTime",			Width:100,	Align:"Center",	Hidden:0,	Edit:0,		Format:abc.consts.DEFAULT_DATETIME_PATTERN, Sort:0}
			  , {Header:"종료일시",		Type:"Date",		SaveName:"endTime",				Width:100,	Align:"Center",	Hidden:0,	Edit:0,		Format:abc.consts.DEFAULT_DATETIME_PATTERN, Sort:0}
			  , {Header:"read",			Type:"Text",		SaveName:"readCount",			Width:50,	Align:"Center",	Hidden:0,	Edit:0, Sort:0}
			  , {Header:"write",		Type:"Text",		SaveName:"writeCount",			Width:50,	Align:"Center",	Hidden:0,	Edit:0, Sort:0}
			  , {Header:"filter",		Type:"Text",		SaveName:"filterCount",			Width:50,	Align:"Center",	Hidden:0,	Edit:0, Sort:0}
			  , {Header:"commit",		Type:"Text",		SaveName:"commitCount",			Width:50,	Align:"Center",	Hidden:0,	Edit:0, Sort:0}
			  , {Header:"rollback",		Type:"Text",		SaveName:"rollbackCount",		Width:50,	Align:"Center",	Hidden:0,	Edit:0, Sort:0}
			  , {Header:"read skip",	Type:"Text",		SaveName:"readSkipCount",		Width:50,	Align:"Center",	Hidden:0,	Edit:0, Sort:0}
			  , {Header:"writer skip",	Type:"Text",		SaveName:"writeSkipCount",		Width:50,	Align:"Center",	Hidden:0,	Edit:0, Sort:0}
			  , {Header:"process skip",	Type:"Text",		SaveName:"processSkipCount",	Width:50,	Align:"Center",	Hidden:0,	Edit:0, Sort:0}
		];

		createIBSheet2(document.getElementById("stepExecutionGrid"), "stepExecutionSheet", "100%", "300px");
		IBS_InitSheet(stepExecutionSheet, initStepExecutionSheet);

		stepExecutionSheet.SetCountPosition(3);
		stepExecutionSheet.SetPagingPosition(2);
//		stepExecutionSheet.SetFocusAfterProcess(0);
		stepExecutionSheet.FitColWidth();

//		abc.biz.system.batch.doActionStepExecution("search");
	}

	/**
	 * action step execution
	 */
	_batch.doActionStepExecution = function(sAction) {
		switch (sAction) {
		case "search":
			var param = {
				url : "/system/batch/step-execution"
				, onePageRow : abc.biz.system.batch.stepExecutionPageCount
				, subparam : FormQueryStringEnc(document.batchForm)
				, sheet : "stepExecutionSheet"
			};

			DataSearchPaging(param);
			break;
		}
	}

	// 배치등록(popup)
	_batch.saveSchedule = function() {
		var $form = $("#scheduleForm");

		if (abc.text.isBlank($("#jobName").val())) {
			alert("업무명을 입력해 주십시요.");
			$("#jobName").focus();
			return false;
		}
		if(abc.text.isLimitLength($("#jobName").val(), 100)){
			alert("업무명이 최대값을 초과하였습니다.");
			$("#jobName").focus();
			return false;
		}

		if (abc.text.isBlank($("#triggerName").val())) {
			alert("배치명을 입력해 주십시요.");
			$("#triggerName").focus();
			return false;
		}
		if(abc.text.isLimitLength($("#triggerName").val(), 100)){
			alert("배치명이 최대값을 초과하였습니다.");
			$("#triggerName").focus();
			return false;
		}

		if (abc.text.isBlank($("#cronExpression").val())) {
			alert("일정 표현식을 입력해 주십시요.");
			$("#cronExpression").focus();
			return false;
		}

		if (abc.text.isBlank($("#startDate").val())) {
			alert("일정 시작일을 입력해 주십시요.");
			$("#startDate").focus();
			return false;
		}

		if (abc.text.isBlank($("#endDate").val())) {
			alert("일정 종료일을 입력해 주십시요.");
			$("#endDate").focus();
			return false;
		}

		var startYmd = $("#startDate").datepicker("getDate");
		startYmd.setHours($("#startHour").val());
		startYmd.setMinutes($("#startMinute").val());
		$("#triggerStartTime").val(startYmd.format());

		var endYmd = $("#endDate").datepicker("getDate");
		endYmd.setHours($("#endHour").val());
		endYmd.setMinutes($("#endMinute").val());
		$("#triggerEndTime").val(endYmd.format());

		if (startYmd > endYmd) {
			alert("시작일이 종료일보다 클 수 없습니다.");
			$("#startDate").focus();
		}

		$.ajax({
			type:"post",
			url: "/system/batch/save-trigger",
			data: $form.serialize()
		}).done(function(data, textStatus, jqXHR) {
			alert("저장되었습니다.");
			self.close();
		}).fail(function(jqXHR, textStatus, errorThrown) {
//			console.log(jqXHR, textStatus, errorThrown);
			alert("오류가 발생했습니다. - " + jqXHR.responseJSON.message);
		});
	}

})();