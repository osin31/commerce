/***
 * 권한그룹 관리.
 * 
 */
(function() {
	/*************************************************************************
	 *					권한그룹관리 : 권한그룹 변경이력
	 *************************************************************************/
	var _authHistory = abc.object.createNestedObject(window,"abc.biz.system.admin.authhistory");

	/**
	 *  sheet init
	 */
	_authHistory.initHistorySheet = function() {
		createIBSheet2(document.getElementById("historyGrid"), "historySheet", "100%", "370px");
		
		var initHistorySheet = {};
		var pageCount = $('#pageCount').val();

		initHistorySheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MaxSort:1};
		initHistorySheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};	
		initHistorySheet.Cols = [
			  {Header:"" ,		 		Type:"Status",		SaveName:"status",				Width: 0,  Align:"", 		Edit:0, Hidden:1}
			, {Header:"번호", 			Type:"Seq",			SaveName:"",					Width: 10, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"권한 그룹명", 		Type:"Text",		SaveName:"authName",			Width: 20, Align:"Left", 	Edit:0, Sort:0}
			, {Header:"구분" ,			Type:"Text",		SaveName:"chngFieldName",		Width: 30,  Align:"Center", Edit:0, Sort:0}
			, {Header:"변경내용(변경 전 > 변경 후)", 	Type:"Text",	SaveName:"chngFieldValue",	Width: 50, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"수정자", 			Type:"Text",		SaveName:"rgsterName",			Width: 20, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"수정일시", 			Type:"Date",		SaveName:"rgstDtm",				Width: 30, Align:"Center", 	Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
		];
		
		IBS_InitSheet(historySheet , initHistorySheet);
		historySheet.SetCountPosition(3);

		historySheet.SetPagingPosition(2);

		historySheet.FitColWidth();

		historySheet.SetExtendLastCol(1);
		
	}
	
	_authHistory.doActionHistory= function(sAction){
		switch (sAction) {
		case "search":

			var pageCount = $('#pageCount').val();
			
			var param = { url : "/system/admin/authority/history-read-list"
				, onePageRow : pageCount
				, subparam : FormQueryStringEnc(document.frmSearch)
				, sheet : "historySheet" };

			DataSearchPaging(param);
			
			break;
		}
	}
	
	
})();