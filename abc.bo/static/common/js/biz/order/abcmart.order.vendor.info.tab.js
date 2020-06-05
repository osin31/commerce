/***
 *  주문 정보 tab 업무와 관련된 함수 정의.
 * 
 */
(function() {
	
	var _orderInfo = abc.object.createNestedObject(window,"abc.biz.order.vendor.info.tab"); 
	
	
	/*************************************************************************
	 *								입점업체 상품 그리드 영역 S
	 *************************************************************************/
	/**
	 * 입점업체 상품 초기 세팅
	 */
	_orderInfo.initOrderCompanyProductGrid = function() {
		if(typeof orderCompanyProductSheet != 'undefined'){
			orderCompanyProductSheet.Reset();
			orderCompanyProductSheet.DisposeSheet();
		}
		createIBSheet2(document.getElementById("orderCompanyProductGrid"), "orderCompanyProductSheet", "100%", "370px");
		
		// Grid 초기화 기본/헤더/컬럼선언
		var initSheet = {};

		initSheet.Cfg = {SearchMode:smLazyLoad, MaxSort:1, MergeSheet:msHeaderOnly};
		initSheet.HeaderMode = {Sort:0, ColMove:0, ColResize:1, HeaderCheck:0};	
		initSheet.Cols = [
			  {Header:"" ,		 		Type:"Status",		SaveName:"status",				Width: 0,  	Align:"", 		Edit:0, Hidden:1}
			, {Header:"사이트", 			Type:"Text",		SaveName:"siteNo",				Width: 60, 	Align:"Center", Edit:0, Hidden:1}
			, {Header:"사이트", 			Type:"Text",		SaveName:"siteName",			Width: 100, 	Align:"Center", Edit:0}
			, {Header:"주문번호", 			Type:"Text",		SaveName:"orderNo",				Width: 120, 	Align:"Center", Edit:0}
			, {Header:"주문상품\n순번", 	Type:"Text",		SaveName:"orderPrdtSeq",		Width: 60, 	Align:"Center", Edit:0}
			, {Header:"상품코드", 			Type:"Text",		SaveName:"prdtNo",				Width: 100, 	Align:"Center", Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer"}
			, {Header:"업체상품코드", 		Type:"Text",		SaveName:"vndrPrdtNoText",		Width: 100, 	Align:"Center", Edit:0}
			, {Header:"상품명",			Type:"Image",		SaveName:"imageUrl",			Width: 50,	MinWidth:50,	Align:"Center",	Edit:0, ImgWidth: 50}
			, {Header:"상품명", 			Type:"Html",		SaveName:"prdtName",			Width: 200, Align:"Left", 	Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer"}
			, {Header:"업체코드", 			Type:"Text",		SaveName:"vndrNo",				Width: 90, 	Align:"Center", Edit:0}
			, {Header:"업체명", 			Type:"Text",		SaveName:"vndrName",			Width: 120, Align:"Center", Edit:0}
			, {Header:"옵션", 			Type:"Text",		SaveName:"optnName",			Width: 100, Align:"Center", Edit:0}
			, {Header:"결제수량", 			Type:"Text",		SaveName:"orderQty",			Width: 60, Align:"Center", 	Edit:0}
			, {Header:"정상가", 			Type:"Float",		SaveName:"prdtNormalAmt",		Width: 80, Align:"Right", 	Edit:0, Format: "#,##0원"}
			, {Header:"판매가", 			Type:"Float",		SaveName:"prdtSellAmt",			Width: 80, Align:"Right", 	Edit:0, Format: "#,##0원"}
			, {Header:"할인금액", 			Type:"Float",		SaveName:"totalDscntAmt",		Width: 80, Align:"Right", 	Edit:0, Format: "#,##0원"}
			, {Header:"쿠폰할인", 			Type:"Float",		SaveName:"cpnDscntAmt",			Width: 80, Align:"Right", 	Edit:0, Format: "#,##0원"}
			, {Header:"주문금액", 			Type:"Float",		SaveName:"orderAmt",			Width: 80, Align:"Right", 	Edit:0, Format: "#,##0원"}
			, {Header:"사후적립인증번호", 	Type:"Text",		SaveName:"crtfcNoText",			Width: 80, Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"배송ID", 			Type:"Text",		SaveName:"orderDlvyHistSeq",	Width: 100, Align:"Center", Edit:0, Hidden:1}
			, {Header:"주문배송상태(코드)", 	Type:"Text",		SaveName:"orderPrdtStatCode",	Width: 100, Align:"Center", Edit:0, Hidden:1}
			, {Header:"주문배송상태", 		Type:"Html",		SaveName:"orderPrdtStatCodeName",	Width: 100, Align:"Center", 	Edit:0}
			, {Header:"클레임", 			Type:"Text",		SaveName:"clmGbnCode",			Width: 80, Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"판매취소요청", 		Type:"Combo",		SaveName:"sellCnclRsnCode",		Width: 80, Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"택배사", 			Type:"Text",		SaveName:"logisVndrCode",		Width: 100, Align:"Center", Edit:0, Hidden:1}
			, {Header:"택배사", 			Type:"Text",		SaveName:"logisVndrCodeName",	Width: 100, Align:"Center", Edit:0}
			, {Header:"송장번호", 			Type:"Text",		SaveName:"waybilNoText",		Width: 150, Align:"Center", Edit:0}
			, {Header:"발송처", 			Type:"Combo",		SaveName:"stockGbnCode",		Width: 70, Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"매출구분코드", 		Type:"Text",		SaveName:"salesCnclGbnType",	Width: 80, Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"사은품상품코드", 		Type:"Text",		SaveName:"giftPrdtNo",			Width: 80, Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"사은품상품명", 		Type:"Text",		SaveName:"giftPrdtName",		Width: 80, Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"클레임번호", 		Type:"Text",		SaveName:"clmNo",				Width: 80, Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"상품유형", 			Type:"Text",		SaveName:"prdtTypeCodeName",	Width: 80, 	Align:"Center", Edit:0}
		];
		
		// Grid 초기화
		IBS_InitSheet(orderCompanyProductSheet , initSheet);
		// Grid width 자동 조절
		//orderCompanyProductSheet.FitColWidth();
		orderCompanyProductSheet.SetRowHeightMin(40);
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		orderCompanyProductSheet.SetExtendLastCol(1);
		
		orderCompanyProductSheet.InitDataCombo(0 , "logisVndrCode" , this.codeCombo.LOGIS_VNDR_CODE.text , this.codeCombo.LOGIS_VNDR_CODE.code  );
		abc.biz.order.vendor.info.tab.companyProductDoAction("search"); // 조회실행
	}
	
	/**
	 * 관리자 목록 Action관리
	 */
	_orderInfo.companyProductDoAction = function(sAction){
		switch(sAction) {
			// 조회 
			case "search" :
				$("#mmnyPrdtYn").val('N'); //업체 N
				var param = {
						url : "/delivery/vendor/order/read-order-vendor-product-list"
				 		, subparam : FormQueryStringEnc(document.frmSearch)
						, sheet : "orderCompanyProductSheet"
					};
	
					DataSearch(param);
					abc.biz.order.vendor.memo.list($("#orderNo").val());
				break;
		}
	}
	
	/*************************************************************************
	 *								입점업체 상품 그리드 영역 E
	 *************************************************************************/
	
})();