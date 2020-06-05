/***
 * Order 당일매출 현환 업무 관련된 공통 함수 정의.
 *
 */
(function() {

	var _orderRealTime = abc.object.createNestedObject(window,"abc.biz.stats.order.realtime");

	abc.biz.stats.order.common.init();
	
	_orderRealTime.sheetType = {};
	_orderRealTime.periodTime = {};
	_orderRealTime.poChk = $('select[name=mmnyPrdtYn]');
	//당일 매출 현황 sheet.
	_orderRealTime.initSaleStatsSheet = function() {
		var initSheet = {};
		var pageCount = ! abc.text.allNull($("#pageCount")) ? $('#pageCount').val() : 10000;

		initSheet.Cfg = {SearchMode:smServerPaging2, MergeSheet:msHeaderOnly, Page:pageCount, MaxSort:1, FrozenCol:4};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		if(_orderRealTime.poChk.children().length >1) {
			initSheet.Cols = [
					{Header:"시간대별통계|시간대별통계", 	Type:"Text",		SaveName:"hourTitle",			Width: 150,		Align:"Center",		Edit:0,		Sort:0, FontBold:1,	FontUnderline:1, Cursor:"Pointer" }
				,	{Header:"합계|수량",					Type:"Int",			SaveName:"orderQtyAll",			Width: 100,		Align:"Right",		Edit:0,		Sort:0	}
				,	{Header:"합계|정상가",					Type:"Int",			SaveName:"prdtNormalAmtAll",	Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
				,	{Header:"합계|결제금액",				Type:"Int",			SaveName:"orderAmtAll",			Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
				,	{Header:"온라인|수량",					Type:"Int",			SaveName:"orderQty10000",		Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
				,	{Header:"온라인|정상가",				Type:"Int",			SaveName:"prdtNormalAmt10000",	Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
				,	{Header:"온라인|결제금액",				Type:"Int",			SaveName:"orderAmt10000",		Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
				,	{Header:"스마트|수량",					Type:"Int",			SaveName:"orderQty10001",		Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
				,	{Header:"스마트|정상가",				Type:"Int",			SaveName:"prdtNormalAmt10001",	Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
				,	{Header:"스마트|결제금액",				Type:"Int",			SaveName:"orderAmt10001",		Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
				,	{Header:"매장|수량",					Type:"Int",			SaveName:"orderQty10002",		Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
				,	{Header:"매장|정상가",					Type:"Int",			SaveName:"prdtNormalAmt10002",	Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
				,	{Header:"매장|결제금액",				Type:"Int",			SaveName:"orderAmt10002",		Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
				,	{Header:"입점사|수량",					Type:"Int",			SaveName:"orderQty10003",		Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
				,	{Header:"입점사|정상가",				Type:"Int",			SaveName:"prdtNormalAmt10003",	Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
				,	{Header:"입점사|결제금액",				Type:"Int",			SaveName:"orderAmt10003",		Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
			];
		} else {
			initSheet.Cols = [
				{Header:"시간대별통계|시간대별통계", 	Type:"Text",		SaveName:"hourTitle",			Width: 50,		Align:"Center",		Edit:0,		Sort:0, FontBold:1,	FontUnderline:1, Cursor:"Pointer" }
			,	{Header:"합계|수량",					Type:"Int",			SaveName:"orderQtyAll",			Width: 50,		Align:"Right",		Edit:0,		Sort:0	}
			,	{Header:"합계|정상가",					Type:"Int",			SaveName:"prdtNormalAmtAll",	Width: 50,		Align:"Right",		Edit:0,		Sort:0	}
			,	{Header:"합계|결제금액",				Type:"Int",			SaveName:"orderAmtAll",			Width: 50,		Align:"Right",		Edit:0,		Sort:0	}
			,	{Header:"수량|수량",					Type:"Int",			SaveName:"orderQty10003",		Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
			,	{Header:"정상가|정상가",				Type:"Int",			SaveName:"prdtNormalAmt10003",	Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
			,	{Header:"결제금액|결제금액",			Type:"Int",			SaveName:"orderAmt10003",		Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
		];
		}

		createIBSheet2(document.getElementById("currentSaleStatsGrid"), "saleStatsSheet", "100%", "429px");
		IBS_InitSheet(saleStatsSheet, initSheet);
		// Grid 건수 정보 표시
		//currentSaleStatsSheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		//currentSaleStatsSheet.SetPagingPosition(2);
		// Grid width 자동 조절
		saleStatsSheet.FitColWidth();
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		saleStatsSheet.SetExtendLastCol(1);
		
		_orderRealTime.sheetType = saleStatsSheet;
		
		saleStatsSheet_OnSearchEnd = function(Code, Msg, StCode, StMsg, Response){
			
			_orderRealTime.excelBtnEnabled(saleStatsSheet.RowCount());
		};

	}
	
	//당일 매출현황 상세 팝업 sheet
	_orderRealTime.initSaleDetailStatsSheet = function() {
		var initSheet = {};
		var pageCount = ! abc.text.allNull($("#pageCount")) ? $('#pageCount').val() : 10000;

		initSheet.Cfg = {SearchMode:smServerPaging2, MergeSheet:msHeaderOnly, Page:pageCount, MaxSort:1, FrozenCol:7};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initSheet.Cols = [
				{Header:"번호|번호", 						Type:"Text",		SaveName:"brandNo",				Width: 80,		Align:"Center",		Edit:0,		Sort:0	}
			,	{Header:"브랜드|브랜드",					Type:"Text",		SaveName:"brandName",			Width: 80,		Align:"Center",		Edit:0,		Sort:0	}
			,	{Header:"카테고리|카테고리",				Type:"Text",		SaveName:"stdCtgrName",			Width: 80,		Align:"Center",		Edit:0,		Sort:0	}
			,	{Header:"스타일코드|스타일코드",			Type:"Text",		SaveName:"styleInfo",			Width: 80,		Align:"Left",		Edit:0,		Sort:0	}
			,	{Header:"색상|색상",						Type:"Text",		SaveName:"prdtColorName",		Width: 80,		Align:"Center",		Edit:0,		Sort:0	}
//			,	{Header:"상품코드/상품명|상품코드/상품명",	Type:"Text",		SaveName:"prdtNoName",			Width: 300,		Align:"Left",		Edit:0,		Sort:0	}
				,	{Header:"상품코드|상품코드",		Type:"Text",		SaveName:"prdtNo",				Width: 150,		Align:"Center",		Edit:0,		Sort:0 }
				,	{Header:"상품명|상품명",				Type:"Text",		SaveName:"prdtName",				Width: 200,		Align:"Left",		Edit:0,		Sort:0 }

			,	{Header:"합계|수량",						Type:"Int",			SaveName:"orderQtyAll",			Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
			,	{Header:"합계|정상가",						Type:"Int",			SaveName:"prdtNormalAmtAll",	Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
			,	{Header:"합계|결제금액",					Type:"Int",			SaveName:"orderAmtAll",			Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
			,	{Header:"온라인|수량",						Type:"Int",			SaveName:"orderQty10000",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 	}
			,	{Header:"온라인|정상가",					Type:"Int",			SaveName:"prdtNormalAmt10000",	Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
			,	{Header:"온라인|결제금액",					Type:"Int",			SaveName:"orderAmt10000",		Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
			,	{Header:"스마트|수량",						Type:"Int",			SaveName:"orderQty10001",		Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
			,	{Header:"스마트|정상가",					Type:"Int",			SaveName:"prdtNormalAmt10001",	Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
			,	{Header:"스마트|결제금액",					Type:"Int",			SaveName:"orderAmt10001",		Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
			,	{Header:"매장|수량",						Type:"Int",			SaveName:"orderQty10002",		Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
			,	{Header:"매장|정상가",						Type:"Int",			SaveName:"prdtNormalAmt10002",	Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
			,	{Header:"매장|결제금액",					Type:"Int",			SaveName:"orderAmt10002",		Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
			,	{Header:"입점사|수량",						Type:"Int",			SaveName:"orderQty10003",		Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
			,	{Header:"입점사|정상가",					Type:"Int",			SaveName:"prdtNormalAmt10003",	Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
			,	{Header:"입점사|결제금액",					Type:"Int",			SaveName:"orderAmt10003",		Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
		];

		createIBSheet2(document.getElementById("currentSaleStatsDetailGrid"), "saleDetailStatsSheet", "100%", "429px");
		IBS_InitSheet(saleDetailStatsSheet, initSheet);
		// Grid 건수 정보 표시
		//saleDetailStatsSheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		//saleDetailStatsSheet.SetPagingPosition(2);
		// Grid width 자동 조절
		//currentSaleStatsDetailSheet.FitColWidth();
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		//currentSaleStatsDetailSheet.SetExtendLastCol(1);
		
		_orderRealTime.sheetType = saleDetailStatsSheet;
		
		if(abc.param.getParams().statsType == 'timeType') {
			abc.biz.stats.sales.detailPopSrchOpt.init();
		}
		if(abc.param.getParams().statsType == 'currentSale') {
			_orderRealTime.detailPopSrchOpt.init();
		}
		
		saleDetailStatsSheet_OnSearchEnd = function(Code, Msg, StCode, StMsg, Response){
			
			_orderRealTime.excelBtnEnabled(saleDetailStatsSheet.RowCount());
		};
		
	}
	
	//당일 디바이스별 매출 현황 sheet.
	_orderRealTime.initDeviceStatsSheet = function() {
		var initSheet = {};
		var pageCount = ! abc.text.allNull($("#pageCount")) ? $('#pageCount').val() : 10000;

		initSheet.Cfg = {SearchMode:smServerPaging2, MergeSheet:msHeaderOnly, Page:pageCount, MaxSort:1, FrozenCol:4};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initSheet.Cols = [
				{Header:"시간대별통계|시간대별통계", 	Type:"Text",		SaveName:"hourTitle",			Width: 100,		Align:"Center",		Edit:0,		Sort:0	}
			,	{Header:"합계|수량",					Type:"Int",			SaveName:"orderQtyAll",			Width: 100,		Align:"Right",		Edit:0,		Sort:0	}
			,	{Header:"합계|정상가",					Type:"Int",			SaveName:"prdtNormalAmtAll",	Width: 100,		Align:"Right",		Edit:0,		Sort:0	}
			,	{Header:"합계|결제금액",				Type:"Int",			SaveName:"orderAmtAll",			Width: 100,		Align:"Right",		Edit:0,		Sort:0	}
			,	{Header:"PC|수량",						Type:"Int",			SaveName:"orderQty10000",		Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
			,	{Header:"PC|정상가",					Type:"Int",			SaveName:"prdtNormalAmt10000",	Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
			,	{Header:"PC|결제금액",					Type:"Int",			SaveName:"orderAmt10000",		Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
			,	{Header:"MOBILE|수량",					Type:"Int",			SaveName:"orderQty10001",		Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
			,	{Header:"MOBILE|정상가",				Type:"Int",			SaveName:"prdtNormalAmt10001",	Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
			,	{Header:"MOBILE|결제금액",				Type:"Int",			SaveName:"orderAmt10001",		Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
			,	{Header:"APP|수량",						Type:"Int",			SaveName:"orderQty10002",		Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
			,	{Header:"APP|정상가",					Type:"Int",			SaveName:"prdtNormalAmt10002",	Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
			,	{Header:"APP|결제금액",					Type:"Int",			SaveName:"orderAmt10002",		Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
			
		];

		createIBSheet2(document.getElementById("currentDeviceStatsGrid"), "deviceStatsSheet", "100%", "429px");
		IBS_InitSheet(deviceStatsSheet, initSheet);
		// Grid 건수 정보 표시
		//currentSaleStatsSheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		//currentSaleStatsSheet.SetPagingPosition(2);
		// Grid width 자동 조절
		//currentSaleStatsSheet.FitColWidth();
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		//currentSaleStatsSheet.SetExtendLastCol(1);
		
		_orderRealTime.sheetType = deviceStatsSheet;
		
		deviceStatsSheet_OnSearchEnd = function(Code, Msg, StCode, StMsg, Response){
			
			_orderRealTime.excelBtnEnabled(deviceStatsSheet.RowCount());
		};

	}
	
	// 결제수단별 현황 sheet
	_orderRealTime.initPaymentStatsSheet = function() {
		var initSheet = {};
		var pageCount = ! abc.text.allNull($("#pageCount")) ? $('#pageCount').val() : 10000;

		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MaxSort:1,  MergeSheet: msHeaderOnly};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initSheet.Cols = [
				{Header:"시간별통계|시간별통계", 				Type:"Text",		SaveName:"hourTitle",			Width: 100,		Align:"Center",		Edit:0,		Sort:0	}
			,	{Header:"합계|건수",							Type:"Int",			SaveName:"orderQtyAll",			Width: 100,		Align:"Right",		Edit:0,		Sort:0	}
			,	{Header:"합계|결제금액",						Type:"Int",			SaveName:"orderAmtAll",			Width: 100,		Align:"Right",		Edit:0,		Sort:0	}
			,	{Header:"신용카드|건수",						Type:"Int",			SaveName:"orderQty10000",		Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
			,	{Header:"신용카드|결제금액",					Type:"Int",			SaveName:"orderAmt10000",		Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
			,	{Header:"무통장입금|건수",						Type:"Int",			SaveName:"orderQty10001",		Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
			,	{Header:"무통장입금|결제금액",					Type:"Int",			SaveName:"orderAmt10001",		Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
			,	{Header:"실시간계좌이체|건수",					Type:"Int",			SaveName:"orderQty10002",		Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
			,	{Header:"실시간계좌이체|결제금액",				Type:"Int",			SaveName:"orderAmt10002",		Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
			,	{Header:"휴대폰결제|건수",						Type:"Int",			SaveName:"orderQty10003",		Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
			,	{Header:"휴대폰결제|결제금액",					Type:"Int",			SaveName:"orderAmt10003",		Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
			,	{Header:"네이버페이|건수",						Type:"Int",			SaveName:"orderQty10004",		Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
			,	{Header:"네이버페이|결제금액",					Type:"Int",			SaveName:"orderAmt10004",		Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
			,	{Header:"카카오페이|건수",						Type:"Int",			SaveName:"orderQty10005",		Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
			,	{Header:"카카오페이|결제금액",					Type:"Int",			SaveName:"orderAmt10005",		Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
			,	{Header:"기프트카드|건수",						Type:"Int",			SaveName:"orderQty10006",		Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
			,	{Header:"기프트카드|결제금액",					Type:"Int",			SaveName:"orderAmt10006",		Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
			,	{Header:"포인트|건수",							Type:"Int",			SaveName:"orderQty99999",		Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
			,	{Header:"포인트|결제금액",						Type:"Int",			SaveName:"orderAmt99999",		Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
			
		];

		createIBSheet2(document.getElementById("currentPaymentStatsGrid"), "paymentStatsSheet", "100%", "429px");
		IBS_InitSheet(paymentStatsSheet, initSheet);
		
		 //Grid 건수 정보 표시
		//paymentStatsSheet.SetCountPosition(3);
		 //Grid 페이지 네비게이션 버튼 표시
		//paymentStatsSheet.SetPagingPosition(2);
		 //Grid width 자동 조절
		//paymentStatsSheet.FitColWidth();
		 //Grid 마지막 컬럼을 All 너비에 맞춘다.
		paymentStatsSheet.SetExtendLastCol(1);
		//좌측행 고정.
		paymentStatsSheet.SetFrozenCol(3);
		
		_orderRealTime.sheetType = paymentStatsSheet;
		
		paymentStatsSheet_OnSearchEnd = function(Code, Msg, StCode, StMsg, Response){
			
			_orderRealTime.excelBtnEnabled(paymentStatsSheet.RowCount());
		};
		
	};
	
	//당일 상품판매 현황
	_orderRealTime.initPrdtStatsSheet = function() {
		var initSheet = {};
		var pageCount = ! abc.text.allNull($("#pageCount")) ? $('#pageCount').val() : 10000;

		initSheet.Cfg = {SearchMode:smServerPaging2, MergeSheet:msHeaderOnly, Page:pageCount, MaxSort:1, FrozenCol:7};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		if(_orderRealTime.poChk.children().length >1) {
			initSheet.Cols = [
					{Header:"번호|번호", 						Type:"Text",		SaveName:"brandNo",				Width: 80,		Align:"Center",		Edit:0,		Sort:0	}
				,	{Header:"브랜드|브랜드",					Type:"Text",		SaveName:"brandName",			Width: 80,		Align:"Left",		Edit:0,		Sort:0	}
				,	{Header:"카테고리|카테고리",				Type:"Text",		SaveName:"stdCtgrName",			Width: 80,		Align:"Left",		Edit:0,		Sort:0	}
				,	{Header:"스타일코드|스타일코드",			Type:"Text",		SaveName:"styleInfo",			Width: 80,		Align:"Left",		Edit:0,		Sort:0	}
				,	{Header:"색상|색상",						Type:"Text",		SaveName:"prdtColorName",		Width: 80,		Align:"Left",		Edit:0,		Sort:0	}
//				,	{Header:"상품코드/상품명|상품코드/상품명",	Type:"Text",		SaveName:"prdtNoName",			Width: 300,		Align:"Left",		Edit:0,		Sort:0	}
				,	{Header:"상품코드|상품코드",		Type:"Text",		SaveName:"prdtNo",				Width: 150,		Align:"Center",		Edit:0,		Sort:0 }
				,	{Header:"상품명|상품명",				Type:"Text",		SaveName:"prdtName",				Width: 200,		Align:"Left",		Edit:0,		Sort:0 }

				,	{Header:"합계|수량",						Type:"Int",			SaveName:"orderQtyAll",			Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
				,	{Header:"합계|정상가",						Type:"Int",			SaveName:"prdtNormalAmtAll",	Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
				,	{Header:"합계|결제금액",					Type:"Int",			SaveName:"orderAmtAll",			Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
				,	{Header:"온라인|수량",						Type:"Int",			SaveName:"orderQty10000",		Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
				,	{Header:"온라인|정상가",					Type:"Int",			SaveName:"prdtNormalAmt10000",	Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
				,	{Header:"온라인|결제금액",					Type:"Int",			SaveName:"orderAmt10000",		Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
				,	{Header:"스마트|수량",						Type:"Int",			SaveName:"orderQty10001",		Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
				,	{Header:"스마트|정상가",					Type:"Int",			SaveName:"prdtNormalAmt10001",	Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
				,	{Header:"스마트|결제금액",					Type:"Int",			SaveName:"orderAmt10001",		Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
				,	{Header:"매장|수량",						Type:"Int",			SaveName:"orderQty10002",		Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
				,	{Header:"매장|정상가",						Type:"Int",			SaveName:"prdtNormalAmt10002",	Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
				,	{Header:"매장|결제금액",					Type:"Int",			SaveName:"orderAmt10002",		Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
				,	{Header:"입점사|수량",						Type:"Int",			SaveName:"orderQty10003",		Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
				,	{Header:"입점사|정상가",					Type:"Int",			SaveName:"prdtNormalAmt10003",	Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
				,	{Header:"입점사|결제금액",					Type:"Int",			SaveName:"orderAmt10003",		Width: 150,		Align:"Right",		Edit:0,		Sort:0	}
			];
		} else {
			initSheet.Cols = [
				{Header:"번호", 						Type:"Text",		SaveName:"brandNo",				Width: 80,		Align:"Center",		Edit:0,		Sort:0	}
			,	{Header:"브랜드",						Type:"Text",		SaveName:"brandName",			Width: 80,		Align:"Center",		Edit:0,		Sort:0	}
			,	{Header:"카테고리",						Type:"Text",		SaveName:"stdCtgrName",			Width: 80,		Align:"Center",		Edit:0,		Sort:0	}
			,	{Header:"스타일코드",					Type:"Text",		SaveName:"styleInfo",			Width: 80,		Align:"Left",		Edit:0,		Sort:0	}
			,	{Header:"색상",							Type:"Text",		SaveName:"prdtColorName",		Width: 80,		Align:"Center",		Edit:0,		Sort:0	}
//			,	{Header:"상품코드/상품명",				Type:"Text",		SaveName:"prdtNoName",			Width: 300,		Align:"Left",		Edit:0,		Sort:0	}
				,	{Header:"상품코드|상품코드",		Type:"Text",		SaveName:"prdtNo",				Width: 150,		Align:"Center",		Edit:0,		Sort:0 }
				,	{Header:"상품명|상품명",				Type:"Text",		SaveName:"prdtName",				Width: 200,		Align:"Left",		Edit:0,		Sort:0 }

			,	{Header:"수량",							Type:"Int",			SaveName:"orderQty10003",		Width: 200,		Align:"Right",		Edit:0,		Sort:0	}
			,	{Header:"정상가",						Type:"Int",			SaveName:"prdtNormalAmt10003",	Width: 200,		Align:"Right",		Edit:0,		Sort:0	}
			,	{Header:"결제금액",						Type:"Int",			SaveName:"orderAmt10003",		Width: 200,		Align:"Right",		Edit:0,		Sort:0	}
		];
		}

		createIBSheet2(document.getElementById("currentPrdtSaleStatsGrid"), "prdtStatsSheet", "100%", "429px");
		IBS_InitSheet(prdtStatsSheet, initSheet);
		// Grid 건수 정보 표시
		//prdtStatsSheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		//prdtStatsSheet.SetPagingPosition(2);
		// Grid width 자동 조절
		//prdtStatsSheet.FitColWidth();
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		prdtStatsSheet.SetExtendLastCol(1);
		
		_orderRealTime.sheetType = prdtStatsSheet;
		
		prdtStatsSheet_OnSearchEnd = function(Code, Msg, StCode, StMsg, Response){
			
			_orderRealTime.excelBtnEnabled(prdtStatsSheet.RowCount());
		};
		
	}
	
	//당일 교환/반품 처리현황, 반품 sheet
	_orderRealTime.initClaimReturnStatsSheet = function() {
		var initSheet = {};
		var pageCount = ! abc.text.allNull($("#pageCount")) ? $('#pageCount').val() : 10000;

		initSheet.Cfg = {SearchMode:smServerPaging2, MergeSheet:msHeaderOnly, Page:pageCount, MaxSort:1, FrozenCol:5};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initSheet.Cols = [
				{Header:"업체별 통계|업체별 통계", 				Type:"Text",		SaveName:"vndrGbn",				Width: 150,		Align:"Center",		Edit:0,		Sort:1	}
			,	{Header:"합계|합계",						Type:"Int",			SaveName:"returnTotalCnt",		Width: 100,		Align:"Right",		Edit:0,		Sort:1,	Format:"#,###"}
			,	{Header:"반품 상태 구분|반품불가",				Type:"Int",			SaveName:"returnImpsblCnt",		Width: 100,		Align:"Right",		Edit:0,		Sort:1,	Format:"#,###"}
			,	{Header:"반품 상태 구분|반품완료",				Type:"Int",			SaveName:"returnFinishCnt",		Width: 100,		Align:"Right",		Edit:0,		Sort:1,	Format:"#,###"}
			,	{Header:"반품 상태 구분|처리중",				Type:"Int",			SaveName:"returnProcCnt",		Width: 100,		Align:"Right",		Edit:0,		Sort:1,	Format:"#,###"}
			,	{Header:"반품 유형 구분|불량",				Type:"Int",			SaveName:"returnRsn01",			Width: 100,		Align:"Right",		Edit:0,		Sort:0,	Format:"#,###"}
			,	{Header:"반품 유형 구분|오배송",				Type:"Int",			SaveName:"returnRsn02",			Width: 100,		Align:"Right",		Edit:0,		Sort:0,	Format:"#,###"}
			,	{Header:"반품 유형 구분|변심(소재)",			Type:"Int",			SaveName:"returnRsn03",			Width: 100,		Align:"Right",		Edit:0,		Sort:0,	Format:"#,###"}
			,	{Header:"반품 유형 구분|변심\n(사이즈 미스)",		Type:"Int",			SaveName:"returnRsn04",			Width: 100,		Align:"Right",		Edit:0,		Sort:0,	Format:"#,###"}
			,	{Header:"반품 유형 구분|변심\n(화면과 실물 상이)",	Type:"Int",			SaveName:"returnRsn05",			Width: 100,		Align:"Right",		Edit:0,		Sort:0,	Format:"#,###"}
			,	{Header:"반품 유형 구분|미출 반품",				Type:"Int",			SaveName:"returnRsn06",			Width: 100,		Align:"Right",		Edit:0,		Sort:0,	Format:"#,###"}
			,	{Header:"반품 유형 구분|오프라인 반품",			Type:"Int",			SaveName:"returnRsn07",			Width: 100,		Align:"Right",		Edit:0,		Sort:0,	Format:"#,###"}
			,	{Header:"반품 유형 구분|브랜드 박스 훼손",			Type:"Int",			SaveName:"returnRsn08",			Width: 100,		Align:"Right",		Edit:0,		Sort:0,	Format:"#,###"}
			,	{Header:"반품 유형 구분|기타",				Type:"Int",			SaveName:"returnRsn09",			Width: 100,		Align:"Right",		Edit:0,		Sort:0,	Format:"#,###"}
		];

		createIBSheet2(document.getElementById("currentClaimReturnSaleStatsGrid"), "returnStatsSheet", "100%", "429px");
		IBS_InitSheet(returnStatsSheet, initSheet);
		// Grid 건수 정보 표시
		returnStatsSheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		returnStatsSheet.SetPagingPosition(2);
		// Grid width 자동 조절
		returnStatsSheet.FitColWidth();
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		//currentSaleStatsDetailSheet.SetExtendLastCol(1);
		
		_orderRealTime.sheetType = returnStatsSheet;
		//console.log(_orderRealTime.sheetType);
		returnStatsSheet_OnSearchEnd = function(Code, Msg, StCode, StMsg, Response){
			
			_orderRealTime.excelBtnEnabled(returnStatsSheet.RowCount());
			//returnStatsSheet.SetFrozenRows(1);
		};
		
	}
	
	//당일 교환/반품 처리현황, 교환 sheet
	_orderRealTime.initClaimChangeStatsSheet = function() {
		var initSheet = {};
		var pageCount = ! abc.text.allNull($("#pageCount")) ? $('#pageCount').val() : 10000;

		initSheet.Cfg = {SearchMode:smServerPaging2, MergeSheet:msHeaderOnly, Page:pageCount, MaxSort:1, FrozenCol:5};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initSheet.Cols = [
				{Header:"업체별 통계|업체별 통계", 			Type:"Text",		SaveName:"vndrGbn",					Width: 150,		Align:"Center",		Edit:0,		Sort:1	}
			,	{Header:"합계|합계",					Type:"Int",			SaveName:"exchangeTotalCnt",		Width: 100,		Align:"Right",		Edit:0,		Sort:1,	Format:"#,###"	}
			,	{Header:"교환 상태 구분|교환불가",			Type:"Int",			SaveName:"exchangeImpsblCnt",		Width: 100,		Align:"Right",		Edit:0,		Sort:1,	Format:"#,###"	}
			,	{Header:"교환 상태 구분|교환완료",			Type:"Int",			SaveName:"exchangeFinishCnt",		Width: 100,		Align:"Right",		Edit:0,		Sort:1,	Format:"#,###"	}
			,	{Header:"교환 상태 구분|처리중",			Type:"Int",			SaveName:"exchangeProcCnt",			Width: 100,		Align:"Right",		Edit:0,		Sort:1,	Format:"#,###"	}
			,	{Header:"교환 유형 구분|불량",			Type:"Int",			SaveName:"exchangeRsn01",			Width: 100,		Align:"Right",		Edit:0,		Sort:0,	Format:"#,###"	}
			,	{Header:"교환 유형 구분|오배송",			Type:"Int",			SaveName:"exchangeRsn02",			Width: 100,		Align:"Right",		Edit:0,		Sort:0,	Format:"#,###"	}
			,	{Header:"교환 유형 구분|옵션 변경",			Type:"Int",			SaveName:"exchangeRsn03",			Width: 100,		Align:"Right",		Edit:0,		Sort:0,	Format:"#,###"	}
			,	{Header:"교환 유형 구분|브랜드 박스 훼손",		Type:"Int",			SaveName:"exchangeRsn04",			Width: 100,		Align:"Right",		Edit:0,		Sort:0,	Format:"#,###"	}
			,	{Header:"교환 유형 구분|기타",			Type:"Int",			SaveName:"exchangeRsn05",			Width: 100,		Align:"Right",		Edit:0,		Sort:0,	Format:"#,###"	}
		];

		createIBSheet2(document.getElementById("currentClaimChangeSaleStatsGrid"), "changeStatsSheet", "100%", "429px");
		IBS_InitSheet(changeStatsSheet, initSheet);
		// Grid 건수 정보 표시
		changeStatsSheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		changeStatsSheet.SetPagingPosition(2);
		// Grid width 자동 조절
		changeStatsSheet.FitColWidth();
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		//currentSaleStatsDetailSheet.SetExtendLastCol(1);
		
		_orderRealTime.sheetType = changeStatsSheet;
		
		changeStatsSheet_OnSearchEnd = function(Code, Msg, StCode, StMsg, Response){
			
			_orderRealTime.excelBtnEnabled(changeStatsSheet.RowCount());
			//changeStatsSheet.SetFrozenRows(1);
		};
		
	}

	_orderRealTime.currentSaleDoAction = function(sAction) {

		switch(sAction) {
		// 조회
		case "search" :
			
			var sheetName = '';
			
			if($('input[name=claimType]').val() == null || $('input[name=claimType]').val() == undefined
					|| $('input[name=claimType]').val() == '') {
						sheetName = $('input[name=typeStats]').val();
			} else {
				sheetName = $('input[name=claimType]:checked').val();
			}
			
			// 상품코드 자사 선택시 입점사 제외
			if($('#mmnyPrdtYn').val() == 'Y') {
				if($('input[name=stockGbnCodeAll]').is(':checked')) {
					$('#chkStore10003').prop('checked', false);
				}
			}
			// 상품코드 입점 선택시 입점사 외 모두 제외
			if($('#mmnyPrdtYn').val() == 'N') {
				if($('input[name=stockGbnCodeAll]').is(':checked')) {
					$('#chkStore10000, #chkStore10001, #chkStore10002').prop('checked', false);
				}
			}
			
			// 반품/교환 통계일 경우 페이징처리가 필요해서 분기.
			if(sheetName == "change" || sheetName == "return"){
				var pageCount=$('#pageCount').val();
				var param = { url : "/stats/order/realtime/current-sales-stats/read-list"
					, onePageRow : pageCount
					, subparam : FormQueryStringEnc(document.form)
					, sheet : sheetName+'StatsSheet' };
				
				if(_orderRealTime.periodValidate(sheetName)) {
					DataSearchPaging(param);
				}
			} else{
				var param = { url : "/stats/order/realtime/current-sales-stats/read-list"
					, subparam : FormQueryStringEnc(document.form)
					, sheet : sheetName+'StatsSheet' };
				
				// 당일매출이 아닌 매출통계-> 시간별매출통계 페이지에서 넘어왔을경우 매출통계 검색 url로 변경.
				if($('input[name=isTimeType]').val() != '' && $('input[name=isTimeType]').val() == 'timeType') {
					param.url = '/stats/sales/stats-sales-search';
				}
				if(_orderRealTime.periodValidate(sheetName)) {
					DataSearch(param);
				}
			}
			
			break;
			
		case 'excel' :
			var typeStats = $('input[name=typeStats]').val();
			
			if(_orderRealTime.sheetType.GetTotalRows() < 1) {
				alert('엑셀 다운로드는 조회 후에 이용 가능합니다.');
				
				return false;
			}
			var setFileName = '';
			var downDate = new Date();
			if(typeStats == 'saleDetail') {
				setFileName = $('h1').text();
			} else {
				setFileName = $('h2.page-title').text();
			}
			var param = {
					  FileName : downDate.getFullYear() + '_' + (downDate.getMonth()+1) + '_' + downDate.getDate() + '_' + setFileName
					, Merge : 1
					, TitleAlign : 'center'
			};
			_orderRealTime.sheetType.Down2Excel(param);

			break;
		}
	}
	
	// common 매출현황 검색조건 control
	_orderRealTime.srchOptionControll = function() {
		
		if($('input[name=claimType]').is(':checked')) {
			if($('input[name=claimType]:checked').val() == 'return') {
				$('#currentClaimChangeSaleStatsGrid').hide();
				$('#currentClaimReturnSaleStatsGrid').show();
			} else if($('input[name=claimType]:checked').val() == 'change') {
				$('#currentClaimChangeSaleStatsGrid').show();
				$('#currentClaimReturnSaleStatsGrid').hide();
			}
		}
		
		// 반품/교환 처리현황 요청 구분 버튼시 sheet 변경
		$('input[name=claimType]').off().on('change', function(){
			
			if($(this).val() == 'return') {
				$('#currentClaimChangeSaleStatsGrid').hide();
				$('#currentClaimReturnSaleStatsGrid').show();
				_orderRealTime.sheetType = returnStatsSheet;
				
				if(_orderRealTime.sheetType.RowCount() <= 1) {
					$('#downExcel').addClass('disabled');
				} else {
					$('#downExcel').removeClass('disabled');
				}
			} else if($(this).val() == 'change') {
				$('#currentClaimChangeSaleStatsGrid').show();
				$('#currentClaimReturnSaleStatsGrid').hide();
				_orderRealTime.sheetType = changeStatsSheet;

				if(_orderRealTime.sheetType.RowCount() <= 1) {
					$('#downExcel').addClass('disabled');
				} else {
					$('#downExcel').removeClass('disabled');
				}
			}
		});
		
		//주문목록 조회.
		$("#searchBtn").off().on('click', function(){
			_orderRealTime.currentSaleDoAction('search');
		});
		
		//엑셀 다운
		$('#downExcel').off().on('click', function(){
			_orderRealTime.currentSaleDoAction('excel');
		});
		
		//검색조건 초기화 버튼
		$('#resetBtn').off().on('click', function(){
			$('#ourChannel').addClass('hidden').val('');
			
			//po 로그인시 입점업체 사이트 select 박스 노출.
			if($('select[name=mmnyPrdtYn]').children().length >1) {
				$('#shoperSiteNo').addClass('hidden').val('');
			}
			$('input[namestockGbnCodeArr]').prop('checked', true).parent().parent().removeClass('hidden');
			
			$('#form')[0].reset();
		})
	};
	
	//당일 매출현황 검색조건 save
	_orderRealTime.currentSrchVal = {
			
			dataSet : {
				siteNo : '',
				mmnyPrdtYn : '',
//				ourChannel : '',
//				shoperStieNo : '',
				deviceCode : '',
//				pymntMeansCode : '',
				fromTime : '',
				toTime : ''
			},
			dataBind : function() {
				this.dataSet.siteNo = $('input[name=siteNo]:checked').val();
				this.dataSet.mmnyPrdtYn = $('#mmnyPrdtYn').val();
				
//				if(this.dataSet.mmnyPrdtYn == 'Y') {
//					this.dataSet.ourChannel = $('#ourChannel').val();
//					this.dataSet.shoperSiteNo = '';
//				} else {
//					this.dataSet.ourChannel = '';
//					this.dataSet.shoperSiteNo = $('#shoperSiteNo').val();
//				}
				
				this.dataSet.deviceCode = [];
				$('input[name=deviceCodes]').each(function(){
					
					if($(this).is(':checked')){
						_orderRealTime.currentSrchVal.dataSet.deviceCode.push($(this).val());
					}
				});
				
//				this.dataSet.pymntMeansCode = [];
//				$('input[name=pymntMeansCode]').each(function(){
//					if($(this).is(':checked')) {
//						_orderRealTime.currentSrchVal.dataSet.pymntMeansCode.push($(this).val());
//					}
//				});
				
				this.dataSet.fromTime = _orderRealTime.periodTime.substring(0, 2);
				this.dataSet.toTime = _orderRealTime.periodTime.substring(3, 5);
			}
	};
	
	//상품매출현황 팝업 검색조건 draw
	_orderRealTime.detailPopSrchOpt = {
			init : function() {
				this.getSrchVal = opener.abc.biz.stats.order.realtime.currentSrchVal.dataSet;
				this.drawData();
			},
			getSrchVal : {},
			drawData : function() {
				if(this.getSrchVal.siteNo != '') {
					$('input[name=siteNo][value="'+this.getSrchVal.siteNo+'"]').prop('checked', true);
				} else {
					$('input[name=siteNo][value=""]').prop('checked', true);
				}
				
				var deviceChkList = this.getSrchVal.deviceCode;

				if($('input[name=deviceCodes]').length == deviceChkList.length) {
					$('input[name=deviceCodeAll]').prop('checked', true);
					$('input[name=deviceCodes]').prop('checked', true);
				}else {
					$('input[name=deviceCodeAll]').prop('checked', false);
					$('input[name=deviceCodes]').prop('checked', false);
				}
				for(var i=0 ; i<deviceChkList.length ; i++) {
					$('input[name=deviceCodes][value="'+deviceChkList[i]+'"]').prop('checked', true);
				}
				
				$('select[name=fromTime]').val(this.getSrchVal.fromTime).prop('selected', true);
				$('select[name=toTime]').val(this.getSrchVal.toTime).prop('selected', true);
			}
	};
	
	//검색전 validate
	_orderRealTime.periodValidate = function(sheetName) {
		var typeStats = sheetName;
		var fromTime = $('select[name=fromTime]').val();
		var toTime = $('select[name=toTime]').val();
		
		if(typeStats == '' || typeStats == null || typeStats == undefined) {
			alert('error: no typeStats');
			
			return false;
		}
		
		if(typeStats != 'prdt') {
			if(toTime - fromTime < 0 || (fromTime == '' || toTime == '')) {
				alert('시간대 선택이 올바르지 않습니다.\n조회 시간대를 다시 확인해주세요.');
				return false;
			}
		}
		
		if($('input[name=typeStats]').val() == 'claim' && !$('input[name=claimType]').is(':checked')) {
			alert('요청 구분은 필수로 선택해야 합니다.');
			return false;
		}
		
		if($('input[type=checkbox]:checked').length <= 0) {
			 alert('검색조건 중 선택사항은 최소 한개이상 선택이 되어야 합니다.');
			 return false;
		 }
		
		return true;
	};
	
	//엑셀 다운로드 버튼 활성, 비활성화
	_orderRealTime.excelBtnEnabled = function(rows) {
		if(rows > 1) {
			$('#downExcel').removeClass('disabled');
		} else {
			$('#downExcel').addClass('disabled');
		}
	}

})();