/***
 * 입점관리 > 예외 수수료 적용 이력 
 * 
 */
(function() {

	var _history = abc.object.createNestedObject(window,"abc.biz.vendor.info.commission.history");

	_history.initHistorySheet = function() {
		createIBSheet2(document.getElementById("historyGrid"), "historySheet", "100%", "480px");
		var initHistorySheet = {};
		var pageCount = "15"; // $('#pageCount').val();
		
		initHistorySheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MaxSort:1};
		initHistorySheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initHistorySheet.Cols = [
				  {Header:"" ,		 		Type:"Status",		SaveName:"status",				Width: 0,   Align:"", 			Edit:0, Hidden:1}
				, {Header:"번호", 			Type:"Seq",			SaveName:"",					Width: 50,  Align:"Center", 	Edit:0, Sort:0}
				, {Header:"수수료 구분", 		Type:"Text",		SaveName:"commissionGubun",			Width: 150, Align:"Center", 	Edit:0, Sort:0, Hidden:1}
				, {Header:"수수료 구분", 		Type:"Text",		SaveName:"commissionGubunText",		Width: 150, Align:"Center", 	Edit:0, Sort:0}
				, {Header:"표준카테고리",		Type:"Text",		SaveName:"commissionstdCtgrName",	Width: 170, Align:"Center",		Edit:0, Sort:1}
				, {Header:"브랜드", 			Type:"Text",		SaveName:"commissionBrandName",		Width: 170, Align:"Center", 	Edit:0, Sort:1}
				, {Header:"적용대상 수수료율", 	Type:"Text",		SaveName:"cmsnRate",			Width: 130, Align:"Right", 		Edit:0, Sort:0}
				, {Header:"적용기간", 			Type:"Text",		SaveName:"applyPeriod",			Width: 200, Align:"Center", 	Edit:0, Sort:0}
				, {Header:"관리자 메모", 		Type:"Text",		SaveName:"noteText",			Width: 200, Align:"Center", 	Edit:0, Sort:0}
				, {Header:"등록자", 			Type:"Text",		SaveName:"rgsterName",			Width: 130, Align:"Center", 	Edit:0, Sort:0} 
				, {Header:"등록일시", 			Type:"Date",		SaveName:"rgstDtm",				Width: 130, Align:"Center", 	Edit:0, Sort:1}
				, {Header:"수정자", 			Type:"Text",		SaveName:"moderName",			Width: 130, Align:"Center", 	Edit:0, Sort:0} 
				, {Header:"수정일시", 			Type:"Date",		SaveName:"modDtm",				Width: 130, Align:"Center", 	Edit:0, Sort:1}
		];

		
		// Grid 초기화
		IBS_InitSheet(historySheet, initHistorySheet);
		// Grid 건수 정보 표시
		historySheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		historySheet.SetPagingPosition(2);
		// Grid width 자동 조절
	//	historySheet.FitColWidth();
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		historySheet.SetExtendLastCol(1);
		
		_history.doAction("search");
		
	}
	
	/**
	 * 관리자 목록 Action관리
	 */
	_history.doAction = function(sAction){
		switch(sAction) {
			// 조회 
			case "search" :

				var pageCount = "15"; //$('#pageCount').val();

				var param = { url : "/vendor/info/read-commission-history"
					, onePageRow : pageCount
					, subparam : FormQueryStringEnc(document.historyForm)
					, sheet : "historySheet" };
				
				DataSearchPaging(param);
				
				break;
		}
	}
	
})();	