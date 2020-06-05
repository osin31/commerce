(function() {

	var _eventResultMemberPopup = abc.object.createNestedObject(window,"abc.biz.promotion.event.result.member.popup");
	
	/**
	 * 초기화
	 */
	_eventResultMemberPopup.init = function(){
		console.log('init');
		_eventResultMemberPopup.sheet.init();
		_eventResultMemberPopup.event();
		_eventResultMemberPopup.getList();
		
		abc.biz.display.common.searchFormCalendarBtnEvent('#startYmd', '#endYmd', '.area-calendar-btn-group');
		
		_eventResultMemberPopup.setMonth();
	}
	
	/**
	 *  IBSheet 초기화
	 */
	_eventResultMemberPopup.sheet = {};
	_eventResultMemberPopup.sheet.init = function(){
		createIBSheet2(document.getElementById("eventResultMemberSheet"), "list", "100%", "370px");
		
		var initSheet = {};
		var pageCount = $('#pageCount').val();

		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, DeferredVScroll:1, AutoFitColWidth: 'init'};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		
		initSheet.Cols = [
			{Header:"", Type:"CheckBox", SaveName:"checkResult",	Width: 8, Align:"Center",Sort:0}
			, {Header:"", Type:"Text", SaveName:"evEventJoinMemberSeq",	Width: 20, Align:"Center",Sort:0,Hidden:1}
			, {Header:"", Type:"Text", SaveName:"memberNo",	Width: 20, Align:"Center",Sort:0,Hidden:1}
			, {Header : "당첨여부", Type:"Text", SaveName:"winYnName", Width: 10, Align:"Center", 	Edit:0, Sort:0}
			, {Header : "번호", Type:"Seq", SaveName:"", Width: 10, Align:"Center", 	Edit:0, Sort:0}
			, {Header : "회원ID", Type:"Text", SaveName:"loginIdMask", Width: 10, Align:"Center", 	Edit:0, Sort:0}
			, {Header : "회원등급" , Type:"Text", SaveName:"memberTypeCodeName", Width: 15,  Align:"Center", Edit:0, FontBold:1}
			, {Header : "디바이스" , Type:"Text", SaveName:"deviceCodeName", Width: 15,  Align:"Center", Edit:0, FontBold:1}
			, {Header : "채널" , Type:"Text", SaveName:"chnnlName", Width: 10,  Align:"Center", Edit:0, FontBold:1}
			, {Header : "쿠폰명" , Type:"Text", SaveName:"cpnName", Width: 10,  Align:"Center", Edit:0, FontBold:1}
			, {Header : "입력ID/이름" , Type:"Text", SaveName:"addInfo1Mask", Width: 10,  Align:"Center", Edit:0, FontBold:1}
			, {Header : "등록일", Type:"Date", SaveName:"joinDtm", Width: 20, Align:"Center", Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
		];
		
		IBS_InitSheet(list , initSheet);
		list.SetCountPosition(3);
		list.SetPagingPosition(2);
		list.FitColWidth();
		list.SetExtendLastCol(1);
		
		_eventResultMemberPopup.sheet.event();
		
	}
	
	/**
	 * IBSheet 이벤트
	 */
	_eventResultMemberPopup.sheet.event = function(){
		
		list_OnClick = function(row, col, value, cellX, cellY, cellW, cellH, rowtype){
			if ( row != 0) {
				/*if ( list.ColSaveName(col) == "eventName" && value != "" ) {
					//상세페이지 이동
					location.href = "/promotion/event/join/detail?eventNo=" + list.GetRowData(row).eventNo;
				}*/
			}
		}
		
	}
	
	/**
	 * 이벤트
	 */
	_eventResultMemberPopup.event = function(){
		//검색
		$(document.forms.searchForm).on('submit', function(e){
			_eventResultMemberPopup.getList();
			return false;
		});
		
		
		$('#save-result-member').on('click', function(e) {
			var chkCnt = list.CheckedRows("checkResult");
			
			var result = [];
			var resultList = list.ExportData({ Type : "json" }).data;
			
			if(resultList.length > 0) {
				for(var i=0 ; i<resultList.length ; i++) {
					if(resultList[i].checkResult == 1) {
						result.push(resultList[i]);
					}
				}
			} else {
				alert("조회된 데이터가 없습니다.");
				return;
			}

			// callback 실행
			var callback = _eventResultMemberPopup.parameter.getCallback();
			
			//호출한 index
			var trIdxName = _eventResultMemberPopup.parameter.getParameter("trIdxName");
			if(typeof callback === "function") {
				if(result.length > 0) {
					callback(result, trIdxName);
					self.close();
				} else {
					alert("선택된 데이터가 없습니다.");
				}
			} else {
				alert("callback 함수를 찾을 수 없습니다.");
			}
		});
		
		$('.inputCalendar').on('change', function(){
			_eventPopup.yearCheck(1);
			$(this).focus();
		});
		
		$('#pageCount').on('change', function() {
			_eventResultMemberPopup.getList();
		});
	}
	
	_eventResultMemberPopup.parameter = {};
	
	/**
	 * 콜백함수 반환
	 */
	_eventResultMemberPopup.parameter.getCallback = function() {
		var callback = _eventResultMemberPopup.parameter.getParameter("callback");
		var result = null;
		
		if(callback) {
			callback = "window.opener." + callback;
			result = new Function("return " + callback)();
		}
		return result;
	}
	
	/**
	 * QueryString에서 해당 key에 대한 값을 반환합니다.
	 */
	_eventResultMemberPopup.parameter.getParameter = function(findParamName) {
		var queryString = window.location.search.slice(1);
		var queryArray = queryString.split("&");
		var result = null;
		
		for(var i=0 ; i<queryArray.length ; i++) {
			var query = queryArray[i].split("=");
			if(query.length === 2) {
				if(query[0] === findParamName) {
					result = query[1];
					break;
				}
			} else {
				console.log("파라미터 key/value 형태가 올바르지 않습니다. " + queryArray[i]);
			}
		}
		return result;
	}
	
	/**
	 * 리스트 조회
	 */
	_eventResultMemberPopup.getList = function(){
		if(_eventResultMemberPopup.yearCheck(1)) {
			var pageCount = $('#pageCount').val(); // 한페이지내 결과 로우 갯수
			var param = { url : "/promotion/event/join/list/read"
				, onePageRow : pageCount
				, subparam : FormQueryStringEnc(document.forms.searchForm)
				, sheet : "list" };
			
			DataSearchPaging(param);
		}
	}
	
	_eventResultMemberPopup.setMonth = function() {
		var today = new Date();
		today.setMonth(today.getMonth()-1);
		
		$('#startYmd').val(today.format('yyyy.MM.dd'));
		$('#endYmd').val(new Date().format('yyyy.MM.dd'));
	}
	
	_eventResultMemberPopup.yearCheck = function(compareYear) {
		var yearCheck = true;
		var startYmd = $('#startYmd').val();
		var endYmd = $('#endYmd').val();
		var startDate = '';
		var endDate = '';
		var betweenDate = '';
		if(startYmd != '' && endYmd != '') {
			startYmd = startYmd.split('.');
			endYmd = endYmd.split('.');
		} else {
			return yearCheck;
		}
		
		startDate = new Date(startYmd[0], startYmd[1], startYmd[2]);
		endDate = new Date(endYmd[0], endYmd[1], endYmd[2]);
		betweenDate = (endDate.getTime() - startDate.getTime())/1000/60/60/24;
		
		if(betweenDate < 0) {
			alert('시작일은 종료일 이전 날짜만 가능합니다.');
			yearCheck = false;
		} else if(betweenDate > (compareYear * 365)) {
			alert('조회기간은 최대' + compareYear + '년까지 가능합니다.');
			yearCheck = false;
		}
		
		return yearCheck;
	}
	
	$(function() {
		_eventResultMemberPopup.init();
	});
	
})();