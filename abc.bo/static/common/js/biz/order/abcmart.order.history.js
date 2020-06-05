/**
 * 주문/클레임 히스토리
 */
(function(){
	
	var _history = abc.object.createNestedObject(window,"abc.biz.order.delivery.history");
	
	_history.SITE_NO_ART = "";
	_history.ABC_FO_URL  = "";
	_history.OTS_FO_URL  = "";
	
	_history.init = function(historyList) {
		createIBSheet2(document.getElementById("listGrid"), "listSheet", "100%", "370px");

		// Grid 초기화 기본/헤더/컬럼선언
		var initSheet = {};
		var pageCount = $('#pageCount').val();
		
		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MaxSort:1};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};	
		
		initSheet.Cols = [
			{Header:"처리내용",	Type:"Text",	SaveName:"orderPrdtStatCode",	Width: 10, Align:"Center", 	Sort:0,	Edit:0}			
			, {Header:"비고", 	Type:"Text",	SaveName:"noteText",			Width: 10, Align:"Center", 	Sort:0,	Edit:0}
			, {Header:"처리자", 	Type:"Text",	SaveName:"adminInfo",			Width: 10, Align:"Center", 	FontBold:1, FontUnderline:1, Cursor:"Pointer", Sort:0,	Edit:0}
			, {Header:"",		Type:"Text",	SaveName:"rgsterNo", 	Hidden:1}
			, {Header:"",		Type:"Text",	SaveName:"adminId",	 	Hidden:1}
			, {Header:"",		Type:"Text",	SaveName:"adminName",	Hidden:1}
			, {Header:"처리일시", 	Type:"Text",	SaveName:"rgstDtm",				Width: 10, Align:"Center", 	Sort:1,	Format:abc.consts.DEFAULT_DATETIME_PATTERN, Edit:0}
		];
		
		// Grid 초기화
		IBS_InitSheet(listSheet , initSheet);
		// Grid width 자동 조절
		listSheet.FitColWidth();
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		listSheet.SetExtendLastCol(1);
		
		_history.search();		
	}
	
	_history.search = function(){
		var param = { url : "/order/delivery-history/list"
			, subparam : FormQueryStringEnc(document.listForm)
			, sheet : "listSheet" };
		
		DataSearchPaging(param);
	}
	
	/**
	 * BO 상품 상세 팝업 호출
	 */
	_history.boPrdtDetailPop = function(el){
		
		var param = { prdtNo : $(el).attr("prdtno") };
		abc.readonlyProductDetailPopup(param);
	}
	
	/**
	 * FO 상품 상세 팝업 호출
	 */
	_history.foPrdtDetailPop = function(el){
		
		if($("#siteNo").val() == _history.SITE_NO_ART){
			window.open(_history.ABC_FO_URL + "/product?prdtNo=" + $(el).attr("prdtno"))
		} else {
			window.open(_history.OTS_FO_URL + "/product?prdtNo=" + $(el).attr("prdtno"))
		}	
	}
	
	/**
	 * 입점사 상세 팝업 호출
	 */
	_history.vndrDetailPop = function(el){
	
		abc.vendorInfoPop( $(el).attr("vndrno"));
	}
	
})();