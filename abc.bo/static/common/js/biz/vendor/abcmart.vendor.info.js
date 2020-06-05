/***
 * 입점관리 > 입점사 정보등록
 *
 */
(function() {

	var _vendorInfo = abc.object.createNestedObject(window,"abc.biz.vendor.info");

	_vendorInfo.initVendorInfoSheet = function() {
		createIBSheet2(document.getElementById("vendorInfoGrid"), "vendorInfoSheet", "100%", "370px");
		var initVendorInfoSheet = {};
		var pageCount = $('#pageCount').val();

		initVendorInfoSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MaxSort:1};
		initVendorInfoSheet.HeaderMode = {Sort:0, ColMove:0, ColResize:1};
		initVendorInfoSheet.Cols = [
				  {Header:"" ,		 		Type:"Status",		SaveName:"status",				Width: 0,  Align:"", 		Edit:0, Hidden:1}
				, {Header:"", 				Type:"CheckBox",	SaveName:"chk",					Width: 30, Align:"Center", Sort:0}
				, {Header:"번호", 			Type:"Seq",			SaveName:"",					Width: 50, Align:"Center", 	Edit:0, Sort:0}
				, {Header:"입점사코드(ERP)", 	Type:"Text",		SaveName:"insdMgmtInfoText",	Width: 150, Align:"Center", 	Edit:0}
				, {Header:"입점사ID", 		Type:"Text",		SaveName:"vndrNo",				Width: 150, Align:"Center", 	Edit:0, Hidden:1}
				, {Header:"입점업체명",			Type:"Text",		SaveName:"vndrName",			Width: 170, Align:"Center",	Edit:0, Sort:0, FontBold:1, FontUnderline:1, Cursor:"Pointer"}
				, {Header:"대표자명", 			Type:"Text",		SaveName:"bossName",			Width: 135, Align:"Center", 	Edit:0, Sort:0}
				, {Header:"사엽자번호", 		Type:"Text",		SaveName:"bizNoText",			Width: 130, Align:"Center", 	Edit:0, Sort:0}
				, {Header:"대표전화번호", 		Type:"Text",		SaveName:"rprsntTelNoText",		Width: 140, Align:"Center", 	Edit:0, Sort:0}
				, {Header:"팩스", 			Type:"Text",		SaveName:"faxNoText",			Width: 140, Align:"Center", 	Edit:0, Sort:0}
				, {Header:"전시채널", 			Type:"Text",		SaveName:"chnnlNames",			Width: 200, Align:"Center", 	Edit:0, Sort:0}
				, {Header:"대표담당자명", 		Type:"Text",		SaveName:"vndrMngrName",		Width: 125, Align:"Center", 	Edit:0, Sort:0}
				, {Header:"거래상태", 			Type:"Text",		SaveName:"vndrStatCode",		Width: 50, Align:"Center", 	Edit:0, Sort:0, Hidden:1}
				, {Header:"거래상태", 			Type:"Text",		SaveName:"vndrStatCodeName",			Width: 100, Align:"Center", 	Edit:0, Sort:0}
				, {Header:"기본수수료율", 		Type:"Text",		SaveName:"dfltCmsnRate",				Width: 40, Align:"Center", 	Edit:0, Hidden:1}
				, {Header:"기본수수료율", 		Type:"Text",		SaveName:"dfltCmsnRateText",			Width: 100, Align:"Center", 	Edit:0}
				, {Header:"예외수수료적용여부", 	Type:"Text",		SaveName:"exceptionCommissionApplyYn",	Width: 110, Align:"Center", 	Edit:0, Sort:0}
				, {Header:"임직원할인적용여부", 	Type:"Text",		SaveName:"employeeDiscountApplyYn",		Width: 110, Align:"Center", 	Edit:0, Sort:0}
				, {Header:"최종수정자", 		Type:"Text",		SaveName:"moderName",			Width: 150, Align:"Center", 	Edit:0, Sort:0}
				, {Header:"최초등록일시", 		Type:"Date",		SaveName:"rgstDtm",				Width: 135, Align:"Center", 	Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
				, {Header:"최종수정일시", 		Type:"Date",		SaveName:"modDtm",				Width: 135, Align:"Center", 	Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
		];


		// Grid 초기화
		IBS_InitSheet(vendorInfoSheet, initVendorInfoSheet);
		// Grid 건수 정보 표시
		vendorInfoSheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		vendorInfoSheet.SetPagingPosition(2);
		// Grid width 자동 조절
	//	vendorInfoSheet.FitColWidth();
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		vendorInfoSheet.SetExtendLastCol(1);

	}

	/**
	 * 관리자 목록 Action관리
	 */
	_vendorInfo.doActionVendorInfo = function(sAction){
		switch(sAction) {
			// 조회
			case "search" :
				if(!_vendorInfo.searchValidation()){
					return;
				}

				var pageCount = $('#pageCount').val();

				var param = { url : "/vendor/info/read-list"
					, onePageRow : pageCount
					, subparam : FormQueryStringEnc(document.vendorForm)
					, CPage : $('#listPageNum').val()
					, sheet : "vendorInfoSheet" };

				DataSearchPaging(param);
				$('#listPageNum').val('1');
				break;
		}
	}

	_vendorInfo.searchValidation = function(sAction){
		var searchDateKey = $("#searchDateKey option:selected").val();

		if(!abc.date.searchValidate()){
			return false;
		}
		if(abc.text.allNull(searchDateKey) && !abc.text.allNull($("#fromDate").val())){
			alert("기간검색을 선택하세요");
			$("#searchDateKey").focus();
			return false;
		}

		var selChnnlNoArr = new Array();
		$("input[id^=chkChannel_]:checked").each(function(){
			selChnnlNoArr.push($(this).val());
		});
		$("#selChnnlNoArr").val(selChnnlNoArr);

		return true;
	}

	// 화면 이벤트
	_vendorInfo.event = function(){
		//검색어 change event
		$("#searchKey").change(function(){
			$("#searchKey option").each(function(){
				if(!abc.text.allNull($(this).val())){
					$("#"+$(this).val()).val("");
				}
			})
		});

		//전시체널 전체선택
		$("#channelCheckAll").click(function(){
			$("input[custom=chnnlNo]").prop("checked", this.checked);
		});

		$("input[custom=chnnlNo]").click(function(){
			if(this.checked == false){
				$("#channelCheckAll").prop("checked", false);
			}else if($("input[custom=chnnlNo]:checkbox:checked").length == $("input[custom=chnnlNo]").length){
				$("#channelCheckAll").prop("checked", true);
			}
		});


		// 초기화
		$("#vendorFormReset").click(function(){
			$('#vendorForm')[0].reset();
			$("#vndrBrandNo").val("");
			$("a[name^=per1Month]").trigger("click");
		});

		// 담당MD찾기 팝업
		$("#searchChargeMdNo").click(function(){
			abc.adminSearchPopup('adminSearchPopupResult');
		});

		// 입점사 검색
		$("#vendorInfoSerch").click(function(){
			_vendorInfo.doActionVendorInfo("search");
		});

		// 입점사 등록이동
		$("#vendorInfoRegist").click(function(){
			var url = "/vendor/info/create-form";
			$(location).attr('href', url);
		});

		// 캘린더 버튼 설정(일)
		$("a[name^=perToday]").click(function(){
			abc.date.days(this);
		});

		// 캘린더 버튼 설정(주)
		$("a[name^=perWeek]").click(function(){
			abc.date.week(this);
		});

		// 캘린더 버튼 설정(월)
		$("a[name^=per1Month]").click(function(){
			abc.date.month(this);
		});

		// 캘린더 버튼 설정(1년)
		$("a[name^=perYear]").click(function(){
			abc.date.year(this);
		});

		//기간검색 세팅
		$("a[name^=per1Month]").trigger("click");

		// 검색어 초기화
//		$("#searchKey").change(function(){
//			$("#searchValue").val("");
//		});

		//엑셀 다운로드
		$("#vendorInfoExcelDown").click(function(){

			if(!_vendorInfo.searchValidation()){
				return;
			}

			vendorForm.action = "/vendor/info/excel-down";
			vendorForm.method="post";
			vendorForm.submit();

		});

		$("#sendVendorSmsAll").click(function(){
			var param = {};
			param.tmsGubun = "ALL";
			abc.sendVendorSmsPop(param);
		});

		$("#sendVendorSmsSel").click(function(){
			var chkList = vendorInfoSheet.FindCheckedRow("chk").split("|");
			if(abc.text.allNull(chkList[0])){
				alert("선택된 입점사가 없습니다.");
				return;
			}

			var vendorInfoList = new Array();
			for(var i in chkList){
				vendorInfoList.push({
					vndrNo  : vendorInfoSheet.GetRowData(chkList[i]).vndrNo
				})
			}

			abc.sendVendorSmsPop(vendorInfoList);
		});

		$("#sendVendorEmailAll").click(function(){
			var param = {};
			param.tmsGubun = "ALL";
			abc.sendVendorEmailPop(param);
			});

		$("#sendVendorEmailSel").click(function(){
			var chkList = vendorInfoSheet.FindCheckedRow("chk").split("|");
			if(abc.text.allNull(chkList[0])){
				alert("선택된 입점사가 없습니다.");
				return;
			}

			var vendorInfoList = new Array();
			for(var i in chkList){
				vendorInfoList.push({
					vndrNo  : vendorInfoSheet.GetRowData(chkList[i]).vndrNo
				})
			}

			abc.sendVendorEmailPop(vendorInfoList);
			});

		//브랜드 찾기 팝업
		$("#serchBrandPop").click(function(){
			abc.brandSearchPopup(false, "abc.biz.vendor.info.callbackFindBrand");
		});

		// enter 검색
		$(".search-wrap").find(".ui-input, .js-ui-cal").on("keypress", function(e) {
			if(e.keyCode === 13) {
				$("#vendorInfoSerch").trigger("click");
		    }
		});

	}

	/**
	 * 브랜드 찾기 callback 이벤트
	 */
	_vendorInfo.callbackFindBrand = function(data) {
		$("#vndrBrandNo").val(data[0].brandNo);
		$("#brandName").val(data[0].brandName);
	}

})();