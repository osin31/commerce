(function() { // 기획전 현황 통계

	var _object = abc.object.createNestedObject(window,"abc.biz.stats.promotion.planning");

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
		createIBSheet2(document.getElementById("planningGrid"), "list", "100%", "429px");

		var initSheet = {};
		var pageCount = $('#page-count').val();
		initSheet.Cfg = {SearchMode : smServerPaging2,	Page : pageCount, MaxSort : 1, MergeSheet:msHeaderOnly, AutoFitColWidth: "init|search|resize"};
		initSheet.HeaderMode = {Sort : 1, ColMove : 0, ColResize : 1};
		initSheet.Cols = [
				{Header:"기획전 통계|기획전ID",		Type:"Text",		SaveName:"planningId",			Width: 100,		Align:"Center",		Edit:0,		Sort:0 }
			,	{Header:"기획전 통계|기획전명",		Type:"Text",		SaveName:"planningName",		Width: 100,		Align:"Center",		Edit:0,		Sort:0 }
			,	{Header:"기획전 통계|기획전 분류",	Type:"Text",		SaveName:"planningType",		Width: 100,		Align:"Center",		Edit:0,		Sort:0 }
			,	{Header:"합계|수량",				Type:"Int",			SaveName:"orderQtyAll",			Width: 100,		Align:"Right",		Edit:0,		Sort:0,	FontUnderline:1 }
			,	{Header:"합계|정상가",			Type:"Int",			SaveName:"prdtNormalAmtAll",	Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"합계|결제금액",			Type:"Int",			SaveName:"sellAmtAll",			Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"온라인|수량",			Type:"Int",			SaveName:"orderQty10000",		Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"온라인|정상가",			Type:"Int",			SaveName:"prdtNormalAmt10000",	Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"온라인|결제금액",			Type:"Int",			SaveName:"sellAmt10000",		Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"스마트|수량",			Type:"Int",			SaveName:"orderQty10001",		Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"스마트|정상가",			Type:"Int",			SaveName:"prdtNormalAmt10001",	Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"스마트|결제금액",			Type:"Int",			SaveName:"sellAmt10001",		Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"매장|수량",				Type:"Int",			SaveName:"orderQty10002",		Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"매장|정상가",			Type:"Int",			SaveName:"prdtNormalAmt10002",	Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"매장|결제금액",			Type:"Int",			SaveName:"sellAmt10002",		Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"입점사|수량",			Type:"Int",			SaveName:"orderQty10003",		Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"입점사|정상가",			Type:"Int",			SaveName:"prdtNormalAmt10003",	Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"입점사|결제금액",			Type:"Int",			SaveName:"sellAmt10003",		Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"사이트 구분",				Type:"Text",		SaveName:"",					Width: 100,		Align:"Right",		Edit:0,		Sort:0, Hidden:1 }
			,	{Header:"디바이스 구분",			Type:"Text",		SaveName:"",					Width: 100,		Align:"Right",		Edit:0,		Sort:0, Hidden:1 }
			,	{Header:"기획전 판매기간",			Type:"Date",		SaveName:"",					Width: 100,		Align:"Right",		Edit:0,		Sort:0, Format:"Ymd" , Hidden:1 }
			,	{Header:"기획전 기간",				Type:"Date",		SaveName:"",					Width: 100,		Align:"Right",		Edit:0,		Sort:0, Format:"Ymd" , Hidden:1 }
		];

		// Grid 초기화
		IBS_InitSheet(list, initSheet);
		// Grid 건수 정보 표시
		list.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		list.SetPagingPosition(2);
		// Grid width 자동 조절
		//list.FitColWidth("7|7|7|7|7|7|7|7|7|7|7|7|7|7");
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		list.SetExtendLastCol(2);
		list.SetFrozenCol(6);
		//list.SetMergeSheet(4);
		list.SetDataRowMerge(1);

		_object.sheet.list = list;

		if(!_object.isAdmin) { // 입점사인 경우 온라인,스마트,매장 숨김
			list.ColDelete('orderQty10000');
			list.ColDelete('prdtNormalAmt10000');
			list.ColDelete('sellAmt10000');
			list.ColDelete('orderQty10001');
			list.ColDelete('prdtNormalAmt10001');
			list.ColDelete('sellAmt10001');
			list.ColDelete('orderQty10002');
			list.ColDelete('prdtNormalAmt10002');
			list.ColDelete('sellAmt10002');
			list.ColDelete('orderQty10003');
			list.ColDelete('prdtNormalAmt10003');
			list.ColDelete('sellAmt10003');
			
		}
	}

	/**
	 * IBSheet 이벤트
	 */
	_object.sheet.event = function(){

		list_OnSearchEnd = function(code, msg) {
		    if (code == 0) {
		        list.SetMergeCell(2, 0, 1, 3, 1);
		        list.SetFrozenRows(1);
		        list.SetCellFontUnderline(2, 'orderQtyAll', 0); // 합계 underline 제거
		    }
		    // 엑셀 다운로드 버튼 제어
			if(list.GetTotalRows() == 0) {
				$('#downExcel').addClass('disabled');
			} else {
				$('#downExcel').removeClass('disabled');
			}
		}

		list_OnClick = function(row, col, value, cellX, cellY, cellW, cellH, rowtype){
			if (row == 0 || row == 1 || row == 2) {
				return false;
			}
			if(list.ColSaveName(col) == 'orderQtyAll') {
				var item = list.GetRowData(row);
				abc.open.popup({
					url 	:	"/stats/promotion/planning-main/promotion-product-pop",
					winname :	"product-detail",
					method	: 	"get",
					title 	:	"product detail",
					width 	:	1480,
					height	:	970,
					params	:	{
						  planningId 	: item.planningId
						, planningName 	: item.planningName
						, sellDate 		: $('input[name="sellFromDate"]').val() + " ~ " + $('input[name="sellToDate"]').val()
						, planningDate 	: $('input[name="plndpFromDate"]').val() + " ~ " + $('input[name="plndpToDate"]').val()
						, siteType 		: $('input[name="siteNo"]:checked').next().text()
						, deviceType	: $('input[name="deviceCodeArr"]:checked').next().map(function() { return $(this).text() }).get().join(', ')
					}
				});
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

		// 검색 버튼 이벤트
		$("#search").click(function() {
			_object.getList();
		});

		// 페이지별 갯수 변경 이벤트
		$("#page-count").on("change",function(){
			_object.getList();
		});

		//엑셀 다운로드
		$('#downExcel').off().on('click', function(){
			if(list.GetTotalRows() == 0) {
				alert('엑셀 다운로드는 조회 후에 이용 가능합니다.');
				return false;
			}
			$('#form').prop('action', '/stats/promotion/download-planningExcel').prop('method', 'post').submit();
		});

		// 기간 제어
		$(".term-date-wrap .btn-group .btn-func").click(function(e) {
			var type = $(this).attr("name");
			switch(type) {
			case "perToday" :
				abc.date.days(this);
				break;
			case "perWeek" :
				abc.date.week(this);
				break;
			case "per1Month" :
				abc.date.month(this);
				break;
			case "perYear" :
				abc.date.year(this);
				break;
			default :
				console.log("지원하지 않는 유형입니다. " + type);
			}
		});

		// 기본 날짜 세팅
		$('a[name="perWeek"]').click();
		// 끝 기간 날짜 제한
		abc.date.setMaxDate($('.term-date-wrap'));

		// 체크박스 제어
		abc.biz.display.common.checkBoxAll({allId: '#device-code-all', itemsClass: '.device-code'});
	}

	/**
	 * 목록 조회
	 */
	_object.getList = function(){
		if($('[name="plndpFromDate"]').val() == '' && $('[name="plndpToDate"]').val() == '' && $("#searchKeyword").val() == '' ) {
			alert('기획전 기간, 기획전 검색 조건 중 하나 이상의 조건은 입력되어야 검색이 가능합니다.');
			return;
		} else if (!abc.date.searchValidate()) {
			return false;
		}

		DataSearchPaging( {
			  url : '/stats/promotion/stats-planning-search'
			, onePageRow : $('#page-count').val()
			, subparam : $("#form").serialize()
			, sheet : 'list'
		});

	}

	$(function() {
		_object.init();
	});

})();