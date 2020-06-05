(function() {

	var _csClaim = abc.object.createNestedObject(window,"abc.biz.stats.cs.claim");

	abc.biz.stats.order.common.init();
	
	_csClaim.fromDate = {};
	_csClaim.toDate = {};
	_csClaim.sheetType = {};
	_csClaim.periodTime = {};
	
	_csClaim.isAdmin;
	
	//당일 교환/반품 처리현황, 반품 sheet
	_csClaim.initClaimReturnSheet = function() {
		var initSheet = {};
		var pageCount = ! abc.text.allNull($("#pageCount")) ? $('#pageCount').val() : 10000;

		initSheet.Cfg = {SearchMode:smServerPaging2, MergeSheet:msHeaderOnly, Page:pageCount, MaxSort:1, FrozenCol:2};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initSheet.Cols = [
			{Header:"기간별 통계|일별 통계", 						Type:"Text",		SaveName:"dateGbn",			Width: 100,		Align:"Center",		Edit:0,		Sort:1	}
			,	{Header:"합계|합계",							Type:"Int",			SaveName:"returnTotalCnt",	Width: 100,		Align:"Right",		Edit:0,		Sort:1,	Format:"#,###"	}
			,	{Header:"반품 완료 건수|불량",					Type:"Int",			SaveName:"returnRsn01",		Width: 100,		Align:"Right",		Edit:0,		Sort:0,	Format:"#,###"	}
			,	{Header:"반품 완료 건수|오배송",					Type:"Int",			SaveName:"returnRsn02",		Width: 100,		Align:"Right",		Edit:0,		Sort:0,	Format:"#,###"	}
			,	{Header:"반품 완료 건수|변심(소재)",				Type:"Int",			SaveName:"returnRsn03",		Width: 100,		Align:"Right",		Edit:0,		Sort:0,	Format:"#,###"	}
			,	{Header:"반품 완료 건수|변심\n(사이즈 미스)",			Type:"Int",			SaveName:"returnRsn04",		Width: 100,		Align:"Right",		Edit:0,		Sort:0,	Format:"#,###"	}
			,	{Header:"반품 완료 건수|변심\n(화면과 실물 상이)",		Type:"Int",			SaveName:"returnRsn05",		Width: 100,		Align:"Right",		Edit:0,		Sort:0,	Format:"#,###"	}
			,	{Header:"반품 완료 건수|미출 반품",					Type:"Int",			SaveName:"returnRsn06",		Width: 100,		Align:"Right",		Edit:0,		Sort:0,	Format:"#,###"	}
			,	{Header:"반품 완료 건수|오프라인 반품",				Type:"Int",			SaveName:"returnRsn07",		Width: 100,		Align:"Right",		Edit:0,		Sort:0,	Format:"#,###"	}
			,	{Header:"반품 완료 건수|브랜드 박스 훼손",				Type:"Int",			SaveName:"returnRsn08",		Width: 100,		Align:"Right",		Edit:0,		Sort:0,	Format:"#,###"	}
			,	{Header:"반품 완료 건수|기타",					Type:"Int",			SaveName:"returnRsn09",		Width: 100,		Align:"Right",		Edit:0,		Sort:0,	Format:"#,###"	}
		];

		createIBSheet2(document.getElementById("claimReturnGrid"), "returnStatsSheet", "100%", "429px");
		IBS_InitSheet(returnStatsSheet, initSheet);
		// Grid 건수 정보 표시
		returnStatsSheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		returnStatsSheet.SetPagingPosition(2);
		// Grid width 자동 조절
		returnStatsSheet.FitColWidth();
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		//currentSaleStatsDetailSheet.SetExtendLastCol(1);
		
		returnStatsSheet_OnSearchEnd = function(Code, Msg, StCode, StMsg, Response){
			
			_csClaim.excelBtnEnabled(returnStatsSheet.RowCount());
			returnStatsSheet.SetCellText(1, "dateGbn", _csClaim.gridText($('#choicePeriod').val()));
			//console.log(_csClaim.gridText($('#choicePeriod').val()));
			//returnStatsSheet.SetFrozenRows(1);
		};
		
	}
	
	//당일 교환/반품 처리현황, 교환 sheet
	_csClaim.initClaimChangeSheet = function() {
		var initSheet = {};
		var pageCount = ! abc.text.allNull($("#pageCount")) ? $('#pageCount').val() : 10000;

		initSheet.Cfg = {SearchMode:smServerPaging2, MergeSheet:msHeaderOnly, Page:pageCount, MaxSort:1, FrozenCol:2};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initSheet.Cols = [
			{Header:"기간별 통계|일별 통계", 				Type:"Text",	SaveName:"dateGbn",				Width: 100,		Align:"Center",		Edit:0,		Sort:1	}
			,	{Header:"합계|합계",					Type:"Int",		SaveName:"exchangeTotalCnt",	Width: 100,		Align:"Right",		Edit:0,		Sort:1,	Format:"#,###"	}
			,	{Header:"교환 완료 건수|불량",			Type:"Int",		SaveName:"exchangeRsn01",		Width: 100,		Align:"Right",		Edit:0,		Sort:0,	Format:"#,###"	}
			,	{Header:"교환 완료 건수|오배송",			Type:"Int",		SaveName:"exchangeRsn02",		Width: 100,		Align:"Right",		Edit:0,		Sort:0,	Format:"#,###"	}
			,	{Header:"교환 완료 건수|옵션 변경",			Type:"Int",		SaveName:"exchangeRsn03",		Width: 100,		Align:"Right",		Edit:0,		Sort:0,	Format:"#,###"	}
			,	{Header:"교환 완료 건수|브랜드 박스 훼손",		Type:"Int",		SaveName:"exchangeRsn04",		Width: 100,		Align:"Right",		Edit:0,		Sort:0,	Format:"#,###"	}
			,	{Header:"교환 완료 건수|기타",			Type:"Int",		SaveName:"exchangeRsn05",		Width: 100,		Align:"Right",		Edit:0,		Sort:0,	Format:"#,###"	}
		];

		createIBSheet2(document.getElementById("claimChangeGrid"), "changeStatsSheet", "100%", "429px");
		IBS_InitSheet(changeStatsSheet, initSheet);
		// Grid 건수 정보 표시
		changeStatsSheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		changeStatsSheet.SetPagingPosition(2);
		// Grid width 자동 조절
		changeStatsSheet.FitColWidth();
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		//currentSaleStatsDetailSheet.SetExtendLastCol(1);
		
		changeStatsSheet_OnSearchEnd = function(Code, Msg, StCode, StMsg, Response){
			
			_csClaim.excelBtnEnabled(changeStatsSheet.RowCount());
			changeStatsSheet.SetCellText(1, "dateGbn", _csClaim.gridText($('#choicePeriod').val()));
			//changeStatsSheet.SetFrozenRows(1);
		};
		
	}

	//그리드 text변경
	_csClaim.gridText = function(value){
		var gridText;
		if(value == 'month'){
			gridText = '월별 통계';
		}else if(value == 'year'){
			gridText = '년별 통계';
		}else{
			gridText = '일별 통계';
		}
		
		return gridText;
	}
	
	//기간 validation
	_csClaim.periodValidate = function() {
		var periodType = $('#choicePeriod').val();
		var toDate = '';
		var fromDate = '';
		
		if(periodType == 'date') {
			/*
			toDate = abc.date.changeDate($('input[name=dayToDate]').val());
			fromDate = abc.date.changeDate($('input[name=dayFromDate]').val());
				
			if((toDate.getFullYear() - fromDate.getFullYear()) < 0
					|| ((toDate.getFullYear() - fromDate.getFullYear()) <= 0 && (toDate.getMonth() - fromDate.getMonth()) < 0)
					|| ((toDate.getMonth() - fromDate.getMonth()) <= 0 && (toDate.getDate() - fromDate.getDate()) < 0)) {
							alert('잘못 입력된 기간입니다.');
					}
			*/
			abc.date.searchValidate(); // 일일 날짜 validate 공통
				
		} else if(periodType == 'month') {
			toDate = $('select[name=monthToDate]').val();
			fromDate = $('select[name=monthFromDate]').val();
			
			if((toDate.slice(0, 4) - fromDate.slice(0, 4)) < 0 || (toDate.slice(5, 7) - fromDate.slice(5, 7)) < 0){
				alert('잘못 입력된 기간입니다.');
			}
			
		} else if(periodType == 'year') {
			toDate = $('select[name=yearToDate]').val();
			fromDate = $('select[name=yearFromDate]').val();
			
			if((toDate.slice(0, 4) - fromDate.slice(0, 4)) < 0){
				alert('잘못 입력된 기간입니다.');
			}
		}
	}
	
	// 반품, 교환 체크에 따라 sheet draw
	_csClaim.sheetGridOpt = function() {
		if($('input[name=claimType]:checked').val() == 'return') {
			$('#claimChangeGrid').hide();
			$('#claimReturnGrid').show();
			_csClaim.sheetType = returnStatsSheet;
			
			if(_csClaim.sheetType.RowCount() <= 1) {
				$('#downExcel').addClass('disabled');
			} else {
				$('#downExcel').removeClass('disabled');
			}
		} else if($('input[name=claimType]:checked').val() == 'change') {
			$('#claimChangeGrid').show();
			$('#claimReturnGrid').hide();
			_csClaim.sheetType = changeStatsSheet;

			if(_csClaim.sheetType.RowCount() <= 1) {
				$('#downExcel').addClass('disabled');
			} else {
				$('#downExcel').removeClass('disabled');
			}
		}
	};
	
	//검색전 조건 validate
	_csClaim.preSearchValidate = function(periodType) {
		var chkBoolean = true;
		
		var periodType = periodType;
		
		//일별 선택시
		if(periodType == 'date') {
			
			var toDate = abc.date.changeDate($('input[name=dayToDate]').val());
			var fromDate = abc.date.changeDate($('input[name=dayFromDate]').val());
			
			if($('input[name=dayFromDate]').val() == '' || $('input[name=dayToDate]').val() == '') {
				alert('기간을 설정해주세요.');
				
				chkBoolean = false;
			}
			
			// 일별 조회기간 최대 1년 validate
			if((toDate.getFullYear()-fromDate.getFullYear()) >= 2 || ( (toDate.getFullYear() - fromDate.getFullYear()) >= 1
					&& ( (toDate.getMonth() - fromDate.getMonth()) > 0
					|| (toDate.getDate() - fromDate.getDate()) > 0 ) )){
						alert('조회기간은 최대 1년까지 선택 가능합니다.');
						
						chkBoolean = false;
			}
		}
		return chkBoolean;
	};
	
	_csClaim.csClaimDoAction = function(csAction) {
		//console.log($("#form").serialize());
		switch(csAction) {
		
		case 'search':
			var sheetName = $('input[name=claimType]:checked').val();
			var periodType = $('select[name=choicePeriod]').val();
			
			var pageCount = $('#pageCount').val();
			
			var param = { url : "/stats/cs/claim-read-list"
				, onePageRow : pageCount
				, subparam : FormQueryStringEnc(document.form)
				, sheet : sheetName+'StatsSheet' };
			
			if(_csClaim.preSearchValidate(periodType)) {
				DataSearchPaging(param);
			}

			break;
		}
	};
	
	//엑셀 다운로드 버튼 활성, 비활성화
	_csClaim.excelBtnEnabled = function(rows) {
		
		if(rows > 1) {
			$('#downExcel').removeClass('disabled');
		} else {
			$('#downExcel').addClass('disabled');
		}
		
	};
	
	_csClaim.excelDown = function(){
		//console.log($("#form").serialize());
		var sheetName = $('input[name=claimType]:checked').val();
		
		var sortInfo;
		var sortColumn;
		var sortType;
		
		if( sheetName == "return"){
			
			sortInfo = returnStatsSheet.GetColSortInfo();
			
			if( sortInfo[0] != undefined ){
				
				sortColumn = returnStatsSheet.ColSaveName(sortInfo[0].Col);
				sortType = sortInfo[0].SortOrder;
				$("#sortColumn").val(sortColumn);
				$("#sortType").val(sortType);
			}
		}else if( sheetName == "change" ){
			
			sortInfo = changeStatsSheet.GetColSortInfo();
			
			if( sortInfo[0] != undefined ){
				
				sortColumn = changeStatsSheet.ColSaveName(sortInfo[0].Col);
				sortType = sortInfo[0].SortOrder;
				$("#sortColumn").val(sortColumn);
				$("#sortType").val(sortType);
			}
		}
		
		$("#form").attr('method','post');
		form.action = "/stats/cs/claim-excel-down";
		form.submit();
		$("#form").attr('method','get');
		
		$("#sortColumn").val("");
		$("#sortType").val("");
	}
	
	_csClaim.event = function() {
		
		_csClaim.sheetGridOpt();
		
		//시작일 종료일 채널번호 default.
		_csClaim.fromDate = $('input[name=dayFromDate]').val();
		_csClaim.toDate = $('input[name=dayToDate]').val();
		
		//기간 변경
		$('#choicePeriod').off().on('change', function(){
			
			//일별 일경우 월별, 년별 비노출
			if($(this).val() == 'date') {
				$('#monthFromDate, #monthWaveMark, #monthToDate').addClass('hidden');
				$('#yearFromDate, #yearWaveMark, #yearToDate').addClass('hidden');
				$('#dayFromDateSpan, #dayWaveMark, #dayToDateSpan').removeClass('hidden');
				$('#toYesterday, #toWeek, #toMonth').removeClass('hidden');
				
			// 월별 일경우 일별, 년별 비노출
			} else if($(this).val() == 'month') {
				$('#yearFromDate, #yearWaveMark, #yearToDate').addClass('hidden');
				$('#toYesterday, #toWeek, #toMonth').addClass('hidden');
				$('#dayFromDateSpan, #dayWaveMark, #dayToDateSpan').addClass('hidden');
				$('#monthFromDate, #monthWaveMark, #monthToDate').removeClass('hidden');
				
			// 년별 일경우 일별, 월별 비노출
			} else if($(this).val() == 'year') {
				$('#dayFromDateSpan, #dayWaveMark, #dayToDateSpan').addClass('hidden');
				$('#toYesterday, #toWeek, #toMonth').addClass('hidden');
				$('#monthFromDate, #monthWaveMark, #monthToDate').addClass('hidden');
				$('#yearFromDate, #yearWaveMark, #yearToDate').removeClass('hidden');

			}
		});
		
		//일별 선택시 어제,일주일,한달 선택 버튼
		$('#toYesterday').off().on('click', function(){
			abc.date.statDays(this);
		});
		$('#toWeek').off().on('click', function(){
			abc.date.statWeek(this);
		});
		$('#toMonth').off().on('click', function(){
			abc.date.statMonth(this);
		});
		
		// 일별,월별,년별 기간 값 변경시 validate 체크
		$('input[name=dayFromDate], input[name=dayToDate], select[name=monthFromDate], select[name=monthToDate]'
				+ ', select[name=yearFromDate], select[name=yearToDate]').on('change', function(){
			
			_csClaim.periodValidate();
				
		 });
		
		//상품코드 구분
		$('#mmnyPrdtYn').off().on('change', function(){

			if($(this).val() == 'Y') {
				$('#ourChannel').removeClass('hidden');
				$('#shoperSiteNo').addClass('hidden').val('');
				
				//자사 선택시, 입점사 비노출, 나머지 노출
				$('#chkStore10003').prop('checked', false).parent().parent().addClass('hidden');
				$('#chkStore10000, #chkStore10001, #chkStore10002').prop('checked', true).parent().parent().removeClass('hidden');

			} else if($(this).val() == 'N') {
				$('#ourChannel').addClass('hidden').val('');
				$('#shoperSiteNo').removeClass('hidden');
				
				//입점 선택시, 입점사 노출, 나머지 비노출
				$('#chkStore10003').prop('checked', true).parent().parent().removeClass('hidden');
				$('#chkStore10000, #chkStore10001, #chkStore10002').prop('checked', false).parent().parent().addClass('hidden');

			} else if($(this).val() == '') {
				$('#ourChannel').addClass('hidden').val('');
				$('#shoperSiteNo').addClass('hidden').val('');
				
				//전체 선택시 발송처 모두 노출
				$('input[name=stockGbnCodeArr]').prop('checked', true).parent().parent().removeClass('hidden');

			}
		});
		
		// 반품/교환 처리현황 요청 구분 버튼시 sheet 변경
		$('input[name=claimType]').off().on('change', function(){
			_csClaim.sheetGridOpt();
			
		});
		
		//검색 조회.
		$('#searchBtn').off().on('click', function(){
			_csClaim.csClaimDoAction('search');
		});
		//검색조건 초기화
		$('#resetBtn').off().on('click', function() {
			$('#form')[0].reset();
			
			$('#ourChannel').addClass('hidden').val('');
			if(_csClaim.isAdmin) {
				$('#shoperSiteNo').addClass('hidden').val('');
			}
			$('input[names=tockGbnCodeArr]').prop('checked', true).parent().parent().removeClass('hidden');
			_csClaim.sheetGridOpt();
			
			abc.date.statWeek($('#toWeek'));
		});
		
		if(_csClaim.isAdmin == false){
			// bo, po 권한구분
			$("#mmnyPrdtYn").empty().append("<option value='N'>입점</option>").prop('disabled', true);
			
			$('#ourChannel').addClass('hidden').val('');
			$('#shoperSiteNo').addClass('hidden').val('');
			
			//입점 선택시, 입점사 노출, 나머지 비노출
			$('#chkStore10003').prop('checked', true).parent().parent().removeClass('hidden');
			$('#chkStore10000, #chkStore10001, #chkStore10002').prop('checked', false).parent().parent().addClass('hidden');
		}
		
		abc.date.statWeek($('#toWeek'));
	}
	
})();
