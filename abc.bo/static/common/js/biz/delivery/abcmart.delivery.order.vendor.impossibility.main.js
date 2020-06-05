/***
 * 주문관리 > 배송관리 >  클레임 불가 요청 현황
 * 
 */
(function() {
		
	
	
		// define Alert Msg
		var alertSelectEmpty				= "처리대상이 선택되지 않았습니다.\n 처리대상을 확인해주세요.";
	    var alertSelectStockGbn = "재배송 처리하려는 발송처로 선택해주세요"; 
		var alertValidSave = "처리 대상 내 처리할 수 없는 배송건이 포함되어 있습니다. ";
		var alertConfirmSvae = "선택한 발송처로 일괄 재배송 처리 하시겠습니까?";
		var alertOrderNo				= "주문번호는 숫자만 입력해주세요.";
 

		//스크립트 Object선언
		var _deliveryJsObject = abc.object.createNestedObject(window,"abc.biz.delivery.order.vendor.impossibility.main");

		_deliveryJsObject.initDataListSheet = function() {
			createIBSheet2(document.getElementById("dataListGrid"), "dataListSheet", "100%", "429px");
			
			var initDataSheet = {};
			var pageCount = $('#pageCount').val();
			
			initDataSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MaxSort:1};
			initDataSheet.Cols = [
						 {Header:"" ,		 						Type:"Status",				SaveName:"status",					Width: 0,		 Align:"", 			Edit:0, Hidden:1}
						, {Header:"", 								Type:"CheckBox",			SaveName:"checkedRows",						Width: 40,		 Align:"Center",	Sort:0}
						, {Header:"업체명",					Type:"Text",				SaveName:"vndrName",						Width: 120,		 Align:"Center", 	Edit:0}
						, {Header:"사이트", 					Type:"Combo",				SaveName:"siteNo",				Width: 100,		 Align:"Center", 	Edit:0, ComboText: "", ComboCode: "" }
						, {Header:"클레임번호",				Type:"Text",				SaveName:"clmNo",				Width: 100,	 Align:"Center", Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer"}
						, {Header:"클레임구분",				Type:"Combo",			SaveName:"clmGbnCode",		Width: 150,		Align:"Center", Edit:0	}
						, {Header:"주문번호",					Type:"Text",				SaveName:"orderNo",				Width: 120,		 Align:"Center",	 Edit:0 , FontBold:1, FontUnderline:1, Cursor:"Pointer"} 
						, {Header:"주문자",					Type:"Text",				SaveName:"buyerName",			Width: 80,		 Align:"Center", 	Edit:0}
						, {Header:"주문자\n휴대폰번호",	Type:"Text",				SaveName:"buyerHdphnNoText",			Width: 150,		 Align:"Center", 	Edit:0}
						, {Header:"상품코드", 				Type:"Text",				SaveName:"prdtNo",				Width: 150,		 Align:"Center", 	Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer"}
						, {Header:"상품명", 					Type:"Text",				SaveName:"prdtName",			Width: 200,		 Align:"Left", 		Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer" }
						, {Header:"옵션", 						Type:"Text",				SaveName:"optnName",			Width: 80,		 Align:"Center", 	Edit:0}
						, {Header:"판매가", 					Type:"Int",					SaveName:"sellAmt",				Width: 80,		 Align:"Right", 	Edit:0}			
						, {Header:"결제금액", 				Type:"Int",					SaveName:"orderAmt",				Width: 80,		 Align:"Right", 	Edit:0}		
						, {Header:"클레임 불가\n요청사유", 			Type:"Combo",					SaveName:"clmImpsbltRsnCode",				Width: 170,		 Align:"Right", 	Edit:0}
						, {Header:"배송ID",					Type:"Text",				SaveName:"dlvyIdText",	  				Width: 150,		 Align:"Center", 	Edit:0}
						, {Header:"택배사",					Type:"Combo",			SaveName:"logisVndrCode",		Width: 100,		 Align:"Center", 	Edit:0}
						, {Header:"운송장번호",				Type:"Text",				SaveName:"waybilNoText",		Width: 150,		 Align:"Center", 	Edit:0}
						, {Header:"발송일자",					Type:"Date",				SaveName:"dlvyProcDtm",					Width: 150,		 Align:"Center", 	Edit:0, Format:"Ymd"}			
						, {Header:"요청일시", 				Type:"Text",				 SaveName:"sellCnclReqDtm",			Width: 120,		 Align:"Center", 	Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
						, {Header:"요청자",					Type:"Text",				SaveName:"sellCnclReqtrNo",					Width: 100,		 Align:"Center", 	Edit:0}
						, {Header:"주문취소일시", 			Type:"Text",				SaveName:"modDtm",			Width: 120,		 Align:"Center", 	Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
						, {Header:"주문상품순번",			Type:"Text",				SaveName:"orderPrdtSeq",					Width: 60,		 Align:"Center", 	Edit:0 , Hidden:1}
						, {Header:"클레임구분코드",				Type:"Text",			SaveName:"orgClmGbnCode",		Width: 150,		Align:"Center", Edit:0, Hidden:1}
						, {Header:"클레임구분코드",				Type:"Text",			SaveName:"orgSiteNo",		Width: 150,		Align:"Center", Edit:0, Hidden:1}
						, {Header:"발송매장",			Type:"Text",	SaveName:"storeName",			Width: 100,		 Align:"Center", 	Edit:0, Hidden:1}
						];

			// Grid 초기화
			IBS_InitSheet(dataListSheet, initDataSheet);
			// Grid 건수 정보 표시
			dataListSheet.SetCountPosition(3);
			// Grid 페이지 네비게이션 버튼 표시
			dataListSheet.SetPagingPosition(2);
			// Grid width 자동 조절
			//dataListSheet.FitColWidth();
			// Grid 마지막 컬럼을 All 너비에 맞춘다.
			dataListSheet.SetExtendLastCol(1);
	 
		   //dataListSheet.InitDataCombo(0, "deviceCode",  this.codeCombo.DEVICE_CODE.text,   this.codeCombo.DEVICE_CODE.code );		//디바이스 구분
		   dataListSheet.InitDataCombo(0, "clmGbnCode",  this.codeCombo.CLM_GBN_CODE.text,   this.codeCombo.CLM_GBN_CODE.code );		//클레임구분
		   dataListSheet.InitDataCombo(0, "clmImpsbltRsnCode",  this.codeCombo.CLM_IMPSBLT_RSN_CODE.text,   this.codeCombo.CLM_IMPSBLT_RSN_CODE.code );		 //클레임 불가 요청사유
		   dataListSheet.InitDataCombo(0, "siteNo",  this.siteCombo .text,   this.siteCombo.code );		//사이트 콤보
	   		dataListSheet.InitDataCombo(0, "logisVndrCode",  this.codeCombo.LOGIS_VNDR_CODE.text,   this.codeCombo.LOGIS_VNDR_CODE.code );		//택배사코드

	}	 

	_deliveryJsObject.SITE_NO_ART	= "";
	_deliveryJsObject.SITE_NO_OTS	= "";
	
	_deliveryJsObject.ABC_FO_URL	= "";
	_deliveryJsObject.OTS_FO_URL	= "";
		
	_deliveryJsObject.fromDash = "";
	
	/**
	 * from dashboard
	 */
	_deliveryJsObject.fromDashBoard = function(){
		
		if( !abc.text.allNull(_deliveryJsObject.fromDash) ){
			
			$("a[name^=perOneMonth]").trigger('click');
			_deliveryJsObject.doActionJs('search');
		}
	}
	
	/**
	 * init 초기화
	 */
	_deliveryJsObject.init = function(){	
		var _Today = new Date(); 
		//일자를 1년으로 기본 셋팅
		if ($("#toDate").val() == "" && $("#fromDate").val() == "" ){
			_Today.setDate(_Today.getDate());	//  당일날짜
			$("#toDate").val($.datepicker.formatDate($("#toDate").datepicker("option", "dateFormat"), _Today));
			$("#fromDate").val($.datepicker.formatDate($("#fromDate").datepicker("option", "dateFormat"), _Today));
		}

		$("#fromDate").attr("readonly", true);  //일자검색 제약
		$("#toDate").attr("readonly", true);      //일자검색 제약
		 
		$("a[name^=perOneMonth]").trigger('click');
	}

	// 화면 이벤트 
	_deliveryJsObject.event = function(){
		
		// 초기화
		$("#gFormReset").click(function(){
			$('#gForm')[0].reset();
			_deliveryJsObject.init();
		});
		
		//  검색
		$("#gFormSearch").click(function(){
			//검색 Validate
			if( _deliveryJsObject.searchValidate()){
				_deliveryJsObject.doActionJs("search");
			}
		});

		//페이지선택시
		$("#pageCount").change(function(){
			_deliveryJsObject.doActionJs("search");
		});
 
		// 캘린더 버튼 설정(일)
		$("a[name^=perToday]").click(function(){
			abc.date.days(this);
		});
		
		// 캘린더 버튼 설정(주)
		$("a[name^=perWeek]").click(function(){
			abc.date.week(this);
		});
		
		// 캘린더 버튼 설정(월)
		$("a[name^=perOneMonth]").click(function(){
			abc.date.month(this);
		});

		// 캘린더 버튼 설정(월)
		$("a[name^=perYearMonth]").click(function(){
			abc.date.year(this);
		});
	  
	  	//업체 찾기 팝업
		$("#searchVendor").click(function(){
			// false 단건 , false n건
			abc.vndrSearchPopup(false, "setSelectedVendors");
		});

		//선택 엑셀 받기
		 $("#excelDownSelect").click(function(){ 
			  var sRow = dataListSheet.CheckedRows("checkedRows");
			 if(sRow <1 ){
					alert(alertExcelEmpty);
					return;
			 } //end if

			$("#excelType").val("SELECT");
			_deliveryJsObject.doActionJs("excelSelect");
		});

		//전체 엑셀 받기
		 $("#excelDownAll").click(function(){ 
			    $("#excelType").val("ALL");
			 	_deliveryJsObject.doActionJs("excelAll");
		});


		//클레임 불가 처리
		 $("#impossibilitySave").click(function(){ 
			 	_deliveryJsObject.doActionJs("impossibilitySave");
		});

		//결제구분
		$("input[name='deviceCodeArr']").click(function() {
			if($(this).val() == "") {
				$("input[name='deviceCodeArr']").prop("checked", this.checked);
			} else {
				$("input[name='deviceCodeArr']:eq(0)").prop("checked", false);
			}

			//전체 선택인경우
			var unChecked = $("input[clickYn=deviceCode]:not(:checked)");
			if (unChecked.length == 0) {
					$("input[name='deviceCodeArr']:eq(0)").prop("checked", true);
			}
		});
	} // end _deliveryJsObject.init = function(){	

	/**
	 * Data Action관리
	 */
	_deliveryJsObject.doActionJs = function(inAction){
		switch(inAction) {
			// 조회 
			case "search" :
				var pageCount = $('#pageCount').val();
				var param = { url : "/delivery/vendor/delivery-order-vendor-impossibility/read-list"
							 , onePageRow : pageCount
							 , subparam : FormQueryStringEnc(document.gForm)
							 , sheet : "dataListSheet" };
				
				DataSearchPaging(param);
				break;

				// 선택엑셀 다운 
			case "excelSelect" :
					var checkRows = dataListSheet.FindCheckedRow("checkedRows");
					var t = new Date();
					var setFilename = t.getFullYear() + "-" +_deliveryJsObject.AddZero(t.getMonth()+1) +"-" + _deliveryJsObject.AddZero(t.getDate());
					var downcol = _deliveryJsObject.makeHiddenSkipCol(dataListSheet);
					var param  = {DownCols:downcol,FileName: setFilename+ ".xls", DownRows: "0|"+checkRows};
					      dataListSheet.Down2Excel(param);
				break;  
			// 전체 엑셀 다운 
			case "excelAll" :
				gForm.action ="/delivery/delivery-order-vendor/impossibility/excelAll";
			   gForm.submit();	 
				break;  
		
		    // 클레임 불가 처리
			case "impossibilitySave" :
			  alert("클레임 불가처리");
				break;  
		}
	}

		//SkipCol 만들기. (감춰진 컬럼이나, Seq,Status,DelCheck 타입의 컬럼은 제외한다.)
	_deliveryJsObject.makeHiddenSkipCol = function(sobj){
		var lc = sobj.LastCol();
		var colsArr = new Array();
		for(var i=0;i<=lc;i++){
			var coltype = sobj.GetCellProperty(0,i,"Type");
			//Hidden이 아니면 넣는다.
			if(0==sobj.GetColHidden(i)&&coltype!="Seq"&&coltype!="Status"&&coltype!="DelCheck"  &&coltype!="CheckBox") {
				colsArr.push(i);
			}
		}
		return colsArr.join("|");
	}

	//zero  체크
	_deliveryJsObject.AddZero = function(str) {
		if((str+"").length==1){
			return "0"+str;
		}	
		return str;
	} 

   /**
	 * 검색조건 Validate 체크
	 */
	_deliveryJsObject.searchValidate = function(){
 	 		
		/** 주문자 정보  */
	 	 var searchOrderValue = $("#searchOrderValue").val();

		 if (!abc.text.allNull( searchOrderValue)) {
				 switch ($("#searchOrderKey").val()) {
					case "orderNo":
						 if (isNaN( searchOrderValue )) {
								alert( alertOrderNo );
								$("#searchOrderValue").val("");
								return false;
							}
						break;
				}
		} //end  if (!abc.text.allNull( searchOrderValue)) {
		 
		return true;

	} //end _deliveryJsObject.searchValidate
 	
	
	/*************************************************************************
	 *								팝업 호출 함수 S
	 *************************************************************************/
	
	_deliveryJsObject.CLM_GBN_CODE_CANCEL	= "";
	_deliveryJsObject.CLM_GBN_CODE_EXCHANGE	= "";
	_deliveryJsObject.CLM_GBN_CODE_RETURN	= "";
	
	/**
	 * 클레임 상세 팝업 호출
	 */
	_deliveryJsObject.openClaimDetailPop = function(clmNo, clmGbnCode){
		
		var url = "";
		var params = {clmNo : clmNo, clmGbnCode : clmGbnCode};
		//alert(clmNo);
		if(clmGbnCode == _deliveryJsObject.CLM_GBN_CODE_CANCEL) { //취소
			
			url = "/claim/claim/read-claim-cancel-detail-pop"; // 클레임 취소 상세
			
		} else if(clmGbnCode == _deliveryJsObject.CLM_GBN_CODE_EXCHANGE) { //교환
			
			url = "/claim/claim/read-claim-exchange-detail-pop"; // 클레임 교환 상세
			
		} else if(clmGbnCode == _deliveryJsObject.CLM_GBN_CODE_RETURN) { //반품
			
			url = "/claim/claim/read-claim-return-detail-pop"; // 클레임 반품 상세
		}
		
		abc.open.popup({
			winname :	"claimDetailPop",
			url 	:	url,
			width 	:	1260,
			height	:	990,
			params	:	params
		});
	}
	
	/*************************************************************************
	 *								팝업 호출 함수 E
	 *************************************************************************/
	
})();	 //end  (function() {		
 
 //그리드 Click 이벤트 
function dataListSheet_OnClick(Row, Col, Value) {
	
	if ( Row != 0) {
		
		//파라미터값셋팅
		var params = {}
			params.orderStatCode = dataListSheet.GetCellValue(Row, "orderStatCode");

		// 클레임상세
		if ( dataListSheet.ColSaveName(Col) == "clmNo") {
			var clmNo = dataListSheet.GetCellValue(Row, "clmNo");
			var clmGbnCode = dataListSheet.GetCellValue(Row, "orgClmGbnCode");
			abc.biz.delivery.order.vendor.impossibility.main.openClaimDetailPop(clmNo, clmGbnCode);
		}
		
		//주문상세
		if ( dataListSheet.ColSaveName(Col) == "orderNo") {
			abc.orderDetailPopup(dataListSheet,"orderNo",Row,Col,"");
		}
		
		//상품코드
		if( dataListSheet.ColSaveName(Col) == "prdtNo" ) {
			
			var prdtNo =  dataListSheet.GetCellValue(Row, "prdtNo");
			var param = { prdtNo : prdtNo };
			abc.readonlyProductDetailPopup(param);
		}
		
		//상품명
		if ( dataListSheet.ColSaveName(Col) == "prdtName"   ) {
				
				var prdtNo = dataListSheet.GetRowData(Row).prdtNo;
				var siteNo = dataListSheet.GetRowData(Row).orgSiteNo;
				
				if(siteNo == abc.biz.delivery.order.vendor.impossibility.main.SITE_NO_ART){
					window.open(abc.biz.delivery.order.vendor.impossibility.main.ABC_FO_URL + "/product?prdtNo=" + prdtNo)
				} else {
					window.open(abc.biz.delivery.order.vendor.impossibility.main.OTS_FO_URL + "/product?prdtNo=" + prdtNo)
				}	
		}
		
		/*//주문배송상태
		if ( dataListSheet.ColSaveName(Col) == "dlvyStatCode") {
			var orderNo =  dataListSheet.GetCellValue(Row, "orderNo");
				var orderPrdtSeq =  dataListSheet.GetCellValue(Row, "orderPrdtSeq");
			 
				abc.orderProductHistoryPopup(orderNo,orderPrdtSeq,"");
		}
		*/

	} //end if ( Row != 0) {
} //end function 그리드 Click 이벤트 

	

	
	 