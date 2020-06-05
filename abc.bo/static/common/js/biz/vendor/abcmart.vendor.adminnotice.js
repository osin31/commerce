/**
 * 관리자 공지와 관련된 함수 정의.
 */
(function() {

	var _adminnotice = abc.object.createNestedObject(window,"abc.biz.vendor.adminnotice");


	/*************************************************************************
	 *								관리자 공지 목록
	 *************************************************************************/

	/**
	 * 관리자 공지 목록 초기 세팅
	 */
	_adminnotice.initAdminNoticeSheet = function() {
		//Grid 객체 생성
		createIBSheet2(document.getElementById("noticeGrid"), "adminnoticeSheet", "100%", "370px");

		// Grid 초기화 기본/헤더/컬럼선언
		var initSheet = {};
		var pageCount = $('#pageCount').val();

		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, DeferredVScroll:1, AutoFitColWidth: 'init'};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initSheet.Cols = [
			{Header:"" ,			Type:"Status",	SaveName:"status",				Width: 0,	Align:"Left", 	Edit:0, Hidden:1}
			, {Header:"",			Type:"Int",		SaveName:"adminNotcSeq",		Width: 5,	Align:"",		Edit:0, Hidden:1}
			, {Header:"",			Type:"Text",	SaveName:"alwaysPopupYn",		Width: 5,	Align:"",		Edit:0, Hidden:1}
			, {Header:"",			Type:"DelCheck",SaveName:"deleteCheck",			Width: 5,	Align:"Center",	Edit:1, Sort:0}
			, {Header:"번호",	 		Type:"Seq",		SaveName:"",					Width: 5,	Align:"Center", Edit:0, Sort:0}
			, {Header:"대상 시스템", 	Type:"Text",	SaveName:"applySystemType",		Width: 10,	Align:"Center", Edit:0, Sort:0}
			, {Header:"제목", 		Type:"Text",	SaveName:"notcTitleText",		Width: 30,	Align:"Left", 	Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer", Sort:1}
			, {Header:"관리자메인팝업",	Type:"Combo",	SaveName:"popupYn",				Width: 10,	Align:"Center", Edit:0, Sort:0, ComboText : "적용|적용안함",	ComboCode : "Y|N"}
			, {Header:"전시여부", 		Type:"Combo",	SaveName:"dispYn",				Width: 10,	Align:"Center", Edit:0, Sort:0, ComboText : "전시|전시안함",	ComboCode : "Y|N"}
			, {Header:"작성자", 		Type:"Text",	SaveName:"listDispRegstName",	Width: 10,	Align:"Center", Edit:0, Sort:0}
			, {Header:"작성일시", 		Type:"Date",	SaveName:"rgstDtm",				Width: 20,	Align:"Center", Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN, Sort:1}
		];

		//Grid 초기화
		IBS_InitSheet(adminnoticeSheet, initSheet);
		// Grid 건수 정보 표시
		adminnoticeSheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		adminnoticeSheet.SetPagingPosition(2);
		// Grid width 자동 조절
		adminnoticeSheet.FitColWidth();
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		adminnoticeSheet.SetExtendLastCol(1);
	}

	/**
	 * 그리드 Click 이벤트
	 */
	_adminnotice.adminnoticeSheetOnClick = function(Row, Col, Value, CellX, CellY, CellW, CellH){

		if ( Row != 0) {
			if ( adminnoticeSheet.ColSaveName(Col) == "notcTitleText" && Value != "" ) {
				//관리자 공지 상세 화면 호출
				var pageNum = adminnoticeSheet.GetCurrentPage();
				
				$("#adminNotcSeqMain").val(adminnoticeSheet.GetRowData(Row).adminNotcSeq);
				$("#listPageNum").val(pageNum);
				
				adminnoticeSheet.GetRowData(Row).adminNotcSeq
				frmSearch.action = "/vendor/admin-notice/read-detail";
				frmSearch.submit();
			}
		}
	}

	_adminnotice.adminnoticeSheetOnSaveEnd = function(Code, Msg, StCode, StMsg, Response){
		if(StCode == "200"){
			alert("삭제하였습니다.");
		}else{
			alert("에러가 발생하였습니다.");
		}
		_adminnotice.adminnoticeDoAction("search");
	}
	
	_adminnotice.adminnoticeSheetOnRowSearchEnd = function(row){
		var adminNotice = adminnoticeSheet.GetRowData(row);
		if(adminNotice.alwaysPopupYn == abc.consts.BOOLEAN_TRUE){
			adminnoticeSheet.SetRowData(row, {popupYn : abc.consts.BOOLEAN_TRUE});
		}
	}


	_adminnotice.adminnoticeDoAction = function(sAction){
		switch(sAction) {

		case "search" :

			//관리자 메인 팝업 라디오버튼
			if($("#radioUseMainPopupAll").is(":checked") == true){
				$("#popupYn").val('');
			} else if($("#radioUseMainPopupUse").is(":checked") == true){
				$("#popupYn").val('Y');

			} else if($("#radioUseMainPopupNone").is(":checked") == true){
				$("#popupYn").val('N');
			}

			//<%-- 전시여부 라디오버튼 --%>
			if($("#radioUseDisplayAll").is(":checked") == true){
				$("#dispYn").val('');
			} else if($("#radioUseDisplayUse").is(":checked") == true){
				$("#dispYn").val('Y');
			} else if($("#radioUseDisplayNone").is(":checked") == true){
				$("#dispYn").val('N');
			}

			//<%-- 검색어 값 입력 후, 검색 시 히든값 설정 --%>
			var searchSelectBox = $("#searchSelectBox option:selected").val();
			var searchInputBox  = $("#searchInputBox").val();
			
			if(!abc.text.allNull(searchInputBox)){
				if(abc.text.allNull(searchSelectBox)){
					alert("검색어 구분값을 선택해주세요.");
					$("#searchSelectBox").focus();
					return ;
				}
			}

			if(searchSelectBox == "notcTitleText" && searchInputBox != ""){
				$("#notcTitleText").val(searchInputBox);
			} else if(searchSelectBox == "loginId" && searchInputBox != ""){
				$("#loginId").val(searchInputBox);
			} else if(searchSelectBox == "adminName" && searchInputBox != ""){
				$("#adminName").val(searchInputBox);
			}

			
			adminnoticeSheet.SetPageCount($("#pageCount").val());
			var pageCount = $("#pageCount").val();
			var listPageNum = $("#listPageNum").val();

			var param = { url : "/vendor/admin-notice/read-list"
				, onePageRow : pageCount
				, subparam : FormQueryStringEnc(document.frmSearch)
				, CPage : listPageNum
				, sheet : "adminnoticeSheet" };

			DataSearchPaging(param);
			$("#listPageNum").val("1");

			break;

		case "delete" :
			var checkCnt = adminnoticeSheet.FindCheckedRow("deleteCheck");
			
			if(checkCnt < 1){
				alert("선택 항목이 없습니다.");
				return false;
			}
			
			var param = { url : "/vendor/admin-notice/delete"
				, subparam:FormQueryStringEnc(document.frmSearch)
				, sheet:"adminnoticeSheet" };

			DataSave(param);

			break;
		}
	}

	/*************************************************************************
	 *								관리자 공지 상세/수정
	 *************************************************************************/


	_adminnotice.adminNotcSeq = "";
	_adminnotice.adminName 	  = "";
	_adminnotice.rgstDtm  	  = "";

	_adminnotice.applySystemType = "";
	_adminnotice.notcTitleText	 = "";
	_adminnotice.popupYn		 = "";
	_adminnotice.alwaysPopupYn	 = "";
	_adminnotice.popupStartYmd	 = "";
	_adminnotice.popupEndYmd	 = "";
	_adminnotice.dispYn			 = "";

	_adminnotice.prevnotcContText = "";

	/**
	 * 관리자 공지 상세 화면 호출시, db칼럼데로 세팅하는 메소드
	 */
	_adminnotice.setAdminNoticeDetail = function() {

		$("#selectApplySystem > option[value=" + _adminnotice.applySystemType +"]").attr("selected", "true");

		if(_adminnotice.applySystemType == 'P'){
			$("#rdoSystemRangeAll").parent().parent().show();
			$("#rdoSystemRangeVendor").parent().parent().show();
			$("#btnVendorSearch").show();
		}

		if(_adminnotice.popupYn == 'Y'){
			$("#chkUseMainNotice").prop('checked', true);
			$("#popupStartYmd").attr('disabled', false);
			$("#popupEndYmd").attr('disabled', false);
		}else{
			$("#chkUseMainNotice").prop('checked', false) ;
			$("#popupStartYmd").attr('disabled', true);
			$("#popupEndYmd").attr('disabled', true);
		}

		if(_adminnotice.alwaysPopupYn == 'Y'){
			$("#chkUseMainNoticeAlways").prop('checked', true);
		}else{
			$("#chkUseMainNoticeAlways").prop('checked', false);
		}

		if(_adminnotice.popupStartYmd != null && _adminnotice.popupStartYmd != "" && _adminnotice.popupStartYmd != undefined){
			var yyyy = _adminnotice.popupStartYmd.substring(0,4);
			var mm   = _adminnotice.popupStartYmd.substring(5,7);
			var dd   = _adminnotice.popupStartYmd.substring(8,10);
			$("#popupStartYmd").val(yyyy + "." + mm + "." + dd);
		}

		if(_adminnotice.popupEndYmd != null && _adminnotice.popupEndYmd != "" && _adminnotice.popupEndYmd != undefined){
			var yyyy = _adminnotice.popupEndYmd.substring(0,4);
			var mm   = _adminnotice.popupEndYmd.substring(5,7);
			var dd   = _adminnotice.popupEndYmd.substring(8,10);
			$("#popupEndYmd").val(yyyy + "." + mm + "." + dd);
		}

		if(_adminnotice.dispYn == 'Y'){
			$("#rdoUseDisplayY").prop('checked', true);
			$("#rdoUseDisplayN").prop('checked', false);
		}else{
			$("#rdoUseDisplayY").prop('checked', false);
			$("#rdoUseDisplayN").prop('checked', true);
		}
	}


	/**
	 * 미리보기 팝업창 호출
	 */
	_adminnotice.previewDetail = function() {
		var url = "/vendor/admin-notice/read-detail-prev-pop";
		var params = {};
		// 관리자 공지 seq
		params.adminNotcSeq = _adminnotice.adminNotcSeq;
		// 대상 시스템
		params.applySystemType = $("#applySystemType").val();
		// 제목
		params.notcTitleText = $("#notcTitleText").val();
		// 작성자 이름
		params.adminName = _adminnotice.adminName;
		// 작성일시
		params.rgstDtm = _adminnotice.rgstDtm;
		// 내용
		params.notcContText = CKEDITOR.instances.notcContText.getData();
		// 전시여부
		params.popupYn = $("#popupYn").val();

		abc.open.popup({
			url 	:	url,
			winname :	"prevParams",	//여러 팝업 창이나 현재 팝업창 조작이 필요할 경우 직접 이 값으로 접근 할 수도 있다.
			method	: 	"post",
			title 	:	"adminNoticePreview",
			width 	:	800,
			height	:	803,
			params	:	params
		});
	}

	/**
	 * 초기화 버튼 클릭 시 호출되는 메소드
	 */
	_adminnotice.resetForm = function() {

		//대상 시스템 초기화
		$("#applySystemType").val(_adminnotice.applySystemType);
		$("#selectApplySystem").val(_adminnotice.applySystemType).prop("selected",true);
		if(_adminnotice.applySystemType == 'P'){
			$("#rdoSystemRangeAll").parent().parent().show();
			$("#rdoSystemRangeVendor").parent().parent().show();
			$("#btnVendorSearch").show();
		}else{
			$("#rdoSystemRangeAll").parent().parent().hide();
			$("#rdoSystemRangeVendor").parent().parent().hide();
			$("#btnVendorSearch").hide();
		}
		//제목 초기화
		$("#notcTitleText").val(_adminnotice.notcTitleText);
		//내용 초기화
		CKEDITOR.instances.notcContText.setData($("#notcContText").text())
		//관리자 메인 팝업 popYn 초기화
		$("#popupYn").val(_adminnotice.popupYn);

		if(_adminnotice.popupYn == 'Y') {
			$("#chkUseMainNotice").prop('checked', true);
			$("#popupStartYmd").attr('disabled', false);
			$("#popupEndYmd").attr('disabled', false);
		}
		else {
			$("#chkUseMainNotice").prop('checked', false);
			$("#popupStartYmd").attr('disabled', true);
			$("#popupEndYmd").attr('disabled', true);
		}
		//팝업 시작일/종료일 초기화
		if(_adminnotice.popupStartYmd != null && _adminnotice.popupStartYmd != "" && _adminnotice.popupStartYmd != undefined){
			var yyyy = _adminnotice.popupStartYmd.substring(0,4);
			var mm   = _adminnotice.popupStartYmd.substring(4,6);
			var dd   = _adminnotice.popupStartYmd.substring(6,8);
			$("#popupStartYmd").val(yyyy + "." + mm + "." + dd);
		}else{
			$("#popupStartYmd").val("");
		}
		if(_adminnotice.popupEndYmd != null && _adminnotice.popupEndYmd != "" && _adminnotice.popupEndYmd != undefined){
			var yyyy = _adminnotice.popupEndYmd.substring(0,4);
			var mm   = _adminnotice.popupEndYmd.substring(4,6);
			var dd   = _adminnotice.popupEndYmd.substring(6,8);
			$("#popupEndYmd").val(yyyy + "." + mm + "." + dd);
		}else{
			$("#popupEndYmd").val("");
		}
		//상시팝업 여부 초기화
		$("#popupYn").val(_adminnotice.popupYn);
		if(_adminnotice.alwaysPopupYn == 'Y') { $("#chkUseMainNoticeAlways").prop('checked', true); }
		else { $("#chkUseMainNoticeAlways").prop('checked', false); }
		//전시여부 초기화
		$("#dispYn").val(_adminnotice.dispYn);
		if(_adminnotice.dispYn == 'Y'){
			$("#rdoUseDisplayY").prop('checked', true);
			$("#rdoUseDisplayN").prop('checked', false);
		}else{
			$("#rdoUseDisplayY").prop('checked', false);
			$("#rdoUseDisplayN").prop('checked', true);
		}

	}

	/**
	 * 수정된 관리자 공지 내용을 저장하는 메소드
	 */
	_adminnotice.modifyAdminNotice = function() {
		//<%-- CKEDITOR 에디터 영역의 내용을 가져온다. --%>
		$("#notcContText").val(CKEDITOR.instances.notcContText.getData());

		if(abc.text.isBlank($("#notcTitleText").val())){
			alert("제목을 입력해 주십시요.");
			$("#notcTitleText").focus();
			return false;
		}
		if(abc.text.isLimitLength($("#notcTitleText").val(), 100)){
			alert("제목이 최대값을 초과하였습니다.");
			$("#notcTitleText").focus();
			return false;
		}
		
		$("#notcContText").val(CKEDITOR.instances.notcContText.getData());
		if(abc.text.isBlank($("#notcContText").val())){
			alert("내용을 입력해주세요.");
			CKEDITOR.instances.notcContText.focus();
			return false;
		}
		if(abc.text.isLimitLength($("#notcContText").val(), 4000)){
			alert("내용이 최대값을 초과하였습니다.");
			CKEDITOR.instances.notcContText.focus();
			return false;
		}
		
		// 날짜체크
		if(!_adminnotice.validationDate()) {
			return false;
		}

		if(!confirm("저장하시겠습니까?")){
			return false;
		}

		//<%-- datePicker를 이용한  팝업시작날짜와 끝날짜 디비칼럼에 맞게 변환 --%>
		var yyyyS = $("#popupStartYmd").val().substring(0,4);
		var mmS   = $("#popupStartYmd").val().substring(5,7);
		var ddS   = $("#popupStartYmd").val().substring(8,10);
		$("#popupStartYmd").val(yyyyS + mmS + ddS);

		var yyyyE = $("#popupEndYmd").val().substring(0,4);
		var mmE   = $("#popupEndYmd").val().substring(5,7);
		var ddE   = $("#popupEndYmd").val().substring(8,10);
		$("#popupEndYmd").val(yyyyE + mmE + ddE);

		if(Number(yyyyS) > Number(yyyyE) ||  Number(mmS) > Number(mmE) || Number(ddS) > Number(ddE)){
			alert("종료날짜가 시작날짜보다 먼저 올 수 없습니다.");
			$("#popupStartYmd").val('');
			$("#popupEndYmd").val('');
			return;
		}

		//<%-- 전시여부 라디오버튼 --%>
		if($("#rdoUseDisplayY").is(":checked") == true){
			$("#dispYn").val('Y');
		} else{
			$("#dispYn").val('N');
		}
		
		//<%-- ajax 전송 --%>
		var formData = new FormData($('#adminNoticeForm')[0]);

		$.ajax({
			processData: false,
            contentType: false,
			type :"post",
			url : "/vendor/admin-notice/update",
			data: formData
		})
		.done(function(data){
			if(data.result == "Y"){
				alert("저장되었습니다.");
			}
			location.href="/vendor/admin-notice";
		})
		.fail(function(e){
			alert("저장에 실패했습니다.");
		});
	}
	
	// 관리자 메인 팝업 기간적용시 날짜 체크
	_adminnotice.validationDate = function() {
		var result = true;
		
		if($("input[name=mainPopDtm]").is(":checked")) {
			if($("input[name=mainPopDtm]:checked").attr("id") === "chkUseMainNotice") {
				var toDate = $("#popupStartYmd");
				var fromDate = $("#popupEndYmd");
				
				if(abc.text.isBlank(toDate.val())) {
					alert("검색 시작일을 설정해주세요");
					toDate.focus();
					return false;
				}
				if(abc.text.isBlank(fromDate.val())) {
					alert("검색 종료일을 설정해주세요");
					fromDate.focus();
					return false;
				}
				if(abc.date.compareDate(toDate.val(), fromDate.val()) > 0){
					alert("시작일보다 이전날짜를 선택할 수 없습니다.");
					fromDate.focus();
					return false;
				}
				
			}
		}
		
		return result;
	}


	/*************************************************************************
	 *								관리자 공지 목록 - 팝업창
	 *************************************************************************/

	/**
	 * 관리자 공지 생성
	 */
	_adminnotice.createAdminNotice = function() {
		// 입점업체 선택시
		if($("input[name=rdoSystemRange]:checked").val() == "select" && $("input[name=vndrNo]").length <= 0) {
			alert("입점업체를 선택 하세요.");
			return false;
		}

		if(abc.text.isBlank($("#notcTitleText").val())){
			alert("제목을 입력해 주십시요.");
			$("#notcTitleText").focus();
			return false;
		}
		if(abc.text.isLimitLength($("#notcTitleText").val(), 100)){
			alert("제목이 최대값을 초과하였습니다.");
			$("#notcTitleText").focus();
			return false;
		}

		$("#notcContText").val(CKEDITOR.instances.notcContText.getData());

		if(abc.text.isBlank($("#notcContText").val())){
			alert("내용을 입력해주세요.");
			CKEDITOR.instances.notcContText.focus();
			return false;
		}
		if(abc.text.isLimitLength($("#notcContText").val(), 4000)){
			alert("내용이 최대값을 초과하였습니다.");
			CKEDITOR.instances.notcContText.focus();
			return false;
		}
		
		//날짜 체크
		if(!_adminnotice.validationDate()) {
			return false;
		}
		
		if(!confirm("저장하시겠습니까?")){
			return false;
		}

		//<%-- datePicker를 이용한  팝업시작날짜와 끝날짜 디비칼럼에 맞게 변환 --%>
		var yyyyS = $("#popupStartYmd").val().substring(0,4);
		var mmS   = $("#popupStartYmd").val().substring(5,7);
		var ddS   = $("#popupStartYmd").val().substring(8,10);
		$("#popupStartYmd").val(yyyyS + mmS + ddS);

		var yyyyE = $("#popupEndYmd").val().substring(0,4);
		var mmE   = $("#popupEndYmd").val().substring(5,7);
		var ddE   = $("#popupEndYmd").val().substring(8,10);
		$("#popupEndYmd").val(yyyyE + mmE + ddE);

		if(Number(yyyyS) > Number(yyyyE) ||  Number(mmS) > Number(mmE) || Number(ddS) > Number(ddE)){
			alert("종료날짜가 시작날짜보다 먼저 올 수 없습니다.");
			$("#popupStartYmd").val('');
			$("#popupEndYmd").val('');
			return;
		}

		//<%-- 전시여부 라디오버튼 --%>
		if($("#rdoUseDisplayY").is(":checked") == true){
			$("#dispYn").val('Y');
		} else{
			$("#dispYn").val('N');
		}

		//<%-- ajax 전송 --%>
        var formData = new FormData($('#adminNoticeForm')[0]);

		$.ajax({
			processData: false,
            contentType: false,
			type :"post",
			url : "/vendor/admin-notice/create",
			data: formData
		})
		.done(function(data){
			alert("저장하였습니다.");
			location.href = "/vendor/admin-notice";
		})
		.fail(function(e){
			alert("저장에 실패했습니다.");
		});
	}

	/**
	 * 관리자 공지 생성 중 미리보기 팝업화면 호출
	 */
	_adminnotice.popupPreCreateAdminNotice = function(){

		var url = "/vendor/admin-notice/create-prev-pop";
		var params = {};
		// 대상 시스템
		params.applySystemType = $("#applySystemType").val();
		// 제목
		params.notcTitleText = $("#notcTitleText").val();
		// 내용
		params.notcContText = CKEDITOR.instances.notcContText.getData();
		// 전시여부
		params.popupYn = $("#popupYn").val();

		abc.open.popup({
			url 	:	url,
			winname :	"prevParams",	//여러 팝업 창이나 현재 팝업창 조작이 필요할 경우 직접 이 값으로 접근 할 수도 있다.
			method	: 	"post",
			title 	:	"adminNoticePreview",
			width 	:	800,
			height	:	803,
			params	:	params
		});
	}

	/*************************************************************************
	 *								관리자 공지 목록 - 팝업창
	 *************************************************************************/

	/**
	 * 관리자 공지 목록 팝업 초기 세팅
	 */
	_adminnotice.initAdminNoticePopSheet = function() {

		createIBSheet2(document.getElementById("noticeGrid"), "adminnoticePopSheet", "100%", "440px");

		//Grid 초기화 기본/헤더/컬럼선언
		var initSheet = {};
		var pageCount = $('#pageCount').val();

		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, DeferredVScroll:1, AutoFitColWidth: 'init'};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initSheet.Cols = [
			  {Header:"",			Type:"Int",			SaveName:"adminNotcSeq",	Width: 5,  Align:"",		Edit:0, Hidden:1}
			, {Header:"번호",			Type:"Seq",			SaveName:"",	Width: 5,  Align:"Center",		Edit:0,		Sort:0}
//			, {Header:"대상 시스템", 	Type:"Text",		SaveName:"applySystemType",	Width: 12, Align:"Center", 	Edit:0}
			, {Header:"제목", 		Type:"Html",		SaveName:"notcTitleText",	Width: 30, Align:"Left", 	Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer"}
//			, {Header:"관리자메인팝업", Type:"Text",			SaveName:"popupYn",			Width: 12, Align:"Center", 	Edit:0}
//			, {Header:"전시여부", 		Type:"Text",		SaveName:"dispYn",			Width: 12, Align:"Center", 	Edit:0}
			, {Header:"작성자", 		Type:"Text",		SaveName:"dispRegstName",	Width: 12, Align:"Center", 	Edit:0}
			, {Header:"작성일시", 		Type:"Date",		SaveName:"rgstDtm",			Width: 20, Align:"Center", 	Edit:0, Format:"YmdHms"}
		];

		// Grid 초기화
		IBS_InitSheet(adminnoticePopSheet , initSheet);
		// Grid 건수 정보 표시
		adminnoticePopSheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		adminnoticePopSheet.SetPagingPosition(2);
		// Grid width 자동 조절
		adminnoticePopSheet.FitColWidth();
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		adminnoticePopSheet.SetExtendLastCol(1);
	}

	_adminnotice.adminnoticePopDoAction = function(sAction, fromDash){
		switch(sAction) {
		// 조회
		case "search" :
			
			// 검색어 값 입력 후, 검색 시 히든값 설정
			var searchSelectBox = $("#searchSelectBox option:selected").val();
			var searchInputBox  = $("#searchInputBox").val();

			if(searchSelectBox == "notcTitleText" && searchInputBox != ""){
				$("#notcTitleText").val(searchInputBox);
			} else if(searchSelectBox == "loginId" && searchInputBox != ""){
				$("#loginId").val(searchInputBox);
			} else if(searchSelectBox == "adminName" && searchInputBox != ""){
				$("#adminName").val(searchInputBox);
			}
			
			var url = "/vendor/admin-notice/read-list";
			var pageCount = $('#pageCount').val(); // 한페이지내 결과 로우 갯수
			
			// 대시보드에서 왔을때 BO/PO 구분에 따른 URL 변경 
			if(fromDash === "BO") {
				url = "/bo/dashboard/read-list";
			}else if(fromDash === "PO") {
				url = "/po/dashboard/read-list";
			}
			
			var param = { url : url
				, onePageRow : pageCount
				, subparam : FormQueryStringEnc(document.frmSearch)
				, sheet : "adminnoticePopSheet" };

			DataSearchPaging(param);

			break;
		}
	}	

	/**
	 * 오늘하루 그만보기
	 */
	_adminnotice.closePopupNotToday = function(adminNoticeSeq) {
		abc.cookie.setCookie('adminNoticeMainPop'+adminNoticeSeq,'Y', 1);
		window.close();
	}

//	_adminnotice.fileDownLoad = function(e) {
//		var downLoadFileName = $(e).find('[name=downLoadFileName]').val(); // 다운로드 파일명
//		var atchFilePathText = $(e).find('[name=atchFilePathText]').val(); // 저장 디렉토리 + 실제 저장된 파일명
//
//		atchFilePathText = atchFilePathText.split("\\").join("/");
//
//		location.href = "/noacl/defaultContentTypedownload?downLoadFileName=" + downLoadFileName + "&atchFilePathText=" + atchFilePathText;
//	}

})();