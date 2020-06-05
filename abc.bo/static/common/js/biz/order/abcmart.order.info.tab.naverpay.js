/***
 *  주문 정보 tab 업무와 관련된 함수 정의.
 * 
 */
(function() {
	
	var _orderInfo = abc.object.createNestedObject(window,"abc.biz.order.info.tab.naverPay"); 
	
	/*************************************************************************
	 *						네이버 페이 결제 내역 
	 *************************************************************************/
	
	/**
	 * ABC 자사 상품 초기 세팅
	 */
	_orderInfo.initNaverPayGrid = function() {
		//
		createIBSheet2(document.getElementById("naverPayGrid"), "naverPaySheet", "100%", "370px");
		
		// Grid 초기화 기본/헤더/컬럼선언
		var initSheet = {};

		initSheet.Cfg = {SearchMode:smLazyLoad, MaxSort:1 , MergeSheet:msHeaderOnly};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:0};	
		initSheet.Cols = [
			  {Header:"" ,		 		Type:"Status",	SaveName:"status",				Width: 0,  	Align:"", 		Edit:0, Hidden:1}
			, {Header:"원결제번호", 		Type:"Text",	SaveName:"paymentId",			Width: 90, 	Align:"Center", Edit:0}
			, {Header:"결제승인유형", 		Type:"Combo",	SaveName:"admissionTypeCode",	Width: 90, 	Align:"Center", ComboText:"승인|전체취소|부분취소", ComboCode:"01|03|04" , Edit:0}
			, {Header:"카드사/은행",		Type:"Html",	SaveName:"paymentInfo",			Width: 150, Align:"Center", Edit:0}
			, {Header:"결제/취소금액", 		Type:"Float",	SaveName:"totalPayAmount",		Width: 90, 	Align:"Right", 	Edit:0 , Format: "#,##0원"}
			, {Header:"결제/취소일시", 		Type:"Date",	SaveName:"admissionYmdt",		Width: 100, Align:"Center", Edit:0 , Format: "YmdHms"}
			, {Header:"",				Type:"Text",	SaveName:"installMonthView",	Width: 150, Align:"Center", Edit:0 , Hidden:1 }
			, {Header:"결제수단",			Type:"Text",	SaveName:"primaryPayMeans",		Width: 150, Align:"Center", Edit:0 , Hidden:1 }
			, {Header:"카드번호",			Type:"Text",	SaveName:"cardNo",				Width: 150, Align:"Center", Edit:0 , Hidden:1 }
			, {Header:"카드사코드",			Type:"Text",	SaveName:"cardCorpCode",		Width: 150, Align:"Center", Edit:0 , Hidden:1 }
			, {Header:"할부개월",			Type:"Text",	SaveName:"cardInstCount",		Width: 150, Align:"Center", Edit:0 , Hidden:1 }
			, {Header:"은행코드",			Type:"Text",	SaveName:"bankCorpCode",		Width: 150, Align:"Center", Edit:0 , Hidden:1 }
			, {Header:"계좌번호",			Type:"Text",	SaveName:"bankAccountNo",		Width: 150, Align:"Center", Edit:0 , Hidden:1 }
		];
		
		// Grid 초기화
		IBS_InitSheet(naverPaySheet , initSheet);
		// Grid 건수 정보 표시
		naverPaySheet.SetCountPosition(0);
		// Grid width 자동 조절
		naverPaySheet.FitColWidth();
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		naverPaySheet.SetExtendLastCol(1);
		
		//naverPayGrid.InitDataCombo(0 , "payment_action_type" , "승인|취소" , "PAYMENT|CANCEL"  );
		
		abc.biz.order.info.tab.naverPay.DoAction("search"); // 조회실행
	}
	
	/**
	 * 관리자 목록 Action관리
	 */
	_orderInfo.DoAction = function(sAction){
		switch(sAction) {
			// 조회 
			case "search" :
				var param = {
						url : "/order/naverpay/history-list"
						, subparam : FormQueryStringEnc(document.frmSearch)
						, sheet : "naverPaySheet"
					};

					DataSearch(param);
					
				break;
		}
	}
	
	
})();