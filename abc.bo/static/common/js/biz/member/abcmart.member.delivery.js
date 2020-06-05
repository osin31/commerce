/***
 * 회원 배송지 목록
 * 
 */
(function() {
	
	var _delivery = abc.object.createNestedObject(window,"abc.biz.member.delivery");
	
	/**
	 * 회원 목록 그리드 초기 세팅
	 */
	_delivery.initMemberDeliverySheet = function() {
		//
		createIBSheet2(document.getElementById("deliveryGrid"), "deliverySheet", "100%", "370px");
		
		// Grid 초기화 기본/헤더/컬럼선언
		var initSheet = {};
		var pageCount = $('#pageCount').val();

		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MaxSort:1};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};	
		initSheet.Cols = [
			  {Header:"" ,		 		Type:"Status",		SaveName:"status",			Width: 0,  Align:"", 		Edit:0, Hidden:1, Sort:0}
			, {Header:"회원번호", 			Type:"Text",		SaveName:"memberNo",		Width: 0,  Align:"", 		Edit:0, Hidden:1, Sort:0}
			, {Header:"번호", 			Type:"Seq",			SaveName:"dlvyAddrSeq",		Width: 5,  Align:"Center", 	Edit:0, Sort:0}
			, {Header:"배송지명", 			Type:"Text",		SaveName:"dlvyAddrName",	Width: 15, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"받는사람", 			Type:"Text",		SaveName:"rcvrName",		Width: 15, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"휴대폰번호", 		Type:"Text",		SaveName:"hdphnNoText",		Width: 15, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"배송지주소", 		Type:"Text",		SaveName:"dtlAddressText",	Width: 50, Align:"Left", 	Edit:0, Sort:0}
		];
		
		// Grid 초기화
		IBS_InitSheet(deliverySheet , initSheet);
		// Grid 건수 정보 표시
		deliverySheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		deliverySheet.SetPagingPosition(2);
		// Grid width 자동 조절
		deliverySheet.FitColWidth();
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		deliverySheet.SetExtendLastCol(1);
		
		_delivery.doMemberDeliveryAction('search');
	}
	
	_delivery.doMemberDeliveryAction = function(sAction) {
		switch(sAction) {
		// 조회 
		case "search" :
			var pageCount = $("#pageCount").val();
			
			var param = { url : "/member/read-member-delivery-list"
				, onePageRow : pageCount
				, subparam : FormQueryStringEnc(document.frmSearch)
				, sheet : "deliverySheet" };
			
			DataSearchPaging(param);
			
			break;
	}
	}
	
	
})();