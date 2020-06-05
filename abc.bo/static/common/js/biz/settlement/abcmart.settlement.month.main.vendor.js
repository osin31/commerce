/***
 * 관리자 업무와 관련된 함수 정의.
 *
 */
var monthOption = {
		penltyLevyRate :'0',
		penltyLevyRateStandard :'0',
		penltyOcrncRate :'0',
		dlvyDayCount :'0',
		pymntCount :'0',
		pymntAmt :'0',
		dlvyAmtCount :'0',
		dlvyMonthAmt :'0',
		sellCmsnCount :'0',
		sellCmsnAmt :'0',
		promoCount :'0',
		promoMonthAmt :'0',
		penltyCount :'0',
		penltyAmt :'0',
		penltyMdatAmt :'0',
		excclcAmt :'0',
		excclcMdatAmt :'0'
		};

var alertSelectEmpty = "처리대상이 선택되지 않았습니다.\n 처리대상을 확인해주세요.";
var alertConfirmMod = "저장 하시겠습니까?";
(function() {
	var _settlementmonthVndr = abc.object.createNestedObject(window,"abc.biz.settlement.month.main.vendor");

	/**
	 * ibsheet 초기화
	 */
	_settlementmonthVndr.sheetInit1 = function(){
		var pageCount1 = $('#pageCount1').val();
		// Grid 객체 생성
		createIBSheet2(document.getElementById("monthGrid"), "monthSheet", "100%", "350px");
		// Grid 초기화 기본/헤더/컬럼선언
		var initSheet1 = {Sort:0, ColMove:0, ColResize:1};

		initSheet1.Cfg = {SearchMode:smServerPaging2, Page:pageCount1, MergeSheet:msHeaderOnly, AutoFitColWidth: "init|search|resize"};
		initSheet1.HeaderMode = {};
		initSheet1.Cols = [
							  {Header:"",						Type:"Status",	SaveName:"status",		Width: 80,		 Align:"Center" ,Hidden:1}
							, {Header:"",						Type:"CheckBox",	SaveName:"checkedRows",		Width: 80,		 Align:"Center"}
							, {Header:"브랜드코드|브랜드코드", 		Type:"Text",		SaveName:"brandNo",		 	MinWidth:130,	Align:"Center", 	Edit:0, Sort:0}
							, {Header:"브랜드명|브랜드명", 			Type:"Text",		SaveName:"brandName",		MinWidth:130,	Align:"Center", 	Edit:0, Sort:0}
							, {Header:"판매금액|결제금액", 			Type:"Float",		SaveName:"pymntAmt",		MinWidth:130,	Align:"Center", 	Edit:0, Format:"#,##0원", Sort:0}
							, {Header:"공제금액(-)|판매수수료", 		Type:"Float",		SaveName:"sellCmsnAmt",		MinWidth:130,	Align:"Center", 	Edit:0, Format:"#,##0원", Sort:0}
							, {Header:"공제금액(-)|프로모션비용", 		Type:"Float",		SaveName:"promoAmt",		MinWidth:130,	Align:"Center", 	Edit:0, Format:"#,##0원", Sort:0}
							, {Header:"패널티 금액(-)|패널티건수", 		Type:"Int",			SaveName:"penltyCount",		MinWidth:130,	Align:"Center", 	Edit:0, Format:"#,##0건", Sort:0}
							, {Header:"패널티 금액(-)|패널티 산정금액",	Type:"Float",		SaveName:"penltyTrgtAmt",	MinWidth:130,	Align:"Center", 	Edit:0, Format:"#,##0원", Sort:0}
							, {Header:"패널티 금액(-)|패널티 조정금액", 	Type:"Float",		SaveName:"penltyMdatAmt",	MinWidth:130,	Align:"Center", 	Edit:0, Format:"#,##0원", Sort:0 , AcceptKeys:"N"}
							, {Header:"최종 전상금액|정산확정금액", 		Type:"Float",		SaveName:"excclcAmt",		MinWidth:130,	Align:"Center", 	Edit:0, Format:"#,##0원", Sort:0}
							, {Header:"최종 전상금액|정산조정금액", 		Type:"Float",		SaveName:"excclcMdatAmt",	MinWidth:130,	Align:"Center", 	Edit:0, Format:"#,##0원", Sort:0 , AcceptKeys:"N"}
							, {Header:"정산확정여부",				Type:"Text",		SaveName:"excclcDcsnYn",   	Align:"Center", Edit:0 , Hidden:1}
							, {Header:"정산SEQ",					Type:"Text",		SaveName:"excclcSeq",   	Align:"Center", Edit:0 , Hidden:1}
						];

		// Grid 초기화
		IBS_InitSheet(monthSheet, initSheet1);
		// Grid 건수 정보 표시
		monthSheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		monthSheet.SetPagingPosition(2);
		// Grid width 자동 조절
		monthSheet.FitColWidth();
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		monthSheet.SetExtendLastCol(1);

	}
	/**
	 * 월정산 리스트 로우 단위로 상태 체크 
	 */
	monthSheet_OnRowSearchEnd = function (row, col, value){
		var excclcDcsnYn = monthSheet.GetRowData(row).excclcDcsnYn;
	
		if(excclcDcsnYn == "Y"){
			monthSheet.SetCellEditable(row,"penltyMdatAmt",0);
			monthSheet.SetCellEditable(row,"excclcMdatAmt",0);
			$('#excclcDcsnYn').val('Y');
		}else{
			monthSheet.SetCellEditable(row,"penltyMdatAmt",1);
			monthSheet.SetCellEditable(row,"excclcMdatAmt",1);
		}
	}
	
	/**
	 * 월정산 검색이 끝난후 
	 */
	monthSheet_OnSearchEnd = function(Code, Msg, StCode, StMsg, Response){

		if(monthSheet.RowCount() > 1){
			$('#excel1').removeClass("disabled");
		}else{
			$('#excel1').addClass("disabled");
		}
	}
	
	
	/**
	 * ibsheet 초기화
	 */
	_settlementmonthVndr.sheetInit2 = function(){
		var pageCount2 = $('#pageCount2').val();
		// Grid 객체 생성
		createIBSheet2(document.getElementById("sellAmtGrid"), "sellAmtSheet", "100%", "300px");
		// Grid 초기화 기본/헤더/컬럼선언
		var initSheet2 = {};

		initSheet2.Cfg = {SearchMode:smServerPaging2, Page:pageCount2, MergeSheet:msHeaderOnly, AutoFitColWidth: "init|search|resize"};
		initSheet2.HeaderMode = {};
		initSheet2.Cols = [
						  {Header:"정산년월|정산년월", 			Type:"Text",		SaveName:"excclcYm",			MinWidth:130,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"주문유형|주문유형", 			Type:"Text",		SaveName:"salesCnclGbnTypeName",MinWidth:130,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"주문번호|주문번호", 			Type:"Text",		SaveName:"orderNo",				MinWidth:130,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"브랜드명|브랜드명", 			Type:"Text",		SaveName:"brandName",			MinWidth:130,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"브랜드코드|브랜드코드", 		Type:"Text",		SaveName:"brandNo",				MinWidth:130,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"업체상품코드|업체상품코드", 		Type:"Text",		SaveName:"vndrPrdtNoText",		MinWidth:130,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"상품명/옵션/수량|상품명/옵션/수량",Type:"Text",		SaveName:"prdtName",			MinWidth:130,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"정상가|정상가", 				Type:"Float",		SaveName:"prdtNormalAmt",		MinWidth:130,	Align:"Center", 	Edit:0, Format:"#,##0원", Sort:0}
						, {Header:"판매가|판매가", 				Type:"Float",		SaveName:"sellAmt",				MinWidth:130,	Align:"Center", 	Edit:0, Format:"#,##0원", Sort:0}
						, {Header:"배송지연\n여부|배송지연\n여부", 	Type:"Text",		SaveName:"delayDayYn",			MinWidth:130,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"쿠폰\n사용여부|쿠폰\n사용여부", 	Type:"Text",		SaveName:"cpnUseYn",			MinWidth:130,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"일반쿠폰|쿠폰명", 			Type:"Text",		SaveName:"cpnName",				MinWidth:130,	Align:"Center", 	Edit:0, Sort:0,ColMerge:0}
						, {Header:"일반쿠폰|쿠폰할인액", 			Type:"Float",		SaveName:"cpnDscntAmt",			MinWidth:130,	Align:"Center", 	Edit:0, Format:"#,##0원", Sort:0,ColMerge:0}
						, {Header:"일반쿠폰|A-RT분담액", 		Type:"Float",		SaveName:"abcAmt",				MinWidth:130,	Align:"Center", 	Edit:0, Format:"#,##0원", Sort:0,ColMerge:0}
						, {Header:"일반쿠폰|입점사분담액", 		Type:"Float",		SaveName:"cpnShareAmt",			MinWidth:130,	Align:"Center", 	Edit:0, Format:"#,##0원", Sort:0,ColMerge:0}
						, {Header:"플러스쿠폰|쿠폰명", 			Type:"Text",		SaveName:"plusCpnName",			MinWidth:130,	Align:"Center", 	Edit:0, Sort:0,ColMerge:0}
						, {Header:"플러스쿠폰|쿠폰할인액", 		Type:"Text",		SaveName:"plusCpnDscntAmt",		MinWidth:130,	Align:"Center",     Edit:0, Sort:0,ColMerge:0}
						, {Header:"플러스쿠폰|A-RT분담액", 		Type:"Float",		SaveName:"plusAbcAmt",			MinWidth:130,	Align:"Center", 	Edit:0, Format:"#,##0원", Sort:0,ColMerge:0}
						, {Header:"플러스쿠폰|입점사분담액", 		Type:"Float",		SaveName:"plusCpnShareAmt",		MinWidth:130,	Align:"Center", 	Edit:0, Format:"#,##0원", Sort:0,ColMerge:0}
						, {Header:"실결제금액|공급가", 			Type:"Text",		SaveName:"supplyAmt",			MinWidth:130,	Align:"Center", Edit:0, Sort:0,ColMerge:0}
						, {Header:"실결제금액|부가세", 			Type:"Text",		SaveName:"vatAmt",				MinWidth:130,	Align:"Center", Edit:0, Sort:0,ColMerge:0}
						, {Header:"실결제금액|결제금액", 			Type:"Float",		SaveName:"actPymnt",				MinWidth:130,	Align:"Center", 	Edit:0, Format:"#,##0원", Sort:0,ColMerge:0}
						, {Header:"실결제금액|판매수수료율", 		Type:"Text",			SaveName:"vndrCmsnRate",		MinWidth:130,	Align:"Center",	Edit:0, Sort:0,ColMerge:0}
						, {Header:"판매수수료|공급가", 			Type:"Text",		SaveName:"vndrSupplyAmt",		MinWidth:130,	Align:"Center", 	Edit:0, Sort:0,ColMerge:0}
						, {Header:"판매수수료|부가세", 			Type:"Text",		SaveName:"vndrVatAmt",			MinWidth:130,	Align:"Center", 	Edit:0, Sort:0,ColMerge:0}
						, {Header:"판매수수료|판매수수료", 		Type:"Float",		SaveName:"vndrCmsnAmt",			MinWidth:130,	Align:"Center", 		Edit:0, Format:"#,##0원", Sort:0,ColMerge:0}
						, {Header:"적립율|적립율", 				Type:"Text",			SaveName:"buyPointSaveRate",	MinWidth:130,	Align:"Center", Edit:0, Sort:0,ColMerge:0}
						, {Header:"적립포인트|적립포인트", 		Type:"Int",			SaveName:"buyPointSaveAmt",		MinWidth:130,	Align:"Center", 		Edit:0, Sort:0,ColMerge:0}
						, {Header:"정산금액|정산금액", 			Type:"Float",		SaveName:"excclcAmt",			MinWidth:130,	Align:"Center", 	Edit:0, Format:"#,##0원", Sort:0,ColMerge:0}
						, {Header:"정산확정여부|정산확정여부", 		Type:"Text",		SaveName:"excclcDcsnYn",		MinWidth:130,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"정산일자|정산일자", 			Type:"Text",		SaveName:"excclcDtm",			MinWidth:130,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"최종 수정자|최종 수정자", 		Type:"Text",		SaveName:"moderName",			MinWidth:130,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"최종수정일시|최종수정일시", 		Type:"Text",		SaveName:"finalModDtm",			MinWidth:130,	Align:"Center", 	Edit:0, Sort:0}
						];
		// Grid 초기화
		IBS_InitSheet(sellAmtSheet, initSheet2);
		// Grid 건수 정보 표시
		sellAmtSheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		sellAmtSheet.SetPagingPosition(2);
		// Grid width 자동 조절
		//sellAmtSheet.FitColWidth("12|8|8|8|8|8|8|8|8|8|8|8");
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		sellAmtSheet.SetExtendLastCol(1);

	}
	
	/**
	 * 판매수수료 검색이 끝난후 row 단위 상태 체크 
	 */
	sellAmtSheet_OnRowSearchEnd = function (row, col, value){
		var excclcYm = sellAmtSheet.GetRowData(row).excclcYm;
		var info = {Type: "Float", Align: "Center", Format:"#,##0원"};
		var infoPer = {Type: "Float", Align: "Center", Format:"\\%"};
		if(excclcYm == "합계"){
			sellAmtSheet.SetCellValue(row,16,"");
			sellAmtSheet.SetCellValue(row,19,"");
			sellAmtSheet.SetCellValue(row,20,"");
			sellAmtSheet.SetCellValue(row,22,"");
			sellAmtSheet.SetCellValue(row,23,"");
			sellAmtSheet.SetCellValue(row,24,"");
			sellAmtSheet.SetCellValue(row,26,"");
			sellAmtSheet.SetCellValue(row,31,"");
		}else{
			sellAmtSheet.InitCellProperty(row,16,info);
			sellAmtSheet.InitCellProperty(row,19,info);
			sellAmtSheet.InitCellProperty(row,20,info);
			sellAmtSheet.InitCellProperty(row,22,infoPer);
			sellAmtSheet.InitCellProperty(row,23,info);
			sellAmtSheet.InitCellProperty(row,24,info);
			sellAmtSheet.InitCellProperty(row,26,infoPer);
		}
	}
	
	/**
	 * 팜매수수료 검색이 끝난후 
	 */
	sellAmtSheet_OnSearchEnd = function(Code, Msg, StCode, StMsg, Response){
		var frozenRow 	= sellAmtSheet.GetFrozenRows();
		var currentPage = sellAmtSheet.GetCurrentPage();
		// 필요없는 '합계'행을 삭제하는 작업
		if(frozenRow > 0 && currentPage == 1){
			for(i=0; i<=sellAmtSheet.SearchRows(); i++){
				var saveName = sellAmtSheet.GetCellValue(i, "excclcYm");
				
				if(saveName == "합계"){
					sellAmtSheet.RowDelete(i, 0);		
				}
			}
		}
		if(sellAmtSheet.RowCount() > 1){
			$('#excel2').removeClass("disabled");
		}else{
			$('#excel2').addClass("disabled");
		}
		sellAmtSheet.SetRowBackColor(2, '#e2f5ff');
		sellAmtSheet.SetFrozenRows(1);
	}

	/**
	 * ibsheet 초기화
	 */
	_settlementmonthVndr.sheetInit3 = function(){
		var pageCount3 = $('#pageCount3').val();
		// Grid 객체 생성
		createIBSheet2(document.getElementById("dlvyAmtGrid"), "dlvyAmtSheet", "100%", "250px");
		// Grid 초기화 기본/헤더/컬럼선언
		var initSheet3 = {};
		
		initSheet3.Cfg = {SearchMode:smServerPaging2, Page:pageCount3, AutoFitColWidth: "init|search|resize"};
		initSheet3.HeaderMode = {};
		initSheet3.Cols = [
				  {Header:"정산년월", 			Type:"Text",	SaveName:"excclcYm",			MinWidth:130,	Align:"Center", 	Edit:0, Sort:0}
				, {Header:"주문유형", 			Type:"Text",	SaveName:"salesCnclGbnTypeName",MinWidth:130,	Align:"Center", 	Edit:0, Sort:0}
				, {Header:"주문번호", 			Type:"Text",	SaveName:"orderNo",				MinWidth:130,	Align:"Center", 	Edit:0, Sort:0}
				, {Header:"배송비", 			Type:"Float",	SaveName:"orderAmt",			MinWidth:130,	Align:"Center", 	Edit:0, Format:"#,##0원", Sort:0}
				, {Header:"정산확정여부", 		Type:"Text",	SaveName:"excclcDcsnYn",		MinWidth:130,	Align:"Center", 	Edit:0, Sort:0}
				, {Header:"정산일자", 			Type:"Text",	SaveName:"excclcDtm",			MinWidth:130,	Align:"Center", 	Edit:0, Sort:0}
				, {Header:"최종 수정자", 		Type:"Text",	SaveName:"moderName",			MinWidth:130,	Align:"Center", 	Edit:0,	Sort:0}
				, {Header:"최종수정일시", 		Type:"Text",	SaveName:"finalModDtm",			MinWidth:130,	Align:"Center", 	Edit:0, Sort:0}
			];
		// Grid 초기화
		IBS_InitSheet(dlvyAmtSheet, initSheet3);
		// Grid 건수 정보 표시
		dlvyAmtSheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		dlvyAmtSheet.SetPagingPosition(2);
		// Grid width 자동 조절
		//dlvyAmtSheet.FitColWidth("12|8|8|8|8|8|8|8|8|8|8|8");
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		dlvyAmtSheet.SetExtendLastCol(2);
//		dlvyAmtSheet.SetFrozenCol(1);
		
	}
	
	/**
	 * 배송비 검색이 끝난후 로우 단위 상태 체크 
	 */
	dlvyAmtSheet_OnRowSearchEnd = function (row, col, value){
		var excclcYm = dlvyAmtSheet.GetRowData(row).excclcYm;
		var info = {Type: "Float", Align: "Center", Format:"#,##0원"};
		var infoPer = {Type: "Float", Align: "Center", Format:"\\%"};
		if(excclcYm == "합계"){
			sellAmtSheet.SetCellValue(row,6,"");
		}else{
		}
	}
	
	/**
	 * 배송비 검색이 끝난후 
	 */
	dlvyAmtSheet_OnSearchEnd = function(Code, Msg, StCode, StMsg, Response){
		var frozenRow 	= dlvyAmtSheet.GetFrozenRows();
		var currentPage = dlvyAmtSheet.GetCurrentPage();
		// 필요없는 '합계'행을 삭제하는 작업
		if(frozenRow > 0 && currentPage == 1){
			for(i=0; i<=dlvyAmtSheet.SearchRows(); i++){
				var saveName = dlvyAmtSheet.GetCellValue(i, "excclcYm");
				
				if(saveName == "합계"){
					dlvyAmtSheet.RowDelete(i, 0);		
				}
			}
		}
		if(dlvyAmtSheet.RowCount() > 1){
			$('#excel3').removeClass("disabled");
		}else{
			$('#excel3').addClass("disabled");
		}
		dlvyAmtSheet.SetRowBackColor(1, '#e2f5ff');
		dlvyAmtSheet.SetFrozenRows(1);
	}
	
	/**
	 * ibsheet 초기화
	 */
	_settlementmonthVndr.sheetInit4 = function(){
		var pageCount4 = $('#pageCount3').val();
		// Grid 객체 생성
		createIBSheet2(document.getElementById("promoAmtGrid"), "promoAmtSheet", "100%", "250px");
		// Grid 초기화 기본/헤더/컬럼선언
		var initSheet4 = {};

		initSheet4.Cfg = {SearchMode:smServerPaging2, Page:pageCount4, MergeSheet:msHeaderOnly, AutoFitColWidth: "init|search|resize"};
		initSheet4.HeaderMode = {};
		initSheet4.Cols = [
						  {Header:"정산년월|정산년월", 			Type:"Text",		SaveName:"excclcYm",			MinWidth:130,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"주문유형|주문유형", 			Type:"Text",		SaveName:"salesCnclGbnTypeName",MinWidth:130,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"주문번호|주문번호", 			Type:"Text",		SaveName:"orderNo",				MinWidth:130,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"브랜드명|브랜드명", 			Type:"Text",		SaveName:"brandName",			MinWidth:130,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"브랜드코드|브랜드코드", 		Type:"Text",		SaveName:"brandNo",				MinWidth:130,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"업체상품코드|업체상품코드", 		Type:"Text",		SaveName:"vndrPrdtNoText",		MinWidth:130,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"상품명/옵션/수량|상품명/옵션/수량",Type:"Text",		SaveName:"prdtName",			MinWidth:130,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"정상가|정상가", 				Type:"Float",		SaveName:"prdtNormalAmt",		MinWidth:130,	Align:"Center", 	Edit:0, Format:"#,##0원", Sort:0}
						, {Header:"판매가|판매가", 				Type:"Float",		SaveName:"sellAmt",				MinWidth:130,	Align:"Center", 	Edit:0, Format:"#,##0원", Sort:0}
						, {Header:"쿠폰\n사용여부|쿠폰\n사용여부", 	Type:"Text",		SaveName:"cpnUseYn",			MinWidth:130,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"일반쿠폰|쿠폰명", 			Type:"Text",		SaveName:"cpnName",				MinWidth:130,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"일반쿠폰|쿠폰할인액", 			Type:"Float",		SaveName:"cpnDscntAmt",			MinWidth:130,	Align:"Center", 	Edit:0, Format:"#,##0원", Sort:0}
						, {Header:"일반쿠폰|A-RT분담액", 		Type:"Float",		SaveName:"abcAmt",				MinWidth:130,	Align:"Center", 	Edit:0, Format:"#,##0원", Sort:0}
						, {Header:"일반쿠폰|입점사분담액", 		Type:"Float",		SaveName:"cpnShareAmt",			MinWidth:130,	Align:"Center", 	Edit:0, Format:"#,##0원", Sort:0}
						, {Header:"플러스쿠폰|쿠폰명", 			Type:"Text",		SaveName:"plusCpnName",			MinWidth:130,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"플러스쿠폰|쿠폰할인액", 		Type:"Text",		SaveName:"plusCpnDscntAmt",		MinWidth:130,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"플러스쿠폰|A-RT분담액", 		Type:"Float",		SaveName:"plusAbcAmt",			MinWidth:130,	Align:"Center", 	Edit:0, Format:"#,##0원", Sort:0}
						, {Header:"플러스쿠폰|입점사분담액", 		Type:"Float",		SaveName:"plusCpnShareAmt",		MinWidth:130,	Align:"Center", 	Edit:0, Format:"#,##0원", Sort:0}
						, {Header:"실결제금액|공급가", 			Type:"Text",		SaveName:"supplyAmt",			MinWidth:130,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"실결제금액|부가세", 			Type:"Text",		SaveName:"vatAmt",				MinWidth:130,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"실결제금액|결제금액", 			Type:"Float",		SaveName:"actPymnt",				MinWidth:130,	Align:"Center", 	Edit:0, Format:"#,##0원", Sort:0}
						, {Header:"정산금액|정산금액", 			Type:"Float",		SaveName:"excclcAmt",			MinWidth:130,	Align:"Center", 	Edit:0, Format:"#,##0원", Sort:0}
						, {Header:"정산확정여부|정산확정여부", 		Type:"Text",		SaveName:"excclcDcsnYn",		MinWidth:130,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"정산일자|정산일자", 			Type:"Text",		SaveName:"excclcDtm",			MinWidth:130,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"최종 수정자|최종 수정자", 		Type:"Text",		SaveName:"moderName",			MinWidth:130,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"최종수정일시|최종수정일시", 		Type:"Text",		SaveName:"finalModDtm",			MinWidth:130,	Align:"Center", 	Edit:0, Sort:0}
						];
		// Grid 초기화
		IBS_InitSheet(promoAmtSheet, initSheet4);
		// Grid 건수 정보 표시
		promoAmtSheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		promoAmtSheet.SetPagingPosition(2);
		// Grid width 자동 조절
		//sellAmtSheet.FitColWidth("12|8|8|8|8|8|8|8|8|8|8|8");
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		promoAmtSheet.SetExtendLastCol(1);

	}
	/**
	 * 프로모션 검색이 끝나후 로우 단위 상태 체크 
	 */
	promoAmtSheet_OnRowSearchEnd = function (row, col, value){
		var excclcYm = promoAmtSheet.GetRowData(row).excclcYm;
		var info = {Type: "Float", Align: "Center", Format:"#,##0원"};
		var infoPer = {Type: "Float", Align: "Center", Format:"\\%"};
		if(excclcYm == "합계"){
			promoAmtSheet.SetCellValue(row,15,"");
			promoAmtSheet.SetCellValue(row,18,"");
			promoAmtSheet.SetCellValue(row,19,"");
			promoAmtSheet.SetCellValue(row,24,"");
		}else{
			promoAmtSheet.InitCellProperty(row,15,info);
			promoAmtSheet.InitCellProperty(row,18,info);
			promoAmtSheet.InitCellProperty(row,19,info);
		}
	}
		
	/**
	 * 프로모션 비용 검색이 끝난후 
	 */
	promoAmtSheet_OnSearchEnd = function(Code, Msg, StCode, StMsg, Response){
		var frozenRow 	= promoAmtSheet.GetFrozenRows();
		var currentPage = promoAmtSheet.GetCurrentPage();
		// 필요없는 '합계'행을 삭제하는 작업
		if(frozenRow > 0 && currentPage == 1){
			for(i=0; i<=promoAmtSheet.SearchRows(); i++){
				var saveName = promoAmtSheet.GetCellValue(i, "excclcYm");
				
				if(saveName == "합계"){
					promoAmtSheet.RowDelete(i, 0);		
				}
			}
		}
		if(promoAmtSheet.RowCount() > 1){
			$('#excel4').removeClass("disabled");
		}else{
			$('#excel4').addClass("disabled");
		}
		
		promoAmtSheet.SetRowBackColor(2, '#e2f5ff');
		promoAmtSheet.SetFrozenRows(1);
	}
	
	/**
	 * ibsheet 초기화
	 */
	_settlementmonthVndr.sheetInit5 = function(){
		var pageCount5 = $('#pageCount5').val();
		// Grid 객체 생성
		createIBSheet2(document.getElementById("penltyGrid"), "penltySheet", "100%", "290px");
		// Grid 초기화 기본/헤더/컬럼선언
		var initSheet5 = {};

		initSheet5.Cfg = {SearchMode:smServerPaging2, Page:pageCount5, MergeSheet:msHeaderOnly};
		initSheet5.HeaderMode = {};
		initSheet5.Cols= [
						  {Header:"정산년월|정산년월", 				Type:"Text",		SaveName:"excclcYm",			MinWidth:130,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"주문유형|주문유형", 				Type:"Text",		SaveName:"salesCnclGbnTypeName",MinWidth:130,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"주문번호|주문번호", 				Type:"Text",		SaveName:"orderNo",				MinWidth:130,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"브랜드명|브랜드명", 				Type:"Text",		SaveName:"brandNo",				MinWidth:130,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"브랜드코드|브랜드코드", 			Type:"Text",		SaveName:"brandName",			MinWidth:130,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"업체상품코드|업체상품코드", 			Type:"Text",		SaveName:"vndrPrdtNoText",		MinWidth:130,	Align:"Center", 	Edit:0, Edit:0, Sort:0}
						, {Header:"상품명/옵션/수량|상품명/옵션/수량",	Type:"Text",		SaveName:"prdtName",			MinWidth:130,	Align:"Center", 	Edit:0, Edit:0, Sort:0}
						, {Header:"정상가|정상가", 					Type:"Float",		SaveName:"prdtNormalAmt",		MinWidth:130,	Align:"Center", 	Edit:0, Format:"#,##0원", Sort:0}
						, {Header:"판매가|판매가", 					Type:"Float",		SaveName:"sellAmt",				MinWidth:130,	Align:"Center", 	Edit:0, Format:"#,##0원", Sort:0}
						, {Header:"배송지연여부|배송지연여부", 	    	Type:"Text",		SaveName:"delayDayYn",			MinWidth:130,	Align:"Center", 	Edit:0, Edit:0, Sort:0}
						, {Header:"패널티산정|배송지연일수", 			Type:"Text",		SaveName:"delayDayCount",	MinWidth:130,	Align:"Center", 	Edit:0, Edit:0, Sort:0}
						, {Header:"패널티산정|패널티수수료율",			Type:"Text",		SaveName:"penltyLevyRate",	MinWidth:130,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"패널티산정|패널티산정금액", 			Type:"Text",		SaveName:"penltyAmt",		MinWidth:130,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"실결제금액|공급가", 				Type:"Text",		SaveName:"supplyAmt",		MinWidth:130,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"실결제금액|부가세", 				Type:"Text",		SaveName:"vatAmt",			MinWidth:130,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"실결제금액|결제금액", 				Type:"Float",		SaveName:"actPymnt",		MinWidth:130,	Align:"Center", 	Edit:0, Format:"#,##0원", Sort:0}
						, {Header:"정산금액|정산금액", 				Type:"Float",		SaveName:"excclcAmt",		MinWidth:130,	Align:"Center", 	Edit:0, Format:"#,##0원", Sort:0}
						, {Header:"정산확정여부|정산확정여부", 			Type:"Text",		SaveName:"excclcDcsnYn",	MinWidth:130,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"정산일자|정산일자", 				Type:"Text",		SaveName:"excclcDtm",		MinWidth:130,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"최종수정자|최종수정자", 			Type:"Text",		SaveName:"moderName",		MinWidth:130,	Align:"Center", 	Edit:0, Sort:0}
						, {Header:"최종수정일시|최종수정일시", 			Type:"Text",		SaveName:"finalModDtm",		MinWidth:130,	Align:"Center", 	Edit:0, Sort:0}
						  			
						];
		// Grid 초기화
		IBS_InitSheet(penltySheet, initSheet5);
		// Grid 건수 정보 표시
		penltySheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		penltySheet.SetPagingPosition(2);
		// Grid width 자동 조절
		//sellAmtSheet.FitColWidth("12|8|8|8|8|8|8|8|8|8|8|8");
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		penltySheet.SetExtendLastCol(1);

	}
	/**
	 * 패널티 검색이 종료된후  로우단위 상태 체크
	 */
	penltySheet_OnRowSearchEnd = function (row, col, value){
		var excclcYm = penltySheet.GetRowData(row).excclcYm;
		var info = {Type: "Float", Align: "Center", Format:"#,##0원"};
		var infoPer = {Type: "Float", Align: "Center", Format:"\\%"};
		if(excclcYm == "합계"){
			penltySheet.SetCellValue(row,10,"");
			penltySheet.SetCellValue(row,11,"");
			penltySheet.SetCellValue(row,12,"");
			penltySheet.SetCellValue(row,13,"");
			penltySheet.SetCellValue(row,14,"");
			penltySheet.SetCellValue(row,19,"");
		}else{
			//penltySheet.InitCellProperty(row,11,"");
			penltySheet.InitCellProperty(row,11,infoPer);
			penltySheet.InitCellProperty(row,12,info);
			penltySheet.InitCellProperty(row,13,info);
			penltySheet.InitCellProperty(row,14,info);
		}
	}

	/**
	 * 패널티 검색이 종료된후 실행 
	 */
	penltySheet_OnSearchEnd = function(Code, Msg, StCode, StMsg, Response){
		var frozenRow 	= penltySheet.GetFrozenRows();
		var currentPage = penltySheet.GetCurrentPage();
		// 필요없는 '합계'행을 삭제하는 작업
		if(frozenRow > 0 && currentPage == 1){
			for(i=0; i<=penltySheet.SearchRows(); i++){
				var saveName = penltySheet.GetCellValue(i, "excclcYm");
				
				if(saveName == "합계"){
					penltySheet.RowDelete(i, 0);		
				}
			}
		}
		if(penltySheet.RowCount() > 1){
			$('#excel5').removeClass("disabled");
		}else{
			$('#excel5').addClass("disabled");
		}
		
		penltySheet.SetRowBackColor(2, '#e2f5ff');
		penltySheet.SetFrozenRows(1);
	}
	
	/**
	 * 월정산, 판매수수료 , 배송비, 프로모션비용, 패널티 엑셀다운로드 실행함수 
	 */
	_settlementmonthVndr.event = function(){
		/**
		 * 엑셀 다운로드
		 */
		$('#excel1, #excel2, #excel3, #excel4, #excel5').on("click", function(){
			var tabGubun 	= $('#tabGubun').val();
			console.log(tabGubun);
			if(tabGubun == null || tabGubun == ""){
				console.log(tabGubun);
				$('.tab-item').eq(1).trigger("click");
				console.log(tabGubun);
			}
			
			var allChk =  $('#chkdeviceTypeAll').prop('checked');  
			if(allChk){
				$('#salesCnclGbnType').val('all');
			}else{
				$("input[name=deviceTypeCodeArr]:checked").each(function() {
					var checkBoxSel = $(this).val();
					if(checkBoxSel == "order"){
						$('#salesCnclGbnType').val('order');
						console.log("order");
					}else if(checkBoxSel == "return"){
						$('#salesCnclGbnType').val('return');
						console.log("return");
					}
				});
			}

			gForm.action = "/settlement/settlement-list-excel";
			gForm.submit();
			
			
		});

		/**
		 * 검색
		 */
		$('#gFormSearch').on("click", function(){
			_settlementmonthVndr.getList();
		});

		/**
		 * 초기화
		 */
		$('#gFormReset').on("click", function(){
			$.form("#gForm").reset();
		
		});

		/**
		 *  주문/클레임 체크하기 컨트롤
		 */
		$('#chkdeviceTypeAll').on("click", function(){
			var checkFlag 	= $(this).is(":checked");
			$("[id^=chkdeviceType0]").each(function(){
				$(this).prop('checked', checkFlag);
			});
		});

		/**
		 * 주문/클레임 유형  체크박스 컨트롤
		 */
		$('[id^=chkdeviceType0]').on("click", function(){
			var checkFlag 	= true;

			$("[id^=chkdeviceType0]").each(function(){
				checkFlag = $(this).is(":checked");
				if(!checkFlag){
					return false;
				}
			});
			$('#chkdeviceTypeAll').prop('checked', checkFlag);
		});
		
		
		
		/**
		 *  정산확여부  전체 체크하기 컨트롤
		 */
		$('#chkCalceAll').on("click", function(){
			var checkFlag 	= $(this).is(":checked");
			
			$("[id^=chkCalce0]").each(function(){
				$(this).prop('checked', checkFlag);
			});
		});

		/**
		 * 정산확여부 
		 */
		$('[id^=chkCalce0]').on("click", function(){
			var checkFlag 	= true;

			$("[id^=chkCalce0]").each(function(){
				checkFlag = $(this).is(":checked");
				if(!checkFlag){
					return false;
				}
			});
			$('#chkCalceAll').prop('checked', checkFlag);
		});
		
		
		/**
		 * 월정산, 판매수수료 , 배송비, 프로모션비용, 패널티 탭 클릭 이벤트 발생시 
		 */
		$('#month, #sellAmt,#dlvyAmt,#promoAmt,#penlty').on("click", function(){
			if(this.id == 'month'){
				$("[id^=chkCalce]").prop("disabled", true);
				$('[id^=chkdeviceType]').prop("disabled", true);
				$(".month").prop("disabled", true); 
				$("#btnBrand").addClass("disabled");
				$('#tabGubun').val('1');
				_settlementmonthVndr.monthOptionRander();
			}else{
				if(this.id == 'sellAmt'){
					$('#tabGubun').val('2');
					$('#sellExcclcDcsnNCount').text('0건');
					$('#sellExcclcDcsnNAmt').text('0원');
					$('#sellExcclcDcsnYCount').text('0건');
					$('#sellExcclcDcsnYAmt').text('0원');
				}else if(this.id == 'dlvyAmt'){
					$('#tabGubun').val('3');
					$('#dlvyExcclcDcsnNCount').text('0건');
					$('#dlvyExcclcDcsnNAmt').text('0원');
					$('#dlvyExcclcDcsnYCount').text('0건');
					$('#dlvyExcclcDcsnYAmt').text('0원');
				}else if(this.id == 'promoAmt'){
					$('#tabGubun').val('4');
					$('#promoExcclcDcsnNCount').text('0건');
					$('#promoExcclcDcsnNAmt').text('0원');
					$('#promoExcclcDcsnYCount').text('0건');
					$('#promoExcclcDcsnYAmt').text('0원');
				}else if(this.id == 'penlty'){
					$('#tabGubun').val('5');
					$('#penltyRate').text('0%');
					$('#dlvyDay').text('일');
					$('#penltyOccur').text('0%');
					$('#penltyStandard').text('0%');
					$('#penltyExcclcDcsnNCount').text('0건');
					$('#penltyExcclcDcsnNAmt').text('0원');
					$('#penltyExcclcDcsnYCount').text('0건');
					$('#penltyExcclcDcsnYAmt').text('0원');
				}
				$("[id^=chkCalce]").prop("disabled", false);
				$('[id^=chkdeviceType]').prop("disabled", false);
				$(".month").prop("disabled", false); 
				$("#btnBrand").removeClass("disabled"); 
			}
		});
		
		
		$('.tab-item').each(function(i) {
			if(i == 0) {
				if($(this).hasClass('ui-state-active')){
					$("[id^=chkCalce]").prop("disabled", true);
					$('[id^=chkdeviceType]').prop("disabled", true);
					$(".month").prop("disabled", true); 
					$("#btnBrand").addClass("disabled");
					$('#tabGubun').val('1');
				}
			}
		});
		
		
		/**
		 * 당월 강제 클릭 이벤트
		 */
		$('#currMonth').trigger("click");
		
		/**
		 * 이벤트 바인딩
		 */
		_settlementmonthVndr.orderNoSelect();
		
		/**
		 * 월정산 탭에 해당하는 정산내역 조회 변수 
		 */
		_settlementmonthVndr.monthOptionRander();

		/**
		 * 월정산, 판매수수료 , 배송비, 프로모션비용, 패널티 검색시 페이지 조회번호 
		 */
		$('#pageCount1, #pageCount2,#pageCount3,#pageCount4,#pageCount5').on('change', function(event) {
			var tabGubun 	= $('#tabGubun').val();
			
			if(tabGubun == '1'){
				monthSheet.SetPageCount(this.value);// 그리드의 페이지 개수를 선택한 개수로 조정한다.
			}else if(tabGubun == '2'){
				sellAmtSheet.SetPageCount(this.value);// 그리드의 페이지 개수를 선택한 개수로 조정한다.
			}else if(tabGubun == '3'){
				dlvyAmtSheet.SetPageCount(this.value);// 그리드의 페이지 개수를 선택한 개수로 조정한다.
			}else if(tabGubun == '4'){
				promoAmtSheet.SetPageCount(this.value);// 그리드의 페이지 개수를 선택한 개수로 조정한다.
			}else{
				penltySheet.SetPageCount(this.value);// 그리드의 페이지 개수를 선택한 개수로 조정한다.
			}
			
			_settlementmonthVndr.getList();
		});	
		
	}

	/**
	 * 그리드 데이터 조회
	 */
	_settlementmonthVndr.getList = function(){
		var tabGubun 	= $('#tabGubun').val();
		var month  =$('#month option:selected').val();
		var year = $('#year option:selected').text()
		$('#excclcYm').val(year+month);
		
		if(tabGubun == null || tabGubun == ""){
			$('.tab-item').eq(1).trigger("click");
		}
		
		var allChk =  $('#chkdeviceTypeAll').prop('checked');  
		if(allChk){
			$('#salesCnclGbnType').val('all');
		}else{
			$("input[name=deviceTypeCodeArr]:checked").each(function() {
				var checkBoxSel = $(this).val();
				if(checkBoxSel == "order"){
					$('#salesCnclGbnType').val('order');
				}else if(checkBoxSel == "return"){
					$('#salesCnclGbnType').val('return');
				}
			});
		}
		
		//상품코드 자릿수 체크 
		if($("#orderNoSelect option:selected").val() == "prdtNo"){
			if(abc.text.isLimitLength($("#orderNoInp").val(), 10)){
				alert("상품코드 자리수를 초과하였습니다.");
				$("#orderNoInp").focus();
				return false;
			}
		}
		
		//주문번호  자릿수 체크 
		if($("#orderNoSelect option:selected").val() == "orderNo") {
			if(abc.text.isLimitLength($("#orderNoInp").val(), 13)){
				alert("주문번호 최대 자리수를 초과하였습니다.");
				$("#orderNoInp").focus();
				return false;
			}
		}
		
		var sheet      	= ""; 
		
		if(tabGubun == '1'){
			sheet = "monthSheet";
		}else if(tabGubun == '2'){
			sheet = "sellAmtSheet";
		}else if(tabGubun == '3'){
			sheet = "dlvyAmtSheet";
		}else if(tabGubun == '4'){
			sheet = "promoAmtSheet";
		}else{
			sheet = "penltySheet";
		}
		var serializedFormData = $("#gForm").serialize();
		var param = { url : "/settlement/sellteMent-month-list"
					, onePageRow : $('#pageCount'+tabGubun).val()
					, subparam : serializedFormData
					, sheet : sheet
					};
		
		DataSearchPaging(param);
		// 탭 별로 정산내역 조회 
		_settlementmonthVndr.SettlementHistory(tabGubun);
	}
	
	/**
	 * 탭에 해당하는 정산내역을 조회 
	 */
	_settlementmonthVndr.SettlementHistory = function(tabGubun){
		$.ajax({
	        url: '/settlement/settlement-history',
	        type: 'post',
	        data:  $("#gForm").serialize(),
	        success:function(data){
	        	$('#excclcSeq').val(data.excclcSeq);
				// 년월에 데이타가 없을 경우 
	        	if(data.noData == 'noData'){
	        		if(tabGubun == '1'){
	        			var dataParam = {
	        					penltyLevyRate :'0',
	        					penltyLevyRateStandard :'0',
	        					penltyOcrncRate :'0',
	        					dlvyDayCount :'0',
	        					pymntCount :'0',
	        					pymntAmt :'0',
	        					dlvyAmtCount :'0',
	        					dlvyMonthAmt :'0',
	        					sellCmsnCount :'0',
	        					sellCmsnAmt :'0',
	        					promoCount :'0',
	        					promoMonthAmt :'0',
	        					penltyCount :'0',
	        					penltyAmt :'0',
	        					penltyMdatAmt :'0',
	        					excclcAmt :'0',
	        					excclcMdatAmt :'0'
		        		}
		        		monthOption = dataParam;
		        		_settlementmonthVndr.monthOptionRander();
	        		}else if(tabGubun == '2'){
	    					$('#tabGubun').val('2');
	    					$('#sellExcclcDcsnNCount').text('0건');
	    					$('#sellExcclcDcsnNAmt').text('0원');
	    					$('#sellExcclcDcsnYCount').text('0건');
	    					$('#sellExcclcDcsnYAmt').text('0원');
	        		}else if(tabGubun == '3'){
	    					$('#dlvyExcclcDcsnNCount').text('0건');
	    					$('#dlvyExcclcDcsnNAmt').text('0원');
	    					$('#dlvyExcclcDcsnYCount').text('0건');
	    					$('#dlvyExcclcDcsnYAmt').text('0원');
	        		}else if(tabGubun == '4'){
	    					$('#promoExcclcDcsnNCount').text('0건');
	    					$('#promoExcclcDcsnNAmt').text('0원');
	    					$('#promoExcclcDcsnYCount').text('0건');
	    					$('#promoExcclcDcsnYAmt').text('0원');
	        		}else if(tabGubun == '5'){
	    					$('#penltyRate').text('0%');
	    					$('#dlvyDay').text('일');
	    					$('#penltyOccur').text('0%');
	    					$('#penltyStandard').text('0%');
	    					$('#penltyExcclcDcsnNCount').text('0건');
	    					$('#penltyExcclcDcsnNAmt').text('0원');
	    					$('#penltyExcclcDcsnYCount').text('0건');
	    					$('#penltyExcclcDcsnYAmt').text('0원');
	    			}
	        	// 데이타가 있을경우 
	        	}else{
		        	if(tabGubun == '1'){
		        		var dataParam = {
				        		penltyLevyRate : data.seExactCalculationData.penltyLevyRate,
					        	penltyLevyRateStandard: data.seExactCalculationData.penltyLevyRateStandard,
					        	penltyOcrncRate : data.seExactCalculationData.penltyOcrncRate,
					        	dlvyDayCount : data.seExactCalculationData.dlvyDayCount,
					        	pymntCount : data.seExactCalculationData.pymntCount,
					        	pymntAmt : data.seExactCalculationData.pymntAmt,
					        	dlvyAmtCount : data.seExactCalculationData.dlvyAmtCount,
					        	dlvyMonthAmt : data.seExactCalculationData.dlvyAmt,
					        	sellCmsnCount : data.seExactCalculationData.sellCmsnCount,
					        	sellCmsnAmt : data.seExactCalculationData.sellCmsnAmt,
					        	promoCount : data.seExactCalculationData.promoCount,
					        	promoMonthAmt : data.seExactCalculationData.promoAmt,
					        	penltyCount : data.seExactCalculationData.penltyCount,
					        	penltyAmt : data.seExactCalculationData.penltyAmt,
					        	penltyMdatAmt : data.seExactCalculationData.penltyMdatAmt,
					        	excclcAmt : data.seExactCalculationData.excclcAmt,
					        	excclcMdatAmt: data.seExactCalculationData.excclcMdatAmt
		        		}
		        		monthOption = dataParam;
		        		_settlementmonthVndr.monthOptionRander();
		        		
		        	}else if(tabGubun == '2'){
		        		$('#sellExcclcDcsnNCount').text(data.seExactCalculationData.sellCmsnCountN+"건");
		        		$('#sellExcclcDcsnNAmt').text(data.seExactCalculationData.sellCmsnAmtN+"원");
		        		$('#sellExcclcDcsnYCount').text(data.seExactCalculationData.sellCmsnCountY+"건");
		        		$('#sellExcclcDcsnYAmt').text(data.seExactCalculationData.sellCmsnAmtY+"원");
		        	}else if(tabGubun == '3'){
		        		$('#dlvyExcclcDcsnNCount').text(data.seExactCalculationData.dlvyAmtCountN+"건");
		        		$('#dlvyExcclcDcsnNAmt').text(data.seExactCalculationData.dlvyAmtN+"원");
		        		$('#dlvyExcclcDcsnYCount').text(data.seExactCalculationData.dlvyAmtCountY+"건");
		        		$('#dlvyExcclcDcsnYAmt').text(data.seExactCalculationData.dlvyAmtY+"원");
		        	}else if(tabGubun == '4'){
		        		$('#promoExcclcDcsnNCount').text(data.seExactCalculationData.promoCountN+"건");
		        		$('#promoExcclcDcsnNAmt').text(data.seExactCalculationData.promoAmtN+"원");
		        		$('#promoExcclcDcsnYCount').text(data.seExactCalculationData.promoCountY+"건");
		        		$('#promoExcclcDcsnYAmt').text(data.seExactCalculationData.promoAmtY+"원");
		        	}else if(tabGubun == '5'){
		        		$('#penltyRate').text(data.seExactCalculationData.penltyLevyRate+"%");
		        		$('#dlvyDay').text(data.seExactCalculationData.dlvyDayCount+"건");
		        		$('#penltyOccur').text(data.seExactCalculationData.penltyOcrncRate+"%");
		        		$('#penltyStandard').text(data.seExactCalculationData.penltyLevyRate+"%");
		        		$('#penltyExcclcDcsnNCount').text(data.seExactCalculationData.penltyCountN+"건");
		        		$('#penltyExcclcDcsnNAmt').text(data.seExactCalculationData.penltyAmtN+"원");
		        		$('#penltyExcclcDcsnYCount').text(data.seExactCalculationData.penltyCountY+"건");
		        		$('#penltyExcclcDcsnYAmt').text(data.seExactCalculationData.penltyAmtY+"원");
		        	}
		        	abc.text.isMakeCommaForAll($('.pr'));
	        	}
	        },
	        error:function(e){
	            console.log(e);
	            alert('잠시후 다시 시도해주세요.');
	        }
	    });
	}
	



	
	/**
	 * 전월 
	 */
	$('#preMonth').on("click", function(){
		var currDay = new Date();
		var month = currDay.getMonth()+1;
		var year = currDay.getFullYear()+1;
		var yearIndex = $("#year option").index($("#year option:selected"));
		var monthIndex = $("#month option").index($("#month option:selected"));
		
		if($('#month option:selected').text() == 12){
			$("#month option:eq("+(monthIndex-1)+")").prop("selected", true);
			$("#month option:eq("+(monthIndex)+")").prop("selected", false);
		}else if($('#month option:selected').text() == 1){
			if(yearIndex == 0){
				
				$("#year option:eq("+(yearIndex)+")").prop("selected", true);
				$("#month option:eq("+(monthIndex-1)+")").prop("selected", true);
				$("#month option:eq("+(monthIndex)+")").prop("selected", false);
			}else{
				$("#year option:eq("+(yearIndex-1)+")").prop("selected", true);
				$('#month option:last').prop('selected',true);
			}
		}else{
			// 전월로 selectBox 를 바꿈. 
			$("#month option:eq("+(monthIndex-1)+")").prop("selected", true);
			$("#month option:eq("+(monthIndex)+")").prop("selected", false);
		}
	});
	
	
	/**
	 * 당월 
	 */
	$('#currMonth').off().on("click", function(){
		var currDay = new Date();
		var month = currDay.getMonth()+1;
		var year = currDay.getFullYear();
		
		
		$("#year").val(year);
		if(String(month).length > 1){
			$("#month").val(month);
			
		}else{
			$("#month").val("0"+month);
			
		}
		
	});
	
	/**
	 * 검색어 
	 */
	_settlementmonthVndr.orderNoSelect = function(){
		
		//상품번호  및 주문번호 selectBox 선택 시
		$("#orderNoSelect").change(function(){
			$("#orderNoInp").off();
			$("#orderNoInp").val("");
			// 상품번호 검색할 때
			if($("#orderNoSelect option:selected").val() == "prdtNo"){
				$("#orderNoInp").attr("name", "prdtNo").on("keyup", function() {
					$(this).val($(this).val().replace(/[^0-9]/g,""));
				});
			} 
				// 주문번호로 검색할 때 
			else if($("#orderNoSelect option:selected").val() == "orderNo") {
				$("#orderNoInp").attr("name", "orderNo").on("keyup", function() {
					$(this).val($(this).val().replace(/[^0-9]/g,""));
				});
			}
		});
	}
	
	
	/**
	 * 브랜드 selectBox 선택 시
	 */
		
	$("#btnBrand").click(function(){
		
		abc.brandSearchPopup(false,"abc.biz.settlement.month.main.vendor.setSelectedBrand");
	});
	
	/**
	 * 브랜드 찾기 callback 이벤트
	 */
	_settlementmonthVndr.setSelectedBrand = function(data) {
		$("#brandNo").val(data[0].brandNo);
		$("#brandName").val(data[0].brandName);
	}
	
	
	
	/**
	 * 즉시 실행 함수
	 */
	$(function(){
		_settlementmonthVndr.sheetInit1();
		_settlementmonthVndr.sheetInit2();
		_settlementmonthVndr.sheetInit3();
		_settlementmonthVndr.sheetInit4();
		_settlementmonthVndr.sheetInit5();
		_settlementmonthVndr.event();
	});
	
	
	/**
	 * 월 정산 탭에 해당하는 금액 및 건수 정보 출력 
	 */
	_settlementmonthVndr.monthOptionRander = function(){
		var th = monthOption;
		$('#penltyLevyRate').text(th.penltyLevyRate);
		$('#penltyLevyRateStandard').text(th.penltyLevyRateStandard);
		$('#penltyOcrncRate').text(th.penltyOcrncRate);
		$('#dlvyDayCount').text(th.dlvyDayCount);
		$('#pymntCount').text(th.pymntCount);
		$('#pymntAmt').text(th.pymntAmt);
		$('#dlvyAmtCount').text(th.dlvyAmtCount);
		$('#dlvyMonthAmt').text(th.dlvyMonthAmt);
		$('#sellCmsnCount').text(th.sellCmsnCount);
		$('#sellCmsnAmt').text(th.sellCmsnAmt);
		$('#promoCount').text(th.promoCount);
		$('#promoMonthAmt').text(th.promoMonthAmt);
		$('#penltyCount').text(th.penltyCount);
		$('#penltyAmt').text(th.penltyAmt);
		$('#penltyMdatAmt').text(th.penltyMdatAmt);
		$('#excclcAmt').text(th.excclcAmt);
		$('#excclcMdatAmt').text(th.excclcMdatAmt);
	}
	
	
	

})();
$(document).ready(function(){
	//$('#currMonth').trigger("click");
});

