/***
 * 회원이력
 * 
 */
(function() {
	
	var _history = abc.object.createNestedObject(window,"abc.biz.member.history");
	
	_history.loginPageCount = 15;
	_history.changePageCount = 15;
	_history.certifyPageCount = 15;
	_history.eventPointPageCount = 15;
	_history.crtfcSuccessYn = {
		code: 'Y|N',
		text: '인증성공|인증실패'
	}
	
	/**
	 * tab focus 처리.
	 */
	_history.setTabFocus = function() {
		var tabIndex = 0; // 2020.05.27 : 로그인 이력 화면이 기본
		var tab = location.hash || "tabLogin";
		if (tab == "#tabLogin") {
			tabIndex = 0;	
		} else if(tab == "#tabChange") {
			tabIndex = 1;
		} else if(tab == "#tabCertify") {
			tabIndex = 2;
		} else if(tab == "#tabEventPoint") {
			tabIndex = 3;
		}
		$("#historyTab").tabs("option", "active", tabIndex);
		
		switch(tabIndex) {
			case 0:
				abc.biz.member.history.initLoginTab();
				$("#searchLoginBtn").trigger("click");
				break;
			case 1:
				abc.biz.member.history.initChangeTab();
				$("#searchChangeBtn").trigger("click");
				break;
			case 2:
				abc.biz.member.history.initCertifyTab();
				$("#searchCertifyBtn").trigger("click");
			  	break;
			case 3: // 2020.05.27 : 이벤트포인트 이력 검색 추가
				abc.biz.member.history.initEvnetPointTab();
				$("#searchEventPointBtn").trigger("click");
			  	break;
			default:
				abc.biz.member.history.initLoginTab();
		}
	}
	
	/**
	 * 로그인이력 tab init
	 */
	_history.initLoginTab = function() {
		if (typeof loginSheet === "undefined") {
			abc.biz.member.history.initLoginSheet();
		}
		
		// 목록 개수
		abc.biz.member.history.loginPageCount = $("#loginPageCount").val();
		$("#loginPageCount").off().on('change', function() {
			abc.biz.member.history.loginPageCount = $(this).val(); 
			abc.biz.member.history.doActionLogin("search");
		});
		
		// 초기화 버튼
		$("#resetLoginBtn").off().on("click", function() {
			$("#searchLoginForm")[0].reset();
			$("a[name^=per1Month]").trigger("click");
		});
		
		// 검색 버튼
		$("#searchLoginBtn").off().on("click", function() {			
			if (!abc.date.searchValidate()) {
				return false;
			}
			abc.biz.member.history.doActionLogin("search");
		});
		
		// 로그인 경로 전체 체크박스
		$("#deviceCodeAll").off().on("click", function() {
			$("input[name=deviceCode]").prop("checked", $(this).is(":checked"));
		});
		
		// 로그인 경로 개별 체크박스
		$("input[name=deviceCode]").off().on("click", function() {
			if ($(this).is(":checked")) {
				var unChecked = $("input[name=deviceCode]:not(:checked)");
				if (unChecked.length == 0) {
					$("#deviceCodeAll").prop("checked", true);
				}
			} else {
				$("#deviceCodeAll").prop("checked", false);
			}
		});
		
		// 회원구분 전체 체크박스
		$("#memberTypeCodeAll").off().on("click", function() {
			$("input[data-group=memberType]").prop("checked", $(this).is(":checked"));
		});
		
		// 회원구분 개별, 임직원 체크박스
		$("input[data-group=memberType]").off().on("click", function() {
			if ($(this).is(":checked")) {
				var unChecked = $("input[data-group=memberType]:not(:checked)");
				if (unChecked.length == 0) {
					$("#memberTypeCodeAll").prop("checked", true);
				}
			} else {
				$("#memberTypeCodeAll").prop("checked", false);
			}
		});
	}
	
	/**
	 * 변경이력 tab init
	 */
	_history.initChangeTab = function() {
		if (typeof changeSheet === "undefined") {
			abc.biz.member.history.initChangeSheet();
		}
		
		// 목록 개수
		abc.biz.member.history.changePageCount = $("#changePageCount").val();
		$("#changePageCount").off().on('change', function() {
			abc.biz.member.history.changePageCount = $(this).val(); 
			abc.biz.member.history.doActionChange("search");
		});
		
		// 초기화 버튼
		$("#resetChangeBtn").off().on("click", function() {
			$("#searchChangeForm")[0].reset();
			$("#changeGroup").trigger("change");
			$("a[name^=per1Month]").trigger("click");
		});
		
		// 검색 버튼
		$("#searchChangeBtn").off().on("click", function() {
			if (abc.date.searchValidate()) {
				abc.biz.member.history.doActionChange("search");
			}
		});
		
		// 구분 change
		$("#changeGroup").off().on("change", function() {
			var value = $(this).val();
			if (!abc.text.isBlank(value)) {
				abc.biz.member.history.getChangeField(value);
				$("#changeFieldTag").show();
			} else {
				$("#changeFieldTag").hide();
			}
		});
	}
	
	/**
	 * get change field
	 */
	_history.getChangeField = function(value) {
		$.ajax({
			type:"get",
			url: "/member/change-history/"+value
		}).done(function(data, textStatus, jqXHR) {
			abc.biz.member.history.setChangeField(data);
		}).fail(function(jqXHR, textStatus, errorThrown) {
			console.log(jqXHR, textStatus, errorThrown);
			alert("오류가 발생했습니다. - " + jqXHR.responseJSON.message);
		});
	}
	
	/**
	 * set change field
	 */
	_history.setChangeField = function(data) {
		var select = $("#changeField");
		select.find("option").remove();
		
		var all = "";		
		$.each(data, function(index, item) {
			select.append(new Option(item.codeDtlName, item.codeDtlNo));
			all += (index == 0) ? item.codeDtlNo : ","+item.codeDtlNo;
		}); 
		
		select.prepend(new Option("전체", all, false, true));
	}
	
	/**
	 * 본인인증이력 tab init
	 */
	_history.initCertifyTab = function() {
		if (typeof certifySheet === "undefined") {
			abc.biz.member.history.initCertifySheet();
		}
		
		// 초기화 버튼
		$("#resetCertifyBtn").off().on("click", function() {
			$("#searchCertifyForm")[0].reset();
			$("a[name^=per1Month]").trigger("click");
		});
		
		// 검색 버튼
		$("#searchCertifyBtn").off().on("click", function() {
			if (abc.date.searchValidate()) {
				abc.biz.member.history.doActionCertify("search");
			}
		});
		
		// 목록 개수
		abc.biz.member.history.certifyPageCount = $("#certifyPageCount").val();
		$("#certifyPageCount").off().on('change', function() {
			abc.biz.member.history.certifyPageCount = $(this).val(); 
			abc.biz.member.history.doActionCertify("search");
		});
	}
	
	/**
	 * 로그인이력 sheet init
	 */
	_history.initLoginSheet = function() {
		var initLoginSheet = {};
		initLoginSheet.Cfg = {SearchMode:smServerPaging2, Page:abc.biz.member.history.loginPageCount,  MaxSort:1};
		initLoginSheet.Cols = [
			{Header:"고유번호", 		Type:"Text",	SaveName:"loginHistSeq",		Width:50,	Align:"Center",	Hidden:0,	Edit:0, Sort:0}
			, {Header:"로그인 경로",		Type:"Combo",	SaveName:"deviceCode",			Width:50,	Align:"Center",	Hidden:0,	Edit:0, Sort:0}
			, {Header:"회원구분",		Type:"Combo",	SaveName:"memberTypeCode",		Width:60,	Align:"Center",	Hidden:0,	Edit:0, Sort:0}
			, {Header:"세션 아이디",		Type:"Text",	SaveName:"sessionId",	 		Width:120,	Align:"Center",	Hidden:0,	Edit:0, Sort:0}
			, {Header:"인증 결과",		Type:"Combo",	SaveName:"cnnctrStatCode",		Width:80,	Align:"Center",	Hidden:0,	Edit:0, Sort:0}
			, {Header:"회차",			Type:"Text",	SaveName:"pswdErrorCount",		Width:30,	Align:"Center",	Hidden:0,	Edit:0, Sort:0}
			, {Header:"접속아이피",		Type:"Text",	SaveName:"accessIpText",		Width:70,	Align:"Center",	Hidden:0,	Edit:0, Sort:0}
			, {Header:"로그인일시",		Type:"Text",	SaveName:"cnnctrDtm",			Width:80,	Align:"Center",	Hidden:0,	Edit:0,	Format:abc.consts.DEFAULT_DATETIME_PATTERN}
		];

		createIBSheet2(document.getElementById("loginGrid"), "loginSheet", "100%", "430px");
		IBS_InitSheet(loginSheet, initLoginSheet);
		
		loginSheet.SetCountPosition(3);
		loginSheet.SetPagingPosition(2);
		loginSheet.FitColWidth();
		loginSheet.SetExtendLastCol(1);
		loginSheet.InitDataCombo(0, "deviceCode", this.codeCombo.DEVICE_CODE.text, this.codeCombo.DEVICE_CODE.code);
		loginSheet.InitDataCombo(0, "memberTypeCode", this.codeCombo.MEMBER_TYPE_CODE.text, this.codeCombo.MEMBER_TYPE_CODE.code);
		loginSheet.InitDataCombo(0, "cnnctrStatCode", this.codeCombo.CNNCTR_STAT_CODE.text, this.codeCombo.CNNCTR_STAT_CODE.code);
		
//		abc.biz.member.history.doActionLogin("search");
	}
	
	_history.initLoginSheetCombo = function(saveName, data) {
		var combo = data.split(",");
		loginSheet.InitDataCombo(0, saveName ,combo[0] , combo[1]);
	}
	
	/**
	 * 로그인이력 grid action
	 */
	_history.doActionLogin = function(sAction){
		switch (sAction) {
		case "search":
			var param = {
				url : "/member/login-history"
				, onePageRow : abc.biz.member.history.loginPageCount
				, subparam : FormQueryStringEnc(document.searchLoginForm)
				, sheet : "loginSheet"
				, callback : function() {
					location.hash = "tabLogin";
				}
			};

			DataSearchPaging(param);
			break;
		}
	}
	
	/**
	 * 변경이력 sheet init
	 */
	_history.initChangeSheet = function() {
		var initChangeSheet = {};
		initChangeSheet.Cfg = {SearchMode:smServerPaging2, Page:abc.biz.member.history.changePageCount,  MaxSort:1};
		initChangeSheet.Cols = [
			{Header:"고유번호", 			Type:"Text",	SaveName:"chngHistSeq",			Width:100,	Align:"Center",	Hidden:0,	Edit:0, Sort:0}
			, {Header:"구분", 		Type:"Text",	SaveName:"chngFieldName",		Width:100,	Align:"Center",	Hidden:0,	Edit:0, Sort:0}
			, {Header:"변경내용(변경 전 > 변경 후)",		Type:"Text",	SaveName:"changeValue",		Width:500,	Align:"Center",	Hidden:0,	Edit:0, Sort:0}
			, {Header:"수정자",		Type:"Text",	SaveName:"rgsterNo",			Width:100,	Align:"Center",	Hidden:1,	Edit:0, Sort:0}
			, {Header:"수정자",		Type:"Text",	SaveName:"rgsterDisplayName",	Width:100,	Align:"Center",	Hidden:0,	Edit:0, Sort:0, FontBold:1, FontUnderline:1, Cursor:"Pointer"}
			, {Header:"수정일시",		Type:"Text",	SaveName:"rgstDtm",	 		Width:100,	Align:"Center",	Hidden:0,	Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
		];

		createIBSheet2(document.getElementById("changeGrid"), "changeSheet", "100%", "430px");
		IBS_InitSheet(changeSheet, initChangeSheet);
		
		changeSheet.SetCountPosition(3);
		changeSheet.SetPagingPosition(2);
		changeSheet.FitColWidth();
		changeSheet.SetExtendLastCol(1);
		
//		abc.biz.member.history.doActionChange("search");
	}
	
	/**
	 * 변경이력 grid action
	 */
	_history.doActionChange = function(sAction){
		switch (sAction) {
		case "search":
			var param = {
				url : "/member/change-history"
				, onePageRow : abc.biz.member.history.changePageCount
				, subparam : FormQueryStringEnc(document.searchChangeForm)
				, sheet : "changeSheet"
				, callback : function() {
					location.hash = "tabChange";
				}
			};

			DataSearchPaging(param);
			break;
		}
	}
	
	/**
	 * 본인인증이력 sheet init
	 */
	_history.initCertifySheet = function() {
		var initCertifySheet = {};
		initCertifySheet.Cfg = {SearchMode:smServerPaging2, Page:abc.biz.member.history.certifyPageCount,  MaxSort:1};
		initCertifySheet.Cols = [
			{Header:"고유번호", 		Type:"Text",	SaveName:"crtfcHistSeq",		Width:100,	Align:"Center",	Hidden:0,	Edit:0, Sort:0}
			, {Header:"인증 수단",		Type:"Combo",	SaveName:"crtfcTypeCode",		Width:100,	Align:"Center",	Hidden:0,	Edit:0, Sort:0}
			, {Header:"세션 아이디",	Type:"Text",	SaveName:"sessionId",	 		Width:100,	Align:"Center",	Hidden:0,	Edit:0, Sort:0}
			, {Header:"인증 경로",		Type:"Combo",	SaveName:"crtfcPathCode",		Width:100,	Align:"Center",	Hidden:0,	Edit:0, Sort:0}
			, {Header:"인증 결과",		Type:"Combo",	SaveName:"crtfcSuccessYn",		Width:100,	Align:"Center",	Hidden:0,	Edit:0, Sort:0}
			, {Header:"접속아이피",		Type:"Text",	SaveName:"accessIpText",		Width:100,	Align:"Center",	Hidden:0,	Edit:0, Sort:0}
			, {Header:"본인인증일시",	Type:"Text",	SaveName:"crtfcDtm",			Width:100,	Align:"Center",	Hidden:0,	Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
		];

		createIBSheet2(document.getElementById("certifyGrid"), "certifySheet", "100%", "430px");
		IBS_InitSheet(certifySheet, initCertifySheet);
		
		certifySheet.SetCountPosition(3);
		certifySheet.SetPagingPosition(2);
		certifySheet.FitColWidth();
		certifySheet.SetExtendLastCol(1);
		
		certifySheet.InitDataCombo(0, "crtfcTypeCode", this.codeCombo.CRTFC_TYPE_CODE.text, this.codeCombo.CRTFC_TYPE_CODE.code);
		certifySheet.InitDataCombo(0, "crtfcPathCode", this.codeCombo.CRTFC_PATH_CODE.text, this.codeCombo.CRTFC_PATH_CODE.code);
		certifySheet.InitDataCombo(0, "crtfcSuccessYn", this.crtfcSuccessYn.text, this.crtfcSuccessYn.code);
		
//		abc.biz.member.history.doActionCertify("search");
	}
	
	/**
	 * 본인인증이력 grid action
	 */
	_history.doActionCertify = function(sAction){
		switch (sAction) {
		case "search":
			var param = {
				url : "/member/certify-history"
				, onePageRow : abc.biz.member.history.certifyPageCount
				, subparam : FormQueryStringEnc(document.searchCertifyForm)
				, sheet : "certifySheet"
				, callback : function() {
					location.hash = "tabCertify";
				}
			};

			DataSearchPaging(param);
			break;
		}
	}
	
	/**
	 * 2020.05.27 추가 : 이벤트포인트 이력 탭 init
	 */
	_history.initEvnetPointTab = function() {
		if (typeof changeSheet === "undefined") {
			abc.biz.member.history.initEvnetPointSheet();
		}
		
		abc.biz.member.history.doActionEventPoint("search");
		// 검색 일시 주석 2020.05.27
		
		$("#eventPointPageCount").off().on('change', function() {
			abc.biz.member.history.doActionEventPoint("search");
			// 목록개수 바꿀때마다 조회되도록 2020.05.27
		});
	}
	
	/**
	 * 2020.05.27 추가 : 이벤트포인트 이력 탭 sheet
	 */
	_history.initEvnetPointSheet = function() {
		var initEvnetPointSheet = {};
		initEvnetPointSheet.Cfg = {SearchMode:smServerPaging2, Page:$("#eventPointPageCount").val(),  MaxSort:1};
		initEvnetPointSheet.Cols = [
			{Header:"구분", 		Type:"Text",	SaveName:"Gubun",		Width:100,	Align:"Center",	Hidden:0,	Edit:0, Sort:0}
			, {Header:"적립",		Type:"Text",	SaveName:"eventPoint",	Width:100,	Align:"Center",	Hidden:0,	Edit:0, Sort:0}
			, {Header:"일자",		Type:"Text",	SaveName:"Date",		Width:100,	Align:"Center",	Hidden:0,	Edit:0, Format:"yyyy.MM.dd"}
		];

		createIBSheet2(document.getElementById("eventPointGrid"), "eventPointSheet", "100%", "430px");
		IBS_InitSheet(eventPointSheet, initEvnetPointSheet);
		
		eventPointSheet.SetCountPosition(3);
		eventPointSheet.SetPagingPosition(2);
		eventPointSheet.FitColWidth();
		eventPointSheet.SetExtendLastCol(1);
	}
	
	/**
	 * 2020.05.27 추가 : 이벤트포인트 이력 탭 greed action
	 */
	_history.doActionEventPoint = function(sAction){
		switch (sAction) {
		case "search":
			var param = {
				url : "/member/read-history-point-list"
				, onePageRow : $("#eventPointPageCount").val()
				, subparam : FormQueryStringEnc(document.eventPointForm)
				, sheet : "eventPointSheet"
				, callback : function() {
					location.hash = "tabEventPoint";
				}
			};

			DataSearchPaging(param);
			break;
		}
	}	
})();