/***
 * 주문관리 > 배송관리 > 배송조회(업체)
 * 
 */
(function() {

		// define Alert Msg
		var alertOrderNo				= "주문번호는 숫자만 입력해주세요.";
		var alertHdphnNoNo				= "핸드폰 번호는  숫자만 입력해주세요.";
		var alertSelectEmpty				= "처리대상이 선택되지 않았습니다.\n 처리대상을 확인해주세요.";
		var alertExcelEmpty				= "엑셀 다운받을 대상을 선택해주세요.";
		var alertConfirmSvae01 = "건의 주문상품을 상품준비중 처리 하시겠습니까?";
		var alertConfirmSvae02 = "건의 주문상품을 배송중 처리 하시겠습니까?";
		var alertConfirmSvae03 = "건의 주문상품을 배송완료 처리 하시겠습니까?";
		var alertDoDeliveryOrderCancelPopup ="이미 판매취소 요청된 주문이 있습니다. \n 주문을 확인해주세요.";
		var alertDoDeliveryOrderCancelPopupFail ="판매취소 요청된 주문은 발송지연 안내가 불가 합니다. \n 주문을 확인해주세요.";
		var alertExcelAll		= "검색 후 검색 내용이 있어야 엑셀 다운이 가능 합니다.";
		var alertExcelSelect		= "엑셀 다운로드 하실 대상을 선택 해주세요.";
		var alertSearchDate			= "검색시작일은 종료일보다 이전 일 수 없습니다.\n검색기간을 다시 설정해주세요.";
		var alertSearchDateOver = "검색기간은 최대 1년 입니다. \n 검색기간을 다시 설정해주세요.";
		var alertChangeDlvyStat ="일괄적용 상태를 선택해주세요";

		var alertCancelFail = "취소 불가능한 상태주문입니다.";
		var alertReservationlFail = "발송지연 안내 전송될 상태가(결제완료/상품준비중상태) 아닙니다.";

		//스크립트 Object선언
		var _deliveryJsObject = abc.object.createNestedObject(window,"abc.biz.delivery.order.vendor.main");

		_deliveryJsObject.SITE_NO_ART  = "";
		_deliveryJsObject.SITE_NO_OTS  = "";
		
		_deliveryJsObject.ABC_FO_URL  = "";
		_deliveryJsObject.OTS_FO_URL  = "";
		
		_deliveryJsObject.NON_MEMBER_NO  = "";
		
		_deliveryJsObject.ORDER_PRDT_STAT_CODE_CANCEL_COMPLETE  = "";
		
		_deliveryJsObject.initDataListSheet = function() {
		createIBSheet2(document.getElementById("dataListGrid"), "dataListSheet", "100%", "429px");
		
		var initDataListSheet = {};
		var pageCount = $('#pageCount').val();
		
		initDataListSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MaxSort:1};
		initDataListSheet.HeaderMode = {Sort:0, ColMove:0, ColResize:1};
		initDataListSheet.Cols = [
				  {Header:"" ,		 			Type:"Status",		SaveName:"status",					Width: 50,	Align:"", 		Edit:0, Hidden:1}
				, {Header:"", 					Type:"CheckBox",	SaveName:"checkedRows",				Width: 40,	Align:"Center"}
				, {Header:"업체명", 				Type:"Text",		SaveName:"vndrName",				Width: 170,	Align:"Center", Edit:0}
				, {Header:"업체번호", 				Type:"Text",		SaveName:"vndrNo",					Width: 100, Align:"Center", Edit:0}
				, {Header:"사이트", 				Type:"Combo",		SaveName:"siteNo",					Width: 100, Align:"Center", Edit:0, ComboText: "", ComboCode: "" }
				, {Header:"사이트번호", 			Type:"Text",		SaveName:"orgSiteNo",				Width: 100, Align:"Center", Edit:0,	Hidden:1}
				, {Header:"결제구분", 				Type:"Combo",		SaveName:"deviceCode",				Width: 70,  Align:"Center", Edit:0}
				, {Header:"주문상품구분",			Type:"Combo",		SaveName:"prdtTypeCode",			Width: 100, Align:"Center",	Edit:0}
				, {Header:"주문일시",				Type:"Date",		SaveName:"orderDtm",				Width: 150, Align:"Center",	Edit:0, Format:"YmdHms"}
				, {Header:"주문번호",				Type:"Text",		SaveName:"orderNo",					Width: 120, Align:"Center", Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer"} 
				, {Header:"주문상품순번", 			Type:"Text",		SaveName:"orderPrdtSeq",			Width: 80, 	Align:"Center",	Edit:0,	Hidden:1 }
				, {Header:"업체상품코드",			Type:"Text",		SaveName:"vndrPrdtNoText",			Width: 150, Align:"Center",	Edit:0, Sort:0}
				, {Header:"배송ID",				Type:"Text",		SaveName:"dlvyIdText",	  			Width: 150, Align:"Center",	Edit:0}
				, {Header:"상품배송상태", 			Type:"Text",		SaveName:"orderPrdtStatCode",		Width: 100, Align:"Center",	Edit:0,	Hidden:1 }
				, {Header:"상품배송상태Befor", 		Type:"Text",		SaveName:"orderPrdtStatCodeBefore",	Width: 100, Align:"Center",	Edit:0,	Hidden:1 }
    			, {Header:"주문배송상태", 			Type:"Combo",		SaveName:"dlvyStatCode",			Width: 80, 	Align:"Center",	Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer"}
				, {Header:"주문배송상태Befor", 		Type:"Text",		SaveName:"dlvyStatCodeBefore",		Width: 100, Align:"Center",	Edit:0, Hidden:1}
				, {Header:"택배사",				Type:"Combo",		SaveName:"logisVndrCode",			Width: 100, Align:"Center",	Edit:0}
				, {Header:"운송장번호",				Type:"Text",		SaveName:"waybilNoText",			Width: 150, Align:"Center",	Edit:0}
				, {Header:"배송처리일자",			Type:"Date",		SaveName:"dlvyProcDtm",				Width: 100, Align:"Center",	Edit:0, Format:"Ymd"}				
				, {Header:"상품코드", 				Type:"Text",		SaveName:"prdtNo",					Width: 150, Align:"Center",	Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer" }
				, {Header:"상품명", 				Type:"Text",		SaveName:"prdtName",				Width: 200, Align:"Left", 	Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer" }
				, {Header:"옵션", 				Type:"Text",		SaveName:"optnName",				Width: 80, 	Align:"Center",	Edit:0}
				, {Header:"판매가", 				Type:"Int",			SaveName:"sellAmt",					Width: 80,	Align:"Right", 	Edit:0, Format:"#,##0 원"}			
				, {Header:"결제금액", 				Type:"Int",			SaveName:"orderAmt",				Width: 80, 	Align:"Right", 	Edit:0, Format:"#,##0 원"}			
				, {Header:"판매취소요청", 			Type:"Text",		SaveName:"sellCnclReqYn",			Width: 100,	Align:"Center",	Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer" }
				, {Header:"발송지연\n안내여부", 		Type:"Text",		SaveName:"delaySendYn",				Width: 100, Align:"Center",	Edit:0 }
				, {Header:"주문자",				Type:"Text",		SaveName:"buyerName",				Width: 80,	Align:"Center",	Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer" }
				, {Header:"주문자ID",				Type:"Text",		SaveName:"buyerId",					Width: 80, 	Align:"Center",	Edit:0, Hidden:1}
				, {Header:"주문자번호",				Type:"Text",		SaveName:"memberNo",				Width: 80,	Align:"Center", Edit:0, Hidden:1}
				, {Header:"주문자\n휴대폰번호",		Type:"Text",		SaveName:"buyerHdphnNoText",		Width: 150, Align:"Center", Edit:0}
				, {Header:"브랜드",				Type:"Text",		SaveName:"brandName",				Width: 100, Align:"Center", Edit:0}
				, {Header:"브랜드번호",				Type:"Text",		SaveName:"brandNo",					Width: 100, Align:"Center", Edit:0, Hidden:0}
				, {Header:"배송비", 				Type:"Int",			SaveName:"logisPymntDlvyAmt",		Width: 80, 	Align:"Right", 	Edit:0, Format:"#,##0 원", Hidden:1 }			
				, {Header:"상품\n총 할인금액", 		Type:"Int",			SaveName:"totalDscntAmt",			Width: 80, 	Align:"Right", 	Edit:0, Format:"#,##0 원"}			
				, {Header:"적용쿠폰할인\n(일반/플러스)",Type:"Int",			SaveName:"cpnDscntAmt",				Width: 80,	Align:"Right", 	Edit:0, Format:"#,##0 원"}			
				, {Header:"수령자",				Type:"Text",		SaveName:"rcvrName",				Width: 100, Align:"Center", Edit:0 , Hidden:0 }
				, {Header:"수령자전화번호",			Type:"Text",		SaveName:"rcvrTelNoText",			Width: 100, Align:"Center", Edit:0 , Hidden:0 }
				, {Header:"수령자핸드폰번호",			Type:"Text",		SaveName:"rcvrHdphnNoText",			Width: 100, Align:"Center", Edit:0 , Hidden:0 }
				, {Header:"수령자우편번호",			Type:"Text",		SaveName:"rcvrPostCodeText",		Width: 100, Align:"Center", Edit:0 , Hidden:0 }
				, {Header:"수령자주소",				Type:"Text",		SaveName:"rcvrPostAddrText",		Width: 100, Align:"Center", Edit:0 , Hidden:0 }
				, {Header:"수령자상세주소",			Type:"Text",		SaveName:"rcvrDtlAddrText",			Width: 100, Align:"Center", Edit:0 , Hidden:0 }
				, {Header:"매출구분코드", 			Type:"Text",		SaveName:"salesCnclGbnType",		Width: 80,	Align:"Center", Edit:0,  Hidden:1}
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

		dataListSheet.InitDataCombo(0, "siteNo",  this.siteCombo.text,   this.siteCombo.code );		//사이트 콤보
		dataListSheet.InitDataCombo(0, "deviceCode",  this.codeCombo.DEVICE_CODE.text,   this.codeCombo.DEVICE_CODE.code );		//디바이스 구분
		dataListSheet.InitDataCombo(0, "dlvyStatCode",  this.codeCombo.DLVY_STAT_CODE.text,   this.codeCombo.DLVY_STAT_CODE.code );		 //주문배송상태
		dataListSheet.InitDataCombo(0, "orderPrdtStatCode",  this.codeCombo.ORDER_PRDT_STAT_CODE.text,   this.codeCombo.ORDER_PRDT_STAT_CODE.code );		 //주문배송상태
		dataListSheet.InitDataCombo(0, "logisVndrCode",  this.codeCombo.LOGIS_VNDR_CODE.text,   this.codeCombo.LOGIS_VNDR_CODE.code );		 //택배사
		dataListSheet.InitDataCombo(0, "prdtTypeCode",  this.codeCombo.PRDT_TYPE_CODE.text,   this.codeCombo.PRDT_TYPE_CODE.code );		 //주문상품 구분

		//입점업체 구분
		var vendorYn =  $("#vndrGbnType").val();
		if( vendorYn =="Y" ){
			dataListSheet.SetColHidden(dataListSheet.SaveNameCol('logisCnvrtRsnCode'), 1);
			dataListSheet.SetColHidden(dataListSheet.SaveNameCol('stockGbnCode'), 1);
			dataListSheet.SetColFontUnderline ("buyerName", 0);

		   var info = { FontBold:0,  Cursor:"default"};
			dataListSheet.InitCellProperty( dataListSheet.SaveNameCol('buyerName'), "Text", info);

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
			 $("#waybilNoDisplay").css("display","none");  //주문배송상태 -운송장 정보 잇음 미노출

			//주문상품 상태별 건수
			_deliveryJsObject.getDeliveryOrderVendorPrdtStatCnt();
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

		//  검색
//		$("#gFormSearch").click(function(){
//
//			//검색 Validate
//			if( _deliveryJsObject.searchValidate()){
//				_deliveryJsObject.doActionJs("search");
//			}
//		});

		// 새로고침
		$("#icoRefresh").click(function(){
			_deliveryJsObject.getDeliveryOrderVendorPrdtStatCnt();
		});
		
		//사이트구분 체크 
//		$("input[name='siteNoArr']").click(function() {
//			
//			if($(this).val() == "") {
//				$("input[name='siteNoArr']").prop("checked", this.checked);
//			} else {
//				$("input[name='siteNoArr']:eq(0)").prop("checked", false);
//			}
//			
//			//전체 선택인경우
//			var unChecked = $("input[clickYn=siteNo]:not(:checked)");
//			if (unChecked.length == 0) {
//					$("input[name='siteNoArr']:eq(0)").prop("checked", true);
//			}
//		});
        

		// 주문번호 입력시 숫자만
		$("#orderNo").keyup(function(){
			_deliveryJsObject.validateOnlyNumber(this);
		});

		//주문배송상태 - 체크박스 노출 - 
		$("#dlvyStatCode").change(function(){
			  var  dlvyStatCode = $('#dlvyStatCode').val();
			   
			   //상품준비중인경우에만 
			   if (dlvyStatCode =="10001") {
				    $("#waybilNoDisplay").css("display","");
			   }else{
				   $("#waybilNoDisplay").css("display","none");
				   $("#waybilNoYn").prop("checked", true);
			   } 
		});
		
		//업체 찾기 팝업
		$("#searchVendor").click(function(){
			// false 단건 , false n건
			abc.vndrSearchPopup(false, "setSelectedVendors");
		});

		//브랜드 찾기 팝업
		$("#serchBrandPop").click(function(){
			abc.brandSearchPopup(false, "setSelectedBrand");
		});

		//택배사 전체 엑셀 받기
		 $("#logisVndrCodeExcelDown").click(function(){ 
			 	_deliveryJsObject.doActionJs("logisExcel");
		});

		//택배사 전체 보기 팝업
		 $("#logisVndrCodePopup").click(function(){ 
			 	_deliveryJsObject.doActionJs("logisPopup");
		});

		//일괄적용
		 $("#dlvyStatCodeUpdate").click(function(){ 
			 	_deliveryJsObject.doDeliveryStatCodeUpdate();
		});

		//저장
		 $("#deliverySave").click(function(){ 
			 	_deliveryJsObject.doDeliveryStatCodeSave();
		});

		//판매취소 요청
		 $("#orderCancelPopup").click(function(){ 
			 	_deliveryJsObject.doDeliveryOrderCancelPopup("C");
		});
		
		//발송지연 안내
		 $("#orderReservationPopup").click(function(){ 
			 	_deliveryJsObject.doDeliveryOrderReservationPopup("C");
		});

		//엑셀업로드
		 $("#orderExcelUpload").click(function(){ 
			 	_deliveryJsObject.doDeliveryOrderExcelPopup("C");
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
		 
		//업체 주문 배송관리 건수 클릭시 검색조건 셋팅
		$(".cntNumber").on('click', function(){
			_deliveryJsObject.cntNumberClick($(this).prop("id"));
		});

		// 2020.05.22 : 주문 검색어 선택 이벤트
		$("#searchOrderKey").change(function(){
			var orderSearchKey = $(this).val();
			_deliveryJsObject.ordMbInfoSelectChange(orderSearchKey);
		});
} //end function _deliveryJsObject.even
 
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
			var dlvyStatCode =  dataListSheet.GetCellValue( arrRow[idx], "dlvyStatCode");
			var dlvyStatCodeBefore 	=  dataListSheet.GetCellValue( arrRow[idx], "dlvyStatCodeBefore");	// 배송상태 값
			var dlvyIdText =  dataListSheet.GetCellValue( arrRow[idx], "dlvyIdText");
			var sellCnclReqYn =  dataListSheet.GetCellValue( arrRow[idx], "sellCnclReqYn"); //판매취소 요청
 
			//판매취소 요청
			if(sellCnclReqYn == "Y" ){
				alert(alertDoDeliveryOrderCancelPopupFail+"\n(주문번호:"+orderNo+")");
				return;
			}


			 if( afterOrderNo != "" && afterOrderNo != orderNo){
				alert("한 주문단위로 전송이 가능 합니다.");
				return;
			}

			// 주문배송 상태값은 '상품 준비중[10001]'의 경우는 그리드에  택배사, 운송장 번호를 입력하면 자동으로 주문배송상태 셀렉트 값을 바꾸므로 원본 배송 상태값으로 판단한다.
			if(dlvyStatCodeBefore != "10000" && dlvyStatCodeBefore !="10001" ){
				alert(alertReservationlFail+"\n(주문번호:"+orderNo+")");
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
			width 	:	750,
			height	:	650,
			params	:	params

		});

	} //end function _deliveryJsObject.doDeliveryOrderReservationPopup


   /*
    판매취소 요청 popup
	cancelPopupType : 팝업 호출 구분 C:등록 R:읽기
   */
	_deliveryJsObject.doDeliveryOrderCancelPopup = function(cancelPopupType){
		
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

		 for (idx=0; idx<arrRow.length; idx++){  
			var orderNo =  dataListSheet.GetCellValue( arrRow[idx], "orderNo");
			var dlvyStatCode =  dataListSheet.GetCellValue( arrRow[idx], "dlvyStatCode");
			var sellCnclReqYn =  dataListSheet.GetCellValue( arrRow[idx], "sellCnclReqYn"); //판매취소 요청
 
			//판매취소 요청
			if(sellCnclReqYn == "Y" ){
				alert(alertDoDeliveryOrderCancelPopup+"\n(주문번호:"+orderNo+")");
				return;
			}

			if(dlvyStatCode != "10000" && dlvyStatCode !="10001" ){
				alert(alertCancelFail+"\n(배송 ID :"+ dataListSheet.GetCellValue( arrRow[idx] , "dlvyIdText") +")");
				return;
			} else{
				 dlvyIdText[idx] = dataListSheet.GetCellValue( arrRow[idx] , "dlvyIdText");
				 orderPrdtSeq[idx] = dataListSheet.GetCellValue( arrRow[idx] , "orderPrdtSeq");
			
			}
		 }//end for
		  
		var url = "/delivery/vendor/delivery-order-vendor/cancel-popup";

		params.orderNo = orderNo;  // params 주문번호
		params.dlvyIdText = dlvyIdText;   // params 배송아이디
		params.orderPrdtSeq = orderPrdtSeq;   // 주문상품 순번
		params.cancelPopupType = cancelPopupType;  // 팝업 호출 구분 C:등록 R:읽기
	
		abc.open.popup({
			winname :	"DeliveryCancelPopup",
			url 	:	url,
			method	: 	"post",
			width 	:	750,
			height	:	650,
			params	:	params

		});

	} //end function _deliveryJsObject.doDeliveryChange


/*
 엑셀 업로드 팝업 호출
*/
_deliveryJsObject.doDeliveryOrderExcelPopup = function(callPopupType){
	  
	//dataListSheet.FindText()
/*		
	var param = {"Type":"json"};

	var json = dataListSheet.ExportData(param).data;
	var delRowSeqStr = "";
	$.each(json, function(i,v){
		//if(v.checked == 1){
			 alert("v.dlvyStatCodeBefore:"+v.dlvyStatCodeBefore );
		delRowSeqStr += dataListSheet.FindText('dlvyStatCodeBefore', v.dlvyStatCodeBefore, 0, 0, 0) + '|';
		//}
	});
	alert("delRowSeqStr:"+delRowSeqStr );
	return;
*/ 

	
		var url = "/delivery/vendor/delivery-order-vendor/delivery-excel-upload-popup";
		var params = {};  //popup
		params.callPopupType = callPopupType;  // 팝업 호출 구분 C:등록 R:읽기
	
		abc.open.popup({
			winname :	"DeliveryExcelUploadPopup",
			url 	:	url,
			method	: 	"get",
			width 	:	850,
			height	:	700,
			params	:	params

		});

	} //end function _deliveryJsObject.doDeliveryOrderReservationPopup

	 //배송상태 변경에 대한 저장
	 _deliveryJsObject.doDeliveryStatCodeSave = function(){
		 
		 //선택여부 체크 * 형식이 있기에 두형식으로 사용 해봄
		 var sRow = dataListSheet.CheckedRows("checkedRows");
		 //선택 데이타 Rows
		var checkRows = dataListSheet.FindCheckedRow("checkedRows");
		 
		 if(sRow <1 ){
				alert(alertSelectEmpty);
				return;
		 } //if(sRow
      
	   var alertConfirmSvae = "";		//최종 메세지
		var dlvyStatCode10001Boolean = false;  //상품준비중
		var dlvyStatCode10003Boolean = false; //배송중
		var dlvyStatCode10005Boolean = false; //배송완료

		 //제약 사항 체크
         var arrRow = checkRows.split("|"); 

		 for (idx=0; idx<arrRow.length; idx++){  
			
			var dlvyStatCode =  dataListSheet.GetCellValue( arrRow[idx], "dlvyStatCode");		 //배송상태
			var dlvyStatCodeBefore =  dataListSheet.GetCellValue( arrRow[idx], "dlvyStatCodeBefore");		 //이전 배송상태
			var dlvyIdText =  dataListSheet.GetCellValue( arrRow[idx], "dlvyIdText");		 //배송ID
			var logisVndrCode  = dataListSheet.GetCellValue(  arrRow[idx] , "logisVndrCode").trim(); // 택배사
			var waybilNoText  = dataListSheet.GetCellValue(  arrRow[idx] , "waybilNoText").trim(); // 송장번호
			var dlvyProcDtm  = dataListSheet.GetCellValue(  arrRow[idx] , "dlvyProcDtm").trim(); // 발송일자
			
			var alertValidSave = "상태에 맞지 않는 변경입니다."

			//결제완료인 -> 상품준비중
			if( dlvyStatCodeBefore =="10000"  && dlvyStatCode != "10001" ){
				alert( alertValidSave +"\n * 배송ID  "+ dlvyIdText );
				return;
			}else if( dlvyStatCodeBefore =="10000"  && dlvyStatCode == "10001" ){
					dlvyStatCode10001Boolean = true;
			}
			 
			 //상품준비중인 -> 배송중
			if( dlvyStatCodeBefore =="10001"  && dlvyStatCode != "10003" ){
				alert( alertValidSave +"\n * 배송ID  "+ dlvyIdText );
				return;
			}else if( dlvyStatCodeBefore =="10001"  && dlvyStatCode == "10003" ){
				dlvyStatCode10003Boolean = true;
			}  

			//배송중 처리 체크
			if(dlvyStatCode == "10003" ){
			
				//송장번호 택배사 체크
				if ( abc.text.allNull( waybilNoText) ||  abc.text.allNull( logisVndrCode)  ) {
						var alertNullValue  = "필수 입력값을 선택/입력해주세요.(운송장번호/택배사)" ;
						alert( alertNullValue +"\n 배송ID : [ "+(dlvyIdText)+" ]");
						return;
				}

				//송장번호 숫자 체크
				if (isNaN( waybilNoText )) {
						var alertNullValueOnlyNum  = "송장번호는 숫자만 입력 가능 합니다."
						alert( alertNullValueOnlyNum+"\n 배송ID : [ "+(dlvyIdText)+" ]");
						return;
				}

				//송장번호 Length체크
				if (  waybilNoText.length < 10 || waybilNoText.length > 30  ) {
						var alertNullValueLength  = "송장번호는 [숫자만  최소 10자리 이상  30자리 이하]만 가능 합니다." ;
						alert( alertNullValueLength +"\n 배송ID : [ "+(dlvyIdText)+" ]");
						return;
				}
				
				//송장번호 택배사 체크
				if ( abc.text.allNull( dlvyProcDtm)   ) {
						var alertNullValue  = "필수 입력값을 선택/입력해주세요.(발송일자)" ;
						alert( alertNullValue +"\n 배송ID : [ "+(dlvyIdText)+" ]");
						return;
				}
				
			}

			 //배송중 -> 배송완료
			if( dlvyStatCodeBefore =="10003"  && dlvyStatCode != "10005" ){
				alert( alertValidSave +"\n * 배송ID  "+ dlvyIdText );
				return;
			}else if( dlvyStatCodeBefore =="10003"  && dlvyStatCode == "10005" ){
				// 이전 배송상태가 "배송중"이고 바꾸는 상태가 "배송완료"라면 
				dlvyStatCode10005Boolean = true;
			}

		 }//end for

		//상품준비중 메세지 처리
		if(dlvyStatCode10001Boolean ){
			 alertConfirmSvae = arrRow.length+alertConfirmSvae01;
		}

	
		//배송중 메세지 처리
		if(dlvyStatCode10003Boolean ){
			 alertConfirmSvae = arrRow.length+alertConfirmSvae02;
		}

		//배송완료 메세지 처리
		if(dlvyStatCode10005Boolean ){
			 alertConfirmSvae = arrRow.length+alertConfirmSvae03;
		}

		// save
		if(confirm( alertConfirmSvae )){
				 var param = {
								url : "/delivery/vendor/delivery-order-vendor/save",
								subparam : FormQueryStringEnc(document.gForm),
								sheet : "dataListSheet"
							};
				DataSave(param);
		} //end confirm

	 } //end _deliveryJsObject.doDeliveryStatCodeSave

   /**
	 *  배송상태 일괄 재배송 변경
	 */
	_deliveryJsObject.doDeliveryStatCodeUpdate = function(){
		
		//선택여부 체크 * 형식이 있기에 두형식으로 사용 해봄
		 var sRow = dataListSheet.CheckedRows("checkedRows");
		 //선택 데이타 Rows
		var checkRows = dataListSheet.FindCheckedRow("checkedRows");

		//변경 물류처
		var newStockGbnCode = $("#newStockGbnCode").val();
		 
		 //변경상태값 결제완료 - 10000 , 상품준비중 - 10001 , 상품출고  - 10002, 배송중   - 10003, 배송완료   - 10005
		var newDlvyStatCode = $("#newDlvyStatCode").val();
		
		if(newDlvyStatCode == ""){
				alert(alertChangeDlvyStat);
				return;
		}

		 if(sRow <1 ){
				alert(alertSelectEmpty);
				return;
		 } //if(sRow

		 //제약 사항 체크
         var arrRow = checkRows.split("|"); 
		var ColCheckedRowsNumber = dataListSheet.SaveNameCol("checkedRows");  //체크 박스 Col번호

		 for (idx=0; idx<arrRow.length; idx++){  
			 
		 		var sellCnclReqYn =  dataListSheet.GetCellValue( arrRow[idx], "sellCnclReqYn"); //판매취소 요청
		 		var dlvyIdText =  dataListSheet.GetCellValue( arrRow[idx], "dlvyIdText");		 //배송ID
		 		//판매취소 요청
				if(sellCnclReqYn == "Y" ){
					alert(alertDoDeliveryOrderCancelPopup+"\n(배송ID:"+dlvyIdText+")");
					return;
				}				
				dataListSheet.SetCellValue( arrRow[idx], "dlvyStatCode", newDlvyStatCode);   
				dataListSheet.SetCellValue( arrRow[idx], ColCheckedRowsNumber, 1);  //체크박스 체크처리

		 }//end for
 
     } //end _deliveryJsObject.doDeliveryStatCodeUpdate 


/**
	 * Data Action관리
	 */
	_deliveryJsObject.doActionJs = function(inAction){
		switch(inAction) {
			// 조회 
			case "search" :
				var pageCount = $('#pageCount').val();
				var param = { url : "/delivery/vendor/delivery-order-vendor/read-list"
							 , onePageRow : pageCount
							 , subparam : FormQueryStringEnc(document.gForm)
							 , sheet : "dataListSheet" };
				
				DataSearchPaging(param);
				break;
			// 택배사 코드 다운로드 
			case "logisExcel" :
				gForm.action ="/delivery/vendor/delivery-order-vendor/logisExcel";
			   gForm.submit();	 
				break;  
			case "logisPopup" :

					 var url = "/delivery/vendor/delivery-order-vendor/logisPopup";

				var params = {}			
				abc.open.popup({
					winname :	"LogisPopup",
					url 	:	url,
					method	: 	"post",
					width 	:	450,
					height	:	650,
					params	:	params

				});

				break;  
			// 선택엑셀 다운 
			case "excelSelect" :
					var checkRows = dataListSheet.FindCheckedRow("checkedRows");
				    var sRow			= dataListSheet.CheckedRows("checkedRows");
					
					if( sRow <1  ) {
						   alert(alertExcelSelect);
						   return;
					}

					var t = new Date();
					var setFilename = t.getFullYear() + "-" +_deliveryJsObject.AddZero(t.getMonth()+1) +"-" + _deliveryJsObject.AddZero(t.getDate());
					var downcol = _deliveryJsObject.makeHiddenSkipCol(dataListSheet);

					var param  = {DownCols:downcol,FileName: setFilename+ ".xls", DownRows: "0|"+checkRows};
					dataListSheet.Down2Excel(param);
				break;  
			// 전체 엑셀 다운 
			case "excelAll" :
				if(dataListSheet.GetDataFirstRow() < 1 ) {
				   alert(alertExcelAll);
				   return;
				}
				gForm.action ="/delivery/delivery-order-vendor/excelAll";
			    gForm.submit();	 
				break;  
 		}
	}

		//SkipCol 만들기. (감춰진 컬럼이나, Seq,Status,DelCheck 타입의 컬럼은 제외한다.)
			_deliveryJsObject.makeHiddenSkipCol = function(sobj){
				var lc = sobj.LastCol();
				var colsArr = new Array();
				for(var i=0;i<=lc;i++){
					var coltype = sobj.GetCellProperty(0,i,"Type");
					var colName = sobj.GetCellProperty(0,i, "SaveName");

					//Hidden이 아니면 넣는다. - 수취인에 대한 엑셀다운은 제외처리
					if(0==( sobj.GetColHidden(i) 
									&& (		colName = "rcvrName"   //수취인명
												&& colName != "rcvrTelNoText"  //수취인전화번호
												&& colName != "rcvrHdphnNoText"  //수취인핸드폰번호
												&& colName != "rcvrPostCodeText" //수취인우편번호
												&& colName != "rcvrPostAddrText" //수취인우편주소
												&& colName != "rcvrDtlAddrText" //수취인상세주소
										  )
						       )
						        &&coltype!="Seq"&&coltype!="Status"&&coltype!="DelCheck"  &&coltype!="CheckBox") {
						colsArr.push(i);
					}
				}
				return colsArr.join("|");
			}

	/**
	 * zero  체크
	 */
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
 	 		
		/**
		 * 주문자 정보
		 */
	 	 var searchOrderValue = $("#searchOrderValue").val();

		 if (!abc.text.allNull( searchOrderValue)) {
				 switch ($("#searchOrderKey").val()) {
					case "buyerHdphnNoText":
						 if (isNaN( searchOrderValue )) {
								alert( alertHdphnNoNo );
								$("#searchOrderValue").val("");
								return false;
							}
						break;
				}
		} //end  if (!abc.text.allNull( searchOrderValue)) {
		 
		/*if( !abc.text.allNull($("#orderNo").val()) ){
			// 주문번호로 검색한다면 검색 기간 X
			$(".hasDatepicker").val("");
			// 주문번호로 검색한다면 주문배송상태 전체
			$("#dlvyStatCode").val("");
		}*/
		 
		// 검색 기간 validate
		if( abc.text.allNull($("#orderNo").val()) && (abc.text.allNull($("#fromDate").val()) || abc.text.allNull($("#toDate").val())) ){
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
		
		/*
		if(abc.text.allNull($('#brandName').val()) && abc.text.allNull($('#brandNo').val())) {
			alert('브랜드 선택은 필수입니다.');
			return false;
		}
		*/
		
		return true;

	} //end _deliveryJsObject.searchValidate

	/**
		  최근 3개월 이내 주문 기준 새로고침 - 상태별 건수
	*/
	_deliveryJsObject.getDeliveryOrderVendorPrdtStatCnt = function(){
			var vndrNo =  $("#vndrNo").val();
			var param = {  vndrNo  :vndrNo } //업체코드

		 	$.ajax({
				type 	:"get",
				data 	: param,
				url		:"/delivery/vendor/delivery-order-vendor-main/stat/read-list"
			})
			.done(function(data){
				var orderPrdtStatCodeComplete = data.orderPrdtStatCodeComplete;
				var orderPrdtStatCodeProductPreparation = data.orderPrdtStatCodeProductPreparation;
				var orderPrdtStatCodeProductDelivery = data.orderPrdtStatCodeProductDelivery;
				var orderPrdtStatCodeDeliveryIng = data.orderPrdtStatCodeDeliveryIng;
				var orderPrdtStatCodeDeliveryFinish = data.orderPrdtStatCodeDeliveryFinish;
				var orderPrdtStatCodeCancelComplete = data.orderPrdtStatCodeCancelComplete;
			    
				$("#orderPrdtStatCodeComplete").html( orderPrdtStatCodeComplete ); //결제완료
				$("#orderPrdtStatCodeProductPreparation").html( orderPrdtStatCodeProductPreparation ); //상품준비중
				$("#orderPrdtStatCodeProductDelivery").html( orderPrdtStatCodeProductDelivery ); //상품출고
				$("#orderPrdtStatCodeDeliveryIng").html( orderPrdtStatCodeDeliveryIng ); //배송중
				$("#orderPrdtStatCodeDeliveryFinish").html( orderPrdtStatCodeDeliveryFinish ); //배송완료
				$("#orderPrdtStatCodeCancelComplete").html( orderPrdtStatCodeCancelComplete ); //결제취소
			})
			.fail(function(e){
				$("#orderPrdtStatCodeComplete").html( 0 ); //결제완료
				$("#orderPrdtStatCodeProductPreparation").html( 0 ); //상품준비중
				$("#orderPrdtStatCodeProductDelivery").html( 0 ); //상품출고
				$("#orderPrdtStatCodeDeliveryIng").html( 0 ); //배송중
				$("#orderPrdtStatCodeDeliveryFinish").html( 0 ); //배송완료
				$("#orderPrdtStatCodeCancelComplete").html( 0 ); //결제취소
			});
	} //end if  _deliveryJsObject.getDeliveryOrderVendorPrdtStatCnt = function(){


	_deliveryJsObject.validateOnlyNumber = function(el){
		abc.text.validateOnlyNumber(el);
	}
	
	//업체 주문 배송관리 건수 클릭시 조회
	_deliveryJsObject.cntNumberClick = function(targetId){
		var dlvyStatCode = "";
		switch(targetId){
			case "orderPrdtStatCodeComplete" :
				dlvyStatCode = '10000';
				break;
			case "orderPrdtStatCodeProductPreparation" :
				dlvyStatCode = '10001';
				break;
			case "orderPrdtStatCodeDeliveryIng" :
				dlvyStatCode = '10003';
				break;
			case "orderPrdtStatCodeDeliveryFinish" :
				dlvyStatCode = '10005';
				break;
				
		}
		if(!abc.text.allNull(dlvyStatCode)){
			$("#dlvyStatCode").val(dlvyStatCode);
			_deliveryJsObject.threeMonth();
			
			$("#gFormSearch").trigger('click');
		}
	}
	
	/**
	 * 날짜 기간 설정 : 3달
	 */
	_deliveryJsObject.threeMonth = function(){
		var _Today = new Date();
		
		$("#toDate").val($.datepicker.formatDate($("#toDate").datepicker("option", "dateFormat"), _Today));
		_Today.setMonth(_Today.getMonth() - 3);
		$("#fromDate").val($.datepicker.formatDate($("#fromDate").datepicker("option", "dateFormat"), _Today));
	}
	 
})();	 //end  (function() {

 
  


/**
	  그리드 변경 처리시 사용
	*/
function dataListSheet_OnChange(Row, Col, Value){
			
		var waybilNoText = dataListSheet.GetCellValue( Row , "waybilNoText") //해당 row의 배송비 금액
		var logisVndrCode = dataListSheet.GetCellValue( Row , "logisVndrCode") //해당 row의 상품금액
		var dlvyStatCodeBefore =  dataListSheet.GetCellValue( Row, "dlvyStatCodeBefore");		 //배송상태
		var ColCheckedRowsCol  = dataListSheet.SaveNameCol("checkedRows");  //체크 박스 Col번호
		var dlvyProcDtmCol = dataListSheet.SaveNameCol("dlvyProcDtm");  //날짜의 Col번호
		var dlvyStatCodeCol = dataListSheet.SaveNameCol("dlvyStatCode");  //배송상태 Col번호
		
		
		//체크박스는 영향을 안받게 하기위해
		if( Col != 1 ){
					
			//상태가 상품준비중 상태인경우에만 진행
			if(dlvyStatCodeBefore == "10001" ){
 				if(waybilNoText == ""  ||  logisVndrCode =="" ){
						dataListSheet.SetCellValue(Row, ColCheckedRowsCol, 0);  //체크박스 체크 해제
						dataListSheet.SetCellValue(Row, dlvyProcDtmCol,   "" );  //날짜 자동 셋팅 초기화
						dataListSheet.SetCellValue(Row, dlvyStatCodeCol,   "10001" );  //상품준비중
						//미수정 처리로 변경 
						dataListSheet.SetCellValue(Row, 0, "");

				}
 				
 				if(waybilNoText != ""  &&  logisVndrCode !="" ){
 				
						dataListSheet.SetCellValue(Row, ColCheckedRowsCol, 1);  //체크박스 체크 처리
						var t = new Date();
						var to_day = t.getFullYear() + "-" +abc.biz.delivery.order.vendor.main.AddZero(t.getMonth()+1) +"-" + abc.biz.delivery.order.vendor.main.AddZero(t.getDate());
						
						dataListSheet.SetCellValue(Row, dlvyProcDtmCol,   to_day );  //날짜 자동 셋팅
						dataListSheet.SetCellValue(Row, dlvyStatCodeCol,   "10003" );  //배송중
						//수정으로 변경처리
						dataListSheet.SetCellValue(Row, 0, "U");
				}
			}
		}//end if Col

		//에디터 가능 불가처리  - 리스트 출력후 금액이 있는 경우는 수정 못하게 변경 해야 할 듯 하다.
		//mySheet.SetColEditable(5,0);   col:12-13 상품금액, 배송비금액
		
} //end _deliveryJsObject_OnChange


//그리드 Click 이벤트 
function dataListSheet_OnClick(Row, Col, Value) {
	
	var vendorYn =  $("#vndrGbnType").val();

	if ( Row != 0) {
		
		//파라미터값셋팅
		var params = {}
			params.waybilNoText = dataListSheet.GetCellValue(Row, "waybilNoText"); //송장번호
			params.prdtNo = dataListSheet.GetCellValue(Row, "prdtNo");  //상품번호
			params.orderNo = dataListSheet.GetCellValue(Row, "orderNo"); //주문번호
			params.orderPrdtSeq = dataListSheet.GetCellValue(Row, "orderPrdtSeq"); //상품 순번
			params.cancelPopupType = "R";  // 팝업 호출 구분 C:등록 R:읽기

			
		//주문상세	
		if ( dataListSheet.ColSaveName(Col) == "orderNo") {
			if( dataListSheet.GetCellValue(Row, "salesCnclGbnType") == "S" ) {
				if( vendorYn =="Y" ){
					abc.orderVendorDetailPopup(dataListSheet,"orderNo",Row,Col,"");
				}else{
					abc.orderDetailPopup(dataListSheet,"orderNo",Row,Col,"");
				}
			}
		}
		
		//주문배송상태
		if ( dataListSheet.ColSaveName(Col) == "dlvyStatCode") {
			var orderNo =  dataListSheet.GetCellValue(Row, "orderNo");
			var orderPrdtSeq =  dataListSheet.GetCellValue(Row, "orderPrdtSeq");
		 
			abc.orderProductHistoryPopup(orderNo,orderPrdtSeq,"");
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
				
				if(siteNo == abc.biz.delivery.order.vendor.main.SITE_NO_ART){
					window.open(abc.biz.delivery.order.vendor.main.ABC_FO_URL + "/product?prdtNo=" + prdtNo)
				} else {
					window.open(abc.biz.delivery.order.vendor.main.OTS_FO_URL + "/product?prdtNo=" + prdtNo)
				}	
		}
		
		//판매취소요청
		if ( dataListSheet.ColSaveName(Col) == "sellCnclReqYn" ) {
			if( dataListSheet.GetCellValue(Row, "sellCnclReqYn") == "Y") {
			      	var url = "/delivery/vendor/delivery-order-vendor/cancel-popup/read";
				
					abc.open.popup({
						winname :	"DeliveryCancelPopupView",
						url 	:	url,
						method	: 	"post",
						width 	:	1240,
						height	:	450,
						params	:	params

					});
			}else{
				alert("판매취소 요청이 없습니다.");
				return;
			}
		}

		//주문자
		if ( dataListSheet.ColSaveName(Col) == "buyerName" && vendorYn == "N" ) {
			var memberNo =  dataListSheet.GetCellValue(Row, "memberNo");
			if( memberNo != abc.biz.delivery.order.vendor.main.NON_MEMBER_NO){
				abc.memberDetailPopup(memberNo);
			}
		}
		
	} //end if ( Row != 0) {
	
	
} //end function 그리드 Click 이벤트 
