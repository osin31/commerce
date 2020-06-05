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
				{Header:"결제고유번호", 		Type:"Text",	SaveName:"tid",					Width: 170,		Align:"Center",		Edit:0,		Sort:1 }
			,	{Header:"호출고유번호",		Type:"Text",	SaveName:"aid",					Width: 170,		Align:"Center",		Edit:0,		Sort:1 }
			,	{Header:"가맹점코드",		Type:"Text",	SaveName:"cid",					Width: 100,		Align:"Center",		Edit:0,		Sort:0 }
			,	{Header:"결제상태값",		Type:"Text",	SaveName:"status",				Width: 170,		Align:"Left",		Edit:0,		Sort:0 }
			,	{Header:"결제수단 ",		Type:"Combo",	SaveName:"paymentMethodType",	Width: 60,		Align:"Center",		Edit:0,		Sort:0, ComboText : "카드|현금",	ComboCode : "CARD|MONEY"}
//			,	{Header:"상품이름",		Type:"Text",	SaveName:"itemName",			Width: 100,		Align:"Right",		Edit:0,		Sort:0, Hidden:1 }
//			,	{Header:"상품코드",		Type:"Text",	SaveName:"itemCode",			Width: 100,		Align:"center",		Edit:0,		Sort:0, Hidden:1 }
			,	{Header:"카드빈",			Type:"Text",	SaveName:"cardBin",				Width: 100,		Align:"Center",		Edit:0,		Sort:0 }
			,	{Header:"할부개월수",		Type:"Text",	SaveName:"installMonth",		Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"카드사정보",		Type:"Text",	SaveName:"cardCorpName",		Width: 300,		Align:"Left",		Edit:0,		Sort:0 }
			,	{Header:"무이자할부여부",	Type:"Text",	SaveName:"interestFreeInstall",	Width: 100,		Align:"Center",		Edit:0,		Sort:0 }
			,	{Header:"결제/취소거래시간",	Type:"Date",	SaveName:"approvedAt",			Width: 140,		Align:"Center",		Edit:0,		Sort:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN }
			,	{Header:"결제/취소총액",		Type:"Int",		SaveName:"amount",				Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"결제타입",		Type:"Combo",	SaveName:"paymentActionType",	Width: 60,		Align:"Center",		Edit:0,		Sort:0, ComboText : "결제|취소",	ComboCode : "PAYMENT|CANCEL"}
			,	{Header:"주문번호",		Type:"Text",	SaveName:"orgOrderNo",			Width: 100,		Align:"Center",		Edit:0,		Sort:0 }
			,	{Header:"매칭 여부",		Type:"Combo",	SaveName:"mapngYn",				Width: 60,		Align:"Center",		Edit:0,		Sort:0, ComboText : "매칭|미매칭",	ComboCode : "Y|N"}
			,	{Header:"매칭 일자",		Type:"Date",	SaveName:"sendParmDate",		Width: 100,		Align:"Center",		Edit:0,		Sort:0 }
			
		
		];

		createIBSheet2(document.getElementById("pgKakaoGrid"), "pgSheet", "100%", "429px");
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
			_pg.setSettlementHistory();
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
		$('#paymentActionTypeAll').off().on('change', function(){
			if($('#paymentActionTypeAll').is(':checked')) {
				$('input[name=paymentActionTypes]').prop('checked', true);
			} else {
				$('input[name=paymentActionTypes]').prop('checked', false);
			}
		});
		$('input[name=paymentActionTypes]').off().on('change', function(){
			var chkBoxAll = $('input[name=paymentActionTypes]');
			var checkedBox = $('input[name=paymentActionTypes]:checked');
			if(chkBoxAll.length != checkedBox.length) {
				$('#paymentActionTypeAll').prop('checked', false);
			} else {
				$('#paymentActionTypeAll').prop('checked', true);
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
		$('#downExcel').off().on('click', function(){
			_pg.doAction('excel');
		});
		$('input[name=searchWord]').off().on('keyup', function(e){
			var searchType = $("#srchType").val();
			if(searchType == 'orgOrderNo'){
				var regExp = /[^0-9]/g;
				var v = $(this).val();
				$(this).prop('maxlength', 10);
				
				if(regExp.test(v)) {
					$(this).val(v.replace(regExp,''));
				}
			}else if(searchType == 'tid'){
				var regExp =/[^a-z0-9]/gi;
				var v = $(this).val();
				$(this).prop('maxlength', 20);
				
				if(regExp.test(v)) {
					$(this).val(v.replace(regExp,''));
				}
			}
			
		});
	};

	_pg.doAction = function(action) {
		switch (action) {
			case 'search' : 
				if(_pg.searchValidate()) {
					// 페이지리스트 목록 개수
					var pageCount = $("#pageCount").val();
					var param = { url : "/pg/kakao/pg-kakao-list"
							, onePageRow : pageCount
							, subparam : FormQueryStringEnc(document.pgForm)
							, sheet : "pgSheet" };
					DataSearchPaging(param);
				}
				break;
			case 'excel' :
				$("#pgForm")
				.prop("action","/pg/kakao/kakao-list-excel")
				.prop("method", "post")
				.submit();
				break;

		}
	};
	
	_pg.searchValidate = function() {
		var chkBool = true;
		if($('#fromDate, #toDate').val() == null || $('#fromDate, #toDate').val() == undefined) {
			alert('기간을 설정해주세요.');
			chkBool = false;
		}
		if('' != $("#srchType").val() && '' == $("input[name='searchWord']").val()){
			alert("검색어를 입력해주세요.");
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
	
	_pg.setSettlementHistory = function(){
		var $form = $("#pgForm");
		
		$.ajax({
			type:"post",
			url: "/pg/kakao/setSettlementHistory",
			data: $form.serialize()
		}).done(function(data, textStatus, jqXHR) {
			console.log(data);
			$("#totalPay").text(_pg.numberFormat(data.totalPay) + "원");
			$("#mapngPay").text(_pg.numberFormat(data.mapngPay) + "원");
			$("#notMapngPay").text(_pg.numberFormat(data.notMapngPay) + "원");
		}).fail(function(jqXHR, textStatus, errorThrown) {
			alert("오류가 발생했습니다. - " + jqXHR.responseJSON.message);
		});
		
	}
	
	_pg.numberFormat = function(num){
		return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	}

	
})();

$(document).ready(function(){
	abc.biz.pg.init();
});
