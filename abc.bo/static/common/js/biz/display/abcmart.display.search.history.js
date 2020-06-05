(function() {

	var _history = abc.object.createNestedObject(window,"abc.biz.display.search.history");
	
	/**
	 * 초기화
	 */
	_history.init = function() {
		
		_history.event();
		_history.initHistorySheet();
	}
	
	/**
	 *  IBSheet 초기화
	 */
	_history.initHistorySheet = function() {
		
		var initHistorySheet = {};
		var pageCount = $('#pageCount').val();
		
		initHistorySheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, DeferredVScroll:1, AutoFitColWidth: 'init'};
		initHistorySheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initHistorySheet.Cols = [
				{Header:'반영일시',		Type:'Date', SaveName:'applyDtm',		Width:30,	Edit:0,	Align:'Center',	Format:abc.consts.DEFAULT_DATETIME_PATTERN}
				, {Header:'1위',		Type:'Text', SaveName:'srchWord1Text',	Width:20,	Edit:0,	Align:'Center'}
				, {Header:'2위',		Type:'Text', SaveName:'srchWord2Text',	Width:20,	Edit:0,	Align:'Center'}
				, {Header:'3위',		Type:'Text', SaveName:'srchWord3Text',	Width:20,	Edit:0,	Align:'Center'}
				, {Header:'4위',		Type:'Text', SaveName:'srchWord4Text',	Width:20,	Edit:0,	Align:'Center'}
				, {Header:'5위',		Type:'Text', SaveName:'srchWord5Text',	Width:20,	Edit:0,	Align:'Center'}
				, {Header:'6위',		Type:'Text', SaveName:'srchWord6Text',	Width:20,	Edit:0,	Align:'Center'}
				, {Header:'7위',		Type:'Text', SaveName:'srchWord7Text',	Width:20,	Edit:0,	Align:'Center'}
				, {Header:'8위',		Type:'Text', SaveName:'srchWord8Text',	Width:20,	Edit:0,	Align:'Center'}
				, {Header:'9위',		Type:'Text', SaveName:'srchWord9Text',	Width:20,	Edit:0,	Align:'Center'}
				, {Header:'10위',	Type:'Text', SaveName:'srchWord10Text',	Width:20,	Edit:0,	Align:'Center'}
		];

		createIBSheet2(document.getElementById('historySheet'), 'historyList', '100%', '370px');
		IBS_InitSheet(historyList, initHistorySheet);
		
		historyList.SetCountPosition(3);
		historyList.SetPagingPosition(2);
		historyList.FitColWidth();
		
		_history.doAction('search');
	}
	
	/**
	 * grid action
	 */
	_history.doAction = function(sAction) {
		
		switch (sAction) {
			case 'search':
				var pageCount = $('#pageCount').val();
				var param = { url : '/display/search-word/history/list'
							, sheet : 'historyList'
							, onePageRow : pageCount
							, subparam : FormQueryStringEnc(document.forms.historyForm)
				};
				DataSearchPaging(param);
				break;
			}
	}
		
	/**
	 * 이벤트
	 */
	_history.event = function() {
		
		// 목록개수 변경
		$('#pageCount').on('change', function() {
			_history.doAction('search');
		});
	}
	
	$(function() {
		_history.init();
	});
	
})();