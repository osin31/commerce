/***
 * 주문관리 > 배송관리 > 업체 클레임 관리(업체)
 * 
 */
(function() {

		// define Alert Msg
		var alertOrderNo				= "주문번호는 숫자만 입력해주세요.";
		var alertHdphnNoNo				= "핸드폰 번호는  숫자만 입력해주세요.";
		var alertSelectEmpty				= "처리대상이 선택되지 않았습니다.\n 처리대상을 확인해주세요.";
		var alertExcelEmpty				= "엑셀 다운받을 대상을 선택해주세요.";
		var alertConfirmSvae = "선택한 클레임을 변경처리 하시겠습니까?";
		var alertCancelFail = "취소 불가능한 상태주문입니다.";
		var alertSearchDate			= "검색시작일은 종료일보다 이전 일 수 없습니다.\n검색기간을 다시 설정해주세요.";
		var alertSearchDateOver = "검색기간은 최대 1년 입니다. \n 검색기간을 다시 설정해주세요.";
		var alertcheckBoxCFail ="검색조건의 취소 상태를 최소한  한개 이상 선택해야 합니다."
		var alertcheckBoxEFail ="검색조건의 교환 상태를 최소한  한개 이상 선택해야 합니다."
		var alertcheckBoxRFail ="검색조건의 반품 상태를 최소한  한개 이상 선택해야 합니다."

		//스크립트 Object선언
		var _deliveryJsObject = abc.object.createNestedObject(window,"abc.biz.delivery.order.vendor.claim.main");

		_deliveryJsObject.initDataListSheet = function() {
		createIBSheet2(document.getElementById("dataListGrid"), "dataListSheet", "100%", "429px");
		
		var initDataListSheet = {};
		var pageCount = $('#pageCount').val();
		
		initDataListSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MaxSort:1};
		initDataListSheet.HeaderMode = {Sort:0, ColMove:0, ColResize:1};
		
		//권한에 따른 헤더 옵션
		var boldCondition = 1;
		var underCondition = 1;
		var cursorCondition = 'Pointer';
		
		if(!abc.text.allNull($("#vndrNo").val())){
			boldCondition = 0; 
			underCondition = 0; 
			cursorCondition = ""; 
		}
		
		initDataListSheet.Cols = [
				  {Header:"" ,					Type:"Status",			SaveName:"status",				Width: 0,		 Align:"", 			Edit:0, Hidden:1}
				, {Header:"",					Type:"CheckBox",		SaveName:"checkedRows",			Width: 40,		 Align:"Center"}
				, {Header:"업체명",				Type:"Text",			SaveName:"vndrName",			Width: 100,		 Align:"Center", 	Edit:0}
				, {Header:"클레임번호",				Type:"Text",			SaveName:"clmNo",				Width: 100,	 	 Align:"Center", 	Edit:0, FontBold:boldCondition, FontUnderline:underCondition, Cursor:cursorCondition}
				, {Header:"클레임접수일", 			Type:"Text",			SaveName:"rgstDtm",				Width: 120,		 Align:"Center", 	Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
				, {Header:"클레임구분",				Type:"Combo",			SaveName:"clmGbnCode",			Width: 70,		 Align:"Center",	Edit:0	}
				, {Header:"클레임사유",				Type:"Combo",			SaveName:"clmRsnCode",			Width: 120,		 Align:"Center", 	Edit:0}
				, {Header:"주문번호",				Type:"Text",			SaveName:"orderNo",				Width: 120,		 Align:"Center",	Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer"} 
				, {Header:"주문상품순번", 			Type:"Text",			SaveName:"orderPrdtSeq",		Width: 60,		 Align:"Center", 	Edit:0,Hidden:1 }
				, {Header:"상품배송상태", 			Type:"Text",			SaveName:"orderPrdtStatCode",	Width: 100,		 Align:"Center", 	Edit:0,Hidden:1 }
				, {Header:"업체상품코드",			Type:"Text",			SaveName:"vndrPrdtNoText",		Width: 120,		 Align:"Center", 	Edit:0}
				, {Header:"상품코드",				Type:"Text",			SaveName:"prdtNo",				Width: 120,		 Align:"Center", 	Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer" }
				, {Header:"상품명",				Type:"Text",			SaveName:"prdtName",			Width: 200,		 Align:"Left", 		Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer" }
				, {Header:"옵션",					Type:"Text",			SaveName:"optnName",			Width: 80,		 Align:"Center", 	Edit:0}
				, {Header:"판매가",				Type:"Int",				SaveName:"sellAmt",				Width: 80,		 Align:"Right", 	Edit:0, Format:"#,##0 원"}			
				, {Header:"결제금액",				Type:"Int",				SaveName:"orderAmt",			Width: 80,		 Align:"Right", 	Edit:0, Format:"#,##0 원"}			
				, {Header:"클레임 베송비\n결제수단", 	Type:"Combo",			SaveName:"addDlvyAmtPymntType",	Width: 120,		 Align:"Center",	Edit:0}			
				, {Header:"클레임배송비",			Type:"Int",				SaveName:"addDlvyAmt",			Align:"Right", 	 Width: 100,		Edit:0, Format:"#,##0 원"}
				, {Header:"클레임상태",				Type:"Combo",			SaveName:"clmPrdtStatCode",		Align:"Center",  Width: 100,	 	Edit:0}
				, {Header:"클레임상태befor",		Type:"Text",			SaveName:"clmPrdtStatCodeBefor",Align:"Center",  Width: 120,	 	Edit:0,Hidden:1 }
				, {Header:"택배사",				Type:"Text",			SaveName:"changeLogisVndrCodeName",	Width: 100,		 Align:"Center", 	Edit:0}
				, {Header:"운송장번호",				Type:"Text",			SaveName:"changeWaybilNoText",	Width: 150,		 Align:"Center", 	Edit:0}
				, {Header:"교환발송일자",			Type:"Date",			SaveName:"changeDlvyProcDtm",	Width: 150,		 Align:"Center", 	Edit:0, Format:"Ymd"}				
				, {Header:"클레임상품순번",			Type:"Text",			SaveName:"clmPrdtSeq",			Width: 100,	 	 Align:"Center", 	Edit:0  , Hidden:1}
				, {Header:"클레임구분코드",			Type:"Text",			SaveName:"orgClmGbnCode",		Width: 150,		 Align:"Center", 	Edit:0, Hidden:1}
				, {Header:"사이트번호",				Type:"Text",			SaveName:"siteNo",				Width: 150,		 Align:"Center", 	Edit:0, Hidden:1}
				, {Header:"발송매장",			Type:"Text",	SaveName:"storeName",			Width: 100,		 Align:"Center", 	Edit:0, Hidden:1}
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

		dataListSheet.InitDataCombo(0, "addDlvyAmtPymntType", "환불금액차감|추가결제|무료쿠폰|배송비무료|", "S|P|C|F|"); //추가배송비결제방법
		dataListSheet.InitDataCombo(0, "siteNo",  this.siteCombo.text,   this.siteCombo.code );		//사이트 콤보
		dataListSheet.InitDataCombo(0, "clmGbnCode",  this.codeCombo.CLM_GBN_CODE.text,   this.codeCombo.CLM_GBN_CODE.code );		//클레임구분
		dataListSheet.InitDataCombo(0, "clmRsnCode",  this.codeCombo.CLM_RSN_CODE.text,   this.codeCombo.CLM_RSN_CODE.code );		//클레임사유
		dataListSheet.InitDataCombo(0, "clmPrdtStatCode",  this.codeCombo.CLM_PRDT_STAT_CODE.text,   this.codeCombo.CLM_PRDT_STAT_CODE.code );		//클레임상태
		dataListSheet.InitDataCombo(0, "logisVndrCode",  this.codeCombo.LOGIS_VNDR_CODE.text,   this.codeCombo.LOGIS_VNDR_CODE.code );		//택배사코드
		
	}	 
	
	_deliveryJsObject.SITE_NO_ART = "";
	_deliveryJsObject.SITE_NO_OTS = "";
	
	_deliveryJsObject.ABC_FO_URL = "";
	_deliveryJsObject.OTS_FO_URL = "";
		
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
			_deliveryJsObject.getDeliveryOrderVendorClaim();
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

		// 새로고침
		$("#icoRefresh").click(function(){
			_deliveryJsObject.getDeliveryOrderVendorClaim();
		});
		
		// 주문번호 입력시 숫자만
		$("#orderNo").keyup(function(){
			_deliveryJsObject.validateOnlyNumber(this);
		});

		//클레임진행상태
		$("#clmGbnCode").change(function(){
			  var  clmGbnCode = $('#clmGbnCode').val();

			//default  변경시 전체 선택
			$("input[name^='clmPrdtStatEArr']").prop("checked",  true);
			$("input[name^='clmPrdtStatCArr']").prop("checked", true);
			$("input[name^='clmPrdtStatRArr']").prop("checked", true);

			   if (clmGbnCode =="C") {
			   //취소 
				    $("#claimGbnCodeC").css("display",""); //취소
				    $("#claimGbnCodeE").css("display","none");   //교환
				    $("#claimGbnCodeR").css("display","none"); //반품
			   }else if (clmGbnCode =="E") {
				//교환
				    $("#claimGbnCodeC").css("display","none"); //취소
				    $("#claimGbnCodeE").css("display","");   //교환
				    $("#claimGbnCodeR").css("display","none"); //반품
			   }else  if (clmGbnCode =="R") {
			   //반품
				      $("#claimGbnCodeC").css("display","none"); //취소
				    $("#claimGbnCodeE").css("display","none");   //교환
				    $("#claimGbnCodeR").css("display",""); //반품
			   }else{
				        $("#claimGbnCodeC").css("display","none"); //취소
				    $("#claimGbnCodeE").css("display","none");   //교환
				    $("#claimGbnCodeR").css("display","none"); //반품
			   }
		}); 

			//교환 체크박스 체크시
		$("input[name='clmPrdtStatEArr']").click(function() {
			if($(this).val() == "ALL") {
				$("input[name='clmPrdtStatEArr']").prop("checked", this.checked);
			} else {
				$("input[name='clmPrdtStatEArr']:eq(0)").prop("checked", false);
			}

			//전체 선택인경우
			var unChecked = $("input[clickYn=clmPrdtStatEArr]:not(:checked)");
			if (unChecked.length == 0) {
					$("input[name='clmPrdtStatEArr']:eq(0)").prop("checked", true);
			}
		});

			//반품  체크박스 체크시
		$("input[name='clmPrdtStatRArr']").click(function() {
			if($(this).val() == "ALL") {
				$("input[name='clmPrdtStatRArr']").prop("checked", this.checked);
			} else {
				$("input[name='clmPrdtStatRArr']:eq(0)").prop("checked", false);
			}

			//전체 선택인경우
			var unChecked = $("input[clickYn=clmPrdtStatRArr]:not(:checked)");
			if (unChecked.length == 0) {
					$("input[name='clmPrdtStatRArr']:eq(0)").prop("checked", true);
			}
		});
 
			//취소  체크박스 체크시
		$("input[name='clmPrdtStatCArr']").click(function() {
			if($(this).val() == "ALL") {
				$("input[name='clmPrdtStatCArr']").prop("checked", this.checked);
			} else {
				$("input[name='clmPrdtStatCArr']:eq(0)").prop("checked", false);
			}

			//전체 선택인경우
			var unChecked = $("input[clickYn=clmPrdtStatCArr]:not(:checked)");
			if (unChecked.length == 0) {
					$("input[name='clmPrdtStatCArr']:eq(0)").prop("checked", true);
			}
		});

		//업체 찾기 팝업
		$("#searchVendor").click(function(){
			// false 단건 , false n건
			abc.vndrSearchPopup(false, "setSelectedVendors");
		});

		 
		//전체 엑셀 받기
		 $("#logisVndrCodeExcelDown").click(function(){ 
			 	_deliveryJsObject.doActionJs("logisExcel");
		});

		//일괄적용
		 $("#clmPrdtStatCodeUpdate").click(function(){ 
			 	_deliveryJsObject.doClmPrdtStatCodeUpdate();
		});

		//저장
		 $("#claimGbnCodeSave").click(function(){ 
			 	_deliveryJsObject.doClaimGbnCodeSave();
		});

		//클레임불가 요청
		 $("#orderClaimCancelPopup").click(function(){ 
			 	_deliveryJsObject.doDeliveryOrderClaimCancelPopup("C");
		});

		//엑셀업로드
		 $("#orderExcelUpload").click(function(){ 
			 	_deliveryJsObject.doDeliveryClaimExcelPopup("C");
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
    클레임불가 요청 popup
	cancelPopupType : 팝업 호출 구분 C:등록 R:읽기
   */
	_deliveryJsObject.doDeliveryOrderClaimCancelPopup = function(cancelPopupType){
		
		//선택여부 체크 * 형식이 있기에 두형식으로 사용 해봄
		 var sRow = dataListSheet.CheckedRows("checkedRows");
		 //선택 데이타 Rows
		var checkRows = dataListSheet.FindCheckedRow("checkedRows");

		 if(sRow <1 ){
			alert(alertSelectEmpty);
			return;
		 } //end if

		var alertSelects ="클레임 불가 처리는 1건씩 가능 합니다.";

		 if(sRow >1 ){
			 alert(alertSelects);
			return;
		 }

		//주문번호 중복여부 체크
         var arrRow = checkRows.split("|"); 
		 var params = {};  //popup창 params
 
		 for (idx=0; idx<arrRow.length; idx++){  
			var clmNo =  dataListSheet.GetCellValue( arrRow[idx], "clmNo");  //주문번호
			var clmPrdtSeq =  dataListSheet.GetCellValue( arrRow[idx], "clmPrdtSeq");  //클레임상품순번
			var orderNo =  dataListSheet.GetCellValue( arrRow[idx], "orderNo");  //주문번호
			var orderPrdtSeq =  dataListSheet.GetCellValue( arrRow[idx], "orderPrdtSeq"); //주문 상품 순번
		 }//end for
		  
		var url = "/claim/claim/regist-claim-impossibility-form-pop";

		params.clmNo = clmNo;  // params 주문번호
		params.clmPrdtSeq =  clmPrdtSeq;   // 주문상품상태
		params.orderNo = orderNo;   // 주문상품순번
		params.orderPrdtSeq = orderPrdtSeq;  // 팝업 호출 구분 C:등록 R:읽기

		abc.open.popup({
			winname :	"registClaimImpossibilityFormPop",
			url 	:	url,
			method	: 	"get",
			width 	:	1000,
			height	:	880,
			params	:	params

		});

	} //end function _deliveryJsObject.doDeliveryOrderClaimCancelPopup


/*
 엑셀 업로드 팝업 호출
  우선은 기능에서 제외 하지만 추후 만들어 질수 있기에 소스는 삭제 안함
*/
_deliveryJsObject.doDeliveryClaimExcelPopup = function(callPopupType){
	  
		var url = "/delivery/vendor/delivery-order-vendor/claim-excel-upload-popup";
		var params = {};  //popup
		params.callPopupType = callPopupType;  // 팝업 호출 구분 C:등록 R:읽기
	
		abc.open.popup({
			winname :	"DeliveryClaimExcelPopup",
			url 	:	url,
			method	: 	"get",
			width 	:	500,
			height	:	855,
			params	:	params

		});

	} //end function _deliveryJsObject.doDeliveryClaimExcelPopup

	 //클레임 상태 변경에 대한 저장
	 _deliveryJsObject.doClaimGbnCodeSave = function(){
		 //alert();
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
			
			var clmPrdtStatCode =  dataListSheet.GetCellValue( arrRow[idx], "clmPrdtStatCode");		 //클레임상태
			var clmPrdtStatCodeBefor =  dataListSheet.GetCellValue( arrRow[idx], "clmPrdtStatCodeBefor");		 //이전 클레임상태
			var orderNo =  dataListSheet.GetCellValue( arrRow[idx], "orderNo");		 //배송ID
			var alertValidSave = "상태에 맞지 않는 변경입니다."

			//교환접수 -> 수거지시
//			if( clmPrdtStatCode  == clmPrdtStatCode  ){
//				alert( alertValidSave +"\n * 주문번호  "+ orderNo );
//				return;
//			}  

			//교환접수 -> 수거지시
			if( clmPrdtStatCodeBefor =="10006"  && clmPrdtStatCode != "10009" ){
				alert( alertValidSave +"\n * 주문번호  "+ orderNo );
				return;
			}  
			 
			 //수거지시 -> 수령완료
			if( clmPrdtStatCodeBefor =="10009"  && clmPrdtStatCode != "10010" ){
				alert( alertValidSave +"\n * 배송ID  "+ orderNo );
				return;
			}  

			//수령완료 -> 심의완료
			if( clmPrdtStatCodeBefor =="10010"  && clmPrdtStatCode != "10011" ){
				alert( alertValidSave +"\n * 배송ID  "+ orderNo );
				return;
			}  

			 //심의완료 -> 교환배송지시
			if( clmPrdtStatCodeBefor =="10011"  && clmPrdtStatCode != "10014" ){
				alert( alertValidSave +"\n * 배송ID  "+ orderNo );
				return;
			}  

			//교환배송지시 -> 교환배송중
			if( clmPrdtStatCodeBefor =="10014"  && clmPrdtStatCode != "10015" ){
				alert( alertValidSave +"\n * 배송ID  "+ orderNo );
				return;
			}  

			//반품접수 -> 수거지시
			if( clmPrdtStatCodeBefor =="10018"  && clmPrdtStatCode != "10021" ){
				alert( alertValidSave +"\n * 배송ID  "+ orderNo );
				return;
			}  

			//수거지시 -> 심의완료
			if( clmPrdtStatCodeBefor =="10021"  && clmPrdtStatCode != "10023" ){
				alert( alertValidSave +"\n * 배송ID  "+ orderNo );
				return;
			}  

		 }//end for

		// save
		if(confirm( alertConfirmSvae )){
				 var param = {
								url : "/delivery/vendor/delivery-order-vendor/claim/save",
								subparam : FormQueryStringEnc(document.gForm),
								sheet : "dataListSheet"
							};
				DataSave(param);
		} //end confirm

	 } //end _deliveryJsObject.doClaimGbnCodeSave

   /**
	 *  배송상태 일괄 재배송 변경
	 */
	_deliveryJsObject.doClmPrdtStatCodeUpdate = function(){
		
		//선택여부 체크 * 형식이 있기에 두형식으로 사용 해봄
		 var sRow = dataListSheet.CheckedRows("checkedRows");
		 //선택 데이타 Rows
		var checkRows = dataListSheet.FindCheckedRow("checkedRows");
 	 
		 if(sRow <1 ){
				alert(alertSelectEmpty);
				return;
		 } //if(sRow
 
		var newClmPrdtStatCode = $("#newClmPrdtStatCode").val();

		 //제약 사항 체크
         var arrRow = checkRows.split("|"); 

		 for (idx=0; idx<arrRow.length; idx++){  
					dataListSheet.SetCellValue( arrRow[idx], "clmPrdtStatCode", newClmPrdtStatCode);   

		 }//end for
 
     } //end _deliveryJsObject.doClmPrdtStatCodeUpdate 


/**
	 * Data Action관리
	 */
	_deliveryJsObject.doActionJs = function(inAction){
		switch(inAction) {
			// 조회 
			case "search" :
				var pageCount = $('#pageCount').val();
				var param = { url : "/delivery/vendor/delivery-order-vendor-claim-main/claim/read-list"
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
				gForm.action ="/delivery/delivery-order-vendor/claim/excelAll";
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
		  
		/**
		 * 검색 기간 체크
		 */
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


		var clmGbnCode = $("#clmGbnCode").val() 
		if(clmGbnCode == ""){
			alert("클레임 진행 상태를 선택 해주세요");
			return false;
		}

	   if (clmGbnCode =="C") {
		   //취소 
				//검색 체크박스 체크
				var checkBoxCLength = $("input[name=clmPrdtStatCArr]:checked").length;
				if( checkBoxCLength < 1 ){
					alert( alertcheckBoxCFail );
					return false;
				}

		   }else if (clmGbnCode =="E") {
			//교환
				//검색 체크박스 체크
				var checkBoxELength = $("input[name=clmPrdtStatEArr]:checked").length;
				if( checkBoxELength < 1 ){
					alert( alertcheckBoxEFail );
					return false;
				}
		   }else  if (clmGbnCode =="R") {
		   //반품
				//검색 체크박스 체크
				var checkBoxRLength = $("input[name=clmPrdtStatRArr]:checked").length;
				if( checkBoxRLength < 1 ){
					alert( alertcheckBoxRFail );
					return false;
				}
		   }

		return true;

	} //end _deliveryJsObject.searchValidate

	/**
		  최근 3개월 이내 주문 기준 새로고침 - 상태별 건수
	*/
	_deliveryJsObject.getDeliveryOrderVendorClaim = function(){
			var vndrNo =  $("#vndrNo").val();
			var param = {  vndrNo  :vndrNo } //업체코드

		 	$.ajax({
				type 	:"get",
				data 	: param,
				url		:"/delivery/vendor/delivery-order-vendor-claim-main/claim/count"
			})
			.done(function(data){
				var orderPrdtStatCodeClaim = data.orderPrdtStatCodeClaim;
				var orderPrdtStatCodeDeliveryReturn = data.orderPrdtStatCodeDeliveryReturn;
				var orderPrdtStatCodeExchangeDelivery = data.orderPrdtStatCodeExchangeDelivery;
				var orderPrdtStatCodeClaimComplete = data.orderPrdtStatCodeClaimComplete;
				var orderPrdtStatCodeClaimCancel = data.orderPrdtStatCodeClaimCancel;
			    
				$("#orderPrdtStatCodeClaim").html( orderPrdtStatCodeClaim ); //클레임 접수 (미확인 클레임)
				$("#orderPrdtStatCodeDeliveryReturn").html( orderPrdtStatCodeDeliveryReturn ); //회수중
				$("#orderPrdtStatCodeExchangeDelivery").html( orderPrdtStatCodeExchangeDelivery ); //교환품 배송중
				$("#orderPrdtStatCodeClaimComplete").html( orderPrdtStatCodeClaimComplete ); //클레임 완료
				$("#orderPrdtStatCodeClaimCancel").html( orderPrdtStatCodeClaimCancel ); //클레임 불가 요청 
			})
			.fail(function(e){
				$("#orderPrdtStatCodeClaim").html( "0" ); //클레임 접수 (미확인 클레임)
				$("#orderPrdtStatCodeDeliveryReturn").html( "0" ); //회수중
				$("#orderPrdtStatCodeExchangeDelivery").html( "0" ); //교환품 배송중
				$("#orderPrdtStatCodeClaimComplete").html( "0" ); //클레임 완료
				$("#orderPrdtStatCodeClaimCancel").html( "0" ); //클레임 불가 요청
			});
	} //end if  _deliveryJsObject.getDeliveryOrderVendorClaim = function(){


	_deliveryJsObject.validateOnlyNumber = function(el){
		abc.text.validateOnlyNumber(el);
	}
	
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

 
 
/**
	  그리드 변경 처리시 사용
	*/
function 	dataListSheet_OnChange(Row, Col, Value){
			
		var waybilNoText = dataListSheet.GetCellValue( Row , "waybilNoText") //해당 row의 배송비 금액
		var logisVndrCode = dataListSheet.GetCellValue( Row , "logisVndrCode") //해당 row의 상품금액

		var ColCheckedRowsNumber = dataListSheet.SaveNameCol("checkedRows");  //체크 박스 Col번호

		//체크박스는 영향을 안받게 하기위해
		if( Col != 1 ){
			//
			if( abc.text.allNull( waybilNoText) &&  abc.text.allNull( logisVndrCode)  ){
					dataListSheet.SetCellValue(Row, ColCheckedRowsNumber, 0);  //체크박스 체크처리
			}else{
					dataListSheet.SetCellValue(Row, ColCheckedRowsNumber, 1); 
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

		// 클레임상세
		if ( dataListSheet.ColSaveName(Col) == "clmNo") {
			var clmNo = dataListSheet.GetCellValue(Row, "clmNo");
			var clmGbnCode = dataListSheet.GetCellValue(Row, "orgClmGbnCode");
			
			if( vendorYn != "Y" ){
				abc.biz.delivery.order.vendor.claim.main.openClaimDetailPop(clmNo, clmGbnCode);
			}
		}	
		
		//주문상세
		if ( dataListSheet.ColSaveName(Col) == "orderNo") {
			if( vendorYn =="Y" ){
				abc.orderVendorDetailPopup(dataListSheet,"orderNo",Row,Col,"");
			}else{
				abc.orderDetailPopup(dataListSheet,"orderNo",Row,Col,"");
			}
		}
		
		if ( dataListSheet.ColSaveName(Col) == "sellCnclReqYn") {
			      	var url = "/delivery/vendor/delivery-order-vendor/cancel-popup/read";
				
					abc.open.popup({
						winname :	"DeliveryCancelPopupView",
						url 	:	url,
						method	: 	"post",
						width 	:	1240,
						height	:	450,
						params	:	params

					});
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
				var siteNo = dataListSheet.GetRowData(Row).siteNo;
				
				if(siteNo == abc.biz.delivery.order.vendor.claim.main.SITE_NO_ART){
					window.open(abc.biz.delivery.order.vendor.claim.main.ABC_FO_URL + "/product?prdtNo=" + prdtNo)
				} else {
					window.open(abc.biz.delivery.order.vendor.claim.main.OTS_FO_URL + "/product?prdtNo=" + prdtNo)
				}	
		}

	} //end if ( Row != 0) {
} //end function 그리드 Click 이벤트 
