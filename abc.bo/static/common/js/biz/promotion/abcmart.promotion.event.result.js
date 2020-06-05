(function() {

	var _eventResult = abc.object.createNestedObject(window,"abc.biz.promotion.event.result");
	
	/**
	 * 초기화
	 */
	_eventResult.init = function(){
		
		abc.biz.display.common.searchFormCalendarBtnEvent('#startYmd', '#endYmd', '.area-calendar-btn-group');
		abc.biz.display.common.checkBoxAll({allId: '#chkDevice01', itemsClass: '[name=deviceCodes]'});
		abc.biz.display.common.checkBoxAll({allId: '#chkChannel01', itemsClass: '[name=chnnlNos]'});
		
		_eventResult.setMonth();
		
		abc.biz.display.common.setFormParameter.setForm($(document.forms.searchForm));
		abc.biz.display.common.setFormParameter.setParam();
		
		_eventResult.sheet.init();
		
		_eventResult.event();
		_eventResult.getList();
		
		
	}
	
	/**
	 *  IBSheet 초기화
	 */
	_eventResult.sheet = {};
	_eventResult.sheet.init = function(){
		createIBSheet2(document.getElementById("eventSheet"), "list", "100%", "370px");
		
		var initSheet = {};
		var pageCount = $('#pageCount').val();

		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, DeferredVScroll:1, /*AutoFitColWidth: 'init',*/ "MergeSheet": msHeaderOnly};
		initSheet.HeaderMode = {Sort:0, ColMove:0, ColResize:1};
		
		initSheet.Cols = [
			 {Header : "번호", 			Type:"Seq", 	SaveName:"", 					Width: 50, Align:"Center", 	Edit:0, Sort:0}
			/*, {Header : "당첨발표" , 	Type:"Combo", 	SaveName:"pblcYn", 				Width: 10,  Align:"Center", Edit:0, FontBold:1, ComboText : "발표|미발표",	ComboCode : "Y|N" , FontUnderline:1}*/
			, {Header : "당첨발표" , 		Type:"Text", 	SaveName:"eventResultName", 	Width: 330,  Align:"Left", Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer"}
			, {Header : "당첨자보기" , 		Type:"Text", 	SaveName:"resultMemberPop", 	Width: 80,  Align:"Center", Edit:0, FontBold:1, FontUnderline:1, DefaultValue:"보기", Cursor:"Pointer"}
			/*, {Header : "당첨자발표일", 	Type:"Date", 	SaveName:"pblcYmd", 			Width: 20, Align:"Center", Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}*/
			, {Header : "당첨자발표일", 	Type:"Date", 	SaveName:"przwrPblcTodoYmd", 	Width: 130, Align:"Center", Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			, {Header : "미리보기" , 		Type:"Button", 	SaveName:"", 					Width: 50,  Align:"Center", Edit:0, DefaultValue:"PC"}	 //추후 FO URL 추가
			, {Header : "미리보기" , 		Type:"Button", 	SaveName:"", 					Width: 80,  Align:"Center", Edit:0, DefaultValue:"MOBILE"} //추후 MO URL 추가
			, {Header :"이벤트 번호" , 		Type:"Text", 	SaveName:"eventNo", 			Width: 100,  Align:"Center", Edit:0, FontBold:1}
			/*, {Header :"이벤트명" , 		Type:"Text", 	SaveName:"eventName", 			Width: 20,  Align:"Left", Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer"}*/
			, {Header :"유형" , 			Type:"Text", 	SaveName:"eventTypeCodeName", 	Width: 110,  Align:"Center", Edit:0}
			, {Header : "디바이스" , 		Type:"Text", 	SaveName:"deviceCodeName", 		Width: 110,  Align:"Center", Edit:0, FontBold:1}
			, {Header : "채널" , 			Type:"Text", 	SaveName:"chnnlName", 			Width: 280,  Align:"Center", Edit:0, FontBold:1}
			/*, {Header : "당첨자수" , 	Type:"Text", 	SaveName:"winCount", 			Width: 10,  Align:"Center", Edit:0, FontBold:1}*/
			, {Header : "시작일", 		Type:"Date", 	SaveName:"eventStartDtm", 		Width: 130, Align:"Center", Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			, {Header : "종료일", 		Type:"Date", 	SaveName:"eventEndDtm", 		Width: 130, Align:"Center", Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			, {Header : "작성자", 		Type:"Text", 	SaveName:"rgsterInfo", 			Width: 130, Align:"Center", Edit:0}
			, {Header : "작성일시", 		Type:"Date", 	SaveName:"rgstDtm", 			Width: 130, Align:"Center", Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			, {Header : "수정자",			Type:"Text",	SaveName:"moderInfo", 			Width: 130, Align:"Center", Edit:0}
			, {Header : "수정일시", 		Type:"Date", 	SaveName:"modDtm", 				Width: 130, Align:"Center", Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			, {Header : "", 			Type:"Text", 	SaveName:"eventTypeCode", 		Width: 20, Align:"Center", Edit:0, Hidden:1}
		];
		
		IBS_InitSheet(list , initSheet);
		list.SetCountPosition(3);
		list.SetPagingPosition(2);
		/*list.FitColWidth();*/
		list.SetExtendLastCol(1);
		
		_eventResult.sheet.event();
		
	}
	
	/**
	 * IBSheet 이벤트
	 */
	_eventResult.sheet.event = function(){
		
		list_OnClick = function(row, col, value, cellX, cellY, cellW, cellH, rowtype){
			if ( row != 0) {
				if ( list.ColSaveName(col) == "eventName" && value != "" ) {
					//추후 front event 상세 url 이동 추가
					window.open("/promotion/event/detail?eventNo=" + list.GetRowData(row).eventNo);
				}
				if ( list.ColSaveName(col) == "eventResultName" && value != "" ) {
					abc.biz.display.common.setFormParameter.setDetailMove();
					//상세페이지 이동
					location.href = "/promotion/event/result/detail?eventNo=" + list.GetRowData(row).eventNo;
				}
				if ( list.ColSaveName(col) == "resultMemberPop" && value != "" ) {
					var params = {};
					params.eventNo = list.GetRowData(row).eventNo;
					params.eventTypeCode = list.GetRowData(row).eventTypeCode;
					_eventResult.eventWinPopup(params);
				}
			}
		}
		
		list_OnRowSearchEnd = function(row) {
			var data = list.GetRowData(row);
			
			if ( !abc.text.allNull(data.chnnlName) ) {
				list.SetRowData(row, {chnnlName : data.chnnlName.replace(/,/gi, ', ')});
			}
		}
		
		list_OnSearchEnd = function(code, msg){
			if(code == 0) {
				abc.biz.display.common.setFormParameter.saveParam(list.GetCurrentPage());
			}
		}
	}
	
	/**
	 * 이벤트
	 */
	_eventResult.event = function(){
		//검색
		$(document.forms.searchForm).on('submit', function(e){
			if($('[name=keywordType]').val() == '' && $('[name=keyword]').val() != '') {
				alert('검색어 구분값을 선택해주세요.');
				$('[name=keywordType]').focus();
				return false;
			}
			abc.biz.display.common.setFormParameter.setPageNum(1);
			_eventResult.getList();
			return false;
		});
		
		$('.inputCalendar').on('change', function(){
			_eventResult.yearCheck(1);
			$(this).focus();
		});
		
		/** 초기화 */
		$('#clear-form').on('click', function() {
			$('#searchForm')[0].reset();
			
			_eventResult.setMonth();
			$("input[type=checkbox]").prop("checked",true);
		});
		
		$('#pageCount').on('change', function() {
			_eventResult.getList();
		});
	}
	
	_eventResult.setMonth = function() {
		var today = new Date();
		today.setMonth(today.getMonth()-1);
		
		$('#startYmd').val(today.format('yyyy.MM.dd'));
		$('#endYmd').val(new Date().format('yyyy.MM.dd'));
	}
	
	/**
	 * 리스트 조회
	 */
	_eventResult.getList = function(){
		if(_eventResult.yearCheck(1)) {
			var pageCount = $('#pageCount').val(); // 한페이지내 결과 로우 갯수
			var param = { url : "/promotion/event/result/list/read"
				, onePageRow : pageCount
				, subparam : FormQueryStringEnc(document.forms.searchForm)
				, sheet : "list"
				, CPage : +abc.biz.display.common.setFormParameter.getPageNum()};
			
			DataSearchPaging(param);
		}
	}
	
	_eventResult.eventWinPopup = function(params){
		var url = "/promotion/event/win/popup";
		abc.open.popup({
			url 	:	url,
			method	: 	"get",
			title 	:	"result-list-search",
			width 	:	'1240',
			height	:	'695',
			params	:	params
		});
	}
	
	_eventResult.yearCheck = function(compareYear) {
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
		_eventResult.init();
	});
	
})();