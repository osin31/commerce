/**
 * 
 */
(function() {

	var _pg = abc.object.createNestedObject(window,"abc.biz.pg");
	
	//발송처별 매출통계sheet
	_pg.initPgSheet = function() {
		var initSheet = {};
		var pageCount = ! abc.text.allNull($("#pageCount")) ? $('#pageCount').val() : 10000;
		var pymntStatCode = 'STJY|STMO|STPC|STMH|STMP|RN01|RN02|RN03|RN04|RN07|RN09|RN10';
		var pymntStatCodeText = '대금정산|취소정산|부분취소정산|환불정산|부분환불정산|매입반송|취소반송|카드사보류|카드사보류해제|부분취소정산|매입반송해제|취소반송해제';
		
		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MaxSort:1,  MergeSheet: msHeaderOnly};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initSheet.Cols = [
				{Header:"kcp거래이력순번", 	Type:"Text",	SaveName:"kcpDealHisSeq",	Width: 100,		Align:"Center",		Edit:0,		Sort:0, Hidden:1 }
			,	{Header:"kcp거래번호",		Type:"Text",	SaveName:"kcpDealNum",		Width: 100,		Align:"Center",		Edit:0,		Sort:0 }
			,	{Header:"주문경로",		Type:"Text",	SaveName:"comparisonType",	Width: 100,		Align:"Center",		Edit:0,		Sort:0, Hidden:1 }
			,	{Header:"거래일시",		Type:"Text",	SaveName:"pymntDtm",		Width: 100,		Align:"Center",		Edit:0,		Sort:0 }
			,	{Header:"원거래일시",		Type:"Text",	SaveName:"pymntOrgDtm",		Width: 100,		Align:"Left",		Edit:0,		Sort:0, Hidden:1 }
			,	{Header:"입금/취소처리일시",	Type:"Text",	SaveName:"lgdDtm",			Width: 100,		Align:"Center",		Edit:0,		Sort:0, Hidden:1 }
			,	{Header:"은행입금/취소일시",	Type:"Text",	SaveName:"lgdBankDtm",		Width: 100,		Align:"Center",		Edit:0,		Sort:0, Hidden:1 }
			,	{Header:"거래구분",		Type:"Combo",	SaveName:"pymntStatCode",	Width: 100,		Align:"Left",		Edit:0,		Sort:0, ComboText : pymntStatCodeText,	ComboCode : pymntStatCode }
			,	{Header:"주문번호",		Type:"Text",	SaveName:"orderNum",		Width: 100,		Align:"Center",		Edit:0,		Sort:0 }
			,	{Header:"결제금액",		Type:"Int",		SaveName:"pymntAmt",		Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"수수료",			Type:"Text",	SaveName:"pymntCharge",		Width: 100,		Align:"Right",		Edit:0,		Sort:0, Hidden:1 }
			,	{Header:"무이자수수료",		Type:"Int"	,	SaveName:"freeCharge",		Width: 100,		Align:"Right",		Edit:0,		Sort:0, Hidden:1 }
			,	{Header:"부가세",			Type:"Int",		SaveName:"pymntSurTax",		Width: 100,		Align:"Right",		Edit:0,		Sort:0, Hidden:1 }
			,	{Header:"정산금액",		Type:"Int",		SaveName:"accountsAmt",		Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"마감일자",		Type:"Date",	SaveName:"deadlineDtm",		Width: 100,		Align:"Center",		Edit:0,		Sort:0, Hidden:1 }
			,	{Header:"수납월",			Type:"Text",	SaveName:"receiveMonth",	Width: 100,		Align:"Center",		Edit:0,		Sort:0, Hidden:1 }
			,	{Header:"정산일자",		Type:"Date",	SaveName:"accountsDtm",		Width: 100,		Align:"Center",		Edit:0,		Sort:0 }
			,	{Header:"에스크로수수료",	Type:"Int",		SaveName:"escrowCharge",	Width: 100,		Align:"Center",		Edit:0,		Sort:0 }
			,	{Header:"에스크로부가세",	Type:"Int",		SaveName:"escrowSurtax",	Width: 100,		Align:"Center",		Edit:0,		Sort:0 }
			,	{Header:"은행코드",		Type:"Text",	SaveName:"bankCode",		Width: 100,		Align:"Center",		Edit:0,		Sort:0, Hidden:1 }
			,	{Header:"가상계좌번호",		Type:"Text",	SaveName:"lgdAccountnum",	Width: 100,		Align:"Center",		Edit:0,		Sort:0, Hidden:1 }
			,	{Header:"입금처리고유번호",	Type:"Text",	SaveName:"lgdCasseqno",		Width: 100,		Align:"Center",		Edit:0,		Sort:0, Hidden:1 }
			,	{Header:"입금인성명",		Type:"Text",	SaveName:"lgdPayer",		Width: 100,		Align:"Center",		Edit:0,		Sort:0, Hidden:1 }
			,	{Header:"할부개월수",		Type:"Int",		SaveName:"istmtCount",		Width: 100,		Align:"Center",		Edit:0,		Sort:0 }
			,	{Header:"승인번호",		Type:"Text",	SaveName:"prmtNum",			Width: 100,		Align:"Center",		Edit:0,		Sort:0 }
			,	{Header:"매입카드사코드",	Type:"Text",	SaveName:"crdtCardCode",	Width: 100,		Align:"Center",		Edit:0,		Sort:0, Hidden:1 }
			,	{Header:"가맹점번호",		Type:"Text",	SaveName:"merchantName",	Width: 100,		Align:"Center",		Edit:0,		Sort:0 }
			,	{Header:"결제수단",		Type:"Text",	SaveName:"pymntMeans",		Width: 100,		Align:"Center",		Edit:0,		Sort:0 }
			,	{Header:"데이터입력일시",	Type:"Text",	SaveName:"rgstDtm",			Width: 100,		Align:"Center",		Edit:0,		Sort:0, Hidden:1 }
			,	{Header:"원주문번호",		Type:"Text",	SaveName:"orgOrderNo",		Width: 100,		Align:"Center",		Edit:0,		Sort:0 }
			,	{Header:"매칭여부",		Type:"Combo",	SaveName:"mapngYn",			Width: 100,		Align:"Center",		Edit:0,		Sort:0, ComboText : "매칭|미매칭",	ComboCode : "Y|N"}
			,	{Header:"매칭일자",		Type:"Date",	SaveName:"sendParmDate",	Width: 100,		Align:"Center",		Edit:0,		Sort:0 }
			
		];

		createIBSheet2(document.getElementById("pgKcpGrid"), "pgSheet", "100%", "429px");
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
		
		pgSheet_OnClick = function(Row, Col, Value) {
			if ( Row != 0) {
				if (pgSheet.ColSaveName(Col) == 'orgOrderNo') {
					var comparisonType = pgSheet.GetCellValue(Row, 'comparisonType');
					switch (comparisonType){
					case "ORDER":
						var orderNo = pgSheet.GetCellValue(Row, 'orgOrderNo');
						if(!abc.text.allNull(orderNo)){
							abc.orderDetailPopup('', '', '', '', orderNo);
						}
						break;
					case "ClAIM":
						
						break;
					case "GIFT":
						
						break;
					case "AS":
						
						break;
					}
					
				}
			}
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
				$(this).prop('maxlength', 15);
				
				if(regExp.test(v)) {
					$(this).val(v.replace(regExp,''));
				}
			}else if(searchType == 'prmtNum'){
				var regExp =/[^a-z0-9]/gi;
				var v = $(this).val();
				$(this).prop('maxlength', 30);
				
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
					var param = { url : "/pg/kcp/pg-kcp-list"
							, onePageRow : pageCount
							, subparam : FormQueryStringEnc(document.pgForm)
							, sheet : "pgSheet" };
					DataSearchPaging(param);
				}
				break;
			case 'excel' :
				$("#pgForm")
				.prop("action","/pg/kcp/kcp-list-excel")
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
			url: "/pg/kcp/setSettlementHistory",
			data: $form.serialize()
		}).done(function(data, textStatus, jqXHR) {
			console.log(data);
			$("#totalOrderPay").text(_pg.numberFormat(data.totalOrderPay) + "원");
			$("#totalClaimPay").text(_pg.numberFormat(data.totalClaimPay) + "원");
			$("#totalGiftcardPay").text(_pg.numberFormat(data.totalGiftcardPay) + "원");
			$("#totalAsPay").text(_pg.numberFormat(data.totalAsPay) + "원");
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
