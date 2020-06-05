/***
 *  주문 정보 tab 업무와 관련된 함수 정의.
 * 
 */
(function() {
	
	var _orderInfo = abc.object.createNestedObject(window,"abc.biz.order.info.tab.kakaoPay"); 
	
	/*************************************************************************
	 *						카카오 페이 결제 내역 
	 *************************************************************************/
	
	/**
	 * ABC 자사 상품 초기 세팅
	 */
	_orderInfo.initKakaoPayGrid = function() {
		//
		createIBSheet2(document.getElementById("kakaoPayGrid"), "kakaoPaySheet", "100%", "370px");
		
		// Grid 초기화 기본/헤더/컬럼선언
		var initSheet = {};

		initSheet.Cfg = {SearchMode:smLazyLoad, MaxSort:1 , MergeSheet:msHeaderOnly};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:0};	
		initSheet.Cols = [
			  {Header:"" ,		 		Type:"Status",	SaveName:"status",					Width: 0,  	Align:"", 		Edit:0, Hidden:1}
			, {Header:"원결제번호", 		Type:"Text",	SaveName:"aid",						Width: 90, 	Align:"Center", Edit:0}
			, {Header:"결제승인유형", 		Type:"Combo",	SaveName:"payment_action_type",		Width: 90, 	Align:"Center", Edit:0}
			, {Header:"카드사/은행",		Type:"Html",	SaveName:"payInfo",					Width: 150, Align:"Center", Edit:0}
			, {Header:"결제/취소금액", 		Type:"Float",	SaveName:"amount",					Width: 90, 	Align:"Right", 	Edit:0 , Format: "#,##0원"}
			, {Header:"결제/취소일시", 		Type:"Date",	SaveName:"approved_at",				Width: 100, Align:"Center", Edit:0 , Format: "YmdHms"}
			, {Header:"카카오페이결제수단",	Type:"Text",	SaveName:"paymentMethodTypeView",	Width: 150, Align:"Center", Edit:0 , Hidden:1 }
			, {Header:"카드bin",			Type:"Text",	SaveName:"cardBinView",				Width: 150, Align:"Center", Edit:0 , Hidden:1 }
			, {Header:"할부개월수",			Type:"Text",	SaveName:"installMonthView",		Width: 150, Align:"Center", Edit:0 , Hidden:1 }
			, {Header:"카드명",			Type:"Text",	SaveName:"cardCorpName",			Width: 150, Align:"Center", Edit:0 , Hidden:1 }
			, {Header:"무이자여부",			Type:"Text",	SaveName:"interestFreeInstallView",	Width: 150, Align:"Center", Edit:0 , Hidden:1 }
		];
		
		// Grid 초기화
		IBS_InitSheet(kakaoPaySheet , initSheet);
		// Grid 건수 정보 표시
		kakaoPaySheet.SetCountPosition(0);
		// Grid width 자동 조절
		kakaoPaySheet.FitColWidth();
		//kakaoPaySheet.SetRowHeightMin(40);
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		kakaoPaySheet.SetExtendLastCol(1);
		
		kakaoPaySheet.InitDataCombo(0 , "payment_action_type" , "승인|취소" , "PAYMENT|CANCEL"  );
		
		abc.biz.order.info.tab.kakaoPay.DoAction("search"); // 조회실행
	}
	
	/**
	 * 관리자 목록 Action관리
	 */
	_orderInfo.DoAction = function(sAction){
		switch(sAction) {
			// 조회 
			case "search" :
				var param = {
						url : "/order/kakaoPay/history-list"
						, subparam : FormQueryStringEnc(document.frmSearch)
						, sheet : "kakaoPaySheet"
					};

					DataSearch(param);
					
				break;
		}
	}
	
	
})();