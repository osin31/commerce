/***
 * 주문관리 > 배송관리 > 배송조회(자사)
 * 
 */
(function() {

    // define Alert Msg
	var alertSelectEmpty				= "처리대상이 선택되지 않았습니다.\n 처리대상을 확인해주세요.";
	var alertSearchDate			= "검색시작일은 종료일보다 이전 일 수 없습니다.\n검색기간을 다시 설정해주세요.";
	var alertSearchDateOver = "검색기간은 최대 1년 입니다. \n 검색기간을 다시 설정해주세요.";
	var alertLogisVndrCode	= "택배사를 선택 해주세요.";
	var alertWaybilNoText	= "운송장 번호를 입력해 주세요. \n 운송장 번호는 숫자만 가능 합니다.";
	var alertOrderNo				= "주문번호는 숫자만 입력해주세요.";
	var alertReservationlFail = "발송지연 안내 전송될 상태가(결제완료/상품준비중상태) 아닙니다.";
	var alertDoDeliveryOrderCancelPopupFail ="판매취소 요청된 주문은 발송지연 안내가 불가 합니다. \n 주문을 확인해주세요.";
	var alertCheckBoxFail ="최소한 한개 이상의 검색 조건은 체크를 해야 검색이 가능 합니다.";

	// param 정보 선언
	var param = abc.param.getParams();
	var _deliveryJsObject = abc.object.createNestedObject(window,"abc.biz.delivery.order.main");

	_deliveryJsObject.SITE_NO_ART = "";
	_deliveryJsObject.SITE_NO_OTS = "";
	
	_deliveryJsObject.ABC_FO_URL = "";
	_deliveryJsObject.OTS_FO_URL = "";
	
	_deliveryJsObject.NON_MEMBER_NO = "";
	
	
	_deliveryJsObject.initDataListSheet = function() {
		createIBSheet2(document.getElementById("dataListGrid"), "dataListSheet", "100%", "429px");
		
		var initDataListSheet = {};
		var pageCount = $('#pageCount').val();

		initDataListSheet.HeaderMode = {Sort:0, ColMove:0, ColResize:1};
		initDataListSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MaxSort:1};
		initDataListSheet.Cols = [
				  {Header:"" ,		 	Type:"Status",	SaveName:"status",				Width: 0,	 Align:"", 		 Edit:0, Hidden:1}
				, {Header:"", 			Type:"CheckBox",SaveName:"checkedRows",			Width: 40,	 Align:"Center"}
				, {Header:"사이트", 		Type:"Combo",	SaveName:"siteNo",				Width: 100,	 Align:"Center", Edit:0, ComboText: "", ComboCode: "" }
				, {Header:"사이트번호", 	Type:"Text",	SaveName:"orgSiteNo",			Width: 100,	 Align:"Center", Edit:0,	Hidden:1}
				, {Header:"주문일시", 		Type:"Text",	SaveName:"orderDtm",			Width: 150,	 Align:"Center", Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
				, {Header:"주문번호",		Type:"Text",	SaveName:"orderNo",				Width: 120,	 Align:"Center", Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer" }
				, {Header:"주문순번",		Type:"Text",	SaveName:"orderPrdtSeq",		Width: 80,	 Align:"Center", Edit:0 }
				, {Header:"상품코드", 		Type:"Text",	SaveName:"prdtNo",				Width: 150,	 Align:"Center", Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer"}
				, {Header:"상품명", 		Type:"Text",	SaveName:"prdtName",			Width: 200,	 Align:"Left", 	 Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer" }
				, {Header:"옵션", 		Type:"Text",	SaveName:"optnName",			Width: 80,	 Align:"Center", Edit:0}
				, {Header:"결제금액", 		Type:"Int",		SaveName:"orderAmt",			Width: 80,	 Align:"Right",  Edit:0, Format:"#,##0 원"}
				, {Header:"배송유형", 		Type:"Combo",	SaveName:"dlvyTypeCode",		Width: 80,	 Align:"Center", Edit:0}
				, {Header:"주문배송상태", 	Type:"Combo",	SaveName:"dlvyStatCode",		Width: 80,	 Align:"Center", Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer" }
				, {Header:"배송ID",		Type:"Text",	SaveName:"dlvyIdText",	  		Width: 150,	 Align:"Center", Edit:0}
				, {Header:"택배사",		Type:"Combo",	SaveName:"logisVndrCode",		Width: 100,	 Align:"Center", Edit:0}
				, {Header:"운송장번호",		Type:"Text",	SaveName:"waybilNoText",		Width: 150,	 Align:"Center", Edit:0}
				, {Header:"발송처",		Type:"Text",	SaveName:"stockGbnCodeName",		Width: 170,	 Align:"Center", Edit:0}
				, {Header:"주문자",		Type:"Text",	SaveName:"buyerName",			Width: 80,	 Align:"Center", Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer"}
				, {Header:"주문자ID",		Type:"Text",	SaveName:"buyerId",				Width: 80,	 Align:"Center", Edit:0, Hidden:1}
				, {Header:"주문자번호",		Type:"Text",	SaveName:"memberNo",			Width: 80,	 Align:"Center", Edit:0, Hidden:1}
				, {Header:"수령자",		Type:"Text",	SaveName:"rcvrName",			Width: 80,	 Align:"Center", Edit:0}
				, {Header:"주소기본",		Type:"Text",	SaveName:"rcvrPostAddrText",	Width: 150,	 Align:"Center", Edit:0}
				, {Header:"주소상세",		Type:"Text",	SaveName:"rcvrDtlAddrText",		Width: 100,	 Align:"Center", Edit:0}
				, {Header:"판매취소요청", 	Type:"Text",	SaveName:"sellCnclReqYn",		Width: 100,	 Align:"Center", Edit:0, Hidden:1 }
				, {Header:"발송매장",			Type:"Text",	SaveName:"storeName",		Width: 100,	Align:"Center", Edit:0, Hidden:1}
		];

		// Grid 초기화
		IBS_InitSheet(dataListSheet, initDataListSheet);
		
		// Grid 건수 정보 표시
		dataListSheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		dataListSheet.SetPagingPosition(2);
		// Grid width 자동 조절
		//dataListSheet.FitColWidth();
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		dataListSheet.SetExtendLastCol(1);

       dataListSheet.InitDataCombo(0, "dlvyTypeCode",  this.codeCombo.DLVY_TYPE_CODE.text,   this.codeCombo.DLVY_TYPE_CODE.code );		 //배송유형콤보코드
	   dataListSheet.InitDataCombo(0, "dlvyStatCode",  this.codeCombo.DLVY_STAT_CODE.text,   this.codeCombo.DLVY_STAT_CODE.code );		 //주문배송상태
       dataListSheet.InitDataCombo(0, "stockGbnCode",  this.codeCombo.STOCK_GBN_CODE.text,   this.codeCombo.STOCK_GBN_CODE.code );		 //발송처
       dataListSheet.InitDataCombo(0, "logisVndrCode",  this.codeCombo.LOGIS_VNDR_CODE.text,   this.codeCombo.LOGIS_VNDR_CODE.code );		 //택배사
	   
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
				dataListSheet.SetCellFontUnderline(i,"buyerName",0);
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

		//사이트구분 체크 
		$("input[name='siteNoArr']").click(function() {
			if($(this).val() == "") {
				$("input[name='siteNoArr']").prop("checked", this.checked);
			} else {
				$("input[name='siteNoArr']:eq(0)").prop("checked", false);
			}

			//전체 선택인경우
			var unChecked = $("input[clickYn=siteNo]:not(:checked)");
			if (unChecked.length == 0) {
					$("input[name='siteNoArr']:eq(0)").prop("checked", true);
			}
		});

		//배송유형 체크 
		$("input[name='dlvyTypeCodeArr']").click(function() {
			if($(this).val() == "") {
				$("input[name='dlvyTypeCodeArr']").prop("checked", this.checked);
			} else {
				$("input[name='dlvyTypeCodeArr']:eq(0)").prop("checked", false);
			}

			//전체 선택인경우
			var unChecked = $("input[clickYn=dlvyTypeCode]:not(:checked)");
			if (unChecked.length == 0) {
					$("input[name='dlvyTypeCodeArr']:eq(0)").prop("checked", true);
			}
		});

		//배송상태 체크 
		$("input[name='dlvyStatCodeArr']").click(function() {
			if($(this).val() == "") {
				$("input[name='dlvyStatCodeArr']").prop("checked", this.checked);
			} else {
				$("input[name='dlvyStatCodeArr']:eq(0)").prop("checked", false);
			}

			//전체 선택인경우
			var unChecked = $("input[clickYn=dlvyStatCode]:not(:checked)");
			if (unChecked.length == 0) {
					$("input[name='dlvyStatCodeArr']:eq(0)").prop("checked", true);
			}
		});

		//발송처
		$("input[name='stockGbnCodeArr']").click(function() {
			if($(this).val() == "") {
				$("input[name='stockGbnCodeArr']").prop("checked", this.checked);
			} else {
				$("input[name='stockGbnCodeArr']:eq(0)").prop("checked", false);
			}

			//전체 선택인경우
			var unChecked = $("input[clickYn=stockGbnCode]:not(:checked)");
			if (unChecked.length == 0) {
					$("input[name='stockGbnCodeArr']:eq(0)").prop("checked", true);
			}
		});

		//결제수단 체크 
		$("input[name='pymntMeansCodeArr']").click(function() {
			if($(this).val() == "") {
				$("input[name='pymntMeansCodeArr']").prop("checked", this.checked);
			} else {
				$("input[name='pymntMeansCodeArr']:eq(0)").prop("checked", false);
			}
			
			//전체 선택인경우
			var unChecked = $("input[clickYn=pymntMeansCode]:not(:checked)");
			if (unChecked.length == 0) {
					$("input[name='pymntMeansCodeArr']:eq(0)").prop("checked", true);
			}
		});
		

		// 매장찾기 팝업
		$("#searchStorePopUp").click(function(){ 
    		abc.storeSearchPopup('abc.biz.delivery.order.main.setStoreInfo', false);
		}); 

		//발송지연 안내
		 $("#orderReservationPopup").click(function(){ 
			 	_deliveryJsObject.doDeliveryOrderReservationPopup("C");
		});

		// 엑셀전체다운
		$("#allExcelDown").click(function(){ 
			_deliveryJsObject.downloadAllExcel();
		});
		 
		// 2020.05.22 : 주문 검색어 선택 이벤트
		$("#searchOrderKey").change(function(){
			var orderSearchKey = $(this).val();
			_deliveryJsObject.ordMbInfoSelectChange(orderSearchKey);
		});
}
	

	
/*
    발송지연 안내 popup
   */
	_deliveryJsObject.doDeliveryOrderReservationPopup = function(cancelPopupType){

		//선택여부 체크 * 형식이 있기에 두형식으로 사용 해봄
		 var sRow = dataListSheet.CheckedRows("checkedRows");
		 //선택 데이타 Rows
		var checkRows = dataListSheet.FindCheckedRow("checkedRows");

		 if(sRow <1 ){
			alert(alertSelectEmpty);
			return;
		 } //end if

		var dlvyIdText=[];
		var orderPrdtSeq =[];

		//주문번호 중복여부 체크
         var arrRow = checkRows.split("|"); 
		 var params = {};  //popup창 params
          var afterOrderNo= "";

		 for (idx=0; idx<arrRow.length; idx++){  
			var orderNo =  dataListSheet.GetCellValue( arrRow[idx], "orderNo");
			var dlvyIdText =  dataListSheet.GetCellValue( arrRow[idx], "dlvyIdText");
			var dlvyStatCode =  dataListSheet.GetCellValue( arrRow[idx], "dlvyStatCode");
			var sellCnclReqYn =  dataListSheet.GetCellValue( arrRow[idx], "sellCnclReqYn"); //판매취소 요청
			
            if( afterOrderNo != "" && afterOrderNo != orderNo){
				alert("한 주문단위로 전송이 가능 합니다.");
				return;
			} 

			//판매취소 요청
			if(sellCnclReqYn == "Y" ){
				alert(alertDoDeliveryOrderCancelPopupFail+"\n(주문번호:"+orderNo+")");
				return;
			}

			if(dlvyStatCode != "10000" && dlvyStatCode !="10001" ){
				alert(alertReservationlFail+"\n(주문번호 :"+orderNo+")");
				return;
			} else{
				 dlvyIdText[idx] = dataListSheet.GetCellValue( arrRow[idx] , "dlvyIdText");
				 orderPrdtSeq[idx] = dataListSheet.GetCellValue( arrRow[idx] , "orderPrdtSeq");
			}
			afterOrderNo = orderNo;
		 }//end for
		  
		var url = "/delivery/vendor/delivery-order-vendor/reservation-popup";

		params.orderNo = orderNo;  // params 주문번호
		params.dlvyIdText = dlvyIdText;   // params 배송아이디
		params.orderPrdtSeq = orderPrdtSeq;   // 주문상품 순번
		params.cancelPopupType = cancelPopupType;  // 팝업 호출 구분 C:등록 R:읽기
	
		abc.open.popup({
			winname :	"DeliveryreservationPopup",
			url 	:	url,
			method	: 	"post",
			width 	:	1240,
			height	:	600,
			params	:	params

		});

	} //end function _deliveryJsObject.doDeliveryOrderReservationPopup




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
				var pageCount = $('#pageCount').val();
				var param = { url : "/delivery/delivery-order/read-list"
							 , onePageRow : pageCount
							 , subparam : FormQueryStringEnc(document.gForm)
							 , sheet : "dataListSheet" };
				
				DataSearchPaging(param);
				break;
		}
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
		} //end  if (!abc 
		
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

		/**
		 * 운송장 정보 체크
		 */
	 	 var waybilNoText = $("#waybilNoText").val();  //운송장번호
	 	 var logisVndrCode = $("#logisVndrCode").val();  //택배사코드
 
		  if (  isNaN( waybilNoText )  ) {
			  alert(alertWaybilNoText );
			  $("#waybilNoText").focus();
			  return false;
		 }

		 if (!abc.text.allNull( logisVndrCode) &&  abc.text.allNull( waybilNoText)) {
			  alert( alertWaybilNoText );
			  $("#waybilNoText").focus();
			  return false;
		 } 

		 if (abc.text.allNull( logisVndrCode) && !abc.text.allNull( waybilNoText)) {
			 alert(alertLogisVndrCode);
 			  $("#logisVndrCode").focus();
			  return false;
		   } 

		return true;

	} //end _deliveryJsObject.searchValidate

	_deliveryJsObject.downloadAllExcel = function() {
		
		gForm.action = "/delivery/delivery-order/download-all-excel";
		gForm.submit();
	}
	
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
			if ( dataListSheet.ColSaveName(Col) == "prdtName"   ) {
					
					var prdtNo = dataListSheet.GetRowData(Row).prdtNo;
					var siteNo = dataListSheet.GetRowData(Row).orgSiteNo;
					
					if(siteNo == abc.biz.delivery.order.main.SITE_NO_ART){
						window.open(abc.biz.delivery.order.main.ABC_FO_URL + "/product?prdtNo=" + prdtNo)
					} else {
						window.open(abc.biz.delivery.order.main.OTS_FO_URL + "/product?prdtNo=" + prdtNo)
					}	
			}
			
			//주문배송상태
			if ( dataListSheet.ColSaveName(Col) == "dlvyStatCode") {
				var orderNo =  dataListSheet.GetCellValue(Row, "orderNo");
				var orderPrdtSeq =  dataListSheet.GetCellValue(Row, "orderPrdtSeq");
			 
				abc.orderProductHistoryPopup(orderNo,orderPrdtSeq,"");
			}
			
			//주문자
			if ( dataListSheet.ColSaveName(Col) == "buyerName") {
				var memberNo =  dataListSheet.GetCellValue(Row, "memberNo");
				if( memberNo != abc.biz.delivery.order.main.NON_MEMBER_NO){
					abc.memberDetailPopup(memberNo);
				}
			}
		}
} //end function 그리드 Click 이벤트 
