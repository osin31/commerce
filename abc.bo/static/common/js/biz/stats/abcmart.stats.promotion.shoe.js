(function() {

	var _proShoe = abc.object.createNestedObject(window,"abc.biz.stats.promotion.shoe");

	abc.biz.stats.order.common.init();
	
	_proShoe.promoFromDate = '';
	_proShoe.promoToDate = '';
	
	_proShoe.initShoeStatsSheet = function() {
		var initSheet = {};
		var pageCount = ! abc.text.allNull($("#pageCount")) ? $('#pageCount').val() : 10000;

		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MaxSort:1,  MergeSheet: msHeaderOnly};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initSheet.Cols = [
				{Header:"기간별통계|일별통계", 					Type:"Text",		SaveName:"hourTitle",		Width: 150,		Align:"Center",		Edit:0,		Sort:0 }
			,	{Header:"합계|수량",							Type:"Int",			SaveName:"orderQtyAll",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"합계|에누리금액",						Type:"Int",			SaveName:"dscntAmtAll",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"합계|결제금액",						Type:"Int",			SaveName:"pymntAmtAll",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"2족|수량",								Type:"Int",			SaveName:"twoQty",			Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"2족|에누리금액",						Type:"Int",			SaveName:"twoDscnt",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"2족|결제금액",							Type:"Int",			SaveName:"twoPymnt",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"3족|수량",								Type:"Int",			SaveName:"threeQty",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"3족|에누리금액",						Type:"Int",			SaveName:"threeDscnt",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"3족|결제금액",							Type:"Int",			SaveName:"threePymnt",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"4족|수량",								Type:"Int",			SaveName:"fourQty",			Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"4족|에누리금액",						Type:"Int",			SaveName:"fourDscnt",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"4족|결제금액",							Type:"Int",			SaveName:"fourPymnt",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"5족|수량",								Type:"Int",			SaveName:"fiveQty",			Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"5족|에누리금액",						Type:"Int",			SaveName:"fiveDscnt",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"5족|결제금액",							Type:"Int",			SaveName:"fivePymnt",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"6족 이상|수량",						Type:"Int",			SaveName:"sixMoreQty",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"6족 이상|에누리금액",					Type:"Int",			SaveName:"sixMoreDscnt",	Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"6족 이상|결제금액",					Type:"Int",			SaveName:"sixMorePymnt",	Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			
		];

		createIBSheet2(document.getElementById("shoeStatsGrid"), "shoeStatsSheet", "100%", "429px");
		IBS_InitSheet(shoeStatsSheet, initSheet);
		
		 //Grid 건수 정보 표시
		shoeStatsSheet.SetCountPosition(3);
		 //Grid 페이지 네비게이션 버튼 표시
		shoeStatsSheet.SetPagingPosition(2);
		 //Grid width 자동 조절
		shoeStatsSheet.FitColWidth();
		 //Grid 마지막 컬럼을 All 너비에 맞춘다.
		shoeStatsSheet.SetExtendLastCol(1);
		//좌측행 고정.
		shoeStatsSheet.SetFrozenCol(4);
		
		shoeStatsSheet_OnSearchEnd = function(Code, Msg, StCode, StMsg, Response){
			
			shoeStatsSheet.SetCellText(1, 0, _proShoe.gridText($('#choicePeriod').val()));
			_proShoe.excelBtnEnabled(shoeStatsSheet.RowCount());
		};
		
	};
	
	//일별 - 어제, 일주일, 한달, 1년 관련 date
	_proShoe.periodYwm = function(data) {
		var toDate = $('input[name=dayToDate]');
		if(data == 'today') {
			abc.date.days(toDate);
		}
		if(data == 'yesterday') {
			abc.date.yesterday(toDate);
		}
		if(data == 'week') {
			abc.date.week(toDate);
		}
		if(data == 'month') {
			abc.date.month(toDate);
		}
		if(data == 'year') {
			abc.date.year(toDate);
		}
		
		_proShoe.promoFromDate = $('input[name=dayFromDate]').val();
		_proShoe.promoToDate = $('input[name=dayToDate]').val();
		
	};
	
	//일별, 월별, 년별 날짜 세팅
	_proShoe.periodSet = function(data) {
		
		//일별
		if(data == 'date') {
			var years = $('input[name=dayToDate]').val().slice(0,4);
			var month = $('input[name=dayToDate]').val().slice(5,7);
			var day = $('input[name=dayToDate]').val().slice(8,10);
			var today = new Date();
			
			_proShoe.promoFromDate = $('input[name=dayFromDate]').val();
			
			//
			if( (today.getFullYear() < years) || ((today.getFullYear() == years) && ((today.getMonth()+1) < month))
					|| ( ( (today.getFullYear() <= years) && ( (today.getMonth()+1) <= month) ) && ( day - (today.getDate()-1) ) > 0 ) ) {
						_proShoe.promoToDate = today.format('yyyy.MM.dd');
			} else {
				_proShoe.promoToDate = $('input[name=dayToDate]').val();
			}
		}
		
		//월별
		if(data == 'month') {
			
			var years = $('#monthToDate').val().slice(0,4);
			var month = $('#monthToDate').val().slice(5,7);
			var lastDay = (new Date(years, month, 0)).getDate();
			var today = new Date();
			
			_proShoe.promoFromDate = $('#monthFromDate').val() + '.01';
			
			// month가 이번달& start end 월이 같을경우는 오늘 날짜로 세팅
			if((today.getMonth()+1) == month) {
				_proShoe.promoToDate = today.format('yyyy.MM.dd');
			} else {
				_proShoe.promoToDate = years + '.' + month + '.' + lastDay;
			}
		}
		//년별
		if(data == 'year') {
			
			var years = $('#yearToDate').val().slice(0,4);
			var month = $('#yearToDate').val().slice(5,7);
			var thisYear = new Date();
			
			_proShoe.promoFromDate = $('#yearFromDate').val() + '.01.01';
			
			//선택 년도가 start, end가 올해로 했을경우 오늘 날짜로 세팅
			if(thisYear.getFullYear() == years) {
				_proShoe.promoToDate = thisYear.format('yyyy.MM.dd');
			} else {
				_proShoe.promoToDate = $('#yearToDate').val() + '.12.31';
			}
			
		}
	};
	
	//검색전 data set
	_proShoe.preSearchDataSet = function() {
		var periodType = $('#choicePeriod').val();
		
		$('#form').children().find('input:hidden[name=promoFromDate]').remove();
		$('#form').children().find('input:hidden[name=promoToDate]').remove();
		
		// 일별, 월별, 년별 선택시 기간 set
		if(periodType == 'date') {
			_proShoe.periodSet('date');
		} else {
			_proShoe.periodSet(periodType);
		}
		
		
		var html ='';
		html += '<input type="hidden" name="promoFromDate" value="' + _proShoe.promoFromDate +'">';
		html += '<input type="hidden" name="promoToDate" value="' + _proShoe.promoToDate +'">';

		$('#form').children().append(html);
	};
	
	//검색전 조건 validate
	_proShoe.preSearchValidate = function(periodType) {	
		var valiPeriodType = periodType;

		//일별 선택시
		if(valiPeriodType == 'date') {
			
			var toDate = abc.date.changeDate($('input[name=dayToDate]').val());
			var fromDate = abc.date.changeDate($('input[name=dayFromDate]').val());
			
			if($('input[name=dayFromDate]').val() == '' || $('input[name=dayToDate]').val() == '') {
				alert('기간을 설정해주세요.');
				
				return false;
			}
			
			if(fromDate > toDate) {
				alert('잘못 입력된 기간입니다.');
				_proshoe.periodYwm('week');
				$('input[name=dayFromDate]').focus();
				
				return false;
			}
			
			// 일별 조회기간 최대 1년 validate
			if((toDate.getFullYear()-fromDate.getFullYear()) >= 2 || ( (toDate.getFullYear() - fromDate.getFullYear()) >= 1
					&& ( (toDate.getMonth() - fromDate.getMonth()) > 0
					|| (toDate.getDate() - fromDate.getDate()) > 0 ) )){
						alert('조회기간은 최대 1년까지 선택 가능합니다.');
						
						abc.date.week($('input[name=dayToDate]'));
						
						return false;
			}
		} else if(valiPeriodType == 'month') {
			toDate = $('select[name=monthToDate]').val();
			fromDate = $('select[name=monthFromDate]').val();
			
			if((toDate.slice(0, 4) - fromDate.slice(0, 4)) < 0 || (toDate.slice(5, 7) - fromDate.slice(5, 7)) < 0){
				alert('잘못 입력된 기간입니다.');
	
				$('select[name=mon thFromDate]').val(fromDate.slice(0, 4) + '.' + fromDate.slice(5, 7)).prop('selected', true);
				$('select[name=monthToDate]').val(fromDate.slice(0, 4) + '.' + fromDate.slice(5, 7)).prop('selected', true);
				
				return false;
			}
			
		} else if(valiPeriodType == 'year') {
			toDate = $('select[name=yearToDate]').val();
			fromDate = $('select[name=yearFromDate]').val();
			
			if((toDate.slice(0, 4) - fromDate.slice(0, 4)) < 0){
				alert('잘못 입력된 기간입니다.');
				
				var thisYear = new Date();
				$('select[name=yearFromDate]').val(String(thisYear.format('yyyy'))).prop('selected', true);
				$('select[name=yearToDate]').val(String(thisYear.format('yyyy'))).prop('selected', true);
				
				return false;
			}
		}
		
		return true;
	};
	
	//그리드 text변경
	_proShoe.gridText = function(value){
		var gridText;
		if(value == 'month'){
			gridText = '월별통계';
		}else if(value == 'year'){
			gridText = '년별통계';
		}else{
			gridText = '일별통계';
		}
		
		return gridText;
	}
	
	//검색함수
	_proShoe.proShoeDoAction = function(proAction) {
		
		switch(proAction) {
			case 'search' :
				
				var periodType = $('#choicePeriod').val();
				var pageCount = $('#pageCount').val();
				var param = { url : "/stats/promotion/shoe-read-list"
					, onePageRow : pageCount
					, subparam : FormQueryStringEnc(document.form)
					, sheet : 'shoeStatsSheet' };
				
				if(_proShoe.preSearchValidate(periodType)) {
					DataSearchPaging(param);
				}
				
				break;
		}
	};
	
	//엑셀 다운로드 버튼 활성, 비활성화
	_proShoe.excelBtnEnabled = function(rows) {
		
		if(rows > 1) {
			$('#downExcel').removeClass('disabled');
		} else {
			$('#downExcel').addClass('disabled');
		}
		
	};
	
	_proShoe.event = function() {
		//일별 기간 default.
		_proShoe.periodYwm('week');
		_proShoe.periodSet('date');
		
		//기간 변경
		$('#choicePeriod').off().on('change', function(){
			
			//일별 일경우 월별, 년별 비노출
			if($(this).val() == 'date') {
				$('#monthFromDate, #monthWaveMark, #monthToDate').addClass('hidden');
				$('#yearFromDate, #yearWaveMark, #yearToDate').addClass('hidden');
				$('#dayFromDateSpan, #dayWaveMark, #dayToDateSpan').removeClass('hidden');
				$('#toToday, #toWeek, #toMonth, #toYear').removeClass('hidden');
				
			// 월별 일경우 일별, 년별 비노출
			} else if($(this).val() == 'month') {
				$('#yearFromDate, #yearWaveMark, #yearToDate').addClass('hidden');
				$('#toToday, #toWeek, #toMonth, #toYear').addClass('hidden');
				$('#dayFromDateSpan, #dayWaveMark, #dayToDateSpan').addClass('hidden');
				$('#monthFromDate, #monthWaveMark, #monthToDate').removeClass('hidden');
				
			// 년별 일경우 일별, 월별 비노출
			} else if($(this).val() == 'year') {
				$('#dayFromDateSpan, #dayWaveMark, #dayToDateSpan').addClass('hidden');
				$('#toToday, #toWeek, #toMonth, #toYear').addClass('hidden');
				$('#monthFromDate, #monthWaveMark, #monthToDate').addClass('hidden');
				$('#yearFromDate, #yearWaveMark, #yearToDate').removeClass('hidden');

			}
		});
		
		//일별 선택시 오늘,어제,일주일,한달, 1년 선택 버튼
		$('#toToday').off().on('click', function(){
			_proShoe.periodYwm('today');
		});
		$('#toYesterday').off().on('click', function(){
			_proShoe.periodYwm('yesterday');
		});
		$('#toWeek').off().on('click', function(){
			_proShoe.periodYwm('week');
		});
		$('#toMonth').off().on('click', function(){
			_proShoe.periodYwm('month');
		});
		$('#toYear').off().on('click', function(){
			_proShoe.periodYwm('year');
		});
		
		//프로모션 검색어
		$('#searchKeyword').off().on('keyup', _proShoe.promoSearchValidate);
		
		//검색 버튼
		$('#searchBtn').off().on('click', function(){
			_proShoe.preSearchDataSet();
			_proShoe.proShoeDoAction('search');
		});
		
		$($('#promotionType')).off().on('change', function(){
			if($(this).val() == 'promoNo') {
				$('#searchKeyword').prop('maxlength', '10');
			} else {
				$('#searchKeyword').prop('maxlength', '50');
			}
		});
		
		//초기화
		$('#resetBtn').off().on('click', function(){
			$('#form')[0].reset();
			abc.date.week($('input[name=dayToDate]'));
			$('#monthFromDate, #monthWaveMark, #monthToDate').addClass('hidden');
			$('#yearFromDate, #yearWaveMark, #yearToDate').addClass('hidden');
			$('#dayFromDateSpan, #dayWaveMark, #dayToDateSpan').removeClass('hidden');
			$('#toYesterday, #toWeek, #toMonth').removeClass('hidden');
		});
		
		//엑셀 다운로드
		$('#downExcel').off().on('click', function(){
			$('#form').prop('action', '/stats/promotion/shoe-excel-down')
					  .prop('method', 'post')
					  .submit();
		});
	};
	
})();