/***
 * 주문관리 > 배송관리 > 분실배송조회
 * 
 */
(function() {

    // define Alert Msg
	var alertSelectEmpty				= "처리대상이 선택되지 않았습니다.\n 처리대상을 확인해주세요.";
 	var alertConfirmSvae = "선택한 주문의 배송비 정보를 저장 하시겠습니까?";
	var alertSearchDate			= "검색시작일은 종료일보다 이전 일 수 없습니다.\n검색기간을 다시 설정해주세요.";
	var alertSearchDateOver = "검색기간은 최대 1년 입니다. \n 검색기간을 다시 설정해주세요.";
	var alertLogisVndrCode	= "택배사를 선택 해주세요.";
	var alertWaybilNoText	= "운송장 번호를 입력해 주세요. \n 운송장 번호는 숫자만 가능 합니다.";
	var alertInputValue ="택배사 상품금액,배송금액 중 한가지는 입력해주세요";
	var alertOrderNo				= "주문번호는 숫자만 입력해주세요.";

	// param 정보 선언
	var param = abc.param.getParams();
	var _deliveryJsObject = abc.object.createNestedObject(window,"abc.biz.delivery.order.loss.main");

	_deliveryJsObject.initDataListSheet = function() {
		createIBSheet2(document.getElementById("dataListGrid"), "dataListSheet", "100%", "429px");
		
		var initDataListSheet = {};
		var pageCount = $('#pageCount').val();

		initDataListSheet.HeaderMode = {Sort:0, ColMove:0, ColResize:1};
		initDataListSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MaxSort:1};
		initDataListSheet.Cols = [
			   {Header:"" ,		 		Type:"Status",			SaveName:"status",				Width: 0,		 Align:"", 			Edit:0, Hidden:1}
			, {Header:"", 				Type:"CheckBox",		SaveName:"checkedRows",			Width: 40,		 Align:"Center",	Sort:0}
			, {Header:"사이트", 			Type:"Combo",			SaveName:"siteNo",				Width: 100,		 Align:"Center", 	Edit:0, ComboText: "", ComboCode: "" }
			, {Header:"주문번호",			Type:"Text",			SaveName:"orderNo",				Width: 120,		 Align:"Center",	Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer" }
			, {Header:"배송ID",			Type:"Text",			SaveName:"dlvyIdText",	  		Width: 150,		 Align:"Center", 	Edit:0}
			, {Header:"배송처",		Type:"Text",	SaveName:"stockGbnCodeName",		Width: 170,	 Align:"Center", Edit:0}
			, {Header:"상품코드", 			Type:"Text",			SaveName:"prdtNo",				Width: 150,		 Align:"Center", 	Edit:0}
			, {Header:"상품명", 			Type:"Text",			SaveName:"prdtName",			Width: 200,		 Align:"Left", 		Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer" }
			, {Header:"주문자",			Type:"Text",			SaveName:"buyerInfo",			Width: 130,		 Align:"Center", 	Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer" }
			, {Header:"처리구분",			Type:"Text",			SaveName:"missProcTypeCodeName",Width: 100,		 Align:"Center", 	Edit:0}
			, {Header:"택배사",			Type:"Text",			SaveName:"logisVndrCodeName",	Width: 100,		 Align:"Center", 	Edit:0}
			, {Header:"운송장번호",			Type:"Text",			SaveName:"waybilNoText",		Width: 150,		 Align:"Center", 	Edit:0}
			, {Header:"재배송일", 			Type:"Text",			SaveName:"dlvyProcDtm",			Width: 130,		 Align:"Center", 	Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			, {Header:"택배사결제\n상품금액",	Type:"Int",				SaveName:"logisPymntPrdtAmt",	Width: 80,		 Align:"Center",	Edit:1, EmptyToReplaceChar:"<font size='1'>금액을 입력해주세요</font>" }
			, {Header:"택배사결제\n배송비",	Type:"Int",				SaveName:"logisPymntDlvyAmt",	Width: 80,		 Align:"Center",	Edit:1, EmptyToReplaceChar:"<font size='1'>금액을 입력해주세요</font>"  }
			, {Header:"처리자",			Type:"Text",			SaveName:"moderInfo",			Width: 130,		 Align:"Center",	Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer" }
			, {Header:"처리일시", 			Type:"Text",			SaveName:"modDtm",				Width: 130,		 Align:"Center", 	Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			, {Header:"회원번호",			Type:"Text",			SaveName:"memberNo",			Width: 150,		 Align:"Center",	Edit:0, Hidden:1}
			, {Header:"처리자번호",			Type:"Text",			SaveName:"moderNo",				Width: 150,		 Align:"Center",	Edit:0, Hidden:1}
			, {Header:"처리구분코드",		Type:"Text",			SaveName:"missProcTypeCode",	Width: 100,		 Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"발송매장",			Type:"Text",			SaveName:"storeName",			Width: 100,		 Align:"Center", 	Edit:0, Hidden:1}
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
 
       dataListSheet.InitDataCombo(0, "stockGbnCode",  this.codeCombo.STOCK_GBN_CODE.text,   this.codeCombo.STOCK_GBN_CODE.code );		 //발송처
	   dataListSheet.InitDataCombo(0, "siteNo",  this.siteCombo .text,   this.siteCombo.code );		//사이트 콤보 
	   
	}	 

	_deliveryJsObject.NON_MEMBER_NO = "";

	//조회가 정상적으로 발생된 이후 발생
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
			// 비회원이면 unline 제거
			var memberNo = dataListSheet.GetRowData(i).memberNo;
			if( memberNo == _deliveryJsObject.NON_MEMBER_NO){
				dataListSheet.SetCellFontUnderline(i,"buyerInfo",0);
			}
			
			// 분실취소 면  재배송일값 ''
			var missProcTypeCode = dataListSheet.GetRowData(i).missProcTypeCode;
			if( missProcTypeCode == "10001" ){
				dataListSheet.SetRowData(i, {dlvyProcDtm : ""});
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
	 
	} //end if _deliveryJsObject.init 

	
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
		
		//운송장정보 번호만 입력되게
		$('#waybilNoText').off().on('keyup', function(e){
			var exReg = /[^0-9]/g;
			var bilText = $(this).val();
			
			if(exReg.test(bilText)) {
				$(this).val(bilText.replace(exReg, ''));
			}
			
		});
 
		 //저장하기
		 $("#deliverySave").click(function(){ 
			_deliveryJsObject.doDeliverySave();
		});

		//선택 엑셀 받기
		 $("#excelDownSelect").click(function(){ 
			    $("#excelType").val("SELECT");
			 	_deliveryJsObject.doActionJs("excel");
		});

		//전체 엑셀 받기
		 $("#excelDownAll").click(function(){ 
			    $("#excelType").val("ALL");
			 	_deliveryJsObject.doActionJs("excel");
		});

		// 2020.05.22 : 주문 검색어 선택 이벤트
		$("#searchOrderKey").change(function(){
			var orderSearchKey = $(this).val();
			_deliveryJsObject.ordMbInfoSelectChange(orderSearchKey);
		});
	} //end if _deliveryJsObject.event 
	 

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
	 * Data Action관리
	 */
	_deliveryJsObject.doActionJs = function(inAction){
		switch(inAction) {
			// 조회 
			case "search" :
				var pageCount = $('#pageCount').val();
				var param = { url : "/delivery/delivery-order-loss/read-list"
							 , onePageRow : pageCount
							 , subparam : FormQueryStringEnc(document.gForm)
							 , sheet : "dataListSheet" };
				
				DataSearchPaging(param);
				break;

				// 선택엑셀 다운 
			case "excel" :
				var excelType = $("#excelType").val() ;
			    var dlvyIdText=[]; //배송아이디 Arr

				if(excelType == "SELECT" ){
					 //선택 데이타 Rows
					 var checkRows = dataListSheet.FindCheckedRow("checkedRows"); 

					 if( checkRows =="" ){
							alert(alertSelectEmpty);
							return;
					 } //end if(sRow

					 //제약 사항 체크
					 var html;
					 
					 for(var i=1; i <= dataListSheet.RowCount(); i++){
						 if(dataListSheet.GetCellValue(i, "checkedRows") == "1"){
							 html = "<input type='hidden' name='dlvyIdText' value='" + dataListSheet.GetCellValue(i, "dlvyIdText") + "'>";
							 $("#gForm").append(html);
						 }
					 }
				} //end if   if(excelT

				gForm.action ="/delivery/delivery-order-loss/excel";
				gForm.submit();	
			    
			    // 다운로드 후, 초기화
			    $("input[name='dlvyIdText']").remove();

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
		} //end  if (!abc 
		
		 
		/**
		 * 검색 기간 체크
		 */
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

	
	/**
	 *  재배송 처리 상태
	 */
	_deliveryJsObject.doDeliverySave = function(){
		
		//선택여부 체크 * 형식이 있기에 두형식으로 사용 해봄
		 var sRow = dataListSheet.CheckedRows("checkedRows");
		 //선택 데이타 Rows
		var checkRows = dataListSheet.FindCheckedRow("checkedRows"); 
		 
		 
		 if(sRow <1 ){
				alert(alertSelectEmpty);
				return;
		 } //if(sRow

     
		 //제약 사항 체크
         var arrRow = checkRows.split("|"); 

		 for (idx=0; idx<arrRow.length; idx++){   
			var dlvyIdText =  dataListSheet.GetCellValue( arrRow[idx], "dlvyIdText");   // 배송 ID
			var logisPymntPrdtAmt =  dataListSheet.GetCellValue( arrRow[idx], "logisPymntPrdtAmt");   // 택배사결제 상품금액
			var logisPymntDlvyAmt =  dataListSheet.GetCellValue( arrRow[idx], "logisPymntDlvyAmt");   // 택배사결제 배송금액
			var dlvyIdText =  dataListSheet.GetCellValue( arrRow[idx], "dlvyIdText");   // 배송 ID
			
			/*
			if( abc.text.allNull( logisPymntDlvyAmt) &&  abc.text.allNull( logisPymntPrdtAmt)  ){
				alert( alertInputValue +"\n (배송아이디 : "+dlvyIdText+")" );
				return;
			}//end if if( abc.text.allNull( logisPymn
			*/

			if( logisPymntDlvyAmt <1  &&  logisPymntPrdtAmt < 1  ){
				alert( alertInputValue +"\n (배송아이디 : "+dlvyIdText+")" );
				return;
			}//end if if( abc.text.allNull( logisPymn

			//관련 Col번호를 가져오기 - Grid값이 유동적이기에...
			var ColDlvyNumber = dataListSheet.SaveNameCol("logisPymntDlvyAmt"); 
			var ColPrdtNumber = dataListSheet.SaveNameCol("logisPymntPrdtAmt"); 

			//value가 없을 경우 default ->0 넣어준다.
			if( !abc.text.allNull( logisPymntDlvyAmt) &&  abc.text.allNull( logisPymntPrdtAmt)  ){
				 dataListSheet.SetCellValue(arrRow[idx], ColPrdtNumber , "0");
			}

			if( abc.text.allNull( logisPymntDlvyAmt) &&  !abc.text.allNull( logisPymntPrdtAmt)  ){
				 dataListSheet.SetCellValue(arrRow[idx], ColDlvyNumber, "0");
			}

		 }//end for

		// save
		if(confirm( alertConfirmSvae )){
				 var param = {
								url : "/delivery/delivery-order-loss/save",
								subparam : FormQueryStringEnc(document.gForm),
								sheet : "dataListSheet"
							};
				DataSave(param);
		} //end confirm

     } //end _deliveryJsObject.doDeliveryRepeatSave 


	

})();	 //end  (function() {


 //그리드 Click 이벤트 
function dataListSheet_OnClick(Row, Col, Value) {
		if ( Row != 0) {
			
			//주문상세
			if ( dataListSheet.ColSaveName(Col) == "orderNo") {
				abc.orderDetailPopup(dataListSheet,"orderNo",Row,Col,"");
			}
			
			//상품명
			if ( dataListSheet.ColSaveName(Col) == "prdtName"  ) {
				var prdtNo =  dataListSheet.GetCellValue(Row, "prdtNo");
				var param = { prdtNo : prdtNo };
				abc.readonlyProductDetailPopup(param);
			}
			
			//주문자
			if( dataListSheet.ColSaveName(Col) == "buyerInfo" ){
				var memeberNo = dataListSheet.GetCellValue(Row, "memberNo");
				if( memeberNo != abc.biz.delivery.order.loss.main.NON_MEMBER_NO ){
					abc.memberDetailPopup(memeberNo);
				}
			}
			
			//처리자
			if( dataListSheet.ColSaveName(Col) == "moderInfo" ){
				var adminNo = dataListSheet.GetCellValue(Row, "moderNo");
				if( !abc.text.allNull(adminNo) ){
					abc.adminDetailPopup(adminNo);
				}
			}
			
			/*
			//주문배송상태
			if ( dataListSheet.ColSaveName(Col) == "dlvyStatCode") {
				var orderNo =  dataListSheet.GetCellValue(Row, "orderNo");
				var orderPrdtSeq =  dataListSheet.GetCellValue(Row, "orderPrdtSeq");
			 
				abc.orderProductHistoryPopup(orderNo,orderPrdtSeq,"");
			}
			*/
 		}
} //end function 그리드 Click 이벤트 


/**
	  그리드 변경 처리시 사용
	*/
function 	dataListSheet_OnChange(Row, Col, Value){
			
		var logisPymntDlvyAmt = dataListSheet.GetCellValue( Row , "logisPymntDlvyAmt") //해당 row의 배송비 금액
		var logisPymntPrdtAmt = dataListSheet.GetCellValue( Row , "logisPymntPrdtAmt") //해당 row의 상품금액

		var ColCheckedRowsNumber = dataListSheet.SaveNameCol("checkedRows");  //체크 박스 Col번호

		//체크박스는 영향을 안받게 하기위해
		if( Col != 1 ){
			//
			if( abc.text.allNull( logisPymntDlvyAmt) &&  abc.text.allNull( logisPymntPrdtAmt)  ){
					dataListSheet.SetCellValue(Row, ColCheckedRowsNumber, 0);  //체크박스 체크처리
			}else{
					dataListSheet.SetCellValue(Row, ColCheckedRowsNumber, 1); 
			}
		}//end if Col

		//에디터 가능 불가처리  - 리스트 출력후 금액이 있는 경우는 수정 못하게 변경 해야 할 듯 하다.
		//mySheet.SetColEditable(5,0);   col:12-13 상품금액, 배송비금액
		
} //end _deliveryJsObject_OnChange


