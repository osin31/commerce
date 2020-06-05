/***
 * 조건날짜 관리 업무와 관련된 함수 정의. 
 * 
 */
(function() {

	var _dayscondtn = abc.object.createNestedObject(window,"abc.biz.cmm.dayscondtn");
	
	/*************************************************************************
	 *							조건 날짜 관리 목록
	 *************************************************************************/
	
	/**
	 * 조건날짜 목록 초기 세팅
	 */
	_dayscondtn.initDaysSheet = function() {
		//
		createIBSheet2(document.getElementById("dasyGrid"), "daysSheet", "100%", "370px");
		
		// Grid 초기화 기본/헤더/컬럼선언
		var initSheet = {};
		var pageCount = $('#pageCount').val();

		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MaxSort:1};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};	
		initSheet.Cols = [
			  {Header:"" ,		 		Type:"Status",		SaveName:"status",				Width: 0,  Align:"", 		Edit:0, Hidden:1}
			, {Header:"조건설정순번", 		Type:"Text",		SaveName:"condtnSetupSeq",		Width: 10, Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"조건 기간명", 		Type:"Text",		SaveName:"condtnTermName",		Width: 30, Align:"Left", 	Edit:0}
			, {Header:"조건 설명", 		Type:"Text",		SaveName:"condtnDescInfo",		Width: 30, Align:"Left", 	Edit:0}
			, {Header:"조건 기간 값(*)", 	Type:"Text",		SaveName:"condtnTermValue",		Width: 10, Align:"Center", 	Edit:1, AcceptKeys: "N"}
			, {Header:"조건 타입", 		Type:"Text",		SaveName:"condtnType",			Width: 10, Align:"Center", 	Edit:0}
			, {Header:"수정자", 			Type:"Text",		SaveName:"moderName",			Width: 15, Align:"Center", 	Edit:0}
			, {Header:"수정일시", 			Type:"Text",		SaveName:"modDtm",				Width: 20, Align:"Center", 	Edit:0, Format:"YmdHms"}
			, {Header:"작성자", 			Type:"Text",		SaveName:"rgsterName",			Width: 15, Align:"Center", 	Edit:0}
			, {Header:"작성일시", 			Type:"Text",		SaveName:"rgstDtm",				Width: 20, Align:"Center", 	Edit:0, Format:"YmdHms"}
		];
		
		// Grid 초기화
		IBS_InitSheet(daysSheet , initSheet);
		// Grid 건수 정보 표시
		daysSheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		daysSheet.SetPagingPosition(2);
		// Grid width 자동 조절
		daysSheet.FitColWidth();
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		daysSheet.SetExtendLastCol(1);
		
		abc.biz.cmm.dayscondtn.daysDoAction('search');
	}
	
	/**
	 * 조건날짜 목록 Action관리
	 */
	_dayscondtn.daysDoAction = function(sAction){
		switch(sAction) {
			// 조회 
			case "search" :
				var pageCount = $('#pageCount').val();
				
				var param = { url : "/cmm/days-condtn/read"
					, onePageRow : pageCount
					, sheet : "daysSheet" };
				
				DataSearchPaging(param);
				
				break;
			case "save" :
				
				if(confirm("저장하시겠습니까? ")){
					var param = { url:"/cmm/days-condtn/update"
						, sheet:"daysSheet"};
					
					DataSave(param);
				}
				
				break;
		}
	}
	
})();