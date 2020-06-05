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
				{Header:"네이버페이결제이력번호",	Type:"Text",	SaveName:"payHistId",				Width: 150,		Align:"Center",		Edit:0,		Sort:0 }
			,	{Header:"네이버페이결제번호",		Type:"Text",	SaveName:"payMentId",				Width: 150,		Align:"Center",		Edit:0,		Sort:0 }
			,	{Header:"결제승인유형 ",			Type:"Combo",	SaveName:"admissionTypeCode",		Width: 80,		Align:"Center",		Edit:0,		Sort:0, ComboText : "결제|취소|취소",	ComboCode : "01|03|04"}
			,	{Header:"가맹점센터로그인아이디",	Type:"Text",	SaveName:"merchantId",				Width: 140,		Align:"Center",		Edit:0,		Sort:0 }
			,	{Header:"결제/취소일시",			Type:"Date",	SaveName:"admissionYmdt",			Width: 130,		Align:"center",		Edit:0,		Sort:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN }
			,	{Header:"총결제/취소금액",		Type:"Int",		SaveName:"totalPayAmount",			Width: 90,		Align:"Right",		Edit:0,		Sort:0 }
//			,	{Header:"상품명",				Type:"Text",	SaveName:"productName",				Width: 160,		Align:"Left",		Edit:0,		Sort:0 }
			,	{Header:"결제상태",			Type:"Combo",	SaveName:"admissionState",			Width: 60,		Align:"Center",		Edit:0,		Sort:0, ComboText : "성공|실패",	ComboCode : "SUCCESS|FAIL"}
			,	{Header:"정산수수료",			Type:"Int",		SaveName:"primaryCommissionAmount",	Width: 70,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"정산금액",			Type:"Int",		SaveName:"primarySettleAmount",		Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"전체정산금액",			Type:"Int",		SaveName:"totalSettleAmount",		Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"전체정산수수료",		Type:"Int",		SaveName:"totalCommissionAmount",	Width: 90,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"주문번호",			Type:"Text",	SaveName:"orgOrderNo",				Width: 100,		Align:"Center",		Edit:0,		Sort:0 }
			,	{Header:"매칭 여부",			Type:"Combo",	SaveName:"mapngYn",					Width: 60,		Align:"Center",		Edit:0,		Sort:0, ComboText : "매칭|미매칭",	ComboCode : "Y|N"}
			,	{Header:"매칠 일자",			Type:"Date",	SaveName:"sendParmDate",			Width: 100,		Align:"Center",		Edit:0,		Sort:0 }
			
		
		];

		createIBSheet2(document.getElementById("pgNaverGrid"), "pgSheet", "100%", "429px");
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
		$('#admissionTypeCodeAll').off().on('change', function(){
			if($('#admissionTypeCodeAll').is(':checked')) {
				$('input[id^=admissionTypeCodes]').prop('checked', true);
			} else {
				$('input[id^=admissionTypeCodes]').prop('checked', false);
			}
		});
		$('input[id^=admissionTypeCodes]').off().on('change', function(){
			var chkBoxAll = $('input[id^=admissionTypeCodes]');
			var checkedBox = $('input[id^=admissionTypeCodes]:checked');
			if(chkBoxAll.length != checkedBox.length) {
				$('#admissionTypeCodeAll').prop('checked', false);
			} else {
				$('#admissionTypeCodeAll').prop('checked', true);
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
			var searchType = $("#srchType").val();
			if(searchType == 'orgOrderNo'){
				var regExp = /[^0-9]/g;
				var v = $(this).val();
				$(this).prop('maxlength', 15);
				
				if(regExp.test(v)) {
					$(this).val(v.replace(regExp,''));
				}
			}else if(searchType == 'payMentId'){
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
				if($('#admissionTypeCodesCancel').prop('checked')){
					$("#admissionTypeCodeCancel1").val("04");
				}else{
					$("#admissionTypeCodeCancel1").val("");
				}
				if(_pg.searchValidate()) {
					// 페이지리스트 목록 개수
					var pageCount = $("#pageCount").val();
					var param = { url : "/pg/naver/pg-naver-list"
							, onePageRow : pageCount
							, subparam : FormQueryStringEnc(document.pgForm)
							, sheet : "pgSheet" };
					DataSearchPaging(param);
				}
				break;
			case 'excel' :
				$("#pgForm")
				.prop("action","/pg/naver/naver-list-excel")
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
			url: "/pg/naver/setSettlementHistory",
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
