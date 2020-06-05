(function() {

	var _eventPopup = abc.object.createNestedObject(window,"abc.biz.promotion.event.popup");
	
	/**
	 * 초기화
	 */
	_eventPopup.init = function(){
		console.log('init');
		_eventPopup.sheet.init();
		_eventPopup.event();
		_eventPopup.getList();
		
		abc.biz.display.common.searchFormCalendarBtnEvent('#startYmd', '#endYmd', '.area-calendar-btn-group');
		
		_eventPopup.setMonth();
	}
	
	/**
	 *  IBSheet 초기화
	 */
	_eventPopup.sheet = {};
	_eventPopup.sheet.init = function(){
		createIBSheet2(document.getElementById("eventSheet"), "list", "100%", "370px");
		
		var initSheet = {};
		var pageCount = $('#pageCount').val();

		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, DeferredVScroll:1, AutoFitColWidth: 'init'};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:false};
		
		initSheet.Cols = [
			 {Header : "번호", Type:"Seq", SaveName:"", Width: 10, Align:"Center", 	Edit:0, Sort:0}
			, {Header :"이벤트 번호" , Type:"Text", SaveName:"eventNo", Width: 15,  Align:"Center", Edit:0, FontBold:1}
			, {Header :"이벤트명" , Type:"Text", SaveName:"eventName", Width: 20,  Align:"Center", Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer"}
			, {Header :"유형" , Type:"Text", SaveName:"eventTypeCodeName", Width: 15,  Align:"Center", Edit:0}
			, {Header : "디바이스" , Type:"Text", SaveName:"deviceCodeName", Width: 15,  Align:"Center", Edit:0, FontBold:1}
			, {Header : "대상 회원" , Type:"Text", SaveName:"memberTypeCodeName", Width: 15,  Align:"Center", Edit:0, FontBold:1}
			, {Header : "채널" , Type:"Text", SaveName:"chnnlName", Width: 10,  Align:"Center", Edit:0, FontBold:1}
			, {Header : "전시여부",	Type : "Combo",	SaveName : "dispYn",	Width : 15,	Align : "Center",	Edit : 0,	ComboText : "전시|전시안함",	ComboCode : "Y|N"}
			/*, {Header : "사용여부",	Type : "Combo",	SaveName : "useYn",	Width : 15,	Align : "Center",	Edit : 0,	ComboText : "사용|사용안함",	ComboCode : "Y|N"}*/
			, {Header : "진행상태" , Type:"Combo", SaveName:"eventProgressStauts", Width: 15,  Align:"Center", Edit:0, ComboText : "진행|대기|종료",	ComboCode : "ing|wait|end"}
			, {Header : "시작일", Type:"Date", SaveName:"eventStartDtm", Width: 20, Align:"Center", Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			, {Header : "종료일", Type:"Date", SaveName:"eventEndDtm", Width: 20, Align:"Center", Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			/*, {Header : "최초 작성자", Type:"Text", SaveName:"rgsterName", Width: 20, Align:"Center", Edit:0}
			, {Header : "최초 등록일시", Type:"Date", SaveName:"rgstDtm", Width: 20, Align:"Center", Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			, {Header : "최종 수정자", Type:"Text", SaveName:"moderName", Width: 20, Align:"Center", Edit:0}
			, {Header : "최종 수정일시", Type:"Date", SaveName:"modDtm", Width: 20, Align:"Center", Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}*/
			, {Header : "", Type:"Text", SaveName:"eventTypeCode", Width: 20, Align:"Center", Edit:0, Hidden:1}
			, {Header : "", Type:"Date", SaveName:"przwrPblcTodoYmd", Width: 20, Align:"Center", Edit:0, Hidden:1}
		];
		
		IBS_InitSheet(list , initSheet);
		list.SetCountPosition(3);
		list.SetPagingPosition(2);
		list.FitColWidth();
		list.SetExtendLastCol(1);
		
		_eventPopup.sheet.event();
		
	}
	
	/**
	 * IBSheet 이벤트
	 */
	_eventPopup.sheet.event = function(){
		list_OnClick = function(row, col, value, cellX, cellY, cellW, cellH, rowtype){
			if ( row != 0) {
				if ( list.ColSaveName(col) == "eventName" && value != "" ) {
					var result = {eventNo : list.GetRowData(row).eventNo, eventName : list.GetRowData(row).eventName, eventTypeCode : list.GetRowData(row).eventTypeCode,
							przwrPblcTodoYmd : list.GetRowData(row).przwrPblcTodoYmd};
					
					// callback 실행
					var callback = _eventPopup.parameter.getCallback();
					if(typeof callback === "function") {
						callback(result);
						self.close();
					} else {
						alert("callback 함수를 찾을 수 없습니다.");
					}
				}
			}
		}
	}
	
	/**
	 * 이벤트
	 */
	_eventPopup.event = function(){
		//검색
		$(document.forms.searchForm).on('submit', function(e){
			_eventPopup.getList();
			return false;
		});
		
		$('.inputCalendar').on('change', function(){
			_eventPopup.yearCheck(1);
			$(this).focus();
		});
		
		/** 초기화 */
		$('#clear-form').on('click', function() {
			abc.biz.display.common.initFormData('searchForm', false);
			
			_eventPopup.setMonth();
		});
	}
	
	_eventPopup.parameter = {};
	
	/**
	 * 콜백함수 반환
	 */
	_eventPopup.parameter.getCallback = function() {
		var callback = _eventPopup.parameter.getParameter("callback");
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
	_eventPopup.parameter.getParameter = function(findParamName) {
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
	_eventPopup.getList = function(){
		if(_eventPopup.yearCheck(1)) {
			var pageCount = $('#pageCount').val(); // 한페이지내 결과 로우 갯수
			var param = { url : "/promotion/event/list/read"
				, onePageRow : pageCount
				, subparam : FormQueryStringEnc(document.forms.searchForm)
				, sheet : "list" };
			
			DataSearchPaging(param);
		}
		
	}
	
	_eventPopup.setMonth = function() {
		var today = new Date();
		today.setMonth(today.getMonth()-1);
		
		$('#startYmd').val(today.format('yyyy.MM.dd'));
		$('#endYmd').val(new Date().format('yyyy.MM.dd'));
	}
	
	_eventPopup.yearCheck = function(compareYear) {
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
		_eventPopup.init();
	});
	
})();