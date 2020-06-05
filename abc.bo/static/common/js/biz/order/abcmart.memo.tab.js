/***
 * 관리자 업무와 관련된 함수 정의.
 * 
 */
(function() {
	
	var _orderDetail = abc.object.createNestedObject(window,"abc.biz.order.order.detail");
	
	/*************************************************************************
	 *								관리자 목록
	 *************************************************************************/
	
	/**
	 * 관리자 목록 초기 세팅
	 */
	_order.initOrderSheet = function() {
		//
		createIBSheet2(document.getElementById("orderGrid"), "orderSheet", "100%", "370px");
		
		// Grid 초기화 기본/헤더/컬럼선언
		var initSheet = {};
		var pageCount = $('#pageCount').val();

		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MaxSort:1};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};	
		initSheet.Cols = [
			  {Header:"" ,		 		Type:"Status",		SaveName:"status",		Width: 0,  Align:"", 		Edit:0, Hidden:1}
			, {Header:"",				Type:"CheckBox",	SaveName:"updateCheck",	Width: 5,  Align:"Center",	Edit:1, Sort:0}
			, {Header:"사이트", 			Type:"Text",		SaveName:"",			Width: 10, Align:"Center", 	Edit:0}
			, {Header:"결제구분", 			Type:"Text",		SaveName:"",			Width: 20, Align:"Center", 	Edit:0}
			, {Header:"주문유형", 			Type:"Text",		SaveName:"",			Width: 20, Align:"Center", 	Edit:0}
			, {Header:"주문번호", 			Type:"Text",		SaveName:"",			Width: 20, Align:"Center", 	Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer"}
			, {Header:"상품명", 			Type:"Text",		SaveName:"",			Width: 25, Align:"Center", 	Edit:0}
			, {Header:"주문일시", 			Type:"Date",		SaveName:"",			Width: 25, Align:"Center", 	Edit:0, Format:"YmdHms"}
			, {Header:"주문자", 			Type:"Text",		SaveName:"",			Width: 30, Align:"Center", 	Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer"}
			, {Header:"수령자", 			Type:"Text",		SaveName:"",			Width: 20, Align:"Center", 	Edit:0}
			, {Header:"결제수단", 			Type:"Text",		SaveName:"",			Width: 15, Align:"Center", 	Edit:0}
			, {Header:"총결제금액", 		Type:"Text",		SaveName:"",			Width: 20, Align:"Center", 	Edit:0}
			, {Header:"결제확인일", 		Type:"Date",		SaveName:"",			Width: 30, Align:"Center", 	Edit:0, Format:"YmdHms"}
			, {Header:"주문배송상태", 		Type:"Text",		SaveName:"",			Width: 30, Align:"Center", 	Edit:0}
			, {Header:"주문취소여부", 		Type:"Text",		SaveName:"",			Width: 30, Align:"Center", 	Edit:0}
			, {Header:"발송처", 			Type:"Text",		SaveName:"",			Width: 30, Align:"Center", 	Edit:0}
			, {Header:"주문-배송유형", 		Type:"Text",		SaveName:"",			Width: 30, Align:"Center", 	Edit:0}
		];
		
		// Grid 초기화
		IBS_InitSheet(orderSheet , initSheet);
		// Grid 건수 정보 표시
		orderSheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		orderSheet.SetPagingPosition(2);
		// Grid width 자동 조절
		orderSheet.FitColWidth();
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		orderSheet.SetExtendLastCol(1);
	}
	
	/**
	 * 관리자 목록 Action관리
	 */
	_order.orderDoAction = function(sAction){
		switch(sAction) {
			// 조회 
			case "search" :
				alert("검색 기능") ; 
				/*
				var pageCount = $('#pageCount').val();
				
				var param = { url : "/order/order-read"
					, onePageRow : pageCount
					, subparam : FormQueryStringEnc(document.frmSearch)
					, sheet : "orderSheet" };
				
				DataSearchPaging(param);
				 */
				break;
		}
	}

	
	/**
	 * 수선 팜업 호출 
	 */
	_order.repairRegPop = function(){
		alert("수선 팜업 호출");
		/*
		var url = "";
		var params = {}
		
		abc.open.popup({
			winname :	"createPop",
			url 	:	url,
			width 	:	645,
			height	:	885,
			params	:	params

		});
	*/
	}
	
	/**
	 * 심의 팝업 호출 
	 */
	_order.reviewRegPop = function(){
		alert("심의 팝업 호출 ");
		/*
		var url = "";
		var params = {}
		
		abc.open.popup({
			winname :	"createPop",
			url 	:	url,
			width 	:	645,
			height	:	885,
			params	:	params

		});
		 */
	}
	
})();