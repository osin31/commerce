/***
 * 사이트 관리.
 *
 */
(function() {
	var _object = abc.object.createNestedObject(window,"abc.biz.stats.promotion.coupon");

	_object.sheet = {};

	/**
	 * 페이지 초기화
	 */
	_object.init = function(){
		//일별 기간 default.
		_object.periodYwm('week');
		_object.periodSet('day');

		_object.fromDate = $('input[name=dayFromDate]').val();
		_object.toDate = $('input[name=dayToDate]').val();

		_object.event();
		_object.sheet.init();
		_object.sheet.event();

		// 기본적으로 전체 체크되어있기때문에 숨김처리
		//$(".ononly").hide();
	}

	/**
	 * IBSheet 이벤트
	 */
	_object.sheet.event = function(){

		list_OnSearchEnd = function(code, msg) {
		    // 엑셀 다운로드 버튼 제어
			if(list.GetTotalRows() == 0) {
				$('#downExcel').addClass('disabled');
			} else {
				$('#downExcel').removeClass('disabled');
			}
		}
	}

	/**
	 * 이벤트 바인딩
	 */
	_object.event = function(){
		//오늘부터 과거만 선택가능
		$("#fromDate").datepicker({
			constrainInput: false ,
			showOn: "both" ,
			maxDate : "0"
		});

		//오늘부터 과거만 선택가능
		$("#toDate").datepicker({
			constrainInput: false ,
			showOn: "both" ,
			maxDate : "0"
		});

		/**
		 * 검색버튼 이벤트
		 */
		$("#search").on("click", function() {
			var valid = _object.valid();
			if(valid) {
				_object.getList();
			}
		});

		/**
		 * 오프라인 사용여부에 따른 쿠폰사용처/쿠폰생성형태 컨트롤 이벤트
		 */
		$("[id^=usePlaceGbnType0").on("click", function() {
			var oChecked = $("#usePlaceGbnType01").is(":checked");
			var fChecked = $("#usePlaceGbnType02").is(":checked");
			var aChecked = $("#usePlaceGbnType03").is(":checked");
			if(fChecked && !oChecked && !aChecked) {
				$(".ononly").hide();
			} else if(!fChecked && !oChecked && !aChecked) {
				$(".ononly").show();
			} else {
				$(".ononly").show();
			}
		});

		/**
		 * 체크박스 컨트롤 이벤트
		 */
		$("[data-check]").on("click", function(){
			var id = $(this).attr("id");
			var checkFlag = $("#"+id).is(":checked");

			if(id.indexOf("All") != -1) {
				var idSplit = id.split("All")[0];

				$("[id^="+idSplit+"0]").each(function(){
					$(this).prop("checked", checkFlag);
				});
			} else {
				checkFlag = true;
				var idSubstr = id.substring(0, id.length-2);

				$("[id^="+idSubstr+"0]").each(function(){
					checkFlag = $(this).is(":checked");
					if(!checkFlag){
						return false;
					}
				});
				$("#"+idSubstr+"All").prop("checked", checkFlag);
			}
		});

		//일별 선택시 어제,일주일,한달 선택 버튼
		$('#toToday').off().on('click', function(){
			_object.periodYwm('today');

			_object.fromDate = $('input[name=dayFromDate]').val();
			_object.toDate = $('input[name=dayToDate]').val();
		});
		$('#toYesterday').off().on('click', function(){
			_object.periodYwm('yesterday');

			_object.fromDate = $('input[name=dayFromDate]').val();
			_object.toDate = $('input[name=dayToDate]').val();
		});
		$('#toWeek').off().on('click', function(){
			_object.periodYwm('week');

			_object.fromDate = $('input[name=dayFromDate]').val();
			_object.toDate = $('input[name=dayToDate]').val();
		});
		$('#toMonth').off().on('click', function(){
			_object.periodYwm('month');

			_object.fromDate = $('input[name=dayFromDate]').val();
			_object.toDate = $('input[name=dayToDate]').val();
		});
		$('#toYear').off().on('click', function(){
			_object.periodYwm('year');

			_object.fromDate = $('input[name=dayFromDate]').val();
			_object.toDate = $('input[name=dayToDate]').val();
		});

		//엑셀 다운로드
		$('#downExcel').off().on('click', function(){

			if(list.GetTotalRows() == 0) {
				alert('엑셀 다운로드는 조회 후에 이용 가능합니다.');
				return false;
			}

			$('#search-form').prop('action', '/stats/promotion/download-couponStatsExcel').prop('method', 'post').submit();
		});

		//초기화
		$("#resetBtn").on("click", function() {
			$("#search-form")[0].reset();
		});

		// 끝 기간 날짜 제한
		abc.date.setMaxDate($('.term-date-wrap'));
	}

	/**
	 * 시트 초기화
	 */
	_object.sheet.init = function(){
		createIBSheet2(document.getElementById("coupon-list"), "list", "100%", "492px");

		var initSheet = {};
		var pageCount = $('#pageCount').val();

		initSheet.Cfg = { SearchMode:smServerPaging2, Page:pageCount, MaxSort:1, MergeSheet: msHeaderOnly, SumPosition:0, FrozenCol:6 };
		initSheet.HeaderMode = { Sort:0, ColMove:0, ColResize:1 };

		initSheet.Cols = [
			{ Header:"상태",						Type:"Status",	SaveName:"status", 			Width: 10,		Align:"Center",		Edit:0,	Cursor:"Pointer", Hidden:1 },
			{ Header:"NO|NO",					Type:"Text",	SaveName:"cpnNo", 			Width: 18,		Align:"Center",		Edit:0, Cursor:"Pointer" },
			{ Header:"전시여부|전시여부",				Type:"Text",	SaveName:"dispYn", 			Width: 10,		Align:"Center",		Edit:0, Cursor:"Pointer" },
			{ Header:"분류|분류",					Type:"Text",	SaveName:"cpnUseGbnType", 	Width: 14,		Align:"Center",		Edit:0, Cursor:"Pointer" },
			{ Header:"속성|속성",					Type:"Text",	SaveName:"normalCpnYn", 	Width: 14,		Align:"Center",		Edit:0, Cursor:"Pointer" },
			{ Header:"쿠폰명|쿠폰명",				Type:"Text",	SaveName:"cpnName", 		Width: 10,		Align:"Center",		Edit:0, Cursor:"Pointer" },
			{ Header:"생성형태|생성형태",				Type:"Text",	SaveName:"cpnCrtType", 		Width: 14,		Align:"Center",		Edit:0, Cursor:"Pointer" },
			{ Header:"사용처|사용처",				Type:"Text",	SaveName:"usePlaceGbnType", Width: 14,		Align:"Center",		Edit:0, Cursor:"Pointer" },
			{ Header:"쿠폰종류|쿠폰종류",				Type:"Text",	SaveName:"cpnTypeCode", 	Width: 10,		Align:"Center",		Edit:0, Cursor:"Pointer" },
			{ Header:"할인금액/율|할인금액/율",			Type:"Text",	SaveName:"dscntValue", 		Width: 14,		Align:"Center",		Edit:0, Cursor:"Pointer" },
			{ Header:"유효기간|유효기간",				Type:"Text",	SaveName:"validDtm", 		Width: 14,		Align:"Center",		Edit:0, Cursor:"Pointer" },
			{ Header:"발급수|발급수",				Type:"Int",		SaveName:"totalIssueCount", Width: 10,		Align:"Center",		Edit:0, Cursor:"Pointer" },
			{ Header:"사용수/쿠폰매출|사용수/쿠폰매출",	Type:"Text",	SaveName:"useCount", 		Width: 14,		Align:"Right",		Edit:0, Cursor:"Pointer" },
			{ Header:"사용처|온라인",				Type:"Text",	SaveName:"useOnlineCount",	Width: 14,		Align:"Right",		Edit:0, Cursor:"Pointer" },
			{ Header:"사용처|오프라인",				Type:"Text",	SaveName:"useOfflineCount",	Width: 10,		Align:"Right",		Edit:0, Cursor:"Pointer" },
			{ Header:"디바이스|PC",				Type:"Text",	SaveName:"devicePcCount",	Width: 14,		Align:"Right",		Edit:0, Cursor:"Pointer" },
			{ Header:"디바이스|MOBILE",			Type:"Text",	SaveName:"deviceMoCount",	Width: 14,		Align:"Right",		Edit:0, Cursor:"Pointer" },
			{ Header:"디바이스|APP",				Type:"Text",	SaveName:"deviceAppCount",	Width: 10,		Align:"Right",		Edit:0, Cursor:"Pointer" }
		];

		IBS_InitSheet(list , initSheet);
		list.SetCountPosition(3);
		list.SetPagingPosition(2);
		list.FitColWidth("5|5|7|7|10|7|7|7|7|12|5|7|7|7|7|7|7");
		list.SetExtendLastCol(1);

		_object.getList();
	};

	/**
	 * 리스트 조회
	 */
	_object.getList = function() {
		var url = "/stats/promotion/coupon-list";
		var serializedFormData = $("#search-form").serialize();
		var pageCount = $("#pageCount").val();
		var param = {
			url : url
			, onePageRow : pageCount
			, subparam : serializedFormData
			, sheet : "list" };

		DataSearchPaging(param);
	}

	//일별 - 어제, 일주일, 한달, 일년 관련 date
	_object.periodYwm = function(data) {

		var fromDate = new Date();
		var toDate = new Date();

		if(data == 'today') {
			toDate.setDate(toDate.getDate());
			fromDate.setDate(toDate.getDate());
		}
		if(data == 'yesterday') {
			toDate.setDate(toDate.getDate());
			fromDate.setDate(toDate.getDate()-1);
		}
		if(data == 'week') {
			toDate.setDate(toDate.getDate());
			fromDate.setDate(toDate.getDate()-7);
		}
		if(data == 'month') {
			toDate.setDate(toDate.getDate());
			fromDate.setMonth(toDate.getMonth()-1);
			fromDate.setDate(fromDate.getDate() + 1);
		}
		if(data == 'year') {
			toDate.setDate(toDate.getDate());
			fromDate.setYear(toDate.format('yyyy') - 1);
			fromDate.setDate(fromDate.getDate() + 1);
		}

		$('input[name=dayFromDate]').val(fromDate.format('yyyy.MM.dd'));
		$('input[name=dayToDate]').val(toDate.format('yyyy.MM.dd'));

	};

	_object.periodSet = function(data) {

		//일별
		if(data == 'day') {

			_object.fromDate = $('input[name=dayFromDate]').val();
			_object.toDate = $('input[name=dayToDate]').val();
		}

		//월별
		if(data == 'month') {

			var years = $('#monthToDate').val().slice(0,4);
			var month = $('#monthToDate').val().slice(5,7);
			var lastDay = (new Date(years, month, 0)).getDate();
			var today = new Date();

			_object.fromDate = $('#monthFromDate').val() + '.01';

			if((today.getMonth()+1) == month){
				_object.toDate = years + '.' + month + '.' + (today.getDate()-1);
			} else{
				_object.toDate = years + '.' + month + '.' + lastDay;
			}
		}
		//년별
		if(data == 'year') {

			var years = $('#yearToDate').val().slice(0,4);
			var month = $('#yearToDate').val().slice(5,7);
			var thisYear = new Date();

			_statsType.fromDate = $('#yearFromDate').val() + '.01.01';

			if(thisYear.getFullYear() == years) {
				thisYear.setDate(thisYear.getDate()-1);
				_statsType.toDate = thisYear.format('yyyy.MM.dd');
			} else {
				_statsType.toDate = $('#yearToDate').val() + '.12.31';
			}

		}
	};

	_object.valid = function() {
		var dayFromDate = $("input[name=dayFromDate]").val();
		var dayToDate = $("input[name=dayToDate]").val();

		var fromDate = new Date(dayFromDate.split(".")[0], dayFromDate.split(".")[1], dayFromDate.split(".")[2]);
		var toDate = new Date(dayToDate.split(".")[0], dayToDate.split(".")[1], dayToDate.split(".")[2]);

		var diff = Math.abs(fromDate - toDate);
		diff = Math.ceil(diff / (1000 * 3600 * 24));

		// 1년 이상 조회 불가
		if(diff > 365) {
			alert("조회기간은 최대 1년까지 선택 가능합니다.");
			return false;
		}

		return true;
	}


	$(function() {
		_object.init();
	});
})();