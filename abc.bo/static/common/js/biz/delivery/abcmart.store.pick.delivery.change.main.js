/***
 * 주문관리 > 배송관리 > 매장픽업택배전환
 * 
 */
(function() {
		
		var alertSelectEmpty				= "처리대상이 선택되지 않았습니다.\n 처리대상을 확인해주세요.";
		var alertSelectDouble				= "동일한 주문번호의 상품만 함께 처리가 가능합니다. \n주문번호를 확인해주세요.";
		var alertDeliveryAddressYn				= "미발송에 대한 신청/접수 여부를 선택해주세요.";
		var alertDlvyStatCode			= "발송여부에 대한 배송상태를 선택해주세요.";
		var alertOrderNo				= "주문번호는 숫자만 입력해주세요.";
		var alertDeliveryChangePopup ="신청 상태인 경우만 택배전환 정보 입력이 가능합니다. \n 상태를 확인해주세요";
		var alertSearchDate			= "검색시작일은 종료일보다 이전 일 수 없습니다.\n검색기간을 다시 설정해주세요.";
		var alertSearchDateOver = "검색기간은 최대 1년 입니다. \n 검색기간을 다시 설정해주세요.";
		var alertCheckBoxFail ="최소한 한개 이상의 검색 조건은 체크를 해야 검색이 가능 합니다.";

		//스크립트 Object선언
		var _deliveryJsObject = abc.object.createNestedObject(window,"abc.biz.store.pick.delivery.change.main");

		_deliveryJsObject.SITE_NO_ART = "";
		_deliveryJsObject.SITE_NO_OTS = "";
		
		_deliveryJsObject.ABC_FO_URL = "";
		_deliveryJsObject.OTS_FO_URL = "";
		
		_deliveryJsObject.NON_MEMBER_NO = "";
		
		_deliveryJsObject.initDataListSheet = function() {
		createIBSheet2(document.getElementById("dataListGrid"), "dataListSheet", "100%", "429px");
		
		var initDataSheet = {};
		var pageCount = $('#pageCount').val();
		
		initDataSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MaxSort:1};
		initDataSheet.Cols = [
				  {Header:"" ,		 		Type:"Status",	SaveName:"status",					Width: 0,		 Align:"", 			Edit:0, Hidden:1}
				, {Header:"", 				Type:"CheckBox",SaveName:"checkedRows",				Width: 40,		 Align:"Center",	Sort:0}
				, {Header:"사이트", 			Type:"Combo",	SaveName:"siteNo",					Width: 100,		 Align:"Center", 	Edit:0, ComboText: "", ComboCode: "" }
				, {Header:"사이트번호", 		Type:"Text",	SaveName:"orgSiteNo",				Width: 100,		 Align:"Center", 	Edit:0,	Hidden:1}
				, {Header:"신청일시", 			Type:"Text",	SaveName:"logisCnvrtReqDtm",		Width: 120,		 Align:"Center", 	Edit:0, 	FontBold:1, FontUnderline:1, Cursor:"Pointer" ,Format:abc.consts.DEFAULT_DATETIME_PATTERN}
				, {Header:"접수여부", 			Type:"Combo",	SaveName:"deliveryAddressEmptyYn",	Width: 80,		 Align:"Center", 	Edit:0, ComboText: "", ComboCode: "" }
				, {Header:"주문번호",			Type:"Text",	SaveName:"orderNo",					Width: 120,		 Align:"Center",	 Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer" }
				, {Header:"주문자",			Type:"Text",	SaveName:"buyerName",				Width: 80,		 Align:"Center", 	Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer"}
				, {Header:"주문자ID",			Type:"Text",	SaveName:"buyerId",					Width: 80,		 Align:"Center", 	Edit:0, Hidden:1}
				, {Header:"상품코드", 			Type:"Text",	SaveName:"prdtNo",					Width: 150,		 Align:"Center", 	Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer"}
				, {Header:"상품명", 			Type:"Text",	SaveName:"prdtName",				Width: 200,		 Align:"Left", 		Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer" }
				, {Header:"옵션", 			Type:"Text",	SaveName:"optnName",				Width: 80,		 Align:"Center", 	Edit:0}
				, {Header:"결제금액", 			Type:"Int",		SaveName:"orderAmt",				Width: 80,		 Align:"Right", 	Edit:0, Format:"#,##0 원"}
				, {Header:"발송처",			Type:"Combo",	SaveName:"stockGbnCode",			Width: 100,		 Align:"Center", 	Edit:0}
				, {Header:"매장픽업\n택배전환사유",Type:"Combo",	SaveName:"logisCnvrtRsnCode",		Width: 120,		 Align:"Center", 	Edit:0}
				, {Header:"배송ID",			Type:"Text",	SaveName:"dlvyIdText",	  			Width: 150,		 Align:"Center", 	Edit:0}
				, {Header:"택배전환상태", 		Type:"Combo",	SaveName:"dlvyStatCode",			Width: 80,		 Align:"Center", 	Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer" }
				, {Header:"택배사",			Type:"Combo",	SaveName:"logisVndrCode",			Width: 100,		 Align:"Center", 	Edit:0}
				, {Header:"발송여부\n(발송일)", 	Type:"Text",	SaveName:"dlvyProcDtm",				Width: 150,		 Align:"Center", 	Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
				, {Header:"처리자",			Type:"Text",	SaveName:"moder",					Width: 120,		 Align:"Center", 	Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer"}
				, {Header:"처리자번호",			Type:"Text",	SaveName:"moderNo",					Width: 120,		 Align:"Center", 	Edit:0, Hidden:1}
				, {Header:"처리자이름",			Type:"Text",	SaveName:"moderName",				Width: 120,		 Align:"Center", 	Edit:0, Hidden:1}
				, {Header:"처리자ID",			Type:"Text",	SaveName:"moderId",					Width: 120,		 Align:"Center", 	Edit:0, Hidden:1}
				, {Header:"처리일시", 			Type:"Text",	SaveName:"modDtm",					Width: 150,		 Align:"Center", 	Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
				, {Header:"주문순번",			Type:"Text",	SaveName:"orderPrdtSeq",			Width: 80,		 Align:"Center",	Edit:0 }
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
 
		dataListSheet.InitDataCombo(0, "logisVndrCode",  this.codeCombo.LOGIS_VNDR_CODE.text,   this.codeCombo.LOGIS_VNDR_CODE.code );		 //택배사
		dataListSheet.InitDataCombo(0, "dlvyStatCode",  "신청|결제완료|상품준비중|상품출고|배송중|픽업준비완료|배송완료|배송취소|분실재배송|구매확정",   "99|10000|10001|10002|10003|10004|10005|10006|10007|10008" );		 //주문배송상태
		dataListSheet.InitDataCombo(0, "logisCnvrtRsnCode",  this.codeCombo.LOGIS_CNVRT_RSN_CODE.text,   this.codeCombo.LOGIS_CNVRT_RSN_CODE.code );		 //전환사유	   
	    dataListSheet.InitDataCombo(0, "stockGbnCode",  this.codeCombo.STOCK_GBN_CODE.text,   this.codeCombo.STOCK_GBN_CODE.code );		 //발송처
		dataListSheet.InitDataCombo(0, "deliveryAddressEmptyYn", "접수|신청"   ,  "Y|N"    );		 //접수여부
	    dataListSheet.InitDataCombo(0, "siteNo",  this.siteCombo .text,   this.siteCombo.code );		//사이트 콤보

	}	 

	_deliveryJsObject.dataListSheetOnSearchEnd = function(){
		// 한페이지의 목록 갯수
		var pageCount = $('#pageCount').val();
		// 현재 페이지 번호
		var currentPage = dataListSheet.GetCurrentPage();
		// 현재 페이지 첫 로우
		var pageFirstRow;
		
		if( currentPage == 1){
			pageFirstRow = 1;
		}else if( currentPage > 1 ){
			pageFirstRow = (currentPage - 1) * currentPage + 1;
		}
		
		for(var i=pageFirstRow; i<=dataListSheet.GetDataLastRow(); i++){
			
			var memberNo = dataListSheet.GetRowData(i).memberNo;
			
			if( memberNo != _deliveryJsObject.NON_MEMBER_NO){
				dataListSheet.SetRowData(i, {buyerName : dataListSheet.GetRowData(i).buyerName + "(" + dataListSheet.GetRowData(i).buyerId + ")"});
			}else{
				dataListSheet.SetCellFontUnderline(i,10,0);
			}
			
			var moderNo = dataListSheet.GetRowData(i).moderNo;
			
			if( !abc.text.allNull(moderNo) ){
				dataListSheet.SetRowData(i, {moder : dataListSheet.GetRowData(i).moderName + "(" + dataListSheet.GetRowData(i).moderId + ")"});
			}
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

		//$("#fromDate").attr("readonly", true);  //일자검색 제약
		//$("#toDate").attr("readonly", true);      //일자검색 제약
		$('#dlvyStatCode').hide();						//택배전환 상태 초기
		$('#deliveryAddressYn').hide();			//택배전환 상태 초기

		//미발송 -  최초 조회시에는 택배전환신청한 기준의 데이타를 추출
		$("#selectChangeType").val("noSend"); //전환상태 - 미발송
		$("#deliveryAddressYn").val("Y");     //신청건 기준으로
		$('#dlvyStatCode').hide();
		$('#deliveryAddressYn').show();
	
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
		
		//채널 구분 체크 
		$("input[name='chanListArr']").click(function() {
			if($(this).val() == "") {
				$("input[name='chanListArr']").prop("checked", this.checked);
			} else {
				$("input[name='chanListArr']:eq(0)").prop("checked", false);
			}

			//전체 선택인경우
			var unChecked = $("input[clickYn=chanList]:not(:checked)");
			if (unChecked.length == 0) {
					$("input[name='chanListArr']:eq(0)").prop("checked", true);
			}

		});

		$("#deliveryChangePopup").click(function(){ 
			_deliveryJsObject.doDeliveryChange();
		});

		$("#searchStorePopUp").click(function(){ 
			// 매장찾기 팝업
    		abc.storeSearchPopup('abc.biz.store.pick.delivery.change.main.setStoreInfo', false);
		}); 
		
		// 2020.05.22 : 주문 검색어 선택 이벤트
		$("#searchOrderKey").change(function(){
			var orderSearchKey = $(this).val();
			_deliveryJsObject.ordMbInfoSelectChange(orderSearchKey);
		});
}

	/**
	 * 2020.05.22 주문 검색어 선택 이벤트
	 */
	_deliveryJsObject.ordMbInfoSelectChange = function(searchOrderKey) {
		
		$("#searchOrderValue").val("");
		$("#buyerHdphnBackNo").val("");
		
		if(searchOrderKey == "buyerName"){
			$("#buyerHdphnBackNo").show();
		}else if(searchOrderKey == "rcvrName"){
			$("#buyerHdphnBackNo").show();
		}else if(searchOrderKey == "orderNo"){
			$("#buyerHdphnBackNo").hide();
		}else if(searchOrderKey == "loginId"){
			$("#buyerHdphnBackNo").hide();
		}
	}
	
	// 매장찾기Set
	_deliveryJsObject.setStoreInfo = function(data){
		  
		if ( data.length > 1  ) {
			alert("1개이상의 매장이 선택되었습니다.");
			return;
		} 
		$("#storeNo").val( data[0].storeNo ); // 매장 번호  
		$("#storeName").val(data[0].storeName); // 매장 명 
		 	
	}
	
	/**
	 * Data Action관리
	 */
	_deliveryJsObject.doActionJs = function(inAction){
		switch(inAction) { 
			// 조회 
			case "search" :
					var strFromDate = $("#fromDate").val().replace(/[.]/gi,'');
					var strToDate = $("#toDate").val().replace(/[.]/gi,'');

					if( !abc.text.isDate(strFromDate) ){
						alert("검색 시작일자가 날짜 형식에 맞지 않습니다. ( 예: 1999.01.01)");
						return;
					}

					if( !abc.text.isDate(strToDate) ){
						alert("검색 종료일자가 날짜 형식에 맞지 않습니다. ( 예: 1999.01.01)");
						return;
					}
			 
				   var pageCount = $('#pageCount').val();
					var param = { url : "/delivery/store-pick-delivery-change/read-list"
								 , onePageRow : pageCount
								 , subparam : FormQueryStringEnc(document.gForm)
								 , sheet : "dataListSheet" };
					
					DataSearchPaging(param);
					break;
			}
	}

	 

    /**
	 * 검색조건 Validate 체크
	 */
	_deliveryJsObject.searchValidate = function(){
 	 		
		/**
		 * 주문자 정보
		 */
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
		 
		// 검색 기간 validate
		if( abc.text.allNull($("#fromDate").val()) || abc.text.allNull($("#toDate").val()) ){
			alert("검색 기간을 입력해주세요.");
			
			if( abc.text.allNull($("#fromDate").val()) && abc.text.allNull($("#toDate").val()) ){
				$("#fromDate").focus();
			} else if( abc.text.allNull($("#fromDate").val()) && !abc.text.allNull($("#toDate").val()) ){
				$("#fromDate").focus();
			} else if( !abc.text.allNull($("#fromDate").val()) && abc.text.allNull($("#toDate").val()) ){
				$("#toDate").focus();
			}
			
			return false;
		}		 
		
		 /**
		 * 검색 기간 체크
		 */
		var fromDate = new Date($('#fromDate')[0].value);
		var toDate = new Date($('#toDate')[0].value);
		var searchYear = new Date(toDate);
		 searchYear.setFullYear(toDate.getFullYear() - 1)

		if (fromDate > toDate){
			alert( alertSearchDate );
			return false;
		} else if(fromDate < searchYear) {
			alert( alertSearchDateOver );
			return false;
		}

		//검색 체크박스 체크
		var checkBoxLength = $("input[type=checkbox]:checked").length;
		if( checkBoxLength < 1 ){
			alert( alertCheckBoxFail );
			return false;
		}
		 
		 /*
		  택배전환상태 선택시
		 */
		var selectChangeType = $("#selectChangeType").val();
		
		if (!abc.text.allNull( selectChangeType)) {
				 switch (selectChangeType) {
						case "noSend":
							 if ( abc.text.allNull( $("#deliveryAddressYn").val()  )) {
									alert( alertDeliveryAddressYn );
									  $("#deliveryAddressYn").focus();
									return false;
								}
							break;
						case "send":
							 if ( abc.text.allNull( $("#dlvyStatCode").val()  )) {
									alert( alertDlvyStatCode );
									  $("#dlvyStatCode").focus();
									return false;
								 }
							break;
					}
			 }

		return true;

	} //end _deliveryJsObject.searchValidate



	/**
	 *  택배전환 상태
	 */
	$('#selectChangeType').change(function(){
 
			$("#dlvyStatCode").val("");
			$("#deliveryAddressYn").val("");

		if(this.value == "noSend"){
			//미발송
			$('#dlvyStatCode').hide();
			$('#deliveryAddressYn').show();
		}else if(this.value == "send"){
			//발송
			$('#dlvyStatCode').show();
			$('#deliveryAddressYn').hide();
			
		}else{
			$('#dlvyStatCode').hide();
			$('#deliveryAddressYn').hide();
		}	//end if
	}); //end function


	/**
	 *  택배전환 상태
	 */
	_deliveryJsObject.doDeliveryChange = function(){
		
		//선택여부 체크 * 형식이 있기에 두형식으로 사용 해봄
		 var sRow = dataListSheet.CheckedRows("checkedRows");
		 //선택 데이타 Rows
		var checkRows = dataListSheet.FindCheckedRow("checkedRows");

		 if(sRow <1 ){
			alert(alertSelectEmpty);
			return;
		 } //end if

		var orderNoAfter = "";
		var dlvyIdText=[];
		var orderPrdtSeq =[];

		//주문번호 중복여부 체크
         var arrRow = checkRows.split("|"); 
		 var params = {};  //popup창 params


		 for (idx=0; idx<arrRow.length; idx++){  
			var orderNo =  dataListSheet.GetCellValue( arrRow[idx], "orderNo");
			var deliveryAddressEmptyYn =  dataListSheet.GetCellValue( arrRow[idx], "deliveryAddressEmptyYn");
			
			//신청인경우만 허용	
			if(deliveryAddressEmptyYn == "Y" ){
				alert(alertDeliveryChangePopup+"\n(주문번호:"+orderNo+")");
				return;
			}

			if(orderNoAfter != orderNo && idx !=0 ){
				alert(alertSelectDouble+"\n(주문번호:"+orderNo+")");
				return;
			} else{
				 dlvyIdText[idx] = dataListSheet.GetCellValue( arrRow[idx] , "dlvyIdText");
				 orderPrdtSeq[idx] = dataListSheet.GetCellValue( arrRow[idx] , "orderPrdtSeq");
				 params.orderNo = orderNo;  // params 주문번호
				 params.callType = "DV";  //호출 유형
				 params.callBack = "";  //스크립트 호출 callBack 유형
			}
			orderNoAfter = orderNo;
		 }//end for

		  
		var url = "/delivery/store-pick-delivery-change-popup";
		
		params.dlvyIdText = dlvyIdText;   // params 배송아이디
		params.orderPrdtSeq = orderPrdtSeq;   // 주문상품 순번
	
		abc.open.popup({
			winname :	"StorePickDeliveryChange",
			url 	:	url,
			method	: 	"post",
			width 	:	1000,
			height	:	880,
			params	:	params

		});

	} //end function _deliveryJsObject.doDeliveryChange


})();	 //end  (function() {


 //그리드 Click 이벤트 
function dataListSheet_OnClick(Row, Col, Value) {
	
	if ( Row != 0) {
		
		//파라미터값셋팅
		var params = {}
			params.orderStatCode = dataListSheet.GetCellValue(Row, "orderStatCode");

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
		if ( dataListSheet.ColSaveName(Col) == "logisCnvrtReqDtm"   ) {
			
			
			var dlvyIdText =  dataListSheet.GetCellValue(Row, "dlvyIdText");
			var orderPrdtSeq =  dataListSheet.GetCellValue(Row, "orderPrdtSeq");
			var deliveryAddressEmptyYn =  dataListSheet.GetCellValue(Row, "deliveryAddressEmptyYn"); //접수 여부
			var orderNo = dataListSheet.GetCellValue(Row, "orderNo");
			
			if(deliveryAddressEmptyYn == "N" ){
				alert("접수상태인 경우만 조회팝업이 확인 가능 합니다.");
				return;
			}
 

			var url = "/delivery/store-pick-delivery-change-popup";
			params.dlvyIdText = dlvyIdText;   // params 배송아이디
			params.orderPrdtSeq = orderPrdtSeq;   // 주문상품 순번
			params.orderNo = orderNo;
		
			abc.open.popup({
				winname :	"StorePickDeliveryChange",
				url 	:	url,
				method	: 	"post",
				width 	:	1000,
				height	:	880,
				params	:	params

			});
		}


		//상품명
		if ( dataListSheet.ColSaveName(Col) == "prdtName"   ) {
				
				var prdtNo = dataListSheet.GetRowData(Row).prdtNo;
				var siteNo = dataListSheet.GetRowData(Row).orgSiteNo;
				
				if(siteNo == abc.biz.store.pick.delivery.change.main.SITE_NO_ART){
					window.open(abc.biz.store.pick.delivery.change.main.ABC_FO_URL + "/product?prdtNo=" + prdtNo)
				} else {
					window.open(abc.biz.store.pick.delivery.change.main.OTS_FO_URL + "/product?prdtNo=" + prdtNo)
				}	
		}
		
		//주문배송상태
		if ( dataListSheet.ColSaveName(Col) == "dlvyStatCode") {
			var orderNo =  dataListSheet.GetCellValue(Row, "orderNo");
				var orderPrdtSeq =  dataListSheet.GetCellValue(Row, "orderPrdtSeq");
			 
				abc.orderProductHistoryPopup(orderNo,orderPrdtSeq,"");
		}

		//처리자
		if ( dataListSheet.ColSaveName(Col) == "moder") {
			var moderNo =  dataListSheet.GetCellValue(Row, "moderNo");
			abc.adminDetailPopup(moderNo);
		}
		
	} //end if ( Row != 0) {
} //end function 그리드 Click 이벤트 

	 