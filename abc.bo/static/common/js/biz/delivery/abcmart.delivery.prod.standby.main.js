/***
 * 주문관리 > 배송관리 > 상품대기조회
 * 
 */
(function() {

		//스크립트 Object선언		
		var _deliveryJsObject = abc.object.createNestedObject(window,"abc.biz.delivery.prod.standby.main");

		_deliveryJsObject.initDataListSheet = function() {
		createIBSheet2(document.getElementById("dataListGrid"), "dataListSheet", "100%", "429px");
		
		var initDataListSheet = {};
		var pageCount = $('#pageCount').val();
		
		initDataListSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MaxSort:1};
		initDataListSheet.Cols = [
				  {Header:"" ,		 		Type:"Status",				SaveName:"status",					Width: 0,		 Align:"", 			Edit:0, Hidden:1}
				, {Header:"", 				Type:"CheckBox",			SaveName:"checkedRows",						Width: 15,		 Align:"Center",	Sort:0}
				, {Header:"사이트_d", 		Type:"Seq",					SaveName:"",						Width: 15,		 Align:"Center", 	Edit:0, Sort:0}
				, {Header:"주문번호", 			Type:"Text",				SaveName:"vndrNo",					Width: 40,		 Align:"Center", 	Edit:0}
				, {Header:"배송ID",			Type:"Text",				SaveName:"vndrName",				Width: 45,		 Align:"Center",	Edit:0, Sort:0}
				, {Header:"상품코드", 			Type:"Text",				SaveName:"bossName",				Width: 35,		 Align:"Center", 	Edit:0, Sort:0}
				, {Header:"상품명", 			Type:"Text",				SaveName:"bizNoText",				Width: 35,		 Align:"Left", 		Edit:0, Sort:0}
				, {Header:"옵션", 			Type:"Text",				SaveName:"rprsntTelNoText",			Width: 35,		 Align:"Center", 	Edit:0, Sort:0}
				, {Header:"주문자", 			Type:"Text",				SaveName:"faxNoText",				Width: 35,		 Align:"Center", 	Edit:0, Sort:0}
				, {Header:"미출처", 			Type:"Text",				SaveName:"chnnlNames",				Width: 80,		 Align:"Center", 	Edit:0, Sort:0}
				, {Header:"배송유형", 			Type:"Text",				SaveName:"adminNames",				Width: 30,		 Align:"Center", 	Edit:0, Sort:0}
				, {Header:"배송ID",			Type:"Text",				SaveName:"vndrStatCode",			Width: 30,		 Align:"Center", 	Edit:0, Sort:0}
				, {Header:"발생일시",			Type:"Text",				SaveName:"dfltCmsnRate",			Width: 40,		 Align:"Center", 	Edit:0}
				, {Header:"처리일시",			Type:"Text",				SaveName:"exceptionYn",				Width: 40,		 Align:"Center", 	Edit:0, Sort:0}
				, {Header:"처리자",			Type:"Text",				SaveName:"employeeYn",				Width: 40,		 Align:"Center", 	Edit:0, Sort:0}
				, {Header:"처리상태",			Type:"Text",				SaveName:"vendorYn",				Width: 40,		 Align:"Center", 	Edit:0, Sort:0}
				, {Header:"배송중단 사유",		Type:"Date",				SaveName:"rgstDtm",					Width: 35,		 Align:"Center", 	Edit:0, Sort:0}
				, {Header:"발송매장",			Type:"Text",	SaveName:"storeName",			Width: 100,		 Align:"Center", 	Edit:0, Hidden:1}
				];

		// Grid 초기화
		IBS_InitSheet(dataListSheet, initDataListSheet);
		// Grid 건수 정보 표시
		dataListSheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		dataListSheet.SetPagingPosition(2);
		// Grid width 자동 조절
		dataListSheet.FitColWidth();
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		dataListSheet.SetExtendLastCol(1);
	}	 
	
})();	