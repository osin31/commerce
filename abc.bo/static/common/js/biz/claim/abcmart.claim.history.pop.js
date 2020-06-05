/**
 * 클레임 히스토리
 */
(function(){
	
	var _history = abc.object.createNestedObject(window,"abc.biz.claim.history.pop");
	
	/*************************************************************************
	 *								클레임 이력 목록 변수 선언
	 *************************************************************************/
	
	//비회원 회원 번호 MEMBER_NO
	_history.NON_MEMBER_NO			= "";
	
	//클레임 구분 코드 : 취소
	_history.CLM_GBN_CODE_CANCEL	= "";
	//클레임 구분 코드 : 교환
	_history.CLM_GBN_CODE_EXCHANGE	= "";
	//클레임 구분 코드 : 반품
	_history.CLM_GBN_CODE_RETURN	= "";
	
	_history.ABC_FO_URL = "";
	_history.OTS_FO_URL = "";
	
	_history.SITE_NO_ART = "";
	_history.SITE_NO_OTS = "";
	
	/*************************************************************************
	 *								클레임 이력 목록 S
	 *************************************************************************/
	
	var pageCount = 15;
	
	_history.init = function(historyList) {
		createIBSheet2(document.getElementById("listGrid"), "listSheet", "100%", "540px");

		// Grid 초기화 기본/헤더/컬럼선언
		var initSheet = {};
		
		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MaxSort:1};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};	
		
		initSheet.Cols = [
			  {Header:"처리내용",	Type:"Text",	SaveName:"clmPrdtStatCodeName",	Width: 10, Align:"Center", 	Sort:0,	Edit:0}			
			, {Header:"비고", 	Type:"Text",	SaveName:"noteText",			Width: 10, Align:"Center", 	Sort:0,	Edit:0}
			, {Header:"처리자", 	Type:"Text",	SaveName:"displayAdmin",		Width: 10, Align:"Center", 	FontBold:1, FontUnderline:1, Cursor:"Pointer", Sort:0,	Edit:0}
			, {Header:"",		Type:"Text",	SaveName:"rgsterNo", 			Hidden:1}
			, {Header:"",		Type:"Text",	SaveName:"loginId",	 			Hidden:1}
			, {Header:"",		Type:"Text",	SaveName:"adminName",			Hidden:1}
			, {Header:"",		Type:"Text",	SaveName:"rgsterNo",			Hidden:1}
			, {Header:"처리일시", 	Type:"Text",	SaveName:"rgstDtm",				Width: 10, Align:"Center", 	Sort:0,	Format:abc.consts.DEFAULT_DATETIME_PATTERN, Edit:0}
			, {Header:"",		Type:"Text",	SaveName:"logisVndrCodeName",	Hidden:1}
			, {Header:"",		Type:"Text",	SaveName:"waybilNoText",		Hidden:1}
		];
		
		// Grid 초기화
		IBS_InitSheet(listSheet , initSheet);
		// Grid width 자동 조절
		listSheet.FitColWidth();
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		listSheet.SetExtendLastCol(1);
		// Grid 건수 정보 표시
		listSheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		listSheet.SetPagingPosition(2);
		
		_history.search();
	}
	
	/**
	 * 그리드 Click 이벤트
	 */
	_history.listSheetOnClick = function(Row, Col, Value, CellX, CellY, CellW, CellH){
		
		if ( Row != 0) {
			if ( listSheet.ColSaveName(Col) == "displayAdmin" && Value != "" ) {
					
				var rgsterNo = listSheet.GetCellValue(Row, "rgsterNo");
				
				if( rgsterNo != _history.NON_MEMBER_NO && !rgsterNo.includes("MB")){
					abc.adminDetailPopup(rgsterNo);
				}
			}
		}
	}
	
	/**
	 * 그리드 Search 후 이벤트
	 */
	_history.listSheetOnRowSearchEnd = function(Row){
		
		var rgsterNo = listSheet.GetCellValue(Row, "rgsterNo");
		var displayAdmin = listSheet.GetCellValue(Row, "displayAdmin");
		
		if( abc.text.allNull(displayAdmin) ){
			// 칼럼 값 변경
			listSheet.SetCellValue( Row, "displayAdmin", "사용자" ); 
			// 밑줄 제거
			listSheet.SetCellFontUnderline( Row ,"displayAdmin" , 0 ) ;
		}
	}
	
	/**
	 * 검색
	 */
	_history.search = function(){
		var param = { url : "/claim/claim/read-claim-histroy-list"
			, onePageRow : pageCount
			, subparam : FormQueryStringEnc(document.listForm)
			, sheet : "listSheet" };
		
		DataSearchPaging(param);
	}
	
	/*************************************************************************
	 *								클레임 이력 목록 E
	 *************************************************************************/
	
	
	/*************************************************************************
	 *								팝업 호출 함수 S
	 *************************************************************************/
	
	/**
	 * 클레임 상세 팝업 호출
	 */
	_history.openClaimDetailPop = function(clmNo, clmGbnCode){
		var url = "";
		var params = {clmNo : clmNo, clmGbnCode : clmGbnCode};

		if(clmGbnCode == _history.CLM_GBN_CODE_CANCEL) { //취소
			url = "/claim/claim/read-claim-cancel-detail-pop"; // 클레임 취소 상세
		} else if(clmGbnCode == _history.CLM_GBN_CODE_EXCHANGE) { //교환
			url = "/claim/claim/read-claim-exchange-detail-pop"; // 클레임 교환 상세
		} else if(clmGbnCode == _history.CLM_GBN_CODE_RETURN) { //반품
			url = "/claim/claim/read-claim-return-detail-pop"; // 클레임 반품 상세
		}
		
		abc.open.popup({
			winname :	"claimDetailPop",
			url 	:	url,
			width 	:	1240,
			height	:	990,
			params	:	params
		});
	}
	
	/**
	 * BO 상품 상세 팝업 호출
	 */
	_history.boPrdtDetailPop = function(){
		
		var param = { prdtNo : $("#prdtNo").val() };
		abc.readonlyProductDetailPopup(param);
	}
	
	/**
	 * FO 상품 상세 팝업 호출
	 */
	_history.foPrdtDetailPop = function(){
		
		if($("#siteNo").val() == _history.SITE_NO_ART){
			window.open(_history.ABC_FO_URL + "/product?prdtNo=" + $("#prdtNo").val())
		} else {
			window.open(_history.OTS_FO_URL + "/product?prdtNo=" + $("#prdtNo").val())
		}	
	}
	
	/**
	 * 입점사 상세 팝업 호출
	 */
	_history.vndrDetailPop = function(){
	
		abc.vendorInfoPop( $("#vndrNo").val() );
	}
	
	
	/*************************************************************************
	 *								팝업 호출 함수 E
	 *************************************************************************/
})();