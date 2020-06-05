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
				{Header:"거래구분", 		Type:"Combo",	SaveName:"niceServiceType",	Width: 70,		Align:"Center",		Edit:0,		Sort:0, ComboText : "회수|판매",	ComboCode : "0215|0216" }
			,	{Header:"승인번호",		Type:"Text",	SaveName:"apprNo",			Width: 140,		Align:"Center",		Edit:0,		Sort:0 }
			,	{Header:"거래일자",		Type:"Date",	SaveName:"tranDt",			Width: 80,		Align:"Center",		Edit:0,		Sort:0 }
			,	{Header:"거래시간",		Type:"Date",	SaveName:"tranHms",			Width: 80,		Align:"Center",		Edit:0,		Sort:0, Format:"HH:mm:ss" }
			,	{Header:"점포코드 ",		Type:"Text",	SaveName:"storeCd",			Width: 70,		Align:"Center",		Edit:0,		Sort:0 }
			,	{Header:"카드번호",		Type:"Text",	SaveName:"voucherFrom",		Width: 130,		Align:"Center",		Edit:0,		Sort:0 }
			,	{Header:"서비스코드",		Type:"Text",	SaveName:"tranKd",			Width: 130,		Align:"Left",		Edit:0,		Sort:0 }
			,	{Header:"거래상태",		Type:"Text",	SaveName:"transStatus",		Width: 100,		Align:"Left",		Edit:0,		Sort:0 }
			,	{Header:"구분코드",		Type:"Text",	SaveName:"tranType",		Width: 100,		Align:"Center",		Edit:0,		Sort:0 }
			,	{Header:"액면가",			Type:"Text",	SaveName:"purchaseAmt",		Width: 60,		Align:"Right",		Edit:0,		Sort:0, Hidden:1 }
			,	{Header:"회수/판매금액",		Type:"Int",		SaveName:"tranAmt",			Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"판매형태",		Type:"Text",	SaveName:"salerKind",		Width: 60,		Align:"Center",		Edit:0,		Sort:0 }
			,	{Header:"환불대상자",		Type:"Text",	SaveName:"returnType",		Width: 100,		Align:"Center",		Edit:0,		Sort:0, Hidden:1 }
			,	{Header:"원거래일자",		Type:"Text",	SaveName:"oriApprDt",		Width: 60,		Align:"Center",		Edit:0,		Sort:0, Hidden:1 }
			,	{Header:"원거래승인번호",	Type:"Text",	SaveName:"oriApprNo",		Width: 60,		Align:"Center",		Edit:0,		Sort:0, Hidden:1 }
			,	{Header:"회원번호",		Type:"Text",	SaveName:"custId",			Width: 60,		Align:"Center",		Edit:0,		Sort:0, Hidden:1 }
			,	{Header:"결제수단",		Type:"Text",	SaveName:"payCd",			Width: 60,		Align:"Center",		Edit:0,		Sort:0, Hidden:1 }
			,	{Header:"가맹점영역",		Type:"Text",	SaveName:"companyInfo",		Width: 60,		Align:"Center",		Edit:0,		Sort:0, Hidden:1 }
			,	{Header:"매핑 여부",		Type:"Combo",	SaveName:"mapngYn",			Width: 60,		Align:"Center",		Edit:0,		Sort:0, ComboText : "매칭|미매칭",	ComboCode : "Y|N" }
			,	{Header:"기프트카드주문번호",	Type:"Text",	SaveName:"giftCardOrderNo",	Width: 60,		Align:"Center",		Edit:0,		Sort:0, Hidden:1 }
			,	{Header:"사유내용",		Type:"Text",	SaveName:"rsnCont",			Width: 60,		Align:"Center",		Edit:0,		Sort:0, Hidden:1 }
			,	{Header:"사유등록자번호",	Type:"Text",	SaveName:"rsnRgsterNo",		Width: 60,		Align:"Center",		Edit:0,		Sort:0, Hidden:1 }
			,	{Header:"사유등록일시",		Type:"Text",	SaveName:"rsnRgstDtm",		Width: 60,		Align:"Center",		Edit:0,		Sort:0, Hidden:1 }
			,	{Header:"연동일시",		Type:"Text",	SaveName:"intrlkDtm",		Width: 60,		Align:"Center",		Edit:0,		Sort:0, Hidden:1 }
			,	{Header:"매칭 일자",		Type:"Date",	SaveName:"sendParmDate",	Width: 100,		Align:"Center",		Edit:0,		Sort:0 }
			,	{Header:"원주문번호",		Type:"Text",	SaveName:"orgOrderNo",		Width: 100,		Align:"Center",		Edit:0,		Sort:0 }
			,	{Header:"주문번호",		Type:"Text",	SaveName:"orderNo",			Width: 100,		Align:"Center",		Edit:0,		Sort:0, Hidden:1 }
			
		
		];

		createIBSheet2(document.getElementById("pgGiftCardGrid"), "pgSheet", "100%", "429px");
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
		$('#niceServiceTypeAll').off().on('change', function(){
			if($('#niceServiceTypeAll').is(':checked')) {
				$('input[name=niceServiceTypes]').prop('checked', true);
			} else {
				$('input[name=niceServiceTypes]').prop('checked', false);
			}
		});
		$('input[name=niceServiceTypes]').off().on('change', function(){
			var chkBoxAll = $('input[name=niceServiceTypes]');
			var checkedBox = $('input[name=niceServiceTypes]:checked');
			if(chkBoxAll.length != checkedBox.length) {
				$('#niceServiceTypeAll').prop('checked', false);
			} else {
				$('#niceServiceTypeAll').prop('checked', true);
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
			}else if(searchType == 'apprNo'){
				var regExp =/[^a-z0-9]/gi;
				var v = $(this).val();
				$(this).prop('maxlength', 8);
				
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
					var param = { url : "/pg/gift-card/pg-gift-card-list"
							, onePageRow : pageCount
							, subparam : FormQueryStringEnc(document.pgForm)
							, sheet : "pgSheet" };
					DataSearchPaging(param);
				}
				break;
			case 'excel' :
				$("#pgForm")
				.prop("action","/pg/gift-card/gift-card-list-excel")
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
			url: "/pg/gift-card/setSettlementHistory",
			data: $form.serialize()
		}).done(function(data, textStatus, jqXHR) {
			console.log(data);
			$("#recoveryAmount").text(_pg.numberFormat(data.recoveryAmount) + "원");
			$("#saleAmount").text(_pg.numberFormat(data.saleAmount) + "원");
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
