/**
 * 
 */
(function() {

	var _pg = abc.object.createNestedObject(window,"abc.biz.pg");
	
	//발송처별 매출통계sheet
	_pg.initPgSheet = function() {
		var initSheet = {};
		var pageCount = ! abc.text.allNull($("#pageCount")) ? $('#pageCount').val() : 10000;

		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MaxSort:1,  MergeSheet: msHeaderOnly};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initSheet.Cols = [
				{Header:"NO|NO", 							Type:"Text",		SaveName:"hourTitle",			Width: 150,		Align:"Center",		Edit:0,		Sort:1 }
			,	{Header:"PG사명|PG사명",					Type:"Int",			SaveName:"orderQtyAll",			Width: 100,		Align:"Right",		Edit:0,		Sort:1 }
			,	{Header:"사이트|사이트",					Type:"Int",			SaveName:"prdtNormalAmtAll",	Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"거래구분|거래구분",				Type:"Int",			SaveName:"orderAmtAll",			Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"거래일시|거래일시",				Type:"Int",			SaveName:"orderQty10000",		Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"승인번호|승인번호",				Type:"Int",			SaveName:"prdtNormalAmt10000",	Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"주문번호|주문번호",				Type:"Int",			SaveName:"orderAmt10000",		Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"A-RT|결제금액",					Type:"Int",			SaveName:"orderAmt10000",		Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"A-RT|배송비",						Type:"Int",			SaveName:"orderAmt10000",		Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"PG사|결제금액",					Type:"Int",			SaveName:"orderAmt10000",		Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"PG사|배송비",						Type:"Int",			SaveName:"orderAmt10000",		Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"차액|결제금액",					Type:"Int",			SaveName:"orderAmt10000",		Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"차액|배송비",						Type:"Int",			SaveName:"orderAmt10000",		Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			
		
		];

		createIBSheet2(document.getElementById("pgGrid"), "pgSheet", "100%", "429px");
		IBS_InitSheet(pgSheet, initSheet);
		
		//Grid 건수 정보 표시
		pgSheet.SetCountPosition(3);
		//Grid 페이지 네비게이션 버튼 표시
		pgSheet.SetPagingPosition(2);
		//Grid width 자동 조절
		pgSheet.FitColWidth();
		//Grid 마지막 컬럼을 All 너비에 맞춘다.
		pgSheet.SetExtendLastCol(1);
		
		pgSheet_OnSearchEnd = function(Code, Msg, StCode, StMsg, Response){
			_pg.checkRowCount(pgSheet.RowCount());
		};

	};
	
	//검색 내역 set
	_pg.init = function() {
		abc.date.month($('#fromDate'));
		//ibsheet
		_pg.initPgSheet();
		//이벤트 바인딩
		_pg.eventSearch();
	};
	
	_pg.eventSearch = function() {
		$('#today').off().on('click', function(){
			abc.date.days($('#fromDate'));
		});
		$('#week').off().on('click', function(){
			abc.date.week($('#fromDate'));
		});
		$('#month').off().on('click', function(){
			abc.date.month($('#fromDate'));
		});
		$('#year').off().on('click', function(){
			abc.date.year($('#fromDate'));
		});
		
		//거래구분 체크박스
		$('#dealTypeAll').off().on('change', function(){
			if($('#dealTypeAll').is(':checked')) {
				$('input[name=dealType]').prop('checked', true);
			} else {
				$('input[name=dealType]').prop('checked', false);
			}
		});
		$('input[name=dealType]').off().on('change', function(){
			var chkBoxAll = $('input[name=dealType]');
			var checkedBox = $('input[name=dealType]:checked');
			if(chkBoxAll.length != checkedBox.length) {
				$('#dealTypeAll').prop('checked', false);
			} else {
				$('#dealTypeAll').prop('checked', true);
			}
		});
		
		// 버튼 이벤트
		$('#searchBtn').off().on('click', function(){
			_pg.doAction('search');
		});
		$('#resetBtn').off().on('click', function(){
			$('form[name=pgForm]')[0].reset();
			abc.date.month($('#fromDate'));
		});
		$('#saveBtn').off().on('click', function(){
			_pg.doAction('save');
		});
		$('#downExcel').off().on('click', function(){
			_pg.doAction('excel');
		});
		$('input[name=searchWord]').off().on('keyup', function(e){
			var regExp = /[^0-9]/g;
			var v = $(this).val();
			$(this).prop('maxlength', 10);
			
			if(regExp.test(v)) {
				$(this).val(v.replace(regExp,''));
			}
		});
	};

	_pg.doAction = function(action) {
		switch (action) {
			case 'search' : 
				alert('검색 개발중');
				if(_pg.searchValidate()) {
					
				}
				break;
			case 'save' :
				alert('저장 개발중');
				break;
			case 'excel' :
				alert('엑셀다운 개발중');
				break;

		}
	};
	
	_pg.searchValidate = function() {
		var chkBool = true;
		if($('#fromDate, #toDate').val() == null || $('#fromDate, #toDate').val() == undefined) {
			alert('기간을 설정해주세요.');
			chkBool = false;
		}
		
		return chkBool;
	}
	_pg.checkRowCount = function(count) {
		if(count <= 1) {
			$('#downExcel').addClass('disabled');
		} else {
			$('#downExcel').removeClass('disabled');
		}
	};
})();

$(document).ready(function(){
	abc.biz.pg.init();
});
