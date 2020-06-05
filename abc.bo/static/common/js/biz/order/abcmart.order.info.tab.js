/***
 *  주문 정보 tab 업무와 관련된 함수 정의.
 * 
 */
(function() {
	
	var _orderInfo = abc.object.createNestedObject(window,"abc.biz.order.info.tab"); 
	
	
	/*************************************************************************
	 *								ABC 자사 상품 그리드 영역 s
	 *************************************************************************/
	
	/**
	 * ABC 자사 상품 초기 세팅
	 */
	_orderInfo.initOrderABCProductGrid = function() {
		// 그리드 객체, 메모리 초기화
		if(typeof orderABCProductSheet != 'undefined'){
	        orderABCProductSheet.Reset();
	        orderABCProductSheet.DisposeSheet();
	        
		}
		if(typeof orderCompanyProductSheet != 'undefined'){
			orderCompanyProductSheet.Reset();
			orderCompanyProductSheet.DisposeSheet();
		}
		if(typeof orderDiscountInfoSheet != 'undefined'){
			orderDiscountInfoSheet.Reset();
			orderDiscountInfoSheet.DisposeSheet();
		}
		if(typeof orderPaymentInfoSheet != 'undefined'){
			orderPaymentInfoSheet.Reset();
			orderPaymentInfoSheet.DisposeSheet();
		}
		
		//
		createIBSheet2(document.getElementById("orderABCProductGrid"), "orderABCProductSheet", "100%", "370px");
		
		// Grid 초기화 기본/헤더/컬럼선언
		var initSheet = {};

		initSheet.Cfg = {SearchMode:smLazyLoad, MaxSort:1 , MergeSheet:msHeaderOnly};
		initSheet.HeaderMode = {Sort:0, ColMove:0, ColResize:1, HeaderCheck:1};	//2020.05.11 : 전체 체크박스 허용
		initSheet.Cols = [
			  {Header:"" ,		 		Type:"Status",		SaveName:"status",					Width: 0,  	Align:"", 			Edit:0, Hidden:1}
			, {Header:"",				Type:"CheckBox",	SaveName:"updateCheck",				Width: 40,  Align:"Center",		Edit:1, Sort:0}
			, {Header:"주문상품\n순번",		Type:"Text",		SaveName:"orderPrdtSeq",			Width: 60, 	Align:"Center", 	Edit:0, Hidden:0}
			, {Header:"주문번호", 			Type:"Text",		SaveName:"orderNo",					Width: 90, 	Align:"Center", 	Edit:0, Hidden:0}
			, {Header:"상품코드", 			Type:"Text",		SaveName:"prdtNo",					Width: 90, 	Align:"Center", 	Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer"}
			, {Header:"상품명",			Type:"Image",		SaveName:"imageUrl",				Width: 50,	MinWidth:50,		Align:"Center",	Edit:0, ImgWidth: 50}
			, {Header:"상품명", 			Type:"Html",		SaveName:"prdtName",				Width: 150, Align:"Left", 		Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer"}
			, {Header:"옵션", 			Type:"Text",		SaveName:"optnName",				Width: 60,	Align:"Center", 	Edit:0}
			, {Header:"옵션", 			Type:"Button",		SaveName:"optnChange",				Width: 60, 	Align:"Center", 	DefaultValue:"옵션변경"}
			, {Header:"옵션번호", 			Type:"Text",		SaveName:"prdtOptnNo",				Width: 0, 	Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"스타일정보",			Type:"Text",		SaveName:"styleInfo",				Width: 80, 	Align:"Center", 	Edit:0}
			, {Header:"컬러", 			Type:"Text",		SaveName:"prdtColorInfo",			Width: 60,	Align:"Center", 	Edit:0}
			, {Header:"결제수량", 			Type:"Text",		SaveName:"orderQty",				Width: 60, 	Align:"Center", 	Edit:0}
			, {Header:"발송처", 			Type:"Combo",		SaveName:"stockGbnCode",			Width: 70, 	Align:"Center", 	Edit:0}
			, {Header:"매장명", 			Type:"Text",		SaveName:"storeName",				Width: 140, Align:"Center", 	Edit:0}
//			, {Header:"배송ID", 			Type:"Text",		SaveName:"orderDlvyHistSeq",		Width: 100, Align:"Center", 	Edit:0}
			, {Header:"배송ID", 			Type:"Text",		SaveName:"dlvyIdText",				Width: 120, 	Align:"Center", 	Edit:0}
			, {Header:"정상가", 			Type:"Float",		SaveName:"prdtNormalAmt",			Width: 70, 	Align:"Right", 		Edit:0, Format: "#,##0원"}
			, {Header:"판매가", 			Type:"Float",		SaveName:"prdtSellAmt",				Width: 70, 	Align:"Right", 		Edit:0, Format: "#,##0원"}
			, {Header:"옵션추가금액", 		Type:"Float",		SaveName:"optnAddAmt",				Width: 70, 	Align:"Right", 		Edit:0, Format: "#,##0원"}
			, {Header:"임직원할인율", 		Type:"Text",		SaveName:"empDscntRate",			Width: 0, 	Align:"Center", 	Edit:0 , Hidden:1}
			, {Header:"임직원가", 			Type:"Float",		SaveName:"empAmt",					Width: 70, 	Align:"Right", 		Edit:0, Format: "#,##0원", Hidden:1}
			, {Header:"할인금액", 			Type:"Float",		SaveName:"totalDscntAmt",			Width: 70, 	Align:"Right", 		Edit:0, Format: "#,##0원"}
			, {Header:"쿠폰할인", 			Type:"Float",		SaveName:"cpnDscntAmt",				Width: 70, 	Align:"Right", 		Edit:0, Format: "#,##0원"}
			, {Header:"주문금액", 			Type:"Float",		SaveName:"orderAmt",				Width: 70, 	Align:"Right", 		Edit:0, Format: "#,##0원"}
			, {Header:"주문배송상태", 		Type:"Html",		SaveName:"orderPrdtStatCodeName",	Width: 160, Align:"Center", 	Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer"}
			, {Header:"주문배송상태", 		Type:"Text",		SaveName:"orderPrdtStatCode",		Width: 80, 	Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"주문배송상태", 		Type:"Combo",		SaveName:"dlvyStatCode",			Width: 90, 	Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"클레임", 			Type:"Combo",		SaveName:"clmGbnCode",				Width: 80, 	Align:"Center", 	Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer"}
			, {Header:"택배사", 			Type:"Combo",		SaveName:"logisVndrCode",			Width: 80, 	Align:"Center", 	Edit:0}
			, {Header:"송장번호", 			Type:"Text",		SaveName:"waybilNoText",			Width: 110, Align:"Center", 	Edit:0}
			, {Header:"배송분실처리", 		Type:"Text",		SaveName:"missProcTypeCodeName",	Width: 90, 	Align:"Center", 	Edit:0}
			, {Header:"배송중단재배송", 		Type:"Combo",		SaveName:"dlvyDscntcRsnCode",		Width: 70, 	Align:"Center" }
			, {Header:"배송중단재배송", 		Type:"Button",		SaveName:"dlvyDscntcBtn",			Width: 100, Align:"Center", 	DefaultValue:"배송중단처리"}
			, {Header:"배송중단재배송", 		Type:"Button",		SaveName:"reDeliveryBtn",			Width: 100, Align:"Center", 	DefaultValue:"재배송처리" }
			//, {Header:"배송중단재배송", 		Type:"Date",		SaveName:"dlvyDscntcProcDtm",		Width: 70, 	Align:"Center", 	Format:"YmdHms" , Hidden:1 }
			, {Header:"차수", 			Type:"Text",		SaveName:"chasu",					Width: 80, 	Align:"Center", 	Edit:0}
			, {Header:"1차드랍사유", 		Type:"Text",		SaveName:"dropReason1",				Width: 80, 	Align:"Center", 	Edit:0}
			, {Header:"2차드랍사유", 		Type:"Text",		SaveName:"dropReason2",				Width: 80, 	Align:"Center", 	Edit:0}
			, {Header:"사후적립인증번호", 	Type:"Text",		SaveName:"crtfcNoText",				Width: 110, Align:"Center", 	Edit:0}
			, {Header:"구매확정일자", 		Type:"Html",		SaveName:"buyDcsnDtm",				Width: 80, 	Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"배송중단여부", 		Type:"Text",		SaveName:"dlvyDscntcYn",			Width: 80, 	Align:"Center", 	Edit:0, Hidden:0}
			, {Header:"배송처리일시", 		Type:"Text",		SaveName:"dlvyProcDtm",				Width: 80, 	Align:"Center", 	Edit:0, Hidden:1, Format:"Ymd"}
			, {Header:"매장번호", 			Type:"Text",		SaveName:"storeNo",					Width: 80, 	Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"매출구분코드", 		Type:"Text",		SaveName:"salesCnclGbnType",		Width: 80, 	Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"사은품상품번호", 		Type:"Text",		SaveName:"giftPrdtNo",				Width: 80, 	Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"사은품상품명", 		Type:"Text",		SaveName:"giftPrdtName",			Width: 80, 	Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"클레임번호", 		Type:"Text",		SaveName:"clmNo",					Width: 80, 	Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"택배전환여부", 		Type:"Text",		SaveName:"logisCnvrtYn",			Width: 80, 	Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"픽업연장여부", 		Type:"Text",		SaveName:"pickupExtsnYn",			Width: 80, 	Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"상품유형코드", 		Type:"Text",		SaveName:"prdtTypeCode",			Width: 80, 	Align:"Center", 	Edit:0, Hidden:1}
//			, {Header:"배송IDTEXT", 		Type:"Text",		SaveName:"dlvyIdText",				Width: 80, 	Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"배송IDSEQ", 		Type:"Text",		SaveName:"orderDlvyHistSeq",		Width: 100, Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"픽업가능일ADD", 		Type:"Text",		SaveName:"pickupPsbltYmdAddSeven",	Width: 80, 	Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"배송메모", 			Type:"Text",		SaveName:"memo",					Width: 80, 	Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"내부관리정보",		Type:"Text",		SaveName:"insdMgmtInfoText",		Width: 80, 	Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"원주문번호", 		Type:"Text",		SaveName:"orgOrderNo",				Width: 90, 	Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"사이트NO", 		Type:"Text",		SaveName:"siteNo",					Width: 90, 	Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"as접수여부", 		Type:"Text",		SaveName:"prdtAsAcceptFlag",		Width: 90, 	Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"as접수번호", 		Type:"Text",		SaveName:"asAcceptNo",				Width: 90, 	Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"재고구분코드", 		Type:"Text",		SaveName:"orgStockGbnCode",			Width: 80, 	Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"브랜드명", 			Type:"Text",		SaveName:"brandName",				Width: 90, 	Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"매장픽업가능일", 		Type:"Date",		SaveName:"pickupPsbltYmd",			Width: 80, 	Align:"Center", 	Edit:0, Hidden:1, Format:"yyyyMMdd"}
			, {Header:"예약상품여부", 		Type:"Text",		SaveName:"rsvPrdtYn",				Width: 90, 	Align:"Center", 	Edit:0, Hidden:1}
		];
		
		// Grid 초기화
		IBS_InitSheet(orderABCProductSheet , initSheet);
		// Grid 건수 정보 표시
		orderABCProductSheet.SetCountPosition(4);
		// Grid width 자동 조절
		//orderABCProductSheet.FitColWidth();
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		orderABCProductSheet.SetRowHeightMin(40);
		orderABCProductSheet.SetExtendLastCol(1);
		
		var dlvyDscntcRsnCodeTxt = "선택|"+this.codeCombo.DLVY_DSCNTC_RSN_CODE.text ;
		var dlvyDscntcRsnCodeCode = "|"+this.codeCombo.DLVY_DSCNTC_RSN_CODE.code ;
		
		orderABCProductSheet.InitDataCombo(0 , "stockGbnCode" , this.codeCombo.STOCK_GBN_CODE.text , this.codeCombo.STOCK_GBN_CODE.code  );
		orderABCProductSheet.InitDataCombo(0 , "logisVndrCode" , "|"+this.codeCombo.LOGIS_VNDR_CODE.text , "|"+this.codeCombo.LOGIS_VNDR_CODE.code  );
		//orderABCProductSheet.InitDataCombo(0 , "orderPrdtStatCode" , this.codeCombo.ORDER_PRDT_STAT_CODE.text , this.codeCombo.ORDER_PRDT_STAT_CODE.code  );
		orderABCProductSheet.InitDataCombo(0 , "dlvyDscntcRsnCode" , dlvyDscntcRsnCodeTxt , dlvyDscntcRsnCodeCode  );
		orderABCProductSheet.InitDataCombo(0 , "clmGbnCode" , "|"+this.codeCombo.CLM_GBN_CODE.text , "|"+this.codeCombo.CLM_GBN_CODE.code   );
		orderABCProductSheet.InitDataCombo(0 , "dlvyStatCode" , this.codeCombo.DLVY_STAT_CODE.text , this.codeCombo.DLVY_STAT_CODE.code  );
		
		abc.biz.order.info.tab.ABCProductDoAction("search"); // 조회실행
	}
	
	/**
	 * 관리자 목록 Action관리
	 */
	_orderInfo.ABCProductDoAction = function(sAction){
		switch(sAction) {
			// 조회 
			case "search" :
				$("#mmnyPrdtYn").val('Y'); // 자사 N 업체 Y
				var param = {
						url : "/order/read-order-info-product-list"
						, subparam : FormQueryStringEnc(document.frmSearch)
						, sheet : "orderABCProductSheet"
					};

					DataSearch(param);
					
				break;
			// 미수령갱신기간  
			case "dlvyDate" :
				var param = {
						url : "/order/dlvyDateModify"
							//, subparam : FormQueryStringEnc(document.frmSearch)
							, sheet : "orderABCProductSheet"
				};
				
				DataSave(param);
				break;
				
		}
	}
	
	// 주문배송 상태 팝업 호출   (자사용)
	_orderInfo.productHistory = function(row){
		
		var orderPrdtStatCode 	= orderABCProductSheet.GetCellValue(row, "orderPrdtStatCode");
		var orderNo 			= orderABCProductSheet.GetCellValue(row, "orderNo");
		var orderPrdtSeq 		= orderABCProductSheet.GetCellValue(row, "orderPrdtSeq");
		
		var url = "/order/delivery-history";
		var params = {}
		
		params.orderNo 		=  orderNo;
		params.orderPrdtSeq =  orderPrdtSeq;
		
		abc.open.popup({
			winname :	"상품 배송 이력",
			url 	:	url,
			width 	:	800,  
			height	:	670,    
			params	:	params
		});
		
	}
	
	// 주문배송 상태 팝업 호출 (입점사용)
	_orderInfo.productHistoryCompany = function(row){
		
		var orderPrdtStatCode 	= orderCompanyProductSheet.GetCellValue(row, "orderPrdtStatCode");
		var orderNo 			= orderCompanyProductSheet.GetCellValue(row, "orderNo");
		var orderPrdtSeq 		= orderCompanyProductSheet.GetCellValue(row, "orderPrdtSeq");
		
		var url = "/order/delivery-history";
		var params = {}
		
		params.orderNo 		=  orderNo;
		params.orderPrdtSeq =  orderPrdtSeq;
		
		abc.open.popup({
			winname :	"상품 배송 이력",
			url 	:	url,
			width 	:	800,  
			height	:	670,    
			params	:	params
		});
		
	}
	
	_orderInfo.asAccept = function(row){
		var orderPrdtStatCode 	= orderABCProductSheet.GetCellValue(row, "orderPrdtStatCode");
		var orderNo 			= orderABCProductSheet.GetCellValue(row, "orderNo");
		var orderPrdtSeq 		= orderABCProductSheet.GetCellValue(row, "orderPrdtSeq");
		
		var url = "/afterservice/create-afterservice-detail-pop";
		var params = {}
		
		params.orderNo 		=  orderNo;
		params.orderPrdtSeq =  orderPrdtSeq;
		
		abc.open.popup({
			winname :	"상품 A/S 신청",
			url 	:	url,
			width 	:	1230,  
			height	:	860,    
			params	:	params
		});
	}
	
	/**
	 * AS접수하고 결과값을 전달하여 목록을 새로고침.
	 * 추후에 타겟은 변경
	 */
	_orderInfo.asAcceptComplete = function(data){
		
		if(data.result == 'Y'){
			alert('A/S 접수가 신청되었습니다.');
			_orderInfo.ABCProductDoAction('search');
		}else{
			alert('A/S 접수에 실패하였습니다.');
			_orderInfo.ABCProductDoAction('search');
		}
	}
	
	// 상품 옵션 변경 
	_orderInfo.optionChange = function(ibsheetObject , row , col , strParam){
		
		var dlvyTypeCode = $("#dlvyTypeCode").val(); // 배송 타입 코드
		var orderPrdtStatCode = ibsheetObject.GetCellValue(row, "orderPrdtStatCode");
		
		// 상품준비중 이전 단계 = 결제 완료  입금대기 상태 변경 여부 확인 필요  
		if( orderPrdtStatCode != "10002" ){
			alert("주문배송상태가 결제 완료경우에만 변경가능 합니다.");
			return; 
		}
		
		if( dlvyTypeCode == "10002" ){
			alert("매장 픽업 주문건은 옵션변경이 불가능합니다.");
			return; 
		}
		
		// 해당 상품 전체 제고 확인 체크 
		var orderNo 		=  ibsheetObject.GetCellValue(row, "orderNo");
		var orderPrdtSeq 	=  ibsheetObject.GetCellValue(row, "orderPrdtSeq");
		var layerType 		= "Order";
		
		abc.optionChangePopup(orderNo , orderPrdtSeq , "rtnOptionChange" , strParam , layerType);
	}
	
	// 상품 옵션 변경 저장
	_orderInfo.optionChangeSave = function ( args ){
		
		console.log ("저장" , args);
		
		var url =  "/order/option-change-save";
		var targetGrid = args.toParams.strParam; 
		
		var params = {}
		params.orderNo = args.toParams.orderNo;
		params.orderPrdtSeq = args.toParams.orderPrdtSeq;
		params.prdtOptnNo = args.prdtOptnNo; // 변경할 옵션 
		params.optnName = args.optnName; // 변경할 옵션명 
		
		$.ajax({
			 type 	: "post"
			,data 	: params
			,url	: url
			,async: false
		})
		.done(function(data){
			if ( data.resultCode == "Y"){
				alert( data.resultMsg );
				
				if (targetGrid == "abc"){
					abc.biz.order.info.tab.ABCProductDoAction("search"); // 조회실행
				}else{
					abc.biz.order.info.tab.companyProductDoAction("search"); // 조회실행
				}
				
			}else {
				alert( data.resultMsg );
			}
		})
		.fail(function(e){
			
			console.log("e :" + e);
		});
	}
	
	// 배송 중단 처리 
	_orderInfo.deliveryStop = function(ibsheetObject , row , col , strParam){
		
		var url = "/order/delivery-stop";
		
		var orderNo 			= ibsheetObject.GetCellValue(row, "orderNo");
		var orderPrdtSeq 		= ibsheetObject.GetCellValue(row, "orderPrdtSeq");
		var orderDlvyHistSeq 	= ibsheetObject.GetCellValue(row, "orderDlvyHistSeq");
		var dlvyDscntcRsnCode 	= ibsheetObject.GetCellValue(row, "dlvyDscntcRsnCode");
		var orderPrdtStatCode 	= ibsheetObject.GetCellValue(row, "orderPrdtStatCode");
		var dlvyIdText 			= ibsheetObject.GetCellValue(row, "dlvyIdText");
		var stockGbnCode 		= ibsheetObject.GetCellValue(row, "stockGbnCode");
		var insdMgmtInfoText 	= ibsheetObject.GetCellValue(row, "insdMgmtInfoText");
		
		if (dlvyDscntcRsnCode == "" || dlvyDscntcRsnCode == null){
			alert ("배송중단 사유를 선택하세요.") ;
			return;
		}
		
		if(!confirm('배송 중단 처리를 하시겠습니까?')){
			return;
		}
		
		// 상품 상태 ? 배송 상태 ? 
		if (orderPrdtStatCode == abc.order.consts.ORDER_PRDT_STAT_CODE_COMPLETE ){
		}else if (orderPrdtStatCode == abc.order.consts.ORDER_PRDT_STAT_CODE_PRODUCT_PREPARATION ){
		}else if (orderPrdtStatCode == "10004" || orderPrdtStatCode == "10006" ){ // 상품출고 or 픽업준비완료
			// 2020.03.20 : 매장픽업주문은 '상품출고''픽업준비완료' 상태에서 배송중단처리 가능하게 변경
			var dlvyTypeCode = $("#dlvyTypeCode").val();
			if( dlvyTypeCode != "10002" ){
				alert("주문배송상태가 처리 가능 한 상태가 아닙니다.");
				return; 
			}
		}else {
			alert("주문배송상태가 처리 가능 한 상태가 아닙니다.");
			return; 
		}
		
		var param = {
			  orderNo 	 		: orderNo
			, orderPrdtSeq 		: orderPrdtSeq 
			, orderDlvyHistSeq 	: orderDlvyHistSeq 
			, dlvyDscntcRsnCode : dlvyDscntcRsnCode 
			, dlvyIdText		: dlvyIdText
			, stockGbnCode		: stockGbnCode
			, insdMgmtInfoText	: insdMgmtInfoText
		};
		
		$.ajax({
			type 	: "post"
			,data 	: param
			,url	: url
		})
		.done(function(data){
			
			console.log(" resultMsg " , data.resultMsg); 
			
			if ( data.resultCode == "Y"){
				alert("배송 중단 처리 되었습니다.");
				abc.biz.order.info.tab.ABCProductDoAction("search"); // 조회실행
			}else{
				alert(data.resultMsg);
			}
		})
		.fail(function(e){
			
		    console.log("e :" + e);
		});
	}
	
	// 재배송 처리 
	_orderInfo.reDelivery = function(ibsheetObject , row , col , strParam){
		
		var url = "/delivery/re-delivery-check";
		
		var params = {}
		params.orderNo 		= ibsheetObject.GetCellValue(row, "orderNo");
		params.orderPrdtSeq = ibsheetObject.GetCellValue(row, "orderPrdtSeq");

		$.ajax({
			 type 	: "post"
			,data 	: params
			,url	: url
			,async: false
		})
		.done(function(data){
			if ( data.result == "Y"){
				// popup 호출 
				abc.biz.order.info.tab.reDeliveryPopup(params);
			}else {
				alert("재배송 처리 상태가 아닙니다.");
			}
		})
		.fail(function(e){
			
			console.log("e :" + e);
		});
		
	}
	
	_orderInfo.reDeliveryPopup = function(params){
		var url = "/delivery/re-delivery-pop";
		abc.open.popup({
			winname :	"reDelivery",
			url 	:	url,
			width 	:	600,
			height	:	350,
			params	:	params

		});
	}
	
	$("#makeClaim").click(function(){
		
		var sRow = orderABCProductSheet.FindCheckedRow("updateCheck");
		var selectVal = $("#makeClaimSelect option:selected").val();
		var dlvyTypeCode  = $("#dlvyTypeCode").val(); // 배송유형코드
		var today = abc.text.todayFormat(); // 오늘날짜 (yyyymmdd)
		var orderNo;
		var orgOrderNo;
		
		var orderNos = [];
		var orderPrdtSeqs = [];
		
		if(selectVal == ""){
			alert("선택 항목이 없습니다.");
			return false;
		} 
		
		if(sRow == ""){
			alert("상품을 선택하세요.");
			return false;
		}
		
		// 원 주문 번호
		orgOrderNo = orderABCProductSheet.GetCellValue(1, "orgOrderNo");
			
		// 배열 처리 
		for(var i=1; i <= orderABCProductSheet.RowCount(); i++){
			if(orderABCProductSheet.GetCellValue(i, "updateCheck") == "1"){
				// 주문번호 교차 확인 하여야 함 (교환건 추가로 인한) 
				orderNos.push(orderABCProductSheet.GetCellValue(i, "orderNo"));
				orderPrdtSeqs.push(orderABCProductSheet.GetCellValue(i, "orderPrdtSeq"));
			}	
		}

		//권한 적용 시스템의 배열의 값들 중 다른 값이 존재할 경우 예외처리한다.
		for(var i=0; i < orderNos.length; i++){
			if(i == 0){
				orderNo = orderNos[i];
			}

			if(orderNo != orderNos[i]){
				alert("서로 다른 주문번호 입니다. 다시 선택하십시오.");
				return false;
			}
		}
		
		// 기본적인 validate 체크 
		for(var i=1; i <= orderABCProductSheet.RowCount(); i++){
			
			if(orderABCProductSheet.GetCellValue(i, "updateCheck") == "1"){
				orderPrdtStatCode = orderABCProductSheet.GetCellValue(i, "orderPrdtStatCode" );  // 주문 배송상태 코드 
				clmNo = orderABCProductSheet.GetCellValue(i, "clmNo" );  // 클레임 번호  
				dlvyDscntcYn = orderABCProductSheet.GetCellValue(i, "dlvyDscntcYn" );  // 배송중단여부 
				logisCnvrtYn = orderABCProductSheet.GetCellValue(i, "logisCnvrtYn" );  // 택배전환여부 
				pickupExtsnYn = orderABCProductSheet.GetCellValue(i, "pickupExtsnYn" );  // 픽업연장여부 
				prdtTypeCode = orderABCProductSheet.GetCellValue(i, "prdtTypeCode" );  // 상품유형코드 
				prdtAsAcceptFlag = orderABCProductSheet.GetCellValue(i, "prdtAsAcceptFlag" );  // as 접수 여부
				dlvyProcDtm = orderABCProductSheet.GetCellValue(i, "dlvyProcDtm");
				
				// 배송비 상품 선택의 경우 클레임 적용 제외 ( 체크박스 비활성화되나 체크)
				if (prdtTypeCode == abc.order.consts.PRDT_TYPE_CODE_DELIVERY  ) {
					alert("배송비 상품은 클레임 적용이 불가합니다. \n확인 바랍니다.");
					return;
				}
				
				// 클레임이 있는 건은 선택 불가 처리 확인이 필요함 
				if (clmNo != "" ) {
					alert("클레임이 적용된 상품입니다. 확인 바랍니다.");
					return;
				}
				
				if (selectVal == "cancel") { // 주문 취소 접수 결제 완료 상태 --> 상품준비중 20190918 
					
					//2020.04.29 : 원주문번호에 걸린 클레임중 취소접수된 건들을 조회하여 취소완료전에는 접수 못하도록
					var cancelAcceptCnt = Number($("#cancelAcceptCnt").val());
					if( cancelAcceptCnt > 0 ){
						alert( "먼저 취소접수된 클레임을 취소완료 후, 취소접수가 가능합니다." );
						return;
					}
					
					if(orderPrdtStatCode == abc.order.consts.ORDER_PRDT_STAT_CODE_STAND_BY ) { // 입금대기
					}else if (orderPrdtStatCode == abc.order.consts.ORDER_PRDT_STAT_CODE_COMPLETE ){ // 결제완료
					}else if (orderPrdtStatCode == abc.order.consts.ORDER_PRDT_STAT_CODE_PRODUCT_PREPARATION ){ // 상품준비중
					}else if (orderPrdtStatCode == "10004" || orderPrdtStatCode == "10006" ){ // 상품출고 or 픽업준비완료
						// 2020.03.20 : 매장픽업주문은 '상품출고''픽업준비완료' 상태에서 배송중단처리 되어있을경우 취소 가능하게 변경
						var dlvyTypeCode = $("#dlvyTypeCode").val();
						if( dlvyTypeCode != "10002"){
							alert( "주문 취소 접수 가능 상태가 아닙니다." );
							return;
						}
						if( dlvyDscntcYn == "N" ){
							alert( "매장픽업주문이 상품출고 또는 픽업준비완료 상태라면 배송중단 전에는 주문취소할 수 없습니다." );
							return;
						}
						 
					}else {
						alert( "주문 취소 접수 가능 상태가 아닙니다." );
						return;
					}
					
				}else if (selectVal == "return") { // 반품 배송완료/수령완료
					
					// 2020.05.06 : 재 추가
					if (orderPrdtStatCode != abc.order.consts.ORDER_PRDT_STAT_CODE_DELIVERY_ING
							&& orderPrdtStatCode != abc.order.consts.ORDER_PRDT_STAT_CODE_DELIVERY_FINISH
							&& orderPrdtStatCode != abc.order.consts.ORDER_PRDT_STAT_CODE_BUY_CONFIRM) {
						alert( "배송중, 배송완료 또는 구매확정 상태에서만 반품접수 가능합니다." );
						return;
					}
					
					if(orderPrdtStatCode == abc.order.consts.ORDER_PRDT_STAT_CODE_DELIVERY_ING && dlvyProcDtm == today){
						alert( "반품 접수가 익일부터 가능한 주문 상품입니다." );
						return;
					}
					
					if (prdtAsAcceptFlag == "Y") { // A/S
						alert( "A/S 접수된 상품입니다." );
						return;
					}
					
				}else if (selectVal == "exchange") { // 교환 배송완료/수령완료
					
					// 2020.05.06 : 재 추가
					if (orderPrdtStatCode != abc.order.consts.ORDER_PRDT_STAT_CODE_DELIVERY_ING
							&& orderPrdtStatCode != abc.order.consts.ORDER_PRDT_STAT_CODE_DELIVERY_FINISH
							&& orderPrdtStatCode != abc.order.consts.ORDER_PRDT_STAT_CODE_BUY_CONFIRM) {
						alert( "배송중, 배송완료 또는 구매확정 상태에서만 교환접수 가능합니다." );
						return;
					}
					
					if(orderPrdtStatCode == abc.order.consts.ORDER_PRDT_STAT_CODE_DELIVERY_ING && dlvyProcDtm == today){
						alert( "교환 접수가 익일부터 가능한 주문 상품입니다." );
						return;
					}
					
					if (prdtAsAcceptFlag == "Y") { // A/S
						alert( "A/S 접수된 상품입니다." );
						return;
					}
					
				}else if (selectVal == "lose") {	// 분실 처리 배송중/배송완료 상태 
					if (orderPrdtStatCode == abc.order.consts.ORDER_PRDT_STAT_CODE_DELIVERY_ING ) { // 배송중
					}else if ( orderPrdtStatCode == abc.order.consts.ORDER_PRDT_STAT_CODE_DELIVERY_FINISH ) {// 배송완료
					}else {
						alert( "배송중/배송완료 상태에서 분실처리 가능합니다." );
						return;
					}
					
					if (prdtAsAcceptFlag == "Y") { // A/S
						alert( "A/S 접수된 상품입니다." );
						return;
					}
					
				}else if (selectVal == "dlvyChange") { // 매장 픽업 주문에 한하여 노출 처리 
					if (dlvyTypeCode != abc.order.consts.DLVY_TYPE_CODE_STORE_PICKUP ) {
						alert("매장 픽업 주문건만 택배신청 가능합니다.");
						return; 
					}
					
					if(logisCnvrtYn == "Y"){
						alert("이미 택배전환된 상품입니다. ");
						return; 
					}
					
					//if( orderPrdtStatCode == abc.order.consts.ORDER_PRDT_STAT_CODE_DELIVERY_ING ) { // 배송중
					//}else if( orderPrdtStatCode == abc.order.consts.ORDER_PRDT_STAT_CODE_PICKUP_PREPARATION_FINISH ) { //픽업준비완료
					// 20191017 as-is 기준으로 변경 
					if( orderPrdtStatCode == abc.order.consts.ORDER_PRDT_STAT_CODE_STAND_BY ) { //입금대기
					}else if( orderPrdtStatCode == abc.order.consts.ORDER_PRDT_STAT_CODE_COMPLETE ) { //결제완료
					}else if( orderPrdtStatCode == abc.order.consts.ORDER_PRDT_STAT_CODE_PRODUCT_PREPARATION ) { //상품준비중
					}else if( orderPrdtStatCode == abc.order.consts.ORDER_PRDT_STAT_CODE_PRODUCT_DELIVERY ) { //상품출고
					}else{
						alert("픽업 준비중 또는 픽업준비완료 상태일 경우에만 택배전환이 가능합니다.");
						return; 
					}
					
					if (prdtAsAcceptFlag == "Y") { // A/S
						alert( "A/S 접수된 상품입니다." );
						return;
					}
					
				}else if (selectVal == "dlvyDate") {	// 픽업준비완료 상태 +7일연장 1회
					if (dlvyTypeCode != abc.order.consts.DLVY_TYPE_CODE_STORE_PICKUP ) {
						alert("매장 픽업 주문건만 택배신청 가능합니다.");
						return; 
					}
					
					if ( logisCnvrtYn == "Y") { // 택배 전환이 된 상품일경우 제외 처리 
						alert("택배 전환이된 상품입니다.");
						return; 
					}
					if ( pickupExtsnYn == "Y") { // 택배 전환이 된 상품일경우 제외 처리 
						alert("픽업기간이 연장된 상품입니다.");
						return; 
					}
					
					if( orderPrdtStatCode == abc.order.consts.ORDER_PRDT_STAT_CODE_DELIVERY_ING ) { // 배송중
					}else if( orderPrdtStatCode == abc.order.consts.ORDER_PRDT_STAT_CODE_PICKUP_PREPARATION_FINISH ) { //픽업준비완료
					}else{
						alert("미수령기간갱신이 불가능한 상태의 상품입니다.");
						return; 
					}
					
					if (prdtAsAcceptFlag == "Y") { // A/S
						alert( "A/S 접수된 상품입니다." );
						return;
					}
					
				}else if (selectVal == "asReturn"){
					// 2020.05.07 : 비회원 주문건만 'AS반품접수'가능
					
					if (orderPrdtStatCode != abc.order.consts.ORDER_PRDT_STAT_CODE_DELIVERY_ING
							&& orderPrdtStatCode != abc.order.consts.ORDER_PRDT_STAT_CODE_DELIVERY_FINISH
							&& orderPrdtStatCode != abc.order.consts.ORDER_PRDT_STAT_CODE_BUY_CONFIRM) {
						alert( "배송중 또는 배송완료 또는 구매확정 상태에서만 AS반품접수 가능합니다." );
						return;
					}
					
					if(orderPrdtStatCode == abc.order.consts.ORDER_PRDT_STAT_CODE_DELIVERY_ING && dlvyProcDtm == today){
						alert( "AS반품 접수가 익일부터 가능한 주문 상품입니다." );
						return;
					}
					
					if (prdtAsAcceptFlag == "Y") { // A/S
						alert( "A/S 접수된 상품입니다." );
						return;
					}
				}
			}	
		}
		
		// 2020.02.17 : 무통장 미입금 결제는 전체취소만 가능 : BACKEND 와 동일하게? 확인 필요
		/*
		var mainPymntMeans = $("#mainPymntMeans").val(); // 주결제 결제수단 / 10001 무통장입금
		var mainPymntStat  = $("#mainPymntStat").val(); // 주결제 결제상태 / 10000 입금대기 / 10001 결제완료
		
		if( mainPymntMeans == '10001' && mainPymntStat == '10000') {
			var onlyOrderProductCnt = $("#onlyOrderProductCnt").val(); // 배송비/사은품을 제외한 주문상품 갯수
			var checkedOrderPrdtCnt = 0; // 체크된 주문상품 개수
			
			if( selectVal == "cancel" ) {
				for(var i=1; i <= orderABCProductSheet.RowCount(); i++){
					if(orderABCProductSheet.GetCellValue(i, "updateCheck") == "1"){
						checkedOrderPrdtCnt++;
					}
				}
				
				console.log("onlyOrderProductCnt : " + onlyOrderProductCnt);
				console.log("checkedOrderPrdtCnt : " + checkedOrderPrdtCnt);
				
				// 주문상품 개수가 주문에 물려있는 배송비/사은품을 제외한 주문상품 갯수와 같지 않다면 부분취소이므로
				if( onlyOrderProductCnt != checkedOrderPrdtCnt){
					alert( "미입금된 무통장입금 주문은 전체주문건 주문취소만 가능합니다." );
					return;
				}
			}
		}
		*/
		
		// 2020.02.17 : 에스크로 결제시 부분 취소 못하게 막기 ( 부분 반품 / 부분 분실 취소 등 고려 대상 )
//		var escrApplyYn = $("#escrApplyYn").val(); // 에스크로적용여부
//		
//		if( escrApplyYn == "Y" ) {
//			var onlyOrderProductCnt = $("#onlyOrderProductCnt").val(); // 배송비/사은품을 제외한 주문상품 갯수
//			var checkedOrderPrdtCnt = 0; // 체크된 주문상품 개수
//			
//			if( selectVal == "cancel" ) {
//				for(var i=1; i <= orderABCProductSheet.RowCount(); i++){
//					if(orderABCProductSheet.GetCellValue(i, "updateCheck") == "1"){
//						checkedOrderPrdtCnt++;
//					}
//				}
//				
//				console.log("onlyOrderProductCnt : " + onlyOrderProductCnt);
//				console.log("checkedOrderPrdtCnt : " + checkedOrderPrdtCnt);
//				
//				// 주문상품 개수가 주문에 물려있는 배송비/사은품을 제외한 주문상품 갯수와 같지 않다면 부분취소이므로
//				if( onlyOrderProductCnt != checkedOrderPrdtCnt){
//					alert( "에스크로 적용 결제는 전체주문건 주문취소만 가능합니다." );
//					return;
//				}
//			}
//		}
		
		// 2020.03.20 : 주결제수단이 카드일때, 법인카드인지 여부
//		var mainPymntCardType = $("#mainPymntCardType").val();
//		
//		if( !abc.text.allNull(mainPymntCardType) && mainPymntCardType == "C" ) {
//			var onlyOrderProductCnt = $("#onlyOrderProductCnt").val(); // 배송비/사은품을 제외한 주문상품 갯수
//			var checkedOrderPrdtCnt = 0; // 체크된 주문상품 개수
//			
//			if( selectVal == "cancel" ) {
//				for(var i=1; i <= orderABCProductSheet.RowCount(); i++){
//					if(orderABCProductSheet.GetCellValue(i, "updateCheck") == "1"){
//						checkedOrderPrdtCnt++;
//					}
//				}
//				
//				console.log("onlyOrderProductCnt : " + onlyOrderProductCnt);
//				console.log("checkedOrderPrdtCnt : " + checkedOrderPrdtCnt);
//				
//				// 주문상품 개수가 주문에 물려있는 배송비/사은품을 제외한 주문상품 갯수와 같지 않다면 부분취소이므로
//				if( onlyOrderProductCnt != checkedOrderPrdtCnt){
//					alert( "법인카드로 결제한 주문은 전체취소만 가능합니다." );
//					return;
//				}
//			}
//		}
		
		// 2020.05.07 : 비회원주문건을 위한 AS반품접수 추가
		if ( selectVal == "cancel" || selectVal == "return" || selectVal == "exchange" || selectVal == "asReturn") {
			// validation chk
			var params = {} ;
			var clmGbnCode = "";
			
			if(selectVal == "cancel") {
				clmGbnCode = "10000";
			} else if(selectVal == "exchange") {
				clmGbnCode = "10001";
			} else if(selectVal == "return" || selectVal == "asReturn") { // 2020.05.07 : 비회원주문건을 위한 AS반품접수 추가
				clmGbnCode = "10002";
			}
			
			params.orderNos = orderNos;
			params.orderPrdtSeqs = orderPrdtSeqs; 
			params.orgOrderNo = orgOrderNo;
			params.clmGbnCode = clmGbnCode;
			
			$.ajax({
				type :"post",
				url : "/claim/claim/validate-claim-product",
				async: false, 
				data: params
			})
			.done(function(data){
				if(data.success == abc.consts.BOOLEAN_TRUE){
					abc.biz.order.info.tab.makeClaimSetting(selectVal , orderNos , orderPrdtSeqs, orgOrderNo, clmGbnCode);
				}else{
					alert(data.message) ;
					return;
				}
			})
			.fail(function(e){
				console.log("e :" + e);
				return false;
			});
			
		}else{
			abc.biz.order.info.tab.makeClaimSetting(selectVal , orderNos , orderPrdtSeqs, orgOrderNo, clmGbnCode);
		}
		
	});
	
	/**
	 * 관리자 메모 글자수 체크(30자)
	 */
	_orderInfo.stringLengthCheck = function(obj) {
		var dlvyTypeCode 		= $("#dlvyTypeCode").val();
		
		if (dlvyTypeCode == abc.order.consts.DLVY_TYPE_CODE_NORMAL_LOGISTICS) {
			if(obj.length <= 40){
				$('#dlvyMemoCounter').html(obj.length);
			}else{
				alert("메모는 40자를 초과할 수 없습니다.");
				$("#dlvyMemoText").val(obj.substring(0, 40));
				return false;
			}
		}
	}
	
	
	// 자사 상품 , 입점사 상품 공통 사용
	_orderInfo.makeClaimSetting = function(selectVal , orderNos , orderPrdtSeqs, orgOrderNo, clmGbnCode ){
		var params = {} ;
		
		params.orderNos = orderNos;
		params.orderPrdtSeqs = orderPrdtSeqs; 
		params.orgOrderNo = orgOrderNo;
		params.clmGbnCode = clmGbnCode;
		
		 // 2020.05.07 : 비회원 AS반품접수를 위한 추가 
		if(selectVal == "asReturn"){
			params.asReturnYn = "Y";
		}
		
		var url ; 
		var winname ; 
		var width ; 
		var height ; 
		
		if (selectVal == "cancel") {
			// 결제 완료 
			winname = "cancel" ; 
			width = "1260";
			height = "990"; 
			url = "/claim/claim/create-claim-cancel-form-pop"; 
			
			abc.biz.order.info.tab.makeClaimPop(winname , url , width , height , params);
		}else if (selectVal == "return" || selectVal == "asReturn") { // 2020.05.07 : 비회원 AS반품접수를 위한 추가 
			// 배송완료 / 수령완료 
			winname = "return" ; 
			width = "1260";
			height = "990";
			url = "/claim/claim/create-claim-return-form-pop"; 
			
			abc.biz.order.info.tab.makeClaimPop(winname , url , width , height , params);
		}else if (selectVal == "exchange") {
			// 배송완료 / 수령완료  
			winname = "exchange" ; 
			width = "1260";
			height = "990";
			url = "/claim/claim/create-claim-exchange-form-pop"; 
			
			abc.biz.order.info.tab.makeClaimPop(winname , url , width , height , params);
		}else if (selectVal == "lose") {
			// 배송중 / 배송완료 
			abc.biz.order.info.tab.doDeliveryMiss();
		}else if (selectVal == "dlvyChange") {
			// 매장픽업 주문건만 노출 가능 
			abc.biz.order.info.tab.doDeliveryChange();
			//return;
		}else if (selectVal == "dlvyDate") {
			// 픽업 준비 완료 픽업 완료일 + 7일 연장 가능 , 1회연장 
			abc.biz.order.info.tab.ABCProductDoAction("dlvyDate");
		}
	}
	
	/**
	 *  택배전환 팝업 호출
	 */
	_orderInfo.doDeliveryChange = function(){
		
 		//선택여부 체크 * 형식이 있기에 두형식으로 사용 해봄
		var sRow = orderABCProductSheet.CheckedRows("updateCheck");
 		//선택 데이타 Rows
		var checkRows = orderABCProductSheet.FindCheckedRow("updateCheck");

		if(sRow <1 ){
			alert("처리대상이 선택되지 않았습니다.\n 처리대상을 확인해주세요.");
			return;
		} //end if

		var orderNoAfter = "";
		var dlvyIdText=[];
		var orderPrdtSeq =[];

		//주문번호 중복여부 체크
		var arrRow = checkRows.split("|"); 
		var params = {};  //popup창 params
 
		for (idx=0; idx<arrRow.length; idx++){  
			var orderNo =  orderABCProductSheet.GetCellValue( arrRow[idx], "orderNo"); 
			var logisCnvrtYn = orderABCProductSheet.GetCellValue(i, "logisCnvrtYn" );  // 택배전환여부
			
			if ( logisCnvrtYn == "Y") { // 택배 전환이 된 상품일경우 제외 처리 
				alert("택배 전환이된 상품입니다.");
				return; 
			}
			
			if(orderNoAfter != orderNo && idx !=0 ){
				alert("동일한 주문번호의 상품만 함께 처리가 가능합니다. \n주문번호를 확인해주세요.\n(주문번호:"+orderNo+")");
				return;
			} else{
				dlvyIdText[idx] = orderABCProductSheet.GetCellValue( arrRow[idx] , "dlvyIdText");
				orderPrdtSeq[idx] = orderABCProductSheet.GetCellValue( arrRow[idx] , "orderPrdtSeq");
				params.orderNo = orderNo;  // params 주문번호
				params.callType = "OV";  //호출 유형
				params.callBackFunc = "";  //스크립트 호출 callBack 유형
			}
			orderNoAfter = orderNo;
		}//end for 
		var url = "/delivery/store-pick-delivery-change-popup";
		
		params.dlvyIdText = dlvyIdText;   // params 배송아이디
		params.orderPrdtSeq = orderPrdtSeq;   // 주문상품 순번
	
		abc.open.popup({
			winname :	"StorePickDeliveryChange",
			url 	:	url,
			method	: 	"post",
			width 	:	1000,
			height	:	880,
			params	:	params

		});

	} //end function _deliveryJsObject.doDeliveryChange

	// 클레임 팝업 호출 
	_orderInfo.makeClaimPop = function(winname , url , width , height , params){
		abc.open.popup({
			winname :	winname,
			url 	:	url,
			width 	:	width,
			height	:	height,
			params	:	params

		});
	}
	


	/**
	 *  분실취소 Popup
	 */
	_orderInfo.doDeliveryMiss = function(){
		
 		//선택여부 체크 * 형식이 있기에 두형식으로 사용 해봄
		 var sRow = orderABCProductSheet.CheckedRows("updateCheck");
 		 //선택 데이타 Rows
		var checkRows = orderABCProductSheet.FindCheckedRow("updateCheck");

		 if(sRow <1 ){
			alert("처리대상이 선택되지 않았습니다.\n 처리대상을 확인해주세요.");
			return;
		 } //end if

		var orderNoAfter = "";
		var dlvyIdText=[];
		var orderPrdtSeq =[];

		//주문번호 중복여부 체크
		var arrRow = checkRows.split("|"); 
		var params = {};  //popup창 params
 
		for (idx=0; idx<arrRow.length; idx++){  
			var orderNo =  orderABCProductSheet.GetCellValue( arrRow[idx], "orderNo");
			
			if(orderNoAfter != orderNo && idx !=0 ){
				alert("동일한 주문번호의 상품만 함께 처리가 가능합니다. \n주문번호를 확인해주세요.\n(주문번호:"+orderNo+")");
				return;
			} else{
				 dlvyIdText[idx] = orderABCProductSheet.GetCellValue( arrRow[idx] , "dlvyIdText");
				 orderPrdtSeq[idx] = orderABCProductSheet.GetCellValue( arrRow[idx] , "orderPrdtSeq");
				 params.orderNo = orderNo;  // params 주문번호
				  params.callType = "OV";  //호출 유형
				  params.callBackFunc = "";  //스크립트 호출 callBack 유형
			}
			orderNoAfter = orderNo;
		 }//end for 
		var url = "/delivery/delivery-miss-proc-popup";
		
		params.dlvyIdText = dlvyIdText;   // params 배송아이디
		params.orderPrdtSeq = orderPrdtSeq;   // 주문상품 순번
	
		abc.open.popup({
			winname :	"DeliveryMissProc",
			url 	:	url,
			method	: 	"post",
			width 	:	1000,
			height	:	880,
			params	:	params
		});

	} //end function _deliveryJsObject.doDeliveryChange

 
	// 클레임 팝업 호출 
	_orderInfo.makeClaimPop = function(winname , url , width , height , params){
		abc.open.popup({
			winname :	winname,
			url 	:	url,
			width 	:	width,
			height	:	height,
			params	:	params

		});
	}
	
	// 택배전환 보기 
	// 상품기준이 아닌 주문번호 기준으로 택배 전환 상품 전체 조회 
	$("#pickupDlvyChangeInfo").click(function(){
		var orderNo =  $("#orderNo").val();
		var params = {}
		var url = "/delivery/store-pick-delivery-change-popup";
		
		params.orderNo = orderNo;   //  
		params.callType = "OV"; 
	
		abc.open.popup({
			winname :	"StorePickDeliveryChange",
			url 	:	url,
			method	: 	"post",
			width 	:	1000,
			height	:	880,
			params	:	params
		});
	});
	
	
	
	
	/*************************************************************************
	 *								ABC 자시 상품 그리드 영역 E
	 *************************************************************************/
	
	
	/*************************************************************************
	 *								입점업체 상품 그리드 영역 S
	 *************************************************************************/
	/**
	 * 입점업체 상품 초기 세팅
	 */
	_orderInfo.initOrderCompanyProductGrid = function() {
		//
		createIBSheet2(document.getElementById("orderCompanyProductGrid"), "orderCompanyProductSheet", "100%", "370px");
		
		// Grid 초기화 기본/헤더/컬럼선언
		var initSheet = {};

		initSheet.Cfg = {SearchMode:smLazyLoad, MaxSort:1, MergeSheet:msHeaderOnly};
		initSheet.HeaderMode = {Sort:0, ColMove:0, ColResize:1, HeaderCheck:1};	//2020.05.11 : 전체 체크박스 허용
		initSheet.Cols = [
			  {Header:"" ,		 		Type:"Status",		SaveName:"status",					Width: 0,	Align:"", 		Edit:0, Hidden:1}
			, {Header:"",				Type:"CheckBox",	SaveName:"updateCheck",				Width: 40,	Align:"Center",	Edit:1, Sort:0}
			, {Header:"주문상품\n순번",		Type:"Text",		SaveName:"orderPrdtSeq",			Width: 60, 	Align:"Center", 	Edit:0, Hidden:0}
			, {Header:"주문번호", 			Type:"Text",		SaveName:"orderNo",					Width: 90, 	Align:"Center", 	Edit:0, Hidden:0}
			, {Header:"상품코드", 			Type:"Text",		SaveName:"prdtNo",					Width: 90,	Align:"Center", 	Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer"}
			, {Header:"업체상품코드", 		Type:"Text",		SaveName:"vndrPrdtNoText",			Width: 90,	Align:"Center", 	Edit:0}
			, {Header:"상품명",			Type:"Image",		SaveName:"imageUrl",				Width:80,	MinWidth:100,		Align:"Center",	Edit:0, ImgWidth: 100}
			, {Header:"상품명", 			Type:"Html",		SaveName:"prdtName",				Width: 150,	Align:"Left", Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer"}
			, {Header:"업체코드", 			Type:"Text",		SaveName:"vndrNo",					Width: 90,	Align:"Center", 	Edit:0}
			, {Header:"업체명", 			Type:"Text",		SaveName:"vndrName",				Width: 120,	Align:"Center", Edit:0 , FontBold:1, FontUnderline:1, Cursor:"Pointer"}
			, {Header:"옵션", 			Type:"Text",		SaveName:"optnName",				Width: 60,	Align:"Center", Edit:0}
			, {Header:"옵션", 			Type:"Button",		SaveName:"optnChange",				Width: 60,	Align:"Center", DefaultValue:"옵션변경"}
			, {Header:"스타일정보",			Type:"Text",		SaveName:"styleInfo",				Width: 80, 	Align:"Center", 	Edit:0}
			, {Header:"컬러", 			Type:"Text",		SaveName:"prdtColorInfo",			Width: 60,	Align:"Center", 	Edit:0}
			, {Header:"결제수량", 			Type:"Text",		SaveName:"orderQty",				Width: 60,	Align:"Center", 	Edit:0}
			, {Header:"정상가", 			Type:"Float",		SaveName:"prdtNormalAmt",			Width: 70,	Align:"Right", 	Edit:0, Format: "#,##0원"}
			, {Header:"판매가", 			Type:"Float",		SaveName:"prdtSellAmt",				Width: 70,	Align:"Right", 	Edit:0, Format: "#,##0원"}
			, {Header:"옵션추가금액", 		Type:"Float",		SaveName:"optnAddAmt",				Width: 70, 	Align:"Right", 	Edit:0, Format: "#,##0원"}
			, {Header:"임직원가", 			Type:"Float",		SaveName:"empAmt",					Width: 70,	Align:"Right", 	Edit:0, Format: "#,##0원", Hidden:1}
			, {Header:"할인금액", 			Type:"Float",		SaveName:"totalDscntAmt",			Width: 70,	Align:"Right", 	Edit:0, Format: "#,##0원"}
			, {Header:"쿠폰할인", 			Type:"Float",		SaveName:"cpnDscntAmt",				Width: 70,	Align:"Right", 	Edit:0, Format: "#,##0원"}
			, {Header:"주문금액", 			Type:"Float",		SaveName:"orderAmt",				Width: 70,	Align:"Right", 	Edit:0, Format: "#,##0원"}
			, {Header:"배송ID", 			Type:"Text",		SaveName:"dlvyIdText",				Width: 100,	Align:"Center", Edit:0}
			, {Header:"주문배송상태", 		Type:"Html",		SaveName:"orderPrdtStatCodeName",	Width: 150,	Align:"Center", 	Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer"}
			, {Header:"주문배송상태", 		Type:"Text",		SaveName:"orderPrdtStatCode",		Width: 80,	Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"주문배송상태", 		Type:"Combo",		SaveName:"dlvyStatCode",			Width: 90, 	Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"클레임", 			Type:"Combo",		SaveName:"clmGbnCode",				Width: 80,	Align:"Center", 	Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer"}
			, {Header:"판매취소요청", 		Type:"Combo",		SaveName:"sellCnclRsnCode",			Width: 80,	Align:"Center", 	Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer"}
			, {Header:"택배사", 			Type:"Combo",		SaveName:"logisVndrCode",			Width: 80,	Align:"Center", 	Edit:0}
			, {Header:"송장번호", 			Type:"Text",		SaveName:"waybilNoText",			Width: 110,	Align:"Center", 	Edit:0}
			, {Header:"발송처", 			Type:"Combo",		SaveName:"stockGbnCode",			Width: 70,	Align:"Center", 	Edit:0}
			, {Header:"사후적립인증번호", 	Type:"Text",		SaveName:"crtfcNoText",				Width: 80,	Align:"Center", 	Edit:0}
			, {Header:"매출구분코드", 		Type:"Text",		SaveName:"salesCnclGbnType",		Width: 80,	Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"사은품상품코드", 		Type:"Text",		SaveName:"giftPrdtNo",				Width: 80,	Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"사은품상품명", 		Type:"Text",		SaveName:"giftPrdtName",			Width: 80,	Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"클레임번호", 		Type:"Text",		SaveName:"clmNo",					Width: 80,	Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"상품유형코드", 		Type:"Text",		SaveName:"prdtTypeCode",			Width: 80,	Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"배송IDTEXT", 		Type:"Text",		SaveName:"orderDlvyHistSeq",		Width: 80, 	Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"원주문번호", 		Type:"Text",		SaveName:"orgOrderNo",				Width: 90, 	Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"사이트NO", 		Type:"Text",		SaveName:"siteNo",					Width: 90, 	Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"as접수여부", 		Type:"Text",		SaveName:"prdtAsAcceptFlag",		Width: 90, 	Align:"Center", 	Edit:0}
			, {Header:"구매확정일자", 		Type:"Text",		SaveName:"buyDcsnDtm",				Width: 80, 	Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"as접수번호", 		Type:"Text",		SaveName:"asAcceptNo",				Width: 90, 	Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"브랜드명", 			Type:"Text",		SaveName:"brandName",				Width: 90, 	Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"예약상품여부", 		Type:"Text",		SaveName:"rsvPrdtYn",				Width: 90, 	Align:"Center", 	Edit:0, Hidden:1}
		];
		
		// Grid 초기화
		IBS_InitSheet(orderCompanyProductSheet , initSheet);
		// Grid 건수 정보 표시
		orderCompanyProductSheet.SetCountPosition(4);
		// Grid width 자동 조절
		//orderCompanyProductSheet.FitColWidth();
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		orderCompanyProductSheet.SetRowHeightMin(40);
		orderCompanyProductSheet.SetExtendLastCol(1);
		
		orderCompanyProductSheet.InitDataCombo(0 , "stockGbnCode" , this.codeCombo.STOCK_GBN_CODE.text , this.codeCombo.STOCK_GBN_CODE.code  ); // 입점사 
		orderCompanyProductSheet.InitDataCombo(0 , "logisVndrCode" , "|"+this.codeCombo.LOGIS_VNDR_CODE.text , "|"+this.codeCombo.LOGIS_VNDR_CODE.code  );
		orderCompanyProductSheet.InitDataCombo(0 , "orderPrdtStatCode" , this.codeCombo.ORDER_PRDT_STAT_CODE.text , this.codeCombo.ORDER_PRDT_STAT_CODE.code  );
		orderCompanyProductSheet.InitDataCombo(0 , "clmGbnCode" , this.codeCombo.CLM_GBN_CODE.text , this.codeCombo.CLM_GBN_CODE.code   );
		orderCompanyProductSheet.InitDataCombo(0 , "sellCnclRsnCode" , "|"+this.codeCombo.SELL_CNCL_RSN_CODE.text , "|"+this.codeCombo.SELL_CNCL_RSN_CODE.code   );
		orderCompanyProductSheet.InitDataCombo(0 , "dlvyStatCode" , this.codeCombo.DLVY_STAT_CODE.text , this.codeCombo.DLVY_STAT_CODE.code  );
		
		abc.biz.order.info.tab.companyProductDoAction("search"); // 조회실행
	}
	
	/**
	 * 관리자 목록 Action관리
	 */
	_orderInfo.companyProductDoAction = function(sAction){
		
		switch(sAction) {
			// 조회 
			case "search" :
				$("#mmnyPrdtYn").val('N'); // 자사 Y 업체 N
				var param = {
						url : "/order/read-order-info-product-list"
				 		, subparam : FormQueryStringEnc(document.frmSearch)
						, sheet : "orderCompanyProductSheet"
					};
	
					DataSearch(param);
					
				break;
		}
	}
	
	
	$("#makeClaimForCompany").click(function(){
		
		var sRow = orderCompanyProductSheet.FindCheckedRow("updateCheck");
		var selectVal = $("#makeClaimSelectForCompany option:selected").val();
		var orderNo;
		var orgOrderNo;
		var vndrNo;
		
		var orderNos = [];
		var orderPrdtSeqs = [];
		var vndrNos = [];
		
		if(selectVal == ""){
			alert("선택 항목이 없습니다.");
			return false;
		} 
		
		if(sRow == ""){
			alert("상품을 선택하세요.");
			return false;
		}
		
		// 원 주문 번호
		orgOrderNo = orderCompanyProductSheet.GetCellValue(1, "orgOrderNo");
			
		// 배열 처리 
		for(var i=1; i <= orderCompanyProductSheet.RowCount(); i++){
			if(orderCompanyProductSheet.GetCellValue(i, "updateCheck") == "1"){
				// 주문번호 교차 확인 하여야 함 (교환건 추가로 인한) 
				console.log ("orderNo " , orderCompanyProductSheet.GetCellValue(i, "orderNo"));
				console.log ("orderPrdtSeq " , orderCompanyProductSheet.GetCellValue(i, "orderPrdtSeq"));
				orderNos.push(orderCompanyProductSheet.GetCellValue(i, "orderNo"));
				orderPrdtSeqs.push(orderCompanyProductSheet.GetCellValue(i, "orderPrdtSeq"));
				vndrNos.push(orderCompanyProductSheet.GetCellValue(i, "vndrNo"));
			}	
		}

		//주문번호가 다를 경우 ( 교환 상품 ) 
		for(var i=0; i < orderNos.length; i++){
			if(i == 0){
				orderNo = orderNos[i];
			}

			if(orderNo != orderNos[i]){
				alert("서로 다른 주문번호 입니다. 다시 선택하십시오.");
				return false;
			}
		}
		
		//업체가  다를 경우  
		for(var i=0; i < vndrNos.length; i++){
			if(i == 0){
				vndrNo = vndrNos[i];
			}
			
			if(vndrNo != vndrNos[i]){
				alert("동일한 업체로만 클레임 접수 가능 합니다. 다시 선택하십시오.");
				return false;
			}
		}
		
		// 기본적인 validate 체크 
		for(var i=1; i <= orderCompanyProductSheet.RowCount(); i++){
			if(orderCompanyProductSheet.GetCellValue(i, "updateCheck") == "1"){
				orderPrdtStatCode 	= orderCompanyProductSheet.GetCellValue(i, "orderPrdtStatCode" );  // 주문 배송상태 코드 
				clmNo 				= orderCompanyProductSheet.GetCellValue(i, "clmNo" );  // 클레임 번호  
				prdtTypeCode 		= orderCompanyProductSheet.GetCellValue(i, "prdtTypeCode" );  // 상품유형코드 
				prdtAsAcceptFlag 	= orderCompanyProductSheet.GetCellValue(i, "prdtAsAcceptFlag" );  // as 접수 여부 
				
				// 배송비 상품 선택의 경우 클레임 적용 제외 ( 체크박스 비활성화되나 체크)
				if (prdtTypeCode == abc.order.consts.PRDT_TYPE_CODE_DELIVERY  ) {
					alert("배송비 상품은 클레임 적용이 불가합니다. \n확인 바랍니다.");
					return;
				}
				
				// 클레임이 있는 건은 선택 불가 처리 확인이 필요함 
				if (clmNo != "" ) {
					alert("클레임이 적용된 상품입니다. 확인 바랍니다.");
					return;
				}
				
				if (selectVal == "cancel") { // 주문 취소 접수 결제 완료 상태  
					
					//2020.04.29 : 원주문번호에 걸린 클레임중 취소접수된 건들을 조회하여 취소완료전에는 접수 못하도록
					var cancelAcceptCnt = Number($("#cancelAcceptCnt").val());
					if( cancelAcceptCnt > 0 ){
						alert( "먼저 취소접수된 클레임을 취소완료 후, 취소접수가 가능합니다." );
						return;
					}
					
					if(orderPrdtStatCode == abc.order.consts.ORDER_PRDT_STAT_CODE_STAND_BY ) { // 입금대기
					}else if (orderPrdtStatCode == abc.order.consts.ORDER_PRDT_STAT_CODE_COMPLETE ){ // 결제완료
					//}else if (orderPrdtStatCode == abc.order.consts.ORDER_PRDT_STAT_CODE_PRODUCT_PREPARATION ){ // 상품준비중
					}else{
						alert( "상품준비중 단계까지만 주문취소가 가능합니다." );
						return;
					}
					
				}else if (selectVal == "return") { // 반품 배송완료/수령완료
					
					if (orderPrdtStatCode != abc.order.consts.ORDER_PRDT_STAT_CODE_DELIVERY_FINISH
							&& orderPrdtStatCode != abc.order.consts.ORDER_PRDT_STAT_CODE_BUY_CONFIRM) {
						alert( "배송완료 또는 구매확정 상태에서만 반품접수 가능합니다." );
						return;
					}
					
					if (prdtAsAcceptFlag == "Y") { // A/S
						alert( "A/S 접수된 상품입니다." );
						return;
					}
					
				}else if (selectVal == "exchange") { // 교환 배송완료/수령완료
					if (orderPrdtStatCode != abc.order.consts.ORDER_PRDT_STAT_CODE_DELIVERY_FINISH
							&& orderPrdtStatCode != abc.order.consts.ORDER_PRDT_STAT_CODE_BUY_CONFIRM) {
						alert( "배송완료 또는 구매확정 상태에서만 교환접수 가능합니다." );
						return;
					}
					
					if (prdtAsAcceptFlag == "Y") { // A/S
						alert( "A/S 접수된 상품입니다." );
						return;
					}
					
				}else if (selectVal == "lose") {	// 분실 처리 배송중/배송완료 상태 
					if (orderPrdtStatCode == abc.order.consts.ORDER_PRDT_STAT_CODE_DELIVERY_ING ) { // 배송중
					}else if ( orderPrdtStatCode == abc.order.consts.ORDER_PRDT_STAT_CODE_DELIVERY_FINISH ) {// 배송완료
					}else {
						alert( "배송중/배송완료 상태에서 분실처리 가능합니다." );
						return;
					}
					
					if (prdtAsAcceptFlag == "Y") { // A/S
						alert( "A/S 접수된 상품입니다." );
						return;
					}
					
				}
			}	
		}
		
		// 2020.02.17 : 무통장 미입금 결제는 전체취소만 가능 : BACKEND와 동일하게? 확인필요
		/*
		var mainPymntMeans = $("#mainPymntMeans").val(); // 주결제 결제수단 / 10001 무통장입금
		var mainPymntStat  = $("#mainPymntStat").val(); // 주결제 결제상태 / 10000 입금대기 / 10001 결제완료
		
		if( mainPymntMeans == '10001' && mainPymntStat == '10000') {
			var onlyOrderProductCnt = $("#onlyOrderProductCnt").val(); // 배송비/사은품을 제외한 주문상품 갯수
			var checkedOrderPrdtCnt = 0; // 체크된 주문상품 개수
			
			if( selectVal == "cancel" ) {
				for(var i=1; i <= orderCompanyProductSheet.RowCount(); i++){
					if(orderCompanyProductSheet.GetCellValue(i, "updateCheck") == "1"){
						checkedOrderPrdtCnt++;
					}
				}
				
				console.log("onlyOrderProductCnt : " + onlyOrderProductCnt);
				console.log("checkedOrderPrdtCnt : " + checkedOrderPrdtCnt);
				
				// 주문상품 개수가 주문에 물려있는 배송비/사은품을 제외한 주문상품 갯수와 같지 않다면 부분취소이므로
				if( onlyOrderProductCnt != checkedOrderPrdtCnt){
					alert( "미입금된 무통장입금 주문은 전체주문건 주문취소만 가능합니다." );
					return;
				}
			}
		}
		*/
		
		// 2020.02.17 : 에스크로 결제시 부분 취소 못하게 막기 ( 부분 반품 / 부분 분실 취소 등 고려 대상 )
		var escrApplyYn = $("#escrApplyYn").val(); // 에스크로적용여부
		
		if( escrApplyYn == "Y" ) {
			var onlyOrderProductCnt = $("#onlyOrderProductCnt").val(); // 배송비/사은품을 제외한 주문상품 갯수
			var checkedOrderPrdtCnt = 0; // 체크된 주문상품 개수
			
			if( selectVal == "cancel" ) {
				for(var i=1; i <= orderCompanyProductSheet.RowCount(); i++){
					if(orderCompanyProductSheet.GetCellValue(i, "updateCheck") == "1"){
						checkedOrderPrdtCnt++;
					}
				}
				
				console.log("onlyOrderProductCnt : " + onlyOrderProductCnt);
				console.log("checkedOrderPrdtCnt : " + checkedOrderPrdtCnt);
				
				// 주문상품 개수가 주문에 물려있는 배송비/사은품을 제외한 주문상품 갯수와 같지 않다면 부분취소이므로
				if( onlyOrderProductCnt != checkedOrderPrdtCnt){
					alert( "에스크로 적용 결제는 전체주문건 주문취소만 가능합니다." );
					return;
				}
			}
		}
		
		// 2020.03.20 : 주결제수단이 카드일때, 법인카드인지 여부
//		var mainPymntCardType = $("#mainPymntCardType").val();
//		
//		if( !abc.text.allNull(mainPymntCardType) && mainPymntCardType == "C" ) {
//			var onlyOrderProductCnt = $("#onlyOrderProductCnt").val(); // 배송비/사은품을 제외한 주문상품 갯수
//			var checkedOrderPrdtCnt = 0; // 체크된 주문상품 개수
//			
//			if( selectVal == "cancel" ) {
//				for(var i=1; i <= orderCompanyProductSheet.RowCount(); i++){
//					if(orderCompanyProductSheet.GetCellValue(i, "updateCheck") == "1"){
//						checkedOrderPrdtCnt++;
//					}
//				}
//				
//				console.log("onlyOrderProductCnt : " + onlyOrderProductCnt);
//				console.log("checkedOrderPrdtCnt : " + checkedOrderPrdtCnt);
//				
//				// 주문상품 개수가 주문에 물려있는 배송비/사은품을 제외한 주문상품 갯수와 같지 않다면 부분취소이므로
//				if( onlyOrderProductCnt != checkedOrderPrdtCnt){
//					alert( "법인카드로 결제한 주문은 전체취소만 가능합니다." );
//					return;
//				}
//			}
//		}
		
		if ( selectVal == "cancel" || selectVal == "return" || selectVal == "exchange" ) {
			// validation chk
			var params = {} ;
			var clmGbnCode = "";
			
			if(selectVal == "cancel") {
				clmGbnCode = "10000";
			} else if(selectVal == "exchange") {
				clmGbnCode = "10001";
			} else if(selectVal == "return") {
				clmGbnCode = "10002";
			}
			
			params.orderNos = orderNos;
			params.orderPrdtSeqs = orderPrdtSeqs; 
			params.orgOrderNo = orgOrderNo;
			params.clmGbnCode = clmGbnCode;
			
			$.ajax({
				type :"post",
				url : "/claim/claim/validate-claim-product",
				async: false, 
				data: params
			})
			.done(function(data){
				if(data.success == abc.consts.BOOLEAN_TRUE){
					abc.biz.order.info.tab.makeClaimSetting(selectVal , orderNos , orderPrdtSeqs, orgOrderNo, clmGbnCode);
				}else{
					alert(data.message) ;
				}
			})
			.fail(function(e){
				console.log("e :" + e);
				return false;
			});
		} else {
			abc.biz.order.info.tab.makeClaimSetting(selectVal , orderNos , orderPrdtSeqs, orgOrderNo, clmGbnCode);
		} 
		
	});
	
	
	/*************************************************************************
	 *								입점업체 상품 그리드 영역 E
	 *************************************************************************/
	
	
	/*************************************************************************
	 *								할인 정보 그리드 영역 S
	 *************************************************************************/
	/**
	 * 입점업체 상품 초기 세팅
	 */
	_orderInfo.initOrderDiscountInfoGrid = function() {
		//
		createIBSheet2(document.getElementById("orderDiscountInfoGrid"), "orderDiscountInfoSheet", "100%", "370px");
		
		// Grid 초기화 기본/헤더/컬럼선언
		var initSheet = {};
		
		initSheet.Cfg = {SearchMode:smLazyLoad, MaxSort:1};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};	
		initSheet.Cols = [
			{Header:"" ,		 		Type:"Status",		SaveName:"status",				Width: 0, 	Align:"", 			Edit:0, Hidden:1}
			//, {Header:"",				Type:"CheckBox",	SaveName:"updateCheck",	Width: 5,  Align:"Center",	Edit:1, Sort:0}
			, {Header:"할인구분", 			Type:"Text",		SaveName:"discountGbn",			Width: 70, 	Align:"Center", 	Edit:0}
			, {Header:"내용", 			Type:"Html",		SaveName:"discountTxt",			Width: 300, Align:"Left", 		Edit:0}
			, {Header:"할인금액(율)", 		Type:"Float",		SaveName:"discountAmt",			Width: 70, 	Align:"Right", 		Edit:0, Format: "#,##0원"}
			, {Header:"적용상품코드", 		Type:"Text",		SaveName:"discountPrdtNo",		Width: 120, Align:"Center", 	Edit:0}
			, {Header:"적용상품명", 		Type:"Text",		SaveName:"discountPrdtName",	Width: 150, Align:"Center", 	Edit:0}
			, {Header:"수량", 			Type:"Text",		SaveName:"",					Width: 50, 	Align:"Center", 	Edit:0}
			, {Header:"ID", 			Type:"Text",		SaveName:"discountId",			Width: 20, 	Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"TXT", 			Type:"Text",		SaveName:"discountTxt",			Width: 20, 	Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"STDT", 			Type:"Text",		SaveName:"discountStartDt",		Width: 20, 	Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"ENDT", 			Type:"Text",		SaveName:"discountEndDt",		Width: 20, 	Align:"Center", 	Edit:0, Hidden:1}
			];
		
		// Grid 초기화
		IBS_InitSheet(orderDiscountInfoSheet , initSheet);
		// Grid 건수 정보 표시
		orderDiscountInfoSheet.SetCountPosition(0);
		// Grid width 자동 조절
		//orderDiscountInfoSheet.FitColWidth();
		orderDiscountInfoSheet.SetRowHeightMin(40); 
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		orderDiscountInfoSheet.SetExtendLastCol(1);
		//orderDiscountInfoSheet.SetSheetWidth(600);
		
		abc.biz.order.info.tab.orderDiscountInfoDoAction("search"); // 조회실행
	}
	
	/**
	 * 관리자 목록 Action관리
	 */
	_orderInfo.orderDiscountInfoDoAction = function(sAction){
		switch(sAction) {
			// 조회 
			case "search" :
				var param = {
						url : "/order/read-order-info-discount-list"
				 		, subparam : FormQueryStringEnc(document.frmSearch)
						, sheet : "orderDiscountInfoSheet"
					};
	
					DataSearch(param);
					
				break;
		}
	}
	
	
	
	/*************************************************************************
	 *								할인 정보 그리드 영역 E
	 *************************************************************************/
	
	
	
	/*************************************************************************
	 *								결제 정보 그리드 영역 S
	 *************************************************************************/
	/**
	 * 입점업체 상품 초기 세팅
	 */
	_orderInfo.initOrderPaymentInfoGrid = function() {
		//
		createIBSheet2(document.getElementById("orderPaymentInfoGrid"), "orderPaymentInfoSheet", "100%", "370px");
		
		// Grid 초기화 기본/헤더/컬럼선언
		var initSheet = {};
		
		initSheet.Cfg = {SearchMode:smLazyLoad, MaxSort:1 , MergeSheet:msHeaderOnly};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:0};	
		initSheet.Cols = [
			{Header:"" ,		 			Type:"Status",		SaveName:"status",				Width: 0, 	Align:"", 		Edit:0, Hidden:1}
			//, {Header:"",				Type:"CheckBox",	SaveName:"updateCheck",	Width: 5,  Align:"Center",	Edit:1, Sort:0}
			, {Header:"결제구분", 				Type:"Combo",		SaveName:"deviceCode",			Width: 70, 	Align:"Center", 	Edit:0}
			, {Header:"결제상태", 				Type:"Combo",		SaveName:"pymntStatCode",		Width: 70, 	Align:"Center", 	Edit:0}
			, {Header:"결제수단", 				Type:"Combo",		SaveName:"pymntMeansCode",		Width: 120, Align:"Center", 	Edit:0}
			, {Header:"결제정보", 				Type:"Html",		SaveName:"pymntInfo",			Width: 200, 	Align:"Center", 	Edit:0}
//			, {Header:"결제정보", 				Type:"Button",		SaveName:"vv",					Width: 120, 	Align:"Center", DefaultValue:"결제내역조회"}
//			, {Header:"결제정보", 				Type:"Button",		SaveName:"vv",					Width: 120, 	Align:"Center", DefaultValue:"결제내역조회"}
			, {Header:"결제금액", 				Type:"Float",		SaveName:"pymntAmt",			Width: 70, 	Align:"Right", 		Edit:0 , Format: "#,##0원"}
			, {Header:"결제승인일", 			Type:"Html",		SaveName:"pymntDtm",			Width: 120, Align:"Center" }
			//, {Header:"결제승인일", 			Type:"Text",		SaveName:"pymntDtm",			Width: 120, Align:"Center", 	Edit:0 , Format:"YmdHms"}
			//, {Header:"결제승인일", 			Type:"Button",		SaveName:"pymntChange",			Width: 120, Align:"Center" 	, DefaultValue:"결제수단변경"}
			, {Header:"에스크로결제", 			Type:"Combo",		SaveName:"escrApplyYn",			Width: 90, 	Align:"Center", 	Edit:0}
			, {Header:"에스크로 배송처리일자", 	Type:"Text",		SaveName:"escrSendDtm",			Width: 130, Align:"Center", 	Edit:0,		Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			, {Header:"현금영수증\n발급여부", 	Type:"Html",		SaveName:"cashRcptIssueYn",		Width: 120, Align:"Center"}
			, {Header:"세금계산서\n발행상태", 	Type:"Text",		SaveName:"",					Width: 120, Align:"Center", 	Edit:0}
			, {Header:"", 					Type:"Text",		SaveName:"mainPymntMeansYn",	Width: 0, 	Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"결제기관명", 			Type:"Text",		SaveName:"pymntOrganName",		Width: 0, 	Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"결제수단코드명", 			Type:"Text",		SaveName:"pymntMeansCodeName",	Width: 0, 	Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"주문번호", 				Type:"Text",		SaveName:"orderNo",				Width: 0, 	Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"주문결제순번", 			Type:"Text",		SaveName:"orderPymntSeq",		Width: 0, 	Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"결제승인번호", 			Type:"Text",		SaveName:"pymntAprvNoText",		Width: 0, 	Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"할부개월수", 			Type:"Text",		SaveName:"instmtTermCount",		Width: 0, 	Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"카드구분", 				Type:"Text",		SaveName:"cardType",			Width: 0, 	Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"결제수단 카운트", 		Type:"Text",		SaveName:"pymntMeansCnt",		Width: 0, 	Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"결제수단변경 카운트", 		Type:"Text",		SaveName:"changeCnt",			Width: 0, 	Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"업체주문카운트", 			Type:"Text",		SaveName:"vendorPrdtCnt",		Width: 0, 	Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"클레임접수카운트", 		Type:"Text",		SaveName:"claimCnt",			Width: 0, 	Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"결제변경수단여부", 		Type:"Text",		SaveName:"pymntMeansChngPsbltYn",			Width: 0, 	Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"결제기관번호", 			Type:"Text",		SaveName:"pymntOrganNoText",			Width: 0, 	Align:"Center", 	Edit:0}
			, {Header:"사이트NO", 			Type:"Text",		SaveName:"siteNo",				Width: 0, 	Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"기프트카드현금영수증",		Type:"Text",		SaveName:"rcptDealNoText",		Width: 100, 	Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"결제변경일자",			Type:"Text",		SaveName:"pymntMeansChngDtm",	Width: 100, 	Align:"Center", 	Edit:0, Hidden:1}
			];
		
		// Grid 초기화
		IBS_InitSheet(orderPaymentInfoSheet , initSheet);
		// Grid 건수 정보 표시
		orderPaymentInfoSheet.SetCountPosition(0);
		// Grid width 자동 조절
		//orderPaymentInfoSheet.FitColWidth();
		orderPaymentInfoSheet.SetRowHeightMin(50); 
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		orderPaymentInfoSheet.SetExtendLastCol(1);
		//orderPaymentInfoSheet.SetSheetWidth(600);
		
		orderPaymentInfoSheet.InitDataCombo(0 , "deviceCode" , this.codeCombo.DEVICE_CODE.text , this.codeCombo.DEVICE_CODE.code ); 
		orderPaymentInfoSheet.InitDataCombo(0 , "pymntStatCode" , this.codeCombo.PYMNT_STAT_CODE.text , this.codeCombo.PYMNT_STAT_CODE.code  ); 
		orderPaymentInfoSheet.InitDataCombo(0 , "pymntMeansCode" , this.codeCombo.PYMNT_MEANS_CODE.text , this.codeCombo.PYMNT_MEANS_CODE.code  ); 
		orderPaymentInfoSheet.InitDataCombo(0 , "escrApplyYn" , "적용|미적용" , "Y|N"  ); 
		//orderPaymentInfoSheet.InitDataCombo(0 , "cashRcptIssueYn" , "발급|미발급" , "Y|N"  ); 
		
		abc.biz.order.info.tab.orderPaymentInfoDoAction("search"); // 조회실행
	}
	
	/**
	 * 관리자 목록 Action관리
	 */
	_orderInfo.orderPaymentInfoDoAction = function(sAction){
		switch(sAction) {
			// 조회 
			case "search" :
				var param = {
						url : "/order/read-order-info-payment-list"
				 		, subparam : FormQueryStringEnc(document.frmSearch)
						, sheet : "orderPaymentInfoSheet"
					};
					DataSearch(param);
				break;
		}
	}
	
	_orderInfo.paymentChage = function (row){
		
		var orderNo = orderPaymentInfoSheet.GetCellValue(row, "orderNo");
		var pymntMeansCode = orderPaymentInfoSheet.GetCellValue(row, "pymntMeansCode");
		var orderPymntSeq = orderPaymentInfoSheet.GetCellValue(row, "orderPymntSeq");
		
		var type = "2"; 
		var param = {
			  orderNo : orderNo
			, vaildationType : type
		};
		
		if(!confirm('결제수단 변경 가능 처리를 하시겠습니까?')){
			return;
		}
		
		$.ajax({
			type :"post",
			url : "/order/order-vaildation",
			async: false, 
			data: param
		})
		.done(function(data){
			console.log("resultCode" , data.resultMsg  );
			if(data.resultCode == abc.consts.BOOLEAN_TRUE){
				_orderInfo.paymentChageSave(row);
			}else{
				alert("결제 수단 변경 불가능 상태 입니다. 확인 바랍니다.");
			}
		})
		.fail(function(e){
			alert("결제 수단 변경 불가능 상태 입니다. 확인 바랍니다.");
			console.log("e :" + e);
			return ;
		});
	}
	
	_orderInfo.paymentChageSave = function (row){
		var orderNo = orderPaymentInfoSheet.GetCellValue(row, "orderNo");
		var pymntMeansCode = orderPaymentInfoSheet.GetCellValue(row, "pymntMeansCode");
		var orderPymntSeq = orderPaymentInfoSheet.GetCellValue(row, "orderPymntSeq");
		
		var param = {
			  orderNo : orderNo
			, orderPymntSeq : orderPymntSeq
		};
		
		$.ajax({
			type :"post",
			url : "/order/orderPaymentChange",
			data: param
		})
		.done(function(data){
			if(data.result == abc.consts.BOOLEAN_TRUE){
				alert("수정되었습니다.");
				_orderInfo.orderPaymentInfoDoAction("search");
			}
		})
		.fail(function(e){
			console.log("e :" + e);
			alert("오류가 발생하였습니다");
		});
	}
	
	_orderInfo.paymentHistory = function (row){
		//alert("결제내역조회 작업중" + row);
		var orderNo 			= orderPaymentInfoSheet.GetCellValue(row, "orderNo");
		var pymntMeansCode 		= orderPaymentInfoSheet.GetCellValue(row, "pymntMeansCode");
		var pymntAprvNoText 	= orderPaymentInfoSheet.GetCellValue(row, "pymntAprvNoText");
		var pymntOrganNoText 	= orderPaymentInfoSheet.GetCellValue(row, "pymntOrganNoText");
		var siteNo 				= orderPaymentInfoSheet.GetCellValue(row, "siteNo");
		
		if ( pymntMeansCode == abc.order.consts.PYMNT_MEANS_CODE_NAVER_PAY ) {
			var url = "/order/naverpay/history";
		}else if (pymntMeansCode == abc.order.consts.PYMNT_MEANS_CODE_KAKAO_PAY ){
			var url = "/order/kakaoPay/history";
		//	pymntAprvNoText = "T2609075332886994891" ; // 임시 
		}
		var params = {}
		
		//파라미터 파싱
		params.orderNo 			=  orderNo;
		params.pymntMeansCode 	=  pymntMeansCode;
		params.pymntAprvNoText 	=  pymntAprvNoText;
		params.paymentId 		=  pymntAprvNoText; // 임시처리 확인후 변경 예정
		params.pymntOrganNoText =  pymntOrganNoText; // 카카오 페이 TID 		
		params.siteNo 			=  siteNo;
		
		abc.open.popup({
			winname :	"결제 내역 조회",
			url 	:	url,
			width 	:	1240,  
			height	:	605,    
			params	:	params
		});
		
	}
	
	_orderInfo.cashReceipte = function (row){
		var siteNo 				= orderPaymentInfoSheet.GetCellValue(row, "siteNo");
		var orderNo 			= orderPaymentInfoSheet.GetCellValue(row, "orderNo");
		var pymntMeansCode 		= orderPaymentInfoSheet.GetCellValue(row, "pymntMeansCode");
		var orderPymntSeq 		= orderPaymentInfoSheet.GetCellValue(row, "orderPymntSeq");
		
		var param = {
				siteNo : siteNo
			,	orderNo : orderNo
			,	orderPymntSeq : orderPymntSeq
			,	pymntMeansCode : pymntMeansCode
		};
		
		/*var param = {
				orderNo   : '2019081205241'
			,	orderPymntSeq : '1'
			,	pymntMeansCode : '10004'
		};*/
		
		$.ajax({
			type :"post",
			url : "/order/order-cashReceipte",
			async: false, 
			data: param
		})
		.done(function(data){
			if (pymntMeansCode == '10004') {
				alert("현금 영수증 발행 대상 금액은 " + data.naverTotCashAmt + "원 입니다.");
			}else{
				var cash_no =  data.orderPymntData.cashRcptDealNo;  
				var order_id = data.orderPymntData.orderNo;
				var trade_mony = data.orderPymntData.pymntAmt;	
				
				var urlTemp = window.location.href.indexOf("admin") > -1;
				console.log("urlTemp :::",urlTemp);
				var cashUrl;
				if(urlTemp){
					// 현금영수증 운영
					cashUrl = "https://admin8.kcp.co.kr/assist/bill.BillActionNew.do?cmd=cash_bill&cash_no="+cash_no+"&order_id="+order_id+"&trade_mony="+trade_mony
				}else{
					// 현금영수증 테스트 
					cashUrl = "https://testadmin8.kcp.co.kr/assist/bill.BillActionNew.do?cmd=cash_bill&cash_no="+cash_no+"&order_id="+order_id+"&trade_mony="+trade_mony
				}
				
				abc.open.popup({
					winname :	"현금영수증",
					url 	: cashUrl,
					width 	:	370,  
					height	:	625
				});
			}
		})
		.fail(function(e){
			alert("현금영수증 조회되지 않습니다. 확인 바랍니다.");
			console.log("e :" + e);
			return ;
		});
		
	}
	
	/*************************************************************************
	 *								결제 정보 그리드 영역 E
	 *************************************************************************/
	
	$("#buyerSameCheck").click(function(){
		
		var orderNo 			= $("#orderNo").val();
		var dlvyTypeCode 		= $("#dlvyTypeCode").val();
		var chkFlag	= $(this).is(":checked");
		
		var param = {
			orderNo   : orderNo
			, dlvyTypeCode : dlvyTypeCode
		};
		
		$.ajax({
			type 	:"post",
			data 	: param,
			url		:"/order/order-detail-info"
		})
		.done(function(data){
			// 편의점 , 매장 픽업시 수령자명 , 휴대폰 번호 이외에 처리 여부 확인 필요 
			console.log ( " data " , data );
			console.log ( " chkFlag " , chkFlag );
			if(chkFlag){
				if (dlvyTypeCode == abc.order.consts.DLVY_TYPE_CODE_NORMAL_LOGISTICS) {	// 일반
					$("#rcvrName").val(data.result.buyerName);
					$("#rcvrHdphnNoText").val(data.result.buyerHdphnNoText);
					$("#rcvrPostCodeText").val(data.result.rcvrPostCodeText);
					$("#rcvrPostAddrText").val(data.result.rcvrPostAddrText);
					$("#rcvrDtlAddrText").val(data.result.rcvrDtlAddrText);
				}else if (dlvyTypeCode == abc.order.consts.DLVY_TYPE_CODE_CONVENIENCE_PICKUP) { 	// 편의점  
					$("#rcvrName").val(data.result.buyerName);
					$("#rcvrHdphnNoText").val(data.result.buyerTelNoText);
				}else if (dlvyTypeCode == abc.order.consts.DLVY_TYPE_CODE_STORE_PICKUP) {	// 매장픽업
					$("#rcvrName").val(data.result.buyerName);
					$("#rcvrHdphnNoText").val(data.result.buyerTelNoText);
				}
			} else {
				if (dlvyTypeCode == abc.order.consts.DLVY_TYPE_CODE_NORMAL_LOGISTICS) {	// 일반
					$("#rcvrName").val(data.result.rcvrName);
					$("#rcvrHdphnNoText").val(data.result.rcvrHdphnNoText);
					$("#rcvrPostCodeText").val(data.result.rcvrPostCodeText);
					$("#rcvrPostAddrText").val(data.result.rcvrPostAddrText);
					$("#rcvrDtlAddrText").val(data.result.rcvrDtlAddrText);
				}else if (dlvyTypeCode == abc.order.consts.DLVY_TYPE_CODE_CONVENIENCE_PICKUP) { 	// 편의점  
					$("#rcvrName").val(data.result.rcvrName);
					$("#rcvrHdphnNoText").val(data.result.rcvrHdphnNoText);
				}else if (dlvyTypeCode == abc.order.consts.DLVY_TYPE_CODE_STORE_PICKUP) {	// 매장픽업
					$("#rcvrName").val(data.result.rcvrName);
					$("#rcvrHdphnNoText").val(data.result.rcvrHdphnNoText);
				}
			}
			
		})
		.fail(function(e){
			console.log("e :" + e);
		});
		
	});
	/**
	 * 편의점 찾기 팝업
	 */
	$("#cvsSearchBtn").click(function( ){
		var type = $("input[name=cvnstrCode]:checked").val();
		
		switch (type) {
			case "10000":
				window.open("http://www.cvsnet.co.kr/GIS/chzero/cvsnet_shop_dt.jsp?id=abcmart&UID=https://localbo.a-rt.com:8800/order/cvsResult", "popupSearchCVS", "width=1020 , height=645");
				break;
			case "10001":
				window.open("http://www.cupost.co.kr/GIS/chzero/cupost_shop_dt.jsp?id=abcmart&UID=https://localbo.a-rt.com:8800/order/cvsResult", "popupSearchCVS", "width=1020 , height=645");
				break;
		}
	});
	
	
	$("#abcmartSearchBtn").click(function(){
		// 상품준비중일경우 제외 처리 
		abc.storeSearchPopup('abc.biz.order.info.tab.setStoreInfo' , false);
	});
	
	$("#couponIssue").click(function(){
		// 상품준비중일경우 제외 처리 
		//alert("쿠폰지급팝업 작업중 ") ;
		abc.couponSearchPopup (true , "abc.biz.order.info.tab.insertMemberCoupon")
		
	});
	
	_orderInfo.insertMemberCoupon = function(data) {
		console.log ("data" , data);
		
		var memberNos = new Array();
		memberNos.push($("#memberNo").val());
		
		if (data.length > 1) {
			alert("한개의 쿠폰만 발급 가능 합니다.확인 바랍니다.");
			return;
		}
		
		$.ajax({
			type :"post",
			url : "/promotion/coupon/member/save",
			async: false, 
			data: $.paramObject({memberNos : memberNos, cpnNo : data[0].cpnNo}),
			dataType : "json"
		})
		.done(function(data){
			if(data.result == abc.consts.BOOLEAN_TRUE){
				alert("쿠폰 발급 되었습니다.");
			}else{
				alert("쿠폰 발급 중 오류 발생 되었습니다.");
			}
		})
		.fail(function(e){
			alert("쿠폰 발급 중 오류 발생 되었습니다.");
			console.log("e :" + e);
			return ;
		});
		
	}
	
	$("#rcvrInfoUdpate").click(function(){
		var orderNo =  $("#orderNo").val();
		var dlvyTypeCode 		= $("#dlvyTypeCode").val();
		
		if (dlvyTypeCode == abc.order.consts.DLVY_TYPE_CODE_NORMAL_LOGISTICS) {	// 일반
			if ($("#rcvrHdphnNoText").val() == "") {
				alert("수령자 휴대폰번호를 입력해주세요.");
				return; 
			}
			if ($("#rcvrPostCodeText").val() == "") {
				alert("우편번호 선택해주세요.");
				return; 
			}
			if ($("#rcvrPostAddrText").val() == "") {
				alert("우편번호 주소를 선택해주세요.");
				return; 
			}
			if ($("#rcvrDtlAddrText").val() == "") {
				alert("상세 주소를 입력해주세요.");
				return; 
			}
			
		}else{
			alert("변경 가능한 배송유형이 아닙니다.(일반택배만 가능합니다.) ");
			return; 
		}
		
		var type = "1"; 
		var param = {
				orderNo   : orderNo
				, vaildationType   : type
		};
		
		$.ajax({
			type :"post",
			url : "/order/order-vaildation",
			async: false, 
			data: param
		})
		.done(function(data){
			if(data.resultCode == abc.consts.BOOLEAN_TRUE){
				_orderInfo.rcvrInfoUdpate();
				//alert("배송지 변경 가능 .");
			}else{
				alert("배송지 주소 불가능 상태 입니다. 확인 바랍니다.");
			}
		})
		.fail(function(e){
			alert("배송지 주소 불가능 상태 입니다. 확인 바랍니다.");
			console.log("e :" + e);
			return ;
		});
		
	});
	
	/**
	 * 편의점 찾기 data 입력
	 */
	_orderInfo.setCvsInfo = function(data){
		console.log ("data  >> "  +  data);
		
		var cvnstrCode = data.ho_code == "01" ? "10000" :"10001" ; 
		
		$("#cvnstrCode").val(cvnstrCode); 
		$("#cvnstrNoText").val(data.store_code ); 
		$("#cvnstrName").val(data.codeName ); 
		$("#telNoText").val(data.codeTel ); 
		$("#postCodeText").val( data.post_no ); 
		$("#postAddrText").val( data.store_address1); 
		$("#dtlAddrText").val(data.store_address2); 
		$("#arvlStoreCodeText").val(data.code_f);
		$("#arvlStoreBrcdText").val( data.dd_zone);
		$("#dongNameCodeText").val(data.dd_bag);
		$("#arvlDongName").val( data.eupmyeon );  
		
		$("#cvsName").val( data.codeName ); 
		var addrInfo = "("+data.post_no+") "+ data.store_address1 +" "+ data.store_address2; 
		$("#cvsAddrInfo").text(addrInfo); 
		$("#cvsTelInfo").text(data.codeTel); 
	}
	
	_orderInfo.setStoreInfo = function(data){
		console.log ("data  >> "  +  data);
		console.log ("data length >> "  +  data.length);
		
		if ( data.length > 1  ) {
			alert("1개이상의 매장이 선택되었습니다.");
			return;
		}
		
		console.log( "fullAddr" , data[0].fullAddr)
		console.log( "storeName" , data[0].storeName)
		console.log( "storeNo" , data[0].storeNo)
		console.log( "businessStartTime" , data[0].businessStartTime)
		console.log( "businessEndTime" , data[0].businessEndTime)
		console.log( "TelNoText" , data[0].TelNoText)
		console.log( "postCodeText" , data[0].postCodeText)
		console.log( "postAddrText" , data[0].postAddrText)
		console.log( "dtlAddrText" , data[0].dtlAddrText)
		
		$("#storeNo").val( data[0].storeNo ); // 매장 번호  
		$("#storeName").val(data[0].storeName); // 매장 명 
		$("#storeAddInfo").val(" "); // 추가정보 명 
		
		$("#storeNameView").text(data[0].storeName);
		var bizTime = " "+ data[0].businessStartTime +"~"+data[0].businessEndTime;
		$("#storeBizTime").text(bizTime);
		$("#storeTel").text(data[0].TelNoText);
		$("#storeAddr").text(data[0].fullAddr);
		
	}
	
	$("#postSearchBtn").click(function(){
		abc.postPopup(abc.biz.order.info.tab.setPostCode);
	});
  
	/**
	 * 우편번호 pop 에서 값을 선택햇을경우 SETTING 
	 */
	_orderInfo.setPostCode = function(data){
		
		$("#rcvrPostCodeText").val(data.postCode);
		$("#rcvrPostAddrText").val(data.postAddress);
		$("#rcvrDtlAddrText").focus();  // 상세주소에 포커스
	}
	
	
	_orderInfo.rcvrInfoUdpate = function (){
		var orderNo =  $("#orderNo").val();
		var type = "1" ; 
		
		var dlvyTypeCode 		= $("#dlvyTypeCode").val(); // 배송유형 
		// 일반 배송 정보 
		var rcvrName 			= $("#rcvrName").val(); // 수령자명 
		var rcvrHdphnNoText 	= $("#rcvrHdphnNoText").val(); // 휴대폰번호  
		var rcvrTelNoText 		= $("#rcvrTelNoText").val(); // 수령자 전화번호  
		var rcvrPostCodeText 	= $("#rcvrPostCodeText").val(); // 우편번호  
		var rcvrPostAddrText 	= $("#rcvrPostAddrText").val(); // 우편주소   
		var rcvrDtlAddrText 	= $("#rcvrDtlAddrText").val(); // 상세주소
		
		// 매장 픽업 정보
		var storeNo 			= $("#storeNo").val(); // 매장 번호  
		var storeName 			= $("#storeName").val(); // 매장 명 
		var storeAddInfo 		= $("#storeAddInfo").val(); // 추가정보 명 
		
		// 편의점 픽업 정보 
		var cvnstrCode			= $("#cvnstrCode").val();
		var cvnstrNoText		= $("#cvnstrNoText").val();
		var cvnstrName			= $("#cvnstrName").val();
		var telNoText			= $("#telNoText").val();
		var postCodeText		= $("#postCodeText").val();
		var postAddrText		= $("#postAddrText").val();
		var dtlAddrText			= $("#dtlAddrText").val();
		var arvlStoreCodeText	= $("#arvlStoreCodeText").val();
		var arvlStoreBrcdText	= $("#arvlStoreBrcdText").val();
		var dongNameCodeText	= $("#dongNameCodeText").val();
		var arvlDongName		= $("#arvlDongName").val();
		var dlvyPlaceYn			= $("#dlvyPlaceYn").val();
		var dlvyMemoText		= $("#dlvyMemoTextArea").val();
		console.log("dlvyMemoText "  , dlvyMemoText);
		
		var param = {
				orderNo		  :	orderNo			
			, dlvyTypeCode 	:	dlvyTypeCode 		
			, rcvrName 		:	rcvrName 			
			, rcvrHdphnNoText :	rcvrHdphnNoText 	  
			, rcvrTelNoText 	:	rcvrTelNoText 		 
			, rcvrPostCodeText :	rcvrPostCodeText 	  
			, rcvrPostAddrText :	rcvrPostAddrText 	   
			, rcvrDtlAddrText 	:	rcvrDtlAddrText 	     
			, storeNo 		:	storeNo 			
			, storeName 		:	storeName 		     
			, storeAddInfo 	:	storeAddInfo 		
			, cvnstrCode		:	cvnstrCode		     
			, cvnstrNoText	:	cvnstrNoText		
			, cvnstrName		:	cvnstrName		     
			, telNoText		:	telNoText			
			, postCodeText	:	postCodeText		
			, postAddrText	:	postAddrText		
			, dtlAddrText		:	dtlAddrText		     
			, arvlStoreCodeText:	arvlStoreCodeText	
			, arvlStoreBrcdText:	arvlStoreBrcdText	
			, dongNameCodeText:	dongNameCodeText
			, arvlDongName	:	arvlDongName		
			, dlvyPlaceYn		:	dlvyPlaceYn		 
			, dlvyMemoText   : dlvyMemoText
			, VaildationType   : type
		};
		
		$.ajax({
			type :"post",
			url : "/order/rcvrInfoUdpate",
			data: param
		})
		.done(function(data){
			if(data.resultCode == abc.consts.BOOLEAN_TRUE){
				alert(data.resultMsg);
				location.reload();
			}else{
				console.log(data.resultMsg);
				alert("오류가 발생하였습니다" + data.resultMsg);
			}
		})
		.fail(function(e){
			console.log("e :" + e);
			alert("오류가 발생하였습니다...... ");
		});
	}
	
	_orderInfo.productVaildation = function (orderNo , type){
		
		var param = {
				orderNo   : orderNo
				, vaildationType   : type
		};
		
		$.ajax({
			type :"post",
			url : "/order/order-vaildation",
			async: false, 
			data: param
		})
		.done(function(data){
			
			if(data.resultCode == abc.consts.BOOLEAN_TRUE){
				return true;
			}
		})
		.fail(function(e){
			console.log("e :" + e);
			return false;
		});
	}
	
	/**
	 * 클레임 상세 팝업 호출
	 */
	_orderInfo.openClaimDetailPop = function(clmNo, clmGbnCode){

		var url = "";
		var params = {clmNo : clmNo, clmGbnCode : clmGbnCode};

		if(clmGbnCode == "10000") { //취소

			url = "/claim/claim/read-claim-cancel-detail-pop"; // 클레임 취소 상세

		} else if(clmGbnCode == "10001") { //교환

			url = "/claim/claim/read-claim-exchange-detail-pop"; // 클레임 교환 상세

		} else if(clmGbnCode == "10002") { //반품

			url = "/claim/claim/read-claim-return-detail-pop"; // 클레임 반품 상세
		}

		abc.open.popup({
			winname :	"claimDetailPop",
			url 	:	url,
			width 	:	1260,
			height	:	990,
			params	:	params
		});
	}
	
})();