(function() { // 재입고 알림 서비스 상품별 통계

	var _object = abc.object.createNestedObject(window,"abc.biz.stats.restock.product");

	/**
	 * 초기화
	 */
	_object.init = function() {
		_object.event();
		_object.sheet.init();
		_object.sheet.event();

		if ($("input[name=fromDate]").val() == "" && $("input[name=toDate]").val() == "" ){
			$("input[name=toDate]").datepicker("setDate", "today");
			$("input[name=fromDate]").datepicker("setDate", "-7D");
		}
		abc.biz.display.common.searchFormCalendarBtnEvent('input[name=fromDate]', 'input[name=toDate]', '.area-calendar-btn-group');
	}

	_object.sheet = {};
	_object.sheet.init = function() {
		createIBSheet2(document.getElementById("restockGrid"), "list", "100%", "429px");

		var initSheet = {};
		var pageCount = $('#page-count').val();
		initSheet.Cfg = {SearchMode : smServerPaging2,	Page : pageCount, MaxSort : 1, MergeSheet:msHeaderOnly, AutoFitColWidth: "init|search|resize"};
		initSheet.HeaderMode = {Sort : 1, ColMove : 0, ColResize : 1};
		initSheet.Cols = [
				{Header:"번호|번호", 					Type:"Text",		SaveName:"selectSeq", 		Width: 5, 		Align:"Center", 	Edit:0, 	Sort:0}
			,	{Header:"상품|브랜드",					Type:"Text",		SaveName:"brandName",		Width: 100,		Align:"Center",		Edit:0,		Sort:0 }
			,	{Header:"상품|상품명",					Type:"Text",		SaveName:"productName",		Width: 100,		Align:"Center",		Edit:0,		Sort:0 }
			,	{Header:"상품|상품코드",				Type:"Text",		SaveName:"productNo",		Width: 100,		Align:"Center",		Edit:0,		Sort:0 }
			,	{Header:"상품|사이즈코드",				Type:"Text",		SaveName:"sizeCode",		Width: 100,		Align:"Center",		Edit:0,		Sort:0 }
			,	{Header:"상품|옵션명",					Type:"Text",		SaveName:"optionName",		Width: 100,		Align:"Center",		Edit:0,		Sort:0 }
			,	{Header:"재입고 알림 서비스 통계|대기",		Type:"Int",			SaveName:"readyRestock",	Width: 100,		Align:"Right",		Edit:0,		Sort:0, Format:"#,###" }
			,	{Header:"재입고 알림 서비스 통계|처리완료",	Type:"Int",			SaveName:"successRestock",	Width: 100,		Align:"Right",		Edit:0,		Sort:0, Format:"#,###" }
			,	{Header:"재입고 알림 서비스 통계|취소",		Type:"Int",			SaveName:"cancelRestock",	Width: 100,		Align:"Right",		Edit:0,		Sort:0, Format:"#,###" }
			,	{Header:"재입고 알림 서비스 통계|기간만료",	Type:"Int",			SaveName:"endDtmRestock",	Width: 100,		Align:"Right",		Edit:0,		Sort:0, Format:"#,###" }
			,	{Header:"재입고 알림 서비스 통계|구매건수",	Type:"Int",			SaveName:"buyCount",		Width: 100,		Align:"Right",		Edit:0,		Sort:0, Format:"#,###" }
			,	{Header:"재입고 알림 서비스 통계|결재금액",	Type:"Int",			SaveName:"sellPrice",		Width: 100,		Align:"Right",		Edit:0,		Sort:0, Format:"#,###" }

		];


		// Grid 초기화
		IBS_InitSheet(list, initSheet);
		// Grid 건수 정보 표시
		list.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		list.SetPagingPosition(2);
		// Grid width 자동 조절
		list.FitColWidth("4|7|7|7|7|7|7|7|7|7|7|7|7|7");
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		list.SetExtendLastCol(2);
//		list.SetFrozenCol(6);

		_object.sheet.list = list;
	}

	/**
	 * IBSheet 이벤트
	 */
	_object.sheet.event = function(){

		list_OnSearchEnd = function(code, msg) {
		    if (code == 0) {
		    	list.SetMergeCell(2, 0, 1, 6, 1);
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

		// 검색 버튼 이벤트
		$('#search').off().on('click', function(){
			_object.getList();
		});

		// 상품코드 구분 이벤트
		$('select[name="mmnyPrdtYn"]').off().on('change', function(e) {
			if('' == $(this).val()) { // 전체 선택한 경우
				$('select[name="chnnlNo"]').val('');
				$('select[name="chnnlNo"]').hide();
			} else {
				$('select[name="chnnlNo"]').show(); // 채널 목록 노출
				if('V' == $(this).val()) {
					$('[data-vndr-use-yn="N"]').hide();
				} else {
					$('[data-vndr-use-yn="N"]').show();
				}
			}
		});

		// 엑셀 다운로드
		$('#downExcel').off().on('click', function(){

			if(list.GetTotalRows() === 0) {
				alert("엑셀 다운로드는 조회 후에 이용 가능합니다..");
					return false;
			}
			$("#form").prop("action","/stats/restock/download-restockPdExcel").prop("method", "post").submit();
		});

		// 브랜드 버튼 이벤트
		$('#search-brand').click(function() {
			window.abc.brandSearchPopup(false, { callback : "abc.biz.stats.restock.product.callbackFindBrand", brandName : $('#brand-name').val() });
		});

		// 이벤트 적용
		$('select[name="mmnyPrdtYn"]').change();
		// 끝 기간 날짜 제한
		abc.date.setMaxDate($('.term-date-wrap'));
	}

	/**
	 * 브랜드 찾기 callback 이벤트
	 */
	_object.callbackFindBrand = function(data) {
		$("#brand-no").val(data[0].brandNo);
		$("#brand-name").val(data[0].brandName);
	}

	/**
	 * 목록 조회
	 */
	_object.getList = function(){
		if (!abc.date.searchValidate()) {
			return false;
		}

		DataSearchPaging( {
			  url : '/stats/restock/stats-product-restock-search'
			, onePageRow : $('#pageCount').val()
			, subparam : $("#form").serialize()
			, sheet : 'list'
		});
	}

	$(function() {
		_object.init();
	});

})();