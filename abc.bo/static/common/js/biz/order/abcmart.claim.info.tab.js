/***
 *  클레임 정보 tab 업무와 관련된 함수 정의.
 * 
 */
(function() {
	
	var _claimInfo = abc.object.createNestedObject(window,"abc.biz.order.claim.info.tab"); 
	
	/*************************************************************************
	 *								클레임관리 탭 변수 선언
	 *************************************************************************/
	
	//클레임 구분 코드 : 취소
	_claimInfo.CLM_GBN_CODE_CANCEL		= "";
	//클레임 구분 코드 : 교환
	_claimInfo.CLM_GBN_CODE_EXCHANGE	= "";
	//클레임 구분 코드 : 반품
	_claimInfo.CLM_GBN_CODE_RETURN		= "";
	
	_claimInfo.PYMNT_STAT_CODE_REFUND_ACCEPT = "";
	_claimInfo.PYMNT_STAT_CODE_REFUND_COMPLETE = "";
	_claimInfo.PYMNT_STAT_CODE_REDEMP_ACCEPT = "";
	_claimInfo.PYMNT_STAT_CODE_REDEMP_COMPLETE = "";
	
	/*************************************************************************
	 *								클레임 할인 취소 그리드 영역 s
	 *************************************************************************/
	/**
	 * 클레임 할인 취소 초기 세팅
	 */
	_claimInfo.initClaimDiscountCancelInfo = function() {
		// 그리드 객체, 메모리 초기화
		if(typeof claimDiscountCancelInfoSheet != 'undefined'){
			claimDiscountCancelInfoSheet.Reset();
			claimDiscountCancelInfoSheet.DisposeSheet();
		}
		
		// Grid 사이즈
		createIBSheet2(document.getElementById("claimDiscountCancelInfo"), "claimDiscountCancelInfoSheet", "100%", "370px");
		
		// Grid 초기화 기본/헤더/컬럼선언
		var initSheet = {};

		initSheet.Cfg = {SearchMode:smLazyLoad, MaxSort:1};
		initSheet.HeaderMode = {Sort:0, ColMove:0, ColResize:1};	
		initSheet.Cols = [
			{Header:"" ,		 		Type:"Status",		SaveName:"status",				Width: 0, 	Align:"", 			Edit:0, Hidden:1}
			//, {Header:"",				Type:"CheckBox",	SaveName:"updateCheck",	Width: 5,  Align:"Center",	Edit:1, Sort:0}
			, {Header:"할인구분", 			Type:"Text",		SaveName:"discountGbn",			Width: 80, Align:"Center", 	Edit:0}
			, {Header:"내용", 			Type:"Html",		SaveName:"discountTxt",			Width: 200, Align:"Left", 		Edit:0}
			, {Header:"할인금액(율)", 		Type:"Float",		SaveName:"discountAmt",			Width: 90, Align:"Right", 		Edit:0, Format: "#,##0원"}
			, {Header:"적용상품코드", 		Type:"Text",		SaveName:"discountPrdtNo",		Width: 130, Align:"Center", 	Edit:0}
			, {Header:"적용상품명", 		Type:"Text",		SaveName:"discountPrdtName",	Width: 200, Align:"Center", 	Edit:0}
			, {Header:"수량", 			Type:"Text",		SaveName:"",					Width: 50, 	Align:"Center", 	Edit:0}
			, {Header:"ID", 			Type:"Text",		SaveName:"discountId",			Width: 20, 	Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"TXT", 			Type:"Text",		SaveName:"discountTxt",			Width: 20, 	Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"STDT", 			Type:"Text",		SaveName:"discountStartDt",		Width: 20, 	Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"ENDT", 			Type:"Text",		SaveName:"discountEndDt",		Width: 20, 	Align:"Center", 	Edit:0, Hidden:1}
			];
		
		// Grid 초기화
		IBS_InitSheet(claimDiscountCancelInfoSheet , initSheet);
		// Grid 건수 정보 표시
		claimDiscountCancelInfoSheet.SetCountPosition(0);
		// Grid width 자동 조절
		//claimDiscountCancelInfoSheet.FitColWidth();
		//claimDiscountCancelInfoSheet.SetRowHeightMin(40); 
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		claimDiscountCancelInfoSheet.SetExtendLastCol(1);
		
		abc.biz.order.claim.info.tab.claimDiscountCancelInfoDoAction("search"); // 조회실행
	}
	
	/**
	 * 클레임 할인 취소 Action관리
	 */
	_claimInfo.claimDiscountCancelInfoDoAction = function(sAction){
		switch(sAction) {
			// 조회
			case "search" :
				var param = {
					url : "/claim/claim/read-discount-cancel-list-intab"
					, subparam : FormQueryStringEnc(document.frmSearch)
					, sheet : "claimDiscountCancelInfoSheet"
				};

				DataSearch(param);
				break;
		}
	}
	
	/*************************************************************************
	 *								클레임 할인 취소 그리드 영역 E
	 *************************************************************************/
	
	/*************************************************************************
	 *								환수/환불금액관리 그리드 영역 s
	 *************************************************************************/
	/**
	 * 클레임 환수/환불금액관리 초기 세팅
	 */
	_claimInfo.initClaimRedemptionRefundInfo = function() {
		// 그리드 객체, 메모리 초기화
		if(typeof claimRedemptionRefundInfoSheet != 'undefined'){
			claimRedemptionRefundInfoSheet.Reset();
			claimRedemptionRefundInfoSheet.DisposeSheet();
		}
		
		// Grid 사이즈
		createIBSheet2(document.getElementById("claimRedemptionRefundInfo"), "claimRedemptionRefundInfoSheet", "100%", "370px");
		
		// Grid 초기화 기본/헤더/컬럼선언
		var initSheet = {};
		
		initSheet.Cfg = {SearchMode:smLazyLoad, MaxSort:1};
		initSheet.HeaderMode = {Sort:0, ColMove:0, ColResize:1};	
		initSheet.Cols = [
			  {Header:"상태" ,		 		Type:"Status",		SaveName:"status",			  Width: 0,  Align:"", 			Edit:0, Hidden:1}
			, {Header:"클레임구분코드" ,		 	Type:"Text",		SaveName:"clmGbnCode",		  Width: 0,  Align:"", 			Edit:0, Hidden:1}
			, {Header:"구분", 				Type:"Text",		SaveName:"redempRfndGbnName", Width: 10, Align:"Center", 	Edit:0}
			, {Header:"발생사유", 				Type:"Text",		SaveName:"ocrncRsnCodeName",  Width: 20, Align:"Center", 	Edit:0}
			, {Header:"사이트", 				Type:"Text",		SaveName:"clmSiteName",		  Width: 20, Align:"Center", 	Edit:0}
			, {Header:"클레임번호", 			Type:"Text",		SaveName:"clmNo",			  Width: 20, Align:"Center", 	Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer"}
			, {Header:"금액", 			  	Type:"Float",		SaveName:"pymntAmt",		  Width: 25, Align:"Center", 	Edit:0, Format:"#,##0 원"}
			, {Header:"지(수)급상태", 			Type:"Text",		SaveName:"pymntStatCodeName", Width: 25, Align:"Center", 	Edit:0}
			, {Header:"환수/환불 금액 발생일시", 	Type:"Text",		SaveName:"rgstDtm",			  Width: 25, Align:"Center", 	Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			, {Header:"처리자", 				Type:"Text",		SaveName:"clmHandler",		  Width: 25, Align:"Center", 	Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer"}
			, {Header:"완료일시", 				Type:"Text",		SaveName:"modDtm",			  Width: 25, Align:"Center", 	Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			, {Header:"처리자번호",				Type:"Text",		SaveName:"moderNo",						 Align:"Center", 	Edit:0, Sort:0, Hidden:1}
			, {Header:"구분", 				Type:"Text",		SaveName:"redempRfndGbnType", Width: 10, Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"지(수)급상태코드",		Type:"Text",		SaveName:"pymntStatCode",     Width: 4,  Align:"Center",    Edit:0, Sort:0, Hidden:1}
			, {Header:"가상계좌 소멸일시", 		Type:"Date",		SaveName:"vrtlAcntExprDtm",	  Width: 13, Align:"Center",    Edit:0, Sort:1,	Format:abc.consts.DEFAULT_DATETIME_PATTERN, Hidden:1}
			];
		
		// Grid 초기화
		IBS_InitSheet(claimRedemptionRefundInfoSheet , initSheet);
		// Grid 건수 정보 표시
		claimRedemptionRefundInfoSheet.SetCountPosition(3);
		
		// 구분
		claimRedemptionRefundInfoSheet.SetColWidth(2, 40);
		// 발생사유
		claimRedemptionRefundInfoSheet.SetColWidth(3, 130);
		// 사이트
		claimRedemptionRefundInfoSheet.SetColWidth(4, 80);
		// 클레임번호
		claimRedemptionRefundInfoSheet.SetColWidth(5, 100);
		// 금액
		claimRedemptionRefundInfoSheet.SetColWidth(6, 90);
		// 지(수)급상태
		claimRedemptionRefundInfoSheet.SetColWidth(7, 90);
		// 발생일시
		claimRedemptionRefundInfoSheet.SetColWidth(8, 140);
		// 처리자
		claimRedemptionRefundInfoSheet.SetColWidth(9, 120);
		// 완료일시
		claimRedemptionRefundInfoSheet.SetColWidth(10, 140);
		
		abc.biz.order.claim.info.tab.claimRedemptionRefundInfoDoAction("search"); // 조회실행
	}
	
	/**
	 * 환수/환불금약관리 그리드 Click 이벤트
	 */
	_claimInfo.claimRedemptionRefundInfoSheetOnClick = function(Row, Col, Value, CellX, CellY, CellW, CellH){
		
		if ( Row != 0 ) {
			
			if ( claimRedemptionRefundInfoSheet.ColSaveName(Col) == "clmNo" && Value != "" ) {
				
				var clmNo = claimRedemptionRefundInfoSheet.GetRowData(Row).clmNo;
				var clmGbnCode = claimRedemptionRefundInfoSheet.GetRowData(Row).clmGbnCode;
				// 클레임 상세 팝업 호출
				abc.biz.order.claim.info.tab.openClaimDetailPop(clmNo, clmGbnCode);
			}
			else if ( claimRedemptionRefundInfoSheet.ColSaveName(Col) == "clmHandler" && Value != "" ) {
			
				var adminNo = claimRedemptionRefundInfoSheet.GetRowData(Row).moderNo;
				abc.adminDetailPopup(adminNo);
			}
		}
	}
	
	/**
	 * 클레임 환수/환불금액관리 Action관리
	 */
	_claimInfo.claimRedemptionRefundInfoDoAction = function(sAction){
		switch(sAction) {
			// 조회
			case "search" :
				var param = {
					url : "/claim/claim/read-redemption-refund-list-intab"
					, subparam : FormQueryStringEnc(document.frmSearch)
					, sheet : "claimRedemptionRefundInfoSheet"
				};
	
				DataSearch(param);
				break;
		}
	}
	
	_claimInfo.claimRedemptionRefundInfoSheetOnSearchEnd = function(){
		
		var rowCnt = claimRedemptionRefundInfoSheet.RowCount();
		
		for(var i=1; i<=claimRedemptionRefundInfoSheet.GetDataLastRow(); i++){
			
			var redempRfndGbnType = claimRedemptionRefundInfoSheet.GetRowData(i).redempRfndGbnType;
			
			if( redempRfndGbnType == "F" ){
				
				claimRedemptionRefundInfoSheet.SetRowData(i, {redempRfndGbnName : "환불"});
			} else if( redempRfndGbnType == "E" ){
				
				claimRedemptionRefundInfoSheet.SetRowData(i, {redempRfndGbnName : "환수"});
			}
			
			// 입금대기,입금완료,입금기한만료
			var rgstDtm = claimRedemptionRefundInfoSheet.GetRowData(i).rgstDtm;
			var vrtlAcntExprDtm = claimRedemptionRefundInfoSheet.GetRowData(i).vrtlAcntExprDtm;
			var pymntStatCode = claimRedemptionRefundInfoSheet.GetRowData(i).pymntStatCode;
			
			if( !abc.text.allNull(vrtlAcntExprDtm) && pymntStatCode == _claimInfo.PYMNT_STAT_CODE_REDEMP_ACCEPT ){
				// 가상계좌만료일이 null이 아니고 환수접수(입금대기) 상태일때
				var today = new Date();
				today = getFormatDate(today);
				vrtlAcntExprDtm = vrtlAcntExprDtm.substring(0,8);
				// 입금기한이 지났다면
				if( abc.date.compareDate( today, vrtlAcntExprDtm ) > 0 ){
					claimRedemptionRefundInfoSheet.SetRowData(i, {pymntStatCodeName : "입금기한만료"});
				}
			}
			
			// 완료일시
			var modDtm = claimRedemptionRefundInfoSheet.GetRowData(i).modDtm;
			
			if( !abc.text.allNull(modDtm) && pymntStatCode == _claimInfo.PYMNT_STAT_CODE_REDEMP_ACCEPT){
				claimRedemptionRefundInfoSheet.SetRowData(i, {modDtm : ""});
			}
		}
	}
		
	/*************************************************************************
	 *								환수/환불금액관리 그리드 영역 E
	 *************************************************************************/
	
	/*************************************************************************
	 *								클레임 내역 그리드 영역 s
	 *************************************************************************/
	/**
	 * 클레임 내역 초기 세팅
	 */
	_claimInfo.initclaimInfo = function() {
		// 그리드 객체, 메모리 초기화
		if(typeof claimInfoSheet != 'undefined'){
			claimInfoSheet.Reset();
			claimInfoSheet.DisposeSheet();
		}
		
		// Grid 사이즈
		createIBSheet2(document.getElementById("claimInfo"), "claimInfoSheet", "100%", "370px");
		
		// Grid 초기화 기본/헤더/컬럼선언
		var initSheet = {};
		
		initSheet.Cfg = {SearchMode:smLazyLoad, MaxSort:1};
		initSheet.HeaderMode = {Sort:0, ColMove:0, ColResize:1};	
		initSheet.Cols = [
			  {Header:"상태" ,		 	Type:"Status",		SaveName:"status",				Width: 0,  Align:"", 		Edit:0, Hidden:1}
			, {Header:"클레임구분코드" ,		Type:"Text",		SaveName:"clmGbnCode",			Width: 0,  Align:"", 		Edit:0, Hidden:1}
			, {Header:"클레임구분", 		Type:"Text",		SaveName:"clmGbnCodeName",		Width: 12, Align:"Center", 	Edit:0}
			, {Header:"클레임번호", 		Type:"Text",		SaveName:"clmNo",				Width: 20, Align:"Center", 	Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer"}
			, {Header:"진행상태", 		    Type:"Text",		SaveName:"clmStatCodeName",		Width: 15, Align:"Center", 	Edit:0}
			, {Header:"클레임사유", 		Type:"Text",		SaveName:"clmRsnName",			Width: 15, Align:"Center", 	Edit:0}
			, {Header:"클레임사유외갯수", 	Type:"Integer",		SaveName:"clmRsnNameExtraCnt",	Width: 20, Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"상품명", 			Type:"Text",		SaveName:"clmPrdtName",			Width: 40, Align:"Left", 	Edit:0}
			, {Header:"상품명외갯수", 		Type:"Integer",		SaveName:"clmPrdtExtraCnt",		Width: 20, Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"처리자", 			Type:"Text",		SaveName:"clmHandler",			Width: 20, Align:"Center", 	Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer"}
			, {Header:"접수일시", 			Type:"Text",		SaveName:"rgstDtm",				Width: 25, Align:"Center", 	Edit:0,	Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			, {Header:"처리자번호",			Type:"Text",		SaveName:"moderNo",					   Align:"Center", 	Edit:0, Sort:0, Hidden:1}
			, {Header:"처리일시", 			Type:"Text",		SaveName:"modDtm",				Width: 25, Align:"Center", 	Edit:0,	Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			];
		
		// Grid 초기화
		IBS_InitSheet(claimInfoSheet , initSheet);
		// Grid 건수 정보 표시
		claimInfoSheet.SetCountPosition(3);
		// Grid width 자동 조절
		claimInfoSheet.FitColWidth();
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		claimInfoSheet.SetExtendLastCol(1);
		
		abc.biz.order.claim.info.tab.claimInfoDoAction("search"); // 조회실행
	}
	
	/**
	 * 클레임 그리드 Click 이벤트
	 */
	_claimInfo.claimInfoSheetOnClick = function(Row, Col, Value, CellX, CellY, CellW, CellH){
		
		if ( Row != 0 ) {
			
			if ( claimInfoSheet.ColSaveName(Col) == "clmNo" && Value != "" ) {
				
				var clmNo = claimInfoSheet.GetRowData(Row).clmNo;
				var clmGbnCode = claimInfoSheet.GetRowData(Row).clmGbnCode;
				// 클레임 상세 팝업 호출
				abc.biz.order.claim.info.tab.openClaimDetailPop(clmNo, clmGbnCode);
			}
			else if ( claimInfoSheet.ColSaveName(Col) == "clmHandler" && Value != "" ) {
			
				var adminNo = claimInfoSheet.GetRowData(Row).moderNo;
				abc.adminDetailPopup(adminNo);
			}
		}
	}
	
	/**
	 * 클레임 그리드 조회 후 데이터 가공
	 */
	_claimInfo.claimInfoSheetOnSearchEnd = function(){
		
		var rowCnt = claimInfoSheet.RowCount();
		
		for(var i=1; i<=claimInfoSheet.GetDataLastRow(); i++){
			
			// 클레임사유 + 외 ~건
			var clmRsnName = claimInfoSheet.GetRowData(i).clmRsnName;
			var clmRsnNameExtraCnt = claimInfoSheet.GetRowData(i).clmRsnNameExtraCnt;
			
			if(clmRsnNameExtraCnt > 0){
				clmRsnName = clmRsnName + " 외 " + clmRsnNameExtraCnt + "건";
			}
			claimInfoSheet.SetRowData(i, {clmRsnName : clmRsnName});
			
			// 상품명 + 외 ~ 건
			var clmPrdtName = claimInfoSheet.GetRowData(i).clmPrdtName;
			var clmPrdtExtraCnt = claimInfoSheet.GetRowData(i).clmPrdtExtraCnt;
			
			if(clmPrdtExtraCnt > 0){
				clmPrdtName = clmPrdtName + " 외 " + clmPrdtExtraCnt + "건";
			}
			claimInfoSheet.SetRowData(i, {clmPrdtName : clmPrdtName});
		}
	}
	
	/**
	 * 클레임 내역 Action관리
	 */
	_claimInfo.claimInfoDoAction = function(sAction){
		switch(sAction) {
		// 조회
		case "search" :
			var param = {
				url : "/claim/claim/read-claim-list-intab"
				, subparam : FormQueryStringEnc(document.frmSearch)
				, sheet : "claimInfoSheet"
			};

			DataSearch(param);
			break;
		}
	}
	
	/*************************************************************************
	 *								클레임 내역 취소 그리드 영역 E
	 *************************************************************************/
	
	/*************************************************************************
	 *								A/S 내역 그리드 영역 s
	 *************************************************************************/
	/**
	 * A/S 내역 초기 세팅
	 */
	_claimInfo.initafterServicetInfo = function() {
		// 그리드 객체, 메모리 초기화
		if(typeof afterServiceInfoSheet != 'undefined'){
			afterServiceInfoSheet.Reset();
			afterServiceInfoSheet.DisposeSheet();
		}
		
		// Grid 사이즈
		createIBSheet2(document.getElementById("afterServicetInfo"), "afterServiceInfoSheet", "100%", "370px");
		
		// Grid 초기화 기본/헤더/컬럼선언
		var initSheet = {};
		
		initSheet.Cfg = {SearchMode:smLazyLoad, MaxSort:1};
		initSheet.HeaderMode = {Sort:0, ColMove:0, ColResize:1};	
		initSheet.Cols = [
			  {Header:"" ,			Type:"Status",	SaveName:"status",				Width: 0,  Align:"", 		Edit:0, Hidden:1}
			, {Header:"진행상태", 		Type:"Text",	SaveName:"asStatCodeName",		Width: 20, Align:"Center", 	Edit:0}
			, {Header:"A/S번호", 		Type:"Text",	SaveName:"asAcceptNo",			Width: 20, Align:"Center", 	Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer"}
			, {Header:"주문배송ID", 	Type:"Text",	SaveName:"dlvyIdText",			Width: 20, Align:"Center", 	Edit:0}
			, {Header:"A/S사유", 		Type:"Text",	SaveName:"asRsnCodeName",		Width: 20, Align:"Center", 	Edit:0}
			, {Header:"처리내용",		Type:"Text",	SaveName:"asProcTypeInfo",		Width: 30, Align:"Left", 	Edit:0}
			, {Header:"상품명", 		Type:"Text",	SaveName:"asAcceptPrdtName",	Width: 30, Align:"Left", 	Edit:0}
			, {Header:"접수일시", 		Type:"Text",	SaveName:"rgstDtm",				Width: 25, Align:"Center", 	Edit:0,	Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			, {Header:"처리자", 		Type:"Text",	SaveName:"asAcceptHandler",		Width: 25, Align:"Center", 	Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer"}
			, {Header:"처리일시", 		Type:"Text",	SaveName:"modDtm",				Width: 25, Align:"Center", 	Edit:0,	Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			, {Header:"처리자번호",		Type:"Text",	SaveName:"moderNo",					   	   Align:"Center", 	Edit:0, Sort:0, Hidden:1}
			];
		
		// Grid 초기화
		IBS_InitSheet(afterServiceInfoSheet , initSheet);
		// Grid 건수 정보 표시
		afterServiceInfoSheet.SetCountPosition(3);
		// Grid width 자동 조절
		afterServiceInfoSheet.FitColWidth();
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		afterServiceInfoSheet.SetExtendLastCol(1);
		
		abc.biz.order.claim.info.tab.afterServiceInfoDoAction("search"); // 조회실행
	}
	
	/**
	 * AS 그리드 Click 이벤트
	 */
	_claimInfo.afterServiceInfoSheetOnClick = function(Row, Col, Value, CellX, CellY, CellW, CellH){
		
		if ( Row != 0 ) {
			
			if ( afterServiceInfoSheet.ColSaveName(Col) == "asAcceptNo" && Value != "" ) {
				
				var asAcceptNo = afterServiceInfoSheet.GetRowData(Row).asAcceptNo;
				var asGbnCode = afterServiceInfoSheet.GetRowData(Row).asGbnCode;
				
				abc.biz.order.claim.info.tab.openAfterServiceDetailPop(asAcceptNo, asGbnCode); // 클레임 상세 팝업
			}
			else if ( afterServiceInfoSheet.ColSaveName(Col) == "asAcceptHandler" && Value != "" ) {
				
				var adminNo = afterServiceInfoSheet.GetRowData(Row).moderNo;
				abc.adminDetailPopup(adminNo);
			}
		}
	}
	
	/**
	 * A/S 내역 Action관리
	 */
	_claimInfo.afterServiceInfoDoAction = function(sAction){
		switch(sAction) {
		// 조회
		case "search" :
			var param = {
				url : "/claim/claim/read-afterservice-list-intab"
				, subparam : FormQueryStringEnc(document.frmSearch)
				, sheet : "afterServiceInfoSheet"
			};

			DataSearch(param);
			break;
		}
	}
	/*************************************************************************
	 *								A/S 내역 취소 그리드 영역 E
	 *************************************************************************/
	
	/*************************************************************************
	 *								팝업 호출 함수 S
	 *************************************************************************/
	
	/**
	 * 클레임 상세 팝업 호출
	 */
	_claimInfo.openClaimDetailPop = function(clmNo, clmGbnCode){
		var url = "";
		var params = {clmNo : clmNo, clmGbnCode : clmGbnCode};
		
		if(clmGbnCode == _claimInfo.CLM_GBN_CODE_CANCEL) { //취소
			url = "/claim/claim/read-claim-cancel-detail-pop"; // 클레임 취소 상세
		} else if(clmGbnCode == _claimInfo.CLM_GBN_CODE_EXCHANGE) { //교환
			url = "/claim/claim/read-claim-exchange-detail-pop"; // 클레임 교환 상세
		} else if(clmGbnCode == _claimInfo.CLM_GBN_CODE_RETURN) { //반품
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
	 * AS  상세 팝업 호출
	 */
	_claimInfo.openAfterServiceDetailPop = function(asAcceptNo, asGbnCode){
		var url = "";
		var params = {asAcceptNo : asAcceptNo};
		url = "/afterservice/afterservice/read-afterservice-detail-pop"; // AS접수 상세 
		
		abc.open.popup({
			winname :	"",
			url 	:	url,
			width 	:	1240,
			height	:	990,
			params	:	params
		});
	}
	
	/*************************************************************************
	 *								팝업 호출 함수 E
	 *************************************************************************/
})();

/**
 *  yyyy.MM.dd 포맷으로 반환
 */
function getFormatDate(date){
	var year = date.getFullYear();	//yyyy 
	var month = (1 + date.getMonth());	//M 
	month = month >= 10 ? month : '0' + month;	//month 두자리로 저장 
	var day = date.getDate();	//d 
	day = day >= 10 ? day : '0' + day;	//day 두자리로 저장 
	return year + '.' + month + '.' + day;
}