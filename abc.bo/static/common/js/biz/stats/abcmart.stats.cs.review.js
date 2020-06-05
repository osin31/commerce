(function() { // 사이트 관리.

	var _object = abc.object.createNestedObject(window,"abc.biz.stats.cs.review");

	/**
	 * 초기화
	 */
	_object.init = function() {
		_object.event();
		_object.sheet.init();
		_object.sheet.event();
	}

	_object.sheet = {};
	_object.sheet.init = function() {
		createIBSheet2(document.getElementById("reviewGrid"), "list", "100%", "429px");

		var initSheet = {};
		var pageCount = $('#page-count').val();
		initSheet.Cfg = {SearchMode : smServerPaging2,	Page : pageCount, MaxSort : 1, MergeSheet:msHeaderOnly, AutoFitColWidth: "init|search|resize"};
		initSheet.HeaderMode = {Sort : 1, ColMove : 0, ColResize : 1};
		initSheet.Cols = [
				{Header:"일자별 합계|일자별 합계",		Type:"Text",			SaveName:"reviewDtm",			Width: 100,		Align:"Center",		Edit:0,		Sort:1 }
			,	{Header:"합계|총 지급 포인트",			Type:"Text",			SaveName:"totalPoint",			Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"합계|총 지급 건수",			Type:"Text",			SaveName:"totalPayCount",		Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"합계|총 미지급 건수",			Type:"Text",			SaveName:"totalPayNo",			Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"합계|총 지급취소 건수",			Type:"Text",			SaveName:"totalPayCncl",		Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"합계|총 지급불가 건수",			Type:"Text",			SaveName:"totalPayImpsblt",		Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"일반후기|지급 포인트",			Type:"Text",			SaveName:"generalPoint",		Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"일반후기|지급",				Type:"Text",			SaveName:"generalPayCount",		Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"일반후기|미지급",				Type:"Text",			SaveName:"generalPayNo",		Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"일반후기|지급취소",			Type:"Text",			SaveName:"generalPayCncl",		Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"일반후기|지급불가",			Type:"Text",			SaveName:"generalPayImpsblt",	Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"포토후기|지급 포인트",			Type:"Text",			SaveName:"photoPoint",			Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"포토후기|지급",				Type:"Text",			SaveName:"photoPayCount",		Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"포토후기|미지급",				Type:"Text",			SaveName:"photoPayNo",			Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"포토후기|지급취소",			Type:"Text",			SaveName:"photoPayCncl",		Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"포토후기|지급불가",			Type:"Text",			SaveName:"photoPayImpsblt",		Width: 100,		Align:"Right",		Edit:0,		Sort:0 }

		];

		// Grid 초기화
		IBS_InitSheet(list, initSheet);
		// Grid 건수 정보 표시
		list.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		list.SetPagingPosition(2);
		// Grid width 자동 조절
		list.FitColWidth("7|7|7|7|7|7|7|7|7|7|7|7|7|7");
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		list.SetExtendLastCol(2);
		list.SetFrozenCol(6);
		list.SetDataRowMerge(1);

		_object.sheet.list = list;
	}


	/**
	 * IBSheet 이벤트
	 */
	_object.sheet.event = function(){

		list_OnSearchEnd = function(code, msg) {
		    if (code == 0) {
//		        list.SetMergeCell(2, 0, 1, 3, 1);
				list.SetFrozenRows(1);
		    }
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

		//enter 검색
		$(".search-wrap").find(".ui-input, .js-ui-cal").on("keypress", function(e) {
			if(e.keyCode === 13) {
				$("#search").trigger("click");
			}
		});

		// 초기화
		$("#clear").off().on("click", function(e) {
			$("#form")[0].reset();
			$('a[name="perWeek"]').click();
		});

		// 구입처구분 이벤트
		$("[name=onlnBuyYn]").on('change',function(){
			var flag = false;
			if($(this).val() == 'N'){
				flag = true;
			} else {
				flag = false;
			}
			$("input[name=siteNo]").prop('disabled', flag);
			$('#device-code-all').prop('disabled', flag);
			$('.device-code').prop('disabled', flag);
		});

		// 검색 버튼 이벤트
		$("#search").click(function() {
			_object.getList();
		});

		// 페이지별 갯수 변경 이벤트
		$("#page-count").on("change",function(){
			_object.getList();
		});

		// 기간 제어
		$(".term-date-wrap .btn-group .btn-func").click(function(e) {
			var type = $(this).attr("name");
			switch(type) {
			case "perYesterday" :
				abc.date.yesterday(this);
				break;
			case "perWeek" :
				abc.date.statWeek(this);
				break;
			case "per1Month" :
				abc.date.statMonth(this);
				break;
			case "perYear" :
				abc.date.statYear(this);
				break;
			default :
				console.log("지원하지 않는 유형입니다. " + type);
			}
		});

		//엑셀 다운로드
		$('#downExcel').off().on('click', function(){
			if(list.GetTotalRows() == 0) {
				alert('엑셀 다운로드는 조회 후에 이용 가능합니다.');
					return false;
			}
			$('#form').prop('action', '/stats/cs/download-reviewExcel').prop('method', 'post').submit();
		});

		// 체크박스 제어
		abc.biz.display.common.checkBoxAll({allId: '#device-code-all', itemsClass: '.device-code'});
		abc.biz.display.common.checkBoxAll({allId: '#member-type-code-all', itemsClass: '.member-type-code'});

		// 기본 날짜 세팅
		$('a[name="perWeek"]').click();
		// 끝 기간 날짜 제한
		abc.date.setMaxDate($('.term-date-wrap'));
	}

	/**
	 * 목록 조회
	 */
	_object.getList = function(){
		if (!abc.date.searchValidate()) {
			return false;
		}

		DataSearchPaging({
			  url : "/stats/cs/review-stats-search"
			, onePageRow : $("#page-count").val()
			, subparam : $("#form").serialize()
			, sheet : "list"
		});
	}

	$(function() {
		_object.init();
	});

})();