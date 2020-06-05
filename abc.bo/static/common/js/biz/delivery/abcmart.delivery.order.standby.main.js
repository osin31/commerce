/***
 * 주문관리 > 배송관리 > 매장픽업택배전환
 * 
 */
(function() {
		
		// define Alert Msg
		var alertSelectEmpty				= "처리대상이 선택되지 않았습니다.\n 처리대상을 확인해주세요.";
	    var alertSelectStockGbn = "재배송 처리하려는 발송처로 선택해주세요"; 
		var alertValidSave = "처리 대상 내 처리할 수 없는 배송건이 포함되어 있습니다. ";
		var alertConfirmSvae = "선택한 발송처로 일괄 재배송 처리 하시겠습니까?";
		var alertOrderNo				= "주문번호는 숫자만 입력해주세요.";
		var alertExcelSelect		= "엑셀 다운로드 하실 대상을 선택 해주세요.";
 

		//스크립트 Object선언
		var _deliveryJsObject = abc.object.createNestedObject(window,"abc.biz.delivery.order.standby.main");

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
			  {Header:"" ,		 		Type:"Status",	SaveName:"status",				Width: 0,		 Align:"", 			Edit:0, Hidden:1}
			, {Header:"", 				Type:"CheckBox",SaveName:"checkedRows",			Width: 40,		 Align:"Center",	Sort:0}
			, {Header:"사이트", 			Type:"Combo",	SaveName:"siteNo",				Width: 100,		 Align:"Center", 	Edit:0, ComboText: "", ComboCode: "", Sort:0}
			, {Header:"사이트번호", 		Type:"Text",	SaveName:"orgSiteNo",			Width: 100,		 Align:"Center", 	Edit:0,	Hidden:1, Sort:0}
			, {Header:"발생주문번호",		Type:"Text",	SaveName:"orderNo",				Width: 120,		 Align:"Center",	Edit:0, Hidden:1}
			, {Header:"주문번호",			Type:"Text",	SaveName:"orgOrderNo",			Width: 120,		 Align:"Center",	Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer", Sort:0 }
			, {Header:"배송ID",			Type:"Text",	SaveName:"dlvyIdText",	  		Width: 150,		 Align:"Center", 	Edit:0, Sort:0}
			, {Header:"처리구분",			Type:"Text",	SaveName:"procGubnCodeName",	Width: 150,		 Align:"Center", 	Edit:0, Sort:0}
			, {Header:"처리구분코드",		Type:"Text",	SaveName:"procGubnCode",		Width: 150,		 Align:"Center", 	Edit:0, Sort:0, Hidden:1}
			, {Header:"처리구분select",	Type:"Text",	SaveName:"procGubnSelect",		Width: 150,		 Align:"Center", 	Edit:0, Sort:0, Hidden:1}
			, {Header:"상품코드", 			Type:"Text",	SaveName:"prdtNo",				Width: 150,		 Align:"Center", 	Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer" }
			, {Header:"전시여부", 			Type:"Combo",	SaveName:"optionUseYn",			Width: 80,		 Align:"Center", 	Edit:0, ComboText: "전시|전시안함", ComboCode: "Y|N", Sort:0 }
			, {Header:"상품명", 			Type:"Text",	SaveName:"prdtName",			Width: 200,		 Align:"Left", 		Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer" }
			, {Header:"옵션", 			Type:"Text",	SaveName:"optnName",			Width: 100,		 Align:"Center", 	Edit:0}
			/* 추후 수정시 주석 해제 필요(재고 주문수량 관련)
			, {Header:"온라인\n재고", 		Type:"Int",		SaveName:"stockQtyAi",			Width: 100,		 Align:"Center", 	Edit:0,	Hidden:1 }
			, {Header:"스마트\n재고", 		Type:"Int",		SaveName:"stockQtyAw",			Width: 100,		 Align:"Center", 	Edit:0,	Hidden:1 }
			, {Header:"매장\n재고", 		Type:"Int",		SaveName:"stockQtyAs",			Width: 100,		 Align:"Center", 	Edit:0,	Hidden:1 }
			, {Header:"온라인\n주문수량", 	Type:"Int",		SaveName:"orderQtyAi",			Width: 100,		 Align:"Center", 	Edit:0,	Hidden:1 }
			, {Header:"스마트\n주문수량", 	Type:"Int",		SaveName:"orderQtyAw",			Width: 100,		 Align:"Center", 	Edit:0,	Hidden:1 }
			, {Header:"매장\n주문수량", 		Type:"Int",		SaveName:"orderQtyAs",			Width: 100,		 Align:"Center", 	Edit:0,	Hidden:1 }
			*/
			, {Header:"온라인\n주문가능수량", 	Type:"Int",		SaveName:"availableQtyAi",		Width: 80,		 Align:"Center", 	Edit:0, Sort:0}
			, {Header:"스마트\n주문가능수량", 	Type:"Int",		SaveName:"availableQtyAw",		Width: 80,		 Align:"Center", 	Edit:0, Sort:0}
			, {Header:"매장\n주문가능수량", 	Type:"Int",		SaveName:"availableQtyAs",		Width: 80,		 Align:"Center", 	Edit:0, Sort:0}
			, {Header:"주문자",			Type:"Text",	SaveName:"buyerName",			Width: 80,		 Align:"Center", 	Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer", Sort:0}
			, {Header:"주문자ID",			Type:"Text",	SaveName:"buyerId",				Width: 120,		 Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"주문자번호",			Type:"Text",	SaveName:"memberNo",			Width: 80,		 Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"미출처",			Type:"Text",	SaveName:"stockGbnCodeName",	Width: 80,		 Align:"Center", 	Edit:0, Sort:0}
			, {Header:"배송유형", 			Type:"Combo",	SaveName:"dlvyTypeCode",		Width: 80,		 Align:"Center", 	Edit:0, Sort:0}
			, {Header:"발생일", 			Type:"Text",	SaveName:"dlvyDscntcAcceptDtm",	Width: 120,		 Align:"Center", 	Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN, Sort:0}
			, {Header:"처리일시", 			Type:"Text",	SaveName:"dlvyDscntcProcDtm",	Width: 120,		 Align:"Center", 	Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN, Sort:0}
			, {Header:"처리자",			Type:"Text",	SaveName:"dlvyDscntcOpetr",		Width: 120,		 Align:"Center", 	Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer", Sort:0}
			, {Header:"처리자번호",			Type:"Text",	SaveName:"dlvyDscntcOpetrNo",	Width: 80,		 Align:"Center", 	Edit:0,	Hidden:1}
			, {Header:"처리자이름",			Type:"Text",	SaveName:"dlvyDscntcOpetrName",	Width: 80,		 Align:"Center", 	Edit:0,	Hidden:1}
			, {Header:"처리자ID",			Type:"Text",	SaveName:"dlvyDscntcOpetrId",	Width: 80,		 Align:"Center", 	Edit:0,	Hidden:1}
			, {Header:"처리상태",			Type:"Combo",	SaveName:"dlvyDscntcProc",		Width: 80,		 Align:"Center", 	Edit:0, Sort:0}
			, {Header:"배송중단사유"	,		Type:"Combo",	SaveName:"dlvyDscntcRsnCode",	Width: 120,		 Align:"Center", 	Edit:0, Sort:0} 
			, {Header:"주문상품순번",		Type:"Text",	SaveName:"orderPrdtSeq",		Width: 80,		 Align:"Center", 	Edit:0, Sort:0}
			, {Header:"주문배송이력순번",		Type:"Text",	SaveName:"orderDlvyHistSeq",	Width: 120,		 Align:"Center", 	Edit:0,	Hidden:1}
			, {Header:"배송상태",			Type:"Text",	SaveName:"dlvyStatCodeName",	Width: 150,		 Align:"Center", 	Edit:0, Sort:0}
			, {Header:"배송상태CODE",		Type:"Text",	SaveName:"dlvyStatCode",		Width: 150,		 Align:"Center", 	Edit:0,	Hidden:1}
			, {Header:"배송주소",			Type:"Text",	SaveName:"rcvrPostAddrText",	Width: 150,		 Align:"Center", 	Edit:0, Sort:0}
			, {Header:"발송매장",			Type:"Text",	SaveName:"storeName",			Width: 100,		 Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"배송유형Code",		Type:"Text",	SaveName:"stockGbnCode",		Width: 80,		 Align:"Center", 	Edit:0, Sort:0}
			, {Header:"채널번호",			Type:"Text",	SaveName:"chnnlNo",				Width: 80,		 Align:"Center", 	Edit:0, Sort:0}
			, {Header:"업체상품코드",		Type:"Text",	SaveName:"vndrPrdtNoText",		Width: 80,		 Align:"Center", 	Edit:0, Sort:0}
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
 
       dataListSheet.InitDataCombo(0, "dlvyDscntcRsnCode",  this.codeCombo.DLVY_DSCNTC_RSN_CODE.text,   this.codeCombo.DLVY_DSCNTC_RSN_CODE.code );		 //배송중단사유
	   //dataListSheet.InitDataCombo(0, "stockGbnCode",  this.codeCombo.STOCK_GBN_CODE.text,   this.codeCombo.STOCK_GBN_CODE.code );		 //발송처
	   dataListSheet.InitDataCombo(0, "dlvyTypeCode",  this.codeCombo.DLVY_TYPE_CODE.text,   this.codeCombo.DLVY_TYPE_CODE.code );		 //배송유형콤보코드
	   dataListSheet.InitDataCombo(0, "dlvyDscntcProc", "대기|완료"   ,  "01|02"    );		 //접수여부
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
			
			var dlvyDscntcOpetrNo = dataListSheet.GetRowData(i).dlvyDscntcOpetrNo;
			
			if( !abc.text.allNull(dlvyDscntcOpetrNo) ){
				dataListSheet.SetRowData(i, {dlvyDscntcOpetr : dataListSheet.GetRowData(i).dlvyDscntcOpetrName + "(" + dataListSheet.GetRowData(i).dlvyDscntcOpetrId + ")"});
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
	 
		 //재배송 처리
		 $("#deliveryRepeat").click(function(){ 
			_deliveryJsObject.doDeliveryRepeatSave();
		});

		//선택 엑셀 받기
		 $("#excelDownSelect").click(function(){
			 _deliveryJsObject.excelSelectDown();
		});

		//전체 엑셀 받기
		 $("#excelDownAll").click(function(){ 
			 _deliveryJsObject.excelAllDown();
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

		// 주문번호 자릿수 제한
		$("#searchOrderValue").keyup(function(){
			if( $("#searchOrderKey").val() == "orderNo" ){
				var orderNo = $("#searchOrderValue").val();
				orderNo = orderNo.substring(0,13);
				$("#searchOrderValue").val(orderNo);
			}
		});
		
		// 2020.05.22 : 주문 검색어 선택 이벤트
		$("#searchOrderKey").change(function(){
			var orderSearchKey = $(this).val();
			_deliveryJsObject.ordMbInfoSelectChange(orderSearchKey);
		});
		
		//처리구분 선택 이벤트
		$("input[name=dlvyDscntcProc]").change(function(){
			_deliveryJsObject.procGubnChange($(this).val());
		});
		
		//처리 확정 버튼
		$("#procGubnConfirm").on("click", function(){
			_deliveryJsObject.procGubnConfirm();
		});
		
		$("input[name=procGubnCodeArr]").on("click", function(){
			if($(this).val() == "") {
				$("input[name='procGubnCodeArr']").prop("checked", this.checked);
			} else {
				$("input[name='procGubnCodeArr']:eq(0)").prop("checked", false);
			}

			//전체 선택인경우
			var unChecked = $("input[clickYn=procGubnCodeArr]:not(:checked)");
			if (unChecked.length == 0) {
				$("input[name='procGubnCodeArr']:eq(0)").prop("checked", true);
			}
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
	
	//처리상태 변경시
	_deliveryJsObject.procGubnChange = function(value){
		var checkBox = $("#procGubnCheckArea").find("input");
		if(value == "01"){
			checkBox.each(function(i, v){
				if($(v).val() == '10000' || $(v).val() == '10002'){
					$(v).prop("checked", true);
				}else{
					$(v).prop("checked", false);
				}
			});
		}else{
			checkBox.each(function(i, v){
				$(v).prop("checked", true);
			});
		}
	}
	
	//처리구분 변경 버튼 
	_deliveryJsObject.procGubnConfirm = function(){
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
		var procGubnSelect = $("#procGubnSelect").val();
		
		var dlvyStatCode;
		var dlvyStatCodeName;
		var dlvyIdText;
		var procGubnCode;
		var procGubnCodeName;
		
		var changeList = [];
		for (var i=0; i<arrRow.length; i++){  
			
			dlvyStatCode =  dataListSheet.GetCellValue( arrRow[i], "dlvyStatCode");			//배송상태 코드
			dlvyStatCodeName =  dataListSheet.GetCellValue( arrRow[i], "dlvyStatCodeName");   // 배송 유형 ( 일반택배, 편의점 픽업만 가능 - 매장픽업 불가)
			dlvyIdText =  dataListSheet.GetCellValue( arrRow[i], "dlvyIdText");   			// 배송 ID
			procGubnCode =  dataListSheet.GetCellValue( arrRow[i], "procGubnCode");   		// 처리구분코드
			procGubnCodeName =  dataListSheet.GetCellValue( arrRow[i], "procGubnCodeName");   // 처리구분 코드명
			
			//배송상태 체크 -- 결제완료와 상품준비중일경우만 처리 가능
			if((dlvyStatCode == "10006" && abc.text.allNull(procGubnCode)) 
					|| (dlvyStatCode == "10006" && procGubnCode == "10003")){
				alert("배송취소건은 확정처리 불가능합니다." +"\n * 배송ID "+ dlvyIdText +"::[배송상태]:"+dlvyStatCodeName);
				return;
			}

			//처리상태  제외
			if(procGubnCode == '10000' || procGubnCode == "10001") {
				alert("처리구분 상태가 " + procGubnCodeName + "인 경우 확정처리 불가능합니다.  \n* 배송ID "+ dlvyIdText);
				return;
			}
			dataListSheet.SetCellValue( arrRow[i], "procGubnSelect", procGubnSelect);
			changeList.push(dataListSheet.GetRowData(arrRow[i]));
		 }

		// save
		if(!confirm("확정처리 하시겠습니까?")){
			return false;
		} //end confirm
		
		$.ajax({
			dataType : "json",
			type :"POST",
			url : "/delivery/delivery-order-standby/confirm-save",
			data : $.paramObject(changeList)
		}).done(function(data){
			if(data.result){
				alert(data.count + '건 확정 처리되었습니다.');
				_deliveryJsObject.doActionJs("search");
			}else{
				alert("처리중 오류가 발생하였습니다.");
				console.log(data.msg);
				_deliveryJsObject.doActionJs("search");
			}
		}).fail(function(e){
			console.log(e);
			alert(e.responseJSON.message);
		});
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
				var param = { url : "/delivery/delivery-order-standby/read-list"
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
		
		if( abc.date.searchValidate() == false ){
			return false;
		}
		
		var dateDiff = abc.date.dateDiffAbs($("#fromDate").val(), $("#toDate").val());
		
		if(dateDiff > 365) {
			
			alert("검색 기간은 1년을 넘을 수 없습니다.");
			$("#fromDate").focus();
			return false;
		}
		 
		return true;

	} //end _deliveryJsObject.searchValidate
 		
		

	/**
	 *  재배송 처리 상태
	 */
	_deliveryJsObject.doDeliveryRepeatSave = function(){
		
		//선택여부 체크 * 형식이 있기에 두형식으로 사용 해봄
		 var sRow = dataListSheet.CheckedRows("checkedRows");
		 //선택 데이타 Rows
		var checkRows = dataListSheet.FindCheckedRow("checkedRows");

		//변경 물류처
		var newStockGbnCode = $("#newStockGbnCode").val();
		 
		 if(sRow <1 ){
				alert(alertSelectEmpty);
				return;
		 } //if(sRow

       //발송처 체크
		if(newStockGbnCode == ""){
			alert(alertSelectStockGbn);
			return;
		}

		 //제약 사항 체크
         var arrRow = checkRows.split("|"); 

		 for (idx=0; idx<arrRow.length; idx++){  
			
			var dlvyDscntcRsnCode =  dataListSheet.GetCellValue( arrRow[idx], "dlvyDscntcRsnCode");		 //매장픽업  택배전환 제외
			var dlvyStatCode =  dataListSheet.GetCellValue( arrRow[idx], "dlvyStatCode");		 //배송상태 코드
			var dlvyStatCodeName =  dataListSheet.GetCellValue( arrRow[idx], "dlvyStatCodeName");		 //배송상태
			var dlvyTypeCode =  dataListSheet.GetCellValue( arrRow[idx], "dlvyTypeCode");   // 배송 유형 ( 일반택배, 편의점 픽업만 가능 - 매장픽업 불가)
			var dlvyIdText =  dataListSheet.GetCellValue( arrRow[idx], "dlvyIdText");   // 배송 ID
			var rcvrPostAddrText =  dataListSheet.GetCellValue( arrRow[idx], "rcvrPostAddrText");   // 배송주소
			var dlvyDscntcProc =  dataListSheet.GetCellValue( arrRow[idx], "dlvyDscntcProc");   // 처리상태
			var stockGbnCodeValue =  dataListSheet.GetCellValue( arrRow[idx], "stockGbnCode");   // 처리상태
			
			

			//발송처 체크 - 매장 픽업은 제외
			if( stockGbnCodeValue =="10002" ){
				alert( "매장건은 재배송  처리가되지 않습니다.  \n * 배송ID  "+ dlvyIdText);
				return;
			}  
			
			//배송유형 체크 - 매장 픽업은 제외
			if( dlvyTypeCode =="10002" ){
				alert( alertValidSave +"\n * 배송ID  "+ dlvyIdText+"::[배송유형]:"+dlvyStatCodeName);
				return;
			}  
			
			//배송상태 체크 -- 결제완료와 상품준비중일경우만 처리 가능
			if( dlvyStatCode  !="10000" &&  dlvyStatCode !="10001"  ){
					alert( alertValidSave +"\n * 배송ID "+ dlvyIdText +"::[배송상태]:"+dlvyStatCodeName);
				return;
			}
 

			//제주지역은 제외
			if(rcvrPostAddrText.indexOf("제주") > 0 ) {
				alert( alertValidSave +" \n (제주지역은 제외) \n* 배송ID "+ dlvyIdText);
				return;
			}

			//처리상태  제외
			if( dlvyDscntcProc == '02' ) {
				alert( alertValidSave +" \n (처리상태 완료) \n* 배송ID "+ dlvyIdText);
				return;
			}

			
			//처리상태  제외
			if( dlvyDscntcRsnCode == '10005' ) {
				alert("매장칙업 택배전환사유는 제외됩니다. \n (사유코드 제외) \n* 배송ID "+ dlvyIdText);
				return;
			}

			
		 }//end for

		// save
		if(confirm( alertConfirmSvae )){
				 var param = {
								url : "/delivery/delivery-order-standby/save",
								subparam : FormQueryStringEnc(document.gForm),
								sheet : "dataListSheet"
							};
				DataSave(param);
		} //end confirm

     } //end _deliveryJsObject.doDeliveryRepeatSave 
	
	
	//상품대기조회 엑셀 선택 다운로드
	_deliveryJsObject.excelSelectDown = function() {
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
	}
	
	_deliveryJsObject.excelAllDown = function() {
		var thisForm = $('#gForm');
		thisForm.prop('action', '/delivery/delivery-order-standby/excel-all-down')
				.prop('method', 'post')
				.submit();
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
	/**
	 * zero  체크
	 */
	_deliveryJsObject.AddZero = function(str) {
		if((str+"").length==1){
			return "0"+str;
		}	
		return str;
	} 
	

})();	 //end  (function() {


 //그리드 Click 이벤트 
function dataListSheet_OnClick(Row, Col, Value) {
	
	if ( Row != 0) {
		
		//파라미터값셋팅
		var params = {}
			params.orderStatCode = dataListSheet.GetCellValue(Row, "orderStatCode");

		//주문상세
		if ( dataListSheet.ColSaveName(Col) == "orgOrderNo") {
			abc.orderDetailPopup(dataListSheet,"orgOrderNo",Row,Col,"");
		}
		
		//상품코드
		if( dataListSheet.ColSaveName(Col) == "prdtNo" ) {
			
			var prdtNo =  dataListSheet.GetCellValue(Row, "prdtNo");
			var siteNo =  dataListSheet.GetCellValue(Row, "siteNo");
			var chnnlNo =  dataListSheet.GetCellValue(Row, "chnnlNo");
			var vndrPrdtNoText =  dataListSheet.GetCellValue(Row, "vndrPrdtNoText");
			abc.modProductDetailPopup(prdtNo,siteNo,chnnlNo,vndrPrdtNoText);
		
		}
		
		//상품명
		if ( dataListSheet.ColSaveName(Col) == "prdtName"   ) {
				
				var prdtNo = dataListSheet.GetRowData(Row).prdtNo;
				var siteNo = dataListSheet.GetRowData(Row).orgSiteNo;
				
				if(siteNo == abc.biz.delivery.order.standby.main.SITE_NO_ART){
					window.open(abc.biz.delivery.order.standby.main.ABC_FO_URL + "/product?prdtNo=" + prdtNo)
				} else {
					window.open(abc.biz.delivery.order.standby.main.OTS_FO_URL + "/product?prdtNo=" + prdtNo)
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
			if( memberNo != abc.biz.delivery.order.standby.main.NON_MEMBER_NO){
				abc.memberDetailPopup(memberNo);
			}
		}
		
		//처리자
		if ( dataListSheet.ColSaveName(Col) == "dlvyDscntcOpetr") {
			var dlvyDscntcOpetrNo =  dataListSheet.GetCellValue(Row, "dlvyDscntcOpetrNo");
			abc.adminDetailPopup(dlvyDscntcOpetrNo);
		}
	} //end if ( Row != 0) {
} //end function 그리드 Click 이벤트 

	

	
	 