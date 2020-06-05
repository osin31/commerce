(function() { // 상품 판매 현황

	var _object = abc.object.createNestedObject(window,"abc.biz.stats.promotion.promotion.product.popup");

	/**
	 * 초기화
	 */
	_object.init = function() {
		_object.event();
		_object.sheet.init();
		_object.sheet.event();

		// 목록 조회
		_object.getList();
	}

	_object.sheet = {};
	_object.sheet.init = function() {
		createIBSheet2(document.getElementById("productGrid"), "planningPdSheet", "100%", "429px");

		var initSheet = {};
		var pageCount = $('#page-count').val();
		initSheet.Cfg = {SearchMode : smServerPaging2,	Page : pageCount, MaxSort : 1, MergeSheet:msHeaderOnly, AutoFitColWidth: "init|search|resize"};
		initSheet.HeaderMode = {Sort : 1, ColMove : 0, ColResize : 1};
		initSheet.Cols = [
			 	{Header:"번호|번호", 				Type:"Text", 			SaveName:"selectSeq", 			Width: 10, 		Align:"Center", 	Edit:0, 	Sort:0}
			,	{Header:"브랜드|누적 합계",			Type:"Text",			SaveName:"brandName",			Width: 100,		Align:"Center",		Edit:0,		Sort:0 }
			,	{Header:"카테고리|누적 합계",			Type:"Text",			SaveName:"ctgrName",			Width: 100,		Align:"Center",		Edit:0,		Sort:0 }
			,	{Header:"스타일코드|누적 합계",		Type:"Text",			SaveName:"styleInfo",			Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"색상|누적 합계",			Type:"Text",			SaveName:"prdtColorInfo",		Width: 100,		Align:"Center",		Edit:0,		Sort:0 }
			,	{Header:"상품코드/상품명|누적 합계",	Type:"Text",			SaveName:"prdtNoName",			Width: 100,		Align:"Center",		Edit:0,		Sort:0 }
			,	{Header:"합계|수량",				Type:"Text",			SaveName:"orderQtyAll",			Width: 100,	 	Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"합계|정상가",				Type:"Text",			SaveName:"prdtNormalAmtAll",	Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"합계|결제금액",			Type:"Text",			SaveName:"sellAmtAll",			Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"온라인|수량",				Type:"Text",			SaveName:"orderQty10000",		Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"온라인|정상가",			Type:"Text",			SaveName:"prdtNormalAmt10000",	Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"온라인|결제금액",			Type:"Text",			SaveName:"sellAmt10000",		Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"스마트|수량",				Type:"Text",			SaveName:"orderQty10001",		Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"스마트|정상가",			Type:"Text",			SaveName:"prdtNormalAmt10001",	Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"스마트|결제금액",			Type:"Text",			SaveName:"sellAmt10001",		Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"매장|수량",				Type:"Text",			SaveName:"orderQty10002",		Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"매장|정상가",				Type:"Text",			SaveName:"prdtNormalAmt10002",	Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"매장|결제금액",			Type:"Text",			SaveName:"sellAmt10002",		Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"입점사|수량",				Type:"Text",			SaveName:"orderQty10003",		Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"입점사|정상가",			Type:"Text",			SaveName:"prdtNormalAmt10003",	Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"입점사|결제금액",			Type:"Text",			SaveName:"sellAmt10003",		Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
		];

		// Grid 초기화
		IBS_InitSheet(planningPdSheet, initSheet);
		// Grid 건수 정보 표시
		planningPdSheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		planningPdSheet.SetPagingPosition(2);
		// Grid width 자동 조절
		planningPdSheet.FitColWidth("7|7|7|7|7|7|7|7|7|7|7|7|7|7");
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		planningPdSheet.SetExtendLastCol(2);
		planningPdSheet.SetFrozenCol(8);

		_object.sheet.list = planningPdSheet;

	}

	/**
	 * IBSheet 이벤트
	 */
	_object.sheet.event = function(){

		planningPdSheet_OnSearchEnd = function(code, msg) {
		    if (code == 0) {
				planningPdSheet.SetCellValue(2, 0, "");
				planningPdSheet.SetCellValue(2, 0, "합계");
				planningPdSheet.SetMergeCell(2, 0, 1, 6, 1);
				planningPdSheet.SetFrozenRows(1);
		    }
		    // 엑셀 다운로드 버튼 제어
			if(planningPdSheet.GetTotalRows() == 0) {
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

		// 페이지별 갯수 변경 이벤트
		$("#page-count").on("change",function(){
			_object.getList();
		});

		// 엑셀 다운로드
		$('#downExcel').off().on('click', function(){
			if(planningPdSheet.GetTotalRows() === 0) {
				alert("엑셀 다운로드는 조회 후에 이용 가능합니다..");
				return false;
			}
			if($("#plnId").val() != ""){ 				// 기확전
				excelUrl = "download-planningPdExcel";
			} else { 									// 프로모션
				excelUrl = "download-promotionPdExcel";
			}
			$('#form').prop('action', '/stats/promotion/' + excelUrl).prop('method', 'post').submit();
		});

	}

	/**
	 * 목록 조회
	 */
	_object.getList = function(){

		var url, subparam;

		if($("#plnId").val() != ""){
			url = '/stats/promotion/stats-planning-search-product';
			subparam = "planningId=" + $("#plnId").val();
		} else {
			url = '/stats/promotion/stats-promotion-search-product';
			subparam = "promoNo=" + $("#proNo").val();
		}

		DataSearchPaging({
			  url : url
			, onePageRow : $('#page-count').val()
			, subparam : subparam
			, sheet : 'planningPdSheet'
		});

	}

	$(function() {
		_object.init();
	});

})();