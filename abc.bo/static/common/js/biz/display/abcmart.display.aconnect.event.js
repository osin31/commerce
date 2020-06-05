(function() {

	var _event = abc.object.createNestedObject(window,"abc.biz.display.aconnect.event");
	
	/**
	 * 초기화
	 */
	_event.init = function() {

		abc.biz.display.common.searchFormCalendarBtnEvent('#dateSearchS', '#dateSearchE', '.area-calendar-btn-group');
		$('.calendar-month').click();
		
		abc.biz.display.common.setFormParameter.setForm($(document.forms.searchForm));
		abc.biz.display.common.setFormParameter.setParam();
		
		_event.initEventSheet();
		_event.event();

		
		_event.doAction('search');
	}
	
	/**
	 *  IBSheet 초기화
	 */
	_event.initEventSheet = function() {
		
		var initEventSheet = {};
		var pageCount = $('#pageCount').val();
		
		initEventSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, DeferredVScroll:1, AutoFitColWidth: 'init'};
		initEventSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initEventSheet.Cols = [
//			{Header:'', 				Type:'CheckBox',SaveName:'cheked',			Width:10,	Edit:0,	Align:'Center'}
		    {Header:'상태', 				Type:'Status',	SaveName:'status',			Width:10,	Edit:0,	Align:'Center', Hidden:1}
		  , {Header:'번호', 				Type:'Seq',		SaveName:'seq',				Width:10,	Edit:0,	Align:'Center', Sort:0}
		  , {Header:'이벤트 id',			Type:'Text',	SaveName:'ctlgEventNo',		Width:30,	Edit:0,	Align:'Center', FontUnderline:1, Cursor:'Pointer', Sort:0}
		  , {Header:'이벤트명',				Type:'Text',	SaveName:'eventName',		Width:50,	Edit:0,	Align:'Left', FontUnderline:1, Cursor:'Pointer'}
		  , {Header:'진행상태',				Type:'Text',	SaveName:'progress',		Width:20,	Edit:0,	Align:'Center', Sort:0}
		  , {Header:'전시여부',				Type:'Combo',	SaveName:'dispYn',			Width:20,	Edit:0,	Align:'Center', ComboCode : 'Y|N', ComboText : '전시|전시안함', Sort:0}
		  , {Header:'적용매장',				Type:'Combo',	SaveName:'storeCommonYn',	Width:20,	Edit:0,	Align:'Center', ComboCode : 'Y|N', ComboText : '공통|공통아님', Sort:0}
		  , {Header:'이벤트기간',			Type:'Text',	SaveName:'eventDtm',		Width:60,	Edit:0,	Align:'Center'}
		  , {Header:'등록자',			Type:'Text',	SaveName:'rgsterInfo',		Width:30,	Edit:0,	Align:'Center', Sort:0}
		  , {Header:'등록일시',			Type:'Date',	SaveName:'rgstDtm',			Width:40,	Edit:0,	Align:'Center',	Format:abc.consts.DEFAULT_DATETIME_PATTERN}
		  , {Header:'수정자',			Type:'Text',	SaveName:'moderInfo',		Width:30,	Edit:0,	Align:'Center', Sort:0}
		  , {Header:'수정일시',			Type:'Date',	SaveName:'modDtm',			Width:40,	Edit:0,	Align:'Center',	Format:abc.consts.DEFAULT_DATETIME_PATTERN}
		];

		createIBSheet2(document.getElementById('eventSheet'), 'eventList', '100%', '370px');
		IBS_InitSheet(eventList, initEventSheet);
		
		eventList.SetCountPosition(3);
		eventList.SetPagingPosition(2);
		eventList.FitColWidth();
		eventList.SetExtendLastCol(1);	
	}
	
	/**
	 * 메뉴 grid action
	 */
	_event.doAction = function(sAction){
		
		//검색기간 체크 추가
		var alertSearchDate = "시작일은 종료일 이전 날짜만 가능합니다.";

		var startDate = new Date($('#dateSearchS').val());
		var endDate = new Date($('#dateSearchE').val());
		
		if(startDate > endDate){
			alert(alertSearchDate);
			return;
		}
		
		switch (sAction) {
		case 'search':
			
			var param = { url : '/display/a-connect/event/list'
						, sheet : 'eventList'
						, onePageRow : $("#pageCount").val()
						, subparam : FormQueryStringEnc(document.forms.searchForm)
						, CPage : +abc.biz.display.common.setFormParameter.getPageNum()
			};
			DataSearchPaging(param);
			break;
		}
	}
	
	/**
	 * 이벤트
	 */
	_event.event = function(){
		
		eventList_OnClick = function(row, col, value, cellX, cellY, cellW, cellH, rowtype) {		
			var data = eventList.GetRowData(row);
			
			if (row > 0) {
				if (eventList.ColSaveName(col) == 'eventName' || eventList.ColSaveName(col) == 'ctlgEventNo') {
					abc.biz.display.common.setFormParameter.setDetailMove();
					location.href = '/display/a-connect/event/detail?ctlgEventNo=' + data.ctlgEventNo;
				}
			}
		}
		

		eventList_OnSearchEnd = function(code, msg){
			if(code == 0) {
				abc.biz.display.common.setFormParameter.saveParam(eventList.GetCurrentPage());
			}
		}
		
		/** 검색 */
		$('#searchBtn').on('click', function() {
			abc.biz.display.common.setFormParameter.setPageNum(1);
			_event.doAction('search');
		});
		
		/** 목록개수 */
		$('#pageCount').on('change', function() {
			
			_event.doAction('search');
		});
		
		/** 초기화 */
		$('#clear-form').on('click', function() {
			
			$('#searchForm')[0].reset();
			$('.area-calendar-btn-group .calendar-month').trigger('click');
		});
		
	}
	
	$(function() {
		_event.init();
	});
	
})();