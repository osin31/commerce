/**
 * 
 */
(function() {

	var _salesStats = abc.object.createNestedObject(window,"abc.biz.stats.sales");
	
	abc.biz.stats.order.common.init();
	
	_salesStats.fromDate = '';
	_salesStats.toDate = '';
	_salesStats.chnnlNo = '';
	_salesStats.sheetType = {};
	_salesStats.periodTime = {};
	_salesStats.poChk = $('#mmnyPrdtYn');
	//매출통계 sheet
	
	//발송처별 매출통계sheet
	_salesStats.initSendTypeSalesStatsSheet = function() {
		var initSheet = {};
		var pageCount = ! abc.text.allNull($("#pageCount")) ? $('#pageCount').val() : 10000;

		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MaxSort:1,  MergeSheet: msHeaderOnly};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initSheet.Cols = [
				{Header:"기간별통계|일별통계", 		Type:"Text",		SaveName:"hourTitle",			Width: 150,		Align:"Center",		Edit:0,		Sort:1 }
			,	{Header:"합계|수량",				Type:"Int",			SaveName:"orderQtyAll",			Width: 100,		Align:"Right",		Edit:0,		Sort:1 }
			,	{Header:"합계|정상가",				Type:"Int",			SaveName:"prdtNormalAmtAll",	Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"합계|결제금액",			Type:"Int",			SaveName:"orderAmtAll",			Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"온라인|수량",				Type:"Int",			SaveName:"orderQty10000",		Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"온라인|정상가",			Type:"Int",			SaveName:"prdtNormalAmt10000",	Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"온라인|결제금액",			Type:"Int",			SaveName:"orderAmt10000",		Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"스마트|수량",				Type:"Int",			SaveName:"orderQty10001",		Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"스마트|정상가",			Type:"Int",			SaveName:"prdtNormalAmt10001",	Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"스마트|결제금액",			Type:"Int",			SaveName:"orderAmt10001",		Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"매장|수량",				Type:"Int",			SaveName:"orderQty10002",		Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"매장|정상가",				Type:"Int",			SaveName:"prdtNormalAmt10002",	Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"매장|결제금액",			Type:"Int",			SaveName:"orderAmt10002",		Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"입점사|수량",				Type:"Int",			SaveName:"orderQty10003",		Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"입점사|정상가",			Type:"Int",			SaveName:"prdtNormalAmt10003",	Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"입점사|결제금액",			Type:"Int",			SaveName:"orderAmt10003",		Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
		
		];

		createIBSheet2(document.getElementById("sendTypeSaleStatsGrid"), "sendTypeSalesStatsSheet", "100%", "429px");
		IBS_InitSheet(sendTypeSalesStatsSheet, initSheet);
		
		 //Grid 건수 정보 표시
		 sendTypeSalesStatsSheet.SetCountPosition(3);
		 //Grid 페이지 네비게이션 버튼 표시
		 sendTypeSalesStatsSheet.SetPagingPosition(2);
		 //Grid width 자동 조절
		 sendTypeSalesStatsSheet.FitColWidth();
		 //Grid 마지막 컬럼을 All 너비에 맞춘다.
		 sendTypeSalesStatsSheet.SetExtendLastCol(1);
		 //좌측행 고정.
		 sendTypeSalesStatsSheet.SetFrozenCol(4);
		 
		_salesStats.sheetType = sendTypeSalesStatsSheet;
		
		sendTypeSalesStatsSheet_OnSearchEnd = function(Code, Msg, StCode, StMsg, Response){
		
			_salesStats.sheetType.SetCellText(1, 0, _salesStats.gridText($('#choicePeriod').val()));
			_salesStats.excelBtnEnabled(sendTypeSalesStatsSheet.RowCount());
		};

	};
	
	//디바이스별 매출통계sheet
	_salesStats.initDeviceTypeSalesStatsSheet = function() {
		var initSheet = {};
		var pageCount = ! abc.text.allNull($("#pageCount")) ? $('#pageCount').val() : 10000;

		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MaxSort:1,  MergeSheet: msHeaderOnly};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initSheet.Cols = [
				{Header:"기간별통계|일별통계", 		Type:"Text",		SaveName:"hourTitle",				Width: 150,		Align:"Center",		Edit:0,		Sort:1 }
			,	{Header:"합계|수량",				Type:"Int",			SaveName:"orderQtyAll",				Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"합계|정상가",				Type:"Int",			SaveName:"prdtNormalAmtAll",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"합계|결제금액",			Type:"Int",			SaveName:"orderAmtAll",				Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"PC|수량",					Type:"Int",			SaveName:"orderQty10000",			Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"PC|정상가",				Type:"Int",			SaveName:"prdtNormalAmt10000",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"PC|결제금액",				Type:"Int",			SaveName:"orderAmt10000",			Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"MOBILE|수량",				Type:"Int",			SaveName:"orderQty10001",			Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"MOBILE|정상가",			Type:"Int",			SaveName:"prdtNormalAmt10001",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"MOBILE|결제금액",			Type:"Int",			SaveName:"orderAmt10001",			Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"APP|수량",					Type:"Int",			SaveName:"orderQty10002",			Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"APP|정상가",				Type:"Int",			SaveName:"prdtNormalAmt10002",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"APP|결제금액",				Type:"Int",			SaveName:"orderAmt10002",			Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			
		];

		createIBSheet2(document.getElementById("deviceTypeSaleStatsGrid"), "deviceTypeSalesStatsSheet", "100%", "429px");
		IBS_InitSheet(deviceTypeSalesStatsSheet, initSheet);
		
		 //Grid 건수 정보 표시
		deviceTypeSalesStatsSheet.SetCountPosition(3);
		 //Grid 페이지 네비게이션 버튼 표시
		deviceTypeSalesStatsSheet.SetPagingPosition(2);
		 //Grid width 자동 조절
		deviceTypeSalesStatsSheet.FitColWidth();
		 //Grid 마지막 컬럼을 All 너비에 맞춘다.
		deviceTypeSalesStatsSheet.SetExtendLastCol(1);
		//좌측행 고정.
		deviceTypeSalesStatsSheet.SetFrozenCol(4);
		
		_salesStats.sheetType = deviceTypeSalesStatsSheet;
		
		deviceTypeSalesStatsSheet_OnSearchEnd = function(Code, Msg, StCode, StMsg, Response){
			
			_salesStats.sheetType.SetCellText(1, 0, _salesStats.gridText($('#choicePeriod').val()));
			_salesStats.excelBtnEnabled(deviceTypeSalesStatsSheet.RowCount());
		};
	};
	
	//결제수단별 매출통계 sheet
	_salesStats.initPaymentTypeSalesStatsSheet = function() {
		var initSheet = {};
		var pageCount = ! abc.text.allNull($("#pageCount")) ? $('#pageCount').val() : 10000;

		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MaxSort:1,  MergeSheet: msHeaderOnly};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initSheet.Cols = [
				{Header:"기간별통계|일별통계", 					Type:"Text",		SaveName:"hourTitle",			Width: 350,		Align:"Center",		Edit:0,		Sort:0 }
			,	{Header:"합계|건수",							Type:"Int",			SaveName:"orderQtyAll",			Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"합계|결제금액",						Type:"Int",			SaveName:"orderAmtAll",			Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"신용카드|건수",						Type:"Int",			SaveName:"orderQty10000",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"신용카드|결제금액",					Type:"Int",			SaveName:"orderAmt10000",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"무통장입금|건수",						Type:"Int",			SaveName:"orderQty10001",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"무통장입금|결제금액",					Type:"Int",			SaveName:"orderAmt10001",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"실시간계좌이체|건수",					Type:"Int",			SaveName:"orderQty10002",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"실시간계좌이체|결제금액",				Type:"Int",			SaveName:"orderAmt10002",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"휴대폰결제|건수",						Type:"Int",			SaveName:"orderQty10003",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"휴대폰결제|결제금액",					Type:"Int",			SaveName:"orderAmt10003",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"네이버페이|건수",						Type:"Int",			SaveName:"orderQty10004",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"네이버페이|결제금액",					Type:"Int",			SaveName:"orderAmt10004",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"카카오페이|건수",						Type:"Int",			SaveName:"orderQty10005",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"카카오페이|결제금액",					Type:"Int",			SaveName:"orderAmt10005",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"기프트카드|건수",						Type:"Int",			SaveName:"orderQty10006",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"기프트카드|결제금액",					Type:"Int",			SaveName:"orderAmt10006",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"포인트|건수",							Type:"Int",			SaveName:"orderQty99999",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"포인트|결제금액",						Type:"Int",			SaveName:"orderAmt99999",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			
		];

		createIBSheet2(document.getElementById("paymentTypeSaleStatsGrid"), "paymentTypeSalesStatsSheet", "100%", "429px");
		IBS_InitSheet(paymentTypeSalesStatsSheet, initSheet);
		
		 //Grid 건수 정보 표시
		paymentTypeSalesStatsSheet.SetCountPosition(3);
		 //Grid 페이지 네비게이션 버튼 표시
		paymentTypeSalesStatsSheet.SetPagingPosition(2);
		 //Grid width 자동 조절
		paymentTypeSalesStatsSheet.FitColWidth();
		 //Grid 마지막 컬럼을 All 너비에 맞춘다.
		paymentTypeSalesStatsSheet.SetExtendLastCol(1);
		//좌측행 고정.
		paymentTypeSalesStatsSheet.SetFrozenCol(3);
		
		_salesStats.sheetType = paymentTypeSalesStatsSheet;
		
		paymentTypeSalesStatsSheet_OnSearchEnd = function(Code, Msg, StCode, StMsg, Response){
			
			_salesStats.sheetType.SetCellText(1, 0, _salesStats.gridText($('#choicePeriod').val()));
			_salesStats.excelBtnEnabled(paymentTypeSalesStatsSheet.RowCount());
		};
		
	};
	
	//배송수단별 매출통계sheet
	_salesStats.initDlvyTypeSalesStatsSheet = function() {
		var initSheet = {};
		var pageCount = ! abc.text.allNull($("#pageCount")) ? $('#pageCount').val() : 10000;

		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MaxSort:1,  MergeSheet: msHeaderOnly};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initSheet.Cols = [
				{Header:"기간별통계|일별통계", 				Type:"Text",		SaveName:"hourTitle",			Width: 150,		Align:"Center",		Edit:0,		Sort:1 }
			,	{Header:"합계|수량",						Type:"Int",			SaveName:"orderQtyAll",			Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"합계|정상가",						Type:"Int",			SaveName:"prdtNormalAmtAll",	Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"합계|결제금액",					Type:"Int",			SaveName:"orderAmtAll",			Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"일반배송|수량",					Type:"Int",			SaveName:"orderQty10000",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"일반배송|정상가",					Type:"Int",			SaveName:"prdtNormalAmt10000",	Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"일반배송|결제금액",				Type:"Int",			SaveName:"orderAmt10000",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"편의점픽업|수량",					Type:"Int",			SaveName:"orderQty10001",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"편의점픽업|정상가",				Type:"Int",			SaveName:"prdtNormalAmt10001",	Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"편의점픽업|결제금액",				Type:"Int",			SaveName:"orderAmt10001",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"매장픽업|수량",					Type:"Int",			SaveName:"orderQty10002",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"매장픽업|정상가",					Type:"Int",			SaveName:"prdtNormalAmt10002",	Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"매장픽업|결제금액",				Type:"Int",			SaveName:"orderAmt10002",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			
		];

		createIBSheet2(document.getElementById("dlvyTypeSaleStatsGrid"), "dlvyTypeSalesStatsSheet", "100%", "429px");
		IBS_InitSheet(dlvyTypeSalesStatsSheet, initSheet);
		
		 //Grid 건수 정보 표시
		dlvyTypeSalesStatsSheet.SetCountPosition(3);
		 //Grid 페이지 네비게이션 버튼 표시
		dlvyTypeSalesStatsSheet.SetPagingPosition(2);
		 //Grid width 자동 조절
		dlvyTypeSalesStatsSheet.FitColWidth();
		 //Grid 마지막 컬럼을 All 너비에 맞춘다.
		dlvyTypeSalesStatsSheet.SetExtendLastCol(1);
		//좌측행 고정.
		dlvyTypeSalesStatsSheet.SetFrozenCol(4);
		
		_salesStats.sheetType = dlvyTypeSalesStatsSheet;
		
		dlvyTypeSalesStatsSheet_OnSearchEnd = function(Code, Msg, StCode, StMsg, Response){
			
			_salesStats.sheetType.SetCellText(1, 0, _salesStats.gridText($('#choicePeriod').val()));
			_salesStats.excelBtnEnabled(dlvyTypeSalesStatsSheet.RowCount());
		};
	};
	
	//시간대별 매출통계sheet
	_salesStats.initTimeTypeSalesStatsSheet = function() {
		var initSheet = {};
		var pageCount = ! abc.text.allNull($("#pageCount")) ? $('#pageCount').val() : 10000;

		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MaxSort:1,  MergeSheet: msHeaderOnly};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		if(_salesStats.poChk.children().length > 1) {
			initSheet.Cols = [
					{Header:"시간대별통계|시간대별통계", 	Type:"Text",		SaveName:"hourTitle",				Width: 150,		Align:"Center",		Edit:0,		Sort:0, FontBold:1,	FontUnderline:1,	 Cursor:"Pointer" }
				,	{Header:"합계|수량",					Type:"Int",			SaveName:"orderQtyAll",				Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
				,	{Header:"합계|정상가",					Type:"Int",			SaveName:"prdtNormalAmtAll",		Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
				,	{Header:"합계|결제금액",				Type:"Int",			SaveName:"orderAmtAll",				Width: 100,		Align:"Right",		Edit:0,		Sort:0 }
				,	{Header:"온라인|수량",					Type:"Int",			SaveName:"orderQty10000",			Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
				,	{Header:"온라인|정상가",				Type:"Int",			SaveName:"prdtNormalAmt10000",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
				,	{Header:"온라인|결제금액",				Type:"Int",			SaveName:"orderAmt10000",			Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
				,	{Header:"스마트|수량",					Type:"Int",			SaveName:"orderQty10001",			Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
				,	{Header:"스마트|정상가",				Type:"Int",			SaveName:"prdtNormalAmt10001",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
				,	{Header:"스마트|결제금액",				Type:"Int",			SaveName:"orderAmt10001",			Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
				,	{Header:"매장|수량",					Type:"Int",			SaveName:"orderQty10002",			Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
				,	{Header:"매장|정상가",					Type:"Int",			SaveName:"prdtNormalAmt10002",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
				,	{Header:"매장|결제금액",				Type:"Int",			SaveName:"orderAmt10002",			Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
				,	{Header:"입점사|수량",					Type:"Int",			SaveName:"orderQty10003",			Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
				,	{Header:"입점사|정상가",				Type:"Int",			SaveName:"prdtNormalAmt10003",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
				,	{Header:"입점사|결제금액",				Type:"Int",			SaveName:"orderAmt10003",			Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			
			];
		} else {
			initSheet.Cols = [
				{Header:"시간대별통계|시간대별통계", 	Type:"Text",		SaveName:"hourTitle",				Width: 30,		Align:"Center",		Edit:0,		Sort:0, FontBold:1,	FontUnderline:1,	 Cursor:"Pointer" }
			,	{Header:"수량|수량",					Type:"Int",			SaveName:"orderQty10003",			Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"정상가|정상가",				Type:"Int",			SaveName:"prdtNormalAmt10003",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"결제금액|결제금액",			Type:"Int",			SaveName:"orderAmt10003",			Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
		
		];
		}

		createIBSheet2(document.getElementById("timeTypeSaleStatsGrid"), "timeTypeSalesStatsSheet", "100%", "429px");
		IBS_InitSheet(timeTypeSalesStatsSheet, initSheet);
		
		 //Grid 건수 정보 표시
		//timeTypeSalesStatsSheet.SetCountPosition(3);
		 //Grid 페이지 네비게이션 버튼 표시
		timeTypeSalesStatsSheet.SetPagingPosition(2);
		 //Grid width 자동 조절
		timeTypeSalesStatsSheet.FitColWidth();
		 //Grid 마지막 컬럼을 All 너비에 맞춘다.
		timeTypeSalesStatsSheet.SetExtendLastCol(1);
		//좌측행 고정.
		timeTypeSalesStatsSheet.SetFrozenCol(4);
		
		_salesStats.sheetType = timeTypeSalesStatsSheet;
		
		timeTypeSalesStatsSheet_OnSearchEnd = function(Code, Msg, StCode, StMsg, Response){
			
			_salesStats.excelBtnEnabled(timeTypeSalesStatsSheet.RowCount());
		};
	};
	
	//연령별 매출통계 sheet
	_salesStats.initAgeTypeSalesStatsSheet = function() {
		var initSheet = {};
		var pageCount = ! abc.text.allNull($("#pageCount")) ? $('#pageCount').val() : 10000;

		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MaxSort:1,  MergeSheet: msHeaderOnly};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initSheet.Cols = [
				{Header:"기간별통계|일별통계", 					Type:"Text",		SaveName:"hourTitle",			Width: 200,		Align:"Center",		Edit:0,		Sort:1 }
			,	{Header:"합계|수량",							Type:"Int",			SaveName:"orderQtyAll",			Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"합계|정상가",							Type:"Int",			SaveName:"prdtNormalAmtAll",	Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"합계|결제금액",						Type:"Int",			SaveName:"orderAmtAll",			Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"10대|수량",							Type:"Int",			SaveName:"orderQty10000",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"10대|정상가",							Type:"Int",			SaveName:"prdtNormalAmt10000",	Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"10대|결제금액",						Type:"Int",			SaveName:"orderAmt10000",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"20대|수량",							Type:"Int",			SaveName:"orderQty10001",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"20대|정상가",							Type:"Int",			SaveName:"prdtNormalAmt10001",	Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"20대|결제금액",						Type:"Int",			SaveName:"orderAmt10001",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"30대|수량",							Type:"Int",			SaveName:"orderQty10002",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"30대|정상가",							Type:"Int",			SaveName:"prdtNormalAmt10002",	Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"30대|결제금액",						Type:"Int",			SaveName:"orderAmt10002",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"40대|수량",							Type:"Int",			SaveName:"orderQty10003",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"40대|정상가",							Type:"Int",			SaveName:"prdtNormalAmt10003",	Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"40대|결제금액",						Type:"Int",			SaveName:"orderAmt10003",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"50대|수량",							Type:"Int",			SaveName:"orderQty10004",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"50대|정상가",							Type:"Int",			SaveName:"prdtNormalAmt10004",	Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"50대|결제금액",						Type:"Int",			SaveName:"orderAmt10004",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"60대|수량",							Type:"Int",			SaveName:"orderQty10005",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"60대|정상가",							Type:"Int",			SaveName:"prdtNormalAmt10005",	Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"60대|결제금액",						Type:"Int",			SaveName:"orderAmt10005",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"기타|수량",							Type:"Int",			SaveName:"orderQty10006",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"기타|정상가",							Type:"Int",			SaveName:"prdtNormalAmt10006",	Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"기타|결제금액",						Type:"Int",			SaveName:"orderAmt10006",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			
		];

		createIBSheet2(document.getElementById("ageTypeSaleStatsGrid"), "ageTypeSalesStatsSheet", "100%", "429px");
		IBS_InitSheet(ageTypeSalesStatsSheet, initSheet);
		
		 //Grid 건수 정보 표시
		ageTypeSalesStatsSheet.SetCountPosition(3);
		 //Grid 페이지 네비게이션 버튼 표시
		ageTypeSalesStatsSheet.SetPagingPosition(2);
		 //Grid width 자동 조절
		ageTypeSalesStatsSheet.FitColWidth();
		 //Grid 마지막 컬럼을 All 너비에 맞춘다.
		ageTypeSalesStatsSheet.SetExtendLastCol(1);
		//좌측행 고정.
		ageTypeSalesStatsSheet.SetFrozenCol(4);
		
		_salesStats.sheetType = ageTypeSalesStatsSheet;
		
		ageTypeSalesStatsSheet_OnSearchEnd = function(Code, Msg, StCode, StMsg, Response){
			
			_salesStats.sheetType.SetCellText(1, 0, _salesStats.gridText($('#choicePeriod').val()));
			_salesStats.excelBtnEnabled(ageTypeSalesStatsSheet.RowCount());
		};
		
	};
	
	//성별 매출통계sheet
	_salesStats.initGenderTypeSalesStatsSheet = function() {
		var initSheet = {};
		var pageCount = ! abc.text.allNull($("#pageCount")) ? $('#pageCount').val() : 10000;

		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MaxSort:1,  MergeSheet: msHeaderOnly};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initSheet.Cols = [
				{Header:"기간별통계|일별통계|일별통계", 	Type:"Text",		SaveName:"hourTitle",				Width: 300,		Align:"Center",		Edit:0,		Sort:1 }
			,	{Header:"합계|합계|수량",					Type:"Int",			SaveName:"orderQtyAll",				Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"합계|합계|정상가",					Type:"Int",			SaveName:"prdtNormalAmtAll",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"합계|합계|결제금액",				Type:"Int",			SaveName:"orderAmtAll",				Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"온라인 회원|남성|수량",			Type:"Int",			SaveName:"orderQty10000",			Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"온라인 회원|남성|정상가",			Type:"Int",			SaveName:"prdtNormalAmt10000",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"온라인 회원|남성|결제금액",		Type:"Int",			SaveName:"orderAmt10000",			Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"온라인 회원|여성|수량",			Type:"Int",			SaveName:"orderQty10001",			Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"온라인 회원|여성|정상가",			Type:"Int",			SaveName:"prdtNormalAmt10001",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"온라인 회원|여성|결제금액",		Type:"Int",			SaveName:"orderAmt10001",			Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"온라인 회원|알수없음|수량",		Type:"Int",			SaveName:"orderQty10002",			Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"온라인 회원|알수없음|정상가",		Type:"Int",			SaveName:"prdtNormalAmt10002",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"온라인 회원|알수없음|결제금액",	Type:"Int",			SaveName:"orderAmt10002",			Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"멤버십 회원|남성|수량",			Type:"Int",			SaveName:"orderQty10003",			Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"멤버십 회원|남성|정상가",			Type:"Int",			SaveName:"prdtNormalAmt10003",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"멤버십 회원|남성|결제금액",		Type:"Int",			SaveName:"orderAmt10003",			Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"멤버십 회원|여성|수량",			Type:"Int",			SaveName:"orderQty10004",			Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"멤버십 회원|여성|정상가",			Type:"Int",			SaveName:"prdtNormalAmt10004",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"멤버십 회원|여성|결제금액",		Type:"Int",			SaveName:"orderAmt10004",			Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"비회원||수량",						Type:"Int",			SaveName:"orderQty10005",			Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"비회원||정상가",					Type:"Int",			SaveName:"prdtNormalAmt10005",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"비회원||결제금액",					Type:"Int",			SaveName:"orderAmt10005",			Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
		
		];

		createIBSheet2(document.getElementById("genderTypeSaleStatsGrid"), "genderTypeSalesStatsSheet", "100%", "429px");
		IBS_InitSheet(genderTypeSalesStatsSheet, initSheet);
		
		 //Grid 건수 정보 표시
		genderTypeSalesStatsSheet.SetCountPosition(3);
		 //Grid 페이지 네비게이션 버튼 표시
		genderTypeSalesStatsSheet.SetPagingPosition(2);
		 //Grid width 자동 조절
		genderTypeSalesStatsSheet.FitColWidth();
		 //Grid 마지막 컬럼을 All 너비에 맞춘다.
		genderTypeSalesStatsSheet.SetExtendLastCol(1);
		//좌측행 고정.
		genderTypeSalesStatsSheet.SetFrozenCol(4);
		
		_salesStats.sheetType = genderTypeSalesStatsSheet;
		
		genderTypeSalesStatsSheet_OnSearchEnd = function(Code, Msg, StCode, StMsg, Response){
			
			_salesStats.sheetType.SetCellText(1, 0, _salesStats.gridText($('#choicePeriod').val()));
			_salesStats.excelBtnEnabled(genderTypeSalesStatsSheet.RowCount());
		};
	};
	
	//상품 매출통계sheet
	_salesStats.initPrdtTypeSalesStatsSheet = function() {
		var initSheet = {};
		var pageCount = ! abc.text.allNull($("#pageCount")) ? $('#pageCount').val() : 10000;

		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MaxSort:1,  MergeSheet: msHeaderOnly, FrozenCol : 8};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		if(_salesStats.poChk.children().length > 1) {
			initSheet.Cols = [
					{Header:"브랜드 번호|브랜드 번호", 				Type:"Text",		SaveName:"brandNo",					Width: 150,		Align:"Center",		Edit:0,		Sort:0 }
				,	{Header:"사이트|사이트",						Type:"Text",		SaveName:"siteName",				Width: 150,		Align:"Left",		Edit:0,		Sort:0 }
				,	{Header:"브랜드명|브랜드명",					Type:"Text",		SaveName:"brandName",				Width: 150,		Align:"Left",		Edit:0,		Sort:0 }
				,	{Header:"카테고리|카테고리",					Type:"Text",		SaveName:"stdCtgrName",				Width: 150,		Align:"Left",		Edit:0,		Sort:0 }
				,	{Header:"스타일코드|스타일코드",				Type:"Text",		SaveName:"styleInfo",				Width: 150,		Align:"Left",		Edit:0,		Sort:0 }
				,	{Header:"색상|색상",							Type:"Text",		SaveName:"prdtColorName",			Width: 150,		Align:"Left",		Edit:0,		Sort:0 }
//			,	{Header:"상품코드/상품명",						Type:"Text",		SaveName:"prdtNoName",			Width: 500,		Align:"Left",		Edit:0,		Sort:0 }
				,	{Header:"상품코드|상품코드",		Type:"Text",		SaveName:"prdtNo",				Width: 150,		Align:"Left",		Edit:0,		Sort:0 }
				,	{Header:"상품명|상품명",				Type:"Text",		SaveName:"prdtName",				Width: 400,		Align:"Left",		Edit:0,		Sort:0 }
				,	{Header:"합계|수량",							Type:"Int",			SaveName:"orderQtyAll",				Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
				,	{Header:"합계|정상가",							Type:"Int",			SaveName:"prdtNormalAmtAll",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
				,	{Header:"합계|결제금액",						Type:"Int",			SaveName:"orderAmtAll",				Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
				,	{Header:"온라인|수량",							Type:"Int",			SaveName:"orderQty10000",			Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
				,	{Header:"온라인|정상가",						Type:"Int",			SaveName:"prdtNormalAmt10000",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
				,	{Header:"온라인|결제금액",						Type:"Int",			SaveName:"orderAmt10000",			Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
				,	{Header:"스마트|수량",							Type:"Int",			SaveName:"orderQty10001",			Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
				,	{Header:"스마트|정상가",						Type:"Int",			SaveName:"prdtNormalAmt10001",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
				,	{Header:"스마트|결제금액",						Type:"Int",			SaveName:"orderAmt10001",			Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
				,	{Header:"매장|수량",							Type:"Int",			SaveName:"orderQty10002",			Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
				,	{Header:"매장|정상가",							Type:"Int",			SaveName:"prdtNormalAmt10002",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
				,	{Header:"매장|결제금액",						Type:"Int",			SaveName:"orderAmt10002",			Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
				,	{Header:"입점사|수량",							Type:"Int",			SaveName:"orderQty10003",			Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
				,	{Header:"입점사|정상가",						Type:"Int",			SaveName:"prdtNormalAmt10003",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
				,	{Header:"입점사|결제금액",						Type:"Int",			SaveName:"orderAmt10003",			Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			
			];
		} else {
			initSheet.Cols = [
				{Header:"브랜드 번호", 						Type:"Text",		SaveName:"brandNo",					Width: 80,		Align:"Center",		Edit:0,		Sort:0 }
			,	{Header:"사이트명",							Type:"Text",		SaveName:"siteName",				Width: 150,		Align:"Left",		Edit:0,		Sort:0 }
			,	{Header:"브랜드",							Type:"Text",		SaveName:"brandName",				Width: 150,		Align:"Left",		Edit:0,		Sort:0 }
			,	{Header:"카테고리",							Type:"Text",		SaveName:"stdCtgrName",				Width: 150,		Align:"Left",		Edit:0,		Sort:0 }
			,	{Header:"스타일코드",						Type:"Text",		SaveName:"styleInfo",				Width: 150,		Align:"Left",		Edit:0,		Sort:0 }
			,	{Header:"색상",								Type:"Text",		SaveName:"prdtColorName",			Width: 150,		Align:"Left",		Edit:0,		Sort:0 }
			,	{Header:"상품코드/\n상품명",				Type:"Text",		SaveName:"prdtNoName",				Width: 150,		Align:"Left",		Edit:0,		Sort:0 }
			,	{Header:"수량",								Type:"Int",			SaveName:"orderQty10003",			Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"정상가",							Type:"Int",			SaveName:"prdtNormalAmt10003",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"결제금액",							Type:"Int",			SaveName:"orderAmt10003",			Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
		
		];
		}

		createIBSheet2(document.getElementById("prdtTypeSaleStatsGrid"), "prdtTypeSalesStatsSheet", "100%", "429px");
		IBS_InitSheet(prdtTypeSalesStatsSheet, initSheet);
		
		 //Grid 건수 정보 표시
		prdtTypeSalesStatsSheet.SetCountPosition(3);
		 //Grid 페이지 네비게이션 버튼 표시
		prdtTypeSalesStatsSheet.SetPagingPosition(2);
		 //Grid width 자동 조절
		prdtTypeSalesStatsSheet.FitColWidth();
		 //Grid 마지막 컬럼을 All 너비에 맞춘다.
		prdtTypeSalesStatsSheet.SetExtendLastCol(1);
		
		_salesStats.sheetType = prdtTypeSalesStatsSheet;
		
		prdtTypeSalesStatsSheet_OnSearchEnd = function(Code, Msg, StCode, StMsg, Response){
			
			_salesStats.excelBtnEnabled(prdtTypeSalesStatsSheet.RowCount());
		};
	};
	
	//입점사 매출통계sheet
	_salesStats.initVendorTypeSalesStatsSheet = function() {
		var initSheet = {};
		var pageCount = ! abc.text.allNull($("#pageCount")) ? $('#pageCount').val() : 10000;

		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MaxSort:1,  MergeSheet: msHeaderOnly};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initSheet.Cols = [
				{Header:"입점사 통계|입점사명", 		Type:"Text",		SaveName:"vndrName",				Width: 150,		Align:"Center",		Edit:0,		Sort:1 }
			,	{Header:"입점사 통계|입점사코드", 		Type:"Text",		SaveName:"vndrNo",					Width: 150,		Align:"Center",		Edit:0,		Sort:1, FontBold:1,	FontUnderline:1,	 Cursor:"Pointer"}
			,	{Header:"합계|수량",					Type:"Int",			SaveName:"orderQtyAll",				Width: 150,		Align:"Right",		Edit:0,		Sort:1 }
			,	{Header:"합계|정상가",					Type:"Int",			SaveName:"prdtNormalAmtAll",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"합계|결제금액",				Type:"Int",			SaveName:"orderAmtAll",				Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"PC|수량",						Type:"Int",			SaveName:"orderQty10000",			Width: 150,		Align:"Right",		Edit:0,		Sort:1 }
			,	{Header:"PC|정상가",					Type:"Int",			SaveName:"prdtNormalAmt10000",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"PC|결제금액",					Type:"Int",			SaveName:"orderAmt10000",			Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"MOBILE|수량",					Type:"Int",			SaveName:"orderQty10001",			Width: 150,		Align:"Right",		Edit:0,		Sort:1 }
			,	{Header:"MOBILE|정상가",				Type:"Int",			SaveName:"prdtNormalAmt10001",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"MOBILE|결제금액",				Type:"Int",			SaveName:"orderAmt10001",			Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"APP|수량",						Type:"Int",			SaveName:"orderQty10002",			Width: 150,		Align:"Right",		Edit:0,		Sort:1 }
			,	{Header:"APP|정상가",					Type:"Int",			SaveName:"prdtNormalAmt10002",		Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"APP|결제금액",					Type:"Int",			SaveName:"orderAmt10002",			Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			
		];

		createIBSheet2(document.getElementById("vendorTypeSaleStatsGrid"), "vendorTypeSalesStatsSheet", "100%", "429px");
		IBS_InitSheet(vendorTypeSalesStatsSheet, initSheet);
		
		 //Grid 건수 정보 표시
		vendorTypeSalesStatsSheet.SetCountPosition(3);
		 //Grid 페이지 네비게이션 버튼 표시
		vendorTypeSalesStatsSheet.SetPagingPosition(2);
		 //Grid width 자동 조절
		vendorTypeSalesStatsSheet.FitColWidth();
		 //Grid 마지막 컬럼을 All 너비에 맞춘다.
		vendorTypeSalesStatsSheet.SetExtendLastCol(1);
		//좌측행 고정.
		vendorTypeSalesStatsSheet.SetFrozenCol(5);
		
		_salesStats.sheetType = vendorTypeSalesStatsSheet;
		
		vendorTypeSalesStatsSheet_OnSearchEnd = function(Code, Msg, StCode, StMsg, Response){
			
			_salesStats.excelBtnEnabled(vendorTypeSalesStatsSheet.RowCount());
		};
	};
	
	//입점사 매출현황 상세 팝업 sheet
	_salesStats.initVendorPopSalesStatsSheet = function() {
		var initSheet = {};
		var pageCount = ! abc.text.allNull($("#pageCount")) ? $('#pageCount').val() : 10000;

		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MaxSort:1,  MergeSheet: msHeaderOnly};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initSheet.Cols = [
				{Header:"브랜드번호", 							Type:"Text",		SaveName:"brandNo",				Width: 80,		Align:"Center",		Edit:0,		Sort:0 }
			,	{Header:"브랜드", 								Type:"Text",		SaveName:"brandName",			Width: 100,		Align:"Left",		Edit:0,		Sort:0 }
			,	{Header:"카테고리",								Type:"Text",		SaveName:"stdCtgrName",			Width: 100,		Align:"Left",		Edit:0,		Sort:0 }
			,	{Header:"스타일코드",							Type:"Text",		SaveName:"styleInfo",			Width: 100,		Align:"Left",		Edit:0,		Sort:0 }
			,	{Header:"색상",									Type:"Text",		SaveName:"prdtColorCode",		Width: 100,		Align:"Left",		Edit:0,		Sort:0 }
//			,	{Header:"상품코드/상품명",						Type:"Text",		SaveName:"prdtNoName",			Width: 500,		Align:"Left",		Edit:0,		Sort:0 }
				,	{Header:"상품코드|상품코드",		Type:"Text",		SaveName:"prdtNo",				Width: 150,		Align:"Left",		Edit:0,		Sort:0 }
				,	{Header:"상품명|상품명",				Type:"Text",		SaveName:"prdtName",				Width: 400,		Align:"Left",		Edit:0,		Sort:0 }
			,	{Header:"수량",									Type:"Int",			SaveName:"orderQtyAll",			Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"정상가",								Type:"Int",			SaveName:"prdtNormalAmtAll",	Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			,	{Header:"결제금액",								Type:"Int",			SaveName:"orderAmtAll",			Width: 150,		Align:"Right",		Edit:0,		Sort:0 }
			
			
		];

		createIBSheet2(document.getElementById("vendorDetailPopGrid"), "vendorPopSalesStatsSheet", "100%", "429px");
		IBS_InitSheet(vendorPopSalesStatsSheet, initSheet);
		
		 //Grid 건수 정보 표시
		vendorPopSalesStatsSheet.SetCountPosition(3);
		 //Grid 페이지 네비게이션 버튼 표시
		vendorPopSalesStatsSheet.SetPagingPosition(2);
		 //Grid width 자동 조절
		vendorPopSalesStatsSheet.FitColWidth();
		 //Grid 마지막 컬럼을 All 너비에 맞춘다.
		vendorPopSalesStatsSheet.SetExtendLastCol(1);
		//좌측행 고정.
		vendorPopSalesStatsSheet.SetFrozenCol(7);
		
		_salesStats.sheetType = vendorPopSalesStatsSheet;
		
		vendorPopSalesStatsSheet_OnSearchEnd = function(Code, Msg, StCode, StMsg, Response){
			
			_salesStats.excelBtnEnabled(vendorPopSalesStatsSheet.RowCount());
		};
	};
	
	//일별 - 어제, 일주일, 한달 관련 date
	_salesStats.periodYwm = function(data) {
		var date = $('input[name=dayToDate]');
		
		if(data == 'yesterday') {
			abc.date.statDays(date);
		}
		if(data == 'week') {
			abc.date.statWeek(date);
		}
		if(data == 'month') {
			abc.date.statMonth(date);
		}
		
		_salesStats.fromDate = $('input[name=dayFromDate]').val();
		_salesStats.toDate = $('input[name=dayToDate]').val();
	};
	
	//일별, 월별, 년별 날짜 세팅
	_salesStats.periodSet = function(data) {
		
		//일별
		if(data == 'date') {
			var years = $('input[name=dayToDate]').val().slice(0,4);
			var month = $('input[name=dayToDate]').val().slice(5,7);
			var day = $('input[name=dayToDate]').val().slice(8,10);
			var today = new Date();
			
			_salesStats.fromDate = $('input[name=dayFromDate]').val();
			
			//
			if( (today.getFullYear() < years) || ((today.getFullYear() == years) && ((today.getMonth()+1) < month))
					|| ( ( (today.getFullYear() <= years) && ( (today.getMonth()+1) <= month) ) && ( day - (today.getDate()-1) ) > 0 ) ) {
						today.setDate(today.getDate()-1);
						_salesStats.toDate = today.format('yyyy.MM.dd');
			} else {
				_salesStats.toDate = $('input[name=dayToDate]').val();
			}
		}
		
		//월별
		if(data == 'month') {
			
			var years = $('#monthToDate').val().slice(0,4);
			var month = $('#monthToDate').val().slice(5,7);
			var lastDay = (new Date(years, month, 0)).getDate();
			var today = new Date();
			
			_salesStats.fromDate = $('#monthFromDate').val() + '.01';
			
			// month가 이번달 같을경우는 어제 날짜로 세팅
			if((today.getMonth()+1) == month) {
				today.setDate(today.getDate()-1);
				_salesStats.toDate = today.format('yyyy.MM.dd');
			} else {
				_salesStats.toDate = years + '.' + month + '.' + lastDay;
			}
		}
		//년별
		if(data == 'year') {
			
			var years = $('#yearToDate').val().slice(0,4);
			var month = $('#yearToDate').val().slice(5,7);
			var thisYear = new Date();
			
			_salesStats.fromDate = $('#yearFromDate').val() + '.01.01';
			
			//선택 년도가 올해랑 같을경우 어제 날짜로 세팅
			if(thisYear.getFullYear() == years) {
				thisYear.setDate(thisYear.getDate()-1);
				_salesStats.toDate = thisYear.format('yyyy.MM.dd');
			} else {
				_salesStats.toDate = $('#yearToDate').val() + '.12.31';
			}
			
		}
	};
	//그리드 text변경
	_salesStats.gridText = function(value){
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
	
	//검색전 data set
	_salesStats.preSearchDataSet = function() {
		var statsTypeName = $('input[name=typeStats]').val();
		var periodType = $('#choicePeriod').val();
		
		$('input[name=typeStats]').next().find('input:hidden[name=fromDate]').remove();
		$('input[name=typeStats]').next().find('input:hidden[name=toDate]').remove();
		$('input[name=typeStats]').next().find('input:hidden[name=chnnlNo]').remove();
		
		// 일별, 월별, 년별 선택시 기간 set
		if(periodType == 'date' || statsTypeName =='timeType' || statsTypeName == 'prdtType'
			|| statsTypeName == 'vendorType' || statsTypeName == 'vendorPop') {
			_salesStats.periodSet('date');
		} else {
			_salesStats.periodSet(periodType);
		}
		
		_salesStats.vendorSrchVal.dataBind();
		
		var html ='';
		html += '<input type="hidden" name="fromDate" value="' + _salesStats.fromDate +'">';
		html += '<input type="hidden" name="toDate" value="' + _salesStats.toDate +'">';
		html += '<input type="hidden" name="chnnlNo" value="' + _salesStats.chnnlNo +'">';

		$('input[name=typeStats]').next().append(html);
	};
	
	//검색전 조건 validate
	_salesStats.preSearchValidate = function(typeName, periodType) {
		
		var statsTypeName = typeName;
		var periodType = periodType;
		var toDate = '';
		var fromDate = '';
		
		if(statsTypeName == 'sendType' || statsTypeName == 'deviceType' || statsTypeName == 'paymentType'
			|| statsTypeName == 'dlvyType' || statsTypeName == 'timeType' || statsTypeName == 'ageType'
				|| statsTypeName == 'prdtType' || statsTypeName == 'genderType' || statsTypeName == 'vendorType'
					|| statsTypeName == 'vendorPop') {
						
		} else {
			alert('typeName error');
			return false;
		}
		
		//일별기간 or 시간별통계 or 상품별통계 or 입점사별통계 or 입점사 상세팝업 선택시
		if(periodType == 'date' || statsTypeName =='timeType' || statsTypeName == 'prdtType'
			|| statsTypeName == 'vendorType' || statsTypeName == 'vendorPop') {
			
			fromDate = abc.date.changeDate($('input[name=dayFromDate]').val());
			toDate = abc.date.changeDate($('input[name=dayToDate]').val());
			
			if($('input[name=dayFromDate]').val() == '' || $('input[name=dayToDate]').val() == '') {
				alert('기간을 설정해주세요.');
				return false;
			}
			
			// 일별 조회기간 최대 1년 validate
			/*
			if((toDate.getFullYear()-fromDate.getFullYear()) >= 2 || ( (toDate.getFullYear() - fromDate.getFullYear()) >= 1
					&& ( (toDate.getMonth() - fromDate.getMonth()) > 0
					|| (toDate.getDate() - fromDate.getDate()) > 0 ) )){
						alert('조회기간은 최대 1년까지 선택 가능합니다.');
						
						abc.date.statWeek($('input[name=dayToDate]'));
						
						return false;
			}
			*/
		}
		
		// 일별,월별,년별 기간 값 변경시 validate 체크
		if(periodType == 'date' || statsTypeName =='timeType' || statsTypeName == 'prdtType'
			|| statsTypeName == 'vendorType' || statsTypeName == 'vendorPop') {
				toDate = abc.date.changeDate($('input[name=dayToDate]').val());
				fromDate = abc.date.changeDate($('input[name=dayFromDate]').val());

				if(fromDate > toDate) {
					alert('잘못 입력된 기간입니다.');
					_salesStats.periodYwm('week');
					$('input[name=dayFromDate]').focus();
					
					return false;
				}
		
		} else if(periodType == 'month') {
			toDate = $('select[name=monthToDate]').val();
			fromDate = $('select[name=monthFromDate]').val();
			
			if((toDate.slice(0, 4) - fromDate.slice(0, 4)) < 0 || (toDate.slice(5, 7) - fromDate.slice(5, 7)) < 0){
				alert('잘못 입력된 기간입니다.');
	
				$('select[name=monthFromDate]').val(fromDate.slice(0, 4) + '.' + fromDate.slice(5, 7)).prop('selected', true);
				$('select[name=monthToDate]').val(fromDate.slice(0, 4) + '.' + fromDate.slice(5, 7)).prop('selected', true);
				
				return false;
			}
			
		} else if(periodType == 'year') {
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
		
		//시간대별 매출통계, 시간대 선택 vaildate
		var fromHour = $('select[name=fromHour]').val();
		var toHour = $('select[name=toHour]').val();

		if((toHour - fromHour) < 0 || (fromHour != '' && toHour == '')
				|| (fromHour == '' && toHour != '')) {
					alert('시간대 선택이 올바르지 않습니다.\n조회 시간대를 다시 확인해주세요.');
					
					$('select[name=fromHour]').val('').prop('selected', true);
					$('select[name=toHour]').val('').prop('selected', true);

					return false;
		}
		
		if($('input[type=checkbox]:checked').length <= 0) {
			 alert('검색조건 중 선택사항은 최소 한개이상 선택이 되어야 합니다.');
			 return false;
		}
//		//디바이스 구분 체크박스
//		if(statsTypeName != 'deviceType' && statsTypeName != 'vendorType') {
//			if($('input[name=deviceCodeArr]:checked').length <= 0) {
//				alert('디바이스 선택은 최소 한개 이상이여야 합니다.');
//				chkBoolean = false;
//			}
//		}
//		// 발송처 구분 체크박스
//		if(statsTypeName == 'deviceType' || statsTypeName == 'paymentType' || statsTypeName == 'dlvyType'
//				|| statsTypeName == 'ageType' || statsTypeName == 'genderType') {
//					if($('input[name=stockGbnCodeArr]:checked').length <=0) {
//						alert('발송처 선택은 최소 한개 이상이여야 합니다.');
//						chkBoolean = false;
//					}
//		}
//		// 회원 구분 체크박스
//		if(statsTypeName == 'dlvyType' || statsTypeName == 'prdtType') {
//			if($('input[name=chkMember]:checked').length <= 0) {
//				alert('회원 선택은 최소 한개 이상이여야 합니다.');
//				chkBoolean = false;
//			}
//		}
//		//결제 수단 체크박스
//		if(statsTypeName != 'paymentType') {
//			if($('input[name=pymntMeansCode]:checked').length <= 0) {
//				alert('결제수단 선택은 최소 한개 이상이여야 합니다.');
//				chkBoolean = false;
//			}
//		}

		return true;
		
	};
	
	//검색 조회 함수
	_salesStats.doSalesStatsAction = function(doAction) {
		
		var statsTypeName = $('input[name=typeStats]').val();
		var periodType = $('#choicePeriod').val();
		
		switch(doAction) {
		
			case 'search' :
				var pageCount = $('#pageCount').val();
				
				//상품코드 자사 선택시 발송처 입점사 제외
				if($('#mmnyPrdtYn').val() == 'C') {
					if($('input[name=stockGbnCodeAll]').is(':checked')) {
						$('#chkStore10003').prop('checked', false);
					}
				}
				// 상품코드 입점 선택시 발송처 입점사 외 나머지 제외
				if($('#mmnyPrdtYn').val() == 'V') {
					if($('input[name=stockGbnCodeAll]').is(':checked')) {
						$('#chkStore10000, #chkStore10001, #chkStore10002').prop('checked', false);
					}
				}

				var param = { url : '/stats/sales/stats-sales-search'
					, onePageRow : pageCount
					, subparam : FormQueryStringEnc(document.form)
					, sheet : statsTypeName+'SalesStatsSheet' };

				//검색전 validate 체크
				if(_salesStats.preSearchValidate(statsTypeName, periodType)) {
					DataSearchPaging(param);
//					_salesStats.hiddenSheet = JSON.parse(_salesStats.sheetType.GetSearchData(param.url, param.subparam));
				}
				
				break;
				
			case 'excel' :
				var statsTypeName = $('input[name=typeStats]').val();
//				_salesStats.preSearchDataSet();

				if($('#choicePeriod').val() !='year' && _salesStats.sheetType.GetTotalRows() < 1) {
					alert('엑셀 다운로드는 조회 후에 이용 가능합니다.');
						return false;
				}
				
				$('#form').prop('action', '/stats/sales/download-'+statsTypeName+'Excel')
						  .prop('method', 'post')
						  .submit();
				break;
		}
	};
	
	//시간별 매출통계, 매출현황 검색조건 save
	_salesStats.srchVal = {
			
			dataSet : {
				dayFromDate : '',
				dayToDate : '',
				siteNo : '',
				mmnyPrdtYn : '',
				deviceCode : '',
				fromHour : '',
				toHour : ''
			},
			dataBind : function() {
				
				this.dataSet.dayFromDate = $('input[name=dayFromDate]').val();
				this.dataSet.dayToDate = $('input[name=dayToDate]').val();
				
				this.dataSet.siteNo = $('input[name=siteNo]:checked').val();
				this.dataSet.mmnyPrdtYn = $('#mmnyPrdtYn').val();
				
				this.dataSet.deviceCode = [];
				$('input[name=deviceCodeArr]').each(function(){
					
					if($(this).is(':checked')){
						_salesStats.srchVal.dataSet.deviceCode.push($(this).val());
					}
				});
				
				this.dataSet.fromHour = _salesStats.periodTime.substring(0, 2);
				this.dataSet.toHour = _salesStats.periodTime.substring(3, 5);
			}
	};
	
	//시간별 매출 통계, 상품매출현황 팝업 검색조건 draw
	_salesStats.detailPopSrchOpt = {
			init : function() {
				this.getSrchVal = opener.abc.biz.stats.sales.srchVal.dataSet;
				this.drawData();
			},
			getSrchVal : {},
			drawData : function() {
				
				$('input[name=dayFromDate]').val(this.getSrchVal.dayFromDate);
				$('input[name=dayToDate]').val(this.getSrchVal.dayToDate);
				
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
				
				$('select[name=fromTime]').val(this.getSrchVal.fromHour).prop('selected', true);
				$('select[name=toTime]').val(this.getSrchVal.toHour).prop('selected', true);
			}
	}
	
	//입점사 상품매출 상세 팝업 검색조건 save
	_salesStats.vendorSrchVal = {
			 
			srchDataSet : {
				 fromDate : '',
				 toDate : '',
				 siteNo : '',
				 vndrNo : '',
				 vndrName : '',
				 chkPayment : '',
				 chkEscroTrue : '',
				 chkEscroFalse : '',
				 isAdmin : ''
			 },
			 
			dataBind : function() {
				
				this.srchDataSet.isAdmin = $('input[name=isAdmin]').val();
				this.srchDataSet.fromDate = $('input[name=dayFromDate]').val();
				this.srchDataSet.toDate = $('input[name=dayToDate]').val();
				$('input[name=siteNo]').each(function(){
					if($(this).is(':checked')) {
						_salesStats.vendorSrchVal.srchDataSet.siteNo = $(this).val();
					}
				});
				this.srchDataSet.vndrNo = $('input[name=vndrNo]').val();
				this.srchDataSet.vndrName = $('input[name=vndrName]').val();
				this.srchDataSet.chkPayment = [];
				$('input[name=pymntMeansCode]').each(function(){
					if($(this).is(':checked')) {
						
						_salesStats.vendorSrchVal.srchDataSet.chkPayment.push($(this).val());
					}
				});
				
				this.srchDataSet.chkEscroTrue = $('input[name=chkEscroTrue]:checked').val();
				this.srchDataSet.chkEscroFalse = $('input[name=chkEscroFalse]:checked').val();
			}
	};
	
	//입점사 검색조건 상세팝업에 draw
	_salesStats.vendorPopSrchOpt = {
			init : function() {
				this.getData = opener.abc.biz.stats.sales.vendorSrchVal.srchDataSet;
				this.dataDraw();
			},
			getData : {},
			dataDraw : function() {
				
				if($('input[name=typeStats]').val() == 'vendorPop') {

					if(this.getData.isAdmin == 'false') {
						$('input[name=vndrName]').prop('disabled', true);
						$('#searchVndrPop').remove();
					}
					$('input[name=dayFromDate]').val(this.getData.fromDate);
					$('input[name=dayToDate]').val(this.getData.toDate);
					_salesStats.periodSet('date');
					
					if(this.getData.sitNo != '') {
						$('input[name=siteNo][value="'+this.getData.siteNo+'"]').prop('checked', true);
					}
					$('input[name=vndrNo]').val(this.getData.vndrNo);
					$('input[name=vndrName]').val(this.getData.vndrName);

					var chkPayment = _salesStats.vendorPopSrchOpt.getData.chkPayment;

					if($('input[name=pymntMeansCode]').length == chkPayment.length) {
						$('input[name=chkPaymentAll]').prop('checked', true);
						$('input[name=pymntMeansCode]').prop('checked', true);
					}else {
						$('input[name=chkPaymentAll]').prop('checked', false);
						$('input[name=pymntMeansCode]').prop('checked', false);
					}
					
					for(var i=0 ; i<chkPayment.length ; i++) {
						$('input[name=pymntMeansCode][value="'+chkPayment[i]+'"]').prop('checked', true);
					}
					if(this.getData.chkEscroTrue != undefined){
						$('input[name=chkEscroTrue]').prop('checked', true);
					} else {
						$('input[name=chkEscroTrue]').prop('checked', false);
					}
					if(this.getData.chkEscroFalse != undefined){
						$('input[name=chkEscroFalse]').prop('checked', true);
					} else {
						$('input[name=chkEscroFalse]').prop('checked', false);
					}
						
				}
			}
	};
	
	//브랜드 찾기 callback
	_salesStats.callbackFindBrand = function(data) {
		$("#vndrBrandNo").val(data[0].brandNo);
		$("#brandName").val(data[0].brandName);
	};
	
	//엑셀 다운로드 버튼 활성, 비활성화
	_salesStats.excelBtnEnabled = function(rows) {
		
		if(rows > 1) {
			$('#downExcel').removeClass('disabled');
		} else {
			$('#downExcel').addClass('disabled');
		}
		
	};
	
	//페이지 control
	_salesStats.commonSet = function() {
		
		//일별 기간 default.
		_salesStats.periodYwm('week');
		_salesStats.periodSet('date');
		
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
			_salesStats.periodYwm('yesterday');
		});
		$('#toWeek').off().on('click', function(){
			_salesStats.periodYwm('week');
		});
		$('#toMonth').off().on('click', function(){
			_salesStats.periodYwm('month');
		});
		
		// 자사, 입점 선택시 채널번호 selectbox
		$('#ourChannel').off().on('change', function(){
			_salesStats.chnnlNo = $(this).val();
		});
		
		//검색조회.
		$('#searchBtn').off().on('click', function(){
			_salesStats.preSearchDataSet();
			
			_salesStats.doSalesStatsAction('search');
		});
		
		//엑셀 다운로드
		$('#downExcel').off().on('click', function(){
			_salesStats.doSalesStatsAction('excel');
		});
		
		//임직원여부 체크시 회원구분 체크박스 비활성화
		$('#empYn').off().on('change', function(){
			if($(this).is(':checked')) {
				$('#chkMemberAll, input[name=chkMember]').prop('checked', false)
											  			 .prop('disabled', true);
			} else {
				$('#chkMemberAll, input[name=chkMember]').prop('checked', true)
				  							  			 .prop('disabled', false);
			}
		});
		
		// 검색 초기화
		$('#resetBtn').off().on('click', function(){
			$('#form')[0].reset();
			
			$('#monthFromDate, #monthWaveMark, #monthToDate').addClass('hidden');
			$('#yearFromDate, #yearWaveMark, #yearToDate').addClass('hidden');
			$('#dayFromDateSpan, #dayWaveMark, #dayToDateSpan').removeClass('hidden');
			$('#toYesterday, #toWeek, #toMonth').removeClass('hidden');
			$('#ourChannel').addClass('hidden').val('');
			
			//po 로그인시에는 입점업체 사이트 select박스 노출.
			if($('select[name=vndrGbnType]').children().length >1) {
				$('#shoperSiteNo').addClass('hidden').val('');
			}
			$('input[name=stockGbnCodeArr]').prop('checked', true).parent().parent().removeClass('hidden');
			$('input[name=vndrNo]').val('');
			$('input[name=vndrBrandNo]').val('');
			$('#midCategory').html('<option value="">중카테고리 선택</option>');
			$('#smallCategory').html('<option value="">소카테고리 선택</option>');
			abc.date.statWeek($('input[name=dayToDate]'));
		});
		
		//상품 매출통계, 브랜드 찾기 팝업
		$("#serchBrandPop, #brandName").off().on('click', function(){
			$(this).val('');
			$("#vndrBrandNo").val('');
			abc.brandSearchPopup(false, "abc.biz.stats.sales.callbackFindBrand");
		});
		
		//상품 매출통계, 입점사 찾기 팝업( callbackFindVndr 맨 아래에 정의)
		$('#searchVndrPop, #vndrName').off().on('click', function(){
			$('#vndrNo').val('');
			$('#vndrName').val('');
			abc.vndrSearchPopup(false, 'callbackFindVndr');
		});
		
		//대분류 카테고리 선택시 중분류 카테고리값 변경.
		$('#bigCategory').off().on('change', function(){
			var ctgrNo = $(this).val();
			var midHtml = '<option value="">중카테고리 선택</option>';
			var smallHtml = '<option value="">소카테고리 선택</option>';
			
			if($(this).val() != '') {
				$.ajax({
					url : '/stats/sales/get-mid-category',
					method : 'post',
					data : {ctgrNo : ctgrNo},
				}).done(function(data){

					$.each(data, function(i, item) {
						midHtml += '<option value="'+item.stdCtgrNo+'">'+item.stdCtgrName+'</option>';
					});
	
					$('#midCategory').html(midHtml);
					$('#smallCategory').html(smallHtml);
				}).fail(function(){
					abc.errorMessage();
				});
			} else {
				$('#midCategory').html(midHtml);
				$('#smallCategory').html(smallHtml);
			}

		});
		
		//소분류 카테고리 목록
		$('#midCategory').off().on('change', function(){
			var ctgrNo = $(this).val();
			var html = '<option value="">소카테고리 선택</option>';

			if($(this).val() != '') {
				$.ajax({
					url : '/stats/sales/get-small-category',
					method : 'post',
					data : {ctgrNo : ctgrNo}
				}).done(function(data){
					
					$.each(data, function(i, item) {
						html += '<option value="'+item.stdCtgrNo+'">'+item.stdCtgrName+'</option>';
					});
	
					$('#smallCategory').html(html);
				}).fail(function(){
					abc.errorMessage();
				});
			} else {
				$('#smallCategory').html(html);
			}
		});
		
	};
		
})();

//입점사 찾기 callback
function callbackFindVndr(data) {
	var vndrNo = data.arrayVndrNo[0];
	var vndrName = data.arrayVndrName[0];
	
	$('#vndrNo').val(vndrNo);
	$('#vndrName').val(vndrName);

};

